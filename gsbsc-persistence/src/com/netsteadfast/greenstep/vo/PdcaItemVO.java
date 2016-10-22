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

import java.util.ArrayList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class PdcaItemVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -973653579350338196L;
	private String oid;
	private String type;
	private String pdcaOid;
	private String title;
	private String description;
	private String startDate;
	private String endDate;	
	
	// --------------------------------------------------------------
	private List<String> employeeOids = null;
	private List<String> uploadOids = null;
	private String employeeAppendOids = ""; // 修改時顯示資料用的
	private String employeeAppendNames = ""; // 修改時顯示資料用的
	private List<PdcaItemDocVO> docs = new ArrayList<PdcaItemDocVO>(); // 修改時顯示資料用的
	// --------------------------------------------------------------
	
	public PdcaItemVO() {
		
	}
	
	public PdcaItemVO(String oid, String type, String pdcaOid, String title, String startDate, String endDate) {
		super();
		this.oid = oid;
		this.type = type;
		this.pdcaOid = pdcaOid;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public PdcaItemVO(String oid, String type, String pdcaOid, String title, String description, String startDate,
			String endDate) {
		super();
		this.oid = oid;
		this.type = type;
		this.pdcaOid = pdcaOid;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getStartDateDisplayValue() {
		return this.getDateDisplayValue(this.startDate, "/");
	}
	
	public String getEndDateDisplayValue() {
		return this.getDateDisplayValue(this.endDate, "/");
	}	
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPdcaOid() {
		return pdcaOid;
	}

	public void setPdcaOid(String pdcaOid) {
		this.pdcaOid = pdcaOid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		
	// --------------------------------------------------------------
	
	public List<String> getEmployeeOids() {
		return employeeOids;
	}

	public void setEmployeeOids(List<String> employeeOids) {
		this.employeeOids = employeeOids;
	}

	public List<String> getUploadOids() {
		return uploadOids;
	}

	public void setUploadOids(List<String> uploadOids) {
		this.uploadOids = uploadOids;
	}

	public String getEmployeeAppendOids() {
		return employeeAppendOids;
	}

	public void setEmployeeAppendOids(String employeeAppendOids) {
		this.employeeAppendOids = employeeAppendOids;
	}

	public String getEmployeeAppendNames() {
		return employeeAppendNames;
	}

	public void setEmployeeAppendNames(String employeeAppendNames) {
		this.employeeAppendNames = employeeAppendNames;
	}

	public List<PdcaItemDocVO> getDocs() {
		return docs;
	}

	public void setDocs(List<PdcaItemDocVO> docs) {
		this.docs = docs;
	}		
	
	// --------------------------------------------------------------
	
}
