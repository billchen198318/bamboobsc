<%@page import="com.netsteadfast.greenstep.util.MenuSupportUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String isGoogleWebConnect = (String)request.getParameter("isGoogleWebConnect");

%>

 	<div data-dojo-type="dijit/layout/AccordionContainer" data-dojo-props="minSize:20, region:'leading', splitter:true" style="width: 300px;" selected="true">
        
        <div data-dojo-type="dijit/layout/AccordionPane" title="<img src='./icons/system-run.png' border='0'/>&nbsp;${action.getText('IndexAction_applicationName')}" id="_application_AccordionPane">
        	<div id="menuTree"></div>
        </div>
        
        <div data-dojo-type="dijit/layout/AccordionPane" title="<img src='./icons/help-about.png' border='0'/>&nbsp;${action.getText('IndexAction_messageAccordionPaneTitle')}" id="_message_AccordionPane">
        	<p id="_my_message"></p>
        </div>    
            
        <div data-dojo-type="dijit/layout/AccordionPane" title="<img src='./icons/xfcalendar.png' border='0'/>&nbsp;${accountId} ${action.getText('IndexAction_noteAccordionPaneTitle')}" id="_account_AccordionPane">
			<div data-dojo-type="dijit/Calendar" value="${nowDate2}" id="_my_note_historyCalendar">
			    <script type="dojo/method" data-dojo-event="onChange" data-dojo-args="value">
        			require(["dojo/dom", "dojo/date/locale"], function(dom, locale){
						dom.byId('_my_calendar_note_msg').innerHTML = '';
						var dateStr = locale.format(value, {formatLength: 'full', selector:'date', datePattern:'yyyy-MM-dd'});					
						repaintSysCalendarNoteMessage(dateStr);
        			});
    			</script>
			</div>			
			<p id="_my_calendar_note_msg"></p>        
        </div>
        
        <%=MenuSupportUtils.getTwitterAccordionPane()%>       
        
    </div><!-- end AccordionContainer -->
    
<script type="text/javascript">

function loadMessagePublish() {
	
	var socket = dojox.socket.LongPoll({
	    url:"/core-web/publish?id=PUBMSG0001&account=${accountId}&refreshUUID=${uuid}",
	    headers: {
	        "Accept": "application/json",
	        "Content-Type": "application/json",
	        "Client-Id": Math.random()
	    }    		
	});
	socket.on("open", function(event){
		socket.send("");
	});
	socket.on("message", function(event) {  	
		require(["dojo/json", "dojo/dom", "dojo/on", "dojo/domReady!"], function(JSON, dom, on){
			var obj = JSON.parse(event.data);
			if (obj!=null && obj.message!=null && obj.message!='' ) {
				if (dojo.byId('_my_message').innerHTML.length>=10000) {
					dojo.byId('_my_message').innerHTML = '';
				}
				if ('Y' == obj.success) {
					dojo.byId('_my_message').innerHTML = obj.message + dojo.byId('_my_message').innerHTML;
				}
			}
			
		});
	}); 
	
}

function repaintSysCalendarNoteMessage(dateStr) {
	xhrSendParameterNoWatitDlg(
			'core.systemCalendarNoteHistoryAction.action', 
			{ 'fields.date' : dateStr }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				dojo.byId('_my_calendar_note_msg').innerHTML = data.message;
				if (data.message == '') {	
					dojo.html.set(
							dojo.byId("_my_calendar_note_msg"), 
							'<s:property value="getText('MESSAGE.IndexAction_noNote')" escapeJavaScript="true"/><br/><button data-dojo-type="dijit/form/Button" type="button" onclick="CORE_PROG001D0004A_TabShow();" class="alt-primary"><s:property value="getText('IndexAction_CORE_PROG001D0004A_TabShow_button')" escapeJavaScript="true"/></button>', 
							{extractContent: true, parseContent: true});
					
				}
			}, 
			function(error) {									
			}
	);
}

setTimeout(function(){ 
	loadMessagePublish();
}, 5000);

setTimeout(function(){ 
	repaintSysCalendarNoteMessage('${nowDate2}'); 
}, 3000);

</script>    
    
    