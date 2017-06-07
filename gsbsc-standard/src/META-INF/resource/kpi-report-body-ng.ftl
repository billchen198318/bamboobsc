<!-- BSC_PROG003D0001Q -->
<#list treeObj.visions as vision >
	
	<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="${backgroundColor}">
		<tr>
			<td colspan="3" bgcolor="${vision.bgColor}" align="center" >
				<b>
				<font color="${vision.fontColor}" size="+2">
					<a href="#" onclick="BSC_PROG003D0001Q_openNew(); return false;" style="color:${vision.fontColor}">${vision.title}</a>
					<BR/>
					${scoreLabel}&nbsp;${vision.score?string(',###.##')}
				</font>
				</b>
			</td>
		</tr>
		<tr>
			<td width="25%" align="left" bgcolor="${backgroundColor}"><b><font color='${fontColor}' size="+1">${perspectiveTitle}</font></b></td>
			<td width="25%" align="left" bgcolor="${backgroundColor}"><b><font color='${fontColor}' size="+1">${objectiveTitle}</font></b></td>
			<td width="50%" align="left" bgcolor="${backgroundColor}"><b><font color='${fontColor}' size="+1">${kpiTitle}</font></b></td>
		</tr>
		
	<#list vision.perspectives as perspective >
		<tr>
			<td width="25%" bgcolor="${perspective.bgColor}" rowspan="${perspective.row}" >
				<table border="0" width="100%">
					<tr>
						<td width="100%" colspan="2" bgcolor="${perspective.bgColor}">
							<font color="${perspective.fontColor}" >
							<b><a href="#" onclick="BSC_PROG003D0001Q_openPerspective('${perspective.perId}'); return false;" style="color:${perspective.fontColor}">${perspective.name}</a></b>
							</font>
							${perspective.imgIcon}
						</td>						
					</tr>
					<tr>
						<td align="left" bgcolor="${perspective.bgColor}" width="15%">
							<font color="${perspective.fontColor}" >
							${scoreLabel}
							</font>
						</td>
						<td align="left" bgcolor="${perspective.bgColor}" width="85%">
							<font color="${perspective.fontColor}" >
							<b>${perspective.score?string(',###.##')}</b>
							</font>
						</td>
					</tr>						
					<tr>
						<td align="left" bgcolor="${perspective.bgColor}" width="15%">
							<font color="${perspective.fontColor}" >
							${weightLabel}
							</font>
						</td>
						<td align="left">
							<font color="${perspective.fontColor}" width="85%">
							${perspective.weight}%
							</font>
						</td>
					</tr>				
					<tr>
						<td align="left" bgcolor="${perspective.bgColor}" width="15%">
							<font color="${perspective.fontColor}" >
							${targetLabel}
							</font>
						</td>
						<td align="left" bgcolor="${perspective.bgColor}" width="85%">
							<font color="${perspective.fontColor}" >
							${perspective.target}
							</font>
						</td>
					</tr>						
					<tr>
						<td align="left" bgcolor="${perspective.bgColor}" width="15%">
							<font color="${perspective.fontColor}" >
							${minLabel}
							</font>
						</td>
						<td align="left" bgcolor="${perspective.bgColor}" width="85%">
							<font color="${perspective.fontColor}" >
							${perspective.min}
							</font>
						</td>
					</tr>	
					<tr>
						<td align="left" bgcolor="${perspective.bgColor}" width="100%" colspan="2">
							<font color="${perspective.fontColor}" >
							${perspective.description}
							</font>
						</td>
					</tr>
																				
				</table>		
			</td>
		
		<#assign p=0 >	
		<#list perspective.objectives as objective >	
			<#if ( p > 0 ) >
				<tr>
			</#if>
			
			<td width="25%" bgcolor="${objective.bgColor}" rowspan="${objective.row}" >
				<table border="0" width="100%">
					<tr>
						<td width="100%" colspan="2" bgcolor="${objective.bgColor}">
							<font color="${objective.fontColor}" >
							<b><a href="#" onclick="BSC_PROG003D0001Q_openObjective('${objective.objId}'); return false;" style="color:${objective.fontColor}">${objective.name}</a></b>
							</font>
							${objective.imgIcon}
						</td>						
					</tr>
					<tr>
						<td align="left" bgcolor="${objective.bgColor}" width="15%">
							<font color="${objective.fontColor}" >
							${scoreLabel}
							</font>
						</td>
						<td align="left" bgcolor="${objective.bgColor}" width="85%">
							<font color="${objective.fontColor}" >
							<b>${objective.score?string(',###.##')}</b>
							</font>
						</td>
					</tr>					
					<tr>
						<td align="left" bgcolor="${objective.bgColor}" width="15%">
							<font color="${objective.fontColor}" >
							${weightLabel}
							</font>
						</td>
						<td align="left">
							<font color="${objective.fontColor}" width="85%">
							${objective.weight}%
							</font>
						</td>
					</tr>
					<tr>
						<td align="left" bgcolor="${objective.bgColor}" width="15%">
							<font color="${objective.fontColor}" >
							${targetLabel}
							</font>
						</td>
						<td align="left" bgcolor="${objective.bgColor}" width="85%">
							<font color="${objective.fontColor}" >
							${objective.target}
							</font>
						</td>
					</tr>						
					<tr>
						<td align="left" bgcolor="${objective.bgColor}" width="15%">
							<font color="${objective.fontColor}" >
							${minLabel}
							</font>
						</td>
						<td align="left" bgcolor="${objective.bgColor}" width="85%">
							<font color="${objective.fontColor}" >
							${objective.min}
							</font>
						</td>
					</tr>					
					<tr>
						<td align="left" bgcolor="${objective.bgColor}" width="100%" colspan="2">
							<font color="${objective.fontColor}" >
							${objective.description}
							</font>
						</td>
					</tr>
															
				</table>	
			</td>
					
				<#list objective.kpis as kpi >
					
					<td width="50%" bgcolor="${kpi.bgColor}" >
						<table border="0" width="100%">
							<tr>
								<td width="100%" colspan="2" bgcolor="${kpi.bgColor}">
									<font color="${kpi.fontColor}" >
									<b><a href="#" onclick="BSC_PROG003D0001Q_openKPI('${kpi.id}'); return false;" style="color:${kpi.fontColor}">${kpi.name}</a></b>
									</font>
									${kpi.imgIcon}
								</td>						
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${scoreLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									<b>${kpi.score?string(',###.##')}</b>
									</font>
								</td>
							</tr>							
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${weightLabel}
									</font>
								</td>
								<td align="left">
									<font color="${kpi.fontColor}" width="85%">
									${kpi.weight}%
									</font>
								</td>
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${maxLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.max}
									</font>
								</td>
							</tr>							
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${targetLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.target}
									</font>
								</td>
							</tr>						
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${minLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.min}
									</font>
								</td>
							</tr>								
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${managementLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.managementName}
									</font>
								</td>
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${calculationLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.calculationName}
									</font>
								</td>
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${unitLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.unit}
									</font>
								</td>
							</tr>							
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${formulaLabel}
									</font>
								</td>
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
									${kpi.formula.name}
									</font>
								</td>
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${organizationLabel}
									</font>
								</td>							
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >																			
										<#list kpi.organizations as org >
										<a href="#" onclick="BSC_PROGCOMM0001Q_showOrgChart('organization', '${org.oid}'); return false;" style="color:${kpi.fontColor}">${org.orgId}-${org.name}</a>,&nbsp;
										</#list>
									</font>
								</td>
							</tr>
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="15%">
									<font color="${kpi.fontColor}" >
									${employeeLabel}
									</font>
								</td>								
								<td align="left" bgcolor="${kpi.bgColor}" width="85%">
									<font color="${kpi.fontColor}" >
										<#list kpi.employees as emp >
										<a href="#" onclick="BSC_PROGCOMM0001Q_showOrgChart('employee', '${emp.oid}'); return false;" style="color:${kpi.fontColor}">${emp.empId}-${emp.fullName}</a>,&nbsp;
										</#list>
									</font>
								</td>
							</tr>								
							<tr>
								<td align="left" bgcolor="${kpi.bgColor}" width="100%" colspan="2">
									<font color="${kpi.fontColor}" >
									${kpi.description}
									</font>
									
									<#list kpi.attachments as attac >
										<#if attac.viewMode == 'Y' >
											
											<br/><a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'KPI document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:${kpi.fontColor}">${attac.showName}</a>
											
										<#else>
									
											<br/><a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:${kpi.fontColor}">${attac.showName}</a>
									
										</#if>
																		
									</#list>		
																	
								</td>
							</tr>
																																								
						</table>				
					</td>
					
					</tr>
					
				</#list>
			
			<#assign p = p + 1 >
		</#list>
				
	</#list>
	
		<tr>
			<td colspan="3" bgcolor="${backgroundColor}" align="left">
				<b>
				<font color="${fontColor}" size="2">
					Frequency:&nbsp;${frequency}&nbsp;&nbsp;Date range:&nbsp;${date1}&nbsp;~&nbsp;${date2}
					${headContent}
				</font>
				</b>
			</td>
		</tr>		
		
		
		<!-- Vision -->
		<tr>
			<td colspan="3" bgcolor="${backgroundColor}" align="left">
				<b><font color="${fontColor}" size="2">Vision date range score</font></b>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" >
						
				
					
					
								
					<tr>
						<td rowspan="2" align="left" bgcolor="${vision.bgColor}" width="200px">
							<font color="${vision.fontColor}" >
							<b>${vision.title}</b>
							</font>			
						</td>
						
						<#list vision.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							<b>${dateRangeScore.date}</b>
							</font>
						</td>						
						</#list>
						
					</tr>
					<tr>
					
						<#list vision.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							${dateRangeScore.score?string(',###.##')}
							</font>
							<!-- vision dateRange no status image icon -->
						</td>						
						</#list>
						
					</tr>


					
				
			
				</table>
			</td>						
		</tr>	
				
		
		<!-- Perspective dateRange score -->
		<tr>
			<td colspan="3" bgcolor="${backgroundColor}" align="left">
				<b><font color="${fontColor}" size="2">${perspectiveTitle} date range score</font></b>
			</td>
		</tr>		
		<tr>
			<td colspan="3">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" >
						
				<#list vision.perspectives as perspective >
					
					
								
					<tr>
						<td rowspan="2" align="left" bgcolor="${perspective.bgColor}" width="200px">
							<font color="${perspective.fontColor}" >
							<b>${perspective.name}</b>
							</font>			
						</td>
						
						<#list perspective.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							<b>${dateRangeScore.date}</b>
							</font>
						</td>						
						</#list>
						
					</tr>
					<tr>
					
						<#list perspective.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							${dateRangeScore.score?string(',###.##')}
							</font>
							<!-- perspective dateRange no status image icon -->
						</td>						
						</#list>
						
					</tr>


					
				</#list>	
			
				</table>
			</td>						
		</tr>	
				
		
		<!-- Objective dateRange score -->
		<tr>
			<td colspan="3" bgcolor="${backgroundColor}" align="left">
				<b><font color="${fontColor}" size="2">${objectiveTitle} date range score</font></b>
			</td>
		</tr>		
		<tr>
			<td colspan="3">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" >
						
				<#list vision.perspectives as perspective >
					<#list perspective.objectives as objective >
					
								
					<tr>
						<td rowspan="2" align="left" bgcolor="${objective.bgColor}" width="200px">
							<font color="${objective.fontColor}" >
							<b>${objective.name}</b>
							</font>			
						</td>
						
						<#list objective.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							<b>${dateRangeScore.date}</b>
							</font>
						</td>						
						</#list>
						
					</tr>
					<tr>
					
						<#list objective.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							${dateRangeScore.score?string(',###.##')}
							</font>
							<!-- objective dateRange no status image icon -->
						</td>						
						</#list>
						
					</tr>


					</#list>
				</#list>	
			
				</table>
			</td>						
		</tr>		
		
		
		<!-- KPIs dateRange score -->
		<tr>
			<td colspan="3" bgcolor="${backgroundColor}" align="left">
				<b><font color="${fontColor}" size="2">${kpiTitle} date range score</font></b>
			</td>
		</tr>		
		<tr>
			<td colspan="3">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" >
						
				<#list vision.perspectives as perspective >
					<#list perspective.objectives as objective >
						<#list objective.kpis as kpi >						
								
					<tr>
						<td rowspan="2" align="left" bgcolor="${kpi.bgColor}" width="200px">
							<font color="${kpi.fontColor}" >
							<b>${kpi.name}</b>
							</font>			
						</td>
						
						<#list kpi.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							<b>${dateRangeScore.date}</b>
							</font>
						</td>						
						</#list>
						
					</tr>
					<tr>
					
						<#list kpi.dateRangeScores as dateRangeScore >
						<td align="center" width="80px" bgcolor="${dateRangeScore.bgColor}">
							<font color="${dateRangeScore.fontColor}" >
							${dateRangeScore.score?string(',###.##')}
							</font>
							${dateRangeScore.imgIcon}
						</td>						
						</#list>
						
					</tr>

						</#list>
					</#list>
				</#list>	
			
				</table>
			</td>						
		</tr>		
				


		
	</table>	
		
</#list>