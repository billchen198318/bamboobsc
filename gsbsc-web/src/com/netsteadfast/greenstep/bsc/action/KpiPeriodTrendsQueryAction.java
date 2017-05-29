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
package com.netsteadfast.greenstep.bsc.action;

import java.util.ArrayList;
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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.PeriodTrendsData;
import com.netsteadfast.greenstep.bsc.util.PeriodTrendsCalUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.KpiVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiPeriodTrendsQueryAction")
@Scope
public class KpiPeriodTrendsQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -9053352575613414666L;
	protected Logger logger=Logger.getLogger(KpiPeriodTrendsQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private String uploadOid = "";
	private List<PeriodTrendsData<KpiVO>> periodData = new ArrayList<PeriodTrendsData<KpiVO>>();  
	
	public KpiPeriodTrendsQueryAction() {
		super();
	}
	
	private void checkFields(String p) throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("visionOid"+p, SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0007Q_visionOid") )
		.add("frequency"+p, SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0007Q_frequency") )
		.process().throwMessage();
		
		String frequency = this.getFields().get("frequency"+p);
		String startDate = this.getFields().get("startDate"+p);
		String endDate = this.getFields().get("endDate"+p);
		String startYearDate = this.getFields().get("startYearDate"+p);
		String endYearDate = this.getFields().get("endYearDate"+p);
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			if ( StringUtils.isBlank( startDate ) || StringUtils.isBlank( endDate ) ) {
				super.throwMessage("startDate"+p+"|"+"endDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg1"));		
			}
			if ( !StringUtils.isBlank( startDate ) || !StringUtils.isBlank( endDate ) ) {
				if ( !SimpleUtils.isDate( startDate ) ) {
					super.throwMessage("startDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg3"));
				}
				if ( !SimpleUtils.isDate( endDate ) ) {
					super.throwMessage("endDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg4"));		
				}
				if ( Integer.parseInt( endDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startDate"+p+"|"+"endDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg5"));		
				}			
			}			
		}
		if ( BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			if ( StringUtils.isBlank( startYearDate ) || StringUtils.isBlank( endYearDate ) ) {
				super.throwMessage("startYearDate"+p+"|"+"endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg2"));		
			}	
			if ( !StringUtils.isBlank( startYearDate ) || !StringUtils.isBlank( endYearDate ) ) {
				if ( !SimpleUtils.isDate( startYearDate+"/01/01" ) ) {
					super.throwMessage("startYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg6"));				
				}
				if ( !SimpleUtils.isDate( endYearDate+"/01/01" ) ) {
					super.throwMessage("endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg7"));					
				}
				if ( Integer.parseInt( endYearDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startYearDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startYearDate"+p+"|"+"endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg8"));		
				}					
			}			
		}		
		String dataFor = this.getFields().get("dataFor"+p);
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureDataOrganizationOid"+p)) ) {
			super.throwMessage("measureDataOrganizationOid"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg9"));
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureDataEmployeeOid"+p)) ) {
			super.throwMessage("measureDataEmployeeOid"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg10"));
		}
	}		
	
	private void setDateValue(String p) throws Exception {
		/**
		 * 周與月頻率的要調整區間日期
		 */
		String frequency = this.getFields().get("frequency"+p);
		if (!BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				&& !BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			return;
		}
		
		String startDate = this.getFields().get("startDate"+p);
		String endDate = this.getFields().get("endDate"+p);
		Map<String, String> startEndDateMap = BscMeasureDataFrequency.getWeekOrMonthStartEnd(frequency, startDate, endDate);
		this.getFields().put("startDate"+p, startEndDateMap.get("startDate"));
		this.getFields().put("endDate"+p, startEndDateMap.get("endDate"));			
		
		/*
		String startDate = this.getFields().get("startDate"+p);
		String endDate = this.getFields().get("endDate"+p);
		if (BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency)) {
			int firstDay = Integer.parseInt( startDate.substring(startDate.length()-2, startDate.length()) );
			int endDay = Integer.parseInt( endDate.substring(endDate.length()-2, endDate.length()) );
			if (firstDay>=1 && firstDay<8) {
				firstDay = 1;
			}
			if (firstDay>=8 && firstDay<15) {
				firstDay = 8;
			}
			if (firstDay>=15 && firstDay<22) {
				firstDay = 15;
			}
			if (firstDay>=22) { 
				firstDay = 22;
			}
			if (endDay>=1 && endDay<8) {
				endDay = 7;
			}
			if (endDay>=8 && endDay<15) {
				endDay = 14;
			}
			if (endDay>=15 && endDay<22) {
				endDay = 21;
			}
			if (endDay>=22) { 
				endDay = SimpleUtils.getMaxDayOfMonth( 
						Integer.parseInt(endDate.substring(0, 4)), 
						Integer.parseInt(endDate.substring(5, 7)) );
			}
			String newStartDate = startDate.substring(0, startDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(firstDay), 2, "0");
			String newEndDate = endDate.substring(0, endDate.length()-2)
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");
			this.getFields().put("startDate"+p, newStartDate);
			this.getFields().put("endDate"+p, newEndDate);
		}
		if (BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency)) {
			int endDay = SimpleUtils.getMaxDayOfMonth( Integer.parseInt(endDate.substring(0, 4)), 
					Integer.parseInt(endDate.substring(5, 7)) );
			String newStartDate = startDate.substring(0, startDate.length()-2) + "01";
			String newEndDate = endDate.substring(0, endDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");			
			this.getFields().put("startDate"+p, newStartDate);
			this.getFields().put("endDate"+p, newEndDate);			
		}
		*/
	}
	
	private void checkDateRange(String p) throws ControllerException, Exception {
		String frequency = this.getFields().get("frequency"+p);
		String startDate = this.defaultString( this.getFields().get("startDate"+p) ).replaceAll("/", "-");
		String endDate = this.defaultString( this.getFields().get("endDate"+p) ).replaceAll("/", "-");
		String startYearDate = this.defaultString( this.getFields().get("startYearDate"+p) ).replaceAll("/", "-");
		String endYearDate = this.defaultString( this.getFields().get("endYearDate"+p) ).replaceAll("/", "-");
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			int betweenMonths = Months.monthsBetween(dt1, dt2).getMonths();
			if ( betweenMonths >= 12 ) {
				super.throwMessage("startDate"+p+"|"+"endDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg11"));
			}
			return;
		}
		DateTime dt1 = new DateTime( startYearDate + "-01-01" ); 
		DateTime dt2 = new DateTime( endYearDate + "-01-01" );		
		int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			if ( betweenYears >= 3 ) {
				super.throwMessage("startYearDate"+p+"|"+"endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg12"));			
			}
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			if ( betweenYears >= 4 ) {
				super.throwMessage("startYearDate"+p+"|"+"endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg13"));			
			}			
		}
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency)) {
			if ( betweenYears >= 6 ) {
				super.throwMessage("startYearDate"+p+"|"+"endYearDate"+p, this.getText("MESSAGE.BSC_PROG003D0007Q_contentQuery_msg14"));		
			}			
		}
	}	
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("1");
		this.checkFields("2");
		this.setDateValue("1");
		this.setDateValue("2");
		this.checkDateRange("1");
		this.checkDateRange("2");
		this.periodData = PeriodTrendsCalUtils.getKpiScoreChange(
				this.getFields().get("visionOid1"), 
				this.getFields().get("startDate1"), 
				this.getFields().get("endDate1"), 
				this.getFields().get("startYearDate1"), 
				this.getFields().get("endYearDate1"), 
				this.getFields().get("frequency1"), 
				this.getFields().get("dataFor1"), 
				this.getFields().get("measureDataOrganizationOid1"), 
				this.getFields().get("measureDataEmployeeOid1"), 
				this.getFields().get("visionOid2"), 
				this.getFields().get("startDate2"), 
				this.getFields().get("endDate2"), 
				this.getFields().get("startYearDate2"), 
				this.getFields().get("endYearDate2"), 
				this.getFields().get("frequency2"), 
				this.getFields().get("dataFor2"), 
				this.getFields().get("measureDataOrganizationOid2"), 
				this.getFields().get("measureDataEmployeeOid2"));
		this.body = PeriodTrendsCalUtils.renderKpiPeriodTrendsBody(
				this.periodData, 
				PeriodTrendsCalUtils.getDateRange(
						this.getFields().get("frequency1"), this.getFields().get("startYearDate1"), this.getFields().get("endYearDate1"), 
						this.getFields().get("startDate1"), this.getFields().get("endDate1")), 
				PeriodTrendsCalUtils.getDateRange(
						this.getFields().get("frequency2"), this.getFields().get("startYearDate2"), this.getFields().get("endYearDate2"), 
						this.getFields().get("startDate2"), this.getFields().get("endDate2")));
		this.success = IS_YES;
		this.message = "success!";
		if (this.periodData!=null && this.periodData.size()>0) {
			if (!YesNo.YES.equals(this.periodData.get(0).getCanChart())) {		
				this.message = super.joinPageMessage(
						"Please select the same frequency and range. It will display the chart.", 
						"Example:",
						"Current pereiod: frequency is YEAR, Date is 2013 - 2015",
						"Previous pereiod: frequency is YEAR, Date is 2012 - 2014",
						"Or",
						"Current pereiod: frequency is month, Date is 2015/01/01 - 2015/04/30",
						"Previous pereiod: frequency is month, Date is 2014/01/01 - 2014/04/30");
			}
		}		
	}
	
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("1");
		this.checkFields("2");
		this.setDateValue("1");
		this.setDateValue("2");
		this.checkDateRange("1");
		this.checkDateRange("2");
		this.uploadOid = PeriodTrendsCalUtils.generateKpiPeriodTrendsExcel(
				this.getFields().get("visionOid1"), 
				this.getFields().get("startDate1"), 
				this.getFields().get("endDate1"), 
				this.getFields().get("startYearDate1"), 
				this.getFields().get("endYearDate1"), 
				this.getFields().get("frequency1"), 
				this.getFields().get("dataFor1"), 
				this.getFields().get("measureDataOrganizationOid1"), 
				this.getFields().get("measureDataEmployeeOid1"), 
				this.getFields().get("visionOid2"), 
				this.getFields().get("startDate2"), 
				this.getFields().get("endDate2"), 
				this.getFields().get("startYearDate2"), 
				this.getFields().get("endYearDate2"), 
				this.getFields().get("frequency2"), 
				this.getFields().get("dataFor2"), 
				this.getFields().get("measureDataOrganizationOid2"), 
				this.getFields().get("measureDataEmployeeOid2"));
		this.success = IS_YES;
		this.message = "success!";
	}
	
	/**
	 * bsc.kpiPeriodTrendsQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0007Q")
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
	 * bsc.kpiPeriodTrendsExcelQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0007Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
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
	public String getBody() {
		return body;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}

	@JSON
	public List<PeriodTrendsData<KpiVO>> getPeriodData() {
		return periodData;
	}	
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
