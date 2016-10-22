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

public class SysFormMethodVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 6470433031220066154L;
	private String oid;
	private String formId;
	private String name;
	private String resultType;
	private String type;
	private byte[] expression;
	private String description;
	
	public SysFormMethodVO() {
		
	}
	
	public SysFormMethodVO(String oid, String formId, String name,
			String resultType, String type, byte[] expression,
			String description) {
		super();
		this.oid = oid;
		this.formId = formId;
		this.name = name;
		this.resultType = resultType;
		this.type = type;
		this.expression = expression;
		this.description = description;
	}

	public SysFormMethodVO(String oid, String formId, String name,
			String resultType, String type, String description) {
		super();
		this.oid = oid;
		this.formId = formId;
		this.name = name;
		this.resultType = resultType;
		this.type = type;
		this.description = description;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getExpression() {
		return expression;
	}

	public void setExpression(byte[] expression) {
		this.expression = expression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
