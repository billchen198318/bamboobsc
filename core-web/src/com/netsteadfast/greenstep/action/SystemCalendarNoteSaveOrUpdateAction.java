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

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.EmailFieldCheckUtils;
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
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.service.logic.ISystemCalendarNoteLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemCalendarNoteSaveOrUpdateAction")
@Scope
public class SystemCalendarNoteSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6828273158664092830L;
	protected Logger logger=Logger.getLogger(SystemCalendarNoteSaveOrUpdateAction.class);
	private ISystemCalendarNoteLogicService systemCalendarNoteLogicService;
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService; 
	private String message = "";
	private String success = IS_NO;
	
	public SystemCalendarNoteSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemCalendarNoteLogicService getSystemCalendarNoteLogicService() {
		return systemCalendarNoteLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemCalendarNoteLogicService")		
	public void setSystemCalendarNoteLogicService(
			ISystemCalendarNoteLogicService systemCalendarNoteLogicService) {
		this.systemCalendarNoteLogicService = systemCalendarNoteLogicService;
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

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"title",
							"note",
							"date",
							"contact",
							"accountOid"
					}, 
					new String[]{
							"Title is required!<BR/>",
							"Note is required!<BR/>",
							"Date is required!<BR/>",
							"Email format error!<BR/>",
							"Please select a account!<BR/>"
					},					
					new Class[]{
							NotBlankFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							EmailFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
		
		// date check
		if ( !SimpleUtils.isDate(this.getFields().get("date")) ) {
			this.getFieldsId().add("date");
			throw new ControllerException("Date format error!");				
		}
		
		// time check
		String time = super.defaultString( this.getFields().get("time") );
		String timeStartEnd[] = time.split(Constants.DATETIME_DELIMITER);
		if (timeStartEnd==null || timeStartEnd.length!=2) {
			throw new ControllerException("Time start and end error!");	
		}
		if ( SimpleUtils.getInt(timeStartEnd[1], -1)<SimpleUtils.getInt(timeStartEnd[0], 0) ) {
			throw new ControllerException("Time start and end error!");	
		}
		
	}	
	
	/**
	 * 儲存 TB_SYS_CALENDAR_NOTE
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String accountOid = this.getFields().get("accountOid");
		SysCalendarNoteVO sysCalendarNote = new SysCalendarNoteVO();
		sysCalendarNote = this.transformFields2ValueObject(sysCalendarNote, new String[]{ "title", "note", "date", "time", "contact" });
		sysCalendarNote.setAlert(YesNo.NO);
		if ("true".equals(this.getFields().get("alert"))) {
			sysCalendarNote.setAlert(YesNo.YES);
		}
		DefaultResult<SysCalendarNoteVO> result = this.systemCalendarNoteLogicService.create(sysCalendarNote, accountOid);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.message = result.getSystemMessage().getValue();
		this.success = IS_YES;
	}	
	
	/**
	 * 更新 TB_SYS_CALENDAR_NOTE
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysCalendarNoteVO sysCalendarNote = new SysCalendarNoteVO();
		sysCalendarNote = this.transformFields2ValueObject(sysCalendarNote, 
				new String[]{ "oid", "title", "note", "date", "time", "contact" });
		sysCalendarNote.setAlert(YesNo.NO);
		if ("true".equals(this.getFields().get("alert"))) {
			sysCalendarNote.setAlert(YesNo.YES);
		}
		DefaultResult<SysCalendarNoteVO> result = this.systemCalendarNoteLogicService.update(sysCalendarNote);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.message = result.getSystemMessage().getValue();
		this.success = IS_YES;		
	}
	
	/**
	 * 刪除 TB_SYS_CALENDAR_NOTE
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysCalendarNoteVO sysCalendarNote = new SysCalendarNoteVO();
		sysCalendarNote = this.transformFields2ValueObject(sysCalendarNote, new String[]{"oid"});	
		DefaultResult<Boolean> result = this.sysCalendarNoteService.deleteObject(sysCalendarNote);
		if (result.getValue()==null || !result.getValue()) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.message = result.getSystemMessage().getValue();
		this.success = IS_YES;			
	}
	
	/**
	 * core.systemCalendarNoteSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004A")
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
	
	/**
	 * core.systemCalendarNoteUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004E")	
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
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
	
	/**
	 * core.systemCalendarNoteDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004Q")	
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
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
