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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.service.logic.ISystemTemplateLogicService;
import com.netsteadfast.greenstep.vo.SysTemplateParamVO;
import com.netsteadfast.greenstep.vo.SysTemplateVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemTemplateSaveOrUpdateAction")
@Scope
public class SystemTemplateSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -2657684976305614732L;
	protected Logger logger=Logger.getLogger(SystemTemplateSaveOrUpdateAction.class);
	private ISystemTemplateLogicService systemTemplateLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemTemplateSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public ISystemTemplateLogicService getSystemTemplateLogicService() {
		return systemTemplateLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemTemplateLogicService")			
	public void setSystemTemplateLogicService(
			ISystemTemplateLogicService systemTemplateLogicService) {
		this.systemTemplateLogicService = systemTemplateLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("templateId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0007A_templateId") )
		.add("title", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0007A_title") )
		.add("message", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0007A_message") )
		.process().throwMessage();
	}
	
	private void checkFieldsForParamEdit() throws ControllerException {
		this.getCheckFieldHandler()
		.add("templateVar", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0007E_S00_templateVar") )
		.add("objectVar", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0007E_S00_objectVar") )
		.process().throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysTemplateVO template = new SysTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"templateId", "title", "message", "description"});
		DefaultResult<SysTemplateVO> result = this.systemTemplateLogicService.create(template);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysTemplateVO template = new SysTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid", "title", "message", "description"});
		DefaultResult<SysTemplateVO> result = this.systemTemplateLogicService.update(template);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysTemplateVO template = new SysTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid"});		
		DefaultResult<Boolean> result = this.systemTemplateLogicService.delete(template);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void paramSave() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForParamEdit();
		SysTemplateParamVO param = new SysTemplateParamVO();
		this.transformFields2ValueObject(param, new String[]{"templateVar", "objectVar"} );
		param.setIsTitle(YesNo.NO);
		if ("true".equals(this.getFields().get("isTitle"))) {
			param.setIsTitle(YesNo.YES);
		}
		DefaultResult<SysTemplateParamVO> result = this.systemTemplateLogicService.createParam(
				param, this.getFields().get("templateOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void paramDelete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysTemplateParamVO param = new SysTemplateParamVO();
		this.transformFields2ValueObject(param, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemTemplateLogicService.deleteParam(param);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}				
	}
	
	/**
	 * core.systemTemplateSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.save();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * core.systemTemplateUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007E")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemTemplateDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007Q")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		

	/**
	 * core.systemTemplateParamSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007E_S00")
	public String doParamSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.paramSave();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * core.systemTemplateParamDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0007E_S00")
	public String doParamDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.paramDelete();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
