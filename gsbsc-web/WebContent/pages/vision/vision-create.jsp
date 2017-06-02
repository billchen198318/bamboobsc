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

var BSC_PROG002D0001A_fieldsId = new Object();
BSC_PROG002D0001A_fieldsId['title'] 	= 'BSC_PROG002D0001A_title';
BSC_PROG002D0001A_fieldsId['content'] 	= 'BSC_PROG002D0001A_content';

function BSC_PROG002D0001A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG002D0001A_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0001A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0001A_fieldsId);
		return;
	}	
	BSC_PROG002D0001A_clear();
}

function BSC_PROG002D0001A_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0001A_fieldsId);	
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0001A_fieldsId);
	dijit.byId('BSC_PROG002D0001A_title').set("value", "");	
	//dijit.byId('BSC_PROG002D0001A_content').set("value", "");		
	document.getElementById('BSC_PROG002D0001A_iframe').contentWindow.clearEditorValue();
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
		saveJsMethod="BSC_PROG002D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="450px" cellpadding="1" cellspacing="0" >	
		<tr>
			<td height="50px" width="100%"  align="left">
				<gs:label text="${action.getText('BSC_PROG002D0001A_title')}" id="BSC_PROG002D0001A_title" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0001A_title"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG002D0001A_title" id="BSC_PROG002D0001A_title" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0001A_title'">
    				Input title.
				</div> 				
			</td>
		</tr>	
		<tr>
			<td height="375px" width="100%"  align="left">
				<gs:label text="${action.getText('BSC_PROG002D0001A_content')}" id="BSC_PROG002D0001A_content" requiredFlag="Y"></gs:label>&nbsp;<font size="2">( recommend content should not exceed A4 paper size )</font>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0001A_content"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<!-- 
				<div data-dojo-type="dijit/Editor" id="BSC_PROG002D0001A_content" data-dojo-props="onChange:function(){ }"></div>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0001A_content'">
    				Input content. ( recommend content should not exceed A4 paper size )
				</div> 					
				-->
				<iframe id="BSC_PROG002D0001A_iframe" name="BSC_PROG002D0001A_iframe" src="./pages/common-froala-editor.jsp?height=250" style="height: 350px;" width="100%" height="350" frameBorder="0"></iframe>
				
			</td>
		</tr>
		<tr>
			<td height="25px" align="left">
    			<gs:button name="BSC_PROG002D0001A_save" id="BSC_PROG002D0001A_save" onClick="BSC_PROG002D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.visionSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.title'		: dijit.byId('BSC_PROG002D0001A_title').get('value'), 
    						'fields.content'	: document.getElementById('BSC_PROG002D0001A_iframe').contentWindow.getEditorValue()
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG002D0001A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    
    			<!-- 'fields.content'	: dijit.byId('BSC_PROG002D0001A_content').get('value') -->				
    			<gs:button name="BSC_PROG002D0001A_clear" id="BSC_PROG002D0001A_clear" onClick="BSC_PROG002D0001A_clear();" 
    				label="${action.getText('BSC_PROG002D0001A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>  			
			</td>
		</tr>		
	</table>

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
