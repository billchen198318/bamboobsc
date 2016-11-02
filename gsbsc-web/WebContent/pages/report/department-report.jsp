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

.btnSignatureIcon {
  	background-image: url(./icons/text_signature.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

#BSC_PROG003D0003Q_content { 
	width:100%; 
	height:100%;
	page-break-after:always 
}

</style>

<script type="text/javascript">

var BSC_PROG003D0003Q_fieldsId = new Object();
BSC_PROG003D0003Q_fieldsId['visionOid'] 		= 'BSC_PROG003D0003Q_visionOid';
BSC_PROG003D0003Q_fieldsId['frequency'] 		= 'BSC_PROG003D0003Q_frequency';
BSC_PROG003D0003Q_fieldsId['organizationOid'] 	= 'BSC_PROG003D0003Q_organizationOid';
BSC_PROG003D0003Q_fieldsId['dateType'] 			= 'BSC_PROG003D0003Q_dateType';

function BSC_PROG003D0003Q_query() {
	BSC_PROG003D0003Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0003Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0003Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.departmentReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 		: 	dijit.byId("BSC_PROG003D0003Q_visionOid").get("value"),
				'fields.frequency'		:	dijit.byId("BSC_PROG003D0003Q_frequency").get("value"),
				'fields.organizationOid':	dijit.byId("BSC_PROG003D0003Q_organizationOid").get("value"),
				'fields.dateType'		:	dijit.byId("BSC_PROG003D0003Q_dateType").get("value"),
				'fields.year'			:	dijit.byId("BSC_PROG003D0003Q_year").get("value")
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0003Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0003Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.byId("BSC_PROG003D0003Q_content").innerHTML = data.body;
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG003D0003Q_generateExport(docType) {
	var url = '${basePath}/bsc.departmentReportExcelQueryAction.action';
	if ( 'PDF' == docType ) {
		url = '${basePath}/bsc.departmentReportPdfQueryAction.action';
	}
	setFieldsBackgroundDefault(BSC_PROG003D0003Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG003D0003Q_fieldsId);
	xhrSendParameter(
			url, 
			{ 
				'fields.visionOid' 			: 	dijit.byId("BSC_PROG003D0003Q_visionOid").get("value"),
				'fields.frequency'			:	dijit.byId("BSC_PROG003D0003Q_frequency").get("value"),
				'fields.organizationOid'	:	dijit.byId("BSC_PROG003D0003Q_organizationOid").get("value"),
				'fields.dateType'			:	dijit.byId("BSC_PROG003D0003Q_dateType").get("value"),
				'fields.year'				:	dijit.byId("BSC_PROG003D0003Q_year").get("value"),
				'fields.uploadSignatureOid'	:	dojo.byId("BSC_PROG003D0003Q_uploadSignatureOid").value
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0003Q_fieldsId);
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG003D0003Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ( 'PDF' == docType ) {
					openCommonLoadUpload(
							'view', 
							data.uploadOid, 
							{ 
								"isDialog" 	: 	"Y",
								"title"		:	_getApplicationProgramNameById('${programId}'),
								"width"		:	1280,
								"height"	:	768
							} 
					);					
				} else {
					openCommonLoadUpload( 'download', data.uploadOid, { } );
				}				
			}, 
			function(error) {
				alert(error);
			}
	);
	
}

function BSC_PROG003D0003Q_setFrequencyValue() {
	var dateType = dijit.byId("BSC_PROG003D0003Q_dateType").get("value");
	dijit.byId("BSC_PROG003D0003Q_frequency").set("value", "5"); // half-year
	if ( "3" == dateType ) {
		dijit.byId("BSC_PROG003D0003Q_frequency").set("value", "6"); // year
	} 	
}

function BSC_PROG003D0003Q_clearContent() {
	dojo.byId("BSC_PROG003D0003Q_content").innerHTML = "";
}

function BSC_PROG003D0003Q_uploadSignatureSuccess() {
	
}

function BSC_PROG003D0003Q_uploadSignatureFail() {
	dojo.byId('BSC_PROG003D0003Q_uploadSignatureOid').value = '';
}

function BSC_PROG003D0003Q_uploadSignatureClear() {
	var uploadOid = dojo.byId('BSC_PROG003D0003Q_uploadSignatureOid').value;
	if (null == uploadOid || 'null' == uploadOid || '' == uploadOid) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0003Q_msg1')" escapeJavaScript="true"/>', function(){}, 'Y');	
		return;
	}	
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"Clear signature? ", 
			function(success) {
				if (!success) {
					return;
				}	
				dojo.byId('BSC_PROG003D0003Q_uploadSignatureOid').value = '';
				alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG003D0003Q_msg2')" escapeJavaScript="true"/>', function(){}, 'Y');				
			}, 
			(window.event ? window.event : null) 
	);
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
	
	<input type="hidden" name="BSC_PROG003D0003Q_uploadSignatureOid" id="BSC_PROG003D0003Q_uploadSignatureOid" value="" />
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="25%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG003D0003Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:120px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="BSC_PROG003D0003Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0003Q_query();
											}"><s:property value="getText('BSC_PROG003D0003Q_btnQuery')"/></button>		
																
									<button id="BSC_PROG003D0003Q_btnExportPng" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconPrint',
											showLabel:false,
											onClick:function(){
											
												html2canvas( $('#BSC_PROG003D0003Q_content'), {
													onrendered: function(canvas) {
												        var a = document.createElement('a');
												        a.download = 'department-report.png';
												        a.href = canvas.toDataURL('image/png');
												        document.body.appendChild(a);
												        a.click();							
													},
													allowTaint 	: true,
													useCORS		: true,
													taintTest	: true
												});
																							  
											}"><s:property value="getText('BSC_PROG003D0003Q_btnExportPng')"/></button>	
					
									<button id="BSC_PROG003D0003Q_btnPdf" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPdfIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0003Q_generateExport('PDF');	
											}"><s:property value="getText('BSC_PROG003D0003Q_btnPdf')"/></button>	
									            
									<button id="BSC_PROG003D0003Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0003Q_generateExport('EXCEL');																			  
											}"><s:property value="getText('BSC_PROG003D0003Q_btnXls')"/></button>									
								
								    &nbsp;&nbsp;&nbsp;&nbsp;
									<button id="BSC_PROG003D0003Q_btnSignature" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnSignatureIcon',
											showLabel:false,
											onClick:function(){
												openCommonSignatureDialog('BSC', 'BSC_PROG003D0003Q_uploadSignatureOid', 'BSC_PROG003D0003Q_uploadSignatureSuccess', 'BSC_PROG003D0003Q_uploadSignatureFail');													  
											}"><s:property value="getText('BSC_PROG003D0003Q_btnSignature')"/></button>												
										
									<button id="BSC_PROG003D0003Q_btnSignatureClear" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconClear',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0003Q_uploadSignatureClear();
											}"><s:property value="getText('BSC_PROG003D0003Q_btnSignatureClear')"/></button>										
									
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0003Q_visionOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0003Q_frequency"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0003Q_organizationOid"></gs:inputfieldNoticeMsgLabel>
									<gs:inputfieldNoticeMsgLabel id="BSC_PROG003D0003Q_dateType"></gs:inputfieldNoticeMsgLabel>
									
								</td>
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									<s:property value="getText('BSC_PROG003D0003Q_visionOid')"/>
									<gs:select name="BSC_PROG003D0003Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0003Q_visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0003Q_visionOid'">
					    				Select vision.
									</div>  									
						    		&nbsp;		    			
					    																	
									<s:property value="getText('BSC_PROG003D0003Q_frequency')"/>
									<gs:select name="BSC_PROG003D0003Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0003Q_frequency" value="6" width="140" readonly="Y"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0003Q_frequency'">
					    				Select frequency.
									</div> 	
																		
									&nbsp;
									<s:property value="getText('BSC_PROG003D0003Q_organizationOid')"/>
									<gs:filteringSelect name="BSC_PROG003D0003Q_organizationOid" dataSource="organizationMap" id="BSC_PROG003D0003Q_organizationOid" value="all"></gs:filteringSelect>									
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0003Q_organizationOid'">
					    				Select measure data organization/department.
									</div>	
																											
								</td>	
							</tr>							
							<tr valign="top">
								<td width="100%" align="left" height="30px">	
									
									<table border="0" width="100%" >
										<tr valign="top">
											<td width="250px" align="left" height="30px" >	
											
												<div id="BSC_PROG003D0003Q_year"
												    style="width:200px;"
												    name="horizontalSlider"
												    data-dojo-type="dijit/form/HorizontalSlider"
												    data-dojo-props="value:${maximum},
													    minimum:${minimum},
													    maximum:${maximum},
													    discreteValues:${discreteValues},
													    intermediateChanges:false,
													    showButtons:false,
													    onChange:function(value) {
													    
													    }
												    ">
												    <ol data-dojo-type="dijit/form/HorizontalRuleLabels" container="topDecoration"
												        style="height:1.5em;font-size:75%;color:gray;">
												        <s:if test="null != yearRangeList">
												        <s:iterator value="yearRangeList" status="st">
												        <li><s:property value="yearRangeList.get(#st.index)"/></li>
												        </s:iterator>
												        </s:if>
												    </ol>
												</div> 	
												<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG003D0003Q_year'">
								    				Select year.
												</div>	
																																		
											</td>
											<td width="300px" align="left" height="30px" >	
												<gs:select name="BSC_PROG003D0003Q_dateType" 
													dataSource="{ \"1\":\"${action.getText('BSC_PROG003D0003Q_dateType_1')}\", \"2\":\"${action.getText('BSC_PROG003D0003Q_dateType_2')}\", \"3\":\"${action.getText('BSC_PROG003D0003Q_dateType_3')}\" }" 
													id="BSC_PROG003D0003Q_dateType"
													onChange="BSC_PROG003D0003Q_setFrequencyValue();"
													value="3"></gs:select>											
											</td>
											<td align="left" height="30px" >
												&nbsp;
											</td>											
										</tr>
									</table>											
										
								</td>	
							</tr>							
							
							
						</table>							
					
					
					</div>
				</div>
			</td>
		</tr>
	</table>					
	
	
	<div id="BSC_PROG003D0003Q_content"></div>
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	