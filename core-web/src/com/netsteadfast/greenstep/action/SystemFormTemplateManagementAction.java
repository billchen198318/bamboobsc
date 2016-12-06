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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormTemplateManagementAction")
@Scope
public class SystemFormTemplateManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 835933478741518224L;
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	private SysFormTemplateVO sysFormTemplate = new SysFormTemplateVO();
	
	public SystemFormTemplateManagementAction() {
		super();
	}
	
	public ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> getSysFormTemplateService() {
		return sysFormTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysFormTemplateService")	
	@Required
	public void setSysFormTemplateService(
			ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService) {
		this.sysFormTemplateService = sysFormTemplateService;
	}	
	
	private void initData() throws ServiceException, Exception {
		
	}
	
	private void loadFormTemplateData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysFormTemplate, new String[]{"oid"});
		DefaultResult<SysFormTemplateVO> result = 
				this.sysFormTemplateService.findObjectByOid( this.sysFormTemplate );
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.sysFormTemplate = result.getValue();
	}
	
	/**
	 * core.systemFormTemplateManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012Q")
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
	
	/**
	 * core.systemFormTemplateCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012A")	
	public String create() throws Exception {
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
	
	/**
	 * core.systemFormTemplateEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadFormTemplateData();
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

	public SysFormTemplateVO getSysFormTemplate() {
		return sysFormTemplate;
	}

}
