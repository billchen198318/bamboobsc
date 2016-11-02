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

var BSC_PROG003D0006Q_fieldsId = new Object();
BSC_PROG003D0006Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0006Q_visionOid';
BSC_PROG003D0006Q_fieldsId['frequency'] 					= 'BSC_PROG003D0006Q_frequency';
BSC_PROG003D0006Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0006Q_startYearDate';
BSC_PROG003D0006Q_fieldsId['endYearDate'] 					= 'BSC_PROG003D0006Q_endYearDate';
BSC_PROG003D0006Q_fieldsId['startDate'] 					= 'BSC_PROG003D0006Q_startDate';
BSC_PROG003D0006Q_fieldsId['endDate'] 						= 'BSC_PROG003D0006Q_endDate';
BSC_PROG003D0006Q_fieldsId['measureDataOrganizationOid'] 	= 'BSC_PROG003D0006Q_measureDataOrganizationOid';
BSC_PROG003D0006Q_fieldsId['measureDataEmployeeOid'] 		= 'BSC_PROG003D0006Q_measureDataEmployeeOid';

function BSC_PROG003D0006Q_query() {
	BSC_PROG003D0006Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0006Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0006Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0006Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0006Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0006Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0006Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0006Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0006Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0006Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0006Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0006Q_frequency").get("value"),
				'fields.nobody'					: "Y"
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0006Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0006Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG003D0006Q_showTables( data );
				BSC_PROG003D0006Q_showKpisBarCharts( data );
				BSC_PROG003D0006Q_showKpisMeterGauge( data );
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG003D0006Q_setDataForValue() {
	dijit.byId('BSC_PROG003D0006Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0006Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0006Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG003D0006Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0006Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0006Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0006Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG003D0006Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG003D0006Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0006Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0006Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG003D0006Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0006Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0006Q_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG003D0006Q_frequency").get("value");
	dijit.byId("BSC_PROG003D0006Q_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0006Q_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0006Q_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0006Q_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG003D0006Q_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0006Q_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG003D0006Q_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0006Q_endYearDate").set("readOnly", false);				
	}
}

function BSC_PROG003D0006Q_showTables( data ) {
	var t = '';
	t += '<table width="1100px" cellspacing="1" cellpadding="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;" >';
	var c = 0;
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			for ( var k in objective.kpis ) {
				var kpi = objective.kpis[k];
				if ( c == 0 ) { // first line label
					t += '<tr>';
					t += '<td bgcolor="#F6F6F6" align="left" width="320px"><b>KPI</b></td>';
					t += '<td bgcolor="#F6F6F6" align="left"><b>Maximum</b></td>';
					t += '<td bgcolor="#F6F6F6" align="left"><b>Target</b></td>';
					t += '<td bgcolor="#F6F6F6" align="left"><b>Minimum</b></td>';	
					t += '<td bgcolor="#F6F6F6" align="left"><b>Score</b></td>';
					for ( var r in kpi.dateRangeScores ) {
						t += '<td bgcolor="#f5f5f5" align="left"><b>' + kpi.dateRangeScores[r].date + '</b></td>';					
					}
					t += '</tr>';
				}
				t += '<tr>';
				t += '<td bgcolor="#ffffff" align="left">' + kpi.name + '</td>';
				t += '<td bgcolor="#ffffff" align="left">' + kpi.max + '</td>';
				t += '<td bgcolor="#ffffff" align="left">' + kpi.target + '</td>';
				t += '<td bgcolor="#ffffff" align="left">' + kpi.min + '</td>';	
				t += '<td bgcolor="' + kpi.bgColor + '" align="center"><font color="' + kpi.fontColor + '">' + BSC_PROG003D0006Q_parseScore(kpi.score) + '</font></td>';	
				for ( var r in kpi.dateRangeScores ) {
					t += '<td bgcolor="' + kpi.dateRangeScores[r].bgColor + '" align="center"><font color="' + kpi.dateRangeScores[r].fontColor + '">' + BSC_PROG003D0006Q_parseScore(kpi.dateRangeScores[r].score) + '</font></td>';					
				}
				t += '</tr>';	
				
				c++;
			}
		}
	}
	t += '</table>';
	dojo.byId("BSC_PROG003D0006Q_table").innerHTML = t;	
}

