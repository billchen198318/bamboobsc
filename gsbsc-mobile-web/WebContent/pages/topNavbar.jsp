<%@page import="com.netsteadfast.greenstep.util.LocaleLanguageUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String active = request.getParameter("active");

%>

<script type="text/javascript">

function logoutEvent() {
	bootbox.confirm(
			"<s:property value="getText('INDEX_Logout')" escapeJavaScript="true"/>, <s:property value="getText('MESSAGE.INDEX_logoutPage')" escapeJavaScript="true"/>", 
			function(result){ 
				if (!result) {
					return;
				}
				window.location = "${basePath}/logout.action";
			}
	);	
}

</script>

<nav class="navbar navbar-toggleable-md navbar-light bg-faded">

  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="./index.action"><img alt="bambooBSC mobile" src="./images/original.jpg" border="0"></a>
  
  
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    
    <ul class="nav navbar-nav">
      <li class="nav-item <% if ("home".equals(active)) { %> active <%} %>">
        <a class="nav-link" href="./index.action"><b>Scorecard</b></a>
      </li>
      <li class="nav-item <% if ("dashboard".equals(active)) { %> active <%} %>">
        <a class="nav-link" href="./dashboard.action"><b>Dashboard</b></a>
      </li>          
      <li class="nav-item <% if ("strategy-map".equals(active)) { %> active <%} %>">
        <a class="nav-link" href="./strategymap.action"><b>Strategy MAP</b></a>
      </li>            
      <li class="nav-item <% if ("about".equals(active)) { %> active <%} %>">
        <a class="nav-link" href="./about.action"><b>About</b></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="logoutEvent();"><b>${action.getText("INDEX_Logout")}</b></a>
      </li>        
    </ul>
    
  </div>  
  
</nav>


<!-- Modal Start here-->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="myPleaseWait" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallModalLabel">${action.getText("MESSAGE.INDEX_pleaseWait")}</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./patch_flat_themes_1_11_icons/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here -->
