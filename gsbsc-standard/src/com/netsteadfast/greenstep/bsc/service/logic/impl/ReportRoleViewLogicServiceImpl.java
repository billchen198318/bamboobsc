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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.model.ReportRoleViewTypes;
import com.netsteadfast.greenstep.bsc.service.IReportRoleViewService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbReportRoleView;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.ReportRoleViewVO;
import com.netsteadfast.greenstep.vo.RoleVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.ReportRoleViewLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ReportRoleViewLogicServiceImpl extends BscBaseLogicService implements IReportRoleViewLogicService {
	protected Logger logger=Logger.getLogger(ReportRoleViewLogicServiceImpl.class);
	private IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService;
	
	public ReportRoleViewLogicServiceImpl() {
		super();
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
		DefaultResult<RoleVO> rResult = this.getRoleService().findObjectByOid(role);
		if ( rResult.getValue() == null ) {
			throw new ServiceException( rResult.getSystemMessage().getValue() );
		}
		role = rResult.getValue();
		this.deleteByRole( role.getRole() );		
		for (int i=0; emplOids!=null && i<emplOids.size(); i++) {
			EmployeeVO employee = this.findEmployeeData( emplOids.get(i) );		
			this.createReportRoleView(role.getRole(), ReportRoleViewTypes.IS_EMPLOYEE, employee.getAccount());			
		}
		for (int i=0; orgaOids!=null && i<orgaOids.size(); i++) {
			OrganizationVO organization = this.findOrganizationData( orgaOids.get(i) );
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
	
	private List<TbUserRole> getUserRoles(String account) throws ServiceException, Exception {
		return this.findUserRoles(account);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public Map<String, String> findForEmployeeMap(boolean pleaseSelect, String accountId) throws ServiceException, Exception {
		if ( super.isBlank(accountId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		List<TbUserRole> roles = this.getUserRoles(accountId);
		for (int i=0; roles!=null && i<roles.size(); i++) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("role", roles.get(i).getRole());
			paramMap.put("type", ReportRoleViewTypes.IS_EMPLOYEE);
			List<BbReportRoleView> views = this.reportRoleViewService.findListByParams(paramMap);
			for (int j=0; views!=null && j<views.size(); j++) {
				paramMap.clear();
				paramMap.put("account", views.get(j).getIdName());
				List<BbEmployee> employees = this.getEmployeeService().findListByParams(paramMap);
				for (int e=0; employees!=null && e<employees.size(); e++) {
					BbEmployee employee = employees.get(e);
					if ( dataMap.get( employee.getOid() )!=null ) {
						continue;
					}
					dataMap.put( employee.getOid(), employee.getFullName() );
				}
			}
		}
		return dataMap;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public Map<String, String> findForOrganizationMap(boolean pleaseSelect, String accountId) throws ServiceException, Exception {
		if ( super.isBlank(accountId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		/*
		List<TbUserRole> roles = this.getUserRoles(accountId);
		for (int i=0; roles!=null && i<roles.size(); i++) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("role", roles.get(i).getRole());
			paramMap.put("type", ReportRoleViewTypes.IS_ORGANIZATION);
			List<BbReportRoleView> views = this.reportRoleViewService.findListByParams(paramMap);
			for (int j=0; views!=null && j<views.size(); j++) {
				BbOrganization organization = new BbOrganization();
				organization.setOrgId(views.get(j).getIdName());
				organization = this.organizationService.findByEntityUK(organization);
				if ( organization == null ) {
					continue;
				}
				if ( dataMap.get(organization.getOid()) != null ) {
					continue;
				}
				dataMap.put(organization.getOid(), organization.getName());				
			}
		}		
		*/
		List<BbOrganization> organizations = this.findForOrganization(accountId);
		for (BbOrganization entity : organizations) {
			if ( dataMap.get(entity.getOid()) != null ) {
				continue;
			}
			dataMap.put(entity.getOid(), entity.getName());				
		}
		return dataMap;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public List<BbOrganization> findForOrganization(String accountId) throws ServiceException, Exception {
		if ( super.isBlank(accountId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		List<BbOrganization> searchList = new ArrayList<BbOrganization>();
		List<TbUserRole> roles = this.getUserRoles(accountId);
		for (int i=0; roles!=null && i<roles.size(); i++) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("role", roles.get(i).getRole());
			paramMap.put("type", ReportRoleViewTypes.IS_ORGANIZATION);
			List<BbReportRoleView> views = this.reportRoleViewService.findListByParams(paramMap);
			for (int j=0; views!=null && j<views.size(); j++) {
				BbOrganization organization = new BbOrganization();
				organization.setOrgId(views.get(j).getIdName());
				organization = this.getOrganizationService().findByEntityUK(organization);
				if ( organization == null ) {
					continue;
				}
				boolean isFound = false;
				for (BbOrganization entity : searchList) {
					if ( entity.getOid().equals(organization.getOid()) ) {
						isFound = true;
					}
				}
				if (!isFound) {
					searchList.add( organization );
				}
			}
		}		
		return searchList;
	}

}
