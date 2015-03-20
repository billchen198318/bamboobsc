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
package com.netsteadfast.greenstep.service.logic.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.model.TemplateCode;
import com.netsteadfast.greenstep.model.TemplateResultObj;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.service.ISysMailHelperService;
import com.netsteadfast.greenstep.service.logic.ISystemCalendarNoteLogicService;
import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;
import com.netsteadfast.greenstep.vo.SysMailHelperVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemCalendarNoteLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemCalendarNoteLogicServiceImpl extends BaseLogicService implements ISystemCalendarNoteLogicService {
	protected Logger logger=Logger.getLogger(SystemCalendarNoteLogicServiceImpl.class);
	private static final int MAX_NOTE_LENGTH = 1000;
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService;
	private ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService;
	
	public SystemCalendarNoteLogicServiceImpl() {
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
	
	public ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> getSysMailHelperService() {
		return sysMailHelperService;
	}

	@Autowired
	@Resource(name="core.service.SysMailHelperService")
	@Required	
	public void setSysMailHelperService(
			ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService) {
		this.sysMailHelperService = sysMailHelperService;
	}

	private void createMailHelper(SysCalendarNoteVO note) {
		if (null==note || super.isBlank(note.getOid())) {
			return;
		}
		if (!YesNo.YES.equals(note.getAlert())) {
			return;
		}
		if (super.isBlank(note.getContact())) {
			return;
		}
		String receiveMail[] = note.getContact().trim().split(Constants.ID_DELIMITER);
		String mailId = SimpleUtils.getStrYMD("");
		for (String toMail : receiveMail) {
			try {
				TemplateResultObj result = TemplateUtils.getResult(TemplateCode.TPLMSG0001, note);
				String content = result.getContent();
				if (StringUtils.isBlank(content)) {
					content = note.getNote();
				}
				SysMailHelperVO mailHelper = new SysMailHelperVO();
				mailHelper.setMailId( this.sysMailHelperService.findForMaxMailIdComplete(mailId) );
				mailHelper.setMailFrom( MailClientUtils.getDefaultFrom() );
				mailHelper.setMailTo(toMail);
				mailHelper.setSubject( note.getTitle() );
				mailHelper.setText( content.getBytes("utf8") );
				mailHelper.setRetainFlag(YesNo.NO);
				mailHelper.setSuccessFlag(YesNo.NO);
				this.sysMailHelperService.saveObject(mailHelper);
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 產生 TB_SYS_CALENDAR_NOTE 資料
	 * 
	 * @param sysCalendarNote
	 * @param accountOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysCalendarNoteVO> create(SysCalendarNoteVO sysCalendarNote, String accountOid) throws ServiceException, Exception {
		if (super.isBlank(accountOid) || null==sysCalendarNote ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AccountVO account = new AccountVO();
		account.setOid(accountOid);
		DefaultResult<AccountVO> aResult = this.accountService.findObjectByOid(account);
		if (aResult.getValue()==null) {
			throw new ServiceException(aResult.getSystemMessage().getValue());
		}
		account = aResult.getValue();
		sysCalendarNote.setDate( sysCalendarNote.getDate().replaceAll("/", "").replaceAll("-", "") );
		sysCalendarNote.setAccount(account.getAccount());
		String canendarId = this.sysCalendarNoteService.findForMaxCalendarId(account.getAccount(), sysCalendarNote.getDate());
		if (StringUtils.isBlank(canendarId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		sysCalendarNote.setCalendarId(canendarId);		
		if ( super.defaultString(sysCalendarNote.getNote()).length() > MAX_NOTE_LENGTH ) {
			sysCalendarNote.setNote( sysCalendarNote.getNote().substring(0, MAX_NOTE_LENGTH) );			
		}
		DefaultResult<SysCalendarNoteVO> result = this.sysCalendarNoteService.saveObject(sysCalendarNote);
		this.createMailHelper(result.getValue());
		return result;
	}

	/**
	 * 更新 TB_SYS_CALENDAR_NOTE 資料
	 * 
	 * @param sysCalendarNote
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysCalendarNoteVO> update(SysCalendarNoteVO sysCalendarNote) throws ServiceException, Exception {
		if (sysCalendarNote==null || super.isBlank(sysCalendarNote.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysCalendarNoteVO> oldResult = this.sysCalendarNoteService.findObjectByOid(sysCalendarNote);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		sysCalendarNote.setAccount(oldResult.getValue().getAccount());
		sysCalendarNote.setCalendarId(oldResult.getValue().getCalendarId());	
		sysCalendarNote.setDate( sysCalendarNote.getDate().replaceAll("/", "").replaceAll("-", "") );
		if ( super.defaultString(sysCalendarNote.getNote()).length() > MAX_NOTE_LENGTH ) {
			sysCalendarNote.setNote( sysCalendarNote.getNote().substring(0, MAX_NOTE_LENGTH) );			
		}		
		DefaultResult<SysCalendarNoteVO> result = this.sysCalendarNoteService.updateObject(sysCalendarNote);
		this.createMailHelper(result.getValue());
		return result;
	}
	
}
