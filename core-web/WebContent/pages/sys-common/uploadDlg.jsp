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

function CORE_PROGCOMM0002Q_uploadDataEvent() {
	if (document.getElementById('upload').value == '' ) {
		alertDialog("Upload file", "Please select an file!", function(){}, 'N');
		return;
	}
	doUpload('CORE_PROGCOMM0002Q_form', '${uploadOidField}', ${callJsFunction}, ${callJsErrFunction});
}

function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
		CORE_PROGCOMM0002Q_DlgHide();
	}		
}

</script>

</head>
<body class="claro" bgcolor="#ffffff">

<form method="post" action="core.commonUploadFileAction.action" name="CORE_PROGCOMM0002Q_form" id="CORE_PROGCOMM0002Q_form" enctype="multipart/form-data" >
	<input type="hidden" name="type" id="CORE_PROGCOMM0002Q_type" value="${type}"/>
	<input type="hidden" name="system" id="CORE_PROGCOMM0002Q_system" value="${system}"/>
	<input type="hidden" name="isFile" id="CORE_PROGCOMM0002Q_isFile" value="${isFile}"/>
	<input type="file" name="upload" id="upload" />
	<br/>
	<input type="button" id="CORE_PROGCOMM0002Q_btnUpload" label="Upload file" value="Upload file" 
		data-dojo-props=" iconClass:'dijitIconSave', showLabel:false "
		data-dojo-type="dijit.form.Button" onClick="CORE_PROGCOMM0002Q_uploadDataEvent();" />
	<input type="button" id="CORE_PROGCOMM0002Q_btnClose" label="Upload close" value="Upload close" 
		data-dojo-props=" iconClass:'dijitIconDelete', showLabel:false "
		data-dojo-type="dijit.form.Button" onClick="CORE_PROGCOMM0002Q_DlgHide();" />		
</form>	

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
