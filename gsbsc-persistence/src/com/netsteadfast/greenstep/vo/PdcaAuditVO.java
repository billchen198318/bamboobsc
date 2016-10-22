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

public class PdcaAuditVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -5231867989535641351L;
	private String oid;
	private String pdcaOid;
	private String type;
	private String empId;
	private String confirmDate;
	private int confirmSeq;
	
	public PdcaAuditVO() {
		
	}

	public PdcaAuditVO(String oid, String pdcaOid, String type, String empId, String confirmDate, int confirmSeq) {
		super();
		this.oid = oid;
		this.pdcaOid = pdcaOid;
		this.type = type;
		this.empId = empId;
		this.confirmDate = confirmDate;
		this.confirmSeq = confirmSeq;
	}
	
	public String getConfirmDateDisplayValue() {
		return this.getDateDisplayValue(this.confirmDate, "/");
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public int getConfirmSeq() {
		return confirmSeq;
	}
	
	public void setConfirmSeq(int confirmSeq) {
		this.confirmSeq = confirmSeq;
	}
	
}
