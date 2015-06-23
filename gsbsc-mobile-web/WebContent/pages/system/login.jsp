<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">
    
<title>bambooCORE</title>

<link rel="stylesheet" href="${basePath}/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script src="${basePath}/jquery/jquery-1.11.1.min.js"></script>
<script src="${basePath}/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>

<style type="text/css">

</style>	
	
<script type="text/javascript">

function pageMessage() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
</script>
</head>  

<body leftmargin="0" topmargin="0">
<form name="loginForm" id="loginForm" data-ajax="false" action="./login.action" method="post">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td bgcolor="#ffffff"><img src="./images/original.jpg" width="220" height="30" /></td>
    </tr>
    <tr>
      <td bgcolor="#ffffff">
	  <center>
	  <table id="loginTable" width="270" border="0" cellpadding="2" cellspacing="2">	 	   
        <tr>
        	<td width="100%" align="center">
        		<img src="./kaptcha.jpg?n=<%=System.currentTimeMillis()%>"/>
        		<BR/>
        		<input name="captcha" id="captcha" value="" type="text">
        	</td>      
        </tr>  
        <tr>  
        	<td width="100%" align="center">
        		<label for="username">Account:</label>
        		<input name="username" id="username" value="" type="text">
        	</td>
        </tr>
        <tr> 
        	<td width="100%" align="center">
        		<label for="password">Password:</label>
        		<input name="password" id="password" value="" autocomplete="off" type="password">		
        	</td>
        </tr>        
        <tr>         
        	<td width="100%" align="center">
        	    <input value="Login" id="submit" name="submit" type="submit">
        	</td>
        </tr>
        <tr> 
          <td width="100%" align="center" >
          <font color="#7E7E7E" size="2"><b></>This is bambooBSC-0.5 mobile version web.</b></font>
          </td>
         </tr>         
      </table>
     
	  </center>
	  </td>
    </tr>
    
  </table>
</form>
<script type="text/javascript">pageMessage();</script>
</body>
</html>