<script type="text/javascript">

function ${onClick} {

<#if confirmDialogMode == 'Y' >
	confirmDialog(
			"${id}_dialog", 
			<#if confirmDialogTitle != "" > "${confirmDialogTitle}" <#else> _getApplicationProgramNameById('${programId}') </#if>, 
			"${confirmDialogMsg}", 
			function(success) {
				if (!success) {
					return;
				}
</#if>
	
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

<#if confirmDialogMode == 'Y' >
			}, 
			(window.event ? window.event : null) 
	);
</#if>

}
</script>
