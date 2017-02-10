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

var BSC_PROG007D0001E_fieldsId = new Object();
BSC_PROG007D0001E_fieldsId['name'] 				= 'BSC_PROG007D0001E_name';
BSC_PROG007D0001E_fieldsId['integration'] 		= 'BSC_PROG007D0001E_integration';
BSC_PROG007D0001E_fieldsId['forecast'] 			= 'BSC_PROG007D0001E_forecast';
BSC_PROG007D0001E_fieldsId['coefficient1'] 		= 'BSC_PROG007D0001E_coefficient1';
BSC_PROG007D0001E_fieldsId['coefficient2'] 		= 'BSC_PROG007D0001E_coefficient2';
BSC_PROG007D0001E_fieldsId['coefficient3'] 		= 'BSC_PROG007D0001E_coefficient3';
BSC_PROG007D0001E_fieldsId["measureFreq_frequency"] 					= "BSC_PROG007D0001E_measureFreq_frequency";
BSC_PROG007D0001E_fieldsId["measureFreq_startYearDate"] 				= "BSC_PROG007D0001E_measureFreq_startYearDate";
BSC_PROG007D0001E_fieldsId["measureFreq_endYearDate"] 					= "BSC_PROG007D0001E_measureFreq_endYearDate";
BSC_PROG007D0001E_fieldsId["measureFreq_startDate"] 					= "BSC_PROG007D0001E_measureFreq_startDate";
BSC_PROG007D0001E_fieldsId["measureFreq_endDate"] 						= "BSC_PROG007D0001E_measureFreq_endDate";
BSC_PROG007D0001E_fieldsId["measureFreq_dataFor"] 						= "BSC_PROG007D0001E_measureFreq_dataFor";
BSC_PROG007D0001E_fieldsId["measureFreq_measureDataOrganizationOid"] 	= "BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid";
BSC_PROG007D0001E_fieldsId["measureFreq_measureDataEmployeeOid"] 		= "BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid";

function BSC_PROG007D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG007D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG007D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG007D0001E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG007D0001E_fieldsId);
		return;
	}
}

function BSC_PROG007D0001E_clear() {
	setFieldsBackgroundDefault(BSC_PROG007D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG007D0001E_fieldsId);	
	dijit.byId("BSC_PROG007D0001E_measureFreq_dataFor").set("value", "all");
	BSC_PROG007D0001E_measureFreq_setDataForValue();
	dijit.byId("BSC_PROG007D0001E_measureFreq_frequency").set("value", "6");
	BSC_PROG007D0001E_measureFreq_setFrequencyValue();
	dijit.byId('BSC_PROG007D0001E_measureFreq_startYearDate').set('displayedValue', '');
	dijit.byId('BSC_PROG007D0001E_measureFreq_endYearDate').set('displayedValue', '');
	dijit.byId('BSC_PROG007D0001E_measureFreq_startDate').set('displayedValue', '');
	dijit.byId('BSC_PROG007D0001E_measureFreq_endDate').set('displayedValue', '');
	dijit.byId("BSC_PROG007D0001E_name").set("value", "");
	dijit.byId('BSC_PROG007D0001E_integration').set("value", "+1");
	dijit.byId('BSC_PROG007D0001E_forecast').set("value", "+1");
	dijit.byId('BSC_PROG007D0001E_coefficient1').set("value", "+0.50");
	dijit.byId('BSC_PROG007D0001E_coefficient2').set("value", "+0.50");
	dijit.byId('BSC_PROG007D0001E_coefficient3').set("value", "+0.50");
	dijit.byId("BSC_PROG007D0001E_description").set("value", "");
}

