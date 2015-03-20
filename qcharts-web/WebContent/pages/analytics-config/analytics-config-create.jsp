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

var QCHARTS_PROG001D0003A_fieldsId = new Object();
QCHARTS_PROG001D0003A_fieldsId['id'] 			= 'QCHARTS_PROG001D0003A_id';
QCHARTS_PROG001D0003A_fieldsId['name'] 			= 'QCHARTS_PROG001D0003A_name';
QCHARTS_PROG001D0003A_fieldsId['jdbcDrivers'] 	= 'QCHARTS_PROG001D0003A_jdbcDrivers';
QCHARTS_PROG001D0003A_fieldsId['jdbcUrl'] 		= 'QCHARTS_PROG001D0003A_jdbcUrl';
QCHARTS_PROG001D0003A_fieldsId['description']	= 'QCHARTS_PROG001D0003A_description';

function QCHARTS_PROG001D0003A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(QCHARTS_PROG001D0003A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG001D0003A_fieldsId);		
		return;
	}	
	QCHARTS_PROG001D0003A_clear();
}

function QCHARTS_PROG001D0003A_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0003A_fieldsId);	
	dijit.byId('QCHARTS_PROG001D0003A_id').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0003A_name').set("value", "");
	dijit.byId('QCHARTS_PROG001D0003A_jdbcDrivers').set("value", "");
	dijit.byId('QCHARTS_PROG001D0003A_jdbcUrl').set("value", "");
	dijit.byId('QCHARTS_PROG001D0003A_description').set("value", "");
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
		saveJsMethod="QCHARTS_PROG001D0003A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="400px" cellpadding="1" cellspacing="0" >			
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Id</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0003A_id" id="QCHARTS_PROG001D0003A_id" value="" width="200" maxlength="20"></gs:textBox>
    		</td>    		
    	</tr>  	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0003A_name" id="QCHARTS_PROG001D0003A_name" value="" width="200" maxlength="100"></gs:textBox>
    		</td>    		
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Drivers</b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0003A_jdbcDrivers" id="QCHARTS_PROG001D0003A_jdbcDrivers" value="" width="200" maxlength="50"></gs:textBox>    			
    		</td>    		
    	</tr>     	    	
   		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>JDBC-url</b>:
    			<br/>
    			<textarea id="QCHARTS_PROG001D0003A_jdbcUrl" name="QCHARTS_PROG001D0003A_jdbcUrl" data-dojo-type="dijit/form/Textarea" rows="2" cols="75" style="width:600px;height:50px;max-height:50px"></textarea>
    		</td>    		
    	</tr>  	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b>Description</b>:
		    	<br/>
		    	<textarea id="QCHARTS_PROG001D0003A_description" name="QCHARTS_PROG001D0003A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
		    </td>		    
		</tr>      	  	    	    	   	  	    		 	  	    	    	      	    	    	    	   	  	    		 	  	    	
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="QCHARTS_PROG001D0003A_save" id="QCHARTS_PROG001D0003A_save" onClick="QCHARTS_PROG001D0003A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.analyticsConfigSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.id'				: dijit.byId('QCHARTS_PROG001D0003A_id').get('value'),
    						'fields.name'			: dijit.byId('QCHARTS_PROG001D0003A_name').get('value'),
    						'fields.jdbcDrivers'	: dijit.byId('QCHARTS_PROG001D0003A_jdbcDrivers').get('value'),
    						'fields.jdbcUrl'		: dijit.byId('QCHARTS_PROG001D0003A_jdbcUrl').get('value'),    				
    						'fields.description'	: dijit.byId('QCHARTS_PROG001D0003A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="QCHARTS_PROG001D0003A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="QCHARTS_PROG001D0003A_clear" id="QCHARTS_PROG001D0003A_clear" onClick="QCHARTS_PROG001D0003A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>       		
    		</td>
    	</tr>     	 	  	    	
	</table>		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	