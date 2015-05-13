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

var CORE_PROG001D0011Q_fieldsId = new Object();
CORE_PROG001D0011Q_fieldsId['mailFrom'] 		= 'CORE_PROG001D0011Q_mailFrom';

function CORE_PROG001D0011Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0011Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0011Q_fieldsId);		
		return;
	}	
	${programId}_TabRefresh();
}

function CORE_PROG001D0011Q_clear() {
	dijit.byId('CORE_PROG001D0011Q_mailFrom').set("value", "");	
	dijit.byId('CORE_PROG001D0011Q_mailEnable').set("checked", false);
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
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<table border="0" width="100%" height="200px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Mail system from</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0011Q_mailFrom" id="CORE_PROG001D0011Q_mailFrom" value="fields.mailFrom" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Mail sender enable</b>:
    			<br/>
    			<input id="CORE_PROG001D0011Q_mailEnable" name="CORE_PROG001D0011Q_mailEnable" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == fields.mailEnable "> checked="checked" </s:if> />
    		</td>    			
    	</tr>   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>System Template file re-write enable</b>:
    			<br/>
    			<input id="CORE_PROG001D0011Q_sysTemplateReWrite" name="CORE_PROG001D0011Q_sysTemplateReWrite" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == fields.sysTemplateReWrite "> checked="checked" </s:if> />
    		</td>    			
    	</tr>       	
    	<tr>
    		<td height="50px" width="100%"  align="left">
			    <gs:button name="CORE_PROG001D0011Q_save" id="CORE_PROG001D0011Q_save" onClick="CORE_PROG001D0011Q_save();"
	    			handleAs="json"
	    			sync="N"
	   				xhrUrl="${basePath}/core.settingsUpdateAction.action"
	   				parameterType="postData"
	   				xhrParameter="
	   					{
	   						'fields.mailFrom'			:	dijit.byId('CORE_PROG001D0011Q_mailFrom').get('value'),	   						
	   						'fields.mailEnable'			:	( dijit.byId('CORE_PROG001D0011Q_mailEnable').checked ? 'true' : 'false' ),
	   						'fields.sysTemplateReWrite'	:	( dijit.byId('CORE_PROG001D0011Q_sysTemplateReWrite').checked ? 'true' : 'false' )
	   					}
					"
	   				errorFn=""
    				loadFn="CORE_PROG001D0011Q_saveSuccess(data);" 
	    			programId="${programId}"
	    			label="Save" 
	    			iconClass="dijitIconSave"></gs:button> 			
    			<gs:button name="CORE_PROG001D0011Q_clear" id="CORE_PROG001D0011Q_clear" onClick="CORE_PROG001D0011Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>      		
    		</td>
    	</tr>     	    	
    </table>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	