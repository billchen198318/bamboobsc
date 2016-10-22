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

function BSC_PROG006D0001Q_GridFieldStructure() {
	return [
			{ name: "View / Edit", field: "oid", formatter: BSC_PROG006D0001Q_GridButtonClick, width: "15%" },  
			{ name: "Title", field: "title", width: "30%" },
			{ name: "Start", field: "startDateDisplayValue", width: "15%" },
			{ name: "End", field: "endDateDisplayValue", width: "15%" },
			{ name: "Confirm", field: "confirmFlag", width: "10%" },
			{ name: "Confirm date", field: "confirmDateDisplayValue", width: "15%" }
		];
}

function BSC_PROG006D0001Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG006D0001Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"BSC_PROG006D0001Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function BSC_PROG006D0001Q_clear() {
	dijit.byId('BSC_PROG006D0001Q_title').set('value', '');
	dijit.byId('BSC_PROG006D0001Q_startDate').set('value', '');
	dijit.byId('BSC_PROG006D0001Q_endDate').set('value', '');
	dijit.byId('BSC_PROG006D0001Q_startDate').set('displayedValue', '');
	dijit.byId('BSC_PROG006D0001Q_endDate').set('displayedValue', '');	
	clearQuery_${programId}_grid();	
}

function BSC_PROG006D0001Q_edit(oid) {
	BSC_PROG006D0001E_TabShow(oid);
}

function BSC_PROG006D0001Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"delete?", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.pdcaDeleteAction.action', 
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
		createNewJsMethod="BSC_PROG006D0001A_TabShow();"		 
		saveEnabel="N"
		saveJsMethod=""
		refreshEnable="Y"
		refreshJsMethod="${programId}_TabRefresh();" 
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		

	<table border="0" width="100%" height="75px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="50%" align="left">
    			<gs:label text="Title" id="BSC_PROG006D0001Q_title"></gs:label>
    			<br/>    		
    			<gs:textBox name="BSC_PROG006D0001Q_title" id="BSC_PROG006D0001Q_title" value="" width="300" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001Q_title'">
    				Input title.
				</div>
    		</td>
    		<td height="50px" width="50%" align="left">
    			<gs:label text="Date range" id="BSC_PROG006D0001Q_dateRangeLabel"></gs:label>
    			<br/>
    			Start
    			<input id="BSC_PROG006D0001Q_startDate" type="text" name="BSC_PROG006D0001Q_startDate" data-dojo-type="dijit.form.DateTextBox"
    				maxlength="10" 
    				constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001Q_startDate'">
					Select start date.
				</div>
				&nbsp;						
				End
				<input id="BSC_PROG006D0001Q_endDate" type="text" name="BSC_PROG006D0001Q_endDate" data-dojo-type="dijit.form.DateTextBox"
					maxlength="10" 
					constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />																	    									    	
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001Q_endDate'">
					Select end date.
				</div>
    		</td>    		
    	</tr>
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG006D0001Q_query" id="BSC_PROG006D0001Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.pdcaManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.title'			: dijit.byId('BSC_PROG006D0001Q_title').get('value'),
    						'searchValue.parameter.startDate' 		: dijit.byId('BSC_PROG006D0001Q_startDate').get('displayedValue'),
    						'searchValue.parameter.endDate' 		: dijit.byId('BSC_PROG006D0001Q_endDate').get('displayedValue'),
    						'pageOf.size'							: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'							: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'						: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="Query" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG006D0001Q_clear" id="BSC_PROG006D0001Q_clear" onClick="BSC_PROG006D0001Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr>     	    	
	</table>
	
	<gs:grid gridFieldStructure="BSC_PROG006D0001Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}" disableOnHeaderCellClick="Y"></gs:grid>

<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
