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


<!-- Highcharts -->
<script src="<%=basePath%>/highcharts/js/highcharts.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-3d.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-more.js"></script>
<script src="<%=basePath%>/highcharts/js/modules/exporting.js"></script>	
<script src="<%=basePath%>/highcharts/js/modules/solid-gauge.js"></script>


<script type="text/javascript">

var uploadOid='';

function refresh_dashboard() {
	var date1 = $("#date1").val();
	var date2 = $("#date2").val();
	var frequency = $("#frequency").val();
	if (date1 != null) {
		date1 = date1.replace("\-", "/").replace("\-", "/");
	}
	if (date2 != null) {
		date2 = date2.replace("\-", "/").replace("\-", "/");
	}
	
	// only clear Trend chart
	$('#daterange_alert_title').hide();
	$("#perspective_daterange_container").html('');
	$("#objective_daterange_container").html('');
	$("#kpi_daterange_container").html('');
	
	$('#myPleaseWait').modal('show');
	$.ajax({
		url: "${basePath}/bsc.mobile.doDashboardAction.action",
		data: { 
				'fields.date1'				:	date1,
				'fields.date2'				:	date2,
				'fields.frequency'			:	frequency,
				'fields.ver'					:	'newVer',
				'fields.visionOid'		:	$("#vision").val()
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
			$('#perspectives_alert_title').show();
			$('#objectives_alert_title').show();
			$('#kpi_alert_title').show();
			showChartForPerspectives(data);
			showChartForObjectives(data);
			showChartForKpis(data);
			showChartForPerspectiveDateRange(data);
			showChartForObjectiveDateRange(data);
			showChartForKpiDateRange(data);
		},
		error: function(e) {		
			$('#myPleaseWait').modal('hide');
			alert( e.statusText );			
		}
		
	});	
}

