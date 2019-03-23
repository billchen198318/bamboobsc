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
	
	<script src="<%=basePath%>codemirror/lib/codemirror.js" type="text/javascript"></script>
	<script src="<%=basePath%>codemirror/addon/edit/matchbrackets.js"></script>
	<script src="<%=basePath%>codemirror/addon/hint/show-hint.js"></script>	
	<script src="<%=basePath%>codemirror/mode/clike/clike.js"></script>	
	<!-- 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>codemirror/doc/docs.css" />
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>codemirror/lib/codemirror.css" />		
	<link rel="stylesheet" href="<%=basePath%>codemirror/addon/hint/show-hint.css">	
	
	
	<script src="<%=basePath%>codemirror/mode/xml/xml.js"></script>
	<script src="<%=basePath%>codemirror/mode/javascript/javascript.js"></script>
	<script src="<%=basePath%>codemirror/mode/css/css.js"></script>
	<script src="<%=basePath%>codemirror/mode/htmlmixed/htmlmixed.js"></script>
	<script src="<%=basePath%>codemirror/addon/mode/multiplex.js"></script>
	<script src="<%=basePath%>codemirror/mode/htmlembedded/htmlembedded.js"></script>
	
	
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
    -webkit-border-radius: 5px;
    border-radius: 5px;     
}

.lighter {
    background: #1e88e5;
    color: #ffffff;
    border: 1px solid #166fbd;
    /* font-weight: bold; */
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

<table border="0" width="100%" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);" >
	<tr>
		<td align="left">
			&nbsp;&nbsp;
			<input type="button" name="okBtn" id="okBtn" value="${action.getText('CORE_PROGCOMM0004Q_okBtn')}" class="flat lighter" onClick="setCbFieldValue();">
			&nbsp;&nbsp;
			<input type="button" name="clBtn" id="clBtn" value="${action.getText('CORE_PROGCOMM0004Q_clBtn')}" class="flat lighter" onClick="parent.CORE_PROGCOMM0004Q_DlgHide();">
		</td>
	</tr>
</table>

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
	
	if ( parent.document.getElementById("${valueFieldId}") == null ) {	
		alert('ERROR: lost value field!');
		return;
	}
	
	parent.document.getElementById("${valueFieldId}").value = getValue();
	
	<s:if test=" null != okFn && \"\" != okFn && \" \" != okFn ">
	parent.${okFn};
	</s:if>	
	
	
}

</script>

</body>
</html>
