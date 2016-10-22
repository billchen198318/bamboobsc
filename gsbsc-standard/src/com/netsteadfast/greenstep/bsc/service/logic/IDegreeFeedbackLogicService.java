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
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;

public interface IDegreeFeedbackLogicService {
	
	public DefaultResult<DegreeFeedbackProjectVO> createProject(DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items,
			List<DegreeFeedbackLevelVO> levels, List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> deleteProject(DegreeFeedbackProjectVO project) throws ServiceException, Exception;
	
	public DefaultResult<DegreeFeedbackProjectVO> updateProject(DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items,
			List<DegreeFeedbackLevelVO> levels, List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception;
	
	public DefaultResult<DegreeFeedbackProjectVO> updateScore(String projectOid, String ownerEmployeeOid, String raterEmployeeOid,
			List<DegreeFeedbackScoreVO> scores) throws ServiceException, Exception;
	
	public void confirmTask(String projectOid, String taskId, String reason, String confirm) throws ServiceException, Exception;
	
	public String getTaskDiagram(String taskId) throws ServiceException, Exception;
	
	public DefaultResult<DegreeFeedbackProjectVO> startProcess(DegreeFeedbackProjectVO project) throws ServiceException, Exception;
	
	public List<BusinessProcessManagementTaskVO> queryTaskByVariableProjectOid(String projectOid) throws ServiceException, Exception;
	
}
