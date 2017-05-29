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
import com.netsteadfast.greenstep.service.logic.ISystemMessageNoticeLogicService;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMessageNoticeConfigSaveOrUpdateAction")
@Scope
public class SystemMessageNoticeConfigSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6504437298323566203L;
	protected Logger logger=Logger.getLogger(SystemMessageNoticeConfigSaveOrUpdateAction.class);
	private ISystemMessageNoticeLogicService systemMessageNoticeLogicService;
	private String message = "";
	private String success = IS_NO;	
	
	public SystemMessageNoticeConfigSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemMessageNoticeLogicService getSystemMessageNoticeLogicService() {
		return systemMessageNoticeLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemMessageNoticeLogicService")		
	public void setSystemMessageNoticeLogicService(
			ISystemMessageNoticeLogicService systemMessageNoticeLogicService) {
		this.systemMessageNoticeLogicService = systemMessageNoticeLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("systemOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0005A_system") )
		.add("msgId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0005A_msgId") )
		.add("className", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0005A_className") )
		.process().throwMessage();
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysMsgNoticeConfigVO config = new SysMsgNoticeConfigVO();
		this.transformFields2ValueObject(config, new String[]{"msgId", "className"} );
		DefaultResult<SysMsgNoticeConfigVO> result = this.systemMessageNoticeLogicService.createConfig(
				config, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysMsgNoticeConfigVO config = new SysMsgNoticeConfigVO();
		this.transformFields2ValueObject(config, new String[]{"oid", "className"} );
		DefaultResult<SysMsgNoticeConfigVO> result = this.systemMessageNoticeLogicService.updateConfig(
				config, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}	
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysMsgNoticeConfigVO config = new SysMsgNoticeConfigVO();
		this.transformFields2ValueObject(config, new String[]{"oid"} );
		DefaultResult<Boolean> result = this.systemMessageNoticeLogicService.deleteConfig(config);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null || !result.getValue()) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * core.systemMessageNoticeConfigSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005A")
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
	 * core.systemMessageNoticeConfigUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005E")
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
	 * core.systemMessageNoticeConfigDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005Q")
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
