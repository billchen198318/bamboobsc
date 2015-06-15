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
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ReportRoleViewSaveOrUpdateAction")
@Scope
public class ReportRoleViewSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 2478393762962491235L;
	protected Logger logger=Logger.getLogger(ReportRoleViewSaveOrUpdateAction.class);
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public ReportRoleViewSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(
			IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"roleOid"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG004D0003Q_roleOid") + "<BR/>"
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class
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
		DefaultResult<Boolean> result = this.reportRoleViewLogicService.create(
				this.getFields().get("roleOid"), 
				this.transformAppendIds2List(this.getFields().get("emplOids")), 
				this.transformAppendIds2List(this.getFields().get("orgaOids")));
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null && result.getValue() ) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.reportRoleViewSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0003Q")
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
