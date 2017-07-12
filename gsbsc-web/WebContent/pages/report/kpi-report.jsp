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
	
<style type="text/css">

.btnExcelIcon {
  	background-image: url(./icons/excel.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnPdfIcon {
  	background-image: url(./icons/application-pdf.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnSignatureIcon {
  	background-image: url(./icons/text_signature.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnPieIcon {
  	background-image: url(./icons/chart-pie.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

#BSC_PROG003D0001Q_content { 
	width:100%; 
	height:100%;
	page-break-after:always 
}

</style>

<script type="text/javascript">

var BSC_PROG003D0001Q_fieldsId = new Object();
BSC_PROG003D0001Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0001Q_visionOid';
BSC_PROG003D0001Q_fieldsId['frequency'] 					= 'BSC_PROG003D0001Q_frequency';
BSC_PROG003D0001Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0001Q_startYearDate';
BSC_PROG003D0001Q_fieldsId['endYearDate'] 					= 'BSC_PROG003D0001Q_endYearDate';
BSC_PROG003D0001Q_fieldsId['startDate'] 					= 'BSC_PROG003D0001Q_startDate';
BSC_PROG003D0001Q_fieldsId['endDate'] 						= 'BSC_PROG003D0001Q_endDate';
BSC_PROG003D0001Q_fieldsId['measureDataOrganizationOid'] 	= 'BSC_PROG003D0001Q_measureDataOrganizationOid';
BSC_PROG003D0001Q_fieldsId['measureDataEmployeeOid'] 		= 'BSC_PROG003D0001Q_measureDataEmployeeOid';

function BSC_PROG003D0001Q_query() {
	BSC_PROG003D0001Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0001Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0001Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0001Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0001Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0001Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0001Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0001Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0001Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0001Q_frequency").get("value"),
				'fields.ngVer'						:	'Y'
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0001Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0001Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.byId("BSC_PROG003D0001Q_content").innerHTML = data.body;
				dijit.byId("BSC_PROG003D0001Q_startDate").set("displayedValue", data.startDate);
				dijit.byId("BSC_PROG003D0001Q_endDate").set("displayedValue", data.endDate);
				BSC_PROG003D0001Q_paintPieCharts(data);
				BSC_PROG003D0001Q_paintBarCharts(data);
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG003D0001Q_setDataForValue() {
	dijit.byId('BSC_PROG003D0001Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0001Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0001Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG003D0001Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0001Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0001Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0001Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG003D0001Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG003D0001Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0001Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0001Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG003D0001Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0001Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0001Q_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG003D0001Q_frequency").get("value");
	dijit.byId("BSC_PROG003D0001Q_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0001Q_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0001Q_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0001Q_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG003D0001Q_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0001Q_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG003D0001Q_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0001Q_endYearDate").set("readOnly", false);				
	}
}

function BSC_PROG003D0001Q_paintPieCharts(data) {
    var myColors = data.perspectivesPieChartBgColor;
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(myColors);
    };
    
	nv.addGraph(function() {
		var chart = nv.models.pieChart()
			.x(function(d) { return d.label })
			.y(function(d) { return d.value })
			.showLabels(true).color(d3.scale.myColors().range());
		
		chart.pie.labelsOutside(false).labelType("percent");
		
		d3.select("#BSC_PROG003D0001Q_pieChart svg")
			.datum(data.perspectivesPieChartValue)
			.transition().duration(350)
			.call(chart);
		
		return chart;
	});		
}

function BSC_PROG003D0001Q_paintBarCharts(data) {
    var myColors = data.perspectivesBarChartBgColor;
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(myColors);
    };
    
	nv.addGraph(function() {
		var chart = nv.models.discreteBarChart()
			.x(function(d) { return d.label })    //Specify the data accessors.
			.y(function(d) { return d.value })
			.staggerLabels(true)    //Too many bars and not enough room? Try staggering labels.
			.showValues(true)       //...instead, show the bar value right on top of each bar.
			.color( myColors );

		d3.select('#BSC_PROG003D0001Q_barChart svg')
			.datum(data.perspectivesBarChartValue)
			.call(chart);

		nv.utils.windowResize(chart.update);

		return chart;
	});	
}

function BSC_PROG003D0001Q_isReportQueryResult() {
	if ( dojo.byId("BSC_PROG003D0001Q_content").innerHTML.indexOf('<!-- BSC_PROG003D0001Q -->') > -1 ) {
		return true;
	}
	return false;
} 

function BSC_PROG003D0001Q_generateChartsToData() {
	dojo.byId('BSC_PROG003D0001Q_pieCanvasToData').value = 
		viewPage.getStrToHex( viewPage.getSVGImage2CanvasToDataUrlPNG('#BSC_PROG003D0001Q_pieChart svg') );
	dojo.byId('BSC_PROG003D0001Q_barCanvasToData').value = 
		viewPage.getStrToHex( viewPage.getSVGImage2CanvasToDataUrlPNG('#BSC_PROG003D0001Q_barChart svg') );
	//alert( dojo.byId('BSC_PROG003D0001Q_pieCanvasToData').value );
	//alert( dojo.byId('BSC_PROG003D0001Q_barCanvasToData').value );
}

function BSC_PROG003D0001Q_generateExport(docType) {
	if (!BSC_PROG003D0001Q_isReportQueryResult()) {
		showFieldsNoticeMessageLabel('BSC_PROG003D0001Q_visionOid'+_gscore_inputfieldNoticeMsgLabelIdName, '<s:property value="getText('MESSAGE.BSC_PROG003D0001Q_msg1')" escapeJavaScript="true"/>');
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0001Q_msg1')" escapeJavaScript="true"/>', function(){}, 'Y');
		return;
	}	
	var url = '${basePath}/bsc.kpiReportExcelQueryAction.action';
	if ( 'PDF' == docType ) {
		url = '${basePath}/bsc.kpiReportPdfQueryAction.action';
	}
	BSC_PROG003D0001Q_generateChartsToData();	
	setFieldsBackgroundDefault(BSC_PROG003D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0001Q_fieldsId);
	xhrSendParameter(
			url, 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0001Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0001Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0001Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0001Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0001Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0001Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0001Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0001Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0001Q_frequency").get("value"),
				'fields.pieCanvasToData'			:	dojo.byId("BSC_PROG003D0001Q_pieCanvasToData").value,
				'fields.barCanvasToData'			:	dojo.byId("BSC_PROG003D0001Q_barCanvasToData").value,
				'fields.uploadSignatureOid'			:	dojo.byId("BSC_PROG003D0001Q_uploadSignatureOid").value
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0001Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0001Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ( 'PDF' == docType ) {
					openCommonLoadUpload(
							'view', 
							data.uploadOid, 
							{ 
								"isDialog" 	: 	"Y",
								"title"		:	_getApplicationProgramNameById('${programId}'),
								"width"		:	1280,
								"height"	:	768
							} 
					);					
				} else {
					openCommonLoadUpload( 'download', data.uploadOid, { } );
				}
			}, 
			function(error) {
				alert(error);
			}
	);
	
}

function BSC_PROG003D0001Q_clearContent() {
	dojo.byId("BSC_PROG003D0001Q_content").innerHTML = "";
}

function BSC_PROG003D0001Q_uploadSignatureSuccess() {
	
}

function BSC_PROG003D0001Q_uploadSignatureFail() {
	dojo.byId('BSC_PROG003D0001Q_uploadSignatureOid').value = '';
}

function BSC_PROG003D0001Q_uploadSignatureClear() {
	var uploadOid = dojo.byId('BSC_PROG003D0001Q_uploadSignatureOid').value;
	if (null == uploadOid || 'null' == uploadOid || '' == uploadOid) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0001Q_msg2')" escapeJavaScript="true"/>', function(){}, 'Y');	
		return;
	}	
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"Clear signature? ", 
			function(success) {
				if (!success) {
					return;
				}	
				dojo.byId('BSC_PROG003D0001Q_uploadSignatureOid').value = '';
				alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0001Q_msg3')" escapeJavaScript="true"/>', function(){}, 'Y');				
			}, 
			(window.event ? window.event : null) 
	);
}

