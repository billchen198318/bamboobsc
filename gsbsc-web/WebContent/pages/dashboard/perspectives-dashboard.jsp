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
BSC_PROG003D0004Q_fieldsId['endYearDate'] 					= 'BSC_PROG003D0004Q_endYearDate';
BSC_PROG003D0004Q_fieldsId['startDate'] 					= 'BSC_PROG003D0004Q_startDate';
BSC_PROG003D0004Q_fieldsId['endDate'] 						= 'BSC_PROG003D0004Q_endDate';
BSC_PROG003D0004Q_fieldsId['measureDataOrganizationOid'] 	= 'BSC_PROG003D0004Q_measureDataOrganizationOid';
BSC_PROG003D0004Q_fieldsId['measureDataEmployeeOid'] 		= 'BSC_PROG003D0004Q_measureDataEmployeeOid';


function BSC_PROG003D0004Q_setDataForValue() {
	dijit.byId('BSC_PROG003D0004Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0004Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0004Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG003D0004Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0004Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0004Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0004Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG003D0004Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG003D0004Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0004Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0004Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG003D0004Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0004Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0004Q_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG003D0004Q_frequency").get("value");
	dijit.byId("BSC_PROG003D0004Q_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0004Q_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0004Q_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG003D0004Q_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG003D0004Q_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0004Q_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG003D0004Q_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG003D0004Q_endYearDate").set("readOnly", false);				
	}
}


function BSC_PROG003D0004Q_query() {
	BSC_PROG003D0004Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0004Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0004Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.perspectivesDashboardContentAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0004Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0004Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0004Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0004Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0004Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0004Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0004Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0004Q_frequency").get("value")
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
				dijit.byId("BSC_PROG003D0004Q_startDate").set("displayedValue", data.startDate);
				dijit.byId("BSC_PROG003D0004Q_endDate").set("displayedValue", data.endDate);
				
				BSC_PROG003D0004Q_showTableContent( data );
				
				BSC_PROG003D0004Q_showChartForPerspectives( data );
				
				BSC_PROG003D0004Q_showChartForPerspectivesDateRange( data );
				
			}, 
			function(error) {
				alert(error);
			}
	);		
}


function BSC_PROG003D0004Q_generateExport() {
	
	var gaugeDatas = [];
	var dateRangeChartPngData = null;
	if ( '' != $('#BSC_PROG003D0004Q_perspectives_daterange_container').html() ) {
		var dateRangeSvg = $('#BSC_PROG003D0004Q_perspectives_daterange_container').highcharts().getSVG();
		dateRangeChartPngData = viewPage.getSVGImage2CanvasToDataUrlPNGfromData( dateRangeSvg );
	}
	dojo.query("div").forEach(function(node){
		if (node.id!=null && node.id.indexOf('BSC_PROG003D0004Q_perspectives_container_')>-1) {
			var gaugeChartPngData = '';
			var gaugeSvg = $('#' + node.id ).highcharts().getSVG();
			gaugeChartPngData = viewPage.getSVGImage2CanvasToDataUrlPNGfromData( gaugeSvg );
			gaugeDatas.push({
				id: node.id,
				data: gaugeChartPngData
			});			
			
		}	
	});		
	if ( dateRangeChartPngData == null ) {
		dateRangeChartPngData = '';
	}
	if (null == gaugeDatas || gaugeDatas.length < 1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0004Q_msg1')" escapeJavaScript="true"/>', function(){}, 'Y');
		showFieldsNoticeMessageLabel('BSC_PROG003D0004Q_visionOid'+_gscore_inputfieldNoticeMsgLabelIdName, '<s:property value="getText('MESSAGE.BSC_PROG003D0004Q_msg1')" escapeJavaScript="true"/>');		
		return;
	}
	
	xhrSendParameter(
			'${basePath}/bsc.perspectivesDashboardExcelAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG003D0004Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG003D0004Q_startYearDate").get('displayedValue'),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG003D0004Q_endYearDate").get('displayedValue'),
				'fields.startDate'					:	dijit.byId("BSC_PROG003D0004Q_startDate").get('displayedValue'),
				'fields.endDate'					:	dijit.byId("BSC_PROG003D0004Q_endDate").get('displayedValue'),
				'fields.dataFor'					:	dijit.byId("BSC_PROG003D0004Q_dataFor").get("value"),
				'fields.measureDataOrganizationOid'	:	dijit.byId("BSC_PROG003D0004Q_measureDataOrganizationOid").get("value"),
				'fields.measureDataEmployeeOid'		:	dijit.byId("BSC_PROG003D0004Q_measureDataEmployeeOid").get("value"),
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0004Q_frequency").get("value"),
				'fields.dateRangeChartPngData'	:	dateRangeChartPngData,
				'fields.gaugeDatas'	: JSON.stringify( { 'gaugeMapList' : gaugeDatas } )
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
				openCommonLoadUpload( 'download', data.uploadOid, { } );
			}, 
			function(error) {
				alert(error);
			}
	);		
	
}


