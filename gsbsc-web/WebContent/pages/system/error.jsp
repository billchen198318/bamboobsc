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
    <td width="10" rowspan="3" bgcolor="#AE0606">&nbsp;</td>
    <td width="48" bgcolor="#000000">&nbsp;</td>
    <td bgcolor="#000000"><font color="#FFFFFF" size="5"><B>Error message:</B></font></td>
  </tr>
  <tr>
    <td width="48" bgcolor="#E9D8D8"><img src="./images/error.png" width="48" height="48" border="0"/></td>
    <td valign="top" bgcolor="#E9D8D8">
    <font color="#666666" size="4"><s:property value="errorMessage"/></font>
    </td>
  </tr>
  <tr>
    <td width="48" bgcolor="#E9D8D8">&nbsp;</td>
    <td bgcolor="#E9D8D8">
    <s:debug></s:debug>    	    	
    	<strong>contact:</strong> <a href="mailto:<s:property value="errorContent"/>"><s:property value="errorContent"/></a> 
    </td>
  </tr>
</table>

</center>
</body>

</html>
