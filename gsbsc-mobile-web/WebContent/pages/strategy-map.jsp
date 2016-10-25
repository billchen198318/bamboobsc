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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="common-include.jsp"></jsp:include>

<script type="text/javascript">

function refresh_map() {

}


</script>

</head>

<body>

<jsp:include page="topNavbar.jsp">
	<jsp:param value="strategy-map" name="active"/>
</jsp:include>

  <div class="form-group">
    <label for="vision">Vision</label>
    <select class="form-control" id="vision">
    	<s:iterator value="visionMap" status="st" var="cols">
    		<option value="<s:property value="#cols.key"/>" ><s:property value="#cols.value"/></option>
    	</s:iterator>
    </select>
  </div>

<button type="button" class="btn btn-primary" onclick="refresh_map();">${action.getText("INDEX_refreshQuery")}</button>



<%
String realPath = getServletContext().getRealPath("/");
if (realPath.indexOf("org.eclipse.wst.server.core")==-1) {
%>
<!-- google analytics for bambooBSC -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-74984756-1', 'auto');
  ga('send', 'pageview');

</script>
<%
} else {
%>
<!-- no need google analytics, when run on wst plugin mode -->
<%
}
%>
</body>
</html>