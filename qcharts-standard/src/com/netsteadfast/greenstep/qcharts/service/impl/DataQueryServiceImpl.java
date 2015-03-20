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
package com.netsteadfast.greenstep.qcharts.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.qcharts.dao.IDataQueryDAO;
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.vo.DataQueryVO;

@Service("qcharts.service.DataQueryService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DataQueryServiceImpl extends BaseService<DataQueryVO, QcDataQuery, String> implements IDataQueryService<DataQueryVO, QcDataQuery, String> {
	protected Logger logger=Logger.getLogger(DataQueryServiceImpl.class);
	private IDataQueryDAO<QcDataQuery, String> dataQueryDAO;
	
	public DataQueryServiceImpl() {
		super();
	}

	public IDataQueryDAO<QcDataQuery, String> getDataQueryDAO() {
		return dataQueryDAO;
	}

	@Autowired
	@Resource(name="qcharts.dao.DataQueryDAO")
	@Required		
	public void setDataQueryDAO(
			IDataQueryDAO<QcDataQuery, String> dataQueryDAO) {
		this.dataQueryDAO = dataQueryDAO;
	}

	@Override
	protected IBaseDAO<QcDataQuery, String> getBaseDataAccessObject() {
		return dataQueryDAO;
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
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<DataQueryVO> searchList = this.dataQueryDAO.findForSimple();
		if (searchList==null || searchList.size()<1) {
			return dataMap;
		}
		for (DataQueryVO query : searchList) {
			dataMap.put(query.getOid(), query.getName());
		}
		return dataMap;
	}

}
