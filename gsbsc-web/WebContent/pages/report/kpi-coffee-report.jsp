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
	
	<script type="text/javascript" src="<%=mainSysBasePath%>core.configJsAction.action?ver=${jsVerBuild}"></script>
	
    <script type="text/javascript" src="<%=mainSysBasePath%>/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.js"></script>
    <script type="text/javascript" src="<%=mainSysBasePath%>/html2canvas/html2canvas.svg.js"></script>	  
    
    <script src="<%=mainSysBasePath%>/d3/d3.js"></script>
    	
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

body {
  margin: 0 auto;
  font: 11px sans-serif;  
  line-height: 1.5em;
  color: #333;
  background: #fefefe;
  width: 100%;
  position: relative;
}

path {
  stroke: #000;
  stroke-width: 0.5;
  cursor: pointer;
}

text {
  font: 11px sans-serif;
  cursor: pointer;
}

</style>

<body>

<center>
	<div id="info"></div>
	<div id="vis"></div>
</center>

<script>



var width = 600,
height = width,
radius = width / 2,
x = d3.scale.linear().range([0, 2 * Math.PI]),
y = d3.scale.pow().exponent(1.3).domain([0, 1]).range([0, radius]),
padding = 5,
duration = 1000;

var div = d3.select("#vis");

//div.select("img").remove();

var vis = div.append("svg")
.attr("width", width + padding * 2)
.attr("height", height + padding * 2)
.append("g")
.attr("transform", "translate(" + [radius + padding, radius + padding] + ")");

div.append("p")
.attr("id", "intro")
.text("Click to zoom!");

var partition = d3.layout.partition()
.sort(null)
.value(function(d) { return 5.8 - d.depth; });

var arc = d3.svg.arc()
.startAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x))); })
.endAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x + d.dx))); })
.innerRadius(function(d) { return Math.max(0, d.y ? y(d.y) : d.y); })
.outerRadius(function(d) { return Math.max(0, y(d.y + d.dy)); });

var jsonDataUrl='./bsc.kpiReportCoffeeChartJsonDataAction.action';

jsonDataUrl += '?fields.visionOid=${fields.opw_visionOid}';
jsonDataUrl += '&fields.startYearDate=${fields.opw_startYearDate}';
jsonDataUrl += '&fields.endYearDate=${fields.opw_endYearDate}';
jsonDataUrl += '&fields.startDate=${fields.opw_startDate}';
jsonDataUrl += '&fields.endDate=${fields.opw_endDate}';
jsonDataUrl += '&fields.dataFor=${fields.opw_dataFor}';
jsonDataUrl += '&fields.measureDataOrganizationOid=${fields.opw_measureDataOrganizationOid}';
jsonDataUrl += '&fields.measureDataEmployeeOid=${fields.opw_measureDataEmployeeOid}';
jsonDataUrl += '&fields.frequency=${fields.opw_frequency}';
jsonDataUrl += '&n=${uuid}';

d3.json(jsonDataUrl, function(error, json) {
	var nodes = partition.nodes({children: json});
	
	var path = vis.selectAll("path").data(nodes);
	path.enter().append("path")
	  .attr("id", function(d, i) { return "path-" + i; })
	  .attr("d", arc)
	  .attr("fill-rule", "evenodd")
	  .style("fill", colour)
	  .on("click", click);
	
	var text = vis.selectAll("text").data(nodes);
	var textEnter = text.enter().append("text")
	  .style("fill-opacity", 1)
	  .style("fill", function(d) {
		  return d.fontColor;
	    //return brightness(d3.rgb(colour(d))) < 125 ? "#eee" : "#000";
	  })
	  .attr("text-anchor", function(d) {
	    return x(d.x + d.dx / 2) > Math.PI ? "end" : "start";
	  })
	  .attr("dy", ".2em")
	  .attr("transform", function(d) {
	    var multiline = (d.name || "").split(" ").length > 1,
	        angle = x(d.x + d.dx / 2) * 180 / Math.PI - 90,
	        rotate = angle + (multiline ? -.5 : 0);
	    return "rotate(" + rotate + ")translate(" + (y(d.y) + padding) + ")rotate(" + (angle > 90 ? -180 : 0) + ")";
	  })
	  .on("click", click);
	textEnter.append("tspan")
	  .attr("x", 0)
	  .text(function(d) { return d.depth ? d.name.split(" ")[0] : ""; });
	textEnter.append("tspan")
	  .attr("x", 0)
	  .attr("dy", "1em")
	  .text(function(d) { return d.depth ? d.name.split(" ")[1] || "" : ""; });
	
	function click(d) {
		path.transition().duration(duration).attrTween("d", arcTween(d));
	
		// Somewhat of a hack as we rely on arcTween updating the scales.
		text.style("visibility", function(e) {
			return isParentOf(d, e) ? null : d3.select(this).style("visibility");
	    })
	    .transition()
	    .duration(duration)
	    .attrTween("text-anchor", function(d) {
	    	return function() {
	    		return x(d.x + d.dx / 2) > Math.PI ? "end" : "start";
	    	};
	    })
	    .attrTween("transform", function(d) {
	    	var multiline = (d.name || "").split(" ").length > 1;
	    	return function() {
	    		var angle = x(d.x + d.dx / 2) * 180 / Math.PI - 90,
	    		rotate = angle + (multiline ? -.5 : 0);
	    		return "rotate(" + rotate + ")translate(" + (y(d.y) + padding) + ")rotate(" + (angle > 90 ? -180 : 0) + ")";
	    	};
	    })
	    .style("fill-opacity", function(e) { return isParentOf(d, e) ? 1 : 1e-6; })
	    .each("end", function(e) {
	    	d3.select(this).style("visibility", isParentOf(d, e) ? null : "hidden");
	    });
		
		var node_name=d.name;
		if ('undefined' == node_name || '' == node_name || ' ' == node_name || null == node_name) {
			displayInfo(null);
			return;
		}
		displayInfo(d);
		
	}
	
	displayInfo(null); // first init
	
});

