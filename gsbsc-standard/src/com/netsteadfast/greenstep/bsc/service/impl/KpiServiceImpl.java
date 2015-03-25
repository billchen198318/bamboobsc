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
		Map<String, Object> params=new LinkedHashMap<String, Object>();
		String visionOid = searchValue.getParameter().get("visionOid");
		String perspectiveOid = searchValue.getParameter().get("perspectiveOid");
		String objectiveOid = searchValue.getParameter().get("objectiveOid");
		String id = searchValue.getParameter().get("id");
		String name = searchValue.getParameter().get("name");
		if (!this.isNoSelectId(visionOid)) {
			params.put("visionOid", visionOid);
		}
		if (!this.isNoSelectId(perspectiveOid)) {
			params.put("perspectiveOid", perspectiveOid);
		}
		if (!this.isNoSelectId(objectiveOid)) {
			params.put("objectiveOid", objectiveOid);
		}		
		if (!StringUtils.isBlank(id)) {
			params.put("id", id);
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
			hqlSb.append("	new com.netsteadfast.greenstep.vo.KpiVO(m.oid, m.id, m.name, m.description, m.weight, v.title, p.name, o.name) ");
		}
		hqlSb.append("FROM BbKpi m, BbObjective o, BbPerspective p, BbVision v WHERE m.objId = o.objId AND o.perId = p.perId AND p.visId = v.visId ");		
		if (params.get("visionOid")!=null) {
			hqlSb.append(" AND v.oid = :visionOid ");			
		}
		if (params.get("perspectiveOid")!=null) {
			hqlSb.append(" AND p.oid = :perspectiveOid ");			
		}		
		if (params.get("objectiveOid")!=null) {
			hqlSb.append(" AND o.oid = :objectiveOid ");			
		}				
		if (params.get("id")!=null) {
			hqlSb.append(" AND m.id = :id ");
		}		
		if (params.get("name")!=null) {
			hqlSb.append(" AND m.name LIKE :name ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.id ASC ");
		}		
		return hqlSb.toString();
	}			

	@Override
	public QueryResult<List<KpiVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<KpiVO>> result=this.kpiDAO.findResult2(
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
	public DefaultResult<List<BscMixDataVO>> findForMixData(String visionOid, String orgId, String empId, String nextType, String nextId) throws ServiceException, Exception {
		DefaultResult<List<BscMixDataVO>> result = new DefaultResult<List<BscMixDataVO>>();
		List<BscMixDataVO> searchList = this.kpiDAO.findForMixData(visionOid, orgId, empId, nextType, nextId);
		if (null!=searchList && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public int countForMixData(String visionOid, String orgId, String empId, String nextType, String nextId) throws ServiceException, Exception {		
		return this.kpiDAO.countForMixData(visionOid, orgId, empId, nextType, nextId);
	}

}
