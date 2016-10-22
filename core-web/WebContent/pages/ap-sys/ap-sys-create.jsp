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

var CORE_PROG001D0001A_fieldsId = new Object();
CORE_PROG001D0001A_fieldsId['sysId'] 		= 'CORE_PROG001D0001A_sysId';
CORE_PROG001D0001A_fieldsId['name'] 		= 'CORE_PROG001D0001A_name';
CORE_PROG001D0001A_fieldsId['host'] 		= 'CORE_PROG001D0001A_host';
CORE_PROG001D0001A_fieldsId['contextPath'] 	= 'CORE_PROG001D0001A_contextPath';

function CORE_PROG001D0001A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0001A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0001A_fieldsId);
		return;
	}	
	CORE_PROG001D0001A_clear();
}

function CORE_PROG001D0001A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0001A_fieldsId);
	dijit.byId('CORE_PROG001D0001A_sysId').set("value", "");
	dijit.byId('CORE_PROG001D0001A_name').set("value", "");
	dijit.byId('CORE_PROG001D0001A_host').set("value", "");
	dijit.byId('CORE_PROG001D0001A_contextPath').set("value", "");
	dijit.byId('CORE_PROG001D0001A_isLocal').set("checked", false);
	dijit.byId('CORE_PROG001D0001A_icon').set("value", '${firstIconValue}');
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
		saveJsMethod="CORE_PROG001D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="350px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_sysId')}" id="CORE_PROG001D0001A_sysId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0001A_sysId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0001A_sysId" id="CORE_PROG001D0001A_sysId" value="" width="200" maxlength="10"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_name')}" id="CORE_PROG001D0001A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0001A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0001A_name" id="CORE_PROG001D0001A_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_host')}" id="CORE_PROG001D0001A_host" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0001A_host"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0001A_host" id="CORE_PROG001D0001A_host" value="" width="200" maxlength="200"></gs:textBox>
    		</td>    		
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_contextPath')}" id="CORE_PROG001D0001A_contextPath" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0001A_contextPath"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0001A_contextPath" id="CORE_PROG001D0001A_contextPath" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_isLocal')}" id="CORE_PROG001D0001A_isLocal"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0001A_isLocal" name="CORE_PROG001D0001A_isLocal" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0001A_icon')}" id="CORE_PROG001D0001A_icon"></gs:label>
    			<br/>
    			<gs:select id="CORE_PROG001D0001A_icon" name="CORE_PROG001D0001A_icon" 
    				dataSource="iconDataMap" >
    			</gs:select>	    			
    		</td>
    	</tr>     	 	  	   
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0001A_save" id="CORE_PROG001D0001A_save" onClick="CORE_PROG001D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.applicationSystemSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.sysId'			: dijit.byId('CORE_PROG001D0001A_sysId').get('value'), 
    						'fields.name'			: dijit.byId('CORE_PROG001D0001A_name').get('value'),
    						'fields.host'			: dijit.byId('CORE_PROG001D0001A_host').get('value'),
    						'fields.contextPath'	: dijit.byId('CORE_PROG001D0001A_contextPath').get('value'),
    						'fields.isLocal'		: dijit.byId('CORE_PROG001D0001A_isLocal').get('value'),
    						'fields.icon'			: dijit.byId('CORE_PROG001D0001A_icon').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0001A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0001A_clear" id="CORE_PROG001D0001A_clear" onClick="CORE_PROG001D0001A_clear();" 
    				label="${action.getText('CORE_PROG001D0001A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>        		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
