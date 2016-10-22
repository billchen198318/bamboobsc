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
package com.netsteadfast.greenstep.base.service.logic;

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.BaseValueObj;

public interface IBusinessProcessManagementResourceProvide<T extends BaseValueObj, TASK extends Object> {
	
	public String getBusinessProcessManagementResourceId();
	
	public T getBusinessProcessManagementResourceObject() throws ServiceException, Exception;
	
	public String startProcess(Map<String, Object> paramMap) throws Exception;
	
	public void completeTask(String taskId, Map<String, Object> paramMap) throws Exception;	
	
	public List<TASK> queryTask() throws Exception;
	
}
