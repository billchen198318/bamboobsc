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

.btnPieIcon {
  	background-image: url(./icons/chart-pie.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnBarIcon {
  	background-image: url(./icons/chart-graph-2d-1.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnLineIcon {
  	background-image: url(./icons/charts-line-chart-icon.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnAreaIcon {
  	background-image: url(./icons/area_chart.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

</style>

<!-- 
<script src="<%=basePath%>highcharts/js/highcharts.js"></script>
<script src="<%=basePath%>highcharts/js/modules/exporting.js"></script>
-->

<script type="text/javascript">

var QCHARTS_PROG002D0001Q_fieldsId = new Object();
QCHARTS_PROG002D0001Q_fieldsId['dataSourceConfOid'] 	= 'QCHARTS_PROG002D0001Q_dataSourceConfOid';
QCHARTS_PROG002D0001Q_fieldsId['dataQueryMapperOid'] 	= 'QCHARTS_PROG002D0001Q_dataQueryMapperOid';
QCHARTS_PROG002D0001Q_fieldsId['dataQueryMapperSetOid'] = 'QCHARTS_PROG002D0001Q_dataQueryMapperSetOid';
QCHARTS_PROG002D0001Q_fieldsId['name'] 					= 'QCHARTS_PROG002D0001Q_name';
QCHARTS_PROG002D0001Q_fieldsId['queryExpression'] 		= 'QCHARTS_PROG002D0001Q_queryExpression';

function getQCHARTS_PROG002D0001Q_Parameter(queryType) {
	return {
		'fields.queryType'				:	queryType,
		'fields.dataSourceConfOid'		:	dijit.byId("QCHARTS_PROG002D0001Q_dataSourceConfOid").get("value"),
		'fields.dataQueryMapperOid'		:	dijit.byId("QCHARTS_PROG002D0001Q_dataQueryMapperOid").get("value"),
		'fields.dataQueryMapperSetOid'	:	dijit.byId("QCHARTS_PROG002D0001Q_dataQueryMapperSetOid").get("value"),
		'fields.name'					:	dijit.byId("QCHARTS_PROG002D0001Q_name").get("value"),
		'fields.queryExpression'		:	dijit.byId("QCHARTS_PROG002D0001Q_queryExpression").get("value")
	};
}

function QCHARTS_PROG002D0001Q_query() {	
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.queryDataAction.action', 
			getQCHARTS_PROG002D0001Q_Parameter('1'), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);				
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
					return;
				}
				dojo.byId("QCHARTS_PROG002D0001Q_content").innerHTML = data.gridContent;
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0001Q_pie() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.queryDataAction.action', 
			getQCHARTS_PROG002D0001Q_Parameter('2'), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);		
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
					return;
				}
				
			    $('#QCHARTS_PROG002D0001Q_charts').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: 1,//null,
			            plotShadow: false
			        },
			        title: {
			            text: dijit.byId("QCHARTS_PROG002D0001Q_name").get("value")
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                }
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: 'Percentage',
			            data: data.pieDatas
			        }]
			    });				
				
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0001Q_bar() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.queryDataAction.action', 
			getQCHARTS_PROG002D0001Q_Parameter('3'), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
					return;
				}
				
			    $('#QCHARTS_PROG002D0001Q_charts').highcharts({
			        chart: {
			            type: 'bar'
			        },
			        title: {
			            text: dijit.byId("QCHARTS_PROG002D0001Q_name").get("value")
			        },
			        subtitle: {
			            text: 'Query result'
			        },
			        xAxis: {
			            categories: data.seriesCategories,
			            title: {
			                text: null
			            }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: 'Query (value)',
			                align: 'high'
			            },
			            labels: {
			                overflow: 'justify'
			            }
			        },
			        tooltip: {
			            valueSuffix: ' value'
			        },
			        plotOptions: {
			            bar: {
			                dataLabels: {
			                    enabled: true
			                }
			            }
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -40,
			            y: 100,
			            floating: true,
			            borderWidth: 1,
			            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
			            shadow: true
			        },
			        credits: {
			            enabled: false
			        },
			        series: data.seriesData
			    });		
				
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0001Q_line() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.queryDataAction.action', 
			getQCHARTS_PROG002D0001Q_Parameter('4'), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
					return;
				}
				
			    $('#QCHARTS_PROG002D0001Q_charts').highcharts({
			        title: {
			            text: dijit.byId("QCHARTS_PROG002D0001Q_name").get("value"),
			            x: -20 //center
			        },
			        subtitle: {
			            text: 'Query result',
			            x: -20
			        },
			        xAxis: {
			            categories: data.seriesCategories
			        },
			        yAxis: {
			            title: {
			                text: 'Query (value)'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: ' value'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: data.seriesData
			    });
				
			}, 
			function(error) {
				alert(error);
			}
	);			
}

