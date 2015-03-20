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
			{ name: "*", field: "oid", formatter: CORE_PROG003D0003E_S01_GridButtonClick, width: "10%" },  
			{ name: "Variable", field: "varName", width: "25%" },			
			{ name: "Method result", field: "methodResultFlag", width: "10%" },
			{ name: "Parameter class", field: "methodParamClass", width: "45%" },			
			{ name: "Parameter index", field: "methodParamIndex", width: "10%" }		
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
			"delete? ", 
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
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0003E_S01_fieldsId);		
		return;
	}		
	getQueryGrid_${programId}_grid();
}

function CORE_PROG003D0003E_S01_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0003E_S01_fieldsId);
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

<body class="claro" bgcolor="#EEEEEE" >

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
	
	<table border="0" width="100%" height="125px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="20%"  align="right">Variable:</td>
    		<td height="25px" width="80%"  align="left"><gs:textBox name="CORE_PROG003D0003E_S01_varName" id="CORE_PROG003D0003E_S01_varName" value="" width="400" maxlength="255" ></gs:textBox></td>
    	</tr>	
		<tr>
    		<td height="25px" width="20%"  align="right">Method result:</td>
    		<td height="25px" width="80%"  align="left"><input id="CORE_PROG003D0003E_S01_methodResultFlag" name="CORE_PROG003D0003E_S01_methodResultFlag" data-dojo-type="dijit/form/CheckBox" value="true" /></td>
    	</tr>	
		<tr>
    		<td height="25px" width="20%"  align="right">Method parameter class:</td>
    		<td height="25px" width="80%"  align="left"><gs:textBox name="CORE_PROG003D0003E_S01_methodParamClass" id="CORE_PROG003D0003E_S01_methodParamClass" value="" width="400" maxlength="255" ></gs:textBox></td>
    	</tr>      	   
		<tr>
    		<td height="25px" width="20%"  align="right">Method parameter index:</td>
    		<td height="25px" width="80%"  align="left"><gs:textBox name="CORE_PROG003D0003E_S01_methodParamIndex" id="CORE_PROG003D0003E_S01_methodParamIndex" value="" width="50" maxlength="3" ></gs:textBox></td>
    	</tr>     		
    	<tr>
    		<td height="25px" width="20%"  align="right">&nbsp;</td>
    		<td height="25px" align="left">
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
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="CORE_PROG003D0003E_S01_clear" id="CORE_PROG003D0003E_S01_clear" onClick="CORE_PROG003D0003E_S01_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
	<hr width="95%" align="center" size="2" color="#CFECEC">
	
	<table border="0" width="100%" height="25px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="100%"  align="center">
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
			    	label="Query expression" 
			    	iconClass="dijitIconSearch"></gs:button>
    		</td>    		  				
    	</tr>   		
	</table>
		
	<gs:grid gridFieldStructure="CORE_PROG003D0003E_S01_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

