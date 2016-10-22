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

var CORE_PROG001D0010A_fieldsId = new Object();
CORE_PROG001D0010A_fieldsId['systemOid'] 		= 'CORE_PROG001D0010A_systemOid';
CORE_PROG001D0010A_fieldsId['title'] 			= 'CORE_PROG001D0010A_title';
CORE_PROG001D0010A_fieldsId['content'] 			= 'CORE_PROG001D0010A_content';

function CORE_PROG001D0010A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0010A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0010A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0010A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0010A_fieldsId);
		return;
	}	
	CORE_PROG001D0010A_clear();
}

function CORE_PROG001D0010A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0010A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0010A_fieldsId);
	dijit.byId('CORE_PROG001D0010A_systemOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0010A_title').set("value", "");
	dijit.byId('CORE_PROG001D0010A_content').set("value", "");		
	dijit.byId('CORE_PROG001D0010A_enableFlag').set("checked", true);
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
		saveJsMethod="CORE_PROG001D0010A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="405px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010A_systemOid')}" id="CORE_PROG001D0010A_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010A_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0010A_systemOid" dataSource="sysMap" id="CORE_PROG001D0010A_systemOid"></gs:select>
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010A_title')}" id="CORE_PROG001D0010A_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010A_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0010A_title" id="CORE_PROG001D0010A_title" value="" width="400" maxlength="200"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010A_enableFlag')}" id="CORE_PROG001D0010A_enableFlag"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0010A_enableFlag" name="CORE_PROG001D0010A_enableFlag" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
    		</td>    		
    	</tr>
		<tr>
    		<td height="205px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010A_content')}" id="CORE_PROG001D0010A_content" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010A_content"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<textarea id="CORE_PROG001D0010A_content" name="CORE_PROG001D0010A_content" data-dojo-type="dijit/form/Textarea" rows="8" cols="200" style="width:600px;height:170px;max-height:180px"></textarea>
    		</td>    		
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0010A_save" id="CORE_PROG001D0010A_save" onClick="CORE_PROG001D0010A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemTwitterSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.systemOid'		: dijit.byId('CORE_PROG001D0010A_systemOid').get('value'), 
    						'fields.title'			: dijit.byId('CORE_PROG001D0010A_title').get('value'),
    						'fields.enableFlag'		: ( dijit.byId('CORE_PROG001D0010A_enableFlag').checked ? 'true' : 'false' ),    						
    						'fields.content'		: dijit.byId('CORE_PROG001D0010A_content').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0010A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0010A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0010A_clear" id="CORE_PROG001D0010A_clear" onClick="CORE_PROG001D0010A_clear();" 
    				label="${action.getText('CORE_PROG001D0010A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>      		
    		</td>
    	</tr>      	    	    	
    </table>		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	