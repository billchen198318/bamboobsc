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

function BSC_PROG002D0002Q_GridFieldStructure() {
	return [
			{ name: "${action.getText('BSC_PROG002D0002Q_grid_01')}", field: "oid", formatter: BSC_PROG002D0002Q_GridButtonClick, width: "10%" },  
			{ name: "${action.getText('BSC_PROG002D0002Q_grid_02')}", field: "visionTitle", width: "30%" },
			{ name: "${action.getText('BSC_PROG002D0002Q_grid_03')}", field: "perId", width: "15%" },
			{ name: "${action.getText('BSC_PROG002D0002Q_grid_04')}", field: "name", width: "35%" },
			{ name: "${action.getText('BSC_PROG002D0002Q_grid_05')}", field: "weight", width: "10%" }			
		];
}

function BSC_PROG002D0002Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0002Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"BSC_PROG002D0002Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function BSC_PROG002D0002Q_clear() {
	dijit.byId('BSC_PROG002D0002Q_visionOid').set('value', _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0002Q_perId').set('value', '');
	dijit.byId('BSC_PROG002D0002Q_name').set('value', '');
	clearQuery_${programId}_grid();
}

function BSC_PROG002D0002Q_edit(oid) {
	BSC_PROG002D0002E_TabShow(oid);
}

function BSC_PROG002D0002Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('BSC_PROG002D0002Q_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.perspectiveDeleteAction.action', 
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

function BSC_PROG002D0002Q_exportCsv() {
	xhrSendParameter(
			'${basePath}/bsc.commonDoExportData2CsvAction.action', 
			{ 'fields.exportId' : 'bb_perspective_001' }, 
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

function BSC_PROG002D0002Q_importCsv() {
	openCommonUploadDialog('BSC', 'tmp', 'N', 'BSC_PROG002D0002Q_uploadCsvOid', 'BSC_PROG002D0002Q_importCsvProcess', 'BSC_PROG002D0002Q_importCsvUploadFail');
}
function BSC_PROG002D0002Q_importCsvProcess() {
	hideCommonUploadDialog();
	xhrSendParameter(
			'${basePath}/bsc.commonDoImportCsvDataAction.action', 
			{ 
				'fields.importType' : 'perspective',
				'fields.uploadOid'	: dojo.byId("BSC_PROG002D0002Q_uploadCsvOid").value
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
function BSC_PROG002D0002Q_importCsvUploadFail() {
	dojo.byId("BSC_PROG002D0002Q_uploadCsvOid").value = "";
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

<body class="flat">

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="Y"
		createNewJsMethod="BSC_PROG002D0002A_TabShow();"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 	
		exportEnable="Y"
		exportJsMethod="BSC_PROG002D0002Q_exportCsv();"	
		importEnable="Y"
		importJsMethod="BSC_PROG002D0002Q_importCsv();"					
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<input type="hidden" name="BSC_PROG002D0002Q_uploadCsvOid" id="BSC_PROG002D0002Q_uploadCsvOid" value=""/>
	
	<table border="0" width="100%" height="125px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0002Q_visionOid')}" id="BSC_PROG002D0002Q_visionOid"></gs:label>
    			<br/>
    			<gs:select name="BSC_PROG002D0002Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0002Q_visionOid"></gs:select>
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0002Q_perId')}" id="BSC_PROG002D0002Q_perId"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0002Q_perId" id="BSC_PROG002D0002Q_perId" value="" width="200" maxlength="14"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0002Q_perId'">
    				Input Id. example: PER20141115001
				</div>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0002Q_name')}" id="BSC_PROG002D0002Q_name"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0002Q_name" id="BSC_PROG002D0002Q_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0002Q_name'">
    				Input name.
				</div>
    		</td>
    		<td height="50px" width="50%"  align="left">&nbsp;</td>
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG002D0002Q_query" id="BSC_PROG002D0002Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.perspectiveManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.visionOid'	: dijit.byId('BSC_PROG002D0002Q_visionOid').get('value'),
    						'searchValue.parameter.perId'		: dijit.byId('BSC_PROG002D0002Q_perId').get('value'), 
    						'searchValue.parameter.name'		: dijit.byId('BSC_PROG002D0002Q_name').get('value'),
    						'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0002Q_query')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG002D0002Q_clear" id="BSC_PROG002D0002Q_clear" onClick="BSC_PROG002D0002Q_clear();" 
    				label="${action.getText('BSC_PROG002D0002Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG002D0002Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
