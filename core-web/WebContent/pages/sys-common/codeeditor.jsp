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
	
	<script src="<%=basePath%>/codemirror-5.0/lib/codemirror.js" type="text/javascript"></script>
	<script src="<%=basePath%>/codemirror-5.0/addon/edit/matchbrackets.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/addon/hint/show-hint.js"></script>	
	<script src="<%=basePath%>/codemirror-5.0/clike.js"></script>	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/codemirror-5.0/doc/docs.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/codemirror-5.0/lib/codemirror.css" />		
	<link rel="stylesheet" href="<%=basePath%>/codemirror-5.0/addon/hint/show-hint.css">	

</head>
<body bgcolor="#ffffff">

<!-- CORE_PROGCOMM0004Q -->

<div style="border-top: 1px solid black; border-bottom: 1px solid black;">
	<textarea id="code">${codeContent}</textarea>
</div>	

<script type="text/javascript">
var javaEditor = CodeMirror.fromTextArea(document.getElementById("code"), {
	lineNumbers: true,
	matchBrackets: true,
	mode: "text/x-java"
});

function getValue() {
	return javaEditor.getValue();
}

function clear() {
	return javaEditor.setValue("");
}


</script>

</body>
</html>
