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

function BSC_PROG006D0001E_S00_saveSuccess(data) {
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {
		return;
	}
	BSC_PROG006D0001E_TabRefresh(); // PDCA修改頁面刷新
	${programId}_DlgHide(); // 隱藏這個dlg
}

function BSC_PROG006D0001E_S00_clear() {
	dijit.byId('BSC_PROG006D0001E_S00_confirm').set("value", "Y");
	dijit.byId('BSC_PROG006D0001E_S00_reason').set("value", "");
	dijit.byId('BSC_PROG006D0001E_S00_newChild').set("checked", false);
}

function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}

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
	
	<table border="0" width="100%" bgcolor="#ffffff">
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Confirm" id="BSC_PROG006D0001E_S00_confirm" requiredFlag="Y"></gs:label>
    			<br/>
    			<gs:select name="BSC_PROG006D0001E_S00_confirm" dataSource="{\"Y\" : \"Yes\", \"N\" : \"Reject\"}" id="BSC_PROG006D0001E_S00_confirm" value="Y"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_S00_confirm'">
					Select confirm.
				</div>    			
    		</td>   
		</tr>
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="Reason" id="BSC_PROG006D0001E_S00_reason"></gs:label>
		    	<br/>
		    	<textarea id="BSC_PROG006D0001E_S00_reason" name="BSC_PROG006D0001E_S00_reason" data-dojo-type="dijit/form/Textarea" rows="4" cols="20" style="width:300px;height:90px;max-height:100px">Please allow: ${bpmTaskObj.task.name}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_S00_reason'">
					Input confirm/reject reason description.
				</div>
		    </td>
		</tr>
		<tr>
    		<td height="50px" width="100%"  align="left" colspan="2">
    			<b>New child project:</b>
    			<br/>
    			<input id="BSC_PROG006D0001E_S00_newChild" name="BSC_PROG006D0001E_S00_newChild" data-dojo-type="dijit/form/CheckBox" value="false" <s:if test=" \"A\" != bpmTaskObj.task.name.substring(0, 1) "> disabled="disabled" </s:if> />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001E_S00_newChild'">
					Checked - The current project must be improved, create child PDCA project.<br/>
    				No check - The PDCA project is completed, no need next PDCA. 
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG006D0001E_S00_save" id="BSC_PROG006D0001E_S00_save" onClick="BSC_PROG006D0001E_S00_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.pdcaConfirmSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{     						
    						'fields.pdcaOid'		: '${pdca.oid}',
    						'fields.taskId'			: '${fields.taskId}',
    						'fields.reason'			: dijit.byId('BSC_PROG006D0001E_S00_reason').get('value'),
    						'fields.confirm'		: dijit.byId('BSC_PROG006D0001E_S00_confirm').get('value'),
    						'fields.newChild'		: ( dijit.byId('BSC_PROG006D0001E_S00_newChild').checked ? 'true' : 'false' )
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG006D0001E_S00_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				confirmDialogMode="Y"
    				confirmDialogTitle=""
    				confirmDialogMsg="Confirm?"    				
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="BSC_PROG006D0001E_S00_clear" id="BSC_PROG006D0001E_S00_clear" onClick="BSC_PROG006D0001E_S00_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>       		
    		</td>
    	</tr>				
	</table>		
	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>	