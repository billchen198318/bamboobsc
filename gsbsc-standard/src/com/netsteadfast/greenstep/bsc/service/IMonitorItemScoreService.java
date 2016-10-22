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
package com.netsteadfast.greenstep.bsc.service;

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.service.IBaseService;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;

public interface IMonitorItemScoreService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseService<T, E, PK> {
	
	public static String MAPPER_ID_PO2VO="monitorItemScore.po2vo";
	public static String MAPPER_ID_VO2PO="monitorItemScore.vo2po";
	
	public static int MAX_DAYS_BEFORE_RANGE = 30;
	
	public int deleteForTypeClass(String itemType, String itemId) throws ServiceException, Exception;
	
	public int deleteForSmallerEqualsThanDate(String itemType, String itemId, String dateVal) throws ServiceException, Exception;	
	
	public int deleteForSmallerEqualsThanDate(String dateVal) throws ServiceException, Exception;
	
	public int deleteForEmpId(String empId) throws ServiceException, Exception;
	
	public int deleteForOrgId(String orgId) throws ServiceException, Exception;	
	
	public Map<String, List<MonitorItemScoreVO>> getHistoryDataList(String itemType, String frequency, String dateVal, 
			String orgId, String empId, int daysBeforeRange) throws ServiceException, Exception;
	
	public Map<String, List<MonitorItemScoreVO>> getHistoryDataList(String itemType, String frequency, String dateVal, 
			String orgId, String empId) throws ServiceException, Exception;	
	
}
