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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectSaveOrUpdateAction")
@Scope
public class DegreeFeedbackProjectSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6776416857096672930L;
	protected Logger logger=Logger.getLogger(DegreeFeedbackProjectSaveOrUpdateAction.class);
	private IDegreeFeedbackLogicService degreeFeedbackLogicService;
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = ""; // 圖檔資料的oid
	
	public DegreeFeedbackProjectSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IDegreeFeedbackLogicService getDegreeFeedbackLogicService() {
		return degreeFeedbackLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.DegreeFeedbackLogicService")		
	public void setDegreeFeedbackLogicService(
			IDegreeFeedbackLogicService degreeFeedbackLogicService) {
		this.degreeFeedbackLogicService = degreeFeedbackLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG005D0001A_name") )
		.add("year", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG005D0001A_year") )
		.process().throwMessage();
		
		if (!SimpleUtils.isDate(this.getFields().get("year")+"/01/01")) {
			super.throwMessage("year", this.getText("MESSAGE.BSC_PROG005D0001A_msg1"));
		}
		if (super.defaultString(this.getFields().get("ownerOids")).trim().length()<1) {
			super.throwMessage("ownerOids", this.getText("MESSAGE.BSC_PROG005D0001A_msg2"));
		}
		if (super.defaultString(this.getFields().get("raterOids")).trim().length()<1) {
			super.throwMessage("raterOids", this.getText("MESSAGE.BSC_PROG005D0001A_msg3"));
		}	
		Map<String, List<Map<String, Object>>> levelData = null;
		Map<String, List<Map<String, Object>>> itemData = null;
		try {
			levelData = this.fillJsonData( this.getFields().get("levelData") );
			itemData = this.fillJsonData( this.getFields().get("itemData") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (levelData == null || levelData.get("data") == null || levelData.get("data").size() < 1 ) {
			super.throwMessage("levelData", this.getText("MESSAGE.BSC_PROG005D0001A_msg4"));
		}
		if (itemData == null || itemData.get("data") == null || itemData.get("data").size() < 1 ) {
			super.throwMessage("itemData", this.getText("MESSAGE.BSC_PROG005D0001A_msg5"));
		}		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Map<String, Object>>> fillJsonData(String dataStr) throws Exception {		
		return (Map<String, List<Map<String, Object>>>)new ObjectMapper().readValue( dataStr, LinkedHashMap.class );
	}
	
	private List<DegreeFeedbackItemVO> fillItems() throws Exception {
		List<DegreeFeedbackItemVO> items = new ArrayList<DegreeFeedbackItemVO>();
		Map<String, List<Map<String, Object>>> dataMap = this.fillJsonData( this.getFields().get("itemData") );
		List<Map<String, Object>> nodes = dataMap.get( "data" );
		for (Map<String, Object> node : nodes) {
			DegreeFeedbackItemVO obj = new DegreeFeedbackItemVO();
			obj.setName( String.valueOf(node.get("name")) );
			obj.setDescription( String.valueOf(node.get("description")) );	
			items.add( obj );
		}
		return items;
	}
	
	private List<DegreeFeedbackLevelVO> fillLevels() throws Exception {
		List<DegreeFeedbackLevelVO> levels = new ArrayList<DegreeFeedbackLevelVO>();
		Map<String, List<Map<String, Object>>> dataMap = this.fillJsonData( this.getFields().get("levelData") );
		List<Map<String, Object>> nodes = dataMap.get( "data" );
		for (Map<String, Object> node : nodes) {
			DegreeFeedbackLevelVO obj = new DegreeFeedbackLevelVO();
			obj.setName( String.valueOf(node.get("name")) );
			obj.setValue( NumberUtils.toInt(String.valueOf(node.get("value")), 1) );
			levels.add( obj );
		}
		return levels;
	}

	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		this.transformFields2ValueObject(project, new String[]{"name", "year", "description"});
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackLogicService.createProject(
				project, 
				this.fillItems(), 
				this.fillLevels(), 
				this.transformAppendIds2List(this.getFields().get("ownerOids")), 
				this.transformAppendIds2List(this.getFields().get("raterOids")));		
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
		this.message = result.getSystemMessage().getValue();
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		this.transformFields2ValueObject(project, new String[]{"oid"});
		DefaultResult<Boolean> result = this.degreeFeedbackLogicService.deleteProject(project);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		this.transformFields2ValueObject(project, new String[]{"oid", "name", "year", "description"});
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackLogicService.updateProject(
				project, 
				this.fillItems(), 
				this.fillLevels(), 
				this.transformAppendIds2List(this.getFields().get("ownerOids")), 
				this.transformAppendIds2List(this.getFields().get("raterOids")));		
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
		this.message = result.getSystemMessage().getValue();		
	}
	
	private void confirmProcessFlowTask() throws ControllerException, AuthorityException, ServiceException, Exception {
		String projectOid = StringUtils.defaultString( this.getFields().get("projectOid") );
		String taskId = StringUtils.defaultString( this.getFields().get("taskId") );
		String reason = StringUtils.defaultString( this.getFields().get("reason") );
		String confirm = StringUtils.defaultString( this.getFields().get("confirm") );
		this.degreeFeedbackLogicService.confirmTask(projectOid, taskId, reason, confirm);
		this.success = IS_YES;
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS);
	}
	
	private void startProcess() throws ControllerException, AuthorityException, ServiceException, Exception {
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		this.transformFields2ValueObject(project, new String[]{"oid"});
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackLogicService.startProcess(project);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.degreeFeedbackProjectSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A")
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
	 * bsc.degreeFeedbackProjectDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001Q")
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
	
	/**
	 * bsc.degreeFeedbackProjecUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001E")
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
	 * bsc.degreeFeedbackProjectConfirmProcessFlowSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A_S03")
	public String doConfirmProcessFlowTask() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.confirmProcessFlowTask();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * bsc.degreeFeedbackProjectLoadTaskDiagramAction.action
	 * 
	 * @return
	 * @throws Exception
	 */		
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A_S02")
	public String doLoadTaskDiagram() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.uploadOid = this.degreeFeedbackLogicService
					.getTaskDiagram( this.getFields().get("taskId") );
			if (!StringUtils.isBlank(this.uploadOid)) {
				this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
				this.success = IS_YES;
			}
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}
	
	/**
	 * bsc.degreeFeedbackProjectStartProcessAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG005D0001E")
	public String doStartProcess() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.startProcess();
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
	public String getUploadOid() {
		return uploadOid;
	}

	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
