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

var BSC_PROG006D0001E_fieldsId = new Object();
BSC_PROG006D0001E_fieldsId["title"] 									= "BSC_PROG006D0001E_title";
BSC_PROG006D0001E_fieldsId["startDate"] 								= "BSC_PROG006D0001E_contentTab_startDate";
BSC_PROG006D0001E_fieldsId["endDate"] 									= "BSC_PROG006D0001E_contentTab_endDate";
BSC_PROG006D0001E_fieldsId["orgaOids"] 									= "BSC_PROG006D0001E_contentTab_orgaOids_noticeMessageOnly";
BSC_PROG006D0001E_fieldsId["emplOids"] 									= "BSC_PROG006D0001E_contentTab_emplOids_noticeMessageOnly";
BSC_PROG006D0001E_fieldsId["kpiOids"] 									= "BSC_PROG006D0001E_contentTab_kpiOids_noticeMessageOnly";
BSC_PROG006D0001E_fieldsId["measureFreq_frequency"] 					= "BSC_PROG006D0001E_contentTab_measureFreq_frequency";
BSC_PROG006D0001E_fieldsId["measureFreq_startYearDate"] 				= "BSC_PROG006D0001E_contentTab_measureFreq_startYearDate";
BSC_PROG006D0001E_fieldsId["measureFreq_endYearDate"] 					= "BSC_PROG006D0001E_contentTab_measureFreq_endYearDate";
BSC_PROG006D0001E_fieldsId["measureFreq_startDate"] 					= "BSC_PROG006D0001E_contentTab_measureFreq_startDate";
BSC_PROG006D0001E_fieldsId["measureFreq_endDate"] 						= "BSC_PROG006D0001E_contentTab_measureFreq_endDate";
BSC_PROG006D0001E_fieldsId["measureFreq_dataFor"] 						= "BSC_PROG006D0001E_contentTab_measureFreq_dataFor";
BSC_PROG006D0001E_fieldsId["measureFreq_measureDataOrganizationOid"] 	= "BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid";
BSC_PROG006D0001E_fieldsId["measureFreq_measureDataEmployeeOid"] 		= "BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid";

/* show inputfield notice message only */
BSC_PROG006D0001E_fieldsId["BSC_PROG006D0001E_pdcaTab_type"] 		= "BSC_PROG006D0001E_pdcaTab_type";
BSC_PROG006D0001E_fieldsId["BSC_PROG006D0001E_pdcaTab_title"] 		= "BSC_PROG006D0001E_pdcaTab_title";
BSC_PROG006D0001E_fieldsId["BSC_PROG006D0001E_pdcaTab_startDate"] 		= "BSC_PROG006D0001E_pdcaTab_startDate";

function BSC_PROG006D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG006D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG006D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG006D0001E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG006D0001E_fieldsId);
		return;
	}	
	//BSC_PROG006D0001E_clear();
}

function BSC_PROG006D0001E_clear() {
	BSC_PROG006D0001E_TabRefresh();
}

function BSC_PROG006D0001E_startProcessSuccess(data) {
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ( 'Y' == data.success ) {
		BSC_PROG006D0001E_TabRefresh();
	}
}

