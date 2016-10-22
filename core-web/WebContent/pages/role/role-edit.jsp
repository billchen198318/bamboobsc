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

var CORE_PROG002D0001E_fieldsId = new Object();
CORE_PROG002D0001E_fieldsId['role'] 		= 'CORE_PROG002D0001E_role';
CORE_PROG002D0001E_fieldsId['description'] 	= 'CORE_PROG002D0001E_description';

function CORE_PROG002D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG002D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG002D0001E_fieldsId);		
		return;
	}		
}

function CORE_PROG002D0001E_clear() {
	setFieldsBackgroundDefault(CORE_PROG002D0001E_fieldsId);
	//dijit.byId('CORE_PROG002D0001E_role').set("value", ""); // readonly
	dijit.byId('CORE_PROG002D0001E_description').set("value", "");
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
		saveJsMethod="CORE_PROG002D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG002D0001E_role')}" id="CORE_PROG002D0001E_role" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG002D0001E_readOnly')"/>
    			<br/>
    			<gs:textBox name="CORE_PROG002D0001E_role" id="CORE_PROG002D0001E_role" value="role.role" width="200" maxlength="50" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG002D0001E_description')}" id="CORE_PROG002D0001E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG002D0001E_description" name="CORE_PROG002D0001E_description" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px"><s:property value="role.description"/></textarea>	
    		</td>
    	</tr>	    
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG002D0001E_update" id="CORE_PROG002D0001E_update" onClick="CORE_PROG002D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.roleUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${role.oid}',
    						'fields.role'			: dijit.byId('CORE_PROG002D0001E_role').get('value'), 
    						'fields.description'	: dijit.byId('CORE_PROG002D0001E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG002D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG002D0001E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG002D0001E_clear" id="CORE_PROG002D0001E_clear" onClick="CORE_PROG002D0001E_clear();" 
    				label="${action.getText('CORE_PROG002D0001E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>      	
    </table>		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	