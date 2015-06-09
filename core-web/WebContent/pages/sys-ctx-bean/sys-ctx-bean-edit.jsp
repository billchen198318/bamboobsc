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

var CORE_PROG001D0009E_fieldsId = new Object();
CORE_PROG001D0009E_fieldsId['systemOid'] 	= 'CORE_PROG001D0009E_systemOid';
CORE_PROG001D0009E_fieldsId['className'] 	= 'CORE_PROG001D0009E_className';
CORE_PROG001D0009E_fieldsId['type'] 		= 'CORE_PROG001D0009E_type';

function CORE_PROG001D0009E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0009E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0009E_fieldsId);		
		return;
	}		
}

function CORE_PROG001D0009E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0009E_fieldsId);
	dijit.byId('CORE_PROG001D0009E_systemOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0009E_className').set("value", "");
	dijit.byId('CORE_PROG001D0009E_type').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0009E_description').set("value", "");
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
		saveJsMethod="CORE_PROG001D0009E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('CORE_PROG001D0009E_systemOid')"/></b>:
    			<br/>
    			<gs:select name="CORE_PROG001D0009E_systemOid" dataSource="sysMap" id="CORE_PROG001D0009E_systemOid" value="selectValue"></gs:select>
    		</td>    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('CORE_PROG001D0009E_className')"/></b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0009E_className" id="CORE_PROG001D0009E_className" value="sysCtxBean.className" width="400" maxlength="255"></gs:textBox>
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('CORE_PROG001D0009E_type')"/></b>:
    			<br/>
    			<gs:select name="CORE_PROG001D0009E_type" dataSource="typeMap" id="CORE_PROG001D0009E_type" value="sysCtxBean.type"></gs:select>
    		</td>    		
    	</tr>    		  	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<b><s:property value="getText('CORE_PROG001D0009E_description')"/></b>:
    			<br/>
    			<textarea id="CORE_PROG001D0009E_description" name="CORE_PROG001D0009E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${sysCtxBean.description}</textarea>
    		</td>    		
    	</tr>     	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0009E_update" id="CORE_PROG001D0009E_update" onClick="CORE_PROG001D0009E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemContextBeanUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysCtxBean.oid}',
    						'fields.systemOid'		: dijit.byId('CORE_PROG001D0009E_systemOid').value, 
    						'fields.className'		: dijit.byId('CORE_PROG001D0009E_className').get('value'),
    						'fields.type'			: dijit.byId('CORE_PROG001D0009E_type').value, 
    						'fields.description'	: dijit.byId('CORE_PROG001D0009E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0009E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0009E_update')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="CORE_PROG001D0009E_clear" id="CORE_PROG001D0009E_clear" onClick="CORE_PROG001D0009E_clear();" 
    				label="${action.getText('CORE_PROG001D0009E_clear')}" 
    				iconClass="dijitIconClear"></gs:button>      			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

