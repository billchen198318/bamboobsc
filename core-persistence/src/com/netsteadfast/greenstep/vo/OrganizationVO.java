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

public class OrganizationVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 998990401751962297L;
	private String oid;	
	private String orgId;
	private String name;
	private String address;
	private String lat;
	private String lng;
	private String description;	
	
	private String parId; // inner join BB_ORGANIZATION_PAR select資料要用的
	
	public OrganizationVO() {
		
	}
	
	public OrganizationVO(String oid, String orgId, String name, String address,
			String lat, String lng, String description) {
		this.oid = oid;
		this.orgId = orgId;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.description = description;
	}
	
	public OrganizationVO(String oid, String orgId, String name, String parId) {
		this.oid = oid;
		this.orgId = orgId;
		this.name = name;
		this.parId = parId;
	}	
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParId() {
		return parId;
	}

	public void setParId(String parId) {
		this.parId = parId;
	}
	
}
