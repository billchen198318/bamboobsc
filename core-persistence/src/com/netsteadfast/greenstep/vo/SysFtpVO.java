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

public class SysFtpVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 4868687619025957423L;
	private String oid;	
	private String type;
	private String id;
	private String address;
	private String name;
	private int port;
	private String user;
	private String pass;
	private String description;
	
	public SysFtpVO() {
		
	}

	public SysFtpVO(String oid, String type, String id, String address,
			String name, int port, String user, String pass, String description) {
		super();
		this.oid = oid;
		this.type = type;
		this.id = id;
		this.address = address;
		this.name = name;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.description = description;
	}

	public SysFtpVO(String oid, String type, String id, String address,
			String name, int port, String user, String pass) {
		super();
		this.oid = oid;
		this.type = type;
		this.id = id;
		this.address = address;
		this.name = name;
		this.port = port;
		this.user = user;
		this.pass = pass;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
