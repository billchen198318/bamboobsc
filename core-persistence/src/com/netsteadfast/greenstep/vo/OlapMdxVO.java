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

public class OlapMdxVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 5919999544985302276L;
	private String oid;	
	private String name;
	private byte[] expression;
	private String confOid;
	private String catalogOid;	
	
	public OlapMdxVO() {
		
	}
	
	public OlapMdxVO(String oid, String id, String name, byte[] expression,
			String confOid, String catalogOid, String description) {
		super();
		this.oid = oid;
		this.name = name;
		this.expression = expression;
		this.confOid = confOid;
		this.catalogOid = catalogOid;
	}

	public OlapMdxVO(String oid, String name) {
		super();
		this.oid = oid;
		this.name = name;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getExpression() {
		return expression;
	}

	public void setExpression(byte[] expression) {
		this.expression = expression;
	}

	public String getConfOid() {
		return confOid;
	}

	public void setConfOid(String confOid) {
		this.confOid = confOid;
	}

	public String getCatalogOid() {
		return catalogOid;
	}

	public void setCatalogOid(String catalogOid) {
		this.catalogOid = catalogOid;
	}

}
