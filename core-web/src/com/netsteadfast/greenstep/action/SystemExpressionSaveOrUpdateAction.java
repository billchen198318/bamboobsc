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
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.service.logic.ISystemExpressionLogicService;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemExpressionSaveOrUpdateAction")
@Scope
public class SystemExpressionSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 4652687376570734328L;
	protected Logger logger=Logger.getLogger(SystemExpressionSaveOrUpdateAction.class);
	private ISystemExpressionLogicService systemExpressionLogicService; 
	private String message = "";
	private String success = IS_NO;
	
	public SystemExpressionSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public ISystemExpressionLogicService getSystemExpressionLogicService() {
		return systemExpressionLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemExpressionLogicService")			
	public void setSystemExpressionLogicService(
			ISystemExpressionLogicService systemExpressionLogicService) {
		this.systemExpressionLogicService = systemExpressionLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"type",
							"exprId", 
							"name",
							"content"
					},
					new String[]{
							"Please select type!<BR/>",
							"ID is required and only normal characters!<BR/>",
							"Name is required!<BR/>",
							"Content is required!<BR/>"
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
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
		if (Constants.HTML_SELECT_NO_SELECT_ID.equals(this.getFields().get("exprId"))) {
			this.getFieldsId().add("exprId");
			throw new ControllerException("ID is incorrect, please change another!");				
		}
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"exprId", "type", "name", "content", "description"} );
		DefaultResult<SysExpressionVO> result = this.systemExpressionLogicService.create(expression);
		this.message = result.getSystemMessage().getValue(); 
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid", "exprId", "type", "name", "content", "description"} );
		DefaultResult<SysExpressionVO> result = this.systemExpressionLogicService.update(expression);
		this.message = result.getSystemMessage().getValue(); 
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}				
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid"} );
		DefaultResult<Boolean> result = this.systemExpressionLogicService.delete(expression);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * core.systemExpressionSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002A")
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
	 * core.systemExpressionUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002E")
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
	 * core.systemExpressionDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002Q")
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
