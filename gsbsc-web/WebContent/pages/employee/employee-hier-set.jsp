<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
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

#BSC_PROG001D0001Q_S01_tree {
    /*height need calculate*/ 
    padding-left: 4px;
    padding-right: 4px;
    overflow-y: auto; 
    overflow-x: hidden;
    font-size: 12px;
}

</style>

<script type="text/javascript">

var BSC_PROG001D0001Q_S01_firstLoad = true;

/* 員工關聯 TREE */
function BSC_PROG001D0001Q_S01_getEmployeeTree() {
	
	var store = new dojo.data.ItemFileWriteStore({		
		url: "${basePath}/bsc.getEmployeeTreeJson.action"
	});

	var treeModel = new dijit.tree.ForestStoreModel({
		store: store,
		query: {"type": "parent"},
		rootId: "root",
		rootLabel: "<b>Employee hierarchy</b>",
		childrenAttrs: ["children"]
	});

	var treeObject = new dijit.Tree({
		model: treeModel,
		dndController: "dijit.tree.dndSource",
		checkAcceptance: BSC_PROG001D0001Q_S01_dragAccept,
		checkItemAcceptance: BSC_PROG001D0001Q_S01_dropAccept,		
        _createTreeNode: function(args) {
            var tnode = new dijit._TreeNode(args);
            tnode.labelNode.innerHTML = args.label;
            return tnode;
        }    		
	}, "BSC_PROG001D0001Q_S01_tree");
	
	dojo.connect(treeModel, 'pasteItem', function(childItem, oldParentItem, newParentItem, bCopy, insertIndex) {
		var entityOid = childItem.id[0]; 
	    var supervisorOid = newParentItem.id[0];
	    BSC_PROG001D0001Q_S01_changeSupervisor(entityOid, supervisorOid);	    
	});		
	
	treeObject.expandAll();
	
	if (BSC_PROG001D0001Q_S01_firstLoad) {
		BSC_PROG001D0001Q_S01_firstLoad = false;
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Drag-and-drop item to change employee hierarchy.', function(){ }, 'Y');
	}
	
}

/* 重新產生 員工關聯  TREE */
function BSC_PROG001D0001Q_S01_reEmployeeTree() {			
	dijit.byId("BSC_PROG001D0001Q_S01_tree").destroy(true);	
	BSC_PROG001D0001Q_S01_getEmployeeTree();
}

/* 移動tree的item */
function BSC_PROG001D0001Q_S01_dragAccept(source,nodes) {	
	return true;
}

/* 下降tree的item */
function BSC_PROG001D0001Q_S01_dropAccept(node,source) {	
	return true;
}

/* 更改 員工關聯 SUP_OID */
function BSC_PROG001D0001Q_S01_changeSupervisor(entityOid, supervisorOid) {
	xhrSendParameter(
			'${basePath}/bsc.employeeUpdateSupervisorAction.action', 
			{ 
				'fields.oid' 		: entityOid,
				'fields.supOid'		: supervisorOid
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){ setTimeout("BSC_PROG001D0001Q_S01_reEmployeeTree();", 500); }, data.success);				
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

<jsp:include page="../header.jsp"></jsp:include>
<table border="0" width="100%" height="600px" cellpadding="1" cellspacing="0" >
	<tr>
		<td width="100%" valign="top" >
			<div id="BSC_PROG001D0001Q_S01_tree" ></div>
		</td>
	</tr>	
</table>					
	
<script type="text/javascript">
${programId}_page_message(); 
setTimeout("BSC_PROG001D0001Q_S01_getEmployeeTree();", 500);
</script>	
</body>
</html>
