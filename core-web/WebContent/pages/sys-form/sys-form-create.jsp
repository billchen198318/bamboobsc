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

var CORE_PROG001D0013A_fieldsId = new Object();
CORE_PROG001D0013A_fieldsId['formId'] 		= 'CORE_PROG001D0013A_formId';
CORE_PROG001D0013A_fieldsId['templateOid'] 	= 'CORE_PROG001D0013A_templateOid';
CORE_PROG001D0013A_fieldsId['name'] 		= 'CORE_PROG001D0013A_name';

function CORE_PROG001D0013A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0013A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0013A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0013A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0013A_fieldsId);
		return;
	}	
	CORE_PROG001D0013A_clear();
}

function CORE_PROG001D0013A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0013A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0013A_fieldsId);
	dijit.byId('CORE_PROG001D0013A_formId').set("value", "");
	dijit.byId('CORE_PROG001D0013A_templateOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0013A_name').set("value", "");
	dijit.byId('CORE_PROG001D0013A_description').set("value", "");	

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
		saveJsMethod="CORE_PROG001D0013A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0013A_formId')}" id="CORE_PROG001D0013A_formId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0013A_formId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0013A_formId" id="CORE_PROG001D0013A_formId" value="" width="200" maxlength="50"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0013A_templateOid')}" id="CORE_PROG001D0013A_templateOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0013A_templateOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0013A_templateOid" dataSource="templateMap" id="CORE_PROG001D0013A_templateOid"></gs:select>
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0013A_name')}" id="CORE_PROG001D0013A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0013A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0013A_name" id="CORE_PROG001D0013A_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	    	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0013A_description')}" id="CORE_PROG001D0013A_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG001D0013A_description" name="CORE_PROG001D0013A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
    		</td>    		
    	</tr>     	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0013A_save" id="CORE_PROG001D0013A_save" onClick="CORE_PROG001D0013A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemFormSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.formId'			: dijit.byId('CORE_PROG001D0013A_formId').get('value'),
    						'fields.templateOid'	: dijit.byId('CORE_PROG001D0013A_templateOid').get('value'),
    						'fields.name'			: dijit.byId('CORE_PROG001D0013A_name').get('value'),    						
    						'fields.description'	: dijit.byId('CORE_PROG001D0013A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0013A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0013A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0013A_clear" id="CORE_PROG001D0013A_clear" onClick="CORE_PROG001D0013A_clear();" 
    				label="${action.getText('CORE_PROG001D0013A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
