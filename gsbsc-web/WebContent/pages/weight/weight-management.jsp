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

var BSC_PROG002D0006Q_fieldsId = new Object();
BSC_PROG002D0006Q_fieldsId['visionOid'] 		= 'BSC_PROG002D0006Q_visionOid';

function BSC_PROG002D0006Q_querySuccess(data) {
	BSC_PROG002D0006Q_clear();
	setFieldsBackgroundDefault(BSC_PROG002D0006Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0006Q_fieldsId);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0006Q_fieldsId);
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0006Q_fieldsId);
		alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
		return;
	} 
	dojo.html.set(dojo.byId("BSC_PROG002D0006Q_content"), data.body, {extractContent: true, parseContent: true});
}

function BSC_PROG002D0006Q_saveSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0006Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0006Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0006Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0006Q_fieldsId);
		return;
	}	
}

function BSC_PROG002D0006Q_clear() {
	dojo.byId("BSC_PROG002D0006Q_content").innerHTML = "";
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
	
	<table border="0" width="100%" height="100%">
		<tr>
			<td width="100%" valign="top" height="55px" align="left">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG002D0006Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:50px">
					
					<gs:label text="${action.getText('BSC_PROG002D0006Q_visionOid')}" id="BSC_PROG002D0006Q_visionOid"></gs:label>
					<gs:select name="BSC_PROG002D0006Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0006Q_visionOid"></gs:select>
					<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0006Q_visionOid'">
						Select vision.
					</div>   					
					&nbsp;&nbsp;
	    			<gs:button name="BSC_PROG002D0006Q_query" id="BSC_PROG002D0006Q_query" onClick="BSC_PROG002D0006Q_query();"
	    				handleAs="json"
	    				sync="N"
	    				xhrUrl="${basePath}/bsc.weightContentQueryAction.action"
	    				parameterType="postData"
	    				xhrParameter=" 
	    					{ 
	    						'fields.visionOid'	:	dijit.byId('BSC_PROG002D0006Q_visionOid').get('value')
	    					} 
	    				"
	    				errorFn=""
	    				loadFn="BSC_PROG002D0006Q_querySuccess(data);" 
	    				programId="${programId}"
	    				label="${action.getText('BSC_PROG002D0006Q_query')}" 
	    				iconClass="dijitIconSearch"
	    				cssClass="alt-primary"></gs:button>
	    			<gs:button name="BSC_PROG002D0006Q_queryAutoAllocation" id="BSC_PROG002D0006Q_queryAutoAllocation" onClick="BSC_PROG002D0006Q_queryAutoAllocation();"
	    				handleAs="json"
	    				sync="N"
	    				xhrUrl="${basePath}/bsc.weightContentQueryAction.action"
	    				parameterType="postData"
	    				xhrParameter=" 
	    					{ 
	    						'fields.visionOid'		:	dijit.byId('BSC_PROG002D0006Q_visionOid').get('value'),
	    						'fields.autoAllocation'	:	'Y'
	    					} 
	    				"
	    				errorFn=""
	    				loadFn="BSC_PROG002D0006Q_querySuccess(data);" 
	    				programId="${programId}"
	    				label="${action.getText('BSC_PROG002D0006Q_queryAutoAllocation')}" 
	    				iconClass="dijitIconSearch"
	    				cssClass="alt-primary"></gs:button>	    
	    			&nbsp;&nbsp;&nbsp;&nbsp;					
	    			<gs:button name="BSC_PROG002D0006Q_save" id="BSC_PROG002D0006Q_save" onClick="BSC_PROG002D0006Q_save();"
	    				handleAs="json"
	    				sync="N"
	    				xhrUrl="${basePath}/bsc.weightUpdateAction.action"
	    				parameterType="form"
	    				xhrParameter="BSC_PROG002D0006Q_weightDataForm"
	    				errorFn=""
	    				loadFn="BSC_PROG002D0006Q_saveSuccess(data);" 
	    				programId="${programId}"
	    				label="${action.getText('BSC_PROG002D0006Q_save')}" 
	    				iconClass="dijitIconSave"
	    				cssClass="alt-primary"></gs:button> 
    				
    				<br/>
    				<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0006Q_visionOid"></gs:inputfieldNoticeMsgLabel>
    									
					</div>
				</div>					
			</td>
		</tr>
		<tr>
			<td width="100%" valign="top" align="center">
				<form name="BSC_PROG002D0006Q_weightDataForm" id="BSC_PROG002D0006Q_weightDataForm" method="post" action=".">
					<div id="BSC_PROG002D0006Q_content"></div>					
				</form>
			</td>
		</tr>
	</table>			
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	