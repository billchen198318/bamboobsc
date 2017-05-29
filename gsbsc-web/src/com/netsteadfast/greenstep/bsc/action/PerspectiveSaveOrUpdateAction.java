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
import com.netsteadfast.greenstep.bsc.action.utils.BscNumberFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IPerspectiveLogicService;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PerspectiveSaveOrUpdateAction")
@Scope
public class PerspectiveSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -5499167704509518082L;
	protected Logger logger=Logger.getLogger(PerspectiveSaveOrUpdateAction.class);
	private IPerspectiveLogicService perspectiveLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public PerspectiveSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IPerspectiveLogicService getPerspectiveLogicService() {
		return perspectiveLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.PerspectiveLogicService")		
	public void setPerspectiveLogicService(
			IPerspectiveLogicService perspectiveLogicService) {
		this.perspectiveLogicService = perspectiveLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("visionOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0002A_visionOid") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0002A_name") )
		.add("weight", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0002A_weight") )
		.add("target", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0002A_target") )
		.add("min", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0002A_min") )
		.process().throwMessage();
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		PerspectiveVO perspective = new PerspectiveVO();
		this.transformFields2ValueObject(perspective, new String[]{"name", "weight", "target", "min", "description"});
		DefaultResult<PerspectiveVO> result = this.perspectiveLogicService.create(
				perspective, this.getFields().get("visionOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		PerspectiveVO perspective = new PerspectiveVO();
		this.transformFields2ValueObject(perspective, new String[]{"oid", "name", "weight", "target", "min", "description"});
		DefaultResult<PerspectiveVO> result = this.perspectiveLogicService.update(
				perspective, this.getFields().get("visionOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		PerspectiveVO perspective = new PerspectiveVO();
		this.transformFields2ValueObject(perspective, new String[]{"oid"});
		DefaultResult<Boolean> result = this.perspectiveLogicService.delete(perspective);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}			
	}
	
	/**
	 * bsc.perspectiveSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002A")
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
	 * bsc.perspectiveUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002E")
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
	 * bsc.perspectiveDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002Q")
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
