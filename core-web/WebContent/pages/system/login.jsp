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

<style type="text/css">
<!--
.style1 {
	color: #000000;
	font-weight: bold;
}
.style2 {
	color: #F7982A;
	font-weight: bold;
	font-style: italic;
}
.style3 {color: #084F01}
.style4 {color: #000000}
-->
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
<s:form name="loginForm" id="loginForm" action="login" method="post">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td bgcolor="#ffffff"><img src="./images/original.jpg" width="220" height="30" /></td>
    </tr>
    <tr>
      <td height="180" bgcolor="#ffffff">
	  <center>
	  <table width="640" border="0" cellpadding="2" cellspacing="2">
        <tr>
          <td width="90" align="right"><img src="./kaptcha.jpg?n=<%=System.currentTimeMillis()%>"/></td>
          <td width="135" align="left"><s:textfield name="captcha" id="captcha" maxLength="12" maxSize="12" theme="simple"/></td>      
        </tr>  
        <tr>  
          <td width="90" align="right"><span class="style1">ACCOUNT</span></td>
          <td width="135" align="left"><s:textfield name="username" id="username" maxLength="12" maxSize="12" theme="simple"/></td>
        </tr>
        <tr> 
          <td width="90" align="right"><span class="style1">PASSWORD</span></td>
          <td width="135" align="left"><s:password name="password" id="password" maxLength="25" maxSize="12" theme="simple"/></td>
        </tr> 
        <tr> 
          <td width="90" align="center" colspan="2"><s:submit value="login" theme="simple"/></td>
        </tr>
      </table>
	  </center>
	  </td>
    </tr>
    <tr>
      <td><span class="style2"><span class="style3">bamboo</span>CORE <span class="style4">0.5</span> </span></td>
    </tr>
  </table>
</s:form>
<script type="text/javascript">pageMessage();</script>
</body>
</html>