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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.AccountVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.UserRoleManagementAction")
@Scope
public class UserRoleManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 6896386073913077451L;
	protected Logger logger=Logger.getLogger(UserRoleManagementAction.class);
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private Map<String, String> accountMap = new LinkedHashMap<String, String>();
	
	public UserRoleManagementAction() {
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
	
	private void initData() throws ServiceException, Exception {
		this.accountMap = this.accountService.findForAllMap(true);
	}
	
	/**
	 * core.userRoleManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0002Q")
	public String execute() throws Exception {
		try {
			this.initData();
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

	public Map<String, String> getAccountMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.accountMap);
		return accountMap;
	}
	
}
