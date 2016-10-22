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

import java.util.Date;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class SysExprJobLogVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 7238359393685582012L;
	private String oid;
	private String id;
	private String logStatus;
	private Date beginDatetime;
	private Date endDatetime;
	private String faultMsg;
	
	public SysExprJobLogVO() {
		
	}
	
	public SysExprJobLogVO(String oid, String id, String logStatus, Date beginDatetime, Date endDatetime,
			String faultMsg) {
		super();
		this.oid = oid;
		this.id = id;
		this.logStatus = logStatus;
		this.beginDatetime = beginDatetime;
		this.endDatetime = endDatetime;
		this.faultMsg = faultMsg;
	}

	public SysExprJobLogVO(String oid, String id, String logStatus, Date beginDatetime, Date endDatetime) {
		super();
		this.oid = oid;
		this.id = id;
		this.logStatus = logStatus;
		this.beginDatetime = beginDatetime;
		this.endDatetime = endDatetime;
	}
	
	@Override
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
	
	public String getLogStatus() {
		return logStatus;
	}
	
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	
	public Date getBeginDatetime() {
		return beginDatetime;
	}
	
	public void setBeginDatetime(Date beginDatetime) {
		this.beginDatetime = beginDatetime;
	}
	
	public Date getEndDatetime() {
		return endDatetime;
	}
	
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	
	public String getFaultMsg() {
		return faultMsg;
	}
	
	public void setFaultMsg(String faultMsg) {
		this.faultMsg = faultMsg;
	}

}
