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
package com.netsteadfast.greenstep.bsc.vo;

import com.netsteadfast.greenstep.po.hbm.BbSwot;

public class SwotIssuesVO implements java.io.Serializable {
	private static final long serialVersionUID = -8005758098493139828L;
	
	private BbSwot strengthsMain = null;
	private BbSwot weaknessesMain = null;
	private BbSwot opportunitiesMain = null;
	private BbSwot threatsMain = null;
	private String perspectiveName = "";
	private String strengths = "";
	private String weaknesses = "";
	private String opportunities = "";
	private String threats = "";
	private String strengthsTextId = ""; // 輸入方塊id , TYPE:PER_ID:ORG_ID
	private String weaknessesTextId = "";
	private String opportunitiesTextId = "";
	private String threatsTextId = "";	

	public BbSwot getStrengthsMain() {
		return strengthsMain;
	}

	public void setStrengthsMain(BbSwot strengthsMain) {
		this.strengthsMain = strengthsMain;
	}

	public BbSwot getWeaknessesMain() {
		return weaknessesMain;
	}

	public void setWeaknessesMain(BbSwot weaknessesMain) {
		this.weaknessesMain = weaknessesMain;
	}

	public BbSwot getOpportunitiesMain() {
		return opportunitiesMain;
	}

	public void setOpportunitiesMain(BbSwot opportunitiesMain) {
		this.opportunitiesMain = opportunitiesMain;
	}

	public BbSwot getThreatsMain() {
		return threatsMain;
	}

	public void setThreatsMain(BbSwot threatsMain) {
		this.threatsMain = threatsMain;
	}

	public String getPerspectiveName() {
		return perspectiveName;
	}
	
	public void setPerspectiveName(String perspectiveName) {
		this.perspectiveName = perspectiveName;
	}
	
	public String getStrengths() {
		return strengths;
	}
	
	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}
	
	public String getWeaknesses() {
		return weaknesses;
	}
	
	public void setWeaknesses(String weaknesses) {
		this.weaknesses = weaknesses;
	}
	
	public String getOpportunities() {
		return opportunities;
	}
	
	public void setOpportunities(String opportunities) {
		this.opportunities = opportunities;
	}
	
	public String getThreats() {
		return threats;
	}
	
	public void setThreats(String threats) {
		this.threats = threats;
	}

	public String getStrengthsTextId() {
		return strengthsTextId;
	}

	public void setStrengthsTextId(String strengthsTextId) {
		this.strengthsTextId = strengthsTextId;
	}

	public String getWeaknessesTextId() {
		return weaknessesTextId;
	}

	public void setWeaknessesTextId(String weaknessesTextId) {
		this.weaknessesTextId = weaknessesTextId;
	}

	public String getOpportunitiesTextId() {
		return opportunitiesTextId;
	}

	public void setOpportunitiesTextId(String opportunitiesTextId) {
		this.opportunitiesTextId = opportunitiesTextId;
	}

	public String getThreatsTextId() {
		return threatsTextId;
	}

	public void setThreatsTextId(String threatsTextId) {
		this.threatsTextId = threatsTextId;
	}
	
}