//------------------------------------------------------------------------------
// Content-Tab
//------------------------------------------------------------------------------
function BSC_PROG006D0001E_contentTab_reloadEmployeeAppendName() {
	var appendOid = dojo.byId('BSC_PROG006D0001E_contentTab_appendEmployeeOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG006D0001E_contentTab_appendEmployeeOid').value = '';
		dojo.byId('BSC_PROG006D0001E_contentTab_employeeAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG006D0001E_contentTab_appendEmployeeOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG006D0001E_contentTab_employeeAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG006D0001E_contentTab_clearEmplAppendId() {
	dojo.byId('BSC_PROG006D0001E_contentTab_appendEmployeeOid').value = '';
	dojo.byId('BSC_PROG006D0001E_contentTab_employeeAppendName').innerHTML = '';
}

function BSC_PROG006D0001E_contentTab_reloadOrganizationAppendName() {
	var appendOid = dojo.byId('BSC_PROG006D0001E_contentTab_appendOrganizationOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG006D0001E_contentTab_appendOrganizationOid').value = '';
		dojo.byId('BSC_PROG006D0001E_contentTab_organizationAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetOrganizationNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG006D0001E_contentTab_appendOrganizationOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG006D0001E_contentTab_organizationAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);		
}
function BSC_PROG006D0001E_contentTab_clearOrgaAppendId() {
	dojo.byId('BSC_PROG006D0001E_contentTab_appendOrganizationOid').value = '';
	dojo.byId('BSC_PROG006D0001E_contentTab_organizationAppendName').innerHTML = '';
}

//------------------------------------------------------------------------------
//Content-Tab measure options settings function
//------------------------------------------------------------------------------
function BSC_PROG006D0001E_contentTab_measureFreq_setDataForValue() {
	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid').set("value", _gscore_please_select_id);	
	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid').set('readOnly', true);
	var dataFor = dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_dataFor").get("value");
	if ('employee' == dataFor) {
		dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid').set('readOnly', false);
	}
	if ('organization' == dataFor) {
		dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').set('readOnly', false);
	}	
}

function BSC_PROG006D0001E_contentTab_measureFreq_setMeasureDataOrgaValue() {
	var dataFor = dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'employee' == dataFor) {
		dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}
}

function BSC_PROG006D0001E_contentTab_measureFreq_setMeasureDataEmplValue() {
	var dataFor = dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_dataFor").get("value");
	if ('all' == dataFor || 'organization' == dataFor) {
		dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG006D0001E_contentTab_measureFreq_setFrequencyValue() {
	var frequency = dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_frequency").get("value");
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_startYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_endYearDate").set("readOnly", true);
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_startDate").set("readOnly", true);
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_endDate").set("readOnly", true);
	if ( frequency == _gscore_please_select_id ) {
		return;
	}
	if ('1' == frequency || '2' == frequency || '3' == frequency) { // day, week, month
		dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_startDate").set("readOnly", false);
		dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_endDate").set("readOnly", false);		
	} else { // quarter, half-year, year
		dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_startYearDate").set("readOnly", false);
		dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_endYearDate").set("readOnly", false);				
	}
}

//------------------------------------------------------------------------------
//Content-Tab upload function
//------------------------------------------------------------------------------
var BSC_PROG006D0001E_contentTab_maxUpload = 5;
var BSC_PROG006D0001E_contentTab_uploads = [];

function BSC_PROG006D0001E_contentTab_uploadDocument() {
	if (BSC_PROG006D0001E_contentTab_uploads.length >= BSC_PROG006D0001E_contentTab_maxUpload) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "Can only upload " + BSC_PROG006D0001E_contentTab_maxUpload + " files!", function(){}, "Y");
		return;
	}
	openCommonUploadDialog('BSC', 'tmp', 'Y', 'BSC_PROG006D0001E_contentTab_uploadDocumentOid', 'BSC_PROG006D0001E_contentTab_uploadSuccess', 'BSC_PROG006D0001E_contentTab_uploadFail');
}
function BSC_PROG006D0001E_contentTab_uploadSuccess() {
	var docOid = dojo.byId("BSC_PROG006D0001E_contentTab_uploadDocumentOid").value;	
	if (docOid!='') {
		BSC_PROG006D0001E_contentTab_uploads.push({
			'oid' 	: docOid,
			'name' 	: BSC_PROG006D0001E_contentTab_getUploadShowName(docOid)
		});
	}
	dojo.byId("BSC_PROG006D0001E_contentTab_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
	BSC_PROG006D0001E_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001E_contentTab_uploadFail() {
	dojo.byId("BSC_PROG006D0001E_contentTab_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
}
function BSC_PROG006D0001E_contentTab_delUpload(oid) {
	var size = BSC_PROG006D0001E_contentTab_uploads.length;
	for (var n=0; n<size; n++) {
		if ( BSC_PROG006D0001E_contentTab_uploads[n].oid == oid ) {
			BSC_PROG006D0001E_contentTab_uploads.splice( n , 1 );
			n = size;
		}
	}	
	BSC_PROG006D0001E_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001E_contentTab_clearUploadDataTable() {
	BSC_PROG006D0001E_contentTab_uploads = [];
	dojo.byId("BSC_PROG006D0001E_contentTab_uploadDocumentOid").value = "";
	BSC_PROG006D0001E_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001E_contentTab_showUploadDataTable() {
	var size = BSC_PROG006D0001E_contentTab_uploads.length;	
	var txtContent = '';
	if (BSC_PROG006D0001E_contentTab_uploads.length > 0) {
		txtContent += '<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">';
		txtContent += '<tr>';
		txtContent += '<td width="20%" align="center" bgcolor="#f1eee5">*</td>';
		txtContent += '<td width="80%" align="left" bgcolor="#f1eee5"><b>Documents (file name)</b></td>';
		txtContent += '</tr>';
		for (var n=0; n<size; n++) {
			var dataItem = BSC_PROG006D0001E_contentTab_uploads[n];
			txtContent += '<tr>';
			var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG006D0001E_contentTab_delUpload(\'' + dataItem.oid + '\');" /> ';
			txtContent += '<td width="20%" align="center" bgcolor="#ffffff">' + img + '</td>';
			txtContent += '<td width="80%" align="left" bgcolor="#ffffff"><a href="#" onclick="openCommonLoadUpload( \'download\', \'' + dataItem.oid + '\', {}); return false;" style="color:#424242">' + dataItem.name + '</a></td>';
			txtContent += '</tr>';				
		}
		txtContent += '</table>';		
	}
	dojo.byId( 'BSC_PROG006D0001E_contentTab_uploadDocumentTable' ).innerHTML = txtContent;	
}
function BSC_PROG006D0001E_contentTab_getUploadShowName(oid) {
	var names = getUploadFileNames(oid);	
	if (names == null || names.length!=1 ) {
		return "unknown";
	}
	return names[0].showName;
}

function BSC_PROG006D0001E_contentTab_reloadKPIsAppendName() {
	var appendOid = dojo.byId('BSC_PROG006D0001E_contentTab_appendKPIsOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG006D0001E_contentTab_appendKPIsOid').value = '';
		dojo.byId('BSC_PROG006D0001E_contentTab_kpisAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetKpisNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG006D0001E_contentTab_appendKPIsOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG006D0001E_contentTab_kpisAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);
}
function BSC_PROG006D0001E_contentTab_clearKPIsAppendId() {
	dojo.byId('BSC_PROG006D0001E_contentTab_appendKPIsOid').value = '';
	dojo.byId('BSC_PROG006D0001E_contentTab_kpisAppendName').innerHTML = '';	
}


//------------------------------------------------------------------------------
//Content-PDCA function
//------------------------------------------------------------------------------
var BSC_PROG006D0001E_pdcaTab_item_node = [];
function BSC_PROG006D0001E_pdcaTab_add() {	
	if (BSC_PROG006D0001E_pdcaTab_item_node.length>=40) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Max only 40 items!', function(){}, 'N');
		return;
	}
	
	setFieldsNoticeMessageLabelDefault(BSC_PROG006D0001E_fieldsId);
	
	var type = dijit.byId("BSC_PROG006D0001E_pdcaTab_type").get("value");
	var title = dijit.byId("BSC_PROG006D0001E_pdcaTab_title").get("value").trim();
	var startDate = dijit.byId("BSC_PROG006D0001E_pdcaTab_startDate").get('displayedValue');
	var endDate = dijit.byId("BSC_PROG006D0001E_pdcaTab_endDate").get('displayedValue');
	if (_gscore_please_select_id == type) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Please select item type!', function(){}, 'N');
		showFieldsNoticeMessageLabel('BSC_PROG006D0001E_pdcaTab_type'+_gscore_inputfieldNoticeMsgLabelIdName, 'Please select item type!');
		return;
	}
	if ('' == title) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Please input item title!', function(){}, 'N');
		showFieldsNoticeMessageLabel('BSC_PROG006D0001E_pdcaTab_title'+_gscore_inputfieldNoticeMsgLabelIdName, 'Please input item title!');
		return;
	}
	if ('' == startDate || '' == endDate) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Please select start-date and end-date!', function(){}, 'N');
		showFieldsNoticeMessageLabel('BSC_PROG006D0001E_pdcaTab_startDate'+_gscore_inputfieldNoticeMsgLabelIdName, 'Please select start-date and end-date!');
		return;
	}
	for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
		if (BSC_PROG006D0001E_pdcaTab_item_node[i].type == type && BSC_PROG006D0001E_pdcaTab_item_node[i].title == title) {
			alertDialog(_getApplicationProgramNameById('${programId}'), 'Same item title is found!', function(){}, 'N');
			showFieldsNoticeMessageLabel('BSC_PROG006D0001E_pdcaTab_title'+_gscore_inputfieldNoticeMsgLabelIdName, 'Same item title is found!');
			return;
		}
	}
	BSC_PROG006D0001E_pdcaTab_item_node.push({
		id			: viewPage.generateGuid(),
		type		: type,
		title		: viewPage.escape1(title),
		startDate	: startDate,
		endDate		: endDate,
		description	: viewPage.escape1(dijit.byId("BSC_PROG006D0001E_pdcaTab_description").get("value")),
		upload		: [],		
		ownerOids	: '',
		appendNames	: '' // for show only
	});
	BSC_PROG006D0001E_pdcaTab_itemTablePaint(BSC_PROG006D0001E_pdcaTab_item_node);
	dijit.byId("BSC_PROG006D0001E_pdcaTab_type").set("value", _gscore_please_select_id);
	dijit.byId("BSC_PROG006D0001E_pdcaTab_title").set("value", "");
	dijit.byId("BSC_PROG006D0001E_pdcaTab_description").set("value", "");
	dijit.byId("BSC_PROG006D0001E_pdcaTab_startDate").set("value", "");
	dijit.byId("BSC_PROG006D0001E_pdcaTab_endDate").set("value", "");	
	dijit.byId("BSC_PROG006D0001E_pdcaTab_startDate").set("displayedValue", "");
	dijit.byId("BSC_PROG006D0001E_pdcaTab_endDate").set("displayedValue", "");
}
function BSC_PROG006D0001E_pdcaTab_tableContentDel(id) {
	var size = BSC_PROG006D0001E_pdcaTab_item_node.length;
	for (var n=0; n<size; n++) {
		if ( BSC_PROG006D0001E_pdcaTab_item_node[n].id == id ) {
			BSC_PROG006D0001E_pdcaTab_item_node.splice( n , 1 );
			n = size;
		}
	}	
	BSC_PROG006D0001E_pdcaTab_itemTablePaint(BSC_PROG006D0001E_pdcaTab_item_node);
}
function BSC_PROG006D0001E_pdcaTab_itemTablePaint() {
	dojo.html.set(dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent"), "", {extractContent: true, parseContent: true});
	var htm = '';
	htm += '<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">';
	htm += '<tr>';
	htm += '<td width="10%" align="center" bgcolor="#f1eee5">*</td>';
	htm += '<td width="10%" align="center" bgcolor="#f1eee5"><b>Type</b></td>';
	htm += '<td width="80%" align="center" bgcolor="#f1eee5"><b>Title / Date / description</b></td>';
	htm += '</tr>';	
	for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
		var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
		var idHead = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + node.id;
		if (dijit.byId( idHead + '_emplSelect' )!= null) {
			dijit.byId( idHead + '_emplSelect' ).destroy( true );
		}
		if (dijit.byId( idHead + '_emplClear' )!= null) {
			dijit.byId( idHead + '_emplClear' ).destroy( true );
		}
		if (dijit.byId( idHead + '_uploadDocumentBtn' )!= null) {
			dijit.byId( idHead + '_uploadDocumentBtn' ).destroy( true );
		}
		if (dijit.byId( idHead + '_uploadDocumentClearBtn' )!= null) {
			dijit.byId( idHead + '_uploadDocumentClearBtn' ).destroy( true );
		}		
		var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG006D0001E_pdcaTab_tableContentDel(\'' + node.id + '\');" /> ';
		htm += '<tr>';
		htm += '<td width="10%" align="center" bgcolor="#ffffff">' + img + '</td>';
		htm += '<td width="10%" align="center" bgcolor="#ffffff">' + node.type + '</td>';
		htm += '<td width="80%" align="left" bgcolor="#ffffff">' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData(node) + '</td>';
		htm += '</tr>';
	}
	htm += '</table>';
	dojo.html.set(dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent"), htm, {extractContent: true, parseContent: true});
	
	// show PDCA item table - upload documents table content.
	for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
		var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
		BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId = node.id;
		BSC_PROG006D0001E_pdcaTab_tableContent_showUploadDataTable();
	}
	
	
}
var BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId = '';
function BSC_PROG006D0001E_pdcaTab_itemTableTdContentData(node) {
	var id = node.id;
	var idHead = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + id;
	
	var str = '';
	str += '<div class="isa_success"><b>' + node.title + '</b>';
	if (node.description.trim() != '') {
		str += '<br/>' + node.description;
	}	
	str += '</div>';
	str += '<b>Date range</b>&nbsp;' + node.startDate + ' - ' + node.endDate + '<br/>';
	
	
	str += '<input type="hidden" name="' + idHead +'_appendEmployeeOid" id="' + idHead +'_appendEmployeeOid" value="' + node.ownerOids + '" />';
	str += '<b>Responsibility&nbsp;(Employee)</b>&nbsp;<font color="RED">*</font>&nbsp;&nbsp;';
	str += '<button name="' + idHead + '_emplSelect" id="' + idHead + '_emplSelect" data-dojo-type="dijit.form.Button" class="alt-info" ';
	str += '	data-dojo-props=" ';
	str += '		showLabel:false, ';
	str += '		iconClass:\'dijitIconFolderOpen\', ';
	str += '		onClick:function(){ ';
	str += ' 			BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId=\'' + id + '\'; ';
	str += '			BSC_PROG001D0001Q_S00_DlgShow(\'' + idHead + '_appendEmployeeOid;BSC_PROG006D0001E_pdcaTab_itemTablePaint_reloadEmployeeAppendName\'); ';
	str += '		} ';
	str += '	"></button>';
	str += '<button name="' + idHead + '_emplClear" id="' + idHead + '_emplClear" data-dojo-type="dijit.form.Button" ';
	str += '	data-dojo-props=" ';
	str += '		showLabel:false, ';
	str += '		iconClass:\'dijitIconClear\', ';
	str += '		onClick:function(){ ';
	str += ' 			BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId=\'' + id + '\'; ';
	str += '			BSC_PROG006D0001E_pdcaTab_itemTablePaint_clearEmplAppendId(); ';
	str += '		} ';
	str += '	"></button>';
	str += '<br/>';
	str += '<span id="' + idHead + '_employeeAppendName">' + node.appendNames + '</span>';
	
	str += '<br/>';
	
	str += '<input type="hidden" name="' + idHead +'_uploadDocumentOid" id="' + idHead +'_uploadDocumentOid" value="' + node.ownerOids + '" />';
	str += '<b>Document / attachment</b>&nbsp;&nbsp;';
	str += '<button name="' + idHead + '_uploadDocumentBtn" id="' + idHead + '_uploadDocumentBtn" data-dojo-type="dijit.form.Button" class="alt-info" ';
	str += '	data-dojo-props=" ';
	str += '		showLabel:false, ';
	str += '		iconClass:\'dijitIconFolderOpen\', ';
	str += '		onClick:function(){ ';
	str += ' 			BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId=\'' + id + '\'; ';
	str += '			BSC_PROG006D0001E_pdcaTab_tableContent_uploadDocument(); ';
	str += '		} ';
	str += '	"></button>';
	str += '<button name="' + idHead + '_uploadDocumentClearBtn" id="' + idHead + '_uploadDocumentClearBtn" data-dojo-type="dijit.form.Button" ';
	str += '	data-dojo-props=" ';
	str += '	showLabel:false, ';
	str += '	iconClass:\'dijitIconClear\', ';
	str += '	onClick:function(){ ';
	str += ' 		BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId=\'' + id + '\'; ';
	str += '		BSC_PROG006D0001E_pdcaTab_tableContent_clearUploadDataTable(); ';
	str += '	} ';
	str += '	"></button>';
	str += '<br/>';
	str += '<div id="' + idHead + '_uploadDocumentTable"></div>';
	
	
	return str;
}
//------------------------------------------------------------------------------
//Content-PDCA item owner load function
//------------------------------------------------------------------------------
function BSC_PROG006D0001E_pdcaTab_itemTablePaint_reloadEmployeeAppendName() {
	var oidField = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_appendEmployeeOid';
	var showField = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_employeeAppendName';
	var appendOid = dojo.byId(oidField).value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId(oidField).value = '';
		dojo.byId(showField).innerHTML = '';
		for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
			var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
			if (node.id == BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId) {
				node.ownerOids = '';
				node.appendNames = '';
			}
		}		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId(oidField).value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId(showField).innerHTML = data.appendName;
					for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
						var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
						if (node.id == BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId) {
							node.ownerOids = dojo.byId(oidField).value;
							node.appendNames = data.appendName;
						}
					}
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG006D0001E_pdcaTab_itemTablePaint_clearEmplAppendId() {
	var oidField = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_appendEmployeeOid';
	var showField = 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_employeeAppendName';
	dojo.byId(oidField).value = '';
	dojo.byId(showField).innerHTML = '';
	for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
		var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
		if (node.id == BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId) {
			node.ownerOids = '';
			node.appendNames = '';
		}
	}	
}
//------------------------------------------------------------------------------
//Content-PDCA item upload load function
//------------------------------------------------------------------------------
var BSC_PROG006D0001E_pdcaTab_tableContent_maxUpload = 3;
function BSC_PROG006D0001E_pdcaTab_tableContent_getNode() {
	for (var i=0; i<BSC_PROG006D0001E_pdcaTab_item_node.length; i++) {
		var node = BSC_PROG006D0001E_pdcaTab_item_node[i];
		if (node.id == BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId) {
			return node;
		}
	}
	return null;
}

function BSC_PROG006D0001E_pdcaTab_tableContent_uploadDocument() {
	var node = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();
	if (node.upload.length >= BSC_PROG006D0001E_pdcaTab_tableContent_maxUpload) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "Can only upload " + BSC_PROG006D0001E_pdcaTab_tableContent_maxUpload + " files!", function(){}, "Y");
		return;
	}
	openCommonUploadDialog('BSC', 'tmp', 'Y', 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_uploadDocumentOid', 'BSC_PROG006D0001E_pdcaTab_tableContent_uploadSuccess', 'BSC_PROG006D0001E_pdcaTab_tableContent_uploadFail');
}
function BSC_PROG006D0001E_pdcaTab_tableContent_uploadSuccess() {
	var node = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();
	var docOid = dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent_" + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + "_uploadDocumentOid").value;	
	if (docOid!='') {
		node.upload.push({
			'oid' 	: docOid,
			'name' 	: BSC_PROG006D0001E_pdcaTab_tableContent_getUploadShowName(docOid)
		});
	}
	dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent_" + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + "_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
	BSC_PROG006D0001E_pdcaTab_tableContent_showUploadDataTable();
}
function BSC_PROG006D0001E_pdcaTab_tableContent_uploadFail() {
	dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent_" + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + "_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
}
function BSC_PROG006D0001E_pdcaTab_tableContent_delUpload(oid) {
	var node = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();
	var size = node.upload.length;
	for (var n=0; n<size; n++) {
		if ( node.upload[n].oid == oid ) {
			node.upload.splice( n , 1 );
			n = size;
		}
	}	
	BSC_PROG006D0001E_pdcaTab_tableContent_showUploadDataTable();
}
function BSC_PROG006D0001E_pdcaTab_tableContent_clearUploadDataTable() {
	var node = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();
	node.upload = [];
	dojo.byId("BSC_PROG006D0001E_pdcaTab_tableContent_" + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + "_uploadDocumentOid").value = "";
	BSC_PROG006D0001E_pdcaTab_tableContent_showUploadDataTable();
}
function BSC_PROG006D0001E_pdcaTab_tableContent_showUploadDataTable() {
	var node = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();
	var size = node.upload.length;	
	var txtContent = '';
	if (node.upload.length > 0) {
		txtContent += '<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">';
		txtContent += '<tr>';
		txtContent += '<td width="20%" align="center" bgcolor="#f1eee5">*</td>';
		txtContent += '<td width="80%" align="left" bgcolor="#f1eee5"><b>Documents (file name)</b></td>';
		txtContent += '</tr>';
		for (var n=0; n<size; n++) {
			var dataItem = node.upload[n];
			txtContent += '<tr>';
			var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId=\'' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '\';BSC_PROG006D0001E_pdcaTab_tableContent_delUpload(\'' + dataItem.oid + '\');" /> ';
			txtContent += '<td width="20%" align="center" bgcolor="#ffffff">' + img + '</td>';
			txtContent += '<td width="80%" align="left" bgcolor="#ffffff"><a href="#" onclick="openCommonLoadUpload( \'download\', \'' + dataItem.oid + '\', {}); return false;" style="color:#424242">' + dataItem.name + '</a></td>';
			txtContent += '</tr>';				
		}
		txtContent += '</table>';		
	}
	dojo.byId( 'BSC_PROG006D0001E_pdcaTab_tableContent_' + BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId + '_uploadDocumentTable' ).innerHTML = txtContent;	
}
function BSC_PROG006D0001E_pdcaTab_tableContent_getUploadShowName(oid) {
	var names = getUploadFileNames(oid);	
	if (names == null || names.length!=1 ) {
		return "unknown";
	}
	return names[0].showName;
}

//------------------------------------------------------------------------------
//PDCA project audit function
//------------------------------------------------------------------------------
function BSC_PROG006D0001E_openConfirmDialog(keyStr) {
	BSC_PROG006D0001E_S00_DlgShow(keyStr);
}

function BSC_PROG006D0001E_loadDiagram(taskId) {
	xhrSendParameter(
			'${basePath}/bsc.pdcaLoadTaskDiagramAction.action', 
			{ 'fields.taskId' : taskId }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ( 'Y' == data.success ) {
					openCommonLoadUpload(
							'view', 
							data.uploadOid, 
							{ 
								"isDialog" 	: 	"Y",
								"title"		:	_getApplicationProgramNameById('${programId}'),
								"width"		:	800,
								"height"		:	600
							} 
					);
				}
			}, 
			function(error) {
				alert(error);
			}
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
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG006D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- ##################################################################### -->
	<!-- ContentTab hidden field -->
	<input type="hidden" name="BSC_PROG006D0001E_contentTab_appendEmployeeOid" id="BSC_PROG006D0001E_contentTab_appendEmployeeOid" value="${fields.appendOwnerOidsForPdcaOwner}" />
	<input type="hidden" name="BSC_PROG006D0001E_contentTab_uploadDocumentOid" id="BSC_PROG006D0001E_contentTab_uploadDocumentOid" value="" /><!-- 這個upload放oid的欄位只是當temp用 -->
	<input type="hidden" name="BSC_PROG006D0001E_contentTab_appendKPIsOid" id="BSC_PROG006D0001E_contentTab_appendKPIsOid" value="${fields.appendKpiOidsForPdcaKpis}" />
	<input type="hidden" name="BSC_PROG006D0001E_contentTab_appendOrganizationOid" id="BSC_PROG006D0001E_contentTab_appendOrganizationOid" value="${fields.appendOrgaOidsForPdcaOrga}" />
	<!-- ##################################################################### -->
	
	
	<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Title" id="BSC_PROG006D0001E_title" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_title"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG006D0001E_title" id="BSC_PROG006D0001E_title" value="pdca.title" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_title'">
    				Input title.
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG006D0001E_update" id="BSC_PROG006D0001E_update" onClick="BSC_PROG006D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.pdcaUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'				: 	'${pdca.oid}',
    						'fields.title'			:	dijit.byId('BSC_PROG006D0001E_title').get('value'),
    						'fields.startDate'		:	dijit.byId('BSC_PROG006D0001E_contentTab_startDate').get('displayedValue'),
    						'fields.endDate'		:	dijit.byId('BSC_PROG006D0001E_contentTab_endDate').get('displayedValue'),
    						'fields.description'	:	dijit.byId('BSC_PROG006D0001E_contentTab_description').get('value'),
    						'fields.orgaOids'		:	dojo.byId('BSC_PROG006D0001E_contentTab_appendOrganizationOid').value,
    						'fields.emplOids'		:	dojo.byId('BSC_PROG006D0001E_contentTab_appendEmployeeOid').value,
    						'fields.kpiOids'		:	dojo.byId('BSC_PROG006D0001E_contentTab_appendKPIsOid').value,
    						'fields.uploadOids'		:	JSON.stringify( { 'oids' : BSC_PROG006D0001E_contentTab_uploads } ),
    						
    						'fields.measureFreq_frequency'					:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_frequency').get('value'),    						
							'fields.measureFreq_startYearDate'				:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_startYearDate').get('displayedValue'),							
							'fields.measureFreq_endYearDate'				:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_endYearDate').get('displayedValue'),
							'fields.measureFreq_startDate'					:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_startDate').get('displayedValue'),
							'fields.measureFreq_endDate'					:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_endDate').get('displayedValue'),
							'fields.measureFreq_dataFor'					:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_dataFor').get('value'),
							'fields.measureFreq_measureDataOrganizationOid'	:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid').get('value'),
							'fields.measureFreq_measureDataEmployeeOid'		:	dijit.byId('BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid').get('value'),
							    						
    						'fields.itemsData'		:	JSON.stringify( { 'items' : BSC_PROG006D0001E_pdcaTab_item_node } )
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG006D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG006D0001E_clear" id="BSC_PROG006D0001E_clear" onClick="BSC_PROG006D0001E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    				
    			&nbsp;&nbsp;&nbsp;&nbsp;
    			<gs:button id="BSC_PROG006D0001E_startProcess" name="BSC_PROG006D0001E_startProcess" onClick="BSC_PROG006D0001E_startProcess();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.pdcaStartProcessAction.action"
    				parameterType="postData"
    				xhrParameter="
    					{ 
    						'fields.oid' : '${pdca.oid}'
    					}
    				"
    				errorFn=""
    				loadFn="BSC_PROG006D0001E_startProcessSuccess(data);" 
    				programId="${programId}"
    				label="Start apply"
    				iconClass="dijitIconSave"
    				confirmDialogMode="Y"
    				confirmDialogTitle=""
    				confirmDialogMsg="Start apply?"
    				cssClass="alt-warning"></gs:button>
    				
    			<button name="BSC_PROG006D0001E_openConfirmDialog" id="BSC_PROG006D0001E_openConfirmDialog"  data-dojo-type="dijit.form.Button"
    				<s:if test=" bpmTaskObj == null || \"Y\" != bpmTaskObj.allowApproval "> disabled="disabled" </s:if>
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							<s:if test=" bpmTaskObj != null ">
							BSC_PROG006D0001E_openConfirmDialog('${pdca.oid};${bpmTaskObj.task.id}');
							</s:if>
						}
					"
    				class="alt-primary">Confirm audit</button>
				
				<button name="BSC_PROG006D0001E_loadDiagram" id="BSC_PROG006D0001E_loadDiagram" data-dojo-type="dijit.form.Button"
					<s:if test=" bpmTaskObj == null "> disabled="disabled" </s:if>
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							<s:if test=" bpmTaskObj != null ">
							BSC_PROG006D0001E_loadDiagram('${bpmTaskObj.task.id}');
							</s:if>
						}
					"
					class="alt-primary">View audit diagram</button>			    				    				
    				
    						
    		</td>
    	</tr>
	</table>
	
	<s:if test=" bpmTaskObj != null && bpmTaskObj.variables != null ">
	<div data-dojo-type="dijit.TitlePane" data-dojo-props=" title: 'Current audit info' " open="true">
		<div data-dojo-type="dijit/layout/ContentPane" title="Audit info" data-dojo-props="selected:true">
			<div class="isa_info">
				<b>Now task:</b>&nbsp;<s:property value="bpmTaskObj.task.name" />
				<br/>
				<b>Before audit</b>
				<br/>
				&nbsp;&nbsp;&nbsp;<b>Confirm:</b>&nbsp;${bpmTaskObj.variables.confirm}
				<br/>
				&nbsp;&nbsp;&nbsp;<b>Assignee:</b>&nbsp;<s:property value="bpmTaskObj.task.assignee" />
				<br/>
				&nbsp;&nbsp;&nbsp;<b>Date:</b>&nbsp;${bpmTaskObj.variables.dateDisplayValue}
				<br/>
				&nbsp;&nbsp;&nbsp;<b>Reason:</b><br/>
				&nbsp;&nbsp;&nbsp;<s:property value="bpmTaskObj.variables.reason" escapeHtml="true"/>
			</div>
		</div>
	</div>
	</s:if>
	
    <div data-dojo-type="dijit/layout/TabContainer" style="width: 100%; height: 100%;" data-dojo-props="region:'center', tabStrip:true" id="BSC_PROG006D0001E_TabContainer">
        
        <!-- ##################################################################### -->
        <!-- ContentTab -->
        <div data-dojo-type="dijit/layout/ContentPane" title="Content" data-dojo-props="selected:true">
			<table border="0" width="100%" height="600px" cellpadding="1" cellspacing="0" >
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:label text="Date range" id="BSC_PROG006D0001E_contentTab_dateRangeLabel" requiredFlag="Y"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_startDate"></gs:inputfieldNoticeMsgLabel>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_endDate"></gs:inputfieldNoticeMsgLabel>
		    			<br/>
		    			Start
		    			<input id="BSC_PROG006D0001E_contentTab_startDate" type="text" name="BSC_PROG006D0001E_contentTab_startDate" data-dojo-type="dijit.form.DateTextBox"
		    				maxlength="10" 
		    				value="${pdca.startDateTextBoxValue}"
		    				constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_startDate'">
							Select start date.
						</div>
						&nbsp;						
						End
						<input id="BSC_PROG006D0001E_contentTab_endDate" type="text" name="BSC_PROG006D0001E_contentTab_endDate" data-dojo-type="dijit.form.DateTextBox"
							maxlength="10" 
							value="${pdca.endDateTextBoxValue}"
							constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />																	    									    	
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_endDate'">
							Select end date.
						</div>  			
		    		</td>
		    	</tr>
		    	<tr>
		    		<td height="50px" width="100%"  align="left">
		    			
		    			<div data-dojo-type="dijit.TitlePane" data-dojo-props=" title: 'BSC Report measure settings' " open="true">	
						<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:140px">
						
							<table border="0" width="100%" >
							
								<tr valign="top">
									<td width="100%" align="left" height="25px">														
										Frequency
										<gs:select name="BSC_PROG006D0001E_contentTab_measureFreq_frequency" dataSource="frequencyMap" id="BSC_PROG006D0001E_contentTab_measureFreq_frequency" value="measureFreq.freq" onChange="BSC_PROG006D0001E_contentTab_measureFreq_setFrequencyValue();" width="140"></gs:select>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_frequency'">
						    				Select frequency.
										</div> 									
									</td>											
								</tr>	
													
								<tr valign="top">
									<td width="100%" align="left" height="25px">
									
								    	Start year
								    	<input id="BSC_PROG006D0001E_contentTab_measureFreq_startYearDate" name="BSC_PROG006D0001E_contentTab_measureFreq_startYearDate" data-dojo-type="dojox.form.YearTextBox" 
								    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' value="${fields.measureFreqStartYearDate}"/>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_startYearDate'">
						    				Select start year.
										</div>							    		
								    	&nbsp;	
								    	End year
								    	<input id="BSC_PROG006D0001E_contentTab_measureFreq_endYearDate" name="BSC_PROG006D0001E_contentTab_measureFreq_endYearDate" data-dojo-type="dojox.form.YearTextBox" 
								    		maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' value="${fields.measureFreqEndYearDate}"/>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_endYearDate'">
						    				Select end year.
										</div>							    									    	
								    	&nbsp;&nbsp;		
										Start date
										<input id="BSC_PROG006D0001E_contentTab_measureFreq_startDate" type="text" name="BSC_PROG006D0001E_contentTab_measureFreq_startDate" data-dojo-type="dijit.form.DateTextBox"
											maxlength="10" 
											constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" value="${fields.measureFreqStartDate}" />
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_startDate'">
						    				Select start date.
										</div>											
										&nbsp;						
										End date
										<input id="BSC_PROG006D0001E_contentTab_measureFreq_endDate" type="text" name="BSC_PROG006D0001E_contentTab_measureFreq_endDate" data-dojo-type="dijit.form.DateTextBox"
											maxlength="10" 
											constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" value="${fields.measureFreqEndDate}" />																	    									    	
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_endDate'">
						    				Select end date.
										</div>							    			
								    </td>	
								</tr>
								<tr valign="top">
									<td width="100%" align="left" height="25px">							
										Data type
										<gs:select name="BSC_PROG006D0001E_contentTab_measureFreq_dataFor" dataSource="{ \"all\":\"All\", \"organization\":\"Organization / department\", \"employee\":\"Personal / employee\" }" id="BSC_PROG006D0001E_contentTab_measureFreq_dataFor" value="fields.measureFreqDataFor" onChange="BSC_PROG006D0001E_contentTab_measureFreq_setDataForValue();" width="140"></gs:select>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_dataFor'">
						    				Select measure data type.
										</div>										
										&nbsp;&nbsp;
										Organization / department
										<gs:select name="BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid" dataSource="measureDataOrganizationMap" id="BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid" onChange="BSC_PROG006D0001E_contentTab_measureFreq_setMeasureDataOrgaValue();" value="measureFreq.organizationOid"></gs:select>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid'">
						    				Select measure data organization/department.
										</div>									
										&nbsp;&nbsp;
										Personal / employee
										<gs:select name="BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid" dataSource="measureDataEmployeeMap" id="BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid" onChange="BSC_PROG006D0001E_contentTab_measureFreq_setMeasureDataEmplValue();" value="measureFreq.employeeOid"></gs:select>
										<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid'">
						    				Select measure data personal/Employee.
										</div>									
									</td>
								</tr>																						
																												
							</table>
				    		
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_frequency"></gs:inputfieldNoticeMsgLabel>
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_startYearDate"></gs:inputfieldNoticeMsgLabel>
				    		<!-- 
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_endYearDate"></gs:inputfieldNoticeMsgLabel>
				    		-->
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_startDate"></gs:inputfieldNoticeMsgLabel>
				    		<!-- 
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_endDate"></gs:inputfieldNoticeMsgLabel>
				    		-->
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_dataFor"></gs:inputfieldNoticeMsgLabel>
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid"></gs:inputfieldNoticeMsgLabel>
				    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid"></gs:inputfieldNoticeMsgLabel>				    		
				    			
			    		</div>	
			    		</div>	 
			    		   		    		
		    		</td>
		    	</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left" colspan="2">
		    			<gs:label text="Responsibility (Organization / Department)" id="BSC_PROG006D0001E_contentTab_deptSelect" requiredFlag="Y"></gs:label>
		    			&nbsp;&nbsp;
						<button name="BSC_PROG006D0001E_contentTab_deptSelect" id="BSC_PROG006D0001E_contentTab_deptSelect" data-dojo-type="dijit.form.Button" class="alt-info"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG001D0002Q_S00_DlgShow('BSC_PROG006D0001E_contentTab_appendOrganizationOid;BSC_PROG006D0001E_contentTab_reloadOrganizationAppendName');
								}
							"></button>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_deptSelect'">
		    				Select responsibility&nbsp;(Organization / Department).
						</div>					
						<button name="BSC_PROG006D0001E_contentTab_deptClear" id="BSC_PROG006D0001E_contentTab_deptClear" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001E_contentTab_clearOrgaAppendId();
								}
							"></button>	
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_deptClear'">
		    				Clear responsibility&nbsp;(Organization / Department).
						</div>
						<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_orgaOids_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
						<br/>	    			    			
		    			<span id="BSC_PROG006D0001E_contentTab_organizationAppendName"><s:property value="fields.appendOrgaNamesForPdcaOrga"/></span>    			
		    		</td>
		    	</tr>     		    	
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:label text="Responsibility (Employee)" id="BSC_PROG006D0001E_contentTab_emplSelect" requiredFlag="Y"></gs:label>
		    			&nbsp;&nbsp;
						<button name="BSC_PROG006D0001E_contentTab_emplSelect" id="BSC_PROG006D0001E_contentTab_emplSelect" data-dojo-type="dijit.form.Button" class="alt-info"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG006D0001E_contentTab_appendEmployeeOid;BSC_PROG006D0001E_contentTab_reloadEmployeeAppendName');
								}
							"></button>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_emplSelect'">
		    				Select responsibility&nbsp;(Employee). 
						</div>						
						<button name="BSC_PROG006D0001E_contentTab_emplClear" id="BSC_PROG006D0001E_contentTab_emplClear" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001E_contentTab_clearEmplAppendId();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_emplClear'">
		    				Clear responsibility (Employee). 
						</div>
						<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_emplOids_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
						<br/>
						<span id="BSC_PROG006D0001E_contentTab_employeeAppendName"><s:property value="fields.appendOwnerNamesForPdcaOwner"/></span>	 		    			
		    		</td>
		    	</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:label text="KPIs" id="BSC_PROG006D0001E_contentTab_kpiSelect" requiredFlag="Y"></gs:label>
		    			&nbsp;&nbsp;
						<button name="BSC_PROG006D0001E_contentTab_kpiSelect" id="BSC_PROG006D0001E_contentTab_kpiSelect" data-dojo-type="dijit.form.Button" class="alt-info"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG002D0004Q_S00_DlgShow('BSC_PROG006D0001E_contentTab_appendKPIsOid;BSC_PROG006D0001E_contentTab_reloadKPIsAppendName');
								}
							"></button>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_kpiSelect'">
		    				Select KPIs. 
						</div>						
						<button name="BSC_PROG006D0001E_contentTab_kpiClear" id="BSC_PROG006D0001E_contentTab_kpiClear" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001E_contentTab_clearKPIsAppendId();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_kpiClear'">
		    				Clear KPIs. 
						</div>
						<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_contentTab_kpiOids_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
						<br/>
						<span id="BSC_PROG006D0001E_contentTab_kpisAppendName"><s:property value="fields.appendKpiNamesForPdcaKpis"/></span>	 		    			
		    		</td>
		    	</tr>		    			    	
				<tr>
		    		<td height="125px" width="100%"  align="left">
				    	<gs:label text="Description" id="BSC_PROG006D0001E_contentTab_description"></gs:label>
				    	<br/>
				    	<textarea id="BSC_PROG006D0001E_contentTab_description" name="BSC_PROG006D0001E_contentTab_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${pdca.description}</textarea>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_description'">
		    				Input description, the maximum allowed 500 characters.
						</div>		    			
		    		</td>
		    	</tr>		    	
				<tr>
				    <td height="150px" width="100%" align="left" colspan="2">
				    	<gs:label text="Document / attachment" id="BSC_PROG006D0001E_contentTab_uploadDocument"></gs:label>
				    	<br/>
						<button name="BSC_PROG006D0001E_contentTab_uploadDocumentBtn" id="BSC_PROG006D0001E_contentTab_uploadDocumentBtn" data-dojo-type="dijit.form.Button" class="alt-info"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG006D0001E_contentTab_uploadDocument();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_uploadDocumentBtn'">
		    				Upload project document/attachment. 
						</div>  
						<button name="BSC_PROG006D0001E_contentTab_uploadDocumentClearBtn" id="BSC_PROG006D0001E_contentTab_uploadDocumentClearBtn" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001E_contentTab_clearUploadDataTable();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_contentTab_uploadDocumentClearBtn'">
		    				Clear project upload document/attachment. 
						</div>												    	
				    	<br/>
				    	<div id="BSC_PROG006D0001E_contentTab_uploadDocumentTable"></div>
				    </td>
				</tr> 		    	
		    </table>
        </div>
        <!-- ##################################################################### -->
        
        
        <!-- ##################################################################### -->
        <!-- PDCA Tab -->        
        <div data-dojo-type="dijit/layout/ContentPane" title="Plan / Do / Check / Action" data-dojo-props="selected:false">
        	<table border="0" width="100%" cellpadding="1" cellspacing="0" >
        		<tr>
        			<td height="50px" width="100%"  align="left">
        				<gs:label text="Type" id="BSC_PROG006D0001E_pdcaTab_type" requiredFlag="Y"></gs:label>
        				<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_pdcaTab_type"></gs:inputfieldNoticeMsgLabel>
        				<br/>
        				<gs:select name="BSC_PROG006D0001E_pdcaTab_type" dataSource="pdcaTypeMap" id="BSC_PROG006D0001E_pdcaTab_type"  width="140"></gs:select>
        			</td>
        		</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:label text="Title" id="BSC_PROG006D0001E_pdcaTab_title" requiredFlag="Y"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_pdcaTab_title"></gs:inputfieldNoticeMsgLabel>
		    			<br/>
		    			<gs:textBox name="BSC_PROG006D0001E_pdcaTab_title" id="BSC_PROG006D0001E_pdcaTab_title" value="" width="400" maxlength="100"></gs:textBox>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_pdcaTab_title'">
		    				Input title.
						</div>
		    		</td>
		    	</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:label text="Date range" id="BSC_PROG006D0001E_pdcaTab_dateRangeLabel" requiredFlag="Y"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_pdcaTab_startDate"></gs:inputfieldNoticeMsgLabel>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG006D0001E_pdcaTab_endDate"></gs:inputfieldNoticeMsgLabel>		    			
		    			<br/>
		    			Start
		    			<input id="BSC_PROG006D0001E_pdcaTab_startDate" type="text" name="BSC_PROG006D0001E_pdcaTab_startDate" data-dojo-type="dijit.form.DateTextBox"
		    				maxlength="10" 
		    				constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_pdcaTab_startDate'">
							Select start date.
						</div>
						&nbsp;						
						End
						<input id="BSC_PROG006D0001E_pdcaTab_endDate" type="text" name="BSC_PROG006D0001E_pdcaTab_endDate" data-dojo-type="dijit.form.DateTextBox"
							maxlength="10" 
							constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />																	    									    	
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_pdcaTab_endDate'">
							Select end date.
						</div>  			
		    		</td>
		    	</tr>	
				<tr>
		    		<td height="125px" width="100%"  align="left">
				    	<gs:label text="Description" id="BSC_PROG006D0001E_pdcaTab_description"></gs:label>
				    	<br/>
				    	<textarea id="BSC_PROG006D0001E_pdcaTab_description" name="BSC_PROG006D0001E_pdcaTab_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_pdcaTab_description'">
		    				Input description, the maximum allowed 500 characters.
						</div>		    			
		    		</td>
		    	</tr>	
		    	<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<gs:button name="BSC_PROG006D0001E_pdcaTab_add" id="BSC_PROG006D0001E_pdcaTab_add" onClick="BSC_PROG006D0001E_pdcaTab_add();" 
		    				label="Add" 
		    				iconClass="dijitIconSave"
		    				cssClass="alt-primary"></gs:button>       		
		    		</td>
		    	</tr>		    			    		    	
    		</table>
    		
    		
    		<!-- PDCA item table content -->
    		<div id="BSC_PROG006D0001E_pdcaTab_tableContent"></div>
    		
    		
        </div>
        <!-- ##################################################################### -->
        
        
    </div>
	
