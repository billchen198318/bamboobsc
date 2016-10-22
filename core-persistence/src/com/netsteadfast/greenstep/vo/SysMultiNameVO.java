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

public class SysMultiNameVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 5386944458924490760L;
	private String oid;	
	private String sysId;
	private String localeCode;
	private String name;	
	private String enableFlag;
	
	private String defaultName; // for page grid query , tb_sys.name
	
	public SysMultiNameVO() {
		
	}
	
	public SysMultiNameVO(String oid, String sysId, String localeCode, String name, String enableFlag) {
		super();
		this.oid = oid;
		this.sysId = sysId;
		this.localeCode = localeCode;
		this.name = name;
		this.enableFlag = enableFlag;
	}

	public SysMultiNameVO(String oid, String sysId, String localeCode, String name, String enableFlag,
			String defaultName) {
		super();
		this.oid = oid;
		this.sysId = sysId;
		this.localeCode = localeCode;
		this.name = name;
		this.enableFlag = enableFlag;
		this.defaultName = defaultName;
	}

	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

}
