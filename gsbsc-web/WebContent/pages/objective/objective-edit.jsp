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

var BSC_PROG002D0003E_fieldsId = new Object();
BSC_PROG002D0003E_fieldsId['visionOid'] 		= 'BSC_PROG002D0003E_visionOid';
BSC_PROG002D0003E_fieldsId['perspectiveOid'] 	= 'BSC_PROG002D0003E_perspectiveOid';
BSC_PROG002D0003E_fieldsId['name'] 				= 'BSC_PROG002D0003E_name';
BSC_PROG002D0003E_fieldsId['weight'] 			= 'BSC_PROG002D0003E_weight';
BSC_PROG002D0003E_fieldsId['target'] 			= 'BSC_PROG002D0003E_target';
BSC_PROG002D0003E_fieldsId['min'] 				= 'BSC_PROG002D0003E_min';
BSC_PROG002D0003E_fieldsId['description']		= 'BSC_PROG002D0003E_description';

function BSC_PROG002D0003E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0003E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0003E_fieldsId);		
		return;
	}		
}

function BSC_PROG002D0003E_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0003E_fieldsId);	
	dijit.byId('BSC_PROG002D0003E_visionOid').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG002D0003E_perspectiveOid').set("value", _gscore_please_select_id); // vision下拉會觸發perspective下拉更新項目
	dijit.byId('BSC_PROG002D0003E_name').set("value", "");		
	dijit.byId('BSC_PROG002D0003E_weight').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0003E_target').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0003E_min').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0003E_description').set("value", "");	
}

/**
 * Vision下拉觸發,改變Perspective下拉項目
 */
function BSC_PROG002D0003E_triggerChangePerspectiveItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0003E_visionOid',
			'BSC_PROG002D0003E_perspectiveOid',
			'${basePath}/bsc.commonGetPerspectiveItemsAction.action',
			{ 'fields.visionOid' : dijit.byId("BSC_PROG002D0003E_visionOid").get("value") }
	);	
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
		saveJsMethod="BSC_PROG002D0003E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="500px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b><s:property value="getText('BSC_PROG002D0003E_objId')"/></b> <s:property value="getText('BSC_PROG002D0003E_readOnly')"/>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0003E_objId" id="BSC_PROG002D0003E_objId" value="objective.objId" width="200" maxlength="14" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_objId'">
    				Id. ( read only )
				</div>  				    			
    		</td>    		    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_visionOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0003E_visionOid" dataSource="visionMap" id="BSC_PROG002D0003E_visionOid" value="fields.visionOid" onChange="BSC_PROG002D0003E_triggerChangePerspectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_visionOid'">
    				Select vision.
				</div>        			
    		</td>    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_perspectiveOid')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0003E_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0003E_perspectiveOid" value="fields.perspectiveOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_perspectiveOid'">
    				Select perspectives.
				</div>     			
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_name')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0003E_name" id="BSC_PROG002D0003E_name" value="objective.name" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_name'">
    				Input name.
				</div>      			
    		</td>    		
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_weight')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0003E_weight" name="BSC_PROG002D0003E_weight" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${objective.weight}" data-dojo-props="smallDelta:10, constraints:{min:0.00,max:999.00, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_weight'">
    				Input weight value, only allow numbers. range: 0.0 ~ 100.0 
				</div>     				     		    			
    		</td>    		
    	</tr>        	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_target')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0003E_target" name="BSC_PROG002D0003E_target" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${objective.target}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_target'">
    				Input target value, only allow numbers.
				</div>      				     		    			
    		</td>
    	</tr>   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG002D0003E_min')"/></b>:
    			<br/>
    			<input id="BSC_PROG002D0003E_min" name="BSC_PROG002D0003E_min" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${objective.min}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_min'">
    				Input min value, only allow numbers.
				</div>     				     		    			
    		</td>
    	</tr>   
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b><s:property value="getText('BSC_PROG002D0003E_description')"/></b>:
		    	<br/>
		    	<textarea id="BSC_PROG002D0003E_description" name="BSC_PROG002D0003E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${objective.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003E_description'">
    				Input description, the maximum allowed 500 characters.
				</div>		    	
		    </td>		    
		</tr>      	   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG002D0003E_update" id="BSC_PROG002D0003E_update" onClick="BSC_PROG002D0003E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.objectiveUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${objective.oid}',
    						'fields.visionOid'		: dijit.byId('BSC_PROG002D0003E_visionOid').get('value'),		 
    						'fields.perspectiveOid'	: dijit.byId('BSC_PROG002D0003E_perspectiveOid').get('value'), 
    						'fields.name'			: dijit.byId('BSC_PROG002D0003E_name').get('value'),
    						'fields.weight'			: dijit.byId('BSC_PROG002D0003E_weight').get('value'),
    						'fields.target'			: dijit.byId('BSC_PROG002D0003E_target').get('value'),    						 
    						'fields.min'			: dijit.byId('BSC_PROG002D0003E_min').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG002D0003E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0003E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0003E_update')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG002D0003E_clear" id="BSC_PROG002D0003E_clear" onClick="BSC_PROG002D0003E_clear();" 
    				label="${action.getText('BSC_PROG002D0003E_clear')}" 
    				iconClass="dijitIconClear"></gs:button>    		    		
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
