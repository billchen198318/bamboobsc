<div id='gridBody' style='height:${height?c}px;width:${width?c}px' >	
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
			
			document.getElementById("gridBody").innerHTML = data.body;
							
		},
		error: function(e) {
			alert( e.statusText );
		}
		
	});
	
</script>
