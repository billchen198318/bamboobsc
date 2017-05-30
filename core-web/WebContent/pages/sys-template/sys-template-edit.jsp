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

var CORE_PROG001D0007E_fieldsId = new Object();
//CORE_PROG001D0007E_fieldsId['templateId'] = 'CORE_PROG001D0007E_templateId'; // readOnly
CORE_PROG001D0007E_fieldsId['title'] 		= 'CORE_PROG001D0007E_title';
CORE_PROG001D0007E_fieldsId['message'] 		= 'CORE_PROG001D0007E_message';

function CORE_PROG001D0007E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0007E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0007E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0007E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0007E_fieldsId);
		return;
	}		
}

function CORE_PROG001D0007E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0007E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0007E_fieldsId);
	//dijit.byId('CORE_PROG001D0007E_templateId').set("value", ""); // readOnly
	dijit.byId('CORE_PROG001D0007E_title').set("value", "");
	//dijit.byId('CORE_PROG001D0007E_message').set("value", "");
	document.getElementById('CORE_PROG001D0007E_iframe').contentWindow.clearEditorValue();
	dijit.byId('CORE_PROG001D0007E_description').set("value", "");
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
		saveJsMethod="CORE_PROG001D0007E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="525px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_templateId')}" id="CORE_PROG001D0007E_templateId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG001D0007E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0007E_templateId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0007E_templateId" id="CORE_PROG001D0007E_templateId" value="sysTemplate.templateId" width="200" maxlength="10" readonly="Y"></gs:textBox>
    		</td>
    	</tr>    		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_title')}" id="CORE_PROG001D0007E_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0007E_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0007E_title" id="CORE_PROG001D0007E_title" value="sysTemplate.title" width="400" maxlength="200"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="325px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_message')}" id="CORE_PROG001D0007E_message" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0007E_message"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<!-- 
    			<div data-dojo-type="dijit/Editor" id="CORE_PROG001D0007E_message" data-dojo-props="onChange:function(){ }">${sysTemplate.message}</div>    
    			-->
    			<iframe id="CORE_PROG001D0007E_iframe" name="CORE_PROG001D0007E_iframe" src="./pages/common-froala-editor.jsp?oid=${contentOid}" style="height: 350px;" width="100%" height="350" frameBorder="0"></iframe>
    						
    		</td>
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_description')}" id="CORE_PROG001D0007E_description"></gs:label>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0007E_description" id="CORE_PROG001D0007E_description" value="sysTemplate.description" width="400" maxlength="200"></gs:textBox>
    		</td>
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">    	
    			<gs:button name="CORE_PROG001D0007E_update" id="CORE_PROG001D0007E_update" onClick="CORE_PROG001D0007E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemTemplateUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysTemplate.oid}',
    						'fields.templateId'		: dijit.byId('CORE_PROG001D0007E_templateId').get('value'), 
    						'fields.title'			: dijit.byId('CORE_PROG001D0007E_title').get('value'),
    						'fields.message'		: document.getElementById('CORE_PROG001D0007E_iframe').contentWindow.getEditorValue(),
    						'fields.description'	: dijit.byId('CORE_PROG001D0007E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0007E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0007E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<!-- 'fields.message'		: dijit.byId('CORE_PROG001D0007E_message').get('value'), -->	
    			<gs:button name="CORE_PROG001D0007E_clear" id="CORE_PROG001D0007E_clear" onClick="CORE_PROG001D0007E_clear();" 
    				label="${action.getText('CORE_PROG001D0007E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

