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

</style>

<script type="text/javascript">

var BSC_PROG007D0002Q_fieldsId = new Object();
BSC_PROG007D0002Q_fieldsId["measureFreq_frequency"] 					= "BSC_PROG007D0002Q_measureFreq_frequency";
BSC_PROG007D0002Q_fieldsId["measureFreq_startYearDate"] 				= "BSC_PROG007D0002Q_measureFreq_startYearDate";
BSC_PROG007D0002Q_fieldsId["measureFreq_endYearDate"] 					= "BSC_PROG007D0002Q_measureFreq_endYearDate";
BSC_PROG007D0002Q_fieldsId["measureFreq_startDate"] 					= "BSC_PROG007D0002Q_measureFreq_startDate";
BSC_PROG007D0002Q_fieldsId["measureFreq_endDate"] 						= "BSC_PROG007D0002Q_measureFreq_endDate";
BSC_PROG007D0002Q_fieldsId["measureFreq_dataFor"] 						= "BSC_PROG007D0002Q_measureFreq_dataFor";
BSC_PROG007D0002Q_fieldsId["measureFreq_measureDataOrganizationOid"] 	= "BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid";
BSC_PROG007D0002Q_fieldsId["measureFreq_measureDataEmployeeOid"] 		= "BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid";

function BSC_PROG007D0002Q_query() {
	
	//bsc.tsaQueryForecastAction.action
	
}


//------------------------------------------------------------------------------
//measure options settings function
//------------------------------------------------------------------------------
function BSC_PROG007D0002Q_measureFreq_setDataForValue() {
	dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG007D0002Q_measureFreq_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG007D0002Q_measureFreq_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG007D0002Q_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG007D0002Q_measureFreq_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG007D0002Q_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG007D0002Q_measureFreq_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG007D0002Q_measureFreq_frequency").get("value");
	dijit.byId("BSC_PROG007D0002Q_measureFreq_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_measureFreq_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_measureFreq_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0002Q_measureFreq_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG007D0002Q_measureFreq_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0002Q_measureFreq_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG007D0002Q_measureFreq_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0002Q_measureFreq_endYearDate").set("readOnly", false);				
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
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:145px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG007D0002Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG007D0002Q_query();
											}"><s:property value="Query"/></button>											
											
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_visionOid"></gs:inputfieldNoticeMsgLabel>		
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_startYearDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_endYearDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_startDate"></gs:inputfieldNoticeMsgLabel>
									<!-- 
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_endDate"></gs:inputfieldNoticeMsgLabel>
									-->
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0002Q_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>											
							</tr>	

							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									Param
									<gs:select name="BSC_PROG007D0002Q_param" dataSource="paramMap" id="BSC_PROG007D0002Q_param"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_param'">
					    				Select param item.
									</div>  									
						    		&nbsp;										
								
									Vision
									<gs:select name="BSC_PROG007D0002Q_visionOid" dataSource="visionMap" id="BSC_PROG007D0002Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									Frequency
									<gs:select name="BSC_PROG007D0002Q_measureFreq_frequency" dataSource="frequencyMap" id="BSC_PROG007D0002Q_measureFreq_frequency" value="6" onChange="BSC_PROG007D0002Q_measureFreq_setFrequencyValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_frequency'">
					    				Select frequency.
									</div> 						
												
								</td>											
							</tr>	
												
							<tr valign="top">
								<td width="100%" align="left" height="25px">
											
							    	Start year
							    	<input id="BSC_PROG007D0002Q_measureFreq_startYearDate" name="BSC_PROG007D0002Q_measureFreq_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_startYearDate'">
					    				Select start year.
									</div>							    		
							    	&nbsp;	
							    	End year
							    	<input id="BSC_PROG007D0002Q_measureFreq_endYearDate" name="BSC_PROG007D0002Q_measureFreq_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
							    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_endYearDate'">
					    				Select end year.
									</div>							    									    	
							    	&nbsp;&nbsp;		
									Start date
									<input id="BSC_PROG007D0002Q_measureFreq_startDate" type="text" name="BSC_PROG007D0002Q_measureFreq_startDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_startDate'">
					    				Select start date.
									</div>											
									&nbsp;						
									End date
									<input id="BSC_PROG007D0002Q_measureFreq_endDate" type="text" name="BSC_PROG007D0002Q_measureFreq_endDate" data-dojo-type="dijit.form.DateTextBox"
										maxlength="10" 
										constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_endDate'">
					    				Select end date.
									</div>							    			
							    </td>	
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">							
									Data type
									<gs:select name="BSC_PROG007D0002Q_measureFreq_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization / department\", \"employee\":\"Personal / employee\" }" id="BSC_PROG007D0002Q_measureFreq_dataFor" onChange="BSC_PROG007D0002Q_measureFreq_setDataForValue();" width="140"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_dataFor'">
					    				Select measure data type.
									</div>										
									&nbsp;&nbsp;
									Organization / department
									<gs:select name="BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid" onChange="BSC_PROG007D0002Q_measureFreq_setMeasureDataOrgaValue();" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_measureDataOrganizationOid'">
					    				Select measure data organization/department.
									</div>									
									&nbsp;&nbsp;
									Personal / employee
									<gs:select name="BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid" onChange="BSC_PROG007D0002Q_measureFreq_setMeasureDataEmplValue();" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0002Q_measureFreq_measureDataEmployeeOid'">
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
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