function showChartForPerspectives(data) {
	
	var chartData = [];
	for (var p in data.rootVision.perspectives) {
		chartData.push( [ data.rootVision.perspectives[p].name, getScore(data.rootVision.perspectives[p].score) ] );
	}
	
    $('#perspectives_container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: data.rootVision.title
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

function showChartForObjectives(data) {
	
	var chartDivContent = "";
	for (var p in data.rootVision.perspectives) {
		for (var o in data.rootVision.perspectives[p].objectives) {
			var objectiveItem = data.rootVision.perspectives[p].objectives[o];
			var divChartId = "objectives_container_" + objectiveItem.objId;
			chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
		}
	}
	$("#objectives_container").html( chartDivContent );
	
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

	for (var p in data.rootVision.perspectives) {
		for (var o in data.rootVision.perspectives[p].objectives) {
			var objectiveItem = data.rootVision.perspectives[p].objectives[o];
			var divChartId = "objectives_container_" + objectiveItem.objId;
			
			var maxVal = objectiveItem.target;
			if (objectiveItem.score > maxVal) {
				maxVal = objectiveItem.score;
			}
			
			setSpeedGaugeChart(gaugeOptions, divChartId, objectiveItem.name, maxVal, objectiveItem.score);
			
		}
	}    
	
	
}


function showChartForKpis(data) {
	
	var chartDivContent = "";
	for (var p in data.rootVision.perspectives) {
		for (var o in data.rootVision.perspectives[p].objectives) {
			var objectiveItem = data.rootVision.perspectives[p].objectives[o];
			for (var k in objectiveItem.kpis) {
				var kpi = objectiveItem.kpis[k];
				var divChartId = "kpi_container_" + kpi.id;
				chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
			}
		}
	}
	$("#kpi_container").html( chartDivContent );
	
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

	for (var p in data.rootVision.perspectives) {
		for (var o in data.rootVision.perspectives[p].objectives) {
			var objectiveItem = data.rootVision.perspectives[p].objectives[o];
			for (var k in objectiveItem.kpis) {
				
				var kpi = objectiveItem.kpis[k];
				var divChartId = "kpi_container_" + kpi.id;
				
				var maxVal = kpi.target;
				if (kpi.score > maxVal) {
					maxVal = kpi.score;
				}
				maxVal = parseInt(maxVal+'', 10);
				
				setSpeedGaugeChart(gaugeOptions, divChartId, kpi.name, maxVal, kpi.score);				
				
			}
		}
	}
	
	
}


function setSpeedGaugeChart(gaugeOptions, chartId, textTitle, maxVal, score) {
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
            data: [ getScore(score) ],
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

function showChartForPerspectiveDateRange(data) {
	
	if ( null == data.perspectiveCategories || data.perspectiveCategories.length < 2 ) {
		return;
	}
	
	$('#daterange_alert_title').show();
	
    $('#perspective_daterange_container').highcharts({
        title: {
            text: 'Perspectives trend',
            x: -20 //center
        },
        subtitle: {
            text: 'Perspectives Score',
            x: -20
        },
        xAxis: {
            categories: data.perspectiveCategories
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
        series: data.perspectiveSeries
    });
    
}
function showChartForObjectiveDateRange(data) {
	
	if ( null == data.objectiveCategories || data.objectiveCategories.length < 2 ) {
		return;
	}
	
	$('#daterange_alert_title').show();
	
    $('#objective_daterange_container').highcharts({
        title: {
            text: 'Strategy objectives trend',
            x: -20 //center
        },
        subtitle: {
            text: 'Strategy objectives Score',
            x: -20
        },
        xAxis: {
            categories: data.objectiveCategories
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
        series: data.objectiveSeries
    });
    
}
function showChartForKpiDateRange(data) {
	
	if ( null == data.categories || data.categories.length < 2 ) {
		return;
	}
	
	$('#daterange_alert_title').show();
	
    $('#kpi_daterange_container').highcharts({
        title: {
            text: 'KPIs trend',
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


function getScore(score) {
	var str = ( score ).toFixed(2) + '';
	str = str.replace(".00", "");
	return parseFloat(str);
}

</script>

</head>

<body>

<jsp:include page="topNavbar.jsp">
	<jsp:param value="dashboard" name="active"/>
</jsp:include>

  <div class="form-group">
    <label for="vision">Vision</label>
    <select class="form-control" id="vision">
    	<s:iterator value="visionMap" status="st" var="cols">
    		<option value="<s:property value="#cols.key"/>" ><s:property value="#cols.value"/></option>
    	</s:iterator>
    </select>
  </div>
  <div class="form-group">
    <label for="frequency">${action.getText('INDEX_frequency')}</label>
    <select class="form-control" id="frequency">
		<s:iterator value="frequencyMap" status="st" var="cols">
			<option value="<s:property value="#cols.key"/>" <s:if test=" \"6\" == #cols.key "> SELECTED </s:if> ><s:property value="#cols.value"/></option>	
		</s:iterator>	
    </select>
  </div>
<div class="form-group row">
  <label for="date1" class="col-xs-2 col-form-label">${action.getText('INDEX_date1')}</label>
  <div class="col-xs-10">
    <input  class="form-control" type="date" value="${measureDataDate2}" id="date1" />
  </div>
</div>
<div class="form-group row">
  <label for="date2" class="col-xs-2 col-form-label">${action.getText('INDEX_date2')}</label>
  <div class="col-xs-10">
    <input class="form-control" type="date" value="${measureDataDate2}" id="date2" />
  </div>
</div>

<button type="button" class="btn btn-primary" onclick="refresh_dashboard();">${action.getText("INDEX_refreshQuery")}</button>

<br/>
<br/>


<div data-role="content">
	<div class="alert alert-success collapse" role="alert" id="perspectives_alert_title">
	  <strong>Perspectives</strong>
	</div>
	<div id="perspectives_container">
		<div class="alert alert-info" role="alert">
		  <h4 class="alert-heading">Measure-data begin/end date description!</h4>
		  <p>The Frequency is <b>Year / Half of year / Quarter</b> , begin/end date capture <b>yyyy</b> for query.</p>
		  <p>The Frequency is <b>Month / Week</b> , begin/end date capture <b>yyyy-MM</b> for query.</p>
		  <p>The Frequency is <b>Day</b> , begin/end date capture <b>yyyy-MM-dd</b> for query.</p>    
		</div>		
	</div>

<table>
<tr>
<td>	
	<div class="alert alert-success collapse" role="alert" id="objectives_alert_title">
	  <strong>Strategy objectives</strong>
	</div>
	<div id="objectives_container">
	</div>
</td>
</tr>
</table>


<table>
<tr>
<td>		
	<div class="alert alert-success collapse" role="alert" id="kpi_alert_title">
	  <strong>KPIs</strong>
	</div>
	<div id="kpi_container">
	</div>
</td>
</tr>
</table>
	
	
	<div class="alert alert-success collapse" role="alert" id="daterange_alert_title">
	  <strong>Trend</strong>
	</div>
	
	<div id="perspective_daterange_container">
	</div>
	<br>	
	<div id="objective_daterange_container">
	</div>	
	<br>		
	<div id="kpi_daterange_container">
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