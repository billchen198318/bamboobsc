<!-- BSC_PROG003D0003Q -->
<#list treeObj.visions as vision >

	<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#171717">
		<tr>
			<td colspan="6" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" size="+3">
					Department Balance SourceCard
				</font>
				</b>
			</td>
		</tr>	
		<tr>
			<td colspan="6" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" size="+2">
					${vision.title}
				</font>
				</b>
			</td>
		</tr>
		<tr>
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					Department
				</font>
				</b>			
			</td>
			<td colspan="4" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					${departmentName}
				</font>
				</b>			
			</td>
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					<#if dateType == "1" >
					${year}&nbsp;In the first half
					<#elseif dateType == "2" >
					${year}&nbsp;In the second half
					<#else>
					${year}&nbsp;Year
					</#if>
				</font>
				</b>			
			</td>						
		</tr>	
		<tr>
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					${perspectiveTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					${objectiveTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					${kpiTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					Weight
				</font>
				</b>			
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					Target
				</font>
				</b>			
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000">
					Score
				</font>
				</b>			
			</td>																
		</tr>
		
	<#list vision.perspectives as perspective >
		<tr>
			<td rowspan="${perspective.row}" bgcolor="#ffffff" align="left">
				<font color="#000000">
					${perspective.name}
				</font>		
			</td>				

		<#assign p=0 >	
		<#list perspective.objectives as objective >	
			<#if ( p > 0 ) >
				<tr>
			</#if>		
				
				<td rowspan="${objective.row}" bgcolor="#ffffff" align="left">
					<font color="#000000">
						${objective.name}
					</font>		
				</td>				
				
				
				<#list objective.kpis as kpi >
				
				<td bgcolor="#ffffff" align="left">
					<font color="#000000">
						${kpi.name}
					</font>		
				</td>				
				<td bgcolor="#ffffff" align="left">
					<font color="#000000">
						${kpi.weight}%
					</font>		
				</td>				
				<td bgcolor="#ffffff" align="left">
					<font color="#000000">
						${kpi.target} / ${kpi.unit}
					</font>		
				</td>	
				<td bgcolor="${kpi.dateRangeScores[0].bgColor}" align="center">
					<font color="${kpi.dateRangeScores[0].fontColor}">
						${kpi.dateRangeScores[0].score?string(',###.##')}
					</font>		
				</td>	
									
			</tr>
			
			</#list>
				
		</#list>
		
	</#list>
		
	</table>	

</#list>