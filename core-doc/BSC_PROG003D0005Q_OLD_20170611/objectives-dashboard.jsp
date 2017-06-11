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

var BSC_PROG003D0005Q_fieldsId = new Object();
BSC_PROG003D0005Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0005Q_visionOid';
BSC_PROG003D0005Q_fieldsId['frequency'] 					= 'BSC_PROG003D0005Q_frequency';
BSC_PROG003D0005Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0005Q_startYearDate';

function BSC_PROG003D0005Q_query() {
	BSC_PROG003D0005Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0005Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0005Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0005Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0005Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0005Q_startYearDate").get('displayedValue'),
				'fields.startDate'					:	'',
				'fields.endDate'					:	'',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	_gscore_please_select_id,
				'fields.measureDataEmployeeOid'		:	_gscore_please_select_id,
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0005Q_frequency").get("value"),
				'fields.nobody'					: "Y"
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0005Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0005Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG003D0005Q_showObjectivesBarCharts( data );
				BSC_PROG003D0005Q_showObjectivesMeterGauge( data );
			}, 
			function(error) {
				alert(error);
			}
	);	
}

var BSC_PROG003D0005Q_objectivesBarCharts = null;
function BSC_PROG003D0005Q_showObjectivesBarCharts( data ) {
	
	// 清除要上傳的資料
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0005Q_objectivesBarChartsData')>-1) {
			dojo.destroy(node.id);
		}	
	});		
	
    var target = [ ];
    var score = [ ];
    var min = [ ];	
    var ticks = [ ];
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			target.push( objective.target );
			score.push( objective.score );
			min.push( objective.min );
			ticks.push( objective.name );
		}
	}
	
	if ( BSC_PROG003D0005Q_objectivesBarCharts != null ) {
		BSC_PROG003D0005Q_objectivesBarCharts.destroy();
		BSC_PROG003D0005Q_objectivesBarCharts = null;
	}
	
	// var target = [ 100, 110, 80 ];
	// var score = [ 100, 110, 80 ];
	// var min = [ 50, 55, 30 ];
	
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
    // var ticks = ['Obj1', 'Obj2', 'Obj3', 'Obj4'];
     
    BSC_PROG003D0005Q_objectivesBarCharts = $.jqplot('BSC_PROG003D0005Q_objectivesBarCharts', [target, score, min], {
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
            {label:'Target'},
            {label:'Score'},
            {label:'Min'}
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
				id: 	'BSC_PROG003D0005Q_objectivesBarChartsData',
				name:	'BSC_PROG003D0005Q_objectivesBarChartsData',
				type: 	"hidden",
				value:	$('#BSC_PROG003D0005Q_objectivesBarCharts').jqplotToImageElem().outerHTML
			},
			"BSC_PROG003D0005Q_form"
	);    
    
}

