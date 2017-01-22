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
package com.netsteadfast.greenstep.bsc.service.logic;

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

public interface IEmployeeLogicService {
	
	public DefaultResult<EmployeeVO> create(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception;
	
	public DefaultResult<EmployeeVO> update(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception;		
	
	public DefaultResult<AccountVO> updatePassword(EmployeeVO employee, String newPassword) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(EmployeeVO employee) throws ServiceException, Exception;
	
	public List<Map<String, Object>> getTreeData(String basePath) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> updateSupervisor(EmployeeVO employee, String supervisorOid) throws ServiceException, Exception;
	
	public void initHierarchyForFirst() throws ServiceException, Exception;
	
	public DefaultResult<String> createOrgChartData(String basePath, EmployeeVO currentEmployee) throws ServiceException, Exception;
	
	public DefaultResult<Map<String, Object>> getOrgChartData(String basePath, EmployeeVO currentEmployee) throws ServiceException, Exception;
	
}