function isParentOf(p, c) {
	if (p === c) return true;
	if (p.children) {
		return p.children.some(function(d) {
			return isParentOf(d, c);
		});
	}
	return false;
}

function colour(d) {
	/*
	if (d.children) {
		// There is a maximum of two children!
		var colours = d.children.map(colour),
		a = d3.hsl(colours[0]),
		b = d3.hsl(colours[1]);
		// L*a*b* might be better here...
		return d3.hsl((a.h + b.h) / 2, a.s * 1.2, a.l / 1.2);
	}
	return d.colour || "#fff";
	*/
	return d.colour;
}

//Interpolate the scales!
function arcTween(d) {
	var my = maxY(d),
	xd = d3.interpolate(x.domain(), [d.x, d.x + d.dx]),
	yd = d3.interpolate(y.domain(), [d.y, my]),
	yr = d3.interpolate(y.range(), [d.y ? 20 : 0, radius]);
	return function(d) {
		return function(t) { x.domain(xd(t)); y.domain(yd(t)).range(yr(t)); return arc(d); };
	};
}

function maxY(d) {
	return d.children ? Math.max.apply(Math, d.children.map(maxY)) : d.y + d.dy;
}

//http://www.w3.org/WAI/ER/WD-AERT/#color-contrast
function brightness(rgb) {
	return rgb.r * .299 + rgb.g * .587 + rgb.b * .114;
}



function displayInfo(d) {
	var content = '';
	content+='<table border=\"0\" cellpadding=\"1\" cellspacing=\"1\" width=\"100%\" bgcolor="#ffffff" >';
	content+='<tr>';
	content+='<td bgcolor="#ffffff" width=\"100%\" align=\"center\" colspan=\"2\" ><input type="button" onclick="exportCoffeeChartsAsPNG();" value="Performance coffee chart - Export image" class="flat lighter"/></font></td>';
	content+='</tr>';	
	if (d!=null) {
		content+='<tr>';
		content+='<td align="center" width="50%" bgcolor="' + d.colour + '" style="border-radius: 5px;"><font color="' + d.fontColor + '" >' + d.name + '</font></td>';
		content+='<td align="center" width="50%" bgcolor="' + d.colour + '" style="border-radius: 5px;"><font color="' + d.fontColor + '" >score: ' + d.score + '</font></td>';
		content+='</tr>';
	}
	content+='</table>';
	document.getElementById("info").innerHTML=content;
}

function exportCoffeeChartsAsPNG() {
	var exportHTML = d3.select("svg")
	.attr("version", 1.1)
	.attr("xmlns", "http://www.w3.org/2000/svg")
	.node().parentNode.innerHTML;
	exportHTML=exportHTML.replace('<p id="intro">Click to zoom!</p>', '');
	
	var image = new Image();
	image.src = 'data:image/svg+xml;charset=UTF-8,' + encodeURIComponent(exportHTML);
	image.onload = function() {
		var canvas = document.createElement('canvas');
		canvas.width = image.width;
        canvas.height = image.height;
        var context = canvas.getContext('2d');
        context.drawImage(image, 0, 0);
        
        var a = document.createElement('a');
        a.download = 'coffee-charts.png';
        a.href = canvas.toDataURL('image/png');
        document.body.appendChild(a);
        a.click();
	}
}

</script>

</body>
</html>