function BSC_PROG003D0001Q_getOpenWindowUrl(nextType, id) {
	var url = "<%=basePath%>/bsc.kpiReportOpenWindowAction.action";
	url += "?fields.opw_visionOid=" 	+ dijit.byId("BSC_PROG003D0001Q_visionOid").get("value");
	url += "&fields.opw_startYearDate=" + dijit.byId("BSC_PROG003D0001Q_startYearDate").get('displayedValue');
	url += "&fields.opw_endYearDate=" 	+ dijit.byId("BSC_PROG003D0001Q_endYearDate").get('displayedValue');
	url += "&fields.opw_startDate=" 	+ dijit.byId("BSC_PROG003D0001Q_startDate").get('displayedValue');
	url += "&fields.opw_endDate=" 		+ dijit.byId("BSC_PROG003D0001Q_endDate").get('displayedValue');
	url += "&fields.opw_dataFor=" 		+ dijit.byId("BSC_PROG003D0001Q_dataFor").get("value");
	url += "&fields.opw_measureDataOrganizationOid=" 	+ dijit.byId("BSC_PROG003D0001Q_measureDataOrganizationOid").get("value");
	url += "&fields.opw_measureDataEmployeeOid=" 		+ dijit.byId("BSC_PROG003D0001Q_measureDataEmployeeOid").get("value");
	url += "&fields.opw_frequency=" 	+ dijit.byId("BSC_PROG003D0001Q_frequency").get("value");
	url += "&fields.opw_nextType=" 		+ nextType;
	url += "&fields.opw_nextId=" 		+ id;
	return url;	
}

