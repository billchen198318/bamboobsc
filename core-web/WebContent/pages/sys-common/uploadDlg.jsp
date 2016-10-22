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

input[type=file] { 
	width: 500px; 
	height: 60px; 
	border: 2px dotted #FFAD1C; 
	background: #FFEFD0;
	border-radius: 4px;
}
    
</style>

<script type="text/javascript">

function CORE_PROGCOMM0002Q_uploadDataEvent() {
	if (document.getElementById('upload').value == '' ) {
		alertDialog("Upload file", "Please select an file!", function(){}, 'N');
		return;
	}
	if (document.getElementById('upload').files[0].size > ${uploadMultipartMaxSize} ) { /* set size ${uploadMultipartMaxSize} equals with struts.xml item "struts.multipart.maxSize"  */
		alertDialog("Upload file", "File exceeded upload size!", function(){}, 'N');
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
<body class="flat" bgcolor="#ffffff">

<table border="0" width="100%">
	<tr valign="top">
		<td  align="center">

<form method="post" action="core.commonUploadFileAction.action" name="CORE_PROGCOMM0002Q_form" id="CORE_PROGCOMM0002Q_form" enctype="multipart/form-data" >
	<input type="hidden" name="type" id="CORE_PROGCOMM0002Q_type" value="${type}"/>
	<input type="hidden" name="system" id="CORE_PROGCOMM0002Q_system" value="${system}"/>
	<input type="hidden" name="isFile" id="CORE_PROGCOMM0002Q_isFile" value="${isFile}"/>
	<div>		
		<label id="upload-label" for="upload"><img border="0" alt="help-icon" src="./icons/help-about.png"/>&nbsp;<font size='2'><b>Drag file to color Box.</b>&nbsp;( maximum size ${uploadMultipartMaxSizeLabel} )</font></label>
		<input type="file" name="upload" id="upload" draggable="true" width="500px" height="60px" title="Drag file there." onchange="CORE_PROGCOMM0002Q_uploadDataEvent();"/>				
	</div>	
	<!-- 
	<input type="button" id="CORE_PROGCOMM0002Q_btnUpload" label="Upload file" value="Upload file" 
		data-dojo-props=" iconClass:'dijitIconSave', showLabel:false "
		data-dojo-type="dijit.form.Button" onClick="CORE_PROGCOMM0002Q_uploadDataEvent();" 
		class="alt-primary"/>
	<input type="button" id="CORE_PROGCOMM0002Q_btnClose" label="Upload close" value="Upload close" 
		data-dojo-props=" iconClass:'dijitEditorIconCancel', showLabel:false "
		data-dojo-type="dijit.form.Button" onClick="CORE_PROGCOMM0002Q_DlgHide();" 
		class="alt-primary"/>		
	-->
</form>	

		</td>
	</tr>
</table>

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
