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

import java.util.ArrayList;
import java.util.HashMap;
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

import com.netsteadfast.greenstep.BscConstants;
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
import com.netsteadfast.greenstep.bsc.dao.IKpiDAO;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.vo.KpiVO;

@Service("bsc.service.KpiService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class KpiServiceImpl extends BaseService<KpiVO, BbKpi, String> implements IKpiService<KpiVO, BbKpi, String> {
	protected Logger logger=Logger.getLogger(KpiServiceImpl.class);
	private IKpiDAO<BbKpi, String> kpiDAO;
	
	public KpiServiceImpl() {
		super();
	}

	public IKpiDAO<BbKpi, String> getKpiDAO() {
		return kpiDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.KpiDAO")
	@Required		
	public void setKpiDAO(
			IKpiDAO<BbKpi, String> kpiDAO) {
		this.kpiDAO = kpiDAO;
	}

	@Override
	protected IBaseDAO<BbKpi, String> getBaseDataAccessObject() {
		return kpiDAO;
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
				.fullEquals4Select("visionOid")
				.fullEquals4Select("perspectiveOid")
				.fullEquals4Select("objectiveOid")
				.fullEquals4TextField("id")
				.containingLike("name")
				.getValue();
	}
	
	private Map<String, Object> getMixDataQueryParam(String visionOid, String orgId, String empId, String nextType, String nextId, List<String> kpiIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (!StringUtils.isBlank(visionOid)) {
			paramMap.put("visionOid", visionOid);
		}		
		if (!StringUtils.isBlank(orgId)) {
			paramMap.put("orgId", orgId);
		}	
		if (!StringUtils.isBlank(empId)) {
			paramMap.put("empId", empId);
		}			
		if (BscConstants.HEAD_FOR_PER_ID.equals(nextType) && !StringUtils.isBlank(nextId)) {
			paramMap.put("perId", nextId);
			//paramMap.put("nextType", nextType);
		}
		if (BscConstants.HEAD_FOR_OBJ_ID.equals(nextType) && !StringUtils.isBlank(nextId)) {
			paramMap.put("objId", nextId);
			//paramMap.put("nextType", nextType);
		}
		if (BscConstants.HEAD_FOR_KPI_ID.equals(nextType) && !StringUtils.isBlank(nextId)) {
			paramMap.put("kpiId", nextId);
			//paramMap.put("nextType", nextType);			
		}	
		if (kpiIds != null && kpiIds.size() > 0) {
			paramMap.put("kpiIds", kpiIds);
		}
		return paramMap;
	}	

	@Override
	public QueryResult<List<KpiVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<KpiVO>> result=this.kpiDAO.findPageQueryResultByQueryName(
				"findKpiPageGrid", pageOf.setQueryOrderSortParameter(params), offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public DefaultResult<List<BscMixDataVO>> findForMixData(String visionOid, String orgId, String empId, String nextType, String nextId, List<String> kpiIds) throws ServiceException, Exception {
		DefaultResult<List<BscMixDataVO>> result = new DefaultResult<List<BscMixDataVO>>();
		List<BscMixDataVO> searchList = this.kpiDAO.findForMixData(
				this.getMixDataQueryParam(visionOid, orgId, empId, nextType, nextId, kpiIds));
		if (null!=searchList && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public int countForMixData(String visionOid, String orgId, String empId, String nextType, String nextId, List<String> kpiIds) throws ServiceException, Exception {		
		return this.kpiDAO.countForMixData(this.getMixDataQueryParam(visionOid, orgId, empId, nextType, nextId, kpiIds));
	}
	
	@Override
	public List<String> findForAppendNames(List<String> oids) throws ServiceException, Exception {
		if (oids==null || oids.size()<1) {
			return new ArrayList<String>();
		}
		return this.kpiDAO.findForAppendNames(oids);
	}

	@Override
	public List<String> findForAppendOidsByPdcaKpis(String pdcaOid) throws Exception {
		if (StringUtils.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.kpiDAO.findForAppendOidsByPdcaKpis(pdcaOid);
	}

}
