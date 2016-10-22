<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
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
	
	<link rel="stylesheet" href="<%=ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request)%>/css/ColorPicker.css" >
	
<style type="text/css">

</style>

<script type="text/javascript">

var BSC_PROG001D0004Q_fieldsId = new Object();
BSC_PROG001D0004Q_fieldsId['score'] 	= 	'BSC_PROG001D0004Q_score';

function BSC_PROG001D0004Q_querySuccess(data) {
	BSC_PROG001D0004Q_clear();
	if ('Y' != data.success) {						
		alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
		return;
	} 
	dojo.html.set(dojo.byId("BSC_PROG001D0004Q_content"), data.body, {extractContent: true, parseContent: true});	
}

function BSC_PROG001D0004Q_saveSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG001D0004Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0004Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0004Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0004Q_fieldsId);
		//return;
	}	
	BSC_PROG001D0004Q_query();
}

function BSC_PROG001D0004Q_edit(oid, fontColor, bgColor, scoreValue, editValue) {
	dojo.byId("BSC_PROG001D0004Q_oid").value = oid;
	dijit.byId("BSC_PROG001D0004Q_colorPicker1").set("value", fontColor);
	dijit.byId("BSC_PROG001D0004Q_colorPicker2").set("value", bgColor);	
	dijit.byId("BSC_PROG001D0004Q_score").set("value", scoreValue);
	dijit.byId("BSC_PROG001D0004Q_score").set("readOnly", true);
	if (editValue) {
		dijit.byId("BSC_PROG001D0004Q_score").set("readOnly", false);
	}
}

function BSC_PROG001D0004Q_delete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('BSC_PROG001D0004Q_delete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.scoreColorDeleteAction.action', 
						{ 'fields.oid' : oid }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
							BSC_PROG001D0004Q_query();
						}, 
						function(error) {
							alert(error);
						}
				);	
			}, 
			(window.event ? window.event : null) 
	);		
}

function BSC_PROG001D0004Q_clear() {
	dojo.byId("BSC_PROG001D0004Q_oid").value = "";
	dojo.byId("BSC_PROG001D0004Q_content").innerHTML = "";
	dijit.byId("BSC_PROG001D0004Q_score").set("value", "0");
	dijit.byId("BSC_PROG001D0004Q_score").set("readOnly", false);
	dijit.byId("BSC_PROG001D0004Q_colorPicker1").set("value", "#000000");
	dijit.byId("BSC_PROG001D0004Q_colorPicker2").set("value", "#ffffff");		
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
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<input type="hidden" name="BSC_PROG001D0004Q_oid" id="BSC_PROG001D0004Q_oid" value="" />
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="left" height="30%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG001D0004Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:255px">
			    			
		    			<gs:button name="BSC_PROG001D0004Q_query" id="BSC_PROG001D0004Q_query" onClick="BSC_PROG001D0004Q_query();"
		    				handleAs="json"
		    				sync="N"
		    				xhrUrl="${basePath}/bsc.scoreColorContentQueryAction.action"
		    				parameterType="postData"
		    				xhrParameter="{ }"
		    				errorFn=""
		    				loadFn="BSC_PROG001D0004Q_querySuccess(data);" 
		    				programId="${programId}"
		    				label="${action.getText('BSC_PROG001D0004Q_query')}" 
		    				iconClass="dijitIconSearch"
		    				cssClass="alt-primary"></gs:button>    					    					
		    			<gs:button name="BSC_PROG001D0004Q_clear" id="BSC_PROG001D0004Q_clear" onClick="BSC_PROG001D0004Q_clear();" 
		    				label="${action.getText('BSC_PROG001D0004Q_clear')}" 
		    				iconClass="dijitIconClear"
		    				cssClass="alt-primary"></gs:button>    	    					
    					
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					
						<gs:label text="${action.getText('BSC_PROG001D0004Q_score')}" id="BSC_PROG001D0004Q_score"></gs:label>
			    		<gs:textBox name="BSC_PROG001D0004Q_score" id="BSC_PROG001D0004Q_score" value="0" width="100" maxlength="9" />
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0004Q_score'">
    						Input range score value, only allow numbers.<BR/>
    						current value must bigger than before-record score value.
    						<BR/><BR/>
    						min cannot smaller than -999999999.<BR/>
    						max cannot bigger than 999999999.
						</div> 	
							    				    		
			    		<gs:button name="BSC_PROG001D0004Q_save" id="BSC_PROG001D0004Q_save" onClick="BSC_PROG001D0004Q_save();"
			    			handleAs="json"
			    			sync="N"
			   				xhrUrl="${basePath}/bsc.scoreColorSaveOrUpdateAction.action"
			   				parameterType="postData"
			   				xhrParameter="
			   					{
			   						'fields.oid'		:	dojo.byId('BSC_PROG001D0004Q_oid').value,
			   						'fields.score'		:	dijit.byId('BSC_PROG001D0004Q_score').get('value'),
			   						'fields.fontColor'	:	dijit.byId('BSC_PROG001D0004Q_colorPicker1').get('value'),
			   						'fields.bgColor'	:	dijit.byId('BSC_PROG001D0004Q_colorPicker2').get('value')
			   					}
							"
			   				errorFn=""
		    				loadFn="BSC_PROG001D0004Q_saveSuccess(data);" 
			    			programId="${programId}"
			    			label="${action.getText('BSC_PROG001D0004Q_save')}" 
			    			iconClass="dijitIconSave"
			    			cssClass="alt-primary"></gs:button> 	
			    			
			    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0004Q_score"></gs:inputfieldNoticeMsgLabel>
			    		
			    		<br/>
			    		<br/>
			    			
			    		<table border="0" width="800px" height="150px">
							<tr valign="top">
								<td width="400px" align="left">
									<gs:label text="${action.getText('BSC_PROG001D0004Q_colorPicker1')}" id="BSC_PROG001D0004Q_colorPicker1"></gs:label>
									<div data-dojo-type="dojox.widget.ColorPicker" value="#000000" id="BSC_PROG001D0004Q_colorPicker1"></div>		
								</td>
								<td width="400px" align="left">
									<gs:label text="${action.getText('BSC_PROG001D0004Q_colorPicker2')}" id="BSC_PROG001D0004Q_colorPicker2"></gs:label>
									<div data-dojo-type="dojox.widget.ColorPicker" value="#ffffff" id="BSC_PROG001D0004Q_colorPicker2"></div>	
								</td>
							</tr>		    		
			    		</table>		    			
		    			
		    		</div>		    		
		    	</div>	
		    			
			</td>
		</tr>
		<tr valign="top">
			<td width="100%" align="left" height="900px">
				<div id="BSC_PROG001D0004Q_content"></div>
			</td>
		</tr>
	</table>

<script type="text/javascript">
${programId}_page_message();
setTimeout("BSC_PROG001D0004Q_query();", 500);
</script>	
</body>
</html>
	