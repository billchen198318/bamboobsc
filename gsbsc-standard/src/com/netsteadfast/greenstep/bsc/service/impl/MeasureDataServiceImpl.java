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

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IMeasureDataDAO;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.vo.MeasureDataVO;

@Service("bsc.service.MeasureDataService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class MeasureDataServiceImpl extends BaseService<MeasureDataVO, BbMeasureData, String> implements IMeasureDataService<MeasureDataVO, BbMeasureData, String> {
	protected Logger logger=Logger.getLogger(MeasureDataServiceImpl.class);
	private IMeasureDataDAO<BbMeasureData, String> measureDataDAO;
	
	public MeasureDataServiceImpl() {
		super();
	}

	public IMeasureDataDAO<BbMeasureData, String> getMeasureDataDAO() {
		return measureDataDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.MeasureDataDAO")
	@Required		
	public void setMeasureDataDAO(
			IMeasureDataDAO<BbMeasureData, String> measureDataDAO) {
		this.measureDataDAO = measureDataDAO;
	}

	@Override
	protected IBaseDAO<BbMeasureData, String> getBaseDataAccessObject() {
		return measureDataDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public int deleteForKpiId(String kpiId) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.measureDataDAO.deleteForKpiId(kpiId);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public int deleteForEmpId(String empId) throws ServiceException, Exception {
		if (StringUtils.isBlank(empId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.measureDataDAO.deleteForEmpId(empId);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public int deleteForOrgId(String orgId) throws ServiceException, Exception {
		if (StringUtils.isBlank(orgId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.measureDataDAO.deleteForOrgId(orgId);
	}

}
