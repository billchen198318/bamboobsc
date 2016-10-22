<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
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

input {
    margin: 0px;
    border: 1px solid #111;
    background: #F2F2F2;
    -webkit-border-radius: 5px;
    border-radius: 5px;     
}

select {
    margin: 0px;
    border: 1px solid #111;
    background: url(./images/br_down.png) 96% no-repeat #F2F2F2;
    width: 250px;
    /* padding: 5px; */
    font-size: 16px;
    border: 1px solid #ccc;
    /* height: 34px; */
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    font-family: Consolas,monaco,monospace;
}

.lighter {
    background: #1e88e5;
    color: #ffffff;
    border: 1px solid #166fbd;
    font-weight: bold;
}


/* -- http://isabelcastillo.com/error-info-messages-css -- */
.isa_info, .isa_success, .isa_warning, .isa_error {
	margin: 10px 0px;
	padding:12px;
	border-radius: 10px;
}
.isa_info {
    color: #00529B;
    background-color: #BDE5F8;
}
.isa_success {
    color: #4F8A10;
    background-color: #DFF2BF;
}
.isa_warning {
    color: #9F6000;
    background-color: #FEEFB3;
}
.isa_error {
    color: #D8000C;
    background-color: #FFBABA;
}
.isa_info i, .isa_success i, .isa_warning i, .isa_error i {
    margin:10px 22px;
    font-size:2em;
    vertical-align:middle;
}


-->
</style>	
	
<script type="text/javascript">

function submit_login() {
	document.getElementById("loginTable").style.visibility="hidden";
	document.getElementById("pwaitTable").style.visibility="visible";
	document.loginForm.submit();
}

function redirectLogin(sel) {
	window.location = '<%=basePath%>login.action?lang=' + sel.value;
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
      <td height="600" bgcolor="#ffffff" align="center">
	  
	  <table id="loginTable" width="640" border="0" cellpadding="2" cellspacing="2">	 
        <tr> 
          <td width="160" align="right"><gs:label text="${action.getText('LOGIN_language')}" id="LOGIN_language"></gs:label></td>
          <td width="160" align="left">
          	<select name="lang" id="lang" onchange="redirectLogin(this);">
          	<%
          	String defaultLang = (String)request.getAttribute("lang");
          	Map<String, Object> langs = LocaleLanguageUtils.getMap();
          	for (Map.Entry<String, Object> entry : langs.entrySet()) {
          	%>
          	<option value="<%=entry.getKey()%>" <% if ( entry.getKey().equals(defaultLang) ) { %> selected <% } %> ><%=String.valueOf( entry.getValue() )%></option>
          	<% 	
          	}
          	%>
          	</select>
          </td>
        </tr>  	   
        
        <!-- 2015-12-18 add https://github.com/billchen198318/bamboobsc/issues/5 -->
        <s:if test=" \"Y\" == loginCaptchaCodeEnable ">        
        <tr>
          <td width="160" align="right"><img src="./kaptcha.jpg?n=<%=System.currentTimeMillis()%>"/></td>
          <td width="160" align="left"><s:textfield name="captcha" id="captcha" maxlength="12" maxSize="12" theme="simple"/></td>      
        </tr>         
		</s:if>
		<!-- ##### -->
		
        <tr>  
          <td width="160" align="right"><gs:label text="${action.getText('LOGIN_username')}" id="LOGIN_username"></gs:label></td>
          <td width="160" align="left"><s:textfield name="username" id="username" maxlength="12" maxSize="12" theme="simple"/></td>
        </tr>
        <tr> 
          <td width="160" align="right"><gs:label text="${action.getText('LOGIN_password')}" id="LOGIN_password"></gs:label></td>
          <td width="160" align="left"><s:password name="password" id="password" maxlength="25" maxSize="12" theme="simple"/></td>
        </tr>        
        <tr>         
          <td width="320" align="center" colspan="2"><s:submit key="LOGIN_btnLogin" theme="simple" id="btnLogin" name="btnLogin" onclick="submit_login();" cssClass="lighter"/></td>          
        </tr>        
        
        <s:if test=" pageMessage != null && \"\" != pageMessage ">
        
        <tr> 
          <td width="320" align="center" colspan="2">
          <div class="isa_error"><s:property value="pageMessage"/></div>
          </td>
		</tr> 
        
        </s:if>
        
        <tr> 
          <td width="320" align="center" colspan="2">
          <div class="isa_success">Please use <b>Chrome</b> (recommend) or <b>Firefox</b> browser, can not support other browser.</div>
          </td>
		</tr> 
        <tr> 
          <td width="320" align="center" colspan="3" bgcolor="#ffffff">
          <a href="../gsbsc-mobile-web/index.action" style="text-decoration: none; font-weight: bold; color:#3794E5" >click to mobile version web.</a>
          </td>
		</tr>                 
      </table>
      
	  <table id="pwaitTable" width="640" border="0" cellpadding="0" cellspacing="0" style="visibility:hidden" >
	  	<tr>
	  		<td align="center"><img alt="please wait" src="./images/please-wait.gif"></img></td>
	  	</tr>
		<tr>
			<td align="center" bgcolor="#ffffff"><div class=isa_success><h3>Please wait!</h3></div></td>
		</tr>	  	
	  </table>
	        
	  
	  </td>
    </tr>
    <tr>
      <td>&nbsp;<font color="#333333" size="2">0.7.0 version</font></td>
    </tr>
  </table>
</s:form>
</body>
</html>