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
package com.netsteadfast.greenstep.bsc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.PdcaType;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPdcaDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.bsc.service.logic.IPdcaLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.po.hbm.BbPdcaDoc;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItem;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItemDoc;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PdcaManagementAction")
@Scope
public class PdcaManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -7298705386115619097L;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> pdcaDocService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private IPdcaItemService<PdcaItemVO, BbPdcaItem, String> pdcaItemService;
	private IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> pdcaItemDocService;
	private IPdcaLogicService pdcaLogicService;
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private Map<String, String> measureDataOrganizationMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> measureDataEmployeeMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> pdcaTypeMap = PdcaType.getDataMap(true);
	private PdcaVO pdca = new PdcaVO();
	private PdcaMeasureFreqVO measureFreq = new PdcaMeasureFreqVO();
	private List<PdcaDocVO> pdcaDocs = new ArrayList<PdcaDocVO>();
	private List<PdcaItemVO> pdcaItems = new ArrayList<PdcaItemVO>();
	private BusinessProcessManagementTaskVO bpmTaskObj;
	
	public PdcaManagementAction() {
		super();
	}
	
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}	
	
	@Autowired
	@Required
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}
	
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(
			IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
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
	
	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.KpiService")		
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
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
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Required
	@Resource(name="core.service.SysUploadService")		
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
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
	
	public IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> getPdcaItemDocService() {
		return pdcaItemDocService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaItemDocService")
	@Required	
	public void setPdcaItemDocService(IPdcaItemDocService<PdcaItemDocVO, BbPdcaItemDoc, String> pdcaItemDocService) {
		this.pdcaItemDocService = pdcaItemDocService;
	}	
	
	public IPdcaLogicService getPdcaLogicService() {
		return pdcaLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.PdcaLogicService")
	@Required
	public void setPdcaLogicService(IPdcaLogicService pdcaLogicService) {
		this.pdcaLogicService = pdcaLogicService;
	}	
	
	private void initData(String type) throws ServiceException, Exception {
		if (!"execute".equals(type) && !"confirmDialog".equals(type)) {
			initDataForCreateOrEdit();
		}
		if ("confirmDialog".equals(type)) { // BSC_PROG006D0001E_S00 Confirm audit dialog , 帶入的 fields.oid 是由 tb_pdca.oid 與 流程 task-id 組成
			String parameterStr = super.defaultString( this.getFields().get("oid") );
			String tmp[] = parameterStr.split(Constants.ID_DELIMITER);
			if (tmp.length == 2) {
				this.getFields().put("pdcaOid", tmp[0].trim());
				this.getFields().put("taskId", tmp[1].trim());
			}
		}
	}
	
	private void loadPdcaData() throws ServiceException, Exception {
		
		// ------------------------------------------------------------------------------
		// PDCA main
		this.loadPdcaDataSimple("oid");		
		// ------------------------------------------------------------------------------
		
		// ------------------------------------------------------------------------------
		// measure frequency
		this.measureFreq.setPdcaOid(pdca.getOid());
		DefaultResult<PdcaMeasureFreqVO> measureFreqResult = this.pdcaMeasureFreqService.findByUK(measureFreq);
		if (measureFreqResult.getValue() == null) {
			throw new ServiceException(measureFreqResult.getSystemMessage().getValue());
		}
		this.measureFreq = measureFreqResult.getValue();
		this.measureFreq.setEmployeeOid("");
		if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(this.measureFreq.getEmpId()) && !StringUtils.isBlank(this.measureFreq.getEmpId())) {
			EmployeeVO employee = new EmployeeVO();
			employee.setEmpId(this.measureFreq.getEmpId());
			DefaultResult<EmployeeVO> empResult = this.employeeService.findByUK(employee);
			if (empResult.getValue()!=null) {
				this.measureFreq.setEmployeeOid(empResult.getValue().getOid());
			}
		}
		this.measureFreq.setOrganizationOid("");
		if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(this.measureFreq.getOrgId()) && !StringUtils.isBlank(this.measureFreq.getOrgId())) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOrgId(this.measureFreq.getOrgId());
			DefaultResult<OrganizationVO> orgResult = this.organizationService.findByUK(organization);
			if (orgResult.getValue()!=null) {
				this.measureFreq.setOrganizationOid(orgResult.getValue().getOid());
			}
		}
		this.getFields().put("measureFreqStartDate", "");
		this.getFields().put("measureFreqEndDate", "");
		this.getFields().put("measureFreqStartYearDate", this.measureFreq.getStartDateTextBoxValue());
		this.getFields().put("measureFreqEndYearDate", this.measureFreq.getEndDateTextBoxValue());
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(measureFreq.getFreq()) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(measureFreq.getFreq()) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(measureFreq.getFreq()) ) {
			this.getFields().put("measureFreqStartDate", this.measureFreq.getStartDateTextBoxValue());
			this.getFields().put("measureFreqEndDate", this.measureFreq.getEndDateTextBoxValue());
			this.getFields().put("measureFreqStartYearDate", "");
			this.getFields().put("measureFreqEndYearDate", "");			
		}
		//1-DEPT,2-EMP,3-Both
		this.getFields().put("measureFreqDataFor", "all");
		if ("1".equals(this.measureFreq.getDataType())) {
			this.getFields().put("measureFreqDataFor", "organization");
		}
		if ("2".equals(this.measureFreq.getDataType())) {
			this.getFields().put("measureFreqDataFor", "employee");
		}
		// ------------------------------------------------------------------------------
		
		// ------------------------------------------------------------------------------
		// PDCA organization, Owner, KPIs, DOC
		List<String> appendOrgaOidsPdcaOrga = this.organizationService.findForAppendOrganizationOidsByPdcaOrga(this.pdca.getOid());
		List<String> appendOrgaNamesPdcaOrga = this.organizationService.findForAppendNames(appendOrgaOidsPdcaOrga);
		this.getFields().put( "appendOrgaOidsForPdcaOrga", this.joinAppend2String(appendOrgaOidsPdcaOrga) );
		this.getFields().put( "appendOrgaNamesForPdcaOrga", this.joinAppend2String(appendOrgaNamesPdcaOrga) );
		
		List<String> appendEmplOidsPdcaOwner = this.employeeService.findForAppendEmployeeOidsByPdcaOwner(pdca.getOid());
		List<String> appendEmplNamesPdcaOwner = this.employeeService.findForAppendNames(appendEmplOidsPdcaOwner);
		this.getFields().put( "appendOwnerOidsForPdcaOwner", this.joinAppend2String(appendEmplOidsPdcaOwner) );
		this.getFields().put( "appendOwnerNamesForPdcaOwner", this.joinAppend2String(appendEmplNamesPdcaOwner) );
		
		List<String> appendKpiOidsPdcaKpis = this.kpiService.findForAppendOidsByPdcaKpis(pdca.getOid());
		List<String> appendKpiNamesPdcaKpis = this.kpiService.findForAppendNames(appendKpiOidsPdcaKpis);
		this.getFields().put( "appendKpiOidsForPdcaKpis", this.joinAppend2String(appendKpiOidsPdcaKpis) );
		this.getFields().put( "appendKpiNamesForPdcaKpis", this.joinAppend2String(appendKpiNamesPdcaKpis) );
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());		
		this.pdcaDocs = this.pdcaDocService.findListVOByParams(paramMap);
		for (int i=0; this.pdcaDocs!=null && i<this.pdcaDocs.size(); i++) {
			PdcaDocVO pdcaDoc = this.pdcaDocs.get(i);
			pdcaDoc.setShowName( "unknown-" + pdcaDoc.getUploadOid() );
			DefaultResult<SysUploadVO> upResult = this.sysUploadService.findForNoByteContent(pdcaDoc.getUploadOid());
			if (upResult.getValue()!=null) {
				pdcaDoc.setShowName( upResult.getValue().getShowName() );
			}
		}
		// ------------------------------------------------------------------------------
		
		// ------------------------------------------------------------------------------
		// PDCA Items
		this.loadPdcaItemsData();
		// ------------------------------------------------------------------------------
		
	}
	
	private void loadPdcaItemsData() throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());		
		this.pdcaItems = this.pdcaItemService.findListVOByParams(paramMap);
		for (PdcaItemVO item : this.pdcaItems) {
			List<String> appendEmplOidsPdcaItemOwner = this.employeeService.findForAppendEmployeeOidsByPdcaItemOwner(item.getPdcaOid(), item.getOid());
			List<String> appendEmplNamesPdcaItemOwner = this.employeeService.findForAppendNames(appendEmplOidsPdcaItemOwner);
			item.setEmployeeAppendOids( this.joinAppend2String(appendEmplOidsPdcaItemOwner) );
			item.setEmployeeAppendNames( this.joinAppend2String(appendEmplNamesPdcaItemOwner) );
			paramMap.put("itemOid", item.getOid());
			List<PdcaItemDocVO> itemDocs = this.pdcaItemDocService.findListVOByParams(paramMap);
			for (int i=0; itemDocs!=null && i<itemDocs.size(); i++) {
				PdcaItemDocVO itemDoc = itemDocs.get(i);
				itemDoc.setShowName( "unknown-" + itemDoc.getUploadOid() );
				DefaultResult<SysUploadVO> upResult = this.sysUploadService.findForNoByteContent(itemDoc.getUploadOid());
				if (upResult.getValue()!=null) {
					itemDoc.setShowName( upResult.getValue().getShowName() );
				}				
				item.getDocs().add(itemDoc);
			}
		}
	}
	
	private void initDataForCreateOrEdit() throws ServiceException, Exception {
		/*
		if ( YesNo.YES.equals(super.getIsSuperRole()) ) {
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);
			return;
		} 
		this.measureDataOrganizationMap = this.reportRoleViewLogicService.findForOrganizationMap(
				true, this.getAccountId());
		this.measureDataEmployeeMap = this.reportRoleViewLogicService.findForEmployeeMap(
				true, this.getAccountId());
		*/
		
		/**
		 * 沒有資料表示,沒有限定使用者的角色,只能選取某些部門或某些員工
		 * 因為沒有限定就全部取出
		 */
		/*
		if ( this.measureDataOrganizationMap.size() <= 1 && this.measureDataEmployeeMap.size() <= 1 ) { // 第1筆是 - Please select -
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);			
		}
		*/
		
		// 這邊是設定配合需要被PDCA處理的項目, 所以不要限定
		this.measureDataOrganizationMap = this.organizationService.findForMap(true);
		this.measureDataEmployeeMap = this.employeeService.findForMap(true);
		
	}
	
	// PDCA main
	private void loadPdcaDataSimple(String fieldName) throws ServiceException, Exception {
		this.transformFields2ValueObject( this.pdca, new String[]{"oid"}, new String[]{fieldName} );
		DefaultResult<PdcaVO> pdcaResult = this.pdcaService.findObjectByOid(pdca);
		if (pdcaResult.getValue() == null) {
			throw new ServiceException(pdcaResult.getSystemMessage().getValue());
		}
		this.pdca = pdcaResult.getValue();
		
	}
	
	private void loadProjectTask() throws Exception {
		
		List<BusinessProcessManagementTaskVO> tasks = this.pdcaLogicService.queryTaskByVariablePdcaOid( this.pdca.getOid() );
		if (tasks.size() != 1) {
			return;
		}
		this.bpmTaskObj = tasks.get(0);	
		String date = this.bpmTaskObj.getVariableValueAsString( "date" );
		this.bpmTaskObj.getVariables().put("dateDisplayValue", SimpleUtils.getStrYMD(date, "/"));
		
	}
	
	/**
	 *  bsc.pdcaManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG006D0001Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}	
	
	/**
	 *  bsc.pdcaCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG006D0001A")	
	public String create() throws Exception {
		try {
			this.initData("create");
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;	
	}
	
	/**
	 * bsc.pdcaEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG006D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.loadPdcaData();
			this.loadProjectTask();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;			
	}	
	
	/**
	 * bsc.pdcaConfirmDialogAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG006D0001E_S00")
	public String confirmDialog() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("confirmDialog");
			this.loadPdcaDataSimple("pdcaOid");
			this.loadProjectTask();
			forward = SUCCESS;
			if (this.bpmTaskObj == null || !YesNo.YES.equals(this.bpmTaskObj.getAllowApproval())) {
				forward = RESULT_NO_AUTHORITH;
			}
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;			
	}
	
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}
	
	public Map<String, String> getFrequencyMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.frequencyMap);
		return frequencyMap;
	}

	public Map<String, String> getMeasureDataOrganizationMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataOrganizationMap);
		return measureDataOrganizationMap;
	}

	public Map<String, String> getMeasureDataEmployeeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataEmployeeMap);
		return measureDataEmployeeMap;
	}

	public Map<String, String> getPdcaTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.pdcaTypeMap);
		return pdcaTypeMap;
	}

	public PdcaVO getPdca() {
		return pdca;
	}

	public void setPdca(PdcaVO pdca) {
		this.pdca = pdca;
	}

	public PdcaMeasureFreqVO getMeasureFreq() {
		return measureFreq;
	}

	public void setMeasureFreq(PdcaMeasureFreqVO measureFreq) {
		this.measureFreq = measureFreq;
	}

	public List<PdcaDocVO> getPdcaDocs() {
		return pdcaDocs;
	}

	public void setPdcaDocs(List<PdcaDocVO> pdcaDocs) {
		this.pdcaDocs = pdcaDocs;
	}

	public List<PdcaItemVO> getPdcaItems() {
		return pdcaItems;
	}

	public void setPdcaItems(List<PdcaItemVO> pdcaItems) {
		this.pdcaItems = pdcaItems;
	}

	public BusinessProcessManagementTaskVO getBpmTaskObj() {
		return bpmTaskObj;
	}

	public void setBpmTaskObj(BusinessProcessManagementTaskVO bpmTaskObj) {
		this.bpmTaskObj = bpmTaskObj;
	}	

}
