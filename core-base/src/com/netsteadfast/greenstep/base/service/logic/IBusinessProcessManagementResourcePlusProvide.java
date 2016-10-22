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

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;

/**
 * 提供直接查出這個Task 與 Task變數, 與是否有權限 assigne 這個Task
 *
 */
public interface IBusinessProcessManagementResourcePlusProvide {
	
	public List<BusinessProcessManagementTaskVO> queryTaskPlus() throws ServiceException, Exception;
	
	public List<BusinessProcessManagementTaskVO> queryTaskPlus(String variableKeyName, String variableKeyValue) throws ServiceException, Exception;
	
}
