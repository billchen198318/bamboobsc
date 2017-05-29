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
package com.netsteadfast.greenstep.bsc.mobile.action;

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

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.util.BscMobileCardUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=false)
@Controller("bsc.mobile.web.controller.ScorecardQueryContentAction")
@Scope
public class ScorecardQueryContentAction extends BaseJsonAction {
	private static final long serialVersionUID = -2274317664693119886L;
	protected Logger logger=Logger.getLogger(ScorecardQueryContentAction.class);
	private String message = "";
	private String success = IS_NO;
	private String content = "";	
	private String backgroundColor = "#ffffff";
	private String fontColor = "#000000";	
	private String perspectiveTitle = "";
	private String objectiveTitle = "";
	private VisionVO rootVision = new VisionVO();
	private PerspectiveVO rootPerspective = new PerspectiveVO();
	private ObjectiveVO rootObjective = new ObjectiveVO();
	
	// 給 Dashboard 頁面 trend line chart 用的資料
	private List<String> categories = new LinkedList<String>();
	private List<Map<String, Object>> series = new LinkedList<Map<String, Object>>();
	
	// copy from KpiReportContentQueryAction
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
		/*
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
			this.getFields().put("startDate", newStartDate);
			this.getFields().put("endDate", newEndDate);
		}
		if (BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency)) {
			int endDay = SimpleUtils.getMaxDayOfMonth( Integer.parseInt(endDate.substring(0, 4)), 
					Integer.parseInt(endDate.substring(5, 7)) );
			String newStartDate = startDate.substring(0, startDate.length()-2) + "01";
			String newEndDate = endDate.substring(0, endDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");			
			this.getFields().put("startDate", newStartDate);
			this.getFields().put("endDate", newEndDate);			
		}
		*/
	}
	
	private void checkDateRange() throws ControllerException, Exception {
		String date1 = "";
		String date2 = "";
		String ver = super.defaultString( this.getFields().get("ver") );
		if (!"newVer".equals(ver)) {
			// mm/dd/yyyy to yyyy/mm/dd
			String date1Tmp[] = super.defaultString( this.getFields().get("date1") ).split("/");
			String date2Tmp[] = super.defaultString( this.getFields().get("date2") ).split("/");
			if (date1Tmp==null || date1Tmp.length!=3 || date2Tmp==null || date2Tmp.length!=3) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_01"));
			}
			date1= date1Tmp[2] + "/" + date1Tmp[0] + "/" + date1Tmp[1];
			date2= date2Tmp[2] + "/" + date2Tmp[0] + "/" + date2Tmp[1];
			if (!SimpleUtils.isDate(date1) || !SimpleUtils.isDate(date2)) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_01"));
			}		
		} else {
			// NEW version page 新版網頁 , yyyy/MM/dd
			date1 = StringUtils.defaultString( this.getFields().get("date1") ).replaceAll("-", "/");
			date2 = StringUtils.defaultString( this.getFields().get("date2") ).replaceAll("-", "/");
			if (!SimpleUtils.isDate(date1) || !SimpleUtils.isDate(date2)) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_01"));
			}
			if ( Integer.parseInt(date1.replaceAll("-", "").replaceAll("/", "")) > Integer.parseInt(date2.replaceAll("-", "").replaceAll("/", "")) ) {
				throw new ControllerException( "Date range: " + date1 + " to " + date2 + " error!" );
			}				
		}
		
		this.getFields().put("startDate", date1);
		this.getFields().put("endDate", date2);
		
		// copy from KpiReportContentQueryAction.java
		String frequency = this.getFields().get("frequency");
		String startDate = this.defaultString( this.getFields().get("startDate") ).replaceAll("/", "-");
		String endDate = this.defaultString( this.getFields().get("endDate") ).replaceAll("/", "-");
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			int betweenMonths = Months.monthsBetween(dt1, dt2).getMonths();
			if ( betweenMonths >= 12 ) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_02"));	
			}
			return;
		}
		DateTime dt1 = new DateTime( startDate.substring(0, 4) + "-01-01" ); 
		DateTime dt2 = new DateTime( endDate.substring(0, 4) + "-01-01" );		
		int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			if ( betweenYears >= 3 ) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_03"));				
			}
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			if ( betweenYears >= 4 ) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_04"));				
			}			
		}
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency)) {
			if ( betweenYears >= 6 ) {
				throw new ControllerException(this.getText("MESSAGE.ScorecardQueryContentAction_05"));				
			}			
		}
	}
	
	private void loadVisionCardContent() throws ServiceException, Exception {
		this.checkDateRange();
		this.setDateValue();
		StringBuilder outContent = new StringBuilder();
		String frequency = this.getFields().get("frequency");
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
		List<VisionVO> visionScores = BscMobileCardUtils.getVisionCard(
				frequency, this.getFields().get("startDate"), this.getFields().get("endDate"));
		String dateStr1 = this.getFields().get("startDate");
		String dateStr2 = this.getFields().get("endDate");		
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)
				|| BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) ) {
			dateStr1 = dateStr1.substring(0, 4);
			dateStr2 = dateStr2.substring(0, 4);
		}
		for (VisionVO vision : visionScores) {			
			outContent.append( BscMobileCardUtils.getVisionCardContent(
					vision, BscMeasureDataFrequency.getFrequencyMap(false).get(frequency), dateStr1, dateStr2) );
			outContent.append(super.getHtmlBr());
		}
		this.content = outContent.toString();		
		if (!StringUtils.isBlank(content)) {
			this.message = this.getText("MESSAGE.ScorecardQueryContentAction_06");
			this.success = IS_YES;
		}		
	}
	
	private void loadColor() throws ServiceException, Exception {
		BscReportPropertyUtils.loadData();
		this.backgroundColor = BscReportPropertyUtils.getBackgroundColor();
		this.fontColor = BscReportPropertyUtils.getFontColor();
		this.perspectiveTitle = BscReportPropertyUtils.getPerspectiveTitle();
		this.objectiveTitle = BscReportPropertyUtils.getObjectiveTitle();		
	}
	
	private void loadPerspectiveCardContent() throws ServiceException, Exception {
		StringBuilder outContent = new StringBuilder();
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
		String uploadOid = this.getFields().get("uploadOid");
		VisionVO vision = BscMobileCardUtils.getVisionCardFromUpload(uploadOid);
		this.rootVision = vision;
		List<PerspectiveVO> perspectives = vision.getPerspectives();
		for (PerspectiveVO perspective : perspectives) {
			outContent.append( BscMobileCardUtils.getPerspectivesCardContent(uploadOid, perspective) );
			outContent.append(super.getHtmlBr());
		}
		this.content = outContent.toString();
		if (!StringUtils.isBlank(content)) {
			this.loadColor();
			this.message = this.getText("MESSAGE.ScorecardQueryContentAction_06");
			this.success = IS_YES;
		}		
	}
	
	private void loadObjectiveCardContent() throws ServiceException, Exception {
		StringBuilder outContent = new StringBuilder();
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
		String uploadOid = this.getFields().get("uploadOid");
		String perspectiveOid = super.defaultString( this.getFields().get("perspectiveOid") );
		VisionVO vision = BscMobileCardUtils.getVisionCardFromUpload(uploadOid);
		List<PerspectiveVO> perspectives = vision.getPerspectives();
		for (PerspectiveVO perspective : perspectives) {
			if (perspectiveOid.equals(perspective.getOid())) {
				this.rootPerspective = perspective;
				List<ObjectiveVO> objectives = perspective.getObjectives();
				for (ObjectiveVO objective : objectives) {
					outContent.append( BscMobileCardUtils.getObjectivesCardContent(uploadOid, objective) );
					outContent.append(super.getHtmlBr());
				}
			}
		}
		this.content = outContent.toString();
		if (!StringUtils.isBlank(content)) {
			this.loadColor();
			this.message = this.getText("MESSAGE.ScorecardQueryContentAction_06");
			this.success = IS_YES;
		}		
	}
	
	private void loadKpiCardContent() throws ServiceException, Exception {
		StringBuilder outContent = new StringBuilder();
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
		String uploadOid = this.getFields().get("uploadOid");
		String objectiveOid = super.defaultString( this.getFields().get("objectiveOid") );
		VisionVO vision = BscMobileCardUtils.getVisionCardFromUpload(uploadOid);
		List<PerspectiveVO> perspectives = vision.getPerspectives();
		for (PerspectiveVO perspective : perspectives) {
			List<ObjectiveVO> objectives = perspective.getObjectives();
			for (ObjectiveVO objective : objectives) {
				if (objectiveOid.equals(objective.getOid())) {
					this.rootPerspective = perspective;
					this.rootObjective = objective;
					List<KpiVO> kpis = objective.getKpis();
					for (KpiVO kpi : kpis) {
						outContent.append( BscMobileCardUtils.getKPIsCardContent(uploadOid, kpi) );
						outContent.append(super.getHtmlBr());						
					}
				}
			}
		}
		this.content = outContent.toString();
		if (!StringUtils.isBlank(content)) {
			this.loadColor();
			this.message = this.getText("MESSAGE.ScorecardQueryContentAction_06");
			this.success = IS_YES;
		}		
	}
	
	private void loadDashboardData() throws ControllerException, ServiceException, Exception {
		if (super.isNoSelectId(this.getFields().get("visionOid"))) {
			throw new ControllerException("Please select vision!");
		}
		this.checkDateRange();
		this.setDateValue();
		String startDate = super.defaultString( this.getFields().get("startDate") ).replaceAll("/", "").replaceAll("-", "");
		String endDate = super.defaultString( this.getFields().get("endDate") ).replaceAll("/", "").replaceAll("-", "");
		this.rootVision = BscMobileCardUtils.getDashboardScore(
				this.getFields().get("visionOid"), 
				this.getFields().get("frequency"), 
				startDate, 
				endDate);
		this.handlerDashboardChartData();
		this.message = this.getText("MESSAGE.ScorecardQueryContentAction_06");
		this.success = IS_YES;
	}
	
	private void handlerDashboardChartData() throws Exception {
		if (this.rootVision == null) {
			return;
		}
		// 準備要顯示 highcharts 要用的資料
		
		// 給 Dashboard 頁面 trend line chart 用的資料
		int c = 0;
		for (PerspectiveVO perspective : this.rootVision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					Map<String, Object> mapData = new HashMap<String, Object>();
					List<Float> rangeScore = new LinkedList<Float>();
					for (DateRangeScoreVO dateRangeScore : kpi.getDateRangeScores()) {
						if (c == 0) { // 用第1筆的資料來組  categories 就可已了
							categories.add( dateRangeScore.getDate() );
						}
						rangeScore.add( dateRangeScore.getScore() );
					}					
					mapData.put("name", kpi.getName());
					mapData.put("data", rangeScore);
					this.series.add(mapData);
					c++;
				}				
			}
		}
		
	}
	
	@JSON(serialize=false)
	public String doVisionCard() throws Exception {
		try {
			this.loadVisionCardContent();			
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}
	
	@JSON(serialize=false)
	public String doPerspectiveCard() throws Exception {
		try {
			this.loadPerspectiveCardContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}
	
	@JSON(serialize=false)
	public String doObjectiveCard() throws Exception {
		try {
			this.loadObjectiveCardContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}	
	
	@JSON(serialize=false)
	public String doKpiCard() throws Exception {
		try {
			this.loadKpiCardContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}	
	
	@JSON(serialize=false)
	public String doDashboard() throws Exception {
		try {
			this.loadDashboardData();
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
	public String getContent() {
		return content;
	}

	@JSON
	public String getBackgroundColor() {
		return backgroundColor;
	}

	@JSON
	public String getFontColor() {
		return fontColor;
	}

	@JSON
	public String getPerspectiveTitle() {
		return perspectiveTitle;
	}

	@JSON
	public String getObjectiveTitle() {
		return objectiveTitle;
	}

	@JSON
	public VisionVO getRootVision() {
		return rootVision;
	}

	@JSON
	public PerspectiveVO getRootPerspective() {
		return rootPerspective;
	}

	@JSON
	public ObjectiveVO getRootObjective() {
		return rootObjective;
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
		
}