function BSC_PROG003D0001Q_getCoffeeChartOpenWindowUrl() {
	var url = "<%=basePath%>/bsc.kpiReportCoffeeChartOpenWindowAction.action";
	url += "?fields.opw_visionOid=" 	+ dijit.byId("BSC_PROG003D0001Q_visionOid").get("value");
	url += "&fields.opw_startYearDate=" + dijit.byId("BSC_PROG003D0001Q_startYearDate").get('displayedValue');
	url += "&fields.opw_endYearDate=" 	+ dijit.byId("BSC_PROG003D0001Q_endYearDate").get('displayedValue');
	url += "&fields.opw_startDate=" 	+ dijit.byId("BSC_PROG003D0001Q_startDate").get('displayedValue');
	url += "&fields.opw_endDate=" 		+ dijit.byId("BSC_PROG003D0001Q_endDate").get('displayedValue');
	url += "&fields.opw_dataFor=" 		+ dijit.byId("BSC_PROG003D0001Q_dataFor").get("value");
	url += "&fields.opw_measureDataOrganizationOid=" 	+ dijit.byId("BSC_PROG003D0001Q_measureDataOrganizationOid").get("value");
	url += "&fields.opw_measureDataEmployeeOid=" 		+ dijit.byId("BSC_PROG003D0001Q_measureDataEmployeeOid").get("value");
	url += "&fields.opw_frequency=" 	+ dijit.byId("BSC_PROG003D0001Q_frequency").get("value");
	return url;	
}

function BSC_PROG003D0001Q_openPerspective(id) {
	/*
	window.open(			
			BSC_PROG003D0001Q_getOpenWindowUrl('PER', id),		
			"KPI-Report",
            "resizable=yes,scrollbars=yes,status=yes,width=1280,height=800");
	*/
	BSC_PROG003D0001Q_openClickShowReportInDialog( BSC_PROG003D0001Q_getOpenWindowUrl('PER', id) );
}
function BSC_PROG003D0001Q_openObjective(id) {
	/*
	window.open(			
			BSC_PROG003D0001Q_getOpenWindowUrl('OBJ', id),		
			"KPI-Report",
            "resizable=yes,scrollbars=yes,status=yes,width=1280,height=800");
	*/
	BSC_PROG003D0001Q_openClickShowReportInDialog( BSC_PROG003D0001Q_getOpenWindowUrl('OBJ', id) );
}
function BSC_PROG003D0001Q_openKPI(id) {
	/*
	window.open(			
			BSC_PROG003D0001Q_getOpenWindowUrl('KPI', id),		
			"KPI-Report",
            "resizable=yes,scrollbars=yes,status=yes,width=1280,height=800");
	*/
	BSC_PROG003D0001Q_openClickShowReportInDialog( BSC_PROG003D0001Q_getOpenWindowUrl('KPI', id) );
}
function BSC_PROG003D0001Q_openNew() {
	/*
	window.open(			
			BSC_PROG003D0001Q_getOpenWindowUrl('', ''),		
			"KPI-Report",
            "resizable=yes,scrollbars=yes,status=yes,width=1280,height=800");	
	*/
	BSC_PROG003D0001Q_openClickShowReportInDialog( BSC_PROG003D0001Q_getOpenWindowUrl('', '') );
}

