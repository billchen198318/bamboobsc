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

</style>

<script type="text/javascript">

function BSC_PROG005D0004Q_ownerChange() {
	if ( _gscore_please_select_id == dijit.byId('BSC_PROG005D0004Q_owner').get('value') ) {
		BSC_PROG005D0004Q_clear();
		return;
	}	
}

function BSC_PROG005D0004Q_query(type) {
	xhrSendParameter(
			'${basePath}/bsc.degreeFeedbackProjectQueryScoreAction.action', 
			{ 
				'fields.projectOid'	:	'${fields.oid}',
				'fields.ownerOid'	:	dijit.byId('BSC_PROG005D0004Q_owner').get('value')
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ( 'Y' != data.success ) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ('bar' == type) {
					BSC_PROG005D0004Q_bar(data.project);
				} else {
					BSC_PROG005D0004Q_pie(data.project);
				}				
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG005D0004Q_bar(project) {
	if (project == null || project.items == null || project.items.length<1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('BSC_PROG005D0004Q_msg_01')"/>', function(){}, 'Y');
		return;
	}
	var seriesCategories = [];
	var seriesData = [];
	var avgScores = [];
	var levelText = BSC_PROG005D0004Q_getLevelTitle(project.levels);
	for (var i=0; i<project.items.length; i++) {
		var item = project.items[i];
		avgScores.push( item.avgScore );
		seriesCategories.push( item.name );
	}
	seriesData.push({
		"name"	:	'Avg Score',
		"data"	:	avgScores
	});
	
    $('#BSC_PROG005D0004Q_charts').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: project.year + ' - ' + project.name + ' / ' + project.employee.empId + ' - ' + project.employee.fullName
        },
        subtitle: {
            text: levelText
        },
        xAxis: {
            categories: seriesCategories,
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Score',
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
        series: seriesData
	});		
}
function BSC_PROG005D0004Q_pie(project) {
	if (project == null || project.items == null || project.items.length<1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('BSC_PROG005D0004Q_msg_01')"/>', function(){}, 'Y');
		return;
	}	
	var levelText = BSC_PROG005D0004Q_getLevelTitle(project.levels);
	var pieData = [];
	for (var i=0; i<project.items.length; i++) {
		var item = project.items[i];
		pieData.push( [item.name, item.avgScore] );		
	}
	
	$(function () {
	    $('#BSC_PROG005D0004Q_charts').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45
	            }
	        },
	        title: {
	            text: project.year + ' - ' + project.name + ' / ' + project.employee.empId + ' - ' + project.employee.fullName
	        },
	        subtitle: {
	            text: levelText
	        },
	        plotOptions: {
	            pie: {
	                innerSize: 100,
	                depth: 45
	            }
	        },
	        series: [{
	            name: 'Avg Score',
	            data: pieData
	        }]
	    });
	});	
}

function BSC_PROG005D0004Q_getLevelTitle(levels) {
	var levelText = '';
	for (var i=0; i<levels.length; i++) {
		var level = levels[i];
		levelText += level.value + '-' + level.name;
		if ( (i+1)<levels.length ) {
			levelText += ', ';
		}
	}	
	return levelText;
}

function BSC_PROG005D0004Q_clear() {
	${programId}_DlgShow('${fields.oid}');
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
		cancelJsMethod="${programId}_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_DlgShow('${fields.oid}');" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%">
		<tr>
			<td align="center" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);"><b><font color="#000000"><s:property value="project.year"/> - <s:property value="project.name"/></font></b></td>
		</tr>		
		<tr>
			<td align="left" bgcolor="#ffffff">
				<gs:label text="${action.getText('BSC_PROG005D0004Q_owner')}" id="BSC_PROG005D0004Q_owner" requiredFlag="Y"></gs:label>
				&nbsp;
				<gs:select name="BSC_PROG005D0004Q_owner" dataSource="ownerMap" id="BSC_PROG005D0004Q_owner" value="fields.employeeOid" onChange="BSC_PROG005D0004Q_ownerChange();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0004Q_owner'">
    				Select project's owner.
				</div> 
				
				<button name="BSC_PROG005D0004Q_btnQueryBar" id="BSC_PROG005D0004Q_btnQueryBar" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'btnBarIcon',
						onClick:function(){ 
							BSC_PROG005D0004Q_query('bar');
						}
					"><s:property value="getText('BSC_PROG005D0004Q_btnQueryBar')"/></button>
					
				<button name="BSC_PROG005D0004Q_btnQueryPie" id="BSC_PROG005D0004Q_btnQueryPie" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'btnPieIcon',
						onClick:function(){ 
							BSC_PROG005D0004Q_query('pie');
						}
					"><s:property value="getText('BSC_PROG005D0004Q_btnQueryPie')"/></button>
										
				<button name="BSC_PROG005D0004Q_btnClear" id="BSC_PROG005D0004Q_btnClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0004Q_clear();
						}
					"><s:property value="getText('BSC_PROG005D0004Q_btnClear')"/></button>									
												
			</td>
		</tr>
	</table>		
	
	<br/>
	
	<div id="BSC_PROG005D0004Q_charts" style="min-width: 800px; height: 100%; max-width: 1024px; margin: 0 auto"></div>
	
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
			
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
