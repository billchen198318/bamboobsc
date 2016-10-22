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

var CORE_PROG001D0012A_fieldsId = new Object();
CORE_PROG001D0012A_fieldsId['uploadOid'] 	= 'CORE_PROG001D0012A_uploadOidNoticeLabelOnly';
CORE_PROG001D0012A_fieldsId['tplId'] 		= 'CORE_PROG001D0012A_tplId';
CORE_PROG001D0012A_fieldsId['name'] 		= 'CORE_PROG001D0012A_name';

function CORE_PROG001D0012A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0012A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0012A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0012A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0012A_fieldsId);
		return;
	}	
	CORE_PROG001D0012A_clear();
}

function CORE_PROG001D0012A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0012A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0012A_fieldsId);
	CORE_PROG001D0012A_uploadFail();
	dijit.byId('CORE_PROG001D0012A_tplId').set("value", "");
	dijit.byId('CORE_PROG001D0012A_name').set("value", "");
	dijit.byId('CORE_PROG001D0012A_description').set("value", "");	

}

function CORE_PROG001D0012A_uploadSuccess() {
	hideCommonUploadDialog();
	dojo.byId('CORE_PROG001D0012A_uploadShow').innerHTML = '<s:property value="getText('CORE_PROG001D0012A_uploadShowSuccess')"/>';
}

function CORE_PROG001D0012A_uploadFail() {
	dojo.byId('CORE_PROG001D0012A_uploadOid').value = "";
	dojo.byId('CORE_PROG001D0012A_uploadShow').innerHTML = '<font color="#6E6E6E"><s:property value="getText('CORE_PROG001D0012A_uploadShow')"/></font>';
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
		saveJsMethod="CORE_PROG001D0012A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0012A_tplId')}" id="CORE_PROG001D0012A_tplId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0012A_tplId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0012A_tplId" id="CORE_PROG001D0012A_tplId" value="" width="200" maxlength="50"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0012A_name')}" id="CORE_PROG001D0012A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0012A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0012A_name" id="CORE_PROG001D0012A_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>JSP</b>&nbsp;<gs:label text="${action.getText('CORE_PROG001D0012A_uploadOid')}" id="CORE_PROG001D0012A_uploadOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0012A_uploadOidNoticeLabelOnly"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input type="button" id="CORE_PROG001D0012A_uploadBtn" label="Upload" value="Upload" class="alt-info"
			   		data-dojo-props=" iconClass:'dijitIconFolderOpen', showLabel:false "
			   		data-dojo-type="dijit.form.Button" onClick="openCommonUploadDialog('CORE', 'tmp', 'N', 'CORE_PROG001D0012A_uploadOid', 'CORE_PROG001D0012A_uploadSuccess', 'CORE_PROG001D0012A_uploadFail');" />
    			<span id="CORE_PROG001D0012A_uploadShow"><font color='#6E6E6E'><s:property value="getText('CORE_PROG001D0012A_uploadShow')"/></font></span>
    			<input type="hidden" name="CORE_PROG001D0012A_uploadOid" id="CORE_PROG001D0012A_uploadOid" value="" />    			
    		</td>
    	</tr>	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0012A_description')}" id="CORE_PROG001D0012A_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG001D0012A_description" name="CORE_PROG001D0012A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
    		</td>    		
    	</tr>     	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0012A_save" id="CORE_PROG001D0012A_save" onClick="CORE_PROG001D0012A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemFormTemplateSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.uploadOid'		: dojo.byId('CORE_PROG001D0012A_uploadOid').value, 
    						'fields.tplId'			: dijit.byId('CORE_PROG001D0012A_tplId').get('value'),
    						'fields.name'			: dijit.byId('CORE_PROG001D0012A_name').get('value'),    						
    						'fields.description'	: dijit.byId('CORE_PROG001D0012A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0012A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0012A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0012A_clear" id="CORE_PROG001D0012A_clear" onClick="CORE_PROG001D0012A_clear();" 
    				label="${action.getText('CORE_PROG001D0012A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
