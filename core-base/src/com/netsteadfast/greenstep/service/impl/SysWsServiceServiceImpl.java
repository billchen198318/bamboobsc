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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysWsServiceDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysWsService;
import com.netsteadfast.greenstep.service.ISysWsServiceService;
import com.netsteadfast.greenstep.vo.SysWsServiceVO;

@Service("core.service.SysWsServiceService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysWsServiceServiceImpl extends BaseService<SysWsServiceVO, TbSysWsService, String> implements ISysWsServiceService<SysWsServiceVO, TbSysWsService, String> {
	protected Logger logger=Logger.getLogger(SysWsServiceServiceImpl.class);
	private ISysWsServiceDAO<TbSysWsService, String> sysWsServiceDAO;
	
	public SysWsServiceServiceImpl() {
		super();
	}

	public ISysWsServiceDAO<TbSysWsService, String> getSysWsServiceDAO() {
		return sysWsServiceDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysWsServiceDAO")
	@Required		
	public void setSysWsServiceDAO(
			ISysWsServiceDAO<TbSysWsService, String> sysWsServiceDAO) {
		this.sysWsServiceDAO = sysWsServiceDAO;
	}

	@Override
	protected IBaseDAO<TbSysWsService, String> getBaseDataAccessObject() {
		return sysWsServiceDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

}
