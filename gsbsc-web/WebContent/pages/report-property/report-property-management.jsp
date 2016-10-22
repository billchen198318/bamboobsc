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
BSC_PROG004D0001Q_fieldsId['scoreLabel'] 			= 'BSC_PROG004D0001Q_scoreLabel';
BSC_PROG004D0001Q_fieldsId['weightLabel'] 			= 'BSC_PROG004D0001Q_weightLabel';
BSC_PROG004D0001Q_fieldsId['maxLabel'] 				= 'BSC_PROG004D0001Q_maxLabel';
BSC_PROG004D0001Q_fieldsId['targetLabel'] 			= 'BSC_PROG004D0001Q_targetLabel';
BSC_PROG004D0001Q_fieldsId['minLabel'] 				= 'BSC_PROG004D0001Q_minLabel';
BSC_PROG004D0001Q_fieldsId['managementLabel'] 		= 'BSC_PROG004D0001Q_managementLabel';
BSC_PROG004D0001Q_fieldsId['calculationLabel'] 		= 'BSC_PROG004D0001Q_calculationLabel';
BSC_PROG004D0001Q_fieldsId['unitLabel'] 			= 'BSC_PROG004D0001Q_unitLabel';
BSC_PROG004D0001Q_fieldsId['formulaLabel'] 			= 'BSC_PROG004D0001Q_formulaLabel';
BSC_PROG004D0001Q_fieldsId['organizationLabel'] 	= 'BSC_PROG004D0001Q_organizationLabel';
BSC_PROG004D0001Q_fieldsId['employeeLabel'] 		= 'BSC_PROG004D0001Q_employeeLabel';

function BSC_PROG004D0001Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG004D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG004D0001Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG004D0001Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG004D0001Q_fieldsId);
		return;
	}	
	${programId}_TabRefresh();
}

