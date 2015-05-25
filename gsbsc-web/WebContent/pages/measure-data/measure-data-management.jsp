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

#BSC_PROG002D0005Q_kpiTree {
    /*height need calculate*/ 
    padding-left: 4px;
    padding-right: 4px;
    overflow-y: auto; 
    overflow-x: hidden;
    font-size: 12px;
}

</style>

<script type="text/javascript">

var BSC_PROG002D0005Q_fieldsId = new Object();
BSC_PROG002D0005Q_fieldsId['dataFor'] 			= 'BSC_PROG002D0005Q_dataForOpt';
BSC_PROG002D0005Q_fieldsId['frequency'] 		= 'BSC_PROG002D0005Q_frequencyOpt';
BSC_PROG002D0005Q_fieldsId['organizationOid'] 	= 'BSC_PROG002D0005Q_organizationOid';
BSC_PROG002D0005Q_fieldsId['employeeOid'] 		= 'BSC_PROG002D0005Q_employeeOid';


/* 產生KPI-TREE */
function BSC_PROG002D0005Q_getKpiTree() {
	
	dijit.byId("BSC_PROG002D0005Q_query").set("disabled", true);
	
	var store = new dojo.data.ItemFileWriteStore({
		url: "${basePath}/bsc.kpiTreeJsonAction.action"
	});

	var treeModel = new dijit.tree.ForestStoreModel({
		store: store,
		query: {"type": "parent"},
		rootId: "root",
		rootLabel: "KPIs",
		childrenAttrs: ["children"]
	});
	
	var treeObject = new dijit.Tree({
		model: treeModel,
        _createTreeNode: function(args) {
            var tnode = new dijit._TreeNode(args);
            tnode.labelNode.innerHTML = args.label;
            return tnode;
        }    	
	}, "BSC_PROG002D0005Q_kpiTree");

	treeObject.on("click", function(object){
		BSC_PROG002D0005Q_clear();
		var oid = object.id;
		if (oid!=null && ''!=oid && 'root'!=oid && !(typeof oid=='undefined') && (oid+'').indexOf("NOT-KPI")==-1 ) {
			BSC_PROG002D0005Q_getKpiData(oid);
			dijit.byId("BSC_PROG002D0005Q_query").set("disabled", false);
		} else {
			dijit.byId("BSC_PROG002D0005Q_query").set("disabled", true);
		}
	}, true);
	
	treeObject.expandAll();
	
	/*
	<s:if test="null != kpiOid && \"\" != kpiOid && \" \" != kpiOid ">
	getHtmlContentData('${kpiOid}');
	</s:if>
	*/
	
}

