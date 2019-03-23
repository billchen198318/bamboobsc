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

var CORE_PROG001D0014A_fieldsId = new Object();
CORE_PROG001D0014A_fieldsId['name'] 		= 'CORE_PROG001D0014A_name';
CORE_PROG001D0014A_fieldsId['resultType'] 	= 'CORE_PROG001D0014A_resultType';
CORE_PROG001D0014A_fieldsId['type'] 		= 'CORE_PROG001D0014A_type';
CORE_PROG001D0014A_fieldsId['expression'] 	= 'CORE_PROG001D0014A_expressionNoticeMsgLabelOnly';

function CORE_PROG001D0014A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG001D0014A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0014A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG001D0014A_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, CORE_PROG001D0014A_fieldsId);
		return;
	}	
	CORE_PROG001D0014A_clear();
}

function CORE_PROG001D0014A_clear() {
	setFieldsBackgroundDefault(CORE_PROG001D0014A_fieldsId);
	setFieldsNoticeMessageLabelDefault(CORE_PROG001D0014A_fieldsId);
	dijit.byId('CORE_PROG001D0014A_name').set("value", "");
	dijit.byId('CORE_PROG001D0014A_resultType').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG001D0014A_type').set("value", _gscore_please_select_id);
	document.getElementById('CORE_PROG001D0014A_iframe1').contentWindow.clear();
	dijit.byId('CORE_PROG001D0014A_description').set("value", "");	
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
		saveJsMethod="CORE_PROG001D0014A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="675px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0014A_name')}" id="CORE_PROG001D0014A_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0014A_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="CORE_PROG001D0014A_name" id="CORE_PROG001D0014A_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>	   	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0014A_resultType')}" id="CORE_PROG001D0014A_resultType" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0014A_resultType"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0014A_resultType" dataSource="resultTypeMap" id="CORE_PROG001D0014A_resultType"></gs:select>
    		</td>    		
    	</tr>  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0014A_type')}" id="CORE_PROG001D0014A_type" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0014A_type"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="CORE_PROG001D0014A_type" dataSource="typeMap" id="CORE_PROG001D0014A_type"></gs:select>
    		</td>    		
    	</tr>  
		<tr>
    		<td height="350px" width="100%"  align="left">    	
	    <table border="0" width="100%" height="325px" cellpadding="1" cellspacing="0" >	    	
			<tr>
	    		<td height="325px" width="100%"  align="left">
	    		
					<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression' " >						
						<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:350px">
	    					<gs:label text="${action.getText('CORE_PROG001D0014A_iframe1')}" id="CORE_PROG001D0014A_iframe1" requiredFlag="Y"></gs:label>
	    					<gs:inputfieldNoticeMsgLabel id="CORE_PROG001D0014A_expressionNoticeMsgLabelOnly"></gs:inputfieldNoticeMsgLabel>
	    					<br/>
	    					<iframe name="CORE_PROG001D0014A_iframe1" id="CORE_PROG001D0014A_iframe1" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=basePath%>core.commonCodeEditorAction.action'></iframe>
						
						</div>
					</div>
						    		    	    		
	    		</td>    		
	    	</tr>   
	    </table> 
	    	</td>
	    </tr>	   	    		
		<tr>
    		<td height="125px" width="100%"  align="left">
    			<gs:label text="${action.getText('CORE_PROG001D0014A_description')}" id="CORE_PROG001D0014A_description"></gs:label>
    			<br/>
    			<textarea id="CORE_PROG001D0014A_description" name="CORE_PROG001D0014A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
    		</td>    		
    	</tr>     	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG001D0014A_save" id="CORE_PROG001D0014A_save" onClick="CORE_PROG001D0014A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemFormMethodSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.formOid'		: '${form.oid}',
    						'fields.name'			: dijit.byId('CORE_PROG001D0014A_name').get('value'),    
    						'fields.resultType'		: dijit.byId('CORE_PROG001D0014A_resultType').get('value'),
    						'fields.type'			: dijit.byId('CORE_PROG001D0014A_type').get('value'),						
    						'fields.expression'		: document.getElementById('CORE_PROG001D0014A_iframe1').contentWindow.getValue(),  
    						'fields.description'	: dijit.byId('CORE_PROG001D0014A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG001D0014A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('CORE_PROG001D0014A_save')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG001D0014A_clear" id="CORE_PROG001D0014A_clear" onClick="CORE_PROG001D0014A_clear();" 
    				label="${action.getText('CORE_PROG001D0014A_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
