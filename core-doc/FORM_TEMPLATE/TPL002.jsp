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

var CORE_PROG001D0015E_fieldsId = new Object();
CORE_PROG001D0015E_fieldsId['name'] 			= 'CORE_PROG001D0015E_name';

function CORE_PROG001D0015E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0015E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0015E_fieldsId);		
		return;
	}		
}

function CORE_PROG001D0015E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0015E_fieldsId);
	dijit.byId('CORE_PROG001D0015E_name').set("value", "");	
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
		saveJsMethod="CORE_PROG001D0015E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0015E_name" id="CORE_PROG001D0015E_name" value="datas.sysCode.name" width="400" maxlength="200"></gs:textBox>
    		</td>
    	</tr>		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0015E_update" id="CORE_PROG001D0015E_update" onClick="CORE_PROG001D0015E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.commomLoadForm.action?prog_id=CORE_PROG001D0015E&form_id=FORM002&form_method=update"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${datas.sysCode.oid}',
    						'fields.name'			: dijit.byId('CORE_PROG001D0015E_name').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0015E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="CORE_PROG001D0015E_clear" id="CORE_PROG001D0015E_clear" onClick="CORE_PROG001D0015E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
