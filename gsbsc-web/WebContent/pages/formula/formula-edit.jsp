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
BSC_PROG001D0003E_fieldsId['returnMode'] 	= 'BSC_PROG001D0003E_returnMode';
BSC_PROG001D0003E_fieldsId['returnVar'] 	= 'BSC_PROG001D0003E_returnVar';
BSC_PROG001D0003E_fieldsId['expression'] 	= 'BSC_PROG001D0003E_expression';
BSC_PROG001D0003E_fieldsId['description']	= 'BSC_PROG001D0003E_description';

function BSC_PROG001D0003E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0003E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0003E_fieldsId);		
		return;
	}		
}

function BSC_PROG001D0003E_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0003E_fieldsId);	
	//dijit.byId('BSC_PROG001D0003E_forId').set("value", "");
	dijit.byId('BSC_PROG001D0003E_name').set("value", "");
	dijit.byId('BSC_PROG001D0003E_type').set("value", _gscore_please_select_id);
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

<body class="claro" bgcolor="#EEEEEE" >

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
	
	<table border="0" width="100%" height="600px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b>&nbsp;(read only)&nbsp;:
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_forId" id="BSC_PROG001D0003E_forId" value="formula.forId" width="200" maxlength="14" readonly="Y"></gs:textBox>
    		</td>	
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_name" id="BSC_PROG001D0003E_name" value="formula.name" width="400" maxlength="100"></gs:textBox>
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Type</b>:
    			<br/>
    			<gs:select name="BSC_PROG001D0003E_type" dataSource="typeMap" id="BSC_PROG001D0003E_type" value="formula.type"></gs:select>
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Mode</b>:
    			<br/>
    			<gs:select name="BSC_PROG001D0003E_returnMode" dataSource="modeMap" id="BSC_PROG001D0003E_returnMode" value="formula.returnMode"></gs:select>
    		</td>
    	</tr>		    	
 		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Return variable</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG001D0003E_returnVar" id="BSC_PROG001D0003E_returnVar" value="formula.returnVar" width="100" maxlength="50"></gs:textBox>
    		</td>
    	</tr>		    	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<font color='RED'>*</font><b>Expression</b>:
		    	<br/>
		    	<textarea id="BSC_PROG001D0003E_expression" name="BSC_PROG001D0003E_expression" data-dojo-type="dijit/form/Textarea" rows="4" cols="65" style="width:450px;height:90px;max-height:100px">${formula.expression}</textarea>
		    	
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
									">actual</button>		
								<button id="BSC_PROG001D0003E_btnCalTarget" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){ 
											BSC_PROG001D0003E_putValue('BSC_PROG001D0003E_expression', 'target');
										}
									">target</button>	
								<div data-dojo-type="dijit/form/DropDownButton">
		  							<span>TEST settings</span>
		  							<div data-dojo-type="dijit/TooltipDialog">
		  								<table border="0" cellpadding="0" cellspacing="0" >
		  									<tr>
		  										<td width="20%" align="right"><label for="name">Actual:</label></td>
		  										<td width="80%" align="left">
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
		  										<td width="20%" align="right"><label for="hobby">Target:</label></td>
		  										<td width="80%" align="left">
													<input id="BSC_PROG001D0003E_target" type="text"
													    data-dojo-type="dijit/form/NumberTextBox"
													    name= "elevation"
													    required="true"
													    value="100"
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
				    						'fields.returnMode'		: dijit.byId('BSC_PROG001D0003E_returnMode').get('value'),
				    						'fields.returnVar'		: dijit.byId('BSC_PROG001D0003E_returnVar').get('value'),    						 
				    						'fields.expression'		: dijit.byId('BSC_PROG001D0003E_expression').get('value'),
				    						'fields.actual'			: dijit.byId('BSC_PROG001D0003E_actual').get('value'),
				    						'fields.target'			: dijit.byId('BSC_PROG001D0003E_target').get('value')
				    					} 
				    				"
				    				errorFn=""
				    				loadFn="BSC_PROG001D0003E_testFormulaSuccess(data);" 
				    				programId="${programId}"
				    				label="test" 
				    				iconClass=""></gs:button>    									  				
			  				</td>		  				
			  				<td width="16%">
								<button id="BSC_PROG001D0003E_btnCalClear" data-dojo-type="dijit.form.Button"
									data-dojo-props="
										iconClass:'',
										showLabel:true,
										onClick:function(){
										 	dijit.byId('BSC_PROG001D0003E_expression').set('value', '');
										}
									">Cls</button>				  				
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
		    	<b>Description</b>:
		    	<br/>
		    	<textarea id="BSC_PROG001D0003E_description" name="BSC_PROG001D0003E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${formula.description}</textarea>	
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
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG001D0003E_clear" id="BSC_PROG001D0003E_clear" onClick="BSC_PROG001D0003E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
