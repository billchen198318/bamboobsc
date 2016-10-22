<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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

<center>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10" rowspan="3" bgcolor="#FFA500">&nbsp;</td>
    <td width="48" bgcolor="#000000">&nbsp;</td>
    <td width="966" bgcolor="#000000"><font color="#FFFFFF" size="5"><B>Not support user agent browser:</B></font></td>
  </tr>
  <tr>
    <td width="48" bgcolor="#E5E4E2"><img src="./images/warning.png" width="48" height="48" border="0"/></td>
    <td valign="top" bgcolor="#E5E4E2"><s:property value="pageMessage"/></td>
  </tr>
  <tr>
    <td width="48" bgcolor="#E5E4E2">&nbsp;</td>
    <td bgcolor="#E5E4E2">
    	please use <a href="http://www.mozilla.org/">FireFox</a> or <a href="http://www.google.com/chrome/">Google chrome</a> browser.
     </td>
  </tr>
</table>

</center>
</body>

</html>
