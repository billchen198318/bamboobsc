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
package com.netsteadfast.greenstep.qcharts.action;

import java.util.List;
import java.util.Map;

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
import com.netsteadfast.greenstep.qcharts.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsConfigLogicService;
import com.netsteadfast.greenstep.vo.OlapConfVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsConfigSaveOrUpdateAction")
@Scope
public class AnalyticsConfigSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 6345903521568621470L;
	protected Logger logger=Logger.getLogger(AnalyticsConfigSaveOrUpdateAction.class);
	private IAnalyticsConfigLogicService analyticsConfigLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public AnalyticsConfigSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IAnalyticsConfigLogicService getAnalyticsConfigLogicService() {
		return analyticsConfigLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.AnalyticsConfigLogicService")		
	public void setAnalyticsConfigLogicService(
			IAnalyticsConfigLogicService analyticsConfigLogicService) {
		this.analyticsConfigLogicService = analyticsConfigLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("id", IdFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG001D0003A_id") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG001D0003A_name") )
		.add("jdbcDrivers", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG001D0003A_jdbcDrivers") )
		.add("jdbcUrl", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG001D0003A_jdbcUrl") )
		.process().throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		OlapConfVO olapConf = new OlapConfVO();
		this.transformFields2ValueObject(olapConf, new String[]{"id", "name", "jdbcDrivers", "jdbcUrl", "description"});
		DefaultResult<OlapConfVO> result = this.analyticsConfigLogicService.create(olapConf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}			
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		OlapConfVO olapConf = new OlapConfVO();
		this.transformFields2ValueObject(olapConf, new String[]{"oid", "id", "name", "jdbcDrivers", "jdbcUrl", "description"});
		DefaultResult<OlapConfVO> result = this.analyticsConfigLogicService.update(olapConf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}			
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		OlapConfVO olapConf = new OlapConfVO();
		this.transformFields2ValueObject(olapConf, new String[]{"oid"});
		DefaultResult<Boolean> result = this.analyticsConfigLogicService.delete(olapConf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * qcharts.analyticsConfigSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003A")
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
	 * qcharts.analyticsConfigUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003E")
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
	 * qcharts.analyticsConfigDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003Q")
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
