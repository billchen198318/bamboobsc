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

<body class="flat">

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
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" cellspacing="1" cellpadding="1" style="border:1px #ebeadb solid; border-radius: 5px; background: linear-gradient(to top, #f1eee5 , #fafafa);">
		<legend><b>Project</b></legend>
		<tr>
			<td align="center" width="10%" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0002Q_grid_01')"/></b></td>
			<td align="left" width="40%" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0002Q_grid_02')"/></b></td>
			<td align="left" width="35%" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0002Q_grid_03')"/></b></td>
			<td align="center" width="15%" bgcolor="#f1eee5"><b><s:property value="getText('BSC_PROG005D0002Q_grid_04')"/></b></td>
		</tr>
		<s:if test=" null != projects && projects.size != 0 ">
		<s:iterator value="projects" status="st">
		
		<tr>
			<td align="center" width="10%" bgcolor="#ffffff"><s:property value="year"/></td>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="left" width="35%" bgcolor="#ffffff"><s:property value="description"/></td>
			<td align="center" width="15%" bgcolor="#ffffff">
				<button name="BSC_PROG005D0002Q_btnScore_${oid}" id="BSC_PROG005D0002Q_btnScore_${oid}" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							BSC_PROG005D0003Q_DlgShow('${oid}');
						}
					"
					class="alt-primary"><s:property value="getText('BSC_PROG005D0002Q_btnScore')"/></button>
				<button name="BSC_PROG005D0002Q_btnReport_${oid}" id="BSC_PROG005D0002Q_btnReport_${oid}" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							BSC_PROG005D0004Q_DlgShow('${oid}');
						}
					"
					class="alt-primary"><s:property value="getText('BSC_PROG005D0002Q_btnReport')"/></button>										
			</td>
		</tr>		
		
		</s:iterator>
		</s:if>
	</table>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	