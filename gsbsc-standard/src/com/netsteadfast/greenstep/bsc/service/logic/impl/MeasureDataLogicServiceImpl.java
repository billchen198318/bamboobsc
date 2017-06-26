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

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.model.ItemTargetOrMaximumAndMinimalValue;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.logic.IMeasureDataLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.MeasureDataLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class MeasureDataLogicServiceImpl extends CoreBaseLogicService implements IMeasureDataLogicService {
	protected Logger logger=Logger.getLogger(MeasureDataLogicServiceImpl.class);
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService; 
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	
	public MeasureDataLogicServiceImpl() {
		super();
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
	
	private String getOrganizationId(String organizationId) throws ServiceException, Exception {
		if (BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(organizationId)) {
			return organizationId;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", organizationId);
		if (this.organizationService.countByParams(params) < 1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		return organizationId;
	}
	
	private String getEmployeeId(String employeeId) throws ServiceException, Exception {
		if (BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(employeeId)) {
			return employeeId;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", employeeId);
		if (this.employeeService.countByParams(params) < 1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}		
		return employeeId;
	}
	
	private void fillMeasureDatas(KpiVO kpi, String organizationId, String employeeId, List<MeasureDataVO> measureDatas) {
		for (MeasureDataVO measureData : measureDatas) {
			measureData.setKpiId(kpi.getId());
			measureData.setEmpId(employeeId);
			measureData.setOrgId(organizationId);
		}
	}
	
	private void delete(KpiVO kpi, String date, String frequency, 
			String organizationId, String employeeId) throws ServiceException, Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> likeParams = new HashMap<String, String>();
		params.put("kpiId", kpi.getId());
		params.put("frequency", frequency);
		params.put("orgId", organizationId);
		params.put("empId", employeeId);
		likeParams.put("date", date+"%");
		List<BbMeasureData> searchList = this.measureDataService.findListByParams(params, likeParams);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbMeasureData measureData : searchList) {
			this.measureDataService.delete(measureData);
		}
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE, ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> saveOrUpdate(String kpiOid, String date,
			String frequency, String dataFor, String organizationId,
			String employeeId, List<MeasureDataVO> measureDatas)
			throws ServiceException, Exception {
		
		if (super.isBlank(kpiOid) || super.isBlank(date) || super.isBlank(frequency) || super.isBlank(dataFor) 
				|| super.isBlank(organizationId) || super.isBlank(employeeId) ) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		KpiVO kpi = new KpiVO();
		kpi.setOid(kpiOid);
		DefaultResult<KpiVO> kResult = this.kpiService.findObjectByOid(kpi);
		if (kResult.getValue()==null) {
			throw new ServiceException( kResult.getSystemMessage().getValue() );
		}
		kpi = kResult.getValue();
		this.getOrganizationId(organizationId);
		this.getEmployeeId(employeeId);
		if (BscConstants.MEASURE_DATA_FOR_ORGANIZATION.equals(dataFor) 
				&& BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(organizationId) ) {
			throw new ServiceException("organization is required!");
		}
		if (BscConstants.MEASURE_DATA_FOR_EMPLOYEE.equals(dataFor) 
				&& BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(employeeId) ) {
			throw new ServiceException("employee is required!");
		}		
		this.fillMeasureDatas(kpi, organizationId, employeeId, measureDatas);
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)) );
		result.setValue(Boolean.FALSE);
		this.delete(kpi, date, frequency, organizationId, employeeId);
		for (MeasureDataVO measureData : measureDatas) {
			measureData.setActual( ItemTargetOrMaximumAndMinimalValue.get(measureData.getActual()) );
			measureData.setTarget( ItemTargetOrMaximumAndMinimalValue.get(measureData.getTarget()) );
			this.measureDataService.saveObject(measureData);
		}
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );
		result.setValue(Boolean.TRUE);
		return result;
	}

}
