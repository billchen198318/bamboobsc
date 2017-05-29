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
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
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
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.service.logic.ISystemExpressionLogicService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemExpressionSaveOrUpdateAction")
@Scope
public class SystemExpressionSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 4652687376570734328L;
	protected Logger logger=Logger.getLogger(SystemExpressionSaveOrUpdateAction.class);
	private ISystemExpressionLogicService systemExpressionLogicService; 
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private String uploadOid = "";
	private String message = "";
	private String success = IS_NO;
	
	public SystemExpressionSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public ISystemExpressionLogicService getSystemExpressionLogicService() {
		return systemExpressionLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemExpressionLogicService")			
	public void setSystemExpressionLogicService(
			ISystemExpressionLogicService systemExpressionLogicService) {
		this.systemExpressionLogicService = systemExpressionLogicService;
	}
	
	@JSON(serialize=false)
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")	
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("type", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0002A_type") )
		.add("exprId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0002A_exprId") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0002A_name") )
		.add("content", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0002A_iframe1") )
		.process().throwMessage();
		
		this.getCheckFieldHandler().single(
				"exprId", 
				(Constants.HTML_SELECT_NO_SELECT_ID.equals(this.getFields().get("exprId"))), 
				this.getText("MESSAGE.CORE_PROG003D0002A_exprId_msg1")
		).throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"exprId", "type", "name", "content", "description"} );
		DefaultResult<SysExpressionVO> result = this.systemExpressionLogicService.create(expression);
		this.message = result.getSystemMessage().getValue(); 
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid", "exprId", "type", "name", "content", "description"} );
		DefaultResult<SysExpressionVO> result = this.systemExpressionLogicService.update(expression);
		this.message = result.getSystemMessage().getValue(); 
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}				
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid"} );
		DefaultResult<Boolean> result = this.systemExpressionLogicService.delete(expression);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void copy2Upload() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid"});
		DefaultResult<SysExpressionVO> result = this.sysExpressionService.findObjectByOid(expression);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		expression = result.getValue();
		this.uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				expression.getContent().getBytes(), 
				expression.getExprId() + "." + expression.getType());	
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
		this.success = IS_YES;
	}
	
	private void updateContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		String content = this.getFields().get("content");
		if ( StringUtils.isBlank(content) ) {
			super.throwMessage( this.getText("CORE_PROG003D0002A_exprId_msg2") );
		}
		if ( content.length() > 8000 ) {
			super.throwMessage( this.getText("CORE_PROG003D0002A_exprId_msg3") );
		}
		SysExpressionVO expression = new SysExpressionVO();
		this.transformFields2ValueObject(expression, new String[]{"oid"});
		DefaultResult<SysExpressionVO> oldResult = this.sysExpressionService.findObjectByOid(expression);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		expression = oldResult.getValue();
		expression.setContent( content );
		DefaultResult<SysExpressionVO> result = this.sysExpressionService.updateObject(expression);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.systemExpressionSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002A")
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
	 * core.systemExpressionUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002E")
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
	 * core.systemExpressionDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002Q")
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
	 * core.systemExpressionCopy2UpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0002Q")
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
	 * core.systemExpressionContentUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0002Q")
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
