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

var QCHARTS_PROG001D0004E_fieldsId = new Object();
//QCHARTS_PROG001D0004E_fieldsId['id'] 			= 'QCHARTS_PROG001D0004E_id';
QCHARTS_PROG001D0004E_fieldsId['name'] 			= 'QCHARTS_PROG001D0004E_name';
QCHARTS_PROG001D0004E_fieldsId['uploadOid'] 	= 'QCHARTS_PROG001D0004E_uploadOid_noticeMessageOnly';

function QCHARTS_PROG001D0004E_updateSuccess(data) {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0004E_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG001D0004E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG001D0004E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG001D0004E_fieldsId);
		return;
	}		
}

function QCHARTS_PROG001D0004E_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0004E_fieldsId);	
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG001D0004E_fieldsId);
	//dijit.byId('QCHARTS_PROG001D0004E_id').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0004E_name').set("value", "");
	dijit.byId('QCHARTS_PROG001D0004E_description').set("value", "");
}

function QCHARTS_PROG001D0004E_uploadSuccess() {
	hideCommonUploadDialog();
	dojo.byId('QCHARTS_PROG001D0004E_uploadShow').innerHTML = '<s:property value="getText('QCHARTS_PROG001D0004E_uploadShowSuccess')" escapeJavaScript="true"/>';
}

function QCHARTS_PROG001D0004E_uploadFail() {
	dojo.byId('QCHARTS_PROG001D0004E_uploadOid').value = "";
	dojo.byId('QCHARTS_PROG001D0004E_uploadShow').innerHTML = '<font color="#6E6E6E"><s:property value="getText('QCHARTS_PROG001D0004E_uploadShow')" escapeJavaScript="true"/></font>';
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
		saveJsMethod="QCHARTS_PROG001D0004E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="350px" cellpadding="1" cellspacing="0" >			
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('QCHARTS_PROG001D0004E_id')}" id="QCHARTS_PROG001D0004E_id" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG001D0004E_id"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0004E_id" id="QCHARTS_PROG001D0004E_id" value="olapCatalog.id" width="200" maxlength="20" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0004E_id'">
    				Id. ( read only )
				</div>       			
    		</td>
    	</tr>  	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('QCHARTS_PROG001D0004E_name')}" id="QCHARTS_PROG001D0004E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG001D0004E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0004E_name" id="QCHARTS_PROG001D0004E_name" value="olapCatalog.name" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0004E_name'">
    				Input name.
				</div>        			
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('QCHARTS_PROG001D0004E_uploadOid')}" id="QCHARTS_PROG001D0004E_uploadOid" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG001D0004E_uploadOid_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<input type="button" id="QCHARTS_PROG001D0004E_uploadBtn" label="Upload" value="Upload" class="alt-info"
			   		data-dojo-props=" iconClass:'dijitFolderOpened', showLabel:false, iconClass:'dijitIconFolderOpen' "
			   		data-dojo-type="dijit.form.Button" onClick="openCommonUploadDialog('QCHARTS', 'tmp', 'N', 'QCHARTS_PROG001D0004E_uploadOid', 'QCHARTS_PROG001D0004E_uploadSuccess', 'QCHARTS_PROG001D0004E_uploadFail');" />
    			<span id="QCHARTS_PROG001D0004E_uploadShow"><font color='#6E6E6E'><s:property value="olapCatalog.id"/>.xml</font></span>
    			<input type="hidden" name="QCHARTS_PROG001D0004E_uploadOid" id="QCHARTS_PROG001D0004E_uploadOid" value="" />    			
    		</td>
    	</tr>   	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('QCHARTS_PROG001D0004E_description')}" id="QCHARTS_PROG001D0004E_description"></gs:label>
		    	<br/>
		    	<textarea id="QCHARTS_PROG001D0004E_description" name="QCHARTS_PROG001D0004E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${olapCatalog.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0004E_description'">
    				Input description, the maximum allowed 500 characters. 
				</div> 		    		
		    </td>
		</tr>   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="QCHARTS_PROG001D0004E_update" id="QCHARTS_PROG001D0004E_update" onClick="QCHARTS_PROG001D0004E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.analyticsCatalogUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${olapCatalog.oid}',		     						
    						'fields.id'				: dijit.byId('QCHARTS_PROG001D0004E_id').get('value'),
    						'fields.name'			: dijit.byId('QCHARTS_PROG001D0004E_name').get('value'),
    						'fields.uploadOid'		: dojo.byId('QCHARTS_PROG001D0004E_uploadOid').value,				
    						'fields.description'	: dijit.byId('QCHARTS_PROG001D0004E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="QCHARTS_PROG001D0004E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('QCHARTS_PROG001D0004E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="QCHARTS_PROG001D0004E_clear" id="QCHARTS_PROG001D0004E_clear" onClick="QCHARTS_PROG001D0004E_clear();" 
    				label="${action.getText('QCHARTS_PROG001D0004E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
