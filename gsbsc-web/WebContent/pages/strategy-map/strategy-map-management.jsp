<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

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
	
	
	<link rel="stylesheet" href="<%=mainSysBasePath%>/jsPlumb/css/jsplumb.css">
	
	<script type="text/javascript" src="<%=mainSysBasePath%>core.configJsAction.action?ver=${jsVerBuild}"></script>
	
	<script src="<%=mainSysBasePath%>/jsPlumb/external/jquery-1.9.0-min.js"></script>
	<script src="<%=mainSysBasePath%>/jsPlumb/external/jquery-ui-1.9.2.min.js"></script>
	<script src="<%=mainSysBasePath%>/jsPlumb/external/jquery.ui.touch-punch-0.2.2.min.js"></script>	
	
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.svg.js"></script>	   	
	
<style type="text/css">

.flat {
    border: 1px;
    background: #222;
    color: #FFF;
    padding: 2px 20px;
    font-size: 12px;
    font-family: Palatino;    
}

.lighter {
    background: #666;
}



#demo {
	margin-top:0em;
}

.w { 	
	padding:16px;
	position:absolute;
	border: 1px solid black;
	z-index:4;
	border-radius:1em;
	border:1px solid #2e6f9a;
	box-shadow: 2px 2px 19px #e0e0e0;
	-o-box-shadow: 2px 2px 19px #e0e0e0;
	-webkit-box-shadow: 2px 2px 19px #e0e0e0;
	-moz-box-shadow: 2px 2px 19px #e0e0e0;
	-moz-border-radius:8px;
	border-radius:8px;
	opacity:0.8;
	filter:alpha(opacity=80);
	cursor:move;
	background-color:white;
	font-size:11px;
	-webkit-transition:background-color 0.25s ease-in;
	-moz-transition:background-color 0.25s ease-in;
	transition:background-color 0.25s ease-in;	
}

.w:hover {
	background-color: #5c96bc;
	color:white;

}

.aLabel {
	-webkit-transition:background-color 0.25s ease-in;
	-moz-transition:background-color 0.25s ease-in;
	transition:background-color 0.25s ease-in;
}

.aLabel._jsPlumb_hover, ._jsPlumb_source_hover, ._jsPlumb_target_hover {
	background-color:#1e8151;
	color:white;
}

.aLabel {
	background-color:white;
	opacity:0.8;
	padding:0.3em;				
	border-radius:0.5em;
	border:1px solid #346789;
	cursor:pointer;
}

.ep {
	position:absolute;
	bottom: 37%;
	right: 5px;
	width:1em;
	height:1em;
	background-color:orange;
	cursor:pointer;
	box-shadow: 0px 0px 2px black;
	-webkit-transition:-webkit-box-shadow 0.25s ease-in;
	-moz-transition:-moz-box-shadow 0.25s ease-in;
	transition:box-shadow 0.25s ease-in;
}

.ep:hover {
	box-shadow: 0px 0px 6px black;
}

.strategymap-main ._jsPlumb_endpoint {
	z-index:3;
}

<s:iterator value="cssItems" status="st" >
	<s:property escapeHtml="false" escapeJavaScript="false" />
</s:iterator>	

.dragHover { border:2px solid orange; }

path { cursor:pointer; }

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