var BSC_PROG003D0006Q_kpisBarCharts = null;
function BSC_PROG003D0006Q_showKpisBarCharts( data ) {
	
	// 清除要上傳的資料
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0006Q_kpisBarChartsData')>-1) {
			dojo.destroy(node.id);
		}	
	});		
	
	var max = [ ];
    var target = [ ];
    var score = [ ];
    var min = [ ];	
    var ticks = [ ];
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			for ( var k in objective.kpis ) {
				var kpi = objective.kpis[k];
				max.push( kpi.max );
				target.push( kpi.target );
				score.push( kpi.score );
				min.push( kpi.min );
				ticks.push( kpi.name );				
			}
		}
	}
	
	if ( BSC_PROG003D0006Q_kpisBarCharts != null ) {
		BSC_PROG003D0006Q_kpisBarCharts.destroy();
		BSC_PROG003D0006Q_kpisBarCharts = null;
	}
	
	// var target = [ 100, 110, 80 ];
	// var score = [ 100, 110, 80 ];
	// var min = [ 50, 55, 30 ];
	
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
    // var ticks = ['Obj1', 'Obj2', 'Obj3', 'Obj4'];
     
    BSC_PROG003D0006Q_kpisBarCharts = $.jqplot('BSC_PROG003D0006Q_kpisBarCharts', [max, target, score, min], {
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        series:[
			{label:'Maximum'},    
            {label:'Target'},
            {label:'Score'},
            {label:'Minimum'}
        ],
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: true,
            placement: 'outsideGrid'
        },
        axesDefaults: { // axesDefaults 增加tick項目斜度 30 的效果
            tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
            tickOptions: {
              angle: -30
            }
        },        
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
                pad: 1.05,
                tickOptions: {formatString: '%d'}
            }
        }
    });
    
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0006Q_kpisBarChartsData',
				name:	'BSC_PROG003D0006Q_kpisBarChartsData',
				type: 	"hidden",
				value:	$('#BSC_PROG003D0006Q_kpisBarCharts').jqplotToImageElem().outerHTML
			},
			"BSC_PROG003D0006Q_form"
	);    
    
}

