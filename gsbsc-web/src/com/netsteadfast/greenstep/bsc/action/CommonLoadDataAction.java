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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.logic.IImportDataLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.util.ExportData2CsvUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

/**
 * 抓取json資料共用的action
 *
 */
@ControllerAuthority(check=false)
@Controller("bsc.web.controller.CommonLoadDataAction")
@Scope
public class CommonLoadDataAction extends BaseJsonAction {
	private static final long serialVersionUID = -2697103469702576490L;
	protected Logger logger=Logger.getLogger(CommonLoadDataAction.class);
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IImportDataLogicService importDataLogicService;
	private String message = "";
	private String success = IS_NO;
	private String appendName = ""; // 放部門名稱
	private List<Map<String, String>> items=new LinkedList<Map<String, String>>();
	private List<Map<String, String>> kpiEmpl = new LinkedList<Map<String, String>>(); // 數據資料維護時, 點選KPI後要出現的 KPI所屬的員工
	private List<Map<String, String>> kpiOrga = new LinkedList<Map<String, String>>(); // 數據資料維護時, 點選KPI後要出現的 KPI所屬的部門
	private KpiVO kpi = new KpiVO();
	private OrganizationVO organization = new OrganizationVO();
	private String oid = ""; // 匯出csv 產出的 TB_SYS_UPLOAD.OID
	
	public CommonLoadDataAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")			
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	@JSON(serialize=false)
	public IPerspectiveService<PerspectiveVO, BbPerspective, String> getPerspectiveService() {
		return perspectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.PerspectiveService")	
	public void setPerspectiveService(
			IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}

	@JSON(serialize=false)
	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.ObjectiveService")		
	public void setObjectiveService(
			IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
	}

	@JSON(serialize=false)
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")	
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	@JSON(serialize=false)
	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")	
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}

	@JSON(serialize=false)
	public IImportDataLogicService getImportDataLogicService() {
		return importDataLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.ImportDataLogicService")	
	public void setImportDataLogicService(
			IImportDataLogicService importDataLogicService) {
		this.importDataLogicService = importDataLogicService;
	}

	private void loadOrganizationAppendNames() throws ControllerException, AuthorityException, ServiceException, Exception {
		List<String> names = this.organizationService.findForAppendNames(
				super.transformAppendIds2List( super.defaultString(this.getFields().get("appendId")) ) );
		StringBuilder sb = new StringBuilder();
		for (int i=0; names!=null && i<names.size(); i++) {
			sb.append(names.get(i)).append(Constants.ID_DELIMITER);
		}
		this.appendName = sb.toString();
		this.success = IS_YES;
	}
	
	private void loadEmployeeAppendNames() throws ControllerException, AuthorityException, ServiceException, Exception {
		List<String> names = this.employeeService.findForAppendNames(
				super.transformAppendIds2List( super.defaultString(this.getFields().get("appendId")) ) );
		StringBuilder sb = new StringBuilder();
		for (int i=0; names!=null && i<names.size(); i++) {
			sb.append(names.get(i)).append(Constants.ID_DELIMITER);
		}
		this.appendName = sb.toString();
		this.success = IS_YES;		
	}
	
	private void loadKpisAppendNames() throws ControllerException, AuthorityException, ServiceException, Exception {
		List<String> names = this.kpiService.findForAppendNames(
				super.transformAppendIds2List( super.defaultString(this.getFields().get("appendId")) ) );
		StringBuilder sb = new StringBuilder();
		for (int i=0; names!=null && i<names.size(); i++) {
			sb.append(names.get(i)).append(Constants.ID_DELIMITER);
		}
		this.appendName = sb.toString();
		this.success = IS_YES;		
	}
	
	private void fillDataMap2Items(Map<String, String> sourceDataMap) throws Exception {
		if (null==sourceDataMap) {
			return;
		}
		for (Map.Entry<String, String> entry : sourceDataMap.entrySet()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("key", entry.getKey());
			dataMap.put("value", entry.getValue());
			this.items.add(dataMap);
		}		
	}
	
	private void loadPerspectiveItems() throws ControllerException, AuthorityException, ServiceException, Exception {
		Map<String, String> perspectiveMap = null;
		if (!this.isNoSelectId(this.getFields().get("visionOid")) ) {
			perspectiveMap = this.perspectiveService.findForMapByVisionOid(this.getFields().get("visionOid"), true);			
		} else {
			perspectiveMap = this.providedSelectZeroDataMap(true);
		}
		this.resetPleaseSelectDataMapFromLocaleLang(perspectiveMap);
		this.fillDataMap2Items(perspectiveMap);
		this.success = IS_YES;		
	}
	
	private void loadObjectiveItems() throws ControllerException, AuthorityException, ServiceException, Exception {
		Map<String, String> objectiveMap = null;
		if (!this.isNoSelectId(this.getFields().get("perspectiveOid")) ) {
			objectiveMap = this.objectiveService.findForMapByPerspectiveOid(this.getFields().get("perspectiveOid"), true);
		} else {
			objectiveMap = this.providedSelectZeroDataMap(true);
		}
		this.resetPleaseSelectDataMapFromLocaleLang(objectiveMap);
		this.fillDataMap2Items(objectiveMap);
		this.success = IS_YES;
	}
	
	private void loadKpiData() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.transformFields2ValueObject(this.kpi, new String[]{"oid"});
		DefaultResult<KpiVO> result = this.kpiService.findObjectByOid(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.kpi = result.getValue();
		this.success = IS_YES;
	}
	
	private void loadMeasureDataOptions() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.transformFields2ValueObject(this.kpi, new String[]{"oid"});
		DefaultResult<KpiVO> result = this.kpiService.findObjectByOid(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.kpi = result.getValue();
		DefaultResult<List<BbOrganization>> orgResult = this.organizationService.findForInKpiOrga(this.kpi.getId());
		DefaultResult<List<BbEmployee>> empResult = this.employeeService.findForInKpiEmpl(this.kpi.getId());
		Map<String, String> firstMap =new HashMap<String, String>();
		firstMap.put("key", Constants.HTML_SELECT_NO_SELECT_ID);
		firstMap.put("value", Constants.HTML_SELECT_NO_SELECT_NAME);
		this.resetPleaseSelectDataMapFromLocaleLang(firstMap);
		this.kpiOrga.add(firstMap);
		this.kpiEmpl.add(firstMap);
		if (orgResult.getValue()!=null) {
			for (BbOrganization organization : orgResult.getValue()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("key", organization.getOid());
				dataMap.put("value", organization.getName());
				this.kpiOrga.add(dataMap);
			}
		}
		if (empResult.getValue()!=null) {
			for (BbEmployee employee : empResult.getValue()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("key", employee.getOid());
				dataMap.put("value", employee.getFullName());
				this.kpiEmpl.add(dataMap);				
			}
		}
		this.success = IS_YES;
	}
	
	private void loadOrganizationData() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.transformFields2ValueObject(this.organization, new String[]{"oid"});
		DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(organization);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.organization = result.getValue();
		this.success = IS_YES;
	}
	
