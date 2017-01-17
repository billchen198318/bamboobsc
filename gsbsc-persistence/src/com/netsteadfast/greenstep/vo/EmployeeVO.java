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

public class EmployeeVO extends BaseValueObj implements java.io.Serializable {	
	private static final long serialVersionUID = -1526906400201542686L;
	private String oid;	
	private String account;
	private String empId;
	private String fullName;
	private String jobTitle;
	
	// 給新增時用的
	private String password;
	
	private String supOid; // inner join bb_employee_hier select資料要用的
	
	public EmployeeVO() {
		
	}
	
	public EmployeeVO(String oid, String account, String empId, String fullName, String jobTitle) {
		this.oid = oid;
		this.account = account;
		this.empId = empId;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
	}	
	
	public EmployeeVO(String oid, String account, String empId, String fullName, String jobTitle, String supOid) {
		this.oid = oid;
		this.account = account;
		this.empId = empId;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.supOid = supOid;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSupOid() {
		return supOid;
	}

	public void setSupOid(String supOid) {
		this.supOid = supOid;
	}	
	
}
