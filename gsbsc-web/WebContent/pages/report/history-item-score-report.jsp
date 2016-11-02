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

.btnLineIcon {
  	background-image: url(./icons/charts-line-chart-icon.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnHeatMapIcon {
  	background-image: url(./icons/view-list-icons.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}


</style>

<script type="text/javascript">

var BSC_PROG003D0008Q_fieldsId = new Object();
BSC_PROG003D0008Q_fieldsId['itemType'] 				= 'BSC_PROG003D0008Q_itemType';
BSC_PROG003D0008Q_fieldsId['frequency'] 			= 'BSC_PROG003D0008Q_frequency';
BSC_PROG003D0008Q_fieldsId['measureDataOrganizationOid'] 	= 'BSC_PROG003D0008Q_measureDataOrganizationOid';
BSC_PROG003D0008Q_fieldsId['measureDataEmployeeOid'] 		= 'BSC_PROG003D0008Q_measureDataEmployeeOid';

var BSC_PROG003D0008Q_chartType = 'line';
var BSC_PROG003D0008Q_dateChangeStatus = '';
function BSC_PROG003D0008Q_changeValue() {
	dojo.byId("BSC_PROG003D0008Q_chartContent").innerHTML = '';
}

function BSC_PROG003D0008Q_prevQuery() {
	BSC_PROG003D0008Q_dateChangeStatus = 'prev';
	BSC_PROG003D0008Q_queryChartData();
}

function BSC_PROG003D0008Q_nextQuery() {
	BSC_PROG003D0008Q_dateChangeStatus = 'next';
	BSC_PROG003D0008Q_queryChartData();
}

function BSC_PROG003D0008Q_setDataForValue() {
	dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG003D0008Q_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG003D0008Q_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG003D0008Q_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG003D0008Q_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG003D0008Q_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG003D0008Q_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG003D0008Q_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG003D0008Q_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG003D0008Q_queryChartData() {
	setFieldsBackgroundDefault(BSC_PROG003D0008Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0008Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.historyItemScoreReportContentQueryAction.action', 
			{ 
				'fields.dateVal'			:	dojo.byId('BSC_PROG003D0008Q_dateVal').value,
				'fields.dateChangeStatus'		:	BSC_PROG003D0008Q_dateChangeStatus,
				'fields.itemType'			: 	dijit.byId('BSC_PROG003D0008Q_itemType').get('value'),
				'fields.frequency'			: 	dijit.byId('BSC_PROG003D0008Q_frequency').get('value'),
				'fields.dataFor'			: 	dijit.byId('BSC_PROG003D0008Q_dataFor').get('value'),
				'fields.measureDataOrganizationOid'	: 	dijit.byId('BSC_PROG003D0008Q_measureDataOrganizationOid').get('value'),
				'fields.measureDataEmployeeOid'		: 	dijit.byId('BSC_PROG003D0008Q_measureDataEmployeeOid').get('value'),
				'fields.chartType'		:	BSC_PROG003D0008Q_chartType
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0008Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0008Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.byId('BSC_PROG003D0008Q_dateVal').value = data.newDateVal;
				dojo.byId('BSC_PROG003D0008Q_startDate').innerHTML = data.startDate;
				dojo.byId('BSC_PROG003D0008Q_endDate').innerHTML = data.endDate;
				if ('line' == BSC_PROG003D0008Q_chartType) {
					BSC_PROG003D0008Q_getLineChart( data );
				} else {
					BSC_PROG003D0008Q_getHeatMapChart( data );
				}
				if ( '' != data.message ) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				}		
			}, 
			function(error) {
				alert(error);
			}
	);		
}
function BSC_PROG003D0008Q_getLineChart(data) {
    $('#BSC_PROG003D0008Q_chartContent').highcharts({
        title: {
            text: 'History item score',
            x: -20 //center
        },
        subtitle: {
            text: data.subtitle,
            x: -20
        },
        xAxis: {
            categories: data.chartCategories
        },
        yAxis: {
            title: {
                text: 'Item (Score)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ' score'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: data.chartData
    });	
}

function BSC_PROG003D0008Q_getHeatMapChart(data) {
    $('#BSC_PROG003D0008Q_chartContent').highcharts({

        chart: {
            type: 'heatmap',
            marginTop: 40,
            marginBottom: 80,
            plotBorderWidth: 1
        },


        title: {
            text: data.subtitle
        },

        xAxis: {
            categories: data.xAxisCategories
        },

        yAxis: {
            categories: data.yAxisCategories,
            title: null
        },

        colorAxis: {
            min: 0,
            minColor: '#FFFFFF',
            maxColor: Highcharts.getOptions().colors[0]
        },

        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 350
        },

        tooltip: {
            formatter: function () {
                return '<b>' + this.series.xAxis.categories[this.point.x] + '</b><br><b>' +
                    this.point.value + '</b> score <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
            }
        },

        series: [{
            name: 'Item Score',
            borderWidth: 1,
            data: data.heatMapChartData,
            dataLabels: {
                enabled: true,
                color: '#000000'
            }
        }]

    });
    
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
			<td width="100%" align="center" height="25%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:125px">
					
						<table border="0" width="100%" >
						
							<tr valign="top">
								<td width="100%" align="left" height="40px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	

									
									<button id="BSC_PROG003D0008Q_btnLineChartQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnLineIcon',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0008Q_dateChangeStatus = '';
												BSC_PROG003D0008Q_chartType = 'line';
												dojo.byId('BSC_PROG003D0008Q_dateVal').value = '${dateVal}';
												BSC_PROG003D0008Q_queryChartData();
											}">Query Line chart</button>			

									<button id="BSC_PROG003D0008Q_btnHeatMapQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnHeatMapIcon',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0008Q_dateChangeStatus = '';
												BSC_PROG003D0008Q_chartType = 'heatMap';
												dojo.byId('BSC_PROG003D0008Q_dateVal').value = '${dateVal}';
												BSC_PROG003D0008Q_queryChartData();
											}">Query Heat map chart</button>	
									
									&nbsp;
									&nbsp;
									&nbsp;
											
									<img src="./images/go-previous.png" alt="prev" border="0" onclick="BSC_PROG003D0008Q_prevQuery();" title="click to query The previous period" />
									&nbsp;
									<b><font color="#333333" size="+1"><span id="BSC_PROG003D0008Q_startDate">${startDate}</span> ~ <span id="BSC_PROG003D0008Q_endDate">${endDate}</span></font></b>									
									&nbsp;
									<img src="./images/go-next.png" alt="next" border="0" onclick="BSC_PROG003D0008Q_nextQuery();" title="click to query Next period" />																					
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0008Q_itemType"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0008Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0008Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0008Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
																
								</td>
							</tr>										
						
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
									Item type
									<gs:select name="BSC_PROG003D0008Q_itemType" dataSource="{ \"1\":\"Vision\", \"2\":\"Perspectives\", \"3\":\"Strategy objectives\", \"4\":\"KPI\"}" id="BSC_PROG003D0008Q_itemType" value="1" onChange="BSC_PROG003D0008Q_changeValue();" width="200"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0008Q_itemType'">
					    				Select type.
									</div> 									
									&nbsp;&nbsp;
									
									Frequency	
									<gs:select name="BSC_PROG003D0008Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0008Q_frequency" value="6" onChange="BSC_PROG003D0008Q_changeValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0008Q_frequency'">
					    				Select frequency.
									</div> 										
									&nbsp;&nbsp;								
								
								</td>
							</tr>	
													

							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
									Measure data type for
									<gs:select name="BSC_PROG003D0008Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization\", \"employee\":\"Employee\" }" id="BSC_PROG003D0008Q_dataFor" onChange="BSC_PROG003D0008Q_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0008Q_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									Organization
									<gs:filteringSelect name="BSC_PROG003D0008Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0008Q_measureDataOrganizationOid" onChange="BSC_PROG003D0008Q_setMeasureDataOrgaValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0008Q_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									Employee
									<gs:filteringSelect name="BSC_PROG003D0008Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0008Q_measureDataEmployeeOid" onChange="BSC_PROG003D0008Q_setMeasureDataEmplValue();" readonly="Y" value="all"></gs:filteringSelect>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0008Q_measureDataEmployeeOid'">
					    				Select measure data personal/Employee.
									</div>
									
									
									<s:hidden name="BSC_PROG003D0008Q_dateVal" id="BSC_PROG003D0008Q_dateVal" value="%{dateVal}"></s:hidden>
									
								</td>
							</tr>										
				
								</td>
							</tr>							
						</table>
						
					</div>
				</div>
			</td>
		</tr>
	</table>								
	
	<div id="BSC_PROG003D0008Q_chartContent" style="min-width: 970px; height: 650px; max-width: 2048px; margin: 0 auto">
		<br/>
		<br/>
		<br/>
		<span class="isa_info">
		This item score is every hour Job monitor data, it not real-time score. If you do not update the frequency of measure-data every day, the item monitor score will not change.
		</span>
	</div>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	