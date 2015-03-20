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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.Constants;
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
	
	private String getMixDataHql(String type, String visionOid, String orgId, String empId) {
		StringBuilder hql = new StringBuilder();
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hql.append("SELECT new com.netsteadfast.greenstep.bsc.vo.BscMixDataVO(");
			hql.append("	k.oid, k.id, k.name, k.description, k.weight, k.unit, k.target, k.min, ");
			hql.append("	k.management, k.compareType, k.cal, k.dataType, k.orgaMeasureSeparate, k.userMeasureSeparate, ");	
			hql.append("	k.quasiRange, ");
			hql.append("	o.oid, o.objId, o.name, o.weight, o.description, o.target, o.min, ");
			hql.append("	p.oid, p.perId, p.name, p.weight, p.description, p.target, p.min,  ");		
			hql.append("	v.oid, v.visId, v.title,  ");
			hql.append("	f.oid, f.forId, f.name, f.type, f.returnMode, f.returnVar, f.expression, ");
			hql.append("	aggr.oid, aggr.aggrId, aggr.name, aggr.type, aggr.expression1, aggr.expression2 ");
			hql.append(") ");			
		} else {
			hql.append("SELECT count(*) ");
		}
		hql.append("FROM BbKpi k, BbObjective o, BbPerspective p, BbVision v, BbFormula f, BbAggregationMethod aggr ");
		hql.append("WHERE k.objId = o.objId AND o.perId = p.perId AND p.visId = v.visId AND k.forId = f.forId AND k.cal = aggr.aggrId ");
		if (!StringUtils.isBlank(visionOid)) {
			hql.append("AND v.oid = :visionOid ");
		}
		if (!StringUtils.isBlank(orgId)) {
			hql.append("AND k.id IN ( SELECT b.kpiId FROM BbKpiOrga b WHERE b.orgId = :orgId ) ");
		}
		if (!StringUtils.isBlank(empId)) {
			hql.append("AND k.id IN ( SELECT b.kpiId FROM BbKpiEmpl b WHERE b.empId = :empId ) ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hql.append("ORDER BY v.visId, p.perId, o.objId, k.id ASC ");
		}
		return hql.toString();
	}
	
	private void setQueryMixDataParameter(Query query, String visionOid, String orgId, String empId) {
		if (!StringUtils.isBlank(visionOid)) {
			query.setString("visionOid", visionOid);
		}		
		if (!StringUtils.isBlank(orgId)) {
			query.setString("orgId", orgId);
		}	
		if (!StringUtils.isBlank(empId)) {
			query.setString("empId", empId);
		}			
	}

	/**
	 * select 
	 * k.OID, k.ID, k.NAME, k.DESCRIPTION, k.WEIGHT, k.UNIT, k.TARGET, k.MIN,
	 * k.MANAGEMENT, k.COMPARE_TYPE, k.CAL, k.DATA_TYPE, k.ORGA_MEASURE_SEPARATE, k.USER_MEASURE_SEPARATE,
	 * k.QUASI_RANGE,
	 * o.OID, o.OBJ_ID, o.NAME, o.WEIGHT, o.DESCRIPTION, o.TARGET, o.MIN,
	 * p.OID, p.PER_ID, p.NAME, p.WEIGHT, p.DESCRIPTION, p.TARGET, p.MIN,
	 * v.OID, v.VIS_ID, v.TITLE, 
	 * f.OID, f.FOR_ID, f.NAME, f.TYPE, f.RETURN_MODE, f.RETURN_VAR, f.EXPRESSION,
	 * aggr.OID, aggr.AGGR_ID, aggr.AGGR_NAME, aggr.TYPE, aggr.EXPRESSION1, aggr.EXPRESSION2
	 * from bb_kpi k, bb_objective o, bb_perspective p, bb_vision v, bb_formula f, bb_aggregation_method aggr
	 * where k.OBJ_ID = o.OBJ_ID
	 * and o.PER_ID = p.PER_ID
	 * and p.VIS_ID = v.VIS_ID
	 * and k.FOR_ID = f.FOR_ID
	 * and k.CAL = aggr.AGGR_ID
	 * ORDER BY v.VIS_ID, p.PER_ID, o.OBJ_ID, k.ID ASC
	 * ;
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BscMixDataVO> findForMixData(String visionOid, String orgId, String empId) throws Exception {
		Query query = this.getCurrentSession().createQuery( 
				this.getMixDataHql(Constants.QUERY_TYPE_OF_SELECT, visionOid, orgId, empId) );	
		this.setQueryMixDataParameter(query, visionOid, orgId, empId);
		return query.list();
	}

	@Override
	public int countForMixData(String visionOid, String orgId, String empId) throws Exception {	
		Query query = this.getCurrentSession().createQuery( 
				this.getMixDataHql(Constants.QUERY_TYPE_OF_COUNT, visionOid, orgId, empId) );
		this.setQueryMixDataParameter(query, visionOid, orgId, empId);
		return DataAccessUtils.intResult( query.list() );
	}
	
}
