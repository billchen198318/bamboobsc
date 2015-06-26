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
package com.netsteadfast.greenstep.bsc.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.support.ScoreCalculationCallable;
import com.netsteadfast.greenstep.bsc.support.ScoreCalculationCallableData;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class KpiDateRangeScoreCommand extends BaseChainCommandSupport implements Command {
	/*
	private static final String QUARTER_1 = "Q1";
	private static final String QUARTER_2 = "Q2";
	private static final String QUARTER_3 = "Q3";
	private static final String QUARTER_4 = "Q4";
	private static final String HALF_YEAR_FIRST = "first";
	private static final String HALF_YEAR_LAST = "last";
	*/
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		String frequency = (String)context.get("frequency");
		String startYearDate = StringUtils.defaultString( (String)context.get("startYearDate") ).trim();
		String endYearDate = StringUtils.defaultString( (String)context.get("endYearDate") ).trim();
		String startDate = StringUtils.defaultString( (String)context.get("startDate") ).trim();
		String endDate = StringUtils.defaultString( (String)context.get("endDate") ).trim();
		//BscReportSupportUtils.loadExpression(); 2015-04-11 rem
		//long beg = System.currentTimeMillis();
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					// 2015-04-11 add
					ExecutorService kpiCalculationPool = 
							Executors.newFixedThreadPool( SimpleUtils.getAvailableProcessors(objective.getKpis().size()) );					
					for (KpiVO kpi : objective.getKpis()) {
						/* 2015-04-11 rem
						if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
								|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
								|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
							this.fillDateRangeMonth(kpi, frequency, startDate, endDate);
						} else {
							this.fillDateRangeYear(kpi, frequency, startYearDate, endYearDate);
						}
						*/
						
						// 2015-04-11 add
						ScoreCalculationCallableData data = new ScoreCalculationCallableData();
						data.setDefaultMode(false);
						data.setKpi(kpi);
						data.setFrequency(frequency);
						data.setDate1(startYearDate);
						data.setDate2(endYearDate);
						if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
								|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
								|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
							data.setDate1(startDate);
							data.setDate2(endDate);							
						}
						data = kpiCalculationPool.submit( new ScoreCalculationCallable(data) ).get();						
						
					}
					kpiCalculationPool.shutdown();
				}
			}
		}
		//long end = System.currentTimeMillis();
		//System.out.println( this.getClass().getName() + " use time(MS) = " + (end-beg) );
		return false;
	}
	
	/*
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
				dateRange.add( yyyy + "/" + QUARTER_1 );
				dateRange.add( yyyy + "/" + QUARTER_2 );
				dateRange.add( yyyy + "/" + QUARTER_3 );
				dateRange.add( yyyy + "/" + QUARTER_4 );
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
				dateRange.add( yyyy + "/" + HALF_YEAR_FIRST );
				dateRange.add( yyyy + "/" + HALF_YEAR_LAST );
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
	*/
	
//	private void calculateDateRangeScore(KpiVO kpi, String frequency) throws Exception {
//		// 2015-03-11 更換成以 bb_aggregation_method.EXPRESSION1 處理計算方式
//		/*
//		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
//			float score = 0.0f;
//			int size = 0;
//			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
//				String date = dateScore.getDate().replaceAll("/", "");
//				if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4
//					String yyyy = date.substring(0, 4);
//					if (date.endsWith(QUARTER_1)) {
//						if (!(yyyy+"0101").equals(measureData.getDate())) { // 一季
//							continue;
//						}
//					}
//					if (date.endsWith(QUARTER_2)) {
//						if (!(yyyy+"0401").equals(measureData.getDate())) { // 二季
//							continue;
//						}						
//					}
//					if (date.endsWith(QUARTER_3)) {
//						if (!(yyyy+"0701").equals(measureData.getDate())) { // 三季
//							continue;
//						}							
//					}
//					if (date.endsWith(QUARTER_4)) {
//						if (!(yyyy+"1001").equals(measureData.getDate())) { // 四季
//							continue;
//						}						
//					}					
//				} else if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last
//					String yyyy = date.substring(0, 4);
//					if (date.endsWith(HALF_YEAR_FIRST)) {
//						if (!(yyyy+"0101").equals(measureData.getDate())) { // 前半年
//							continue;
//						}						
//					}
//					if (date.endsWith(HALF_YEAR_LAST)) {
//						if (!(yyyy+"0701").equals(measureData.getDate())) { // 後半年
//							continue;
//						}							
//					}										
//				} else { // DAY, WEEK, MONTH, YEAR
//					if (!measureData.getDate().startsWith(date)) {
//						continue;
//					}					
//				}				
//				BscMeasureData data = new BscMeasureData();
//				data.setActual( measureData.getActual() );
//				data.setTarget( measureData.getTarget() );
//				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
//				if (value == null) {
//					continue;
//				}
//				if ( !(value instanceof Integer || value instanceof Float || value instanceof Long) ) {
//					continue;
//				}
//				score += NumberUtils.toFloat( String.valueOf(value), 0.0f);
//				size++;
//			}
//			if (BscKpiCode.CAL_AVERAGE.equals(kpi.getCal()) && score != 0.0f ) {
//				score = score / size;
//			}
//			dateScore.setScore(score);
//			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
//			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
//			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
//		}	
//		*/	
//		
//		// 2015-03-11 更換成以 bb_aggregation_method.EXPRESSION1 處理計算方式
//		AggregationMethodUtils.processDateRangeMode(kpi, frequency);
//		
//	}
	
	/*
	private void fillDateRangeYear(KpiVO kpi, String frequency, String date1, String date2) throws Exception {
		List<String> dateRange = this.getDateRange(frequency, date1, date2);
		this.initKpiDateRangeScores(kpi, dateRange);
		this.calculateDateRangeScore(kpi, frequency);
	}
	
	private void fillDateRangeMonth(KpiVO kpi, String frequency, String date1, String date2) throws Exception {
		List<String> dateRange = this.getDateRange(frequency, date1, date2);
		this.initKpiDateRangeScores(kpi, dateRange);
		this.calculateDateRangeScore(kpi, frequency);
	}
	*/
	
}
