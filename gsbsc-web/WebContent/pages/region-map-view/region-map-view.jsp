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

.div-info { 
    max-width: 1024px; 
}

</style>

<script type="text/javascript">

//------------------------------------------------------------------------------
//map
//------------------------------------------------------------------------------
var BSC_PROG001D0006Q_map = null;
var BSC_PROG001D0006Q_infowindow = null;
function BSC_PROG001D0006Q_map_initialize() {
		
	<s:iterator value="organizations" status="st" >
	
	var latLng_${orgId} = new google.maps.LatLng(${lat}, ${lng});
	
	<s:if test=" #st.index==0 ">
	
	var mapOptions = {
			zoom: 2,
			center: latLng_${orgId},
			mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
	BSC_PROG001D0006Q_map = new google.maps.Map(
			document.getElementById('BSC_PROG001D0006Q_map_canvas'), 
			mapOptions);	
	
	</s:if>
		
	
	var marker_${orgId} = new google.maps.Marker({
		position: latLng_${orgId},
	    map: BSC_PROG001D0006Q_map,
	    title: "<s:property value="name" escapeJavaScript="true" />",
	    draggable: false
	});	
	
	google.maps.event.addListener(marker_${orgId}, 'click', function(event) {	
		
		if (BSC_PROG001D0006Q_infowindow) {
			BSC_PROG001D0006Q_infowindow.close();
		}
		BSC_PROG001D0006Q_infowindow = new google.maps.InfoWindow({
			content: '<font size="2"><b><s:property value="name" escapeJavaScript="true" /></b></font><hr size="1"><iframe frameborder="1" width="480px" height="500px" src="<%=basePath%>/bsc.regionMapViewGetInfowindowContent.action?fields.orgId=${orgId}&fields.year=' + dijit.byId("BSC_PROG001D0006Q_yearHorizontalSlider").get("value") + '" ></iframe>',
			maxWidth: 1024
		});
		BSC_PROG001D0006Q_infowindow.open(BSC_PROG001D0006Q_map, marker_${orgId});
		
		dojo.byId("BSC_PROG001D0006Q_contentOrganizationInfo").innerHTML = '';
		dojo.byId("BSC_PROG001D0006Q_contentRelationKpis").innerHTML = '';
		xhrSendParameter(
				'${basePath}/bsc.commonGetOrganizationDataAction.action', 
				{ 'fields.oid' : '${oid}' }, 
				'json', 
				_gscore_dojo_ajax_timeout,
				_gscore_dojo_ajax_sync, 
				true, 
				function(data) {
					if ('Y' != data.success) {
						alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
						return;
					}
					BSC_PROG001D0006Q_showOrganizationInfo(data.organization);	
					
					xhrSendParameter(
							'${basePath}/bsc.regionMapRelationKpisAction.action', 
							{ 
								'fields.year' 	: dijit.byId("BSC_PROG001D0006Q_yearHorizontalSlider").get("value"),
								'fields.orgId' 	: data.organization.orgId
							}, 
							'json', 
							_gscore_dojo_ajax_timeout,
							_gscore_dojo_ajax_sync, 
							true, 
							function(kData) {
								if ('Y' != kData.success) {
									alertDialog(_getApplicationProgramNameById('${programId}'), kData.message, function(){}, kData.success);
									return;
								}
								BSC_PROG001D0006Q_showRelationKpis( kData.relationKpis );
								
							}, 
							function(error) {
								alert(error);
							}
					);					
					
				}, 
				function(error) {
					alert(error);
				}
		);		
		
	});
	
	<s:if test=" #st.index==0 ">
	new google.maps.event.trigger( marker_${orgId}, 'click' );
	</s:if>
	
	</s:iterator>
	
}

function BSC_PROG001D0006Q_showOrganizationInfo(organization) {
	
	var content = '';
	content += '<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#F2F2F2" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="#F2F2F2" align="center" ><b>Organization/Department</b></td>';
	content += '</tr>';	
	content += '<tr>';
	content += '<td bgcolor="#F8F8F8" align="right" >Id:</td>';
	content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + organization.orgId + '</font></td>';
	content += '</tr>';
	content += '<tr>';
	content += '<td bgcolor="#F8F8F8" align="right" >Name:</td>';
	content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + organization.name + '</font></td>';
	content += '</tr>';	
	if (organization.address!=null && ""!=organization.address) {
		content += '<tr>';
		content += '<td bgcolor="#F8F8F8" align="right" >Address:</td>';
		content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + organization.address + '</font></td>';
		content += '</tr>';		
	}	
	if (organization.description!=null && ""!=organization.description) {
		content += '<tr>';
		content += '<td bgcolor="#F8F8F8" align="right" >Description:</td>';
		content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + organization.description + '</font></td>';
		content += '</tr>';							
	}
	content += '</table>';
	dojo.byId("BSC_PROG001D0006Q_contentOrganizationInfo").innerHTML = content;
	
}

function BSC_PROG001D0006Q_showRelationKpis(kpis) {
	
	var content = '';
	content += '<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#F2F2F2" >';
	content += '<tr>';
	content += '<td colspan="2" bgcolor="#F2F2F2" align="center" ><b>KPIs</b></td>';
	content += '</tr>';	
	
	for (var k in kpis) {
		
		/*
		content += '<tr>';
		content += '<td bgcolor="#F2F2F2" align="right" >Id</td>';
		content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + kpis[k].id + '</font></td>';
		content += '</tr>';
		content += '<tr>';
		content += '<td bgcolor="#F2F2F2" align="right" >Name</td>';
		content += '<td bgcolor="#ffffff" align="left" ><font size="2">' + kpis[k].name + '</font></td>';
		content += '</tr>';					
		content += '<tr>';
		content += '<td bgcolor="#F2F2F2" align="right" >Score</td>';
		content += '<td bgcolor="#ffffff" align="center" ><div id="BSC_PROG001D0006Q_charts_' + kpis[k].id + '" ></td>';
		content += '</tr>';			
		*/

		content += '<tr>';
		content += '<td colspan="2" bgcolor="#ffffff" align="center" ><div id="BSC_PROG001D0006Q_charts_' + kpis[k].id + '" style="width:250px;height:200px;" ></td>';
		content += '</tr>';					
		
	}
	
	content += '</table>';
	//dojo.byId("BSC_PROG001D0006Q_contentRelationKpis").innerHTML = content;
	dojo.html.set(dojo.byId("BSC_PROG001D0006Q_contentRelationKpis"), content, {extractContent: true, parseContent: true});
	
	for (var k in kpis) {
		var target = kpis[k].target;
		var score = kpis[k].score;
		var id = 'BSC_PROG001D0006Q_charts_' + kpis[k].id;
		
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
		
		var labelString = kpis[k].name + " ( " + score + " ) ";
		
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
	
	<table border="0" width="100%" cellpadding="0" cellspacing="0" >
		<tr valign="top">
			<td width="75%" height="100%">
			
				<div dojoType="dijit.layout.BorderContainer" style="height:900px">
					<div dojoType="dijit.layout.ContentPane" region="center" style="overflow:hidden">
						<div id="BSC_PROG001D0006Q_map_canvas" style="height:100%; width:100%"></div>
					</div>
				</div>    						
				
			</td>			
			<td width="25%" height="100%">
				
				<table border="0" width="100%" cellpadding="1" cellspacing="1" >
					<tr>
						<td align="center">
						
							<div id="BSC_PROG001D0006Q_yearHorizontalSlider"
							    style="width:200px;"
							    name="horizontalSlider"
							    data-dojo-type="dijit/form/HorizontalSlider"
							    data-dojo-props="value:${maximum},
							    minimum:${minimum},
							    maximum:${maximum},
							    discreteValues:${discreteValues},
							    intermediateChanges:false,
							    showButtons:false,
							    onChange:function(value) {
							    	
							    }
							    ">
							    <ol data-dojo-type="dijit/form/HorizontalRuleLabels" container="topDecoration"
							        style="height:1.5em;font-size:75%;color:gray;">
							        <s:if test="null != yearRangeList">
							        <s:iterator value="yearRangeList" status="st">
							        <li><s:property value="yearRangeList.get(#st.index)"/></li>
							        </s:iterator>
							        </s:if>
							    </ol>
							</div>  
															
						</td>
					</tr>
				</table>
				
				<br/>
				
				<div id="BSC_PROG001D0006Q_contentOrganizationInfo" style="height:100%; width:100%"></div>
				<br/>
				<div id="BSC_PROG001D0006Q_contentRelationKpis" style="height:100%; width:100%"></div>
				
				
			</td>
		</tr>
	</table>
	
<script type="text/javascript">
${programId}_page_message();
setTimeout("BSC_PROG001D0006Q_map_initialize();", 500);
</script>	
</body>
</html>
	