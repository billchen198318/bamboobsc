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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="common-include.jsp"></jsp:include>


<!-- Highcharts -->
<script src="<%=basePath%>/highcharts/js/highcharts.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-3d.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-more.js"></script>
<script src="<%=basePath%>/highcharts/js/modules/heatmap.js"></script>
<script src="<%=basePath%>/highcharts/js/modules/exporting.js"></script>	


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
	
	$("#card_content").html('');
	
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
				alert( data.message );
				return;
			}			
			showChartForPerspectives(data);
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
		chartData.push([data.rootVision.perspectives[p].name, data.rootVision.perspectives[p].score]);
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
	<div id="perspectives_container">

<div class="alert alert-info" role="alert">
  <h4 class="alert-heading">Measure-data begin/end date description!</h4>
  <p>The Frequency is <b>Year / Half of year / Quarter</b> , begin/end date capture <b>yyyy</b> for query.</p>
  <p>The Frequency is <b>Month / Week</b> , begin/end date capture <b>yyyy-MM</b> for query.</p>
  <p>The Frequency is <b>Day</b> , begin/end date capture <b>yyyy-MM-dd</b> for query.</p>    
</div>	
	
	</div>
</div>

<br/>

<div data-role="content">
	<div id="objectives_container">
	</div>
</div>

<br/>

<div data-role="content">
	<div id="kpi_container">
	</div>	
</div>

<br/>

<div data-role="content">
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