	/**
	 * 匯出CSV 檔案
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void exportData2Csv() throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
			if (!entry.getKey().equals("exportId")) {
				paramMap.put(entry.getKey(), entry.getValue());
			}
		}
		this.oid = ExportData2CsvUtils.create(
				this.getFields().get("exportId")+".xml", 
				paramMap);		
		this.success = IS_YES;
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
	}
	
	public void importCsvData() throws ControllerException, ServiceException, Exception {
		String importType = this.getFields().get("importType");
		String uploadOid = this.getFields().get("uploadOid");
		if (StringUtils.isBlank(uploadOid) || StringUtils.isBlank(importType)) {
			throw new ControllerException( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		DefaultResult<Boolean> result = null;
		if ( "vision".equals( importType ) ) { // vision
			result = this.importDataLogicService.importVisionCsv(uploadOid);			
		} else if ( "perspective".equals( importType ) ) { // perspectives
			result = this.importDataLogicService.importPerspectivesCsv(uploadOid);
		} else if ( "objective".equals( importType ) ) { // strategy-objectives
			result = this.importDataLogicService.importObjectivesCsv(uploadOid);
		} else if ( "kpi".equals( importType ) ) { // KPIs
			result = this.importDataLogicService.importKPIsCsv(uploadOid);
		} else { // measure-data
			result = this.importDataLogicService.importMeasureData(uploadOid);
		}
		if ( result.getValue()!=null && result.getValue() ) {
			this.success = IS_YES;
		}
		this.message = result.getSystemMessage().getValue();
	}
	
	/**
	 * bsc.commonGetOrganizationNamesAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getOrganizationNames() throws Exception {
		try {
			this.loadOrganizationAppendNames();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * bsc.commonGetEmployeeNamesAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getEmployeeNames() throws Exception {
		try {
			this.loadEmployeeAppendNames();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	/**
	 * bsc.commonGetKpisNamesAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String getKpisNames() throws Exception {
		try {
			this.loadKpisAppendNames();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) {
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	/**
	 * bsc.commonGetPerspectiveItemsAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getPerspectiveItems() throws Exception {
		try {
			this.loadPerspectiveItems();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * bsc.commonGetObjectiveItemsAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getObjectiveItems() throws Exception {
		try {
			this.loadObjectiveItems();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	/**
	 * bsc.commonGetKpiDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getKpiData() throws Exception {
		try {
			this.loadKpiData();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}		
	
	/**
	 * bsc.commonGetKpiOrgaAndEmplAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false) // 這邊不加入 JSON serialize=false 就會怪怪的, 每個action都會自動的呼叫getKpiData , 懷疑是有變數kpi 與 getKpiData() 搞混了
	public String getKpiOrgaAndEmpl() throws Exception {
		try {
			this.loadMeasureDataOptions();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;				
	}
	
	/**
	 * bsc.commonGetOrganizationDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String getOrganizationData() throws Exception {
		try {
			this.loadOrganizationData();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;				
	}
	
	/**
	 * bsc.commonDoExportData2CsvAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String doExportData2Csv() throws Exception {
		try {
			this.exportData2Csv();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * bsc.commonDoImportCsvDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String doImportCsvData() throws Exception {
		try {
			this.importCsvData();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}

	public String getAppendName() {
		return appendName;
	}

	public List<Map<String, String>> getItems() {
		return items;
	}

	public KpiVO getKpi() {
		return kpi;
	}

	public List<Map<String, String>> getKpiEmpl() {
		return kpiEmpl;
	}

	public List<Map<String, String>> getKpiOrga() {
		return kpiOrga;
	}

	public OrganizationVO getOrganization() {
		return organization;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
