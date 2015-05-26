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

function BSC_PROG002D0004Q_GridFieldStructure() {
	return [
			{ name: "View&nbsp;/&nbsp;Edit", field: "oid", formatter: BSC_PROG002D0004Q_GridButtonClick, width: "10%" },  
			{ name: "Vision", field: "visionTitle", width: "10%" },
			{ name: "Perspective", field: "perspectiveName", width: "15%" },
			{ name: "Objective", field: "objectiveName", width: "15%" },
			{ name: "Id", field: "id", width: "10%" },
			{ name: "Name", field: "name", width: "20%" },
			{ name: "Weight", field: "weight", width: "5%" },
			{ name: "Description", field: "description", width: "15%" }	
		];
}

function BSC_PROG002D0004Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0004Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"BSC_PROG002D0004Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function BSC_PROG002D0004Q_clear() {
	dijit.byId('BSC_PROG002D0004Q_objectiveOid').set('value', _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004Q_perspectiveOid').set('value', _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0004Q_visionOid').set('value', _gscore_please_select_id);	
	dijit.byId('BSC_PROG002D0004Q_id').set('value', '');
	dijit.byId('BSC_PROG002D0004Q_name').set('value', '');
	clearQuery_${programId}_grid();
}

function BSC_PROG002D0004Q_edit(oid) {
	BSC_PROG002D0004E_TabShow(oid);
}

function BSC_PROG002D0004Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete? ", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.kpiDeleteAction.action', 
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

/**
 * Vision下拉觸發,改變Perspective下拉項目
 */
function BSC_PROG002D0004Q_triggerChangePerspectiveItems() {
	clearSelectItems(true, 'BSC_PROG002D0004Q_objectiveOid'); // 當 vision 改變後要將 Objective 清掉, 因為只會觸發 perspective 項目的改變
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004Q_visionOid',
			'BSC_PROG002D0004Q_perspectiveOid',
			'${basePath}/bsc.commonGetPerspectiveItemsAction.action',
			{ 'fields.visionOid' : dijit.byId("BSC_PROG002D0004Q_visionOid").get("value") }
	);	
}

/**
 * Perspective下拉觸發,改變Objective下拉項目
 */
function BSC_PROG002D0004Q_triggerChangeObjectiveItems() {
	selectChangeTriggerRefreshTargetSelectItems(
			'BSC_PROG002D0004Q_perspectiveOid',
			'BSC_PROG002D0004Q_objectiveOid',
			'${basePath}/bsc.commonGetObjectiveItemsAction.action',
			{ 'fields.perspectiveOid' : dijit.byId("BSC_PROG002D0004Q_perspectiveOid").get("value") }
	);	
}

function BSC_PROG002D0004Q_exportCsv() {
	xhrSendParameter(
			'${basePath}/bsc.commonDoExportData2CsvAction.action', 
			{ 'fields.exportId' : 'bb_kpi_001' }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					return;
				}
				openCommonLoadUpload( 'download', data.oid, { } );
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG002D0004Q_importCsv() {
	openCommonUploadDialog('BSC', 'tmp', 'N', 'BSC_PROG002D0004Q_uploadCsvOid', 'BSC_PROG002D0004Q_importCsvProcess', 'BSC_PROG002D0004Q_importCsvUploadFail');
}
function BSC_PROG002D0004Q_importCsvProcess() {
	hideCommonUploadDialog();
	xhrSendParameter(
			'${basePath}/bsc.commonDoImportCsvDataAction.action', 
			{ 
				'fields.importType' : 'kpi',
				'fields.uploadOid'	: dojo.byId("BSC_PROG002D0004Q_uploadCsvOid").value
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' == data.success) {
					getQueryGrid_${programId}_grid();
				}
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG002D0004Q_importCsvUploadFail() {
	dojo.byId("BSC_PROG002D0004Q_uploadCsvOid").value = "";
	hideCommonUploadDialog();
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
		createNewJsMethod="BSC_PROG002D0004A_TabShow();"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 
		exportEnable="Y"
		exportJsMethod="BSC_PROG002D0004Q_exportCsv();"	
		importEnable="Y"
		importJsMethod="BSC_PROG002D0004Q_importCsv();"								
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<input type="hidden" name="BSC_PROG002D0004Q_uploadCsvOid" id="BSC_PROG002D0004Q_uploadCsvOid" value=""/>
	
	<table border="0" width="100%" height="75px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="10%"  align="right">Vision:</td>
    		<td height="25px" width="40%"  align="left"><gs:select name="BSC_PROG002D0004Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0004Q_visionOid" onChange="BSC_PROG002D0004Q_triggerChangePerspectiveItems();"></gs:select></td>
    		<td height="25px" width="10%"  align="right">Perspective:</td>
    		<td height="25px" width="40%"  align="left"><gs:select name="BSC_PROG002D0004Q_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0004Q_perspectiveOid" onChange="BSC_PROG002D0004Q_triggerChangeObjectiveItems();"></gs:select></td>
    	</tr>
		<tr>
    		<td height="25px" width="10%"  align="right">Objective:</td>
    		<td height="25px" width="40%"  align="left"><gs:select name="BSC_PROG002D0004Q_objectiveOid" dataSource="objectiveMap" id="BSC_PROG002D0004Q_objectiveOid" ></gs:select></td>
    		<td height="25px" width="10%"  align="right">&nbsp;</td>
    		<td height="25px" width="40%"  align="left">&nbsp;</td>
    	</tr>	    		
		<tr>
    		<td height="25px" width="10%"  align="right">Id:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="BSC_PROG002D0004Q_id" id="BSC_PROG002D0004Q_id" value="" width="200" maxlength="14"></gs:textBox></td>  					
    		<td height="25px" width="10%"  align="right">Name:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="BSC_PROG002D0004Q_name" id="BSC_PROG002D0004Q_name" value="" width="200" maxlength="100"></gs:textBox></td>  					
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="4">
    			<gs:button name="BSC_PROG002D0004Q_query" id="BSC_PROG002D0004Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.kpiManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.visionOid'		: dijit.byId('BSC_PROG002D0004Q_visionOid').get('value'),
    						'searchValue.parameter.perspectiveOid'	: dijit.byId('BSC_PROG002D0004Q_perspectiveOid').get('value'),
    						'searchValue.parameter.objectiveOid'	: dijit.byId('BSC_PROG002D0004Q_objectiveOid').get('value'),
    						'searchValue.parameter.id'				: dijit.byId('BSC_PROG002D0004Q_id').get('value'), 
    						'searchValue.parameter.name'			: dijit.byId('BSC_PROG002D0004Q_name').get('value'),
    						'pageOf.size'							: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'							: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'						: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="Query" 
    				iconClass="dijitIconSearch"></gs:button>
    			<gs:button name="BSC_PROG002D0004Q_clear" id="BSC_PROG002D0004Q_clear" onClick="BSC_PROG002D0004Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG002D0004Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
