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
package com.netsteadfast.greenstep.service.logic.impl;

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
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbRolePermission;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.po.hbm.TbSysMenuRole;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.IRolePermissionService;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.service.ISysMenuRoleService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.IUserRoleService;
import com.netsteadfast.greenstep.service.logic.IRoleLogicService;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.RolePermissionVO;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysCodeVO;
import com.netsteadfast.greenstep.vo.SysMenuRoleVO;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.UserRoleVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.RoleLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class RoleLogicServiceImpl extends BaseLogicService implements IRoleLogicService {
	protected Logger logger=Logger.getLogger(RoleLogicServiceImpl.class);
	private final static String DEFAULT_ROLE_CODE = "BSC_CONF001"; // 預設要套用role的 TB_SYS_CODE.CODE = 'BSC_CONF001' and TYPE='BSC'
	private final static String DEFAULT_ROLE_CODE_TYPE = "BSC"; // 預設要套用role的 TB_SYS_CODE.CODE = 'BSC_CONF001' and TYPE='BSC'	
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IRolePermissionService<RolePermissionVO, TbRolePermission, String> rolePermissionService;
	private IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService;
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private ISysMenuRoleService<SysMenuRoleVO, TbSysMenuRole, String> sysMenuRoleService; 
	
	public RoleLogicServiceImpl() {
		super();
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

	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}

	@Autowired
	@Resource(name="core.service.RoleService")
	@Required		
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}

	public IRolePermissionService<RolePermissionVO, TbRolePermission, String> getRolePermissionService() {
		return rolePermissionService;
	}

	@Autowired
	@Resource(name="core.service.RolePermissionService")
	@Required			
	public void setRolePermissionService(
			IRolePermissionService<RolePermissionVO, TbRolePermission, String> rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
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

	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required		
	public void setSysProgService(
			ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}

	public ISysMenuRoleService<SysMenuRoleVO, TbSysMenuRole, String> getSysMenuRoleService() {
		return sysMenuRoleService;
	}

	@Autowired
	@Resource(name="core.service.SysMenuRoleService")
	@Required		
	public void setSysMenuRoleService(
			ISysMenuRoleService<SysMenuRoleVO, TbSysMenuRole, String> sysMenuRoleService) {
		this.sysMenuRoleService = sysMenuRoleService;
	}

	/**
	 * 建立 TB_ROLE 資料
	 * 
	 * @param role
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<RoleVO> create(RoleVO role) throws ServiceException, Exception {
		
		if (role==null || super.isBlank(role.getRole())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		role.setDescription( super.defaultString(role.getDescription()) );
		if (role.getDescription().length()>MAX_DESCRIPTION_LENGTH) {
			role.setDescription(role.getDescription().substring(0, MAX_DESCRIPTION_LENGTH));
		}
		return this.roleService.saveObject(role);
	}

	/**
	 * 更新 TB_ROLE 資料
	 * 
	 * @param role
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<RoleVO> update(RoleVO role) throws ServiceException, Exception {
		
		if (role==null || super.isBlank(role.getRole())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		role.setDescription( super.defaultString(role.getDescription()) );
		if (role.getDescription().length()>MAX_DESCRIPTION_LENGTH) {
			role.setDescription(role.getDescription().substring(0, MAX_DESCRIPTION_LENGTH));
		}
		return this.roleService.updateObject(role);
	}

	/**
	 * 刪除 TB_ROLE, TB_ROLE_PERMISSION, TB_USER_ROLE 資料
	 * 
	 * @param role
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(RoleVO role) throws ServiceException, Exception {
		if (role==null || super.isBlank(role.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<RoleVO> rResult = this.roleService.findObjectByOid(role);
		if (rResult.getValue()==null) {
			throw new ServiceException(rResult.getSystemMessage().getValue());
		}
		role = rResult.getValue();
		if (Constants.SUPER_ROLE_ADMIN.equals(role.getRole()) || Constants.SUPER_ROLE_ALL.equals(role.getRole())) {			
			throw new ServiceException("Administrator or super role cannot delete!");
		}		
		String defaultUserRole = this.getDefaultUserRole();
		if (role.getRole().equals(defaultUserRole)) {
			throw new ServiceException("Default user role: " + defaultUserRole + " cannot delete!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role", role.getRole());		
		this.deleteRolePermission(params);
		this.deleteUserRole(params);
		this.deleteSysMenuRole(params);
		return roleService.deleteObject(role);
	}
	
	private void deleteRolePermission(Map<String, Object> params) throws ServiceException, Exception {
		List<TbRolePermission> permList = this.rolePermissionService.findListByParams(params);
		if (null==permList || permList.size()<1) {
			return;
		}
		for (TbRolePermission rolePerm : permList) {
			this.rolePermissionService.delete(rolePerm);
		}
	}
	
	private void deleteUserRole(Map<String, Object> params) throws ServiceException, Exception {
		List<TbUserRole> userRoleList = this.userRoleService.findListByParams(params);
		if (null==userRoleList || userRoleList.size()<1) {
			return;
		}
		for (TbUserRole userRole : userRoleList) {
			this.userRoleService.delete(userRole);
		}
	}
	
	private void deleteSysMenuRole(Map<String, Object> params) throws ServiceException, Exception {
		List<TbSysMenuRole> menuRoleList = this.sysMenuRoleService.findListByParams(params);
		if (menuRoleList==null || menuRoleList.size()<1) {
			return;
		}
		for (TbSysMenuRole sysMenuRole : menuRoleList) {
			this.sysMenuRoleService.delete(sysMenuRole);
		}
	}

	/**
	 * 產生 TB_ROLE_PERMISSION 資料
	 * 
	 * @param permission
	 * @param roleOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<RolePermissionVO> createPermission(RolePermissionVO permission, String roleOid) throws ServiceException, Exception {
		
		if ( super.isBlank(roleOid) || permission==null || super.isBlank(permission.getPermission()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		RoleVO role = new RoleVO();
		role.setOid(roleOid);
		DefaultResult<RoleVO> rResult = this.roleService.findObjectByOid(role);
		if (rResult.getValue()==null) {
			throw new ServiceException(rResult.getSystemMessage().getValue());
		}
		role = rResult.getValue();
		if (Constants.SUPER_ROLE_ADMIN.equals(role.getRole()) || Constants.SUPER_ROLE_ALL.equals(role.getRole())) {			
			throw new ServiceException("Administrator or super role no need to set permission!");
		}
		permission.setRole(role.getRole());
		if ( super.defaultString(permission.getDescription()).length()>MAX_DESCRIPTION_LENGTH ) {
			permission.setDescription( permission.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		return this.rolePermissionService.saveObject(permission);
	}

	/**
	 * 刪除 TB_ROLE_PERMISSION 資料
	 * 
	 * @param permission
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deletePermission(RolePermissionVO permission) throws ServiceException, Exception {
		
		if ( null==permission || super.isBlank(permission.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return rolePermissionService.deleteObject(permission);
	}

	/**
	 * 找出全部的role與某帳戶下的role
	 * 
	 * map 中的  key 
	 * enable	- 帳戶下的role
	 * all	- 所有role
	 * 
	 * @param accountOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public Map<String, List<RoleVO>> findForAccountRoleEnableAndAll(String accountOid) throws ServiceException, Exception {
		if (super.isBlank(accountOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AccountVO account = new AccountVO();
		account.setOid(accountOid);
		DefaultResult<AccountVO> aResult = this.accountService.findObjectByOid(account);
		if (aResult.getValue()==null) {
			throw new ServiceException(aResult.getSystemMessage().getValue());
		}
		account = aResult.getValue();
		Map<String, List<RoleVO>> roleMap = new HashMap<String, List<RoleVO>>();
		List<RoleVO> enableRole = this.roleService.findForAccount(account.getAccount());
		List<RoleVO> allRole = this.roleService.findForAll();
		roleMap.put("enable", enableRole);
		roleMap.put("all", allRole);
		return roleMap;
	}

	/**
	 * 更新帳戶的role
	 * 
	 * @param accountOid
	 * @param roles
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> updateUserRole(String accountOid, List<String> roles) throws ServiceException, Exception {
		if (super.isBlank(accountOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AccountVO account = new AccountVO();
		account.setOid(accountOid);
		DefaultResult<AccountVO> aResult = this.accountService.findObjectByOid(account);
		if (aResult.getValue()==null) {
			throw new ServiceException(aResult.getSystemMessage().getValue());
		}
		account = aResult.getValue();
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(false);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)));		
		this.deleteUserRoleByAccount(account);
		for (int i=0; roles!=null && i<roles.size(); i++) {
			String roleOid = roles.get(i).trim();
			if (super.isBlank(roleOid)) {
				continue;
			}
			RoleVO role = new RoleVO();
			role.setOid(roleOid);
			DefaultResult<RoleVO> rResult = this.roleService.findObjectByOid(role);
			if (rResult.getValue()==null) {
				throw new ServiceException(rResult.getSystemMessage().getValue());
			}
			role = rResult.getValue();
			UserRoleVO userRole = new UserRoleVO();
			userRole.setAccount(account.getAccount());
			userRole.setRole(role.getRole());
			userRole.setDescription("");
			DefaultResult<UserRoleVO> urResult = this.userRoleService.saveObject(userRole);
			if (urResult.getValue()==null) {
				throw new ServiceException(urResult.getSystemMessage().getValue());
			}
		}
		result.setValue(true);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)));
		return result;
	}
	
	private void deleteUserRoleByAccount(AccountVO account) throws ServiceException, Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account.getAccount());
		List<TbUserRole> userRoleList = this.userRoleService.findListByParams(params);
		if (userRoleList==null || userRoleList.size()<1) {
			return;
		}
		for (TbUserRole userRole : userRoleList) {
			this.userRoleService.delete(userRole);
		}
	}

	/**
	 * 找出全部的role與某程式menu所屬的role
	 * 
	 * map 中的  key 
	 * enable	- 程式menu的role
	 * all	- 所有role
	 * 
	 * @param programOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public Map<String, List<RoleVO>> findForProgramRoleEnableAndAll(String programOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(programOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysProgVO sysProg = new SysProgVO();
		sysProg.setOid(programOid);
		DefaultResult<SysProgVO> spResult = this.sysProgService.findObjectByOid(sysProg);
		if (spResult.getValue()==null) {
			throw new ServiceException(spResult.getSystemMessage().getValue());
		}
		sysProg = spResult.getValue();
		Map<String, List<RoleVO>> roleMap = new HashMap<String, List<RoleVO>>();
		List<RoleVO> enableRole = this.roleService.findForProgram(sysProg.getProgId());
		List<RoleVO> allRole = this.roleService.findForAll();
		roleMap.put("enable", enableRole);
		roleMap.put("all", allRole);				
		return roleMap;
	}

	/**
	 * 更新存在選單中程式的選單所屬 role
	 * 
	 * @param progOid
	 * @param roles
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> updateMenuRole(String progOid, List<String> roles) throws ServiceException, Exception {
		if (super.isBlank(progOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysProgVO sysProg = new SysProgVO();
		sysProg.setOid(progOid);
		DefaultResult<SysProgVO> spResult = this.sysProgService.findObjectByOid(sysProg);
		if (spResult.getValue()==null) {
			throw new ServiceException(spResult.getSystemMessage().getValue());
		}
		sysProg = spResult.getValue();
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(false);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)));			
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("progId", sysProg.getProgId());
		List<TbSysMenuRole> sysMenuRoleList = this.sysMenuRoleService.findListByParams(params);
		for (int i=0; sysMenuRoleList!=null && i<sysMenuRoleList.size(); i++) {
			TbSysMenuRole sysMenuRole = sysMenuRoleList.get(i);
			this.sysMenuRoleService.delete(sysMenuRole);
		}
		for (int i=0; roles!=null && i<roles.size(); i++) {
			String roleOid = roles.get(i).trim();
			if (super.isBlank(roleOid)) {
				continue;
			}
			RoleVO role = new RoleVO();
			role.setOid(roleOid);
			DefaultResult<RoleVO> rResult = this.roleService.findObjectByOid(role);
			if (rResult.getValue()==null) {
				throw new ServiceException(rResult.getSystemMessage().getValue());
			}
			role = rResult.getValue();
			SysMenuRoleVO sysMenuRole = new SysMenuRoleVO();
			sysMenuRole.setProgId(sysProg.getProgId());
			sysMenuRole.setRole(role.getRole());
			DefaultResult<SysMenuRoleVO> smrResult = this.sysMenuRoleService.saveObject(sysMenuRole);
			if (smrResult.getValue()==null) {
				throw new ServiceException(smrResult.getSystemMessage().getValue());
			}			
		}
		result.setValue(true);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)));
		return result;
	}

	/**
	 * 拷備一份role
	 * 
	 * @param fromRoleOid
	 * @param role
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<RoleVO> copyAsNew(String fromRoleOid, RoleVO role) throws ServiceException, Exception {
		if (role==null || super.isBlank(role.getRole()) || super.isBlank(fromRoleOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		super.setStringValueMaxLength(role, "description", MAX_DESCRIPTION_LENGTH);
		DefaultResult<RoleVO> result = this.roleService.saveObject(role);
		RoleVO oldRole = new RoleVO();
		oldRole.setOid(fromRoleOid);
		DefaultResult<RoleVO> fromResult = this.roleService.findObjectByOid(oldRole);
		if ( fromResult.getValue() == null ) {
			throw new ServiceException( fromResult.getSystemMessage().getValue() );
		}
		oldRole = fromResult.getValue();		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("role", oldRole.getRole());
		List<TbRolePermission> permissions = this.rolePermissionService.findListByParams(paramMap);
		for (int i=0; permissions!=null && i<permissions.size(); i++) {
			RolePermissionVO permission = new RolePermissionVO();
			this.rolePermissionService.fillToValueObject(permission, permissions.get(i));
			permission.setOid(null);
			permission.setRole( result.getValue().getRole() );
			this.rolePermissionService.saveObject(permission);
		}
		// 選單menu role 也copy一份
		List<TbSysMenuRole> menuRoles = this.sysMenuRoleService.findListByParams(paramMap);
		for (int i=0; menuRoles!=null && i<menuRoles.size(); i++) {
			SysMenuRoleVO menuRole = new SysMenuRoleVO();
			this.sysMenuRoleService.fillToValueObject(menuRole, menuRoles.get(i) );
			menuRole.setOid(null);
			menuRole.setRole( result.getValue().getRole() );
			this.sysMenuRoleService.saveObject(menuRole);
		}
		return result;
	}
	
	/**
	 * 使用者設的role-id(角色), 它設定在 tb_sys_code中
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	public String getDefaultUserRole() throws ServiceException, Exception {
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

}
