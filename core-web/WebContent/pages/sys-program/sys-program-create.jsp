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

var CORE_PROG001D0002A_fieldsId = new Object();
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_progSystem']	= 'CORE_PROG001D0002A_progSystem';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_progId'] 		= 'CORE_PROG001D0002A_progId';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_name'] 			= 'CORE_PROG001D0002A_name';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_url'] 			= 'CORE_PROG001D0002A_url';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_itemType'] 		= 'CORE_PROG001D0002A_itemType';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_dialogW'] 		= 'CORE_PROG001D0002A_dialogW';
CORE_PROG001D0002A_fieldsId['CORE_PROG001D0002A_dialogH'] 		= 'CORE_PROG001D0002A_dialogH';

function CORE_PROG001D0002A_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0002A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0002A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0002A_fieldsId);
		return;
	}	
	CORE_PROG001D0002A_clear();	
}

function CORE_PROG001D0002A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0002A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002A_fieldsId);
	dijit.byId('CORE_PROG001D0002A_progId').set("value", "");
	dijit.byId('CORE_PROG001D0002A_name').set("value", "");
	dijit.byId('CORE_PROG001D0002A_url').set("value", "");	
	dijit.byId('CORE_PROG001D0002A_editMode').set("checked", false);		
	dijit.byId('CORE_PROG001D0002A_progSystem').set("value", '${firstProgSystemValue}');
	dijit.byId('CORE_PROG001D0002A_itemType').set("value", 'FOLDER');
	dijit.byId('CORE_PROG001D0002A_icon').set("value", '${firstIconValue}');
	dijit.byId('CORE_PROG001D0002A_isDialog').set("checked", false);	
	dijit.byId('CORE_PROG001D0002A_dialogW').set("value", "");
	dijit.byId('CORE_PROG001D0002A_dialogH').set("value", "");
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
		saveJsMethod="CORE_PROG001D0002A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>

<form name="CORE_PROG001D0002A_form" id="CORE_PROG001D0002A_form" action="." method="post">	
	
	<input type="hidden" id="CORE_PROG001D0002A_isWindow" name="fields.CORE_PROG001D0002A_isWindow" value="N" />
	
	<table border="0" width="100%" height="550px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_progSystem')}" id="CORE_PROG001D0002A_progSystem" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_progSystem"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="fields.CORE_PROG001D0002A_progSystem" dataSource="progSystemDataMap" id="CORE_PROG001D0002A_progSystem"></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_progId')}" id="CORE_PROG001D0002A_progId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_progId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002A_progId" id="CORE_PROG001D0002A_progId" value="" width="200" maxlength="50"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_name')}" id="CORE_PROG001D0002A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002A_name" id="CORE_PROG001D0002A_name" value="" width="300" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_url')}" id="CORE_PROG001D0002A_url"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_url"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002A_url" id="CORE_PROG001D0002A_url" value="" width="400" maxlength="255"></gs:textBox>
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_editMode')}" id="CORE_PROG001D0002A_editMode"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0002A_editMode" name="fields.CORE_PROG001D0002A_editMode" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_isDialog')}" id="CORE_PROG001D0002A_isDialog"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0002A_isDialog" name="fields.CORE_PROG001D0002A_isDialog" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>    		
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_dialogW')}" id="CORE_PROG001D0002A_dialogW"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_dialogW"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002A_dialogW" id="CORE_PROG001D0002A_dialogW" value="" width="50" maxlength="4"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_dialogH')}" id="CORE_PROG001D0002A_dialogH"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_dialogH"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="fields.CORE_PROG001D0002A_dialogH" id="CORE_PROG001D0002A_dialogH" value="" width="50" maxlength="4"></gs:textBox>
    		</td>    		
    	</tr>    	 		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_itemType')}" id="CORE_PROG001D0002A_itemType"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002A_itemType"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="fields.CORE_PROG001D0002A_itemType" dataSource="{ \"FOLDER\":\"FOLDER\", \"ITEM\":\"ITEM\" }" id="CORE_PROG001D0002A_itemType"></gs:select>
    		</td>    		
    	</tr>    	    	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0002A_icon')}" id="CORE_PROG001D0002A_icon"></gs:label>
    			<br/>
    			<gs:select id="CORE_PROG001D0002A_icon" name="fields.CORE_PROG001D0002A_icon" dataSource="iconDataMap" ></gs:select>
    		</td>    		
    	</tr>    
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0002A_save" id="CORE_PROG001D0002A_save" onClick="CORE_PROG001D0002A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemProgramSaveAction.action"
    				parameterType="form"
    				xhrParameter="CORE_PROG001D0002A_form"
    				errorFn=""
    				loadFn="CORE_PROG001D0002A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0002A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button> 			
    			<gs:button name="CORE_PROG001D0002A_clear" id="CORE_PROG001D0002A_clear" onClick="CORE_PROG001D0002A_clear();" 
    				label="${action.getText('CORE_PROG001D0002A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    	    		    		
    		</td>
    	</tr>    	    	    	    	    	
	</table>    		
</form>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	