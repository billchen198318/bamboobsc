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

var CORE_PROG001D0002E_fieldsId = new Object();
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_progSystem']	= 'CORE_PROG001D0002E_progSystem';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_progId'] 		= 'CORE_PROG001D0002E_progId';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_name'] 			= 'CORE_PROG001D0002E_name';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_url'] 			= 'CORE_PROG001D0002E_url';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_itemType'] 		= 'CORE_PROG001D0002E_itemType';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_dialogW'] 		= 'CORE_PROG001D0002E_dialogW';
CORE_PROG001D0002E_fieldsId['CORE_PROG001D0002E_dialogH'] 		= 'CORE_PROG001D0002E_dialogH';

function CORE_PROG001D0002E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0002E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0002E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0002E_fieldsId);
		return;
	}	
}

function CORE_PROG001D0002E_clear() {	
	setFieldsBackgroundDefault(CORE_PROG001D0002E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002E_fieldsId);
	//dijit.byId('CORE_PROG001D0002E_progId').set("value", ""); readonly
	dijit.byId('CORE_PROG001D0002E_name').set("value", "");
	dijit.byId('CORE_PROG001D0002E_url').set("value", "");	
	dijit.byId('CORE_PROG001D0002E_editMode').set("checked", false);		
	dijit.byId('CORE_PROG001D0002E_progSystem').set("value", '${editProgSystemValue}');
	dijit.byId('CORE_PROG001D0002E_itemType').set("value", 'FOLDER');
	dijit.byId('CORE_PROG001D0002E_icon').set("value", '${editIconValue}');
	dijit.byId('CORE_PROG001D0002E_isDialog').set("checked", false);	
	dijit.byId('CORE_PROG001D0002E_dialogW').set("value", "");
	dijit.byId('CORE_PROG001D0002E_dialogH').set("value", "");	
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
		saveJsMethod="CORE_PROG001D0002E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
<form name="CORE_PROG001D0002E_form" id="CORE_PROG001D0002E_form" action="." method="post">	
<input type="hidden" name="fields.oid" id="CORE_PROG001D0002E_oid" value="${sysProg.oid}" />	
<input type="hidden" name="fields.CORE_PROG001D0002E_isWindow" id="CORE_PROG001D0002E_isWindow" value="${sysProg.isWindow}" />
	<table border="0" width="100%" height="550px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_progSystem')}" id="CORE_PROG001D0002E_progSystem" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG001D0002E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_progSystem"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="fields.CORE_PROG001D0002E_progSystem" dataSource="progSystemDataMap" id="CORE_PROG001D0002E_progSystem" value="editProgSystemValue" readonly="Y"></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_progId')}" id="CORE_PROG001D0002E_progId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG001D0002E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_progId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002E_progId" id="CORE_PROG001D0002E_progId" value="sysProg.progId" width="200" maxlength="50" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_name')}" id="CORE_PROG001D0002E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002E_name" id="CORE_PROG001D0002E_name" value="sysProg.name" width="300" maxlength="100"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_url')}" id="CORE_PROG001D0002E_url"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_url"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002E_url" id="CORE_PROG001D0002E_url" value="sysProg.url" width="400" maxlength="255"></gs:textBox>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_editMode')}" id="CORE_PROG001D0002E_editMode"></gs:label>
    			<br/>
				<input id="CORE_PROG001D0002E_editMode" name="fields.CORE_PROG001D0002E_editMode" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysProg.editMode "> checked </s:if> />   		
    		</td>
    	</tr>    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_isDialog')}" id="CORE_PROG001D0002E_isDialog"></gs:label>
    			<br/>
				<input id="CORE_PROG001D0002E_isDialog" name="fields.CORE_PROG001D0002E_isDialog" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysProg.isDialog "> checked </s:if> />   		
    		</td>
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_dialogW')}" id="CORE_PROG001D0002E_dialogW"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_dialogW"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002E_dialogW" id="CORE_PROG001D0002E_dialogW" value="sysProg.dialogW" width="50" maxlength="4"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_dialogH')}" id="CORE_PROG001D0002E_dialogH"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_dialogH"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002E_dialogH" id="CORE_PROG001D0002E_dialogH" value="sysProg.dialogH" width="50" maxlength="4"></gs:textBox>
    		</td>
    	</tr>        	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_itemType')}" id="CORE_PROG001D0002E_itemType"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_itemType"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="fields.CORE_PROG001D0002E_itemType" dataSource="{ \"FOLDER\":\"FOLDER\", \"ITEM\":\"ITEM\" }" id="CORE_PROG001D0002E_itemType" value="sysProg.itemType"></gs:select>
    		</td>
    	</tr>    	    	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002E_icon')}" id="CORE_PROG001D0002E_icon"></gs:label>
    			<br/>
    			<gs:select id="CORE_PROG001D0002E_icon" name="fields.CORE_PROG001D0002E_icon" dataSource="iconDataMap" value="editIconValue"></gs:select>	
    		</td>
    	</tr>    
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0002E_update" id="CORE_PROG001D0002E_update" onClick="CORE_PROG001D0002E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemProgramUpdateAction.action"
    				parameterType="form"
    				xhrParameter="CORE_PROG001D0002E_form"
    				errorFn=""
    				loadFn="CORE_PROG001D0002E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0002E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0002E_clear" id="CORE_PROG001D0002E_clear" onClick="CORE_PROG001D0002E_clear();" 
    				label="${action.getText('CORE_PROG001D0002E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>    		
	</table>	
</form>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	
	