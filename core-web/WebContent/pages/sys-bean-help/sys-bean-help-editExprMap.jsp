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

function CORE_PROG003D0003E_S01_GridFieldStructure() {
	return [
			{ name: "${action.getText('CORE_PROG003D0003E_S01_grid_01')}", field: "oid", formatter: CORE_PROG003D0003E_S01_GridButtonClick, width: "10%" },  
			{ name: "${action.getText('CORE_PROG003D0003E_S01_grid_02')}", field: "varName", width: "25%" },			
			{ name: "${action.getText('CORE_PROG003D0003E_S01_grid_03')}", field: "methodResultFlag", width: "10%" },
			{ name: "${action.getText('CORE_PROG003D0003E_S01_grid_04')}", field: "methodParamClass", width: "45%" },			
			{ name: "${action.getText('CORE_PROG003D0003E_S01_grid_05')}", field: "methodParamIndex", width: "10%" }		
		];		
}

function CORE_PROG003D0003E_S01_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG003D0003E_S01_confirmDelete('" + itemOid + "');\" />";
	return rd;		
}

function CORE_PROG003D0003E_S01_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('CORE_PROG003D0003E_S01_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemBeanHelpExprMapDeleteAction.action', 
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

var CORE_PROG003D0003E_S01_fieldsId = new Object();
CORE_PROG003D0003E_S01_fieldsId['varName'] 			= 'CORE_PROG003D0003E_S01_varName';
CORE_PROG003D0003E_S01_fieldsId['methodParamClass'] = 'CORE_PROG003D0003E_S01_methodParamClass';
CORE_PROG003D0003E_S01_fieldsId['methodParamIndex'] = 'CORE_PROG003D0003E_S01_methodParamIndex';

function CORE_PROG003D0003E_S01_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_S01_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0003E_S01_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0003E_S01_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0003E_S01_fieldsId);
		return;
	}		
	getQueryGrid_${programId}_grid();
}

function CORE_PROG003D0003E_S01_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_S01_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0003E_S01_fieldsId);
	dijit.byId('CORE_PROG003D0003E_S01_varName').set("value", "");
	dijit.byId('CORE_PROG003D0003E_S01_methodResultFlag').set("checked", false);
	dijit.byId('CORE_PROG003D0003E_S01_methodParamClass').set("value", "");
	dijit.byId('CORE_PROG003D0003E_S01_methodParamIndex').set("value", ""); 
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
		saveJsMethod="CORE_PROG003D0003E_S01_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="275px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Expression Id / SEQ / Type" id="CORE_PROG003D0003E_S01_update_mainInfoTempLabel"></gs:label>
    			<br/>
    			<s:property value="sysBeanHelpExpr.exprId"/>&nbsp;/&nbsp;<s:property value="sysBeanHelpExpr.exprSeq"/>&nbsp;/&nbsp;<s:property value="sysBeanHelpExpr.runType"/>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S01_varName')}" id="CORE_PROG003D0003E_S01_varName" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S01_varName"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_S01_varName" id="CORE_PROG003D0003E_S01_varName" value="" width="400" maxlength="255" ></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S01_methodResultFlag')}" id="CORE_PROG003D0003E_S01_methodResultFlag"></gs:label>
    			<br/>
    			<input id="CORE_PROG003D0003E_S01_methodResultFlag" name="CORE_PROG003D0003E_S01_methodResultFlag" data-dojo-type="dijit/form/CheckBox" value="true" />
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S01_methodParamClass')}" id="CORE_PROG003D0003E_S01_methodParamClass" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S01_methodParamClass"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_S01_methodParamClass" id="CORE_PROG003D0003E_S01_methodParamClass" value="" width="400" maxlength="255" ></gs:textBox>
    		</td>
    	</tr>      	   
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0003E_S01_methodParamIndex')}" id="CORE_PROG003D0003E_S01_methodParamIndex" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0003E_S01_methodParamIndex"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0003E_S01_methodParamIndex" id="CORE_PROG003D0003E_S01_methodParamIndex" value="" width="50" maxlength="3" ></gs:textBox>
    		</td>
    	</tr>     		
    	<tr>
    		<td height="25px" width="100%" align="left">
    			<gs:button name="CORE_PROG003D0003E_S01_update" id="CORE_PROG003D0003E_S01_update" onClick="CORE_PROG003D0003E_S01_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemBeanHelpExprMapSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.helpExprOid'		: '${sysBeanHelpExpr.oid}',
    						'fields.varName'			: dijit.byId('CORE_PROG003D0003E_S01_varName').get('value'),
    						'fields.methodResultFlag'	: ( dijit.byId('CORE_PROG003D0003E_S01_methodResultFlag').checked ? 'true' : 'false' ),    						    						     						
    						'fields.methodParamClass'	: dijit.byId('CORE_PROG003D0003E_S01_methodParamClass').get('value'),       						 						    						
    						'fields.methodParamIndex'	: dijit.byId('CORE_PROG003D0003E_S01_methodParamIndex').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0003E_S01_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0003E_S01_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0003E_S01_clear" id="CORE_PROG003D0003E_S01_clear" onClick="CORE_PROG003D0003E_S01_clear();" 
    				label="${action.getText('CORE_PROG003D0003E_S01_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    	
    				
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				
			    <gs:button name="CORE_PROG003D0003E_S01_query" id="CORE_PROG003D0003E_S01_query" onClick="getQueryGrid_${programId}_grid();"
			    	handleAs="json"
			    	sync="N"
			    	xhrUrl="core.systemBeanHelpExprMapGridQueryAction.action"
			    	parameterType="postData"
			    	xhrParameter=" 
			    		{
			    			'searchValue.parameter.helpExprOid'	: '${sysBeanHelpExpr.oid}',
			    			'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
			    			'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
			    			'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()			    	 
			    		} 
			    	"
			    	errorFn="clearQuery_${programId}_grid();"
			    	loadFn="dataGrid_${programId}_grid(data);" 
			    	programId="${programId}"
			    	label="${action.getText('CORE_PROG003D0003E_S01_query')}" 
			    	iconClass="dijitIconSearch"
			    	cssClass="alt-primary"></gs:button>    				
			    	
    		</td>
    	</tr>     	 	  	    	
	</table>	
		
	<gs:grid gridFieldStructure="CORE_PROG003D0003E_S01_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

