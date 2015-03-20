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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.IAggregationMethodDAO;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;

@Repository("bsc.dao.AggregationMethodDAO")
@Scope("prototype")
public class AggregationMethodDAOImpl extends BaseDAO<BbAggregationMethod, String> implements IAggregationMethodDAO<BbAggregationMethod, String> {
	
	public AggregationMethodDAOImpl() {
		super();
	}

	@Override
	public AggregationMethodVO findSimpleById(String aggrId) throws Exception {			
		return (AggregationMethodVO) this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.AggregationMethodVO(m.oid, m.aggrId, m.name, m.type) FROM BbAggregationMethod m WHERE m.aggrId = :aggrId ")
				.setString("aggrId", aggrId)
				.uniqueResult();
	}

	@Override
	public AggregationMethodVO findSimpleByOid(String aggrOid) throws Exception {
		return (AggregationMethodVO) this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.AggregationMethodVO(m.oid, m.aggrId, m.name, m.type) FROM BbAggregationMethod m WHERE m.oid = :aggrOid ")
				.setString("aggrOid", aggrOid)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AggregationMethodVO> findForSimple() throws Exception {		
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.AggregationMethodVO(m.oid, m.aggrId, m.name, m.type) FROM BbAggregationMethod m ORDER BY m.name ASC ")
				.list();
	}
	
}
