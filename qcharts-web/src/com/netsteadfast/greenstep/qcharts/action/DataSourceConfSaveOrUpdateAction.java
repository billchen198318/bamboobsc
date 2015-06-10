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
import com.netsteadfast.greenstep.qcharts.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataSourceLogicService;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.DataSourceConfSaveOrUpdateAction")
@Scope
public class DataSourceConfSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 8827352463287481766L;
	protected Logger logger=Logger.getLogger(DataSourceConfSaveOrUpdateAction.class);
	private IDataSourceLogicService dataSourceLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public DataSourceConfSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IDataSourceLogicService getDataSourceLogicService() {
		return dataSourceLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.DataSourceLogicService")			
	public void setDataSourceLogicService(
			IDataSourceLogicService dataSourceLogicService) {
		this.dataSourceLogicService = dataSourceLogicService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"driverOid",
							"id",
							"name",
							"jdbcUrl",
							"dbAccount",
							"dbPassword"
					}, 
					new String[]{
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_driverOid") + "<BR/>",
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_id") + "<BR/>",							
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_name") + "<BR/>",
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_jdbcUrl") + "<BR/>",
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_dbAccount") + "<BR/>",
							this.getText("MESSAGE.QCHARTS_PROG001D0001A_dbPassword") + "<BR/>"							
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
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
	}		
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		DataSourceConfVO conf = new DataSourceConfVO();
		this.transformFields2ValueObject(conf, new String[]{"id", "name", "jdbcUrl", "dbAccount", "dbPassword", "description"});
		DefaultResult<DataSourceConfVO> result = this.dataSourceLogicService.createConf(this.getFields().get("driverOid"), conf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		DataSourceConfVO conf = new DataSourceConfVO();
		this.transformFields2ValueObject(conf, new String[]{"oid", "id", "name", "jdbcUrl", "dbAccount", "dbPassword", "description"});
		DefaultResult<DataSourceConfVO> result = this.dataSourceLogicService.updateConf(this.getFields().get("driverOid"), conf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}			
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		DataSourceConfVO conf = new DataSourceConfVO();
		this.transformFields2ValueObject(conf, new String[]{"oid"});
		DefaultResult<Boolean> result = this.dataSourceLogicService.deleteConf(conf);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * qcharts.dataSourceConfSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001A")
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
	 * qcharts.dataSourceConfUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001E")
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
	 * qcharts.dataSourceConfDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001Q")
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
