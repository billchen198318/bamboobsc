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
package com.netsteadfast.greenstep.bsc.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.IKpiDAO;
import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.po.hbm.BbKpi;

@Repository("bsc.dao.KpiDAO")
@Scope("prototype")
public class KpiDAOImpl extends BaseDAO<BbKpi, String> implements IKpiDAO<BbKpi, String> {
	
	public KpiDAOImpl() {
		super();
	}
	
	private void setQueryMixDataParameter(Query query, Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			if (entry.getValue()!=null && entry.getValue() instanceof String) {
				query.setString(entry.getKey(), String.valueOf(entry.getValue()));
			}
			if (entry.getValue()!=null && entry.getValue() instanceof List) {
				query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
			}
		}		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BscMixDataVO> findForMixData(Map<String, Object> paramMap) throws Exception {		
		Query query = this.getCurrentSession().createQuery(this.getDynamicHql("findKpiMixData-select", paramMap));
		this.setQueryMixDataParameter(query, paramMap);
		return query.list();
	}

	@Override
	public int countForMixData(Map<String, Object> paramMap) throws Exception {		
		Query query = this.getCurrentSession().createQuery(this.getDynamicHql("findKpiMixData-count", paramMap));
		this.setQueryMixDataParameter(query, paramMap);
		return DataAccessUtils.intResult( query.list() );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendNames(List<String> oids) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.name FROM BbKpi m WHERE m.oid IN (:oids) ")
				.setParameterList("oids", oids)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendOidsByPdcaKpis(String pdcaOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbKpi m WHERE m.id IN ( SELECT b.kpiId FROM BbPdcaKpis b WHERE b.pdcaOid = :pdcaOid ) ")
				.setString("pdcaOid", pdcaOid)
				.list();
	}
	
}
