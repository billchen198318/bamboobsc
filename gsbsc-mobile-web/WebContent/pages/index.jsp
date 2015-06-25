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

var uploadOid='';

function refresh_content() {
	var date1 = $("#date1").val();
	var date2 = $("#date1").val();
	var frequency = $("#frequency").val();
	
	$("#card_content").html('');
	
	$.mobile.loading( 'show', {
		text: '<s:property value="getText('MESSAGE.INDEX_pleaseWait')" escapeJavaScript="true"/>',
		textVisible: true,
		theme: 'z',
		html: ""
	});	
	
	$.ajax({
		url: "${basePath}/bsc.mobile.doVisionCardAction.action",
		data: { 
				'fields.date1'				:	date1,
				'fields.date2'				:	date2,
				'fields.frequency'			:	frequency
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 300000,
		cache: false,
		success: function(data) {
			$.mobile.loading( 'hide' );			
			
			if ( data.success != 'Y' ) {				
				alert( data.message );
				return;
			}
			
			$("#card_content").html('<center>' + data.content + '</center>');
			
		},
		error: function(e) {
			$.mobile.loading( 'hide' );			
			alert( e.statusText );			
		}
		
	});	
	
}

function query_perspective(uploadOid) {
	
	$.mobile.loading( 'show', {
		text: '<s:property value="getText('MESSAGE.INDEX_pleaseWait')" escapeJavaScript="true"/>',
		textVisible: true,
		theme: 'z',
		html: ""
	});	
	
	$.ajax({
		url: "${basePath}/bsc.mobile.doPerspectiveCardAction.action",
		data: { 
				'fields.uploadOid'			:	uploadOid
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 300000,
		cache: false,
		success: function(data) {
			$.mobile.loading( 'hide' );			
			
			if ( data.success != 'Y' ) {				
				alert( data.message );
				return;
			}
			
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, data.rootVision.title, 'refresh_content();');
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {
			$.mobile.loading( 'hide' );			
			alert( e.statusText );			
		}
		
	});	
	
}

function query_objectiveByPerspective(uploadOid, perspectiveOid) {
	
	$.mobile.loading( 'show', {
		text: '<s:property value="getText('MESSAGE.INDEX_pleaseWait')" escapeJavaScript="true"/>',
		textVisible: true,
		theme: 'z',
		html: ""
	});	
	
	$.ajax({
		url: "${basePath}/bsc.mobile.doObjectiveCardAction.action",
		data: { 
				'fields.uploadOid'			:	uploadOid,
				'fields.perspectiveOid'		:	perspectiveOid
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 300000,
		cache: false,
		success: function(data) {
			$.mobile.loading( 'hide' );			
			
			if ( data.success != 'Y' ) {				
				alert( data.message );
				return;
			}
			
			var fnname = "query_perspective('" + uploadOid + "');";
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, (data.perspectiveTitle + ' : ' + data.rootPerspective.name), fnname);
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {
			$.mobile.loading( 'hide' );			
			alert( e.statusText );			
		}
		
	});	
	
}

function query_kpiByObjective(uploadOid, objectiveOid) {
	
	$.mobile.loading( 'show', {
		text: '<s:property value="getText('MESSAGE.INDEX_pleaseWait')" escapeJavaScript="true"/>',
		textVisible: true,
		theme: 'z',
		html: ""
	});	
	
	$.ajax({
		url: "${basePath}/bsc.mobile.doKpiCardAction.action",
		data: { 
				'fields.uploadOid'			:	uploadOid,
				'fields.objectiveOid'		:	objectiveOid
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 300000,
		cache: false,
		success: function(data) {
			$.mobile.loading( 'hide' );			
			
			if ( data.success != 'Y' ) {				
				alert( data.message );
				return;
			}
			
			var fnname = "query_objectiveByPerspective('" + uploadOid + "', '" + data.rootPerspective.oid + "');";
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, (data.objectiveTitle + ' : ' + data.rootObjective.name), fnname);			
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {
			$.mobile.loading( 'hide' );			
			alert( e.statusText );			
		}
		
	});	
	
}

function getTitleContent(backgroundColor, fontColor, title, fnname) {	
	var titleContent = '';
	titleContent += '<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="' + backgroundColor + '">';
	titleContent += '<tr valign="top">';
	titleContent += '<td width="100%" align="center" bgcolor="' + backgroundColor + '" onclick="' + fnname + '">';
	titleContent += '<font color="' + fontColor + '" size="4"><b>' + title + '</b></font>';
	titleContent += '</td>';
	titleContent += '</tr>';
	titleContent += '</table>';
	titleContent += '<BR/><BR/>';	
	return titleContent;
}

function logout_page() {
	if ( confirm("<s:property value="getText('MESSAGE.INDEX_logoutPage')" escapeJavaScript="true"/>") ) {
		window.location = "${basePath}/logout.action";
	}
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
            <li><a href="#" data-icon="delete" onclick="logout_page();"><s:property value="getText('INDEX_Logout')"/></a></li>
            <li><a href="#" data-icon="refresh" onclick="refresh_content();"><s:property value="getText('INDEX_refreshQuery')"/></a></li>
            <li><a href="#leftpanel1" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-mini" data-icon="gear"><s:property value="getText('INDEX_options')"/></a></li>
        </ul>
    </div><!-- /navbar -->
</div><!-- /footer -->

<div data-role="content">
	<div id="card_content"></div>
</div>

<div data-role="panel" id="leftpanel1" data-position="left" data-display="reveal" data-theme="a">
	<table width="100%">	
	    <tr valign="top">
	    	<td bgcolor="#ffffff"><img src="./images/original.jpg" width="220" height="30" /></td>
	    </tr>		
		<tr valign="top">
			<td width="100%">
			
				<label for="frequency"><s:property value="getText('INDEX_frequency')"/>:</label>				    
				<select name="frequency" id="frequency" data-mini="true">
					<s:iterator value="frequencyMap" status="st" id="cols">
						<option value="<s:property value="#cols.key"/>" <s:if test=" \"6\" == #cols.key "> SELECTED </s:if> ><s:property value="#cols.value"/></option>	
					</s:iterator>			    	
				</select>
	
			</td>
		</tr>
		<tr valign="top">
			
			<td width="100%">
				<label for="date1"><s:property value="getText('INDEX_date1')"/>:</label>
				<input data-role="date" type="text" name="date1" id="date1" value="${measureDataDate1}"/>
			</td>
			
		</tr>
		<tr valign="top">
			
			<td width="100%">
				<label for="date2"><s:property value="getText('INDEX_date2')"/>:</label>
				<input data-role="date" type="text" name="date2" id="date2" value="${measureDataDate1}"/>
			</td>
			
		</tr>	
	
	</table>
</div>

<script type="text/javascript">pageMessage();</script>
</body>
</html>