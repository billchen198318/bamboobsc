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

public class SysVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 3232043821560523684L;
	private String oid;	
	private String sysId;
	private String name;
	private String host;
	private String contextPath;
	private String isLocal;
	private String icon;
	
	/**
	 * ApplicationSiteUtils.checkLoginUrlWithAllSysHostConfig(HttpServletRequest request) 
	 * 判斷是否有 cross site
	 */
	private String crossSiteFlag = "";
	
	/**
	 * ApplicationSiteUtils.checkLoginUrlWithAllSysHostConfig(HttpServletRequest request)
	 * 測試 pages/system/test.json
	 */
	private String testFlag = "";
	
	public SysVO() {
		
	}
	
	public SysVO(String oid, String sysId, String name, String host, String contextPath, 
			String isLocal, String icon) {
		this.oid = oid;
		this.sysId = sysId;
		this.name = name;
		this.host = host;
		this.contextPath = contextPath;
		this.isLocal = isLocal;
		this.icon = icon;
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

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getIsLocal() {
		return isLocal;
	}
	
	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCrossSiteFlag() {
		return crossSiteFlag;
	}

	public void setCrossSiteFlag(String crossSiteFlag) {
		this.crossSiteFlag = crossSiteFlag;
	}

	public String getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}	
		
}
