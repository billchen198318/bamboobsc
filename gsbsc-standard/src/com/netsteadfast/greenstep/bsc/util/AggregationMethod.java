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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.bsc.model.BscMeasureData;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;

public class AggregationMethod {
	public static final String QUARTER_1 = "Q1";
	public static final String QUARTER_2 = "Q2";
	public static final String QUARTER_3 = "Q3";
	public static final String QUARTER_4 = "Q4";
	public static final String HALF_YEAR_FIRST = "first";
	public static final String HALF_YEAR_LAST = "last";
	
	public AggregationMethod() {
		
	}
	
	public static AggregationMethod build() throws InstantiationException, IllegalAccessException {
		return AggregationMethod.class.newInstance();
	}
	
	private boolean isDateRange(String date, String frequency, BbMeasureData measureData) {
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4
			String yyyy = date.substring(0, 4);
			if (date.endsWith(QUARTER_1)) {
				if (!(yyyy+"0101").equals(measureData.getDate())) { // Q1
					return false;
				}
			}
			if (date.endsWith(QUARTER_2)) {
				if (!(yyyy+"0401").equals(measureData.getDate())) { // Q2
					return false;
				}						
			}
			if (date.endsWith(QUARTER_3)) {
				if (!(yyyy+"0701").equals(measureData.getDate())) { // Q3
					return false;
				}							
			}
			if (date.endsWith(QUARTER_4)) {
				if (!(yyyy+"1001").equals(measureData.getDate())) { // Q4
					return false;
				}						
			}					
		} else if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last
			String yyyy = date.substring(0, 4);
			if (date.endsWith(HALF_YEAR_FIRST)) {
				if (!(yyyy+"0101").equals(measureData.getDate())) { // first-half
					return false;
				}						
			}
			if (date.endsWith(HALF_YEAR_LAST)) {
				if (!(yyyy+"0701").equals(measureData.getDate())) { // last-half
					return false;
				}							
			}										
		} else { // DAY, WEEK, MONTH, YEAR
			if (!measureData.getDate().startsWith(date)) {
				return false;
			}					
		}			
		return true;
	}
	
	public float average(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		float score = 0.0f; // init zero
		int size = 0;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				score += NumberUtils.toFloat(String.valueOf(value), 0.0f);
				size++;
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		if ( score != 0.0f && size > 0 ) {
			score = score / size;
		}
		return score;
	}
	
	public void averageDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			float score = 0.0f;
			int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					score += NumberUtils.toFloat( String.valueOf(value), 0.0f);
					size++;						
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			if ( score != 0.0f && size > 0 ) {
				score = score / size;
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}
	}
	
	public float averageDistinct(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		List<Float> scores = new ArrayList<Float>();
		float score = 0.0f; // init zero
		int size = 0;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
		      	if ( !scores.contains(nowScore) ) {
					scores.add( nowScore );
					score += nowScore;
					size++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ( score != 0.0f && size>0 ) {
			score = score / size;
		}		
		return score;
	}
	
	public void averageDistinctDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			List<Float> scores = new ArrayList<Float>();
			float score = 0.0f;
			int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
			      	if ( !scores.contains(nowScore) ) {
						scores.add( nowScore );
						score += nowScore;
						size++;
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ( score != 0.0f && size>0 ) {
				score = score / size;
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}
	}
	
	public float count(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		return Float.valueOf( measureDatas.size() );
	}
	
	public void countDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			float score = 0.0f;
			int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				size++;
			}
		    score = Float.valueOf(size);
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}
	}
	
	public float countDistinct(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		List<Float> scores = new ArrayList<Float>();
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
		      	if ( !scores.contains(nowScore) ) {
					scores.add( nowScore );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return Float.valueOf( scores.size() );
	}
	
	public void countDistinctDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			List<Float> scores = new ArrayList<Float>();
			float score = 0.0f;
			//int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}	
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
		      		if ( !scores.contains(nowScore) ) {
						scores.add( nowScore );
					}
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		    score = Float.valueOf( scores.size() );
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}		
	}
	
	public float max(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		float score = 0.0f; // init
		int size = 0;
		float nowScore = 0.0f;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
				if ( size < 1 ) {
					score = nowScore;
				} else { // Max
					if ( score < nowScore ) {
						score = nowScore;
					}
				}
				size++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return score;
	}
	
	public void maxDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			float score = 0.0f;
			float nowScore = 0.0f;
			int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					nowScore = NumberUtils.toFloat( String.valueOf(value), 0.0f);
					if ( size < 1 ) {
						score = nowScore;
					} else { // Max
						if ( score < nowScore ) {
							score = nowScore;
						}
					}
					size++;					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}		
	}	
	
	public float min(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		float score = 0.0f; // init
		int size = 0;
		float nowScore = 0.0f;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
				if ( size < 1 ) {
					score = nowScore;
				} else { // Min
					if ( score > nowScore ) {
						score = nowScore;
					}
				}
				size++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return score;
	}
	
	public void minDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			float score = 0.0f;
			float nowScore = 0.0f;
			int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
					if ( size < 1 ) {
						score = nowScore;
					} else { // Min
						if ( score > nowScore ) {
							score = nowScore;
						}
					}
					size++;					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}	
	}	
	
	public float sum(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		float score = 0.0f; // init
		//int size = 0;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				score += NumberUtils.toFloat(String.valueOf(value), 0.0f);
				//size++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return score;
	}
	
	public void sumDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			float score = 0.0f;
			//int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					score += NumberUtils.toFloat(String.valueOf(value), 0.0f);
					//size++;					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}
	}	
	
	public float sumDistinct(KpiVO kpi) throws Exception {
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		List<Float> scores = new ArrayList<Float>();
		float score = 0.0f; // init
		//int size = 0;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			try {
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
					continue;
				}
				float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
				if ( !scores.contains(nowScore) ) {
					scores.add( nowScore );
					//size++;
					score += nowScore;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return score;
	}
	
	public void sumDistinctDateRange(KpiVO kpi, String frequency) throws Exception {
		BscReportSupportUtils.loadExpression();
		for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
			List<Float> scores = new ArrayList<Float>();
			float score = 0.0f;
			//int size = 0;
			for (BbMeasureData measureData : kpi.getMeasureDatas()) {
				String date = dateScore.getDate().replaceAll("/", "");
				if (!this.isDateRange(date, frequency, measureData)) {
					continue;
				}
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				try {
					Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
					if (value == null) {
						continue;
					}
					if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {
						continue;
					}
					float nowScore = NumberUtils.toFloat(String.valueOf(value), 0.0f);
					if ( !scores.contains(nowScore) ) {
						scores.add( nowScore );
						//size++;
						score += nowScore;
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dateScore.setScore(score);
			dateScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
			dateScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateScore.setImgIcon( BscReportSupportUtils.getHtmlIcon(kpi, score) );
		}
	}
	
}
