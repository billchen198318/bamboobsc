<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);
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

function BSC_PROG002D0001Q_GridFieldStructure() {
	return [
			{ name: "${action.getText('BSC_PROG002D0001Q_grid_01')}", field: "oid", formatter: BSC_PROG002D0001Q_GridButtonClick, width: "15%" },  
			{ name: "${action.getText('BSC_PROG002D0001Q_grid_02')}", field: "visId", width: "25%" },
			{ name: "${action.getText('BSC_PROG002D0001Q_grid_03')}", field: "title", width: "60%" }			
		];
}

function BSC_PROG002D0001Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0001Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('APPLICATION_PDF') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0001Q_pdf('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"BSC_PROG002D0001Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function BSC_PROG002D0001Q_clear() {
	dijit.byId('BSC_PROG002D0001Q_visId').set('value', '');
	dijit.byId('BSC_PROG002D0001Q_title').set('value', '');
	clearQuery_${programId}_grid();
}

function BSC_PROG002D0001Q_edit(oid) {
	BSC_PROG002D0001E_TabShow(oid);
}

function BSC_PROG002D0001Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('BSC_PROG002D0001Q_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.visionDeleteAction.action', 
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

function BSC_PROG002D0001Q_pdf(oid) {
	openCommonJasperReportLoadWindow( "Vision-Report", "BSC_RPT001", "PDF", { 'oid' : oid } );
}

function BSC_PROG002D0001Q_exportCsv() {
	xhrSendParameter(
			'${basePath}/bsc.commonDoExportData2CsvAction.action', 
			{ 'fields.exportId' : 'bb_vision_001' }, 
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

function BSC_PROG002D0001Q_importCsv() {
	openCommonUploadDialog('BSC', 'tmp', 'N', 'BSC_PROG002D0001Q_uploadCsvOid', 'BSC_PROG002D0001Q_importCsvProcess', 'BSC_PROG002D0001Q_importCsvUploadFail');
}
function BSC_PROG002D0001Q_importCsvProcess() {
	hideCommonUploadDialog();
	xhrSendParameter(
			'${basePath}/bsc.commonDoImportCsvDataAction.action', 
			{ 
				'fields.importType' : 'vision',
				'fields.uploadOid'	: dojo.byId("BSC_PROG002D0001Q_uploadCsvOid").value
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
function BSC_PROG002D0001Q_importCsvUploadFail() {
	dojo.byId("BSC_PROG002D0001Q_uploadCsvOid").value = "";
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
		createNewJsMethod="BSC_PROG002D0001A_TabShow();"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 
		exportEnable="Y"
		exportJsMethod="BSC_PROG002D0001Q_exportCsv();"	
		importEnable="Y"
		importJsMethod="BSC_PROG002D0001Q_importCsv();"				
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<input type="hidden" name="BSC_PROG002D0001Q_uploadCsvOid" id="BSC_PROG002D0001Q_uploadCsvOid" value=""/>
	
	<table border="0" width="100%" height="75px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0001Q_visId')}" id="BSC_PROG002D0001Q_visId"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0001Q_visId" id="BSC_PROG002D0001Q_visId" value="" width="200" maxlength="14"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0001Q_visId'">
    				Input Id. example: VIS20141114001
				</div>
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0001Q_title')}" id="BSC_PROG002D0001Q_title"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0001Q_title" id="BSC_PROG002D0001Q_title" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0001Q_title'">
    				Input title.
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG002D0001Q_query" id="BSC_PROG002D0001Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.visionManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.visId'		: dijit.byId('BSC_PROG002D0001Q_visId').get('value'), 
    						'searchValue.parameter.title'		: dijit.byId('BSC_PROG002D0001Q_title').get('value'),
    						'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0001Q_query')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG002D0001Q_clear" id="BSC_PROG002D0001Q_clear" onClick="BSC_PROG002D0001Q_clear();" 
    				label="${action.getText('BSC_PROG002D0001Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG002D0001Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