function BSC_PROG002D0005Q_getKpiData(oid) {
	xhrSendParameter(
			'${basePath}/bsc.commonGetKpiDataAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data==null) {
					alert('Data error!');
					return;
				}
				if (data!=null && data.success != 'Y' ) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.byId("BSC_PROG002D0005Q_kpiOid").value = data.kpi.oid;
				dojo.byId("BSC_PROG002D0005Q_orgaMeasureSeparate").value = data.kpi.orgaMeasureSeparate;
				dojo.byId("BSC_PROG002D0005Q_userMeasureSeparate").value = data.kpi.userMeasureSeparate;
				alertDialog(_getApplicationProgramNameById('${programId}'), 'Maintain kpi: ' + data.kpi.name, function(){}, data.success);
				BSC_PROG002D0005Q_handlerOptions(data.kpi);
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG002D0005Q_handlerOptions(kpi) {
	clearSelectItems(true, 'BSC_PROG002D0005Q_organizationOid');
	clearSelectItems(true, 'BSC_PROG002D0005Q_employeeOid');
	dijit.byId("BSC_PROG002D0005Q_dataForOpt").set("value", "all");
	xhrSendParameter(
			'${basePath}/bsc.commonGetKpiOrgaAndEmplAction.action', 
			{ 'fields.oid' : kpi.oid }, 
			'json', 
			_gscore_dojo_ajax_timeout, 
			_gscore_dojo_ajax_sync, 
			true, 
			function(data){
    			if ('Y'==data.success) {
    				
    				var orgaSelect=dijit.byId('BSC_PROG002D0005Q_organizationOid');
    				orgaSelect.removeOption(orgaSelect.getOptions());	
    				for (var i=0; data.kpiOrga!=null && i<data.kpiOrga.length; i++) {
    					orgaSelect.addOption( 
    							{ 
    								label: data.kpiOrga[i].value, 
    								value: data.kpiOrga[i].key 
    							} 
    					);		
    				}
    				
    				var emplSelect=dijit.byId('BSC_PROG002D0005Q_employeeOid');
    				emplSelect.removeOption(emplSelect.getOptions());	
    				for (var i=0; data.kpiEmpl!=null && i<data.kpiEmpl.length; i++) {
    					emplSelect.addOption( 
    							{ 
    								label: data.kpiEmpl[i].value, 
    								value: data.kpiEmpl[i].key 
    							} 
    					);		
    				}    				
    				
    			}			
			}, 
			function(error){
				alert(error);
			}
	);	
}

function BSC_PROG002D0005Q_setDataForValue() {
	BSC_PROG002D0005Q_clearContent();
	var value = dijit.byId("BSC_PROG002D0005Q_dataForOpt").get("value");
	if ('Y' != dojo.byId("BSC_PROG002D0005Q_orgaMeasureSeparate").value && 'organization' == value ) {
		dijit.byId("BSC_PROG002D0005Q_dataForOpt").set("value", "all");
		value = 'all';
	}
	if ('Y' != dojo.byId("BSC_PROG002D0005Q_userMeasureSeparate").value && 'employee' == value ) {
		dijit.byId("BSC_PROG002D0005Q_dataForOpt").set("value", "all");
		value = 'all';
	}	
	dijit.byId('BSC_PROG002D0005Q_organizationOid').set('readOnly', true);
	dijit.byId('BSC_PROG002D0005Q_employeeOid').set('readOnly', true);
	dijit.byId('BSC_PROG002D0005Q_organizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0005Q_employeeOid').set("value", _gscore_please_select_id);		
	if ('organization' == value) {
		dijit.byId('BSC_PROG002D0005Q_organizationOid').set('readOnly', false);
		dijit.byId('BSC_PROG002D0005Q_organizationOid').set("value", _gscore_please_select_id);
	}
	if ('employee' == value) {
		dijit.byId('BSC_PROG002D0005Q_employeeOid').set('readOnly', false);
		dijit.byId('BSC_PROG002D0005Q_employeeOid').set("value", _gscore_please_select_id);
	}	
}

function BSC_PROG002D0005Q_renderBodySuccess(data) {
	dojo.byId("BSC_PROG002D0005Q_dateStatus").value = "0";
	setFieldsBackgroundDefault(BSC_PROG002D0005Q_fieldsId);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0005Q_fieldsId);
		alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
		return;
	}	
	//dojo.byId("BSC_PROG002D0005Q_content").innerHTML = data.body;
	dojo.html.set(dojo.byId("BSC_PROG002D0005Q_content"), data.body, {extractContent: true, parseContent: true});
	dojo.byId("BSC_PROG002D0005Q_date").value = data.dateValue; // 日期
}

function BSC_PROG002D0005Q_saveSuccess(data) {
	dojo.byId("BSC_PROG002D0005Q_dateStatus").value = "0";
	setFieldsBackgroundDefault(BSC_PROG002D0005Q_fieldsId);	
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0005Q_fieldsId);
		return;
	}	
	BSC_PROG002D0005Q_renderBody(); // 2015-04-20 add reload input measure-data!
}

function BSC_PROG002D0005Q_prevCalendar() {
	dojo.byId("BSC_PROG002D0005Q_dateStatus").value = "-1";
	BSC_PROG002D0005Q_renderBody();
}

