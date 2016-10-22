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
package com.netsteadfast.greenstep.bsc.service.impl;

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
import com.netsteadfast.greenstep.bsc.dao.IStrategyMapDAO;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMap;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapService;
import com.netsteadfast.greenstep.vo.StrategyMapVO;

@Service("bsc.service.StrategyMapService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class StrategyMapServiceImpl extends BaseService<StrategyMapVO, BbStrategyMap, String> implements IStrategyMapService<StrategyMapVO, BbStrategyMap, String> {
	protected Logger logger=Logger.getLogger(StrategyMapServiceImpl.class);
	private IStrategyMapDAO<BbStrategyMap, String> strategyMapDAO;
	
	public StrategyMapServiceImpl() {
		super();
	}

	public IStrategyMapDAO<BbStrategyMap, String> getStrategyMapDAO() {
		return strategyMapDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.StrategyMapDAO")
	@Required		
	public void setStrategyMapDAO(
			IStrategyMapDAO<BbStrategyMap, String> strategyMapDAO) {
		this.strategyMapDAO = strategyMapDAO;
	}

	@Override
	protected IBaseDAO<BbStrategyMap, String> getBaseDataAccessObject() {
		return strategyMapDAO;
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
