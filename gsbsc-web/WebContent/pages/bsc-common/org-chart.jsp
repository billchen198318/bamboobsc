<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
	
<style type="text/css">

</style>

<script type="text/javascript">

function BSC_PROGCOMM0001Q_query() {
	var actionUrl = 'bsc.commonCreateOrgChartForEmployeeAction.action';
	if ('organization' == '${fields.paramType}') {
		actionUrl = 'bsc.commonCreateOrgChartForOrganizationAction.action';
	}
	xhrSendParameter(
			'${basePath}/' + actionUrl, 
			{ 
				'fields.oid' 		: 	'${fields.paramOid}'
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROGCOMM0001Q_OrgChart(data);
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROGCOMM0001Q_OrgChart(data) {
	
	$('#BSC_PROGCOMM0001Q_orgChart').orgchart({
		'data' : data.rootData,
		'depth': 99,
		'nodeTitle': 'name',
		'nodeContent': 'title'
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

<body class="flat">

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>

<div id="BSC_PROGCOMM0001Q_orgChart"></div>
	
<script type="text/javascript">
${programId}_page_message();
BSC_PROGCOMM0001Q_query();
</script>	
</body>
</html>
