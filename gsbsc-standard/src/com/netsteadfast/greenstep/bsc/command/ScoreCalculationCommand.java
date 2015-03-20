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

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class ScoreCalculationCommand extends BaseChainCommandSupport implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		this.scoreCalculation(treeObj);
		return false;
	}
	
	private void scoreCalculation(BscStructTreeObj treeObj) throws Exception {
		List<VisionVO> visions = treeObj.getVisions();
		BscScoreColorUtils.loadScoreColors();
		this.processKpisScore(visions);
		this.processObjectivesScore(visions);
		this.processPerspectivesScore(visions);
		this.processVisionsScore(visions);
	}
	
	private void processVisionsScore(List<VisionVO> visions) throws Exception {
		for (VisionVO vision : visions) {
			float score = 0.0f;
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				score += perspective.getScore() * this.getWeightPercentage(perspective.getWeight());
			}
			vision.setScore(score);
			vision.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			vision.setFontColor( BscScoreColorUtils.getFontColor(score) );		
		}
	}
	
	private void processPerspectivesScore(List<VisionVO> visions) throws Exception {
		for (VisionVO vision : visions) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				float score = 0.0f;
				for (ObjectiveVO objective : perspective.getObjectives()) {
					score += objective.getScore() * this.getWeightPercentage(objective.getWeight());
				}
				perspective.setScore(score);
				perspective.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
				perspective.setFontColor( BscScoreColorUtils.getFontColor(score) );					
			}
		}
	}
	
	private void processObjectivesScore(List<VisionVO> visions) throws Exception {
		for (VisionVO vision : visions) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					float score = 0.0f;
					for (KpiVO kpi : objective.getKpis()) {
						score += kpi.getScore() * this.getWeightPercentage(kpi.getWeight());						
					}
					objective.setScore(score);
					objective.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
					objective.setFontColor( BscScoreColorUtils.getFontColor(score) );					
				}
			}
		}
	}
	
	private void processKpisScore(List<VisionVO> visions) throws Exception {
		for (VisionVO vision : visions) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						float score = this.calculationMeasureData(kpi);
						kpi.setScore(score);
						kpi.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
						kpi.setFontColor( BscScoreColorUtils.getFontColor(score) );
					}
				}
			}
		}
	}
	
	private float calculationMeasureData(KpiVO kpi) throws Exception {
		// 2015-03-11 更換成以 bb_aggregation_method.EXPRESSION1 處理計算方式
		/*
		List<BbMeasureData> measureDatas = kpi.getMeasureDatas();
		Float score = 0.0f;
		int size = 0;
		for (BbMeasureData measureData : measureDatas) {
			BscMeasureData data = new BscMeasureData();
			data.setActual( measureData.getActual() );
			data.setTarget( measureData.getTarget() );
			Object value = null;
			try {
				value = BscFormulaUtils.parse(kpi.getFormula(), data);
				if (value == null) {
					continue;
				}
				if ( !(value instanceof Integer || value instanceof Float || value instanceof Long) ) {
					continue;
				}
				score += NumberUtils.toFloat( String.valueOf(value), 0.0f);
				size++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (BscKpiCode.CAL_AVERAGE.equals(kpi.getCal()) && score != 0.0f ) {
			score = score / size;
		}
		return score;
		*/
		
		// 2015-03-11 更換成以 bb_aggregation_method.EXPRESSION1 處理計算方式		
		return AggregationMethodUtils.processDefaultMode(kpi);		
	}
	
	private float getWeightPercentage(BigDecimal weight) {
		if (weight==null) {
			return 0.0f;
		}
		if (weight.floatValue() == 0.0f ) {
			return 0.0f;
		}
		return weight.floatValue() / 100.0f;
	}

}
