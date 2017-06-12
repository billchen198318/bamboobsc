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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.TimeSeriesAnalysisResult;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.TimeSeriesAnalysisUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.TsaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.TsaQueryForecastAction")
@Scope
public class TsaQueryForecastAction extends BaseJsonAction {
	private static final long serialVersionUID = -449705564005894371L;
	protected Logger logger = Logger.getLogger(TsaQueryForecastAction.class);
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;		
	private TsaVO tsa = new TsaVO();
	private TsaMeasureFreqVO measureFreq = new TsaMeasureFreqVO();
	private List<Map<String, String>> coefficients = new LinkedList<Map<String, String>>();
	private List<String> categories = new LinkedList<String>(); // line chart 用的資料
	private List<Map<String, Object>> series = new LinkedList<Map<String, Object>>(); // line chart 用的資料	
	private String uploadOid = "";
	private String message = "";
	private String success = IS_NO;	
	
	/**
	 * 今天接到家裏的電話，覺的自己很不孝順，都34歲了，一事無成，到台北工作沒賺到錢，
	 * 也沒有女友，也沒房子車子，什麼都沒有，雖然平常沒亂花錢，生活簡樸，但工作薪水不高，
	 * 如果這樣一直下去，完全沒人生希望了，覺的對生活很無奈... Orz...
	 * 
	 */
	
	public TsaQueryForecastAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}	
	
	@Autowired
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	@JSON(serialize=false)
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
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
	
	private void getContentForExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		this.uploadOid = TimeSeriesAnalysisUtils.getResultForExcel(
				this.getFields().get("tsaOid"), 
				this.getFields().get("visionOid"), 
				this.getFields().get("startDate"), 
				this.getFields().get("endDate"), 
				this.getFields().get("startYearDate"), 
				this.getFields().get("endYearDate"), 
				this.getFields().get("frequency"), 
				this.getFields().get("dataFor"), 
				this.getFields().get("measureDataOrganizationOid"), 
				this.getFields().get("measureDataEmployeeOid"),
				this.getFields().get("dateRangeChartPngData"));
		this.success = IS_YES;
		this.message = "Success!";		
	}
	
	private void fetchParamMeasureFreqData() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.getCheckFieldHandler()
		.add("tsaOid", SelectItemFieldCheckUtils.class, "Please select param!" )
		.process().throwMessage();
		this.tsa = TimeSeriesAnalysisUtils.getParam(this.getFields().get("tsaOid"));
		this.measureFreq = TimeSeriesAnalysisUtils.getMeasureFreq( this.tsa );
		this.measureFreq.setEmployeeOid("");
		if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(this.measureFreq.getEmpId()) && !StringUtils.isBlank(this.measureFreq.getEmpId())) {
			
			EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(this.employeeService, this.measureFreq.getEmpId());
			this.measureFreq.setEmployeeOid(employee.getOid());
			
		}
		this.measureFreq.setOrganizationOid("");
		if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(this.measureFreq.getOrgId()) && !StringUtils.isBlank(this.measureFreq.getOrgId())) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOrgId(this.measureFreq.getOrgId());
			DefaultResult<OrganizationVO> orgResult = this.organizationService.findByUK(organization);
			if (orgResult.getValue()!=null) {
				this.measureFreq.setOrganizationOid(orgResult.getValue().getOid());
			}
		}		
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * bsc.tsaParamMeasureFreqDataAction.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG007D0002Q")
	public String loadParamMeasureFreq() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.fetchParamMeasureFreqData();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.tsaQueryForecastForExcelAction.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG007D0002Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContentForExcel();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
	public TsaMeasureFreqVO getMeasureFreq() {
		return measureFreq;
	}

	@JSON
	public List<Map<String, String>> getCoefficients() {
		return coefficients;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}	
	
}
