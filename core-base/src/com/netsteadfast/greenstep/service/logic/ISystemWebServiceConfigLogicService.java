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
package com.netsteadfast.greenstep.service.logic;

import javax.servlet.http.HttpServletRequest;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.SysWsConfigVO;

public interface ISystemWebServiceConfigLogicService {
	
	public DefaultResult<SysWsConfigVO> create(SysWsConfigVO config, String systemOid) throws ServiceException, Exception;
	
	public DefaultResult<SysWsConfigVO> update(SysWsConfigVO config, String systemOid) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(SysWsConfigVO config) throws ServiceException, Exception;
	
	public void stopOrReload(HttpServletRequest request, String system, String type) throws ServiceException, Exception;
	
	public void stopOrReload(HttpServletRequest request, String type) throws ServiceException, Exception;

}
