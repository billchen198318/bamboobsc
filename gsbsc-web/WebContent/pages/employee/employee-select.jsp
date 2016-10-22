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

function BSC_PROG001D0001Q_S00_GridFieldStructure() {
	return [
			{ name: "${action.getText('BSC_PROG001D0001Q_S00_grid_01')}", field: "oid", formatter: BSC_PROG001D0001Q_S00_GridButtonClick, width: "5%" },  
			{ name: "${action.getText('BSC_PROG001D0001Q_S00_grid_02')}", field: "empId", width: "15%" },
			{ name: "${action.getText('BSC_PROG001D0001Q_S00_grid_03')}", field: "account", width: "15%" },
			{ name: "${action.getText('BSC_PROG001D0001Q_S00_grid_04')}", field: "fullName", width: "35%" },
			{ name: "${action.getText('BSC_PROG001D0001Q_S00_grid_05')}", field: "jobTitle", width: "30%" }			
		];
}

function BSC_PROG001D0001Q_S00_GridButtonClick(itemOid) {
	var idName = '${checkBoxIdDelimiter}' + itemOid;
	var rd="";
	rd += "<input type='checkbox' name='" + idName + "' id='" + idName + "' onclick='BSC_PROG001D0001Q_S00_putValue(\"" + idName + "\", \"" + itemOid + "\");' ";
	if ( dojo.byId("${fields.appendId}").value.indexOf(itemOid+_gscore_delimiter) > -1 ) {
		rd += " checked ";
	}
	rd += " />";
	return rd;	
}

function BSC_PROG001D0001Q_S00_clear() {
	dijit.byId('BSC_PROG001D0001Q_S00_empId').set('value', '');
	dijit.byId('BSC_PROG001D0001Q_S00_fullName').set('value', '');
	clearQuery_${programId}_grid();
	dojo.byId("${fields.appendId}").value = "";
	${fields.functionName}();
}

function BSC_PROG001D0001Q_S00_putValue(checkBoxId, oid) {
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
	
	<!-- 
	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_DlgShow('${fields.oid}');" 		
		></gs:toolBar>
	-->	
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<table border="0" width="100%" height="75px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001Q_S00_empId')}" id="BSC_PROG001D0001Q_S00_empId"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001Q_S00_empId" id="BSC_PROG001D0001Q_S00_empId" value="" width="100" maxlength="10"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001Q_S00_empId'">
    				Input employee Id.
				</div>
    		</td>
    		
    		<td height="25px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0001Q_S00_fullName')}" id="BSC_PROG001D0001Q_S00_fullName"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0001Q_S00_fullName" id="BSC_PROG001D0001Q_S00_fullName" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0001Q_S00_fullName'">
    				Input full name.
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG001D0001Q_S00_query" id="BSC_PROG001D0001Q_S00_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.employeeManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.empId'		: dijit.byId('BSC_PROG001D0001Q_S00_empId').get('value'), 
    						'searchValue.parameter.fullName'	: dijit.byId('BSC_PROG001D0001Q_S00_fullName').get('value'),
    						'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0001Q_S00_query')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG001D0001Q_S00_clear" id="BSC_PROG001D0001Q_S00_clear" onClick="BSC_PROG001D0001Q_S00_clear();" 
    				label="${action.getText('BSC_PROG001D0001Q_S00_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG001D0001Q_S00_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>

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
${programId}_page_message();
setTimeout("getQueryGrid_${programId}_grid();", 500);
</script>	
</body>
</html>
