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

var BSC_PROG001D0001E_fieldsId = new Object();
BSC_PROG001D0001E_fieldsId['fullName'] 	= 'BSC_PROG001D0001E_fullName';

function BSC_PROG001D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0001E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0001E_fieldsId);
		return;
	}		
}

function BSC_PROG001D0001E_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001E_fieldsId);
	dijit.byId('BSC_PROG001D0001E_fullName').set("value", "");
	dijit.byId('BSC_PROG001D0001E_jobTitle').set("value", "");	
	BSC_PROG001D0001E_clearAppendId();
}

function BSC_PROG001D0001E_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG001D0001E_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG001D0001E_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG001D0001E_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG001D0001E_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG001D0001E_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG001D0001E_clearAppendId() {
	dojo.byId('BSC_PROG001D0001E_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG001D0001E_organizationAppendName').innerHTML = '';
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
		saveJsMethod="BSC_PROG001D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG001D0001E_appendOrganizationOid" id="BSC_PROG001D0001E_appendOrganizationOid" value="${appendId}" />
	
	<table border="0" width="100%" height="300px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_account')}" id="BSC_PROG001D0001E_account" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0001E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_account"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001E_account" id="BSC_PROG001D0001E_account" value="employee.account" width="200" maxlength="24" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_account'">
    				Account. ( read only ) 
				</div>      			
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_empId')}" id="BSC_PROG001D0001E_empId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0001E_empIdno')"/>&nbsp;/&nbsp;<s:property value="getText('BSC_PROG001D0001E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_empId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001E_empId" id="BSC_PROG001D0001E_empId" value="employee.empId" width="200" maxlength="10" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_empId'">
    				Employee serial-number. ( read only )
				</div>     			
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_fullName')}" id="BSC_PROG001D0001E_fullName" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001E_fullName"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001E_fullName" id="BSC_PROG001D0001E_fullName" value="employee.fullName" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_fullName'">
    				Input full name. 
				</div>       			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_jobTitle')}" id="BSC_PROG001D0001E_jobTitle"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001E_jobTitle" id="BSC_PROG001D0001E_jobTitle" value="employee.jobTitle" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001E_jobTitle'">
    				Input job title. 
				</div>      			
    		</td>
    	</tr>		    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001E_deptSelect')}" id="BSC_PROG001D0001E_deptSelect"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG001D0001E_deptSelect" id="BSC_PROG001D0001E_deptSelect" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG001D0001E_appendOrganizationOid;BSC_PROG001D0001E_reloadOrganizationAppendName');
						}
					"></button>
				<button name="BSC_PROG001D0001E_deptClear" id="BSC_PROG001D0001E_deptClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG001D0001E_clearAppendId();
						}
					"></button>	
				<br/>
				<span id="BSC_PROG001D0001E_organizationAppendName">${appendName}</span>	    			
    		</td>
    	</tr>		    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0001E_update" id="BSC_PROG001D0001E_update" onClick="BSC_PROG001D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.employeeUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${employee.oid}',		 
    						'fields.fullName'		: dijit.byId('BSC_PROG001D0001E_fullName').get('value'),
    						'fields.jobTitle'		: dijit.byId('BSC_PROG001D0001E_jobTitle').get('value'),
    						'fields.appendId'		: dojo.byId('BSC_PROG001D0001E_appendOrganizationOid').value
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0001E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0001E_clear" id="BSC_PROG001D0001E_clear" onClick="BSC_PROG001D0001E_clear();" 
    				label="${action.getText('BSC_PROG001D0001E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    	    		
    		</td>
    	</tr>	    	   	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
