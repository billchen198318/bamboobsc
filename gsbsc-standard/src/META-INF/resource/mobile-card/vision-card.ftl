<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}" style="border:1px ${backgroundColor} solid; border-radius: 5px;" >
	<tr valign="top">
		<td width="100%" align="center" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<img src="./images/go-next.png" border="0" alt="next" onclick="query_perspective('${uploadOid}');"/>
		<font color="${fontColor}" size="4"><b>${vision.title}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<font color="${fontColor}" size="2"><b>${perspectiveTitle}: ${perspectiveSize}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<font color="${fontColor}" size="2"><b>${objectiveTitle}: ${objectiveSize}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<font color="${fontColor}" size="2"><b>${kpiTitle}: ${kpiSize}</b></font>
		</td>
	</tr>
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<font color="${fontColor}" size="2"><b>Frequency: ${frequencyName}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}" onclick="query_perspective('${uploadOid}');">
		<font color="${fontColor}" size="2"><b>Date range: ${startDate} - ${endDate}</b></font>
		</td>
	</tr>	
	<!--
	<tr>
		<td width="100%" align="left" bgcolor="${vision.bgColor}">
			<font color="${vision.fontColor}" size="3"><b>Score: ${vision.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${vision.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>		
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${vision.fontColor}" style="box-shadow: 0 0 15px 1px ${vision.fontColor}; border-radius: 3px;">
		</td>
	</tr>
	-->
	<tr>
		<td width="100%" align="left" bgcolor="${vision.bgColor}">
			<font color="${vision.fontColor}" size="3"><b>Score: ${vision.score?string(',###.##')}</b></font>&nbsp;
			<font color="${vision.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>
		</td>
	</tr>		
	<tr>
		<td width="100%" align="left" bgcolor="#ffffff">
			<div class="progress">
			  <div class="progress-bar" role="progressbar" style="width: ${hrWidth}%;" aria-valuenow="${hrWidth}" aria-valuemin="0" aria-valuemax="100">${hrWidth}%</div>
			</div>
		</td>
	</tr>	
<#if ( vision.dateRangeScores?size > 1 ) >	
	<#list vision.dateRangeScores as dateRange >
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
		<td width="100%" bgcolor="#ffffff">
		${visionContent}
		</td>
	</tr>
</table>
