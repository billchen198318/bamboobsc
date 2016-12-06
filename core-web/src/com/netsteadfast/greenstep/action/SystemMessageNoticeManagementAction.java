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
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMessageNoticeManagementAction")
@Scope
public class SystemMessageNoticeManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -3027114769183941371L;
	protected Logger logger=Logger.getLogger(SystemMessageNoticeManagementAction.class);
	private ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService;
	private ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService;
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private Map<String, String> msgDataMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> accountMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> hourMap = new LinkedHashMap<String, String>();
	private Map<String, String> minutesMap = new LinkedHashMap<String, String>();
	private SysMsgNoticeVO sysMsgNotice = new SysMsgNoticeVO(); // edit 模式用
	private String selectConfigOid = ""; // edit 模式用
	private String selectAccountOid = ""; // edit 模式用
	private String date1 = ""; // edit 模式用
	private String date2 = ""; // edit 模式用
	private String startHour = ""; // edit 模式用
	private String startMinutes = ""; // edit 模式用
	private String endHour = ""; // edit 模式用
	private String endMinutes = ""; // edit 模式用
	
	public SystemMessageNoticeManagementAction() {
		super();
	}
	
	public ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> getSysMsgNoticeConfigService() {
		return sysMsgNoticeConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeConfigService")
	@Required				
	public void setSysMsgNoticeConfigService(
			ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService) {
		this.sysMsgNoticeConfigService = sysMsgNoticeConfigService;
	}

	public ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> getSysMsgNoticeService() {
		return sysMsgNoticeService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeService")
	@Required				
	public void setSysMsgNoticeService(
			ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService) {
		this.sysMsgNoticeService = sysMsgNoticeService;
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
	
	private void initData() throws ServiceException, Exception {
		this.msgDataMap = this.sysMsgNoticeConfigService.findForDataMap(true);
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
	
	private void loadSysMsgNoticeData() throws ServiceException, Exception {		
		this.transformFields2ValueObject(this.sysMsgNotice, new String[]{"oid"});
		DefaultResult<SysMsgNoticeVO> result = this.sysMsgNoticeService.findObjectByOid(this.sysMsgNotice);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysMsgNotice = result.getValue();	
		String dateTmp[] = sysMsgNotice.getDate().split(Constants.DATETIME_DELIMITER);
		this.date1 = dateTmp[0].substring(0, 4) + "-" + dateTmp[0].substring(4, 6) + "-" + dateTmp[0].substring(6, 8);
		this.date2 = dateTmp[1].substring(0, 4) + "-" + dateTmp[1].substring(4, 6) + "-" + dateTmp[1].substring(6, 8);
		String time[] = sysMsgNotice.getTime().split(Constants.DATETIME_DELIMITER);
		this.startHour = time[0].substring(0, 2);
		this.startMinutes = time[0].substring(2, 4);
		this.endHour = time[1].substring(0, 2);
		this.endMinutes = time[1].substring(2, 4);
		
		SysMsgNoticeConfigVO config = new SysMsgNoticeConfigVO();
		config.setMsgId(sysMsgNotice.getMsgId());
		DefaultResult<SysMsgNoticeConfigVO> configResult = this.sysMsgNoticeConfigService.findByUK(config);
		if (configResult.getValue()==null) {
			throw new ServiceException(configResult.getSystemMessage().getValue());
		}
		config = configResult.getValue();
		this.selectConfigOid = config.getOid();
		
		AccountVO account = new AccountVO();
		if (!"*".equals(sysMsgNotice.getToAccount())) {
			account.setAccount(sysMsgNotice.getToAccount());
			DefaultResult<AccountVO> aResult = this.accountService.findByUK(account);
			if (aResult.getValue()==null) {
				throw new ServiceException(aResult.getSystemMessage().getValue());
			}
			account = aResult.getValue();
			this.selectAccountOid = account.getOid();
		}
		
	}

	/**
	 * core.systemMessageNoticeManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006Q")
	public String execute() throws Exception {
		try {
			this.initData();
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
	 * core.systemMessageNoticeCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006A")	
	public String create() throws Exception {
		try {
			this.initData();
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
	 * core.systemMessageNoticeEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0006E")		
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysMsgNoticeData();
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

	public Map<String, String> getMsgDataMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.msgDataMap);
		return msgDataMap;
	}

	public Map<String, String> getAccountMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.accountMap);
		return accountMap;
	}

	public Map<String, String> getHourMap() {
		return hourMap;
	}

	public Map<String, String> getMinutesMap() {
		return minutesMap;
	}

	public SysMsgNoticeVO getSysMsgNotice() {
		return sysMsgNotice;
	}

	public String getSelectConfigOid() {
		return selectConfigOid;
	}

	public String getSelectAccountOid() {
		return selectAccountOid;
	}

	public String getDate1() {
		return date1;
	}

	public String getDate2() {
		return date2;
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

}
