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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemCalendarNoteManagementAction")
@Scope
public class SystemCalendarNoteManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 3164493299966511321L;
	protected Logger logger=Logger.getLogger(SystemCalendarNoteManagementAction.class);
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService;
	private Map<String, String> accountMap = new LinkedHashMap<String, String>();
	private Map<String, String> hourMap = new LinkedHashMap<String, String>();
	private Map<String, String> minutesMap = new LinkedHashMap<String, String>();
	private String accountOid = ""; // 編輯mode 用 , 放 TB_ACCOUNT.OID 
	private String calendarNoteDate = ""; // 編輯mode 的 TB_SYS_CALENDAR_NOTE.DATE
	private String startHour = ""; // 編輯mode 用
	private String startMinutes = ""; // 編輯mode 用
	private String endHour = ""; // 編輯mode 用
	private String endMinutes = ""; // 編輯mode 用
	private SysCalendarNoteVO sysCalendarNote = new SysCalendarNoteVO();   // 編輯mode 用
	
	public SystemCalendarNoteManagementAction() {
		super();
	}
	
	public IAccountService<AccountVO, TbAccount, String> getAccountService() {
		return accountService;
	}

	@Autowired
	@Resource(name="core.service.AccountService")	
	@Required
	public void setAccountService(
			IAccountService<AccountVO, TbAccount, String> accountService) {
		this.accountService = accountService;
	}

	public ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> getSysCalendarNoteService() {
		return sysCalendarNoteService;
	}

	@Autowired
	@Resource(name="core.service.SysCalendarNoteService")	
	@Required	
	public void setSysCalendarNoteService(
			ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService) {
		this.sysCalendarNoteService = sysCalendarNoteService;
	}

	private void init() throws ServiceException, Exception {
		this.accountMap = this.accountService.findForAllMap(true);
		for (int hour=0; hour<24; hour++) {
			String hourStr = StringUtils.leftPad(String.valueOf(hour), 2, "0");
			hourMap.put(hourStr, hourStr);
		}
		for (int mins=0; mins<60; mins++) {
			String minsStr = StringUtils.leftPad(String.valueOf(mins), 2, "0");
			minutesMap.put(minsStr, minsStr);			
		}
	}
	
	/**
	 * 載入編輯mode需要的資料
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void loadSysCalendarNoteData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysCalendarNote, new String[]{"oid"} );
		DefaultResult<SysCalendarNoteVO> result = this.sysCalendarNoteService.findObjectByOid(sysCalendarNote);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysCalendarNote = result.getValue();
		AccountVO account = new AccountVO();
		account.setAccount(sysCalendarNote.getAccount());
		DefaultResult<AccountVO> aResult = this.accountService.findByUK(account);
		if (aResult.getValue()==null) {
			throw new ServiceException(aResult.getSystemMessage().getValue());
		}
		account = aResult.getValue();
		this.accountOid = account.getOid();		
		this.calendarNoteDate = sysCalendarNote.getDate().substring(0, 4) + "-" 
				+ sysCalendarNote.getDate().substring(4, 6) + "-"
				+ sysCalendarNote.getDate().substring(6, 8);
		String time[] = this.sysCalendarNote.getTime().split(Constants.DATETIME_DELIMITER);
		this.startHour = time[0].substring(0, 2);
		this.startMinutes = time[0].substring(2, 4);
		this.endHour = time[1].substring(0, 2);
		this.endMinutes = time[1].substring(2, 4);
	}

	/**
	 * core.systemCalendarNoteManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004Q")
	public String execute() throws Exception {
		try {
			this.init();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;
	}	
	
	/**
	 * core.systemCalendarNoteCreateAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004A")
	public String create() throws Exception {
		try {
			this.init();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;
	}	
	
	/**
	 * core.systemCalendarNoteEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0004E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.init();
			this.loadSysCalendarNoteData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;		
	}

	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getAccountMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.accountMap);
		return accountMap;
	}
	
	public String getSelectReadonly() {
		if (YesNo.YES.equals(super.getIsSuperRole())) {
			return YesNo.NO;
		}
		return YesNo.YES;
	}
	
	public String getSelectValue() {
		if (YesNo.YES.equals(super.getIsSuperRole())) { // 管理者在查詢頁面育設是 please select
			return Constants.HTML_SELECT_NO_SELECT_ID;
		}		
		String value = Constants.HTML_SELECT_NO_SELECT_ID;
		for (Map.Entry<String, String> entry : this.accountMap.entrySet()) {
			if (entry.getValue().equals(super.getAccountId())) {
				value = entry.getKey(); // TB_ACCOUNT.OID
			}
		}
		return value;
	}

	public Map<String, String> getHourMap() {
		return hourMap;
	}

	public Map<String, String> getMinutesMap() {
		return minutesMap;
	}

	public String getCalendarNoteDate() {
		return calendarNoteDate;
	}

	public SysCalendarNoteVO getSysCalendarNote() {
		return sysCalendarNote;
	}

	public void setSysCalendarNote(SysCalendarNoteVO sysCalendarNote) {
		this.sysCalendarNote = sysCalendarNote;
	}

	public String getStartHour() {
		return startHour;
	}

	public String getStartMinutes() {
		return startMinutes;
	}

	public String getEndHour() {
		return endHour;
	}

	public String getEndMinutes() {		
		return endMinutes;
	}

	public String getAccountOid() {
		return accountOid;
	}
	
}