function QCHARTS_PROG002D0001Q_area() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.queryDataAction.action', 
			getQCHARTS_PROG002D0001Q_Parameter('4'), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
					return;
				}
				
			    $('#QCHARTS_PROG002D0001Q_charts').highcharts({
			        chart: {
			            type: 'area'
			        },
			        title: {
			            text: dijit.byId("QCHARTS_PROG002D0001Q_name").get("value")
			        },
			        subtitle: {
			            text: 'Query result'
			        },
			        xAxis: {
			            categories: data.seriesCategories,
			            tickmarkPlacement: 'on',
			            title: {
			                enabled: false
			            }
			        },
			        yAxis: {
			            title: {
			                text: 'Query (value)'
			            },
			            labels: {
			                formatter: function () {
			                    return this.value;
			                }
			            }
			        },
			        tooltip: {
			            shared: true,
			            valueSuffix: ' value'
			        },
			        plotOptions: {
			            area: {
			                //stacking: 'normal',
			                lineColor: '#666666',
			                lineWidth: 1,
			                marker: {
			                    lineWidth: 1,
			                    lineColor: '#666666'
			                }
			            }
			        },
			        series: data.seriesData
			    });			   
				
			}, 
			function(error) {
				alert(error);
			}
	);				
}

/**
 * Mapper 下拉觸發,改變 MapperSet 下拉項目
 */
function QCHARTS_PROG002D0001Q_triggerChangeMapperSetItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'QCHARTS_PROG002D0001Q_dataQueryMapperOid',
			'QCHARTS_PROG002D0001Q_dataQueryMapperSetOid',
			'${basePath}/qcharts.commonGetDataQueryMapperSetItemsAction.action',
			{ 'fields.dataQueryMapperOid' : dijit.byId("QCHARTS_PROG002D0001Q_dataQueryMapperOid").get("value") }
	);	
}

function QCHARTS_PROG002D0001Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0001Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0001Q_fieldsId);
		return;
	}	
	QCHARTS_PROG002D0001Q_clear();
}

function QCHARTS_PROG002D0001Q_delete() {
	var oid = dijit.byId("QCHARTS_PROG002D0001Q_queryOid").get("value");
	if ( '' == oid || _gscore_please_select_id == oid ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('QCHARTS_PROG002D0001Q_queryOid_pleaseSelect')" escapeJavaScript="true"/>', function(){}, 'N');	
		return;
	}
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('QCHARTS_PROG002D0001Q_deleteConfirm')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/qcharts.queryDataDeleteAction.action', 
						{ 'fields.oid' : oid }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							if ('Y' != data.success) {		
								alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
								return;
							}	
							QCHARTS_PROG002D0001Q_clear();
						}, 
						function(error) {
							alert(error);
						}
				);		
			}, 
			(window.event ? window.event : null) 
	);	
		
}

function QCHARTS_PROG002D0001Q_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0001Q_fieldsId);
	dijit.byId("QCHARTS_PROG002D0001Q_queryOid").set("value", _gscore_please_select_id);
	dijit.byId("QCHARTS_PROG002D0001Q_dataSourceConfOid").set("value", _gscore_please_select_id);
	dijit.byId("QCHARTS_PROG002D0001Q_name").set("value", "");
	dijit.byId("QCHARTS_PROG002D0001Q_dataQueryMapperOid").set("value", _gscore_please_select_id);
	dijit.byId("QCHARTS_PROG002D0001Q_queryExpression").set("value", "");
	dojo.byId("QCHARTS_PROG002D0001Q_content").innerHTML = "";
	if ( $('#QCHARTS_PROG002D0001Q_charts').highcharts()!=null ) {
		$('#QCHARTS_PROG002D0001Q_charts').highcharts().destroy();
	}
	QCHARTS_PROG002D0001Q_reloadQueryHistoryItems();
}

function QCHARTS_PROG002D0001Q_reloadQueryHistoryItems() {
	clearSelectItems(true, 'QCHARTS_PROG002D0001Q_queryOid');
	xhrSendParameter(
			'${basePath}/qcharts.commonGetDataQueryItemsAction.action', 
			{ }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {			
					return;
				}	
				setSelectItems(data, 'QCHARTS_PROG002D0001Q_queryOid');
			}, 
			function(error) {
				alert(error);
			}
	);		
	
}

