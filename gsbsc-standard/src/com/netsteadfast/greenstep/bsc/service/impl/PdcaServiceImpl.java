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

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IPdcaDAO;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.vo.PdcaVO;

@Service("bsc.service.PdcaService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PdcaServiceImpl extends BaseService<PdcaVO, BbPdca, String> implements IPdcaService<PdcaVO, BbPdca, String> {
	protected Logger logger=Logger.getLogger(PdcaServiceImpl.class);
	private IPdcaDAO<BbPdca, String> pdcaDAO;
	
	public PdcaServiceImpl() {
		super();
	}

	public IPdcaDAO<BbPdca, String> getPdcaDAO() {
		return pdcaDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.PdcaDAO")
	@Required		
	public void setPdcaDAO(
			IPdcaDAO<BbPdca, String> pdcaDAO) {
		this.pdcaDAO = pdcaDAO;
	}

	@Override
	protected IBaseDAO<BbPdca, String> getBaseDataAccessObject() {
		return pdcaDAO;
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
		Map<String, Object> params = super.getQueryParamHandler(searchValue).containingLike("title").getValue();
		String startDate = super.defaultString(searchValue.getParameter().get("startDate")).trim().replaceAll("/", "");
		String endDate = super.defaultString(searchValue.getParameter().get("endDate")).trim().replaceAll("/", "");
		if (SimpleUtils.isDate(startDate) && SimpleUtils.isDate(endDate)) {
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		return params;
	}		

	@Override
	public QueryResult<List<PdcaVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<PdcaVO>> result=this.pdcaDAO.findPageQueryResultByQueryName(
				"findPdcaPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(true);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("title", "ASC");
		List<BbPdca> pdcaList = this.findListByParams(null, null, orderParams);
		for (int i=0; pdcaList!=null && i<pdcaList.size(); i++) {
			dataMap.put(pdcaList.get(i).getOid(), pdcaList.get(i).getTitle());
		}
		return dataMap;
	}

}
