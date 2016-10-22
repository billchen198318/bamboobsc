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

import java.math.BigDecimal;

public class BscMixDataVO implements java.io.Serializable {
	private static final long serialVersionUID = 6720540588500166242L;
	private String kpiOid;
	private String kpiId;
	private String kpiName;
	private String kpiDescription;
	private BigDecimal kpiWeight;
	private String kpiUnit;
	private float kpiMax;
	private float kpiTarget;
	private float kpiMin;	
	private String kpiManagement;
	private String kpiCompareType;
	private String kpiCal;
	private String kpiDataType;
	private String kpiOrgaMeasureSeparate;
	private String kpiUserMeasureSeparate;	
	private int kpiQuasiRange;
	private String kpiActivate;
	private String objOid;
	private String objId;
	private String objName;
	private BigDecimal objWeight;
	private String objDescription;
	private float objTarget;
	private float objMin;			
	private String perOid;
	private String perId;
	private String perName;
	private BigDecimal perWeight;
	private String perDescription;
	private float perTarget;
	private float perMin;		
	private String visOid;
	private String visId;
	private String visTitle;
	private String forOid;
	private String forId;
	private String forName;
	private String forType;
	private String forReturnMode;
	private String forReturnVar;
	private String forExpression;
	private String aggrOid;
	private String aggrId;
	private String aggrName;
	private String aggrType;
	private String aggrExpression1;
	private String aggrExpression2;
	private String trendsForOid;
	private String trendsForId;
	private String trendsForName;
	private String trendsForType;
	private String trendsForReturnMode;
	private String trendsForReturnVar;
	private String trendsForExpression;	
	
	public BscMixDataVO() {
		
	}		

	public BscMixDataVO(String kpiOid, String kpiId, String kpiName,
			String kpiDescription, BigDecimal kpiWeight, String kpiUnit,
			float kpiMax, float kpiTarget, float kpiMin, String kpiManagement,
			String kpiCompareType, String kpiCal, String kpiDataType,
			String kpiOrgaMeasureSeparate, String kpiUserMeasureSeparate,
			int kpiQuasiRange, String kpiActivate, String objOid, String objId, String objName,
			BigDecimal objWeight, String objDescription, float objTarget,
			float objMin, String perOid, String perId, String perName,
			BigDecimal perWeight, String perDescription, float perTarget,
			float perMin, String visOid, String visId, String visTitle,
			String forOid, String forId, String forName, String forType,
			String forReturnMode, String forReturnVar, String forExpression,
			String aggrOid, String aggrId, String aggrName, String aggrType,
			String aggrExpression1, String aggrExpression2,
			String trendsForOid, String trendsForId, String trendsForName,
			String trendsForType, String trendsForReturnMode,
			String trendsForReturnVar, String trendsForExpression) {
		super();
		this.kpiOid = kpiOid;
		this.kpiId = kpiId;
		this.kpiName = kpiName;
		this.kpiDescription = kpiDescription;
		this.kpiWeight = kpiWeight;
		this.kpiUnit = kpiUnit;
		this.kpiMax = kpiMax;
		this.kpiTarget = kpiTarget;
		this.kpiMin = kpiMin;
		this.kpiManagement = kpiManagement;
		this.kpiCompareType = kpiCompareType;
		this.kpiCal = kpiCal;
		this.kpiDataType = kpiDataType;
		this.kpiOrgaMeasureSeparate = kpiOrgaMeasureSeparate;
		this.kpiUserMeasureSeparate = kpiUserMeasureSeparate;
		this.kpiQuasiRange = kpiQuasiRange;
		this.kpiActivate = kpiActivate;
		this.objOid = objOid;
		this.objId = objId;
		this.objName = objName;
		this.objWeight = objWeight;
		this.objDescription = objDescription;
		this.objTarget = objTarget;
		this.objMin = objMin;
		this.perOid = perOid;
		this.perId = perId;
		this.perName = perName;
		this.perWeight = perWeight;
		this.perDescription = perDescription;
		this.perTarget = perTarget;
		this.perMin = perMin;
		this.visOid = visOid;
		this.visId = visId;
		this.visTitle = visTitle;
		this.forOid = forOid;
		this.forId = forId;
		this.forName = forName;
		this.forType = forType;
		this.forReturnMode = forReturnMode;
		this.forReturnVar = forReturnVar;
		this.forExpression = forExpression;
		this.aggrOid = aggrOid;
		this.aggrId = aggrId;
		this.aggrName = aggrName;
		this.aggrType = aggrType;
		this.aggrExpression1 = aggrExpression1;
		this.aggrExpression2 = aggrExpression2;
		this.trendsForOid = trendsForOid;
		this.trendsForId = trendsForId;
		this.trendsForName = trendsForName;
		this.trendsForType = trendsForType;
		this.trendsForReturnMode = trendsForReturnMode;
		this.trendsForReturnVar = trendsForReturnVar;
		this.trendsForExpression = trendsForExpression;
	}
	
