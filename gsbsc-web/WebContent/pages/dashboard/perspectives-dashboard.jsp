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

var BSC_PROG003D0004Q_fieldsId = new Object();
BSC_PROG003D0004Q_fieldsId['visionOid'] 					= 'BSC_PROG003D0004Q_visionOid';
BSC_PROG003D0004Q_fieldsId['frequency'] 					= 'BSC_PROG003D0004Q_frequency';
BSC_PROG003D0004Q_fieldsId['startYearDate'] 				= 'BSC_PROG003D0004Q_startYearDate';

function BSC_PROG003D0004Q_query() {
	BSC_PROG003D0004Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0004Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0004Q_fieldsId);
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
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0004Q_frequency").get("value"),
				'fields.nobody'					: "Y"
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0004Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0004Q_fieldsId);
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
				
		chart.pie.labelsOutside(false).labelType("percent");
		
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
			//.tooltips(false)        //Don't show tooltips , 2016-01-09 tooltips no need for nvd3 1.8.1
			.showValues(true)       //...instead, show the bar value right on top of each bar.
			.color( myColors );

		d3.select('#BSC_PROG003D0004Q_barChart svg')
			.datum(data.perspectivesBarChartValue)
			.call(chart);

		nv.utils.windowResize(chart.update);

		return chart;
	});	
}

function BSC_PROG003D0004Q_showPerspectivesMeterGauge( data ) {
	
	// 清除要上傳的資料
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0004Q_meterGaugeChartDatas:')>-1) {
			dojo.destroy(node.id);
		}	
		if (node.id!=null && ( node.id.indexOf('BSC_PROG003D0004Q_barChartDatas')>-1 
				|| node.id.indexOf('BSC_PROG003D0004Q_pieChartDatas')>-1
				|| node.id.indexOf('BSC_PROG003D0004Q_year')>-1 ) ) {
			dojo.destroy(node.id);
		}
	});	
	
	var content = '';
	content += '<table width="1100px" border="0" cellpadding="1" cellspacing="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="#F6F6F6" align="center" ><font size="4"><b>Perspectives metrics gauge ( ' + dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue') + ' )</b></font></td>';
	content += '</tr>';	
	for (var n in data.perspectiveItems) {
		content += '<tr>';
		content += '<td width="50%" bgcolor="#ffffff" align="center" ><div id="BSC_PROG003D0004Q_charts_' + data.perspectiveItems[n].perId + '" style="width:450px;height:320px;" ></td>';
		content += '<td width="50%" bgcolor="#ffffff" align="center">' + BSC_PROG003D0004Q_showPerspectiveItemsDataContentTable( data.perspectiveItems[n] ) + '<td>';
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
		
		var maxValue = target;
		if (maxValue < score) {
			maxValue = score;
		}	
		maxValue = parseInt(maxValue+'', 10);
		
		var labelString = perspective.name + " ( " + score + " ) ";
		
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
		
		var inputDataId = 'BSC_PROG003D0004Q_meterGaugeChartDatas:' + perspective.perId;
		var datas = [];
		datas.push({
			id: perspective.perId,
			name: perspective.name,
			target: perspective.target,
			min: perspective.min,
			score: perspective.score,
			bgColor: perspective.bgColor,
			fontColor: perspective.fontColor,
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
				"BSC_PROG003D0004Q_form"
		);			
		
	}	
}
function BSC_PROG003D0004Q_showPerspectiveItemsDataContentTable( perspective ) {
	var content = '';
	content += '<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE" >';
	content += '<tr>';
	content += '<td bgcolor="' + perspective.bgColor + '" align="center" ><b><font size="4" color="' + perspective.fontColor + '" >' + perspective.name + '</font></b></td>';
	content += '</tr>';		
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Target:&nbsp;</b>' + perspective.target + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Min:&nbsp;</b>' + perspective.min + '</td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" ><b>Score:&nbsp;</b>' + perspective.score + '</td>';
	content += '</tr>';				
	content += '<tr>';
	content += '<td bgcolor="#ffffff" align="center" >' + perspective.description + '</td>';
	content += '</tr>';	
	content += '</table>';
	return content;
}

function BSC_PROG003D0004Q_generateExport(type) {
	
	var chartsCount = 0;
	dojo.query("input").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0004Q_meterGaugeChartDatas:')>-1) {
			chartsCount++;
		}	
	});
	if ( chartsCount < 1 ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0004Q_msg1')" escapeJavaScript="true"/>', function(){}, 'Y');
		showFieldsNoticeMessageLabel('BSC_PROG003D0004Q_visionOid'+_gscore_inputfieldNoticeMsgLabelIdName, '<s:property value="getText('MESSAGE.BSC_PROG003D0004Q_msg1')" escapeJavaScript="true"/>');
		return;
	}
	
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0004Q_barChartDatas',
				name:	'BSC_PROG003D0004Q_barChartDatas',
				type: 	"hidden",
				value:	viewPage.getStrToHex( viewPage.getSVGImage2CanvasToDataUrlPNG('#BSC_PROG003D0004Q_barChart svg') )
			},
			"BSC_PROG003D0004Q_form"
	);
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0004Q_pieChartDatas',
				name:	'BSC_PROG003D0004Q_pieChartDatas',
				type: 	"hidden",
				value:	viewPage.getStrToHex( viewPage.getSVGImage2CanvasToDataUrlPNG('#BSC_PROG003D0004Q_pieChart svg') )
			},
			"BSC_PROG003D0004Q_form"
	);	
	dojo.create(
			"input", {
				id: 	'BSC_PROG003D0004Q_year',
				name:	'BSC_PROG003D0004Q_year',
				type: 	"hidden",
				value:	dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue')
			},
			"BSC_PROG003D0004Q_form"
	);		
	setFieldsBackgroundDefault(BSC_PROG003D0004Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0004Q_fieldsId);
	xhrSendForm(
			'${basePath}/bsc.perspectivesDashboardExcelAction.action', 
			'BSC_PROG003D0004Q_form', 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0004Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0004Q_fieldsId);
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
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0004Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:80px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0004Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0004Q_query();
											}"><s:property value="getText('BSC_PROG003D0004Q_btnQuery')"/></button>		
											
									<button id="BSC_PROG003D0004Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0004Q_generateExport('EXCEL');																			  
											}"><s:property value="getText('BSC_PROG003D0004Q_btnXls')"/></button>		
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_startYearDate"></gs:inputfieldNoticeMsgLabel>							
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0004Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0004Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0004Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0004Q_frequency')"/>
									<gs:select name="BSC_PROG003D0004Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0004Q_frequency" value="6" width="140" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_frequency'">
					    				Select frequency. ( fixed for year )
									</div> 										
									&nbsp;
									
							    	<s:property value="getText('BSC_PROG003D0004Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0004Q_startYearDate" name="BSC_PROG003D0004Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_startYearDate'">
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
	
	<form name="BSC_PROG003D0004Q_form" id="BSC_PROG003D0004Q_form" action="."></form>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	