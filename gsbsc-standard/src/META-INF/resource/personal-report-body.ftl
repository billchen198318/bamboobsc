<!-- BSC_PROG003D0002Q -->
<#list treeObj.visions as vision >

	<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#171717">
		<tr>
			<td colspan="6" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" size="+3">
					Personal Balance SourceCard
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
				<font color="#000000" >
					Job title
				</font>
				</b>
			</td>
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					${jobTitle}
				</font>
				</b>
			</td>			
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Department
				</font>
				</b>
			</td>			
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					${departmentName}
				</font>
				</b>
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					name: ${fullName}
				</font>
				</b>
			</td>	
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Annual assessment: ${year}
				</font>
				</b>
			</td>										
		</tr>		
		<tr>
			<td rowspan="2" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					${objectiveTitle}
				</font>
				</b>
			</td>
			<td rowspan="2" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					${kpiTitle}
				</font>
				</b>
			</td>			
			<td rowspan="2" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Target
				</font>
				</b>
			</td>
			<td rowspan="2" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Weight
				</font>
				</b>
			</td>
			<td rowspan="2" bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Formula
				</font>
				</b>
			</td>
			<td bgcolor="#C8C8C8" align="center">
				<b>
				<font color="#000000" >
					Score
				</font>
				</b>
			</td>												
		</tr>			
		<tr>
			<td bgcolor="#F5F4F4" align="center">
				<b>
				<font color="#000000" >
					<#if dateType == "1" >
					In the first half
					<#elseif dateType == "2" >
					In the second half
					<#else>
					Year
					</#if>
				</font>
				</b>
			</td>											
		</tr>
		
<#list vision.perspectives as perspective >
	<#list perspective.objectives as objective >
	
		<tr>
			<td rowspan="${objective.row}" bgcolor="#ffffff" align="left">
				<font color="#000000" >
					${objective.name}
				</font>
			</td>		
	
		<#assign kx = 0 >	
		<#list objective.kpis as kpi >
		
			<#if ( kx > 0 ) >
				<tr>
			</#if>
			
			
			<td bgcolor="#ffffff" align="left">
				<font color="#000000" >
					${kpi.name}
				</font>
			</td>				
			<td bgcolor="#ffffff" align="left">
				<font color="#000000" >
					${kpi.target?c} / ${kpi.unit}
				</font>
			</td>
			<td bgcolor="#ffffff" align="left">
				<font color="#000000" >
					${kpi.weight?c}%
				</font>
			</td>
			<td bgcolor="#ffffff" align="left">
				<font color="#000000" >
					${kpi.formula.name}
				</font>
			</td>	
			<td bgcolor="${kpi.dateRangeScores[0].bgColor}" align="center">
				<font color="${kpi.dateRangeScores[0].fontColor}" >
					${kpi.dateRangeScores[0].score?string(',###.##')}
				</font>
			</td>	
		</tr>	
		
			<#assign kx = kx + 1 >
																			
		</#list>
		
	</#list>	
</#list>

		<tr>
			<td rowspan="2" bgcolor="#ffffff" align="center">
				<b>
				<font color="#000000" >
					assess:
				</font>
				</b>
			</td>
			<td rowspan="2" colspan="3" bgcolor="#ffffff" align="center">
				<b>
				<font color="#000000" >
					${classLevel}
				</font>
				</b>
			</td>			
			<td bgcolor="#ffffff" align="center">
				<b>
				<font color="#000000" >
					Total
				</font>
				</b>
			</td>			
			<td bgcolor="#ffffff" align="center">
				<font color="#000000" >
					${total?string(',###.##')}
				</font>
			</td>						
		</tr>
		<tr>
			<td bgcolor="#ffffff" align="center">
				<b>
				<font color="#000000" >
					Class
				</font>
				</b>
			</td>	
			<td bgcolor="#ffffff" align="center">
				<font color="#000000" >
					&nbsp;
				</font>
			</td>						
		</tr>
					
	</table>
		
</#list>