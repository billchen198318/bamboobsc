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

var BSC_PROG001D0001A_fieldsId = new Object();
BSC_PROG001D0001A_fieldsId['account'] 	= 'BSC_PROG001D0001A_account';
BSC_PROG001D0001A_fieldsId['empId'] 	= 'BSC_PROG001D0001A_empId';
BSC_PROG001D0001A_fieldsId['password1'] = 'BSC_PROG001D0001A_password1';
BSC_PROG001D0001A_fieldsId['password2'] = 'BSC_PROG001D0001A_password2';
BSC_PROG001D0001A_fieldsId['fullName'] 	= 'BSC_PROG001D0001A_fullName';

function BSC_PROG001D0001A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG001D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0001A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0001A_fieldsId);
		return;
	}	
	BSC_PROG001D0001A_clear();
}

function BSC_PROG001D0001A_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0001A_fieldsId);	
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0001A_fieldsId);
	dijit.byId('BSC_PROG001D0001A_account').set("value", "");	
	dijit.byId('BSC_PROG001D0001A_empId').set("value", "");		
	dijit.byId('BSC_PROG001D0001A_password1').set("value", "");
	dijit.byId('BSC_PROG001D0001A_password2').set("value", "");
	dijit.byId('BSC_PROG001D0001A_fullName').set("value", "");
	dijit.byId('BSC_PROG001D0001A_jobTitle').set("value", "");	
	BSC_PROG001D0001A_clearAppendId();
}

function BSC_PROG001D0001A_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG001D0001A_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG001D0001A_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG001D0001A_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG001D0001A_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG001D0001A_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG001D0001A_clearAppendId() {
	dojo.byId('BSC_PROG001D0001A_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG001D0001A_organizationAppendName').innerHTML = '';
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
		saveJsMethod="BSC_PROG001D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG001D0001A_appendOrganizationOid" id="BSC_PROG001D0001A_appendOrganizationOid" value="" />
	
	<table border="0" width="100%" height="400px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_account')}" id="BSC_PROG001D0001A_account" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001A_account"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001A_account" id="BSC_PROG001D0001A_account" value="" width="200" maxlength="24"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_account'">
    				Input account, only allow normal characters. 
				</div>          			
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_empId')}" id="BSC_PROG001D0001A_empId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0001A_empIdno')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001A_empId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001A_empId" id="BSC_PROG001D0001A_empId" value="" width="200" maxlength="10"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_empId'">
    				Input employee serial-number, only allow normal characters. 
				</div>     			
    		</td>
    	</tr>      	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_password1')}" id="BSC_PROG001D0001A_password1" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001A_password1"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input name="BSC_PROG001D0001A_password1" id="BSC_PROG001D0001A_password1" type="password" trim="true" maxlength="14" 
					data-dojo-type="dijit.form.TextBox" data-dojo-props='style:"width: 200px;" ' />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_password1'">
    				Input password, only allow normal characters. 
				</div>     					    	    			
    		</td>
    	</tr> 
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_password1')}" id="BSC_PROG001D0001A_password1" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0001A_password2')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001A_password2"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input name="BSC_PROG001D0001A_password2" id="BSC_PROG001D0001A_password2" type="password" trim="true" maxlength="14" 
					data-dojo-type="dijit.form.TextBox" data-dojo-props='style:"width: 200px;" ' />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_password2'">
    				Input password ( confirm ), only allow normal characters. 
				</div>  					        			
    		</td>
    	</tr> 
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_fullName')}" id="BSC_PROG001D0001A_fullName" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0001A_fullName"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001A_fullName" id="BSC_PROG001D0001A_fullName" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_fullName'">
    				Input full name. 
				</div>      			
    		</td>
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_jobTitle')}" id="BSC_PROG001D0001A_jobTitle"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001A_jobTitle" id="BSC_PROG001D0001A_jobTitle" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001A_jobTitle'">
    				Input job title. 
				</div>    			
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="20%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001A_deptSelect')}" id="BSC_PROG001D0001A_deptSelect"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG001D0001A_deptSelect" id="BSC_PROG001D0001A_deptSelect" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG001D0001A_appendOrganizationOid;BSC_PROG001D0001A_reloadOrganizationAppendName');
						}
					"></button>
				<button name="BSC_PROG001D0001A_deptClear" id="BSC_PROG001D0001A_deptClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG001D0001A_clearAppendId();
						}
					"></button>			
				<br/>
				<span id="BSC_PROG001D0001A_organizationAppendName"></span>	    			
    		</td>
    	</tr>      	    	    	    	   	  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0001A_save" id="BSC_PROG001D0001A_save" onClick="BSC_PROG001D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.employeeSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.account'		: dijit.byId('BSC_PROG001D0001A_account').get('value'), 
    						'fields.empId'			: dijit.byId('BSC_PROG001D0001A_empId').get('value'),
    						'fields.password1'		: dijit.byId('BSC_PROG001D0001A_password1').get('value'),
    						'fields.password2'		: dijit.byId('BSC_PROG001D0001A_password2').get('value'),    						 
    						'fields.fullName'		: dijit.byId('BSC_PROG001D0001A_fullName').get('value'),
    						'fields.jobTitle'		: dijit.byId('BSC_PROG001D0001A_jobTitle').get('value'),
    						'fields.appendId'		: dojo.byId('BSC_PROG001D0001A_appendOrganizationOid').value
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0001A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0001A_clear" id="BSC_PROG001D0001A_clear" onClick="BSC_PROG001D0001A_clear();" 
    				label="${action.getText('BSC_PROG001D0001A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>        		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
