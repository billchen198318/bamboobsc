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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.bsc.service.IKpiEmplService;
import com.netsteadfast.greenstep.bsc.service.IKpiOrgaService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbKpiEmpl;
import com.netsteadfast.greenstep.po.hbm.BbKpiOrga;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiEmplVO;
import com.netsteadfast.greenstep.vo.KpiOrgaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.KpiLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class KpiLogicServiceImpl extends BaseLogicService implements IKpiLogicService {
	protected Logger logger=Logger.getLogger(KpiLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IFormulaService<FormulaVO, BbFormula, String> formulaService;
	private IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService;
	private IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService; 
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	
	public KpiLogicServiceImpl() {
		super();
	}
	
	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.ObjectiveService")
	@Required			
	public void setObjectiveService(
			IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
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
	
	public IFormulaService<FormulaVO, BbFormula, String> getFormulaService() {
		return formulaService;
	}

	@Autowired
	@Resource(name="bsc.service.FormulaService")
	@Required			
	public void setFormulaService(
			IFormulaService<FormulaVO, BbFormula, String> formulaService) {
		this.formulaService = formulaService;
	}

	public IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> getKpiOrgaService() {
		return kpiOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiOrgaService")
	@Required				
	public void setKpiOrgaService(
			IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService) {
		this.kpiOrgaService = kpiOrgaService;
	}

	public IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> getKpiEmplService() {
		return kpiEmplService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiEmplService")
	@Required			
	public void setKpiEmplService(
			IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService) {
		this.kpiEmplService = kpiEmplService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required	
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")
	@Required		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	public IMeasureDataService<MeasureDataVO, BbMeasureData, String> getMeasureDataService() {
		return measureDataService;
	}

	@Autowired
	@Resource(name="bsc.service.MeasureDataService")
	@Required			
	public void setMeasureDataService(
			IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService) {
		this.measureDataService = measureDataService;
	}
	
	private void handlerDataForCreateOrUpdate(KpiVO kpi, 
			String objectiveOid, String formulaOid, String aggrOid) throws ServiceException, Exception {
		ObjectiveVO objective = new ObjectiveVO();
		objective.setOid(objectiveOid);
		DefaultResult<ObjectiveVO> objResult = this.objectiveService.findObjectByOid(objective);
		if (objResult.getValue()==null) {
			throw new ServiceException( objResult.getSystemMessage().getValue() );
		}
		objective = objResult.getValue();
		FormulaVO formula = new FormulaVO();
		formula.setOid(formulaOid);
		DefaultResult<FormulaVO> forResult = this.formulaService.findObjectByOid(formula);
		if (forResult.getValue()==null) {
			throw new ServiceException( forResult.getSystemMessage().getValue() );
		}
		formula = forResult.getValue();
		kpi.setObjId( objective.getObjId() );
		kpi.setForId( formula.getForId() );
		kpi.setCal( AggregationMethodUtils.findSimpleByOid(aggrOid).getAggrId() );
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<KpiVO> create(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids) throws ServiceException, Exception {
		if (null == kpi || super.isNoSelectId(objectiveOid) || super.isNoSelectId(formulaOid) || super.isNoSelectId(aggrOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(kpi, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerDataForCreateOrUpdate(kpi, objectiveOid, formulaOid, aggrOid);
		DefaultResult<KpiVO> result = this.kpiService.saveObject(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}				
		this.createKpiOrganization(result.getValue(), organizationOids); // create KPI's organization				
		this.createKpiEmployee(result.getValue(), employeeOids); // create KPI's owner
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<KpiVO> update(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids) throws ServiceException, Exception {
		if (null == kpi || super.isBlank(kpi.getOid()) 
				|| super.isNoSelectId(objectiveOid) || super.isNoSelectId(formulaOid) || super.isNoSelectId(aggrOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<KpiVO> oldResult = this.kpiService.findObjectByOid(kpi);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		this.setStringValueMaxLength(kpi, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerDataForCreateOrUpdate(kpi, objectiveOid, formulaOid, aggrOid);
		kpi.setId( oldResult.getValue().getId() );		
		DefaultResult<KpiVO> result = this.kpiService.updateObject(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}				
		kpi = result.getValue();		
		this.deleteKpiOrganization(kpi); // delete KPI's organization
		this.deleteKpiEmployee(kpi); // delete KPI's owner
		this.createKpiOrganization(result.getValue(), organizationOids); // create KPI's organization				
		this.createKpiEmployee(result.getValue(), employeeOids); // create KPI's owner
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(KpiVO kpi) throws ServiceException, Exception {
		if (null == kpi || super.isBlank(kpi.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<KpiVO> oldResult = this.kpiService.findObjectByOid(kpi);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		this.deleteKpiOrganization( oldResult.getValue() ); // delete KPI's organization
		this.deleteKpiEmployee( oldResult.getValue() ); // delete KPI's owner						
		this.measureDataService.deleteForKpiId( oldResult.getValue().getId() ); // delete measuer data
		return this.kpiService.deleteObject(kpi);		
	}	
	
	private void deleteKpiOrganization(KpiVO kpi) throws ServiceException, Exception {
		if (kpi==null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kpiId", kpi.getId());
		List<BbKpiOrga> searchList = this.kpiOrgaService.findListByParams(params);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbKpiOrga kpiOrga : searchList) {
			this.kpiOrgaService.delete(kpiOrga);
		}
	}
	
	private void deleteKpiEmployee(KpiVO kpi) throws ServiceException, Exception {
		if (kpi==null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kpiId", kpi.getId());
		List<BbKpiEmpl> searchList = this.kpiEmplService.findListByParams(params);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbKpiEmpl kpiEmpl : searchList) {
			this.kpiEmplService.delete(kpiEmpl);
		}		
	}
	
	private void createKpiOrganization(KpiVO kpi, List<String> organizationOids) throws ServiceException, Exception {
		if (kpi==null || organizationOids==null || organizationOids.size() < 1 ) {
			return;
		}
		for (String oid : organizationOids) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid(oid);
			DefaultResult<OrganizationVO> oResult = this.organizationService.findObjectByOid(organization);
			if (oResult.getValue()==null) {
				throw new ServiceException(oResult.getSystemMessage().getValue());
			}
			organization = oResult.getValue();
			KpiOrgaVO kpiOrga = new KpiOrgaVO();
			kpiOrga.setKpiId(kpi.getId());
			kpiOrga.setOrgId(organization.getOrgId());
			this.kpiOrgaService.saveObject(kpiOrga);
		}
	}
	
	private void createKpiEmployee(KpiVO kpi, List<String> employeeOids) throws ServiceException, Exception {
		if (kpi==null || employeeOids==null || employeeOids.size() < 1 ) {
			return;
		}
		for (String oid : employeeOids) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid(oid);
			DefaultResult<EmployeeVO> eResult = this.employeeService.findObjectByOid(employee);
			if (eResult.getValue()==null) {
				throw new ServiceException(eResult.getSystemMessage().getValue());
			}
			employee = eResult.getValue();
			KpiEmplVO kpiEmpl = new KpiEmplVO();
			kpiEmpl.setKpiId(kpi.getId());
			kpiEmpl.setEmpId(employee.getEmpId());
			this.kpiEmplService.saveObject(kpiEmpl);
		}		
	}

}