	public String getKpiOid() {
		return kpiOid;
	}

	public void setKpiOid(String kpiOid) {
		this.kpiOid = kpiOid;
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiDescription() {
		return kpiDescription;
	}

	public void setKpiDescription(String kpiDescription) {
		this.kpiDescription = kpiDescription;
	}

	public BigDecimal getKpiWeight() {
		return kpiWeight;
	}

	public void setKpiWeight(BigDecimal kpiWeight) {
		this.kpiWeight = kpiWeight;
	}

	public String getKpiUnit() {
		return kpiUnit;
	}

	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}

	public float getKpiMax() {
		return kpiMax;
	}

	public void setKpiMax(float kpiMax) {
		this.kpiMax = kpiMax;
	}

	public float getKpiTarget() {
		return kpiTarget;
	}

	public void setKpiTarget(float kpiTarget) {
		this.kpiTarget = kpiTarget;
	}

	public float getKpiMin() {
		return kpiMin;
	}

	public void setKpiMin(float kpiMin) {
		this.kpiMin = kpiMin;
	}

	public String getKpiManagement() {
		return kpiManagement;
	}

	public void setKpiManagement(String kpiManagement) {
		this.kpiManagement = kpiManagement;
	}

	public String getKpiCompareType() {
		return kpiCompareType;
	}

	public void setKpiCompareType(String kpiCompareType) {
		this.kpiCompareType = kpiCompareType;
	}

	public String getKpiCal() {
		return kpiCal;
	}

	public void setKpiCal(String kpiCal) {
		this.kpiCal = kpiCal;
	}

	public String getKpiDataType() {
		return kpiDataType;
	}

	public void setKpiDataType(String kpiDataType) {
		this.kpiDataType = kpiDataType;
	}

	public String getKpiOrgaMeasureSeparate() {
		return kpiOrgaMeasureSeparate;
	}

	public void setKpiOrgaMeasureSeparate(String kpiOrgaMeasureSeparate) {
		this.kpiOrgaMeasureSeparate = kpiOrgaMeasureSeparate;
	}

	public String getKpiUserMeasureSeparate() {
		return kpiUserMeasureSeparate;
	}

	public void setKpiUserMeasureSeparate(String kpiUserMeasureSeparate) {
		this.kpiUserMeasureSeparate = kpiUserMeasureSeparate;
	}

	public int getKpiQuasiRange() {
		return kpiQuasiRange;
	}

	public void setKpiQuasiRange(int kpiQuasiRange) {
		this.kpiQuasiRange = kpiQuasiRange;
	}
	
	public String getKpiActivate() {
		return kpiActivate;
	}

	public void setKpiActivate(String kpiActivate) {
		this.kpiActivate = kpiActivate;
	}

	public String getObjOid() {
		return objOid;
	}

