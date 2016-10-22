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

var CORE_PROG001D0008E_fieldsId = new Object();
CORE_PROG001D0008E_fieldsId['uploadOid'] 	= 'CORE_PROG001D0008E_uploadOid_noticeMessageOnly';
CORE_PROG001D0008E_fieldsId['reportId'] 	= 'CORE_PROG001D0008E_reportId';

function CORE_PROG001D0008E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0008E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0008E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0008E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0008E_fieldsId);
		return;
	}		
	CORE_PROG001D0008E_TabRefresh();
}

function CORE_PROG001D0008E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0008E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0008E_fieldsId);
	dijit.byId('CORE_PROG001D0008E_isCompile').set("checked", false);		
	dijit.byId('CORE_PROG001D0008E_description').set("value", "");	
}

function CORE_PROG001D0008E_uploadSuccess() {
	hideCommonUploadDialog();
	dojo.byId('CORE_PROG001D0008E_uploadShow').innerHTML = '<s:property value="getText('CORE_PROG001D0008E_uploadShowSuccess')"/>';
}

function CORE_PROG001D0008E_uploadFail() {
	
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
		saveJsMethod="CORE_PROG001D0008E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0008E_upload_label')}" id="CORE_PROG001D0008E_upload_label"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0008E_uploadOid_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input type="button" id="CORE_PROG001D0008E_uploadBtn" label="Upload" value="Upload" class="alt-info"
			   		data-dojo-props=" iconClass:'dijitIconFolderOpen', showLabel:false "
			   		data-dojo-type="dijit.form.Button" onClick="openCommonUploadDialog('CORE', 'tmp', 'Y', 'CORE_PROG001D0008E_uploadOid', 'CORE_PROG001D0008E_uploadSuccess', 'CORE_PROG001D0008E_uploadFail');" />
    			<span id="CORE_PROG001D0008E_uploadShow"><font color='#6E6E6E'><s:property value="sysJreport.file"/></font></span>
    			<input type="hidden" name="CORE_PROG001D0008E_uploadOid" id="CORE_PROG001D0008E_uploadOid" value="" />
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0008E_reportId')}" id="CORE_PROG001D0008E_reportId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG001D0008E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0008E_reportId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0008E_reportId" id="CORE_PROG001D0008E_reportId" value="sysJreport.reportId" width="200" maxlength="50" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0008E_isCompile')}" id="CORE_PROG001D0008E_isCompile"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0008E_isCompile" name="CORE_PROG001D0008E_isCompile" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysJreport.isCompile "> checked </s:if> /> 
    		</td>
    	</tr>        	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0008E_description')}" id="CORE_PROG001D0008E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG001D0008E_description" name="CORE_PROG001D0008E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${sysJreport.description}</textarea>	
    		</td>
    	</tr>     	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0008E_update" id="CORE_PROG001D0008E_update" onClick="CORE_PROG001D0008E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemJreportUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysJreport.oid}',
    						'fields.uploadOid'		: dojo.byId('CORE_PROG001D0008E_uploadOid').value, 
    						'fields.reportId'		: dijit.byId('CORE_PROG001D0008E_reportId').get('value'),
    						'fields.isCompile'		: ( dijit.byId('CORE_PROG001D0008E_isCompile').checked ? 'true' : 'false' ),
    						'fields.description'	: dijit.byId('CORE_PROG001D0008E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0008E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0008E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0008E_clear" id="CORE_PROG001D0008E_clear" onClick="CORE_PROG001D0008E_clear();" 
    				label="${action.getText('CORE_PROG001D0008E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

