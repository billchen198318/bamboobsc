<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

%>
<!doctype html>
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
	
	<!-- *** note: not include core.css *** -->
	
	<script type="text/javascript" src="<%=mainSysBasePath%>core.configJsAction.action?ver=${jsVerBuild}"></script>
	
    <script type="text/javascript" src="<%=mainSysBasePath%>jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>html2canvas/html2canvas.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>html2canvas/html2canvas.svg.js"></script>	  
    	
<style type="text/css">

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

</style>

<script type="text/javascript">

function BSC_PROG003D0001Q_getOpenWindowView() {
    $.ajax({
    	type	: "POST",
    	dataType: 'json',
    	async	: !_gscore_dojo_ajax_sync,
    	timeout	: _gscore_dojo_ajax_timeout,
    	cache	: false,
    	url		: '<%=basePath%>bsc.kpiReportContentQueryAction.action',
    	data	: { 
			'fields.visionOid' 					: 	'${fields.opw_visionOid}',
			'fields.startYearDate'				:	'${fields.opw_startYearDate}',
			'fields.endYearDate'				:	'${fields.opw_endYearDate}',
			'fields.startDate'					:	'${fields.opw_startDate}',
			'fields.endDate'					:	'${fields.opw_endDate}',
			'fields.dataFor'					:	'${fields.opw_dataFor}',
			'fields.measureDataOrganizationOid'	:	'${fields.opw_measureDataOrganizationOid}',
			'fields.measureDataEmployeeOid'		:	'${fields.opw_measureDataEmployeeOid}',
			'fields.frequency'					:	'${fields.opw_frequency}',
			'fields.nextType'					:	'${fields.opw_nextType}',
			'fields.nextId'						:	'${fields.opw_nextId}',
			'fields.ngVer'						:	'N'
    	},
    	success	: function(data) {
    		if ('Y' != data.success) {
    			$("#BSC_PROG003D0001Q_content").html("<div class='isa_warning'><b>" + data.message + "</b></div>");
    			return;
    		}
    		$("#BSC_PROG003D0001Q_content").html( data.body );
			html2canvas( $('#BSC_PROG003D0001Q_content'), {
				onrendered: function(canvas) {
			        var a = document.createElement('a');
			        a.download = 'kpi-report.png';
			        a.href = canvas.toDataURL('image/png');
			        document.body.appendChild(a);
			        a.click();							
				},
				allowTaint 	: true,
				useCORS		: true,
				taintTest	: true
			});    		
    	},
     	error: function(data) {
     		alert(data);
     		$("#BSC_PROG003D0001Q_content").html( "<h1><font color='RED'>error!</font></h1>" );
     	}        	
	});    	
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="flat" bgcolor="#ffffff" >
		
	<div id="BSC_PROG003D0001Q_content">
		<table border="0">
			<tr>
				<td align="center" width="500px"><img alt="please wait" src="./images/please-wait.gif"></td>
			</tr>
			<tr>
				<td align="center" width="500px" bgcolor="#FAFAFA"><h2><font color="#7E7E7E">Please wait!</font></h2></td>
			</tr>
		</table>		
	</div>
	
<script type="text/javascript">
${programId}_page_message();
BSC_PROG003D0001Q_getOpenWindowView();
</script>
</body>
</html>