<script type="text/javascript">${programId}_page_message();</script>
<script type="text/javascript">


//-----------------------------------------------------------------------------------
<s:if test="pdcaDocs!=null && pdcaDocs.size!=0">
<s:iterator value="pdcaDocs" status="st">
BSC_PROG006D0001E_contentTab_uploads.push( {oid: '<s:property value="uploadOid" />' , name: '<s:property value="showName" escapeJavaScript="true"/>'} );
</s:iterator>
</s:if>
//-----------------------------------------------------------------------------------


// -----------------------------------------------------------------------------------
<s:if test="pdcaItems!=null && pdcaItems.size!=0">

var BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_editMode_tmpNode = null;

<s:iterator value="pdcaItems" status="st">

BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId = viewPage.generateGuid();

BSC_PROG006D0001E_pdcaTab_item_node.push({
	id			: BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId,
	type		: '${type}',
	title		: '<s:property value="title" escapeJavaScript="true"/>',
	startDate	: '<s:property value="startDateDisplayValue"/>',
	endDate		: '<s:property value="endDateDisplayValue"/>',
	description	: '<s:property value="description" escapeJavaScript="true"/>',
	upload		: [],		
	ownerOids	: '${employeeAppendOids}',
	appendNames	: '<s:property value="employeeAppendNames" escapeJavaScript="true"/>' 
});
BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_editMode_tmpNode = BSC_PROG006D0001E_pdcaTab_tableContent_getNode();

	<s:iterator value="docs" status="st">
BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_editMode_tmpNode.upload.push( {oid : '<s:property value="uploadOid" />', name: '<s:property value="showName" escapeJavaScript="true"/>'} );
	</s:iterator>

</s:iterator>

BSC_PROG006D0001E_pdcaTab_itemTableTdContentData_nowClickId = '';

</s:if>
//-----------------------------------------------------------------------------------


// -----------------------------------------------------------------------------------
setTimeout(function(){ 
	BSC_PROG006D0001E_contentTab_measureFreq_setFrequencyValue();
	BSC_PROG006D0001E_contentTab_measureFreq_setDataForValue();
	
	BSC_PROG006D0001E_contentTab_showUploadDataTable();
	BSC_PROG006D0001E_pdcaTab_itemTablePaint();
	
	<s:if test=" \"\" != measureFreq.organizationOid && null != measureFreq.organizationOid ">
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_measureDataOrganizationOid").set("value", "${measureFreq.organizationOid}");
	</s:if>
	<s:if test=" \"\" != measureFreq.employeeOid && null != measureFreq.employeeOid ">
	dijit.byId("BSC_PROG006D0001E_contentTab_measureFreq_measureDataEmployeeOid").set("value", "${measureFreq.employeeOid}");
	</s:if>
	
}, 1500);
//-----------------------------------------------------------------------------------


</script>
</body>
</html>
