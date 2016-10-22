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
package com.netsteadfast.greenstep.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbRolePermission;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.IRolePermissionService;
import com.netsteadfast.greenstep.service.IUserRoleService;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.RolePermissionVO;
import com.netsteadfast.greenstep.vo.UserRoleVO;

public class GreenStepBaseAuthorizingRealm extends AuthorizingRealm {
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService;
	private IRolePermissionService<RolePermissionVO, TbRolePermission, String> rolePermissionService;
	
	public GreenStepBaseAuthorizingRealm() {
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

	private SimpleAuthorizationInfo getSimpleAuthorizationInfo(String username) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", username);
		List<TbUserRole> roleList = userRoleService.findListByParams(params);
		if (roleList==null) {
			return null;
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (TbUserRole userRole : roleList) {
			info.addRole(userRole.getRole());
			params.clear();
			params.put("role", userRole.getRole());
			List<TbRolePermission> rolePermissionList = rolePermissionService.findListByParams(params);
			if (rolePermissionList==null) {
				continue;
			}
			for (TbRolePermission rolePermission : rolePermissionList) {
				info.addStringPermission(rolePermission.getPermission());
			}
		}
		return info;		
	}

	/**
	 * 授權
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.fromRealm(this.getName()).iterator().next();
		if (username==null || StringUtils.isBlank(username)) {
			return null;
		}
		try {
			return this.getSimpleAuthorizationInfo(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 認證
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		GreenStepBaseUsernamePasswordToken token = (GreenStepBaseUsernamePasswordToken)authenticationToken;
		String account = token.getUsername();
		AccountVO accountObj = new AccountVO();
		accountObj.setAccount(account);
		try {
			DefaultResult<AccountVO> result = accountService.findByUK(accountObj);
			if (result.getValue()==null) {
				return null;
			}
			accountObj = result.getValue();			
			return new SimpleAuthenticationInfo(
					accountObj.getAccount(), accountObj.getPassword(), this.getName());			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
