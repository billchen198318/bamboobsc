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
import com.netsteadfast.greenstep.vo.SysFormMethodVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormMethodSaveOrUpdateAction")
@Scope
public class SystemFormMethodSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 3750175370656378004L;
	protected Logger logger=Logger.getLogger(SystemFormMethodSaveOrUpdateAction.class);
	private ISystemFormLogicService systemFormLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemFormMethodSaveOrUpdateAction() {
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
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"formOid",
							"name",
							"resultType",
							"type",
							"expression"
					}, 
					new String[]{
							"Data error!<BR/>",
							"Name is required and only normal characters!<BR/>",
							"Please select result type!<BR/>",
							"Please select type!<BR/>",
							"Expression is required!<BR/>"
					}, 
					new Class[]{
							NotBlankFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysFormMethodVO method = new SysFormMethodVO();
		method.setExpression( this.getFields().get("expression").getBytes() );
		this.transformFields2ValueObject(method, new String[]{ "name", "resultType", "type", "description" });
		DefaultResult<SysFormMethodVO> result = this.systemFormLogicService.createMethod(
				method, this.getFields().get("formOid") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}			
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysFormMethodVO method = new SysFormMethodVO();
		method.setExpression( this.getFields().get("expression").getBytes() );
		this.transformFields2ValueObject(method, new String[]{ "oid", "name", "resultType", "type", "description" });
		DefaultResult<SysFormMethodVO> result = this.systemFormLogicService.updateMethod(
				method, this.getFields().get("formOid") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysFormMethodVO method = new SysFormMethodVO();
		this.transformFields2ValueObject(method, new String[]{"oid"});	
		DefaultResult<Boolean> result = this.systemFormLogicService.deleteMethod(method);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null && result.getValue() ) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * core.systemFormMethodSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.save();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemFormMethodUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014E")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemFormMethodDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014Q")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
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

}
