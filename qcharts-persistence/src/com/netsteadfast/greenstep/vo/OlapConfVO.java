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

public class OlapConfVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -8913991330217237681L;
	private String oid;	
	private String id;
	private String name;
	private String jdbcDrivers;
	private String jdbcUrl;
	private String description;	
	
	public OlapConfVO() {
		
	}
	
	public OlapConfVO(String oid, String id, String name, String jdbcDrivers, String jdbcUrl, String description) {
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.jdbcDrivers = jdbcDrivers;
		this.jdbcUrl = jdbcUrl;
		this.description = description;
	}
	
	public OlapConfVO(String oid, String id, String name, String jdbcDrivers, String jdbcUrl) {
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.jdbcDrivers = jdbcDrivers;
		this.jdbcUrl = jdbcUrl;
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

	public String getJdbcDrivers() {
		return jdbcDrivers;
	}

	public void setJdbcDrivers(String jdbcDrivers) {
		this.jdbcDrivers = jdbcDrivers;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
