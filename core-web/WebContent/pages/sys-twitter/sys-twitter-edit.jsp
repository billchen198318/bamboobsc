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

var CORE_PROG001D0010E_fieldsId = new Object();
//CORE_PROG001D0010E_fieldsId['systemOid'] 		= 'CORE_PROG001D0010E_systemOid'; // readOnly
CORE_PROG001D0010E_fieldsId['title'] 			= 'CORE_PROG001D0010E_title';
CORE_PROG001D0010E_fieldsId['content'] 			= 'CORE_PROG001D0010E_content';

function CORE_PROG001D0010E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0010E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0010E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0010E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0010E_fieldsId);
		return;
	}		
}

function CORE_PROG001D0010E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0010E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0010E_fieldsId);
	//dijit.byId('CORE_PROG001D0010E_systemOid').set("value", _gscore_please_select_id); // readOnly
	dijit.byId('CORE_PROG001D0010E_title').set("value", "");
	dijit.byId('CORE_PROG001D0010E_content').set("value", "");		
	dijit.byId('CORE_PROG001D0010E_enableFlag').set("checked", true);
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
		saveJsMethod="CORE_PROG001D0010E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="405px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010E_systemOid')}" id="CORE_PROG001D0010E_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010E_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0010E_systemOid" dataSource="sysMap" id="CORE_PROG001D0010E_systemOid" readonly="Y" value="fields.systemOid"></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010E_title')}" id="CORE_PROG001D0010E_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010E_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0010E_title" id="CORE_PROG001D0010E_title" value="sysTwitter.title" width="400" maxlength="200"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010E_enableFlag')}" id="CORE_PROG001D0010E_enableFlag"></gs:label>
    			<br/>
				<input id="CORE_PROG001D0010E_enableFlag" name="CORE_PROG001D0010E_enableFlag" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysTwitter.enableFlag "> checked="checked" </s:if> />   		
    		</td>
    	</tr>      	
		<tr>
    		<td height="205px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0010E_content')}" id="CORE_PROG001D0010E_content" requiredFlag="Y"></gs:label>
    			&nbsp;(escape script mode)<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0010E_content"></gs:inputfieldNoticeMsgLabel>
    			<br/>    	
    			<textarea id="CORE_PROG001D0010E_content" name="CORE_PROG001D0010E_content" data-dojo-type="dijit/form/Textarea" rows="8" cols="60" style="width:600px;height:170px;max-height:180px"><s:property value="fields.content" escapeJavaScript="true"/></textarea>	
    		</td>
    	</tr>    	 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0010E_update" id="CORE_PROG001D0010E_update" onClick="CORE_PROG001D0010E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemTwitterUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysTwitter.oid}',
    						'fields.systemOid'		: dijit.byId('CORE_PROG001D0010E_systemOid').get('value'), 
    						'fields.title'			: dijit.byId('CORE_PROG001D0010E_title').get('value'),
    						'fields.enableFlag'		: ( dijit.byId('CORE_PROG001D0010E_enableFlag').checked ? 'true' : 'false' ),    						
    						'fields.content'		: dijit.byId('CORE_PROG001D0010E_content').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0010E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0010E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0010E_clear" id="CORE_PROG001D0010E_clear" onClick="CORE_PROG001D0010E_clear();" 
    				label="${action.getText('CORE_PROG001D0010E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
