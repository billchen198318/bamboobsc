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

public class SysMenuVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -8753926516772899633L;
	private String oid;
	private String progId;
	private String parentOid;
	private String enableFlag;
	
	// ----------------------------------------------------------------------------
	// inner join 要用的
	private String name;
	private String url;
	private String progSystem;
	private String itemType;
	private String icon;	
	// ----------------------------------------------------------------------------
	
	public SysMenuVO() {
		
	}
	
	public SysMenuVO(String oid, String progId, String parentOid, String enableFlag) {
		this.oid = oid;
		this.progId = progId;
		this.parentOid = parentOid;
		this.enableFlag = enableFlag;
	}
	
	public SysMenuVO(String oid, String progId, String parentOid, String enableFlag, 
			String name, String url, String progSystem, String itemType, String icon) {
		this.oid = oid;
		this.progId = progId;
		this.parentOid = parentOid;
		this.enableFlag = enableFlag;
		this.name = name;
		this.url = url;
		this.progSystem = progSystem;
		this.itemType = itemType;
		this.icon = icon;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getProgId() {
		return progId;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}

	public String getParentOid() {
		return parentOid;
	}

	public void setParentOid(String parentOid) {
		this.parentOid = parentOid;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	// ----------------------------------------------------------------------------
	// inner join 要用的	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProgSystem() {
		return progSystem;
	}

	public void setProgSystem(String progSystem) {
		this.progSystem = progSystem;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}	
	
	// ----------------------------------------------------------------------------
	
}
