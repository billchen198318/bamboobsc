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
import com.netsteadfast.greenstep.bsc.dao.IVisionDAO;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.VisionVO;

@Repository("bsc.dao.VisionDAO")
@Scope("prototype")
public class VisionDAOImpl extends BaseDAO<BbVision, String> implements IVisionDAO<BbVision, String> {
	
	public VisionDAOImpl() {
		super();
	}

	@Override
	public String findForMaxVisId(String visId) throws Exception {		
		return (String)this.getCurrentSession()
				.createQuery("SELECT max(m.visId) FROM BbVision m WHERE m.visId LIKE :visId ")
				.setString("visId", visId+"%")
				.uniqueResult();
	}

	@Override
	public VisionVO findForSimple(String oid) throws Exception {		
		return (VisionVO) this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.VisionVO(m.oid, m.visId, m.title) FROM BbVision m WHERE m.oid = :oid ")
				.setString("oid", oid)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisionVO> findForSimple() throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.VisionVO(m.oid, m.visId, m.title) FROM BbVision m ORDER BY m.visId ASC")
				.list();				
	}

	@Override
	public VisionVO findForSimpleByVisId(String visId) throws Exception {
		return (VisionVO) this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.VisionVO(m.oid, m.visId, m.title) FROM BbVision m WHERE m.visId = :visId ")
				.setString("visId", visId)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForOidByKpiOrga(String orgId) throws Exception {		
		return (List<String>)this.getCurrentSession()
				.createQuery(
						"select distinct v.oid from BbVision v, BbKpi k, BbKpiOrga ko, BbObjective o, BbPerspective p " + 
						"where k.id = ko.kpiId and k.objId = o.objId and o.perId = p.perId and p.visId = v.visId and ko.orgId = :orgId ")
				.setString("orgId", orgId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForOidByPdcaOid(String pdcaOid) throws Exception {
		return (List<String>)this.getCurrentSession()
				.createQuery(
						"select distinct v.oid from BbVision v, BbKpi k, BbObjective o, BbPerspective p " + 
						"where k.objId = o.objId and o.perId = p.perId and p.visId = v.visId and k.id in ( select pk.kpiId from BbPdcaKpis pk where pk.pdcaOid = :pdcaOid ) ")
				.setString("pdcaOid", pdcaOid)
				.list();
	}
	
}
