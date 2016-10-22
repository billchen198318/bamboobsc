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
package com.netsteadfast.greenstep.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.IAccountDAO;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.AccountVO;

@Service("core.service.AccountService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AccountServiceImpl extends BaseService<AccountVO, TbAccount, String> implements IAccountService<AccountVO, TbAccount, String> {
	protected Logger logger=Logger.getLogger(AccountServiceImpl.class);
	private IAccountDAO<TbAccount, String> accountDAO;
	
	public AccountServiceImpl() {
		super();
	}

	public IAccountDAO<TbAccount, String> getAccountDAO() {
		return accountDAO;
	}

	@Autowired
	@Resource(name="core.dao.AccountDAO")
	@Required		
	public void setAccountDAO(
			IAccountDAO<TbAccount, String> accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	protected IBaseDAO<TbAccount, String> getBaseDataAccessObject() {
		return accountDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public List<AccountVO> findForAll() throws ServiceException, Exception {
		return this.accountDAO.findForAll();
	}

	/**
	 * 下拉Select 要用
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public Map<String, String> findForAllMap(boolean pleaseSelect) throws ServiceException, Exception {
		List<AccountVO> searchList = this.findForAll();
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (searchList==null || searchList.size()<1) {
			return dataMap;
		}
		for (AccountVO account : searchList) {
			dataMap.put(account.getOid(), account.getAccount());
		}
		return dataMap;
	}

	@Override
	public String tranPassword(String password) throws Exception {
		return SimpleUtils.toMD5Hex( SimpleUtils.toB64(password) );
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public String generateNewPassword(String account) throws ServiceException, Exception {		
		if (StringUtils.isBlank(account)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		TbAccount entity = new TbAccount();
		entity.setAccount(account);
		entity = this.findByEntityUK(entity);
		if (entity==null || StringUtils.isBlank(entity.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST));
		}
		String newPasswordStr = SimpleUtils.createRandomString(5); 
		entity.setPassword( this.tranPassword(newPasswordStr) );
		this.update(entity);
		return newPasswordStr;
	}		

}
