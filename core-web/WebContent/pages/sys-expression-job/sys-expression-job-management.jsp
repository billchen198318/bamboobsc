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

function CORE_PROG003D0006Q_GridFieldStructure() {
	return [
			{ name: "*", field: "oid", formatter: CORE_PROG003D0006Q_GridButtonClick, width: "10%" },
			{ name: "System", field: "system", width: "10%" },
			{ name: "Id", field: "id", width: "15%" },
			{ name: "Name", field: "name", width: "25%" },
			{ name: "Expression Id", field: "exprId", width: "15%" },
			{ name: "Run datetime", field: "runDatetime", width: "15%" },
			{ name: "Active", field: "active", width: "5%" },
			{ name: "Status", field: "runStatus", width: "5%" }
		];	
}

function CORE_PROG003D0006Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"CORE_PROG003D0006Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";				
	rd += "<img src=\"" + _getSystemIconUrl('SYSTEM') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG003D0006Q_confirmExecuteJob('" + itemOid + "');\" />";	
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";				
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG003D0006Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function CORE_PROG003D0006Q_clear() {	
	dijit.byId('CORE_PROG003D0006Q_id').set('value', '');
	dijit.byId('CORE_PROG003D0006Q_name').set('value', '');	
	clearQuery_${programId}_grid();	
}

function CORE_PROG003D0006Q_edit(oid) {
	CORE_PROG003D0006E_TabShow(oid);
}

function CORE_PROG003D0006Q_confirmExecuteJob(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"Warning, are you sure manual run this Job?", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemExpressionJobManualExecuteAction.action', 
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

function CORE_PROG003D0006Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete?", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemExpressionJobDeleteAction.action', 
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
		createNewEnable="Y"
		createNewJsMethod="CORE_PROG003D0006A_TabShow()"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<table border="0" width="100%" height="75px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="Id" id="CORE_PROG003D0006Q_id"></gs:label>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006Q_id" id="CORE_PROG003D0006Q_id" value="" width="200" maxlength="20"></gs:textBox>
    		</td>		  	
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<gs:label text="Name" id="CORE_PROG003D0006Q_name"></gs:label>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0006Q_name" id="CORE_PROG003D0006Q_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    						
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
    			<gs:button name="CORE_PROG003D0006Q_query" id="CORE_PROG003D0006Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemExpressionJobManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{  
    						'searchValue.parameter.id'		: dijit.byId('CORE_PROG003D0006Q_id').get('value'),
    						'searchValue.parameter.name'		: dijit.byId('CORE_PROG003D0006Q_name').get('value'),
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
    			<gs:button name="CORE_PROG003D0006Q_clear" id="CORE_PROG003D0006Q_clear" onClick="CORE_PROG003D0006Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr>     	    	
    </table>	
    
    <gs:grid gridFieldStructure="CORE_PROG003D0006Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
