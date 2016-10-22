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

var BSC_PROG001D0003E_fieldsId = new Object();
BSC_PROG001D0003E_fieldsId['forId'] 		= 'BSC_PROG001D0003E_forId';
BSC_PROG001D0003E_fieldsId['name'] 			= 'BSC_PROG001D0003E_name';
BSC_PROG001D0003E_fieldsId['type'] 			= 'BSC_PROG001D0003E_type';
BSC_PROG001D0003E_fieldsId['trendsFlag'] 	= 'BSC_PROG001D0003E_trendsFlag';
BSC_PROG001D0003E_fieldsId['returnMode'] 	= 'BSC_PROG001D0003E_returnMode';
BSC_PROG001D0003E_fieldsId['returnVar'] 	= 'BSC_PROG001D0003E_returnVar';
BSC_PROG001D0003E_fieldsId['expression'] 	= 'BSC_PROG001D0003E_expression';
BSC_PROG001D0003E_fieldsId['description']	= 'BSC_PROG001D0003E_description';

function BSC_PROG001D0003E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0003E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0003E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0003E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0003E_fieldsId);
		return;
	}		
}

function BSC_PROG001D0003E_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0003E_fieldsId);	
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0003E_fieldsId);
	//dijit.byId('BSC_PROG001D0003E_forId').set("value", "");
	dijit.byId('BSC_PROG001D0003E_name').set("value", "");
	dijit.byId('BSC_PROG001D0003E_type').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003E_trendsFlag').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003E_returnMode').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG001D0003E_returnVar').set("value", "");		
	dijit.byId('BSC_PROG001D0003E_expression').set("value", "");
	dijit.byId('BSC_PROG001D0003E_description').set("value", "");
}

function BSC_PROG001D0003E_putValue(id, str) {
	var formulaValue = dijit.byId(id).get("value");
	dijit.byId(id).set("value", formulaValue+str);	
}

