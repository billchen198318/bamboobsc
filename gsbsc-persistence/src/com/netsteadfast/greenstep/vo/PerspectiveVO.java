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
package com.netsteadfast.greenstep.vo;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class PerspectiveVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -462067437853938767L;
	private String oid;	
	private String perId;
	private String visId;
	private String name;
	private BigDecimal weight;
	private float target;
	private float min;
	private String description;	
	
	private String visionTitle; // 查詢 query Grid 要用的 , 放 BB_VISION.TITLE
	private List<ObjectiveVO> objectives = new LinkedList<ObjectiveVO>(); // 給 StructTreeObj 用的
	private int row; // TD要用的row
	private String fontColor; // 報表要用的
	private String bgColor; // 報表要用的
	private float score; // 報表要用的
	private String imgIcon; // 報表要用的
	
	private List<DateRangeScoreVO> dateRangeScores = new LinkedList<DateRangeScoreVO>(); // 報表要用的日期區間分數 , 2017-06-06
	
	private List<Double> forecastNext = new LinkedList<Double>(); // 給 BSC_PROG007D0002Q ( 02 - Forecast analysis ) 用的 , 2017-01-01
	
	public PerspectiveVO() {
		
	}
	
	public PerspectiveVO(String oid, String perId, String visId, String name, 
			BigDecimal weight, float target, float min, String description) {
		this.oid = oid;
		this.perId = perId;
		this.visId = visId;
		this.name = name;
		this.weight = weight;
		this.target = target;
		this.min = min;
		this.description = description;
	}
	
	public PerspectiveVO(String oid, String perId, String visId, String name, 
			BigDecimal weight, String visionTitle) {
		this.oid = oid;
		this.perId = perId;
		this.visId = visId;
		this.name = name;
		this.weight = weight;
		this.visionTitle = visionTitle;
	}
	
	public PerspectiveVO(String oid, String perId, String visId, String name) {
		this.oid = oid;
		this.perId = perId;
		this.visId = visId;
		this.name = name;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public float getTarget() {
		return target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVisionTitle() {
		return visionTitle;
	}

	public void setVisionTitle(String visionTitle) {
		this.visionTitle = visionTitle;
	}

	public List<ObjectiveVO> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<ObjectiveVO> objectives) {
		this.objectives = objectives;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
	}

	public List<DateRangeScoreVO> getDateRangeScores() {
		return dateRangeScores;
	}

	public void setDateRangeScores(List<DateRangeScoreVO> dateRangeScores) {
		this.dateRangeScores = dateRangeScores;
	}

	public List<Double> getForecastNext() {
		return forecastNext;
	}

	public void setForecastNext(List<Double> forecastNext) {
		this.forecastNext = forecastNext;
	}

}
