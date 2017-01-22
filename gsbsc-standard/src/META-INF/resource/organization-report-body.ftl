<!-- BSC_PROG003D0003Q -->
<#list treeObj.visions as vision >

	<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#d8d8d8" onclick="BSC_PROGCOMM0001Q_showOrgChart('organization', '${departmentOid}')" >
		<tr>
			<td colspan="6" bgcolor="#F2F2F2" align="center" onclick="BSC_PROGCOMM0001Q_showOrgChart('organization', '${departmentOid}')" >
				<b>
				<font color="#000000" size="+3">
					Department Balance SourceCard
				</font>
				</b>
			</td>
		</tr>	
		<tr>
			<td colspan="6" bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000" size="+2">
					${vision.title}
				</font>
				</b>
			</td>
		</tr>
		<tr>
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					Department
				</font>
				</b>			
			</td>
			<td colspan="4" bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					<a href="#" onclick="BSC_PROGCOMM0001Q_showOrgChart('organization', '${departmentOid}'); return false;" style="color:#000000">${departmentName}</a>
				</font>
				</b>			
			</td>
			<td bgcolor="#F2F2F2" align="center">
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
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					${perspectiveTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					${objectiveTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					${kpiTitle}
				</font>
				</b>			
			</td>	
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					Weight
				</font>
				</b>			
			</td>	
			<td bgcolor="#F2F2F2" align="center">
				<b>
				<font color="#000000">
					Maximum<br/>
					Target<br/>
					Minimum
				</font>
				</b>			
			</td>	
			<td bgcolor="#F2F2F2" align="center">
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
				<td bgcolor="#ffffff" align="left" width="170px">

					<table border="0" width="100%">
						<tr>
							<td align="right" bgcolor="#ffffff" width="30%">
								<font color="#000000" >max:</font>
							<td>
							<td align="left" bgcolor="#ffffff" width="70%">
								<font color="#000000" >${kpi.max?c}</font>
							<td>						
						</tr>
						<tr>
							<td align="right" bgcolor="#ffffff" width="30%">
								<font color="#000000" >target:</font>
							<td>
							<td align="left" bgcolor="#ffffff" width="70%">
								<font color="#000000" >${kpi.target?c}</font>
							<td>						
						</tr>
						<tr>
							<td align="right" bgcolor="#ffffff" width="30%">
								<font color="#000000" >min:</font>
							<td>
							<td align="left" bgcolor="#ffffff" width="70%">
								<font color="#000000" >${kpi.min?c}</font>
							<td>						
						</tr>
						<tr>
							<td align="right" bgcolor="#ffffff" width="30%">
								<font color="#000000" >unit:</font>
							<td>
							<td align="left" bgcolor="#ffffff" width="70%">
								<font color="#000000" >${kpi.unit}</font>
							<td>						
						</tr>																								
					</table>
	
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