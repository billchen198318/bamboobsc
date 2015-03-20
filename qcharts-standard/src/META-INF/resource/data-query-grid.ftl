<table boder='0' width='100%' cellspacing='1' cellpadding='0' bgcolor='#DCDCDC' >

	<tr>
		<#list labels as label>
			<td align='center' bgcolor='#959595'><B>${label}</B></td>
		</#list>
	</tr>
	
	<#list searchDatas as data>
	
	<tr>
		<#list labels as label>
			<td align='left' bgcolor='#ffffff'>${data[label]}</td>
		</#list>
	</tr>
	
	</#list>
	
</table>