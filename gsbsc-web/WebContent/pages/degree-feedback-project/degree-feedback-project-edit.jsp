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

var BSC_PROG005D0001E_fieldsId = new Object();
BSC_PROG005D0001E_fieldsId['name'] 			= 'BSC_PROG005D0001E_name';
BSC_PROG005D0001E_fieldsId['year'] 			= 'BSC_PROG005D0001E_year';
BSC_PROG005D0001E_fieldsId['description']	= 'BSC_PROG005D0001E_description';
BSC_PROG005D0001E_fieldsId['ownerOids']		= 'BSC_PROG005D0001E_ownerOids_noticeMessageOnly';
BSC_PROG005D0001E_fieldsId['raterOids']		= 'BSC_PROG005D0001E_raterOids_noticeMessageOnly';
BSC_PROG005D0001E_fieldsId['levelData']		= 'BSC_PROG005D0001E_levelData_noticeMessageOnly';
BSC_PROG005D0001E_fieldsId['itemData']		= 'BSC_PROG005D0001E_itemData_noticeMessageOnly';

function BSC_PROG005D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG005D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG005D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG005D0001E_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG005D0001E_fieldsId);
		return;
	}	
}

function BSC_PROG005D0001E_startProcessSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG005D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG005D0001E_fieldsId);		
		return;
	}	
	//刷新畫面
	BSC_PROG005D0001E_TabRefresh();
}

function BSC_PROG005D0001E_reloadOwnerName() {
	BSC_PROG005D0001E_reloadEmployeeAppendName( 'BSC_PROG005D0001E_owner', 'BSC_PROG005D0001E_ownerName' );
}

function BSC_PROG005D0001E_reloadRaterName() {
	BSC_PROG005D0001E_reloadEmployeeAppendName( 'BSC_PROG005D0001E_rater', 'BSC_PROG005D0001E_raterName' );
}

function BSC_PROG005D0001E_reloadEmployeeAppendName(keyFieldId, nameFieldId) {
	var appendOid = dojo.byId( keyFieldId ).value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId( keyFieldId ).value = '';
		dojo.byId( nameFieldId ).innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId( keyFieldId ).value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId( nameFieldId ).innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}

function BSC_PROG005D0001E_clear() {
	setFieldsBackgroundDefault(BSC_PROG005D0001E_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG005D0001E_fieldsId);
	dijit.byId('BSC_PROG005D0001E_name').set("value", "");
	dijit.byId('BSC_PROG005D0001E_year').set("value", "");
	dijit.byId('BSC_PROG005D0001E_description').set("value", "");	
	BSC_PROG005D0001E_clearOwner();
	BSC_PROG005D0001E_clearRater();
	BSC_PROG005D0001E_levelData = []; 
	BSC_PROG005D0001E_itemData = []; 
	dojo.byId('BSC_PROG005D0001E_levelData').innerHTML = '';
	dojo.byId('BSC_PROG005D0001E_itemData').innerHTML = '';	
}

function BSC_PROG005D0001E_clearOwner() {
	dojo.byId('BSC_PROG005D0001E_owner').value = '';
	dojo.byId('BSC_PROG005D0001E_ownerName').innerHTML = '';	
}

function BSC_PROG005D0001E_clearRater() {
	dojo.byId('BSC_PROG005D0001E_rater').value = '';
	dojo.byId('BSC_PROG005D0001E_raterName').innerHTML = '';		
}

var BSC_PROG005D0001E_levelData = []; // 評分等級資料要用
var BSC_PROG005D0001E_itemData = []; // 項目資料要用

<s:if test="degreeFeedbackLevels!=null && degreeFeedbackLevels.size!=0">
<s:iterator value="degreeFeedbackLevels">
BSC_PROG005D0001E_levelData.push( {name: '<s:property value="name" escapeJavaScript="true"/>' , value: <s:property value="value" escapeJavaScript="true"/>} );
</s:iterator>
</s:if>

<s:if test="degreeFeedbackItems!=null && degreeFeedbackItems.size!=0">
<s:iterator value="degreeFeedbackItems">
BSC_PROG005D0001E_itemData.push( {name: '<s:property value="name" escapeJavaScript="true"/>', description: '<s:property value="description" escapeJavaScript="true"/>'} );
</s:iterator>
</s:if>

