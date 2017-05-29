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

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.DateFieldCheckUtils;
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
import com.netsteadfast.greenstep.service.logic.ISystemMessageNoticeLogicService;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMessageNoticeSaveOrUpdateAction")
@Scope
public class SystemMessageNoticeSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 2995601401550856228L;
	protected Logger logger=Logger.getLogger(SystemMessageNoticeSaveOrUpdateAction.class);
	private ISystemMessageNoticeLogicService systemMessageNoticeLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemMessageNoticeSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemMessageNoticeLogicService getSystemMessageNoticeLogicService() {
		return systemMessageNoticeLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemMessageNoticeLogicService")		
	public void setSystemMessageNoticeLogicService(
			ISystemMessageNoticeLogicService systemMessageNoticeLogicService) {
		this.systemMessageNoticeLogicService = systemMessageNoticeLogicService;
	}	
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("msgOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_msgOid") )
		.add("noticeId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_noticeId") )
		.add("title", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_title") )
		.add("date1", DateFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_date1") )
		.add("date2", DateFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_date2") )
		.add("message", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0006A_message") )
		.process().throwMessage();
		
		String date1 = this.getFields().get("date1").replaceAll("/", "").replaceAll("-", "");
		String date2 = this.getFields().get("date2").replaceAll("/", "").replaceAll("-", "");	
		this.getCheckFieldHandler().single(
				"date1|date2", 
				(Integer.parseInt(date1) > Integer.parseInt(date2)), 
				this.getText("MESSAGE.CORE_PROG001D0006A_dateRange") )
		.throwMessage();
		
		this.getCheckFieldHandler().single(
				"startHour|startMinutes|endHour|endMinutes", 
				( Integer.parseInt( this.getFields().get("startHour")+this.getFields().get("startMinutes") ) > Integer.parseInt( this.getFields().get("endHour")+this.getFields().get("endMinutes") ) ), 
				this.getText("MESSAGE.CORE_PROG001D0006A_timeRange") )
		.throwMessage();
		
		this.getCheckFieldHandler().single(
				"toAccountOid", 
				(!"true".equals(this.getFields().get("isGlobal")) && this.isNoSelectId(this.getFields().get("toAccountOid"))), 
				this.getText("MESSAGE.CORE_PROG001D0006A_toAccountOid") )
		.throwMessage();
		
	}	
	
	private String getNoticeDate() {
		String date = this.getFields().get("date1").replaceAll("/", "").replaceAll("-", "") + Constants.DATETIME_DELIMITER 
				+ this.getFields().get("date2").replaceAll("/", "").replaceAll("-", "");
		return date;
	}
	
	private String getNoticeTime() {
		String time = this.getFields().get("startHour") + this.getFields().get("startMinutes") + Constants.DATETIME_DELIMITER +
				this.getFields().get("endHour") + this.getFields().get("endMinutes");
		return time;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysMsgNoticeVO notice = new SysMsgNoticeVO();
		this.getFields().put("date", this.getNoticeDate());
		this.getFields().put("time", this.getNoticeTime());
		this.transformFields2ValueObject(notice, new String[]{"noticeId", "title", "message", "date", "time"} );
		notice.setIsGlobal(YesNo.NO);
		if ("true".equals(this.getFields().get("isGlobal"))) {
			notice.setIsGlobal(YesNo.YES);
		}
		DefaultResult<SysMsgNoticeVO> result = this.systemMessageNoticeLogicService.create(
				notice, this.getFields().get("msgOid"), this.getFields().get("toAccountOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysMsgNoticeVO notice = new SysMsgNoticeVO();
		this.getFields().put("date", this.getNoticeDate());
		this.getFields().put("time", this.getNoticeTime());
		this.transformFields2ValueObject(notice, new String[]{"oid", "noticeId", "title", "message", "date", "time"} );
		notice.setIsGlobal(YesNo.NO);
		if ("true".equals(this.getFields().get("isGlobal"))) {
			notice.setIsGlobal(YesNo.YES);
		}
		DefaultResult<SysMsgNoticeVO> result = this.systemMessageNoticeLogicService.update(
				notice, this.getFields().get("toAccountOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysMsgNoticeVO notice = new SysMsgNoticeVO();
		this.transformFields2ValueObject(notice, new String[]{"oid"} );
		DefaultResult<Boolean> result = this.systemMessageNoticeLogicService.delete(notice);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.systemMessageNoticeSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006A")
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
	 * core.systemMessageNoticeUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006E")
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
	 * core.systemMessageNoticeDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006Q")
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
