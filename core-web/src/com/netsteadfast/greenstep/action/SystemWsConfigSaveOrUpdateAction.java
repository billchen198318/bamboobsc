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

import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.WSConfig;
import com.netsteadfast.greenstep.service.logic.ISystemWebServiceConfigLogicService;
import com.netsteadfast.greenstep.sys.CxfServerBean;
import com.netsteadfast.greenstep.vo.SysWsConfigVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemWsConfigSaveOrUpdateAction")
@Scope
public class SystemWsConfigSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -8273978745934033423L;
	protected Logger logger=Logger.getLogger(SystemWsConfigSaveOrUpdateAction.class);
	private ISystemWebServiceConfigLogicService systemWebServiceConfigLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemWsConfigSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemWebServiceConfigLogicService getSystemWebServiceConfigLogicService() {
		return systemWebServiceConfigLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemWebServiceConfigLogicService")			
	public void setSystemWebServiceConfigLogicService(
			ISystemWebServiceConfigLogicService systemWebServiceConfigLogicService) {
		this.systemWebServiceConfigLogicService = systemWebServiceConfigLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("systemOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0001A_systemOid") )
		.add("wsId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0001A_wsId") )
		.add("beanId", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0001A_beanId") )
		.process().throwMessage();
		
		this.getCheckFieldHandler().single(
				"publishAddress", 
				( WSConfig.TYPE_SOAP.equals(this.getFields().get("type")) && StringUtils.isBlank(this.getFields().get("publishAddress")) ), 
				this.getText("MESSAGE.CORE_PROG003D0001A_publishAddress") )
		.throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysWsConfigVO config = new SysWsConfigVO();
		config.setPublishAddress(" ");		
		this.transformFields2ValueObject(config, new String[]{"wsId", "beanId", "type", "publishAddress", "description"});
		DefaultResult<SysWsConfigVO> result = this.systemWebServiceConfigLogicService.create(
				config, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysWsConfigVO config = new SysWsConfigVO();
		config.setPublishAddress(" ");		
		this.transformFields2ValueObject(config, new String[]{"oid", "wsId", "beanId", "type", "publishAddress", "description"});
		DefaultResult<SysWsConfigVO> result = this.systemWebServiceConfigLogicService.update(
				config, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysWsConfigVO config = new SysWsConfigVO();
		this.transformFields2ValueObject(config, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemWebServiceConfigLogicService.delete(config);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * core.systemWsConfigSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001A")
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
	 * core.systemWsConfigUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001E")
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
	 * core.systemWsConfigDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001Q")
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
	 * core.systemWsConfigStopOrReloadAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0001Q")
	public String doStopOrReload() throws Exception {
		this.message = "config fail!";
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			String type = this.getFields().get("type");
			Map<String, String> result = CxfServerBean.shutdownOrReloadCallAllSystem(getHttpServletRequest(), type);
			this.message = super.defaultString( result.get("message") ).trim().replaceAll("\n", Constants.HTML_BR);
			if (YesNo.YES.equals(result.get("success"))) {
				this.success = IS_YES;
			}
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) {
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
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
