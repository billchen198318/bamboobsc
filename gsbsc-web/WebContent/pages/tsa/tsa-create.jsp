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
		saveJsMethod="BSC_PROG007D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="950px" cellpadding="1" cellspacing="0" >	
		<tr>
			<td height="100px" width="100%"  align="left">
			
		        <!-- ContentTab -->
		        <div data-dojo-type="dijit/layout/ContentPane" title="Content" data-dojo-props="selected:true">
					<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >
				    	<tr>
				    		<td height="50px" width="100%"  align="left">
				    			
				    			<div data-dojo-type="dijit.TitlePane" data-dojo-props=" title: 'Measure settings' " open="true">	
								<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:140px">
								
									<table border="0" width="100%" >
									
										<tr valign="top">
											<td width="100%" align="left" height="25px">														
												Frequency
												<gs:select name="BSC_PROG007D0001A_measureFreq_frequency" dataSource="frequencyMap" id="BSC_PROG007D0001A_measureFreq_frequency" value="6" onChange="BSC_PROG007D0001A_measureFreq_setFrequencyValue();" width="140"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_frequency'">
								    				Select frequency.
												</div> 									
											</td>											
										</tr>	
															
										<tr valign="top">
											<td width="100%" align="left" height="25px">
											
										    	Start year
										    	<input id="BSC_PROG007D0001A_measureFreq_startYearDate" name="BSC_PROG007D0001A_measureFreq_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
										    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_startYearDate'">
								    				Select start year.
												</div>							    		
										    	&nbsp;	
										    	End year
										    	<input id="BSC_PROG007D0001A_measureFreq_endYearDate" name="BSC_PROG007D0001A_measureFreq_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
										    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_endYearDate'">
								    				Select end year.
												</div>							    									    	
										    	&nbsp;&nbsp;		
												Start date
												<input id="BSC_PROG007D0001A_measureFreq_startDate" type="text" name="BSC_PROG007D0001A_measureFreq_startDate" data-dojo-type="dijit.form.DateTextBox"
													maxlength="10" 
													constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_startDate'">
								    				Select start date.
												</div>											
												&nbsp;						
												End date
												<input id="BSC_PROG007D0001A_measureFreq_endDate" type="text" name="BSC_PROG007D0001A_measureFreq_endDate" data-dojo-type="dijit.form.DateTextBox"
													maxlength="10" 
													constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" readonly />																	    									    	
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_endDate'">
								    				Select end date.
												</div>							    			
										    </td>	
										</tr>
										<tr valign="top">
											<td width="100%" align="left" height="25px">							
												Data type
												<gs:select name="BSC_PROG007D0001A_measureFreq_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization / department\", \"employee\":\"Personal / employee\" }" id="BSC_PROG007D0001A_measureFreq_dataFor" onChange="BSC_PROG007D0001A_measureFreq_setDataForValue();" width="140"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_dataFor'">
								    				Select measure data type.
												</div>										
												&nbsp;&nbsp;
												Organization / department
												<gs:select name="BSC_PROG007D0001A_measureFreq_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG007D0001A_measureFreq_measureDataOrganizationOid" onChange="BSC_PROG007D0001A_measureFreq_setMeasureDataOrgaValue();" readonly="Y"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_measureDataOrganizationOid'">
								    				Select measure data organization/department.
												</div>									
												&nbsp;&nbsp;
												Personal / employee
												<gs:select name="BSC_PROG007D0001A_measureFreq_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG007D0001A_measureFreq_measureDataEmployeeOid" onChange="BSC_PROG007D0001A_measureFreq_setMeasureDataEmplValue();" readonly="Y"></gs:select>
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_measureFreq_measureDataEmployeeOid'">
								    				Select measure data personal/Employee.
												</div>									
											</td>
										</tr>																						
																														
									</table>
						    		
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_frequency"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_startYearDate"></gs:inputfieldNoticeMsgLabel>
						    		<!-- 
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_endYearDate"></gs:inputfieldNoticeMsgLabel>
						    		-->
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_startDate"></gs:inputfieldNoticeMsgLabel>
						    		<!-- 
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_endDate"></gs:inputfieldNoticeMsgLabel>
						    		-->
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_dataFor"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
						    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_measureFreq_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>
						    			
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
				<gs:label text="Name" id="BSC_PROG007D0001A_name" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_name"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG007D0001A_name" id="BSC_PROG007D0001A_name" value="" width="300" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_name'">
    				Input name.
				</div> 				
			</td>
		</tr>	
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Integration" id="BSC_PROG007D0001A_integration" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_integration"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001A_integration" name="BSC_PROG007D0001A_integration" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="1" data-dojo-props="smallDelta:1, constraints:{min:1,max:5, pattern: '+0;-0' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_integration'">
    				Input integration value, only allow numbers. range: 1 ~ 5
				</div>      		
			</td>
		</tr>
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="Forecast size" id="BSC_PROG007D0001A_forecast" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG007D0001A_forecast"></gs:inputfieldNoticeMsgLabel>
				<br/>
    			<input id="BSC_PROG007D0001A_forecast" name="BSC_PROG007D0001A_forecast" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="1" data-dojo-props="smallDelta:1, constraints:{min:1,max:6, pattern: '+0;-0' } ,style:'width: 100px;' " />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_forecast'">
    				Input forecast value, only allow numbers. range: 1 ~ 6
				</div>      		
			</td>
		</tr>		
		<tr>
		    <td height="150px" width="100%" align="left" colspan="2">
		    	<gs:label text="Description" id="BSC_PROG007D0001A_description"></gs:label>
		    	<br/>
		    	<textarea id="BSC_PROG007D0001A_description" name="BSC_PROG007D0001A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG007D0001A_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   		    	
		    </td>
		</tr>		
		
		
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
