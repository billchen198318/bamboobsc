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

.btnDiagramIcon {
  	background-image: url(./icons/x-dia-diagram.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

#BSC_PROG006D0002Q_content { 
	width:100%; 
	height:100%;
	page-break-after:always 
}

</style>

<script type="text/javascript">

var BSC_PROG006D0002Q_fieldsId = new Object();
BSC_PROG006D0002Q_fieldsId['pdcaOid']	= 'BSC_PROG006D0002Q_pdcaOid';

function BSC_PROG006D0002Q_query(type) {
	if ('EXCEL' != type) { 
		document.getElementById( 'BSC_PROG006D0002Q_projectRelated_orgChart' ).innerHTML = '';
		document.getElementById( 'BSC_PROG006D0002Q_content' ).innerHTML = '';
	}
	var actionUrl = 'bsc.pdcaReportContentQuery.action';
	if ('EXCEL' == type) {
		actionUrl = 'bsc.pdcaReportExcelQuery.action';
	}
	if ('PROJECT_RELATED' == type) {
		actionUrl = 'bsc.pdcaProjectRelatedQuery.action';
	}
	setFieldsBackgroundDefault(BSC_PROG006D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG006D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/' + actionUrl, 
			{ 
				'fields.pdcaOid' 		: dijit.byId('BSC_PROG006D0002Q_pdcaOid').get('value'),
				'fields.showBscReport'	: ( dijit.byId('BSC_PROG006D0002Q_bscReportShow').checked ? 'true' : 'false' )
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG006D0002Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG006D0002Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ('EXCEL' == type) {
					openCommonLoadUpload( 'download', data.uploadOid, { } );
				} else if ('HTML' == type) {
					dojo.byId('BSC_PROG006D0002Q_content').innerHTML = data.body;
					BSC_PROG006D0002Q_chart(data);					
				} else {
					BSC_PROG006D0002Q_OrgChart(data);
				}
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG006D0002Q_chart(data) {
	
	var pdcaItemData = [];
	BSC_PROG006D0002Q_chartFillItems( data.pdca.itemPlan, pdcaItemData );
	BSC_PROG006D0002Q_chartFillItems( data.pdca.itemDo, pdcaItemData );
	BSC_PROG006D0002Q_chartFillItems( data.pdca.itemCheck, pdcaItemData );
	BSC_PROG006D0002Q_chartFillItems( data.pdca.itemAction, pdcaItemData );
	
	var series = [];
	$.each(pdcaItemData.reverse(), function(i, application) {
	    var item = {
	        name: application.name,
	        data: [],
	        pointStart: Date.UTC(2015, 0, 1),
	        pointInterval: 3 * 24 * 3600 * 1000
	    };
	    $.each(application.intervals, function(j, interval) {
	        item.data.push({
	            x: interval.from,
	            y: i,
	            label: interval.label,
	            from: interval.from,
	            to: interval.to,
	            color: interval.color
	        }, {
	            x: interval.to,
	            y: i,
	            from: interval.from,
	            to: interval.to,
	            color: interval.color
	        });
	        
	        // add a null value between intervals
	        if (application.intervals[j + 1]) {
	            item.data.push(
	                [(interval.to + application.intervals[j + 1].from) / 2, null]
	            );
	        }

	    });

	    series.push(item);
	});

	// create the chart
	var chart = new Highcharts.Chart({

	    chart: {
	        renderTo: 'BSC_PROG006D0002Q_container' // BSC_PROG006D0002Q_container	div-id in pdca-report-body.ftl
	    },
	    
	    title: {
	        text: data.pdca.title
	    },

	    xAxis: {
	        //startOfWeek: 1,
	        type: 'datetime',
	        labels: {
	            formatter: function () {
	                return Highcharts.dateFormat('%e %b', this.value);
	            }
	        }
	    },

	    yAxis: {
	        tickInterval: 1,
	        labels: {
	            formatter: function() {
	                if (pdcaItemData[this.value]) {
	                    return pdcaItemData[this.value].name;
	                }
	            }
	        },
	        startOnTick: false,
	        endOnTick: false,
	        title: {
	            text: 'PDCA items'
	        },
	            minPadding: 0.2,
	                maxPadding: 0.2
	    },

	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        formatter: function() {
	            return '<b>'+ pdcaItemData[this.y].name + '</b><br/>' +
	                Highcharts.dateFormat('%Y-%m-%d', this.point.options.from)  +
	                ' - ' + Highcharts.dateFormat('%Y-%m-%d', this.point.options.to); 
	        }
	    },   
	    // We can define the color chart to our lines
	    //colors: ['#B572A7'],
	    plotOptions: {
	        series: {
	            // We can specify a single color of a line
	            //lineColor: '#303030'
	            //lineColor: function() {
	            //        return this.point.options.color;
	            //        return '#303030';
				//},
	        },
	        line: {
	            lineWidth: 9,
	            // We can specify a single color of a line
	            //color: '#B572A7',
	            // We can't make function (){ ... } to get color for each     point.option or juste return a single color !
	            //color: function() {
	            //        return this.point.options.color;
	            //        return '#B572A7';
				//},
	            marker: {
	                enabled: false
	            },
	            dataLabels: {
	                enabled: true,
	                align: 'left',
	                formatter: function() {
	                    return this.point.options && this.point.options.label;
	                }
	            }
	        }
	    },
	    series: series

	});
	
}
function BSC_PROG006D0002Q_chartFillItems(items, pdcaItemData) {
	for (var i=0; i<items.length; i++) {
		var item = items[i];
		var d1 = item.startDate;
		var d2 = item.endDate;
		pdcaItemData.push({
			name : item.type + ' - ' + item.title,
			intervals : [{
				from	:	Date.UTC( parseInt( d1.substring(0, 4) ), parseInt( d1.substring(4, 6) ) -1, parseInt( d1.substring(6, 8) ) ),
				to		:	Date.UTC( parseInt( d2.substring(0, 4) ), parseInt( d2.substring(4, 6) ) -1, parseInt( d2.substring(6, 8) ) ),
				label	:	item.type + ' - ' + item.title
			}]
		});
	}	
}

function BSC_PROG006D0002Q_OrgChart(data) {
	
	$('#BSC_PROG006D0002Q_projectRelated_orgChart').orgchart({
		'data' : data.projectRelated.orgData,
		'depth': 99,
		'nodeTitle': 'name',
		'nodeContent': 'title'
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
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:80px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="40px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG006D0002Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG006D0002Q_query('HTML');
											}">Query</button>
										
									<button id="BSC_PROG006D0002Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG006D0002Q_query('EXCEL');																			  
											}">Excel</button>											
									
									<button id="BSC_PROG006D0002Q_btnProjectRelated" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnDiagramIcon',
											showLabel:false,
											onClick:function(){  
												BSC_PROG006D0002Q_query('PROJECT_RELATED');
											}">Query project related diagram only</button>	
											
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0002Q_pdcaOid"></gs:inputfieldNoticeMsgLabel>
																			
											
								</td>
							</tr>																						
							<tr>
								<td valign="top" align="left" height="25px">
								
									<gs:label text="PDCA Project" id="BSC_PROG006D0002Q_pdcaOid" ></gs:label>
									<gs:select name="BSC_PROG006D0002Q_pdcaOid" dataSource="pdcaMap" id="BSC_PROG006D0002Q_pdcaOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0002Q_pdcaOid'">
					    				Select PDCA project.
									</div>  									
						    		&nbsp;
						    		
						    		<gs:label text="BSC Report" id="BSC_PROG006D0002Q_bscReportShow"></gs:label>
						    		<input id="BSC_PROG006D0002Q_bscReportShow" name="BSC_PROG006D0002Q_bscReportShow" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0002Q_bscReportShow'">
					    				Show with BSC report.
									</div>					
																	
								</td>
							</tr>																				
						</table>
			    			
		    		</div>		    		
		    	</div>	
		    	
			    			
			</td>
		</tr>
	</table>
	
	
	<div id="BSC_PROG006D0002Q_projectRelated_orgChart"></div>
	<div id="BSC_PROG006D0002Q_content"></div>
	
		
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
