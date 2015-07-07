<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}">
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
	<tr>
		<td width="100%" align="left" bgcolor="${vision.bgColor}">
			<font color="${vision.fontColor}" size="3"><b>Score: ${vision.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${vision.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${vision.fontColor}">
		</td>
	</tr>
	<tr valign="top">
		<td width="100%" bgcolor="#ffffff">
		${visionContent}
		</td>
	</tr>
</table>
