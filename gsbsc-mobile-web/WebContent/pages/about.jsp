<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>bambooBSC mobile version</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="common-include.jsp"></jsp:include>

</head>

<body>

<jsp:include page="topNavbar.jsp">
	<jsp:param value="about" name="active"/>
</jsp:include>

<div class="container">	
		
	<table border="0" width="100%" bgcolor="#ffffff">
		<tr valign="top">
			<td align="center" width="128px">
				<img alt="logo" src="./images/bamboobsc-logo.png" border="0" width="128px" height="128px"/>
			</td>
			<td align="center">
				<font size='2'>				
				bambooBSC 0.7.6 - opensource Balanced Scorecard (BSC) Business Intelligence<BR>
				<hr size="1" width="90%"/>
				Developer: Bill Chen / Chen Xin Nien<BR>
				Contact: <a href="mailto:chen.xin.nien@gmail.com">chen.xin.nien@gmail.com</a><BR>
				License: Apache License, Version 2.0
				</font>
				
				<br/>
				
			</td>
		</tr>
	</table>

</div>

</body>
</html>
