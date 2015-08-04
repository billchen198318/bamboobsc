<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String isGoogleWebConnect = (String)request.getParameter("isGoogleWebConnect");

%>
<div dojoType="dijit.layout.ContentPane" region="top" id="topBar" style=" overflow:hidden; ">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#C5DDF6" > <!-- #CFECEC -->
		<tr>
			<td width="70%" align="left">
			
				<div id="comboButtonMenu" data-dojo-type="dijit.form.ComboButton">
					<span><s:property value="getText('IndexAction_applicationName')"/></span>				
					<div dojoType="dijit.Menu" >	
							
							${comboButtonMenuData}	
					
							<div dojoType="dijit.MenuSeparator"></div>
							<div dojoType="dijit.MenuItem" jsId="backHome" data-dojo-props='onClick:function(){ window.location="<%=basePath%>/pages/way.jsp"; }' ><img src="./icons/view-refresh.png" border="0">&nbsp;<s:property value="getText('IndexAction_refreshPage')"/></div>
							
					</div>					
				</div>			
				&nbsp;|&nbsp;
				<div id="comboButtonHelp" data-dojo-type="dijit.form.ComboButton">
					<span><s:property value="getText('IndexAction_help')"/></span>
					<div dojoType="dijit.Menu" id="helpMenu">
						<div dojoType="dijit.MenuItem" data-dojo-props='onClick:function(){ if (!confirm("${action.getText('IndexAction_issuesMsg')}")) { return; } window.location="https://github.com/billchen198318/bamboobsc/issues"; }' ><s:property value="getText('IndexAction_issues')"/></div>
						<div dojoType="dijit.MenuItem" data-dojo-props='onClick:function(){ CORE_PROGCOMM0001Q_DlgShow(); }' ><s:property value="getText('IndexAction_about')"/></div>										
					</div>
				</div>								
									
				&nbsp;|&nbsp;
				<a href="#" title="logout, end session." alt="logout, end session." onClick='confirmDialog("logoutDialogId000", "${action.getText('IndexAction_logoutTitle')}", "${action.getText('IndexAction_logoutMsg')}", logoutEvent, (window.event ? window.event : null) ); return false;'><img src="./images/logout.png" alt="logoOut" border="0" /></a>
				
				&nbsp;|&nbsp;										
				<font size='2'>${verMsg}</font>			
				
			</td>

			<td width="30%" align="right">
					<!--  
					<img src="./images/original.jpg" alt="logo" />
					-->
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
			</td>
		</tr>
	</table>				
</div>	
			
<!-- dlg -->			
${dialogData}

<div id="pleaseWaitDlg" dojoType="dojox.widget.DialogSimple" style="width: 420px; height: 140px" title="Please wait">
	<table border="0" width="100%">
		<tr valign="top">
			<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr valign="top">
					<td align="left" valign="middle" bgcolor="#F5F5F5">
						<img src="<%=basePath%>/images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font color="#000000">Please wait.</font></b>
						<br/>
						<hr color="#3794E5" size="2">
					</td>
				</tr>
			</table>				
			</td>
		</tr>
		<tr>
			<td align="center">
				<!-- <font size='3'>Please wait.</font> -->
				<br>
				<div id="indeterminateBar" data-dojo-type="dijit/ProgressBar" data-dojo-props='style:"width:380px" ' indeterminate="true" ></div>
				<br>
				<br>
				<br>
				<br>
				<br>
			</td>
		</tr>
	</table>
</div>

		