function BSC_PROG003D0005Q_showObjectivesMeterGauge( data ) {
	
	// 清除要上傳的資料
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0005Q_meterGaugeChartDatas:')>-1) {
			dojo.destroy(node.id);
		}	
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0005Q_year')>-1 ) {
			dojo.destroy(node.id);
		}
	});	
	
	var content = '';
	content += '<table width="1100px" border="0" cellpadding="1" cellspacing="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="#F6F6F6" align="center" ><font size="4"><b>Objectives metrics gauge ( ' + dijit.byId("BSC_PROG003D0005Q_startYearDate").get('displayedValue') + ' )</b></font></td>';
	content += '</tr>';	
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			content += '<tr>';
			content += '<td width="50%" bgcolor="#ffffff" align="center" ><div id="BSC_PROG003D0005Q_charts_' + objective.objId + '" style="width:450px;height:320px;" ></td>';
			content += '<td width="50%" bgcolor="#ffffff" align="center">' + BSC_PROG003D0005Q_showObjectiveItemsDataContentTable( objective ) + '<td>';
			content += '</tr>';				
		}						
	}	
	content += '</table>';
	dojo.html.set(dojo.byId("BSC_PROG003D0005Q_contentObjectives"), content, {extractContent: true, parseContent: true});	
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			var target = objective.target;
			var score = objective.score;
			var id = 'BSC_PROG003D0005Q_charts_' + objective.objId;
			
			if (document.getElementById(id)==null) {
				alert('error lost id of div: ' + id);
				continue;
			} 
			
			var maxValue = target;
			if (maxValue < score) {
				maxValue = score;
			}		
			maxValue = parseInt(maxValue+'', 10);
			
			var labelString = objective.name + " ( " + score + " ) ";
			
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
			
			var inputDataId = 'BSC_PROG003D0005Q_meterGaugeChartDatas:' + objective.objId;
			var datas = [];
			datas.push({
				id: objective.objId,
				name: objective.name,
				target: objective.target,
				min: objective.min,
				score: objective.score,
				bgColor: objective.bgColor,
				fontColor: objective.fontColor,
				outerHTML: $('#' + id).jqplotToImageElem().outerHTML
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
					"BSC_PROG003D0005Q_form"
			);		
			
		}
	}		
}
function BSC_PROG003D0005Q_showObjectiveItemsDataContentTable( objective ) {
	var content = '';
	content += '<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE" >';
	content += '<tr>';
	content += '<td bgcolor="' + objective.bgColor + '" align="center" ><b><font size="4" color="' + objective.fontColor + '" >' + objective.name + '</font></b></td>';
	content += '</tr>';		
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Target:&nbsp;</b>' + objective.target + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Min:&nbsp;</b>' + objective.min + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Score:&nbsp;</b>' + objective.score + '</td>';
	content += '</tr>';				
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" >' + objective.description + '</td>';
	content += '</tr>';	
	content += '</table>';
	return content;	
}

function BSC_PROG003D0005Q_generateExport(type) {

	var chartsCount = 0;
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0005Q_meterGaugeChartDatas:')>-1) {
			chartsCount++;
		}	
	});
	if ( chartsCount < 1 ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0005Q_msg1')" escapeJavaScript="ture"/>', function(){}, 'Y');
		showFieldsNoticeMessageLabel('BSC_PROG003D0005Q_visionOid'+_gscore_inputfieldNoticeMsgLabelIdName, '<s:property value="getText('MESSAGE.BSC_PROG003D0005Q_msg1')" escapeJavaScript="ture"/>');
		return;
	}
	
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0005Q_year',
				name:	'BSC_PROG003D0005Q_year',
				type: 	"hidden",
				value:	dijit.byId("BSC_PROG003D0005Q_startYearDate").get('displayedValue')
			},
			"BSC_PROG003D0005Q_form"
	);		
	setFieldsBackgroundDefault(BSC_PROG003D0005Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0005Q_fieldsId);
	xhrSendForm(
			'${basePath}/bsc.objectivesDashboardExcelAction.action', 
			'BSC_PROG003D0005Q_form', 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0005Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0005Q_fieldsId);
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

function BSC_PROG003D0005Q_clearContent() {
	
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
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0005Q_options')" escapeJavaScript="ture"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:80px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0005Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0005Q_query();
											}"><s:property value="getText('BSC_PROG003D0005Q_btnQuery')"/></button>		
											
									<button id="BSC_PROG003D0005Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0005Q_generateExport('EXCEL');																			  
											}"><s:property value="getText('BSC_PROG003D0005Q_btnXls')"/></button>												
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0005Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0005Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0005Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0005Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0005Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0005Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0005Q_visionOid'">
					    				Select vision.
									</div>  										
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0005Q_frequency')"/>
									<gs:select name="BSC_PROG003D0005Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0005Q_frequency" value="6" width="140" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0005Q_frequency'">
					    				Select frequency. ( fixed for year )
									</div> 											
									&nbsp;
									
							    	<s:property value="getText('BSC_PROG003D0005Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0005Q_startYearDate" name="BSC_PROG003D0005Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0005Q_startYearDate'">
					    				Select year.
									</div>								    		
							    	&nbsp;				
							    										
								</td>											
							</tr>								
														
						</table>										
					
					</div>
				</div>
			</td>
		</tr>
	</table>	
	
	<div id="BSC_PROG003D0005Q_objectivesBarCharts" style="height:480px; width:1100px"></div>	
	
	<div id="BSC_PROG003D0005Q_contentObjectives" style="height:100%; width:1100px"></div>
	
	<form name="BSC_PROG003D0005Q_form" id="BSC_PROG003D0005Q_form" action="."></form>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	