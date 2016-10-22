<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@page import="com.netsteadfast.greenstep.util.Pivot4JUtils"%>
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

<%=Pivot4JUtils.getHtmlCss()%>


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

.btnExportIcon {
  	background-image: url(./icons/document-export.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

</style>

<script type="text/javascript">

var QCHARTS_PROG002D0002Q_fieldsId = new Object();
QCHARTS_PROG002D0002Q_fieldsId['oid'] 					= 'QCHARTS_PROG002D0002Q_mdxOid';
QCHARTS_PROG002D0002Q_fieldsId['name'] 					= 'QCHARTS_PROG002D0002Q_name';
QCHARTS_PROG002D0002Q_fieldsId['configOid'] 			= 'QCHARTS_PROG002D0002Q_olapConfigOid';
QCHARTS_PROG002D0002Q_fieldsId['catalogOid'] 			= 'QCHARTS_PROG002D0002Q_olapCatalogOid';
QCHARTS_PROG002D0002Q_fieldsId['expression'] 			= 'QCHARTS_PROG002D0002Q_expression';

function getQCHARTS_PROG002D0002Q_Parameter() {
	return {
		'fields.oid'					:	dijit.byId('QCHARTS_PROG002D0002Q_mdxOid').get('value'),
		'fields.name'					:	dijit.byId('QCHARTS_PROG002D0002Q_name').get('value'),
		'fields.configOid'				:	dijit.byId('QCHARTS_PROG002D0002Q_olapConfigOid').get('value'),
		'fields.catalogOid'				:	dijit.byId('QCHARTS_PROG002D0002Q_olapCatalogOid').get('value'),												
		'fields.expression'				:	dijit.byId('QCHARTS_PROG002D0002Q_expression').get('value'),
		'fields.showDimensionTitle'		: 	( dijit.byId('QCHARTS_PROG002D0002Q_showDimensionTitle').checked ? 'true' : 'false' ),
		'fields.showParentMembers'		: 	( dijit.byId('QCHARTS_PROG002D0002Q_showParentMembers').checked ? 'true' : 'false' )		
	};
}

function QCHARTS_PROG002D0002Q_query() {	
	setFieldsBackgroundDefault(QCHARTS_PROG002D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.analyticsHtmlAction.action', 
			getQCHARTS_PROG002D0002Q_Parameter(), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0002Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0002Q_fieldsId);
					return;
				}
				dojo.byId("QCHARTS_PROG002D0002Q_content").innerHTML = data.content;
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0002Q_queryExport() {	
	setFieldsBackgroundDefault(QCHARTS_PROG002D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.analyticsHtmlExportAction.action', 
			getQCHARTS_PROG002D0002Q_Parameter(), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0002Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0002Q_fieldsId);
					return;
				}
				dojo.byId("QCHARTS_PROG002D0002Q_content").innerHTML = data.content;
				openCommonLoadUpload( 'download', data.oid, { } );
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0002Q_excel() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/qcharts.analyticsExcelAction.action', 
			getQCHARTS_PROG002D0002Q_Parameter(), 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0002Q_fieldsId);					
					setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0002Q_fieldsId);
					return;
				}
				openCommonLoadUpload( 'download', data.oid, { } );				 				
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function QCHARTS_PROG002D0002Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(QCHARTS_PROG002D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0002Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG002D0002Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, QCHARTS_PROG002D0002Q_fieldsId);
		return;
	}	
	QCHARTS_PROG002D0002Q_clear();
}

function QCHARTS_PROG002D0002Q_delete() {
	var oid = dijit.byId("QCHARTS_PROG002D0002Q_mdxOid").get("value");
	if ( '' == oid || _gscore_please_select_id == oid ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('QCHARTS_PROG002D0002Q_mdxOid_pleaseSelect')" escapeJavaScript="true"/>', function(){}, 'N');	
		return;
	}
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('QCHARTS_PROG002D0002Q_deleteConfirm')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/qcharts.analyticsDeleteAction.action', 
						{ 'fields.oid' : oid }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							if ('Y' != data.success) {		
								alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
								return;
							}	
							QCHARTS_PROG002D0002Q_clear();
						}, 
						function(error) {
							alert(error);
						}
				);		
			}, 
			(window.event ? window.event : null) 
	);	
		
}

