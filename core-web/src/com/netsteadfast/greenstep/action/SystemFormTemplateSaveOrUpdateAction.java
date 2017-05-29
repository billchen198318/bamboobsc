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
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.service.logic.ISystemFormLogicService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormTemplateSaveOrUpdateAction")
@Scope
public class SystemFormTemplateSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 2151158467965309656L;
	protected Logger logger=Logger.getLogger(SystemFormTemplateSaveOrUpdateAction.class);
	private ISystemFormLogicService systemFormLogicService;
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = ""; // 要處理 SYS_FORM_TEMPLATE.CONTENT 抄寫至 TB_SYS_UPLOAD.CONTENT 要用的
	
	public SystemFormTemplateSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public ISystemFormLogicService getSystemFormLogicService() {
		return systemFormLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemFormLogicService")		
	public void setSystemFormLogicService(
			ISystemFormLogicService systemFormLogicService) {
		this.systemFormLogicService = systemFormLogicService;
	}
	
	@JSON(serialize=false)
	public ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> getSysFormTemplateService() {
		return sysFormTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysFormTemplateService")		
	public void setSysFormTemplateService(
			ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService) {
		this.sysFormTemplateService = sysFormTemplateService;
	}
	
	private void checkFields() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.getCheckFieldHandler()
		.add("tplId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0012A_tplId") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0012A_name") )
		.process().throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			super.throwMessage( "uploadOid", this.getText("MESSAGE.CORE_PROG001D0012A_uploadOid") );
		}
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"tplId", "name", "description"});
		DefaultResult<SysFormTemplateVO> result = this.systemFormLogicService.createTmplate(
				template, this.getFields().get("uploadOid") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}		
	}	
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid", "tplId", "name", "description"});
		DefaultResult<SysFormTemplateVO> result = this.systemFormLogicService.updateTemplate(
				template, this.getFields().get("uploadOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemFormLogicService.deleteTemplate(template);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void copy2Upload() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid"});
		DefaultResult<SysFormTemplateVO> result = this.sysFormTemplateService.findObjectByOid(template);
		if ( result.getValue() == null ) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		template = result.getValue();
		this.uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				template.getContent(), 
				template.getFileName());	
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
		this.success = IS_YES;
	}
	
	private void updateContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"oid"});
		DefaultResult<SysFormTemplateVO> result = this.systemFormLogicService.updateTemplateContentOnly(
				template, this.getFields().get("content") );
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null ) {
			this.success = IS_YES;
		}
	}

	/**
	 * core.systemFormTemplateSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012A")
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
	 * core.systemFormTemplateUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012E")
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
	 * core.systemFormTemplateDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0012Q")
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
	 * core.systemFormTemplateCopy2UploadAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG001D0012Q")
	public String doCopy2Upload() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.copy2Upload();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.systemFormTemplateContentUploadAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG001D0012Q")
	public String doUploadContent() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.updateContent();
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