function BSC_PROG004D0001Q_clear() {
	setFieldsBackgroundDefault(BSC_PROG004D0001Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG004D0001Q_fieldsId);	
	dijit.byId('BSC_PROG004D0001Q_perspectiveTitle').set("value", "");	
	dijit.byId('BSC_PROG004D0001Q_objectiveTitle').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_kpiTitle').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_classNote').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_colorPicker1').set("value", "#000000");
	dijit.byId('BSC_PROG004D0001Q_colorPicker2').set("value", "#ffffff");
	dijit.byId('BSC_PROG004D0001Q_scoreLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_weightLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_maxLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_targetLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_minLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_managementLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_calculationLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_unitLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_formulaLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_organizationLabel').set("value", "");
	dijit.byId('BSC_PROG004D0001Q_employeeLabel').set("value", "");
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
		saveJsMethod="BSC_PROG004D0001Q_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<table border="0" height="1050px">
		<tr valign="top">
			<td width="100%" align="left" height="30%">
			    <table border="0" width="800px" height="150px">
					<tr valign="top">
						<td width="400px" align="left">
							<gs:label text="${action.getText('BSC_PROG004D0001Q_colorPicker1')}" id="BSC_PROG004D0001Q_colorPicker1"></gs:label>
							<div data-dojo-type="dojox.widget.ColorPicker" value="${fields.fontColor}" id="BSC_PROG004D0001Q_colorPicker1"></div>		
						</td>
						<td width="400px" align="left">
							<gs:label text="${action.getText('BSC_PROG004D0001Q_colorPicker2')}" id="BSC_PROG004D0001Q_colorPicker2"></gs:label>
							<div data-dojo-type="dojox.widget.ColorPicker" value="${fields.backgroundColor}" id="BSC_PROG004D0001Q_colorPicker2"></div>	
						</td>
					</tr>		    		
			    </table>			
			</td>
		</tr>
		<tr>			
			<td width="100%" height="50px" align="left">
				<gs:label text="${action.getText('BSC_PROG004D0001Q_perspectiveTitle')}" id="BSC_PROG004D0001Q_perspectiveTitle" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_perspectiveTitle"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_perspectiveTitle" id="BSC_PROG004D0001Q_perspectiveTitle" value="fields.perspectiveTitle" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_perspectiveTitle'">
    				Input Report perspectives label text.
				</div> 				
			</td>			
		</tr>
		<tr>			
			<td width="100%" height="50px" align="left" >
				<gs:label text="${action.getText('BSC_PROG004D0001Q_objectiveTitle')}" id="BSC_PROG004D0001Q_objectiveTitle" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_objectiveTitle"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_objectiveTitle" id="BSC_PROG004D0001Q_objectiveTitle" value="fields.objectiveTitle" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_objectiveTitle'">
    				Input Report strategy of objectives label text.
				</div> 				
			</td>			
		</tr>	
		<tr>			
			<td width="100%" height="50px" align="left" >
				<gs:label text="${action.getText('BSC_PROG004D0001Q_kpiTitle')}" id="BSC_PROG004D0001Q_kpiTitle" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_kpiTitle"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_kpiTitle" id="BSC_PROG004D0001Q_kpiTitle" value="fields.kpiTitle" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_kpiTitle'">
    				Input Report KPI label text.
				</div> 					
			</td>			
		</tr>	
		<tr>		    
		    <td height="125px" width="100%"  align="left">
		    	<gs:label text="${action.getText('BSC_PROG004D0001Q_classNote')}" id="BSC_PROG004D0001Q_classNote" requiredFlag="Y"></gs:label>
		    	<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_classNote"></gs:inputfieldNoticeMsgLabel>
		    	<br/>
		    	<textarea id="BSC_PROG004D0001Q_classNote" name="BSC_PROG004D0001Q_classNote" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${fields.classNote}</textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_classNote'">
    				Input Personal Report class note text, the maximum allowed 100 characters.
				</div> 		    		
		    </td>
		</tr>     		
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Score label" id="BSC_PROG004D0001Q_scoreLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_scoreLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_scoreLabel" id="BSC_PROG004D0001Q_scoreLabel" value="fields.scoreLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_scoreLabel'">
    				Input Report score label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Weight label" id="BSC_PROG004D0001Q_weightLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_weightLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_weightLabel" id="BSC_PROG004D0001Q_weightLabel" value="fields.weightLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_weightLabel'">
    				Input Report weight label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Max label" id="BSC_PROG004D0001Q_maxLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_maxLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_maxLabel" id="BSC_PROG004D0001Q_maxLabel" value="fields.maxLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_maxLabel'">
    				Input Report max label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Target label" id="BSC_PROG004D0001Q_targetLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_targetLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_targetLabel" id="BSC_PROG004D0001Q_targetLabel" value="fields.targetLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_targetLabel'">
    				Input Report target label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Min label" id="BSC_PROG004D0001Q_minLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_minLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_minLabel" id="BSC_PROG004D0001Q_minLabel" value="fields.minLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_minLabel'">
    				Input Report min label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Management label" id="BSC_PROG004D0001Q_managementLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_managementLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_managementLabel" id="BSC_PROG004D0001Q_managementLabel" value="fields.managementLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_managementLabel'">
    				Input Report management label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Calculation label" id="BSC_PROG004D0001Q_calculationLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_calculationLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_calculationLabel" id="BSC_PROG004D0001Q_calculationLabel" value="fields.calculationLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_calculationLabel'">
    				Input Report calculation label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Unit label" id="BSC_PROG004D0001Q_unitLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_unitLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_unitLabel" id="BSC_PROG004D0001Q_unitLabel" value="fields.unitLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_unitLabel'">
    				Input Report unit label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Formula label" id="BSC_PROG004D0001Q_formulaLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_formulaLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_formulaLabel" id="BSC_PROG004D0001Q_formulaLabel" value="fields.formulaLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_formulaLabel'">
    				Input Report formula label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Organization label" id="BSC_PROG004D0001Q_organizationLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_organizationLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_organizationLabel" id="BSC_PROG004D0001Q_organizationLabel" value="fields.organizationLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_organizationLabel'">
    				Input Report organization label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" height="50px" align="left" >
				<gs:label text="Employee label" id="BSC_PROG004D0001Q_employeeLabel" requiredFlag="Y"></gs:label>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0001Q_employeeLabel"></gs:inputfieldNoticeMsgLabel>
				<br/>
				<gs:textBox name="BSC_PROG004D0001Q_employeeLabel" id="BSC_PROG004D0001Q_employeeLabel" value="fields.employeeLabel" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0001Q_employeeLabel'">
    				Input Report employee label text.
				</div> 
			</td>
		</tr>
		<tr>
			<td width="100%" align="left" height="25px">
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
	   						'fields.classNote'				:	dijit.byId('BSC_PROG004D0001Q_classNote').get('value'),
	   						'fields.scoreLabel'				:	dijit.byId('BSC_PROG004D0001Q_scoreLabel').get('value'),
	   						'fields.weightLabel'			:	dijit.byId('BSC_PROG004D0001Q_weightLabel').get('value'),
	   						'fields.maxLabel'				:	dijit.byId('BSC_PROG004D0001Q_maxLabel').get('value'),
	   						'fields.targetLabel'			:	dijit.byId('BSC_PROG004D0001Q_targetLabel').get('value'),
	   						'fields.minLabel'				:	dijit.byId('BSC_PROG004D0001Q_minLabel').get('value'),
	   						'fields.managementLabel'		:	dijit.byId('BSC_PROG004D0001Q_managementLabel').get('value'),
	   						'fields.calculationLabel'		:	dijit.byId('BSC_PROG004D0001Q_calculationLabel').get('value'),
	   						'fields.unitLabel'				:	dijit.byId('BSC_PROG004D0001Q_unitLabel').get('value'),
	   						'fields.formulaLabel'			:	dijit.byId('BSC_PROG004D0001Q_formulaLabel').get('value'),
	   						'fields.organizationLabel'		:	dijit.byId('BSC_PROG004D0001Q_organizationLabel').get('value'),
	   						'fields.employeeLabel'			:	dijit.byId('BSC_PROG004D0001Q_employeeLabel').get('value')
	   					}
					"
	   				errorFn=""
    				loadFn="BSC_PROG004D0001Q_saveSuccess(data);" 
	    			programId="${programId}"
	    			label="${action.getText('BSC_PROG004D0001Q_save')}" 
	    			iconClass="dijitIconSave"
	    			cssClass="alt-primary"></gs:button> 			
    			<gs:button name="BSC_PROG004D0001Q_clear" id="BSC_PROG004D0001Q_clear" onClick="BSC_PROG004D0001Q_clear();" 
    				label="${action.getText('BSC_PROG004D0001Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    				    					
			</td>
		</tr>			
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	