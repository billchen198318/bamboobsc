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

function CORE_PROG001D0015Q_GridFieldStructure() {
	return [
			{ name: "View&nbsp;/&nbsp;Edit", field: "OID", formatter: CORE_PROG001D0015Q_GridButtonClick, width: "15%" },  
			{ name: "Code", field: "CODE", width: "30%" },
			{ name: "Name", field: "NAME", width: "55%" }
		];	
}

function CORE_PROG001D0015Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"CORE_PROG001D0015Q_edit('" + itemOid + "');\" />";
	return rd;	
}

function CORE_PROG001D0015Q_clear() {
	dijit.byId('CORE_PROG001D0015Q_code').set('value', '');
	dijit.byId('CORE_PROG001D0015Q_name').set('value', '');
	clearQuery_${programId}_grid();	
}

function CORE_PROG001D0015Q_edit(oid) {
	CORE_PROG001D0015E_TabShow(oid);
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
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="10%"  align="right">Code:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="CORE_PROG001D0015Q_code" id="CORE_PROG001D0015Q_code" value="" width="200" maxlength="50"></gs:textBox></td>
    		<td height="25px" width="10%"  align="right">Name:</td>
    		<td height="25px" width="40%"  align="left"><gs:textBox name="CORE_PROG001D0015Q_name" id="CORE_PROG001D0015Q_name" value="" width="200" maxlength="100"></gs:textBox></td>  					
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="4">
    			<gs:button name="CORE_PROG001D0015Q_query" id="CORE_PROG001D0015Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.commomLoadForm.action?prog_id=CORE_PROG001D0015Q&form_id=FORM001&form_method=query"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.code'	: dijit.byId('CORE_PROG001D0015Q_code').get('value'), 
    						'searchValue.parameter.name'	: dijit.byId('CORE_PROG001D0015Q_name').get('value'),
    						'pageOf.size'					: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'					: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'				: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="Query" 
    				iconClass="dijitIconSearch"></gs:button>
    			<gs:button name="CORE_PROG001D0015Q_clear" id="CORE_PROG001D0015Q_clear" onClick="CORE_PROG001D0015Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>
    		</td>
    	</tr>     	    	
    </table>	
    
    <gs:grid gridFieldStructure="CORE_PROG001D0015Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
