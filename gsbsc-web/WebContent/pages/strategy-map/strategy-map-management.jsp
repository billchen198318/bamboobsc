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
	
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no"/>
	
	<link rel="stylesheet" href="<%=mainSysBasePath%>/jsPlumb/css/main.css">
	<link rel="stylesheet" href="<%=mainSysBasePath%>/jsPlumb/css/jsPlumbToolkit-defaults.css">
	<link rel="stylesheet" href="<%=mainSysBasePath%>/jsPlumb/css/jsPlumbToolkit-demo.css">        	
	
	<link href="<%=mainSysBasePath%>/toastr/toastr.min.css" rel="stylesheet"/>
	
	<script type="text/javascript" src="<%=mainSysBasePath%>core.configJsAction.action?ver=${jsVerBuild}"></script>
	
	<script src="<%=mainSysBasePath%>/jquery/jquery-1.11.1.min.js"></script>
	
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.svg.js"></script>	   	
	
	<script src="<%=mainSysBasePath%>/toastr/toastr.min.js"></script>
	
<style type="text/css">

.flat {
    border: 1px;
    background: #222;
    color: #FFF;
    padding: 2px 20px;
    font-size: 12px;
    font-family: Palatino; 
    -webkit-border-radius: 5px;
    border-radius: 5px;          
}

.lighter {
    background: #1e88e5;
    color: #ffffff;
    border: 1px solid #166fbd;
    /* font-weight: bold; */
}

.flatSelect {
    margin: 0px;
    border: 1px solid #111;
    background: url(./images/br_down.png) 96% no-repeat #1e88e5;
    width: 250px;   
    color: #ffffff; 
    font-size: 14px;
    border: 1px solid #166fbd;    
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    -webkit-border-radius: 5px;
    border-radius: 5px;         
    font-family: Consolas,monaco,monospace;
}


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



/* 2017-07-10 取代  jsPlumbToolkit-demo.css 中的 jtk-demo-canvas */
.jtk-demo-canvas {
    margin-left: 0px;
    height:600px;
    max-height:700px;
    border:1px solid #CCC;
    background-color:white;
<s:if test=" null != backgroundOid && \"\" != backgroundOid ">
    /* 2017-07-11 增加背景圖  */
    background-image: url(<%=mainSysBasePath%>/core.commonLoadUploadFileAction.action?type=view&oid=${backgroundOid});
    background-repeat:repeat;
</s:if>
<s:else>
    /* 2017-07-10 增加背景grid圖, 之後會在調整 */
    background-image: url(./images/s-map-bg-grid.png);
    background-repeat:repeat;
</s:else>    
}

<s:iterator value="cssItems" status="st" >
	<s:property escapeHtml="false" escapeJavaScript="false" />
</s:iterator>	


</style>

<script type="text/javascript">


//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		//alert(pageMessage);
		toastr.warning( pageMessage );
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body data-demo-id="statemachine" data-library="dom">
	
	<jsp:include page="../header.jsp"></jsp:include>		
        
<s:if test=" \"N\" == printMode ">
        
    <div class="navbar navbar-top navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">        
        
        <gs:label text="${action.getText('BSC_PROG002D0007Q_vision')}" id="BSC_PROG002D0007Q_vision"></gs:label>
        <s:select list="visionMap" name="visionOid" id="visionOid" value="visionOid" class="flatSelect"></s:select>
        <input type="button" name="load" id="load" value="${action.getText('BSC_PROG002D0007Q_btnLoad')}" class="flat lighter" >
        <s:if test=" divItems != null && divItems.size != 0 "> 
        <input type="button" name="save" id="save" value=${action.getText('BSC_PROG002D0007Q_btnSave')} class="flat lighter" >
        <!-- 2016-04-18 rem -->
        <!-- 
        <input type="button" name="export" id="export" value="${action.getText('BSC_PROG002D0007Q_btnExport')}" class="flat lighter" >
        -->
        <input type="button" name="print" id="print" value="${action.getText('BSC_PROG002D0007Q_btnPrint')}" class="flat lighter" >
        </s:if>                
        <input type="button" name="new" id="new" value="${action.getText('BSC_PROG002D0007Q_btnNew')}" class="flat lighter" >
        	
        	</div>
        </div>
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
        
        
        <script src="<%=mainSysBasePath%>/jsPlumb/js/jsPlumb-2.1.8.js"></script>
        
        
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
            	toastr.info("Maximum connections (" + info.maxConnections + ") reached")                
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
    		toastr.info('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}
    	window.location = "<%=basePath%>/bsc.strategyMapLoadNewAction.action?visionOid=" + $("#visionOid").val() + "&<%=Constants.IS_IFRAME_MODE%>=Y";
    });
    
    $("#load").click(function (e) {
    	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {
    		toastr.info('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}
    	window.location = "<%=basePath%>/bsc.strategyMapLoadRecordAction.action?visionOid=" + $("#visionOid").val() + "&<%=Constants.IS_IFRAME_MODE%>=Y";
    });    
    
    $("#save").click(function (e) {
    	if ( 'all' == $("#visionOid").val() || '' == $("#visionOid").val() ) {
    		toastr.info('<s:property value="getText('MESSAGE.BSC_PROG002D0007Q_vision')" escapeJavaScript="true"/>');
    		return;
    	}    	
        e.preventDefault();
        var jsonData = getToJson();
        var mapData = btoa( encodeURIComponent( jsonData ) );
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
        		if ('Y' == data.success) {
        			toastr.info( data.message );
        		} else if ('N' == data.success || 'E' == data.success ) {
        			toastr.warning( data.message );
        		} else {
        			toastr.error( data.message );
        		}        		
        	},
         	error: function(data) {
         		alert(data);
         	}        	
		});        
        
    });    
    
    // 2016-04-18 rem
    /*
    $("#export").click(function (e) {
    	
		html2canvas( $('#jtk-demo-main'), {
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
    */
    
    $("#print").click(function (e) {
    	
    	var url = "<%=basePath%>/bsc.strategyMapOpenWinDlgAction.action?visionOid=" + $("#visionOid").val();
    	window.open(url, "Strategy Map print", "width=1024", "height=600");
    	
    });
    
    <s:if test=" \"Y\" == printMode ">
    setTimeout( function(){ print(); }, 2000 );    
    </s:if>

    
    <s:if test=" \"Y\" != printMode && divItems!=null && divItems.size!=0">
    // node click event
   $('#canvas > div').each(function () {
	   
	   var idStr = this.id;
	   if ( null == idStr || "" == idStr ) {
		   return;
	   }
	   if ( idStr.indexOf("jsPlumb") > -1 ) {
		   return;
	   }
	   $("#" + idStr).dblclick(function() {
		   parent.BSC_PROG002D0007Q_showObjectiveItem(idStr); // 請參考 page.js
	   });
	   
   });   
   
   toastr.info( "Double Click Strategy objectives item, will show detail." );
   
   </s:if>
    
});


</script>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	