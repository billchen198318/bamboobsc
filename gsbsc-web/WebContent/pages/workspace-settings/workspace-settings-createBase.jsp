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

	<table border="0" width="100%" height="225px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG004D0002A_C00_workspaceId')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG004D0002A_C00_workspaceId" id="BSC_PROG004D0002A_C00_workspaceId" value="" maxlength="20"></gs:textBox>
    		</td>
    	</tr>     		
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG004D0002A_C00_workspaceName')"/></b>:
    			<br/>
    			<gs:textBox name="BSC_PROG004D0002A_C00_workspaceName" id="BSC_PROG004D0002A_C00_workspaceName" value="" maxlength="100" width="400"></gs:textBox>
    		</td>
    	</tr> 
		<tr>
		    <td height="125px" width="100%" align="left">
		    	<b><s:property value="getText('BSC_PROG004D0002A_C00_description')"/></b>:
		    	<br/>
		    	<textarea id="BSC_PROG004D0002A_C00_description" name="BSC_PROG004D0002A_C00_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
		    </td>
		</tr>     	   	
    </table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