function BSC_PROG003D0004Q_showTableContent( data ) {
	var vision = data.vision;
	var t = '';
	var c = 0;
	t += '<table width="1100px" cellspacing="1" cellpadding="1" bgcolor="' + data.backgroundColor + '" style="border:1px ' + data.backgroundColor  + ' solid; border-radius: 5px;" >';
	
	var headColspan = 4 + vision.perspectives[0].dateRangeScores.length;
	t += '<tr>';
	t += '<td bgcolor="' + data.backgroundColor + '" align="center" colspan="' + headColspan + '"><font color="' + data.fontColor + '"><b>' + data.vision.title + '</b></font></td>';
	t += '</tr>';
	
	for (var p in vision.perspectives) {
		var perspective = vision.perspectives[p];
		if ( c == 0 ) {
			t += '<tr>';
			t += '<td bgcolor="' + data.backgroundColor + '" align="left" width="320px"><font color="' + data.fontColor + '"><b>' + data.perspectiveTitle + '</b></font></td>';
			t += '<td bgcolor="' + data.backgroundColor + '" align="left"><font color="' + data.fontColor + '"><b>Target</b></font></td>';
			t += '<td bgcolor="' + data.backgroundColor + '" align="left"><font color="' + data.fontColor + '"><b>Minimum</b></font></td>';	
			t += '<td bgcolor="' + data.backgroundColor + '" align="left"><font color="' + data.fontColor + '"><b>Score</b></font></td>';
			for ( var r in perspective.dateRangeScores ) {
				t += '<td bgcolor="' + data.backgroundColor + '" align="left"><font color="' + data.fontColor + '"><b>' + perspective.dateRangeScores[r].date + '</b></font></td>';					
			}
			t += '</tr>';			
		}
		t += '<tr>';
		t += '<td bgcolor="#ffffff" align="left">' + perspective.name + '</td>';
		t += '<td bgcolor="#ffffff" align="left">' + perspective.target + '</td>';
		t += '<td bgcolor="#ffffff" align="left">' + perspective.min + '</td>';	
		t += '<td bgcolor="' + perspective.bgColor + '" align="center"><font color="' + perspective.fontColor + '">' + BSC_PROG003D0004Q_parseScore(perspective.score) + '</font></td>';	
		for ( var r in perspective.dateRangeScores ) {
			t += '<td bgcolor="' + perspective.dateRangeScores[r].bgColor + '" align="center"><font color="' + perspective.dateRangeScores[r].fontColor + '">' + BSC_PROG003D0004Q_parseScore(perspective.dateRangeScores[r].score) + '</font></td>';					
		}
		t += '</tr>';			
		c++;
	}
	t += '<tr>';
	t += '<td bgcolor="' + data.backgroundColor + '" align="left" colspan="' + headColspan + '"><font color="' + data.fontColor + '"><b>Frequency&nbsp;:&nbsp;' + data.displayFrequency + '&nbsp;&nbsp;date range&nbsp;:&nbsp;' + data.displayDateRange1 + '&nbsp;~&nbsp;' + data.displayDateRange2 + '&nbsp;&nbsp;Measure data type&nbsp;:&nbsp;' + data.measureDataTypeForTitle + '</b></font></td>';
	t += '</tr>';	
	t += '</table>';
	dojo.byId("BSC_PROG003D0004Q_content").innerHTML = t;
}


