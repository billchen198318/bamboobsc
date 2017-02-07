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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BscBaseBusinessProcessManagementLogicService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IPdcaDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaAuditService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemOwnerService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemService;
import com.netsteadfast.greenstep.bsc.service.IPdcaKpisService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IPdcaOrgaService;
import com.netsteadfast.greenstep.bsc.service.IPdcaOwnerService;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.bsc.service.logic.IPdcaLogicService;
import com.netsteadfast.greenstep.bsc.vo.PdcaProjectRelatedVO;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.po.hbm.BbPdcaAudit;
import com.netsteadfast.greenstep.po.hbm.BbPdcaDoc;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItem;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItemDoc;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItemOwner;
import com.netsteadfast.greenstep.po.hbm.BbPdcaKpis;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbPdcaOrga;
import com.netsteadfast.greenstep.po.hbm.BbPdcaOwner;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaAuditVO;
import com.netsteadfast.greenstep.vo.PdcaDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemOwnerVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaKpisVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaOrgaVO;
import com.netsteadfast.greenstep.vo.PdcaOwnerVO;
import com.netsteadfast.greenstep.vo.PdcaVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.PdcaLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PdcaLogicServiceImpl extends BscBaseBusinessProcessManagementLogicService implements IPdcaLogicService {
	protected Logger logger=Logger.getLogger(PdcaLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private static final int MAX_PROJECT_RELATED_SHOW = 5;
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;
	private IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> pdcaDocService;
	private IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService;
	private IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> pdcaOrgaService;
	private IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> pdcaOwnerService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private IPdcaItemService<PdcaItemVO, BbPdcaItem, String> pdcaItemService;
	private IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String> pdcaAuditService;
	private IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> pdcaItemDocService;
	private IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> pdcaItemOwnerService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	public PdcaLogicServiceImpl() {
		super();
	}
	
	@Override
	public String getBusinessProcessManagementResourceId() {
		return "PDCAProjectProcess";
	}	
	
	public IPdcaService<PdcaVO, BbPdca, String> getPdcaService() {
		return pdcaService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaService")
	@Required		
	public void setPdcaService(IPdcaService<PdcaVO, BbPdca, String> pdcaService) {
		this.pdcaService = pdcaService;
	}

	public IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> getPdcaDocService() {
		return pdcaDocService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaDocService")
	@Required
	public void setPdcaDocService(IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> pdcaDocService) {
		this.pdcaDocService = pdcaDocService;
	}

	public IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> getPdcaKpisService() {
		return pdcaKpisService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaKpisService")
	@Required
	public void setPdcaKpisService(IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService) {
		this.pdcaKpisService = pdcaKpisService;
	}
	
	public IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> getPdcaOrgaService() {
		return pdcaOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaOrgaService")
	@Required
	public void setPdcaOrgaService(IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> pdcaOrgaService) {
		this.pdcaOrgaService = pdcaOrgaService;
	}

	public IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> getPdcaOwnerService() {
		return pdcaOwnerService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaOwnerService")
	@Required	
	public void setPdcaOwnerService(IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> pdcaOwnerService) {
		this.pdcaOwnerService = pdcaOwnerService;
	}
	
	public IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> getPdcaMeasureFreqService() {
		return pdcaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaMeasureFreqService")
	@Required
	public void setPdcaMeasureFreqService(
			IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService) {
		this.pdcaMeasureFreqService = pdcaMeasureFreqService;
	}
	
	public IPdcaItemService<PdcaItemVO, BbPdcaItem, String> getPdcaItemService() {
		return pdcaItemService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaItemService")
	@Required	
	public void setPdcaItemService(IPdcaItemService<PdcaItemVO, BbPdcaItem, String> pdcaItemService) {
		this.pdcaItemService = pdcaItemService;
	}
	
	public IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String> getPdcaAuditService() {
		return pdcaAuditService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaAuditService")
	@Required	
	public void setPdcaAuditService(IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String> pdcaAuditService) {
		this.pdcaAuditService = pdcaAuditService;
	}

	public IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> getPdcaItemDocService() {
		return pdcaItemDocService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaItemDocService")
	@Required	
	public void setPdcaItemDocService(IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> pdcaItemDocService) {
		this.pdcaItemDocService = pdcaItemDocService;
	}

	public IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> getPdcaItemOwnerService() {
		return pdcaItemOwnerService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaItemOwnerService")
	@Required		
	public void setPdcaItemOwnerService(
			IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> pdcaItemOwnerService) {
		this.pdcaItemOwnerService = pdcaItemOwnerService;
	}

	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")
	@Required
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}	
	
	private Map<String, Object> getProcessFlowParam(String pdcaOid, String pdcaType, String date, String userid, 
			String confirm, String reason, String newChild) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdcaOid);
		paramMap.put("pdcaType", pdcaType);
		paramMap.put("confirm", confirm);
		paramMap.put("reason", super.defaultString(reason).length() > MAX_DESCRIPTION_LENGTH ? reason.substring(0, MAX_DESCRIPTION_LENGTH) : reason);
		paramMap.put("date", date);
		paramMap.put("cuserid", userid);
		paramMap.put("new_chile", newChild); // only for "Action" final step
		return paramMap;
	}	
	
	private PdcaVO findPdca(String oid) throws ServiceException, Exception {
		PdcaVO pdca = new PdcaVO();
		pdca.setOid(oid);
		DefaultResult<PdcaVO> result = this.pdcaService.findObjectByOid(pdca);
		if (result.getValue() == null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		pdca = result.getValue();
		return pdca;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<PdcaVO> create(PdcaVO pdca, PdcaMeasureFreqVO measureFreq, List<String> organizationOids, List<String> employeeOids, List<String> kpiOids,
			List<String> attachment, List<PdcaItemVO> items) throws ServiceException, Exception {
		if (null == pdca || super.isBlank(pdca.getTitle()) || null == items || items.size()<1 
				|| organizationOids.size()<1 || employeeOids.size()<1 || kpiOids.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(pdca, "description", MAX_DESCRIPTION_LENGTH);
		pdca.setConfirmFlag(YesNo.NO);
		this.replaceSplit2Blank(pdca, "startDate", "/");
		this.replaceSplit2Blank(pdca, "endDate", "/");
		DefaultResult<PdcaVO> result = this.pdcaService.saveObject(pdca);
		if (result.getValue() == null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		pdca = result.getValue();
		this.createMeasureFreq(pdca, measureFreq);
		this.createOwner(pdca, employeeOids);
		this.createOrganization(pdca, organizationOids);
		this.createKpis(pdca, kpiOids);
		this.createDocuments(pdca, attachment);
		this.createItems(pdca, items);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<PdcaVO> update(PdcaVO pdca, PdcaMeasureFreqVO measureFreq, List<String> organizationOids, List<String> employeeOids, List<String> kpiOids,
			List<String> attachment, List<PdcaItemVO> items) throws ServiceException, Exception {
		if (null == pdca || super.isBlank(pdca.getOid()) || super.isBlank(pdca.getTitle()) || null == items || items.size()<1 
				|| organizationOids.size()<1 || employeeOids.size()<1 || kpiOids.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<PdcaVO> oldResult = this.pdcaService.findObjectByOid(pdca);
		if (oldResult.getValue() == null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		if ( pdca.getTitle().equals(oldResult.getValue().getTitle()) && !(pdca.getOid().equals(oldResult.getValue().getOid())) ) {
			throw new ServiceException( "Same title is found: " + pdca.getTitle() );
		}
		if (YesNo.YES.equals(oldResult.getValue().getConfirmFlag())) {
			throw new ServiceException( "Cannot update/modify confirm project!" );
		}
		this.setStringValueMaxLength(pdca, "description", MAX_DESCRIPTION_LENGTH);
		pdca.setConfirmFlag(YesNo.NO);
		this.replaceSplit2Blank(pdca, "startDate", "/");
		this.replaceSplit2Blank(pdca, "endDate", "/");
		pdca.setParentOid( oldResult.getValue().getParentOid() );
		DefaultResult<PdcaVO> result = this.pdcaService.updateObject(pdca);
		pdca = result.getValue();
		this.deleteMeasureFreq(pdca);
		this.deleteOwner(pdca);
		this.deleteOrganization(pdca);
		this.deleteKpis(pdca);
		this.deleteDocuments(pdca);
		this.deleteItems(pdca);
		this.createMeasureFreq(pdca, measureFreq);
		this.createOwner(pdca, employeeOids);
		this.createOrganization(pdca, organizationOids);
		this.createKpis(pdca, kpiOids);
		this.createDocuments(pdca, attachment);
		this.createItems(pdca, items);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<Boolean> delete(PdcaVO pdca) throws ServiceException, Exception {
		if (null == pdca || super.isBlank(pdca.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentOid", pdca.getOid());
		if (this.pdcaService.countByParams(paramMap)>0) {
			throw new ServiceException("The project has child project, cannot delete!");
		}
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariablePdcaOid(pdca.getOid());
		if (tasks!=null && tasks.size()>0) {
			throw new ServiceException("Audit processing running, project cannot delete!");
		}		
		this.deleteMeasureFreq(pdca);
		this.deleteOwner(pdca);
		this.deleteOrganization(pdca);
		this.deleteKpis(pdca);
		this.deleteDocuments(pdca);
		this.deleteItems(pdca);
		return this.pdcaService.deleteObject(pdca);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<PdcaVO> startProcess(PdcaVO pdca) throws ServiceException, Exception {
		if (null == pdca || super.isBlank(pdca.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<PdcaVO> result = this.pdcaService.findObjectByOid(pdca);
		if (result.getValue() == null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		pdca = result.getValue();
		if (YesNo.YES.equals(pdca.getConfirmFlag())) {
			throw new ServiceException("The project is confirm, cannot start audit processing!");
		}
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariablePdcaOid(pdca.getOid());
		if (tasks!=null && tasks.size()>0) {
			throw new ServiceException("Audit processing has been started!");
		}
		String reason = "Start PDCA audit processing, " + pdca.getTitle() + "";
		this.startProcess(this.getProcessFlowParam(pdca.getOid(), "*", SimpleUtils.getStrYMD(""), super.getAccountId(), YesNo.YES, reason, YesNo.NO));
		result.setSystemMessage( new SystemMessage(reason) );
		return result;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public List<BusinessProcessManagementTaskVO> queryTaskByVariablePdcaOid(String pdcaOid) throws ServiceException, Exception {		
		if (super.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		return this.queryTaskPlus("pdcaOid", pdcaOid);
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public String getTaskDiagram(String taskId) throws ServiceException, Exception {
		if (super.isBlank(taskId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return BusinessProcessManagementUtils.getTaskDiagramById2Upload(this.getBusinessProcessManagementResourceId(), taskId);
	}	

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	@Override
	public void confirmTask(String pdcaOid, String taskId, String confirm, String reason, String newChild) throws ServiceException, Exception {
		if (super.isBlank(pdcaOid) || super.isBlank(taskId) || super.isBlank(confirm)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		PdcaVO pdca = this.findPdca(pdcaOid);
		String newDate = SimpleUtils.getStrYMD("");
		String type = "E";
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariablePdcaOid(pdca.getOid());
		if (tasks!=null && tasks.size()>0) {
			type = tasks.get(0).getTask().getName().substring(0, 1);
		}
		Map<String, Object> flowDataMap = this.getProcessFlowParam(pdcaOid, type, newDate, super.getAccountId(), confirm, reason, newChild);
		this.completeTask(taskId, flowDataMap);
		/*
		if (YesNo.YES.equals(confirm)) { // 只有 confirm == Y 的, 才要寫入 bb_pdca_audit , 報表顯示時要用到
			this.createAudit(pdca, flowDataMap);
		}
		*/
		this.createAudit(pdca, flowDataMap); // 改為都要產生 bb_pdca_audit
		
		tasks = this.queryTaskByVariablePdcaOid(pdca.getOid()); // 重新查是否還有task
		
		if (null != tasks && tasks.size()>0) { 
			return;
		}
		
		if (YesNo.YES.equals(confirm)) {
			pdca.setConfirmDate(newDate);
			pdca.setConfirmEmpId(this.findEmployeeDataByAccountId(super.getAccountId()).getEmpId());
			pdca.setConfirmFlag(YesNo.YES);
			this.pdcaService.updateObject(pdca);
		}
		if (YesNo.YES.equals(confirm) && YesNo.YES.equals(newChild)) {
			this.cloneNewProject(pdca);
		}
		
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public PdcaProjectRelatedVO findProjectRelated(String pdcaOid) throws ServiceException, Exception {
		if (super.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		PdcaVO pdca = this.findPdca(pdcaOid);
		PdcaProjectRelatedVO projectRelated = new PdcaProjectRelatedVO();
		projectRelated.setProject(pdca);
		this.findProjectRelatedParent(projectRelated, pdca);
		this.findProjectRelatedChild(projectRelated, pdca);
		return projectRelated;
	}	
	
	private void findProjectRelatedParent(PdcaProjectRelatedVO projectRelated, PdcaVO pdca) throws ServiceException, Exception {
		if (super.isBlank(pdca.getParentOid()) || projectRelated.getParent().size() >= MAX_PROJECT_RELATED_SHOW ) {
			return;
		}
		PdcaVO parentPdca = this.findPdca(pdca.getParentOid());
		( (LinkedList<PdcaVO>) projectRelated.getParent() ).addFirst(parentPdca);
		this.findProjectRelatedParent(projectRelated, parentPdca);
	}
	
	private void findProjectRelatedChild(PdcaProjectRelatedVO projectRelated, PdcaVO pdca) throws ServiceException, Exception {
		if (projectRelated.getChild().size() >= MAX_PROJECT_RELATED_SHOW ) {
			return;
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentOid", pdca.getOid());
		List<PdcaVO> childList = this.pdcaService.findListVOByParams(paramMap);
		if (childList == null || childList.size() < 1) {
			return;
		}
		if (childList.size() != 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		( (LinkedList<PdcaVO>) projectRelated.getChild() ).addLast( childList.get(0) );
		this.findProjectRelatedChild(projectRelated, childList.get(0) );
	}
	
	private void deleteMeasureFreq(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaMeasureFreq> measureFreqList = this.pdcaMeasureFreqService.findListByParams(paramMap);
		for (BbPdcaMeasureFreq measureFreq : measureFreqList) {
			this.pdcaMeasureFreqService.delete(measureFreq);
		}
	}
	
	private void createMeasureFreq(PdcaVO pdca, PdcaMeasureFreqVO measureFreq) throws ServiceException, Exception {
		measureFreq.setPdcaOid(pdca.getOid());
		this.replaceSplit2Blank(measureFreq, "startDate", "/");
		this.replaceSplit2Blank(measureFreq, "endDate", "/");
		if (this.isNoSelectId(measureFreq.getOrganizationOid())) {
			measureFreq.setOrgId(BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		} else {
			measureFreq.setOrgId( this.findOrganizationData(measureFreq.getOrganizationOid()).getOrgId() );
		}
		if (this.isNoSelectId(measureFreq.getEmployeeOid())) {
			measureFreq.setEmpId(BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		} else {
			measureFreq.setEmpId( this.findEmployeeData(measureFreq.getEmployeeOid()).getEmpId() );
		}		
		this.pdcaMeasureFreqService.saveObject(measureFreq);
	}
	
	private void deleteOrganization(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaOrga> orgaList = this.pdcaOrgaService.findListByParams(paramMap);
		for (BbPdcaOrga orga : orgaList) {
			this.pdcaOrgaService.delete(orga);
		}
	}
	
	private void createOrganization(PdcaVO pdca, List<String> orgaOids) throws ServiceException, Exception {
		for (String oid : orgaOids) {
			OrganizationVO organization = this.findOrganizationData(oid);
			PdcaOrgaVO pdcaOrga = new PdcaOrgaVO();
			pdcaOrga.setPdcaOid(pdca.getOid());
			pdcaOrga.setOrgId( organization.getOrgId() );
			this.pdcaOrgaService.saveObject(pdcaOrga);
		}
	}
	
	private void deleteOwner(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaOwner> ownerList = this.pdcaOwnerService.findListByParams(paramMap);
		for (BbPdcaOwner owner : ownerList) {
			this.pdcaOwnerService.delete(owner);
		}
	}
	
	private void createOwner(PdcaVO pdca, List<String> emplOids) throws ServiceException, Exception {
		for (String oid : emplOids) {
			EmployeeVO employee = this.findEmployeeData(oid);
			PdcaOwnerVO pdcaOwner = new PdcaOwnerVO();
			pdcaOwner.setPdcaOid(pdca.getOid());
			pdcaOwner.setEmpId(employee.getEmpId());
			this.pdcaOwnerService.saveObject(pdcaOwner);
		}
	}
	
	private void deleteKpis(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaKpis> kpisList = this.pdcaKpisService.findListByParams(paramMap);
		for (BbPdcaKpis kpi : kpisList) {
			this.pdcaKpisService.delete(kpi);
		}
	}
	
	private void createKpis(PdcaVO pdca, List<String> kpiOids) throws ServiceException, Exception {
		for (String oid : kpiOids) {
			KpiVO kpi = new KpiVO();
			kpi.setOid(oid);
			DefaultResult<KpiVO> kpiResult = this.kpiService.findObjectByOid(kpi);
			if (kpiResult.getValue() == null) {
				throw new ServiceException(kpiResult.getSystemMessage().getValue());
			}
			kpi = kpiResult.getValue();
			if (!YesNo.YES.equals(kpi.getActivate())) { // 在打開 KPI-tree 選取時還是 ACTIVATE = 'Y' , 然後故意的去KPI修改, 把 ACTIVATE 設定為'N' , 所以要檢查是否正確
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			PdcaKpisVO pdcaKpi = new PdcaKpisVO();
			pdcaKpi.setPdcaOid(pdca.getOid());
			pdcaKpi.setKpiId(kpi.getId());
			this.pdcaKpisService.saveObject(pdcaKpi);
		}
	}
	
	private void deleteDocuments(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaDoc> docList = this.pdcaDocService.findListByParams(paramMap);
		for (BbPdcaDoc doc : docList) {
			UploadSupportUtils.updateType(doc.getUploadOid(), UploadTypes.IS_TEMP);
			this.pdcaDocService.delete(doc);
		}
	}
	
	private void createDocuments(PdcaVO pdca, List<String> attachment) throws ServiceException, Exception {
		if (attachment == null || attachment.size()<1) {
			return;
		}
		for (String oid : attachment) {
			SysUploadVO upload = this.findUploadDataForNoByteContent( oid );
			if (!(upload.getSystem().equals(Constants.getSystem()) && upload.getType().equals(UploadTypes.IS_TEMP))) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			PdcaDocVO pdcaDoc = new PdcaDocVO();
			pdcaDoc.setPdcaOid(pdca.getOid());
			pdcaDoc.setUploadOid(upload.getOid());
			pdcaDoc.setViewMode( UploadSupportUtils.getViewMode(upload.getShowName()) );
			DefaultResult<PdcaDocVO> result = this.pdcaDocService.saveObject(pdcaDoc);
			if (result.getValue() == null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			UploadSupportUtils.updateType(oid, UploadTypes.IS_PDCA_DOCUMENT);
		}
	}
	
	private void deleteItems(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		List<BbPdcaItem> items = this.pdcaItemService.findListByParams(paramMap);
		for (BbPdcaItem item : items) {
			this.deleteItemOwner(item);
			this.deleteItemDocuments(item);
			this.pdcaItemService.delete(item);
		}
	}
	
	private void createItems(PdcaVO pdca, List<PdcaItemVO> items) throws ServiceException, Exception {
		for (PdcaItemVO itemObj : items) {
			itemObj.setPdcaOid(pdca.getOid());
			this.setStringValueMaxLength(itemObj, "description", MAX_DESCRIPTION_LENGTH);
			this.replaceSplit2Blank(itemObj, "startDate", "/");
			this.replaceSplit2Blank(itemObj, "endDate", "/");
			DefaultResult<PdcaItemVO> result = this.pdcaItemService.saveObject(itemObj);
			if (result.getValue() == null) {
				throw new ServiceException( result.getSystemMessage().getValue() );
			}
			PdcaItemVO item = result.getValue();
			item.setEmployeeOids( itemObj.getEmployeeOids() );
			item.setUploadOids( itemObj.getUploadOids() );
			this.createItemOwner( item );
			this.createItemDocuments( item );
		}
	}
	
	private void deleteItemOwner(BbPdcaItem pdcaItem) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdcaItem.getPdcaOid());
		paramMap.put("itemOid", pdcaItem.getOid());
		List<BbPdcaItemOwner> itemOwnerList = this.pdcaItemOwnerService.findListByParams(paramMap);
		for (BbPdcaItemOwner owner : itemOwnerList) {
			this.pdcaItemOwnerService.delete(owner);
		}
	}
	
	private void createItemOwner(PdcaItemVO pdcaItem) throws ServiceException, Exception {
		for (String oid : pdcaItem.getEmployeeOids()) {
			EmployeeVO employee = this.findEmployeeData(oid);
			PdcaItemOwnerVO itemOwner = new PdcaItemOwnerVO();
			itemOwner.setPdcaOid(pdcaItem.getPdcaOid());
			itemOwner.setItemOid(pdcaItem.getOid());
			itemOwner.setEmpId(employee.getEmpId());
			this.pdcaItemOwnerService.saveObject(itemOwner);
		}
	}
	
	private void deleteItemDocuments(BbPdcaItem pdcaItem) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdcaItem.getPdcaOid());
		paramMap.put("itemOid", pdcaItem.getOid());
		List<BbPdcaItemDoc> itemDocList = this.pdcaItemDocService.findListByParams(paramMap);
		for (BbPdcaItemDoc doc : itemDocList) {
			UploadSupportUtils.updateType(doc.getUploadOid(), UploadTypes.IS_TEMP);
			this.pdcaItemDocService.delete(doc);			
		}
	}
	
	private void createItemDocuments(PdcaItemVO pdcaItem) throws ServiceException, Exception {
		if (pdcaItem.getUploadOids() == null || pdcaItem.getUploadOids().size()<1) {
			return;
		}
		for (String oid : pdcaItem.getUploadOids()) {
			SysUploadVO upload = this.findUploadDataForNoByteContent(oid);
			if (!(upload.getSystem().equals(Constants.getSystem()) && upload.getType().equals(UploadTypes.IS_TEMP))) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			PdcaItemDocVO itemDoc = new PdcaItemDocVO();
			itemDoc.setPdcaOid(pdcaItem.getPdcaOid());
			itemDoc.setItemOid(pdcaItem.getOid());
			itemDoc.setUploadOid(upload.getOid());
			itemDoc.setViewMode( UploadSupportUtils.getViewMode(upload.getShowName()) );
			DefaultResult<PdcaItemDocVO> result = this.pdcaItemDocService.saveObject(itemDoc);
			if (result.getValue() == null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			UploadSupportUtils.updateType(oid, UploadTypes.IS_PDCA_DOCUMENT);
		}		
	}
	
	private void createAudit(PdcaVO pdca, Map<String, Object> flowDataMap) throws ServiceException, Exception {
		PdcaAuditVO audit = new PdcaAuditVO();
		audit.setPdcaOid(pdca.getOid());
		audit.setEmpId( this.getEmployeeService().findByAccountId( (String)flowDataMap.get("cuserid") ).getEmpId() );
		audit.setType( (String)flowDataMap.get("pdcaType") );
		audit.setConfirmDate( (String)flowDataMap.get("date") );
		audit.setConfirmSeq( (this.pdcaAuditService.findForMaxConfirmSeq(pdca.getOid())+1) );
		this.pdcaAuditService.saveObject(audit);
	}
	
	private void cloneNewProject(PdcaVO parentPdca) throws ServiceException, Exception {
		
		// 1. PDCA main
		PdcaVO pdca = new PdcaVO();
		this.pdcaService.copyProperties(parentPdca, pdca);
		pdca.setOid( null );
		pdca.setConfirmDate( null );
		pdca.setConfirmEmpId( null );
		pdca.setConfirmFlag( YesNo.NO );
		pdca.setParentOid(parentPdca.getOid());
		String lastTitle = "-(New)";
		if ((pdca.getTitle()+lastTitle).length()<=100) {
			pdca.setTitle( pdca.getTitle() + lastTitle );
		} else {
			pdca.setTitle( pdca.getTitle().substring( pdca.getTitle().length() - lastTitle.length(), 100 ) + lastTitle );
		}
		
		// 2. PDCA measure-freq
		PdcaMeasureFreqVO measureFreq = new PdcaMeasureFreqVO();
		measureFreq.setPdcaOid(parentPdca.getOid());
		DefaultResult<PdcaMeasureFreqVO> mfResult = this.pdcaMeasureFreqService.findByUK(measureFreq);
		if (mfResult.getValue() == null) {
			throw new ServiceException( mfResult.getSystemMessage().getValue() );
		}
		measureFreq = mfResult.getValue();
		measureFreq.setOid( null );
		measureFreq.setPdcaOid( null );
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", parentPdca.getOid());
		
		// 3. organizationOids
		List<String> organizationOids = new ArrayList<String>();
		List<BbPdcaOrga> pdcaOrgaList = this.pdcaOrgaService.findListByParams(paramMap);
		for (BbPdcaOrga pdcaOrga : pdcaOrgaList) {
			OrganizationVO organization = this.findOrganizationDataByUK(pdcaOrga.getOrgId());
			organizationOids.add(organization.getOid());
		}
		
		// 4. employeeOids
		List<String> employeeOids = new ArrayList<String>();
		List<BbPdcaOwner> pdcaOwnerList = this.pdcaOwnerService.findListByParams(paramMap);
		for (BbPdcaOwner pdcaOwner : pdcaOwnerList) {
			EmployeeVO employee = this.findEmployeeDataByEmpId(pdcaOwner.getEmpId());
			employeeOids.add(employee.getOid());
		}
		
		// 5. kpiOids
		List<String> kpiOids = new ArrayList<String>();
		List<BbPdcaKpis> pdcaKpisList = this.pdcaKpisService.findListByParams(paramMap);
		for (BbPdcaKpis pdcaKpi : pdcaKpisList) {
			KpiVO kpi = new KpiVO();
			kpi.setId( pdcaKpi.getKpiId() );
			DefaultResult<KpiVO> kResult = this.kpiService.findByUK(kpi);
			if (kResult.getValue() == null) {
				throw new ServiceException( kResult.getSystemMessage().getValue() );
			}
			kpi = kResult.getValue();
			kpiOids.add( kpi.getOid() );
		}
		
		// 6. attachment
		List<String> attachment = new ArrayList<String>();
		List<BbPdcaDoc> pdcaDocList = this.pdcaDocService.findListByParams(paramMap);
		for (BbPdcaDoc pdcaDoc : pdcaDocList) {
			DefaultResult<SysUploadVO> upResult = this.getSysUploadService().findForNoByteContent( pdcaDoc.getUploadOid() );
			if (upResult.getValue() == null) {
				throw new ServiceException( upResult.getSystemMessage().getValue() );
			}
			SysUploadVO upload = upResult.getValue();
			File oldFile = UploadSupportUtils.getRealFile(pdcaDoc.getUploadOid());
			attachment.add( UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, true, oldFile, upload.getShowName()) );
		}
		
		// 7. items - PdcaItemVO
		List<PdcaItemVO> items = new ArrayList<PdcaItemVO>();
		List<BbPdcaItem> pdcaItems = this.pdcaItemService.findListByParams(paramMap);
		for (BbPdcaItem pdcaItem : pdcaItems) {
			PdcaItemVO itemObj = new PdcaItemVO();
			this.pdcaItemService.doMapper(pdcaItem, itemObj, IPdcaItemService.MAPPER_ID_PO2VO);
			items.add(itemObj);
		}
		
		// 8. item - owner
		for (PdcaItemVO itemObj : items) {
			itemObj.setEmployeeOids( new ArrayList<String>() );
			paramMap.put("itemOid", itemObj.getOid());
			List<BbPdcaItemOwner> itemOwnerList = this.pdcaItemOwnerService.findListByParams(paramMap);
			for (BbPdcaItemOwner itemOwner : itemOwnerList) {
				itemObj.getEmployeeOids().add( this.findEmployeeDataByEmpId(itemOwner.getEmpId()).getOid() );
			}
		}
		
		// 9. item - attachment
		for (PdcaItemVO itemObj : items) {
			itemObj.setUploadOids( new ArrayList<String>() );
			paramMap.put("itemOid", itemObj.getOid());
			List<BbPdcaItemDoc> itemDocList = this.pdcaItemDocService.findListByParams(paramMap);
			for (BbPdcaItemDoc itemDoc : itemDocList) {
				DefaultResult<SysUploadVO> upResult = this.getSysUploadService().findForNoByteContent( itemDoc.getUploadOid() );
				if (upResult.getValue() == null) {
					throw new ServiceException( upResult.getSystemMessage().getValue() );
				}
				SysUploadVO upload = upResult.getValue();
				File oldFile = UploadSupportUtils.getRealFile(itemDoc.getUploadOid());
				itemObj.getUploadOids().add( UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, true, oldFile, upload.getShowName()) );
			}
		}
		
		// clear item old OID, PDCA_OID
		for (PdcaItemVO itemObj : items) {
			itemObj.setOid( null );
			itemObj.setPdcaOid( null );			
		}
		
		this.create(pdca, measureFreq, organizationOids, employeeOids, kpiOids, attachment, items);
		
	}
	
}
