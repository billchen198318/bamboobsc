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

import java.util.LinkedHashMap;
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

import com.netsteadfast.greenstep.base.Constants;
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
import com.netsteadfast.greenstep.bsc.dao.IFormulaDAO;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.vo.FormulaVO;

@Service("bsc.service.FormulaService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class FormulaServiceImpl extends BaseService<FormulaVO, BbFormula, String> implements IFormulaService<FormulaVO, BbFormula, String> {
	protected Logger logger=Logger.getLogger(FormulaServiceImpl.class);
	private IFormulaDAO<BbFormula, String> formulaDAO;
	
	public FormulaServiceImpl() {
		super();
	}

	public IFormulaDAO<BbFormula, String> getFormulaDAO() {
		return formulaDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.FormulaDAO")
	@Required		
	public void setFormulaDAO(
			IFormulaDAO<BbFormula, String> formulaDAO) {
		this.formulaDAO = formulaDAO;
	}

	@Override
	protected IBaseDAO<BbFormula, String> getBaseDataAccessObject() {
		return formulaDAO;
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
		Map<String, Object> params=new LinkedHashMap<String, Object>();
		String forId = searchValue.getParameter().get("forId");
		String name = searchValue.getParameter().get("name");
		if (!StringUtils.isBlank(forId)) {
			params.put("forId", forId);
		}		
		if (!StringUtils.isBlank(name)) {
			params.put("name", "%"+name+"%");
		}
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.FormulaVO(m.oid, m.forId, m.name, m.type, m.returnMode, m.returnVar, m.expression, m.description) ");
		}
		hqlSb.append("FROM BbFormula m WHERE 1=1 ");		
		if (params.get("forId")!=null) {
			hqlSb.append(" AND m.forId = :forId ");
		}		
		if (params.get("name")!=null) {
			hqlSb.append(" AND m.name LIKE :name ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.forId ASC ");
		}		
		return hqlSb.toString();
	}				
	
	@Override
	public QueryResult<List<FormulaVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<FormulaVO>> result=this.formulaDAO.findResult2(
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_SELECT, params), 
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_COUNT, params), 
				params, 
				offset, 
				limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public DefaultResult<List<FormulaVO>> findForSimple() throws ServiceException, Exception {
		DefaultResult<List<FormulaVO>> result = new DefaultResult<List<FormulaVO>>();
		List<FormulaVO> searchList = this.formulaDAO.findForSimple();
		if (searchList!=null && searchList.size() > 0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<FormulaVO> searchList = this.formulaDAO.findForSimple();
		for (int i=0; searchList!=null && i < searchList.size(); i++) {
			FormulaVO formula = searchList.get(i);
			dataMap.put(formula.getOid(), formula.getName());
		}
		return dataMap;
	}

}
