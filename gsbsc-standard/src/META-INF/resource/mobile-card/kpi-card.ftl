<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}" style="border:1px ${backgroundColor} solid; border-radius: 5px;" >
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="4"><b>${kpi.name}</b></font>
		${kpi.imgIcon}
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Max: ${kpi.max}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Target: ${kpi.target}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Min: ${kpi.min}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Weight: ${kpi.weight} %</b></font>
		</td>
	</tr>			
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>management: ${kpi.managementName}</b></font>
		</td>
	</tr>			
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Compare type: ${compareTypeName}</b></font>
		</td>
	</tr>			
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Calculation: ${kpi.calculationName}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Unit: ${kpi.unit}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Formula: ${kpi.formula.name}</b></font>
		</td>
	</tr>	
	<!--
	<tr>
		<td width="100%" align="left" bgcolor="${kpi.bgColor}">
			<font color="${kpi.fontColor}" size="3"><b>Score: ${kpi.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${kpi.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${kpi.fontColor}" style="box-shadow: 0 0 15px 1px ${kpi.fontColor}; border-radius: 3px;">
		</td>
	</tr>	
	-->
	<tr>
		<td width="100%" align="left" bgcolor="${kpi.bgColor}">
			<font color="${kpi.fontColor}" size="3"><b>Score: ${kpi.score?string(',###.##')}</b></font>&nbsp;
			<font color="${kpi.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>
		</td>
	</tr>
	<tr>
		<td width="100%" align="left" bgcolor="#ffffff">
			<div class="progress">
			  <div class="progress-bar" role="progressbar" style="width: ${hrWidth}%;" aria-valuenow="${hrWidth}" aria-valuemin="0" aria-valuemax="100">${hrWidth}%</div>
			</div>
		</td>
	</tr>		
<#if ( kpi.dateRangeScores?size > 1 ) >	
	<#list kpi.dateRangeScores as dateRange >
	<tr>
		<td width="100%" align="left" bgcolor="${dateRange.bgColor}">
			<div class="text-xs-center" id="example-progress-vision">
			<font color="${dateRange.fontColor}" size="3"><b>Date: ${dateRange.date}&nbsp;Score: ${dateRange.score?string(',###.##')}</b></font>
			</div>
		</td>
	</tr>	
	</#list>
</#if>			
	<tr valign="top">
		<td width="100%" align="center" bgcolor="#ffffff">
			<img src="./bsc.mobile.commonMeterChartAction.action?oid=${chartDataOid}" border="0" alt="score-meter-chart" />
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="#ffffff">
		<font size="2" color="#333333"><b>Description:</b></font><BR/>
		<font size="2" color="#333333">
		${kpi.description}
		</font>
		</td>
	</tr>
</table>
