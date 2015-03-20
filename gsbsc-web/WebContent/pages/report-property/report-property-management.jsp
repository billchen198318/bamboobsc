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

var BSC_PROG004D0001Q_fieldsId = new Object();
BSC_PROG004D0001Q_fieldsId['perspectiveTitle'] 		= 'BSC_PROG004D0001Q_perspectiveTitle';
BSC_PROG004D0001Q_fieldsId['objectiveTitle'] 		= 'BSC_PROG004D0001Q_objectiveTitle';
BSC_PROG004D0001Q_fieldsId['kpiTitle'] 				= 'BSC_PROG004D0001Q_kpiTitle';
BSC_PROG004D0001Q_fieldsId['classNote'] 			= 'BSC_PROG004D0001Q_classNote';

function BSC_PROG004D0001Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG004D0001Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG004D0001Q_fieldsId);		
		return;
	}	
	${programId}_TabRefresh();
}

function BSC_PROG004D0001Q_clear() {
	dijit.byId('BSC_PROG004D0001Q_perspectiveTitle').set("value", "");	
	dijit.byId('BSC_PROG004D0001Q_objectiveTitle').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_kpiTitle').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_classNote').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_colorPicker1').set("value", "#000000");
	dijit.byId('BSC_PROG004D0001Q_colorPicker2').set("value", "#ffffff");
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

<body class="tundra" role="main">

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
	
	<table border="0" width="800px" >
		<tr valign="top">
			<td width="100%" align="left" height="30%" colspan="2">
			    <table border="0" width="800px" height="150px">
					<tr valign="top">
						<td width="400px" align="left">
							<h3>font color</h3>
							<div data-dojo-type="dojox.widget.ColorPicker" value="${fields.fontColor}" id="BSC_PROG004D0001Q_colorPicker1"></div>		
						</td>
						<td width="400px" align="left">
							<h3>background color</h3>
							<div data-dojo-type="dojox.widget.ColorPicker" value="${fields.backgroundColor}" id="BSC_PROG004D0001Q_colorPicker2"></div>	
						</td>
					</tr>		    		
			    </table>			
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" >Perspective title:</td>
			<td width="80%" align="left" >
				<gs:textBox name="BSC_PROG004D0001Q_perspectiveTitle" id="BSC_PROG004D0001Q_perspectiveTitle" value="fields.perspectiveTitle" maxlength="100"></gs:textBox>
			</td>			
		</tr>
		<tr>
			<td width="20%" align="right" >Objective title:</td>
			<td width="80%" align="left" >
				<gs:textBox name="BSC_PROG004D0001Q_objectiveTitle" id="BSC_PROG004D0001Q_objectiveTitle" value="fields.objectiveTitle" maxlength="100"></gs:textBox>
			</td>			
		</tr>	
		<tr>
			<td width="20%" align="right" >KPI title:</td>
			<td width="80%" align="left" >
				<gs:textBox name="BSC_PROG004D0001Q_kpiTitle" id="BSC_PROG004D0001Q_kpiTitle" value="fields.kpiTitle" maxlength="100"></gs:textBox>
			</td>			
		</tr>	
		<tr>
		    <td height="100px" width="20%" align="right">Class level note:</td>
		    <td height="100px" width="80%"  align="left">
		    	<textarea id="BSC_PROG004D0001Q_classNote" name="BSC_PROG004D0001Q_classNote" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${fields.classNote}</textarea>	
		    </td>
		</tr>     		
		<tr>
			<td width="20%" align="right" >&nbsp;</td>
			<td width="80%" align="left" >
			    <gs:button name="BSC_PROG004D0001Q_save" id="BSC_PROG004D0001Q_save" onClick="BSC_PROG004D0001Q_save();"
	    			handleAs="json"
	    			sync="N"
	   				xhrUrl="${basePath}/bsc.reportPropertyUpdateAction.action"
	   				parameterType="postData"
	   				xhrParameter="
	   					{
	   						'fields.perspectiveTitle'		:	dijit.byId('BSC_PROG004D0001Q_perspectiveTitle').get('value'),	   						
	   						'fields.objectiveTitle'			:	dijit.byId('BSC_PROG004D0001Q_objectiveTitle').get('value'),
	   						'fields.kpiTitle'				:	dijit.byId('BSC_PROG004D0001Q_kpiTitle').get('value'),	   						
	   						'fields.fontColor'				:	dijit.byId('BSC_PROG004D0001Q_colorPicker1').get('value'),
	   						'fields.backgroundColor'		:	dijit.byId('BSC_PROG004D0001Q_colorPicker2').get('value'),
	   						'fields.classNote'				:	dijit.byId('BSC_PROG004D0001Q_classNote').get('value')
	   					}
					"
	   				errorFn=""
    				loadFn="BSC_PROG004D0001Q_saveSuccess(data);" 
	    			programId="${programId}"
	    			label="Save" 
	    			iconClass="dijitIconSave"></gs:button> 			
    			<gs:button name="BSC_PROG004D0001Q_clear" id="BSC_PROG004D0001Q_clear" onClick="BSC_PROG004D0001Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    				    					
			</td>
		</tr>			
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	