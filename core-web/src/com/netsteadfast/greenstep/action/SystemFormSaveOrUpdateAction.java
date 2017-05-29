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
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.service.logic.ISystemFormLogicService;
import com.netsteadfast.greenstep.vo.SysFormVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormSaveOrUpdateAction")
@Scope
public class SystemFormSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -218472664301005887L;
	protected Logger logger=Logger.getLogger(SystemFormSaveOrUpdateAction.class);
	private ISystemFormLogicService systemFormLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemFormSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemFormLogicService getSystemFormLogicService() {
		return systemFormLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemFormLogicService")		
	public void setSystemFormLogicService(
			ISystemFormLogicService systemFormLogicService) {
		this.systemFormLogicService = systemFormLogicService;
	}	
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("formId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0013A_formId") )
		.add("templateOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0013A_templateOid") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0013A_name") )
		.process().throwMessage();
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysFormVO form = new SysFormVO();
		this.transformFields2ValueObject(form, new String[]{"formId", "name", "description"});
		DefaultResult<SysFormVO> result = this.systemFormLogicService.create(
				form, this.getFields().get("templateOid") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}			
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysFormVO form = new SysFormVO();
		this.transformFields2ValueObject(form, new String[]{"oid", "formId", "name", "description"});
		DefaultResult<SysFormVO> result = this.systemFormLogicService.update(
				form, this.getFields().get("templateOid") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysFormVO form = new SysFormVO();
		this.transformFields2ValueObject(form, new String[]{"oid"});	
		DefaultResult<Boolean> result = this.systemFormLogicService.delete(form);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null && result.getValue() ) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * core.systemFormSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013A")
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
	 * core.systemFormUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013E")
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
	 * core.systemFormDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0013Q")
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
