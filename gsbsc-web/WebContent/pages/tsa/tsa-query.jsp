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

.btnExcelIcon {
  	background-image: url(./icons/excel.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

</style>

<script type="text/javascript">

var BSC_PROG007D0002Q_fieldsId = new Object();
BSC_PROG007D0002Q_fieldsId["tsaOid"] 						= "BSC_PROG007D0002Q_tsaOid";
BSC_PROG007D0002Q_fieldsId['visionOid'] 					= 'BSC_PROG007D0002Q_visionOid';
BSC_PROG007D0002Q_fieldsId['frequency'] 					= 'BSC_PROG007D0002Q_frequency';
BSC_PROG007D0002Q_fieldsId["frequency"] 					= "BSC_PROG007D0002Q_frequency";
BSC_PROG007D0002Q_fieldsId["startYearDate"] 				= "BSC_PROG007D0002Q_startYearDate";
BSC_PROG007D0002Q_fieldsId["endYearDate"] 					= "BSC_PROG007D0002Q_endYearDate";
BSC_PROG007D0002Q_fieldsId["startDate"] 					= "BSC_PROG007D0002Q_startDate";
BSC_PROG007D0002Q_fieldsId["endDate"] 						= "BSC_PROG007D0002Q_endDate";
BSC_PROG007D0002Q_fieldsId["dataFor"] 						= "BSC_PROG007D0002Q_dataFor";
BSC_PROG007D0002Q_fieldsId["measureDataOrganizationOid"] 	= "BSC_PROG007D0002Q_measureDataOrganizationOid";
BSC_PROG007D0002Q_fieldsId["measureDataEmployeeOid"] 		= "BSC_PROG007D0002Q_measureDataEmployeeOid";

function BSC_PROG007D0002Q_query(type) {
	
	var url = 'bsc.tsaQueryForecastAction.action';
	if ('excel' == type) {
		url = 'bsc.tsaQueryForecastForExcelAction.action';
	}
	
	if ('html' == type) {
		BSC_PROG007D0002Q_clearContent();
	}
	
	var dateRangeChartPngData = null;
	if ('excel' == type) {
		if ( '' != $('#BSC_PROG007D0002Q_kpi_daterange_container').html() ) {
			var dateRangeSvg = $('#BSC_PROG007D0002Q_kpi_daterange_container').highcharts().getSVG();
			dateRangeChartPngData = viewPage.getSVGImage2CanvasToDataUrlPNGfromData( dateRangeSvg );
		}		
	}
	if ( dateRangeChartPngData == null ) {
		dateRangeChartPngData = '';
	}	
	
	setFieldsBackgroundDefault(BSC_PROG007D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG007D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/' + url, 
			{ 
				'fields.tsaOid' 					: 	dijit.byId("BSC_PROG007D0002Q_tsaOid").get("value"),
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG007D0002Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG007D0002Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG007D0002Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG007D0002Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG007D0002Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG007D0002Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG007D0002Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG007D0002Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG007D0002Q_frequency").get("value"),
				'fields.dateRangeChartPngData'		:	dateRangeChartPngData
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG007D0002Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG007D0002Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ('excel' == type) {
					openCommonLoadUpload( 'download', data.uploadOid, { } );
					return;
				}
				BSC_PROG007D0002Q_showInfo(data);
				BSC_PROG007D0002Q_showChartForKpiDateRange(data);
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG007D0002Q_showInfo(data) {
	dojo.byId("BSC_PROG007D0002Q_kpi_daterange_paramInfo").innerHTML = '';
	var str = '';
	
	var tsa = data.tsa;
	var coefficients = data.coefficients;
	
	str += '<table border="0" width="600px" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">';
	str += '<legend><b>Param infornation</b></legend>';
	str += '<tr>';
	str += '<td width="30%" align="center" bgcolor="#f1eee5"><b>Item</b></td>';
	str += '<td width="70%" align="center" bgcolor="#f1eee5"><b>Value</b></td>';
	str += '</tr>';
	
	str += '<tr>';
	str += '<td width="30%" align="left" bgcolor="#ffffff">Param name</td>';
	str += '<td width="70%" align="left" bgcolor="#ffffff">' + tsa.name + '</td>';
	str += '</tr>';	
	
	str += '<tr>';
	str += '<td width="30%" align="left" bgcolor="#ffffff">Integration order</td>';
	str += '<td width="70%" align="left" bgcolor="#ffffff">' + tsa.integrationOrder + '</td>';
	str += '</tr>';	
	
	str += '<tr>';
	str += '<td width="30%" align="left" bgcolor="#ffffff">Forecast next</td>';
	str += '<td width="70%" align="left" bgcolor="#ffffff">' + tsa.forecastNext + '</td>';
	str += '</tr>';
	
	str += '<tr>';
	str += '<td width="30%" align="left" bgcolor="#ffffff">Description</td>';
	str += '<td width="70%" align="left" bgcolor="#ffffff">' + tsa.description + '</td>';
	str += '</tr>';	
	
	for (var n = 0; n < coefficients.length; n++) {
		str += '<tr>';
		str += '<td width="30%" align="left" bgcolor="#ffffff">Coefficient&nbsp;(' + coefficients[n].seq + ')</td>';
		str += '<td width="70%" align="left" bgcolor="#ffffff">' + coefficients[n].seqValue + '</td>';
		str += '</tr>';			
	}
	
	str += '</table>';
	
	dojo.byId("BSC_PROG007D0002Q_kpi_daterange_paramInfo").innerHTML = str;
}

function BSC_PROG007D0002Q_showChartForKpiDateRange(data) {
	
	if ( null == data.categories || data.categories.length < 2 ) {
		return;
	}
	
    $('#BSC_PROG007D0002Q_kpi_daterange_container').highcharts({
        title: {
            text: 'Forecast analysis',
            x: -20 //center
        },
        subtitle: {
            text: 'KPI Score',
            x: -20
        },
        xAxis: {
            categories: data.categories
        },
        yAxis: {
            title: {
                text: 'Score'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ' Score'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: data.series
    });
    
}

function BSC_PROG007D0002Q_clearContent() {
	// only clear chart
	dojo.byId("BSC_PROG007D0002Q_kpi_daterange_paramInfo").innerHTML = "";
	dojo.byId("BSC_PROG007D0002Q_kpi_daterange_container").innerHTML = "";
}

//------------------------------------------------------------------------------
function BSC_PROG007D0002Q_changeParam() {
	var tsaOid = dijit.byId("BSC_PROG007D0002Q_tsaOid").get("value");
	if ( null == tsaOid || _gscore_please_select_id == tsaOid ) {
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.tsaParamMeasureFreqDataAction.action', 
			{ 
				'fields.tsaOid' 					: 	tsaOid
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
				dijit.byId('BSC_PROG007D0002Q_frequency').set("value", data.measureFreq.freq);
				//1-DEPT,2-EMP,3-Both
				dijit.byId('BSC_PROG007D0002Q_dataFor').set("value", "all");
				if ('1' == data.measureFreq.dataType) {
					dijit.byId('BSC_PROG007D0002Q_dataFor').set("value", "organization");
				}
				if ('2' == data.measureFreq.dataType) {
					dijit.byId('BSC_PROG007D0002Q_dataFor').set("value", "employee");
				}				
				if ('1' == data.measureFreq.freq || '2' == data.measureFreq.freq || '3' == data.measureFreq.freq) {
					dijit.byId('BSC_PROG007D0002Q_startDate').set("value", data.measureFreq.startDateTextBoxValue);
					dijit.byId('BSC_PROG007D0002Q_endDate').set("value", data.measureFreq.endDateTextBoxValue);					
				} else {
					dijit.byId('BSC_PROG007D0002Q_startYearDate').set("value", data.measureFreq.startDateTextBoxValue);
					dijit.byId('BSC_PROG007D0002Q_endYearDate').set("value", data.measureFreq.endDateTextBoxValue);
				}
				
				BSC_PROG007D0002Q_setFrequencyValue();
				BSC_PROG007D0002Q_setDataForValue();				
				
				setTimeout(function(){
					if ('1' == data.measureFreq.dataType) {
						dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set("value", data.measureFreq.organizationOid);
					}				
					if ('2' == data.measureFreq.dataType) {
						dijit.byId('BSC_PROG007D0002Q_measureDataEmployeeOid').set("value", data.measureFreq.employeeOid);
					}						
				}, 150);
				
			}, 
			function(error) {
				alert(error);
			}
	);		
	
}


//------------------------------------------------------------------------------
//measure options settings function
//------------------------------------------------------------------------------
function BSC_PROG007D0002Q_setDataForValue() {
	dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG007D0002Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG007D0002Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG007D0002Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG007D0002Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG007D0002Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG007D0002Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG007D0002Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG007D0002Q_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG007D0002Q_frequency").get("value");
	dijit.byId("BSC_PROG007D0002Q_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG007D0002Q_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0002Q_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG007D0002Q_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0002Q_endYearDate").set("readOnly", false);				
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
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:145px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG007D0002Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG007D0002Q_query('html');
											}">Query</button>				
																		
									<button id="BSC_PROG007D0002Q_btnExcel" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){  
												BSC_PROG007D0002Q_query('excel');
											}">Excel</button>
																				
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_tsaOid"></gs:inputfieldNoticeMsgLabel>		
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_visionOid"></gs:inputfieldNoticeMsgLabel>		
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_endYearDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_endDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>											
							</tr>	

							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									Param
									<gs:select name="BSC_PROG007D0002Q_tsaOid" dataSource="paramMap" id="BSC_PROG007D0002Q_tsaOid" onChange="BSC_PROG007D0002Q_changeParam();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_tsaOid'">
					    				Select param item.
									</div>  									
						    		&nbsp;										
								
									Vision
									<gs:select name="BSC_PROG007D0002Q_visionOid" dataSource="visionMap" id="BSC_PROG007D0002Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									Measure data frequency
									<gs:select name="BSC_PROG007D0002Q_frequency" dataSource="frequencyMap" id="BSC_PROG007D0002Q_frequency" value="6" onChange="BSC_PROG007D0002Q_setFrequencyValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_frequency'">
					    				Select frequency.
									</div> 						
												
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
											
							    	Measure data start year
							    	<input id="BSC_PROG007D0002Q_startYearDate" name="BSC_PROG007D0002Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	End year
							    	<input id="BSC_PROG007D0002Q_endYearDate" name="BSC_PROG007D0002Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									Measure data start date
									<input id="BSC_PROG007D0002Q_startDate" type="text" name="BSC_PROG007D0002Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									End date
									<input id="BSC_PROG007D0002Q_endDate" type="text" name="BSC_PROG007D0002Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_endDate'">
					    				Select end date.
									</div>							    			
							    </td>	
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									Measure data type for
									<gs:select name="BSC_PROG007D0002Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization\", \"employee\":\"Employee\" }" id="BSC_PROG007D0002Q_dataFor" onChange="BSC_PROG007D0002Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									Organization
									<gs:select name="BSC_PROG007D0002Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG007D0002Q_measureDataOrganizationOid" onChange="BSC_PROG007D0002Q_setMeasureDataOrgaValue();" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									Employee
									<gs:select name="BSC_PROG007D0002Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG007D0002Q_measureDataEmployeeOid" onChange="BSC_PROG007D0002Q_setMeasureDataEmplValue();" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureDataEmployeeOid'">
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
	
	<BR/>
	<div id="BSC_PROG007D0002Q_kpi_daterange_paramInfo"></div>
	<BR/>
	<div id="BSC_PROG007D0002Q_kpi_daterange_container"></div>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
