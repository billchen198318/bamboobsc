<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}" style="border:1px ${backgroundColor} solid; border-radius: 5px;" >
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<img src="./images/go-next.png" border="0" alt="next" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');"/>
		<font color="${fontColor}" size="4"><b>${objective.name}</b></font>
		${objective.imgIcon}
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Target: ${objective.target}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Min: ${objective.min}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Weight: ${objective.weight} %</b></font>
		</td>
	</tr>			
	<!--
	<tr>
		<td width="100%" align="left" bgcolor="${objective.bgColor}">
			<font color="${objective.fontColor}" size="3"><b>Score: ${objective.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${objective.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${objective.fontColor}" style="box-shadow: 0 0 15px 1px ${objective.fontColor}; border-radius: 3px;">
		</td>
	</tr>
	-->
	<tr>
		<td width="100%" align="left" bgcolor="${objective.bgColor}">
			<font color="${objective.fontColor}" size="3"><b>Score: ${objective.score?string(',###.##')}</b></font>&nbsp;
			<font color="${objective.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>		
		</td>
	</tr>			
	<tr>
		<td width="100%" align="left" bgcolor="#ffffff">
			<div class="progress">
			  <div class="progress-bar" role="progressbar" style="width: ${hrWidth}%;" aria-valuenow="${hrWidth}" aria-valuemin="0" aria-valuemax="100">${hrWidth}%</div>
			</div>	
		</td>
	</tr>	
<#if ( objective.dateRangeScores?size > 1 ) >	
	<#list objective.dateRangeScores as dateRange >
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
		<td width="100%" align="left" bgcolor="#ffffff">
		<font size="2" color="#333333"><b>Description:</b></font><BR/>
		<font size="2" color="#333333">
		${objective.description}
		</font>
		</td>
	</tr>
</table>
