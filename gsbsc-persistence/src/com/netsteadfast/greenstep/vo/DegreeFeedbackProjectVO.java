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

public class DegreeFeedbackProjectVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 8808017079060191115L;
	private String oid;
	private String name;
	private String year;
	private String publishFlag;
	private String description;
	
	// 報表要用
	private List<DegreeFeedbackItemVO> items = new ArrayList<DegreeFeedbackItemVO>();
	private List<DegreeFeedbackLevelVO> levels = new ArrayList<DegreeFeedbackLevelVO>();
	private EmployeeVO employee = new EmployeeVO();
	
	public DegreeFeedbackProjectVO() {
		
	}		
	
	public DegreeFeedbackProjectVO(String oid, String name, String year,
			String publishFlag, String description) {
		super();
		this.oid = oid;
		this.name = name;
		this.year = year;
		this.publishFlag = publishFlag;
		this.description = description;
	}

	public DegreeFeedbackProjectVO(String oid, String name, String year, String publishFlag) {
		super();
		this.oid = oid;
		this.name = name;
		this.year = year;
		this.publishFlag = publishFlag;
	}

	public DegreeFeedbackProjectVO(String oid, String name) {
		super();
		this.oid = oid;
		this.name = name;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DegreeFeedbackItemVO> getItems() {
		return items;
	}

	public void setItems(List<DegreeFeedbackItemVO> items) {
		this.items = items;
	}

	public List<DegreeFeedbackLevelVO> getLevels() {
		return levels;
	}

	public void setLevels(List<DegreeFeedbackLevelVO> levels) {
		this.levels = levels;
	}

	public EmployeeVO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeVO employee) {
		this.employee = employee;
	}			

}
