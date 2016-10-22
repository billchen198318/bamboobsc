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

var CORE_PROG003D0001A_fieldsId = new Object();
CORE_PROG003D0001A_fieldsId['systemOid'] 		= 'CORE_PROG003D0001A_systemOid';
CORE_PROG003D0001A_fieldsId['wsId'] 			= 'CORE_PROG003D0001A_wsId';
CORE_PROG003D0001A_fieldsId['beanId'] 			= 'CORE_PROG003D0001A_beanId';
CORE_PROG003D0001A_fieldsId['publishAddress'] 	= 'CORE_PROG003D0001A_publishAddress';

function CORE_PROG003D0001A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG003D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0001A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0001A_fieldsId);
		return;
	}	
	CORE_PROG003D0001A_clear();
}

function CORE_PROG003D0001A_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0001A_fieldsId);
	dijit.byId('CORE_PROG003D0001A_systemOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0001A_wsId').set("value", "");
	dijit.byId('CORE_PROG003D0001A_beanId').set("value", "");
	dijit.byId('CORE_PROG003D0001A_type').set("value", "SOAP");	
	dijit.byId('CORE_PROG003D0001A_publishAddress').set("value", "");
	dijit.byId('CORE_PROG003D0001A_description').set("value", "");	
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
		saveJsMethod="CORE_PROG003D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="525px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_systemOid')}" id="CORE_PROG003D0001A_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001A_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0001A_systemOid" dataSource="sysMap" id="CORE_PROG003D0001A_systemOid"></gs:select>
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_wsId')}" id="CORE_PROG003D0001A_wsId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001A_wsId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001A_wsId" id="CORE_PROG003D0001A_wsId" value="" width="200" maxlength="10"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_beanId')}" id="CORE_PROG003D0001A_beanId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001A_beanId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001A_beanId" id="CORE_PROG003D0001A_beanId" value="" width="400" maxlength="255"></gs:textBox>
    		</td>    		
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_type')}" id="CORE_PROG003D0001A_type"></gs:label>
    			<br/>
    			<gs:select name="CORE_PROG003D0001A_type" dataSource="{ \"SOAP\":\"SOAP\", \"REST\":\"REST\" }" id="CORE_PROG003D0001A_type"></gs:select>
    		</td>
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_publishAddress')}" id="CORE_PROG003D0001A_publishAddress"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001A_publishAddress"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001A_publishAddress" id="CORE_PROG003D0001A_publishAddress" value="" width="400" maxlength="255"></gs:textBox>
    		</td>    		
    	</tr>      	    
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001A_description')}" id="CORE_PROG003D0001A_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0001A_description" name="CORE_PROG003D0001A_description" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px"></textarea>
    		</td>    		
    	</tr>     		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0001A_save" id="CORE_PROG003D0001A_save" onClick="CORE_PROG003D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemWsConfigSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.systemOid'		: dijit.byId('CORE_PROG003D0001A_systemOid').get('value'), 
    						'fields.wsId'			: dijit.byId('CORE_PROG003D0001A_wsId').get('value'),
    						'fields.beanId'			: dijit.byId('CORE_PROG003D0001A_beanId').get('value'),
    						'fields.type'			: dijit.byId('CORE_PROG003D0001A_type').get('value'),
    						'fields.publishAddress'	: dijit.byId('CORE_PROG003D0001A_publishAddress').get('value'),
    						'fields.description'	: dijit.byId('CORE_PROG003D0001A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0001A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0001A_clear" id="CORE_PROG003D0001A_clear" onClick="CORE_PROG003D0001A_clear();" 
    				label="${action.getText('CORE_PROG003D0001A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>      		
    		</td>    		
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
