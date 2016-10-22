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

public class PdcaItemDocVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -8138390790761806294L;
	private String oid;
	private String pdcaOid;
	private String itemOid;
	private String uploadOid;
	private String viewMode;
	
	// ----------------------------------------------------
	private String showName = "";
	// ----------------------------------------------------	
	
	public PdcaItemDocVO() {
		
	}
	
	public PdcaItemDocVO(String oid, String pdcaOid, String itemOid, String uploadOid, String viewMode) {
		super();
		this.oid = oid;
		this.pdcaOid = pdcaOid;
		this.itemOid = itemOid;
		this.uploadOid = uploadOid;
		this.viewMode = viewMode;
	}

	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPdcaOid() {
		return pdcaOid;
	}

	public void setPdcaOid(String pdcaOid) {
		this.pdcaOid = pdcaOid;
	}

	public String getItemOid() {
		return itemOid;
	}

	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}

	public String getUploadOid() {
		return uploadOid;
	}

	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	
	// ----------------------------------------------------

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	// ----------------------------------------------------
	
}
