<input type="hidden" name="BSC_PROG002D0005Q_dataFor" id="BSC_PROG002D0005Q_dataFor" value="${dataFor}" />
<input type="hidden" name="BSC_PROG002D0005Q_frequency" id="BSC_PROG002D0005Q_frequency" value="${frequency}" />
<input type="hidden" name="BSC_PROG002D0005Q_empId" id="BSC_PROG002D0005Q_empId" value="${empId}" />
<input type="hidden" name="BSC_PROG002D0005Q_orgId" id="BSC_PROG002D0005Q_orgId" value="${orgId}" />
<input type="hidden" name="BSC_PROG002D0005Q_queryCalendar" id="BSC_PROG002D0005Q_queryCalendar" value="Y" />
<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#232323">		
	<tr>
		<td colspan="7" bgcolor="#3796FF" align="center">
			<b><font color="#FFFFFF" size="+1">${kpi.name}</font></b>
		</td>
	</tr>	
	<tr>
		<td colspan="7" bgcolor="#3796FF" align="center">
			<font color="#FFFFFF" size="2"><b>Management:</b>${managementName}, <b>Calculation:</b>${calculationName}, <b>Unit:</b>${kpi.unit}, <b>Target:${kpi.target}, <b>Min:</b>${kpi.min}</font>
		</td>
	</tr>				
	<tr>
		<td colspan="7" bgcolor="#3796FF" align="center">
			<font color="#FFFFFF" size="2"><b>Formula name:</b>${formula.name}</font>
		</td>
	</tr>		
	<tr>
		<td colspan="7" bgcolor="#3796FF" align="center">
			<img src="./images/go-previous.png" alt="prev" border="0" onclick="BSC_PROG002D0005Q_prevCalendar();" />
			&nbsp;
			<b><font color="#FFFFFF" size="+3">${yyyy}/${mm}</font></b>
			&nbsp;
			<img src="./images/go-next.png" alt="next" border="0" onclick="BSC_PROG002D0005Q_nextCalendar();" />
		</td>
	</tr>	
	<tr>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Sunday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Monday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Tuesday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Wednesday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Thursday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Friday</font></b></div></td>
		<td bgcolor="#3796FF" width="14%"><div align="center"><b><font color="#FFFFFF" size="+1">Saturday</font></b></div></td>
	</tr>	
	
<#assign len=1 >
<#assign count=1 >
<#assign dayCount=1 >
<#assign nextMonthDay=1 >
<#assign prevMonthDayMinus=dayOfWeek-2 >

<#list len..showLen as i>		<!-- 當月日曆份所需幾星期列 -->

	<tr>
		<#assign day=1 >
		<#list day..7 as d >		<!-- 每列7日 -->
			
			<#if (count >= dayOfWeek) && (dayCount <= maxday) >		<!-- 資料起於當月開始日(dayOfWeek), 並迄於當月最大日(maxday) -->				
				<#assign dayStr = yyyy+mm+dayCount?string?left_pad(2, "0") >
				
				<#assign targetValue = "" >
				<#assign actualValue = "" >
				<#list masureDatas as masure >
					<#if masure.date == dayStr >
						<#assign targetValue = masure.target >
						<#assign actualValue = masure.actual >					
					</#if>
				</#list>				

				
				<td bgcolor="#CDD7E2" align="center">
					<b><font color="#FF8000" >${dayCount?string?left_pad(2, "0")}</font></b>					
					<table border="0" width="85px">
						<tr>
							<td width="15px" align="center">
								T:
							</td>
							<td width="70px" align="center">
								<input name="MEASURE_DATA_TARGET:${dayStr}" 
									type="text"
								    data-dojo-type="dijit/form/NumberTextBox"
								    value="${targetValue}"
								    data-dojo-props="constraints:{pattern: '#.##',min:-999999999.00,max:999999999.00},
								    invalidMessage:'Please enter a numeric value.',
								    rangeMessage:'Invalid value.'" 
								    style="width: 70px;"
								    maxlength="9" />								
							</td>														
						</tr>
						<tr>
							<td width="15px" align="center">
								A:	
							</td>
							<td width="70px" align="center">
								<input name="MEASURE_DATA_ACTUAL:${dayStr}" 
								type="text"
							    data-dojo-type="dijit/form/NumberTextBox"
							    value="${actualValue}"
							    data-dojo-props="constraints:{pattern: '#.##',min:-999999999.00,max:999999999.00},
							    invalidMessage:'Please enter a numeric value.',
							    rangeMessage:'Invalid value.'" 
							    style="width: 70px;"
							    maxlength="9" />							
							</td>														
						</tr>						
					</table> 										    
				</td>				
											
				<#assign dayCount = dayCount + 1 >
			<#else>
				<!-- 放空白的區塊, 本月無此日期 -->				
				<#if (dayCount > maxday) >		
					<!-- 過完當月了 -->
					<#assign nextMonthDayStr = nextMonthDay?string >					
					<td bgcolor="#B6B6B4" align="center" ><b><font color="#808080" >${nextMonthDayStr?left_pad(2, "0")}</font></b></td>
					<#assign nextMonthDay = nextMonthDay + 1 >				
				<#else>
					<!-- 前一個月月底 -->
					<#assign prevMonthDay = previousMonthMaxDay - prevMonthDayMinus >
					<#assign prevMonthDayStr = prevMonthDay?string >					
					<td bgcolor="#B6B6B4" align="center" ><b><font color="#808080" >${prevMonthDayStr?left_pad(2, "0")}</font></b></td>					
					<#assign prevMonthDayMinus = prevMonthDayMinus - 1 >
				</#if>
				
			</#if>
			
			<#assign count = count+1 >			
		</#list>
	</tr>		
			
</#list>  	

	<tr>
		<td bgcolor="#FFFFFF" colspan="7"><b><font color="#333333">T (Target value) , A (Actual value)</font></b></td>
	</tr>		
</table>



