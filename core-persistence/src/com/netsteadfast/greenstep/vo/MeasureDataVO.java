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

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class MeasureDataVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 206245661989831714L;
	private String oid;
	private String kpiId;
	private String date;
	private float target;
	private float actual;
	private String frequency;
	private String orgId;
	private String empId;
	
	@Override
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}	
	
	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getTarget() {
		return target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public float getActual() {
		return actual;
	}

	public void setActual(float actual) {
		this.actual = actual;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

}
