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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormTemplateSaveOrUpdateAction")
@Scope
public class SystemFormTemplateSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 2151158467965309656L;
	protected Logger logger=Logger.getLogger(SystemFormTemplateSaveOrUpdateAction.class);
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemFormTemplateSaveOrUpdateAction() {
		super();
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
	
	@JSON(serialize=false)
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")		
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException, AuthorityException, ServiceException, Exception {
		try {
			super.checkFields(
					new String[]{
							"tplId", 
							"name"
					}, 
					new String[]{
							"Template ID is required and only normal characters!<BR/>",
							"Name is required!<BR/>"
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
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			throw new ControllerException( "Please upload JSP file!" );
		}
		byte[] content = UploadSupportUtils.getDataBytes( this.getFields().get("uploadOid") );
		SysFormTemplateVO template = new SysFormTemplateVO();
		this.transformFields2ValueObject(template, new String[]{"tplId", "name", "description"});
		if ( super.defaultString(template.getDescription()).length()>500 ) {
			template.setDescription( template.getDescription().substring(0, 500) );			
		}
		template.setFileName( template.getTplId() + ".jsp" );
		template.setContent( content );
		DefaultResult<SysFormTemplateVO> result = this.sysFormTemplateService.saveObject(template);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
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
