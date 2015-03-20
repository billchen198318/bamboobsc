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

public class SysBeanHelpVO extends BaseValueObj implements java.io.Serializable {	
	private static final long serialVersionUID = -13126111260857495L;
	private String oid;	
	private String beanId;
	private String method;
	private String system;
	private String enableFlag;
	private String description;
	
	public SysBeanHelpVO() {
		
	}
	
	public SysBeanHelpVO(String oid, String beanId, String method, String system, String enableFlag, String description) {
		this.oid = oid;
		this.beanId = beanId;
		this.method = method;
		this.system = system;
		this.enableFlag = enableFlag;
		this.description = description;
	}
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
