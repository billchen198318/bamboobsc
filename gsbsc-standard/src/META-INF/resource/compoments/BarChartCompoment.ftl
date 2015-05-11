<div id='barChart' >
	<svg style='height:${height?c}px;width:${width?c}px'> </svg>
</div>	
<script>
	$.ajax({
		url: "${basePath}/bsc.kpiReportContentQueryAction.action",
		data: { 
				'fields.visionOid' 					: 	'${visionOid}',
				'fields.startYearDate'				:	'${startYear}',
				'fields.endYearDate'				:	'${endYear}',
				'fields.startDate'					:	'',
				'fields.endDate'					:	'',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	'all',
				'fields.measureDataEmployeeOid'		:	'all',
				'fields.frequency'					:	'6'
		},
		type: "POST",
		dataType: "json",
		async: true,
		timeout: 24000,
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
					.tooltips(false)        //Don't show tooltips
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