function BSC_PROG002D0005Q_nextCalendar() {
	dojo.byId("BSC_PROG002D0005Q_dateStatus").value = "1";
	BSC_PROG002D0005Q_renderBody();
}

function BSC_PROG002D0005Q_clear() {
	dojo.byId("BSC_PROG002D0005Q_date").value = "${nowDate2}";
	dijit.byId("BSC_PROG002D0005Q_dataForOpt").set("value", "all");
	dijit.byId("BSC_PROG002D0005Q_frequencyOpt").set("value", "day");
	//clearSelectItems(true, 'BSC_PROG002D0005Q_organizationOid');
	//clearSelectItems(true, 'BSC_PROG002D0005Q_employeeOid');	
	dijit.byId('BSC_PROG002D0005Q_organizationOid').set("readOnly", true);
	dijit.byId('BSC_PROG002D0005Q_employeeOid').set("readOnly", true);
	dijit.byId('BSC_PROG002D0005Q_organizationOid').set("value", _gscore_please_select_id);
	dijit.byId('BSC_PROG002D0005Q_employeeOid').set("value", _gscore_please_select_id);
	BSC_PROG002D0005Q_clearContent();
}

function BSC_PROG002D0005Q_clearContent() {
	dojo.byId("BSC_PROG002D0005Q_content").innerHTML = "";
}