<body data-demo-id="statemachine" data-library="jquery">
	
	<jsp:include page="../header.jsp"></jsp:include>		
        
        <s:property value="getText('BSC_PROG002D0007Q_vision')"/>:&nbsp;<s:select list="visionMap" name="visionOid" id="visionOid" value="visionOid" class="flat lighter"></s:select>
        <input type="button" name="load" id="load" value="${action.getText('BSC_PROG002D0007Q_btnLoad')}" class="flat lighter" >
        <s:if test=" divItems != null && divItems.size != 0 "> 
        <input type="button" name="save" id="save" value=${action.getText('BSC_PROG002D0007Q_btnSave')} class="flat lighter" >
        <input type="button" name="export" id="export" value="${action.getText('BSC_PROG002D0007Q_btnExport')}" class="flat lighter" >
        </s:if>                
        <input type="button" name="new" id="new" value="${action.getText('BSC_PROG002D0007Q_btnNew')}" class="flat lighter" >
        	
        <div id="main">

		

           <!-- demo -->
            <div class="demo strategymap-main" id="strategymap-main">

				<s:iterator value="divItems" status="st" >
					<s:property escapeHtml="false" escapeJavaScript="false" />
				</s:iterator>	
                
                
            </div>
            <!-- /demo -->



        </div>	
	
	<!-- JS -->
	<!-- support lib for bezier stuff -->
	<script src="<%=mainSysBasePath%>/jsPlumb/lib/jsBezier-0.6.js"></script>
	<!-- event adapter -->
	<script src="<%=mainSysBasePath%>/jsPlumb/lib/mottle-0.4.js"></script>
	<!-- geom functions -->
	<script src="<%=mainSysBasePath%>/jsPlumb/lib/biltong-0.2.js"></script>
	<!-- jsplumb util -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/util.js"></script>
	<script src="<%=mainSysBasePath%>/jsPlumb/src/browser-util.js"></script>
	<!-- base DOM adapter -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/dom-adapter.js"></script>
	<!-- main jsplumb engine -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/jsPlumb.js"></script>
	<!-- endpoint -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/endpoint.js"></script>
	<!-- connection -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/connection.js"></script>
	<!-- anchors -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/anchors.js"></script>
	<!-- connectors, endpoint and overlays  -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/defaults.js"></script>
	<!-- bezier connectors -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/connectors-bezier.js"></script>
	<!-- state machine connectors -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/connectors-statemachine.js"></script>
	<!-- flowchart connectors -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/connectors-flowchart.js"></script>
	<script src="<%=mainSysBasePath%>/jsPlumb/src/connector-editors.js"></script>
	<!-- SVG renderer -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/renderers-svg.js"></script>
	
	<!-- vml renderer -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/renderers-vml.js"></script>
	
	<!-- common adapter -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/base-library-adapter.js"></script>
	<!-- jquery jsPlumb adapter -->
	<script src="<%=mainSysBasePath%>/jsPlumb/src/jquery.jsPlumb.js"></script>
	<!-- /JS -->	
	
<script type="text/javascript">

