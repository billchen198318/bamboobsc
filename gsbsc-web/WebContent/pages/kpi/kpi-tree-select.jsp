<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String oids = StringUtils.defaultString((String)request.getParameter("fields.oid")).trim();
String sysParam[] = oids.split(";");
String oidFieldId = sysParam[0];
String reloadLabelFn = sysParam[1];

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

#BSC_PROG002D0004Q_S00_kpiTree {
    /*height need calculate*/ 
    padding-left: 4px;
    padding-right: 4px;
    overflow-y: auto; 
    overflow-x: hidden;
    font-size: 12px;
}

</style>

<script type="text/javascript">

/* 產生KPI-TREE */
function BSC_PROG002D0004Q_S00_getKpiTree() {
	
	var store = new dojo.data.ItemFileWriteStore({
		url: "<%=basePath%>bsc.kpiTreeJsonAction.action"
	});

	var treeModel = new dijit.tree.ForestStoreModel({
		store: store,
		query: {"type": "parent"},
		rootId: "root",
		rootLabel: "<b>KPIs</b>",
		childrenAttrs: ["children"]
	});
	
	var treeObject = new dijit.Tree({
		model: treeModel,
        _createTreeNode: function(args) {
            var tnode = new dijit._TreeNode(args);
            var cbox = '';
            if (tnode.item.type == "Leaf") {
            	var id = tnode.item.id; 
            	var chk = ' ';
            	if ( dojo.byId('<%=oidFieldId%>').value.indexOf(id+_gscore_delimiter) > -1 ) {
            		chk = ' checked ';
            	}
            	cbox = '<input type="checkbox" id="BSC_PROG002D0004Q_S00_kpi:' + id + '" name="BSC_PROG002D0004Q_S00_kpi:' + id + '" onclick="return false;" ' + chk + ' disabled />';
            } 
            tnode.labelNode.innerHTML =  cbox + '&nbsp;' + args.label;
            return tnode;
        }    	
	}, "BSC_PROG002D0004Q_S00_kpiTree");

	treeObject.on("click", function(object){
		var oid = object.id;
		if (oid!=null && ''!=oid && 'root'!=oid && !(typeof oid=='undefined') && (oid+'').indexOf("NOT-KPI")==-1 ) {
			var idName = 'BSC_PROG002D0004Q_S00_kpi:' + oid;			
			if ( dojo.byId(idName).checked ) {
				dojo.byId(idName).checked = false;
				BSC_PROG002D0004Q_S00_getKpiData(idName, oid);
			} else {
				dojo.byId(idName).checked = true;
				BSC_PROG002D0004Q_S00_getKpiData(idName, oid);
			}
		} 
	}, true);
	
	treeObject.expandAll();
	
}

function BSC_PROG002D0004Q_S00_getKpiData(checkBoxId,oid) {
	var fieldId = '<%=oidFieldId%>';
	if ( dojo.byId(checkBoxId).checked ) {
		if ( dojo.byId(fieldId).value.indexOf(oid+_gscore_delimiter) == -1 ) {
			dojo.byId(fieldId).value = dojo.byId(fieldId).value + oid + _gscore_delimiter;
		}			
	} else {
		if ( dojo.byId(fieldId).value.indexOf(oid+_gscore_delimiter) > -1 ) {
			dojo.byId(fieldId).value = dojo.byId(fieldId).value.replace(oid+_gscore_delimiter, "");
		}
	}
	<%=reloadLabelFn%>();
}


//------------------------------------------------------------------------------

</script>

</head>

<body class="flat">
	 
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td align="left" valign="middle" bgcolor="#F5F5F5">
				<img src="<%=basePath%>images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/>&nbsp;&nbsp;<font size='2' color='#394045' style="vertical-align:middle;margin-top:0.25em"><b>KPIs tree select</b></font>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size='1' color="#F5F5F5">ID:&nbsp;BSC_PROG002D0004Q_S00</font>
				<br/>
				<hr color="#3794E5" size="2">
			</td>
		</tr>
	</table>	
	
	<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:600px;height:500px">
		<div id="BSC_PROG002D0004Q_S00_kpiTree"></div> 
	</div>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>	
	
<script type="text/javascript">
setTimeout("BSC_PROG002D0004Q_S00_getKpiTree();",1000);
</script>	
</body>
</html>
