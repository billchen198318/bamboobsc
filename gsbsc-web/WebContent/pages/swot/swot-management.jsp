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

.btnExcelIcon {
  	background-image: url(./icons/excel.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnPdfIcon {
  	background-image: url(./icons/application-pdf.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

</style>

<script type="text/javascript">

var BSC_PROG002D0008Q_fieldsId = new Object();
BSC_PROG002D0008Q_fieldsId['visionOid'] 		= 'BSC_PROG002D0008Q_visionOid';
BSC_PROG002D0008Q_fieldsId['organizationOid'] 	= 'BSC_PROG002D0008Q_organizationOid';

function BSC_PROG002D0008Q_query() {
	BSC_PROG002D0008Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG002D0008Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0008Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.swotContentQueryAction.action', 
			{ 
				'fields.visionOid' 			: 	dijit.byId("BSC_PROG002D0008Q_visionOid").get("value"),
				'fields.organizationOid'	:	dijit.byId("BSC_PROG002D0008Q_organizationOid").get("value")
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0008Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0008Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.html.set(dojo.byId("BSC_PROG002D0008Q_content"), data.body, {extractContent: true, parseContent: true});
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG002D0008Q_pdf() {
	setFieldsBackgroundDefault(BSC_PROG002D0008Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0008Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.swotPdfDataAction.action', 
			{ 
				'fields.visionOid' 			: 	dijit.byId("BSC_PROG002D0008Q_visionOid").get("value"),
				'fields.organizationOid'	:	dijit.byId("BSC_PROG002D0008Q_organizationOid").get("value")
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0008Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0008Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				openCommonJasperReportLoadWindow( "SWOT-Report", "BSC_RPT002", "PDF", { 'reportId' : data.reportId } );
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG002D0008Q_saveSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0008Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG002D0008Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0008Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG002D0008Q_fieldsId);
		return;
	}	
}

function BSC_PROG002D0008Q_clearContent() {
	dojo.byId("BSC_PROG002D0008Q_content").innerHTML = "";
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

<body data-demo-id="statemachine" data-library="jquery">
	
	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG002D0008Q_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>	
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG002D0008Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:80px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG002D0008Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){
												BSC_PROG002D0008Q_query();
											}"><s:property value="getText('BSC_PROG002D0008Q_btnQuery')"/></button>		
									
					    			<gs:button name="BSC_PROG002D0008Q_save" id="BSC_PROG002D0008Q_save" onClick="BSC_PROG002D0008Q_save();"
					    				handleAs="json"
					    				sync="N"
					    				xhrUrl="${basePath}/bsc.swotSaveAction.action"
					    				parameterType="form"
					    				xhrParameter="BSC_PROG002D0008Q_form"
					    				errorFn=""
					    				loadFn="BSC_PROG002D0008Q_saveSuccess(data);" 
					    				programId="${programId}"
					    				label="${action.getText('BSC_PROG002D0008Q_save')}" 
					    				showLabel="N"
					    				iconClass="dijitIconSave"></gs:button> 									
																
									<button id="BSC_PROG002D0008Q_btnExportPng" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconPrint',
											showLabel:false,
											onClick:function(){
											
												html2canvas( $('#BSC_PROG002D0008Q_content'), {
													onrendered: function(canvas) {
												        var a = document.createElement('a');
												        a.download = 'swot.png';
												        a.href = canvas.toDataURL('image/png');
												        document.body.appendChild(a);
												        a.click();							
													},
													allowTaint 	: true,
													useCORS		: true,
													taintTest	: true
												});											
																							  
											}"><s:property value="getText('BSC_PROG002D0008Q_btnExportPng')"/></button>	
					
									<button id="BSC_PROG002D0008Q_btnPdf" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPdfIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG002D0008Q_pdf();
											}"><s:property value="getText('BSC_PROG002D0008Q_btnPdf')"/></button>	
									
									&nbsp;
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0008Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG002D0008Q_organizationOid"></gs:inputfieldNoticeMsgLabel>
											
								</td>											
							</tr>
							
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
									<gs:label text="${action.getText('BSC_PROG002D0008Q_visionOid')}" id="BSC_PROG002D0008Q_visionOid"></gs:label>
									<gs:select name="BSC_PROG002D0008Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0008Q_visionOid" onChange="BSC_PROG002D0008Q_clearContent();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0008Q_visionOid'">
										Select vision.
									</div> 									
						    		&nbsp;		    			
					    																	
									<gs:label text="${action.getText('BSC_PROG002D0008Q_organizationOid')}" id="BSC_PROG002D0008Q_organizationOid"></gs:label>
									<gs:select name="BSC_PROG002D0008Q_organizationOid" dataSource="organizationMap" id="BSC_PROG002D0008Q_organizationOid" onChange="BSC_PROG002D0008Q_clearContent();"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG002D0008Q_organizationOid'">
										Select organization/department.
									</div> 											
								</td>											
							</tr>							
							
						</table>						
					
					
					</div>
				</div>
			</td>
		</tr>
	</table>			
	
	<form name="BSC_PROG002D0008Q_form" id="BSC_PROG002D0008Q_form" method="post" action=".">
		<div id="BSC_PROG002D0008Q_content"></div>		
	</form>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
		