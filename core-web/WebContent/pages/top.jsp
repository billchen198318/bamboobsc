<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String isGoogleWebConnect = (String)request.getParameter("isGoogleWebConnect");

%>
<div dojoType="dijit.layout.ContentPane" region="top" id="topBar" style=" overflow:hidden; ">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" style="background: linear-gradient(to top, #ffffff, #f3f3f3);" >
		<tr>
			<td width="70%" align="left">
				<!-- 
				2016-09-14 change dijit/form/ComboButton to dijit/form/DropDownButton
				-->
				<div id="comboButtonMenu" data-dojo-type="dijit/form/DropDownButton" data-dojo-props=" iconClass:'dijitIconConfigure' " class="alt-success">
					<span><s:property value="getText('IndexAction_applicationName')"/></span>				
					<div dojoType="dijit.Menu" >	
							
							${comboButtonMenuData}	
					
							<div dojoType="dijit.MenuSeparator"></div>
							<div dojoType="dijit.MenuItem" jsId="backHome" data-dojo-props='onClick:function(){ window.location="<%=basePath%>pages/way.jsp"; }' ><img src="./icons/view-refresh.png" border="0">&nbsp;<s:property value="getText('IndexAction_refreshPage')"/></div>
							
					</div>					
				</div>			
				&nbsp;&nbsp;
				<!-- 
				2016-09-14 change dijit/form/ComboButton to dijit/form/DropDownButton
				-->
				<div id="comboButtonHelp" data-dojo-type="dijit/form/DropDownButton">
					<span><s:property value="getText('IndexAction_help')"/></span>
					<div dojoType="dijit.Menu" id="helpMenu">
						<div dojoType="dijit.MenuItem" data-dojo-props='onClick:function(){ window.open("https://github.com/billchen198318/bamboobsc/raw/master/core-doc/bamboobsc-guide.pdf"); }' ><s:property value="getText('IndexAction_manual')"/></div>
						<div dojoType="dijit.MenuItem" data-dojo-props='onClick:function(){ window.open("https://github.com/billchen198318/bamboobsc/issues"); }' ><s:property value="getText('IndexAction_issues')"/></div>
						<div dojoType="dijit.MenuItem" data-dojo-props='onClick:function(){ CORE_PROGCOMM0001Q_DlgShow(); }' ><s:property value="getText('IndexAction_about')"/></div>										
					</div>
				</div>								
									
				&nbsp;&nbsp;
				<a href="#" title="logout, end session." alt="logout, end session." onClick='confirmDialog("logoutDialogId000", "${action.getText('IndexAction_logoutTitle')}", "${action.getText('IndexAction_logoutMsg')}", logoutEvent, (window.event ? window.event : null) ); return false;'><img src="./images/logout.png" alt="logoOut" border="0" style="vertical-align: middle;" /></a>
				
				&nbsp;&nbsp;
				<font size='2' color="#394045">${verMsg}</font>			
				
			</td>

			<td width="30%" align="right">
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
			</td>
		</tr>
	</table>				
</div>	
			
<!-- dlg -->			
${dialogData}

<div id="pleaseWaitDlg" dojoType="dojox/widget/DialogSimple" style="width: 420px; height: 150px" title="Please wait">
	<table border="0" width="100%">
		<tr valign="top">
			<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr valign="top">
					<td align="left" valign="middle" bgcolor="#F5F5F5">
						<img src="<%=basePath%>images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font size='2' color='#394045' style="vertical-align:middle;margin-top:0.25em">&nbsp;Process ...</font></b>
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

		