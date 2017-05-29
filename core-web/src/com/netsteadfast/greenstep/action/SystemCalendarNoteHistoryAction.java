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

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.netsteadfast.greenstep.model.TemplateCode;
import com.netsteadfast.greenstep.model.TemplateResultObj;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

/**
 * 主要提供給 user 查看自己的 calendar note 備忘錄
 *
 */
@ControllerAuthority(check=false)
@Controller("core.web.controller.SystemCalendarNoteHistoryAction")
@Scope
public class SystemCalendarNoteHistoryAction extends BaseJsonAction {
	private static final long serialVersionUID = 5053053457855886493L;
	protected Logger logger=Logger.getLogger(SystemCalendarNoteHistoryAction.class);
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemCalendarNoteHistoryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> getSysCalendarNoteService() {
		return sysCalendarNoteService;
	}

	@Autowired
	@Resource(name="core.service.SysCalendarNoteService")			
	public void setSysCalendarNoteService(
			ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService) {
		this.sysCalendarNoteService = sysCalendarNoteService;
	}
	
	private void generateContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		String date = this.getFields().get("date");
		if (!SimpleUtils.isDate(date)) {
			return;
		}
		date = date.replaceAll("/", "").replaceAll("-", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", super.getAccountId());
		params.put("date", date);
		Map<String, String> orderParams = new LinkedHashMap<String, String>();		
		orderParams.put("time", "ASC");
		List<TbSysCalendarNote> searchList = sysCalendarNoteService.findListByParams2(params, null, null, orderParams);
		if (searchList==null || searchList.size()<1) {
			this.message = "";
			return;
		}
		StringBuilder sb = new StringBuilder();		
		for (TbSysCalendarNote calendarNote : searchList) {
			calendarNote.setTitle( TemplateUtils.escapeHtml4TemplateHtmlContent(calendarNote.getTitle()) );
			calendarNote.setNote( TemplateUtils.escapeHtml4TemplateHtmlContent(calendarNote.getNote()) );
			TemplateResultObj result = TemplateUtils.getResult(TemplateCode.TPLMSG0001, calendarNote);
			sb.append( result.getContent() );
		}		
		this.message = sb.toString();
	}	
	
	/**
	 * core.systemCalendarNoteHistoryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.generateContent();
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
