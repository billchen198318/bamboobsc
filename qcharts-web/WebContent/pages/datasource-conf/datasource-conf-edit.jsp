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

var QCHARTS_PROG001D0001E_fieldsId = new Object();
QCHARTS_PROG001D0001E_fieldsId['driverOid'] 	= 'QCHARTS_PROG001D0001E_driverOid';
//QCHARTS_PROG001D0001E_fieldsId['id'] 			= 'QCHARTS_PROG001D0001E_id';
QCHARTS_PROG001D0001E_fieldsId['name'] 			= 'QCHARTS_PROG001D0001E_name';
QCHARTS_PROG001D0001E_fieldsId['jdbcUrl'] 		= 'QCHARTS_PROG001D0001E_jdbcUrl';
QCHARTS_PROG001D0001E_fieldsId['dbAccount'] 	= 'QCHARTS_PROG001D0001E_dbAccount';
QCHARTS_PROG001D0001E_fieldsId['dbPassword'] 	= 'QCHARTS_PROG001D0001E_dbPassword';
QCHARTS_PROG001D0001E_fieldsId['description']	= 'QCHARTS_PROG001D0001E_description';

function QCHARTS_PROG001D0001E_updateSuccess(data) {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0001E_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG001D0001E_fieldsId);		
		return;
	}		
}

function QCHARTS_PROG001D0001E_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0001E_fieldsId);
	dijit.byId('QCHARTS_PROG001D0001E_driverOid').set("value", _gscore_please_select_id);
	//dijit.byId('QCHARTS_PROG001D0001E_id').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0001E_name').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0001E_jdbcUrl').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001E_dbAccount').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001E_dbPassword').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001E_description').set("value", "");
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
		saveJsMethod="QCHARTS_PROG001D0001E_update();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="530px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Driver</b>:
    			<br/>
    			<gs:select name="QCHARTS_PROG001D0001E_driverOid" dataSource="driverMap" id="QCHARTS_PROG001D0001E_driverOid" value="fields.driverOid"></gs:select>
    		</td>
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b> (read only):
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001E_id" id="QCHARTS_PROG001D0001E_id" value="dataSourceConf.id" width="200" maxlength="20" readonly="Y"></gs:textBox>
    		</td>
    	</tr>  	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001E_name" id="QCHARTS_PROG001D0001E_name" value="dataSourceConf.name" width="200" maxlength="100"></gs:textBox>
    		</td>
    	</tr>  	
   		<tr>
    		<td height="80px" width="100%"  align="left">
    			<font color='RED'>*</font><b>JDBC-url</b>:
    			<br/>
    			<textarea id="QCHARTS_PROG001D0001E_jdbcUrl" name="QCHARTS_PROG001D0001E_jdbcUrl" data-dojo-type="dijit/form/Textarea" rows="2" cols="75" style="width:600px;height:50px;max-height:50px">${dataSourceConf.jdbcUrl}</textarea>
    		</td>
    	</tr>  	
 		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>DB account</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001E_dbAccount" id="QCHARTS_PROG001D0001E_dbAccount" value="dataSourceConf.dbAccount" width="200" maxlength="50"></gs:textBox>
    		</td>
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>DB password</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001E_dbPassword" id="QCHARTS_PROG001D0001E_dbPassword" value="dataSourceConf.dbPassword" width="200" maxlength="100"></gs:textBox>
    		</td>
    	</tr>  	    	   	 	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b>Description</b>:
		    	<br/>
		    	<textarea id="QCHARTS_PROG001D0001E_description" name="QCHARTS_PROG001D0001E_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px">${dataSourceConf.description}</textarea>	
		    </td>
		</tr>   	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="QCHARTS_PROG001D0001E_update" id="QCHARTS_PROG001D0001E_update" onClick="QCHARTS_PROG001D0001E_update();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.dataSourceConfUpdateAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.oid'			: '${dataSourceConf.oid}',		 
    						'fields.driverOid'		: dijit.byId('QCHARTS_PROG001D0001E_driverOid').get('value'), 
    						'fields.id'				: dijit.byId('QCHARTS_PROG001D0001E_id').get('value'),
    						'fields.name'			: dijit.byId('QCHARTS_PROG001D0001E_name').get('value'),
    						'fields.jdbcUrl'		: dijit.byId('QCHARTS_PROG001D0001E_jdbcUrl').get('value'),    				
    						'fields.dbAccount'		: dijit.byId('QCHARTS_PROG001D0001E_dbAccount').get('value'),
    						'fields.dbPassword'		: dijit.byId('QCHARTS_PROG001D0001E_dbPassword').get('value'),
    						'fields.description'	: dijit.byId('QCHARTS_PROG001D0001E_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="QCHARTS_PROG001D0001E_updateSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="QCHARTS_PROG001D0001E_clear" id="QCHARTS_PROG001D0001E_clear" onClick="QCHARTS_PROG001D0001E_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    			
    		</td>
    	</tr>     	 	  	    	
	</table>	
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
