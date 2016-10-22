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

ul{  
	border: 3px solid #ccc;  
	padding: 2em;  
	margin: 5em;  
	float: left;  
	cursor: default;  
}  
.dojoDndItemOver{  
	background: #ededed;  
	cursor: pointer;  
}  
.dojoDndItemSelected {  
	background: #ccf;   
}  
.dojoDndItemAnchor{  
	background: #ccf;  
}  
            
</style>

<script type="text/javascript">


function BSC_PROG004D0002A_C01_onWorkspaceTemplateChange() {
	
	viewPage.addOrUpdateContentPane(
			'BSC_PROG004D0002A_TabContainer', 
			'BSC_PROG004D0002A_TabContainer_contentChildTab', 
			'Content', 
			'${basePath}/bsc.workspaceSettingsCreateContentAction.action?fields.workspaceTemplateOid=' + dijit.byId("BSC_PROG004D0002A_C01_workspaceTemplateOid").get("value"), 
			false, 
			true);		
	
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
	
	<table border="0" width="100%" height="25px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="25px" width="10%"  align="right">
    			<gs:label text="${action.getText('BSC_PROG004D0002A_C01_workspaceTemplateOid')}" id="BSC_PROG004D0002A_C01_workspaceTemplateOid" requiredFlag="Y"></gs:label>
    		</td>
    		<td height="25px" width="90%"  align="left">
    			<gs:select name="BSC_PROG004D0002A_C01_workspaceTemplateOid" dataSource="workspaceTemplateMap" id="BSC_PROG004D0002A_C01_workspaceTemplateOid" onChange="BSC_PROG004D0002A_C01_onWorkspaceTemplateChange();" value="fields.workspaceTemplateOid"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG004D0002A_C01_workspaceTemplateOid'">
    				Select layout template.
				</div>
				<gs:inputfieldNoticeMsgLabel id="BSC_PROG004D0002A_C01_workspaceTemplateOid"></gs:inputfieldNoticeMsgLabel>     			
    		</td>
    	</tr>	
    </table>		
	<table border="0" width="100%" height="100%" cellpadding="1" cellspacing="0" >	
		<tr>
			<td width="48px" valign="top">
				<ul dojoType="dojo.dnd.Source">
					<s:iterator value="workspaceCompoments" var="st">
					<li class="dojoDndItem">
						<!-- BSC_PROG004D0002A_C01_COMPOMENT:${compId} -->
						<img id="${compId}" alt="${name}" src="./compoment-images/${image}">
						<center>${name}</center>
					</li>
					</s:iterator>				
				</ul>
			</td>
			<td valign="top">
				${confContent}
			</td>
		</tr>
	</table>

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
