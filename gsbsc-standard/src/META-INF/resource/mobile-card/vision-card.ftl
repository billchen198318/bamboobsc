<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}">
	<tr valign="top">
		<td width="100%" align="center" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="4"><b>${vision.title}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>${perspectiveTitle}: ${perspectiveSize}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>${objectiveTitle}: ${objectiveSize}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="right" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>${kpiTitle}: ${kpiSize}</b></font>
		</td>
	</tr>
	<tr>
		<td width="100%" align="left" bgcolor="${vision.bgColor}">
			<font color="${vision.fontColor}" size="3"><b>Score: ${vision.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${vision.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${percentage}%" size="15" color="${vision.fontColor}">
		</td>
	</tr>
	<tr valign="top">
		<td width="100%" bgcolor="#ffffff">
		${visionContent}
		</td>
	</tr>
</table>
