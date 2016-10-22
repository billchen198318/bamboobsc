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

function CORE_PROG004D0002Q_GridFieldStructure() {
	return [
			{ name: "View&nbsp;/&nbsp;Edit", field: "OID", formatter: CORE_PROG004D0002Q_GridButtonClick, width: "10%" },  
			{ name: "User", field: "USER", width: "55%" },
			{ name: "Date", field: "CDATE", width: "35%" }
		];	
}

function CORE_PROG004D0002Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG004D0002Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function CORE_PROG004D0002Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete? ", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.commomLoadForm.action?prog_id=CORE_PROG004D0002Q&form_id=FORM_CORE_4D_002_01&form_method=delete', 
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

function CORE_PROG004D0002Q_clear() {
	dijit.byId('CORE_PROG004D0002Q_user').set('value', '');
	clearQuery_${programId}_grid();	
}

function CORE_PROG004D0002Q_removeAll() {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete? ", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.commomLoadForm.action?prog_id=CORE_PROG004D0002Q&form_id=FORM_CORE_4D_002_01&form_method=deleteAll', 
						{ }, 
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
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="10%"  align="right">Account:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="CORE_PROG004D0002Q_user" id="CORE_PROG004D0002Q_user" value="" width="200" maxlength="50"></gs:textBox></td>
    		<td height="25px" width="10%"  align="right">&nbsp;</td>
    		<td height="25px" width="40%"  align="left">&nbsp;</td>  					
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="4">
    			<gs:button name="CORE_PROG004D0002Q_query" id="CORE_PROG004D0002Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.commomLoadForm.action?prog_id=CORE_PROG004D0002Q&form_id=FORM_CORE_4D_002_01&form_method=query"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.user'	: dijit.byId('CORE_PROG004D0002Q_user').get('value'), 
    						'pageOf.size'					: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'					: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'				: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="Query" 
    				iconClass="dijitIconSearch"></gs:button>
    			<gs:button name="CORE_PROG004D0002Q_clear" id="CORE_PROG004D0002Q_clear" onClick="CORE_PROG004D0002Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>
    			<gs:button name="CORE_PROG004D0002Q_remove" id="CORE_PROG004D0002Q_remove" onClick="CORE_PROG004D0002Q_removeAll();" 
    				label="Remove" 
    				iconClass="dijitIconDelete"></gs:button>				
    		</td>
    	</tr>     	    	
    </table>	
    
    <gs:grid gridFieldStructure="CORE_PROG004D0002Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
