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


function CORE_PROG003D0004Q_S00_loadDiagram(type, objId) {
	xhrSendParameter(
			'${basePath}/core.systemBpmnResourceExportDiagramAction.action', 
			{ 
				'fields.type' 		: type,
				'fields.objectId'	: objId,
				'fields.resourceId'	: '${bpmnResource.id}'
			}, 
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
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<legend><b>Process definition</b></legend>
		<tr>
			<td align="left" width="40%" bgcolor="#f1eee5"><b>Key</b></td>
			<td align="left" width="40%" bgcolor="#f1eee5"><b>Name</b></td>
			<td align="center" width="20%" bgcolor="#f1eee5"><b>#</b></td>
		</tr>
		
		<s:if test="processDefinitions!=null">
		<s:iterator value="processDefinitions" status="st">
		
		<tr>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="key"/></td>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="center" width="20%" bgcolor="#ffffff">
			
				<button name="CORE_PROG003D0004Q_S00_processDefinitionDiagramDlgBtn_${id}" id="CORE_PROG003D0004Q_S00_processDefinitionDiagramDlgBtn_${id}" data-dojo-type="dijit.form.Button"					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							CORE_PROG003D0004Q_S00_loadDiagram('processDefinition', '${id}');
						}
					"
					class="alt-primary">Diagram</button>	
					
			</td>
		</tr>			
		
		</s:iterator>		
		</s:if>
		
	</table>
	
	<br/>
	
	<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<legend><b>Process instance</b></legend>
		<tr>
			<td align="left" width="40%" bgcolor="#f1eee5"><b>Id</b></td>
			<td align="left" width="40%" bgcolor="#f1eee5"><b>Process definition</b></td>
			<td align="center" width="20%" bgcolor="#f1eee5"><b>#</b></td>
		</tr>
		
		<s:if test="processInstances!=null">
		<s:iterator value="processInstances" status="st">
		
		<tr>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="id"/></td>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="processDefinitionId"/></td>
			<td align="center" width="20%" bgcolor="#ffffff">
			
				<button name="CORE_PROG003D0004Q_S00_processInstanceDiagramDlgBtn_${id}" id="CORE_PROG003D0004Q_S00_processInstanceDiagramDlgBtn_${id}" data-dojo-type="dijit.form.Button"					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							CORE_PROG003D0004Q_S00_loadDiagram('processInstance', '${id}');
						}
					"
					class="alt-primary">Diagram</button>	
								
			</td>
		</tr>			
		
		</s:iterator>		
		</s:if>
		
	</table>	
	
	<br/>
	
	<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<legend><b>Task</b></legend>
		<tr>
			<td align="left" width="30%" bgcolor="#f1eee5"><b>Id</b></td>
			<td align="left" width="30%" bgcolor="#f1eee5"><b>Name</b></td>
			<td align="left" width="30%" bgcolor="#f1eee5"><b>Assignee</b></td>			
			<td align="center" width="10%" bgcolor="#f1eee5"><b>#</b></td>
		</tr>
		
		<s:if test="tasks!=null">
		<s:iterator value="tasks" status="st">
		
		<tr>
			<td align="left" width="30%" bgcolor="#ffffff"><s:property value="id"/></td>
			<td align="left" width="30%" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="left" width="30%" bgcolor="#ffffff"><s:property value="assignee"/></td>
			<td align="center" width="30%" bgcolor="#ffffff">
			
				<button name="CORE_PROG003D0004Q_S00_taskDiagramDlgBtn_${id}" id="CORE_PROG003D0004Q_S00_taskDiagramDlgBtn_${id}" data-dojo-type="dijit.form.Button"					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							CORE_PROG003D0004Q_S00_loadDiagram('task', '${id}');
						}
					"
					class="alt-primary">Diagram</button>
								
			</td>
		</tr>			
		
		</s:iterator>		
		</s:if>
		
	</table>	
			
		
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
