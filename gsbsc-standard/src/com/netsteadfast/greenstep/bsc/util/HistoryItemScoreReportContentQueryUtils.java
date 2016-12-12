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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.model.MonitorItemType;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@SuppressWarnings("unchecked")
public class HistoryItemScoreReportContentQueryUtils {
	private static IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	private static IVisionService<VisionVO, BbVision, String> visionService;
	private static IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService; 
	private static IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private static IKpiService<KpiVO, BbKpi, String> kpiService;
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	static {
		monitorItemScoreService = (IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String>)
				AppContext.getBean("bsc.service.MonitorItemScoreService");
		visionService = (IVisionService<VisionVO, BbVision, String>)
				AppContext.getBean("bsc.service.VisionService");
		perspectiveService = (IPerspectiveService<PerspectiveVO, BbPerspective, String>)
				AppContext.getBean("bsc.service.PerspectiveService");
		objectiveService = (IObjectiveService<ObjectiveVO, BbObjective, String>)
				AppContext.getBean("bsc.service.ObjectiveService");
		kpiService = (IKpiService<KpiVO, BbKpi, String>)AppContext.getBean("bsc.service.KpiService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");		
	}
	
	public static List<Map<String, Object>> getVisionLineChartData(String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		return getLineChartData(MonitorItemType.VISION, frequency, dateVal, orgId, empId);
	}
	
	public static List<Map<String, Object>> getPerspectivesLineChartData(String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		return getLineChartData(MonitorItemType.PERSPECTIVES, frequency, dateVal, orgId, empId);
	}
	
	public static List<Map<String, Object>> getObjectiveOfStrategyLineChartData(String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		return getLineChartData(MonitorItemType.STRATEGY_OF_OBJECTIVES, frequency, dateVal, orgId, empId);
	}
	
	public static List<Map<String, Object>> getKpisLineChartData(String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		return getLineChartData(MonitorItemType.KPI, frequency, dateVal, orgId, empId);
	}	
	
	public static List<String> getLineChartCategoriesFromData(String dateVal, List<Map<String, Object>> chartDataList) throws Exception {
		if (chartDataList == null || chartDataList.size() < 1 || chartDataList.get(0).get("data") == null) {
			return getLineChartCategories(dateVal, 0);
		}
		// 取 List 第1筆資料, 就可以了, 因為假如有3筆項目資料, 它們的 dateRange 日期區間是一樣的, 請參考 MonitorItemScoreServiceImpl.java getHistoryDataList()
		return getLineChartCategories( dateVal, ( (List<?>)chartDataList.get(0).get("data") ).size() );
	}
	
	public static List<String> getLineChartCategories(String dateVal, int dataSize) throws Exception {
		List<String> categories = new LinkedList<String>();
		if (dataSize < 0) {
			throw new ServiceException("data-size error!");
		}		
		Date endDate = DateUtils.parseDate(dateVal, new String[]{"yyyyMMdd"});
		int dateRange = dataSize -1;
		if (dateRange < 0) {
			dateRange = 0;
		}
		if (dateRange == 0) {
			categories.add( DateFormatUtils.format(endDate, "yyyy/MM/dd") );
			return categories;
		}
		Date startDate = DateUtils.addDays(endDate, (dateRange * -1) );
		for (int i=0; i<dataSize; i++) {
			Date currentDate = DateUtils.addDays(startDate, i);
			String currentDateStr = DateFormatUtils.format(currentDate, "yyyy/MM/dd");
			categories.add(currentDateStr);
		}
		return categories;
	}
	
