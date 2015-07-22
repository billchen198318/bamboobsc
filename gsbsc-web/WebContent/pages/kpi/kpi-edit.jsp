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

var BSC_PROG002D0004E_fieldsId = new Object();
BSC_PROG002D0004E_fieldsId['visionOid'] 		= 'BSC_PROG002D0004E_visionOid';
BSC_PROG002D0004E_fieldsId['perspectiveOid'] 	= 'BSC_PROG002D0004E_perspectiveOid';
BSC_PROG002D0004E_fieldsId['objectiveOid'] 		= 'BSC_PROG002D0004E_objectiveOid';
BSC_PROG002D0004E_fieldsId['id'] 				= 'BSC_PROG002D0004E_id';
BSC_PROG002D0004E_fieldsId['name'] 				= 'BSC_PROG002D0004E_name';
BSC_PROG002D0004E_fieldsId['formulaOid'] 		= 'BSC_PROG002D0004E_formulaOid';
BSC_PROG002D0004E_fieldsId['weight'] 			= 'BSC_PROG002D0004E_weight';
BSC_PROG002D0004E_fieldsId['target'] 			= 'BSC_PROG002D0004E_target';
BSC_PROG002D0004E_fieldsId['min'] 				= 'BSC_PROG002D0004E_min';
BSC_PROG002D0004E_fieldsId['compareType'] 		= 'BSC_PROG002D0004E_compareType';
BSC_PROG002D0004E_fieldsId['unit'] 				= 'BSC_PROG002D0004E_unit';
BSC_PROG002D0004E_fieldsId['management'] 		= 'BSC_PROG002D0004E_management';
BSC_PROG002D0004E_fieldsId['cal'] 				= 'BSC_PROG002D0004E_cal';
BSC_PROG002D0004E_fieldsId['dataType'] 			= 'BSC_PROG002D0004E_dataType';

function BSC_PROG002D0004E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0004E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0004E_fieldsId);		
		return;
	}		
}

function BSC_PROG002D0004E_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0004E_fieldsId);	
	clearSelectItems(true, 'BSC_PROG002D0004E_objectiveOid'); // 當 vision 改變後要將 Objective 清掉, 因為只會觸發 perspective 項目的改變
	dijit.byId('BSC_PROG002D0004E_visionOid').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG002D0004E_perspectiveOid').set("value", _gscore_please_select_id); // vision下拉會觸發perspective下拉更新項目
	//dijit.byId('BSC_PROG002D0004E_id').set("value", "");		
	dijit.byId('BSC_PROG002D0004E_name').set("value", "");		
	dijit.byId('BSC_PROG002D0004E_formulaOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004E_weight').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004E_target').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004E_min').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0004E_compareType').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004E_unit').set("value", "");
	dijit.byId('BSC_PROG002D0004E_management').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004E_quasiRange').set("value", "0");
	dijit.byId('BSC_PROG002D0004E_cal').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004E_dataType').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004E_orgaMeasureSeparate').set("checked", true);
	dijit.byId('BSC_PROG002D0004E_userMeasureSeparate').set("checked", true);
	dijit.byId('BSC_PROG002D0004E_description').set("value", "");	
	BSC_PROG002D0004E_clearOrgaAppendId();
	BSC_PROG002D0004E_clearEmplAppendId();
}

/**
 * Vision下拉觸發,改變Perspective下拉項目
 */
function BSC_PROG002D0004E_triggerChangePerspectiveItems() {
	clearSelectItems(true, 'BSC_PROG002D0004E_objectiveOid'); // 當 vision 改變後要將 Objective 清掉, 因為只會觸發 perspective 項目的改變
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004E_visionOid',
			'BSC_PROG002D0004E_perspectiveOid',
			'${basePath}/bsc.commonGetPerspectiveItemsAction.action',
			{ 'fields.visionOid' : dijit.byId("BSC_PROG002D0004E_visionOid").get("value") }
	);	
}

