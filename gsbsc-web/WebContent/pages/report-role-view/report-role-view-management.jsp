<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
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

var BSC_PROG004D0003Q_defaultUrl = '${defaultUrl}';

var BSC_PROG004D0003Q_fieldsId = new Object();
BSC_PROG004D0003Q_fieldsId['roleOid'] 		= 'BSC_PROG004D0003Q_roleOid';

function BSC_PROG004D0003Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG004D0003Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG004D0003Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG004D0003Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG004D0003Q_fieldsId);
		return;
	}	
	//BSC_PROG004D0003Q_clear();
}

function BSC_PROG004D0003Q_roleChange() {
	BSC_PROG004D0003Q_clearOrgaAppendId();
	BSC_PROG004D0003Q_clearEmplAppendId();		
	var roleOid = dijit.byId('BSC_PROG004D0003Q_roleOid').get('value');
	if ( roleOid == null || roleOid == '' || roleOid == _gscore_please_select_id ) {	
		return;
	}
	var nowTabPane = dijit.byId('BSC_PROG004D0003Q_ChildTab');
	nowTabPane.attr("href", BSC_PROG004D0003Q_defaultUrl + "?<%=Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD%>=Y&fields.oid=" + roleOid );
	nowTabPane.refresh();   	
}

function BSC_PROG004D0003Q_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG004D0003Q_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG004D0003Q_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG004D0003Q_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG004D0003Q_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG004D0003Q_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG004D0003Q_reloadEmployeeAppendName() {
	var appendOid = dojo.byId('BSC_PROG004D0003Q_appendEmployeeOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG004D0003Q_appendEmployeeOid').value = '';
		dojo.byId('BSC_PROG004D0003Q_employeeAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG004D0003Q_appendEmployeeOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG004D0003Q_employeeAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG004D0003Q_clear() {
	setFieldsBackgroundDefault(BSC_PROG004D0003Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG004D0003Q_fieldsId);	
	BSC_PROG004D0003Q_clearOrgaAppendId();
	BSC_PROG004D0003Q_clearEmplAppendId();
	dijit.byId("BSC_PROG004D0003Q_roleOid").set("value", _gscore_please_select_id );
}

function BSC_PROG004D0003Q_clearOrgaAppendId() {
	dojo.byId('BSC_PROG004D0003Q_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG004D0003Q_organizationAppendName').innerHTML = '';
}

function BSC_PROG004D0003Q_clearEmplAppendId() {
	dojo.byId('BSC_PROG004D0003Q_appendEmployeeOid').value = '';
	dojo.byId('BSC_PROG004D0003Q_employeeAppendName').innerHTML = '';
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
		saveJsMethod="BSC_PROG004D0003Q_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG004D0003Q_appendOrganizationOid" id="BSC_PROG004D0003Q_appendOrganizationOid" value="<s:property value="fields.appendOrgaOids"/>" />
	<input type="hidden" name="BSC_PROG004D0003Q_appendEmployeeOid" id="BSC_PROG004D0003Q_appendEmployeeOid" value="<s:property value="fields.appendEmplOids"/>" />
	
	<table border="0" width="100%" height="200px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG004D0003Q_roleOid')}" id="BSC_PROG004D0003Q_roleOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0003Q_roleOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG004D0003Q_roleOid" dataSource="roleMap" id="BSC_PROG004D0003Q_roleOid" onChange="BSC_PROG004D0003Q_roleChange();" value="fields.oid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0003Q_roleOid'">
    				Select role.
				</div> 			    			
    		</td>  		
		</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG004D0003Q_deptSelect')}" id="BSC_PROG004D0003Q_deptSelect"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG004D0003Q_deptSelect" id="BSC_PROG004D0003Q_deptSelect" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG004D0003Q_appendOrganizationOid;BSC_PROG004D0003Q_reloadOrganizationAppendName');
						}
					"></button>
				<button name="BSC_PROG004D0003Q_deptClear" id="BSC_PROG004D0003Q_deptClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG004D0003Q_clearOrgaAppendId();
						}
					"></button>	
				<br/>	    			    			
    			<span id="BSC_PROG004D0003Q_organizationAppendName"><s:property value="fields.appendOrgaNames"/></span>    			
    		</td>
    	</tr>     
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG004D0003Q_emplSelect')}" id="BSC_PROG004D0003Q_emplSelect"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG004D0003Q_emplSelect" id="BSC_PROG004D0003Q_emplSelect" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG004D0003Q_appendEmployeeOid;BSC_PROG004D0003Q_reloadEmployeeAppendName');
						}
					"></button>
				<button name="BSC_PROG004D0003Q_emplClear" id="BSC_PROG004D0003Q_emplClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG004D0003Q_clearEmplAppendId();
						}
					"></button>		
				<br/>
				<span id="BSC_PROG004D0003Q_employeeAppendName"><s:property value="fields.appendEmplNames"/></span>	    			
    		</td>    
    	</tr>	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG004D0003Q_save" id="BSC_PROG004D0003Q_save" onClick="BSC_PROG004D0003Q_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.reportRoleViewSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.roleOid'		: dijit.byId('BSC_PROG004D0003Q_roleOid').get('value'),
    						'fields.orgaOids'		: dojo.byId('BSC_PROG004D0003Q_appendOrganizationOid').value,
    						'fields.emplOids'		: dojo.byId('BSC_PROG004D0003Q_appendEmployeeOid').value
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG004D0003Q_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG004D0003Q_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG004D0003Q_clear" id="BSC_PROG004D0003Q_clear" onClick="BSC_PROG004D0003Q_clear();" 
    				label="${action.getText('BSC_PROG004D0003Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    	    		
    		</td>
    	</tr>    		
	</table>			
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
