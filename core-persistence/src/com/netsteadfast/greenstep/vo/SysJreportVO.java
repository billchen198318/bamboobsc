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

public class SysJreportVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -670296333516510358L;
	private String oid;	
	private String reportId;
	private String file;
	private String isCompile;
	private byte[] content;
	private String description;
	
	public SysJreportVO() {
		super();
	}
	
	public SysJreportVO(String oid, String reportId, String file, String isCompile, 
			byte[] content, String description) {
		this.oid = oid;
		this.reportId = reportId;
		this.file = file;
		this.isCompile = isCompile;
		this.content = content;
		this.description = description;
	}
	
	public SysJreportVO(String oid, String reportId, String file, String isCompile, 
			String description) {
		this.oid = oid;
		this.reportId = reportId;
		this.file = file;
		this.isCompile = isCompile;
		this.description = description;
	}	
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getIsCompile() {
		return isCompile;
	}

	public void setIsCompile(String isCompile) {
		this.isCompile = isCompile;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
