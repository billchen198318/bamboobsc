<button id="${id}" name="${name}" data-dojo-type="dijit.form.Button" <#if cssClass??> class="${cssClass}" </#if>
	data-dojo-props="
		showLabel: ${showLabel},
			iconClass: '${iconClass}',
			onClick: function(){ 
				${onClick}
			}">${label}</button>				