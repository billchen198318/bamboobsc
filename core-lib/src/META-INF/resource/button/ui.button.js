<script type="text/javascript">

function ${onClick} {
	
<#if parameterType == 'postData' >
	var queryParameters=${xhrParameter};
	xhrSendParameter(
			'${xhrUrl}', 
			queryParameters, 
			'${handleAs}', 
			${timeout}, 
			${sync}, 
			${preventCache}, 
			function(data) {
				${loadFn}
			},
			function(error) {
				alert(error);
				${errorFn}
			}
	);
</#if>  
<#if parameterType == 'form' >
	xhrSendForm(
			'${xhrUrl}', 
			'${xhrParameter}', 
			'${handleAs}', 
			${timeout}, 
			${sync}, 
			${preventCache}, 
			function(data) {
				${loadFn}
			}, 
			function(error) {
				alert(error);
				${errorFn}
			}
	);
</#if>

}
</script>