	public void setObjOid(String objOid) {
		this.objOid = objOid;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public BigDecimal getObjWeight() {
		return objWeight;
	}

	public void setObjWeight(BigDecimal objWeight) {
		this.objWeight = objWeight;
	}

	public String getObjDescription() {
		return objDescription;
	}

	public void setObjDescription(String objDescription) {
		this.objDescription = objDescription;
	}

	public float getObjTarget() {
		return objTarget;
	}

	public void setObjTarget(float objTarget) {
		this.objTarget = objTarget;
	}

	public float getObjMin() {
		return objMin;
	}

	public void setObjMin(float objMin) {
		this.objMin = objMin;
	}

	public String getPerOid() {
		return perOid;
	}

	public void setPerOid(String perOid) {
		this.perOid = perOid;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public BigDecimal getPerWeight() {
		return perWeight;
	}

	public void setPerWeight(BigDecimal perWeight) {
		this.perWeight = perWeight;
	}

	public String getPerDescription() {
		return perDescription;
	}

	public void setPerDescription(String perDescription) {
		this.perDescription = perDescription;
	}

	public float getPerTarget() {
		return perTarget;
	}

	public void setPerTarget(float perTarget) {
		this.perTarget = perTarget;
	}

	public float getPerMin() {
		return perMin;
	}

	public void setPerMin(float perMin) {
		this.perMin = perMin;
	}

	public String getVisOid() {
		return visOid;
	}

	public void setVisOid(String visOid) {
		this.visOid = visOid;
	}

	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}

	public String getVisTitle() {
		return visTitle;
	}

	public void setVisTitle(String visTitle) {
		this.visTitle = visTitle;
	}

	public String getForOid() {
		return forOid;
	}

	public void setForOid(String forOid) {
		this.forOid = forOid;
	}

	public String getForId() {
		return forId;
	}

	public void setForId(String forId) {
		this.forId = forId;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

	public String getForType() {
		return forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
	}

	public String getForReturnMode() {
		return forReturnMode;
	}

	public void setForReturnMode(String forReturnMode) {
		this.forReturnMode = forReturnMode;
	}

	public String getForReturnVar() {
		return forReturnVar;
	}

	public void setForReturnVar(String forReturnVar) {
		this.forReturnVar = forReturnVar;
	}

	public String getForExpression() {
		return forExpression;
	}

	public void setForExpression(String forExpression) {
		this.forExpression = forExpression;
	}

	public String getAggrOid() {
		return aggrOid;
	}

	public void setAggrOid(String aggrOid) {
		this.aggrOid = aggrOid;
	}

	public String getAggrId() {
		return aggrId;
	}

	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}

	public String getAggrName() {
		return aggrName;
	}

	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}

	public String getAggrType() {
		return aggrType;
	}

	public void setAggrType(String aggrType) {
		this.aggrType = aggrType;
	}

	public String getAggrExpression1() {
		return aggrExpression1;
	}

	public void setAggrExpression1(String aggrExpression1) {
		this.aggrExpression1 = aggrExpression1;
	}

	public String getAggrExpression2() {
		return aggrExpression2;
	}

	public void setAggrExpression2(String aggrExpression2) {
		this.aggrExpression2 = aggrExpression2;
	}

	public String getTrendsForOid() {
		return trendsForOid;
	}

	public void setTrendsForOid(String trendsForOid) {
		this.trendsForOid = trendsForOid;
	}

	public String getTrendsForId() {
		return trendsForId;
	}

	public void setTrendsForId(String trendsForId) {
		this.trendsForId = trendsForId;
	}

	public String getTrendsForName() {
		return trendsForName;
	}

	public void setTrendsForName(String trendsForName) {
		this.trendsForName = trendsForName;
	}

	public String getTrendsForType() {
		return trendsForType;
	}

	public void setTrendsForType(String trendsForType) {
		this.trendsForType = trendsForType;
	}

	public String getTrendsForReturnMode() {
		return trendsForReturnMode;
	}

	public void setTrendsForReturnMode(String trendsForReturnMode) {
		this.trendsForReturnMode = trendsForReturnMode;
	}

	public String getTrendsForReturnVar() {
		return trendsForReturnVar;
	}

	public void setTrendsForReturnVar(String trendsForReturnVar) {
		this.trendsForReturnVar = trendsForReturnVar;
	}

	public String getTrendsForExpression() {
		return trendsForExpression;
	}

	public void setTrendsForExpression(String trendsForExpression) {
		this.trendsForExpression = trendsForExpression;
	}
	
}
