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

public class SysFormTemplateVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 5809084590855775501L;
	private String oid;
	private String tplId;
	private String name;
	private byte[] content;
	private String fileName;
	private String description;
	
	public SysFormTemplateVO() {
		
	}
	
	public SysFormTemplateVO(String oid, String tplId, String name,
			byte[] content, String fileName, String description) {
		super();
		this.oid = oid;
		this.tplId = tplId;
		this.name = name;
		this.content = content;
		this.fileName = fileName;
		this.description = description;
	}

	public SysFormTemplateVO(String oid, String tplId, String name,
			String fileName, String description) {
		super();
		this.oid = oid;
		this.tplId = tplId;
		this.name = name;
		this.fileName = fileName;
		this.description = description;
	}

	public SysFormTemplateVO(String oid, String tplId, String name) {
		super();
		this.oid = oid;
		this.tplId = tplId;
		this.name = name;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
