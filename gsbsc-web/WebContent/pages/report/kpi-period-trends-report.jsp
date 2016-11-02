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

#BSC_PROG003D0007Q_content { 
	width:100%; 
	height:100%;
	page-break-after:always 
}

</style>

<script type="text/javascript">

var BSC_PROG003D0007Q_fieldsId = new Object();

/* ==================== Current period ==================== */
BSC_PROG003D0007Q_fieldsId['visionOid1'] 					= 'BSC_PROG003D0007Q_visionOid_1';
BSC_PROG003D0007Q_fieldsId['frequency1'] 					= 'BSC_PROG003D0007Q_frequency_1';
BSC_PROG003D0007Q_fieldsId['startYearDate1'] 				= 'BSC_PROG003D0007Q_startYearDate_1';
BSC_PROG003D0007Q_fieldsId['endYearDate1'] 					= 'BSC_PROG003D0007Q_endYearDate_1';
BSC_PROG003D0007Q_fieldsId['startDate1'] 					= 'BSC_PROG003D0007Q_startDate_1';
BSC_PROG003D0007Q_fieldsId['endDate1'] 						= 'BSC_PROG003D0007Q_endDate_1';
BSC_PROG003D0007Q_fieldsId['measureDataOrganizationOid1'] 	= 'BSC_PROG003D0007Q_measureDataOrganizationOid_1';
BSC_PROG003D0007Q_fieldsId['measureDataEmployeeOid1'] 		= 'BSC_PROG003D0007Q_measureDataEmployeeOid_1';

/* ==================== Previous period ==================== */
BSC_PROG003D0007Q_fieldsId['visionOid2'] 					= 'BSC_PROG003D0007Q_visionOid_2';
BSC_PROG003D0007Q_fieldsId['frequency2'] 					= 'BSC_PROG003D0007Q_frequency_2';
BSC_PROG003D0007Q_fieldsId['startYearDate2'] 				= 'BSC_PROG003D0007Q_startYearDate_2';
BSC_PROG003D0007Q_fieldsId['endYearDate2'] 					= 'BSC_PROG003D0007Q_endYearDate_2';
BSC_PROG003D0007Q_fieldsId['startDate2'] 					= 'BSC_PROG003D0007Q_startDate_2';
BSC_PROG003D0007Q_fieldsId['endDate2'] 						= 'BSC_PROG003D0007Q_endDate_2';
BSC_PROG003D0007Q_fieldsId['measureDataOrganizationOid2'] 	= 'BSC_PROG003D0007Q_measureDataOrganizationOid_2';
BSC_PROG003D0007Q_fieldsId['measureDataEmployeeOid2'] 		= 'BSC_PROG003D0007Q_measureDataEmployeeOid_2';


