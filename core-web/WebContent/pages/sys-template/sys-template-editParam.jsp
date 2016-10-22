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

require(["dojo/ready", "dojo/parser", "dijit/registry", "dijit/form/Button", "dijit/form/TextBox", "dijit/form/Select", "dijit/_Widget", "dojo/domReady!"], function(ready, parser, registry){	
	setTimeout("getQueryGrid_${programId}_grid()", 1000);
});

var CORE_PROG001D0007E_S00_fieldsId = new Object();
CORE_PROG001D0007E_S00_fieldsId['templateVar'] 		= 'CORE_PROG001D0007E_S00_templateVar';
CORE_PROG001D0007E_S00_fieldsId['objectVar'] 		= 'CORE_PROG001D0007E_S00_objectVar';

function CORE_PROG001D0007E_S00_GridFieldStructure() {
	return [
			{ name: "${action.getText('CORE_PROG001D0007E_S00_grid_01')}", field: "oid", formatter: CORE_PROG001D0007E_S00_GridButtonClick, width: "10%" },  
			{ name: "${action.getText('CORE_PROG001D0007E_S00_grid_02')}", field: "templateVar", width: "40%" },
			{ name: "${action.getText('CORE_PROG001D0007E_S00_grid_03')}", field: "objectVar", width: "40%" },
			{ name: "${action.getText('CORE_PROG001D0007E_S00_grid_04')}", field: "isTitle", width: "10%" }
		];		
}

function CORE_PROG001D0007E_S00_GridButtonClick(itemOid) {
	var rd="";	
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG001D0007E_S00_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function CORE_PROG001D0007E_S00_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0007E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0007E_S00_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0007E_S00_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0007E_S00_fieldsId);
		return;
	}		
	CORE_PROG001D0007E_S00_clear();
	getQueryGrid_${programId}_grid();
}

function CORE_PROG001D0007E_S00_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0007E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0007E_S00_fieldsId);
	dijit.byId('CORE_PROG001D0007E_S00_templateVar').set("value", "");
	dijit.byId('CORE_PROG001D0007E_S00_objectVar').set("value", "");
	dijit.byId('CORE_PROG001D0007E_S00_isTitle').set("checked", false);
	
	clearQuery_${programId}_grid();
	
}

function CORE_PROG001D0007E_S00_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('CORE_PROG001D0007E_S00_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemTemplateParamDeleteAction.action', 
						{ 'fields.oid' : oid }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
							getQueryGrid_${programId}_grid();
						}, 
						function(error) {
							alert(error);
						}
				);	
			}, 
			(window.event ? window.event : null) 
	);		
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
		saveJsMethod="CORE_PROG001D0007E_S00_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="175px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_S00_templateVar')}" id="CORE_PROG001D0007E_S00_templateVar" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0007E_S00_templateVar"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0007E_S00_templateVar" id="CORE_PROG001D0007E_S00_templateVar" value="" width="200" maxlength="100" ></gs:textBox>
    		</td>
    		<td height="50px" width="50%"  align="left">&nbsp;</td>
    	</tr>
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_S00_objectVar')}" id="CORE_PROG001D0007E_S00_objectVar" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0007E_S00_objectVar"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0007E_S00_objectVar" id="CORE_PROG001D0007E_S00_objectVar" value="" width="200" maxlength="100" ></gs:textBox>
    		</td>
    		<td height="50px" width="50%"  align="left">&nbsp;</td>
    	</tr>	    		
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0007E_S00_isTitle')}" id="CORE_PROG001D0007E_S00_isTitle"></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0007E_S00_isTitle" name="CORE_PROG001D0007E_S00_isTitle" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>
    		<td height="50px" width="50%"  align="left">&nbsp;</td>
    	</tr>	    	
    	<tr>
    		<td height="25px" width="100%" align="left" colspan="2">
    			<gs:button name="CORE_PROG001D0007E_S00_update" id="CORE_PROG001D0007E_S00_update" onClick="CORE_PROG001D0007E_S00_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemTemplateParamSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.templateOid'	: '${sysTemplate.oid}',
    						'fields.templateVar'	: dijit.byId('CORE_PROG001D0007E_S00_templateVar').get('value'), 
    						'fields.objectVar'		: dijit.byId('CORE_PROG001D0007E_S00_objectVar').get('value'), 
    						'fields.isTitle'		: ( dijit.byId('CORE_PROG001D0007E_S00_isTitle').checked ? 'true' : 'false' )
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0007E_S00_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0007E_S00_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0007E_S00_clear" id="CORE_PROG001D0007E_S00_clear" onClick="CORE_PROG001D0007E_S00_clear();" 
    				label="${action.getText('CORE_PROG001D0007E_S00_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    			
    				
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
			    <gs:button name="CORE_PROG001D0007E_S00_query" id="CORE_PROG001D0007E_S00_query" onClick="getQueryGrid_${programId}_grid();"
			    	handleAs="json"
			    	sync="N"
			    	xhrUrl="core.systemTemplateParamGridQueryAction.action"
			    	parameterType="postData"
			    	xhrParameter=" 
			    		{
			    			'searchValue.parameter.templateOid'	: '${sysTemplate.oid}', 
			    			'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
			    			'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
			    			'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()			    	 
			    		} 
			    	"
			    	errorFn="clearQuery_${programId}_grid();"
			    	loadFn="dataGrid_${programId}_grid(data);" 
			    	programId="${programId}"
			    	label="${action.getText('CORE_PROG001D0007E_S00_query')}" 
			    	iconClass="dijitIconSearch"
			    	cssClass="alt-primary"></gs:button>
			    	
			    	
    		</td>
    	</tr>     	 	  	    	
	</table>	

	<gs:grid gridFieldStructure="CORE_PROG001D0007E_S00_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

