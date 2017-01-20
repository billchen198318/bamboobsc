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
import com.netsteadfast.greenstep.bsc.dao.IEmployeeDAO;
import com.netsteadfast.greenstep.bsc.model.ReportRoleViewTypes;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@Repository("bsc.dao.EmployeeDAO")
@Scope("prototype")
public class EmployeeDAOImpl extends BaseDAO<BbEmployee, String> implements IEmployeeDAO<BbEmployee, String> {
	
	public EmployeeDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendNames(List<String> oids) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.fullName FROM BbEmployee m WHERE m.oid IN (:oids) ")
				.setParameterList("oids", oids)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByKpiEmpl(String kpiId) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.empId IN ( SELECT b.empId FROM BbKpiEmpl b WHERE b.kpiId = :kpiId ) ")
				.setString("kpiId", kpiId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbEmployee> findForInKpiEmpl(String kpiId) throws Exception {
		return this.getCurrentSession()
				.createQuery("FROM BbEmployee m WHERE m.empId IN ( SELECT b.empId FROM BbKpiEmpl b WHERE b.kpiId = :kpiId ) ")
				.setString("kpiId", kpiId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByReportRoleViewEmpl(String roleId) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.account IN ( SELECT b.idName FROM BbReportRoleView b WHERE b.role = :role and b.type = :type ) ")
				.setString("role", roleId)
				.setString("type", ReportRoleViewTypes.IS_EMPLOYEE)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(String projectOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.empId IN ( SELECT DISTINCT b.ownerId FROM BbDegreeFeedbackAssign b WHERE b.projectOid = :projectOid ) ")
				.setString("projectOid", projectOid)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectRater(String projectOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.empId IN ( SELECT DISTINCT b.raterId FROM BbDegreeFeedbackAssign b WHERE b.projectOid = :projectOid ) ")
				.setString("projectOid", projectOid)
				.list();
	}

	@Override
	public BbEmployee findByAccountOid(String accountOid) throws Exception {
		return (BbEmployee) this.getCurrentSession()
				.createQuery("FROM BbEmployee WHERE account IN ( SELECT b.account FROM TbAccount b WHERE b.oid = :oid ) ")
				.setString("oid", accountOid)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByPdcaOwner(String pdcaOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.empId IN ( SELECT b.empId FROM BbPdcaOwner b WHERE b.pdcaOid = :pdcaOid ) ")
				.setString("pdcaOid", pdcaOid)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findForAppendEmployeeOidsByPdcaItemOwner(String pdcaOid, String itemOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT m.oid FROM BbEmployee m WHERE m.empId IN ( SELECT b.empId FROM BbPdcaItemOwner b WHERE b.pdcaOid = :pdcaOid AND b.itemOid = :itemOid ) ")
				.setString("pdcaOid", pdcaOid)
				.setString("itemOid", itemOid)
				.list();
	}

	/**
	 * select m.OID, m.ACCOUNT, m.EMP_ID, m.FULL_NAME, m.JOB_TITLE, h.SUP_OID
	 * from bb_employee m, bb_employee_hier h 
	 * where m.OID = h.EMP_OID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeVO> findForJoinHier() throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.EmployeeVO(m.oid, m.account, m.empId, m.fullName, m.jobTitle, h.supOid) FROM BbEmployee m, BbEmployeeHier h WHERE m.oid = h.empOid")
				.list();
	}
	
}
