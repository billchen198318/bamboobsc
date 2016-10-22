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
import com.netsteadfast.greenstep.bsc.vo.PdcaProjectRelatedVO;
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaVO;

public interface IPdcaLogicService {
	
	public DefaultResult<PdcaVO> create(PdcaVO pdca, PdcaMeasureFreqVO measureFreq, List<String> organizationOids, List<String> employeeOids, List<String> kpiOids,
			List<String> attachment, List<PdcaItemVO> items) throws ServiceException, Exception;
	
	public DefaultResult<PdcaVO> update(PdcaVO pdca, PdcaMeasureFreqVO measureFreq, List<String> organizationOids, List<String> employeeOids, List<String> kpiOids,
			List<String> attachment, List<PdcaItemVO> items) throws ServiceException, Exception;	
	
	public DefaultResult<Boolean> delete(PdcaVO pdca) throws ServiceException, Exception;
	
	public DefaultResult<PdcaVO> startProcess(PdcaVO pdca) throws ServiceException, Exception;
	
	public List<BusinessProcessManagementTaskVO> queryTaskByVariablePdcaOid(String pdcaOid) throws ServiceException, Exception;
	
	public String getTaskDiagram(String taskId) throws ServiceException, Exception;
	
	public void confirmTask(String pdcaOid, String taskId, String confirm, String reason, String newChild) throws ServiceException, Exception;
	
	public PdcaProjectRelatedVO findProjectRelated(String pdcaOid) throws ServiceException, Exception;
	
}
