<%@page import="com.netsteadfast.greenstep.util.MenuSupportUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
		
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojo/resources/dojo.css">
    <link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dijit/themes/dijit.css">
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dijit/themes/claro/claro.css">
    <link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dijit/themes/dijit_rtl.css">
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dijit/themes/claro/claro_rtl.css">	
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/grid/resources/Grid.css">
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/grid/resources/claroGrid.css">
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/widget/Toaster/Toaster.css" />
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/widget/Calendar/Calendar.css" />
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/calendar/themes/claro/Calendar.css" />
	
	<link rel="stylesheet" href="<%=basePath%>/css/core.css" media="screen">
	
	<link rel="stylesheet" href="<%=basePath%>/d3/nv.d3.css">
	
    <script type="text/javascript" src="<%=basePath%>core.configJsAction.action?ver=${jsVerBuild}"></script>
    <script type="text/javascript" src="<%=basePath%>core.generateMenuJs.action?ver=${jsVerBuild}"></script>
    
	<script>
		var dojoConfig = {
			parseOnLoad: true,
		    packages: [
				{ name: "d3", 		location: "<%=basePath%>/d3/",	main: "d3" 		},
				{ name: "nv",		location: "<%=basePath%>/d3/",	main: "nv.d3"	}
		    ]	
	    };
	</script>	    
    <script type="text/javascript" src="<%=basePath%>${dojoLocal}/dojo/dojo.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/home.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript" src="<%=basePath%>/js/core.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript" src="<%=basePath%>/js/page.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    
    <s:if test=" \"Y\" == googleMapEnable ">
    <script type="text/javascript" src="http://www.google.com/jsapi?key=${googleMapKey}"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=${googleMapKey}&sensor=false&language=${googleMapLanguage}"></script>       
    </s:if>
    <s:else>
    <!-- modify applicationContext-appSettings.properties settings googleMap.enable=Y -->
    </s:else>
	
    <script type="text/javascript" src="<%=basePath%>/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/html2canvas/html2canvas.js"></script>
    <script type="text/javascript" src="<%=basePath%>/html2canvas/html2canvas.svg.js"></script>	    
	
	<script type="text/javascript" src="<%=basePath%>/canvg/canvg.js"></script>
	<script type="text/javascript" src="<%=basePath%>/canvg/rgbcolor.js"></script>
	<script type="text/javascript" src="<%=basePath%>/canvg/StackBlur.js"></script>
	
	
	<script type="text/javascript" src="<%=basePath%>/signature_pad/signature_pad.js"></script>
	
	
	<!-- Highcharts -->
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>	
	
	
<style type="text/css">

</style>

<script type="text/javascript">

dojo.addOnLoad(function(){
	
	dojo.byId('loaderInner').innerHTML += " OK!";
	setTimeout(function hideLoader(){
		dojo.fadeOut({ 
			node: 'loader', 
			duration:1000,
			onEnd: function(n){
				n.style.display = "none";
				loadMenuTree();
				<%=MenuSupportUtils.getFirstLoadJavascript()%>
			}
		}).play();
	}, 250);
	//dojox.io.xhrPlugins.addCrossSiteXhr("http://localhost:8080/");
	//dojox.io.xhrPlugins.addCrossSiteXhr("http://localhost:8088/");
});

function loadMenuTree() {
	
	var treeData = ${treeJsonData};
	
    var menuTreeStore = new dojo.data.ItemFileReadStore
    ({
         data: {
             identifier: 'id',
             label: 'label',
             items: treeData
         }  
    });

    var treeModel = new dijit.tree.ForestStoreModel({
    	store: menuTreeStore
    });  
    
    var menuTreeObj = new dijit.Tree(
    		{
                model: treeModel,
                showRoot: false,
                openOnClick: true,
                autoExpand: true,
                onClick: function(treeNodeItem,treeNode) {
                	/*
                    var url = menuTreeStore.getValue(treeNodeItem, "url");
                    alert( url );
                    */
                    var tabShowEvent = menuTreeStore.getValue(treeNodeItem, "onclick");
                    if (null!=tabShowEvent && ""!=tabShowEvent && " "!=tabShowEvent && (typeof tabShowEvent!='undefined') ) {
                    	eval( tabShowEvent );
                    }                    
                },
                _createTreeNode: function(args) {
                    var tnode = new dijit._TreeNode(args);
                    tnode.labelNode.innerHTML = args.label;
                    return tnode;
                }    			
    		},
    		dojo.byId("menuTree")
    );
    
}

var viewPage = new GS.ViewPage('<%=basePath%>');

</script>

</head>

<body class="claro" bgcolor="#EEEEEE" >
<div id="loader"><div id="loaderInner" style="direction:ltr;white-space:nowrap;overflow:visible;">Loading ... </div></div>

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="gutters:false, liveSplitters:false" id="borderContainer">
	
	<jsp:include page="top.jsp"></jsp:include>
    <jsp:include page="left.jsp"></jsp:include>
     	
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true" id="${dojoMainTabContainer}" >
    </div>
   
        
</div><!-- end BorderContainer -->

<div data-dojo-type="dojox/widget/Toaster" data-dojo-props="positionDirection:'br-left'" id="mainToaster"></div>

</body>

</html>



