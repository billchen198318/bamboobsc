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

function CORE_PROG003D0003E_S00_GridFieldStructure() {
	return [
			{ name: "${action.getText('CORE_PROG003D0003E_S00_grid_01')}", field: "oid", formatter: CORE_PROG003D0003E_S00_GridButtonClick, width: "15%" },  
			{ name: "${action.getText('CORE_PROG003D0003E_S00_grid_02')}", field: "exprId", width: "45%" },			
			{ name: "${action.getText('CORE_PROG003D0003E_S00_grid_03')}", field: "exprSeq", width: "20%" },			
			{ name: "${action.getText('CORE_PROG003D0003E_S00_grid_04')}", field: "runType", width: "20%" }			
		];		
}

function CORE_PROG003D0003E_S00_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('IMPORTANT') + "\" border=\"0\" alt=\"edit\" onclick=\"CORE_PROG003D0003E_S00_editExpressionMap('" + itemOid + "');\" />";	
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG003D0003E_S00_confirmDelete('" + itemOid + "');\" />";
	return rd;		
}

function CORE_PROG003D0003E_S00_editExpressionMap(oid) {
	CORE_PROG003D0003E_S01_TabShow(oid);
}

function CORE_PROG003D0003E_S00_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('CORE_PROG003D0003E_S00_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemBeanHelpExprDeleteAction.action', 
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

var CORE_PROG003D0003E_S00_fieldsId = new Object();
CORE_PROG003D0003E_S00_fieldsId['exprOid'] 		= 'CORE_PROG003D0003E_S00_exprOid';
CORE_PROG003D0003E_S00_fieldsId['exprSeq'] 		= 'CORE_PROG003D0003E_S00_exprSeq';
CORE_PROG003D0003E_S00_fieldsId['runType'] 		= 'CORE_PROG003D0003E_S00_runType';

function CORE_PROG003D0003E_S00_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0003E_S00_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0003E_S00_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0003E_S00_fieldsId);
		return;
	}		
	getQueryGrid_${programId}_grid();
}

function CORE_PROG003D0003E_S00_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_S00_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0003E_S00_fieldsId);
	dijit.byId('CORE_PROG003D0003E_S00_exprOid').set("value", _gscore_please_select_id); 		
	dijit.byId('CORE_PROG003D0003E_S00_exprSeq').set("value", "");
	dijit.byId('CORE_PROG003D0003E_S00_runType').set("value", _gscore_please_select_id); 
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
		saveJsMethod="CORE_PROG003D0003E_S00_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="225px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="SYSTEM / Bean-Id / Method" id="CORE_PROG003D0003E_S00_update_mainInfoTempLabel"></gs:label>
    			<br/>
    			<s:property value="sysBeanHelp.system"/>&nbsp;/&nbsp;<s:property value="sysBeanHelp.beanId"/>&nbsp;/&nbsp;<s:property value="sysBeanHelp.method"/>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S00_exprOid')}" id="CORE_PROG003D0003E_S00_exprOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S00_exprOid"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0003E_S00_exprOid" dataSource="expressionMap" id="CORE_PROG003D0003E_S00_exprOid" ></gs:select>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S00_exprSeq')}" id="CORE_PROG003D0003E_S00_exprSeq" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S00_exprSeq"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_S00_exprSeq" id="CORE_PROG003D0003E_S00_exprSeq" value="" width="200" maxlength="10" ></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S00_runType')}" id="CORE_PROG003D0003E_S00_runType" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S00_runType"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0003E_S00_runType" dataSource="runTypeMap" id="CORE_PROG003D0003E_S00_runType"></gs:select>
    		</td>
    	</tr>      	   	
    	<tr>
    		<td height="25px" width="100%" align="left">
    			<gs:button name="CORE_PROG003D0003E_S00_update" id="CORE_PROG003D0003E_S00_update" onClick="CORE_PROG003D0003E_S00_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemBeanHelpExprSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.helpOid'		: '${sysBeanHelp.oid}',
    						'fields.exprOid'		: dijit.byId('CORE_PROG003D0003E_S00_exprOid').get('value'),     						
    						'fields.exprSeq'		: dijit.byId('CORE_PROG003D0003E_S00_exprSeq').get('value'),    						
    						'fields.runType'		: dijit.byId('CORE_PROG003D0003E_S00_runType').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0003E_S00_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0003E_S00_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0003E_S00_clear" id="CORE_PROG003D0003E_S00_clear" onClick="CORE_PROG003D0003E_S00_clear();" 
    				label="${action.getText('CORE_PROG003D0003E_S00_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    
    				
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				
			    <gs:button name="CORE_PROG003D0003E_S00_query" id="CORE_PROG003D0003E_S00_query" onClick="getQueryGrid_${programId}_grid();"
			    	handleAs="json"
			    	sync="N"
			    	xhrUrl="core.systemBeanHelpExprGridQueryAction.action"
			    	parameterType="postData"
			    	xhrParameter=" 
			    		{
			    			'searchValue.parameter.helpOid'		: '${sysBeanHelp.oid}', 
			    			'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
			    			'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
			    			'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()			    	 
			    		} 
			    	"
			    	errorFn="clearQuery_${programId}_grid();"
			    	loadFn="dataGrid_${programId}_grid(data);" 
			    	programId="${programId}"
			    	label="${action.getText('CORE_PROG003D0003E_S00_query')}" 
			    	iconClass="dijitIconSearch"
			    	cssClass="alt-primary"></gs:button>    				
    				
    							
    		</td>
    	</tr>     	 	  	    	
	</table>	
		
	<gs:grid gridFieldStructure="CORE_PROG003D0003E_S00_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