	public static List<Map<String, Object>> getLineChartData(String itemType, String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, List<MonitorItemScoreVO>> itemScoreDataMap = monitorItemScoreService.getHistoryDataList(
				itemType, frequency, dateVal, orgId, empId);
		if (itemScoreDataMap == null || itemScoreDataMap.size() < 1) {
			return dataList;
		}
		List<VisionVO> basicDataList = getBasicDataList();
		for (Map.Entry<String, List<MonitorItemScoreVO>> entry : itemScoreDataMap.entrySet()) {
			String name = "";
			String id = entry.getKey();
			List<MonitorItemScoreVO> scoreList = entry.getValue();		
			if (scoreList == null || scoreList.size() < 1) {
				continue;
			}			
			if (MonitorItemType.VISION.equals(itemType)) {
				DefaultResult<VisionVO> visionResult = visionService.findForSimpleByVisId(id);
				if (visionResult.getValue() == null) {
					throw new ServiceException( visionResult.getSystemMessage().getValue() );
				}
				VisionVO vision = visionResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.VISION, vision.getVisId(), vision.getTitle(), basicDataList);
			}
			if (MonitorItemType.PERSPECTIVES.equals(itemType)) {
				PerspectiveVO perspective = new PerspectiveVO();
				perspective.setPerId(id);
				DefaultResult<PerspectiveVO> perspectiveResult = perspectiveService.findByUK(perspective);
				if (perspectiveResult.getValue() == null) {
					throw new ServiceException( perspectiveResult.getSystemMessage().getValue() );
				}
				perspective = perspectiveResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.PERSPECTIVES, perspective.getPerId(), perspective.getName(), basicDataList);
			}
			if (MonitorItemType.STRATEGY_OF_OBJECTIVES.equals(itemType)) {
				ObjectiveVO objective = new ObjectiveVO();
				objective.setObjId(id);
				DefaultResult<ObjectiveVO> objectiveResult = objectiveService.findByUK(objective);
				if (objectiveResult.getValue() == null) {
					throw new ServiceException( objectiveResult.getSystemMessage().getValue() );
				}
				objective = objectiveResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.STRATEGY_OF_OBJECTIVES, objective.getObjId(), objective.getName(), basicDataList);
			}		
			if (MonitorItemType.KPI.equals(itemType)) {
				KpiVO kpi = new KpiVO();
				kpi.setId(id);
				DefaultResult<KpiVO> kpiResult = kpiService.findByUK(kpi);
				if (kpiResult.getValue() == null) {
					throw new ServiceException( kpiResult.getSystemMessage().getValue() );
				}
				kpi = kpiResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.KPI, kpi.getId(), kpi.getName(), basicDataList);
			}				
			if (StringUtils.isBlank(name)) {
				throw new ServiceException( "Name not found!" );
			}
			Map<String, Object> chartDataMap = new HashMap<String, Object>();
			chartDataMap.put("name", name);
			chartDataMap.put("data", new LinkedList<Float>());
			for (MonitorItemScoreVO itemScore : scoreList) {
				( (List<Float>)chartDataMap.get("data") ).add( Float.valueOf(itemScore.getScore()) );
			}
			dataList.add(chartDataMap);
		}
		return dataList;
	}	
	
	public static Map<String, Object> getHartMapChartData(String itemType, String frequency, String dateVal,
			String orgId, String empId) throws ServiceException, Exception {
		
		List<Map<String, Object>> chartLineData = getLineChartData(itemType, frequency, dateVal, orgId, empId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (null == chartLineData || chartLineData.size() < 1) {
			return dataMap;
		}
		List<String> xAxisCategories = new LinkedList<String>();
		List<List<Object>> dataList = new LinkedList<List<Object>>();	
		for (int x=0; x<chartLineData.size(); x++) {
			String itemName = String.valueOf( chartLineData.get(x).get("name") );
			List<Float> itemScoreList = (List<Float>) chartLineData.get(x).get("data");
			for (int n=0; n<itemScoreList.size(); n++) {
				List<Object> itemHartMapData = new LinkedList<Object>();
				itemHartMapData.add(x);
				itemHartMapData.add(n);
				itemHartMapData.add(itemScoreList.get(n));
				dataList.add(itemHartMapData);
			}
			xAxisCategories.add(itemName);
		}
		dataMap.put("xAxisCategories", xAxisCategories);
		dataMap.put("yAxisCategories", getLineChartCategoriesFromData(dateVal, chartLineData));
		dataMap.put("seriesData", dataList);
		return dataMap;
	}
	
	public static List<MonitorItemScoreVO> fill2ValueObjectList(List<BbMonitorItemScore> monitorItemScores) throws ServiceException, Exception {
		List<MonitorItemScoreVO> results = new ArrayList<MonitorItemScoreVO>();
		if (null == monitorItemScores) {
			return results;
		}
		List<VisionVO> basicDataList = getBasicDataList();
		for (BbMonitorItemScore monitorScoreSrc : monitorItemScores) {
			MonitorItemScoreVO monitorScoreDest = new MonitorItemScoreVO();
			monitorItemScoreService.doMapper(monitorScoreSrc, monitorScoreDest, IMonitorItemScoreService.MAPPER_ID_PO2VO);
			String id = monitorScoreSrc.getItemId();
			String name = "";
			String itemType = monitorScoreSrc.getItemType();
			if (MonitorItemType.VISION.equals(itemType)) {
				DefaultResult<VisionVO> visionResult = visionService.findForSimpleByVisId(id);
				if (visionResult.getValue() == null) {
					throw new ServiceException( visionResult.getSystemMessage().getValue() );
				}
				VisionVO vision = visionResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.VISION, vision.getVisId(), vision.getTitle(), basicDataList);
			}
			if (MonitorItemType.PERSPECTIVES.equals(itemType)) {
				PerspectiveVO perspective = new PerspectiveVO();
				perspective.setPerId(id);
				DefaultResult<PerspectiveVO> perspectiveResult = perspectiveService.findByUK(perspective);
				if (perspectiveResult.getValue() == null) {
					throw new ServiceException( perspectiveResult.getSystemMessage().getValue() );
				}
				perspective = perspectiveResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.PERSPECTIVES, perspective.getPerId(), perspective.getName(), basicDataList);
			}
			if (MonitorItemType.STRATEGY_OF_OBJECTIVES.equals(itemType)) {
				ObjectiveVO objective = new ObjectiveVO();
				objective.setObjId(id);
				DefaultResult<ObjectiveVO> objectiveResult = objectiveService.findByUK(objective);
				if (objectiveResult.getValue() == null) {
					throw new ServiceException( objectiveResult.getSystemMessage().getValue() );
				}
				objective = objectiveResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.STRATEGY_OF_OBJECTIVES, objective.getObjId(), objective.getName(), basicDataList);
			}		
			if (MonitorItemType.KPI.equals(itemType)) {
				KpiVO kpi = new KpiVO();
				kpi.setId(id);
				DefaultResult<KpiVO> kpiResult = kpiService.findByUK(kpi);
				if (kpiResult.getValue() == null) {
					throw new ServiceException( kpiResult.getSystemMessage().getValue() );
				}
				kpi = kpiResult.getValue();
				name = getItemNameWithVisionGroup(MonitorItemType.KPI, kpi.getId(), kpi.getName(), basicDataList);
			}				
			monitorScoreDest.setName(name);
			monitorScoreDest.setOrganizationName( monitorScoreSrc.getOrgId() );
			monitorScoreDest.setEmployeeName( monitorScoreSrc.getEmpId() );
			if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(monitorScoreSrc.getOrgId())) {
				OrganizationVO organization = BscBaseLogicServiceCommonSupport.findOrganizationDataByUK(organizationService, monitorScoreSrc.getOrgId());
				monitorScoreDest.setOrganizationName(monitorScoreSrc.getOrgId() + " - " + organization.getName());
			}
			if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(monitorScoreSrc.getEmpId())) {
				EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(employeeService, monitorScoreSrc.getEmpId());
				monitorScoreDest.setEmployeeName(monitorScoreSrc.getEmpId() + " - " + employee.getFullName());
			}
			results.add(monitorScoreDest);
		}
		return results;
	}
	
	private static String getItemNameWithVisionGroup(String itemType, String id, String name, List<VisionVO> basicDataList) throws Exception {
		if (basicDataList.size() < 2) { // 沒有1筆以上的 vision 資料, 不需要組出 group 字串
			return name;
		}
		String groupStr = "";
		for (int v = 0; v < basicDataList.size() && "".equals(groupStr); v++) {
			int group = v + 1;
			VisionVO vision = basicDataList.get(v);
			if (MonitorItemType.VISION.equals(itemType) && id.equals(vision.getVisId())) {
				groupStr = String.valueOf(group);
			}
			for (int p = 0; p < vision.getPerspectives().size() && "".equals(groupStr); p++) {
				PerspectiveVO perspective = vision.getPerspectives().get(p);
				if (MonitorItemType.PERSPECTIVES.equals(itemType) && id.equals(perspective.getPerId())) {
					groupStr = String.valueOf(group);
				}
				for (int o = 0; o < perspective.getObjectives().size() && "".equals(groupStr); o++) {
					ObjectiveVO objective = perspective.getObjectives().get(o);
					if (MonitorItemType.STRATEGY_OF_OBJECTIVES.equals(itemType) && id.equals(objective.getObjId())) {
						groupStr = String.valueOf(group);
					}
					for (int k = 0 ; k < objective.getKpis().size() && "".equals(groupStr); k++) {
						KpiVO kpi = objective.getKpis().get(k);
						if (MonitorItemType.KPI.equals(itemType) && id.equals(kpi.getId())) {
							groupStr = String.valueOf(group);
						}
					}
				}
			}
		}
		if ("".equals(groupStr)) {
			return name;
		}
		return name + "(" + groupStr + ")";
	}
	
	private static List<VisionVO> getBasicDataList() throws ServiceException, Exception {
		List<VisionVO> visions = new LinkedList<VisionVO>();
		List<VisionVO> visionsTempList = new ArrayList<VisionVO>();
		Map<String, String> visionMap = visionService.findForMap(false);
		for (Map.Entry<String, String> visionEntry : visionMap.entrySet()) {
			DefaultResult<VisionVO> visionResult = visionService.findForSimple(visionEntry.getKey());
			if (visionResult.getValue() == null) {
				throw new ServiceException(visionResult.getSystemMessage().getValue());
			}
			VisionVO vision = visionResult.getValue();
			visionsTempList.add(vision);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (VisionVO vision : visionsTempList) {
			paramMap.clear();
			paramMap.put("visId", vision.getVisId());
			List<PerspectiveVO> perspectivesList = perspectiveService.findListVOByParams(paramMap);
			for (int p=0; perspectivesList != null && p < perspectivesList.size(); p++) {
				PerspectiveVO perspective = perspectivesList.get(p);
				vision.getPerspectives().add(perspective);
				paramMap.clear();
				paramMap.put("perId", perspective.getPerId());
				List<ObjectiveVO> objectivesList = objectiveService.findListVOByParams(paramMap);
				for (int o=0; objectivesList != null && o < objectivesList.size(); o++) {
					ObjectiveVO objective = objectivesList.get(o);
					perspective.getObjectives().add(objective);
					paramMap.clear();
					paramMap.put("objId", objective.getObjId());
					List<KpiVO> kpiList = kpiService.findListVOByParams(paramMap);
					if (kpiList != null && kpiList.size() > 0) {
						objective.getKpis().addAll(kpiList);
					}
				}
			}
		}
		// 必須有 KPI 才能放到回傳的 visions
		for (int v = 0; v < visionsTempList.size(); v++) {
			boolean isFoundData = true;
			VisionVO vision = visionsTempList.get(v);
			for (int p = 0; p < vision.getPerspectives().size() && isFoundData; p++) {
				PerspectiveVO perspective = vision.getPerspectives().get(p);
				for (int o = 0; o < perspective.getObjectives().size() && isFoundData; o++) {
					ObjectiveVO objective = perspective.getObjectives().get(o);
					if (objective.getKpis() == null || objective.getKpis().size() < 1) {
						isFoundData = false;
					}
				}
			}
			if (isFoundData) {
				visions.add(vision);
			}
		}
		return visions;
	}
	
}
