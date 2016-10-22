<select data-dojo-type="dijit/form/FilteringSelect" id="${id}" name="${name}" data-dojo-id="${id}" 
	data-dojo-props='style:"width: ${width}px;" ' value="${value}" <#if onChange?? > onChange="${onChange}" </#if> <#if readonly == "Y" > readonly="readonly" </#if> >
	<#if dataSource?? && dataSource?size gt 0>
		<#list dataSource?keys as key>
			<option value="${key}">${dataSource[key]}</option>
		</#list>		
	</#if>  				 
</select>