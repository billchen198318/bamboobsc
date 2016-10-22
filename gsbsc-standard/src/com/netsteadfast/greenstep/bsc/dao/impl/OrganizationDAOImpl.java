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

import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.IOrganizationDAO;
import com.netsteadfast.greenstep.bsc.model.ReportRoleViewTypes;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@Repository("bsc.dao.OrganizationDAO")
public class OrganizationDAOImpl extends BaseDAO<BbOrganization, String> implements IOrganizationDAO<BbOrganization, String> {
	
	public OrganizationDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationVO> findForJoinParent() throws Exception {		
		return this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.OrganizationVO(m.oid, m.orgId, m.name, p.parId) FROM BbOrganization m, BbOrganizationPar p WHERE m.orgId = p.orgId ")
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendNames(List<String> oids) throws Exception {		
		return this.getCurrentSession()
				.createQuery("SELECT m.name FROM BbOrganization m WHERE m.oid IN (:oids) ")
				.setParameterList("oids", oids)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendOrganizationOids(String empId) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbOrganization m WHERE m.orgId IN ( SELECT b.orgId FROM BbEmployeeOrga b WHERE b.empId = :empId ) ")
				.setString("empId", empId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendOrganizationOidsByKpiOrga(String kpiId) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbOrganization m WHERE m.orgId IN ( SELECT b.orgId FROM BbKpiOrga b WHERE b.kpiId = :kpiId ) ")
				.setString("kpiId", kpiId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbOrganization> findForInKpiOrga(String kpiId) throws Exception {		
		return this.getCurrentSession()
				.createQuery("FROM BbOrganization m WHERE m.orgId IN ( SELECT b.orgId FROM BbKpiOrga b WHERE b.kpiId = :kpiId ) ")
				.setString("kpiId", kpiId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendOrganizationOidsByReportRoleViewOrga(String roleId) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbOrganization m WHERE m.orgId IN ( SELECT b.idName FROM BbReportRoleView b WHERE b.role = :role and b.type = :type ) ")
				.setString("role", roleId)
				.setString("type", ReportRoleViewTypes.IS_ORGANIZATION)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendOrganizationOidsByPdcaOrga(String pdcaOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbOrganization m WHERE m.orgId IN ( SELECT b.orgId FROM BbPdcaOrga b WHERE b.pdcaOid = :pdcaOid ) ")
				.setString("pdcaOid", pdcaOid)
				.list();
	}
	
}
