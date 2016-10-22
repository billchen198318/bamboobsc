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

import java.util.HashMap;
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
import com.netsteadfast.greenstep.qcharts.dao.IDataQueryMapperSetDAO;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapperSet;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperSetService;
import com.netsteadfast.greenstep.vo.DataQueryMapperSetVO;

@Service("qcharts.service.DataQueryMapperSetService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DataQueryMapperSetServiceImpl extends BaseService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> implements IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> {
	protected Logger logger=Logger.getLogger(DataQueryMapperSetServiceImpl.class);
	private IDataQueryMapperSetDAO<QcDataQueryMapperSet, String> dataQueryMapperSetDAO;
	
	public DataQueryMapperSetServiceImpl() {
		super();
	}

	public IDataQueryMapperSetDAO<QcDataQueryMapperSet, String> getDataQueryMapperSetDAO() {
		return dataQueryMapperSetDAO;
	}

	@Autowired
	@Resource(name="qcharts.dao.DataQueryMapperSetDAO")
	@Required		
	public void setDataQueryMapperSetDAO(
			IDataQueryMapperSetDAO<QcDataQueryMapperSet, String> dataQueryMapperSetDAO) {
		this.dataQueryMapperSetDAO = dataQueryMapperSetDAO;
	}

	@Override
	protected IBaseDAO<QcDataQueryMapperSet, String> getBaseDataAccessObject() {
		return dataQueryMapperSetDAO;
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
	public Map<String, String> findForMap(boolean pleaseSelect, String dataQueryMapperOid) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		if (super.isNoSelectId(dataQueryMapperOid)) {
			return dataMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", dataQueryMapperOid);
		List<QcDataQueryMapperSet> searchList = this.findListByParams(paramMap);
		for (QcDataQueryMapperSet entity : searchList) {
			dataMap.put(entity.getOid(), entity.getLabelField() + " - " + entity.getValueField() );
		}
		return dataMap;
	}

}