/**
 * Perspective下拉觸發,改變Objective下拉項目
 */
function BSC_PROG002D0004E_triggerChangeObjectiveItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004E_perspectiveOid',
			'BSC_PROG002D0004E_objectiveOid',
			'${basePath}/bsc.commonGetObjectiveItemsAction.action',
			{ 'fields.perspectiveOid' : dijit.byId("BSC_PROG002D0004E_perspectiveOid").get("value") }
	);	
}

function BSC_PROG002D0004E_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG002D0004E_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG002D0004E_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG002D0004E_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG002D0004E_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG002D0004E_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG002D0004E_reloadEmployeeAppendName() {
	var appendOid = dojo.byId('BSC_PROG002D0004E_appendEmployeeOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG002D0004E_appendEmployeeOid').value = '';
		dojo.byId('BSC_PROG002D0004E_employeeAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG002D0004E_appendEmployeeOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG002D0004E_employeeAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG002D0004E_clearOrgaAppendId() {
	dojo.byId('BSC_PROG002D0004E_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG002D0004E_organizationAppendName').innerHTML = '';
}

function BSC_PROG002D0004E_clearEmplAppendId() {
	dojo.byId('BSC_PROG002D0004E_appendEmployeeOid').value = '';
	dojo.byId('BSC_PROG002D0004E_employeeAppendName').innerHTML = '';
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
		saveJsMethod="BSC_PROG002D0004E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG002D0004E_appendOrganizationOid" id="BSC_PROG002D0004E_appendOrganizationOid" value="${fields.appendOrgaOids}" />
	<input type="hidden" name="BSC_PROG002D0004E_appendEmployeeOid" id="BSC_PROG002D0004E_appendEmployeeOid" value="${fields.appendEmplOids}" />	
	
	<table border="0" width="850" height="800px" cellpadding="1" cellspacing="0" >
		<tr>
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_visionOid')"/></b>:
    			<br/>    		
    			<gs:select name="BSC_PROG002D0004E_visionOid" dataSource="visionMap" id="BSC_PROG002D0004E_visionOid" onChange="BSC_PROG002D0004E_triggerChangePerspectiveItems();" value="fields.visionOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_visionOid'">
    				Select vision.
				</div>     			
    		</td> 
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_id')"/></b> <s:property value="getText('BSC_PROG002D0004E_readOnly')"/>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004E_id" id="BSC_PROG002D0004E_id" value="kpi.id" width="200" maxlength="14" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_id'">
    				Id. ( read only ) 
				</div>     			
    		</td>	    		    		
    	</tr>		
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_perspectiveOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0004E_perspectiveOid" onChange="BSC_PROG002D0004E_triggerChangeObjectiveItems();" value="fields.perspectiveOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_perspectiveOid'">
    				Select perspectives.
				</div>        			
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_name')"/></b>:
    			<br/>
				<gs:textBox name="BSC_PROG002D0004E_name" id="BSC_PROG002D0004E_name" value="kpi.name" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_name'">
    				Input name.
				</div>   				    			
    		</td>	    		
    	</tr>		    	
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_objectiveOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_objectiveOid" dataSource="objectiveMap" id="BSC_PROG002D0004E_objectiveOid" value="fields.objectiveOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_objectiveOid'">
    				Select objectives.
				</div>       			
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_formulaOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_formulaOid" dataSource="formulaMap" id="BSC_PROG002D0004E_formulaOid" value="fields.formulaOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_formulaOid'">
    				Select formula.
				</div>       			
    		</td>	    		
    	</tr>		    		
		<tr>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_weight')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004E_weight" name="BSC_PROG002D0004E_weight" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${kpi.weight}" data-dojo-props="smallDelta:10, constraints:{min:0.00,max:999.00, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_weight'">
    				Input weight value, only allow numbers. range: 0.0 ~ 100.0 
				</div>      				        			
    		</td>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_management')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_management" dataSource="managementMap" id="BSC_PROG002D0004E_management" value="kpi.management"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_management'">
    				Select management method:<BR/> 
    				1. Bigger ( score bigger is better) <BR/> 
    				2. Smaller ( score smaller is better)  <BR/>
    				3. Quasi ( score quasi is better) <BR/>
    				the options will effect KPI Report result date-range item UP / DOWN status icon.
				</div>      			
    			&nbsp;&nbsp;
    			<b><s:property value="getText('BSC_PROG002D0004E_quasiRange')"/></b>:
    			<gs:select name="BSC_PROG002D0004E_quasiRange" dataSource="quasiRangeMap" id="BSC_PROG002D0004E_quasiRange" value="kpi.quasiRange" width="60"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_quasiRange'">
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
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_target')"/></b>:
    			<br/>
	   			<input id="BSC_PROG002D0004E_target" name="BSC_PROG002D0004E_target" type="text" data-dojo-type="dijit/form/NumberSpinner" 
	    				value="${kpi.target}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_target'">
    				Input target value, only allow numbers.
				</div>  	    				      			
    		</td>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_cal')"/></b>:
    			<br/>
				<gs:select name="BSC_PROG002D0004E_cal" dataSource="calculationMap" id="BSC_PROG002D0004E_cal" value="fields.aggrMethodOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_cal'">
    				Select calculation. ( aggregation method )
				</div>   				    			
    		</td>	    		
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_min')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004E_min" name="BSC_PROG002D0004E_min" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${kpi.min}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_min'">
    				Input min value, only allow numbers.
				</div>       				      			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_compareType')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_compareType" dataSource="compareTypeMap" id="BSC_PROG002D0004E_compareType" value="kpi.compareType"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_compareType'">
    				Select compare type. <BR/>
    				&nbsp;&nbsp;Target: KPI item result score compare with target value. <BR/>
    				&nbsp;&nbsp;Min: KPI item result score compare with min value. <BR/>
    				<BR/>
    				&nbsp;&nbsp;the options will effect KPI Report result date-range item UP / DOWN status icon.
				</div>       			
    		</td>
    		<td height="50px" width="50%"  align="left" >
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_unit')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004E_unit" id="BSC_PROG002D0004E_unit" value="kpi.unit" width="150" maxlength="20"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_unit'">
    				Input unit, the maximum allowed 20 characters. 
				</div>     			
    		</td>	    		    		
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0004E_dataType')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0004E_dataType" dataSource="dataTypeMap" id="BSC_PROG002D0004E_dataType" value="kpi.dataType"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_dataType'">
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
    			<b><s:property value="getText('BSC_PROG002D0004E_deptSelect')"/></b>:
    			&nbsp;&nbsp;
				<button name="BSC_PROG002D0004E_deptSelect" id="BSC_PROG002D0004E_deptSelect" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG002D0004E_appendOrganizationOid;BSC_PROG002D0004E_reloadOrganizationAppendName');
						}
					"></button>
				<button name="BSC_PROG002D0004E_deptClear" id="BSC_PROG002D0004E_deptClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG002D0004E_clearOrgaAppendId();
						}
					"></button>		
				<br/>
				<span id="BSC_PROG002D0004E_organizationAppendName">${fields.appendOrgaNames}</span>	    			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004E_emplSelect')"/></b>:
    			&nbsp;&nbsp;
				<button name="BSC_PROG002D0004E_emplSelect" id="BSC_PROG002D0004E_emplSelect" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG002D0004E_appendEmployeeOid;BSC_PROG002D0004E_reloadEmployeeAppendName');
						}
					"></button>
				<button name="BSC_PROG002D0004E_emplClear" id="BSC_PROG002D0004E_emplClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG002D0004E_clearEmplAppendId();
						}
					"></button>		
				<br/>	    			
    			<span id="BSC_PROG002D0004E_employeeAppendName">${fields.appendEmplNames}</span>
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004E_orgaMeasureSeparate')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004E_orgaMeasureSeparate" name="BSC_PROG002D0004E_orgaMeasureSeparate" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == kpi.orgaMeasureSeparate "> checked="checked" </s:if> />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_orgaMeasureSeparate'">
    				enable, can input KPI item measure-data for organization/department. 
				</div> 	    			    			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b><s:property value="getText('BSC_PROG002D0004E_userMeasureSeparate')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0004E_userMeasureSeparate" name="BSC_PROG002D0004E_userMeasureSeparate" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == kpi.userMeasureSeparate "> checked="checked" </s:if> />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_userMeasureSeparate'">
    				enable, can input KPI item measure-data for personal/Employee. 
				</div> 		    			
    		</td>
    	</tr>		    	
		<tr>
		    <td height="150px" width="100%" align="left" colspan="2">
		    	<b><s:property value="getText('BSC_PROG002D0004E_description')"/></b>:
		    	<br/>
		    	<textarea id="BSC_PROG002D0004E_description" name="BSC_PROG002D0004E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${kpi.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004E_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   		    	
		    </td>
		</tr>		    	    	
    	<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<gs:button name="BSC_PROG002D0004E_update" id="BSC_PROG002D0004E_update" onClick="BSC_PROG002D0004E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.kpiUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'				: '${kpi.oid}',
    						'fields.visionOid'			: dijit.byId('BSC_PROG002D0004E_visionOid').get('value'),
    						'fields.perspectiveOid'		: dijit.byId('BSC_PROG002D0004E_perspectiveOid').get('value'),
    						'fields.objectiveOid'		: dijit.byId('BSC_PROG002D0004E_objectiveOid').get('value'),
    						'fields.id'					: dijit.byId('BSC_PROG002D0004E_id').get('value'),
    						'fields.name'				: dijit.byId('BSC_PROG002D0004E_name').get('value'),   						
    						'fields.formulaOid'			: dijit.byId('BSC_PROG002D0004E_formulaOid').get('value'),
    						'fields.weight'				: dijit.byId('BSC_PROG002D0004E_weight').get('value'),
    						'fields.target'				: dijit.byId('BSC_PROG002D0004E_target').get('value'),    						 
    						'fields.min'				: dijit.byId('BSC_PROG002D0004E_min').get('value'),    						
    						'fields.compareType'		: dijit.byId('BSC_PROG002D0004E_compareType').get('value'),
    						'fields.unit'				: dijit.byId('BSC_PROG002D0004E_unit').get('value'),
    						'fields.management'			: dijit.byId('BSC_PROG002D0004E_management').get('value'),
    						'fields.quasiRange'			: dijit.byId('BSC_PROG002D0004E_quasiRange').get('value'),
    						'fields.cal'				: dijit.byId('BSC_PROG002D0004E_cal').get('value'),
    						'fields.dataType'			: dijit.byId('BSC_PROG002D0004E_dataType').get('value'),
    						'fields.orgaMeasureSeparate': ( dijit.byId('BSC_PROG002D0004E_orgaMeasureSeparate').checked ? 'true' : 'false' ),
    						'fields.userMeasureSeparate': ( dijit.byId('BSC_PROG002D0004E_userMeasureSeparate').checked ? 'true' : 'false' ),    						
    						'fields.description'		: dijit.byId('BSC_PROG002D0004E_description').get('value'),
    						'fields.orgaOids'			: dojo.byId('BSC_PROG002D0004E_appendOrganizationOid').value,
    						'fields.emplOids'			: dojo.byId('BSC_PROG002D0004E_appendEmployeeOid').value
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0004E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0004E_update')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG002D0004E_clear" id="BSC_PROG002D0004E_clear" onClick="BSC_PROG002D0004E_clear();" 
    				label="${action.getText('BSC_PROG002D0004E_clear')}" 
    				iconClass="dijitIconClear"></gs:button>       		    		
    		</td>
    	</tr>			   	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
