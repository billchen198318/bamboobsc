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

import java.util.List;
import java.util.Map;

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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IAggregationMethodDAO;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;

@Service("bsc.service.AggregationMethodService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AggregationMethodServiceImpl extends BaseService<AggregationMethodVO, BbAggregationMethod, String> implements IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> {
	protected Logger logger=Logger.getLogger(AggregationMethodServiceImpl.class);
	private IAggregationMethodDAO<BbAggregationMethod, String> aggregationMethodDAO;
	
	public AggregationMethodServiceImpl() {
		super();
	}

	public IAggregationMethodDAO<BbAggregationMethod, String> getAggregationMethodDAO() {
		return aggregationMethodDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.AggregationMethodDAO")
	@Required		
	public void setAggregationMethodDAO(
			IAggregationMethodDAO<BbAggregationMethod, String> aggregationMethodDAO) {
		this.aggregationMethodDAO = aggregationMethodDAO;
	}

	@Override
	protected IBaseDAO<BbAggregationMethod, String> getBaseDataAccessObject() {
		return aggregationMethodDAO;
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
	public DefaultResult<AggregationMethodVO> findSimpleById(String aggrId) throws ServiceException, Exception {
		if ( StringUtils.isBlank(aggrId) || super.isNoSelectId(aggrId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AggregationMethodVO aggr = this.aggregationMethodDAO.findSimpleById(aggrId);
		DefaultResult<AggregationMethodVO> result = new DefaultResult<AggregationMethodVO>();
		if ( aggr!=null && !StringUtils.isBlank(aggr.getOid()) ) {
			result.setValue(aggr);
		} else {
			result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) ) );
		}
		return result;
	}

	@Override
	public DefaultResult<AggregationMethodVO> findSimpleByOid(String aggrOid) throws ServiceException, Exception {
		if ( StringUtils.isBlank(aggrOid) || super.isNoSelectId(aggrOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		AggregationMethodVO aggr = this.aggregationMethodDAO.findSimpleByOid(aggrOid);
		DefaultResult<AggregationMethodVO> result = new DefaultResult<AggregationMethodVO>();
		if ( aggr!=null && !StringUtils.isBlank(aggr.getOid()) ) {
			result.setValue(aggr);
		} else {
			result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) ) );
		}
		return result;
	}

	@Override
	public DefaultResult<List<AggregationMethodVO>> findForSimple() throws ServiceException, Exception {
		DefaultResult<List<AggregationMethodVO>> result = new DefaultResult<List<AggregationMethodVO>>();
		List<AggregationMethodVO> searchList = this.aggregationMethodDAO.findForSimple();
		if ( searchList!=null && searchList.size() > 0 ) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) ) );
		}
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<AggregationMethodVO> searchList = this.aggregationMethodDAO.findForSimple();
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			AggregationMethodVO aggr = searchList.get(i);
			dataMap.put(aggr.getOid(), aggr.getName());
		}
		return dataMap;
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.fullEquals4TextField("aggrId")
				.containingLike("name")
				.getValue();
	}	

	@Override
	public QueryResult<List<AggregationMethodVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<AggregationMethodVO>> result=this.aggregationMethodDAO.findPageQueryResultByQueryName(
				"findAggregationMethodPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

}
