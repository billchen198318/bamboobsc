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

</style>

<script type="text/javascript">

var BSC_PROG003D0009Q_fieldsId = new Object();
BSC_PROG003D0009Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0009Q_visionOid';
BSC_PROG003D0009Q_fieldsId['frequency'] 					= 'BSC_PROG003D0009Q_frequency';
BSC_PROG003D0009Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0009Q_startYearDate';
BSC_PROG003D0009Q_fieldsId['endYearDate'] 					= 'BSC_PROG003D0009Q_endYearDate';
BSC_PROG003D0009Q_fieldsId['startDate'] 					= 'BSC_PROG003D0009Q_startDate';
BSC_PROG003D0009Q_fieldsId['endDate'] 						= 'BSC_PROG003D0009Q_endDate';
BSC_PROG003D0009Q_fieldsId['measureDataOrganizationOid'] 	= 'BSC_PROG003D0009Q_measureDataOrganizationOid';
BSC_PROG003D0009Q_fieldsId['measureDataEmployeeOid'] 		= 'BSC_PROG003D0009Q_measureDataEmployeeOid';

function BSC_PROG003D0009Q_query() {
	BSC_PROG003D0009Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0009Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0009Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0009Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0009Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0009Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0009Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0009Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0009Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0009Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0009Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0009Q_frequency").get("value"),
				'fields.nobody'					: "Y"
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0009Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0009Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG003D0009Q_showChartForPerspectives(data);
				BSC_PROG003D0009Q_showChartForObjectives(data);
				BSC_PROG003D0009Q_showChartForKpis(data);
				BSC_PROG003D0009Q_showChartForKpiDateRange(data);
			}, 
			function(error) {
				alert(error);
			}
	);	
}


function BSC_PROG003D0009Q_setDataForValue() {
	dijit.byId('BSC_PROG003D0009Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0009Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0009Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG003D0009Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0009Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0009Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0009Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG003D0009Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG003D0009Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0009Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0009Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG003D0009Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0009Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0009Q_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG003D0009Q_frequency").get("value");
	dijit.byId("BSC_PROG003D0009Q_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0009Q_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0009Q_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0009Q_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG003D0009Q_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0009Q_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG003D0009Q_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0009Q_endYearDate").set("readOnly", false);				
	}
}


function BSC_PROG003D0009Q_showChartForPerspectives(data) {
		
	dojo.byId("BSC_PROG003D0009Q_perspectives_alert_title").innerHTML = '<span class="isa_info"><b>Perspectives</b></span>';
	
	var chartData = [];
	for (var p in data.perspectiveItems) {
		var score = parseFloat( BSC_PROG003D0009Q_parseScore(data.perspectiveItems[p].score) );
		chartData.push( [ data.perspectiveItems[p].name, score ] );
	}	
	
    $('#BSC_PROG003D0009Q_perspectives_container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: data.subTitle
        },
        subtitle: {
            text: 'Perspectives item'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Score'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Score: <b>{point.y:.1f}</b>'
        },
        series: [{
            name: 'Item',
            data: chartData,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.1f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
    
}


function BSC_PROG003D0009Q_showChartForObjectives(data) {
	
	dojo.byId("BSC_PROG003D0009Q_objectives_alert_title").innerHTML = '<span class="isa_info"><b>Strategy objectives</b></span>';
	
	var chartDivContent = "";
	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			var divChartId = "BSC_PROG003D0009Q_objectives_container_" + objectiveItem.objId;
			chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
		}
	}
	$("#BSC_PROG003D0009Q_objectives_container").html( chartDivContent );
	
    var gaugeOptions = {

            chart: {
                type: 'solidgauge'
            },

            title: null,

            pane: {
                center: ['50%', '85%'],
                size: '140%',
                startAngle: -90,
                endAngle: 90,
                background: {
                    backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                    innerRadius: '60%',
                    outerRadius: '100%',
                    shape: 'arc'
                }
            },

            tooltip: {
                enabled: true
            },

            // the value axis
            yAxis: {
                stops: [
                    [0.1, '#DF5353'], // red
                    [0.5, '#DDDF0D'], // yellow
                    [0.9, '#55BF3B'] // green
                ],
                lineWidth: 0,
                minorTickInterval: null,
                tickAmount: 2,
                title: {
                    y: -70
                },
                labels: {
                    y: 16
                }
            },

            plotOptions: {
                solidgauge: {
                    dataLabels: {
                        y: 5,
                        borderWidth: 0,
                        useHTML: true
                    }
                }
            }
        };

	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			var divChartId = "BSC_PROG003D0009Q_objectives_container_" + objectiveItem.objId;
			
			var maxVal = objectiveItem.target;
			if (objectiveItem.score > maxVal) {
				maxVal = objectiveItem.score;
			}
			
			BSC_PROG003D0009Q_setSpeedGaugeChart(gaugeOptions, divChartId, objectiveItem.name, maxVal, objectiveItem.score);
			
		}
	}    
	
	
}


