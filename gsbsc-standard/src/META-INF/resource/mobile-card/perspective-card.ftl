<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}">
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_objectiveByPerspective('${uploadOid}', '${perspective.oid}');">
		<img src="./images/go-next.png" border="0" alt="next" onclick="query_objectiveByPerspective('${uploadOid}', '${perspective.oid}');"/>
		<font color="${fontColor}" size="4"><b>${perspective.name}</b></font>
		${perspective.imgIcon}
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_objectiveByPerspective('${uploadOid}', '${perspective.oid}');">
		<font color="${fontColor}" size="2"><b>Target: ${perspective.target}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_objectiveByPerspective('${uploadOid}', '${perspective.oid}');">
		<font color="${fontColor}" size="2"><b>Min: ${perspective.min}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_objectiveByPerspective('${uploadOid}', '${perspective.oid}');">
		<font color="${fontColor}" size="2"><b>Weight: ${perspective.weight} %</b></font>
		</td>
	</tr>			
	<tr>
		<td width="100%" align="left" bgcolor="${perspective.bgColor}">
			<font color="${perspective.fontColor}" size="3"><b>Score: ${perspective.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${perspective.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${perspective.fontColor}">
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="#ffffff">
		description:<BR/>
		${perspective.description}
		</td>
	</tr>
</table>
