/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.espy.arima.ArimaForecaster;
import org.espy.arima.DefaultArimaForecaster;
import org.espy.arima.DefaultArimaProcess;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.ITsaMaCoefficientsService;
import com.netsteadfast.greenstep.bsc.service.ITsaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.ITsaService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbTsa;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.po.hbm.BbTsaMeasureFreq;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.TsaMaCoefficientsVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.TsaVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@SuppressWarnings("unchecked")
public class TimeSeriesAnalysisUtils {
	
	private static ITsaService<TsaVO, BbTsa, String> tsaService;
	private static ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService;	
	private static ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;		
	
	static {
		tsaService = (ITsaService<TsaVO, BbTsa, String>) AppContext.getBean("bsc.service.TsaService");
		tsaMaCoefficientsService = (ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String>) AppContext.getBean("bsc.service.TsaMaCoefficientsService");
		tsaMeasureFreqService = (ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String>) AppContext.getBean("bsc.service.TsaMeasureFreqService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>) AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>) AppContext.getBean("bsc.service.EmployeeService");
	}
	
	public static TsaVO getParam(String tsaOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(tsaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		TsaVO tsa = new TsaVO();
		tsa.setOid(tsaOid);
		DefaultResult<TsaVO> result = tsaService.findObjectByOid(tsa);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		tsa = result.getValue();
		return tsa;
	}
	
	public static TsaMeasureFreqVO getMeasureFreq(TsaVO tsa) throws ServiceException, Exception {
		TsaMeasureFreqVO measureFreq = new TsaMeasureFreqVO();
		measureFreq.setTsaOid( tsa.getOid() );
		DefaultResult<TsaMeasureFreqVO> result = tsaMeasureFreqService.findByUK(measureFreq);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		measureFreq = result.getValue();
		return measureFreq;
	}
	
	public static List<BbTsaMaCoefficients> getCoefficients(TsaVO tsa) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tsaOid", tsa.getOid());
		Map<String, String> orderByParam = new HashMap<String, String>();
		orderByParam.put("seq", "ASC");
		return tsaMaCoefficientsService.findListByParams(paramMap, null, orderByParam);
	}
	
	public static double[] getForecastNext(TsaVO tsa, double[] observations) throws ServiceException, Exception {
		if (null == observations || observations.length < 1) {
			throw new IllegalArgumentException("observations array cannot zero size");
		}
		List<BbTsaMaCoefficients> coefficients = getCoefficients(tsa);
		if (coefficients == null || coefficients.size() != 3) {
			throw new IllegalStateException("Ma coefficients data error");
		}
		
		DefaultArimaProcess arimaProcess = new DefaultArimaProcess();
		arimaProcess.setMaCoefficients(coefficients.get(0).getSeqValue(), coefficients.get(1).getSeqValue(), coefficients.get(2).getSeqValue());
		arimaProcess.setIntegrationOrder( tsa.getIntegrationOrder() );
		
		ArimaForecaster arimaForecaster = new DefaultArimaForecaster(arimaProcess, observations);
		double[] forecast = arimaForecaster.next( tsa.getForecastNext() );
		
		return forecast;
	}
	
	public static VisionVO getResult(
			String tsaOid, 
			String visionOid, String startDate, String endDate, 
			String startYearDate, String endYearDate, String frequency, String dataFor, 
			String measureDataOrganizationOid, String measureDataEmployeeOid) throws ServiceException, Exception {
		
		ChainResultObj chainResult = PerformanceScoreChainUtils.getResult(
				visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid);
		if (chainResult.getValue() == null || ( (BscStructTreeObj)chainResult.getValue() ).getVisions() == null 
				|| ( (BscStructTreeObj)chainResult.getValue() ).getVisions().size() == 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		TsaVO tsa = getParam(tsaOid);
		BscStructTreeObj resultObj = (BscStructTreeObj)chainResult.getValue();
		VisionVO vision = resultObj.getVisions().get(0);
		
		// Vision
		double[] observations = new double[vision.getDateRangeScores().size()];
		for (int i=0; i < observations.length; i++) {
			observations[i] = Double.parseDouble( Float.toString(vision.getDateRangeScores().get(i).getScore()) );
		}
		double[] forecastNext = getForecastNext(tsa, observations);
		for (int i=0; i< forecastNext.length; i++) {
			vision.getForecastNext().add(forecastNext[i]);
		}
		
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			// Perspectives
			observations = new double[perspective.getDateRangeScores().size()];
			for (int i=0; i < observations.length; i++) {
				observations[i] = Double.parseDouble( Float.toString(perspective.getDateRangeScores().get(i).getScore()) );
			}
			forecastNext = getForecastNext(tsa, observations);
			for (int i=0; i< forecastNext.length; i++) {
				perspective.getForecastNext().add(forecastNext[i]);
			}
			
			for (ObjectiveVO objective : perspective.getObjectives()) {
				// Objectives
				observations = new double[objective.getDateRangeScores().size()];
				for (int i=0; i < observations.length; i++) {
					observations[i] = Double.parseDouble( Float.toString(objective.getDateRangeScores().get(i).getScore()) );
				}
				forecastNext = getForecastNext(tsa, observations);
				for (int i=0; i< forecastNext.length; i++) {
					objective.getForecastNext().add(forecastNext[i]);
				}
				
				for (KpiVO kpi : objective.getKpis()) {
					// KPIs
					observations = new double[kpi.getDateRangeScores().size()];
					for (int i=0; i < observations.length; i++) {
						observations[i] = Double.parseDouble( Float.toString(kpi.getDateRangeScores().get(i).getScore()) );
					}
					forecastNext = getForecastNext(tsa, observations);
					for (int i=0; i< forecastNext.length; i++) {
						kpi.getForecastNext().add(forecastNext[i]);
					}
					
				}
			}
		}
		
		return vision;
	}
	
	public static String getResultForExcel(
			String tsaOid, 
			String visionOid, String startDate, String endDate, 
			String startYearDate, String endYearDate, String frequency, String dataFor, 
			String measureDataOrganizationOid, String measureDataEmployeeOid,
			String visionDateRangeChartPngData,
			String perspectiveDateRangeChartPngData,
			String objectiveDateRangeChartPngData,
			String dateRangeChartPngData) throws ServiceException, Exception {
		
		VisionVO vision = getResult(
				tsaOid, visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid);
		TsaVO tsa = getParam(tsaOid);
		List<BbTsaMaCoefficients> coefficients = getCoefficients(tsa);
		Context context = new ContextBase();
		context.put("tsaVisionResult", vision);
		context.put("tsa", tsa);
		context.put("coefficients", coefficients);
		
		// for show only.
		context.put("visionName", vision.getTitle());
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)
				|| BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			context.put("date1", startYearDate);
			context.put("date2", endYearDate);
		} else {
			context.put("date1", startDate);
			context.put("date2", endDate);					
		}
		context.put("frequencyName", BscMeasureDataFrequency.getFrequencyMap(false).get(frequency));
		context.put("dataFor", dataFor);
		context.put("organizationName", "");
		context.put("employeeName", "");
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataOrganizationOid) && !StringUtils.isBlank(measureDataOrganizationOid)) {
			context.put("organizationName", BscBaseLogicServiceCommonSupport.findOrganizationData(organizationService, measureDataOrganizationOid).getName() );
		}
		if (!Constants.HTML_SELECT_NO_SELECT_ID.equals(measureDataEmployeeOid) && !StringUtils.isBlank(measureDataEmployeeOid)) {
			context.put("employeeName", BscBaseLogicServiceCommonSupport.findEmployeeData(employeeService, measureDataEmployeeOid).getFullName() );
		}
		context.put("visionDateRangeChartPngData", visionDateRangeChartPngData);
		context.put("perspectiveDateRangeChartPngData", perspectiveDateRangeChartPngData);
		context.put("objectiveDateRangeChartPngData", objectiveDateRangeChartPngData);
		context.put("dateRangeChartPngData", dateRangeChartPngData);
		
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("timeSeriesAnalysisExcelCommandContentChain", context);		
		if ( !(resultObj.getValue() instanceof String) ) {
			throw new java.lang.IllegalStateException( "timeSeriesAnalysisExcelCommandContentChain error!" );
		}
		return (String)resultObj.getValue();
	}	
	
}
