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
		
	<table border="0" width="100%" bgcolor="#ffffff">
		<tr valign="top">
			<td colspan="2">
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr valign="top">
					<td align="left" valign="middle" bgcolor="#F5F5F5">
						<img src="./images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font size='2' color='#394045' style="vertical-align:middle;margin-top:0.25em">&nbsp;About me</font></b>
						<br/>
						<hr color="#3794E5" size="2">
					</td>
				</tr>
			</table>				
			</td>
		</tr>
		<tr valign="top">
			<td align="center" width="128px">
				<img alt="logo" src="./images/bamboobsc-logo.png" border="0" width="128px" height="128px"/>
			</td>
			<td align="center">
				<font size='2'>				
				bambooBSC 0.7.3 - opensource Balanced Scorecard (BSC) Business Intelligence<BR>
				<hr size="1" width="90%"/>
				Developer: Bill Chen / Chen Xin Nien<BR>
				Contact: <a href="mailto:chen.xin.nien@gmail.com">chen.xin.nien@gmail.com</a><BR>
				License: Apache License, Version 2.0
				</font>
				
				<br/>
				
			</td>
		</tr>
	</table>

</body>
</html>
