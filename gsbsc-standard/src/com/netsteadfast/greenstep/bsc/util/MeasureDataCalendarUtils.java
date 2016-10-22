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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.DataUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@SuppressWarnings("unchecked")
public class MeasureDataCalendarUtils {
	
	private static final String[][] resourceTables = new String[][]{
		{ BscMeasureDataFrequency.FREQUENCY_DAY, 			"META-INF/resource/measure-data-calendar-day.ftl" 		},
		{ BscMeasureDataFrequency.FREQUENCY_WEEK, 			"META-INF/resource/measure-data-calendar-week.ftl"		},
		{ BscMeasureDataFrequency.FREQUENCY_MONTH,			"META-INF/resource/measure-data-calendar-month.ftl"		},
		{ BscMeasureDataFrequency.FREQUENCY_QUARTER,		"META-INF/resource/measure-data-calendar-quarter.ftl"	},
		{ BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR,	"META-INF/resource/measure-data-calendar-halfyear.ftl"	},
		{ BscMeasureDataFrequency.FREQUENCY_YEAR,			"META-INF/resource/measure-data-calendar-year.ftl"		}
	};
	private static IKpiService<KpiVO, BbKpi, String> kpiService;
	// private static IFormulaService<FormulaVO, BbFormula, String> formulaService;
	private static IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private static TransactionTemplate transactionTemplate;
	
