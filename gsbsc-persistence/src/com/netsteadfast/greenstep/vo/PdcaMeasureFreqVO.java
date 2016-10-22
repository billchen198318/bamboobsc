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

public class PdcaMeasureFreqVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -7953281702348862428L;
	private String oid;
	private String pdcaOid;
	private String freq;
	private String startDate;
	private String endDate;
	private String dataType;
	private String orgId;
	private String empId;
	
	// -----------------------------------------------------------
	private String organizationOid;
	private String employeeOid;
	// -----------------------------------------------------------
	
	public PdcaMeasureFreqVO() {
		
	}
	
	public PdcaMeasureFreqVO(String oid, String pdcaOid, String freq, String startDate, String endDate, String dataType,
			String orgId, String empId) {
		super();
		this.oid = oid;
		this.pdcaOid = pdcaOid;
		this.freq = freq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dataType = dataType;
		this.orgId = orgId;
		this.empId = empId;
	}
	
	public String getStartDateDisplayValue() {
		return super.getDateDisplayValue(this.startDate, "/");
	}
	
	public String getEndDateDisplayValue() {
		return super.getDateDisplayValue(this.endDate, "/");
	}	
	
	public String getStartDateTextBoxValue() {
		return super.getDateDisplayValue(this.startDate, "-");
	}
	
	public String getEndDateTextBoxValue() {
		return super.getDateDisplayValue(this.endDate, "-");
	}		
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPdcaOid() {
		return pdcaOid;
	}

	public void setPdcaOid(String pdcaOid) {
		this.pdcaOid = pdcaOid;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	
	// -----------------------------------------------------------
	
	public String getOrganizationOid() {
		return organizationOid;
	}

	public void setOrganizationOid(String organizationOid) {
		this.organizationOid = organizationOid;
	}

	public String getEmployeeOid() {
		return employeeOid;
	}

	public void setEmployeeOid(String employeeOid) {
		this.employeeOid = employeeOid;
	}	
	
	// -----------------------------------------------------------
	
}
