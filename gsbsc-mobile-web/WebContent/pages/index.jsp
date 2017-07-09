<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>bambooBSC mobile version</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="common-include.jsp"></jsp:include>

<script type="text/javascript">


var uploadOid='';

function refresh_content() {
	var date1 = $("#date1").val();
	var date2 = $("#date2").val();
	var frequency = $("#frequency").val();
	if (date1 != null) {
		date1 = date1.replace("\-", "/").replace("\-", "/");
	}
	if (date2 != null) {
		date2 = date2.replace("\-", "/").replace("\-", "/");
	}
	
	$("#card_content").html('');
	
	$('#myPleaseWait').modal('show');
	$.ajax({
		url: "${basePath}/bsc.mobile.doVisionCardAction.action",
		data: { 
				'fields.date1'				:	date1,
				'fields.date2'				:	date2,
				'fields.frequency'			:	frequency,
				'fields.ver'					:	'newVer'
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 300000,
		cache: false,
		success: function(data) {
			$('#myPleaseWait').modal('hide');
			if ( data.success != 'Y' ) {
				bootbox.alert( data.message );
				return;
			}
			
			$("#card_content").html('<center>' + data.content + '</center>');
			
		},
		error: function(e) {		
			$('#myPleaseWait').modal('hide');
			alert( e.statusText );			
		}
		
	});	
	
}

function query_perspective(uploadOid) {
	$('#myPleaseWait').modal('show');
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
			$('#myPleaseWait').modal('hide');
			if ( data.success != 'Y' ) {				
				bootbox.alert( data.message );
				return;
			}
			
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, data.rootVision.title, 'refresh_content();');
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {
			$('#myPleaseWait').modal('hide');
			alert( e.statusText );			
		}
		
	});	
	
}

function query_objectiveByPerspective(uploadOid, perspectiveOid) {
	$('#myPleaseWait').modal('show');
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
			$('#myPleaseWait').modal('hide');
			if ( data.success != 'Y' ) {				
				bootbox.alert( data.message );
				return;
			}
			
			var fnname = "query_perspective('" + uploadOid + "');";
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, (data.perspectiveTitle + ' : ' + data.rootPerspective.name), fnname);
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {	
			$('#myPleaseWait').modal('hide');
			alert( e.statusText );			
		}
		
	});	
	
}

function query_kpiByObjective(uploadOid, objectiveOid) {
	$('#myPleaseWait').modal('show');
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
			$('#myPleaseWait').modal('hide');
			if ( data.success != 'Y' ) {				
				bootbox.alert( data.message );
				return;
			}
			
			var fnname = "query_objectiveByPerspective('" + uploadOid + "', '" + data.rootPerspective.oid + "');";
			var titleContent = getTitleContent(data.backgroundColor, data.fontColor, (data.objectiveTitle + ' : ' + data.rootObjective.name), fnname);			
			$("#card_content").html('<center>' + titleContent + data.content + '</center>');
			
		},
		error: function(e) {	
			$('#myPleaseWait').modal('hide');
			alert( e.statusText );			
		}
		
	});	
	
}

function getTitleContent(backgroundColor, fontColor, title, fnname) {	
	var titleContent = '';
	titleContent += '<table width="100%" cellspacing="2" cellpadding="0" bgcolor="' + backgroundColor + '" style="border:1px ' + backgroundColor + ' solid; border-radius: 5px;" >';
	titleContent += '<tr valign="top">';
	titleContent += '<td width="100%" align="center" bgcolor="' + backgroundColor + '" onclick="' + fnname + '">';
	titleContent += '<img src="./images/go-previous.png" border="0" alt="previous" onclick="' + onclick + '"/>';
	titleContent += '<font color="' + fontColor + '" size="4"><b>' + title + '</b></font>';
	titleContent += '</td>';
	titleContent += '</tr>';
	titleContent += '</table>';
	titleContent += '<BR/>';	
	return titleContent;
}

</script>

</head>

<body>

<jsp:include page="topNavbar.jsp">
	<jsp:param value="home" name="active"/>
</jsp:include>

  <div class="form-group">
    <label for="frequency">${action.getText('INDEX_frequency')}</label>
    <select class="form-control" id="frequency">
		<s:iterator value="frequencyMap" status="st" var="cols">
			<option value="<s:property value="#cols.key"/>" <s:if test=" \"6\" == #cols.key "> SELECTED </s:if> ><s:property value="#cols.value"/></option>	
		</s:iterator>	
    </select>
  </div>
<div class="form-group row">
  <label for="date1">&nbsp;&nbsp;&nbsp;&nbsp;${action.getText('INDEX_date1')}</label>
  <div class="col-xs-10">
    <input  class="form-control" type="date" value="${measureDataDate2}" id="date1" />
  </div>
</div>
<div class="form-group row">
  <label for="date2">&nbsp;&nbsp;&nbsp;&nbsp;${action.getText('INDEX_date2')}</label>
  <div class="col-xs-10">
    <input class="form-control" type="date" value="${measureDataDate2}" id="date2" />
  </div>
</div>

<button type="button" class="btn btn-primary" onclick="refresh_content();">${action.getText("INDEX_refreshQuery")}</button>

<br/>
<br/>

<div data-role="content">
	<div id="card_content">

<div class="alert alert-info" role="alert">
  <h4 class="alert-heading">Measure-data begin/end date description!</h4>
  <p>The Frequency is <b>Year / Half of year / Quarter</b> , begin/end date capture <b>yyyy</b> for query.</p>
  <p>The Frequency is <b>Month / Week</b> , begin/end date capture <b>yyyy-MM</b> for query.</p>
  <p>The Frequency is <b>Day</b> , begin/end date capture <b>yyyy-MM-dd</b> for query.</p>    
</div>	
	
	</div>
</div>

<%
String realPath = getServletContext().getRealPath("/");
if (realPath.indexOf("org.eclipse.wst.server.core")==-1) {
%>
<!-- google analytics for bambooBSC -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-74984756-1', 'auto');
  ga('send', 'pageview');

</script>
<%
} else {
%>
<!-- no need google analytics, when run on wst plugin mode -->
<%
}
%>
</body>
</html>
