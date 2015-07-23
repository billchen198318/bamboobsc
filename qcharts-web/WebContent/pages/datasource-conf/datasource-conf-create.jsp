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

var QCHARTS_PROG001D0001A_fieldsId = new Object();
QCHARTS_PROG001D0001A_fieldsId['driverOid'] 	= 'QCHARTS_PROG001D0001A_driverOid';
QCHARTS_PROG001D0001A_fieldsId['id'] 			= 'QCHARTS_PROG001D0001A_id';
QCHARTS_PROG001D0001A_fieldsId['name'] 			= 'QCHARTS_PROG001D0001A_name';
QCHARTS_PROG001D0001A_fieldsId['jdbcUrl'] 		= 'QCHARTS_PROG001D0001A_jdbcUrl';
QCHARTS_PROG001D0001A_fieldsId['dbAccount'] 	= 'QCHARTS_PROG001D0001A_dbAccount';
QCHARTS_PROG001D0001A_fieldsId['dbPassword'] 	= 'QCHARTS_PROG001D0001A_dbPassword';
QCHARTS_PROG001D0001A_fieldsId['description']	= 'QCHARTS_PROG001D0001A_description';

function QCHARTS_PROG001D0001A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(QCHARTS_PROG001D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, QCHARTS_PROG001D0001A_fieldsId);		
		return;
	}	
	QCHARTS_PROG001D0001A_clear();
}

function QCHARTS_PROG001D0001A_clear() {
	setFieldsBackgroundDefault(QCHARTS_PROG001D0001A_fieldsId);	
	dijit.byId('QCHARTS_PROG001D0001A_driverOid').set("value", _gscore_please_select_id);
	dijit.byId('QCHARTS_PROG001D0001A_id').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0001A_name').set("value", "");	
	dijit.byId('QCHARTS_PROG001D0001A_jdbcUrl').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001A_dbAccount').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001A_dbPassword').set("value", "");
	dijit.byId('QCHARTS_PROG001D0001A_description').set("value", "");
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
		saveJsMethod="QCHARTS_PROG001D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="530px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_driverOid')"/></b>:
    			<br/>
    			<gs:select name="QCHARTS_PROG001D0001A_driverOid" dataSource="driverMap" id="QCHARTS_PROG001D0001A_driverOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_driverOid'">
    				Select driver.<BR/>
    				
    				&nbsp;&nbsp;PostgreSQL driver <a href="https://jdbc.postgresql.org/">download</a><BR/>
    				&nbsp;&nbsp;SQL-Server driver <a href="https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx">download</a><BR/>
    				&nbsp;&nbsp;Oracle driver <a href="http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html">download</a> <BR/>    				
    				<BR/>
    				<BR/>
    				Non MySQL database:<BR/>
    				1. need <font color='RED'>copy jdbc driver JAR library file to WEB-INF/lib/</font> .  <BR/>  				
    				2. need config table named `<font color='RED'>qc_data_source_driver</font>` to add datasource. 
				</div>         			
    		</td>    		
    	</tr>		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_id')"/></b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001A_id" id="QCHARTS_PROG001D0001A_id" value="" width="200" maxlength="20"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_id'">
    				Input Id, only allow normal characters.
				</div>        			
    		</td>    		
    	</tr>  	    	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_name')"/></b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001A_name" id="QCHARTS_PROG001D0001A_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_name'">
    				Input name.
				</div>       			
    		</td>    		
    	</tr>  	
   		<tr>
    		<td height="80px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_jdbcUrl')"/></b>:
    			<br/>
    			<textarea id="QCHARTS_PROG001D0001A_jdbcUrl" name="QCHARTS_PROG001D0001A_jdbcUrl" data-dojo-type="dijit/form/Textarea" rows="2" cols="75" style="width:600px;height:50px;max-height:50px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_jdbcUrl'">
    				Input jdbc url.<BR/>
    				Example:<BR/>
    				jdbc:mysql://localhost:3306/testdb
    				<BR/>
    				<BR/>
    				<a href="http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html">Reference</a>
				</div>     			
    		</td>    		
    	</tr>  	
 		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_dbAccount')"/></b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001A_dbAccount" id="QCHARTS_PROG001D0001A_dbAccount" value="" width="200" maxlength="50"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_dbAccount'">
    				Input database account.
				</div>       			
    		</td>    		
    	</tr>  	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('QCHARTS_PROG001D0001A_dbPassword')"/></b>:
    			<br/>
    			<gs:textBox name="QCHARTS_PROG001D0001A_dbPassword" id="QCHARTS_PROG001D0001A_dbPassword" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_dbPassword'">
    				Input database password.
				</div>     			
    		</td>    		
    	</tr>  	    	   	 	
		<tr>
		    <td height="150px" width="100%" align="left">
		    	<b><s:property value="getText('QCHARTS_PROG001D0001A_description')"/></b>:
		    	<br/>
		    	<textarea id="QCHARTS_PROG001D0001A_description" name="QCHARTS_PROG001D0001A_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0001A_description'">
    				Input description, the maximum allowed 500 characters. 
				</div> 		    	
		    </td>
		</tr>      	  	    	    	   	  	    				 	  	    	    	      	    	    	    	   	  	    		 	  	    
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="QCHARTS_PROG001D0001A_save" id="QCHARTS_PROG001D0001A_save" onClick="QCHARTS_PROG001D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.dataSourceConfSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.driverOid'		: dijit.byId('QCHARTS_PROG001D0001A_driverOid').get('value'), 
    						'fields.id'				: dijit.byId('QCHARTS_PROG001D0001A_id').get('value'),
    						'fields.name'			: dijit.byId('QCHARTS_PROG001D0001A_name').get('value'),
    						'fields.jdbcUrl'		: dijit.byId('QCHARTS_PROG001D0001A_jdbcUrl').get('value'),    				
    						'fields.dbAccount'		: dijit.byId('QCHARTS_PROG001D0001A_dbAccount').get('value'),
    						'fields.dbPassword'		: dijit.byId('QCHARTS_PROG001D0001A_dbPassword').get('value'),
    						'fields.description'	: dijit.byId('QCHARTS_PROG001D0001A_description').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="QCHARTS_PROG001D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('QCHARTS_PROG001D0001A_save')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="QCHARTS_PROG001D0001A_clear" id="QCHARTS_PROG001D0001A_clear" onClick="QCHARTS_PROG001D0001A_clear();" 
    				label="${action.getText('QCHARTS_PROG001D0001A_clear')}" 
    				iconClass="dijitIconClear"></gs:button>       		
    		</td>
    	</tr>     	 	  	    	
	</table>		
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
	