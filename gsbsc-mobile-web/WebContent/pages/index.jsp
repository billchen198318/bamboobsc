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
<link rel="stylesheet" href="https://rawgithub.com/arschmitz/jquery-mobile-datepicker-wrapper/v0.1.1/jquery.mobile.datepicker.css" />
<script src="${basePath}/jquery/jquery-1.11.1.min.js"></script>
<script src="${basePath}/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="https://rawgithub.com/jquery/jquery-ui/1.10.4/ui/jquery.ui.datepicker.js"></script>
<script id="mobile-datepicker" src="https://rawgithub.com/arschmitz/jquery-mobile-datepicker-wrapper/v0.1.1/jquery.mobile.datepicker.js"></script>
    
<style type="text/css">

</style>	
	
<script type="text/javascript">

function refresh_content() {
	alert( 123 );
}

function pageMessage() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
</script>
</head>  

<body leftmargin="0" topmargin="0">

<div data-role="footer">
    <div data-role="navbar" data-iconpos="left">
        <ul>
            <li><a href="${basePath}/logout.action" data-icon="delete">Logout</a></li>
            <li><button class="ui-btn ui-icon-refresh ui-btn-icon-left" onclick="refresh_content();">Refresh</button></li>
            <li><a href="#leftpanel1" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-mini" data-icon="gear">Settings</a></li>
        </ul>
    </div><!-- /navbar -->
</div><!-- /footer -->

<div data-role="panel" id="leftpanel1" data-position="left" data-display="reveal" data-theme="a">
	<table width="100%">
		<tr valign="top">
			<td width="100%">
			
				<div class="ui-field-contain">
				    <label for="frequency">Frequency:</label>
				    <select name="frequency" id="frequency" data-mini="true">
				    	<s:iterator value="frequencyMap" status="st" id="cols">
				    		<option value="<s:property value="#cols.key"/>" <s:if test=" \"6\" == #cols.key "> SELECTED </s:if> ><s:property value="#cols.value"/></option>	
				    	</s:iterator>			    	
				    </select>
				</div>		
	
			</td>
		</tr>
		<tr valign="top">
			
			<td width="100%">
				<label for="date1">Measures-data begin date:</label>
				<input data-role="date" type="text" name="date1" id="date1"/>
			</td>
			
		</tr>
		<tr valign="top">
			
			<td width="100%">
				<label for="date2">Measures-data end date:</label>
				<input data-role="date" type="text" name="date2" id="date2"/>
			</td>
			
		</tr>	
		
	</table>
</div>

<script type="text/javascript">pageMessage();</script>
</body>
</html>