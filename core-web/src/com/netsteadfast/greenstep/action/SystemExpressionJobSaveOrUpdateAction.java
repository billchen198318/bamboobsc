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

import com.netsteadfast.greenstep.action.utils.EmailFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.ExpressionJobConstants;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJob;
import com.netsteadfast.greenstep.service.ISysExprJobService;
import com.netsteadfast.greenstep.service.logic.ISystemExpressionLogicService;
import com.netsteadfast.greenstep.util.SystemExpressionJobUtils;
import com.netsteadfast.greenstep.vo.SysExprJobLogVO;
import com.netsteadfast.greenstep.vo.SysExprJobVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemExpressionJobSaveOrUpdateAction")
@Scope
public class SystemExpressionJobSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 4042327976292020695L;
	protected Logger logger=Logger.getLogger(SystemExpressionJobSaveOrUpdateAction.class);
	private ISystemExpressionLogicService systemExpressionLogicService;
	private ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService;
	private String message = "";
	private String success = IS_NO;	
	
	public SystemExpressionJobSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemExpressionLogicService getSystemExpressionLogicService() {
		return systemExpressionLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemExpressionLogicService")			
	public void setSystemExpressionLogicService(ISystemExpressionLogicService systemExpressionLogicService) {
		this.systemExpressionLogicService = systemExpressionLogicService;
	}	
	
	@JSON(serialize=false)
	public ISysExprJobService<SysExprJobVO, TbSysExprJob, String> getSysExprJobService() {
		return sysExprJobService;
	}

	@Autowired
	@Resource(name="core.service.SysExprJobService")			
	public void setSysExprJobService(ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService) {
		this.sysExprJobService = sysExprJobService;
	}		
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("systemOid", SelectItemFieldCheckUtils.class, "Please select system!" )
		.add("id", IdFieldCheckUtils.class, "Please input Id!" )
		.add("name", NotBlankFieldCheckUtils.class, "Please input Name!" )
		.add("expressionOid", SelectItemFieldCheckUtils.class, "Please select expression!" )
		.add("contact", EmailFieldCheckUtils.class, "Contact value is not email address!" )
		.process().throwMessage();
		
		this.getCheckFieldHandler()
		.single(
				"contact", 
				( !ExpressionJobConstants.CONTACT_MODE_NO.equals( this.getFields().get("contactMode") ) && StringUtils.isBlank(this.getFields().get("contact")) ), 
				"Please input contact!"
		).throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExprJobVO sysExprJob = new SysExprJobVO();
		this.transformFields2ValueObject(sysExprJob, new String[]{"id", "name", 
				"description", "runDayOfWeek", "runHour", "runMinute", "contactMode", "contact"});
		sysExprJob.setActive(YesNo.NO);
		if ("true".equals(this.getFields().get("active"))) {
			sysExprJob.setActive(YesNo.YES);
		}
		sysExprJob.setCheckFault(YesNo.NO);
		if ("true".equals(this.getFields().get("checkFault"))) {
			sysExprJob.setCheckFault(YesNo.YES);
		}
		DefaultResult<SysExprJobVO> result = this.systemExpressionLogicService.createJob(
				sysExprJob, 
				this.getFields().get("systemOid"), 
				this.getFields().get("expressionOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysExprJobVO sysExprJob = new SysExprJobVO();
		this.transformFields2ValueObject(sysExprJob, new String[]{"oid", "id", "name", 
				"description", "runDayOfWeek", "runHour", "runMinute", "contactMode", "contact"});
		sysExprJob.setActive(YesNo.NO);
		if ("true".equals(this.getFields().get("active"))) {
			sysExprJob.setActive(YesNo.YES);
		}	
		sysExprJob.setCheckFault(YesNo.NO);
		if ("true".equals(this.getFields().get("checkFault"))) {
			sysExprJob.setCheckFault(YesNo.YES);
		}		
		DefaultResult<SysExprJobVO> result = this.systemExpressionLogicService.updateJob(
				sysExprJob, 
				this.getFields().get("systemOid"), 
				this.getFields().get("expressionOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysExprJobVO sysExprJob = new SysExprJobVO();
		this.transformFields2ValueObject(sysExprJob, new String[]{"oid"} );
		DefaultResult<Boolean> result = this.systemExpressionLogicService.deleteJob(sysExprJob);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}	
	
	private void manualExecute() throws ControllerException, AuthorityException, ServiceException, Exception {
		String exprJobOid = this.getFields().get("oid");
		String accountId = super.getAccountId();
		SysExprJobVO exprJob = new SysExprJobVO();
		exprJob.setOid(exprJobOid);
		DefaultResult<SysExprJobVO> result = this.sysExprJobService.findObjectByOid(exprJob);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		exprJob = result.getValue();
		
		if (Constants.getMainSystem().equals(exprJob.getSystem())) { // 是自己 CORE系統, 所以不用觸發遠端的服務
			SystemExpressionJobUtils.executeJobForManual(exprJobOid);
			return;
		}
		/*
		SysExprJobLogVO jobLog = SystemExpressionJobUtils.executeJobForManualFromRestServiceUrl(
				exprJob, accountId, this.getHttpServletRequest());
		*/
		SysExprJobLogVO jobLog = SystemExpressionJobUtils.executeJobForManualWebClient(
				exprJob, accountId, this.getHttpServletRequest());
		if (null != jobLog && !StringUtils.isBlank(jobLog.getFaultMsg())) {
			throw new ServiceException(jobLog.getFaultMsg());
		}
	}
	
	/**
	 * core.systemExpressionJobSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006A")
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
	 * core.systemExpressionJobUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006E")
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
	 * core.systemExpressionJobDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006Q")
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
	 * core.systemExpressionJobManualExecuteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006Q")
	public String doManualExecute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.manualExecute();
			this.success = IS_YES;
			this.message = "Please check run status flag!";
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
