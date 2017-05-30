<%@page import="com.netsteadfast.greenstep.util.UploadSupportUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

String uploadOid = request.getParameter("oid");
String content = "";
if (!StringUtils.isBlank(uploadOid)) {
	try {
		byte[] data = UploadSupportUtils.getDataBytes(uploadOid);
		if (null != data) {
			content = new String(data, Constants.BASE_ENCODING);
		}
	} catch (Exception e) {
		
	}
}

%>
<!DOCTYPE html>
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
	
	
	<link href="<%=mainSysBasePath%>/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	
	
    <script type="text/javascript" src="<%=mainSysBasePath%>/jquery/jquery-1.11.1.min.js"></script>
    
    
	 <!-- Include Editor style. -->
	<link href="<%=mainSysBasePath%>/froala_editor/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=mainSysBasePath%>/froala_editor/css/froala_style.min.css" rel="stylesheet" type="text/css" />
	<!-- Include Editor JS files. -->
	<script type="text/javascript" src="<%=mainSysBasePath%>/froala_editor/js/froala_editor.pkgd.min.js"></script>   
    
    
<script type="text/javascript">

$( document ).ready(function() {
	
	$('#commonEditor').froalaEditor();
	
});

function getEditorValue() {
	return $('#commonEditor').froalaEditor('html.get');
}

function clearEditorValue() {
	$('#commonEditor').froalaEditor('html.set', '');
}

</script>

	
</head>

<body>

<textarea rows="35" cols="5" id="commonEditor" name="commonEditor"><%=content%></textarea>

</body>

</html>