function QCHARTS_PROG002D0002Q_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG002D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(QCHARTS_PROG002D0002Q_fieldsId);
	dijit.byId("QCHARTS_PROG002D0002Q_mdxOid").set("value", _gscore_please_select_id);
	dijit.byId("QCHARTS_PROG002D0002Q_name").set("value", "");	
	dijit.byId("QCHARTS_PROG002D0002Q_olapConfigOid").set("value", _gscore_please_select_id);	
	dijit.byId("QCHARTS_PROG002D0002Q_olapCatalogOid").set("value", _gscore_please_select_id);	
	dijit.byId("QCHARTS_PROG002D0002Q_expression").set("value", "");
	dijit.byId('QCHARTS_PROG002D0002Q_showDimensionTitle').set("checked", true);
	dijit.byId('QCHARTS_PROG002D0002Q_showParentMembers').set("checked", true);	
	dojo.byId("QCHARTS_PROG002D0002Q_content").innerHTML = "";
	QCHARTS_PROG002D0002Q_reloadQueryHistoryItems();
}

function QCHARTS_PROG002D0002Q_reloadQueryHistoryItems() {
	clearSelectItems(true, 'QCHARTS_PROG002D0002Q_mdxOid');
	xhrSendParameter(
			'${basePath}/qcharts.commonGetMdxItemsAction.action', 
			{ }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {			
					return;
				}	
				setSelectItems(data, 'QCHARTS_PROG002D0002Q_mdxOid');
			}, 
			function(error) {
				alert(error);
			}
	);		
	
}

