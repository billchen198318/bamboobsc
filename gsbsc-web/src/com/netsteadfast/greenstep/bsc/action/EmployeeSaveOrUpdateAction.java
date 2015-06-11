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
package com.netsteadfast.greenstep.bsc.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IEmployeeLogicService;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.EmployeeSaveOrUpdateAction")
@Scope
public class EmployeeSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6392032279242407667L;
	protected Logger logger=Logger.getLogger(EmployeeSaveOrUpdateAction.class);
	private IEmployeeLogicService employeeLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public EmployeeSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IEmployeeLogicService getEmployeeLogicService() {
		return employeeLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.EmployeeLogicService")	
	public void setEmployeeLogicService(IEmployeeLogicService employeeLogicService) {
		this.employeeLogicService = employeeLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"account",
							"empId",
							"password1",
							"password2",
							"fullName"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG001D0001A_account") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001A_empId") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001A_password1") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001A_password2") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001A_fullName") + "<BR/>"
					}, 
					new Class[]{
							IdFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							IdFieldCheckUtils.class,
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
		if ( !this.getFields().get("password1").equals( this.getFields().get("password2") ) ) {
			this.getFieldsId().add("password1");
			this.getFieldsId().add("password2");
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG001D0001A_password12_msg1") + "<BR/>");
		}
		if ( this.getFields().get("password1").length() < 4 || this.getFields().get("password1").length() > 14 ) {
			this.getFieldsId().add("password1");
			this.getFieldsId().add("password2");			
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG001D0001A_password12_msg2") + "<BR/>");
		}
		this.getFields().put("password", this.getFields().get("password1"));
	}	
	
	@SuppressWarnings("unchecked")
	private void checkFieldsForUpdate() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"fullName"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG001D0001A_fullName") + "<BR/>"
					}, 
					new Class[]{
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
	
	@SuppressWarnings("unchecked")
	private void checkFieldsForUpdatePassword() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"password1",
							"password2",
							"password3"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG001D0001E_S00_password1") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001E_S00_password2") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0001E_S00_password3") + "<BR/>",
					}, 
					new Class[]{
							IdFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							IdFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}	
		if ( !this.getFields().get("password2").equals( this.getFields().get("password3") ) ) {
			this.getFieldsId().add("password2");
			this.getFieldsId().add("password3");
			throw new ControllerException(this.getText("MESSAGE.BSC_PROG001D0001E_S00_password23_msg1") + "<BR/>");
		}
		if ( this.getFields().get("password2").length() < 4 || this.getFields().get("password2").length() > 14 ) {
			this.getFieldsId().add("password2");
			this.getFieldsId().add("password3");			
			throw new ControllerException(this.getText("MESSAGE.BSC_PROG001D0001E_S00_password23_msg2") + "<BR/>");
		}
		this.getFields().put("password", this.getFields().get("password1"));		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		EmployeeVO employee = new EmployeeVO();
		this.transformFields2ValueObject(employee, new String[]{"account", "password", "empId", "fullName", "jobTitle"});
		DefaultResult<EmployeeVO> result = this.employeeLogicService.create(
				employee, this.transformAppendIds2List(this.getFields().get("appendId")) );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForUpdate();
		EmployeeVO employee = new EmployeeVO();
		this.transformFields2ValueObject(employee, new String[]{"oid", "fullName", "jobTitle"});
		DefaultResult<EmployeeVO> result = this.employeeLogicService.update(
				employee, this.transformAppendIds2List(this.getFields().get("appendId")) );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}	
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		EmployeeVO employee = new EmployeeVO();
		this.transformFields2ValueObject(employee, new String[]{"oid"});
		DefaultResult<Boolean> result = this.employeeLogicService.delete(employee);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void updatePassword() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForUpdatePassword();
		EmployeeVO employee = new EmployeeVO();
		this.transformFields2ValueObject(employee, new String[]{"oid", "password"});
		DefaultResult<AccountVO> result = this.employeeLogicService.updatePassword(
				employee, this.getFields().get("password2"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * bsc.employeeSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001A")
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
	 * bsc.employeeUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001E")
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
	 * bsc.employeeDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001Q")
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
	
	/**
	 * bsc.employeeUpdatePasswordAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001E_S00")
	public String doUpdatePassword() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.updatePassword();
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