function BSC_PROG003D0006Q_showKpisMeterGauge( data ) {
	
	// 清除要上傳的資料
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0006Q_meterGaugeChartDatas:')>-1 
				|| node.id!=null && node.id.indexOf('BSC_PROG003D0006Q_dateRangeLabel')>-1 ) { 
			dojo.destroy(node.id);
		}	
	});	
	
	var frequency = dijit.byId("BSC_PROG003D0006Q_frequency").get("value");
	var dateRangeStr = dijit.byId("BSC_PROG003D0006Q_startYearDate").get('displayedValue') + ' - ' + dijit.byId("BSC_PROG003D0006Q_endYearDate").get('displayedValue');
	if ( '1' == frequency || '2' == frequency || '3' == frequency ) {
		dateRangeStr = dijit.byId("BSC_PROG003D0006Q_startDate").get('displayedValue') + ' - ' + dijit.byId("BSC_PROG003D0006Q_endDate").get('displayedValue');
	}
	dateRangeStr += ' Frequency: ' + dijit.byId("BSC_PROG003D0006Q_frequency").get('displayedValue');
	
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0006Q_dateRangeLabel',
				name:	'BSC_PROG003D0006Q_dateRangeLabel',
				type: 	"hidden",
				value:	dateRangeStr
			},
			"BSC_PROG003D0006Q_form"
	);	
	
	var content = '';
	content += '<table width="1100px" border="0" cellpadding="1" cellspacing="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="#F6F6F6" align="center" ><font size="4"><b>KPIs metrics gauge ( ' + dateRangeStr + ' )</b></font></td>';
	content += '</tr>';		
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			for ( var k in objective.kpis ) {
				var kpi = objective.kpis[k];
				content += '<tr>';
				content += '<td width="50%" bgcolor="#ffffff" align="center" ><div id="BSC_PROG003D0006Q_charts_' + kpi.id + '" style="width:450px;height:320px;" ></td>';
				content += '<td width="50%" bgcolor="#ffffff" align="center">' + BSC_PROG003D0006Q_showKpiItemsDataContentTable( kpi ) + '<td>';
				content += '</tr>';					
			}
		}
	}
	content += '</table>';
	dojo.html.set(dojo.byId("BSC_PROG003D0006Q_contentKpis"), content, {extractContent: true, parseContent: true});	
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			for ( var k in objective.kpis ) {
				var kpi = objective.kpis[k];
				
				var target = kpi.target;
				var score = kpi.score;
				var id = 'BSC_PROG003D0006Q_charts_' + kpi.id;
				
				if (document.getElementById(id)==null) {
					alert('error lost id of div: ' + id);
					continue;
				} 
				
				var maxValue = target;
				if (maxValue < score) {
					maxValue = score;
				}
				maxValue = parseInt(maxValue+'', 10);				
				
				var labelString = kpi.name + " ( " + score + " ) ";
				
				$.jqplot(id, [ [parseInt(score+'', 10)] ], {
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
				
				var inputDataId = 'BSC_PROG003D0006Q_meterGaugeChartDatas:' + kpi.objId;
				var datas = [];
				datas.push({
					id: kpi.id,
					name: kpi.name,
					max: kpi.max,
					target: kpi.target,
					min: kpi.min,
					score: kpi.score,
					bgColor: kpi.bgColor,
					fontColor: kpi.fontColor,
					outerHTML: $('#' + id).jqplotToImageElem().outerHTML,
					dateRangeScores: kpi.dateRangeScores // 只有KPI才有的日期區間分數
				});
				var chartDatas = {};
				chartDatas.id = id;
				chartDatas.datas = datas;
				dojo.create(
						"input", {
							id: 	inputDataId,
							name:	inputDataId,
							type: 	"hidden",
							value:	JSON.stringify(chartDatas)
						},
						"BSC_PROG003D0006Q_form"
				);	
				
			}
		}
	}
	
}
function BSC_PROG003D0006Q_showKpiItemsDataContentTable( kpi ) {
	var content = '';
	content += '<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE" >';
	content += '<tr>';
	content += '<td bgcolor="' + kpi.bgColor + '" align="center" ><b><font size="4" color="' + kpi.fontColor + '" >' + kpi.name + '</font></b></td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Maximum:&nbsp;</b>' + kpi.max + '</td>';
	content += '</tr>';	
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Target:&nbsp;</b>' + kpi.target + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Minimum:&nbsp;</b>' + kpi.min + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Score:&nbsp;</b>' + kpi.score + '</td>';
	content += '</tr>';				
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" >' + kpi.description + '</td>';
	content += '</tr>';	
	if (kpi.attachments!=null) {
		content += '<tr>';
		content += '<td bgcolor="#ffffff" align="center" >' + BSC_PROG003D0006Q_getKpiAttacUrl(kpi.attachments) + '</td>';
		content += '</tr>';				
	}
	content += '</table>';
	return content;		
}
function BSC_PROG003D0006Q_getKpiAttacUrl(attac) {
	var urlStr = '';	
	for (var i=0; i<attac.length; i++) {
		var o = attac[i];
		if ('Y' == o.viewMode) {
			urlStr += '<a href="#" onclick="openCommonLoadUpload( \'view\', \'' + o.uploadOid + '\', { \'isDialog\' : \'Y\', \'title\' : \'KPI document/attachment view\', \'width\' : 1280, \'height\' : 768 }); return false;" style="color:#000000">' + o.showName + '</a>';
		} else {
			urlStr += '<a href="#" onclick="openCommonLoadUpload( \'download\', \'' + o.uploadOid + '\', {}); return false;" style="color:#000000">' + o.showName + '</a>';
		}		
		urlStr += '<br/>';
	}
	return urlStr;
}

