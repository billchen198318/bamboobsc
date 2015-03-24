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
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.model.ReportRoleViewTypes;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IReportRoleViewService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbReportRoleView;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.ReportRoleViewVO;
import com.netsteadfast.greenstep.vo.RoleVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.ReportRoleViewLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ReportRoleViewLogicServiceImpl extends BaseLogicService implements IReportRoleViewLogicService {
	protected Logger logger=Logger.getLogger(ReportRoleViewLogicServiceImpl.class);
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService; 
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService;
	
	public ReportRoleViewLogicServiceImpl() {
		super();
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
	
	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}

	@Autowired
	@Resource(name="core.service.RoleService")
	@Required		
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}	
	
	public IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> getReportRoleViewService() {
		return reportRoleViewService;
	}

	@Autowired
	@Resource(name="bsc.service.ReportRoleViewService")
	@Required		
	public void setReportRoleViewService(
			IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService) {
		this.reportRoleViewService = reportRoleViewService;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> create(String roleOid, List<String> emplOids, List<String> orgaOids) throws ServiceException, Exception {
		if ( super.isNoSelectId(roleOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		RoleVO role = new RoleVO();
		role.setOid(roleOid);
		DefaultResult<RoleVO> rResult = this.roleService.findObjectByOid(role);
		if ( rResult.getValue() == null ) {
			throw new ServiceException( rResult.getSystemMessage().getValue() );
		}
		role = rResult.getValue();
		this.deleteByRole( role.getRole() );		
		for (int i=0; emplOids!=null && i<emplOids.size(); i++) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid( emplOids.get(i) );
			DefaultResult<EmployeeVO> eResult = this.employeeService.findObjectByOid(employee);
			if ( eResult.getValue() == null ) {
				throw new ServiceException( eResult.getSystemMessage().getValue() );
			}
			employee = eResult.getValue();			
			this.createReportRoleView(role.getRole(), ReportRoleViewTypes.IS_EMPLOYEE, employee.getAccount());			
		}
		for (int i=0; orgaOids!=null && i<orgaOids.size(); i++) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid( orgaOids.get(i) );
			DefaultResult<OrganizationVO> oResult = this.organizationService.findObjectByOid(organization);
			if ( oResult.getValue() == null ) {
				throw new ServiceException( oResult.getSystemMessage().getValue() );
			}
			organization = oResult.getValue();
			this.createReportRoleView(role.getRole(), ReportRoleViewTypes.IS_ORGANIZATION, organization.getOrgId());
		}		
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS) ) );
		return result;
	}
	
	private void createReportRoleView(String roleId, String type, String idName) throws ServiceException, Exception {
		ReportRoleViewVO reportRoleView = new ReportRoleViewVO();
		reportRoleView.setIdName(idName);
		reportRoleView.setType(type);
		reportRoleView.setRole(roleId);
		this.reportRoleViewService.saveObject(reportRoleView);
	}
	
	private void deleteByRole(String roleId) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("role", roleId);
		List<BbReportRoleView> rRoleViews = this.reportRoleViewService.findListByParams(paramMap);
		for (int i=0; rRoleViews!=null && i<rRoleViews.size(); i++) {
			this.reportRoleViewService.delete( rRoleViews.get(i) );
		}
	}

}