function BSC_PROG003D0007Q_query(format) {
	var url = '${basePath}/bsc.kpiPeriodTrendsQueryAction.action';
	if ( 'EXCEL' == format ) {
		url = '${basePath}/bsc.kpiPeriodTrendsExcelQueryAction.action';
	}
	if ( 'HTML' == format ) {
		BSC_PROG003D0007Q_clearContent();
	}
	setFieldsBackgroundDefault(BSC_PROG003D0007Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0007Q_fieldsId);
	xhrSendParameter(
			url, 
			{ 
				/* ==================== Current period ==================== */
				'fields.visionOid1' 					: 	dijit.byId("BSC_PROG003D0007Q_visionOid_1").get("value"),
				'fields.startYearDate1'					:	dijit.byId("BSC_PROG003D0007Q_startYearDate_1").get('displayedValue'),
				'fields.endYearDate1'					:	dijit.byId("BSC_PROG003D0007Q_endYearDate_1").get('displayedValue'),
				'fields.startDate1'						:	dijit.byId("BSC_PROG003D0007Q_startDate_1").get('displayedValue'),
				'fields.endDate1'						:	dijit.byId("BSC_PROG003D0007Q_endDate_1").get('displayedValue'),
				'fields.dataFor1'						:	dijit.byId("BSC_PROG003D0007Q_dataFor_1").get("value"),
				'fields.measureDataOrganizationOid1'	:	dijit.byId("BSC_PROG003D0007Q_measureDataOrganizationOid_1").get("value"),
				'fields.measureDataEmployeeOid1'		:	dijit.byId("BSC_PROG003D0007Q_measureDataEmployeeOid_1").get("value"),
				'fields.frequency1'						:	dijit.byId("BSC_PROG003D0007Q_frequency_1").get("value"),
				
				/* ==================== Previous period ==================== */
				'fields.visionOid2' 					: 	dijit.byId("BSC_PROG003D0007Q_visionOid_2").get("value"),
				'fields.startYearDate2'					:	dijit.byId("BSC_PROG003D0007Q_startYearDate_2").get('displayedValue'),
				'fields.endYearDate2'					:	dijit.byId("BSC_PROG003D0007Q_endYearDate_2").get('displayedValue'),
				'fields.startDate2'						:	dijit.byId("BSC_PROG003D0007Q_startDate_2").get('displayedValue'),
				'fields.endDate2'						:	dijit.byId("BSC_PROG003D0007Q_endDate_2").get('displayedValue'),
				'fields.dataFor2'						:	dijit.byId("BSC_PROG003D0007Q_dataFor_2").get("value"),
				'fields.measureDataOrganizationOid2'	:	dijit.byId("BSC_PROG003D0007Q_measureDataOrganizationOid_2").get("value"),
				'fields.measureDataEmployeeOid2'		:	dijit.byId("BSC_PROG003D0007Q_measureDataEmployeeOid_2").get("value"),
				'fields.frequency2'						:	dijit.byId("BSC_PROG003D0007Q_frequency_2").get("value")				
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0007Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0007Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ( 'EXCEL' == format ) {
					openCommonLoadUpload( 'download', data.uploadOid, { } );
				} else {
					dojo.byId("BSC_PROG003D0007Q_content").innerHTML = data.body;
					BSC_PROG003D0007Q_lineChart( data.periodData );
					if ( '' != data.message ) {
						alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					}
				}
				dijit.byId("BSC_PROG003D0007Q_startDate_1").set("displayedValue", data.startDate1);
				dijit.byId("BSC_PROG003D0007Q_endDate_1").set("displayedValue", data.endDate1);
				dijit.byId("BSC_PROG003D0007Q_startDate_2").set("displayedValue", data.startDate2);
				dijit.byId("BSC_PROG003D0007Q_endDate_2").set("displayedValue", data.endDate2);						
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG003D0007Q_setDataForValue(p) {
	dijit.byId('BSC_PROG003D0007Q_measureDataOrganizationOid_'+p).set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0007Q_measureDataEmployeeOid_'+p).set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0007Q_measureDataOrganizationOid_'+p).set('readOnly', true);
	dijit.byId('BSC_PROG003D0007Q_measureDataEmployeeOid_'+p).set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0007Q_dataFor_"+p).get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0007Q_measureDataEmployeeOid_'+p).set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0007Q_measureDataOrganizationOid_'+p).set('readOnly', false);
	}	
}

function BSC_PROG003D0007Q_setMeasureDataOrgaValue(p) {
	var dataFor = dijit.byId("BSC_PROG003D0007Q_dataFor_"+p).get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0007Q_measureDataOrganizationOid_'+p).set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0007Q_setMeasureDataEmplValue(p) {
	var dataFor = dijit.byId("BSC_PROG003D0007Q_dataFor_"+p).get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0007Q_measureDataOrganizationOid_'+p).set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0007Q_setFrequencyValue(p) {
	var frequency = dijit.byId("BSC_PROG003D0007Q_frequency_"+p).get("value");
	dijit.byId("BSC_PROG003D0007Q_startYearDate_"+p).set("readOnly", true);
	dijit.byId("BSC_PROG003D0007Q_endYearDate_"+p).set("readOnly", true);
	dijit.byId("BSC_PROG003D0007Q_startDate_"+p).set("readOnly", true);
	dijit.byId("BSC_PROG003D0007Q_endDate_"+p).set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG003D0007Q_startDate_"+p).set("readOnly", false);
		dijit.byId("BSC_PROG003D0007Q_endDate_"+p).set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG003D0007Q_startYearDate_"+p).set("readOnly", false);
		dijit.byId("BSC_PROG003D0007Q_endYearDate_"+p).set("readOnly", false);				
	}
}