function BSC_PROG003D0006Q_generateExport(type) {
	
	var chartsCount = 0;
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0006Q_meterGaugeChartDatas:')>-1) {
			chartsCount++;
		}	
	});
	if ( chartsCount < 1 ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0006Q_msg1')" escapeJavaScript="true"/>', function(){}, 'Y');
		showFieldsNoticeMessageLabel('BSC_PROG003D0006Q_visionOid'+_gscore_inputfieldNoticeMsgLabelIdName, '<s:property value="getText('MESSAGE.BSC_PROG003D0006Q_msg1')" escapeJavaScript="true"/>');
		return;
	}
		
	setFieldsBackgroundDefault(BSC_PROG003D0006Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0006Q_fieldsId);
	xhrSendForm(
			'${basePath}/bsc.kpisDashboardExcelAction.action', 
			'BSC_PROG003D0006Q_form', 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0006Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0006Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				openCommonLoadUpload( 'download', data.uploadOid, { } );				    								
			}, 
			function(error) {
				alert(error);
			}
	);
	
}

function BSC_PROG003D0006Q_clearContent() {
	
}

function BSC_PROG003D0006Q_parseScore( score ) {
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
								
									<button id="BSC_PROG003D0006Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0006Q_query();
											}"><s:property value="getText('BSC_PROG003D0006Q_btnQuery')"/></button>		
											
									<button id="BSC_PROG003D0006Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0006Q_generateExport('EXCEL');																			  
											}"><s:property value="getText('BSC_PROG003D0006Q_btnXls')"/></button>												
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_endYearDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<!--  
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_endDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0006Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0006Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0006Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0006Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0006Q_frequency')"/>
									<gs:select name="BSC_PROG003D0006Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0006Q_frequency" value="6" width="140" onChange="BSC_PROG003D0006Q_setFrequencyValue();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_frequency'">
					    				Select frequency.
									</div> 										
									&nbsp;
																	
								</td>											
							</tr>	
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0006Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0006Q_startYearDate" name="BSC_PROG003D0006Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_startYearDate'">
					    				Select start year.
									</div>									    				
							    	&nbsp;
							    	<s:property value="getText('BSC_PROG003D0006Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0006Q_endYearDate" name="BSC_PROG003D0006Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_endYearDate'">
					    				Select end year.
									</div>																		    									    							
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0006Q_startDate')"/>
									<input id="BSC_PROG003D0006Q_startDate" type="text" name="BSC_PROG003D0006Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_startDate'">
					    				Select start date.
									</div>												
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0006Q_endDate')"/>
									<input id="BSC_PROG003D0006Q_endDate" type="text" name="BSC_PROG003D0006Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_endDate'">
					    				Select end date.
									</div>									    										    														    										
								</td>
							</tr>															
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									<s:property value="getText('BSC_PROG003D0006Q_dataFor')"/>
									<gs:select name="BSC_PROG003D0006Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0006Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0006Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0006Q_dataFor" onChange="BSC_PROG003D0006Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_dataFor'">
					    				Select measure data type.
									</div>											
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0006Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0006Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0006Q_measureDataOrganizationOid" onChange="BSC_PROG003D0006Q_setMeasureDataOrgaValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>											
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0006Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0006Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0006Q_measureDataEmployeeOid" onChange="BSC_PROG003D0006Q_setMeasureDataEmplValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0006Q_measureDataEmployeeOid'">
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
	
	<div id="BSC_PROG003D0006Q_table" style="width:1100px"></div>		
	
	<div id="BSC_PROG003D0006Q_kpisBarCharts" style="height:540px; width:1100px"></div>
	
	<div id="BSC_PROG003D0006Q_contentKpis" style="height:100%; width:1100px"></div>
	
	<form name="BSC_PROG003D0006Q_form" id="BSC_PROG003D0006Q_form" action="."></form>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>

	