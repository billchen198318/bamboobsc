<div id='barChart' >
	<svg style='height:${height?c}px;width:${width?c}px'> </svg>
</div>	
<script>
	$.ajax({
		url: "${basePath}/bsc.kpiReportContentLoadFromUploadAction.action",
		data: { 
				'fields.visionOid' 					: 	'${visionOid}',		
				'fields.uploadOid' 					: 	'${uploadOid}'
		},
		type: "POST",
		dataType: "json",
		async: !_gscore_dojo_ajax_sync,
		timeout: _gscore_dojo_ajax_timeout,
		cache: false,
		success: function(data) {
			
			if ( data.success != 'Y' ) {
				alert( data.message );
				return;
			}
			
		    var myColors = data.perspectivesBarChartBgColor;
		    d3.scale.myColors = function() {
		        return d3.scale.ordinal().range(myColors);
		    };
		    
			nv.addGraph(function() {
				var chart = nv.models.discreteBarChart()
					.x(function(d) { return d.label })    //Specify the data accessors.
					.y(function(d) { return d.value })
					.staggerLabels(true)    //Too many bars and not enough room? Try staggering labels.
					.showValues(true)       //...instead, show the bar value right on top of each bar.
					.color( myColors );
		
				d3.select('#barChart svg')
					.datum(data.perspectivesBarChartValue)
					.call(chart);
		
				nv.utils.windowResize(chart.update);
		
				return chart;
			});	
							
		},
		error: function(e) {
			alert( e.statusText );
		}
		
	});
	
</script>
