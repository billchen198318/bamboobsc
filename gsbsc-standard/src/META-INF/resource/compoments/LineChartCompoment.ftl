<div id="lineChart" style="margin-top:5px; margin-left:5px; width:${width?c}px; height:${height?c}px;"></div>
<script>

/* Need cancel / deprecated this component , because query parameter not same other Bar/Grid/Pie */

	$.ajax({
		url: "${basePath}/bsc.kpiReportContentQueryAction.action",
		data: { 
				'fields.visionOid' 					: 	'${visionOid}',
				'fields.startYearDate'				:	'${startYear}',
				'fields.endYearDate'				:	'${endYear}',
				'fields.startDate'					:	'${startYear}/01/01',
				'fields.endDate'					:	'${endYear}/12/28',
				'fields.dataFor'					:	'all',
				'fields.measureDataOrganizationOid'	:	'all',
				'fields.measureDataEmployeeOid'		:	'all',
				'fields.frequency'					:	'3'
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
			
			
			if ($('#lineChart')!=null) {
				$('#lineChart').empty(); 
			}	
			$.jqplot('lineChart', data.lineChartValues, {
			    title: '${title} - KPIs', 
			    animate: true,
			    axesDefaults: {
		            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
		        },
			    seriesDefaults: {
			          rendererOptions: {
			              //////
			              // Turn on line smoothing.  By default, a constrained cubic spline
			              // interpolation algorithm is used which will not overshoot or
			              // undershoot any data points.
			              //////
			              smooth: true
			          }
			    },
			    legend: {
		            show: true,
		            renderer: $.jqplot.EnhancedLegendRenderer,
		            placement: "outsideGrid",
		            location: "ne",
		            rowSpacing: "0px",
		            rendererOptions: {
		                // set to true to replot when toggling series on/off
		                // set to an options object to pass in replot options.
		                seriesToggle: 'normal',
		                seriesToggleReplot: {resetAxes: true}
		            }
		        },
			    axes:{
			      	xaxis:{
			        	renderer:$.jqplot.DateAxisRenderer, 
			        	tickOptions:{formatString:'%Y-%m'},
			        	min:'Jan 1, ' + ${startYear}, 
			            tickInterval:'1 month'
			      	}
			    },
		        series: [{ lineWidth: 4,
		            markerOptions: { style: 'square' }
		
		        }],
		        series: data.lineChartNames
		    });				
			
			
		},
		error: function(e) {
			alert( e.statusText );
		}
		
	});			
	
</script>	