function QCHARTS_PROG002D0002Q_getMdxHistory() {
	var oid = dijit.byId("QCHARTS_PROG002D0002Q_mdxOid").get("value");
	if ( '' == oid || _gscore_please_select_id == oid ) {
		QCHARTS_PROG002D0002Q_clear();
		return;
	}
	xhrSendParameter(
			'${basePath}/qcharts.commonGetMdxHistoryAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {		
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
					return;
				}					
				dijit.byId("QCHARTS_PROG002D0002Q_name").set("value", data.olapMdx.name);
				dijit.byId("QCHARTS_PROG002D0002Q_olapConfigOid").set("value", data.olapMdx.confOid);
				dijit.byId("QCHARTS_PROG002D0002Q_olapCatalogOid").set("value", data.olapMdx.catalogOid);
				dijit.byId("QCHARTS_PROG002D0002Q_expression").set("value", data.fields.expression);
				
			}, 
			function(error) {
				alert(error);
			}
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
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="55%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('QCHARTS_PROG002D0002Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:240px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">	
								
									<button id="QCHARTS_PROG002D0002Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0002Q_query();
											}"><s:property value="getText('QCHARTS_PROG002D0002Q_btnQuery')"/></button>				
							
									<button id="QCHARTS_PROG002D0002Q_btnQueryExport" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExportIcon',
											showLabel:false,
											onClick:function(){  
												QCHARTS_PROG002D0002Q_queryExport();
											}"><s:property value="getText('QCHARTS_PROG002D0002Q_btnQueryExport')"/></button>																			
											
									<button id="QCHARTS_PROG002D0002Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												QCHARTS_PROG002D0002Q_excel();																			  
											}"><s:property value="getText('QCHARTS_PROG002D0002Q_btnXls')"/></button>	
											
					    			<gs:button name="QCHARTS_PROG002D0002Q_save" id="QCHARTS_PROG002D0002Q_save" onClick="QCHARTS_PROG002D0002Q_save();"
					    				handleAs="json"
					    				sync="N"
					    				xhrUrl="${basePath}/qcharts.analyticsSaveAction.action"
					    				parameterType="postData"
					    				xhrParameter=" 
					    					{ 
					    						'fields.oid'					:	dijit.byId('QCHARTS_PROG002D0002Q_mdxOid').get('value'),
												'fields.name'					:	dijit.byId('QCHARTS_PROG002D0002Q_name').get('value'),
												'fields.configOid'				:	dijit.byId('QCHARTS_PROG002D0002Q_olapConfigOid').get('value'),
												'fields.catalogOid'				:	dijit.byId('QCHARTS_PROG002D0002Q_olapCatalogOid').get('value'),												
												'fields.expression'				:	dijit.byId('QCHARTS_PROG002D0002Q_expression').get('value')
					    					} 
					    				"
					    				errorFn=""
					    				loadFn="QCHARTS_PROG002D0002Q_saveSuccess(data);" 
					    				programId="${programId}"
					    				label="${action.getText('QCHARTS_PROG002D0002Q_save')}" 
					    				showLabel="N"
					    				iconClass="dijitIconSave"></gs:button>    
					    							
					    			<gs:button name="QCHARTS_PROG002D0002Q_clear" id="QCHARTS_PROG002D0002Q_clear" onClick="QCHARTS_PROG002D0002Q_clear();" 
					    				label="${action.getText('QCHARTS_PROG002D0002Q_clear')}"
					    				showLabel="N" 
					    				iconClass="dijitIconClear"></gs:button>    												
																						
					    			<gs:button name="QCHARTS_PROG002D0002Q_delete" id="QCHARTS_PROG002D0002Q_delete" onClick="QCHARTS_PROG002D0002Q_delete();" 
					    				label="${action.getText('QCHARTS_PROG002D0002Q_delete')}" 
					    				showLabel="N"
					    				iconClass="dijitIconDelete"></gs:button>      
		    						
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0002Q_mdxOid"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0002Q_name"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0002Q_olapConfigOid"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0002Q_olapCatalogOid"></gs:inputfieldNoticeMsgLabel>
		    						<gs:inputfieldNoticeMsgLabel id="QCHARTS_PROG002D0002Q_expression"></gs:inputfieldNoticeMsgLabel>
		    																							
								</td>
							</tr>
							<tr>
								<td width="100%" align="left" height="25px">
								
									<s:property value="getText('QCHARTS_PROG002D0002Q_mdxOid')"/>
									<gs:select name="QCHARTS_PROG002D0002Q_mdxOid" dataSource="mdxMap" id="QCHARTS_PROG002D0002Q_mdxOid" onChange="QCHARTS_PROG002D0002Q_getMdxHistory();" ></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_mdxOid'">
    									Select history data.
									</div>   									 
									&nbsp;		
						    									
									<s:property value="getText('QCHARTS_PROG002D0002Q_name')"/>
									<gs:textBox name="QCHARTS_PROG002D0002Q_name" id="QCHARTS_PROG002D0002Q_name" value="" width="200" maxlength="100"></gs:textBox>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_name'">
    									Input name.
									</div> 
																		
									&nbsp;
									<s:property value="getText('QCHARTS_PROG002D0002Q_showDimensionTitle')"/>
									<input id="QCHARTS_PROG002D0002Q_showDimensionTitle" name="QCHARTS_PROG002D0002Q_showDimensionTitle" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_showDimensionTitle'">
    									enable show Dimension-Title on report.
									</div> 									
									&nbsp;
									<s:property value="getText('QCHARTS_PROG002D0002Q_showParentMembers')"/>
									<input id="QCHARTS_PROG002D0002Q_showParentMembers" name="QCHARTS_PROG002D0002Q_showParentMembers" data-dojo-type="dijit/form/CheckBox" value="true" checked="checked" />
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_showParentMembers'">
    									enable show Parent-Members on report.
									</div>
																		
								</td>
							</tr>
							<tr>
								<td width="100%" align="left" height="25px">
								
									<s:property value="getText('QCHARTS_PROG002D0002Q_olapConfigOid')"/>
									<gs:select name="QCHARTS_PROG002D0002Q_olapConfigOid" dataSource="configMap" id="QCHARTS_PROG002D0002Q_olapConfigOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_olapConfigOid'">
    									Select datasource config.
									</div> 									
						    		&nbsp;		
						    																	
									<s:property value="getText('QCHARTS_PROG002D0002Q_olapCatalogOid')"/>
									<gs:select name="QCHARTS_PROG002D0002Q_olapCatalogOid" dataSource="catalogMap" id="QCHARTS_PROG002D0002Q_olapCatalogOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_olapCatalogOid'">
    									Select Mondrian-Catalog.<BR/>the catalog file need work with success datasource config.
									</div> 	
																											
								</td>
							</tr>							
							<tr>
								<td width="100%" align="left" height="125px">
								
									<gs:label text="${action.getText('QCHARTS_PROG002D0002Q_expression')}" id="QCHARTS_PROG002D0002Q_expression"></gs:label>
									<br/>
									<textarea id="QCHARTS_PROG002D0002Q_expression" name="QCHARTS_PROG002D0002Q_expression" data-dojo-type="dijit/form/Textarea" rows="6" cols="120" style="width:960px;height:90px;max-height:100px"></textarea>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG002D0002Q_expression'">
    									MultiDimensional eXpressions.<BR/>
    									Example:<BR/>
    									
    									<hr size="1">
    									<font size='2'>
										SELECT <BR/>
										&nbsp;&nbsp;{ [Measures].[Store Sales] } ON COLUMNS,<BR/> 
										&nbsp;&nbsp;{ [Date].[2002], [Date].[2003] } ON ROWS<BR/> 
										FROM Sales WHERE ( [Store].[USA].[CA] )
										</font>
										<hr size="1">
										
										<BR/> 									
    									Reference: <a href="https://en.wikipedia.org/wiki/MultiDimensional_eXpressions">MultiDimensional eXpressions</a>
									</div> 	
																		
								</td>
							</tr>
														
						</table>							
					
					</div>
				</div>
			</td>
		</tr>
	</table>				
	
	<div id="QCHARTS_PROG002D0002Q_content"></div>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	