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

var BSC_PROG002D0003A_fieldsId = new Object();
BSC_PROG002D0003A_fieldsId['visionOid'] 		= 'BSC_PROG002D0003A_visionOid';
BSC_PROG002D0003A_fieldsId['perspectiveOid'] 	= 'BSC_PROG002D0003A_perspectiveOid';
BSC_PROG002D0003A_fieldsId['name'] 				= 'BSC_PROG002D0003A_name';
BSC_PROG002D0003A_fieldsId['weight'] 			= 'BSC_PROG002D0003A_weight';
BSC_PROG002D0003A_fieldsId['target'] 			= 'BSC_PROG002D0003A_target';
BSC_PROG002D0003A_fieldsId['min'] 				= 'BSC_PROG002D0003A_min';
BSC_PROG002D0003A_fieldsId['description']		= 'BSC_PROG002D0003A_description';

function BSC_PROG002D0003A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG002D0003A_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0003A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0003A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0003A_fieldsId);
		return;
	}	
	BSC_PROG002D0003A_clear();
}

function BSC_PROG002D0003A_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0003A_fieldsId);	
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0003A_fieldsId);
	dijit.byId('BSC_PROG002D0003A_visionOid').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG002D0003A_perspectiveOid').set("value", _gscore_please_select_id); // vision下拉會觸發perspective下拉更新項目
	dijit.byId('BSC_PROG002D0003A_name').set("value", "");		
	//dijit.byId('BSC_PROG002D0003A_weight').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0003A_weight').set("value", "0.00");
	/*
	dijit.byId('BSC_PROG002D0003A_target').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0003A_min').set("value", "+000.00");
	*/
	dijit.byId('BSC_PROG002D0003A_target').set("value", "0.00");
	dijit.byId('BSC_PROG002D0003A_min').set("value", "0.00");	
	dijit.byId('BSC_PROG002D0003A_description').set("value", "");	
}

/**
 * Vision下拉觸發,改變Perspective下拉項目
 */
function BSC_PROG002D0003A_triggerChangePerspectiveItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0003A_visionOid',
			'BSC_PROG002D0003A_perspectiveOid',
			'${basePath}/bsc.commonGetPerspectiveItemsAction.action',
			{ 'fields.visionOid' : dijit.byId("BSC_PROG002D0003A_visionOid").get("value") }
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

<body class="flat">

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG002D0003A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="500px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_visionOid')}" id="BSC_PROG002D0003A_visionOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_visionOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG002D0003A_visionOid" dataSource="visionMap" id="BSC_PROG002D0003A_visionOid" onChange="BSC_PROG002D0003A_triggerChangePerspectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_visionOid'">
    				Select vision.
				</div>     			
    		</td>    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_perspectiveOid')}" id="BSC_PROG002D0003A_perspectiveOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_perspectiveOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG002D0003A_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0003A_perspectiveOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_perspectiveOid'">
    				Select perspectives.
				</div>     	    			
    		</td>    		
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_name')}" id="BSC_PROG002D0003A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0003A_name" id="BSC_PROG002D0003A_name" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_name'">
    				Input name.
				</div>       			
    		</td>    		
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_weight')}" id="BSC_PROG002D0003A_weight" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_weight"></gs:inputfieldNoticeMsgLabel>		
    			<br/>
    			<input id="BSC_PROG002D0003A_weight" name="BSC_PROG002D0003A_weight" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:0.00,max:999.00, pattern: '#.##', locale: 'en-us'}" /><!-- constraints:{min:0.00,max:999.00, pattern: '+000.00;-0.00', locale: 'en-us'} -->
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_weight'">
    				Input weight value, only allow numbers. range: 0.0 ~ 100.0 
				</div>      				     		    			
    		</td>    		
    	</tr>        	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_target')}" id="BSC_PROG002D0003A_target" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_target"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input id="BSC_PROG002D0003A_target" name="BSC_PROG002D0003A_target" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:-9999999.00,max:9999999.00, pattern: '#.##', locale: 'en-us'}" /><!-- constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' } -->
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_target'">
    				Input target value, only allow numbers.
				</div>       				     		    			    		
    		</td>    		
    	</tr>   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0003A_min')}" id="BSC_PROG002D0003A_min" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_min"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input id="BSC_PROG002D0003A_min" name="BSC_PROG002D0003A_min" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0.0" data-dojo-props="smallDelta:10, constraints:{min:-9999999.00,max:9999999.00, pattern: '#.##', locale: 'en-us'}" /><!-- constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' } -->
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_min'">
    				Input min value, only allow numbers.
				</div>      				     		    			
    		</td>    		
    	</tr>   
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG002D0003A_description')}" id="BSC_PROG002D0003A_description"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0003A_description"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG002D0003A_description" name="BSC_PROG002D0003A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0003A_description'">
    				Input description, the maximum allowed 500 characters.
				</div>			    	
		    </td>		    
		</tr>      	  	    	    	   	  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG002D0003A_save" id="BSC_PROG002D0003A_save" onClick="BSC_PROG002D0003A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.objectiveSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.visionOid'		: dijit.byId('BSC_PROG002D0003A_visionOid').get('value'),
    						'fields.perspectiveOid'	: dijit.byId('BSC_PROG002D0003A_perspectiveOid').get('value'),  
    						'fields.name'			: dijit.byId('BSC_PROG002D0003A_name').get('value'),
    						'fields.weight'			: dijit.byId('BSC_PROG002D0003A_weight').get('value'),
    						'fields.target'			: dijit.byId('BSC_PROG002D0003A_target').get('value'),    						 
    						'fields.min'			: dijit.byId('BSC_PROG002D0003A_min').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG002D0003A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0003A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0003A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG002D0003A_clear" id="BSC_PROG002D0003A_clear" onClick="BSC_PROG002D0003A_clear();" 
    				label="${action.getText('BSC_PROG002D0003A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>        		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
