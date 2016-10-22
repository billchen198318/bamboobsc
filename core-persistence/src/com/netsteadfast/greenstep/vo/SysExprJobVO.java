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

import org.apache.commons.lang.StringUtils;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class SysExprJobVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 7991088254972538151L;
	private String oid;
	private String system;
	private String id;
	private String name;
	private String active;
	private String description;
	private String runStatus;
	private String checkFault;
	private String exprId;
	private String runDayOfWeek;
	private String runHour;
	private String runMinute;
	private String contactMode;
	private String contact;
	
	public SysExprJobVO() {
		
	}
	
	public SysExprJobVO(String oid, String system, String id, String name, String active, String description,
			String runStatus, String checkFault, String exprId, String runDayOfWeek, String runHour, String runMinute,
			String contactMode, String contact) {
		super();
		this.oid = oid;
		this.system = system;
		this.id = id;
		this.name = name;
		this.active = active;
		this.description = description;
		this.runStatus = runStatus;
		this.checkFault = checkFault;
		this.exprId = exprId;
		this.runDayOfWeek = runDayOfWeek;
		this.runHour = runHour;
		this.runMinute = runMinute;
		this.contactMode = contactMode;
		this.contact = contact;
	}

	public SysExprJobVO(String oid, String system, String id, String name, String active, String runStatus,
			String checkFault, String exprId, String runDayOfWeek, String runHour, String runMinute, String contactMode,
			String contact) {
		super();
		this.oid = oid;
		this.system = system;
		this.id = id;
		this.name = name;
		this.active = active;
		this.runStatus = runStatus;
		this.checkFault = checkFault;
		this.exprId = exprId;
		this.runDayOfWeek = runDayOfWeek;
		this.runHour = runHour;
		this.runMinute = runMinute;
		this.contactMode = contactMode;
		this.contact = contact;
	}
	
	public SysExprJobVO(String oid, String system, String id, String name, String active, String runStatus,
			String checkFault, String exprId, String runDayOfWeek, String runHour, String runMinute) {
		super();
		this.oid = oid;
		this.system = system;
		this.id = id;
		this.name = name;
		this.active = active;
		this.runStatus = runStatus;
		this.checkFault = checkFault;
		this.exprId = exprId;
		this.runDayOfWeek = runDayOfWeek;
		this.runHour = runHour;
		this.runMinute = runMinute;
	}
	
	public String getRunDatetime() {
		return StringUtils.defaultString(this.runDayOfWeek) 
				+ ( (!StringUtils.isBlank(this.runDayOfWeek) && !StringUtils.isBlank(this.runHour)) ? "/" : "" ) 
				+ StringUtils.defaultString(this.runHour) 
				+ ( (!StringUtils.isBlank(this.runHour) && !StringUtils.isBlank(this.runMinute)) ? "/" : "" )
				+ StringUtils.defaultString(this.runMinute);
	}

	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRunStatus() {
		return runStatus;
	}
	
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	
	public String getCheckFault() {
		return checkFault;
	}
	
	public void setCheckFault(String checkFault) {
		this.checkFault = checkFault;
	}
	
	public String getExprId() {
		return exprId;
	}
	
	public void setExprId(String exprId) {
		this.exprId = exprId;
	}
	
	public String getRunDayOfWeek() {
		return runDayOfWeek;
	}
	
	public void setRunDayOfWeek(String runDayOfWeek) {
		this.runDayOfWeek = runDayOfWeek;
	}
	
	public String getRunHour() {
		return runHour;
	}
	
	public void setRunHour(String runHour) {
		this.runHour = runHour;
	}
	
	public String getRunMinute() {
		return runMinute;
	}
	
	public void setRunMinute(String runMinute) {
		this.runMinute = runMinute;
	}
	
	public String getContactMode() {
		return contactMode;
	}
	
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}

}
