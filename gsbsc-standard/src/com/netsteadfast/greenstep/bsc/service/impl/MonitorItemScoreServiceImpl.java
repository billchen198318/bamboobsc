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
package com.netsteadfast.greenstep.bsc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IMonitorItemScoreDAO;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;

@Service("bsc.service.MonitorItemScoreService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class MonitorItemScoreServiceImpl extends BaseService<MonitorItemScoreVO, BbMonitorItemScore, String> implements IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> {
	protected Logger logger=Logger.getLogger(MonitorItemScoreServiceImpl.class);
	private IMonitorItemScoreDAO<BbMonitorItemScore, String> monitorItemScoreDAO;
	
	public MonitorItemScoreServiceImpl() {
		super();
	}

	public IMonitorItemScoreDAO<BbMonitorItemScore, String> getMonitorItemScoreDAO() {
		return monitorItemScoreDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.MonitorItemScoreDAO")
	@Required		
	public void setMonitorItemScoreDAO(
			IMonitorItemScoreDAO<BbMonitorItemScore, String> monitorItemScoreDAO) {
		this.monitorItemScoreDAO = monitorItemScoreDAO;
	}

	@Override
	protected IBaseDAO<BbMonitorItemScore, String> getBaseDataAccessObject() {
		return monitorItemScoreDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public int deleteForTypeClass(String itemType, String itemId) throws ServiceException, Exception {
		if (StringUtils.isBlank(itemType) || StringUtils.isBlank(itemId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.monitorItemScoreDAO.deleteForTypeClass(itemType, itemId);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public int deleteForSmallerEqualsThanDate(String itemType, String itemId, String dateVal) throws ServiceException, Exception {
		if (StringUtils.isBlank(itemType) || StringUtils.isBlank(itemId) || !SimpleUtils.isDate(dateVal)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.monitorItemScoreDAO.deleteForSmallerEqualsThanDate(itemType, itemId, dateVal);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public int deleteForSmallerEqualsThanDate(String dateVal) throws ServiceException, Exception {
		if (!SimpleUtils.isDate(dateVal)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.monitorItemScoreDAO.deleteForSmallerEqualsThanDate(dateVal);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public int deleteForEmpId(String empId) throws ServiceException, Exception {
		if (StringUtils.isBlank(empId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.monitorItemScoreDAO.deleteForEmpId(empId);
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public int deleteForOrgId(String orgId) throws ServiceException, Exception {
		if (StringUtils.isBlank(orgId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.monitorItemScoreDAO.deleteForOrgId(orgId);
	}
	
	@Override
	public Map<String, List<MonitorItemScoreVO>> getHistoryDataList(String itemType, String frequency, String dateVal, 
			String orgId, String empId, int daysBeforeRange) throws ServiceException, Exception {
		
		if (StringUtils.isBlank(itemType) || StringUtils.isBlank(frequency) || StringUtils.isBlank(dateVal)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (!SimpleUtils.isDate(dateVal)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		if (daysBeforeRange > MAX_DAYS_BEFORE_RANGE || daysBeforeRange < 0) {
			throw new ServiceException("daysBeforeRange error!");
		}
		Date endDate = DateUtils.parseDate(dateVal, new String[]{"yyyyMMdd"});
		Date startDate = DateUtils.addDays(endDate, (daysBeforeRange * -1) );
		String startDateStr = DateFormatUtils.format(startDate, "yyyyMMdd");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("itemType", itemType);
		paramMap.put("frequency", frequency);
		paramMap.put("orgId", orgId);
		paramMap.put("empId", empId);
		Map<String, CustomeOperational> customeMap = new HashMap<String, CustomeOperational>();
		CustomeOperational op1 = new CustomeOperational();
		op1.setField("dateVal");
		op1.setOp(">=");
		op1.setValue(startDateStr);
		CustomeOperational op2 = new CustomeOperational();
		op2.setField("dateVal");
		op2.setOp("<=");
		op2.setValue(dateVal);		
		customeMap.put("op1", op1);
		customeMap.put("op2", op2);
		List<BbMonitorItemScore> searchList = this.findListByParams2(paramMap, customeMap);
		if (null == searchList || searchList.size() <1) {
			return null;
		}
		Map<String, List<MonitorItemScoreVO>> dataMap = new HashMap<String, List<MonitorItemScoreVO>>();
		List<String> idKeyList = new ArrayList<String>();
		for (BbMonitorItemScore data : searchList) {
			if (idKeyList.contains(data.getItemId())) {
				continue;
			}
			idKeyList.add(data.getItemId());
			dataMap.put(data.getItemId(), new LinkedList<MonitorItemScoreVO>());
		}
		for (int i=0; i<=daysBeforeRange; i++) {
			Date currentDate = DateUtils.addDays(startDate, i);
			String currentDateStr = DateFormatUtils.format(currentDate, "yyyyMMdd");
			for (String id : idKeyList) {
				MonitorItemScoreVO scoreData = new MonitorItemScoreVO();
				scoreData.setItemType(itemType);
				scoreData.setItemId(id);
				scoreData.setFrequency(frequency);
				scoreData.setOrgId(orgId);
				scoreData.setEmpId(empId);
				scoreData.setDateVal(currentDateStr);
				scoreData.setScore("0"); // 如果沒有當天歷史分數資料的話, 預設就是"0"
				for (BbMonitorItemScore data : searchList) {
					if (data.getItemId().equals(id) && data.getDateVal().equals(currentDateStr)) {
						scoreData.setScore(data.getScore());
					}
				}
				dataMap.get(id).add(scoreData);
			}
		}
		return dataMap;
	}

	@Override
	public Map<String, List<MonitorItemScoreVO>> getHistoryDataList(String itemType, String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		return this.getHistoryDataList(itemType, frequency, dateVal, orgId, empId, MAX_DAYS_BEFORE_RANGE);
	}
	
}
