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

var CORE_PROG003D0003E_fieldsId = new Object();
//CORE_PROG003D0003E_fieldsId['systemOid'] 		= 'CORE_PROG003D0003E_systemOid'; // readOnly
//CORE_PROG003D0003E_fieldsId['beanId'] 		= 'CORE_PROG003D0003E_beanId';	// readOnly
//CORE_PROG003D0003E_fieldsId['method'] 		= 'CORE_PROG003D0003E_method';	// readOnly

function CORE_PROG003D0003E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0003E_fieldsId);		
		return;
	}		
}

function CORE_PROG003D0003E_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_fieldsId);
	//dijit.byId('CORE_PROG003D0003E_systemOid').set("value", _gscore_please_select_id); // readOnly
	//dijit.byId('CORE_PROG003D0003E_beanId').set("value", ""); // readOnly
	//dijit.byId('CORE_PROG003D0003E_method').set("value", ""); // readOnly
	dijit.byId('CORE_PROG003D0003E_enableFlag').set("checked", false);		
	dijit.byId('CORE_PROG003D0003E_description').set("value", "");	
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
		saveJsMethod="CORE_PROG003D0003E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="475px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_systemOid')}" id="CORE_PROG003D0003E_systemOid" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG003D0003E_readOnly')"/>
    			<br/>
    			<gs:select name="CORE_PROG003D0003E_systemOid" dataSource="sysMap" id="CORE_PROG003D0003E_systemOid" value="selectValue" readonly="Y"></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_beanId')}" id="CORE_PROG003D0003E_beanId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG003D0003E_readOnly')"/>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_beanId" id="CORE_PROG003D0003E_beanId" value="sysBeanHelp.beanId" width="400" maxlength="255" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_method')}" id="CORE_PROG003D0003E_method" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG003D0003E_readOnly')"/>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_method" id="CORE_PROG003D0003E_method" value="sysBeanHelp.method" width="200" maxlength="100" readonly="Y"></gs:textBox>
    		</td>
    	</tr>      	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_enableFlag')}" id="CORE_PROG003D0003E_enableFlag"></gs:label>
    			<br/>
				<input id="CORE_PROG003D0003E_enableFlag" name="CORE_PROG003D0003E_enableFlag" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysBeanHelp.enableFlag "> checked="checked" </s:if> />   		
    		</td>
    	</tr>    	    	    	    
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_description')}" id="CORE_PROG003D0003E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0003E_description" name="CORE_PROG003D0003E_description" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px">${sysBeanHelp.description}</textarea>	
    		</td>
    	</tr>     	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0003E_update" id="CORE_PROG003D0003E_update" onClick="CORE_PROG003D0003E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemBeanHelpUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysBeanHelp.oid}',
    						'fields.systemOid'		: dijit.byId('CORE_PROG003D0003E_systemOid').get('value'), 
    						'fields.beanId'			: dijit.byId('CORE_PROG003D0003E_beanId').get('value'),
    						'fields.method'			: dijit.byId('CORE_PROG003D0003E_method').get('value'),
    						'fields.enableFlag'		: ( dijit.byId('CORE_PROG003D0003E_enableFlag').checked ? 'true' : 'false' ),
    						'fields.description'	: dijit.byId('CORE_PROG003D0003E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0003E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0003E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0003E_clear" id="CORE_PROG003D0003E_clear" onClick="CORE_PROG003D0003E_clear();" 
    				label="${action.getText('CORE_PROG003D0003E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

