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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysExpressionDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@Service("core.service.SysExpressionService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysExpressionServiceImpl extends BaseService<SysExpressionVO, TbSysExpression, String> implements ISysExpressionService<SysExpressionVO, TbSysExpression, String> {
	protected Logger logger=Logger.getLogger(SysExpressionServiceImpl.class);
	private ISysExpressionDAO<TbSysExpression, String> sysExpressionDAO;
	
	public SysExpressionServiceImpl() {
		super();
	}

	public ISysExpressionDAO<TbSysExpression, String> getSysExpressionDAO() {
		return sysExpressionDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysExpressionDAO")
	@Required		
	public void setSysExpressionDAO(
			ISysExpressionDAO<TbSysExpression, String> sysExpressionDAO) {
		this.sysExpressionDAO = sysExpressionDAO;
	}

	@Override
	protected IBaseDAO<TbSysExpression, String> getBaseDataAccessObject() {
		return sysExpressionDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.fullEquals4TextField("exprId")
				.containingLike("name")
				.fullEquals4Select("type")
				.getValue();
	}
	
	@Override
	public QueryResult<List<SysExpressionVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysExpressionVO>> result=this.sysExpressionDAO.findPageQueryResultByQueryName(
				"findSysExpressionPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public Map<String, String> findExpressionMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		List<SysExpressionVO> searchList = this.sysExpressionDAO.findListForSimple();
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			SysExpressionVO expression = searchList.get(i);
			dataMap.put(expression.getOid(), expression.getExprId() + " - " + expression.getName());
		}
		return dataMap;
	}

	// 2015-04-10 add
	@Cacheable(value="default", key="#sysExpression.exprId")
	@Override
	public DefaultResult<SysExpressionVO> findByUkCacheable(
			SysExpressionVO sysExpression) throws ServiceException, Exception {
		return this.findByUK(sysExpression);
	}
	
}
