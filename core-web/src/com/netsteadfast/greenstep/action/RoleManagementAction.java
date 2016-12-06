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
package com.netsteadfast.greenstep.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.RoleVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.RoleManagementAction")
@Scope
public class RoleManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -890542499667366811L;
	protected Logger logger=Logger.getLogger(RoleManagementAction.class);
	private IRoleService<RoleVO, TbRole, String> roleService;
	private RoleVO role = new RoleVO();
	
	public RoleManagementAction() {
		super();
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

	private void loadRoleData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.role, new String[]{"oid"});
		DefaultResult<RoleVO> result = this.roleService.findObjectByOid(role);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.role = result.getValue();
	}
	
	/**
	 * core.roleManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001Q")
	public String execute() throws Exception {
		try {
			// nothing
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;
	}	
	
	/**
	 * core.roleCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001A")
	public String create() throws Exception {
		try {
			// nothing
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}
	
	/**
	 * core.roleEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.loadRoleData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;		
	}
	
	/**
	 * core.roleCopyAsNewAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001A_S00")	
	public String copyAsNew() throws Exception {
		String forward = this.edit();
		if ( SUCCESS.equals(forward) ) {
			if ( Constants.SUPER_ROLE_ADMIN.equals(this.role.getRole()) 
					|| Constants.SUPER_ROLE_ALL.equals(this.role.getRole()) ) {
				super.setErrorMessage("Super role or all role can not copy new as!");
				forward = ERROR;
			}
		}
		return forward;
	}
	
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public RoleVO getRole() {
		return role;
	}

	public void setRole(RoleVO role) {
		this.role = role;
	}

}
