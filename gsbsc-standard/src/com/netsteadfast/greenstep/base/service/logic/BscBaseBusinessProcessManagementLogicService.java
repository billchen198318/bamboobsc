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

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

public abstract class BscBaseBusinessProcessManagementLogicService extends BusinessProcessManagementBaseLogicService implements IBscBaseLogicServiceCommonProvide {
	
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	
	public BscBaseBusinessProcessManagementLogicService() {
		super();
	}
	
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required
	public void setEmployeeService(IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")
	@Required
	public void setOrganizationService(IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}
	
	public OrganizationVO findOrganizationData(String oid) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findOrganizationData(organizationService, oid);
	}
	
	public EmployeeVO findEmployeeData(String oid) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findEmployeeData(employeeService, oid);
	}	
	
	public OrganizationVO findOrganizationDataByUK(String orgId) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findOrganizationDataByUK(organizationService, orgId);
	}
	
	public EmployeeVO findEmployeeDataByUK(String accountId, String empId) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findEmployeeDataByUK(employeeService, accountId, empId);
	}
	
	public EmployeeVO findEmployeeDataByAccountId(String accountId) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findEmployeeDataByAccountId(employeeService, accountId);
	}
	
	public EmployeeVO findEmployeeDataByEmpId(String empId) throws ServiceException, Exception {
		return BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(employeeService, empId);
	}

}
