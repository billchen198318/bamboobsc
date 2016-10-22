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

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.service.IUserRoleService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;
import com.netsteadfast.greenstep.vo.UserRoleVO;

public abstract class CoreBaseLogicService extends BaseLogicService {
	
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	
	public CoreBaseLogicService() {
		super();
	}
	
	public IAccountService<AccountVO, TbAccount, String> getAccountService() {
		return accountService;
	}

	@Autowired
	@Resource(name="core.service.AccountService")
	@Required		
	public void setAccountService(IAccountService<AccountVO, TbAccount, String> accountService) {
		this.accountService = accountService;
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
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")
	@Required					
	public void setSysUploadService(ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}		
	
	public AccountVO findAccountData() throws ServiceException, Exception {
		return this.findAccountData( super.getAccountId() );
	}
	
	public AccountVO findAccountData(String accountId) throws ServiceException, Exception {
		if (super.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		AccountVO accountObj = new AccountVO();
		accountObj.setAccount(accountId);
		DefaultResult<AccountVO> result = this.accountService.findByUK(accountObj);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().toString() );
		}
		accountObj = result.getValue();
		return accountObj;
	}
	
	public List<TbUserRole> findUserRoles() throws ServiceException, Exception {
		return this.findUserRoles( super.getAccountId() );
	}
	
	public List<TbUserRole> findUserRoles(String accountId) throws ServiceException, Exception {
		if (super.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("account", super.getAccountId());
		return this.userRoleService.findListByParams(paramMap);
	}
	
	public SysUploadVO findUploadData(String oid) throws ServiceException, Exception {
		if (super.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return UploadSupportUtils.findUpload(oid);	
	}
	
	public SysUploadVO findUploadDataForNoByteContent(String oid) throws ServiceException, Exception {
		if (super.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return UploadSupportUtils.findUploadNoByteContent(oid);
	}
	
}
