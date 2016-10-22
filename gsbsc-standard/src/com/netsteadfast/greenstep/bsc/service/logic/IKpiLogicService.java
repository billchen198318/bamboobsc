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

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.KpiVO;

public interface IKpiLogicService {
	
	public DefaultResult<KpiVO> create(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids, String trendsFormulaOid, List<String> attachment) throws ServiceException, Exception;
	
	public DefaultResult<KpiVO> update(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids, String trendsFormulaOid, List<String> attachment) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(KpiVO kpi) throws ServiceException, Exception;
	
	/**
	 * for TEST
	 * 這是測試 WS REST 用的  metod , 暴露 KPIs 主檔資料
	 * 
	 * rest address: http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/
	 * 
	 * json:
	 * http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/json
	 * 
	 * xml:
	 * http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/xml
	 * 
	 * @param format			example:	xml / json
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public String findKpis(String format) throws ServiceException, Exception;
	
}
