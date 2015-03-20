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

var BSC_PROG002D0002E_fieldsId = new Object();
BSC_PROG002D0002E_fieldsId['visionOid'] 	= 'BSC_PROG002D0002E_visionOid';
BSC_PROG002D0002E_fieldsId['name'] 			= 'BSC_PROG002D0002E_name';
BSC_PROG002D0002E_fieldsId['weight'] 		= 'BSC_PROG002D0002E_weight';
BSC_PROG002D0002E_fieldsId['target'] 		= 'BSC_PROG002D0002E_target';
BSC_PROG002D0002E_fieldsId['min'] 			= 'BSC_PROG002D0002E_min';
BSC_PROG002D0002E_fieldsId['description']	= 'BSC_PROG002D0002E_description';

function BSC_PROG002D0002E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0002E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0002E_fieldsId);		
		return;
	}		
}

function BSC_PROG002D0002E_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0002E_fieldsId);
	dijit.byId('BSC_PROG002D0002E_visionOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG002D0002E_name').set("value", "");		
	dijit.byId('BSC_PROG002D0002E_weight').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0002E_target').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0002E_min').set("value", "+000.00");
	dijit.byId('BSC_PROG002D0002E_description').set("value", "");	
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
		saveJsMethod="BSC_PROG002D0002E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="500px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Id</b>&nbsp;(read only)&nbsp;:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0002E_perId" id="BSC_PROG002D0002E_perId" value="perspective.perId" width="200" maxlength="14" readonly="Y"></gs:textBox>
    		</td>    		
    	</tr>		
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Vision</b>:
    			<br/>
    			<gs:select name="BSC_PROG002D0002E_visionOid" dataSource="visionMap" id="BSC_PROG002D0002E_visionOid" value="fields.visionSelectValue"></gs:select>
    		</td>    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG002D0002E_name" id="BSC_PROG002D0002E_name" value="perspective.name" width="400" maxlength="100"></gs:textBox>
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Weight</b>:
    			<br/>
    			<input id="BSC_PROG002D0002E_weight" name= "BSC_PROG002D0002E_weight" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${perspective.weight}" data-dojo-props="smallDelta:10, constraints:{min:0.00,max:999.00, pattern: '+000.00;-0.00' }" />     		    			
    		</td>
    	</tr>        	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Target</b>:
    			<br/>
    			<input id="BSC_PROG002D0002E_target" name= "BSC_PROG002D0002E_target" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${perspective.target}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
			</td>    				     		
    	</tr>   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Min</b>&nbsp;(&nbsp;alert&nbsp;)&nbsp;:
    			<br/>
    			<input id="BSC_PROG002D0002E_min" name= "BSC_PROG002D0002E_min" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="${perspective.min}" data-dojo-props="smallDelta:10, constraints:{min:-9999999999.99,max:9999999999.99, pattern: '+000.00;-0.00' }" />
    		</td>		     		
    	</tr>   
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b>Description</b>:
		    	<br/>		
		    	<textarea id="BSC_PROG002D0002E_description" name="BSC_PROG002D0002E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${perspective.description}</textarea>
		    </td>	
		</tr>      	   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG002D0002E_update" id="BSC_PROG002D0002E_update" onClick="BSC_PROG002D0002E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.perspectiveUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${perspective.oid}',		 
    						'fields.visionOid'		: dijit.byId('BSC_PROG002D0002E_visionOid').get('value'), 
    						'fields.name'			: dijit.byId('BSC_PROG002D0002E_name').get('value'),
    						'fields.weight'			: dijit.byId('BSC_PROG002D0002E_weight').get('value'),
    						'fields.target'			: dijit.byId('BSC_PROG002D0002E_target').get('value'),    						 
    						'fields.min'			: dijit.byId('BSC_PROG002D0002E_min').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG002D0002E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0002E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG002D0002E_clear" id="BSC_PROG002D0002E_clear" onClick="BSC_PROG002D0002E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
