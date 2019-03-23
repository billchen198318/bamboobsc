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

var CORE_PROG003D0002E_fieldsId = new Object();
//CORE_PROG003D0002E_fieldsId['exprId'] 			= 'CORE_PROG003D0002E_exprId';	// readOnly
CORE_PROG003D0002E_fieldsId['type'] 			= 'CORE_PROG003D0002E_type';
CORE_PROG003D0002E_fieldsId['name'] 			= 'CORE_PROG003D0002E_name';
CORE_PROG003D0002E_fieldsId['content'] 			= 'CORE_PROG003D0002E_content_noticeMsgLabelOnly';

function CORE_PROG003D0002E_updateSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG003D0002E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0002E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0002E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG003D0002E_fieldsId);
		return;
	}		
}

function CORE_PROG003D0002E_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0002E_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG003D0002E_fieldsId);
	//dijit.byId('CORE_PROG003D0002E_exprId').set("value", "");	// readOnly
	dijit.byId('CORE_PROG003D0002E_type').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0002E_name').set("value", "");
	//dijit.byId('CORE_PROG003D0002E_content').set("value", "");		
	document.getElementById('CORE_PROG003D0002E_iframe1').contentWindow.clear();
	dijit.byId('CORE_PROG003D0002E_description').set("value", "");	
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
		saveJsMethod="CORE_PROG003D0002E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="275px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0002E_exprId')}" id="CORE_PROG003D0002E_exprId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('CORE_PROG003D0002E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0002E_exprId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0002E_exprId" id="CORE_PROG003D0002E_exprId" value="sysExpression.exprId" width="200" maxlength="20" readonly="Y"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="50px" width="20%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0002E_type')}" id="CORE_PROG003D0002E_type" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0002E_type"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG003D0002E_type" dataSource="typeMap" id="CORE_PROG003D0002E_type" value="sysExpression.type"></gs:select>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0002E_name')}" id="CORE_PROG003D0002E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0002E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG003D0002E_name" id="CORE_PROG003D0002E_name" value="sysExpression.name" width="400" maxlength="100"></gs:textBox>
    		</td>
    	</tr>	
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG003D0002E_description')}" id="CORE_PROG003D0002E_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG003D0002E_description" name="CORE_PROG003D0002E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${sysExpression.description}</textarea>	
    		</td>
    	</tr>     	   
    </table>	
    <table border="0" width="100%" height="350px" cellpadding="1" cellspacing="0" >	      	 	
		<tr>
    		<td height="325px" width="100%"  align="left">
    		
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:350px">
						
    					<gs:label text="${action.getText('CORE_PROG003D0002E_iframe1')}" id="CORE_PROG003D0002E_iframe1" requiredFlag="Y"></gs:label>
    					<gs:inputfieldNoticeMsgLabel id="CORE_PROG003D0002E_content_noticeMsgLabelOnly"></gs:inputfieldNoticeMsgLabel>
    					<br/>
    					<iframe name="CORE_PROG003D0002E_iframe1" id="CORE_PROG003D0002E_iframe1" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=basePath%>core.commonCodeEditorAction.action?oid=${exprOid}'></iframe>
    				
					</div>
				</div>
					    		    	    		
    		</td>    		
    	</tr>   
    </table>
    <table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >	    	  		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0002E_update" id="CORE_PROG003D0002E_update" onClick="CORE_PROG003D0002E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemExpressionUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${sysExpression.oid}',
    						'fields.exprId'			: dijit.byId('CORE_PROG003D0002E_exprId').get('value'), 
    						'fields.type'			: dijit.byId('CORE_PROG003D0002E_type').get('value'),    						
    						'fields.name'			: dijit.byId('CORE_PROG003D0002E_name').get('value'),    						
    						'fields.content'		: document.getElementById('CORE_PROG003D0002E_iframe1').contentWindow.getValue(),    						
    						'fields.description'	: dijit.byId('CORE_PROG003D0002E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0002E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG003D0002E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0002E_clear" id="CORE_PROG003D0002E_clear" onClick="CORE_PROG003D0002E_clear();" 
    				label="${action.getText('CORE_PROG003D0002E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
