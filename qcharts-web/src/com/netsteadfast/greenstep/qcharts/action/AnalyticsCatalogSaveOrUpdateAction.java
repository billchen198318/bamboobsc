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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.qcharts.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsCatalogLogicService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsCatalogSaveOrUpdateAction")
@Scope
public class AnalyticsCatalogSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 311305594446071320L;
	protected Logger logger=Logger.getLogger(AnalyticsCatalogSaveOrUpdateAction.class);
	private IAnalyticsCatalogLogicService analyticsCatalogLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public AnalyticsCatalogSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IAnalyticsCatalogLogicService getAnalyticsCatalogLogicService() {
		return analyticsCatalogLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.AnalyticsCatalogLogicService")			
	public void setAnalyticsCatalogLogicService(
			IAnalyticsCatalogLogicService analyticsCatalogLogicService) {
		this.analyticsCatalogLogicService = analyticsCatalogLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {		
		try {
			super.checkFields(
					new String[]{
							"id",
							"name"
					}, 
					new String[]{						
							this.getText("MESSAGE.QCHARTS_PROG001D0004A_id") + "<BR/>",
							this.getText("MESSAGE.QCHARTS_PROG001D0004A_name") + "<BR/>"
					}, 
					new Class[]{
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
	}			
	
	private void checkUploadCatalog() throws ControllerException, AuthorityException, ServiceException, Exception {
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			return;
		}
		byte datas[] = UploadSupportUtils.getDataBytes( this.getFields().get("uploadOid") );
		if ( null == datas ) {
			throw new Exception( this.getText("MESSAGE.QCHARTS_PROG001D0004A_uploadOid_msg1") + "<BR/>" );
		}
		String xmlContent = new String(datas, Constants.BASE_ENCODING);
		if ( xmlContent.indexOf("Schema") == -1 ) {
			throw new Exception( this.getText("MESSAGE.QCHARTS_PROG001D0004A_uploadOid_msg2") + "<BR/>" );
		}
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			throw new ControllerException( this.getText("MESSAGE.QCHARTS_PROG001D0004A_uploadOid_msg3") + "<BR/>" );
		}
		this.checkUploadCatalog();
		OlapCatalogVO olapCatalog = new OlapCatalogVO();
		this.transformFields2ValueObject(olapCatalog, new String[]{"id", "name", "description"});
		DefaultResult<OlapCatalogVO> result = this.analyticsCatalogLogicService.create(
				olapCatalog, this.getFields().get("uploadOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}	
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.checkUploadCatalog();
		OlapCatalogVO olapCatalog = new OlapCatalogVO();
		this.transformFields2ValueObject(olapCatalog, new String[]{"oid", "id", "name", "description"});
		DefaultResult<OlapCatalogVO> result = this.analyticsCatalogLogicService.update(
				olapCatalog, this.getFields().get("uploadOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}			
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		OlapCatalogVO olapCatalog = new OlapCatalogVO();
		this.transformFields2ValueObject(olapCatalog, new String[]{"oid"});
		DefaultResult<Boolean> result = this.analyticsCatalogLogicService.delete(olapCatalog);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}

	/**
	 * qcharts.analyticsCatalogSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004A")
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
	 * qcharts.analyticsCatalogUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004E")
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
	 * qcharts.analyticsCatalogDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004Q")
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
