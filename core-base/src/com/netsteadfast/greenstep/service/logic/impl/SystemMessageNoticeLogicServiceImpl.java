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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.logic.ISystemMessageNoticeLogicService;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemMessageNoticeLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemMessageNoticeLogicServiceImpl extends BaseLogicService implements ISystemMessageNoticeLogicService {
	protected Logger logger=Logger.getLogger(SystemMessageNoticeLogicServiceImpl.class);
	private static final int MAX_MESSAGE_LENGTH = 1000;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService;
	private ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService;
	private IAccountService<AccountVO, TbAccount, String> accountService;
	
	public SystemMessageNoticeLogicServiceImpl() {
		super();
	}

	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required			
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
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

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysMsgNoticeConfigVO> createConfig(SysMsgNoticeConfigVO config, String systemOid)
			throws ServiceException, Exception {
		if (super.isBlank(systemOid) || config==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
		if (sysResult.getValue()==null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		config.setSystem(sys.getSysId());
		return this.sysMsgNoticeConfigService.saveObject(config);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysMsgNoticeConfigVO> updateConfig(SysMsgNoticeConfigVO config, String systemOid)
			throws ServiceException, Exception {
		if (config==null || super.isBlank(config.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
		if (sysResult.getValue()==null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();		
		DefaultResult<SysMsgNoticeConfigVO> oldResult = this.sysMsgNoticeConfigService.findObjectByOid(config);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		config.setMsgId( oldResult.getValue().getMsgId() );
		config.setSystem(sys.getSysId());
		return this.sysMsgNoticeConfigService.updateObject(config);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> deleteConfig(SysMsgNoticeConfigVO config) throws ServiceException, Exception {
		if (config==null || super.isBlank(config.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysMsgNoticeConfigVO> oldResult = this.sysMsgNoticeConfigService.findObjectByOid(config);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgId", oldResult.getValue().getMsgId());
		if (this.sysMsgNoticeService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		return this.sysMsgNoticeConfigService.deleteObject(config);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysMsgNoticeVO> create(SysMsgNoticeVO notice, String configOid, String accountOid) throws ServiceException, Exception {
		if (super.isBlank(configOid) || notice==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		SysMsgNoticeConfigVO config = new SysMsgNoticeConfigVO();
		config.setOid(configOid);
		DefaultResult<SysMsgNoticeConfigVO> configResult = this.sysMsgNoticeConfigService.findObjectByOid(config);
		if (configResult.getValue()==null) {
			throw new ServiceException(configResult.getSystemMessage().getValue());
		}
		config = configResult.getValue();	
		notice.setMsgId(config.getMsgId());
		notice.setToAccount("*");
		if (!super.isNoSelectId(accountOid) && !"*".equals(accountOid)) {
			AccountVO account = new AccountVO();
			account.setOid(accountOid);
			DefaultResult<AccountVO> aResult = this.accountService.findObjectByOid(account);
			if (aResult.getValue()==null) {
				throw new ServiceException(aResult.getSystemMessage().getValue());
			}
			account = aResult.getValue();				
			notice.setToAccount(account.getAccount());
		}		
		if (super.defaultString(notice.getMessage()).length() > MAX_MESSAGE_LENGTH) {
			notice.setMessage( notice.getMessage().substring(0, MAX_MESSAGE_LENGTH) );
		}
		return this.sysMsgNoticeService.saveObject(notice);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysMsgNoticeVO> update(SysMsgNoticeVO notice, String accountOid) throws ServiceException, Exception {
		if (notice==null || super.isBlank(notice.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<SysMsgNoticeVO> oldResult = this.sysMsgNoticeService.findObjectByOid(notice);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		notice.setNoticeId( oldResult.getValue().getNoticeId() );
		notice.setMsgId( oldResult.getValue().getMsgId() );
		notice.setToAccount("*");
		if (!super.isNoSelectId(accountOid) && !"*".equals(accountOid)) {
			AccountVO account = new AccountVO();
			account.setOid(accountOid);
			DefaultResult<AccountVO> aResult = this.accountService.findObjectByOid(account);
			if (aResult.getValue()==null) {
				throw new ServiceException(aResult.getSystemMessage().getValue());
			}
			account = aResult.getValue();				
			notice.setToAccount(account.getAccount());
		}				
		if (super.defaultString(notice.getMessage()).length() > MAX_MESSAGE_LENGTH) {
			notice.setMessage( notice.getMessage().substring(0, MAX_MESSAGE_LENGTH) );
		}		
		return this.sysMsgNoticeService.updateObject(notice);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysMsgNoticeVO notice) throws ServiceException, Exception {
		if (notice==null || super.isBlank(notice.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}	
		return this.sysMsgNoticeService.deleteObject(notice);
	}
	
	
}
