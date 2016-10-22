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
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_01')}<br/><img src='./images/sort-up.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortDefaultASC();' alt='UP' title='sort up(A-Z)' />&nbsp;&nbsp;&nbsp;<img src='./images/sort-down.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortDefaultDESC();' alt='DOWN' title='sort down(Z-A)' />", field: "oid", formatter: BSC_PROG002D0004Q_GridButtonClick, width: "10%" },  
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_02')}", field: "visionTitle", width: "10%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_03')}", field: "perspectiveName", width: "15%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_04')}", field: "objectiveName", width: "15%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_05')}<br/><img src='./images/sort-up.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortIdASC();' alt='UP' title='sort up(A-Z)' />&nbsp;&nbsp;&nbsp;<img src='./images/sort-down.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortIdDESC();' alt='DOWN' title='sort down(Z-A)' />", field: "id", width: "10%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_06')}<br/><img src='./images/sort-up.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortNameASC();' alt='UP' title='sort up(A-Z)' />&nbsp;&nbsp;&nbsp;<img src='./images/sort-down.png' border='1' style='border:2px #BDBDBD dashed' onclick='BSC_PROG002D0004Q_grid_sortNameDESC();' alt='DOWN' title='sort down(Z-A)' />", field: "name", width: "20%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_07')}", field: "weight", width: "5%" },
			{ name: "${action.getText('BSC_PROG002D0004Q_grid_08')}", field: "description", width: "15%" }	
		];
}

// ---------------------------------------------------------------------
// order by & sort type
// ---------------------------------------------------------------------
function BSC_PROG002D0004Q_grid_sortDefaultASC() {
	setPageOfSortAsc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'v.visId, p.perId, o.objId, m.id');
	getQueryGrid_${programId}_grid();
}

function BSC_PROG002D0004Q_grid_sortDefaultDESC() {
	setPageOfSortDesc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'v.visId, p.perId, o.objId, m.id');
	getQueryGrid_${programId}_grid();
}

function BSC_PROG002D0004Q_grid_sortIdASC() {
	setPageOfSortAsc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'm.id');
	getQueryGrid_${programId}_grid();
}

function BSC_PROG002D0004Q_grid_sortIdDESC() {
	setPageOfSortDesc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'm.id');
	getQueryGrid_${programId}_grid();
}

function BSC_PROG002D0004Q_grid_sortNameASC() {
	setPageOfSortAsc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'm.name');
	getQueryGrid_${programId}_grid();
}

function BSC_PROG002D0004Q_grid_sortNameDESC() {
	setPageOfSortDesc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'm.name');
	getQueryGrid_${programId}_grid();
}
//---------------------------------------------------------------------

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
	
	setPageOfSortAsc('BSC_PROG002D0004Q_sortType');
	setPageOfOrderBy('BSC_PROG002D0004Q_orderBy', 'v.visId, p.perId, o.objId, m.id');
	
}

function BSC_PROG002D0004Q_edit(oid) {
	BSC_PROG002D0004E_TabShow(oid);
}

function BSC_PROG002D0004Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('BSC_PROG002D0004Q_confirmDelete')}", 
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

<body class="flat">

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
	
	<!-- order by & sort type default for query -->
	<input type="hidden" name="BSC_PROG002D0004Q_sortType" id="BSC_PROG002D0004Q_sortType" value="ASC"/>
	<input type="hidden" name="BSC_PROG002D0004Q_orderBy" id="BSC_PROG002D0004Q_orderBy" value="v.visId, p.perId, o.objId, m.id"/>
	
	<table border="0" width="100%" height="175px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0004Q_visionOid')}" id="BSC_PROG002D0004Q_visionOid"></gs:label>
    			<br/>
    			<gs:select name="BSC_PROG002D0004Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0004Q_visionOid" onChange="BSC_PROG002D0004Q_triggerChangePerspectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004Q_visionOid'">
    				Select vision.
				</div>
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0004Q_id')}" id="BSC_PROG002D0004Q_id"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004Q_id" id="BSC_PROG002D0004Q_id" value="" width="200" maxlength="14"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004Q_id'">
    				Input Id. 
				</div>
    		</td>
    	</tr>
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0004Q_perspectiveOid')}" id="BSC_PROG002D0004Q_perspectiveOid"></gs:label>
    			<br/>
    			<gs:select name="BSC_PROG002D0004Q_perspectiveOid" dataSource="perspectiveMap" id="BSC_PROG002D0004Q_perspectiveOid" onChange="BSC_PROG002D0004Q_triggerChangeObjectiveItems();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004Q_perspectiveOid'">
    				Select perspectives.
				</div>
    		</td>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0004Q_name')}" id="BSC_PROG002D0004Q_name"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG002D0004Q_name" id="BSC_PROG002D0004Q_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004Q_name'">
    				Input name.
				</div>    		
			</td>
    	</tr>	    		
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG002D0004Q_objectiveOid')}" id="BSC_PROG002D0004Q_objectiveOid"></gs:label>
    			<br/>
    			<gs:select name="BSC_PROG002D0004Q_objectiveOid" dataSource="objectiveMap" id="BSC_PROG002D0004Q_objectiveOid" ></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0004Q_objectiveOid'">
    				Select objectives.
				</div>
    		</td>
    		<td height="50px" width="50%"  align="left">&nbsp;</td>
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
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
    						'pageOf.showRow'						: getGridQueryPageOfShowRow_${programId}_grid(),
    						'pageOf.orderBy'						: dojo.byId('BSC_PROG002D0004Q_orderBy').value,
    						'pageOf.sortType'						: dojo.byId('BSC_PROG002D0004Q_sortType').value
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0004Q_query')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG002D0004Q_clear" id="BSC_PROG002D0004Q_clear" onClick="BSC_PROG002D0004Q_clear();" 
    				label="${action.getText('BSC_PROG002D0004Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG002D0004Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}" disableOnHeaderCellClick="Y"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
