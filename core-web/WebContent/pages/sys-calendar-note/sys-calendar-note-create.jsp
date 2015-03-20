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

var CORE_PROG001D0004A_fieldsId = new Object();
CORE_PROG001D0004A_fieldsId['accountOid']						= 'CORE_PROG001D0004A_accountOid';
CORE_PROG001D0004A_fieldsId['title'] 							= 'CORE_PROG001D0004A_title';
CORE_PROG001D0004A_fieldsId['note'] 							= 'CORE_PROG001D0004A_note';
CORE_PROG001D0004A_fieldsId['date'] 							= 'CORE_PROG001D0004A_date';

function CORE_PROG001D0004A_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0004A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0004A_fieldsId);		
		return;
	}	
	CORE_PROG001D0004A_clear();	
}

function CORE_PROG001D0004A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0004A_fieldsId);
	if ('Y' == _gscore_is_super_role) { // 只有 super-role 的帳戶能選取其他帳戶
		dijit.byId('CORE_PROG001D0004A_accountOid').set('value',_gscore_please_select_id);
	}			
	dijit.byId('CORE_PROG001D0004A_title').set("value", "");
	dijit.byId('CORE_PROG001D0004A_note').set("value", "");	
	dijit.byId('CORE_PROG001D0004A_date').set("value", "");
	dijit.byId('CORE_PROG001D0004A_date').set('displayedValue', '');	
	dijit.byId('CORE_PROG001D0004A_time_startHour').set("value", "00");
	dijit.byId('CORE_PROG001D0004A_time_startMinutes').set("value", "00");
	dijit.byId('CORE_PROG001D0004A_time_endHour').set("value", "00");
	dijit.byId('CORE_PROG001D0004A_time_endMinutes').set("value", "00");	
	dijit.byId('CORE_PROG001D0004A_alert').set("checked", false);
	dijit.byId('CORE_PROG001D0004A_contact').set("value", "");		
}

function CORE_PROG001D0004A_getTime() {
	var timeVal = '';
	timeVal = timeVal + dijit.byId('CORE_PROG001D0004A_time_startHour').get("value") + dijit.byId('CORE_PROG001D0004A_time_startMinutes').get("value");
	timeVal = timeVal + _gscore_datetime_delimiter;
	timeVal = timeVal + dijit.byId('CORE_PROG001D0004A_time_endHour').get("value") + dijit.byId('CORE_PROG001D0004A_time_endMinutes').get("value");
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

<body class="claro" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="CORE_PROG001D0004A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>

	<table border="0" width="100%" height="575px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Owner</b>:
    			<br/>
    			<gs:select name="CORE_PROG001D0004A_accountOid" dataSource="accountMap" id="CORE_PROG001D0004A_accountOid" readonly="${selectReadonly}" value="${selectValue}"></gs:select>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Title</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0004A_title" id="CORE_PROG001D0004A_title" value="" width="400" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="225px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Note</b>:
    			<br/>
    			<textarea id="CORE_PROG001D0004A_note" name="CORE_PROG001D0004A_note" data-dojo-type="dijit/form/Textarea" rows="9" cols="50" style="width:300px;height:190px;max-height:200px"></textarea>
    		</td>    		
    	</tr>	   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Date</b>:
    			<br/>
    			<input id="CORE_PROG001D0004A_date" type="text" name="CORE_PROG001D0004A_date" data-dojo-type="dijit.form.DateTextBox" maxlength="10" constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" required="true" style="width:120px;" />
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Time</b>:
    			<br/>
    			<b>start</b>&nbsp;
    			hour<gs:select name="CORE_PROG001D0004A_time_startHour" dataSource="hourMap" id="CORE_PROG001D0004A_time_startHour" width="50"></gs:select>
    			minutes<gs:select name="CORE_PROG001D0004A_time_startMinutes" dataSource="minutesMap" id="CORE_PROG001D0004A_time_startMinutes" width="50"></gs:select>
    			&nbsp;&nbsp;&nbsp;
    			
    			<b>end</b>&nbsp;
    			hour<gs:select name="CORE_PROG001D0004A_time_endHour" dataSource="hourMap" id="CORE_PROG001D0004A_time_endHour" width="50"></gs:select>
    			minutes<gs:select name="CORE_PROG001D0004A_time_endMinutes" dataSource="minutesMap" id="CORE_PROG001D0004A_time_endMinutes" width="50"></gs:select>    			
    			    			
    		</td>
    		<!-- Time: 下拉-起時 下拉-起分 / 下拉-迄時 下拉-迄分 -->
    	</tr>    
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Send notification</b>:
    			<br/>
    			<input id="CORE_PROG001D0004A_alert" name="CORE_PROG001D0004A_alert" data-dojo-type="dijit/form/CheckBox" value="true" />    		
    		</td>
    	</tr>    	    	        		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<b>Contact mail</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG001D0004A_contact" id="CORE_PROG001D0004A_contact" value="" width="400" maxlength="500"></gs:textBox>
    		</td>
    	</tr>    
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0004A_save" id="CORE_PROG001D0004A_save" onClick="CORE_PROG001D0004A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemCalendarNoteSaveAction.action"
    				parameterType="postData"
    				xhrParameter="
    					{
    						'fields.accountOid'		: dijit.byId('CORE_PROG001D0004A_accountOid').get('value'),
    						'fields.title'			: dijit.byId('CORE_PROG001D0004A_title').get('value'),
    						'fields.note'			: dijit.byId('CORE_PROG001D0004A_note').get('value'),
    						'fields.date'			: dijit.byId('CORE_PROG001D0004A_date').get('displayedValue'),
    						'fields.time'			: CORE_PROG001D0004A_getTime(),
    						'fields.alert'			: ( dijit.byId('CORE_PROG001D0004A_alert').checked ? 'true' : 'false' ),
    						'fields.contact'		: dijit.byId('CORE_PROG001D0004A_contact').get('value')
    					}
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0004A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button> 			
    			<gs:button name="CORE_PROG001D0004A_clear" id="CORE_PROG001D0004A_clear" onClick="CORE_PROG001D0004A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>      		
    		</td>
    	</tr>    	    	    	    	    	
	</table>    		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	
