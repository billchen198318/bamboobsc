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

var BSC_PROG002D0001E_fieldsId = new Object();
BSC_PROG002D0001E_fieldsId['title'] 	= 'BSC_PROG002D0001E_title';
BSC_PROG002D0001E_fieldsId['content'] 	= 'BSC_PROG002D0001E_content';

function BSC_PROG002D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0001E_fieldsId);		
		return;
	}		
}

function BSC_PROG002D0001E_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0001E_fieldsId);
	dijit.byId('BSC_PROG002D0001E_title').set("value", "");
	dijit.byId('BSC_PROG002D0001E_content').set("value", "");	
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

<body class="claro" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG002D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="475px" cellpadding="1" cellspacing="0" >
		<tr>
			<td height="50px" width="100%"  align="left">
				<b>Id</b>&nbsp;(read only)&nbsp;:
				<br/>
				<gs:textBox name="BSC_PROG002D0001E_visId" id="BSC_PROG002D0001E_visId" value="vision.visId" width="200" maxlength="14" readonly="Y"></gs:textBox>
			</td>
		</tr>	
		<tr>
			<td height="50px" width="100%"  align="left">
				<font color='RED'>*</font><b>Title</b>:
				<br/>
				<gs:textBox name="BSC_PROG002D0001E_title" id="BSC_PROG002D0001E_title" value="vision.title" width="400" maxlength="100"></gs:textBox>
			</td>
		</tr>	    	
		<tr>
			<td height="375px" width="100%"  align="left">
				<font color='RED'>*</font><b>Content</b>:
				<br/>
				<div data-dojo-type="dijit/Editor" id="BSC_PROG002D0001E_content" data-dojo-props="onChange:function(){ }">${visionContent}</div>
			</td>
		</tr>    	
		<tr>
			<td height="25px" align="left">
    			<gs:button name="BSC_PROG002D0001E_update" id="BSC_PROG002D0001E_update" onClick="BSC_PROG002D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.visionUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'		: '${vision.oid}',		 
    						'fields.title'		: dijit.byId('BSC_PROG002D0001E_title').get('value'), 
    						'fields.content'	: dijit.byId('BSC_PROG002D0001E_content').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG002D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG002D0001E_clear" id="BSC_PROG002D0001E_clear" onClick="BSC_PROG002D0001E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    				
			</td>
		</tr>			   	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
