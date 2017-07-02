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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.BaseValueObj;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;

public class KpiVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -204616011759315811L;
	private String oid;
	private String id;
	private String objId;
	private String name;
	private String description;
	private BigDecimal weight;
	private String unit;
	private String forId;
	private String trendsForId;
	private float max;
	private float target;
	private float min;
	private String management;
	private String compareType;
	private String cal;
	private String dataType;
	private String orgaMeasureSeparate;
	private String userMeasureSeparate;
	private int quasiRange;
	private String activate;
	
	private String visionTitle; // 查詢 query Grid 要用的 , 放 BB_VISION.TITLE
	private String perspectiveName; // 查詢 query Grid 要用的 , 放 BB_PERSPECTIVE.NAME
	private String objectiveName; // 查詢 query Grid 要用的 , 放 BB_OBJECTIVE.NAME
	private List<BbMeasureData> measureDatas = new ArrayList<BbMeasureData>(); // 給 StructTreeObj 用的
	private FormulaVO formula; // 給 StructTreeObj 用的
	private FormulaVO trendsFormula; // 給 StructTreeObj 用的
	private AggregationMethodVO aggregationMethod; // 給 StructTreeObj 用的
	private String fontColor; // 報表要用的
	private String bgColor; // 報表要用的
	private float score; // 報表要用的
	private String imgIcon; // 報表要用的
	private String managementName; // 報表要用的管理方式名稱
	private String calculationName; // 報表要用的計算方式名稱
	private List<DateRangeScoreVO> dateRangeScores = new LinkedList<DateRangeScoreVO>(); // 報表要用的日期區間分數	
	private List<EmployeeVO> employees = new ArrayList<EmployeeVO>(); // KPI擁有者 , 目前只有KPI報表要用到
	private List<OrganizationVO> organizations = new ArrayList<OrganizationVO>(); // KPI所屬部門, 目前只有KPI報表要用到
	private List<KpiAttacVO> attachments = new ArrayList<KpiAttacVO>(); // KPI的上傳文件
	
	private List<Double> forecastNext = new LinkedList<Double>(); // 給 BSC_PROG007D0002Q ( 02 - Forecast analysis ) 用的 , 2017-01-01
	
	public KpiVO() {
		
	}
	
	public KpiVO(String oid, String id, String name, String description, BigDecimal weight,
			String visionTitle, String perspectiveName, String objectiveName) {
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.visionTitle = visionTitle;
		this.perspectiveName = perspectiveName;
		this.objectiveName = objectiveName;
	}	
	
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getObjId() {
		return objId;
	}
	
	public void setObjId(String objId) {
		this.objId = objId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getWeight() {
		return weight;
	}
	
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getForId() {
		return forId;
	}

	public void setForId(String forId) {
		this.forId = forId;
	}

	public String getTrendsForId() {
		return trendsForId;
	}

	public void setTrendsForId(String trendsForId) {
		this.trendsForId = trendsForId;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
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
	
	public String getManagement() {
		return management;
	}
	
	public void setManagement(String management) {
		this.management = management;
	}
	
	public String getCompareType() {
		return compareType;
	}
	
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	
	public String getCal() {
		return cal;
	}
	
	public void setCal(String cal) {
		this.cal = cal;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getOrgaMeasureSeparate() {
		return orgaMeasureSeparate;
	}
	
	public void setOrgaMeasureSeparate(String orgaMeasureSeparate) {
		this.orgaMeasureSeparate = orgaMeasureSeparate;
	}
	
	public String getUserMeasureSeparate() {
		return userMeasureSeparate;
	}
	
	public void setUserMeasureSeparate(String userMeasureSeparate) {
		this.userMeasureSeparate = userMeasureSeparate;
	}

	public int getQuasiRange() {
		return quasiRange;
	}

	public void setQuasiRange(int quasiRange) {
		this.quasiRange = quasiRange;
	}
	
	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public String getVisionTitle() {
		return visionTitle;
	}

	public void setVisionTitle(String visionTitle) {
		this.visionTitle = visionTitle;
	}

	public String getPerspectiveName() {
		return perspectiveName;
	}

	public void setPerspectiveName(String perspectiveName) {
		this.perspectiveName = perspectiveName;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public List<BbMeasureData> getMeasureDatas() {
		return measureDatas;
	}

	public void setMeasureDatas(List<BbMeasureData> measureDatas) {
		this.measureDatas = measureDatas;
	}

	public FormulaVO getFormula() {
		return formula;
	}

	public void setFormula(FormulaVO formula) {
		this.formula = formula;
	}

	public FormulaVO getTrendsFormula() {
		return trendsFormula;
	}

	public void setTrendsFormula(FormulaVO trendsFormula) {
		this.trendsFormula = trendsFormula;
	}

	public AggregationMethodVO getAggregationMethod() {
		return aggregationMethod;
	}

	public void setAggregationMethod(AggregationMethodVO aggregationMethod) {
		this.aggregationMethod = aggregationMethod;
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

	public String getManagementName() {
		return managementName;
	}

	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}

	public String getCalculationName() {
		return calculationName;
	}

	public void setCalculationName(String calculationName) {
		this.calculationName = calculationName;
	}

	public List<DateRangeScoreVO> getDateRangeScores() {
		return dateRangeScores;
	}

	public void setDateRangeScores(List<DateRangeScoreVO> dateRangeScores) {
		this.dateRangeScores = dateRangeScores;
	}

	public List<EmployeeVO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeVO> employees) {
		this.employees = employees;
	}

	public List<OrganizationVO> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<OrganizationVO> organizations) {
		this.organizations = organizations;
	}

	public List<KpiAttacVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<KpiAttacVO> attachments) {
		this.attachments = attachments;
	}

	public List<Double> getForecastNext() {
		return forecastNext;
	}

	public void setForecastNext(List<Double> forecastNext) {
		this.forecastNext = forecastNext;
	}	

}
