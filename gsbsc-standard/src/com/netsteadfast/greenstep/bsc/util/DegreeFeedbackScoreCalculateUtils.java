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
package com.netsteadfast.greenstep.bsc.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

/**
 * 主要是用來計算 360 Degree Feedback 的分用的工具
 * 
 */
@SuppressWarnings("unchecked")
public class DegreeFeedbackScoreCalculateUtils {
	private static IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private static IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private static IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private static IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService;	
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	static {
		degreeFeedbackProjectService = (IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String>)
				AppContext.getBean("bsc.service.DegreeFeedbackProjectService");
		degreeFeedbackItemService = (IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String>)
				AppContext.getBean("bsc.service.DegreeFeedbackItemService");
		degreeFeedbackLevelService = (IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String>)
				AppContext.getBean("bsc.service.DegreeFeedbackLevelService");
		degreeFeedbackScoreService = (IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String>)
				AppContext.getBean("bsc.service.DegreeFeedbackScoreService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
		
	}
	
	public static DegreeFeedbackProjectVO getProjectScore(String projectOid, String ownerEmployeeOid) throws ServiceException, Exception {
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		project.setOid( projectOid );
		DefaultResult<DegreeFeedbackProjectVO> projectResult = degreeFeedbackProjectService.findObjectByOid(project);
		if (projectResult.getValue()==null) {
			throw new ServiceException(projectResult.getSystemMessage().getValue());
		}
		project = projectResult.getValue();
		
		EmployeeVO employee = new EmployeeVO();
		employee.setOid(ownerEmployeeOid);
		DefaultResult<EmployeeVO> empResult = employeeService.findObjectByOid(employee);
		if (empResult.getValue()==null) {
			throw new ServiceException(empResult.getSystemMessage().getValue());
		}
		employee = empResult.getValue();		
		List<DegreeFeedbackItemVO> items = getItemsScore(project, employee);
		if (items!=null && items.size()>0) {
			project.setEmployee(employee);
			project.setItems(items);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("projectOid", project.getOid());
			Map<String, String> orderMap = new HashMap<String, String>();
			orderMap.put("value", "asc");
			List<BbDegreeFeedbackLevel> levels = degreeFeedbackLevelService.findListByParams(paramMap, null, orderMap);
			project.setLevels( wrapLevels(levels) );			
		}
		return project;
	}
	
	public static List<DegreeFeedbackItemVO> getItemsScore(DegreeFeedbackProjectVO project, EmployeeVO employee) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		List<DegreeFeedbackItemVO> items = degreeFeedbackItemService.findListVOByParams(paramMap);
		DefaultResult<List<BbDegreeFeedbackScore>> scoresResult = degreeFeedbackScoreService
				.findResultsByProjectAndOwner(project.getOid(), employee.getEmpId());
		if (scoresResult.getValue()!=null) {
			setItemScoreCalculate(project, items, scoresResult.getValue());
		}
		return items;
	}
	
	private static void setItemScoreCalculate(DegreeFeedbackProjectVO project, 
			List<DegreeFeedbackItemVO> items, List<BbDegreeFeedbackScore> scores) throws ServiceException, Exception {
		List<String> assigns = new ArrayList<String>(); // 拿來放真的有評分的評分者數量
		for (BbDegreeFeedbackScore scoreData : scores) {
			if (!assigns.contains(scoreData.getAssignOid())) {
				assigns.add( scoreData.getAssignOid() );
			}
		}
		for (DegreeFeedbackItemVO item : items) {
			int sumScore = 0;
			float avgScore = 0.0f;
			for (BbDegreeFeedbackScore scoreData : scores) {
				if (scoreData.getItemOid().equals(item.getOid())) {
					sumScore += scoreData.getScore();
				}
			}
			if (sumScore>0 && assigns.size()>0) { // 得分 = 總計/評分者				
				avgScore = Float.valueOf( sumScore ) / Float.valueOf( assigns.size() );
				BigDecimal bd = new BigDecimal(avgScore);
				avgScore = bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
			}
			item.setSumScore(sumScore);			
			item.setAvgScore(avgScore);
		}
		assigns.clear();
		assigns = null;
	}
	
	private static List<DegreeFeedbackLevelVO> wrapLevels(List<BbDegreeFeedbackLevel> levels) throws Exception {
		List<DegreeFeedbackLevelVO> newLevels = new LinkedList<DegreeFeedbackLevelVO>();
		for (BbDegreeFeedbackLevel level : levels) {
			DegreeFeedbackLevelVO obj = new DegreeFeedbackLevelVO();
			degreeFeedbackLevelService.copyProperties(level, obj);	
			newLevels.add( obj );
		}
		return newLevels;
	}	
	
}