function BSC_PROG003D0001Q_openClickShowReportInDialog(url) {
	openCommonLoadUpload(
			'view', 
			'no_need', 
			{ 
				"isDialog" 	: 	"Y",
				"title"		:	_getApplicationProgramNameById('${programId}'),
				"width"		:	1440,
				"height"	:	768,
				"url"		:	url
			} 
	);	
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
	
	<input type="hidden" name="BSC_PROG003D0001Q_pieCanvasToData" id="BSC_PROG003D0001Q_pieCanvasToData" value="" />
	<input type="hidden" name="BSC_PROG003D0001Q_barCanvasToData" id="BSC_PROG003D0001Q_barCanvasToData" value="" />
	<input type="hidden" name="BSC_PROG003D0001Q_uploadSignatureOid" id="BSC_PROG003D0001Q_uploadSignatureOid" value="" />
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0001Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:145px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0001Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0001Q_query();
											}"><s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/></button>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnQuery'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/>
									</div>											
									
									<!-- ####################################################################################################### -->
									<!-- Current year -->		
									<button id="BSC_PROG003D0001Q_btnQueryForCurrentYear" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											showLabel:true,
											onClick:function(){  
												dijit.byId('BSC_PROG003D0001Q_frequency').set('value', '6');
												setReportQueryDateFieldForCurrent('6', 'BSC_PROG003D0001Q_startYearDate', 'BSC_PROG003D0001Q_endYearDate');
												BSC_PROG003D0001Q_query();
											}"><b>Y</b></button>	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnQueryForCurrentYear'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/> (Current year)
									</div>
																																	
									<!-- Current half-year -->		
									<button id="BSC_PROG003D0001Q_btnQueryForCurrentHalfYear" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											showLabel:true,
											onClick:function(){  
												dijit.byId('BSC_PROG003D0001Q_frequency').set('value', '5');
												setReportQueryDateFieldForCurrent('5', 'BSC_PROG003D0001Q_startYearDate', 'BSC_PROG003D0001Q_endYearDate');
												BSC_PROG003D0001Q_query();
											}"><b>H</b></button>		
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnQueryForCurrentHalfYear'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/> (Current half-year)
									</div>											
											
									<!-- Current quarterly -->		
									<button id="BSC_PROG003D0001Q_btnQueryForCurrentQuarter" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											showLabel:true,
											onClick:function(){  
												dijit.byId('BSC_PROG003D0001Q_frequency').set('value', '4');
												setReportQueryDateFieldForCurrent('4', 'BSC_PROG003D0001Q_startYearDate', 'BSC_PROG003D0001Q_endYearDate');
												BSC_PROG003D0001Q_query();
											}"><b>Q</b></button>			
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnQueryForCurrentQuarter'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/> (Current quarterly of the year)
									</div>
																				
									<!-- Current month -->		
									<button id="BSC_PROG003D0001Q_btnQueryForCurrentMonth" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											showLabel:true,
											onClick:function(){  
												dijit.byId('BSC_PROG003D0001Q_frequency').set('value', '3');
												setReportQueryDateFieldForCurrent('3', 'BSC_PROG003D0001Q_startDate', 'BSC_PROG003D0001Q_endDate');
												BSC_PROG003D0001Q_query();
											}"><b>M</b></button>		
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnQueryForCurrentMonth'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/> (Current month)
									</div>
																																																	
									<!-- ####################################################################################################### -->
																
									<button id="BSC_PROG003D0001Q_btnExportPng" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconPrint',
											showLabel:false,
											onClick:function(){
											
												BSC_PROG003D0001Q_openNew();
												
												/*
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
												*/											  
											}"><s:property value="getText('BSC_PROG003D0001Q_btnExportPng')"/></button>	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnExportPng'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnExportPng')"/>
									</div>																						
					
									<button id="BSC_PROG003D0001Q_btnPdf" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPdfIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0001Q_generateExport('PDF');
											}"><s:property value="getText('BSC_PROG003D0001Q_btnPdf')"/></button>	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnPdf'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnPdf')"/>
									</div>											
									            
									<button id="BSC_PROG003D0001Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0001Q_generateExport('EXCEL');																			  
											}"><s:property value="getText('BSC_PROG003D0001Q_btnXls')"/></button>	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnXls'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnXls')"/>
									</div>											
									
									<button id="BSC_PROG003D0001Q_btnCoffeeChart" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPieIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0001Q_openClickShowReportInDialog( BSC_PROG003D0001Q_getCoffeeChartOpenWindowUrl() );																			  
											}">Coffee chart</button>										
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnCoffeeChart'">
					    				Coffee chart
									</div>
																		
									&nbsp;&nbsp;&nbsp;&nbsp;		
									<button id="BSC_PROG003D0001Q_btnSignature" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnSignatureIcon',
											showLabel:false,
											onClick:function(){
												openCommonSignatureDialog('BSC', 'BSC_PROG003D0001Q_uploadSignatureOid', 'BSC_PROG003D0001Q_uploadSignatureSuccess', 'BSC_PROG003D0001Q_uploadSignatureFail');													  
											}"><s:property value="getText('BSC_PROG003D0001Q_btnSignature')"/></button>												
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnSignature'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnSignature')"/>
									</div>
																			
									<button id="BSC_PROG003D0001Q_btnSignatureClear" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconClear',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0001Q_uploadSignatureClear();
											}"><s:property value="getText('BSC_PROG003D0001Q_btnSignatureClear')"/></button>		
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_btnSignatureClear'">
					    				<s:property value="getText('BSC_PROG003D0001Q_btnSignatureClear')"/>
									</div>
																				
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_visionOid"></gs:inputfieldNoticeMsgLabel>		
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_endYearDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_endDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0001Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>											
							</tr>	

							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0001Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0001Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0001Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0001Q_frequency')"/>
									<gs:select name="BSC_PROG003D0001Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0001Q_frequency" value="6" onChange="BSC_PROG003D0001Q_setFrequencyValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_frequency'">
					    				Select frequency.
									</div> 									
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0001Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0001Q_startYearDate" name="BSC_PROG003D0001Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	<s:property value="getText('BSC_PROG003D0001Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0001Q_endYearDate" name="BSC_PROG003D0001Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0001Q_startDate')"/>
									<input id="BSC_PROG003D0001Q_startDate" type="text" name="BSC_PROG003D0001Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0001Q_endDate')"/>
									<input id="BSC_PROG003D0001Q_endDate" type="text" name="BSC_PROG003D0001Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_endDate'">
					    				Select end date.
									</div>							    			
							    </td>	
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									<s:property value="getText('BSC_PROG003D0001Q_dataFor')"/>
									<gs:select name="BSC_PROG003D0001Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0001Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0001Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0001Q_dataFor" onChange="BSC_PROG003D0001Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0001Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0001Q_measureDataOrganizationOid" onChange="BSC_PROG003D0001Q_setMeasureDataOrgaValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0001Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0001Q_measureDataEmployeeOid" onChange="BSC_PROG003D0001Q_setMeasureDataEmplValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_measureDataEmployeeOid'">
					    				Select measure data personal/Employee.
									</div>									
								</td>
							</tr>																						
																											
						</table>
			    			
		    		</div>		    		
		    	</div>	
		    	
			    			
			</td>
		</tr>
	</table>
		
	
	<table border="0" width="100%" >
		<tr>
			<td width="50%" align="center">
				<div id='BSC_PROG003D0001Q_pieChart' >
			  		<svg style='height:350px;width:500px'> </svg>
				</div>				
			</td>
			<td width="50%" align="center">
				<div id='BSC_PROG003D0001Q_barChart' >
			  		<svg style='height:350px;width:500px'> </svg>
				</div>				
			</td>			
		</tr>
	</table>
	
	<div id="BSC_PROG003D0001Q_content"></div>
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
