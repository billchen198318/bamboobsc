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

var BSC_PROG001D0003A_fieldsId = new Object();
BSC_PROG001D0003A_fieldsId['forId'] 		= 'BSC_PROG001D0003A_forId';
BSC_PROG001D0003A_fieldsId['name'] 			= 'BSC_PROG001D0003A_name';
BSC_PROG001D0003A_fieldsId['type'] 			= 'BSC_PROG001D0003A_type';
BSC_PROG001D0003A_fieldsId['trendsFlag'] 	= 'BSC_PROG001D0003A_trendsFlag';
BSC_PROG001D0003A_fieldsId['returnMode'] 	= 'BSC_PROG001D0003A_returnMode';
BSC_PROG001D0003A_fieldsId['returnVar'] 	= 'BSC_PROG001D0003A_returnVar';
BSC_PROG001D0003A_fieldsId['expression'] 	= 'BSC_PROG001D0003A_expression';
BSC_PROG001D0003A_fieldsId['description']	= 'BSC_PROG001D0003A_description';

function BSC_PROG001D0003A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG001D0003A_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0003A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0003A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0003A_fieldsId);
		return;
	}	
	BSC_PROG001D0003A_clear();
}

function BSC_PROG001D0003A_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0003A_fieldsId);		
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0003A_fieldsId);
	dijit.byId('BSC_PROG001D0003A_forId').set("value", "");
	dijit.byId('BSC_PROG001D0003A_name').set("value", "");
	dijit.byId('BSC_PROG001D0003A_type').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003A_trendsFlag').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003A_returnMode').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003A_returnVar').set("value", "");		
	dijit.byId('BSC_PROG001D0003A_expression').set("value", "");
	dijit.byId('BSC_PROG001D0003A_description').set("value", "");
}

function BSC_PROG001D0003A_putValue(id, str) {
	var formulaValue = dijit.byId(id).get("value");
	dijit.byId(id).set("value", formulaValue+str);	
}

