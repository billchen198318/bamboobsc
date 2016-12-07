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

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysTemplate;
import com.netsteadfast.greenstep.service.ISysTemplateService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysTemplateVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemTemplateManagementAction")
@Scope
public class SystemTemplateManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -8194130689909829788L;
	protected Logger logger=Logger.getLogger(SystemTemplateManagementAction.class);
	private ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService;
	private SysTemplateVO sysTemplate = new SysTemplateVO(); // edit 模式用
	
	public SystemTemplateManagementAction() {
		super();
	}
	
	public ISysTemplateService<SysTemplateVO, TbSysTemplate, String> getSysTemplateService() {
		return sysTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysTemplateService")
	@Required		
	public void setSysTemplateService(
			ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService) {
		this.sysTemplateService = sysTemplateService;
	}

	private void loadSysTemplateData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysTemplate, new String[]{"oid"});
		DefaultResult<SysTemplateVO> result = this.sysTemplateService.findObjectByOid(this.sysTemplate);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysTemplate = result.getValue();		
	}
	
	/**
	 * core.systemTemplateManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007Q")
	public String execute() throws Exception {
		try {
			
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
	 * core.systemTemplateCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007A")	
	public String create() throws Exception {
		try {
			
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
	 * core.systemTemplateEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG001D0007E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.loadSysTemplateData();
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
	 * core.systemTemplateParamEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG001D0007E_S00")		
	public String editParam() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.loadSysTemplateData();
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public SysTemplateVO getSysTemplate() {
		return sysTemplate;
	}

}
