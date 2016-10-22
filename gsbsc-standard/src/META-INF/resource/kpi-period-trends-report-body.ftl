<!-- BSC_PROG003D0007Q -->

<table width="1100px" cellspacing="1" cellpadding="1" style="background-color:#d8d8d8" >
	<tr>
		<td bgcolor="#f5f5f5" align="left" width="320px"><b>KPI</b></td>
		<td bgcolor="#f5f5f5" align="left"><b>Maximum</b></td>
		<td bgcolor="#f5f5f5" align="left"><b>Target</b></td>
		<td bgcolor="#f5f5f5" align="left"><b>Minimum</b></td>	
		<td bgcolor="#f5f5f5" align="left"><b>Current score</b></td>
		<td bgcolor="#f5f5f5" align="left"><b>Previous score</b></td>
		<td bgcolor="#f5f5f5" align="left"><b>Change(%)</b></td>
	</tr>
	
	<#list periodDatas as periodData >
	
	<tr>
		<td bgcolor="#ffffff" align="left">${periodData.current.name}</td>
		<td bgcolor="#ffffff" align="right">${periodData.current.max}</td>
		<td bgcolor="#ffffff" align="right">${periodData.current.target}</td>
		<td bgcolor="#ffffff" align="right">${periodData.current.min}</td>
		<td bgcolor="#ffffff" align="right">${periodData.current.score?string(',###.##')}</td>
		<td bgcolor="#ffffff" align="right">${periodData.previous.score?string(',###.##')}</td>
		<td bgcolor="#ffffff" align="right">${periodData.change?string(',###.##')}</td>
	</tr>
	
	</#list>
	<tr>
		<td bgcolor="#f5f5f5" align="left" width="100%" colspan="7"><b>Current period:</b> ${currentPeriodDateRange} <b>, Previous period:</b> ${previousPeriodDateRange}</td>
	</tr>
	
</table>
