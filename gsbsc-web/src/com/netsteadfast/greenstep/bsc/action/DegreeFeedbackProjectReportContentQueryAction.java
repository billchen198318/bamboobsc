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
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.util.DegreeFeedbackScoreCalculateUtils;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectReportContentQueryAction")
@Scope
public class DegreeFeedbackProjectReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -3721445811954037906L;
	protected Logger logger=Logger.getLogger(DegreeFeedbackProjectReportContentQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
	
	public DegreeFeedbackProjectReportContentQueryAction() {
		super();
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("projectOid", NotBlankFieldCheckUtils.class, "Data error no project, please close the page!")
		.add("ownerOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG005D0004Q_ownerOid") )
		.process().throwMessage();
	}
	
	private void queryProjectScore() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.project = DegreeFeedbackScoreCalculateUtils.getProjectScore(
				this.getFields().get("projectOid"), 
				this.getFields().get("ownerOid"));
		this.success = IS_YES;
	}
	
	/**
	 * bsc.degreeFeedbackProjectQueryScoreAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0004Q")
	public String doQueryProjectScore() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.queryProjectScore();
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
	public DegreeFeedbackProjectVO getProject() {
		return project;
	}

	public void setProject(DegreeFeedbackProjectVO project) {
		this.project = project;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
