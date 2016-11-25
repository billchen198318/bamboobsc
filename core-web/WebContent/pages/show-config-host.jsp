<%@page import="com.netsteadfast.greenstep.util.MenuSupportUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
	
</head>

<body>

<div class="isa_error">
<font size="3"><b>Warning, Non-core modules will not work</b></font>
</div>

<br/>
<br/>

You login url path is:&nbsp;<font color="#8A0808"><b><%=basePath%></b></font><br/>
But system config (01 - Application site) host settings value is:&nbsp;<font color="#8A0808"><b>${sysDefaultCoreHost}</b></font><br/>

<br/>
<br/>

<b>Solution:</b>
<br/>
<br/>
<a href="${sysDefaultCoreHost}/pages/way.jsp" style="color:#3794E5">Solution 1. click Try link redirect login page from ${sysDefaultCoreHost}/pages/way.jsp</a>
<br/>
<br/>
<a href="#" onclick="CORE_PROG001D0001Q_TabShow(); return false;" style="color:#3794E5">Solution 2. click to config "01 - Application site" host settings, please view install.pdf page(5 - 8)</a><br/>
<br/>



</body>	