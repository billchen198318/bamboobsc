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
	
}
