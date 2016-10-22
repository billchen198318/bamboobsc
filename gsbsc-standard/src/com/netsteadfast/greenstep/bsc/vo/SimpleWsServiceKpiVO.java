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

public class SimpleWsServiceKpiVO implements java.io.Serializable {
	private static final long serialVersionUID = 6384945677181634069L;
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
	
}
