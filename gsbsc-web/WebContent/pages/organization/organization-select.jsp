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

#BSC_PROG001D0002Q_S00_tree {
    /*height need calculate*/ 
    padding-left: 4px;
    padding-right: 4px;
    overflow-y: auto; 
    overflow-x: hidden;
    font-size: 12px;
}

</style>

<script type="text/javascript">

/* 產生部門TREE */
function BSC_PROG001D0002Q_S00_getOrganizationTree() {
	
	var store = new dojo.data.ItemFileWriteStore({		
		url: "${basePath}/bsc.getOrganizationTreeJson.action?fields.checkBoxMode=Y&fields.appendId=" + dojo.byId('${fields.appendId}').value
	});

	var treeModel = new dijit.tree.ForestStoreModel({
		store: store,
		query: {"type": "parent"},
		rootId: "root",
		rootLabel: "<b><s:property value="getText('BSC_PROG001D0002Q_S00_getOrganizationTree_rootLabel')" escapeJavaScript="true"/></b>",
		childrenAttrs: ["children"]
	});

	var treeObject = new dijit.Tree({
		model: treeModel,
        _createTreeNode: function(args) {
            var tnode = new dijit._TreeNode(args);
            tnode.labelNode.innerHTML = args.label;
            return tnode;
        }    		
	}, "BSC_PROG001D0002Q_S00_tree");
	
	treeObject.on("click", function(object){			
		var oid=object.id;
		if (oid!=null && ''!=oid && 'root'!=oid && !(typeof oid=='undefined') ) {
			var idName = '${checkBoxIdDelimiter}' + oid;			
			if ( dojo.byId(idName).checked ) {
				dojo.byId(idName).checked = false;
				BSC_PROG001D0002Q_S00_putValue(idName, oid);
			} else {
				dojo.byId(idName).checked = true;
				BSC_PROG001D0002Q_S00_putValue(idName, oid);				
			}
		}
	}, true);	
	
	treeObject.expandAll();
	
}

function BSC_PROG001D0002Q_S00_putValue(checkBoxId, oid) {
	if ( dojo.byId(checkBoxId).checked ) {
		if ( dojo.byId("${fields.appendId}").value.indexOf(oid+_gscore_delimiter) == -1 ) {
			dojo.byId("${fields.appendId}").value = dojo.byId("${fields.appendId}").value + oid + _gscore_delimiter;
		}			
	} else {
		if ( dojo.byId("${fields.appendId}").value.indexOf(oid+_gscore_delimiter) > -1 ) {
			dojo.byId("${fields.appendId}").value = dojo.byId("${fields.appendId}").value.replace(oid+_gscore_delimiter, "");
		}
	}
	${fields.functionName}();
}

//${fields.functionName} button要觸發的事件function

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

	<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:97%;height:410px;">
		<div id="BSC_PROG001D0002Q_S00_tree" ></div>						
	</div>

<script type="text/javascript">
${programId}_page_message(); 
setTimeout("BSC_PROG001D0002Q_S00_getOrganizationTree();", 500);
</script>	
</body>
</html>
