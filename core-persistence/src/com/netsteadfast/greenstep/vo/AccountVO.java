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

import com.netsteadfast.greenstep.base.model.AccountObj;

public class AccountVO extends AccountObj implements java.io.Serializable {
	private static final long serialVersionUID = -4818301117532064245L;
	private String oid;
	private String account;
	private String password;
	private String onJob;
	
	public AccountVO() {
		
	}
	
	public AccountVO(String oid, String account) {
		this.oid = oid;
		this.account = account;
	}
	
	public AccountVO(String oid, String account, String password) {
		this.oid = oid;
		this.account = account;
		this.password = password;
	}	
	
	public String getOid() {
		return oid;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOnJob() {
		return onJob;
	}
	public void setOnJob(String onJob) {
		this.onJob = onJob;
	}
	
}