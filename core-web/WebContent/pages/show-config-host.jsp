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

You login url path is:&nbsp;<font color="#8A0808"><b><%=basePath%></b></font><br/>
But system config (01 - Application site) host settings value is:&nbsp;<font color="#8A0808"><b>${sysDefaultCoreHost}</b></font> , or has module is cross site.<br/>


<br/>

	<table border="0" width="700px" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<legend><b>System modules</b></legend>
		<tr>
			<td align="center" width="100px" bgcolor="#f1eee5"><b>ID</b></td>
			<td align="center" width="150px" bgcolor="#f1eee5"><b>Name</b></td>
			<td align="center" width="150px" bgcolor="#f1eee5"><b>Host</b></td>	
			<td align="center" width="150px" bgcolor="#f1eee5"><b>Context path</b></td>
			<td align="center" width="150px" bgcolor="#f1eee5"><b>Status</b></td>
		</tr>
		
		<s:if test="sysList != null">
		<s:iterator value="sysList" status="st">
		
		<tr>
			<td align="left" bgcolor="#ffffff"><s:property value="sysId"/></td>
			<td align="left" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="left" bgcolor="#ffffff"><s:property value="host"/></td>
			<td align="left" bgcolor="#ffffff"><s:property value="contextPath"/></td>
			<td align="center" bgcolor="#ffffff">
				<s:if test=" \"Y\" != crossSiteFlag && \"Y\" == testFlag "><div class="isa_success">OK</div></s:if>
				<s:if test=" \"Y\" == crossSiteFlag "><div class="isa_warning">Warning, Cross site</div></s:if>
				<s:if test=" \"Y\" != testFlag "><div class="isa_error">Error, connection fail</div></s:if>
			</td>			
		</tr>			
		
		</s:iterator>
		</s:if>		
		
		
	</table>	
	
	
<br/>

<div class="isa_info">
<b>Solution:</b>
<br/>
<br/>
<a href="${sysDefaultCoreHost}/pages/way.jsp" style="color:#00529B">Solution 1. click Try link redirect login page from ${sysDefaultCoreHost}/pages/way.jsp</a>
<br/>
<br/>
<a href="#" onclick="CORE_PROG001D0001Q_TabShow(); return false;" style="color:#00529B">Solution 2. click to config "01 - Application site" host settings, please view install.pdf page(5 - 8)</a>&nbsp;&nbsp;(&nbsp;need <b>admin</b> login&nbsp;/&nbsp;<a href="#" style="color:#00529B" onclick="window.open('https://github.com/billchen198318/bamboobsc/blob/master/core-doc/install.pdf'); return false;">view install.pdf</a>&nbsp;)<br/>
<br/>
<br/>
<b>Server IP address allocation by DHCP-server:</b>
<br/>
<br/>
<a href="#" style="color:#00529B" onclick="window.open('https://github.com/billchen198318/bamboobsc/issues/24'); return false;">DHCP network, and no want manual config "01 - application-site host. Please reference: https://github.com/billchen198318/bamboobsc/issues/24</a>

<s:if test=" \"Y\" != testFlag ">
<br/>
<br/>
<br/>
<b>Connection fail:</b>
<br/>
<br/>
<a href="#" style="color:#00529B" onclick="window.open('https://github.com/billchen198318/bamboobsc/blob/master/core-doc/install.pdf'); return false;">Maybe is config file ( applicationContext-dataSource.properties ) setting incorrect or database problems, causing the module to start failing, please view install.pdf</a>
</s:if>

</div>


<br/>


<font color="RED">*</font>&nbsp;If it is an internal network environment with allow cross site, and there is has enable <font color="#00529B"><b>chrome --disable-web-security</b></font> please ignore this warning message page.

</body>	