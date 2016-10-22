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

function CORE_PROG001D0002E_S00_GridFieldStructure() {
	return [
			{ name: "*", field: "oid", formatter: CORE_PROG001D0002E_S00_GridButtonClick, width: "15%" },  
			{ name: "Name", field: "name", width: "60%" },
			{ name: "Locale code", field: "localeCode", width: "15%" },
			{ name: "Enable", field: "enableFlag", width: "10%" }
		];		
}

function CORE_PROG001D0002E_S00_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG001D0002E_S00_confirmDelete('" + itemOid + "');\" />";
	return rd;		
}

function CORE_PROG001D0002E_S00_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"Delete?", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemProgramMultiNameDeleteAction.action', 
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

var CORE_PROG001D0002E_S00_fieldsId = new Object();
CORE_PROG001D0002E_S00_fieldsId['localeCode'] 	= 'CORE_PROG001D0002E_S00_localeCode';
CORE_PROG001D0002E_S00_fieldsId['name'] 		= 'CORE_PROG001D0002E_S00_name';

function CORE_PROG001D0002E_S00_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG001D0002E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002E_S00_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0002E_S00_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0002E_S00_fieldsId);
		return;
	}		
	getQueryGrid_${programId}_grid();
}

function CORE_PROG001D0002E_S00_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0002E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0002E_S00_fieldsId);
	dijit.byId('CORE_PROG001D0002E_S00_localeCode').set("value", "${pageLocaleCode}");
	dijit.byId('CORE_PROG001D0002E_S00_name').set("value", "");
	dijit.byId('CORE_PROG001D0002E_S00_enableFlag').set("checked", true); 
	clearQuery_${programId}_grid();
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
		saveJsMethod="CORE_PROG001D0002E_S00_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="225px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Program Id / Default name (current no support edit mode)" id="CORE_PROG001D0002E_S00_mainInfoTempLabel"></gs:label>
    			<br/>
    			<s:property value="sysProg.progId"/>&nbsp;/&nbsp;<s:property value="sysProg.name"/>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Locale" id="CORE_PROG001D0002E_S00_localeCode" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_S00_localeCode"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0002E_S00_localeCode" dataSource="langDataMap" id="CORE_PROG001D0002E_S00_localeCode" value="pageLocaleCode"></gs:select>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Name" id="CORE_PROG001D0002E_S00_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0002E_S00_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0002E_S00_name" id="CORE_PROG001D0002E_S00_name" maxlength="100" width="300"></gs:textBox>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Enable" id="CORE_PROG001D0002E_S00_enableFlag" ></gs:label>
    			<br/>
    			<input id="CORE_PROG001D0002E_S00_enableFlag" name="CORE_PROG001D0002E_S00_enableFlag" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
    		</td>
    	</tr>
    	<tr>
    		<td height="25px" width="100%" align="left">
    			<gs:button name="CORE_PROG001D0002E_S00_save" id="CORE_PROG001D0002E_S00_save" onClick="CORE_PROG001D0002E_S00_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemProgramMultiNameSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.progOid'		: '${sysProg.oid}',
    						'fields.progId'			: '${sysProg.progId}',
    						'fields.localeCode'		: dijit.byId('CORE_PROG001D0002E_S00_localeCode').get('value'),
    						'fields.name'			: dijit.byId('CORE_PROG001D0002E_S00_name').get('value'),
    						'fields.enableFlag'		: ( dijit.byId('CORE_PROG001D0002E_S00_enableFlag').checked ? 'true' : 'false' )
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0002E_S00_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0002E_S00_clear" id="CORE_PROG001D0002E_S00_clear" onClick="CORE_PROG001D0002E_S00_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    
    				
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				
			    <gs:button name="CORE_PROG001D0002E_S00_query" id="CORE_PROG001D0002E_S00_query" onClick="getQueryGrid_${programId}_grid();"
			    	handleAs="json"
			    	sync="N"
			    	xhrUrl="core.systemProgramMultiNameGridQueryAction.action"
			    	parameterType="postData"
			    	xhrParameter=" 
			    		{
			    			'searchValue.parameter.progId'		: '${sysProg.progId}', 
			    			'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
			    			'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
			    			'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()			    	 
			    		} 
			    	"
			    	errorFn="clearQuery_${programId}_grid();"
			    	loadFn="dataGrid_${programId}_grid(data);" 
			    	programId="${programId}"
			    	label="Query" 
			    	iconClass="dijitIconSearch"
			    	cssClass="alt-primary"></gs:button>    				
    				
    							
    		</td>
    	</tr>
	</table>
	
	<gs:grid gridFieldStructure="CORE_PROG001D0002E_S00_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	