<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

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

var BSC_PROG001D0008A_fieldsId = new Object();
BSC_PROG001D0008A_fieldsId['aggrId'] 		= 'BSC_PROG001D0008A_aggrId';
BSC_PROG001D0008A_fieldsId['name'] 			= 'BSC_PROG001D0008A_name';
BSC_PROG001D0008A_fieldsId['type'] 			= 'BSC_PROG001D0008A_type';
//BSC_PROG001D0008A_fieldsId['expression1'] 	= 'BSC_PROG001D0008A_expression1';
//BSC_PROG001D0008A_fieldsId['expression2'] 	= 'BSC_PROG001D0008A_expression2';
BSC_PROG001D0008A_fieldsId['description']	= 'BSC_PROG001D0008A_description';

function BSC_PROG001D0008A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG001D0008A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0008A_fieldsId);		
		return;
	}	
	BSC_PROG001D0008A_clear();
}

function BSC_PROG001D0008A_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0008A_fieldsId);		
	dijit.byId('BSC_PROG001D0008A_aggrId').set("value", "");
	dijit.byId('BSC_PROG001D0008A_name').set("value", "");
	dijit.byId('BSC_PROG001D0008A_type').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG001D0008A_expression1').set("value", "");
	//dijit.byId('BSC_PROG001D0008A_expression2').set("value", "");	
	document.getElementById('BSC_PROG001D0008A_iframe1').contentWindow.clear();
	document.getElementById('BSC_PROG001D0008A_iframe2').contentWindow.clear();	
	dijit.byId('BSC_PROG001D0008A_description').set("value", "");
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
		saveJsMethod="BSC_PROG001D0008A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="300px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG001D0008A_aggrId" id="BSC_PROG001D0008A_aggrId" value="" width="200" maxlength="14"></gs:textBox>
    		</td>
    	</tr>  		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG001D0008A_name" id="BSC_PROG001D0008A_name" value="" width="400" maxlength="100"></gs:textBox>
    		</td>
    	</tr>     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Type</b>:
    			<br/>
    			<gs:select name="BSC_PROG001D0008A_type" dataSource="typeMap" id="BSC_PROG001D0008A_type"></gs:select>
    		</td>
    	</tr>  
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b>Description</b>:
		    	<br/>
		    	<textarea id="BSC_PROG001D0008A_description" name="BSC_PROG001D0008A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
		    </td>
		</tr> 
	</table>
	<table border="0" width="100%" height="360px" cellpadding="1" cellspacing="0" >	    
		<tr>
		    <td height="360px" width="100%" align="left">
		    
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:350px">
							    
		    		<font color='RED'>*</font><b>Expression</b>:
		    		<br/>
		    		<iframe name="BSC_PROG001D0008A_iframe1" id="BSC_PROG001D0008A_iframe1" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=mainSysBasePath%>/core.commonCodeEditorAction.action?<%=Constants.IS_IFRAME_MODE%>=Y'></iframe>
		    
		    		</div>
		    	</div>
		    		
		    </td>
		</tr>  
	</table>	
	<table border="0" width="100%" height="360px" cellpadding="1" cellspacing="0" >
		<tr>
		    <td height="360px" width="100%" align="left">
		    
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression ( for date range )' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:350px">		    
		    
		    		<font color='RED'>*</font><b>Expression</b> ( for date range ):
		    		<br/>
		    		<iframe name="BSC_PROG001D0008A_iframe2" id="BSC_PROG001D0008A_iframe2" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=mainSysBasePath%>/core.commonCodeEditorAction.action?<%=Constants.IS_IFRAME_MODE%>=Y'></iframe>
		    
		    		</div>
		    	</div>	
		    
		    </td>
		</tr>  
	</table>			    	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0008A_save" id="BSC_PROG001D0008A_save" onClick="BSC_PROG001D0008A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.aggregationMethodSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.aggrId'			: dijit.byId('BSC_PROG001D0008A_aggrId').get('value'),
    						'fields.name'			: dijit.byId('BSC_PROG001D0008A_name').get('value'),  
    						'fields.type'			: dijit.byId('BSC_PROG001D0008A_type').get('value'),					 
    						'fields.expression1'	: document.getElementById('BSC_PROG001D0008A_iframe1').contentWindow.getValue(),
    						'fields.expression2'	: document.getElementById('BSC_PROG001D0008A_iframe2').contentWindow.getValue(),
    						'fields.description'	: dijit.byId('BSC_PROG001D0008A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0008A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG001D0008A_clear" id="BSC_PROG001D0008A_clear" onClick="BSC_PROG001D0008A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>      		
    		</td>
    	</tr> 		    	
    </table>		

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	