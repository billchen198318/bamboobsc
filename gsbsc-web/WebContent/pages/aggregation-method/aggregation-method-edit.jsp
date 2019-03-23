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

var BSC_PROG001D0008E_fieldsId = new Object();
//BSC_PROG001D0008E_fieldsId['aggrId'] 		= 'BSC_PROG001D0008E_aggrId';
BSC_PROG001D0008E_fieldsId['name'] 			= 'BSC_PROG001D0008E_name';
BSC_PROG001D0008E_fieldsId['type'] 			= 'BSC_PROG001D0008E_type';
BSC_PROG001D0008E_fieldsId['expression1'] 	= 'BSC_PROG001D0008E_expression1_noticeMsgLabelOnly';
BSC_PROG001D0008E_fieldsId['expression2'] 	= 'BSC_PROG001D0008E_expression2_noticeMsgLabelOnly';
BSC_PROG001D0008E_fieldsId['description']	= 'BSC_PROG001D0008E_description';

function BSC_PROG001D0008E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0008E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0008E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0008E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0008E_fieldsId);
		return;
	}		
}

function BSC_PROG001D0008E_clear() {
	setFieldsBackgroundDefault(BSC_PROG001D0008E_fieldsId);		
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0008E_fieldsId);
	//dijit.byId('BSC_PROG001D0008E_aggrId').set("value", "");
	dijit.byId('BSC_PROG001D0008E_name').set("value", "");
	dijit.byId('BSC_PROG001D0008E_type').set("value", _gscore_please_select_id);
	//dijit.byId('BSC_PROG001D0008E_expression1').set("value", "");
	//dijit.byId('BSC_PROG001D0008E_expression2').set("value", "");
	document.getElementById('BSC_PROG001D0008E_iframe1').contentWindow.clear();
	document.getElementById('BSC_PROG001D0008E_iframe2').contentWindow.clear();		
	dijit.byId('BSC_PROG001D0008E_description').set("value", "");
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
		saveJsMethod="BSC_PROG001D0008E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="300px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0008E_aggrId')}" id="BSC_PROG001D0008E_aggrId" requiredFlag="Y"></gs:label>
    			&nbsp;<s:property value="getText('BSC_PROG001D0008E_readOnly')"/><gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_aggrId"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0008E_aggrId" id="BSC_PROG001D0008E_aggrId" value="aggr.aggrId" width="200" maxlength="14" readonly="Y"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0008E_aggrId'">
    				Input Id, ( read only )
				</div>        			
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0008E_name')}" id="BSC_PROG001D0008E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG001D0008E_name" id="BSC_PROG001D0008E_name" value="aggr.name" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0008E_name'">
    				Input name.
				</div>        			
    		</td>
    	</tr>     	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG001D0008E_type')}" id="BSC_PROG001D0008E_type" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_type"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:select name="BSC_PROG001D0008E_type" dataSource="typeMap" id="BSC_PROG001D0008E_type" value="aggr.type"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0008E_type'">
		    		Select langauge type. ( recommend groovy )
				</div>     			
    		</td>
    	</tr>     	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG001D0008E_description')}" id="BSC_PROG001D0008E_description"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_description"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG001D0008E_description" name="BSC_PROG001D0008E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${aggr.description}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0008E_description'">
		    		Input description, the maximum allowed 500 characters.
				</div>		    		
		    </td>
		</tr>         	
	</table>
	<table border="0" width="100%" height="360px" cellpadding="1" cellspacing="0" >	    
		<tr>
		    <td height="360px" width="100%" align="left">
		    
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:350px">
							    
		    		<gs:label text="${action.getText('BSC_PROG001D0008E_iframe1')}" id="BSC_PROG001D0008E_iframe1" requiredFlag="Y"></gs:label>
		    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_expression1_noticeMsgLabelOnly"></gs:inputfieldNoticeMsgLabel>
		    		<br/>
		    		<iframe name="BSC_PROG001D0008E_iframe1" id="BSC_PROG001D0008E_iframe1" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=mainSysBasePath%>core.commonCodeEditorAction.action?oid=${uploadExprOid1}&<%=Constants.IS_IFRAME_MODE%>=Y'></iframe>
		    		
		    		</div>
		    	</div>
		    		
		    </td>
		</tr>  
	</table>	
	<table border="0" width="100%" height="360px" cellpadding="1" cellspacing="0" >
		<tr>
		    <td height="360px" width="100%" align="left">
		    
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Expression ( for date range )' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:350px">		    
		    
		    		<gs:label text="${action.getText('BSC_PROG001D0008E_iframe2')}" id="BSC_PROG001D0008E_iframe2" requiredFlag="Y"></gs:label>
		    		<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0008E_expression2_noticeMsgLabelOnly"></gs:inputfieldNoticeMsgLabel>
		    		<br/>
		    		<iframe name="BSC_PROG001D0008E_iframe2" id="BSC_PROG001D0008E_iframe2" style='width:100%;height:300px;border:0px' border='0' scrolling='no' src='<%=mainSysBasePath%>core.commonCodeEditorAction.action?oid=${uploadExprOid2}&<%=Constants.IS_IFRAME_MODE%>=Y'></iframe>
		    		
		    		</div>
		    	</div>	
		    
		    </td>
		</tr>  
	</table>			    	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >		    	  	     	   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG001D0008E_update" id="BSC_PROG001D0008E_update" onClick="BSC_PROG001D0008E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.aggregationMethodUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${aggr.oid}',
    						'fields.aggrId'			: dijit.byId('BSC_PROG001D0008E_aggrId').get('value'),
    						'fields.name'			: dijit.byId('BSC_PROG001D0008E_name').get('value'),  
    						'fields.type'			: dijit.byId('BSC_PROG001D0008E_type').get('value'),					 
    						'fields.expression1'	: document.getElementById('BSC_PROG001D0008E_iframe1').contentWindow.getValue(),
    						'fields.expression2'	: document.getElementById('BSC_PROG001D0008E_iframe2').contentWindow.getValue(),
    						'fields.description'	: dijit.byId('BSC_PROG001D0008E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG001D0008E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG001D0008E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG001D0008E_clear" id="BSC_PROG001D0008E_clear" onClick="BSC_PROG001D0008E_clear();" 
    				label="${action.getText('BSC_PROG001D0008E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
