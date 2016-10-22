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

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysMenuDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysMenu;
import com.netsteadfast.greenstep.service.ISysMenuService;
import com.netsteadfast.greenstep.vo.SysMenuVO;

@Service("core.service.SysMenuService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysMenuServiceImpl extends BaseService<SysMenuVO, TbSysMenu, String> implements ISysMenuService<SysMenuVO, TbSysMenu, String> {
	protected Logger logger=Logger.getLogger(SysMenuServiceImpl.class);
	private ISysMenuDAO<TbSysMenu, String> sysMenuDAO;
	
	public SysMenuServiceImpl() {
		super();
	}

	public ISysMenuDAO<TbSysMenu, String> getSysMenuDAO() {
		return sysMenuDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysMenuDAO")
	@Required		
	public void setSysMenuDAO(
			ISysMenuDAO<TbSysMenu, String> sysMenuDAO) {
		this.sysMenuDAO = sysMenuDAO;
	}

	@Override
	protected IBaseDAO<TbSysMenu, String> getBaseDataAccessObject() {
		return sysMenuDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	/**
	 * 找此帳戶有的選單 , 如果是 super 就把 account 帶入空白
	 * 
	 * @param progSystem
	 * @param account
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public DefaultResult<List<SysMenuVO>> findForMenuGenerator(String progSystem, String account) throws ServiceException, Exception {
		
		if (StringUtils.isBlank(progSystem)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<List<SysMenuVO>> result = new DefaultResult<List<SysMenuVO>>();
		List<SysMenuVO> searchList = this.sysMenuDAO.findForMenuGenerator(progSystem, account);
		if (searchList!=null && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}
		return result;
	}

}
