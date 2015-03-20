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

var CORE_PROG001D0005A_fieldsId = new Object();
CORE_PROG001D0005A_fieldsId['systemOid'] 	= 'CORE_PROG001D0005A_system';
CORE_PROG001D0005A_fieldsId['msgId'] 		= 'CORE_PROG001D0005A_msgId';
CORE_PROG001D0005A_fieldsId['className'] 	= 'CORE_PROG001D0005A_className';

function CORE_PROG001D0005A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0005A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0005A_fieldsId);		
		return;
	}	
	CORE_PROG001D0005A_clear();
}

function CORE_PROG001D0005A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0005A_fieldsId);
	dijit.byId('CORE_PROG001D0005A_system').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0005A_msgId').set("value", "");
	dijit.byId('CORE_PROG001D0005A_className').set("value", "");
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
		saveJsMethod="CORE_PROG001D0005A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="200px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>System</b>:
    			<br/>
    			<gs:select name="CORE_PROG001D0005A_system" dataSource="systemDataMap" id="CORE_PROG001D0005A_system"></gs:select>	
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0005A_msgId" id="CORE_PROG001D0005A_msgId" value="" width="200" maxlength="10"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Class</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0005A_className" id="CORE_PROG001D0005A_className" value="" width="400" maxlength="255"></gs:textBox>
    		</td>
    	</tr>   
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0005A_save" id="CORE_PROG001D0005A_save" onClick="CORE_PROG001D0005A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemMessageNoticeConfigSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.systemOid'		: dijit.byId('CORE_PROG001D0005A_system').get('value'), 
    						'fields.msgId'			: dijit.byId('CORE_PROG001D0005A_msgId').get('value'),
    						'fields.className'		: dijit.byId('CORE_PROG001D0005A_className').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0005A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="CORE_PROG001D0005A_clear" id="CORE_PROG001D0005A_clear" onClick="CORE_PROG001D0005A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>      		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