function QCHARTS_PROG002D0001Q_getQueryHistory() {
	var oid = dijit.byId("QCHARTS_PROG002D0001Q_queryOid").get("value");
	if ( '' == oid || _gscore_please_select_id == oid ) {
		QCHARTS_PROG002D0001Q_clear();
		return;
	}
	xhrSendParameter(
			'${basePath}/qcharts.commonGetQueryHistoryAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {		
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
					return;
				}	
				dijit.byId("QCHARTS_PROG002D0001Q_dataSourceConfOid").set("value", data.dataQuery.dataSourceConfOid);
				dijit.byId("QCHARTS_PROG002D0001Q_name").set("value", data.dataQuery.name);
				dijit.byId("QCHARTS_PROG002D0001Q_dataQueryMapperOid").set("value", data.dataQuery.mapperOid);
				dijit.byId("QCHARTS_PROG002D0001Q_queryExpression").set("value", data.dataQuery.queryExpression);
				
			}, 
			function(error) {
				alert(error);
			}
	);			
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
			<td width="100%" align="center" height="55%">								
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('QCHARTS_PROG002D0001Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:240px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="QCHARTS_PROG002D0001Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0001Q_query();
											}"><s:property value="getText('QCHARTS_PROG002D0001Q_btnQuery')"/></button>		
																		
									<button id="QCHARTS_PROG002D0001Q_btnPie" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPieIcon',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0001Q_pie();
											}"><s:property value="getText('QCHARTS_PROG002D0001Q_btnPie')"/></button>	

									<button id="QCHARTS_PROG002D0001Q_btnBar" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnBarIcon',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0001Q_bar();
											}"><s:property value="getText('QCHARTS_PROG002D0001Q_btnBar')"/></button>	
																						
									<button id="QCHARTS_PROG002D0001Q_btnLine" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnLineIcon',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0001Q_line();
											}"><s:property value="getText('QCHARTS_PROG002D0001Q_btnLine')"/></button>	
											
									<button id="QCHARTS_PROG002D0001Q_btnArea" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnAreaIcon',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0001Q_area();
											}"><s:property value="getText('QCHARTS_PROG002D0001Q_btnArea')"/></button>												
											
					    			<gs:button name="QCHARTS_PROG002D0001Q_save" id="QCHARTS_PROG002D0001Q_save" onClick="QCHARTS_PROG002D0001Q_save();"
					    				handleAs="json"
					    				sync="N"
					    				xhrUrl="${basePath}/qcharts.queryDataSaveAction.action"
					    				parameterType="postData"
					    				xhrParameter=" 
					    					{ 
					    						'fields.oid'					:	dijit.byId('QCHARTS_PROG002D0001Q_queryOid').get('value'),
												'fields.dataSourceConfOid'		:	dijit.byId('QCHARTS_PROG002D0001Q_dataSourceConfOid').get('value'),
												'fields.dataQueryMapperOid'		:	dijit.byId('QCHARTS_PROG002D0001Q_dataQueryMapperOid').get('value'),
												'fields.dataQueryMapperSetOid'	:	dijit.byId('QCHARTS_PROG002D0001Q_dataQueryMapperSetOid').get('value'),
												'fields.name'					:	dijit.byId('QCHARTS_PROG002D0001Q_name').get('value'),
												'fields.queryExpression'		:	dijit.byId('QCHARTS_PROG002D0001Q_queryExpression').get('value')
					    					} 
					    				"
					    				errorFn=""
					    				loadFn="QCHARTS_PROG002D0001Q_saveSuccess(data);" 
					    				programId="${programId}"
					    				label="${action.getText('QCHARTS_PROG002D0001Q_save')}" 
					    				showLabel="N"
					    				iconClass="dijitIconSave"></gs:button>    
					    							
					    			<gs:button name="QCHARTS_PROG002D0001Q_clear" id="QCHARTS_PROG002D0001Q_clear" onClick="QCHARTS_PROG002D0001Q_clear();" 
					    				label="${action.getText('QCHARTS_PROG002D0001Q_clear')}" 
					    				showLabel="N"
					    				iconClass="dijitIconClear"></gs:button>    												
																						
					    			<gs:button name="QCHARTS_PROG002D0001Q_delete" id="QCHARTS_PROG002D0001Q_delete" onClick="QCHARTS_PROG002D0001Q_delete();" 
					    				label="${action.getText('QCHARTS_PROG002D0001Q_delete')}" 
					    				showLabel="N"
					    				iconClass="dijitIconDelete"></gs:button>      
		    						
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0001Q_dataSourceConfOid"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0001Q_dataQueryMapperOid"></gs:inputfieldNoticeMsgLabel>
		    						<!-- 
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0001Q_dataQueryMapperSetOid"></gs:inputfieldNoticeMsgLabel>
		    						-->
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0001Q_name"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0001Q_queryExpression"></gs:inputfieldNoticeMsgLabel>
		    																							
								</td>
							</tr>
							<tr>
								<td width="100%" align="left" height="25px">
								
									<s:property value="getText('QCHARTS_PROG002D0001Q_queryOid')"/>
									<gs:select name="QCHARTS_PROG002D0001Q_queryOid" dataSource="queryHistoryMap" id="QCHARTS_PROG002D0001Q_queryOid" onChange="QCHARTS_PROG002D0001Q_getQueryHistory();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_queryOid'">
    									Select history data.
									</div>  									
									&nbsp;		
								
									<s:property value="getText('QCHARTS_PROG002D0001Q_dataSourceConfOid')"/>
									<gs:select name="QCHARTS_PROG002D0001Q_dataSourceConfOid" dataSource="confMap" id="QCHARTS_PROG002D0001Q_dataSourceConfOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_dataSourceConfOid'">
    									Select datasource.
									</div> 									
						    		&nbsp;		
						    									
									<s:property value="getText('QCHARTS_PROG002D0001Q_name')"/>
									<gs:textBox name="QCHARTS_PROG002D0001Q_name" id="QCHARTS_PROG002D0001Q_name" value="" width="200" maxlength="100"></gs:textBox>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_name'">
    									Input name.
									</div> 	
																		
								</td>
							</tr>
							<tr>
								<td width="100%" align="left" height="25px">
															
									<s:property value="getText('QCHARTS_PROG002D0001Q_dataQueryMapperOid')"/>
									<gs:select name="QCHARTS_PROG002D0001Q_dataQueryMapperOid" dataSource="mapperMap" id="QCHARTS_PROG002D0001Q_dataQueryMapperOid" onChange="QCHARTS_PROG002D0001Q_triggerChangeMapperSetItems();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_dataQueryMapperOid'">
    									Select mapper.
									</div> 
																		
									&nbsp;
									<s:property value="getText('QCHARTS_PROG002D0001Q_dataQueryMapperSetOid')"/>&nbsp;
									<s:property value="getText('QCHARTS_PROG002D0001Q_dataQueryMapperSetOid_forPieChart')"/>
									<gs:select name="QCHARTS_PROG002D0001Q_dataQueryMapperSetOid" dataSource="mapperSetMap" id="QCHARTS_PROG002D0001Q_dataQueryMapperSetOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_dataQueryMapperSetOid'">
    									Select mapper item. ( only need when PIE-chart )
									</div> 
																		
								</td>
							</tr>							
							<tr>
								<td width="100%" align="left" height="125px">
								
									<gs:label text="${action.getText('QCHARTS_PROG002D0001Q_queryExpression')}" id="QCHARTS_PROG002D0001Q_queryExpression"></gs:label>
									<br/>
									<textarea id="QCHARTS_PROG002D0001Q_queryExpression" name="QCHARTS_PROG002D0001Q_queryExpression" data-dojo-type="dijit/form/Textarea" rows="6" cols="120" style="width:960px;height:90px;max-height:100px"></textarea>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0001Q_queryExpression'">
    									SQL expression.<BR/>
    									Example:<BR/>
    									
    									<hr size="1">
    									<font size='2'>
										select <BR/>
										&nbsp;&nbsp;ID, NAME, PERFORMANCE <BR/> 										
										from SALES where BRANCH = '01'
										</font>
										<hr size="1">
										
										<BR/> 									
    									Reference: <a href="https://en.wikipedia.org/wiki/SQL">SQL</a>
									</div> 	
																		
								</td>
							</tr>
														
						</table>							
					
					</div>
				</div>
			</td>
		</tr>
	</table>					
	
	<div id="QCHARTS_PROG002D0001Q_content"></div>
	
	<div id="QCHARTS_PROG002D0001Q_charts" style="min-width: 1024px; height: 100%; max-width: 2048px; margin: 0 auto"></div>
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	