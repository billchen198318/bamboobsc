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

var CORE_PROG001D0006E_fieldsId = new Object();
//CORE_PROG001D0006E_fieldsId['msgOid'] 		= 'CORE_PROG001D0006E_msgOid';			// readOnly
//CORE_PROG001D0006E_fieldsId['noticeId'] 		= 'CORE_PROG001D0006E_noticeId';		// readOnly
CORE_PROG001D0006E_fieldsId['title'] 			= 'CORE_PROG001D0006E_title';
CORE_PROG001D0006E_fieldsId['message'] 			= 'CORE_PROG001D0006E_message';
CORE_PROG001D0006E_fieldsId['date1'] 			= 'CORE_PROG001D0006E_date1';
CORE_PROG001D0006E_fieldsId['date2'] 			= 'CORE_PROG001D0006E_date2';
CORE_PROG001D0006E_fieldsId['startHour'] 		= 'CORE_PROG001D0006E_time_startHour';
CORE_PROG001D0006E_fieldsId['startMinutes'] 	= 'CORE_PROG001D0006E_time_startMinutes';
CORE_PROG001D0006E_fieldsId['endHour'] 			= 'CORE_PROG001D0006E_time_endHour';
CORE_PROG001D0006E_fieldsId['endMinutes'] 		= 'CORE_PROG001D0006E_time_endMinutes';
CORE_PROG001D0006E_fieldsId['toAccountOid'] 	= 'CORE_PROG001D0006E_toAccountOid';

function CORE_PROG001D0006E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0006E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0006E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0006E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0006E_fieldsId);
		return;
	}		
}

