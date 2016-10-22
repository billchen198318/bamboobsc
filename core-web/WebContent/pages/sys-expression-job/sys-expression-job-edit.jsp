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

var CORE_PROG003D0006E_fieldsId = new Object();
CORE_PROG003D0006E_fieldsId['systemOid']	= 'CORE_PROG003D0006E_systemOid';
CORE_PROG003D0006E_fieldsId['name']	= 'CORE_PROG003D0006E_name';
CORE_PROG003D0006E_fieldsId['expressionOid']	= 'CORE_PROG003D0006E_expressionOid';
CORE_PROG003D0006E_fieldsId['contact']	= 'CORE_PROG003D0006E_contact';

function CORE_PROG003D0006E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0006E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0006E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0006E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0006E_fieldsId);
		return;
	}	
}

function CORE_PROG003D0006E_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0006E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0006E_fieldsId);
	dijit.byId('CORE_PROG003D0006E_systemOid').set("value", _gscore_please_select_id);	
	dijit.byId('CORE_PROG003D0006E_name').set("value", "");
	dijit.byId('CORE_PROG003D0006E_active').set("checked", false);
	dijit.byId('CORE_PROG003D0006E_description').set("value", "");	
	dijit.byId('CORE_PROG003D0006E_checkFault').set("checked", false);
	dijit.byId('CORE_PROG003D0006E_expressionOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0006E_runDayOfWeek').set("value", '*');
	dijit.byId('CORE_PROG003D0006E_runHour').set("value", '*');
	dijit.byId('CORE_PROG003D0006E_runMinute').set("value", '*');
	dijit.byId('CORE_PROG003D0006E_contactMode').set("value", '0');
	dijit.byId('CORE_PROG003D0006E_contact').set("value", "");
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
		saveJsMethod="CORE_PROG003D0006E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="625px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="System" id="CORE_PROG003D0006E_systemOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_systemOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006E_systemOid" dataSource="systemMap" id="CORE_PROG003D0006E_systemOid" value="fields.systemOid"></gs:select>
    		</td>
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Id" id="CORE_PROG003D0006E_id" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_id"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006E_id" id="CORE_PROG003D0006E_id" value="sysExprJob.id" width="200" maxlength="20" readonly="Y"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Name" id="CORE_PROG003D0006E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006E_name" id="CORE_PROG003D0006E_name" value="sysExprJob.name" width="300" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Expression" id="CORE_PROG003D0006E_expressionOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_expressionOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006E_expressionOid" dataSource="sysExprMap" id="CORE_PROG003D0006E_expressionOid" value="fields.expressionOid" width="300px"></gs:select>
    		</td>
    	</tr>	  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Active" id="CORE_PROG003D0006E_active"></gs:label>
    			<br/>
    			<input id="CORE_PROG003D0006E_active" name="CORE_PROG003D0006E_active" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysExprJob.active "> checked="checked" </s:if> />
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Check fault" id="CORE_PROG003D0006E_checkFault"></gs:label>
    			<br/>
    			<input id="CORE_PROG003D0006E_checkFault" name="CORE_PROG003D0006E_checkFault" data-dojo-type="dijit/form/CheckBox" value="true" <s:if test=" \"Y\" == sysExprJob.checkFault "> checked="checked" </s:if> />
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Day of week / Hour / Minute" id="CORE_PROG003D0006E_runDayOfWeek" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_runDayOfWeek"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			Day of week&nbsp;<gs:select name="CORE_PROG003D0006E_runDayOfWeek" dataSource="runDayOfWeekMap" id="CORE_PROG003D0006E_runDayOfWeek" width="60px" value="sysExprJob.runDayOfWeek"></gs:select>
    			&nbsp;/&nbsp;
    			Hour&nbsp;<gs:select name="CORE_PROG003D0006E_runHour" dataSource="runHourMap" id="CORE_PROG003D0006E_runHour" width="60px" value="sysExprJob.runHour"></gs:select>
    			&nbsp;/&nbsp;
    			Minute&nbsp;<gs:select name="CORE_PROG003D0006E_runMinute" dataSource="runMinuteMap" id="CORE_PROG003D0006E_runMinute" width="60px" value="sysExprJob.runMinute"></gs:select>
    		</td>
    	</tr>	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Contact mode" id="CORE_PROG003D0006E_contactMode"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_contactMode"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0006E_contactMode" dataSource="{ \"0\":\"No\", \"1\":\"Only fault\", \"2\":\"Only success\", \"3\":\"Both fault/success\" }" id="CORE_PROG003D0006E_contactMode" width="150px" value="sysExprJob.contactMode"></gs:select>
    		</td>
    	</tr>	     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Contact" id="CORE_PROG003D0006E_contact"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0006E_contact"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006E_contact" id="CORE_PROG003D0006E_contact" value="sysExprJob.contact" width="350" maxlength="150"></gs:textBox>
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="Description" id="CORE_PROG003D0006E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0006E_description" name="CORE_PROG003D0006E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${sysExprJob.description}</textarea>	
    		</td>
    	</tr>        	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0006E_update" id="CORE_PROG003D0006E_update" onClick="CORE_PROG003D0006E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemExpressionJobUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'		: '${sysExprJob.oid}',
    						'fields.systemOid'		: dijit.byId('CORE_PROG003D0006E_systemOid').get('value'),
    						'fields.id'		: '${sysExprJob.id}', 
    						'fields.name'		: dijit.byId('CORE_PROG003D0006E_name').get('value'),
    						'fields.active'		: ( dijit.byId('CORE_PROG003D0006E_active').checked ? 'true' : 'false' ),
    						'fields.description'		: dijit.byId('CORE_PROG003D0006E_description').get('value'),
    						'fields.checkFault'		: ( dijit.byId('CORE_PROG003D0006E_checkFault').checked ? 'true' : 'false' ),
    						'fields.expressionOid'		: dijit.byId('CORE_PROG003D0006E_expressionOid').get('value'),
    						'fields.runDayOfWeek'		: dijit.byId('CORE_PROG003D0006E_runDayOfWeek').get('value'),
    						'fields.runHour'		: dijit.byId('CORE_PROG003D0006E_runHour').get('value'),
    						'fields.runMinute'		: dijit.byId('CORE_PROG003D0006E_runMinute').get('value'),
    						'fields.contactMode'		: dijit.byId('CORE_PROG003D0006E_contactMode').get('value'),
    						'fields.contact'		: dijit.byId('CORE_PROG003D0006E_contact').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0006E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Update" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0006E_clear" id="CORE_PROG003D0006E_clear" onClick="CORE_PROG003D0006E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>    	    	    	    	    	
	</table>    		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	