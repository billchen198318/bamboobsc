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

public class DataSourceConfVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 3904359991076443724L;
	private String oid;	
	private String id;
	private String name;
	private String driverId;
	private String jdbcUrl;
	private String dbAccount;
	private String dbPassword;
	private String description;
	
	public DataSourceConfVO() {
		
	}
	
	public DataSourceConfVO(String oid, String id, String name,
			String driverId, String jdbcUrl, String dbAccount, String dbPassword, String description) {
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.driverId = driverId;
		this.jdbcUrl = jdbcUrl;
		this.dbAccount = dbAccount;
		this.dbPassword = dbPassword;
		this.description = description;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getDbAccount() {
		return dbAccount;
	}

	public void setDbAccount(String dbAccount) {
		this.dbAccount = dbAccount;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
