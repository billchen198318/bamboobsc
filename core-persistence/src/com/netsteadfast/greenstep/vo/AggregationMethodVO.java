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

public class AggregationMethodVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 1268453055878561495L;
	private String oid;	
	private String aggrId;
	private String name;
	private String type;
	private String expression1;
	private String expression2;
	private String description;
	
	public AggregationMethodVO() {
		
	}
	
	public AggregationMethodVO(String oid, String aggrId, String name,
			String type, String expression1, String expression2, String description) {
		super();
		this.oid = oid;
		this.aggrId = aggrId;
		this.name = name;
		this.type = type;
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.description = description;
	}
	
	public AggregationMethodVO(String oid, String aggrId, String name,
			String type) {
		super();
		this.oid = oid;
		this.aggrId = aggrId;
		this.name = name;
		this.type = type;
	}
	
	public AggregationMethodVO(String oid, String aggrId, String name,
			String type, String description) {
		super();
		this.oid = oid;
		this.aggrId = aggrId;
		this.name = name;
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

	public String getAggrId() {
		return aggrId;
	}

	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression1() {
		return expression1;
	}

	public void setExpression1(String expression1) {
		this.expression1 = expression1;
	}

	public String getExpression2() {
		return expression2;
	}

	public void setExpression2(String expression2) {
		this.expression2 = expression2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
