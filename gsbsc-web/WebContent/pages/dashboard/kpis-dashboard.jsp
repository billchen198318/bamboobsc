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
				'fields.frequency'					:	dijit.byId("BSC_PROG003D0006Q_frequency").get("value")
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0006Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG003D0006Q_showTables( data );
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
	t += '<table width="1100px" cellspacing="1" cellpadding="1" style="background-color:#B1B1B1" >';
	var c = 0;
	for (var n in data.perspectiveItems) {
		for ( var o in data.perspectiveItems[n].objectives ) {
			var objective = data.perspectiveItems[n].objectives[o];
			for ( var k in objective.kpis ) {
				var kpi = objective.kpis[k];
				if ( c == 0 ) { // first line label
					t += '<tr>';
					t += '<td bgcolor="#EEEEEE" align="left" width="320px"><b>KPI</b></td>';
					t += '<td bgcolor="#EEEEEE" align="left"><b>Target</b></td>';
					t += '<td bgcolor="#EEEEEE" align="left"><b>Min</b></td>';	
					t += '<td bgcolor="#EEEEEE" align="left"><b>Score</b></td>';
					for ( var r in kpi.dateRangeScores ) {
						t += '<td bgcolor="#EEEEEE" align="left"><b>' + kpi.dateRangeScores[r].date + '</b></td>';					
					}
					t += '</tr>';
				}
				t += '<tr>';
				t += '<td bgcolor="#ffffff" align="left">' + kpi.name + '</td>';
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

function BSC_PROG003D0006Q_generateExport(type) {
	
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
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:120px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" bgcolor="#d7e3ed">	
								
									<button id="BSC_PROG003D0006Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0006Q_query();
											}">Query</button>		
											
									<button id="BSC_PROG003D0006Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0006Q_generateExport('EXCEL');																			  
											}">Excel</button>												
											
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									Vision: 
									<gs:select name="BSC_PROG003D0006Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0006Q_visionOid"></gs:select>
						    		&nbsp;		    			
					    																	
									Measure data frequency:
									<gs:select name="BSC_PROG003D0006Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0006Q_frequency" value="6" width="140" onChange="BSC_PROG003D0006Q_setFrequencyValue();"></gs:select>
									&nbsp;
																	
								</td>											
							</tr>	
							<tr valign="top">
								<td width="100%" align="left" height="25px">
								
							    	Measure data start year:
							    	<input id="BSC_PROG003D0006Q_startYearDate" name="BSC_PROG003D0006Q_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />		
							    	&nbsp;
							    	end year:
							    	<input id="BSC_PROG003D0006Q_endYearDate" name="BSC_PROG003D0006Q_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
							    									    							
							    	&nbsp;&nbsp;		
									Measure data start date:
									<input id="BSC_PROG003D0006Q_startDate" type="text" name="BSC_PROG003D0006Q_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />	
									&nbsp;						
									end date:
									<input id="BSC_PROG003D0006Q_endDate" type="text" name="BSC_PROG003D0006Q_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
							    										    							
							    										
								</td>
							</tr>															
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									Measure data for:
									<gs:select name="BSC_PROG003D0006Q_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization\", \"employee\":\"Employee\" }" id="BSC_PROG003D0006Q_dataFor" onChange="BSC_PROG003D0006Q_setDataForValue();" width="140"></gs:select>
									&nbsp;&nbsp;
									organization:
									<gs:select name="BSC_PROG003D0006Q_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG003D0006Q_measureDataOrganizationOid" onChange="BSC_PROG003D0006Q_setMeasureDataOrgaValue();" readonly="Y"></gs:select>
									&nbsp;&nbsp;
									employee:
									<gs:select name="BSC_PROG003D0006Q_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG003D0006Q_measureDataEmployeeOid" onChange="BSC_PROG003D0006Q_setMeasureDataEmplValue();" readonly="Y"></gs:select>
									
								</td>
							</tr>								
							
														
						</table>										
					
					</div>
				</div>
			</td>
		</tr>
	</table>	
	
	<div id="BSC_PROG003D0006Q_table" style="width:1100px"></div>		
	
	<form name="BSC_PROG003D0006Q_form" id="BSC_PROG003D0006Q_form" action="."></form>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>

	