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

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscMeasureData;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.BscFormulaUtils;
import com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class PersonalAndOrganizationReportDateRangeScoreCommand extends BaseChainCommandSupport implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}		
		float total = 0.0f;
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		String year = StringUtils.defaultString( (String)context.get("startYearDate") ).trim();
		String dateType = StringUtils.defaultString( (String)context.get("dateType") ).trim();
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						this.setDateRangeScore(kpi, dateType, year);
						total = total + kpi.getDateRangeScores().get(0).getScore(); // 只有一筆資料
					}
				}
			}
		}		
		context.put("total", total);
		return false;
	}
	
	private void setDateRangeScore(KpiVO kpi, String dateType, String year) throws Exception {
		float score = 0.0f;
		String date = year + "0101"; // year
		if ("2".equals(dateType)) { // second helf-year
			date = year + "0701";
		} 
		for (BbMeasureData measureData : kpi.getMeasureDatas()) {
			if ( date.equals(measureData.getDate()) ) {
				BscMeasureData data = new BscMeasureData();
				data.setActual( measureData.getActual() );
				data.setTarget( measureData.getTarget() );
				Object value = BscFormulaUtils.parse(kpi.getFormula(), data);
				/* 2016-07-01 rem
				if (value != null && (value instanceof Integer || value instanceof Float || value instanceof Long) ) {
					score = NumberUtils.toFloat( String.valueOf(value), 0.0f);
				}
				*/
				// 2016-07-01
				if (NumberUtils.isCreatable( String.valueOf(value)) ) {
					score = NumberUtils.toFloat(String.valueOf(value), 0.0f);
				}
			}
		}
		DateRangeScoreVO dateRangeScore = new DateRangeScoreVO();
		dateRangeScore.setDate( date );
		dateRangeScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
		dateRangeScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
		dateRangeScore.setTarget(kpi.getTarget());
		dateRangeScore.setMin(kpi.getMin());
		dateRangeScore.setScore(score);
		dateRangeScore.setImgIcon("");
		kpi.getDateRangeScores().add(dateRangeScore);		
	}
	
}
