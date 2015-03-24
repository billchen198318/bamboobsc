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

import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeOrgaService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IReportRoleViewService;
import com.netsteadfast.greenstep.bsc.service.logic.IEmployeeLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbEmployeeOrga;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbReportRoleView;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.service.IUserRoleService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.EmployeeOrgaVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.ReportRoleViewVO;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;
import com.netsteadfast.greenstep.vo.SysCodeVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;
import com.netsteadfast.greenstep.vo.UserRoleVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.EmployeeLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class EmployeeLogicServiceImpl extends BaseLogicService implements IEmployeeLogicService {
	protected Logger logger=Logger.getLogger(EmployeeLogicServiceImpl.class);
	private final static String DEFAULT_ROLE_CODE = "BSC_CONF001"; // 預設要套用role的 TB_SYS_CODE.CODE = 'BSC_CONF001' and TYPE='BSC'
	private final static String DEFAULT_ROLE_CODE_TYPE = "BSC"; // 預設要套用role的 TB_SYS_CODE.CODE = 'BSC_CONF001' and TYPE='BSC'
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService;
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService;
	private IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService;
	
	public EmployeeLogicServiceImpl() {
		super();
	}

	public IAccountService<AccountVO, TbAccount, String> getAccountService() {
		return accountService;
	}

	@Autowired
	@Resource(name="core.service.AccountService")
	@Required			
	public void setAccountService(
			IAccountService<AccountVO, TbAccount, String> accountService) {
		this.accountService = accountService;
	}	
	
	public IUserRoleService<UserRoleVO, TbUserRole, String> getUserRoleService() {
		return userRoleService;
	}

	@Autowired
	@Resource(name="core.service.UserRoleService")
	@Required		
	public void setUserRoleService(
			IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService) {
		this.userRoleService = userRoleService;
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

	public ISysCodeService<SysCodeVO, TbSysCode, String> getSysCodeService() {
		return sysCodeService;
	}

	@Autowired
	@Resource(name="core.service.SysCodeService")
	@Required		
	public void setSysCodeService(
			ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService) {
		this.sysCodeService = sysCodeService;
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

	public IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> getEmployeeOrgaService() {
		return employeeOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeOrgaService")
	@Required		
	public void setEmployeeOrgaService(
			IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService) {
		this.employeeOrgaService = employeeOrgaService;
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
	
	public ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> getSysMsgNoticeService() {
		return sysMsgNoticeService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeService")
	@Required			
	public void setSysMsgNoticeService(
			ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService) {
		this.sysMsgNoticeService = sysMsgNoticeService;
	}

	public ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> getSysCalendarNoteService() {
		return sysCalendarNoteService;
	}

	@Autowired
	@Resource(name="core.service.SysCalendarNoteService")
	@Required	
	public void setSysCalendarNoteService(
			ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService) {
		this.sysCalendarNoteService = sysCalendarNoteService;
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

	private boolean isAdministrator(String account) {
		if (account.equals("admin") || account.equals(Constants.SYSTEM_BACKGROUND_USER)) {
			return true;
		}
		return false;
	}
	
	private String getDefaultRole() throws ServiceException, Exception {
		String role = "";
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType(DEFAULT_ROLE_CODE_TYPE);
		sysCode.setCode(DEFAULT_ROLE_CODE);
		DefaultResult<SysCodeVO> result = this.sysCodeService.findByUK(sysCode);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysCode = result.getValue();
		role = sysCode.getParam1();
		if (super.isBlank(role)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role", role);
		if ( this.roleService.countByParams(params) != 1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		return role;
	}

	private String tranPassword(String password) {
		return SimpleUtils.toMD5Hex( SimpleUtils.toB64(password) );
	}
	
	private AccountVO tranAccount(EmployeeVO employee) {
		AccountVO account = new AccountVO();
		account.setAccount(employee.getAccount());
		account.setOnJob(YesNo.YES);
		account.setPassword( this.tranPassword(employee.getPassword()) );		
		return account;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<EmployeeVO> create(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getEmpId())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", employee.getEmpId());
		if (this.employeeService.countByParams(params) > 0) {
			throw new ServiceException("Please change another Id!");
		}
		AccountVO account = this.tranAccount(employee);
		if (this.isAdministrator(account.getAccount())) {
			throw new ServiceException("Please change another Account!");
		}		
		
		DefaultResult<AccountVO> mResult = this.accountService.saveObject(account);
		if (mResult.getValue()==null) {
			throw new ServiceException( mResult.getSystemMessage().getValue() );
		}		
		DefaultResult<EmployeeVO> result = this.employeeService.saveObject(employee);
		this.createEmployeeOrganization(result.getValue(), organizationOid);
		
		// create default role
		UserRoleVO userRole = new UserRoleVO();
		userRole.setAccount(result.getValue().getAccount());
		userRole.setRole(this.getDefaultRole());
		userRole.setDescription(result.getValue().getAccount() + " `s role!");
		this.userRoleService.saveObject(userRole);
		
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<EmployeeVO> update(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<EmployeeVO> oldResult = this.employeeService.findObjectByOid(employee);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		this.deleteEmployeeOrganization( oldResult.getValue() );
		employee.setAccount( oldResult.getValue().getAccount() );
		employee.setEmpId( oldResult.getValue().getEmpId() );	
		DefaultResult<EmployeeVO> result = this.employeeService.updateObject(employee);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.createEmployeeOrganization(result.getValue(), organizationOid);		
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<AccountVO> updatePassword(EmployeeVO employee, String newPassword) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) 
				|| super.isBlank(employee.getPassword()) || super.isBlank(newPassword) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<EmployeeVO> oldResult = this.employeeService.findObjectByOid(employee);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		AccountVO account = new AccountVO();
		account.setAccount( oldResult.getValue().getAccount() );
		DefaultResult<AccountVO> mResult = this.accountService.findByUK(account);
		if (mResult.getValue()==null) {
			throw new ServiceException( mResult.getSystemMessage().getValue() );
		}
		account = mResult.getValue();
		if (!account.getPassword().equals( this.tranPassword(employee.getPassword()) ) ) {
			throw new ServiceException("The current password(old password) is incorrect!");
		}		
		account.setPassword( this.tranPassword(newPassword) );		
		return accountService.updateObject(account);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(EmployeeVO employee) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<EmployeeVO> oldResult = this.employeeService.findObjectByOid(employee);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		employee = oldResult.getValue();
		AccountVO account = new AccountVO();
		account.setAccount( oldResult.getValue().getAccount() );
		DefaultResult<AccountVO> mResult = this.accountService.findByUK(account);
		if (mResult.getValue()==null) {
			throw new ServiceException( mResult.getSystemMessage().getValue() );
		}
		account = mResult.getValue();
		if (this.isAdministrator(account.getAccount())) {
			throw new ServiceException("Administrator cannot delete!");
		}
		
		
		// check account data for other table use.
		this.checkInformationRelated(account, employee);
		
		this.deleteEmployeeOrganization(employee);
		
		// delete user role
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account.getAccount());
		List<TbUserRole> userRoles = this.userRoleService.findListByParams(params);
		for (int i=0; userRoles!=null && i<userRoles.size(); i++) {
			TbUserRole uRole = userRoles.get(i);
			this.userRoleService.delete(uRole);
		}
		
		// delete BB_REPORT_ROLE_VIEW
		params.clear();
		params.put("idName", account.getAccount());
		List<BbReportRoleView> reportRoleViews = this.reportRoleViewService.findListByParams(params);
		for (int i=0; reportRoleViews!=null && i<reportRoleViews.size(); i++) {
			BbReportRoleView reportRoleView = reportRoleViews.get( i );
			this.reportRoleViewService.delete(reportRoleView);
		}
		
		this.accountService.deleteByPKng(account.getOid());
		return employeeService.deleteObject(employee);
	}
	
	private void checkInformationRelated(AccountVO account, EmployeeVO employee) throws ServiceException, Exception {
		if (account==null || super.isBlank(account.getAccount()) 
				|| employee==null || super.isBlank(employee.getEmpId()) ) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		
		// tb_sys_msg_notice
		params.put("toAccount", account.getAccount());
		if (this.sysMsgNoticeService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// tb_sys_calendar_note
		params.clear();
		params.put("account", account.getAccount());
		if (this.sysCalendarNoteService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
				
	}
	
	private void createEmployeeOrganization(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || StringUtils.isBlank(employee.getEmpId()) 
				|| organizationOid==null || organizationOid.size() < 1 ) {
			return;
		}
		for (String oid : organizationOid) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid(oid);
			DefaultResult<OrganizationVO> oResult = this.organizationService.findObjectByOid(organization);
			if (oResult.getValue()==null) {
				throw new ServiceException( oResult.getSystemMessage().getValue() );
			}
			organization = oResult.getValue();
			EmployeeOrgaVO empOrg = new EmployeeOrgaVO();
			empOrg.setEmpId(employee.getEmpId());
			empOrg.setOrgId(organization.getOrgId());
			this.employeeOrgaService.saveObject(empOrg);
		}		
	}
	
	private void deleteEmployeeOrganization(EmployeeVO employee) throws ServiceException, Exception {
		if (employee==null || StringUtils.isBlank(employee.getEmpId()) ) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", employee.getEmpId());
		List<BbEmployeeOrga> searchList = this.employeeOrgaService.findListByParams(params);
		if (searchList==null || searchList.size()<1 ) {
			return;
		}
		for (BbEmployeeOrga empOrg : searchList) {
			this.employeeOrgaService.delete(empOrg);
		}		
	}

}
