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
package com.netsteadfast.greenstep.bsc.dao;

import java.util.List;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.vo.EmployeeVO;

public interface IEmployeeDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<BbEmployee, String> {
	
	public List<String> findForAppendNames(List<String> oids) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByKpiEmpl(String kpiId) throws Exception;
	
	public List<BbEmployee> findForInKpiEmpl(String kpiId) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByReportRoleViewEmpl(String roleId) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(String projectOid) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectRater(String projectOid) throws Exception;
	
	public T findByAccountOid(String oid) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByPdcaOwner(String pdcaOid) throws Exception;
	
	public List<String> findForAppendEmployeeOidsByPdcaItemOwner(String pdcaOid, String itemOid) throws Exception;
	
	public List<EmployeeVO> findForJoinHier() throws Exception;
	
}
