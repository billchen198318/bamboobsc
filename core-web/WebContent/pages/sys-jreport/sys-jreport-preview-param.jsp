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



function CORE_PROG001D0008Q_S00_preview() {
	var paramData = { };
	<s:if test=" null != jreportParams ">
	<s:iterator value="jreportParams" var="st">
	paramData.${urlParam} = dijit.byId("CORE_PROG001D0008Q_S00_input_${urlParam}").get("value");	
	</s:iterator>
	</s:if>		
	openCommonJasperReportLoadWindow( "Report preview", "${sysJreport.reportId}", "PDF", paramData );
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

	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="100%" cellpadding="1" cellspacing="0" >
   	    	
    	<tr>
    		<td height="25px" width="100%"  align="left" colspan="2">
    			<gs:button name="CORE_PROG001D0008Q_S00_preview" id="CORE_PROG001D0008Q_S00_preview" onClick="CORE_PROG001D0008Q_S00_preview();"
    				label="${action.getText('CORE_PROG001D0008Q_S00_preview')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>    					
    		</td>
    	</tr>     	 	  	    	
    	
    	<tr>
    		<td height="25px" width="50%"  align="center" bgcolor="#C1C1C1"><font color="#ffffff"><b><s:property value="getText('CORE_PROG001D0008Q_S00_previewParam')"/></b></font></td>
    		<td height="25px" width="50%"  align="center" bgcolor="#C1C1C1"><font color="#ffffff"><b><s:property value="getText('CORE_PROG001D0008Q_S00_previewParamValue')"/></b></font></td>    		
    	</tr>	   
    	 	
    	<s:if test=" null != jreportParams ">
    	<s:iterator value="jreportParams" var="st">
    	
    	<tr>
    		<td height="25px" width="50%"  align="center">
    			<b>${urlParam} / ( ${rptParam} )</b>
    		</td>
    		<td height="25px" width="50%"  align="left">
    			<gs:textBox name="CORE_PROG001D0008Q_S00_input_${urlParam}" id="CORE_PROG001D0008Q_S00_input_${urlParam}" maxlength="100" width="250px"></gs:textBox>    			    		
    		</td>    		
    	</tr>	    	
    	
    	</s:iterator>
    	</s:if>
    	
    	
	</table>	
	
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
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>