function BSC_PROG001D0003E_testFormulaSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0003E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0003E_fieldsId);		
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
		saveJsMethod="BSC_PROG001D0003E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="800px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_forId')}" id="BSC_PROG001D0003E_forId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0003E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_forId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_forId" id="BSC_PROG001D0003E_forId" value="formula.forId" width="200" maxlength="14" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_forId'">
		    		Id. ( read only )
				</div>       			
    		</td>	
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_name')}" id="BSC_PROG001D0003E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_name" id="BSC_PROG001D0003E_name" value="formula.name" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_name'">
		    		Input name.
				</div>       			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_type')}" id="BSC_PROG001D0003E_type" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_type"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003E_type" dataSource="typeMap" id="BSC_PROG001D0003E_type" value="formula.type"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_type'">
		    		Select langauge type. ( recommend groovy )
				</div>     			
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_trendsFlag')}" id="BSC_PROG001D0003E_trendsFlag" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_trendsFlag"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003E_trendsFlag" dataSource="trendsFlagMap" id="BSC_PROG001D0003E_trendsFlag" value="formula.trendsFlag"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_trendsFlag'">
		    		Y is for KPI Trends, N is for KPI.
				</div>       			
    		</td>
    	</tr>    			    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_returnMode')}" id="BSC_PROG001D0003E_returnMode" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_returnMode"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0003E_returnMode" dataSource="modeMap" id="BSC_PROG001D0003E_returnMode" value="formula.returnMode"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_returnMode'">
		    		Select mode.
				</div>         			
    		</td>
    	</tr>		    	
 		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0003E_returnVar')}" id="BSC_PROG001D0003E_returnVar"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_returnVar"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_returnVar" id="BSC_PROG001D0003E_returnVar" value="formula.returnVar" width="100" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_returnVar'">
		    		Input return variable name when mode is custom.
				</div>    			
    		</td>
    	</tr>		    	
		<tr>
		    <td height="250px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG001D0003E_expression')}" id="BSC_PROG001D0003E_expression"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0003E_expression"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG001D0003E_expression" name="BSC_PROG001D0003E_expression" data-dojo-type="dijit/form/Textarea" rows="4" cols="65" style="width:650px;height:210px;max-height:220px">${formula.expression}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_expression'">
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
								<button id="BSC_PROG001D0003E_btnCalActual" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'actual');
										}
									"><s:property value="getText('BSC_PROG001D0003E_btnCalActual')"/></button>		
								<button id="BSC_PROG001D0003E_btnCalTarget" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'target');
										}
									"><s:property value="getText('BSC_PROG001D0003E_btnCalTarget')"/></button>	
								<div data-dojo-type="dijit/form/DropDownButton">
		  							<span><s:property value="getText('BSC_PROG001D0003E_testSet')"/></span>
		  							<div data-dojo-type="dijit/TooltipDialog">
		  								<table border="0" cellpadding="0" cellspacing="0" >
		  									<tr>
		  										<td width="70%" align="right"><label for="name"><s:property value="getText('BSC_PROG001D0003E_actual')"/>:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003E_actual" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="80"
													    data-dojo-props="constraints:{min:-9999,max:9999,places:0},
													    invalidMessage:'Invalid elevation.'" />		  										
		  										</td>
		  									</tr>
		  									<tr>
		  										<td width="70%" align="right"><label for="hobby"><s:property value="getText('BSC_PROG001D0003E_target')"/>:</label></td>
		  										<td width="30%" align="left">
													<input id="BSC_PROG001D0003E_target" type="text"
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
													<input id="BSC_PROG001D0003E_cv" type="text"
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
													<input id="BSC_PROG001D0003E_pv" type="text"
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
								<button id="BSC_PROG001D0003E_btnCalCurrentPeroidScore" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'cv');
										}
									">KPI Current peroid score(cv)</button>
								<button id="BSC_PROG001D0003E_btnCalPreviousPeroidScore" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'pv');
										}
									">KPI Previous peroid score(pv)</button>												  				
			  				</td>
			  			</tr>			  			
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003E_btnCal7" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '7');
										}
									">7</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal8" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '8');
										}
									">8</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal9" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '9');
										}
									">9</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalDivision" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '÷');
										}
									">÷</button>				  				
			  				</td>
			  				<td width="16%">
				    			<gs:button name="BSC_PROG001D0003E_testFormula" id="BSC_PROG001D0003E_testFormula" onClick="BSC_PROG001D0003E_testFormula();"
				    				handleAs="json"
				    				sync="N"
				    				xhrUrl="${basePath}/bsc.formulaTestAction.action"
				    				parameterType="postData"
				    				xhrParameter=" 
				    					{  
				    						'fields.type'			: dijit.byId('BSC_PROG001D0003E_type').get('value'),
				    						'fields.trendsFlag'		: dijit.byId('BSC_PROG001D0003E_trendsFlag').get('value'),
				    						'fields.returnMode'		: dijit.byId('BSC_PROG001D0003E_returnMode').get('value'),
				    						'fields.returnVar'		: dijit.byId('BSC_PROG001D0003E_returnVar').get('value'),    						 
				    						'fields.expression'		: dijit.byId('BSC_PROG001D0003E_expression').get('value'),
				    						'fields.actual'			: dijit.byId('BSC_PROG001D0003E_actual').get('value'),
				    						'fields.target'			: dijit.byId('BSC_PROG001D0003E_target').get('value'),
				    						'fields.cv'				: dijit.byId('BSC_PROG001D0003E_cv').get('value'),
				    						'fields.pv'				: dijit.byId('BSC_PROG001D0003E_pv').get('value')				    						
				    					} 
				    				"
				    				errorFn=""
				    				loadFn="BSC_PROG001D0003E_testFormulaSuccess(data);" 
				    				programId="${programId}"
				    				label="${action.getText('BSC_PROG001D0003E_testFormula')}" 
				    				iconClass=""
				    				cssClass="alt-info"></gs:button>    									  				
			  				</td>		  				
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalClear" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){
										 	dijit.byId('BSC_PROG001D0003E_expression').set('value', '');
										}
									"
									class="alt-info">Cls</button>				  				
			  				</td>					  				
			  			</tr>
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003E_btnCal4" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '4');
										}
									">4</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal5" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '5');
										}
									">5</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal6" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '6');
										}
									">6</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalMultiply" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '×');
										}
									">×</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalLeftParentheses" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '(');
										}
									">(</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalRightParentheses" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', ')');
										}
									">)</button>				  				
			  				</td>			  							  				
			  			</tr>				  			
			  			<tr>
			  				<td width="17%">
								<button id="BSC_PROG001D0003E_btnCal1" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '1');
										}
									">1</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal2" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '2');
										}
									">2</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCal3" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '3');
										}
									">3</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalMinus" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '−');
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
								<button id="BSC_PROG001D0003E_btnCal0" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '0');
										}
									">0</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalDot" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '.');
										}
									">.</button>			  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalMod" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '%');
										}
									">%</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalPlus" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', '+');
										}
									">+</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalLeftAbs" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'abs(');
										}
									">abs</button>				  				
			  				</td>
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalRightSqrt" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'sqrt(');
										}
									">sqrt</button>				  				
			  				</td>		  				
			  			</tr>			  					  			
			  					  			
			  		</table>		    	
		    		
		    </td>
		</tr>        	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG001D0003E_description')}" id="BSC_PROG001D0003E_description"></gs:label>
		    	<br/>
		    	<textarea id="BSC_PROG001D0003E_description" name="BSC_PROG001D0003E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${formula.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0003E_description'">
    				Input description, the maximum allowed 500 characters. 
				</div>   		    		
		    </td>
		</tr>      	     	   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0003E_update" id="BSC_PROG001D0003E_update" onClick="BSC_PROG001D0003E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.formulaUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${formula.oid}',
    						'fields.forId'			: dijit.byId('BSC_PROG001D0003E_forId').get('value'),
    						'fields.name'			: dijit.byId('BSC_PROG001D0003E_name').get('value'),  
    						'fields.type'			: dijit.byId('BSC_PROG001D0003E_type').get('value'),
    						'fields.trendsFlag'		: dijit.byId('BSC_PROG001D0003E_trendsFlag').get('value'),
    						'fields.returnMode'		: dijit.byId('BSC_PROG001D0003E_returnMode').get('value'),
    						'fields.returnVar'		: dijit.byId('BSC_PROG001D0003E_returnVar').get('value'),    						 
    						'fields.expression'		: dijit.byId('BSC_PROG001D0003E_expression').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG001D0003E_description').get('value'),
    						'fields.actual'			: dijit.byId('BSC_PROG001D0003E_actual').get('value'),
    						'fields.target'			: dijit.byId('BSC_PROG001D0003E_target').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0003E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0003E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0003E_clear" id="BSC_PROG001D0003E_clear" onClick="BSC_PROG001D0003E_clear();" 
    				label="${action.getText('BSC_PROG001D0003E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
