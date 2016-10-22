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

public class SysExpressionVO extends BaseValueObj implements java.io.Serializable {	
	private static final long serialVersionUID = 5144771795919804515L;
	private String oid;	
	private String exprId;
	private String type;
	private String name;
	private String content;
	private String description;
	
	public SysExpressionVO() {
		
	}
	
	public SysExpressionVO(String oid, String exprId, String type, String name, String content, String description) {
		this.oid = oid;
		this.exprId = exprId;
		this.type = type;
		this.name = name;
		this.content = content;
		this.description = description;		
	}
	
	public SysExpressionVO(String oid, String exprId, String type, String name, String description) {
		this.oid = oid;
		this.exprId = exprId;
		this.type = type;
		this.name = name;
		this.description = description;
	}
	
	public SysExpressionVO(String oid, String exprId, String type, String name) {
		this.oid = oid;
		this.exprId = exprId;
		this.type = type;
		this.name = name;		
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getExprId() {
		return exprId;
	}

	public void setExprId(String exprId) {
		this.exprId = exprId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
