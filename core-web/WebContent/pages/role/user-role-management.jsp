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

.buttonBackNav {
  background-image: url(<%=basePath%>/icons/stock_left.png);
  background-repeat: no-repeat;
  width: 16px;
  height: 16px;
  text-align: center;
}
.buttonForwardNav {
  background-image: url(<%=basePath%>/icons/stock_right.png);
  background-repeat: no-repeat;
  width: 16px;
  height: 16px;
  text-align: center;
}

</style>

<script type="text/javascript">

var CORE_PROG002D0002Q_fieldsId = new Object();
CORE_PROG002D0002Q_fieldsId['accountOid']	= 'CORE_PROG002D0002Q_account';

function CORE_PROG002D0002Q_account_change() {
	CORE_PROG002D0002Q_clearOptions();
	var accountOid = dijit.byId("CORE_PROG002D0002Q_account").get("value");
	if ( _gscore_please_select_id == accountOid || _gscore_please_select_name == accountOid ) {
		return;
	}
	xhrSendParameter(
			'core.userRoleEnableAndDisableSelectDataAction.action', 
			{ 'fields.accountOid' : accountOid }, 
			'json', 
			_gscore_dojo_ajax_timeout, 
			_gscore_dojo_ajax_sync, 
			true, 
			function(data){
    			if ('Y' != data.success) {    				
    				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
    				return;
    			}	
    			CORE_PROG002D0002Q_setOptions("CORE_PROG002D0002Q_enable", data.enableItems);
    			CORE_PROG002D0002Q_setOptions("CORE_PROG002D0002Q_disable", data.disableItems);
			}, 
			function(error){
				alert(error);
			}
	);		
}

function CORE_PROG002D0002Q_saveSuccess(data) {
	setFieldsBackgroundDefault(CORE_PROG002D0002Q_fieldsId);	
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG002D0002Q_fieldsId);		
	}	
}

function CORE_PROG002D0002Q_getEnableIds() {
	var sel = document.CORE_PROG002D0002Q_form.CORE_PROG002D0002Q_enable.options;
	var appendOids = '';
	for (var i=0; sel!=null && i<sel.length; i++) {
		appendOids += sel[i].value + _gscore_delimiter;
	}	
	return viewPage.getStrToHex( viewPage.getStrToBase64(appendOids) );
}

function CORE_PROG002D0002Q_setOptions(selectId, items) {
	var sel = dojo.byId(selectId);
	if (sel==null) {
		return;
	}
	for (var i=0; items!=null && i<items.length; i++) {
        var c = dojo.doc.createElement('option');
        c.innerHTML = items[i].value;
        c.value = items[i].key;
        sel.appendChild(c);	
	}	
}

function CORE_PROG002D0002Q_clearOptions() {
	dojo.empty("CORE_PROG002D0002Q_enable");
	dojo.empty("CORE_PROG002D0002Q_disable");
}

function CORE_PROG002D0002Q_clear() {
	setFieldsBackgroundDefault(CORE_PROG002D0002Q_fieldsId);
	dijit.byId("CORE_PROG002D0002Q_account").set("value", _gscore_please_select_id);
}

function CORE_PROG002D0002Q_moveSelectItem(srcSelectId, destSelectId) {
	dijit.byId(destSelectId).addSelected( dijit.byId(srcSelectId) );
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
		saveJsMethod="CORE_PROG002D0002Q_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	

<form action="" name="CORE_PROG002D0002Q_form" id="CORE_PROG002D0002Q_form">		
	<table border="0" width="100%" height="60px" cellpadding="1" cellspacing="0" >
		<tr>
			<td height="30px" width="20%"  align="right">Account:</td>
			<td height="30px" width="80%"  align="left">
				<gs:select name="CORE_PROG002D0002Q_account" dataSource="accountMap" id="CORE_PROG002D0002Q_account" onChange="CORE_PROG002D0002Q_account_change()"></gs:select>
			</td>
		</tr>
	</table>		
	<table width="750px" height="160px" border="0" cellpadding="1" cellspacing="0" >
		<tr>
			<td align="center" width="350px" height="200px">
				<center><b>Have role:</b></center>
				<select data-dojo-type="dijit/form/MultiSelect" id="CORE_PROG002D0002Q_enable" name="CORE_PROG002D0002Q_enable" size="10" style="width:320px;height:300px" >			    
				</select>					
			</td>
			<td align="center" width="35px" height="200px">
				<br/>
				<button id="CORE_PROG002D0002Q_back" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						iconClass:'buttonBackNav',
						showLabel:false,
						onClick:function(){
							CORE_PROG002D0002Q_moveSelectItem('CORE_PROG002D0002Q_disable', 'CORE_PROG002D0002Q_enable');							  							
						}
					">←</button>					
				<br/>	
				<button id="CORE_PROG002D0002Q_forward" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						iconClass:'buttonForwardNav',
						showLabel:false,
						onClick:function(){
							CORE_PROG002D0002Q_moveSelectItem('CORE_PROG002D0002Q_enable', 'CORE_PROG002D0002Q_disable');
						}
					">→</button>					
			</td>
			<td align="center" width="350px" height="200px">
				<center><b>Not have role:</b></center>
				<select data-dojo-type="dijit/form/MultiSelect" id="CORE_PROG002D0002Q_disable" name="CORE_PROG002D0002Q_disable" size="10" style="width:320px;height:300px" >
				</select>					
			</td>
		</tr>			
	</table>
	<table border="0" width="750px" height="35px" cellpadding="1" cellspacing="0" >
		<tr>
			<td align="center">
    			<gs:button name="CORE_PROG002D0002Q_save" id="CORE_PROG002D0002Q_save" onClick="CORE_PROG002D0002Q_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.userRoleSaveAction.action"
    				parameterType="postData"
    				xhrParameter="
    					{
    						'fields.accountOid'			:	dijit.byId('CORE_PROG002D0002Q_account').get('value'),
    						'fields.appendRoleOid'		:	CORE_PROG002D0002Q_getEnableIds()
    					}
    				"
    				errorFn=""
    				loadFn="CORE_PROG002D0002Q_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button> 			
    			<gs:button name="CORE_PROG002D0002Q_clear" id="CORE_PROG002D0002Q_clear" onClick="CORE_PROG002D0002Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>   			
			</td>
		</tr>
	</table>
	
</form>
			
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	