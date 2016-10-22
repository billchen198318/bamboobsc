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

var BSC_PROG001D0001E_S00_fieldsId = new Object();
BSC_PROG001D0001E_S00_fieldsId['password1'] 	= 'BSC_PROG001D0001E_S00_password1';
BSC_PROG001D0001E_S00_fieldsId['password2'] 	= 'BSC_PROG001D0001E_S00_password2';
BSC_PROG001D0001E_S00_fieldsId['password3'] 	= 'BSC_PROG001D0001E_S00_password3';

function BSC_PROG001D0001E_S00_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0001E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001E_S00_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0001E_S00_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0001E_S00_fieldsId);
		return;
	}	
	BSC_PROG001D0001E_S00_clear();
	BSC_PROG001D0001E_S00_DlgHide();
}

function BSC_PROG001D0001E_S00_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0001E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001E_S00_fieldsId);
	dijit.byId('BSC_PROG001D0001E_S00_password1').set("value", "");
	dijit.byId('BSC_PROG001D0001E_S00_password2').set("value", "");	
	dijit.byId('BSC_PROG001D0001E_S00_password3').set("value", "");	
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

	<!-- 
	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="BSC_PROG001D0001E_S00_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG001D0001E_S00_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="BSC_PROG001D0001E_S00_DlgShow('${employee.oid}');" 		
		></gs:toolBar>
	-->	
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG001D0001E_S00_appendOrganizationOid" id="BSC_PROG001D0001E_S00_appendOrganizationOid" value="${appendId}" />
	
	<table border="0" width="100%" height="175px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_S00_password1')}" id="BSC_PROG001D0001E_S00_password1"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_S00_password1"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input name="BSC_PROG001D0001E_S00_password1" id="BSC_PROG001D0001E_S00_password1" type="password" trim="true" maxlength="14" 
					data-dojo-type="dijit.form.TextBox" data-dojo-props='style:"width: 200px;" ' />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_S00_password1'">
    				Input old password.
				</div>
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_S00_password2')}" id="BSC_PROG001D0001E_S00_password2"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_S00_password2"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input name="BSC_PROG001D0001E_S00_password2" id="BSC_PROG001D0001E_S00_password2" type="password" trim="true" maxlength="14" 
					data-dojo-type="dijit.form.TextBox" data-dojo-props='style:"width: 200px;" ' />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_S00_password2'">
    				Input new password.
				</div>
    		</td>
    	</tr>  	 
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_S00_password3')}" id="BSC_PROG001D0001E_S00_password3"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_S00_password3"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input name="BSC_PROG001D0001E_S00_password3" id="BSC_PROG001D0001E_S00_password3" type="password" trim="true" maxlength="14" 
					data-dojo-type="dijit.form.TextBox" data-dojo-props='style:"width: 200px;" ' />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_S00_password3'">
    				Input new password. ( confirm )
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td height="25px" align="left">
    			<gs:button name="BSC_PROG001D0001E_S00_update" id="BSC_PROG001D0001E_S00_update" onClick="BSC_PROG001D0001E_S00_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.employeeUpdatePasswordAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${employee.oid}',		 
    						'fields.password1'		: dijit.byId('BSC_PROG001D0001E_S00_password1').get('value'),
    						'fields.password2'		: dijit.byId('BSC_PROG001D0001E_S00_password2').get('value'),
    						'fields.password3'		: dijit.byId('BSC_PROG001D0001E_S00_password3').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0001E_S00_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0001E_S00_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0001E_S00_clear" id="BSC_PROG001D0001E_S00_clear" onClick="BSC_PROG001D0001E_S00_clear();" 
    				label="${action.getText('BSC_PROG001D0001E_S00_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