function BSC_PROG005D0001E_loadDiagram(taskId) {
	xhrSendParameter(
			'${basePath}/bsc.degreeFeedbackProjectLoadTaskDiagramAction.action', 
			{ 'fields.taskId' : taskId }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ( 'Y' == data.success ) {
					openCommonLoadUpload(
							'view', 
							data.uploadOid, 
							{ 
								"isDialog" 	: 	"Y",
								"title"		:	_getApplicationProgramNameById('${programId}'),
								"width"		:	800,
								"height"	:	600
							} 
					);
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
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG005D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<input type="hidden" name="BSC_PROG005D0001E_owner" id="BSC_PROG005D0001E_owner" value="${fields.ownerOids}" />
	<input type="hidden" name="BSC_PROG005D0001E_rater" id="BSC_PROG005D0001E_rater" value="${fields.raterOids}" />
	
	<table border="0" width="100%" height="650px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_name')}" id="BSC_PROG005D0001E_name" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_name"></gs:inputfieldNoticeMsgLabel>
    			<br/>
    			<gs:textBox name="BSC_PROG005D0001E_name" id="BSC_PROG005D0001E_name" value="degreeFeedbackProject.name" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001E_name'">
    				Input name.
				</div>       			
    		</td>    		
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_year')}" id="BSC_PROG005D0001E_year" requiredFlag="Y"></gs:label>
    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_year"></gs:inputfieldNoticeMsgLabel>
    			<br/>
				<input id="BSC_PROG005D0001E_year" name="BSC_PROG005D0001E_year" data-dojo-type="dojox.form.YearTextBox" 
					maxlength="4"  type="text" data-dojo-props='style:"width: 80px;" ' value="${degreeFeedbackProject.year}"/>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001E_year'">
					Select year.
				</div>	   			
    		</td>    		
    	</tr>  	    	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<gs:label text="${action.getText('BSC_PROG005D0001E_description')}" id="BSC_PROG005D0001E_description"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_description"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG005D0001E_description" name="BSC_PROG005D0001E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"><s:property value="degreeFeedbackProject.description"/></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001E_description'">
    				Input description, the maximum allowed 500 characters.
				</div>		    	
		    </td>
		</tr>      	  
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_emplSelect_1')}" id="BSC_PROG005D0001E_emplSelect_1" requiredFlag="Y"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG005D0001E_emplSelect_1" id="BSC_PROG005D0001E_emplSelect_1" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG005D0001E_owner;BSC_PROG005D0001E_reloadOwnerName');
						}
					"></button>
				<button name="BSC_PROG005D0001E_emplClear_1" id="BSC_PROG005D0001E_emplClear_1" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001E_clearOwner();
						}
					"></button>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_ownerOids_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<span id="BSC_PROG005D0001E_ownerName"><s:property value="fields.ownerNames"/></span>	    			
    		</td>    
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_emplSelect_2')}" id="BSC_PROG005D0001E_emplSelect_2" requiredFlag="Y"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG005D0001E_emplSelect_2" id="BSC_PROG005D0001E_emplSelect_2" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG005D0001E_rater;BSC_PROG005D0001E_reloadRaterName');
						}
					"></button>
				<button name="BSC_PROG005D0001E_emplClear_2" id="BSC_PROG005D0001E_emplClear_2" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001E_clearRater();
						}
					"></button>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_raterOids_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<span id="BSC_PROG005D0001E_raterName"><s:property value="fields.raterNames"/></span>	    			
    		</td>    
    	</tr>     			
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_levelSettings')}" id="BSC_PROG005D0001E_levelSettings" requiredFlag="Y"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG005D0001E_levelSettings" id="BSC_PROG005D0001E_levelSettings" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG005D0001A_S00_DlgShow('BSC_PROG005D0001E_levelData');
						}
					"></button>
				<button name="BSC_PROG005D0001E_levelSettingsClear" id="BSC_PROG005D0001E_levelSettingsClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001E_levelData = [];
							dojo.byId('BSC_PROG005D0001E_levelData').innerHTML = '';
						}
					"></button>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_levelData_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<span id="BSC_PROG005D0001E_levelData"><s:property value="levelsLabel"/></span>						   			
    		</td>    
    	</tr>    	    	

		<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001E_itemSettings')}" id="BSC_PROG005D0001E_itemSettings" requiredFlag="Y"></gs:label>
    			&nbsp;&nbsp;
				<button name="BSC_PROG005D0001E_itemSettings" id="BSC_PROG005D0001E_itemSettings" data-dojo-type="dijit.form.Button" class="alt-info"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconFolderOpen',
						onClick:function(){ 
							BSC_PROG005D0001A_S01_DlgShow('BSC_PROG005D0001E_itemData');
						}
					"></button>
				<button name="BSC_PROG005D0001E_itemSettingsClear" id="BSC_PROG005D0001E_itemSettingsClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001E_itemData = [];
							dojo.byId('BSC_PROG005D0001E_itemData').innerHTML = '';
						}
					"></button>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG005D0001E_itemData_noticeMessageOnly"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<span id="BSC_PROG005D0001E_itemData"><s:property value="itemsLabel"/></span>					   			
    		</td>    
    	</tr>    	       		  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="150px" width="100%"  align="left">
    		
				<s:if test=" bpmTaskObj != null && bpmTaskObj.variables != null ">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props=" title: 'Current audit info' " open="true">
					<div data-dojo-type="dijit/layout/ContentPane" title="Audit info" data-dojo-props="selected:true">
						<div class="isa_info">
							<b>Now task:</b>&nbsp;<s:property value="bpmTaskObj.task.name" />
							<br/>
							<b>Before audit</b>
							<br/>
							&nbsp;&nbsp;&nbsp;<b>Confirm:</b>&nbsp;${bpmTaskObj.variables.confirm}									
							<br/>									
							&nbsp;&nbsp;&nbsp;<b>Assignee:</b>&nbsp;<s:property value="bpmTaskObj.task.assignee" />
							<br/>
							&nbsp;&nbsp;&nbsp;<b>Reason:</b><br/>
							&nbsp;&nbsp;&nbsp;<s:property value="bpmTaskObj.variables.reason" escapeHtml="true"/>
						</div>
					</div>
				</div>
				</s:if>    	    		
    		
    		
    			<gs:button name="BSC_PROG005D0001E_update" id="BSC_PROG005D0001E_update" onClick="BSC_PROG005D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.degreeFeedbackProjectUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{     						
    						'fields.oid'			: '${degreeFeedbackProject.oid}',
    						'fields.name'			: dijit.byId('BSC_PROG005D0001E_name').get('value'),
    						'fields.year'			: dijit.byId('BSC_PROG005D0001E_year').get('value'),
    						'fields.description'	: dijit.byId('BSC_PROG005D0001E_description').get('value'),
    						'fields.ownerOids'		: dojo.byId('BSC_PROG005D0001E_owner').value,
    						'fields.raterOids'		: dojo.byId('BSC_PROG005D0001E_rater').value,
    						'fields.levelData'		: JSON.stringify( { 'data' : BSC_PROG005D0001E_levelData } ),
    						'fields.itemData'		: JSON.stringify( { 'data' : BSC_PROG005D0001E_itemData } )
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG005D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG005D0001E_update')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="BSC_PROG005D0001E_clear" id="BSC_PROG005D0001E_clear" onClick="BSC_PROG005D0001E_clear();" 
    				label="${action.getText('BSC_PROG005D0001E_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>   
    			&nbsp;&nbsp;&nbsp;&nbsp;
    			<gs:button name="BSC_PROG005D0001E_startProcess" id="BSC_PROG005D0001E_startProcess" onClick="BSC_PROG005D0001E_startProcess();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.degreeFeedbackProjectStartProcessAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{     						
    						'fields.oid'			: '${degreeFeedbackProject.oid}'
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG005D0001E_startProcessSuccess(data);" 
    				programId="${programId}"
    				label="Start apply" 
    				iconClass="dijitIconSave"
    				confirmDialogMode="Y"
    				confirmDialogTitle=""
    				confirmDialogMsg="Start apply?"       				
    				cssClass="alt-warning"></gs:button> 
    				
				<button name="BSC_PROG005D0001E_confirmDlgBtn" id="BSC_PROG005D0001E_confirmDlgBtn" data-dojo-type="dijit.form.Button"
					
					<s:if test=" bpmTaskObj == null || \"Y\" != bpmTaskObj.allowApproval "> disabled="disabled" </s:if>
					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							BSC_PROG005D0001A_S03_DlgShow('${degreeFeedbackProject.oid};${bpmTaskObj.task.id}');
						}
					"
					class="alt-primary">Confirm audit</button>
				
				<button name="BSC_PROG005D0001E_diagramDlgBtn" id="BSC_PROG005D0001E_diagramDlgBtn" data-dojo-type="dijit.form.Button"
					
					<s:if test=" bpmTaskObj == null "> disabled="disabled" </s:if>
					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							BSC_PROG005D0001E_loadDiagram('${bpmTaskObj.task.id}');
						}
					"
					class="alt-primary">View audit diagram</button>		    				
    				
    				   				    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