	static {
		kpiService = (IKpiService<KpiVO, BbKpi, String>)AppContext.getBean("bsc.service.KpiService");
		// formulaService = (IFormulaService<FormulaVO, BbFormula, String>)AppContext.getBean("bsc.service.FormulaService");
		measureDataService = (IMeasureDataService<MeasureDataVO, BbMeasureData, String>)
				AppContext.getBean("bsc.service.MeasureDataService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
		transactionTemplate = DataUtils.getTransactionTemplate();
	}
	
	private static String getTemplateResource(String frequency) {
		String resource = "";
		for (int i=0; i < resourceTables.length; i++) {
			if (resourceTables[i][0].equals(frequency)) {
				resource = resourceTables[i][1];
			}
		}
		return resource;
	}
	
	private static KpiVO findKpi(String oid) throws ServiceException, Exception {
		KpiVO kpi = new KpiVO();
		kpi.setOid(oid);
		DefaultResult<KpiVO> result = kpiService.findObjectByOid(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		kpi = result.getValue();
		return kpi;
	}
	
	/*
	private static FormulaVO findFormula(String forId) throws ServiceException, Exception {
		FormulaVO formula = new FormulaVO();
		formula.setForId(forId);
		DefaultResult<FormulaVO> result = formulaService.findByUK(formula);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		formula = result.getValue();
		return formula;
	}
	*/
	
	private static EmployeeVO findEmployee(String empOid) throws ServiceException, Exception {
		EmployeeVO employee = new EmployeeVO();
		employee.setOid(empOid);
		DefaultResult<EmployeeVO> result = employeeService.findObjectByOid(employee);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		employee = result.getValue();
		return employee;
	}
	
	private static OrganizationVO findOrganization(String orgOid) throws ServiceException, Exception {
		OrganizationVO organization = new OrganizationVO();
		organization.setOid(orgOid);
		DefaultResult<OrganizationVO> result = organizationService.findObjectByOid(organization);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		organization = result.getValue();
		return organization;
	}
	

	/**
	 * 這個 method 主要給 Expression 使用, 避免近入LogicService 處理時影響到 Transaction
	 * 
	 * @param kpiId
	 * @param date
	 * @param frequency
	 * @param orgaId
	 * @param emplId
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static MeasureDataVO findMeasureData(String kpiId, String date, String frequency, 
			String orgaId, String emplId) throws ServiceException, Exception {
		
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		transactionTemplate.setReadOnly(true);		
		MeasureDataVO measureData = null;
		try {
			measureData = (MeasureDataVO) transactionTemplate.execute( 
					new TransactionCallback() {
						
						@Override
						public Object doInTransaction(TransactionStatus status) {
							MeasureDataVO value = new MeasureDataVO();
							value.setKpiId( kpiId );
							value.setDate( date );
							value.setFrequency( frequency );
							value.setOrgId( orgaId );
							value.setEmpId( emplId );
							try {
								DefaultResult<MeasureDataVO> mdResult = measureDataService.findByUK(value);
								if ( mdResult.getValue() != null ) {
									value = mdResult.getValue();
								} else {
									value = null;
								}
							} catch (Exception e) {
								e.printStackTrace();
								value = null;
							}							
							return value;
						}
						
					}
			);
		} catch (Exception e) {
			throw e;
		}
		return measureData;
	}
	
	private static List<BbMeasureData> findMeasureData(KpiVO kpi, String date, String frequency, 
			String dataFor, String orgaId, String emplId) throws ServiceException, Exception {
		List<BbMeasureData> searchList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> likeParams = new HashMap<String, String>();
		params.put("kpiId", kpi.getId());
		params.put("frequency", frequency);
		params.put("orgId", orgaId);
		params.put("empId", emplId);	
		String queryDate = BscMeasureDataFrequency.getQueryDate(date, frequency);
		likeParams.put("date", queryDate+"%");
		searchList = measureDataService.findListByParams(params, likeParams);
		if (null == searchList) {
			searchList = new ArrayList<BbMeasureData>();
		}
		return searchList;
	}
	
	private static Map<String, Object> getParameter(KpiVO kpi, FormulaVO formula, 
			String date, String frequency, String dataFor, String orgaOid, String emplOid) throws ServiceException, Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("date", date);
		parameter.put("frequency", frequency);
		parameter.put("dataFor", dataFor);
		parameter.put("kpi", kpi);
		parameter.put("formula", formula);
		parameter.put("managementName", BscKpiCode.getManagementMap(false).get(kpi.getManagement()) );
		//parameter.put("calculationName", BscKpiCode.getCalculationMap(false).get(kpi.getCal()) );
		parameter.put("calculationName", AggregationMethodUtils.getNameByAggrId(kpi.getCal()) );		
		parameter.put("orgId", BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		parameter.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);			
		if (BscConstants.MEASURE_DATA_FOR_ORGANIZATION.equals(dataFor) ) {
			OrganizationVO organization = findOrganization(orgaOid);
			parameter.put("orgId", organization.getOrgId());
		}
		if (BscConstants.MEASURE_DATA_FOR_EMPLOYEE.equals(dataFor) ) {
			EmployeeVO employee = findEmployee(emplOid);
			parameter.put("empId", employee.getEmpId());
		}		
		parameter.put("masureDatas", findMeasureData(kpi, date, frequency, dataFor, 
				(String)parameter.get("orgId"), (String)parameter.get("empId")) );
		return parameter;
	}
	
	private static void fillParameterForDay(Map<String, Object> parameter) throws Exception {
		String tmp[] = ( (String)parameter.get("date") ).split("-");
		String yyyy = tmp[0];
		String mm = tmp[1];
		String yyyyMM = yyyy+mm;
		int maxday = SimpleUtils.getMaxDayOfMonth(SimpleUtils.getInt(yyyy, 1990), SimpleUtils.getInt(mm, 1) );
		int dayOfWeek = SimpleUtils.getDayOfWeek(SimpleUtils.getInt(yyyy, 1990), SimpleUtils.getInt(mm, 1) );
		int showLen = (maxday+dayOfWeek) / 7;
		if ( (maxday + dayOfWeek) % 7 > 1 ) {
			showLen = showLen + 1;		
		}		
		int previousMonthMaxDay = 0;
		int previousMonth = SimpleUtils.getInt(mm, 1)-1;
		int previousYear = SimpleUtils.getInt(yyyy, 1990);
		if (previousMonth < 1 ) {
			previousYear = previousYear - 1;
			previousMonth = 12;
		}
		previousMonthMaxDay = SimpleUtils.getMaxDayOfMonth(previousYear, previousMonth);
		
		parameter.put("yyyy", yyyy);
		parameter.put("mm", mm);
		parameter.put("yyyyMM", yyyyMM);
		parameter.put("maxday", maxday);
		parameter.put("dayOfWeek", dayOfWeek);
		parameter.put("showLen", showLen);
		parameter.put("previousMonthMaxDay", previousMonthMaxDay);
		parameter.put("previousMonth", previousMonth);
		parameter.put("previousYear", previousYear);
	}
	
	private static void fillParameterForWeekOrMonth(Map<String, Object> parameter) throws Exception {
		String tmp[] = ( (String)parameter.get("date") ).split("-");
		String yyyy = tmp[0];
		String mm = tmp[1];
		String yyyyMM = yyyy+mm;
		
		parameter.put("yyyy", yyyy);
		parameter.put("mm", mm);
		parameter.put("yyyyMM", yyyyMM);		
	}
	
	private static void fillParameterForQuarterOrYear(Map<String, Object> parameter) throws Exception {
		String tmp[] = ( (String)parameter.get("date") ).split("-");
		String yyyy = tmp[0];
		
		parameter.put("yyyy", yyyy);		
	}
	
	private static String render(Map<String, Object> parameter, String templateResource) throws Exception {
		/*
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("resourceTemplate", 
				TemplateUtils.getResourceSrc(MeasureDataCalendarUtils.class.getClassLoader(), templateResource) );
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(templateLoader);
		Template template = cfg.getTemplate("resourceTemplate", "utf-8");
		Writer out = new StringWriter();
		template.process(parameter, out);
		return out.toString();
		*/
		return TemplateUtils.processTemplate(
				"resourceTemplate", 
				MeasureDataCalendarUtils.class.getClassLoader(), 
				templateResource, 
				parameter);
	}	
	
	public static String renderBody(String kpiOid, String date, String frequency,
			String dataFor, String orgaOid, String emplOid, Map<String, String> labels) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiOid) || StringUtils.isBlank(date) 
				|| StringUtils.isBlank(frequency) || StringUtils.isBlank(dataFor) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		KpiVO kpi = findKpi(kpiOid);
		//FormulaVO formula = findFormula(kpi.getForId());
		FormulaVO formula = BscFormulaUtils.getFormulaById(kpi.getForId());
		Map<String, Object> parameter = getParameter(
				kpi, formula, date, frequency, dataFor, orgaOid, emplOid);
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) ) {
			fillParameterForDay(parameter);
		}
		if (BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			fillParameterForWeekOrMonth(parameter);
		}
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			fillParameterForQuarterOrYear(parameter);
		}		
		for (Map.Entry<String, String> entry : labels.entrySet()) {
			parameter.put(entry.getKey(), entry.getValue());
		}
		return render(parameter, getTemplateResource(frequency) );
	}	
	
}
