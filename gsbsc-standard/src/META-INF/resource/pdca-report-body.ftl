<table style="width: 100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#d8d8d8">
				<tr>
								<td colspan="6" bgcolor="#d8d8d8" align="center">
								<h2><b>${pdca.title}</b></h2>
								</td>
				</tr>
				<tr>
								<td style="width: 110px; height: 20px" bgcolor="#F2F2F2"><b>Responsibility</b></td>
								<td style="height: 20px" colspan="2" bgcolor="#ffffff">${pdca.responsibilityAppendNames}</td>

								<td style="height: 20px"  bgcolor="#F2F2F2"><b>Date range</b></td>
								<td style="height: 20px" colspan="2" bgcolor="#ffffff">${pdca.startDateDisplayValue}&nbsp;~&nbsp;${pdca.endDateDisplayValue}</td>
					
				</tr>
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>Confirm</b></td>
								<td colspan="2" bgcolor="#ffffff">${pdca.confirmEmployeeName}</td>
						
								<td  bgcolor="#F2F2F2"><b >Confirm date</b></td>
								<td colspan="2" bgcolor="#ffffff">${pdca.confirmDateDisplayValue}</td>
		
				</tr>
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>Organization<br/>Department</b></td>
								<td style="width: 240px" colspan="5" bgcolor="#ffffff">${pdca.organizationAppendNames}</td>
				</tr>				
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>KPIs</b></td>
								<td style="width: 240px" colspan="5" bgcolor="#ffffff">${pdca.kpisAppendNames}</td>
				</tr>			
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>Documents<br/>Attachments</b></td>
								<td style="width: 240px" colspan="5" bgcolor="#ffffff">
								
									<#list pdca.docs as attac >
										<#if attac.viewMode == 'Y' >
											
											<a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'PDCA document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:#3794E5">${attac.showName}</a><br/>
											
										<#else>
									
											<a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:#3794E5">${attac.showName}</a><br/>
									
										</#if>
																		
									</#list>										
								
								
								</td>
				</tr>					
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>Parent PDCA</b></td>
								<td style="width: 240px" colspan="5" bgcolor="#ffffff">${pdca.parentName}</td>
				</tr>
				
				<tr>
								<td style="width: 110px"  bgcolor="#F2F2F2"><b>TYPE</b></td>
								<td style="width: 340px"  bgcolor="#F2F2F2"><b>Title</b></td>
								<td style="width: 250px"  bgcolor="#F2F2F2"><b>Responsibility</b></td>
								<td  bgcolor="#F2F2F2"><b>Date range</b></td>
								<td  bgcolor="#F2F2F2"><b>Audit</b></td>
								<td  bgcolor="#F2F2F2"><b>Audit date</b></td>
				</tr>
	
				
	<#assign p=0 >			
	<#list pdca.itemPlan as item >			

				<tr>

					<#if ( p == 0 ) >
								<td style="width: 110px" rowspan="${pdca.itemPlan?size}" bgcolor="#ffffff" ><b>Plan</b></td>
					</#if>					
								<td style="width: 340px" bgcolor="#ffffff" >
									<div class="isa_success"><b>${item.title}</b>
									<#if item.description??>
									<#if item.description != "">
									<br/>${item.description}
									</#if>
									</#if>									
									</div>
									<#list item.docs as attac >
										<#if attac.viewMode == 'Y' >
											
											<a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'PDCA item document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:#3794E5">${attac.showName}</a><br/>
											
										<#else>
									
											<a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:#3794E5">${attac.showName}</a><br/>
									
										</#if>
																		
									</#list>	
								</td>
								<td style="width: 250px" bgcolor="#ffffff" >${item.employeeAppendNames}</td>
								<td bgcolor="#ffffff" >${item.startDateDisplayValue}&nbsp;~&nbsp;${item.endDateDisplayValue}</td>
					<#if ( p == 0 ) >					
								<td rowspan="${pdca.itemPlan?size}" bgcolor="#ffffff" ><#if pdca.auditPlan??>${pdca.auditPlan.empId}</#if>&nbsp;</td>
								<td rowspan="${pdca.itemPlan?size}" bgcolor="#ffffff" ><#if pdca.auditPlan??>${pdca.auditPlan.confirmDateDisplayValue}&nbsp;</#if></td>
					</#if>	
												
				</tr>
				
		<#assign p = p + 1 >			
	</#list>					


	<#assign p=0 >			
	<#list pdca.itemDo as item >			

				<tr>

					<#if ( p == 0 ) >
								<td style="width: 110px" rowspan="${pdca.itemDo?size}" bgcolor="#ffffff" ><b>Do</b></td>
					</#if>					
								<td style="width: 340px" bgcolor="#ffffff" >
									<div class="isa_success"><b>${item.title}</b>
									<#if item.description??>
									<#if item.description != "">
									<br/>${item.description}
									</#if>
									</#if>									
									</div>
									<#list item.docs as attac >
										<#if attac.viewMode == 'Y' >
											
											<a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'PDCA item document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:#3794E5">${attac.showName}</a><br/>
											
										<#else>
									
											<a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:#3794E5">${attac.showName}</a><br/>
									
										</#if>
																		
									</#list>	
								</td>
								<td style="width: 250px" bgcolor="#ffffff" >${item.employeeAppendNames}</td>
								<td bgcolor="#ffffff" >${item.startDateDisplayValue}&nbsp;~&nbsp;${item.endDateDisplayValue}</td>
					<#if ( p == 0 ) >					
								<td rowspan="${pdca.itemDo?size}" bgcolor="#ffffff" ><#if pdca.auditDo??>${pdca.auditDo.empId}</#if>&nbsp;</td>
								<td rowspan="${pdca.itemDo?size}" bgcolor="#ffffff" ><#if pdca.auditDo??>${pdca.auditDo.confirmDateDisplayValue}</#if>&nbsp;</td>
					</#if>	
												
				</tr>
				
		<#assign p = p + 1 >			
	</#list>


	<#assign p=0 >			
	<#list pdca.itemCheck as item >			

				<tr>

					<#if ( p == 0 ) >
								<td style="width: 110px" rowspan="${pdca.itemCheck?size}" bgcolor="#ffffff" ><b>Check</b></td>
					</#if>					
								<td style="width: 340px" bgcolor="#ffffff" >
									<div class="isa_success"><b>${item.title}</b>
									<#if item.description??>
									<#if item.description != "">
									<br/>${item.description}
									</#if>
									</#if>									
									</div>
									<#list item.docs as attac >
										<#if attac.viewMode == 'Y' >
											
											<a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'PDCA item document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:#3794E5">${attac.showName}</a><br/>
											
										<#else>
									
											<a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:#3794E5">${attac.showName}</a><br/>
									
										</#if>
																		
									</#list>	
								</td>
								<td style="width: 250px" bgcolor="#ffffff" >${item.employeeAppendNames}</td>
								<td bgcolor="#ffffff" >${item.startDateDisplayValue}&nbsp;~&nbsp;${item.endDateDisplayValue}</td>
					<#if ( p == 0 ) >					
								<td rowspan="${pdca.itemCheck?size}" bgcolor="#ffffff" ><#if pdca.auditCheck??>${pdca.auditCheck.empId}</#if>&nbsp;</td>
								<td rowspan="${pdca.itemCheck?size}" bgcolor="#ffffff" ><#if pdca.auditCheck??>${pdca.auditCheck.confirmDateDisplayValue}</#if>&nbsp;</td>
					</#if>	
												
				</tr>
				
		<#assign p = p + 1 >			
	</#list>


	<#assign p=0 >			
	<#list pdca.itemAction as item >			

				<tr>
				
					<#if ( p == 0 ) >
								<td style="width: 110px" rowspan="${pdca.itemAction?size}" bgcolor="#ffffff" ><b>Action</b></td>
					</#if>					
								<td style="width: 340px" bgcolor="#ffffff" >
									<div class="isa_success"><b>${item.title}</b>
									<#if item.description??>
									<#if item.description != "">
									<br/>${item.description}
									</#if>
									</#if>									
									</div>
									<#list item.docs as attac >
										<#if attac.viewMode == 'Y' >
											
											<a href="#" onclick="openCommonLoadUpload( 'view', '${attac.uploadOid}', { 'isDialog' : 'Y', 'title' : 'PDCA item document/attachment view', 'width' : 1280, 'height' : 768 } ); return false;" style="color:#3794E5">${attac.showName}</a><br/>
											
										<#else>
									
											<a href="#" onclick="openCommonLoadUpload( 'download', '${attac.uploadOid}', {}); return false;" style="color:#3794E5">${attac.showName}</a><br/>
									
										</#if>
																		
									</#list>	
								</td>
								<td style="width: 250px" bgcolor="#ffffff" >${item.employeeAppendNames}</td>
								<td bgcolor="#ffffff" >${item.startDateDisplayValue}&nbsp;~&nbsp;${item.endDateDisplayValue}</td>
					<#if ( p == 0 ) >					
								<td rowspan="${pdca.itemAction?size}" bgcolor="#ffffff" ><#if pdca.auditAction??>${pdca.auditAction.empId}</#if>&nbsp;</td>
								<td rowspan="${pdca.itemAction?size}" bgcolor="#ffffff" ><#if pdca.auditAction??>${pdca.auditAction.confirmDateDisplayValue}</#if>&nbsp;</td>
					</#if>	
												
				</tr>
				
		<#assign p = p + 1 >			
	</#list>

</table>

<div id="BSC_PROG006D0002Q_container"></div>
