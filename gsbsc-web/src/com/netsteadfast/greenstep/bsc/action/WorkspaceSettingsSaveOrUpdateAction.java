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

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IWorkspaceLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.WorkspaceVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.WorkspaceSettingsSaveOrUpdateAction")
@Scope
public class WorkspaceSettingsSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -1892382731860390651L;
	protected Logger logger=Logger.getLogger(WorkspaceSettingsSaveOrUpdateAction.class);
	private IWorkspaceLogicService workspaceLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public WorkspaceSettingsSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IWorkspaceLogicService getWorkspaceLogicService() {
		return workspaceLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.WorkspaceLogicService")		
	public void setWorkspaceLogicService(
			IWorkspaceLogicService workspaceLogicService) {
		this.workspaceLogicService = workspaceLogicService;
	}
	
	private void checkFields() throws ControllerException {	
		this.getCheckFieldHandler()
		.add("workspaceId", IdFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0002A_workspaceId") )
		.add("workspaceName", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0002A_workspaceName") )
		.add("workspaceTemplateOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0002A_workspaceTemplateOid") )
		.process().throwMessage();
	}
	
	@SuppressWarnings("unchecked")
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String datas = this.getFields().get("datas");
		datas = SimpleUtils.deB64( datas );	// 這邊要 decode btoa
		String jsonDataStr = URLDecoder.decode( datas, "utf8" ); // 這邊要 decode 因為 encodeURIComponent
		Map<String, Object> jsonData = (Map<String, Object>)
				new ObjectMapper().readValue(jsonDataStr, LinkedHashMap.class);
		DefaultResult<WorkspaceVO> result = this.workspaceLogicService.create(
				this.getFields().get("workspaceId"), 
				this.getFields().get("workspaceName"), 
				this.getFields().get("workspaceDescription"), 
				this.getFields().get("workspaceTemplateOid"), 
				jsonData);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null ) {
			this.success = IS_YES;
		}		
	}	
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {		
		DefaultResult<Boolean> result = this.workspaceLogicService.delete(this.getFields().get("oid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * bsc.workspaceSettingsSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0002A")
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
	 * bsc.workspaceSettingsDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0002Q")
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