function BSC_PROG002D0005Q_exportCsv() {
	xhrSendParameter(
			'${basePath}/bsc.commonDoExportData2CsvAction.action', 
			{ 'fields.exportId' : 'bb_measure_data_001' }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
				if ('Y' != data.success) {
					return;
				}
				openCommonLoadUpload( 'download', data.oid, { } );
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

<body class="claro" bgcolor="#EEEEEE" >

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
		exportEnable="Y"
		exportJsMethod="BSC_PROG002D0005Q_exportCsv();"				
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>			
	
	<input type="hidden" name="BSC_PROG002D0005Q_dateStatus" id="BSC_PROG002D0005Q_dateStatus" value="0" /> <!-- 0當前, -1上一個, 1下一個 -->
	
	<table border="0" width="100%" height="100%">
		<tr>
			<td width="270px" valign="top">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'KPIs menu' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:270px;height:600px">
   						<div id="BSC_PROG002D0005Q_kpiTree"></div>
   					</div>		    						       								    					
		  		</div>		
		  						
			</td>
			<td valign="top" align="center" >
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:75px">
   						<table border="0" width="100%" height="100%">
   							<tr>							
   								<td width="20%" align="right"><font size='2'>Measure data for:</font></td>
   								<td width="30%" align="left">
   									<gs:select name="BSC_PROG002D0005Q_dataForOpt" dataSource="{ \"all\":\"All\", \"organization\":\"Organization\", \"employee\":\"Employee\" }" id="BSC_PROG002D0005Q_dataForOpt" onChange="BSC_PROG002D0005Q_setDataForValue();"></gs:select>
   								</td>
   								<td width="20%" align="right"><font size='2'>Frequency:</font></td>
   								<td width="30%" align="left">
   									<gs:select name="BSC_PROG002D0005Q_frequencyOpt" dataSource="frequencyMap" id="BSC_PROG002D0005Q_frequencyOpt" onChange="BSC_PROG002D0005Q_clearContent();"></gs:select>
   								</td>   								
   							</tr>   						
   							<tr>							
   								<td width="20%" align="right"><font size='2'>Organization:</font></td>
   								<td width="30%" align="left">
   									<gs:select name="BSC_PROG002D0005Q_organizationOid" dataSource="organizationMap" id="BSC_PROG002D0005Q_organizationOid" readonly="Y" onChange="BSC_PROG002D0005Q_clearContent();"></gs:select>
   								</td>
   								<td width="20%" align="right"><font size='2'>Employee:</font></td>
   								<td width="30%" align="left">
   									<gs:select name="BSC_PROG002D0005Q_employeeOid" dataSource="employeeMap" id="BSC_PROG002D0005Q_employeeOid" readonly="Y" onChange="BSC_PROG002D0005Q_clearContent();"></gs:select>
   								</td>   								
   							</tr>
   							<tr>
   								<td colspan="6" align="center">
					    			<gs:button name="BSC_PROG002D0005Q_query" id="BSC_PROG002D0005Q_query" onClick="BSC_PROG002D0005Q_renderBody();"
					    				handleAs="json"
					    				sync="N"
					    				xhrUrl="${basePath}/bsc.measureDataCalendarQueryAction.action"
					    				parameterType="postData"
					    				xhrParameter=" 
					    					{ 
					    						'fields.oid'				:		dojo.byId('BSC_PROG002D0005Q_kpiOid').value,
					    						'fields.date'				:		dojo.byId('BSC_PROG002D0005Q_date').value,
					    						'fields.dateStatus'			:		dojo.byId('BSC_PROG002D0005Q_dateStatus').value,
					    						'fields.dataFor'			:		dijit.byId('BSC_PROG002D0005Q_dataForOpt').get('value'),
					    						'fields.frequency'			:		dijit.byId('BSC_PROG002D0005Q_frequencyOpt').get('value'),
					    						'fields.organizationOid'	:		dijit.byId('BSC_PROG002D0005Q_organizationOid').get('value'),
					    						'fields.employeeOid'		:		dijit.byId('BSC_PROG002D0005Q_employeeOid').get('value')
					    					} 
					    				"
					    				errorFn=""
					    				loadFn="BSC_PROG002D0005Q_renderBodySuccess(data)" 
					    				programId="${programId}"
					    				label="Query" 					    				
					    				iconClass="dijitIconSearch"></gs:button>
					    			<gs:button name="BSC_PROG002D0005Q_clear" id="BSC_PROG002D0005Q_clear" onClick="BSC_PROG002D0005Q_clear();" 
					    				label="Clear" 
					    				iconClass="dijitIconClear"></gs:button>  
					    			<gs:button name="BSC_PROG002D0005Q_save" id="BSC_PROG002D0005Q_save" onClick="BSC_PROG002D0005Q_save();"
					    				handleAs="json"
					    				sync="N"
					    				xhrUrl="${basePath}/bsc.measureDataSaveAction.action"
					    				parameterType="form"
					    				xhrParameter="BSC_PROG002D0005Q_measureDataForm"
					    				errorFn=""
					    				loadFn="BSC_PROG002D0005Q_saveSuccess(data);" 
					    				programId="${programId}"
					    				label="Save" 
					    				iconClass="dijitIconSave"></gs:button>    						    				 								
   								</td>
   							</tr>
   						</table>
   					</div>		    						       								    					
		  		</div>				
				
				<form name="BSC_PROG002D0005Q_measureDataForm" id="BSC_PROG002D0005Q_measureDataForm" method="post" action=".">
					<input type="hidden" name="BSC_PROG002D0005Q_kpiOid" id="BSC_PROG002D0005Q_kpiOid" value="" />
					<input type="hidden" name="BSC_PROG002D0005Q_date" id="BSC_PROG002D0005Q_date" value="${nowDate2}" />
					<input type="hidden" name="BSC_PROG002D0005Q_orgaMeasureSeparate" id="BSC_PROG002D0005Q_orgaMeasureSeparate" value="" />
					<input type="hidden" name="BSC_PROG002D0005Q_userMeasureSeparate" id="BSC_PROG002D0005Q_userMeasureSeparate" value="" />
					<!-- 
					BSC_PROG002D0005Q_dataFor 
					BSC_PROG002D0005Q_frequency 
					BSC_PROG002D0005Q_empId
					BSC_PROG002D0005Q_orgId
					由 bsc.measureDataCalendarQueryAction.action 回傳回來的資料中
					-->
					<div id="BSC_PROG002D0005Q_content"></div>
				</form>			
							
			</td>
		</tr>
	</table>		
							
<script type="text/javascript">
${programId}_page_message();
setTimeout("BSC_PROG002D0005Q_getKpiTree();",1000);
</script>	
</body>
</html>
