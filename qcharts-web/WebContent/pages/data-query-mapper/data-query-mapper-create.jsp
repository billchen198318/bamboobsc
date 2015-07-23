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

var QCHARTS_PROG001D0002A_fieldsId = new Object();
QCHARTS_PROG001D0002A_fieldsId['name'] 			= 'QCHARTS_PROG001D0002A_name';
QCHARTS_PROG001D0002A_fieldsId['labelField'] 	= 'QCHARTS_PROG001D0002A_labelField';
QCHARTS_PROG001D0002A_fieldsId['valueField'] 	= 'QCHARTS_PROG001D0002A_valueField';

function QCHARTS_PROG001D0002A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(QCHARTS_PROG001D0002A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG001D0002A_fieldsId);		
		return;
	}	
	QCHARTS_PROG001D0002A_clear();
}

function QCHARTS_PROG001D0002A_clear() {
	dijit.byId("QCHARTS_PROG001D0002A_name").set("value", "");
	dijit.byId("QCHARTS_PROG001D0002A_description").set("value", "");
	dijit.byId("QCHARTS_PROG001D0002A_labelField").set("value", "");
	dijit.byId("QCHARTS_PROG001D0002A_valueField").set("value", "");
    var table = document.getElementById('QCHARTS_PROG001D0002A_mapperTable');    
	for (var r=table.rows.length-1; r>0; r--) { // 第一列是Title
		table.deleteRow(r);
	}	
}

function QCHARTS_PROG001D0002A_getAppendFields() {
	var str = '';
    var table = document.getElementById('QCHARTS_PROG001D0002A_mapperTable');    
	for (var r=1; r<table.rows.length; r++) { // 第一列是Title , 所以 r=1
		str += table.rows[r].cells[1].innerHTML + ':' + table.rows[r].cells[2].innerHTML + _gscore_delimiter;
	}		
	return btoa( encodeURIComponent( escape( str ) ) );
}

function QCHARTS_PROG001D0002A_addField() {
	var labelField = dijit.byId("QCHARTS_PROG001D0002A_labelField").get("value");
	var valueField = dijit.byId("QCHARTS_PROG001D0002A_valueField").get("value");	
	if ('' == labelField || '' == valueField) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('QCHARTS_PROG001D0002A_addField_msg1')" escapeJavaScript="true"/>', function(){}, 'N');
		return;
	} 
	
	if (labelField.indexOf(':')>-1 || labelField.indexOf(_gscore_delimiter)>-1 || valueField.indexOf(':')>-1 || valueField.indexOf(_gscore_delimiter)>-1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('QCHARTS_PROG001D0002A_addField_msg2')" escapeJavaScript="true"/>', function(){}, 'N');
		return;		
	}
	
    var table = document.getElementById('QCHARTS_PROG001D0002A_mapperTable');    
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    var cell1 = row.insertCell(0);
    cell1.style.backgroundColor="#ffffff";
    cell1.innerHTML = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="QCHARTS_PROG001D0002A_removeField(\'' + labelField + '\', \'' + valueField + '\');" /> ';
    
    var cell2 = row.insertCell(1);
    cell2.style.backgroundColor="#ffffff";
    cell2.innerHTML = labelField;
    
    var cell3 = row.insertCell(2);
    cell3.style.backgroundColor="#ffffff";
    cell3.innerHTML = valueField;    	
}

function QCHARTS_PROG001D0002A_removeField(labelFieldValue, valueFieldValue) {
    var table = document.getElementById('QCHARTS_PROG001D0002A_mapperTable');    
	for (var r=1; r<table.rows.length; r++) { // 第一列是Title , 所以 r=1
		var content1 = table.rows[r].cells[1].innerHTML;
		var content2 = table.rows[r].cells[2].innerHTML;
		if (content1 == labelFieldValue && content2 == valueFieldValue) {
			table.deleteRow(r);
		}
	}	
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
		saveEnabel="Y" 
		saveJsMethod="QCHARTS_PROG001D0002A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="200px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0002A_name')"/></b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0002A_name" id="QCHARTS_PROG001D0002A_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0002A_name'">
    				Input name.
				</div>      			
    		</td>    		
    	</tr>  
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b><s:property value="getText('QCHARTS_PROG001D0002A_description')"/></b>:
		    	<br/>
		    	<textarea id="QCHARTS_PROG001D0002A_description" name="QCHARTS_PROG001D0002A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0002A_description'">
    				Input description, the maximum allowed 500 characters. 
				</div> 			    	
		    </td>		    
		</tr>  
	</table>	
	<table border="0" width="100%" height="25px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="90%"  align="left">
    			<b><s:property value="getText('QCHARTS_PROG001D0002A_labelField')"/></b>:
    				<gs:textBox name="QCHARTS_PROG001D0002A_labelField" id="QCHARTS_PROG001D0002A_labelField" value="" width="200" maxlength="50"></gs:textBox>
					<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0002A_labelField'">
    					Input label field name.
					</div> 		    				
    			&nbsp;
    			<b><s:property value="getText('QCHARTS_PROG001D0002A_valueField')"/></b>:
    				<gs:textBox name="QCHARTS_PROG001D0002A_valueField" id="QCHARTS_PROG001D0002A_valueField" value="" width="200" maxlength="50"></gs:textBox>
					<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0002A_valueField'">
    					Input value field name.
					</div> 		       				
    				
    			&nbsp;
    			<gs:button name="QCHARTS_PROG001D0002A_addField" id="QCHARTS_PROG001D0002A_addField" onClick="QCHARTS_PROG001D0002A_addField();" 
    				label="+" ></gs:button>       			
    		</td>
    	</tr>  			
	</table>
	<table id="QCHARTS_PROG001D0002A_mapperTable" width="600px" border="0" cellspacing="1" cellpadding="1" bgcolor="#C0C0C0" >
		<tr>
			<td bgcolor="#F1F1F1" width="10%" align="center"><b>#</b></td>
			<td bgcolor="#F1F1F1" width="45%" align="center"><b><s:property value="getText('QCHARTS_PROG001D0002A_mapperTableLabel')"/></b></td>
			<td bgcolor="#F1F1F1" width="45%" align="center"><b><s:property value="getText('QCHARTS_PROG001D0002A_mapperTableValue')"/></b></td>			
		</tr>
	</table>	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >	
		<tr>
		    <td height="50px" width="100%"  align="left">
    			<gs:button name="QCHARTS_PROG001D0002A_save" id="QCHARTS_PROG001D0002A_save" onClick="QCHARTS_PROG001D0002A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.dataQueryMapperSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.name'			: dijit.byId('QCHARTS_PROG001D0002A_name').get('value'),
    						'fields.description'	: dijit.byId('QCHARTS_PROG001D0002A_description').get('value'),
    						'fields.appendFields'	: QCHARTS_PROG001D0002A_getAppendFields()
    					} 
    				"
    				errorFn=""
    				loadFn="QCHARTS_PROG001D0002A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('QCHARTS_PROG001D0002A_save')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="QCHARTS_PROG001D0002A_clear" id="QCHARTS_PROG001D0002A_clear" onClick="QCHARTS_PROG001D0002A_clear();" 
    				label="${action.getText('QCHARTS_PROG001D0002A_clear')}" 
    				iconClass="dijitIconClear"></gs:button>  		    		
		    </td>
		</tr>  
	</table>
		
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	