function BSC_PROG003D0004Q_showChartForPerspectives(data) {
	
	var vision = data.vision;
	
	var chartDivContent = "";
	for (var p in vision.perspectives) {
		var perspective = vision.perspectives[p];
		var divChartId = "BSC_PROG003D0004Q_perspectives_container_" + perspective.perId;
		chartDivContent += '<div id="' + divChartId +'" style="width: 300px; height: 200px; float: left"></div>';
	}
	$("#BSC_PROG003D0004Q_perspectives_container").html( chartDivContent );
	
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
    
    for (var p in vision.perspectives) {
    	var perspective = vision.perspectives[p];
		var divChartId = "BSC_PROG003D0004Q_perspectives_container_" + perspective.perId;
		
		var maxVal = perspective.target;
		if (perspective.score > maxVal) {
			maxVal = perspective.score;
		}
		
	    $( '#'+divChartId ).highcharts(Highcharts.merge(gaugeOptions, {
	        yAxis: {
	            min: 0,
	            max: maxVal,
	            title: {
	                text: perspective.name
	            }
	        },

	        credits: {
	            enabled: false
	        },

	        series: [{
	            name: perspective.name,
	            data: [ parseFloat( BSC_PROG003D0004Q_parseScore(perspective.score) ) ],
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
	
	
}


function BSC_PROG003D0004Q_showChartForPerspectivesDateRange(data) {
	
	if ( null == data.categories || data.categories.length < 2 ) {
		return;
	}
	
    $('#BSC_PROG003D0004Q_perspectives_daterange_container').highcharts({
        title: {
            text: data.perspectiveTitle + ' trend',
            x: -20 //center
        },
        subtitle: {
            text: 'Frequency : ' + data.displayFrequency + '  date range : ' + data.displayDateRange1 + ' ~ ' + data.displayDateRange2,
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


function BSC_PROG003D0004Q_clearContent() {
	dojo.byId("BSC_PROG003D0004Q_content").innerHTML = "";
	dojo.byId("BSC_PROG003D0004Q_perspectives_container").innerHTML = "";
	dojo.byId("BSC_PROG003D0004Q_perspectives_daterange_container").innerHTML = "";
}


function BSC_PROG003D0004Q_parseScore( score ) {
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
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0004Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:145px">
					
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
												BSC_PROG003D0004Q_generateExport();																			  
											}"><s:property value="getText('BSC_PROG003D0004Q_btnXls')"/></button>	

									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_visionOid"></gs:inputfieldNoticeMsgLabel>		
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0004Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
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
									<gs:select name="BSC_PROG003D0004Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0004Q_frequency" value="6" onChange="BSC_PROG003D0004Q_setFrequencyValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_frequency'">
					    				Select frequency.
									</div> 									
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	<s:property value="getText('BSC_PROG003D0004Q_startYearDate')"/>
							    	<input id="BSC_PROG003D0004Q_startYearDate" name="BSC_PROG003D0004Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	<s:property value="getText('BSC_PROG003D0004Q_endYearDate')"/>
							    	<input id="BSC_PROG003D0004Q_endYearDate" name="BSC_PROG003D0004Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									<s:property value="getText('BSC_PROG003D0004Q_startDate')"/>
									<input id="BSC_PROG003D0004Q_startDate" type="text" name="BSC_PROG003D0004Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									<s:property value="getText('BSC_PROG003D0004Q_endDate')"/>
									<input id="BSC_PROG003D0004Q_endDate" type="text" name="BSC_PROG003D0004Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_endDate'">
					    				Select end date.
									</div>							    			
							    </td>	
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									<s:property value="getText('BSC_PROG003D0004Q_dataFor')"/>
									<gs:select name="BSC_PROG003D0004Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"${action.getText('BSC_PROG003D0004Q_measureDataOrganizationOid')}\", \"employee\":\"${action.getText('BSC_PROG003D0004Q_measureDataEmployeeOid')}\" }" id="BSC_PROG003D0004Q_dataFor" onChange="BSC_PROG003D0004Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0004Q_measureDataOrganizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0004Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0004Q_measureDataOrganizationOid" onChange="BSC_PROG003D0004Q_setMeasureDataOrgaValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									<s:property value="getText('BSC_PROG003D0004Q_measureDataEmployeeOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0004Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0004Q_measureDataEmployeeOid" onChange="BSC_PROG003D0004Q_setMeasureDataEmplValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0004Q_measureDataEmployeeOid'">
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
	
	<div id="BSC_PROG003D0004Q_content"></div>	
	
	<br>
	
	<div id="BSC_PROG003D0004Q_perspectives_container"></div>
	
	<br>
	
	<div id="BSC_PROG003D0004Q_perspectives_daterange_container"  style="width: 100%; height: 450px; float: left"></div>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
