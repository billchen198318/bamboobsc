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

var CORE_PROG001D0004E_fieldsId = new Object();
CORE_PROG001D0004E_fieldsId['accountOid']						= 'CORE_PROG001D0004E_accountOid';
CORE_PROG001D0004E_fieldsId['title'] 							= 'CORE_PROG001D0004E_title';
CORE_PROG001D0004E_fieldsId['note'] 							= 'CORE_PROG001D0004E_note';
CORE_PROG001D0004E_fieldsId['date'] 							= 'CORE_PROG001D0004E_date';

function CORE_PROG001D0004E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0004E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0004E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0004E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0004E_fieldsId);
		return;
	}		
}

function CORE_PROG001D0004E_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0004E_fieldsId);		
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0004E_fieldsId);
	dijit.byId('CORE_PROG001D0004E_title').set("value", "");
	dijit.byId('CORE_PROG001D0004E_note').set("value", "");	
	dijit.byId('CORE_PROG001D0004E_date').set("value", "");
	dijit.byId('CORE_PROG001D0004E_date').set('displayedValue', '');	
	dijit.byId('CORE_PROG001D0004E_time_startHour').set("value", "00");
	dijit.byId('CORE_PROG001D0004E_time_startMinutes').set("value", "00");
	dijit.byId('CORE_PROG001D0004E_time_endHour').set("value", "00");
	dijit.byId('CORE_PROG001D0004E_time_endMinutes').set("value", "00");	
	dijit.byId('CORE_PROG001D0004E_alert').set("checked", false);
	dijit.byId('CORE_PROG001D0004E_contact').set("value", "");	
}

function CORE_PROG001D0004E_getTime() {
	var timeVal = '';
	timeVal = timeVal + dijit.byId('CORE_PROG001D0004E_time_startHour').get("value") + dijit.byId('CORE_PROG001D0004E_time_startMinutes').get("value");
	timeVal = timeVal + _gscore_datetime_delimiter;
	timeVal = timeVal + dijit.byId('CORE_PROG001D0004E_time_endHour').get("value") + dijit.byId('CORE_PROG001D0004E_time_endMinutes').get("value");
	return timeVal;
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
		saveJsMethod="CORE_PROG001D0004E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="575px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_accountOid')}" id="CORE_PROG001D0004E_accountOid" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG001D0004E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0004E_accountOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0004E_accountOid" dataSource="accountMap" id="CORE_PROG001D0004E_accountOid" readonly="Y" value="accountOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_accountOid'">
    				owner account. ( read only )
				</div>       			
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_title')}" id="CORE_PROG001D0004E_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0004E_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0004E_title" id="CORE_PROG001D0004E_title" value="sysCalendarNote.title" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_title'">
    				Input title.
				</div>        			
    		</td>
    	</tr>	
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_note')}" id="CORE_PROG001D0004E_note" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0004E_note"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<textarea id="CORE_PROG001D0004E_note" name="CORE_PROG001D0004E_note" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px">${sysCalendarNote.note}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_note'">
    				Input description, the maximum allowed 1000 characters.
				</div>     				
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_date')}" id="CORE_PROG001D0004E_date" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0004E_date"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input id="CORE_PROG001D0004E_date" type="text" name="CORE_PROG001D0004E_date" data-dojo-type="dijit.form.DateTextBox" maxlength="10" constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" required="true" style="width:120px;" value="${calendarNoteDate}" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_date'">
    				Select date.
				</div>        			
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_time')}" id="CORE_PROG001D0004E_time"></gs:label>
    			<br/>
    			<b><s:property value="getText('CORE_PROG001D0004E_time_start')"/></b>&nbsp;
    			<s:property value="getText('CORE_PROG001D0004E_time_startHour')"/><gs:select name="CORE_PROG001D0004E_time_startHour" dataSource="hourMap" id="CORE_PROG001D0004E_time_startHour" width="60" value="startHour"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_time_startHour'">
    				Select start Hour.
				</div>  
				    			
    			<s:property value="getText('CORE_PROG001D0004E_time_startMinutes')"/><gs:select name="CORE_PROG001D0004E_time_startMinutes" dataSource="minutesMap" id="CORE_PROG001D0004E_time_startMinutes" width="60" value="startMinutes"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_time_startMinutes'">
    				Select start Minutes.
				</div>  
				    			
    			&nbsp;&nbsp;&nbsp;
    			
    			<b><s:property value="getText('CORE_PROG001D0004E_time_end')"/></b>&nbsp;
    			<s:property value="getText('CORE_PROG001D0004E_time_endHour')"/><gs:select name="CORE_PROG001D0004E_time_endHour" dataSource="hourMap" id="CORE_PROG001D0004E_time_endHour" width="60" value="endHour"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_time_endHour'">
    				Select end Hour.
				</div>   
				    			
    			<s:property value="getText('CORE_PROG001D0004E_time_endMinutes')"/><gs:select name="CORE_PROG001D0004E_time_endMinutes" dataSource="minutesMap" id="CORE_PROG001D0004E_time_endMinutes" width="60" value="endMinutes"></gs:select>    			
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_time_endMinutes'">
    				Select end Minutes.
				</div> 
				    			
    		</td>
    		<!-- Time: 下拉-起時 下拉-起分 / 下拉-迄時 下拉-迄分 -->
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_alert')}" id="CORE_PROG001D0004E_alert"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0004E_alert" name="CORE_PROG001D0004E_alert" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysCalendarNote.alert "> checked </s:if> />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_alert'">
    				enable, send mail to notice.
				</div> 
				    			 
    		</td>
    	</tr>    	    	        		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0004E_contact')}" id="CORE_PROG001D0004E_contact"></gs:label>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0004E_contact" id="CORE_PROG001D0004E_contact" value="sysCalendarNote.contact" width="400" maxlength="500"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'CORE_PROG001D0004E_contact'">
    				Mail address.<BR/>
    				Example:<BR/>
    				&nbsp;&nbsp;1.Single: bill1234@gmail.com<BR/>
    				&nbsp;&nbsp;2.Many: bill1234@gmail.com;amy@gmail.com;tiffany@gmail.com
				</div> 
				    			
    		</td>
    	</tr>     	 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0004E_update" id="CORE_PROG001D0004E_update" onClick="CORE_PROG001D0004E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemCalendarNoteUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysCalendarNote.oid}',
    						'fields.accountOid'		: dijit.byId('CORE_PROG001D0004E_accountOid').get('value'),
    						'fields.title'			: dijit.byId('CORE_PROG001D0004E_title').get('value'),
    						'fields.note'			: dijit.byId('CORE_PROG001D0004E_note').get('value'),
    						'fields.date'			: dijit.byId('CORE_PROG001D0004E_date').get('displayedValue'),
    						'fields.time'			: CORE_PROG001D0004E_getTime(),
    						'fields.alert'			: ( dijit.byId('CORE_PROG001D0004E_alert').checked ? 'true' : 'false' ),
    						'fields.contact'		: dijit.byId('CORE_PROG001D0004E_contact').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0004E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0004E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0004E_clear" id="CORE_PROG001D0004E_clear" onClick="CORE_PROG001D0004E_clear();" 
    				label="${action.getText('CORE_PROG001D0004E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

