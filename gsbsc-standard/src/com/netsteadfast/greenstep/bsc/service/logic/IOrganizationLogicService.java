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
import com.netsteadfast.greenstep.vo.OrganizationVO;

public interface IOrganizationLogicService {
	
	public DefaultResult<OrganizationVO> create(OrganizationVO organization) throws ServiceException, Exception;
	
	public DefaultResult<OrganizationVO> update(OrganizationVO organization) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(OrganizationVO organization) throws ServiceException, Exception;
	
	/**
	 * 為了組織架構 tree 的呈現用, json 資料
	 * 
	 * 不包含
	 * {
	 * 		"identifier":"id",
	 * 		"label":"name",
	 * 
	 * ==================================================
	 * 只包含 items 的資料內容
	 * ==================================================		
	 * 
	 * 		"items":[
	 * 			...............
	 * 		]
	 * ==================================================
	 * 
	 * }	
	 * 
	 * @param basePath
	 * @param checkBox		是否打開checkBox
	 * @param appendId		已被打勾的部門OID組成的字串, 如 1b2ac208-345c-4f93-92c5-4b26aead31d2;3ba52439-6756-45e8-8269-ae7b4fb6a3dc
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTreeData(String basePath, boolean checkBox, String appendId) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> updateParent(OrganizationVO organization, String parentOid) throws ServiceException, Exception;
	
	public DefaultResult<String> createOrgChartData(String basePath, OrganizationVO currentOrganization) throws ServiceException, Exception;
	
	public DefaultResult<Map<String, Object>> getOrgChartData(String basePath, OrganizationVO currentOrganization) throws ServiceException, Exception;
	
}