function BSC_PROG003D0009Q_showChartForKpis(data) {
	
	dojo.byId("BSC_PROG003D0009Q_kpis_alert_title").innerHTML = '<span class="isa_info"><b>KPIs</b></span>';
	
	var chartDivContent = "";
	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			for (var k in objectiveItem.kpis) {
				var kpi = objectiveItem.kpis[k];
				var divChartId = "BSC_PROG003D0009Q_kpi_container_" + kpi.id;
				chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
			}
		}
	}
	$("#BSC_PROG003D0009Q_kpis_container").html( chartDivContent );
	
    var gaugeOptions = {

            chart: {
                type: 'solidgauge'
            },

            title: null,

            pane: {
                center: ['50%', '85%'],
                size: '140%',
                startAngle: -90,
                endAngle: 90,
                background: {
                    backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                    innerRadius: '60%',
                    outerRadius: '100%',
                    shape: 'arc'
                }
            },

            tooltip: {
                enabled: true
            },

            // the value axis
            yAxis: {
                stops: [
                    [0.1, '#DF5353'], // red
                    [0.5, '#DDDF0D'], // yellow
                    [0.9, '#55BF3B'] // green
                ],
                lineWidth: 0,
                minorTickInterval: null,
                tickAmount: 2,
                title: {
                    y: -70
                },
                labels: {
                    y: 16
                }
            },

            plotOptions: {
                solidgauge: {
                    dataLabels: {
                        y: 5,
                        borderWidth: 0,
                        useHTML: true
                    }
                }
            }
        };

	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			for (var k in objectiveItem.kpis) {
				
				var kpi = objectiveItem.kpis[k];
				var divChartId = "BSC_PROG003D0009Q_kpi_container_" + kpi.id;
				
				var maxVal = kpi.target;
				if (kpi.score > maxVal) {
					maxVal = kpi.score;
				}
				maxVal = parseInt(maxVal+'', 10);
				
				BSC_PROG003D0009Q_setSpeedGaugeChart(gaugeOptions, divChartId, kpi.name, maxVal, kpi.score);				
				
			}
		}
	}
	
	
}


function BSC_PROG003D0009Q_setSpeedGaugeChart(gaugeOptions, chartId, textTitle, maxVal, score) {
    // The speed gauge
    $( '#'+chartId ).highcharts(Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: maxVal,
            title: {
                text: textTitle
            }
        },

        credits: {
            enabled: false
        },

        series: [{
            name: textTitle,
            data: [ parseFloat( BSC_PROG003D0009Q_parseScore(score) ) ],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                    ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                       '<span style="font-size:12px;color:silver">Score</span></div>'
            },
            tooltip: {
                valueSuffix: ' Score'
            }
        }]

    }));		
}


