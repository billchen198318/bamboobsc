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

<nav class="navbar navbar-light bg-faded">
  <button class="navbar-toggler hidden-sm-up" type="button" data-toggle="collapse" data-target="#menuCollapsingNavbar" aria-controls="menuCollapsingNavbar" aria-expanded="false" aria-label="Toggle navigation">
    &#9776;
  </button>
  <div class="collapse navbar-toggleable-xs" id="menuCollapsingNavbar">
    <a class="navbar-brand" href="./index.action"><img alt="bambooBSC mobile" src="./images/original.jpg" border="0"></a>
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
<div class="modal fade bs-example-modal-sm" id="myPleaseWait" tabindex="-1"
    role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-time">
                    </span>${action.getText("MESSAGE.INDEX_pleaseWait")}
                 </h4>
            </div>
            <div class="modal-body">
                <div class="progress">
                    <img alt="loading" src="./patch_flat_themes_1_11_icons/loadingAnimation.gif">
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal ends Here -->
