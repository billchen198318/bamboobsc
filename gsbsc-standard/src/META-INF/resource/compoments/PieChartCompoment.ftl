<div id='pieChart' >
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
		async: !_gscore_dojo_ajax_sync,
		timeout: _gscore_dojo_ajax_timeout,
		cache: false,
		success: function(data) {
			
			if ( data.success != 'Y' ) {
				alert( data.message );
				return;
			}
			
			var myColors = data.perspectivesPieChartBgColor;
			d3.scale.myColors = function() {
				return d3.scale.ordinal().range(myColors);
			};
			nv.addGraph(function() {
				var chart = nv.models.pieChart()
					.x(function(d) { return d.label })
					.y(function(d) { return d.value })
					.showLabels(true).color(d3.scale.myColors().range());
						
				chart.pie.labelsOutside(false).labelType("percent");
					
				d3.select("#pieChart svg")
					.datum(data.perspectivesPieChartValue)
					.transition().duration(350)
					.call(chart);
					
				return chart;
			});	
							
		},
		error: function(e) {
			alert( e.statusText );
		}
		
	});
	
</script>
