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
	
	<!-- jqPlot -->
	<script type="text/javascript" src="<%=mainSysBasePath%>/jqplot/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="<%=mainSysBasePath%>/jqplot/plugins/jqplot.meterGaugeRenderer.min.js"></script>	
	
	<link rel="stylesheet" type="text/css" href="<%=mainSysBasePath%>/jqplot/jquery.jqplot.min.css" />		
	
<style type="text/css">

</style>

<script type="text/javascript">

var BSC_PROG003D0004Q_fieldsId = new Object();
BSC_PROG003D0004Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0004Q_visionOid';
BSC_PROG003D0004Q_fieldsId['frequency'] 					= 'BSC_PROG003D0004Q_frequency';
BSC_PROG003D0004Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0004Q_startYearDate';

function BSC_PROG003D0004Q_query() {
	BSC_PROG003D0004Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0004Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0004Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue'),
				'fields.startDate'					:	'',
				'fields.endDate'					:	'',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	_gscore_please_select_id,
				'fields.measureDataEmployeeOid'		:	_gscore_please_select_id,
				'fields.frequency'					:	'6'
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0004Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG003D0004Q_paintPieCharts(data);
				BSC_PROG003D0004Q_paintBarCharts(data);	
				BSC_PROG003D0004Q_showPerspectivesMeterGauge( data );
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG003D0004Q_paintPieCharts(data) {
    var myColors = data.perspectivesPieChartBgColor;
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(myColors);
    };
    
	nv.addGraph(function() {
		var chart = nv.models.pieChart()
			.x(function(d) { return d.label })
			.y(function(d) { return d.value })
			.showLabels(true).color(d3.scale.myColors().range());
		
		chart.pie.pieLabelsOutside(false).labelType("percent");
		
		d3.select("#BSC_PROG003D0004Q_pieChart svg")
			.datum(data.perspectivesPieChartValue)
			.transition().duration(350)
			.call(chart);
		
		return chart;
	});		
}

function BSC_PROG003D0004Q_paintBarCharts(data) {
    var myColors = data.perspectivesBarChartBgColor;
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(myColors);
    };
    
	nv.addGraph(function() {
		var chart = nv.models.discreteBarChart()
			.x(function(d) { return d.label })    //Specify the data accessors.
			.y(function(d) { return d.value })
			.staggerLabels(true)    //Too many bars and not enough room? Try staggering labels.
			.tooltips(false)        //Don't show tooltips
			.showValues(true)       //...instead, show the bar value right on top of each bar.
			.transitionDuration(350).color(d3.scale.myColors().range());

		d3.select('#BSC_PROG003D0004Q_barChart svg')
			.datum(data.perspectivesBarChartValue)
			.call(chart);

		nv.utils.windowResize(chart.update);

		return chart;
	});	
}

function BSC_PROG003D0004Q_showPerspectivesMeterGauge( data ) {
	var content = '';
	content += '<table width="1100px" border="0" cellpadding="1" cellspacing="1" bgcolor="#c1c7d0" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="DFFAFF" align="center" ><b>Perspectives meter gauge</b></td>';
	content += '</tr>';	
	for (var n in data.perspectiveItems) {
		content += '<tr>';
		content += '<td colspan="2" bgcolor="#ffffff" align="center" ><div id="BSC_PROG003D0004Q_charts_' + data.perspectiveItems[n].perId + '" style="width:450px;height:360px;" ></td>';
		content += '</tr>';					
		
	}	
	content += '</table>';
	dojo.html.set(dojo.byId("BSC_PROG003D0004Q_contentPerspectives"), content, {extractContent: true, parseContent: true});
	
	for (var n in data.perspectiveItems) {
		var perspective = data.perspectiveItems[n];
		var target = perspective.target;
		var score = perspective.score;
		var id = 'BSC_PROG003D0004Q_charts_' + perspective.perId;
		
		if (document.getElementById(id)==null) {
			alert('error lost id of div: ' + id);
			continue;
		} 
		
		var maxValue = 100;
		if (maxValue < score) {
			maxValue = score;
		}
		if (maxValue < target) {
			maxValue = target;
		}		
		
		var labelString = perspective.name + " ( " + score + " ) ";
		
		$.jqplot(id, [ [score] ], {
		       seriesDefaults: {
		           renderer: $.jqplot.MeterGaugeRenderer,
		           rendererOptions: {
		               label: labelString,
		               labelPosition: 'bottom',
		               intervals:[parseInt((maxValue*0.6)+'', 10), parseInt((maxValue*0.8)+'', 10), maxValue],
		               intervalColors:['#cc6666', '#E7E658', '#66cc66']
		           }
		       }
		});	
	}	
}

function BSC_PROG003D0004Q_clearContent() {
	
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

<body class="claro" bgcolor="#EEEEEE" >

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
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:80px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" bgcolor="#d7e3ed">	
								
									<button id="BSC_PROG003D0004Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0004Q_query();
											}">Query</button>		
											
											
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									Vision: 
									<gs:select name="BSC_PROG003D0004Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0004Q_visionOid"></gs:select>
						    		&nbsp;		    			
					    																	
									Measure data frequency:
									<gs:select name="BSC_PROG003D0004Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0004Q_frequency" value="6" width="140" readonly="Y"></gs:select>
									&nbsp;
									
							    	Measure data Year:
							    	<input id="BSC_PROG003D0004Q_startYearDate" name="BSC_PROG003D0004Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
							    	&nbsp;				
							    										
								</td>											
							</tr>								
														
						</table>										
					
					</div>
				</div>
			</td>
		</tr>
	</table>					
	
	<table border="0" width="1100px" >
		<tr>
			<td width="50%" align="center">
				<div id='BSC_PROG003D0004Q_pieChart' >
			  		<svg style='height:350px;width:500px'> </svg>
				</div>				
			</td>
			<td width="50%" align="center">
				<div id='BSC_PROG003D0004Q_barChart' >
			  		<svg style='height:350px;width:500px'> </svg>
				</div>				
			</td>			
		</tr>
	</table>	
	
	
	<br/>
	
	<div id="BSC_PROG003D0004Q_contentPerspectives" style="height:100%; width:1100px"></div>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	