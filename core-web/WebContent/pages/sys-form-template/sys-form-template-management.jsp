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

function CORE_PROG001D0012Q_GridFieldStructure() {
	return [
			{ name: "View&nbsp;/&nbsp;Edit", field: "oid", formatter: CORE_PROG001D0012Q_GridButtonClick, width: "15%" },  
			{ name: "Id", field: "tplId", width: "15%" },
			{ name: "Name", field: "name", width: "20%" },
			{ name: "File", field: "fileName", width: "25%" },
			{ name: "Description", field: "description", width: "25%" }
		];	
}

function CORE_PROG001D0012Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"CORE_PROG001D0012Q_edit('" + itemOid + "');\" />";	
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('TEXT_SOURCE') + "\" border=\"0\" alt=\"edit template\" onclick=\"CORE_PROG001D0012Q_editTemplate('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";			
	rd += "<img src=\"" + _getSystemIconUrl('EXPORT') + "\" border=\"0\" alt=\"export\" onclick=\"CORE_PROG001D0012Q_downloadFile('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";		
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"CORE_PROG001D0012Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function CORE_PROG001D0012Q_clear() {
	dijit.byId('CORE_PROG001D0012Q_tplId').set('value', '');
	dijit.byId('CORE_PROG001D0012Q_name').set('value', '');
	clearQuery_${programId}_grid();	
}

function CORE_PROG001D0012Q_edit(oid) {
	CORE_PROG001D0012E_TabShow(oid);
}

function CORE_PROG001D0012Q_editTemplate(oid) {
	document.getElementById("CORE_PROG001D0012Q_templateContent").value = "";
	xhrSendParameter(
			'core.systemFormTemplateCopy2UploadAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ( 'Y' != data.success ) {
					return;
				}
				openCommonCodeEditorWindow(
						data.uploadOid, 
						"CORE_PROG001D0012Q_templateContent", 
						"CORE_PROG001D0012Q_updateTemplate('" + oid + "')",
						"jsp");			
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function CORE_PROG001D0012Q_updateTemplate(oid) {
	var content = document.getElementById("CORE_PROG001D0012Q_templateContent").value;
	if ( null == content ) {
		return;
	}
	xhrSendParameter(
			'core.systemFormTemplateContentUploadAction.action', 
			{ 
				'fields.oid' 		: oid,
				'fields.content'	: content
			}, 
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
}

function CORE_PROG001D0012Q_downloadFile(oid) {
	xhrSendParameter(
			'core.systemFormTemplateCopy2UploadAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ( 'Y' != data.success ) {
					return;
				}
				window.open(
						"<%=basePath%>/core.commonLoadUploadFileAction.action?oid=" + data.uploadOid + "&type=download",
						"form-template-file-export",
			            "resizable=yes,scrollbars=yes,status=yes,width=400,height=200"); 				
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function CORE_PROG001D0012Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete? ", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'core.systemFormTemplateDeleteAction.action', 
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

<body class="claro" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="Y"
		createNewJsMethod="CORE_PROG001D0012A_TabShow()"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<s:hidden name="CORE_PROG001D0012Q_templateContent" id="CORE_PROG001D0012Q_templateContent"></s:hidden>
	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="10%"  align="right">Id:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="CORE_PROG001D0012Q_tplId" id="CORE_PROG001D0012Q_tplId" value="" width="200" maxlength="10"></gs:textBox></td>
    		<td height="25px" width="10%"  align="right">Name:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="CORE_PROG001D0012Q_name" id="CORE_PROG001D0012Q_name" value="" width="200" maxlength="200"></gs:textBox></td>  					
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="4">
    			<gs:button name="CORE_PROG001D0012Q_query" id="CORE_PROG001D0012Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemFormTemplateManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.tplId'		: dijit.byId('CORE_PROG001D0012Q_tplId').get('value'), 
    						'searchValue.parameter.name'		: dijit.byId('CORE_PROG001D0012Q_name').get('value'),
    						'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="Query" 
    				iconClass="dijitIconSearch"></gs:button>
    			<gs:button name="CORE_PROG001D0012Q_clear" id="CORE_PROG001D0012Q_clear" onClick="CORE_PROG001D0012Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>
    		</td>
    	</tr>     	    	
    </table>	
    
    <gs:grid gridFieldStructure="CORE_PROG001D0012Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