function BSC_PROG003D0007Q_visionOid_1_change() {
	var vis1 = dijit.byId("BSC_PROG003D0007Q_visionOid_1").get("value");
	dijit.byId("BSC_PROG003D0007Q_visionOid_2").set("value", vis1);
}

function BSC_PROG003D0007Q_clearContent() {
	dojo.byId("BSC_PROG003D0007Q_content").innerHTML = "";
	dojo.byId("BSC_PROG003D0007Q_chart").innerHTML = "";
}

function BSC_PROG003D0007Q_lineChart(data) {
	dojo.byId("BSC_PROG003D0007Q_chart").innerHTML = "";
	if ( null == data || data.length < 1 ) {
		return;
	}
	var n = 0;
	var content = '';
	content += '<table width="1100px" border="0" cellpadding="1" cellspacing="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;" >';
	content += '<tr>';
	content += '<td bgcolor="#F6F6F6" align="center" ><font size="4"><b>KPIs period trends</b></font></td>';
	content += '</tr>';	
	for (var i=0; i<data.length; i++) {
		var periodData = data[i];
		if ( 'Y' != periodData.canChart ) {
			continue;
		}		
		var chartId = "BSC_PROG003D0007Q_chart_" + periodData.current.id;
		content += '<tr>';
		content += '<td bgcolor="#ffffff" align="center" ><div id="' + chartId + '" style="min-width: 1024px; height: 450px; max-width: 2048px; margin: 0 auto" ></div></td>';
		content += '</tr>';					
	}
	content += '</table>';
	dojo.byId("BSC_PROG003D0007Q_chart").innerHTML = content;
	
	for (var i=0; i<data.length; i++) {
		var periodData = data[i];
		if ( 'Y' != periodData.canChart ) {
			continue;
		}						
		var chartId = "BSC_PROG003D0007Q_chart_" + periodData.current.id;
		
	    $('#'+chartId).highcharts({
	        title: {
	            text: periodData.current.name,
	            x: -20 //center
	        },
	        subtitle: {
	            text: 'Period trends - (C) is current , (P) is previous',
	            x: -20
	        },
	        xAxis: {
	            categories: periodData.dateRangeLabels
	        },
	        yAxis: {
	            title: {
	                text: 'Score / Change(%)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: ' value'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [
	                 { name : "Change(%)", data : periodData.dateRangeScores },
	                 { name : "Current", data : periodData.currentDateRangeScores },
	                 { name : "Previous", data : periodData.previousDateRangeScores }
			]
	    });	
	    n++;
	}
	
	if ( n == 0 ) {
		dojo.byId("BSC_PROG003D0007Q_chart").innerHTML = "";
	}
	
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
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="50%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0001Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:310px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0007Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0007Q_query('HTML');
											}"><s:property value="getText('BSC_PROG003D0001Q_btnQuery')"/></button>		
									            
									<button id="BSC_PROG003D0007Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0007Q_query('EXCEL');																  
											}"><s:property value="getText('BSC_PROG003D0001Q_btnXls')"/></button>		
											
								</td>											
							</tr>	

							<tr valign="top">
								<td width="100%" align="left" height="25px">	
									<gs:label text="Current period" id="BSC_PROG003D0001Q_currentPeriodShowLabel"></gs:label>
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_visionOid_1"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_frequency_1"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_startYearDate_1"></gs:inputfieldNoticeMsgLabel>
									<!--  
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_endYearDate_1"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_startDate_1"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_endDate_1"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_measureDataOrganizationOid_1"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_measureDataEmployeeOid_1"></gs:inputfieldNoticeMsgLabel>	
																		
								</td>
							</tr>	
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0001Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0007Q_visionOid_1" dataSource="visionMap" id="BSC_PROG003D0007Q_visionOid_1" onChange="BSC_PROG003D0007Q_visionOid_1_change();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0001Q_frequency')"/>
									<gs:select name="BSC_PROG003D0007Q_frequency_1" dataSource="frequencyMap" id="BSC_PROG003D0007Q_frequency_1" value="6" onChange="BSC_PROG003D0007Q_setFrequencyValue('1');" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_frequency'">
					    				Select frequency.
									</div> 									
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0001Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0007Q_startYearDate_1" name="BSC_PROG003D0007Q_startYearDate_1" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	<s:property value="getText('BSC_PROG003D0001Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0007Q_endYearDate_1" name="BSC_PROG003D0007Q_endYearDate_1" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0001Q_startDate')"/>
									<input id="BSC_PROG003D0007Q_startDate_1" type="text" name="BSC_PROG003D0007Q_startDate_1" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0001Q_endDate')"/>
									<input id="BSC_PROG003D0007Q_endDate_1" type="text" name="BSC_PROG003D0007Q_endDate_1" data-dojo-type="dijit.form.DateTextBox"
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
									<gs:select name="BSC_PROG003D0007Q_dataFor_1" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0001Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0001Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0007Q_dataFor_1" onChange="BSC_PROG003D0007Q_setDataForValue('1');" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0007Q_measureDataOrganizationOid_1" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0007Q_measureDataOrganizationOid_1" onChange="BSC_PROG003D0007Q_setMeasureDataOrgaValue('1');" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0007Q_measureDataEmployeeOid_1" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0007Q_measureDataEmployeeOid_1" onChange="BSC_PROG003D0007Q_setMeasureDataEmplValue('1');" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_measureDataEmployeeOid'">
					    				Select measure data personal/Employee.
									</div>									
								</td>
							</tr>																													
							
																											
						</table>
						
						
						<table border="0" width="100%" >
						
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
									<gs:label text="Previous period" id="BSC_PROG003D0001Q_previousPeriodShowLabel"></gs:label>
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_visionOid_2"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_frequency_2"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_startYearDate_2"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_endYearDate_2"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_startDate_2"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_endDate_2"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_measureDataOrganizationOid_2"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0007Q_measureDataEmployeeOid_2"></gs:inputfieldNoticeMsgLabel>
																		
								</td>
							</tr>							
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0001Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0007Q_visionOid_2" dataSource="visionMap" id="BSC_PROG003D0007Q_visionOid_2" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0001Q_frequency')"/>
									<gs:select name="BSC_PROG003D0007Q_frequency_2" dataSource="frequencyMap" id="BSC_PROG003D0007Q_frequency_2" value="6" onChange="BSC_PROG003D0007Q_setFrequencyValue('2');" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_frequency'">
					    				Select frequency.
									</div> 									
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0001Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0007Q_startYearDate_2" name="BSC_PROG003D0007Q_startYearDate_2" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	<s:property value="getText('BSC_PROG003D0001Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0007Q_endYearDate_2" name="BSC_PROG003D0007Q_endYearDate_2" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0001Q_startDate')"/>
									<input id="BSC_PROG003D0007Q_startDate_2" type="text" name="BSC_PROG003D0007Q_startDate_2" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0001Q_endDate')"/>
									<input id="BSC_PROG003D0007Q_endDate_2" type="text" name="BSC_PROG003D0007Q_endDate_2" data-dojo-type="dijit.form.DateTextBox"
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
									<gs:select name="BSC_PROG003D0007Q_dataFor_2" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0001Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0001Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0007Q_dataFor_2" onChange="BSC_PROG003D0007Q_setDataForValue('2');" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0007Q_measureDataOrganizationOid_2" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0007Q_measureDataOrganizationOid_2" onChange="BSC_PROG003D0007Q_setMeasureDataOrgaValue('2');" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0001Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0001Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0007Q_measureDataEmployeeOid_2" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0007Q_measureDataEmployeeOid_2" onChange="BSC_PROG003D0007Q_setMeasureDataEmplValue('2');" readonly="Y" value="all"></gs:filteringSelect>
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
		<tr valign="top">
			<td width="100%" align="left" >
				<div id="BSC_PROG003D0007Q_content"></div>
			</td>
		</tr>
		<tr valign="top">
			<td width="100%" align="left" >
				<div id="BSC_PROG003D0007Q_chart"></div>
			</td>
		</tr>		
	</table>			
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
