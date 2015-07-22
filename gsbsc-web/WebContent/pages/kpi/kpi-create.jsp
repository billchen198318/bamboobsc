<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>bambooCORE</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bambooCORE">
	<meta http-equiv="description" content="bambooCORE">
	
<style type="text/css">

</style>

<script type="text/javascript">

var BSC_PROG002D0004A_fieldsId = new Object();
BSC_PROG002D0004A_fieldsId['visionOid'] 		= 'BSC_PROG002D0004A_visionOid';
BSC_PROG002D0004A_fieldsId['perspectiveOid'] 	= 'BSC_PROG002D0004A_perspectiveOid';
BSC_PROG002D0004A_fieldsId['objectiveOid'] 		= 'BSC_PROG002D0004A_objectiveOid';
BSC_PROG002D0004A_fieldsId['id'] 				= 'BSC_PROG002D0004A_id';
BSC_PROG002D0004A_fieldsId['name'] 				= 'BSC_PROG002D0004A_name';
BSC_PROG002D0004A_fieldsId['formulaOid'] 		= 'BSC_PROG002D0004A_formulaOid';
BSC_PROG002D0004A_fieldsId['weight'] 			= 'BSC_PROG002D0004A_weight';
BSC_PROG002D0004A_fieldsId['target'] 			= 'BSC_PROG002D0004A_target';
BSC_PROG002D0004A_fieldsId['min'] 				= 'BSC_PROG002D0004A_min';
BSC_PROG002D0004A_fieldsId['compareType'] 		= 'BSC_PROG002D0004A_compareType';
BSC_PROG002D0004A_fieldsId['unit'] 				= 'BSC_PROG002D0004A_unit';
BSC_PROG002D0004A_fieldsId['management'] 		= 'BSC_PROG002D0004A_management';
BSC_PROG002D0004A_fieldsId['cal'] 				= 'BSC_PROG002D0004A_cal';
BSC_PROG002D0004A_fieldsId['dataType'] 			= 'BSC_PROG002D0004A_dataType';

function BSC_PROG002D0004A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG002D0004A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0004A_fieldsId);		
		return;
	}	
	BSC_PROG002D0004A_clear();
}

function BSC_PROG002D0004A_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0004A_fieldsId);	
	clearSelectItems(true, 'BSC_PROG002D0004A_objectiveOid'); // 當 vision 改變後要將 Objective 清掉, 因為只會觸發 perspective 項目的改變
	dijit.byId('BSC_PROG002D0004A_visionOid').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG002D0004A_perspectiveOid').set("value", _gscore_please_select_id); // vision下拉會觸發perspective下拉更新項目
	dijit.byId('BSC_PROG002D0004A_id').set("value", "");		
	dijit.byId('BSC_PROG002D0004A_name').set("value", "");		
	dijit.byId('BSC_PROG002D0004A_formulaOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004A_weight').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004A_target').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004A_min').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004A_compareType').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004A_unit').set("value", "");
	dijit.byId('BSC_PROG002D0004A_management').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004A_quasiRange').set("value", "0");
	dijit.byId('BSC_PROG002D0004A_cal').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004A_dataType').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004A_orgaMeasureSeparate').set("checked", true);
	dijit.byId('BSC_PROG002D0004A_userMeasureSeparate').set("checked", true);
	dijit.byId('BSC_PROG002D0004A_description').set("value", "");	
	BSC_PROG002D0004A_clearOrgaAppendId();
	BSC_PROG002D0004A_clearEmplAppendId();
}

/**
 * Vision下拉觸發,改變Perspective下拉項目
 */
function BSC_PROG002D0004A_triggerChangePerspectiveItems() {
	clearSelectItems(true, 'BSC_PROG002D0004A_objectiveOid'); // 當 vision 改變後要將 Objective 清掉, 因為只會觸發 perspective 項目的改變
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004A_visionOid',
			'BSC_PROG002D0004A_perspectiveOid',
			'${basePath}/bsc.commonGetPerspectiveItemsAction.action',
			{ 'fields.visionOid' : dijit.byId("BSC_PROG002D0004A_visionOid").get("value") }
	);	
}

