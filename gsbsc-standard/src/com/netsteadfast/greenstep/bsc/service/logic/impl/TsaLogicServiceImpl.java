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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
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
	private static final float MIN_MC_COEFFICIENT_VALUE = -1.00f;
	private static final float MAX_MC_COEFFICIENT_VALUE = 1.00f;
	private static final int MIN_INTEGRATION_ORDER = 1;
	private static final int MAX_INTEGRATION_ORDER = 5;
	private static final int MIN_FORECAST_NEXT = 1;
	private static final int MAX_FORECAST_NEXT = 6;
	private ITsaService<TsaVO, BbTsa, String> tsaService;
	private ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	private ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService;
	
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
		return tsaMaCoefficientsService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMaCoefficientsService")
	@Required			
	public void setTsaMaCoefficientsService(ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService) {
		this.tsaMaCoefficientsService = tsaMaCoefficientsService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<TsaVO> create(TsaVO tsa, TsaMeasureFreqVO tsaMeasureFreq, TsaMaCoefficientsVO coefficient1, TsaMaCoefficientsVO coefficient2, TsaMaCoefficientsVO coefficient3) throws ServiceException, Exception {
		if (null == tsa || null == tsaMeasureFreq || null == coefficient1 || null == coefficient2 || null == coefficient3) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(tsa, "description", MAX_DESCRIPTION_LENGTH);
		this.checkTsaParamValue(tsa);
		DefaultResult<TsaVO> result = this.tsaService.saveObject(tsa);
		if (null == result.getValue()) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		tsa = result.getValue();
		this.createMeasureFreq(tsa, tsaMeasureFreq);
		this.createMaCoefficients(tsa, coefficient1, coefficient2, coefficient3);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<TsaVO> update(TsaVO tsa, TsaMeasureFreqVO tsaMeasureFreq, TsaMaCoefficientsVO coefficient1, TsaMaCoefficientsVO coefficient2, TsaMaCoefficientsVO coefficient3) throws ServiceException, Exception {
		if (null == tsa || super.isBlank(tsa.getOid()) || null == tsaMeasureFreq || null == coefficient1 || null == coefficient2 || null == coefficient3) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<TsaVO> oldResult = this.tsaService.findObjectByOid(tsa);
		if (oldResult.getValue() == null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		if ( oldResult.getValue().getName().equals(tsa.getName()) && !(tsa.getOid().equals(oldResult.getValue().getOid())) ) {
			throw new ServiceException( "Same name is found: " + tsa.getName() );
		}
		this.setStringValueMaxLength(tsa, "description", MAX_DESCRIPTION_LENGTH);
		this.checkTsaParamValue(tsa);
		DefaultResult<TsaVO> result = this.tsaService.updateObject(tsa);
		if (result.getValue() == null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		tsa = result.getValue();
		this.deleteMeasureFreq(tsa);
		this.deleteMaCoefficients(tsa);
		this.createMeasureFreq(tsa, tsaMeasureFreq);
		this.createMaCoefficients(tsa, coefficient1, coefficient2, coefficient3);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(TsaVO tsa) throws ServiceException, Exception {
		if (null == tsa || super.isBlank(tsa.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.deleteMeasureFreq(tsa);
		this.deleteMaCoefficients(tsa);		
		return this.tsaService.deleteObject(tsa);
	}
	
	private void createMeasureFreq(TsaVO tsa, TsaMeasureFreqVO measureFreq) throws ServiceException, Exception {
		measureFreq.setTsaOid( tsa.getOid() );
		this.replaceSplit2Blank(measureFreq, "startDate", "/");
		this.replaceSplit2Blank(measureFreq, "endDate", "/");
		if (this.isNoSelectId(measureFreq.getOrganizationOid())) {
			measureFreq.setOrgId(BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		} else {
			measureFreq.setOrgId( this.findOrganizationData(measureFreq.getOrganizationOid()).getOrgId() );
		}
		if (this.isNoSelectId(measureFreq.getEmployeeOid())) {
			measureFreq.setEmpId(BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		} else {
			measureFreq.setEmpId( this.findEmployeeData(measureFreq.getEmployeeOid()).getEmpId() );
		}	
		this.tsaMeasureFreqService.saveObject(measureFreq);
	}
	
	private void createMaCoefficients(TsaVO tsa, TsaMaCoefficientsVO coefficient1, TsaMaCoefficientsVO coefficient2, TsaMaCoefficientsVO coefficient3) throws ServiceException, Exception {
		coefficient1.setTsaOid( tsa.getOid() );
		coefficient1.setSeq(1);
		coefficient2.setTsaOid( tsa.getOid() );
		coefficient2.setSeq(2);
		coefficient3.setTsaOid( tsa.getOid() );
		coefficient3.setSeq(3);
		this.checkMcCoefficientValue(coefficient1);
		this.checkMcCoefficientValue(coefficient2);
		this.checkMcCoefficientValue(coefficient3);
		this.tsaMaCoefficientsService.saveObject(coefficient1);
		this.tsaMaCoefficientsService.saveObject(coefficient2);
		this.tsaMaCoefficientsService.saveObject(coefficient3);
	}
	
	private void deleteMeasureFreq(TsaVO tsa) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tsaOid", tsa.getOid());
		List<BbTsaMeasureFreq> measureFreqList = this.tsaMeasureFreqService.findListByParams(paramMap);
		for (BbTsaMeasureFreq measureFreq : measureFreqList) {
			this.tsaMeasureFreqService.delete(measureFreq);
		}
	}
	
	private void deleteMaCoefficients(TsaVO tsa) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tsaOid", tsa.getOid());
		List<BbTsaMaCoefficients> maCoefficientsList = this.tsaMaCoefficientsService.findListByParams(paramMap);
		for (BbTsaMaCoefficients maCoefficients : maCoefficientsList) {
			this.tsaMaCoefficientsService.delete(maCoefficients);
		}
	}
	
	private void checkTsaParamValue(TsaVO tsa) throws Exception {
		if (tsa.getIntegrationOrder() > MAX_INTEGRATION_ORDER) {
			tsa.setIntegrationOrder( MAX_INTEGRATION_ORDER );
		}
		if (tsa.getIntegrationOrder() < MIN_INTEGRATION_ORDER) {
			tsa.setIntegrationOrder( MIN_INTEGRATION_ORDER );
		}
		if (tsa.getForecastNext() > MAX_FORECAST_NEXT) {
			tsa.setForecastNext( MAX_FORECAST_NEXT );
		}
		if (tsa.getForecastNext() < MIN_FORECAST_NEXT) {
			tsa.setForecastNext( MIN_FORECAST_NEXT );
		}
	}
	
	private void checkMcCoefficientValue(TsaMaCoefficientsVO coefficient) throws Exception {
		if (coefficient.getSeqValue() > MAX_MC_COEFFICIENT_VALUE) {
			coefficient.setSeqValue( MAX_MC_COEFFICIENT_VALUE );
		}
		if (coefficient.getSeqValue() < MIN_MC_COEFFICIENT_VALUE) {
			coefficient.setSeqValue( MIN_MC_COEFFICIENT_VALUE );
		}
	}
	
}
