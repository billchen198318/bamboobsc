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

var CORE_PROG003D0001E_fieldsId = new Object();
CORE_PROG003D0001E_fieldsId['systemOid'] 		= 'CORE_PROG003D0001E_systemOid';
//CORE_PROG003D0001E_fieldsId['wsId'] 			= 'CORE_PROG003D0001E_wsId'; // readOnly
CORE_PROG003D0001E_fieldsId['beanId'] 			= 'CORE_PROG003D0001E_beanId';
CORE_PROG003D0001E_fieldsId['publishAddress'] 	= 'CORE_PROG003D0001E_publishAddress';

function CORE_PROG003D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0001E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0001E_fieldsId);
		return;
	}		
}

function CORE_PROG003D0001E_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0001E_fieldsId);
	dijit.byId('CORE_PROG003D0001E_systemOid').set("value", _gscore_please_select_id);
	//dijit.byId('CORE_PROG003D0001E_wsId').set("value", ""); // readOnly
	dijit.byId('CORE_PROG003D0001E_beanId').set("value", "");
	dijit.byId('CORE_PROG003D0001E_type').set("value", "SOAP");	
	dijit.byId('CORE_PROG003D0001E_publishAddress').set("value", "");
	dijit.byId('CORE_PROG003D0001E_description').set("value", "");	
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
		saveJsMethod="CORE_PROG003D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="525px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_systemOid')}" id="CORE_PROG003D0001E_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001E_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0001E_systemOid" dataSource="sysMap" id="CORE_PROG003D0001E_systemOid" value="selectValue"></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_wsId')}" id="CORE_PROG003D0001E_wsId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG003D0001E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001E_wsId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001E_wsId" id="CORE_PROG003D0001E_wsId" value="sysWsConfig.wsId" width="200" maxlength="10" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_beanId')}" id="CORE_PROG003D0001E_beanId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001E_beanId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001E_beanId" id="CORE_PROG003D0001E_beanId" value="sysWsConfig.beanId" width="400" maxlength="255"></gs:textBox>
    		</td>
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_type')}" id="CORE_PROG003D0001E_type"></gs:label>
    			<br/>
    			<gs:select name="CORE_PROG003D0001E_type" dataSource="{ \"SOAP\":\"SOAP\", \"REST\":\"REST\" }" id="CORE_PROG003D0001E_type" value="sysWsConfig.type"></gs:select>
    		</td>
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_publishAddress')}" id="CORE_PROG003D0001E_publishAddress"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0001E_publishAddress"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0001E_publishAddress" id="CORE_PROG003D0001E_publishAddress" value="sysWsConfig.publishAddress" width="400" maxlength="255"></gs:textBox>
    		</td>
    	</tr>      	    
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0001E_description')}" id="CORE_PROG003D0001E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0001E_description" name="CORE_PROG003D0001E_description" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px">${sysWsConfig.description}</textarea>	
    		</td>
    	</tr>     	 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0001E_update" id="CORE_PROG003D0001E_update" onClick="CORE_PROG003D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemWsConfigUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysWsConfig.oid}',
    						'fields.systemOid'		: dijit.byId('CORE_PROG003D0001E_systemOid').get('value'), 
    						'fields.wsId'			: dijit.byId('CORE_PROG003D0001E_wsId').get('value'),
    						'fields.beanId'			: dijit.byId('CORE_PROG003D0001E_beanId').get('value'),
    						'fields.type'			: dijit.byId('CORE_PROG003D0001E_type').get('value'),
    						'fields.publishAddress'	: dijit.byId('CORE_PROG003D0001E_publishAddress').get('value'),
    						'fields.description'	: dijit.byId('CORE_PROG003D0001E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0001E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0001E_clear" id="CORE_PROG003D0001E_clear" onClick="CORE_PROG003D0001E_clear();" 
    				label="${action.getText('CORE_PROG003D0001E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

