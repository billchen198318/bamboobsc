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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.ITsaMaCoefficientsService;
import com.netsteadfast.greenstep.bsc.service.ITsaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.ITsaService;
import com.netsteadfast.greenstep.bsc.service.logic.ITsaLogicService;
import com.netsteadfast.greenstep.po.hbm.BbTsa;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.po.hbm.BbTsaMeasureFreq;
import com.netsteadfast.greenstep.vo.TsaMaCoefficientsVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.TsaVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.TsaLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class TsaLogicServiceImpl extends BscBaseLogicService implements ITsaLogicService {
	protected Logger logger = Logger.getLogger(TsaLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;	
	private ITsaService<TsaVO, BbTsa, String> tsaService;
	private ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	private ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> TsaMaCoefficientsService;
	
	public TsaLogicServiceImpl() {
		super();
	}
	
	public ITsaService<TsaVO, BbTsa, String> getTsaService() {
		return tsaService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaService")
	@Required	
	public void setTsaService(ITsaService<TsaVO, BbTsa, String> tsaService) {
		this.tsaService = tsaService;
	}

	public ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> getTsaMeasureFreqService() {
		return tsaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMeasureFreqService")
	@Required		
	public void setTsaMeasureFreqService(ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService) {
		this.tsaMeasureFreqService = tsaMeasureFreqService;
	}

	public ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> getTsaMaCoefficientsService() {
		return TsaMaCoefficientsService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMaCoefficientsService")
	@Required			
	public void setTsaMaCoefficientsService(ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService) {
		TsaMaCoefficientsService = tsaMaCoefficientsService;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<TsaVO> create(TsaVO tsa, TsaMeasureFreqVO tsaMeasureFreq, TsaMaCoefficientsVO tsaMaCoefficients) throws ServiceException, Exception {
		
		return null;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<TsaVO> update(TsaVO tsa, TsaMeasureFreqVO tsaMeasureFreq, TsaMaCoefficientsVO tsaMaCoefficients) throws ServiceException, Exception {
		
		return null;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(TsaVO tsa) throws ServiceException, Exception {
		
		return null;
	}
	
}
