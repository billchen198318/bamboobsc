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

import com.netsteadfast.greenstep.action.utils.NormalFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.service.logic.IApplicationSystemLogicService;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.ApplicationSystemManagementSaveOrUpdateAction")
@Scope
public class ApplicationSystemManagementSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -3768902191174494661L;
	protected Logger logger=Logger.getLogger(ApplicationSystemManagementSaveOrUpdateAction.class);
	private IApplicationSystemLogicService applicationSystemLogicService; 
	private String message = "";
	private String success = IS_NO;
	
	public ApplicationSystemManagementSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IApplicationSystemLogicService getApplicationSystemLogicService() {
		return applicationSystemLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.ApplicationSystemLogicService")
	public void setApplicationSystemLogicService(
			IApplicationSystemLogicService applicationSystemLogicService) {
		this.applicationSystemLogicService = applicationSystemLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"sysId",
							"name",
							"host",
							"contextPath"
					}, 
					new String[]{
							this.getText("MESSAGE.CORE_PROG001D0001A_sysId") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0001A_name") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0001A_host") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0001A_contextPath")
					}, 
					new Class[]{
							NormalFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
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
		if (Constants.HTML_SELECT_NO_SELECT_ID.equals(this.getFields().get("sysId")) ) { // id cann't euqals all
			this.getFieldsId().add("sysId");
			throw new ControllerException("ID is incorrect, please change another!");	
		}		
	}
	
	/**
	 * 產生 TB_SYS
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysVO sys = new SysVO();
		this.transformFields2ValueObject(sys, new String[]{"sysId", "name", "host", "contextPath"});
		if ("true".equals(super.getFields().get("isLocal"))) {
			sys.setIsLocal(YesNo.YES);
		} else {
			sys.setIsLocal(YesNo.NO);
		}
		DefaultResult<SysVO> result = this.applicationSystemLogicService.create(sys, super.getFields().get("icon"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * 更新 TB_SYS
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysVO sys = new SysVO();
		sys.setOid( this.getFields().get("oid") );
		this.transformFields2ValueObject(sys, new String[]{"sysId", "name", "host", "contextPath"});
		if ("true".equals(super.getFields().get("isLocal"))) {
			sys.setIsLocal(YesNo.YES);
		} else {
			sys.setIsLocal(YesNo.NO);
		}
		DefaultResult<SysVO> result = this.applicationSystemLogicService.update(sys, super.getFields().get("icon"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;		
	}
	
	/**
	 * 刪除 TB_SYS
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysVO sys = new SysVO();
		DefaultResult<Boolean> result = this.applicationSystemLogicService.delete(
				this.transformFields2ValueObject(sys, new String[]{"oid"}));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null || !result.getValue()) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * core.applicationSystemSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001A")
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
	 * core.applicationSystemDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001A")
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
	 * core.applicationSystemUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001E")
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
