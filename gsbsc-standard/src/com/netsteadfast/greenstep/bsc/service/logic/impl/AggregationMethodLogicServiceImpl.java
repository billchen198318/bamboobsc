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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

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
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.logic.IAggregationMethodLogicService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;
import com.netsteadfast.greenstep.vo.KpiVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.AggregationMethodLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AggregationMethodLogicServiceImpl extends CoreBaseLogicService implements IAggregationMethodLogicService {
	protected Logger logger=Logger.getLogger(AggregationMethodLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	public AggregationMethodLogicServiceImpl() {
		super();
	}
	
	public IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> getAggregationMethodService() {
		return aggregationMethodService;
	}

	@Autowired
	@Resource(name="bsc.service.AggregationMethodService")
	@Required		
	public void setAggregationMethodService(
			IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService) {
		this.aggregationMethodService = aggregationMethodService;
	}

	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")
	@Required			
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}		
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<AggregationMethodVO> create(AggregationMethodVO aggregationMethod) throws ServiceException, Exception {
		if (null==aggregationMethod || super.isBlank(aggregationMethod.getName())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", aggregationMethod.getName());
		if ( this.aggregationMethodService.countByParams(paramMap) > 0 ) {
			throw new ServiceException("Please change another name!");
		}
		this.setStringValueMaxLength(aggregationMethod, "description", MAX_DESCRIPTION_LENGTH);		
		return this.aggregationMethodService.saveObject(aggregationMethod);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<AggregationMethodVO> update(AggregationMethodVO aggregationMethod) throws ServiceException, Exception {
		if (null==aggregationMethod || super.isBlank(aggregationMethod.getName()) 
				|| super.isBlank(aggregationMethod.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AggregationMethodVO oldAggregationMethod = AggregationMethodUtils.findSimpleByOid(aggregationMethod.getOid());
		aggregationMethod.setAggrId( oldAggregationMethod.getAggrId() ); // ID 是 UK 不能改
		long countName1 = 0;
		long countName2 = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", aggregationMethod.getName());
		countName1 = this.aggregationMethodService.countByParams(paramMap);		
		paramMap.put("aggrId", aggregationMethod.getAggrId());
		countName2 = this.aggregationMethodService.countByParams(paramMap);
		if ( countName1 > 0 && countName2 == 0  ) { // 有別筆資料名稱是一樣的
			throw new ServiceException("Please change another name!");
		}		
		this.setStringValueMaxLength(aggregationMethod, "description", MAX_DESCRIPTION_LENGTH);
		return this.aggregationMethodService.updateObject(aggregationMethod);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(AggregationMethodVO aggregationMethod) throws ServiceException, Exception {
		if (null==aggregationMethod || super.isBlank(aggregationMethod.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AggregationMethodVO oldAggregationMethod = AggregationMethodUtils.findSimpleByOid(aggregationMethod.getOid());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cal", oldAggregationMethod.getAggrId());
		if ( this.kpiService.countByParams(paramMap) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		return this.aggregationMethodService.deleteObject(aggregationMethod);
	}

}
