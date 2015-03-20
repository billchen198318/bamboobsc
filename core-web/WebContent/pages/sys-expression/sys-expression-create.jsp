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

var CORE_PROG003D0002A_fieldsId = new Object();
CORE_PROG003D0002A_fieldsId['exprId'] 			= 'CORE_PROG003D0002A_exprId';
CORE_PROG003D0002A_fieldsId['type'] 			= 'CORE_PROG003D0002A_type';
CORE_PROG003D0002A_fieldsId['name'] 			= 'CORE_PROG003D0002A_name';
//CORE_PROG003D0002A_fieldsId['content'] 			= 'CORE_PROG003D0002A_content';

function CORE_PROG003D0002A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG003D0002A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0002A_fieldsId);		
		return;
	}	
	CORE_PROG003D0002A_clear();
}

function CORE_PROG003D0002A_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0002A_fieldsId);
	dijit.byId('CORE_PROG003D0002A_exprId').set("value", "");
	dijit.byId('CORE_PROG003D0002A_type').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0002A_name').set("value", "");
	//dijit.byId('CORE_PROG003D0002A_content').set("value", "");		
	document.getElementById('CORE_PROG003D0002A_iframe1').contentWindow.clear();
	dijit.byId('CORE_PROG003D0002A_description').set("value", "");	
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
		saveJsMethod="CORE_PROG003D0002A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="275px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG003D0002A_exprId" id="CORE_PROG003D0002A_exprId" value="" width="200" maxlength="20"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="20%"  align="left">
    			<font color='RED'>*</font><b>Type</b>:
    			<br/>
    			<gs:select name="CORE_PROG003D0002A_type" dataSource="typeMap" id="CORE_PROG003D0002A_type"></gs:select>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG003D0002A_name" id="CORE_PROG003D0002A_name" value="" width="400" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<b>Description</b>:
    			<br/>
    			<textarea id="CORE_PROG003D0002A_description" name="CORE_PROG003D0002A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
    		</td>    		
    	</tr>   
    </table>	
    <table border="0" width="100%" height="350px" cellpadding="1" cellspacing="0" >	    	
		<tr>
    		<td height="325px" width="100%"  align="left">
    		
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:350px">
						
    					<font color='RED'>*</font><b>Expression</b>:
    					<br/>
    					<iframe name="CORE_PROG003D0002A_iframe1" id="CORE_PROG003D0002A_iframe1" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=basePath%>/core.commonCodeEditorAction.action'></iframe>
					
					</div>
				</div>
					    		    	    		
    		</td>    		
    	</tr>   
    </table>
    <table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >	    	  		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0002A_save" id="CORE_PROG003D0002A_save" onClick="CORE_PROG003D0002A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemExpressionSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.exprId'			: dijit.byId('CORE_PROG003D0002A_exprId').get('value'), 
    						'fields.type'			: dijit.byId('CORE_PROG003D0002A_type').get('value'),    						
    						'fields.name'			: dijit.byId('CORE_PROG003D0002A_name').get('value'),    						
    						'fields.content'		: document.getElementById('CORE_PROG003D0002A_iframe1').contentWindow.getValue(),    						
    						'fields.description'	: dijit.byId('CORE_PROG003D0002A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0002A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="CORE_PROG003D0002A_clear" id="CORE_PROG003D0002A_clear" onClick="CORE_PROG003D0002A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>   
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
