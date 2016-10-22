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

var CORE_PROG003D0006A_fieldsId = new Object();
CORE_PROG003D0006A_fieldsId['systemOid']	= 'CORE_PROG003D0006A_systemOid';
CORE_PROG003D0006A_fieldsId['id']	= 'CORE_PROG003D0006A_id';
CORE_PROG003D0006A_fieldsId['name']	= 'CORE_PROG003D0006A_name';
CORE_PROG003D0006A_fieldsId['expressionOid']	= 'CORE_PROG003D0006A_expressionOid';
CORE_PROG003D0006A_fieldsId['contact']	= 'CORE_PROG003D0006A_contact';

function CORE_PROG003D0006A_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0006A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0006A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0006A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0006A_fieldsId);
		return;
	}	
	CORE_PROG003D0006A_clear();	
}

function CORE_PROG003D0006A_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0006A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0006A_fieldsId);
	dijit.byId('CORE_PROG003D0006A_systemOid').set("value", _gscore_please_select_id);	
	dijit.byId('CORE_PROG003D0006A_id').set("value", "");	
	dijit.byId('CORE_PROG003D0006A_name').set("value", "");
	dijit.byId('CORE_PROG003D0006A_active').set("checked", false);
	dijit.byId('CORE_PROG003D0006A_description').set("value", "");	
	dijit.byId('CORE_PROG003D0006A_checkFault').set("checked", false);
	dijit.byId('CORE_PROG003D0006A_expressionOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0006A_runDayOfWeek').set("value", '*');
	dijit.byId('CORE_PROG003D0006A_runHour').set("value", '*');
	dijit.byId('CORE_PROG003D0006A_runMinute').set("value", '*');
	dijit.byId('CORE_PROG003D0006A_contactMode').set("value", '0');
	dijit.byId('CORE_PROG003D0006A_contact').set("value", "");
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
		saveJsMethod="CORE_PROG003D0006A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="625px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="System" id="CORE_PROG003D0006A_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006A_systemOid" dataSource="systemMap" id="CORE_PROG003D0006A_systemOid"></gs:select>
    		</td>
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Id" id="CORE_PROG003D0006A_id" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_id"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006A_id" id="CORE_PROG003D0006A_id" value="" width="200" maxlength="20"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Name" id="CORE_PROG003D0006A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006A_name" id="CORE_PROG003D0006A_name" value="" width="300" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Expression" id="CORE_PROG003D0006A_expressionOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_expressionOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006A_expressionOid" dataSource="sysExprMap" id="CORE_PROG003D0006A_expressionOid" width="300px"></gs:select>
    		</td>
    	</tr>	  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Active" id="CORE_PROG003D0006A_active"></gs:label>
    			<br/>
    			<input id="CORE_PROG003D0006A_active" name="CORE_PROG003D0006A_active" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Check fault" id="CORE_PROG003D0006A_checkFault"></gs:label>
    			<br/>
    			<input id="CORE_PROG003D0006A_checkFault" name="CORE_PROG003D0006A_checkFault" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Day of week / Hour / Minute" id="CORE_PROG003D0006A_runDayOfWeek" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_runDayOfWeek"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			Day of week&nbsp;<gs:select name="CORE_PROG003D0006A_runDayOfWeek" dataSource="runDayOfWeekMap" id="CORE_PROG003D0006A_runDayOfWeek" width="60px"></gs:select>
    			&nbsp;/&nbsp;
    			Hour&nbsp;<gs:select name="CORE_PROG003D0006A_runHour" dataSource="runHourMap" id="CORE_PROG003D0006A_runHour" width="60px"></gs:select>
    			&nbsp;/&nbsp;
    			Minute&nbsp;<gs:select name="CORE_PROG003D0006A_runMinute" dataSource="runMinuteMap" id="CORE_PROG003D0006A_runMinute" width="60px"></gs:select>
    		</td>
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Contact mode" id="CORE_PROG003D0006A_contactMode"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_contactMode"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006A_contactMode" dataSource="{ \"0\":\"No\", \"1\":\"Only fault\", \"2\":\"Only success\", \"3\":\"Both fault/success\" }" id="CORE_PROG003D0006A_contactMode" width="150px"></gs:select>
    		</td>
    	</tr>	     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Contact" id="CORE_PROG003D0006A_contact"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006A_contact"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006A_contact" id="CORE_PROG003D0006A_contact" value="" width="350" maxlength="150"></gs:textBox>
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="Description" id="CORE_PROG003D0006A_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0006A_description" name="CORE_PROG003D0006A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>	
    		</td>
    	</tr>        	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0006A_save" id="CORE_PROG003D0006A_save" onClick="CORE_PROG003D0006A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemExpressionJobSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.systemOid'		: dijit.byId('CORE_PROG003D0006A_systemOid').get('value'), 
    						'fields.id'		: dijit.byId('CORE_PROG003D0006A_id').get('value'),
    						'fields.name'		: dijit.byId('CORE_PROG003D0006A_name').get('value'),
    						'fields.active'		: ( dijit.byId('CORE_PROG003D0006A_active').checked ? 'true' : 'false' ),
    						'fields.description'		: dijit.byId('CORE_PROG003D0006A_description').get('value'),
    						'fields.checkFault'		: ( dijit.byId('CORE_PROG003D0006A_checkFault').checked ? 'true' : 'false' ),
    						'fields.expressionOid'		: dijit.byId('CORE_PROG003D0006A_expressionOid').get('value'),
    						'fields.runDayOfWeek'		: dijit.byId('CORE_PROG003D0006A_runDayOfWeek').get('value'),
    						'fields.runHour'		: dijit.byId('CORE_PROG003D0006A_runHour').get('value'),
    						'fields.runMinute'		: dijit.byId('CORE_PROG003D0006A_runMinute').get('value'),
    						'fields.contactMode'		: dijit.byId('CORE_PROG003D0006A_contactMode').get('value'),
    						'fields.contact'		: dijit.byId('CORE_PROG003D0006A_contact').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0006A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0006A_clear" id="CORE_PROG003D0006A_clear" onClick="CORE_PROG003D0006A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>    	    	    	    	    	
	</table>    		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	