function BSC_PROG003D0009Q_showChartForKpiDateRange(data) {
	
	if ( null == data.categories || data.categories.length < 2 ) {
		return;
	}
	
	dojo.byId("BSC_PROG003D0009Q_kpi_daterange_alert_title").innerHTML = '<span class="isa_info"><b>Trend</b></span>';
	
    $('#BSC_PROG003D0009Q_kpi_daterange_container').highcharts({
        title: {
            text: 'Trend',
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


function BSC_PROG003D0009Q_clearContent() {
	// only clear Trend chart
	dojo.byId("BSC_PROG003D0009Q_kpi_daterange_alert_title").innerHTML = "";
	dojo.byId("BSC_PROG003D0009Q_kpi_daterange_container").innerHTML = "";
}

function BSC_PROG003D0009Q_parseScore( score ) {
	var scoreStr = viewPage.roundFloat(score, 2) + '';
	scoreStr = scoreStr.replace('.00', '');
	return scoreStr;
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
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0006Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:145px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0009Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0009Q_query();
											}"><s:property value="getText('BSC_PROG003D0006Q_btnQuery')"/></button>										
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_endYearDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<!--  
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_endDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0009Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0006Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0009Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0009Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0006Q_frequency')"/>
									<gs:select name="BSC_PROG003D0009Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0009Q_frequency" value="6" width="140" onChange="BSC_PROG003D0009Q_setFrequencyValue();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_frequency'">
					    				Select frequency.
									</div> 										
									&nbsp;
																	
								</td>											
							</tr>	
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0006Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0009Q_startYearDate" name="BSC_PROG003D0009Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_startYearDate'">
					    				Select start year.
									</div>									    				
							    	&nbsp;
							    	<s:property value="getText('BSC_PROG003D0006Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0009Q_endYearDate" name="BSC_PROG003D0009Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_endYearDate'">
					    				Select end year.
									</div>																		    									    							
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0006Q_startDate')"/>
									<input id="BSC_PROG003D0009Q_startDate" type="text" name="BSC_PROG003D0009Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_startDate'">
					    				Select start date.
									</div>												
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0006Q_endDate')"/>
									<input id="BSC_PROG003D0009Q_endDate" type="text" name="BSC_PROG003D0009Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_endDate'">
					    				Select end date.
									</div>									    										    														    										
								</td>
							</tr>															
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									<s:property value="getText('BSC_PROG003D0006Q_dataFor')"/>
									<gs:select name="BSC_PROG003D0009Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0006Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0006Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0009Q_dataFor" onChange="BSC_PROG003D0009Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_dataFor'">
					    				Select measure data type.
									</div>											
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0006Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0009Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0009Q_measureDataOrganizationOid" onChange="BSC_PROG003D0009Q_setMeasureDataOrgaValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>											
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0006Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0009Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0009Q_measureDataEmployeeOid" onChange="BSC_PROG003D0009Q_setMeasureDataEmplValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0009Q_measureDataEmployeeOid'">
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
	
	<br/>
	<br/>
	
	<div id="BSC_PROG003D0009Q_perspectives_alert_title"></div>
	<div id="BSC_PROG003D0009Q_perspectives_container"></div>
	
	<br/>
	<br/>
	
<table border="0">
<tr>
<td>	
	<div id="BSC_PROG003D0009Q_objectives_alert_title"></div>
	<div id="BSC_PROG003D0009Q_objectives_container"></div>	
</td>
</tr>
</table>	
	
	<br/>
	<br/>
	
<table border="0">
<tr>
<td>	
	<div id="BSC_PROG003D0009Q_kpis_alert_title"></div>
	<div id="BSC_PROG003D0009Q_kpis_container"></div>			
</td>
</tr>
</table>	
	
	<br/>
	<br/>
	
	<div id="BSC_PROG003D0009Q_kpi_daterange_alert_title"></div>
	<div id="BSC_PROG003D0009Q_kpi_daterange_container"></div>			

	<br/>
	<br/>
			
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>

	