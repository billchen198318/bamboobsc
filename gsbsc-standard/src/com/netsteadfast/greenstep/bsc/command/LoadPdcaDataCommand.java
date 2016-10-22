/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.bsc.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.model.PdcaType;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPdcaAuditService;
import com.netsteadfast.greenstep.bsc.service.IPdcaDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemService;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.po.hbm.BbPdcaAudit;
import com.netsteadfast.greenstep.po.hbm.BbPdcaDoc;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItem;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItemDoc;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaAuditVO;
import com.netsteadfast.greenstep.vo.PdcaDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaVO;

public class LoadPdcaDataCommand extends BaseChainCommandSupport implements Command {
	protected Logger logger=Logger.getLogger(LoadPdcaDataCommand.class);
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;
	private IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> pdcaDocService;
	private IPdcaItemService<PdcaItemVO, BbPdcaItem, String> pdcaItemService;
	private IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> pdcaItemDocService;
	private IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String> pdcaAuditService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		pdcaService = (IPdcaService<PdcaVO, BbPdca, String>) AppContext.getBean("bsc.service.PdcaService");
		pdcaDocService = (IPdcaDocService<PdcaDocVO, BbPdcaDoc, String>) AppContext.getBean("bsc.service.PdcaDocService");
		pdcaItemService = (IPdcaItemService<PdcaItemVO, BbPdcaItem, String>) AppContext.getBean("bsc.service.PdcaItemService");
		pdcaItemDocService = (IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String>) AppContext.getBean("bsc.service.PdcaItemDocService");
		pdcaAuditService = (IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String>) AppContext.getBean("bsc.service.PdcaAuditService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>) AppContext.getBean("bsc.service.EmployeeService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>) AppContext.getBean("bsc.service.OrganizationService");
		kpiService = (IKpiService<KpiVO, BbKpi, String>) AppContext.getBean("bsc.service.KpiService");
		String pdcaOid = (String)context.get("pdcaOid");
		PdcaVO pdca = new PdcaVO();
		pdca.setOid(pdcaOid);
		DefaultResult<PdcaVO> result = pdcaService.findObjectByOid(pdca);
		if (result.getValue() == null) {
			this.setMessage(context, result.getSystemMessage().getValue());
		} else {
			pdca = result.getValue();
			this.loadDetail(pdca);
			this.loadPdcaItems(pdca);
			this.loadAudit(pdca);
			this.setResult(context, pdca);
			context.put("pdca", pdca); // Action 輸出可能會要用到
		}
		return false;
	}
	
	private void loadDetail(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		
		// 1. Project owner
		List<String> ownerNames = this.employeeService.findForAppendNames(
				this.employeeService.findForAppendEmployeeOidsByPdcaOwner(pdca.getOid()) );
		pdca.setResponsibilityAppendNames( StringUtils.join(ownerNames.toArray(), Constants.ID_DELIMITER)+Constants.ID_DELIMITER );
		
		// 2. Confirm employee
		if (!StringUtils.isBlank(pdca.getConfirmEmpId())) {
			pdca.setConfirmEmployeeName( this.getEmployeeNameNoThrow(pdca.getConfirmEmpId()) );
		}
		
		// 3. Project organization/department
		List<String> orgaNames = this.organizationService.findForAppendNames(
				this.organizationService.findForAppendOrganizationOidsByPdcaOrga(pdca.getOid()) );
		pdca.setOrganizationAppendNames( StringUtils.join(orgaNames.toArray(), Constants.ID_DELIMITER)+Constants.ID_DELIMITER );
		
		// 4. Project KPIs
		List<String> kpiNames = this.kpiService.findForAppendNames(
				this.kpiService.findForAppendOidsByPdcaKpis(pdca.getOid()) );
		pdca.setKpisAppendNames( StringUtils.join(kpiNames.toArray(), Constants.ID_DELIMITER)+Constants.ID_DELIMITER );
		
		// 5. parent project
		if (!StringUtils.isBlank(pdca.getParentOid())) {
			PdcaVO parentPdca = new PdcaVO();
			parentPdca.setOid( pdca.getParentOid() );
			DefaultResult<PdcaVO> pResult = this.pdcaService.findObjectByOid(parentPdca);
			if (pResult.getValue() == null) {
				throw new ServiceException( pResult.getSystemMessage().getValue() );
			}
			pdca.setParentName( pResult.getValue().getTitle() );
		}
		
		// 6. Documents file
		List<PdcaDocVO> pdcaDocs = this.pdcaDocService.findListVOByParams(paramMap);
		if (pdcaDocs != null && pdcaDocs.size() > 0) {
			pdca.getDocs().addAll( pdcaDocs );
		}
		for (int i=0; pdca.getDocs()!=null && i<pdca.getDocs().size(); i++) {
			PdcaDocVO doc = pdca.getDocs().get(i);
			doc.setShowName( UploadSupportUtils.findUploadNoByteContent(doc.getUploadOid()).getShowName() );
		}
		
	}
	
	// 7. item detail
	private void loadPdcaItems(PdcaVO pdca) throws ServiceException, Exception {
		pdca.setItemPlan( new ArrayList<PdcaItemVO>() );
		pdca.setItemDo( new ArrayList<PdcaItemVO>() );
		pdca.setItemCheck( new ArrayList<PdcaItemVO>() );
		pdca.setItemAction( new ArrayList<PdcaItemVO>() );
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());		
		List<BbPdcaItem> pdcaItems = this.pdcaItemService.findListByParams(paramMap);
		for (BbPdcaItem pdcaItem : pdcaItems) {
			PdcaItemVO item = new PdcaItemVO();
			this.pdcaItemService.doMapper(pdcaItem, item, IPdcaItemService.MAPPER_ID_PO2VO);
			
			// item owner
			List<String> ownerNames = this.employeeService.findForAppendNames(
					this.employeeService.findForAppendEmployeeOidsByPdcaItemOwner(item.getPdcaOid(), item.getOid()) );
			item.setEmployeeAppendNames( StringUtils.join(ownerNames.toArray(), Constants.ID_DELIMITER)+Constants.ID_DELIMITER );
			
			// item documents file
			paramMap.put("itemOid", item.getOid());
			List<PdcaItemDocVO> itemDocs = this.pdcaItemDocService.findListVOByParams(paramMap);
			if (itemDocs != null && itemDocs.size() > 0) {
				item.getDocs().addAll( itemDocs );
			}
			for (int i=0; item.getDocs()!=null && i<item.getDocs().size(); i++) {
				PdcaItemDocVO doc = item.getDocs().get(i);
				doc.setShowName( UploadSupportUtils.findUploadNoByteContent(doc.getUploadOid()).getShowName() );
			}
			
			if (PdcaType.PLAN.equals(item.getType())) {
				pdca.getItemPlan().add(item);
			} else if (PdcaType.DO.equals(item.getType())) {
				pdca.getItemDo().add(item);
			} else if (PdcaType.CHECK.equals(item.getType())) {
				pdca.getItemCheck().add(item);
			} else {
				pdca.getItemAction().add(item);
			}
		}
		
	}
	
	// 8. audit
	private void loadAudit(PdcaVO pdca) throws ServiceException, Exception {
		pdca.setAuditPlan( findMaxConfirmSeqPdcaAuditData(pdca, PdcaType.PLAN) );
		pdca.setAuditDo( findMaxConfirmSeqPdcaAuditData(pdca, PdcaType.DO) );
		pdca.setAuditCheck( findMaxConfirmSeqPdcaAuditData(pdca, PdcaType.CHECK) );
		pdca.setAuditAction( findMaxConfirmSeqPdcaAuditData(pdca, PdcaType.ACTION) );
		// 用 empId 放員工名稱 , 不想括充變數
		if (pdca.getAuditPlan() != null) {
			pdca.getAuditPlan().setEmpId( this.getEmployeeNameNoThrow(pdca.getAuditPlan().getEmpId()) );
		}
		if (pdca.getAuditDo() != null) {
			pdca.getAuditDo().setEmpId( this.getEmployeeNameNoThrow(pdca.getAuditDo().getEmpId()) );
		}
		if (pdca.getAuditCheck() != null) {
			pdca.getAuditCheck().setEmpId( this.getEmployeeNameNoThrow(pdca.getAuditCheck().getEmpId()) );
		}
		if (pdca.getAuditAction() != null) {
			pdca.getAuditAction().setEmpId( this.getEmployeeNameNoThrow(pdca.getAuditAction().getEmpId()) );
		}
	}
	
	private PdcaAuditVO findMaxConfirmSeqPdcaAuditData(PdcaVO pdca, String type) throws ServiceException, Exception {
		/*
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());	
		
		Map<String, String> orderParam = new HashMap<String, String>();
		orderParam.put("confirmSeq", "DESC");
		
		Map<String, CustomeOperational> operParam1 = new HashMap<String, CustomeOperational>();
		CustomeOperational op1 = new CustomeOperational();
		op1.setField("type");
		op1.setOp("=");
		op1.setValue(type);
		operParam1.put("op1", op1);
		List<BbPdcaAudit> planAuditList = this.pdcaAuditService.findListByParams2(paramMap, null, operParam1, orderParam);
		if (planAuditList == null || planAuditList.size() < 1) {
			return null;
		}
		*/
		BbPdcaAudit pdcaAudit = this.pdcaAuditService.findForLast(pdca.getOid(), type);
		if (null == pdcaAudit) {
			return null;
		}
		PdcaAuditVO audit = new PdcaAuditVO();
		pdcaAuditService.doMapper(pdcaAudit, audit, IPdcaAuditService.MAPPER_ID_PO2VO);
		return audit;
	}
	
	private String getEmployeeNameNoThrow(String empId) {
		String name = "";
		try {
			EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(employeeService, empId);
			name = employee.getFullName();
		} catch (Exception e) { 
			logger.warn( "No find employee: " + empId );
		}
		if (StringUtils.isBlank(name)) {
			return empId;
		}
		return name;
	}
	
}
