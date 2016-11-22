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

<link rel="stylesheet" href="<%=basePath%>/jsPlumb/css/main.css">
<link rel="stylesheet" href="<%=basePath%>/jsPlumb/css/jsPlumbToolkit-defaults.css">
<link rel="stylesheet" href="<%=basePath%>/jsPlumb/css/jsPlumbToolkit-demo.css">        	

<style type="text/css">

.demo {
    /* for IE10+ touch devices */
    touch-action:none;
}

.w {
    padding: 16px;
    position: absolute;
    z-index: 4;
    border: 1px solid #2e6f9a;
    box-shadow: 2px 2px 19px #e0e0e0;
    -o-box-shadow: 2px 2px 19px #e0e0e0;
    -webkit-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-border-radius: 8px;
    border-radius: 8px;
    opacity: 0.8;
    filter: alpha(opacity=80);
    cursor: move;
    background-color: white;
    font-size: 11px;
    -webkit-transition: background-color 0.25s ease-in;
    -moz-transition: background-color 0.25s ease-in;
    transition: background-color 0.25s ease-in;
    
    width: 450px;
    
}

.w:hover {
    background-color: #5c96bc;
    color: white;

}

.aLabel {
    -webkit-transition: background-color 0.25s ease-in;
    -moz-transition: background-color 0.25s ease-in;
    transition: background-color 0.25s ease-in;
}

.aLabel.jsplumb-hover, .jsplumb-source-hover, .jsplumb-target-hover {
    background-color: #1e8151;
    color: white;
}

.aLabel {
    background-color: white;
    opacity: 0.8;
    padding: 0.3em;
    border-radius: 0.5em;
    border: 1px solid #346789;
    cursor: pointer;
}

.ep {
    position: absolute;
    bottom: 37%;
    right: 5px;
    /*width: 1em;*/
    width: 16px;
    /*height: 1em;*/
    height: 16px;
    /* background-color: orange; */
    background-color: #F5DA81;
    cursor: pointer;
    box-shadow: 0 0 2px black;
    -webkit-transition: -webkit-box-shadow 0.25s ease-in;
    -moz-transition: -moz-box-shadow 0.25s ease-in;
    transition: box-shadow 0.25s ease-in;
    
    background-image: url("./icons/star.png");
    
}

.ep:hover {
    box-shadow: 0px 0px 6px black;
}

.statemachine-demo .jsplumb-endpoint {
    z-index: 3;
}

.dragHover {
    border: 2px solid orange;
}

path, .jsplumb-endpoint { cursor:pointer; }





<s:iterator value="cssItems" status="st" >
	<s:property escapeHtml="false" escapeJavaScript="false" />
</s:iterator>	


</style>


</head>

<body>


<s:if test=" null == fields.visionOid || \"\" == fields.visionOid ">
<div class="alert alert-info" role="alert">
  <p>The MAP data is history, If need editor please change use WEB-version, Mobile-version only view.</p>
</div>	
</s:if>

        <div id="jtk-demo-main">

		

           <!-- demo -->            
			<div class="jtk-demo-canvas canvas-wide statemachine-demo jtk-surface jtk-surface-nopan" id="canvas">
			
				<s:iterator value="divItems" status="st" >
					<s:property escapeHtml="false" escapeJavaScript="false" />
				</s:iterator>	
                
                
            </div>
            <!-- /demo -->



        </div>
        
        
        <script src="<%=basePath%>/jsPlumb/js/jsPlumb-2.1.8.js"></script>

<script type="text/javascript">

jsPlumb.ready(function () {

    // setup some defaults for jsPlumb.
    var instance = jsPlumb.getInstance({
        Endpoint: ["Dot", {radius: 2}],
        HoverPaintStyle: {strokeStyle: "#1e8151", lineWidth: 2 },
        ConnectionOverlays: [
            [ "Arrow", {
                location: 1,
                id: "arrow",
                length: 14,
                foldback: 0.8
            } ],
            [ "Label", { label: "Link line", id: "label", cssClass: "aLabel" }]
        ],
        Container: "canvas"
    });

    window.jsp = instance;

    var windows = jsPlumb.getSelector(".statemachine-demo .w");

    // initialise draggable elements.
    instance.draggable(windows);

    // bind a click listener to each connection; the connection is deleted. you could of course
    // just do this: jsPlumb.bind("click", jsPlumb.detach), but I wanted to make it clear what was
    // happening.
    instance.bind("click", function (c) {
        instance.detach(c);
    });

    // bind a connection listener. note that the parameter passed to this function contains more than
    // just the new connection - see the documentation for a full list of what is included in 'info'.
    // this listener sets the connection's internal
    // id as the label overlay's text.
    instance.bind("connection", function (info) {
        info.connection.getOverlay("label").setLabel(info.connection.id);
        info.connection.hideOverlay("label"); // 不要顯示connection label
    });


    // suspend drawing and initialise.
    instance.batch(function () {
        instance.makeSource(windows, {
            filter: ".ep",
            anchor: "Continuous",
            connector: [ "StateMachine", { curviness: 20 } ],
            connectorStyle: { strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4 },
            maxConnections: 5,
            onMaxConnections: function (info, e) {
            	alert("Maximum connections (" + info.maxConnections + ") reached")                
            }
        });
        
        // and finally, make a couple of connections
        <s:if test=" divItems!=null && divItems.size!=0 ">
        
        // initialise all '.w' elements as connection targets.
        instance.makeTarget(windows, {
            dropOptions: { hoverClass: "dragHover" },
            anchor: "Continuous",
            allowLoopback: true
        });        
        
    	<s:iterator value="conItems" status="st" >
    	instance.connect(<s:property escapeHtml="false" escapeJavaScript="false" />);
    	</s:iterator>	
    	
    	</s:if>
    	
    });

    
    <s:if test=" divItems!=null && divItems.size!=0 ">
    jsPlumb.fire("jsPlumbDemoLoaded", instance);
    </s:if>
    
    
});


var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
	alert(pageMessage);
}	


</script>


</body>
</html>