jsPlumb.ready(function() {

	// setup some defaults for jsPlumb.
	var instance = jsPlumb.getInstance({
		Endpoint : ["Dot", {radius:2}],
		HoverPaintStyle : {strokeStyle:"#1e8151", lineWidth:2 },
		ConnectionOverlays : [
			[ "Arrow", {
				location:1,
				id:"arrow",
                length:14,
                foldback:0.8
			} ],
            [ "Label", { label:"FOO", id:"label", cssClass:"aLabel" }]
		],
		Container:"strategymap-main"
	});

    window.jsp = instance;

	var windows = jsPlumb.getSelector(".strategymap-main .w");

    // initialise draggable elements.
	instance.draggable(windows);

    // bind a click listener to each connection; the connection is deleted. you could of course
	// just do this: jsPlumb.bind("click", jsPlumb.detach), but I wanted to make it clear what was
	// happening.
	instance.bind("click", function(c) {
		instance.detach(c);
	});

	// bind a connection listener. note that the parameter passed to this function contains more than
	// just the new connection - see the documentation for a full list of what is included in 'info'.
	// this listener sets the connection's internal
	// id as the label overlay's text.
    instance.bind("connection", function(info) {
		info.connection.getOverlay("label").setLabel(info.connection.id);
		info.connection.hideOverlay("label"); // 不要顯示connection label
    });


	// suspend drawing and initialise.
	instance.doWhileSuspended(function() {
		var isFilterSupported = instance.isDragFilterSupported();
		// make each ".ep" div a source and give it some parameters to work with.  here we tell it
		// to use a Continuous anchor and the StateMachine connectors, and also we give it the
		// connector's paint style.  note that in this demo the strokeStyle is dynamically generated,
		// which prevents us from just setting a jsPlumb.Defaults.PaintStyle.  but that is what i
		// would recommend you do. Note also here that we use the 'filter' option to tell jsPlumb
		// which parts of the element should actually respond to a drag start.
		// here we test the capabilities of the library, to see if we
		// can provide a `filter` (our preference, support by vanilla
		// jsPlumb and the jQuery version), or if that is not supported,
		// a `parent` (YUI and MooTools). I want to make it perfectly
		// clear that `filter` is better. Use filter when you can.
		if (isFilterSupported) {
			instance.makeSource(windows, {
				filter:".ep",
				anchor:"Continuous",
				connector:[ "StateMachine", { curviness:20 } ],
				connectorStyle:{ strokeStyle:"#5c96bc", lineWidth:2, outlineColor:"transparent", outlineWidth:4 },
				maxConnections:5,
				onMaxConnections:function(info, e) {
					alert("Maximum connections (" + info.maxConnections + ") reached");
				}
			});
		}
		else {
			var eps = jsPlumb.getSelector(".ep");
			for (var i = 0; i < eps.length; i++) {
				var e = eps[i], p = e.parentNode;
				instance.makeSource(e, {
					parent:p,
					anchor:"Continuous",
					connector:[ "StateMachine", { curviness:20 } ],
					connectorStyle:{ strokeStyle:"#5c96bc",lineWidth:2, outlineColor:"transparent", outlineWidth:4 },
					maxConnections:5,
					onMaxConnections:function(info, e) {
						alert("Maximum connections (" + info.maxConnections + ") reached");
					}
				});
			}
		}
	});

	<s:if test=" divItems!=null && divItems.size!=0 ">
	
	// initialise all '.w' elements as connection targets.
	instance.makeTarget(windows, {
		dropOptions:{ hoverClass:"dragHover" },
		anchor:"Continuous",
		allowLoopback:true
	});
	
	
	<s:iterator value="conItems" status="st" >
	instance.connect(<s:property escapeHtml="false" escapeJavaScript="false" />);
	</s:iterator>		
	
	
	jsPlumb.fire("jsPlumbDemoLoaded", instance);
	
	</s:if>
	
    
    function getToJson() {
    	
        var instance=window.jsp;
        var nodes = []

        $(".w").each(function (idx, elem) {
            var $elem = $(elem);
            var endpoints = instance.getEndpoints($elem.attr('id'));
            nodes.push({
                id: $elem.attr('id'),
                text: $elem.text(),
                positionX: parseInt($elem.css("left"), 10),
                positionY: parseInt($elem.css("top"), 10)
            });
        });
        var connections = [];
        $.each(instance.getAllConnections(), function (idx, connection) {
            connections.push({
                connectionId: connection.id,
                sourceId: connection.sourceId,
                targetId: connection.targetId,
            });
        });

        var flowChart = {};
        flowChart.nodes = nodes;
        flowChart.connections = connections;
        return JSON.stringify(flowChart);
    }	
    
    $("#visionOid").change(function(){
    	window.location = "<%=basePath%>/bsc.strategyMapManagementAction.action?visionOid=" + $("#visionOid").val() + "&<%=Constants.IS_IFRAME_MODE%>=Y";
    });
	
    $("#new").click(function (e) {
    	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {
    		alert('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}
    	window.location = "<%=basePath%>/bsc.strategyMapLoadNewAction.action?visionOid=" + $("#visionOid").val() + "&<%=Constants.IS_IFRAME_MODE%>=Y";
    });
    
    $("#load").click(function (e) {
    	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {
    		alert('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}
    	window.location = "<%=basePath%>/bsc.strategyMapLoadRecordAction.action?visionOid=" + $("#visionOid").val() + "&<%=Constants.IS_IFRAME_MODE%>=Y";
    });    
    
    $("#save").click(function (e) {
    	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {
    		alert('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}    	
        e.preventDefault();
        var jsonData = getToJson();
        var mapData = btoa( encodeURIComponent( escape( jsonData ) ) );
        $.ajax({
        	type	: "POST",
        	dataType: 'json',
        	async	: !_gscore_dojo_ajax_sync,
        	timeout	: _gscore_dojo_ajax_timeout,
        	cache	: false,
        	url		: '<%=basePath%>/bsc.strategyMapSaveAction.action',
        	data	: { 
        		'fields.mapData'	: mapData,
        		'fields.visionOid'	: $("#visionOid").val()
        	},
        	success	: function(data) {
        		alert( data.message );
        	},
         	error: function(data) {
         		alert(data);
         	}        	
		});        
        
    });    
    
    $("#export").click(function (e) {
    	
		html2canvas( $('#main'), {
			onrendered: function(canvas) {
		        var a = document.createElement('a');
		        a.download = 'strategy-map.png';
		        a.href = canvas.toDataURL('image/png');
		        document.body.appendChild(a);
		        a.click();							
			},
			allowTaint 	: true,
			useCORS		: true,
			taintTest	: true
		});    	
		
    });
    
});

</script>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	