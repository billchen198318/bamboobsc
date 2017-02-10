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
package com.netsteadfast.greenstep.bsc.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.TimeSeriesAnalysisResult;
import com.netsteadfast.greenstep.bsc.util.TimeSeriesAnalysisUtils;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.TsaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.TsaQueryForecastAction")
@Scope
public class TsaQueryForecastAction extends BaseJsonAction {
	private static final long serialVersionUID = -449705564005894371L;
	protected Logger logger = Logger.getLogger(TsaQueryForecastAction.class);
	private TsaVO tsa = new TsaVO();
	private List<Map<String, String>> coefficients = new LinkedList<Map<String, String>>();
	private List<String> categories = new LinkedList<String>(); // line chart 用的資料
	private List<Map<String, Object>> series = new LinkedList<Map<String, Object>>(); // line chart 用的資料	
	private String message = "";
	private String success = IS_NO;	
	
	public TsaQueryForecastAction() {
		super();
	}
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("tsaOid", SelectItemFieldCheckUtils.class, "Please select param!" )
		.add("visionOid", SelectItemFieldCheckUtils.class, "Please select vision!" )
		.add("frequency", SelectItemFieldCheckUtils.class, "Please select frequency!" )
		.process().throwMessage();
		
		String frequency = this.getFields().get("frequency");
		String startDate = this.getFields().get("startDate");
		String endDate = this.getFields().get("endDate");
		String startYearDate = this.getFields().get("startYearDate");
		String endYearDate = this.getFields().get("endYearDate");
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			if ( StringUtils.isBlank( startDate ) || StringUtils.isBlank( endDate ) ) {
				super.throwMessage("startDate|endDate", "Start-date and end-date is required!");			
			}
			if ( !StringUtils.isBlank( startDate ) || !StringUtils.isBlank( endDate ) ) {
				if ( !SimpleUtils.isDate( startDate ) ) {
					super.throwMessage("startDate", "Start-date format is incorrect!");
				}
				if ( !SimpleUtils.isDate( endDate ) ) {
					super.throwMessage("endDate", "End-date format is incorrect!");		
				}
				if ( Integer.parseInt( endDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startDate|endDate", "Start-date / end-date incorrect!");	
				}			
			}			
		}
		if ( BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			if ( StringUtils.isBlank( startYearDate ) || StringUtils.isBlank( endYearDate ) ) {
				super.throwMessage("startYearDate|endYearDate", "Start-year and end-year is required!");			
			}
			if ( !StringUtils.isBlank( startYearDate ) || !StringUtils.isBlank( endYearDate ) ) {
				if ( !SimpleUtils.isDate( startYearDate+"/01/01" ) ) {
					super.throwMessage("startYearDate", "Start-year format is incorrect!");		
				}
				if ( !SimpleUtils.isDate( endYearDate+"/01/01" ) ) {
					super.throwMessage("endYearDate", "End-year format is incorrect!");					
				}
				if ( Integer.parseInt( endYearDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startYearDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startYearDate|endYearDate", "Start-year / end-year incorrect!");	
				}					
			}			
		}		
		String dataFor = this.getFields().get("dataFor");
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureDataOrganizationOid")) ) {
			super.throwMessage("measureDataOrganizationOid", "Please select measure-data organization!");
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureDataEmployeeOid")) ) {
			super.throwMessage("measureDataEmployeeOid", "Please select measure-data employee!");
		}
	}		
	
	private void setDateValue() throws Exception {
		/**
		 * 周與月頻率的要調整區間日期
		 */
		String frequency = this.getFields().get("frequency");
		if (!BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				&& !BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			return;
		}
		String startDate = this.getFields().get("startDate");
		String endDate = this.getFields().get("endDate");
		Map<String, String> startEndDateMap = BscMeasureDataFrequency.getWeekOrMonthStartEnd(frequency, startDate, endDate);
		this.getFields().put("startDate", startEndDateMap.get("startDate"));
		this.getFields().put("endDate", startEndDateMap.get("endDate"));			
	}
	
	private void checkDateRange() throws ControllerException, Exception {
		String frequency = this.getFields().get("frequency");
		String startDate = this.defaultString( this.getFields().get("startDate") ).replaceAll("/", "-");
		String endDate = this.defaultString( this.getFields().get("endDate") ).replaceAll("/", "-");
		String startYearDate = this.defaultString( this.getFields().get("startYearDate") ).replaceAll("/", "-");
		String endYearDate = this.defaultString( this.getFields().get("endYearDate") ).replaceAll("/", "-");
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			int betweenMonths = Months.monthsBetween(dt1, dt2).getMonths();
			if ( betweenMonths >= 12 ) {
				super.throwMessage("startDate|endDate", "Date range can not be more than 12 months!");
			}
			return;
		}
		DateTime dt1 = new DateTime( startYearDate + "-01-01" ); 
		DateTime dt2 = new DateTime( endYearDate + "-01-01" );		
		int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			if ( betweenYears >= 3 ) {
				super.throwMessage("startYearDate|endYearDate", "Date range can not be more than 3 years!");			
			}
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			if ( betweenYears >= 4 ) {
				super.throwMessage("startYearDate|endYearDate", "Date range can not be more than 4 years!");		
			}			
		}
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency)) {
			if ( betweenYears >= 6 ) {
				super.throwMessage("startYearDate|endYearDate", "Date range can not be more than 6 years!");			
			}			
		}
	}	
	
	private void fillLineChartData(List<TimeSeriesAnalysisResult> results) throws Exception {
		this.tsa = TimeSeriesAnalysisUtils.getParam(this.getFields().get("tsaOid"));
		List<BbTsaMaCoefficients> coefficientsList = TimeSeriesAnalysisUtils.getCoefficients(this.tsa);
		for (BbTsaMaCoefficients maCoefficients : coefficientsList) {
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("seq", String.valueOf(maCoefficients.getSeq()) );
			dataMap.put("seqValue", String.valueOf(maCoefficients.getSeqValue()) );
			this.coefficients.add(dataMap);
		}
		
		// ==============================================================================
		// 產生 categories 資料
		KpiVO firstKpi = results.get(0).getKpi();
		for (DateRangeScoreVO dateRangeScore : firstKpi.getDateRangeScores()) {
			this.categories.add( dateRangeScore.getDate() );
		}
		List<Double> firstForecastNext = results.get(0).getForecastNext();
		for (int i=0; i < firstForecastNext.size(); i++) {
			this.categories.add( "next(" + (i+1) + ")" );
		}
		// ==============================================================================
		
		
		// ==============================================================================
		// 產生 series 資料
		for (int i=0; results != null && i < results.size(); i++) {
			KpiVO kpi = results.get(i).getKpi();
			List<Double> forecastNext = results.get(i).getForecastNext();
			Map<String, Object> mapData = new HashMap<String, Object>();
			List<Float> rangeScore = new LinkedList<Float>();	
			for (DateRangeScoreVO dateRangeScore : kpi.getDateRangeScores()) {
				rangeScore.add( dateRangeScore.getScore() );
			}
			for (int j=0; j < forecastNext.size(); j++) {
				rangeScore.add( Float.parseFloat( Double.toString(forecastNext.get(j)) ) );
			}				
			mapData.put("name", kpi.getName());
			mapData.put("data", rangeScore);
			this.series.add(mapData);		
		}
		// ==============================================================================
		
	}
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		List<TimeSeriesAnalysisResult> results = TimeSeriesAnalysisUtils.getResult(
				this.getFields().get("tsaOid"), 
				this.getFields().get("visionOid"), 
				this.getFields().get("startDate"), 
				this.getFields().get("endDate"), 
				this.getFields().get("startYearDate"), 
				this.getFields().get("endYearDate"), 
				this.getFields().get("frequency"), 
				this.getFields().get("dataFor"), 
				this.getFields().get("measureDataOrganizationOid"), 
				this.getFields().get("measureDataEmployeeOid"));
		this.fillLineChartData(results);
		this.success = IS_YES;
		this.message = "Success!";
	}
	
	/**
	 * bsc.tsaQueryForecastAction.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG007D0002Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}

	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
	@JSON
	public List<String> getCategories() {
		return categories;
	}

	@JSON
	public List<Map<String, Object>> getSeries() {
		return series;
	}

	@JSON
	public TsaVO getTsa() {
		return tsa;
	}

	@JSON
	public List<Map<String, String>> getCoefficients() {
		return coefficients;
	}	
	
}
