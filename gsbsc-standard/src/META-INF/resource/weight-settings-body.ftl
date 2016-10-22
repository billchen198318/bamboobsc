<input type="hidden" id="BSC_PROG002D0006Q_queryWeight" name="BSC_PROG002D0006Q_queryWeight" value="Y" />

<#list treeObj.visions as vision >
	
	<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#E9E9E9" style="border:1px #E9E9E9 solid; border-radius: 5px;">
		<tr>
			<td colspan="3" bgcolor="#E9E9E9" align="center">
				<b><font color="#333333" size="+2">${vision.title}</font></b>
			</td>
		</tr>
		<tr>
			<td width="33%" align="left" bgcolor="#F6F6F6"><b><font color='#333333' size="3">Perspectives</font></b></td>
			<td width="33%" align="left" bgcolor="#F6F6F6"><b><font color='#333333' size="3">Objectives</font></b></td>
			<td width="33%" align="left" bgcolor="#F6F6F6"><b><font color='#333333' size="3">KPI</font></b></td>
		</tr>
		
	<#list vision.perspectives as perspective >
		<tr>
			<td width="33%" bgcolor="#FFFFFF" rowspan="${perspective.row}" >
				<b>${perspective.name}</b>
				<BR/>
				<font size="2" color="#6D6D6D">${weightName}:</font>
				<input name="PER:${perspective.oid}" 
					type="text"
					data-dojo-type="dijit/form/NumberTextBox"
					value="${perspective.weight}"
					data-dojo-props="constraints:{min:0,max:999,places:0, locale: 'en-us'},
					invalidMessage:'Please enter a numeric value.',
					rangeMessage:'Invalid value.'" 
					style="width: 50px;"
					maxlength="3" />					
			</td>
		
		<#assign p=0 >	
		<#list perspective.objectives as objective >	
			<#if ( p > 0 ) >
				<tr>
			</#if>
			
			<td width="33%" bgcolor="#FFFFFF" rowspan="${objective.row}" >
				<b>${objective.name}</b>
				<BR/>
				<font size="2" color="#6D6D6D">${weightName}:</font>
				<input name="OBJ:${objective.oid}" 
					type="text"
					data-dojo-type="dijit/form/NumberTextBox"
					value="${objective.weight}"
					data-dojo-props="constraints:{min:0,max:999,places:0, locale: 'en-us'},
					invalidMessage:'Please enter a numeric value.',
					rangeMessage:'Invalid value.'" 
					style="width: 50px;"
					maxlength="3" />				
			</td>
					
				<#list objective.kpis as kpi >
					
					<td width="33%" bgcolor="#FFFFFF" >
						<b>${kpi.name}</b>
						<BR/>
						<font size="2" color="#6D6D6D">${weightName}:</font>
						<input name="KPI:${kpi.oid}" 
							type="text"
							data-dojo-type="dijit/form/NumberTextBox"
							value="${kpi.weight}"
							data-dojo-props="constraints:{min:0,max:999,places:0, locale: 'en-us'},
							invalidMessage:'Please enter a numeric value.',
							rangeMessage:'Invalid value.'" 
							style="width: 50px;"
							maxlength="3" />						
					</td>
					
					</tr>
					
				</#list>
			
			<#assign p = p + 1 >
		</#list>
				
	</#list>
		
	</table>
		
</#list>