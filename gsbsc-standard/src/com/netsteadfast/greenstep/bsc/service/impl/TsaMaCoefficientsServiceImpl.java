/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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
import com.netsteadfast.greenstep.bsc.dao.ITsaMaCoefficientsDAO;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.bsc.service.ITsaMaCoefficientsService;
import com.netsteadfast.greenstep.vo.TsaMaCoefficientsVO;

@Service("bsc.service.TsaMaCoefficientsService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class TsaMaCoefficientsServiceImpl extends BaseService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> implements ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> {
	protected Logger logger=Logger.getLogger(TsaMaCoefficientsServiceImpl.class);
	private ITsaMaCoefficientsDAO<BbTsaMaCoefficients, String> tsaMaCoefficientsDAO;
	
	public TsaMaCoefficientsServiceImpl() {
		super();
	}

	public ITsaMaCoefficientsDAO<BbTsaMaCoefficients, String> getTsaMaCoefficientsDAO() {
		return tsaMaCoefficientsDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.TsaMaCoefficientsDAO")
	@Required		
	public void setTsaMaCoefficientsDAO(
			ITsaMaCoefficientsDAO<BbTsaMaCoefficients, String> tsaMaCoefficientsDAO) {
		this.tsaMaCoefficientsDAO = tsaMaCoefficientsDAO;
	}

	@Override
	protected IBaseDAO<BbTsaMaCoefficients, String> getBaseDataAccessObject() {
		return tsaMaCoefficientsDAO;
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
