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

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysFormVO;

public interface ISystemFormLogicService {
	
	public DefaultResult<SysFormTemplateVO> createTmplate(SysFormTemplateVO template, String uploadOid) throws ServiceException, Exception; 

	public DefaultResult<SysFormTemplateVO> updateTemplate(SysFormTemplateVO template, String uploadOid) throws ServiceException, Exception;
	
	public DefaultResult<SysFormTemplateVO> updateTemplateContentOnly(SysFormTemplateVO template, String content) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> deleteTemplate(SysFormTemplateVO template) throws ServiceException, Exception;
	
	public DefaultResult<SysFormVO> create(SysFormVO form, String templateOid) throws ServiceException, Exception;
	
	public DefaultResult<SysFormVO> update(SysFormVO form, String templateOid) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(SysFormVO form) throws ServiceException, Exception;
	
	public DefaultResult<SysFormMethodVO> createMethod(SysFormMethodVO formMethod, String formOid) throws ServiceException, Exception;
	
	public DefaultResult<SysFormMethodVO> updateMethod(SysFormMethodVO formMethod, String formOid) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> deleteMethod(SysFormMethodVO formMethod) throws ServiceException, Exception;
	
	public DefaultResult<SysFormMethodVO> updateMethodExpressionOnly(SysFormMethodVO formMethod, String expression) throws ServiceException, Exception;
	
}
