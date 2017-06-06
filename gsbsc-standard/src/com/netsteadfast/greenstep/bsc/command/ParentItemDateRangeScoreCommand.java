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

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class ParentItemDateRangeScoreCommand extends BaseChainCommandSupport implements Command {
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		//BscReportSupportUtils.loadExpression();
		BscScoreColorUtils.loadScoreColors();
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					this.handlerObjectiveDateRangeScore(objective);
					//this.printDateRangeScore(objective.getName(), objective.getDateRangeScores());
				}
			}
		}
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				this.handlerPerspectiveDateRangeScore(perspective);
				//this.printDateRangeScore(perspective.getName(), perspective.getDateRangeScores());
			}
		}
		for (VisionVO vision : treeObj.getVisions()) {
			this.handlerVisionDateRangeScore(vision);
			//this.printDateRangeScore(vision.getTitle(), vision.getDateRangeScores());
		}
		return false;
	}
	
	/*
	private void printDateRangeScore(String name , List<DateRangeScoreVO> dateRangeScore) {
		System.out.println("\n");
		System.out.print("name: " + name + " , dateRange: [ " );
		for (DateRangeScoreVO d : dateRangeScore) {
			System.out.print( d.getScore() + " , ");
		}
		System.out.print(" ] ");
		
	}
	*/
	
	private void handlerObjectiveDateRangeScore(ObjectiveVO objective) throws Exception {
		if (objective.getKpis() == null || objective.getKpis().size() < 1 || objective.getKpis().get(0).getDateRangeScores() == null || objective.getKpis().get(0).getDateRangeScores().size() <1) {
			return;
		}
		// init dateRange
		for (DateRangeScoreVO dateRangeScore : objective.getKpis().get(0).getDateRangeScores()) {
			DateRangeScoreVO d = new DateRangeScoreVO();
			d.setMin( objective.getMin() ); 
			d.setTarget( objective.getTarget() );
			d.setFontColor("");
			d.setBgColor("");
			d.setScore(0);
			d.setDate(dateRangeScore.getDate());
			d.setImgIcon("");
			objective.getDateRangeScores().add(d);
		}
		for (int i=0; i<objective.getDateRangeScores().size(); i++) {
			float score = 0.0f;
			DateRangeScoreVO dateRangeScore = objective.getDateRangeScores().get(i);
			for (KpiVO kpi : objective.getKpis()) {
				DateRangeScoreVO kpiDateRangeScore = kpi.getDateRangeScores().get(i);
				score += kpiDateRangeScore.getScore() * this.getWeightPercentage(kpi.getWeight());
			}
			dateRangeScore.setScore(score);
			dateRangeScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateRangeScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
		}
		
	}
	
	private void handlerPerspectiveDateRangeScore(PerspectiveVO perspective) throws Exception {
		if (perspective.getObjectives() == null || perspective.getObjectives().size() < 1 || perspective.getObjectives().get(0).getDateRangeScores() == null || perspective.getObjectives().get(0).getDateRangeScores().size() < 1) {
			return;
		}
		// init dateRange
		for (DateRangeScoreVO dateRangeScore : perspective.getObjectives().get(0).getDateRangeScores()) {
			DateRangeScoreVO d = new DateRangeScoreVO();
			d.setMin( perspective.getMin() ); 
			d.setTarget( perspective.getTarget() );
			d.setFontColor("");
			d.setBgColor("");
			d.setScore(0);
			d.setDate(dateRangeScore.getDate());
			d.setImgIcon("");
			perspective.getDateRangeScores().add(d);			
		}
		for (int i=0; i<perspective.getDateRangeScores().size(); i++) {
			float score = 0.0f;
			DateRangeScoreVO dateRangeScore = perspective.getDateRangeScores().get(i);
			for (ObjectiveVO objective : perspective.getObjectives()) {
				DateRangeScoreVO objDateRangeScore = objective.getDateRangeScores().get(i);
				score += objDateRangeScore.getScore() * this.getWeightPercentage(objective.getWeight());
			}
			dateRangeScore.setScore(score);
			dateRangeScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateRangeScore.setFontColor( BscScoreColorUtils.getFontColor(score) );
		}		
		
	}
	
	private void handlerVisionDateRangeScore(VisionVO vision) throws Exception {
		if (vision.getPerspectives() == null || vision.getPerspectives().size() < 1 || vision.getPerspectives().get(0).getDateRangeScores() == null || vision.getPerspectives().get(0).getDateRangeScores().size() < 1) {
			return;
		}
		// init dateRange
		for (DateRangeScoreVO dateRangeScore : vision.getPerspectives().get(0).getDateRangeScores()) {
			DateRangeScoreVO d = new DateRangeScoreVO();
			d.setMin( 0 );  // vision 沒有 Min
			d.setTarget( 0 ); // vision 沒有 Target
			d.setFontColor("");
			d.setBgColor("");
			d.setScore(0);
			d.setDate(dateRangeScore.getDate());
			d.setImgIcon("");
			vision.getDateRangeScores().add(d);			
		}
		for (int i=0; i<vision.getDateRangeScores().size(); i++) {
			float score = 0.0f;
			DateRangeScoreVO dateRangeScore = vision.getDateRangeScores().get(i);
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				DateRangeScoreVO perDateRangeScore = perspective.getDateRangeScores().get(i);
				score += perDateRangeScore.getScore() * this.getWeightPercentage(perspective.getWeight());
			}
			dateRangeScore.setScore(score);
			dateRangeScore.setBgColor( BscScoreColorUtils.getBackgroundColor(score) );
			dateRangeScore.setFontColor( BscScoreColorUtils.getFontColor(score) );			
		}
		
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