/**
 * Perspective下拉觸發,改變Objective下拉項目
 */
function BSC_PROG002D0004A_triggerChangeObjectiveItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004A_perspectiveOid',
			'BSC_PROG002D0004A_objectiveOid',
			'${basePath}/bsc.commonGetObjectiveItemsAction.action',
			{ 'fields.perspectiveOid' : dijit.byId("BSC_PROG002D0004A_perspectiveOid").get("value") }
	);	
}

function BSC_PROG002D0004A_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG002D0004A_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG002D0004A_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG002D0004A_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG002D0004A_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG002D0004A_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG002D0004A_reloadEmployeeAppendName() {
	var appendOid = dojo.byId('BSC_PROG002D0004A_appendEmployeeOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG002D0004A_appendEmployeeOid').value = '';
		dojo.byId('BSC_PROG002D0004A_employeeAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG002D0004A_appendEmployeeOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG002D0004A_employeeAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG002D0004A_clearOrgaAppendId() {
	dojo.byId('BSC_PROG002D0004A_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG002D0004A_organizationAppendName').innerHTML = '';
}

function BSC_PROG002D0004A_clearEmplAppendId() {
	dojo.byId('BSC_PROG002D0004A_appendEmployeeOid').value = '';
	dojo.byId('BSC_PROG002D0004A_employeeAppendName').innerHTML = '';
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="claro" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG002D0004A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG002D0004A_appendOrganizationOid" id="BSC_PROG002D0004A_appendOrganizationOid" value="" />
	<input type="hidden" name="BSC_PROG002D0004A_appendEmployeeOid" id="BSC_PROG002D0004A_appendEmployeeOid" value="" />
	
	<table border="0" width="850" height="800px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_visionOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_visionOid" dataSource="visionMap" id="BSC_PROG002D0004A_visionOid" onChange="BSC_PROG002D0004A_triggerChangePerspectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_visionOid'">
    				Select vision.
				</div>       			
    		</td>    		
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_id')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004A_id" id="BSC_PROG002D0004A_id" value="" width="200" maxlength="14"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_id'">
    				Input Id, only allow normal characters. 
				</div>     			
    		</td>      		
    	</tr>		
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_perspectiveOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0004A_perspectiveOid" onChange="BSC_PROG002D0004A_triggerChangeObjectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_perspectiveOid'">
    				Select perspectives.
				</div>       			
    		</td>    		
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_name')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004A_name" id="BSC_PROG002D0004A_name" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_name'">
    				Input name.
				</div>      			
    		</td>        		
    	</tr>		    
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_objectiveOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_objectiveOid" dataSource="objectiveMap" id="BSC_PROG002D0004A_objectiveOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_objectiveOid'">
    				Select objectives.
				</div>     			
    		</td>    		
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_formulaOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_formulaOid" dataSource="formulaMap" id="BSC_PROG002D0004A_formulaOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_formulaOid'">
    				Select formula. the options will effect result score.
				</div>      			
    		</td>      		
    	</tr>	  		     			    	    
		<tr>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_weight')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004A_weight" name="BSC_PROG002D0004A_weight" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:0.00,max:999.00, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_weight'">
    				Input weight value, only allow numbers. range: 0.0 ~ 100.0 
				</div>       				    				     		    			
    		</td>    		
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_management')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_management" dataSource="managementMap" id="BSC_PROG002D0004A_management"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_management'">
    				Select management method:<BR/> 
    				1. Bigger ( score bigger is better) <BR/> 
    				2. Smaller ( score smaller is better)  <BR/>
    				3. Quasi ( score quasi is better) <BR/>
    				the options will effect KPI Report result date-range item UP / DOWN status icon.
				</div>      			
    			&nbsp;&nbsp;
    			<b><s:property value="getText('BSC_PROG002D0004A_quasiRange')"/></b>:
    			<gs:select name="BSC_PROG002D0004A_quasiRange" dataSource="quasiRangeMap" id="BSC_PROG002D0004A_quasiRange" width="60"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_quasiRange'">
    				Select quasi-range %. <BR/>
    				Example: <BR/>
    				&nbsp;&nbsp;if a KPI item result-score is 90 , quasi-range is 15 , target value is 100 ,<BR/> 
    				&nbsp;&nbsp;and management method is 'quasi is better' , compare type is 'Target' <BR/>
    				<BR/>
    				&nbsp;&nbsp;quasi-range (Max) = target(100) + ( 100 * 15/100 ) = 113.5 <BR/>
    				&nbsp;&nbsp;quasi-range (Min) = target(100) - ( 100 * 15/100 ) = 86.5 <BR/>
    				<BR/>
    				&nbsp;&nbsp;score(90) is bigger then value: Min(86.5) and small then value: Max(113.5) . so KPI status is Good / Up.    				
				</div>    			      			    			
    		</td>      		
    	</tr>        	
		<tr>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_target')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004A_target" name="BSC_PROG002D0004A_target" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />     		
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_target'">
    				Input target value, only allow numbers.
				</div>       				    			
    		</td>  
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_cal')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_cal" dataSource="calculationMap" id="BSC_PROG002D0004A_cal"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_cal'">
    				Select calculation. ( aggregation method ) the options will effect result score.
				</div>     			
    		</td>     		  		
    	</tr>   
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_min')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004A_min" name="BSC_PROG002D0004A_min" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_min'">
    				Input min value, only allow numbers.
				</div>       				     		    			
    		</td>    		
    	</tr>       	    	
		<tr>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_compareType')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_compareType" dataSource="compareTypeMap" id="BSC_PROG002D0004A_compareType"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_compareType'">
    				Select compare type. <BR/>
    				&nbsp;Target: KPI item result score compare with target value. <BR/>
    				<BR/>
    				&nbsp;Min: KPI item result score compare with min value. <BR/>
    				<BR/>
    				&nbsp;the options will effect KPI Report result date-range item UP / DOWN status icon.
				</div>      			
    		</td>   
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_unit')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004A_unit" id="BSC_PROG002D0004A_unit" value="" width="150" maxlength="20"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_unit'">
    				Input unit, the maximum allowed 20 characters. 
				</div>     			
    		</td>       		 		
    	</tr>      	    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004A_dataType')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004A_dataType" dataSource="dataTypeMap" id="BSC_PROG002D0004A_dataType"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_dataType'">
    				Select belong type.<BR/>
    				Example:<BR/>
    				&nbsp;Department/Organization type:<BR/>
    				&nbsp;&nbsp;KPI item measure data for 'All' and 'Department/Organization'.<BR/><BR/>
    				&nbsp;Personal/Employee:<BR/>
    				&nbsp;&nbsp;KPI item measure data for 'All' and 'Personal/Employee'.<BR/><BR/>
    				&nbsp;Both:<BR/>
    				&nbsp;&nbsp;KPI item measure data for 'All' and 'Department/Organization' and 'Personal/Employee'.<BR/>
				</div>     			
    		</td>    		
    	</tr>    	    	    	    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004A_deptSelect')"/></b>:
    			&nbsp;&nbsp;
				<button name="BSC_PROG002D0004A_deptSelect" id="BSC_PROG002D0004A_deptSelect" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG002D0004A_appendOrganizationOid;BSC_PROG002D0004A_reloadOrganizationAppendName');
						}
					"></button>
				<button name="BSC_PROG002D0004A_deptClear" id="BSC_PROG002D0004A_deptClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG002D0004A_clearOrgaAppendId();
						}
					"></button>	
				<br/>	    			    			
    			<span id="BSC_PROG002D0004A_organizationAppendName"></span>    			
    		</td>
    	</tr>     
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004A_emplSelect')"/></b>:
    			&nbsp;&nbsp;
				<button name="BSC_PROG002D0004A_emplSelect" id="BSC_PROG002D0004A_emplSelect" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG002D0004A_appendEmployeeOid;BSC_PROG002D0004A_reloadEmployeeAppendName');
						}
					"></button>
				<button name="BSC_PROG002D0004A_emplClear" id="BSC_PROG002D0004A_emplClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG002D0004A_clearEmplAppendId();
						}
					"></button>		
				<br/>
				<span id="BSC_PROG002D0004A_employeeAppendName"></span>	    			
    		</td>    
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004A_orgaMeasureSeparate')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004A_orgaMeasureSeparate" name="BSC_PROG002D0004A_orgaMeasureSeparate" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_orgaMeasureSeparate'">
    				enable, can input KPI item measure-data for organization/department. 
				</div> 				    			
    		</td>
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004A_userMeasureSeparate')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004A_userMeasureSeparate" name="BSC_PROG002D0004A_userMeasureSeparate" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_userMeasureSeparate'">
    				enable, can input KPI item measure-data for personal/Employee. 
				</div> 				    			    			    		
    		</td>    		
    	</tr>   	       	
		<tr>
		    <td height="150px" width="100%" align="left" colspan="2">
		    	<b><s:property value="getText('BSC_PROG002D0004A_description')"/></b>:
		    	<br/>
		    	<textarea id="BSC_PROG002D0004A_description" name="BSC_PROG002D0004A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004A_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   		    	
		    </td>
		</tr>      	  	    	    	   	  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<gs:button name="BSC_PROG002D0004A_save" id="BSC_PROG002D0004A_save" onClick="BSC_PROG002D0004A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.kpiSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.visionOid'			: dijit.byId('BSC_PROG002D0004A_visionOid').get('value'),
    						'fields.perspectiveOid'		: dijit.byId('BSC_PROG002D0004A_perspectiveOid').get('value'),
    						'fields.objectiveOid'		: dijit.byId('BSC_PROG002D0004A_objectiveOid').get('value'),
    						'fields.id'					: dijit.byId('BSC_PROG002D0004A_id').get('value'),
    						'fields.name'				: dijit.byId('BSC_PROG002D0004A_name').get('value'),   						
    						'fields.formulaOid'			: dijit.byId('BSC_PROG002D0004A_formulaOid').get('value'),
    						'fields.weight'				: dijit.byId('BSC_PROG002D0004A_weight').get('value'),
    						'fields.target'				: dijit.byId('BSC_PROG002D0004A_target').get('value'),    						 
    						'fields.min'				: dijit.byId('BSC_PROG002D0004A_min').get('value'),    						
    						'fields.compareType'		: dijit.byId('BSC_PROG002D0004A_compareType').get('value'),
    						'fields.unit'				: dijit.byId('BSC_PROG002D0004A_unit').get('value'),
    						'fields.management'			: dijit.byId('BSC_PROG002D0004A_management').get('value'),
    						'fields.quasiRange'			: dijit.byId('BSC_PROG002D0004A_quasiRange').get('value'),  				
    						'fields.cal'				: dijit.byId('BSC_PROG002D0004A_cal').get('value'),
    						'fields.dataType'			: dijit.byId('BSC_PROG002D0004A_dataType').get('value'),
    						'fields.orgaMeasureSeparate': ( dijit.byId('BSC_PROG002D0004A_orgaMeasureSeparate').checked ? 'true' : 'false' ),
    						'fields.userMeasureSeparate': ( dijit.byId('BSC_PROG002D0004A_userMeasureSeparate').checked ? 'true' : 'false' ),  		
    						'fields.description'		: dijit.byId('BSC_PROG002D0004A_description').get('value'),
    						'fields.orgaOids'			: dojo.byId('BSC_PROG002D0004A_appendOrganizationOid').value,
    						'fields.emplOids'			: dojo.byId('BSC_PROG002D0004A_appendEmployeeOid').value
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0004A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0004A_save')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG002D0004A_clear" id="BSC_PROG002D0004A_clear" onClick="BSC_PROG002D0004A_clear();" 
    				label="${action.getText('BSC_PROG002D0004A_clear')}" 
    				iconClass="dijitIconClear"></gs:button>    	    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
