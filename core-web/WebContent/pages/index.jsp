<%@page import="com.netsteadfast.greenstep.util.MenuSupportUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
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
	
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/grid/resources/Grid.css?ver=${jsVerBuild}">
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/widget/Toaster/Toaster.css?ver=${jsVerBuild}" />	
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dojox/widget/Calendar/Calendar.css?ver=${jsVerBuild}" />
	
	<!-- Flat -->
	<link rel="stylesheet" href="<%=basePath%>${dojoLocal}/dijit/themes/flat/flat.css?ver=${jsVerBuild}">
		
	
	<link rel="stylesheet" href="<%=basePath%>/css/core.css?ver=${jsVerBuild}" media="screen">
	
	<link rel="stylesheet" href="<%=basePath%>/d3/nv.d3.css">
	
    <script type="text/javascript" src="<%=basePath%>core.configJsAction.action?ver=${jsVerBuild}&lc=${LocaleCode}&refreshUUID=${uuid}"></script>
    <script type="text/javascript" src="<%=basePath%>core.generateMenuJs.action?ver=${jsVerBuild}&lc=${LocaleCode}&refreshUUID=${uuid}"></script>
    
	<script>
		var dojoConfig = {
			parseOnLoad: true,
		    packages: [
				{ name: "d3", 		location: "<%=basePath%>/d3/",	main: "d3" 		},
				{ name: "nv",		location: "<%=basePath%>/d3/",	main: "nv.d3"	}
		    ]	
	    };
	</script>	    
    <script type="text/javascript" src="<%=basePath%>${dojoLocal}/dojo/dojo.js?ver=${jsVerBuild}"></script>
    <script type="text/javascript" src="<%=basePath%>/js/home.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript" src="<%=basePath%>/js/core.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript" src="<%=basePath%>/js/page.js?ver=${jsVerBuild}" djConfig="parseOnLoad: true"></script>
    
    <s:if test=" \"Y\" == googleMapEnable ">
    <!-- 
    <script type="text/javascript" src="http://www.google.com/jsapi?key=${googleMapKey}"></script>
    -->
    <script type="text/javascript" src="${googleMapUrl}/maps/api/js?key=${googleMapKey}&sensor=false&language=${googleMapLanguage}"></script>       
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
	<script src="<%=basePath%>/highcharts/js/highcharts.js"></script>
	<script src="<%=basePath%>/highcharts/js/highcharts-3d.js"></script>
	<script src="<%=basePath%>/highcharts/js/highcharts-more.js"></script>
	<script src="<%=basePath%>/highcharts/js/modules/heatmap.js"></script>
	<script src="<%=basePath%>/highcharts/js/modules/exporting.js"></script>	
	<script src="<%=basePath%>/highcharts/js/modules/solid-gauge.js"></script>
	
	
	<!-- ################################################################################ -->
	<!-- jqPlot -->
	<script type="text/javascript" src="<%=basePath%>/jqplot/jquery.jqplot.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.meterGaugeRenderer.js"></script>	
	
	<!-- jqPlot plugins -->
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.barRenderer.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.pointLabels.js"></script>	
	
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.dateAxisRenderer.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.canvasTextRenderer.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jqplot/plugins/jqplot.canvasAxisTickRenderer.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/jqplot/jquery.jqplot.css" />
	<!-- ################################################################################ -->
	
	
	<!-- ################################################################################ -->
	<!-- OrgChart -->
	<script type="text/javascript" src="<%=basePath%>/OrgChart/jquery.orgchart.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/OrgChart/jquery.orgchart.css" />
	<!-- ################################################################################ -->
		
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
				
				<s:if test=" \"Y\" == leftAccordionContainerEnable ">
				loadMenuTree();
				</s:if>
				
				<%=MenuSupportUtils.getFirstLoadJavascript()%>
				
				
				<s:if test=" \"Y\" == showConfigHost ">
				setTimeout(function(){ 
					viewPage.addOrUpdateContentPane(			
							'gscoreTabContainer',			
							'CORE_PROGNON-CORE-NOT-WORK_ChildTab',			
							'Warning, Non-core modules will not work',			
							'showConfigHostAction.action',			
							true,			
							true,		
							false);					
				}, 1000);				
				</s:if>				
				
			}
		}).play();
	}, 250);
	//dojox.io.xhrPlugins.addCrossSiteXhr("http://localhost:8080/");
	//dojox.io.xhrPlugins.addCrossSiteXhr("http://localhost:8088/");
});

<s:if test=" \"Y\" == leftAccordionContainerEnable ">

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

</s:if>

var viewPage = new GS.ViewPage('<%=basePath%>');

</script>

</head>

<body class="flat">
<div id="loader"><div id="loaderInner" style="direction:ltr;white-space:nowrap;overflow:visible;">Loading ... </div></div>

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="gutters:false, liveSplitters:false" id="borderContainer">
	
	<jsp:include page="top.jsp"></jsp:include>
	
	<s:if test=" \"Y\" == leftAccordionContainerEnable ">
	
	<jsp:include page="left.jsp"></jsp:include>
	
	</s:if>	
     	
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true" id="${dojoMainTabContainer}" >
    </div>
   
        
</div><!-- end BorderContainer -->

<div data-dojo-type="dojox/widget/Toaster" data-dojo-props="positionDirection:'br-left'" id="mainToaster"></div>

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



