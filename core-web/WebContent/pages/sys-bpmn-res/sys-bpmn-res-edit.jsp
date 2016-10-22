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

var CORE_PROG003D0004E_fieldsId = new Object();
CORE_PROG003D0004E_fieldsId['uploadOid'] 	= 'CORE_PROG003D0004E_uploadOid_noticeMsgLabel';
CORE_PROG003D0004E_fieldsId['name'] 		= 'CORE_PROG003D0004E_name';

function CORE_PROG003D0004E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0004E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0004E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0004E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0004E_fieldsId);
		return;
	}		
	CORE_PROG003D0004E_TabRefresh();
}

function CORE_PROG003D0004E_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0004E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0004E_fieldsId);
	dijit.byId('CORE_PROG003D0004E_name').set("value", "");
	dijit.byId('CORE_PROG003D0004E_description').set("value", "");
	dojo.byId('CORE_PROG003D0004E_uploadOid').value = "";
}

function CORE_PROG003D0004E_uploadSuccess() {
	hideCommonUploadDialog();
	dojo.byId('CORE_PROG003D0004E_uploadShow').innerHTML = 'Completed file upload!';
}

function CORE_PROG003D0004E_uploadFail() {
	
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
		saveJsMethod="CORE_PROG003D0004E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Activiti BPMN(zip) file" id="CORE_PROG003D0004E_uploadOid"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0004E_uploadOid_noticeMsgLabel"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input type="button" id="CORE_PROG003D0004E_uploadBtn" label="Upload" value="Upload" class="alt-info"
			   		data-dojo-props=" iconClass:'dijitIconFolderOpen', showLabel:false "
			   		data-dojo-type="dijit.form.Button" onClick="openCommonUploadDialog('CORE', 'tmp', 'Y', 'CORE_PROG003D0004E_uploadOid', 'CORE_PROG003D0004E_uploadSuccess', 'CORE_PROG003D0004E_uploadFail');" />
    			<span id="CORE_PROG003D0004E_uploadShow"><font color='#6E6E6E'>BPMN: <s:property value="bpmnResource.id"/></font></span>
    			<input type="hidden" name="CORE_PROG003D0004E_uploadOid" id="CORE_PROG003D0004E_uploadOid" value="" />
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Id" id="CORE_PROG003D0004E_id" requiredFlag="Y"></gs:label>
    			&nbsp;(Read only)<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0004E_id"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0004E_id" id="CORE_PROG003D0004E_id" value="bpmnResource.id" width="400" maxlength="100" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="Name" id="CORE_PROG003D0004E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0004E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0004E_name" id="CORE_PROG003D0004E_name" value="bpmnResource.name" width="400" maxlength="255"></gs:textBox>
    		</td>    		
    	</tr>    	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="Description" id="CORE_PROG003D0004E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0004E_description" name="CORE_PROG003D0004E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"><s:property value="bpmnResource.description"/></textarea>
    		</td>    		
    	</tr>    	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0004E_update" id="CORE_PROG003D0004E_update" onClick="CORE_PROG003D0004E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemBpmnResourceUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${bpmnResource.oid}',
    						'fields.uploadOid'		: dojo.byId('CORE_PROG003D0004E_uploadOid').value, 
    						'fields.id'				: dijit.byId('CORE_PROG003D0004E_id').get('value'),
    						'fields.name'			: dijit.byId('CORE_PROG003D0004E_name').get('value'),
    						'fields.description'	: dijit.byId('CORE_PROG003D0004E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0004E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0004E_clear" id="CORE_PROG003D0004E_clear" onClick="CORE_PROG003D0004E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