function BSC_PROG001D0003A_testFormulaSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0003A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0003A_fieldsId);		
		return;
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
		saveJsMethod="BSC_PROG001D0003A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="800px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_forId')}" id="BSC_PROG001D0003A_forId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_forId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003A_forId" id="BSC_PROG001D0003A_forId" value="" width="200" maxlength="14"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_forId'">
		    		Input Id, only allow normal characters.
				</div>       			
    		</td>
    	</tr>  		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_name')}" id="BSC_PROG001D0003A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003A_name" id="BSC_PROG001D0003A_name" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_name'">
		    		Input name.
				</div>       			
    		</td>
    	</tr>     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_type')}" id="BSC_PROG001D0003A_type" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_type"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003A_type" dataSource="typeMap" id="BSC_PROG001D0003A_type"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_type'">
		    		Select langauge type. ( recommend groovy )
				</div>       			
    		</td>
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_trendsFlag')}" id="BSC_PROG001D0003A_trendsFlag" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_trendsFlag"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003A_trendsFlag" dataSource="trendsFlagMap" id="BSC_PROG001D0003A_trendsFlag" value="N"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_trendsFlag'">
		    		Y is for KPI Trends(KPI Current peroid score V.S KPI Previous peroid score change), N is for KPI.
				</div>       			
    		</td>
    	</tr>     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_returnMode')}" id="BSC_PROG001D0003A_returnMode" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_returnMode"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003A_returnMode" dataSource="modeMap" id="BSC_PROG001D0003A_returnMode"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_returnMode'">
		    		Select mode.
				</div>     			
    		</td>
    	</tr>     	
 		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003A_returnVar')}" id="BSC_PROG001D0003A_returnVar"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_returnVar"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003A_returnVar" id="BSC_PROG001D0003A_returnVar" value="" width="100" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_returnVar'">
		    		Input return variable name when mode is custom.
				</div>     			
    		</td>
    	</tr>     	   	    	    	
		<tr>
		    <td height="250px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG001D0003A_expression')}" id="BSC_PROG001D0003A_expression" requiredFlag="Y"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003A_expression"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG001D0003A_expression" name="BSC_PROG001D0003A_expression" data-dojo-type="dijit/form/Textarea" rows="4" cols="65" style="width:650px;height:210px;max-height:220px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_expression'">
		    		Input expression.<BR/>
		    		Example:<BR/>
		    		&nbsp;&nbsp;( actual / target ) × 100 + 1<BR/>
		    		<BR/>
		    		&nbsp;&nbsp;Actual(actual) : the variable is measured data actual field.<BR/>
		    		&nbsp;&nbsp;Target(target) : the variable is measured data target field.<BR/>
		    		<BR/>
		    		Input expression for KPI Current peroid score V.S KPI Previous peroid score change.<BR/>
		    		Example:<BR/>
		    		( cv / pv - 1 ) × 100<BR/>
		    		&nbsp;&nbsp;KPI Current peroid score(cv)<BR/>
		    		&nbsp;&nbsp;KPI Previous peroid score(pv)<BR/> 
				</div> 
						    	
			  		<table border="0" cellpadding="0" cellspacing="0" >
			  			<tr>
			  				<td colspan="6">
								<button id="BSC_PROG001D0003A_btnCalActual" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'actual');
										}
									"><s:property value="getText('BSC_PROG001D0003A_btnCalActual')"/></button>		
								<button id="BSC_PROG001D0003A_btnCalTarget" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'target');
										}
									"><s:property value="getText('BSC_PROG001D0003A_btnCalTarget')"/></button>	
								<div data-dojo-type="dijit/form/DropDownButton">
		  							<span><s:property value="getText('BSC_PROG001D0003A_testSet')"/></span>
		  							<div data-dojo-type="dijit/TooltipDialog">
		  								<table border="0" cellpadding="0" cellspacing="0" >
		  									<tr>
		  										<td width="70%" align="right"><label for="name"><s:property value="getText('BSC_PROG001D0003A_actual')"/>:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003A_actual" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="80"
													    data-dojo-props="constraints:{min:-9999,max:9999,places:0},
													    invalidMessage:'Invalid elevation.'" />		  										
		  										</td>
		  									</tr>
		  									<tr>
		  										<td width="70%" align="right"><label for="hobby"><s:property value="getText('BSC_PROG001D0003A_target')"/>:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003A_target" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="100"
													    data-dojo-props="constraints:{min:-9999,max:9999,places:0},
													    invalidMessage:'Invalid elevation.'" />			  										
		  										</td>
		  									</tr>			
		  									
		  									
		  									<tr>
		  										<td width="70%" align="right"><label>KPI Current Peroid Score:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003A_cv" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="70"
													    data-dojo-props="constraints:{min:-9999,max:9999,places:0},
													    invalidMessage:'Invalid elevation.'" />			  										
		  										</td>
		  									</tr>	
		  									<tr>
		  										<td width="70%" align="right"><label>KPI Previous Peroid Score:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003A_pv" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="55"
													    data-dojo-props="constraints:{min:-9999,max:9999,places:0},
													    invalidMessage:'Invalid elevation.'" />			  										
		  										</td>
		  									</tr>			  											  									
		  									
		  																	  									
		  								</table>
		  							</div>
								</div>									
																													  				
			  				</td>
			  			</tr>
			  			<tr>
			  				<td colspan="6">			  			
								<button id="BSC_PROG001D0003A_btnCalCurrentPeroidScore" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'cv');
										}
									">KPI Current peroid score(cv)</button>
								<button id="BSC_PROG001D0003A_btnCalPreviousPeroidScore" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'pv');
										}
									">KPI Previous peroid score(pv)</button>												  				
			  				</td>
			  			</tr>			  			
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003A_btnCal7" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '7');
										}
									">7</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal8" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '8');
										}
									">8</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal9" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '9');
										}
									">9</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalDivision" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '÷');
										}
									">÷</button>				  				
			  				</td>
			  				<td width="16%">
				    			<gs:button name="BSC_PROG001D0003A_testFormula" id="BSC_PROG001D0003A_testFormula" onClick="BSC_PROG001D0003A_testFormula();"
				    				handleAs="json"
				    				sync="N"
				    				xhrUrl="${basePath}/bsc.formulaTestAction.action"
				    				parameterType="postData"
				    				xhrParameter=" 
				    					{  
				    						'fields.type'			: dijit.byId('BSC_PROG001D0003A_type').get('value'),
				    						'fields.trendsFlag'		: dijit.byId('BSC_PROG001D0003A_trendsFlag').get('value'),
				    						'fields.returnMode'		: dijit.byId('BSC_PROG001D0003A_returnMode').get('value'),
				    						'fields.returnVar'		: dijit.byId('BSC_PROG001D0003A_returnVar').get('value'),    						 
				    						'fields.expression'		: dijit.byId('BSC_PROG001D0003A_expression').get('value'),
				    						'fields.actual'			: dijit.byId('BSC_PROG001D0003A_actual').get('value'),
				    						'fields.target'			: dijit.byId('BSC_PROG001D0003A_target').get('value'),
				    						'fields.cv'				: dijit.byId('BSC_PROG001D0003A_cv').get('value'),
				    						'fields.pv'				: dijit.byId('BSC_PROG001D0003A_pv').get('value')
				    					} 
				    				"
				    				errorFn=""
				    				loadFn="BSC_PROG001D0003A_testFormulaSuccess(data);" 
				    				programId="${programId}"
				    				label="${action.getText('BSC_PROG001D0003A_testFormula')}" 
				    				iconClass=""
				    				cssClass="alt-info"></gs:button>    									  				
			  				</td>		  				
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalClear" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){
										 	dijit.byId('BSC_PROG001D0003A_expression').set('value', '');
										}
									"
									class="alt-info">Cls</button>				  				
			  				</td>					  				
			  			</tr>
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003A_btnCal4" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '4');
										}
									">4</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal5" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '5');
										}
									">5</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal6" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '6');
										}
									">6</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalMultiply" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '×');
										}
									">×</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalLeftParentheses" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '(');
										}
									">(</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalRightParentheses" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', ')');
										}
									">)</button>				  				
			  				</td>			  							  				
			  			</tr>				  			
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003A_btnCal1" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '1');
										}
									">1</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal2" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '2');
										}
									">2</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCal3" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '3');
										}
									">3</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalMinus" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '−');
										}
									">−</button>				  				
			  				</td>
			  				<td width="16%">		  				
			  				</td>
			  				<td width="16%">		  				
			  				</td>			  							  				
			  			</tr>	
			  			
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003A_btnCal0" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '0');
										}
									">0</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalDot" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '.');
										}
									">.</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalMod" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '%');
										}
									">%</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalPlus" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', '+');
										}
									">+</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalLeftAbs" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'abs(');
										}
									">abs</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003A_btnCalRightSqrt" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003A_putValue('BSC_PROG001D0003A_expression', 'sqrt(');
										}
									">sqrt</button>				  				
			  				</td>		  				
			  			</tr>			  					  			
			  					  			
			  		</table>		    	
		    		
		    </td>
		</tr>        	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG001D0003A_description')}" id="BSC_PROG001D0003A_description"></gs:label>
		    	<br/>
		    	<textarea id="BSC_PROG001D0003A_description" name="BSC_PROG001D0003A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003A_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   			    	
		    </td>
		</tr>      	    	   	  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0003A_save" id="BSC_PROG001D0003A_save" onClick="BSC_PROG001D0003A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.formulaSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.forId'			: dijit.byId('BSC_PROG001D0003A_forId').get('value'),
    						'fields.name'			: dijit.byId('BSC_PROG001D0003A_name').get('value'),  
    						'fields.type'			: dijit.byId('BSC_PROG001D0003A_type').get('value'),
    						'fields.trendsFlag'		: dijit.byId('BSC_PROG001D0003A_trendsFlag').get('value'),
    						'fields.returnMode'		: dijit.byId('BSC_PROG001D0003A_returnMode').get('value'),
    						'fields.returnVar'		: dijit.byId('BSC_PROG001D0003A_returnVar').get('value'),    						 
    						'fields.expression'		: dijit.byId('BSC_PROG001D0003A_expression').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG001D0003A_description').get('value'),
    						'fields.actual'			: dijit.byId('BSC_PROG001D0003A_actual').get('value'),
    						'fields.target'			: dijit.byId('BSC_PROG001D0003A_target').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0003A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0003A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0003A_clear" id="BSC_PROG001D0003A_clear" onClick="BSC_PROG001D0003A_clear();" 
    				label="${action.getText('BSC_PROG001D0003A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>     		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
