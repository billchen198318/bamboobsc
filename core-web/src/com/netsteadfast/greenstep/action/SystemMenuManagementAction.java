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
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMenuManagementAction")
@Scope
public class SystemMenuManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {	
	private static final long serialVersionUID = -3237099870115918800L;
	protected Logger logger=Logger.getLogger(SystemMenuManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;	 
	private Map<String, String> sysMap = new LinkedHashMap<String, String>();
	private Map<String, String> sysProgMap = super.providedSelectZeroDataMap(true);
	
	public SystemMenuManagementAction() {
		super();
	}
	
	private void initData() throws ServiceException, Exception {
		this.sysMap = this.sysService.findSysMap(super.getBasePath(), true);
	}

	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required		
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
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
	
	/**
	 * core.systemMenuManagementAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0003Q")
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

	public Map<String, String> getSysMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysMap);
		return sysMap;
	}

	public void setSysMap(Map<String, String> sysMap) {
		this.sysMap = sysMap;
	}

	public Map<String, String> getSysProgMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysProgMap);
		return sysProgMap;
	}

	public void setSysProgMap(Map<String, String> sysProgMap) {
		this.sysProgMap = sysProgMap;
	}
	
}
