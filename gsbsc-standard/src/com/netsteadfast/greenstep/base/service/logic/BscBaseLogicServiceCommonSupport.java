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
package com.netsteadfast.greenstep.base.service.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

public class BscBaseLogicServiceCommonSupport {
	
	public static OrganizationVO findOrganizationData(IOrganizationService<OrganizationVO, BbOrganization, String> service, String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		OrganizationVO organization = new OrganizationVO();
		organization.setOid(oid);
		DefaultResult<OrganizationVO> orgResult = service.findObjectByOid(organization);
		if (orgResult.getValue() == null) {
			throw new ServiceException( orgResult.getSystemMessage().getValue() );
		}
		organization = orgResult.getValue();
		return organization;		
	}
	
	public static EmployeeVO findEmployeeData(IEmployeeService<EmployeeVO, BbEmployee, String> service, String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		EmployeeVO employee = new EmployeeVO();
		employee.setOid(oid);
		DefaultResult<EmployeeVO> empResult = service.findObjectByOid(employee);
		if (empResult.getValue() == null) {
			throw new ServiceException(empResult.getSystemMessage().getValue());
		}
		employee = empResult.getValue();
		return employee;		
	}
	
	public static OrganizationVO findOrganizationDataByUK(IOrganizationService<OrganizationVO, BbOrganization, String> service, String orgId) throws ServiceException, Exception {
		if (StringUtils.isBlank(orgId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		OrganizationVO organization = new OrganizationVO();
		organization.setOrgId(orgId);
		DefaultResult<OrganizationVO> orgResult = service.findByUK(organization);
		if (orgResult.getValue() == null) {
			throw new ServiceException( orgResult.getSystemMessage().getValue() );
		}
		organization = orgResult.getValue();
		return organization;				
	}

	public static EmployeeVO findEmployeeDataByUK(IEmployeeService<EmployeeVO, BbEmployee, String> service, String accountId, String empId) throws ServiceException, Exception {
		if (StringUtils.isBlank(accountId) || StringUtils.isBlank(empId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}	
		EmployeeVO employee = new EmployeeVO();
		employee.setAccount(accountId);
		employee.setEmpId(empId);
		DefaultResult<EmployeeVO> empResult = service.findByUK(employee);
		if (empResult.getValue() == null) {
			throw new ServiceException(empResult.getSystemMessage().getValue());
		}
		employee = empResult.getValue();
		return employee;		
	}		
	
	public static EmployeeVO findEmployeeDataByAccountId(IEmployeeService<EmployeeVO, BbEmployee, String> service, String accountId) throws ServiceException, Exception {
		if (StringUtils.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		BbEmployee poEmployee = service.findByAccountId(accountId);
		EmployeeVO employeeObj = new EmployeeVO();
		service.doMapper(poEmployee, employeeObj, IEmployeeService.MAPPER_ID_PO2VO);
		return employeeObj;
	}	
	
	public static EmployeeVO findEmployeeDataByEmpId(IEmployeeService<EmployeeVO, BbEmployee, String> service, String empId) throws ServiceException, Exception {
		if (StringUtils.isBlank(empId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("empId", empId);
		List<EmployeeVO> employeeList = service.findListVOByParams(paramMap);
		if (employeeList == null || employeeList.size() < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		if (employeeList.size() != 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		return employeeList.get(0);
	}

}
