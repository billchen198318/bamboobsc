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
	
	
	<script src="<%=basePath%>/codemirror-5.0/mode/xml/xml.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/mode/javascript/javascript.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/mode/css/css.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/mode/htmlmixed/htmlmixed.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/addon/mode/multiplex.js"></script>
	<script src="<%=basePath%>/codemirror-5.0/mode/htmlembedded/htmlembedded.js"></script>
	
	
<style type="text/css">

body, html {
	height: 100%;
	width: 100%;
	margin: 0; 
	padding: 0;
	/* overflow: hidden; */
	font-size: 13px !important; 
	background: white; 
	color: #333;
}

.flat {
    border: 1px;
    background: #222;
    color: #FFF;
    padding: 2px 20px;
    font-size: 12px;
    font-family: Palatino;    
}

.lighter {
    background: #666;
}

<s:if test=" \"Y\" == cbMode ">
.CodeMirror {
  border: 1px solid #eee;
  height: auto;
}
</s:if>

</style>


</head>
<body bgcolor="#ffffff">

<!-- CORE_PROGCOMM0004Q -->

<s:if test=" \"Y\" == cbMode ">

<input type="button" name="okBtn" id="okBtn" value="OK" class="flat lighter" onClick="setCbFieldValue();">
<input type="button" name="clBtn" id="clBtn" value="Close" class="flat lighter" onClick="window.close();">
<br/>

</s:if>

<div style="border-top: 1px solid black; border-bottom: 1px solid black;">
	<textarea id="code">${codeContent}</textarea>
</div>	

<script type="text/javascript">
var langType = '${lang}';
var modeType = 'text/x-java';
if ( 'jsp' == langType) {
	modeType = 'application/x-jsp';
} 
var javaEditor = CodeMirror.fromTextArea(document.getElementById("code"), {
	lineNumbers: true,
	matchBrackets: true,
	mode: modeType
});

function getValue() {
	return javaEditor.getValue();
}

function clear() {
	return javaEditor.setValue("");
}

function setCbFieldValue() {
	if ( window.opener.document.getElementById("${valueFieldId}") == null ) {
		alert('ERROR: lost value field!');
		return;
	}
	window.opener.document.getElementById("${valueFieldId}").value = getValue();
	window.close();
	<s:if test=" null != okFn && \"\" != okFn && \" \" != okFn ">
	if ( eval("typeof " + window.opener.${okFn} + "=='function'") ) {
		window.opener.${okFn};
	} else {
		alert('ERROR: ok button event function is no found!');
	}
	</s:if>	
}

</script>

</body>
</html>
