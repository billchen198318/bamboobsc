<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td align="left" valign="middle" bgcolor="#F5F5F5">
				<img src="<%=basePath%>images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/>&nbsp;&nbsp;<font size='2' color='#394045' style="vertical-align:middle;margin-top:0.25em"><b><s:property value="programName"/></b></font>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size='1' color="#F5F5F5">ID:&nbsp;<s:property value="programId"/></font>
				<br/>
				<hr color="#3794E5" size="2">
			</td>
		</tr>
	</table>	
	