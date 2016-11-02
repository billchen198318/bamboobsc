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

function BSC_PROG005D0001A_S00_add() {
	var size = ${fields.oid}.length;	
	if ( size >= ${maxLevelSize} ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_01')"/> : ' + '${maxLevelSize}', function(){}, 'Y');
		return;
	}
	var nameStr = dijit.byId('BSC_PROG005D0001A_S00_name').get('value');
	var valueStr = dijit.byId('BSC_PROG005D0001A_S00_value').get('value');
	if ( !viewPage.isNormalInteger(valueStr) ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_02')"/>', function(){}, 'N');
		return;
	}
	if ( viewPage.isBlank(nameStr) || viewPage.isEmpty(nameStr) ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_03')"/>', function(){}, 'N');
		return;
	}	
	if (parseInt(valueStr,10)<1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_02')"/>', function(){}, 'N');
		return;		
	}
	nameStr = viewPage.escape1(nameStr);
	for (var n=0; n<size; n++) {
		if ( ${fields.oid}[n].name == nameStr ) {
			n = size;
			alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_04')"/>', function(){}, 'N');
			return;
		}
		if ( ${fields.oid}[n].value == valueStr ) {
			n = size;
			alertDialog(_getApplicationProgramNameById('${programId}'), '<s:property value="getText('MESSAGE.BSC_PROG005D0001A_S00_add_05')"/>', function(){}, 'N');
			return;			
		}
	}		
	${fields.oid}.push( {name: nameStr , value: valueStr} );
	BSC_PROG005D0001A_S00_showDataTable();
}

function BSC_PROG005D0001A_S00_removeField( name ) {
	var size = ${fields.oid}.length;
	for (var n=0; n<size; n++) {
		var dataItem = ${fields.oid}[n];
		if ( dataItem.name == name ) {
			${fields.oid}.splice( n , 1 );
			n = size;
		}
	}
	BSC_PROG005D0001A_S00_showDataTable();
}

function BSC_PROG005D0001A_S00_clear() {	
	dijit.byId('BSC_PROG005D0001A_S00_name').set("value", "");
	dijit.byId('BSC_PROG005D0001A_S00_value').set("value", "");
	${fields.oid} = [];
	BSC_PROG005D0001A_S00_showDataTable();	
	dojo.byId( '${fields.oid}' ).innerHTML = '';
}

function BSC_PROG005D0001A_S00_showDataTable() {
	var size = ${fields.oid}.length;
	var labelStr = '';
	var txtContent = '';
	txtContent += '<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">';
	txtContent += '<tr>';
	txtContent += '<td width="20%" align="center" bgcolor="#f1eee5">*</td>';
	txtContent += '<td width="50%" align="left" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0001A_S00_name')"/></b></td>';
	txtContent += '<td width="30%" align="left" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0001A_S00_value')"/></b></td>';
	txtContent += '</tr>';
	for (var n=0; n<size; n++) {
		var dataItem = ${fields.oid}[n];
		txtContent += '<tr>';
		var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG005D0001A_S00_removeField(\'' + dataItem.name + '\');" /> ';
		txtContent += '<td width="20%" align="center" bgcolor="#ffffff">' + img + '</td>';
		txtContent += '<td width="50%" align="left" bgcolor="#ffffff">' + dataItem.name + '</td>';
		txtContent += '<td width="30%" align="left" bgcolor="#ffffff">' + dataItem.value + '</td>';
		txtContent += '</tr>';		
		labelStr += dataItem.name + ';';
	}
	txtContent += '</table>';
	dojo.byId( 'BSC_PROG005D0001A_S00_dataTable' ).innerHTML = txtContent;
	dojo.byId( '${fields.oid}' ).innerHTML = labelStr;
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
		cancelJsMethod="${programId}_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_DlgShow('${fields.oid}');" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001A_S00_name')}" id="BSC_PROG005D0001A_S00_name" requiredFlag="Y"></gs:label>
    			<br/>
    			<gs:textBox name="BSC_PROG005D0001A_S00_name" id="BSC_PROG005D0001A_S00_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001A_S00_name'">
    				Input name.
				</div>       			
    		</td>  
    		<td height="50px" width="50%"  align="left">
    			<gs:label text="${action.getText('BSC_PROG005D0001A_S00_value')}" id="BSC_PROG005D0001A_S00_value" requiredFlag="Y"></gs:label>
    			<br/>    			
    			<input id="BSC_PROG005D0001A_S00_value" name="BSC_PROG005D0001A_S00_value" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0" data-dojo-props="smallDelta:1, constraints:{min:-999,max:999, pattern: '+000;-0' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001A_S00_value'">
    				Input value and only number.
				</div>       			
    		</td> 		
    	</tr>  	
    	<tr>
    		<td height="50px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG005D0001A_S00_add" id="BSC_PROG005D0001A_S00_add" onClick="BSC_PROG005D0001A_S00_add();"
    				label="${action.getText('BSC_PROG005D0001A_S00_add')}" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    	
				<button name="BSC_PROG005D0001A_S00_clear" id="BSC_PROG005D0001A_S00_clear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001A_S00_clear();
						}
					"
					class="alt-primary"><s:property value="getText('BSC_PROG005D0001A_S00_clear')"/></button>    						  		
    		</td>
    	</tr> 
    	    	
    </table>
    
    <div id="BSC_PROG005D0001A_S00_dataTable"></div>
    
<br/>
<br/>
<br/>
<br/>
<br/>    
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<script type="text/javascript">BSC_PROG005D0001A_S00_showDataTable();</script>   
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
    		
	