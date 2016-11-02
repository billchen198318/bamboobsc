<% /* 基本上這隻程式 BSC_PROG005D0001A_S02 目前停用了 */ %>
<% /* 基本上這隻程式 BSC_PROG005D0001A_S02 目前停用了 */ %>
<% /* 基本上這隻程式 BSC_PROG005D0001A_S02 目前停用了 */ %>
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


function BSC_PROG005D0001A_S02_loadDiagram(taskId) {
	xhrSendParameter(
			'${basePath}/bsc.degreeFeedbackProjectLoadTaskDiagramAction.action', 
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
								"height"	:	600
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
		cancelJsMethod="${programId}_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_DlgShow('${fields.oid}');" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<tr>
			<td align="left" width="15%" bgcolor="#f1eee5"><b>Id</b></td>
			<td align="left" width="15%" bgcolor="#f1eee5"><b>Name</b></td>
			<td align="left" width="15%" bgcolor="#f1eee5"><b>Assignee</b></td>
			<td align="left" width="30%" bgcolor="#f1eee5"><b>Reason</b></td>
			<td align="center" width="25%" bgcolor="#f1eee5"><b>#</b></td>
		</tr>
		<s:if test="null != bpmTaskObj">
		
		<tr>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="bpmTaskObj.task.id"/></td>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="bpmTaskObj.task.name"/></td>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="bpmTaskObj.task.assignee"/></td>
			<td align="left" width="30%" bgcolor="#ffffff"><s:property value="bpmTaskObj.variables.reason"/></td>
			<td align="center" width="25%" bgcolor="#ffffff">
				
				<button name="BSC_PROG005D0001A_S02_confirmDlgBtn_${fields.oid}_${bpmTaskObj.task.id}" id="BSC_PROG005D0001A_S02_confirmDlgBtn_${fields.oid}_${bpmTaskObj.task.id}" data-dojo-type="dijit.form.Button"
					
					<s:if test=" bpmTaskObj == null || \"Y\" != bpmTaskObj.allowApproval "> disabled="disabled" </s:if>
					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							BSC_PROG005D0001A_S03_DlgShow('${fields.oid};${bpmTaskObj.task.id}');
						}
					"
					class="alt-primary"><s:property value="getText('BSC_PROG005D0001A_S02_confirmDlgBtn')"/></button>
				
				<button name="BSC_PROG005D0001A_S02_diagramDlgBtn_${fields.oid}_${bpmTaskObj.task.id}" id="BSC_PROG005D0001A_S02_diagramDlgBtn_${fields.oid}_${bpmTaskObj.task.id}" data-dojo-type="dijit.form.Button"
					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							BSC_PROG005D0001A_S02_loadDiagram('${bpmTaskObj.task.id}');
						}
					"
					class="alt-primary"><s:property value="getText('BSC_PROG005D0001A_S02_diagramDlgBtn')"/></button>				
					
			</td>
		</tr>			
		
		</s:if>
		
	</table>		
	
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
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
