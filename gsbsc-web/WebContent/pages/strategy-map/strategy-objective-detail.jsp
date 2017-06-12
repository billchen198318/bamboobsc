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
	
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no"/>
	
<style type="text/css">

</style>

<script type="text/javascript">

function BSC_PROG002D0007Q_S00_query() {
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	'${visionOid}',
				'fields.startYearDate'				:	'${yearDate}',
				'fields.endYearDate'				:	'${yearDate}',
				'fields.startDate'					:	'',
				'fields.endDate'					:	'',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	'',
				'fields.measureDataEmployeeOid'		:	'',
				'fields.frequency'					:	'6',
				'fields.nextType'					:	'OBJ', // 請參考 BscConstants.HEAD_FOR_OBJ_ID
				'fields.nextId'						:	'${objective.objId}',
				'fields.ngVer'						:	'N'				
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
				
				alertDialog(_getApplicationProgramNameById('${programId}'), 'Measure-data frequency year: ${yearDate}', function(){}, 'Y');
				
				$("#BSC_PROG002D0007Q_S00_content").html( data.body );
				BSC_PROG002D0007Q_S00_showChartForObjectives(data);
				BSC_PROG002D0007Q_S00_showChartForKpis(data);
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG002D0007Q_S00_showChartForObjectives(data) {
	
	dojo.byId("BSC_PROG002D0007Q_S00_objectives_alert_title").innerHTML = '<span class="isa_info"><b>Strategy objectives</b></span>';
	
	var chartDivContent = "";
	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			var divChartId = "BSC_PROG002D0007Q_S00_objectives_container_" + objectiveItem.objId;
			chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
		}
	}
	$("#BSC_PROG002D0007Q_S00_objectives_container").html( chartDivContent );
	
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
			var divChartId = "BSC_PROG002D0007Q_S00_objectives_container_" + objectiveItem.objId;
			
			var maxVal = objectiveItem.target;
			if (objectiveItem.score > maxVal) {
				maxVal = objectiveItem.score;
			}
			
			BSC_PROG002D0007Q_S00_setSpeedGaugeChart(gaugeOptions, divChartId, objectiveItem.name, maxVal, objectiveItem.score);
			
		}
	}    
	
	
}


function BSC_PROG002D0007Q_S00_showChartForKpis(data) {
	
	dojo.byId("BSC_PROG002D0007Q_S00_kpis_alert_title").innerHTML = '<span class="isa_info"><b>KPIs</b></span>';
	
	var chartDivContent = "";
	for (var p in data.perspectiveItems) {
		for (var o in data.perspectiveItems[p].objectives) {
			var objectiveItem = data.perspectiveItems[p].objectives[o];
			for (var k in objectiveItem.kpis) {
				var kpi = objectiveItem.kpis[k];
				var divChartId = "BSC_PROG002D0007Q_S00_kpi_container_" + kpi.id;
				chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
			}
		}
	}
	$("#BSC_PROG002D0007Q_S00_kpis_container").html( chartDivContent );
	
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
				var divChartId = "BSC_PROG002D0007Q_S00_kpi_container_" + kpi.id;
				
				var maxVal = kpi.target;
				if (kpi.score > maxVal) {
					maxVal = kpi.score;
				}
				maxVal = parseInt(maxVal+'', 10);
				
				BSC_PROG002D0007Q_S00_setSpeedGaugeChart(gaugeOptions, divChartId, kpi.name, maxVal, kpi.score);				
				
			}
		}
	}
	
	
}


function BSC_PROG002D0007Q_S00_setSpeedGaugeChart(gaugeOptions, chartId, textTitle, maxVal, score) {
	
	var minVal = 0;
	if ( minVal > score ) {
		minVal = score;
	}
	
    // The speed gauge
    $( '#'+chartId ).highcharts(Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: minVal,
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
            data: [ parseFloat( BSC_PROG002D0007Q_S00_parseScore(score) ) ],
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


function BSC_PROG002D0007Q_S00_parseScore( score ) {
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

<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="style: {overflow: 'auto', padding: 0, height: '530px'}">


	<br/>
		
	<table border="0">
	<tr>
	<td>	
		<div id="BSC_PROG002D0007Q_S00_objectives_alert_title"></div>
		<div id="BSC_PROG002D0007Q_S00_objectives_container"></div>	
	</td>
	</tr>
	</table>	
		
		<br/>
		<br/>
		
	<table border="0">
	<tr>
	<td>	
		<div id="BSC_PROG002D0007Q_S00_kpis_alert_title"></div>
		<div id="BSC_PROG002D0007Q_S00_kpis_container"></div>			
	</td>
	</tr>
	</table>
	
	<div id="BSC_PROG002D0007Q_S00_content"></div>	

</div>	
	
<script type="text/javascript">
${programId}_page_message();
setTimeout(BSC_PROG002D0007Q_S00_query(), 1000);
</script>	
</body>
</html>
