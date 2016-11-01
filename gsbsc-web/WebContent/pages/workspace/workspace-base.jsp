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

function BSC_PROG001D0005Q_query() {
	
	var childTabId = '';
	var childTabTitle = '';
	
	if ('all' == dijit.byId("BSC_PROG001D0005Q_workspaceOid").get("value") || '' == dijit.byId("BSC_PROG001D0005Q_workspaceOid").get("value") ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "<s:property value="getText('BSC_PROG001D0005Q_workspaceOid_msg1')" escapeJavaScript="true"/>", function(){}, "Y");
		return;
	}
	if ('all' == dijit.byId("BSC_PROG001D0005Q_visionOid").get("value") || '' == dijit.byId("BSC_PROG001D0005Q_visionOid").get("value") ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "<s:property value="getText('BSC_PROG001D0005Q_visionOid_msg1')" escapeJavaScript="true"/>", function(){}, "Y");
		return;
	}
	childTabId = 'BSC_PROG001D0005Q_TabContainerChildTab' + dijit.byId("BSC_PROG001D0005Q_workspaceOid").get("value");
	childTabTitle = dijit.byId("BSC_PROG001D0005Q_workspaceOid").get("displayedValue") + " - " + dijit.byId("BSC_PROG001D0005Q_yearHorizontalSlider").get("value");
	
	xhrSendParameter(
			'${basePath}/bsc.kpiReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 					: 	dijit.byId("BSC_PROG001D0005Q_visionOid").get("value"),
				'fields.startYearDate'				:	dijit.byId("BSC_PROG001D0005Q_yearHorizontalSlider").get("value"),
				'fields.endYearDate'				:	dijit.byId("BSC_PROG001D0005Q_yearHorizontalSlider").get("value"),
				'fields.startDate'					:	'',
				'fields.endDate'					:	'',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	'',
				'fields.measureDataEmployeeOid'		:	'',
				'fields.frequency'					:	'6',
				'fields.saveResultJson'					: 'Y'
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				if ( data.uploadOid != null && data.uploadOid != "" ) {
					// 只有 LineChart 需要 year 參數, 其它只要 workspaceOid, uploadOid, visionOid 之後會淘汰 LineChart, 因為LineChart它的查詢條件與其它Chart不一樣
					viewPage.addOrUpdateContentPane(
							'BSC_PROG001D0005Q_TabContainer', 
							childTabId, 
							childTabTitle, 
							'${basePath}/bsc.loadContentBody.action?fields.workspaceOid=' + dijit.byId("BSC_PROG001D0005Q_workspaceOid").get("value") 
									+ '&fields.visionOid=' + dijit.byId("BSC_PROG001D0005Q_visionOid").get("value") 
									+ '&fields.year=' + dijit.byId("BSC_PROG001D0005Q_yearHorizontalSlider").get("value")
									+ '&fields.uploadOid=' + data.uploadOid,
							true, 
							true,
							true);
				}	else {
					alert('Error for workspace!');
				}			
				
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
			<td width="100%" align="center" height="35%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG001D0005Q_options')" escapeJavaScript="true"/>' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:99%;height:60px">
					
						<table border="0" width="100%" cellspacing="0" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);" >
							<tr valign="top">
								<td width="65%" align="left" height="50px" >	
									
									<gs:label text="${action.getText('BSC_PROG001D0005Q_workspaceOid')}" id="BSC_PROG001D0005Q_workspaceOid"></gs:label>
									<gs:select name="BSC_PROG001D0005Q_workspaceOid" dataSource="workspaceMap" id="BSC_PROG001D0005Q_workspaceOid" value="fields.workspaceOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0005Q_workspaceOid'">
					    				Select workspace.
									</div> 									
						    		&nbsp;		    
						    							
						    																
									<gs:label text="${action.getText('BSC_PROG001D0005Q_visionOid')}" id="BSC_PROG001D0005Q_visionOid"></gs:label> 
									<gs:select name="BSC_PROG001D0005Q_visionOid" dataSource="visionMap" id="BSC_PROG001D0005Q_visionOid" value="fields.visionOid"></gs:select>
									<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0005Q_visionOid'">
					    				Select vision.
									</div> 																		
						    		&nbsp;		    															
								
								</td>
								<td width="25%" align="left" height="45px" >
									
									<div id="BSC_PROG001D0005Q_yearHorizontalSlider"
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
																	
								</td>
								<td align="left" height="10%" >

									<button id="BSC_PROG001D0005Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG001D0005Q_query();
											}"
											class="alt-primary"><s:property value="getText('BSC_PROG001D0005Q_btnQuery')"/></button>						
															
								</td>
								
								
							</tr>
							
						</table>								
					
		    		</div>		    		
		    	</div>			
			</td>
		</tr>
	</table>					

    <div data-dojo-type="dijit/layout/TabContainer" style="width: 100%; height: 100%;" data-dojo-props="region:'center', tabStrip:true" id="BSC_PROG001D0005Q_TabContainer">
    </div>	

<script type="text/javascript">${programId}_page_message();</script>
<script type="text/javascript">setTimeout("BSC_PROG001D0005Q_query();", 1000);</script>	
</body>
</html>
