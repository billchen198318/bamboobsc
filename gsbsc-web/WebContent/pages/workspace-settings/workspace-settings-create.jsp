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

var BSC_PROG004D0002A_fieldsId = new Object();
BSC_PROG004D0002A_fieldsId['workspaceId'] 			= 'BSC_PROG004D0002A_C00_workspaceId';
BSC_PROG004D0002A_fieldsId['workspaceName'] 		= 'BSC_PROG004D0002A_C00_workspaceName';
BSC_PROG004D0002A_fieldsId['workspaceTemplateOid'] 	= 'BSC_PROG004D0002A_C01_workspaceTemplateOid';

function BSC_PROG004D0002A_save() {	
	setFieldsBackgroundDefault(BSC_PROG004D0002A_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG004D0002A_fieldsId);	
	if (dojo.byId("BSC_PROG004D0002A_C01_table") == null) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "<s:property value="getText('MESSAGE.BSC_PROG004D0002A_C01_table')" escapeJavaScript="true"/>", function(){}, "Y");
		showFieldsNoticeMessageLabel(
				'BSC_PROG004D0002A_C01_workspaceTemplateOid'+_gscore_inputfieldNoticeMsgLabelIdName, 
				"<s:property value="getText('MESSAGE.BSC_PROG004D0002A_C01_table')" escapeJavaScript="true"/>");
		return;
	}
	var table = document.getElementById("BSC_PROG004D0002A_C01_table");
	var positionIdx = 0;
	var nodes = [];
	var labels = [];
	for (var r=0; r<table.rows.length; r++) {
		for (var c=0; c<table.rows[r].cells.length; c++) {			
			var content = table.rows[r].cells[c].innerHTML;
			var titleId = "BSC_PROG004D0002A_C01_table_label_position_" + positionIdx;
			if (content.indexOf("BSC_PROG004D0002A_C01_COMPOMENT:")>-1) {
				var idStr = content.substring( content.indexOf("<!--")+4, content.indexOf("-->") );
				idStr = idStr.split(":")[1];
				nodes.push({id: idStr, position: positionIdx});
			}			
			if ( document.getElementById(titleId) != null ) {
				labels.push({label: document.getElementById(titleId).value, position: positionIdx});
			}
			positionIdx++;
		}
	}	
    var datas = {};
    datas.nodes = nodes;	
    datas.labels = labels;
	//alert( JSON.stringify(datas) );
	
	xhrSendParameter(
			'${basePath}/bsc.workspaceSettingsSaveAction.action', 
			{ 
				'fields.workspaceId' 			: 	dijit.byId("BSC_PROG004D0002A_C00_workspaceId").get("value"),
				'fields.workspaceName'			:	dijit.byId("BSC_PROG004D0002A_C00_workspaceName").get("value"),
				'fields.workspaceDescription'	:	dijit.byId("BSC_PROG004D0002A_C00_description").get("value"),
				'fields.workspaceTemplateOid'	:	dijit.byId("BSC_PROG004D0002A_C01_workspaceTemplateOid").get("value"),
				'fields.datas'					: 	btoa( encodeURIComponent( JSON.stringify(datas) ) )
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG004D0002A_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG004D0002A_fieldsId);							
					return;
				}
				BSC_PROG004D0002A_TabClose();
			}, 
			function(error) {
				alert(error);
			}
	);	
	
}

function BSC_PROG004D0002A_TabContainer_show() {
	
	viewPage.addOrUpdateContentPane(
			'BSC_PROG004D0002A_TabContainer', 
			'BSC_PROG004D0002A_TabContainer_baseChildTab', 
			'${action.getText('BSC_PROG004D0002A_TabContainer_contentPane1')}', 
			'${basePath}/bsc.workspaceSettingsCreateBaseAction.action', 
			false, 
			true,
			false);	
	
	viewPage.addOrUpdateContentPane(
			'BSC_PROG004D0002A_TabContainer', 
			'BSC_PROG004D0002A_TabContainer_contentChildTab', 
			'${action.getText('BSC_PROG004D0002A_TabContainer_contentPane2')}', 
			'${basePath}/bsc.workspaceSettingsCreateContentAction.action', 
			false, 
			true,
			false);	
	
	var mainTab = dijit.byId("BSC_PROG004D0002A_TabContainer");		
	mainTab.selectChild( dijit.byId("BSC_PROG004D0002A_TabContainer_baseChildTab") );		
	
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
		saveJsMethod="BSC_PROG004D0002A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
    <div data-dojo-type="dijit/layout/TabContainer" style="width: 100%; height: 100%;" data-dojo-props="region:'center', tabStrip:true" id="BSC_PROG004D0002A_TabContainer">
    </div>	
	
<script type="text/javascript">${programId}_page_message();</script>
<script type="text/javascript">setTimeout("BSC_PROG004D0002A_TabContainer_show();", 500);</script>
</body>
</html>
	