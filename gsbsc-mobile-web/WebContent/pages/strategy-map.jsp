<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>bambooBSC mobile version</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="common-include.jsp"></jsp:include>

<style type="text/css">

</style>

<script type="text/javascript">

function refresh_map() {
	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {    		
		bootbox.alert( 'Please select vision!' );
		document.getElementById('map-iframe').src = "<%=basePath%>/strategymapContent.action";
		return;
	}
	document.getElementById('map-iframe').src = "<%=basePath%>/strategymapContent.action?fields.visionOid=" + $("#visionOid").val();
}


</script>

</head>

<body>

<jsp:include page="topNavbar.jsp">
	<jsp:param value="strategy-map" name="active"/>
</jsp:include>

  <div class="form-group">
    <label for="visionOid">Vision</label>
    <select class="form-control" id="visionOid" onchange="refresh_map();">
    	<s:iterator value="visionMap" status="st" var="cols">
    		<option value="<s:property value="#cols.key"/>" <s:if test=" fields.visionOid == #cols.key"> SELECTED </s:if> ><s:property value="#cols.value"/></option>
    	</s:iterator>
    </select>
  </div>

 <div id="my-iframe" style="-webkit-overflow-scrolling: touch;  overflow: auto;" align="center">
	<iframe id="map-iframe" style='width:800px;height:600px;' src="<%=basePath%>/strategymapContent.action">
	</iframe>
</div>

</body>
</html>