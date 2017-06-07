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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.model.MonitorItemType;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiEmplService;
import com.netsteadfast.greenstep.bsc.service.IKpiOrgaService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpiEmpl;
import com.netsteadfast.greenstep.po.hbm.BbKpiOrga;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.DataUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiEmplVO;
import com.netsteadfast.greenstep.vo.KpiOrgaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

/**
 * 這個程式 monitor score expression job 也會用到, 要修改時 要注意一下 
 *
 */
@SuppressWarnings("unchecked")
public class PerformanceScoreChainUtils {
	protected static Logger logger = Logger.getLogger(PerformanceScoreChainUtils.class);
	private static final int MAX_SCORE_FIELD_SIZE = 13;
	private static IVisionService<VisionVO, BbVision, String> visionService;
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private static IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	private static IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService;
	private static IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService;
	@SuppressWarnings("unused")
	private static TransactionTemplate transactionTemplate;
	
	static {
		visionService = (IVisionService<VisionVO, BbVision, String>)
				AppContext.getBean("bsc.service.VisionService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
		monitorItemScoreService = (IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String>)
				AppContext.getBean("bsc.service.MonitorItemScoreService");
		kpiEmplService = (IKpiEmplService<KpiEmplVO, BbKpiEmpl, String>)
				AppContext.getBean("bsc.service.KpiEmplService");
		kpiOrgaService = (IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String>)
				AppContext.getBean("bsc.service.KpiOrgaService");
		transactionTemplate = DataUtils.getTransactionTemplate();
	}
	
	/**
	 * 清除 公式 與 歸類方法 的表達式內容
	 * 
	 * @param vision
	 * @throws Exception
	 */
	public static void clearExpressionContentOut(VisionVO vision) throws Exception {
		if (null == vision) {
			return;
		}
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					kpi.getFormula().setExpression("");
					kpi.getFormula().setDescription("");
					kpi.getTrendsFormula().setExpression("");
					kpi.getTrendsFormula().setDescription("");
					kpi.getAggregationMethod().setExpression1("");
					kpi.getAggregationMethod().setExpression2("");
					kpi.getAggregationMethod().setDescription("");
				}
			}
		}
	}
	
	public static void clearExpressionContentOut(ChainResultObj result) throws Exception {
		if (result.getValue() == null || ( (BscStructTreeObj)result.getValue() ).getVisions() == null || ( (BscStructTreeObj)result.getValue() ).getVisions().size() == 0) {
			return;
		}		
		List<VisionVO> visions = ( (BscStructTreeObj)result.getValue() ).getVisions();
		for (VisionVO vision : visions) {
			clearExpressionContentOut(vision);
		}
	}
	
	public static Context getContext(String visionOid, String startDate, String endDate, 
			String startYearDate, String endYearDate, String frequency, String dataFor, 
			String measureDataOrganizationOid, String measureDataEmployeeOid) throws ServiceException, Exception {
		Context context = new ContextBase();
		context.put("visionOid", visionOid);
		context.put("startDate", startDate);
		context.put("endDate", endDate);		
		context.put("startYearDate", startYearDate);
		context.put("endYearDate", endYearDate);		
		context.put("frequency", frequency);
		context.put("dataFor", dataFor);
		context.put("orgId", BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		context.put("account", "");
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataOrganizationOid) && !StringUtils.isBlank(measureDataOrganizationOid)) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid( measureDataOrganizationOid );
			DefaultResult<OrganizationVO> result = organizationService.findObjectByOid(organization);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			organization = result.getValue();
			context.put("orgId", organization.getOrgId() );
		}
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataEmployeeOid) && !StringUtils.isBlank(measureDataEmployeeOid)) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid( measureDataEmployeeOid );
			DefaultResult<EmployeeVO> result = employeeService.findObjectByOid(employee);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			employee = result.getValue();
			context.put("empId", employee.getEmpId() );
			context.put("account", employee.getAccount() );
		}				
		return context;
	}
	
	public static ChainResultObj getResult(Context context) throws ServiceException, Exception {
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("performanceScoreChain", context);
		return resultObj;
	}
	
	public static ChainResultObj getResult(String visionOid, String startDate, String endDate, 
			String startYearDate, String endYearDate, String frequency, String dataFor,
			String measureDataOrganizationOid, String measureDataEmployeeOid) throws ServiceException, Exception {
		
		return getResult(
				getContext(visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid) 
		);
	}	
	
	public static void createOrUpdateMonitorItemScoreCurrentDateForEmployees(String frequency) throws ServiceException, Exception {
		Map<String, String> employeeMap = employeeService.findForMap(false);
		if (null == employeeMap || employeeMap.size() < 1) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Map.Entry<String, String> emp : employeeMap.entrySet()) {
			EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeData(employeeService, emp.getKey());
			paramMap.put("empId", employee.getEmpId());
			if (kpiEmplService.countByParams(paramMap)<1) {
				continue;
			}
			try {
				createOrUpdateMonitorItemScoreCurrentDate(frequency, "employee", "", employee.getOid());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createOrUpdateMonitorItemScoreCurrentDateForOrganizations(String frequency) throws ServiceException, Exception {
		Map<String, String> organizationMap = organizationService.findForMap(false);		
		if (null == organizationMap || organizationMap.size() < 1) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Map.Entry<String, String> org : organizationMap.entrySet()) {
			OrganizationVO organization = BscBaseLogicServiceCommonSupport.findOrganizationData(organizationService, org.getKey());
			paramMap.put("orgId", organization.getOrgId());
			if (kpiOrgaService.countByParams(paramMap)<1) {
				continue;
			}
			try {
				createOrUpdateMonitorItemScoreCurrentDate(frequency, "organization", organization.getOid(), "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createOrUpdateMonitorItemScoreCurrentDate(String frequency, 
			String dataFor, String measureDataOrganizationOid, String measureDataEmployeeOid) throws ServiceException, Exception {
		String dateVal = SimpleUtils.getStrYMD("");
		String year = dateVal.substring(0, 4);
		String startDate = "";
		String endDate = "";
		String startYearDate = year;
		String endYearDate = year;
		//String dataFor = "all"; // all, organization, employee
		if (BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency)) {
			Map<String, String> dateMap = BscMeasureDataFrequency.getWeekOrMonthStartEnd(frequency, dateVal, dateVal);
			startDate = dateMap.get("startDate");
			endDate = dateMap.get("endDate");			
		}
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency)) {
			startDate = dateVal;
			endDate = dateVal;
		}
		
		Map<String, String> visions = visionService.findForMap(false);
		for (Map.Entry<String, String> visionData : visions.entrySet()) {
			String visionOid = visionData.getKey();
			Context context = getContext(
					visionOid, startDate, endDate, startYearDate, endYearDate, frequency, 
					dataFor, measureDataOrganizationOid, measureDataEmployeeOid);
			createOrUpdateMonitorItemScore(dateVal, context);			
		}
		
	}
	
	public static void createOrUpdateMonitorItemScore(String dateVal, Context context) throws ServiceException, Exception {
		ChainResultObj result = getResult(context);
		if (result.getValue() == null || ( (BscStructTreeObj)result.getValue() ).getVisions() == null || ( (BscStructTreeObj)result.getValue() ).getVisions().size() == 0) {
			logger.warn( "No data!" );
			return;
		}
		//String dateVal = SimpleUtils.getStrYMD("");
		String frequency = (String)context.get("frequency");
		String orgId = (String)context.get("orgId");
		String empId = (String)context.get("empId");
		List<VisionVO> visions = ( (BscStructTreeObj)result.getValue() ).getVisions();
		for (VisionVO vision : visions) {
			List<PerspectiveVO> perspectives = vision.getPerspectives();
			MonitorItemScoreVO visionMonitorItemScore = getMonitorItemScore(
					dateVal, frequency, orgId, empId, MonitorItemType.VISION, vision.getVisId());
			//visionMonitorItemScore.setScore( BscReportSupportUtils.parse2(vision.getScore()) );
			setScore(visionMonitorItemScore, vision.getScore());
			saveOrUpdateMonitorItemScore(visionMonitorItemScore);
			for (PerspectiveVO perspective : perspectives) {
				List<ObjectiveVO> objectives = perspective.getObjectives();
				MonitorItemScoreVO perspectiveMonitorItemScore = getMonitorItemScore(
						dateVal, frequency, orgId, empId, MonitorItemType.PERSPECTIVES, perspective.getPerId());
				//perspectiveMonitorItemScore.setScore( BscReportSupportUtils.parse2(perspective.getScore()) );
				setScore(perspectiveMonitorItemScore, perspective.getScore());
				saveOrUpdateMonitorItemScore(perspectiveMonitorItemScore);
				for (ObjectiveVO objective : objectives) {
					List<KpiVO> kpis = objective.getKpis();
					MonitorItemScoreVO objectiveMonitorItemScore = getMonitorItemScore(
							dateVal, frequency, orgId, empId, MonitorItemType.STRATEGY_OF_OBJECTIVES, objective.getObjId());
					//objectiveMonitorItemScore.setScore( BscReportSupportUtils.parse2(objective.getScore()) );
					setScore(objectiveMonitorItemScore, objective.getScore());
					saveOrUpdateMonitorItemScore(objectiveMonitorItemScore);
					for (KpiVO kpi : kpis) {
						MonitorItemScoreVO kpiMonitorItemScore = getMonitorItemScore(
								dateVal, frequency, orgId, empId, MonitorItemType.KPI, kpi.getId());
						//kpiMonitorItemScore.setScore( BscReportSupportUtils.parse2(kpi.getScore()) );
						setScore(kpiMonitorItemScore, kpi.getScore());
						saveOrUpdateMonitorItemScore(kpiMonitorItemScore);		
					}
				}
			}
		}
		
	}
	
	private static void setScore(MonitorItemScoreVO itemScoreObj, float score) {
		String scoreStr = BscReportSupportUtils.parse2(score);
		if (scoreStr.length() > MAX_SCORE_FIELD_SIZE) {
			String tmp[] = scoreStr.split("[.]");
			if (tmp.length != 2) { // 123123123123123
				scoreStr = StringUtils.leftPad("", MAX_SCORE_FIELD_SIZE, "9");
			} else { // 1234567891234.23
				if (tmp[0].length() > MAX_SCORE_FIELD_SIZE) {
					scoreStr = StringUtils.leftPad("", MAX_SCORE_FIELD_SIZE, "9");
				} else {
					scoreStr = scoreStr.substring(0, MAX_SCORE_FIELD_SIZE);
				}
			}
		}
		itemScoreObj.setScore(scoreStr);
	}
	
	private static void saveOrUpdateMonitorItemScore(MonitorItemScoreVO item) throws ServiceException, Exception {
		if (StringUtils.isBlank(item.getOid())) { // create new
			monitorItemScoreService.saveObject(item);
		} else { // update
			monitorItemScoreService.updateObject(item);
		}
	}
	
	private static MonitorItemScoreVO getMonitorItemScore(String dateVal, String frequency, String orgId, String empId, 
			String itemType, String itemId) throws ServiceException, Exception {
		MonitorItemScoreVO item = new MonitorItemScoreVO();
		item.setDateVal(dateVal);
		item.setEmpId(empId);
		item.setFrequency(frequency);
		item.setItemId(itemId);
		item.setItemType(itemType);
		item.setOrgId(orgId);
		DefaultResult<MonitorItemScoreVO> result = monitorItemScoreService.findByUK(item);
		if (result.getValue() == null) {
			return item;
		}
		item = result.getValue();
		return item;
	}
	
	public static float getWeightPercentage(BigDecimal weight) {
		if (weight==null) {
			return 0.0f;
		}
		if (weight.floatValue() == 0.0f ) {
			return 0.0f;
		}
		return weight.floatValue() / 100.0f;
	}	
	
}
