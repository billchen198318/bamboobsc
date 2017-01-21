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
package com.netsteadfast.greenstep.bsc.support;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;

import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.util.AggregationMethod;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;

public class ScoreCalculationCallable implements Callable<ScoreCalculationCallableData> {
	/*
	private static final String QUARTER_1 = "Q1";
	private static final String QUARTER_2 = "Q2";
	private static final String QUARTER_3 = "Q3";
	private static final String QUARTER_4 = "Q4";
	private static final String HALF_YEAR_FIRST = "first";
	private static final String HALF_YEAR_LAST = "last";
	*/
	private ScoreCalculationCallableData data = null;
	
	public ScoreCalculationCallable(ScoreCalculationCallableData data) {
		super();
		this.data = data;
	}

	@Override
	public ScoreCalculationCallableData call() throws Exception {
		BscScoreColorUtils.loadScoreColors();
		//BscReportSupportUtils.loadExpression(); // 這邊會用到與 aggregation method 的 expression 也會用到
		if ( this.data.isDefaultMode() ) { // KPI分數
			float score = AggregationMethodUtils.processDefaultMode( this.data.getKpi() );
			this.data.getKpi().setScore(score);
			this.data.getKpi().setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			this.data.getKpi().setFontColor( BscScoreColorUtils.getFontColor(score) );	
			/*
			this.data.getKpi().setImgIcon( 
					BscReportSupportUtils.getHtmlIconBase(
							"KPI", 
							this.data.getKpi().getTarget(), 
							this.data.getKpi().getMin(), 
							score, 
							this.data.getKpi().getCompareType(), 
							this.data.getKpi().getManagement(), 
							this.data.getKpi().getQuasiRange())
			);
			*/
		} else { // KPI 日期區間分數
			if (BscMeasureDataFrequency.FREQUENCY_DAY.equals( this.data.getFrequency() ) 
					|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals( this.data.getFrequency() ) 
					|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals( this.data.getFrequency() ) ) {
				this.fillDateRangeMonth(
						this.data.getKpi(), 
						this.data.getFrequency(), 
						this.data.getDate1(), 
						this.data.getDate2());
			} else {
				this.fillDateRangeYear(
						this.data.getKpi(), 
						this.data.getFrequency(), 
						this.data.getDate1(), 
						this.data.getDate2());
			}			
		}
		return this.data;
	}

	public ScoreCalculationCallableData getData() {
		return data;
	}

	public void setData(ScoreCalculationCallableData data) {
		this.data = data;
	}
	
	// -------------------------------------------------------------------------------
	
	private void fillDateRangeYear(KpiVO kpi, String frequency, String date1, String date2) throws Exception {
		List<String> dateRange = this.getDateRange(frequency, date1, date2);
		this.initKpiDateRangeScores(kpi, dateRange);
		AggregationMethodUtils.processDateRangeMode(kpi, frequency);
	}
	
	private void fillDateRangeMonth(KpiVO kpi, String frequency, String date1, String date2) throws Exception {
		List<String> dateRange = this.getDateRange(frequency, date1, date2);
		this.initKpiDateRangeScores(kpi, dateRange);
		AggregationMethodUtils.processDateRangeMode(kpi, frequency);
	}
	
	private List<String> getDateRange(String frequency, String date1, String date2) throws Exception {
		List<String> dateRange = new LinkedList<String>();
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			DateTime dt1 = new DateTime( date1.replaceAll("/", "-").substring( 0, date1.length()-2 ) + "01" ); 
			DateTime dt2 = new DateTime( date2.replaceAll("/", "-").substring( 0, date2.length()-2 ) + "01" );
			int betweenMonths = Months.monthsBetween(dt1, dt2).getMonths();
			Date nowDate = dt1.toDate();
			for (int i=0; i<=betweenMonths; i++) {
				dateRange.add( SimpleUtils.getStrYMD(nowDate, "/").substring(0, 7) );
				nowDate = DateUtils.addMonths(nowDate, 1);
			}			
			return dateRange;
		} 
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			DateTime dt1 = new DateTime( date1 + "-01-01" ); 
			DateTime dt2 = new DateTime( date2 + "-01-01" );
			int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
			Date nowDate = dt1.toDate();
			for (int i=0; i<=betweenYears; i++) {
				String yyyy = SimpleUtils.getStrYMD(nowDate, "/").substring(0, 4);				
				dateRange.add( yyyy + "/" + AggregationMethod.QUARTER_1 );
				dateRange.add( yyyy + "/" + AggregationMethod.QUARTER_2 );
				dateRange.add( yyyy + "/" + AggregationMethod.QUARTER_3 );
				dateRange.add( yyyy + "/" + AggregationMethod.QUARTER_4 );
				nowDate = DateUtils.addYears(nowDate, 1);
			}
			return dateRange;
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			DateTime dt1 = new DateTime( date1 + "-01-01" ); 
			DateTime dt2 = new DateTime( date2 + "-01-01" );
			int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
			Date nowDate = dt1.toDate();
			for (int i=0; i<=betweenYears; i++) {
				String yyyy = SimpleUtils.getStrYMD(nowDate, "/").substring(0, 4);				
				dateRange.add( yyyy + "/" + AggregationMethod.HALF_YEAR_FIRST );
				dateRange.add( yyyy + "/" + AggregationMethod.HALF_YEAR_LAST );
				nowDate = DateUtils.addYears(nowDate, 1);
			}
			return dateRange;
		}		
		int begYear = Integer.parseInt( date1.substring(0, 4) );
		int endYear = Integer.parseInt( date2.substring(0, 4) );
		for (int i=begYear; i<=endYear; i++) {
			dateRange.add( String.valueOf(i) );
		}
		return dateRange;		
	}
	
	private void initKpiDateRangeScores(KpiVO kpi, List<String> dateRange) throws Exception {
		for (String date : dateRange) {
			DateRangeScoreVO dateScore = new DateRangeScoreVO();
			dateScore.setTarget(kpi.getTarget());
			dateScore.setMin(kpi.getMin());
			dateScore.setDate(date);
			dateScore.setScore(0.0f);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(0.0f) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(0.0f) );
			dateScore.setImgIcon("");
			kpi.getDateRangeScores().add(dateScore);			
		}
	}	
	
	// -------------------------------------------------------------------------------
	
}