//------------------------------------------------------------------------------
// measure options settings function
//------------------------------------------------------------------------------
function BSC_PROG007D0001E_measureFreq_setDataForValue() {
	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG007D0001E_measureFreq_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG007D0001E_measureFreq_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG007D0001E_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG007D0001E_measureFreq_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG007D0001E_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG007D0001E_measureFreq_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG007D0001E_measureFreq_frequency").get("value");
	dijit.byId("BSC_PROG007D0001E_measureFreq_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0001E_measureFreq_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0001E_measureFreq_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG007D0001E_measureFreq_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG007D0001E_measureFreq_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0001E_measureFreq_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG007D0001E_measureFreq_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG007D0001E_measureFreq_endYearDate").set("readOnly", false);				
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
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG007D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="600px" cellpadding="1" cellspacing="0" >	
		<tr>
			<td height="100px" width="100%"  align="left">
			
		        <!-- ContentTab -->
		        <div data-dojo-type="dijit/layout/ContentPane" title="Content" data-dojo-props="selected:true">
					<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >
				    	<tr>
				    		<td height="50px" width="100%"  align="left">
				    			
				    			<div data-dojo-type="dijit.TitlePane" data-dojo-props=" title: 'Measure settings&nbsp;<font color=RED>*</font>' " open="true">	
								<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:140px">
								
									<table border="0" width="100%" >
									
										<tr valign="top">
											<td width="100%" align="left" height="25px">														
												Frequency
												<gs:select name="BSC_PROG007D0001E_measureFreq_frequency" dataSource="frequencyMap" id="BSC_PROG007D0001E_measureFreq_frequency" value="measureFreq.freq" onChange="BSC_PROG007D0001E_measureFreq_setFrequencyValue();" width="140"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_frequency'">
								    				Select frequency.
												</div> 									
											</td>											
										</tr>	
															
										<tr valign="top">
											<td width="100%" align="left" height="25px">
											
										    	Start year
										    	<input id="BSC_PROG007D0001E_measureFreq_startYearDate" name="BSC_PROG007D0001E_measureFreq_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
										    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' value="${fields.measureFreqStartYearDate}" />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_startYearDate'">
								    				Select start year.
												</div>							    		
										    	&nbsp;	
										    	End year
										    	<input id="BSC_PROG007D0001E_measureFreq_endYearDate" name="BSC_PROG007D0001E_measureFreq_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
										    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' value="${fields.measureFreqEndYearDate}" />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_endYearDate'">
								    				Select end year.
												</div>							    									    	
										    	&nbsp;&nbsp;		
												Start date
												<input id="BSC_PROG007D0001E_measureFreq_startDate" type="text" name="BSC_PROG007D0001E_measureFreq_startDate" data-dojo-type="dijit.form.DateTextBox"
													maxlength="10" 
													constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" value="${fields.measureFreqStartDate}" />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_startDate'">
								    				Select start date.
												</div>											
												&nbsp;						
												End date
												<input id="BSC_PROG007D0001E_measureFreq_endDate" type="text" name="BSC_PROG007D0001E_measureFreq_endDate" data-dojo-type="dijit.form.DateTextBox"
													maxlength="10" 
													constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" value="${fields.measureFreqEndDate}" />																	    									    	
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_endDate'">
								    				Select end date.
												</div>							    			
										    </td>	
										</tr>
										<tr valign="top">
											<td width="100%" align="left" height="25px">							
												Data type
												<gs:select name="BSC_PROG007D0001E_measureFreq_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization\", \"employee\":\"Employee\" }" id="BSC_PROG007D0001E_measureFreq_dataFor" value="fields.measureFreqDataFor" onChange="BSC_PROG007D0001E_measureFreq_setDataForValue();" width="140"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_dataFor'">
								    				Select measure data type.
												</div>										
												&nbsp;&nbsp;
												Organization
												<gs:select name="BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid" onChange="BSC_PROG007D0001E_measureFreq_setMeasureDataOrgaValue();" readonly="Y"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid'">
								    				Select measure data organization/department.
												</div>									
												&nbsp;&nbsp;
												Employee
												<gs:select name="BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid" onChange="BSC_PROG007D0001E_measureFreq_setMeasureDataEmplValue();" readonly="Y"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid'">
								    				Select measure data personal/Employee.
												</div>									
											</td>
										</tr>																						
																														
									</table>
						    		
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_frequency"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_startYearDate"></gs:inputfieldNoticeMsgLabel>
						    		<!-- 
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_endYearDate"></gs:inputfieldNoticeMsgLabel>
						    		-->
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_startDate"></gs:inputfieldNoticeMsgLabel>
						    		<!-- 
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_endDate"></gs:inputfieldNoticeMsgLabel>
						    		-->
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_dataFor"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
						    			
					    		</div>	
					    		</div>	 
					    		   		    		
				    		</td>
				    	</tr>
				    	
					</table>
				</div>
					    				
			</td>			
		</tr>
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Name" id="BSC_PROG007D0001E_name" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_name"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG007D0001E_name" id="BSC_PROG007D0001E_name" value="tsa.name" width="300" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_name'">
    				Input name.
				</div> 				
			</td>
		</tr>	
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Integration" id="BSC_PROG007D0001E_integration" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_integration"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001E_integration" name="BSC_PROG007D0001E_integration" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${tsa.integrationOrder}" data-dojo-props="smallDelta:1, constraints:{min:1,max:5, pattern: '+0;-0' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_integration'">
    				Input integration value, only allow numbers. range: 1 ~ 5
				</div>      		
			</td>
		</tr>
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Forecast next" id="BSC_PROG007D0001E_forecast" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_forecast"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001E_forecast" name="BSC_PROG007D0001E_forecast" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${tsa.forecastNext}" data-dojo-props="smallDelta:1, constraints:{min:1,max:6, pattern: '+0;-0' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_forecast'">
    				Input forecast value, only allow numbers. range: 1 ~ 6
				</div>      		
			</td>
		</tr>		
		
		
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Coefficient (1)" id="BSC_PROG007D0001E_coefficient1" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_coefficient1"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001E_coefficient1" name="BSC_PROG007D0001E_coefficient1" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${coefficient1.seqValueAsString}" data-dojo-props="smallDelta:0.10, constraints:{min:-1.00,max:1.00, pattern: '+0.00;-0.00' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_coefficient1'">
    				Input coefficient value, only allow numbers. range: -1.00 ~ 1.00
				</div>      		
			</td>
		</tr>	
		
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Coefficient (2)" id="BSC_PROG007D0001E_coefficient2" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_coefficient2"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001E_coefficient2" name="BSC_PROG007D0001E_coefficient2" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${coefficient2.seqValueAsString}" data-dojo-props="smallDelta:0.10, constraints:{min:-1.00,max:1.00, pattern: '+0.00;-0.00' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_coefficient2'">
    				Input coefficient value, only allow numbers. range: -1.00 ~ 1.00
				</div>      		
			</td>
		</tr>							
		
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Coefficient (3)" id="BSC_PROG007D0001E_coefficient3" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001E_coefficient3"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001E_coefficient3" name="BSC_PROG007D0001E_coefficient3" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${coefficient3.seqValueAsString}" data-dojo-props="smallDelta:0.10, constraints:{min:-1.00,max:1.00, pattern: '+0.00;-0.00' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_coefficient3'">
    				Input coefficient value, only allow numbers. range: -1.00 ~ 1.00
				</div>      		
			</td>
		</tr>	
				
		
		<tr>
		    <td height="150px" width="100%" align="left" >
		    	<gs:label text="Description" id="BSC_PROG007D0001E_description"></gs:label>
		    	<br/>
		    	<textarea id="BSC_PROG007D0001E_description" name="BSC_PROG007D0001E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${tsa.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001E_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   		    	
		    </td>
		</tr>			
		
		
    	<tr>
    		<td height="50px" width="100%"  align="left" >
    			<gs:button name="BSC_PROG007D0001E_save" id="BSC_PROG007D0001E_save" onClick="BSC_PROG007D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.tsaUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    					
    						'fields.oid'									:	'${tsa.oid}',
    						'fields.measureFreq_frequency'					:	dijit.byId('BSC_PROG007D0001E_measureFreq_frequency').get('value'),    						
							'fields.measureFreq_startYearDate'				:	dijit.byId('BSC_PROG007D0001E_measureFreq_startYearDate').get('displayedValue'),							
							'fields.measureFreq_endYearDate'				:	dijit.byId('BSC_PROG007D0001E_measureFreq_endYearDate').get('displayedValue'),
							'fields.measureFreq_startDate'					:	dijit.byId('BSC_PROG007D0001E_measureFreq_startDate').get('displayedValue'),
							'fields.measureFreq_endDate'					:	dijit.byId('BSC_PROG007D0001E_measureFreq_endDate').get('displayedValue'),
							'fields.measureFreq_dataFor'					:	dijit.byId('BSC_PROG007D0001E_measureFreq_dataFor').get('value'),
							'fields.measureFreq_measureDataOrganizationOid'	:	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid').get('value'),
							'fields.measureFreq_measureDataEmployeeOid'		:	dijit.byId('BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid').get('value'),
							
							'fields.name'									:	dijit.byId('BSC_PROG007D0001E_name').get('value'),
							'fields.integration'							:	dijit.byId('BSC_PROG007D0001E_integration').get('value'),
							'fields.forecast'								:	dijit.byId('BSC_PROG007D0001E_forecast').get('value'),
							'fields.coefficient1'							:	dijit.byId('BSC_PROG007D0001E_coefficient1').get('value'),
							'fields.coefficient2'							:	dijit.byId('BSC_PROG007D0001E_coefficient2').get('value'),
							'fields.coefficient3'							:	dijit.byId('BSC_PROG007D0001E_coefficient3').get('value'),
							'fields.description'							:	dijit.byId('BSC_PROG007D0001E_description').get('value')	
								
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG007D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG007D0001E_clear" id="BSC_PROG007D0001E_clear" onClick="BSC_PROG007D0001E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    	    		
    		</td>
    	</tr>  		
		
		
	</table>
	
<script type="text/javascript">${programId}_page_message();</script>

<script type="text/javascript">

//-----------------------------------------------------------------------------------
setTimeout(function(){
	
	BSC_PROG007D0001E_measureFreq_setFrequencyValue();
	BSC_PROG007D0001E_measureFreq_setDataForValue();
	
	<s:if test=" \"\" != measureFreq.organizationOid && null != measureFreq.organizationOid ">
	dijit.byId("BSC_PROG007D0001E_measureFreq_measureDataOrganizationOid").set("value", "${measureFreq.organizationOid}");
	</s:if>
	<s:if test=" \"\" != measureFreq.employeeOid && null != measureFreq.employeeOid ">
	dijit.byId("BSC_PROG007D0001E_measureFreq_measureDataEmployeeOid").set("value", "${measureFreq.employeeOid}");
	</s:if>
	
}, 1000);
//-----------------------------------------------------------------------------------

</script>

</body>
</html>
