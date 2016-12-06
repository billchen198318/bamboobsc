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

import java.util.Map;

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
import com.netsteadfast.greenstep.po.hbm.TbSysForm;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysFormVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormManagementAction")
@Scope
public class SystemFormManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 7271407846688469034L;
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	private ISysFormService<SysFormVO, TbSysForm, String> sysFormService;
	private Map<String, String> templateMap = this.providedSelectZeroDataMap( true );
	private SysFormVO form = new SysFormVO();
	
	public SystemFormManagementAction() {
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

	public ISysFormService<SysFormVO, TbSysForm, String> getSysFormService() {
		return sysFormService;
	}

	@Autowired
	@Resource(name="core.service.SysFormService")
	@Required		
	public void setSysFormService(
			ISysFormService<SysFormVO, TbSysForm, String> sysFormService) {
		this.sysFormService = sysFormService;
	}

	private void initData(String  type) throws ServiceException, Exception {
		if ("create".equals(type) || "edit".equals(type)) {
			this.templateMap = this.sysFormTemplateService.findForAllMap(true);
		}
	}
	
	private void loadFormData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.form, new String[]{"oid"});
		DefaultResult<SysFormVO> result = this.sysFormService.findObjectByOid(form);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.form = result.getValue();
		SysFormTemplateVO template = new SysFormTemplateVO();
		template.setTplId( this.form.getTemplateId() );
		DefaultResult<SysFormTemplateVO> tplResult = this.sysFormTemplateService.findByUK(template);
		if (tplResult.getValue()!=null) {
			this.getFields().put("templateOid", tplResult.getValue().getOid());
		}
	}
	
	/**
	 * core.systemFormManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
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
	 * core.systemFormCreateAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013A")	
	public String create() throws Exception {
		try {
			this.initData("create");
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
	 * core.systemFormEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013E")		
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.loadFormData();
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

	public Map<String, String> getTemplateMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.templateMap);
		return templateMap;
	}

	public SysFormVO getForm() {
		return form;
	}

}
