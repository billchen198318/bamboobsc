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

import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.PositiveIntegerFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.service.logic.ISystemBeanHelpLogicService;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprMapVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBeanHelpSaveOrUpdateAction")
@Scope
public class SystemBeanHelpSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 7967876807741620140L;
	protected Logger logger=Logger.getLogger(SystemBeanHelpSaveOrUpdateAction.class);
	private ISystemBeanHelpLogicService systemBeanHelpLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemBeanHelpSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemBeanHelpLogicService getSystemBeanHelpLogicService() {
		return systemBeanHelpLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.SystemBeanHelpLogicService")	
	public void setSystemBeanHelpLogicService(
			ISystemBeanHelpLogicService systemBeanHelpLogicService) {
		this.systemBeanHelpLogicService = systemBeanHelpLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("systemOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003A_systemOid") )
		.add("beanId", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003A_beanId") )
		.add("method", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003A_method") )
		.process().throwMessage();
	}
	
	private void checkExprFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("exprOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S00_exprOid") )
		.add("exprSeq", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S00_exprSeq") )
		.add("runType", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S00_runType") )
		.process().throwMessage();
	}
	
	private void checkExprMapFields() throws ControllerException {
		if ("true".equals(this.getFields().get("methodResultFlag"))) {
			this.getCheckFieldHandler()
			.single(
					"varName", 
					( StringUtils.isBlank(this.getFields().get("varName")) ), 
					"Variable is required!"
			).throwMessage();
			return;
		}
		this.getCheckFieldHandler()
		.add("varName", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S01_varName") )
		.add("methodParamClass", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S01_methodParamClass") )
		.add("methodParamIndex", PositiveIntegerFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG003D0003E_S01_methodParamIndex") )
		.process().throwMessage();
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysBeanHelpVO beanHelp = new SysBeanHelpVO();
		this.transformFields2ValueObject(beanHelp, new String[]{"beanId", "method", "description"} );
		beanHelp.setEnableFlag(YesNo.NO);
		if ("true".equals(this.getFields().get("enableFlag"))) {
			beanHelp.setEnableFlag(YesNo.YES);
		}
		DefaultResult<SysBeanHelpVO> result = this.systemBeanHelpLogicService.create(
				beanHelp, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysBeanHelpVO beanHelp = new SysBeanHelpVO();
		this.transformFields2ValueObject(beanHelp, new String[]{"oid", "beanId", "method", "description"} );
		beanHelp.setEnableFlag(YesNo.NO);
		if ("true".equals(this.getFields().get("enableFlag"))) {
			beanHelp.setEnableFlag(YesNo.YES);
		}
		DefaultResult<SysBeanHelpVO> result = this.systemBeanHelpLogicService.update(
				beanHelp, this.getFields().get("systemOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBeanHelpVO beanHelp = new SysBeanHelpVO();
		this.transformFields2ValueObject(beanHelp, new String[]{"oid"} );		
		DefaultResult<Boolean> result = this.systemBeanHelpLogicService.delete(beanHelp);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}				
	}
	
	private void saveExpr() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkExprFields();
		SysBeanHelpExprVO beanHelpExpr = new SysBeanHelpExprVO();
		this.transformFields2ValueObject(beanHelpExpr, new String[]{"exprSeq", "runType"});
		DefaultResult<SysBeanHelpExprVO> result = this.systemBeanHelpLogicService.createExpr(
				beanHelpExpr, this.getFields().get("helpOid"), this.getFields().get("exprOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void deleteExpr() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBeanHelpExprVO beanHelpExpr = new SysBeanHelpExprVO();
		this.transformFields2ValueObject(beanHelpExpr, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemBeanHelpLogicService.deleteExpr(beanHelpExpr);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	private void saveExprMap() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkExprMapFields();
		SysBeanHelpExprMapVO beanHelpExprMap = new SysBeanHelpExprMapVO();
		this.transformFields2ValueObject(beanHelpExprMap, 
				new String[]{"varName", "methodResultFlag", "methodParamClass", "methodParamIndex"});
		beanHelpExprMap.setMethodResultFlag(YesNo.NO);
		if ("true".equals(this.getFields().get("methodResultFlag"))) {
			beanHelpExprMap.setMethodResultFlag(YesNo.YES);
		}
		DefaultResult<SysBeanHelpExprMapVO> result = this.systemBeanHelpLogicService.createExprMap(
				beanHelpExprMap, this.getFields().get("helpExprOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void deleteExprMap() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBeanHelpExprMapVO beanHelpExprMap = new SysBeanHelpExprMapVO();
		this.transformFields2ValueObject(beanHelpExprMap, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemBeanHelpLogicService.deleteExprMap(beanHelpExprMap);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.systemBeanHelpSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003A")
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
	 * core.systemBeanHelpUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E")
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
	 * core.systemBeanHelpDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003Q")
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
	 * core.systemBeanHelpExprSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S00")
	public String doExprSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveExpr();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemBeanHelpExprDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S00")
	public String doExprDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.deleteExpr();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * core.systemBeanHelpExprMapSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S01")
	public String doExprMapSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveExprMap();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemBeanHelpExprMapDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S01")
	public String doExprMapDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.deleteExprMap();
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