function CORE_PROG001D0006E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0006E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0006E_fieldsId);
	//dijit.byId('CORE_PROG001D0006E_msgOid').set("value", _gscore_please_select_id);	// readOnly
	//dijit.byId('CORE_PROG001D0006E_noticeId').set("value", "");						// readOnly
	dijit.byId('CORE_PROG001D0006E_title').set("value", "");
	dijit.byId('CORE_PROG001D0006E_message').set("value", "");
	dijit.byId('CORE_PROG001D0006E_date1').set("displayedValue", "");
	dijit.byId('CORE_PROG001D0006E_date2').set("displayedValue", "");
	dijit.byId('CORE_PROG001D0006E_time_startHour').set("value", "00");
	dijit.byId('CORE_PROG001D0006E_time_startMinutes').set("value", "00");
	dijit.byId('CORE_PROG001D0006E_time_endHour').set("value", "00");
	dijit.byId('CORE_PROG001D0006E_time_endMinutes').set("value", "00");
	dijit.byId('CORE_PROG001D0006E_isGlobal').set("checked", false);
	dijit.byId('CORE_PROG001D0006E_toAccountOid').set("value", _gscore_please_select_id);
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
		saveJsMethod="CORE_PROG001D0006E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="625px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_msgOid')}" id="CORE_PROG001D0006E_msgOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_msgOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0006E_msgOid" dataSource="msgDataMap" id="CORE_PROG001D0006E_msgOid" value="selectConfigOid" readonly="Y"></gs:select>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_noticeId')}" id="CORE_PROG001D0006E_noticeId" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_noticeId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0006E_noticeId" id="CORE_PROG001D0006E_noticeId" value="sysMsgNotice.noticeId" width="200" maxlength="13" readonly="Y"></gs:textBox>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_title')}" id="CORE_PROG001D0006E_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0006E_title" id="CORE_PROG001D0006E_title" value="sysMsgNotice.title" width="400" maxlength="100"></gs:textBox>
    		</td>
    	</tr> 	
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_message')}" id="CORE_PROG001D0006E_message" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_message"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<textarea id="CORE_PROG001D0006E_message" name="CORE_PROG001D0006E_message" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px">${sysMsgNotice.message}</textarea>	
    		</td>
    	</tr>	 	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_date')}" id="CORE_PROG001D0006E_date" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_date1"></gs:inputfieldNoticeMsgLabel><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_date2"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			
    			<gs:label text="${action.getText('CORE_PROG001D0006E_date1')}" id="CORE_PROG001D0006E_date1"></gs:label>
    			<input id="CORE_PROG001D0006E_date1" type="text" name="CORE_PROG001D0006E_date1" data-dojo-type="dijit.form.DateTextBox" maxlength="10" constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" required="true" style="width:120px;" value="${date1}"/>
    			&nbsp;
    			~
    			&nbsp;
    			<gs:label text="${action.getText('CORE_PROG001D0006E_date2')}" id="CORE_PROG001D0006E_date2"></gs:label>
    			<input id="CORE_PROG001D0006E_date2" type="text" name="CORE_PROG001D0006E_date2" data-dojo-type="dijit.form.DateTextBox" maxlength="10" constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" required="true" style="width:120px;" value="${date2}"/>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_time')}" id="CORE_PROG001D0006E_time"></gs:label>
    			<br/>
    			<b><s:property value="getText('CORE_PROG001D0006E_timeStart')"/></b>&nbsp;
    			<s:property value="getText('CORE_PROG001D0006E_time_startHour')"/><gs:select name="CORE_PROG001D0006E_time_startHour" dataSource="hourMap" id="CORE_PROG001D0006E_time_startHour" value="startHour" width="60"></gs:select>
    			<s:property value="getText('CORE_PROG001D0006E_time_startMinutes')"/><gs:select name="CORE_PROG001D0006E_time_startMinutes" dataSource="minutesMap" id="CORE_PROG001D0006E_time_startMinutes" value="startMinutes" width="60"></gs:select>
    			&nbsp;&nbsp;&nbsp;
    			
    			<b><s:property value="getText('CORE_PROG001D0006E_timeEnd')"/></b>&nbsp;
    			<s:property value="getText('CORE_PROG001D0006E_time_endHour')"/><gs:select name="CORE_PROG001D0006E_time_endHour" dataSource="hourMap" id="CORE_PROG001D0006E_time_endHour" value="endHour" width="60"></gs:select>
    			<s:property value="getText('CORE_PROG001D0006E_time_endMinutes')"/><gs:select name="CORE_PROG001D0006E_time_endMinutes" dataSource="minutesMap" id="CORE_PROG001D0006E_time_endMinutes" value="endMinutes" width="60"></gs:select>    			
    			
    		</td>
    		<!-- Time: 下拉-起時 下拉-起分 / 下拉-迄時 下拉-迄分 -->
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0006E_isGlobal')}" id="CORE_PROG001D0006E_isGlobal"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0006E_isGlobal" name="CORE_PROG001D0006E_isGlobal" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysMsgNotice.isGlobal "> checked</s:if> /> 
    		</td>
    	</tr> 
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="${action.getText('CORE_PROG001D0006E_toAccountOid')}" id="CORE_PROG001D0006E_toAccountOid"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0006E_toAccountOid"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:select name="CORE_PROG001D0006E_toAccountOid" dataSource="accountMap" id="CORE_PROG001D0006E_toAccountOid" value="selectAccountOid"></gs:select>
			</td>
		</tr>    		  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0006E_update" id="CORE_PROG001D0006E_update" onClick="CORE_PROG001D0006E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemMessageNoticeUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysMsgNotice.oid}',
    						'fields.msgOid'			: dijit.byId('CORE_PROG001D0006E_msgOid').get('value'), 
    						'fields.noticeId'		: dijit.byId('CORE_PROG001D0006E_noticeId').get('value'),
    						'fields.title'			: dijit.byId('CORE_PROG001D0006E_title').get('value'),
    						'fields.message'		: dijit.byId('CORE_PROG001D0006E_message').get('value'),
    						'fields.date1'			: dijit.byId('CORE_PROG001D0006E_date1').get('displayedValue'),
    						'fields.date2'			: dijit.byId('CORE_PROG001D0006E_date2').get('displayedValue'),    						
    						'fields.startHour'		: dijit.byId('CORE_PROG001D0006E_time_startHour').get('value'),
    						'fields.startMinutes'	: dijit.byId('CORE_PROG001D0006E_time_startMinutes').get('value'),
    						'fields.endHour'		: dijit.byId('CORE_PROG001D0006E_time_endHour').get('value'),
    						'fields.endMinutes'		: dijit.byId('CORE_PROG001D0006E_time_endMinutes').get('value'),
    						'fields.isGlobal'		: ( dijit.byId('CORE_PROG001D0006E_isGlobal').checked ? 'true' : 'false' ),
    						'fields.toAccountOid'	: dijit.byId('CORE_PROG001D0006E_toAccountOid').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0006E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0006E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0006E_clear" id="CORE_PROG001D0006E_clear" onClick="CORE_PROG001D0006E_clear();" 
    				label="${action.getText('CORE_PROG001D0006E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

