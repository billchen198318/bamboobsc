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

public class FormulaVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 6376012277840922106L;
	private String oid;
	private String forId;
	private String name;
	private String type;
	private String trendsFlag;
	private String returnMode;
	private String returnVar;
	private String expression;
	private String description;
	
	public FormulaVO() {
		
	}
	
	public FormulaVO(String oid, String forId, String name, String type, String trendsFlag,
			String returnMode, String returnVar, String expression, String description) {
		this.oid = oid;
		this.forId = forId;
		this.name = name;
		this.type = type;
		this.trendsFlag = trendsFlag;
		this.returnMode = returnMode;
		this.returnVar = returnVar;
		this.expression = expression;
		this.description = description;
	}	
	
	public FormulaVO(String oid, String forId, String name, String type, String trendsFlag) {
		this.oid = oid;
		this.forId = forId;
		this.name = name;
		this.type = type;
		this.trendsFlag = trendsFlag;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getForId() {
		return forId;
	}

	public void setForId(String forId) {
		this.forId = forId;
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

	public String getTrendsFlag() {
		return trendsFlag;
	}

	public void setTrendsFlag(String trendsFlag) {
		this.trendsFlag = trendsFlag;
	}

	public String getReturnMode() {
		return returnMode;
	}

	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}

	public String getReturnVar() {
		return returnVar;
	}

	public void setReturnVar(String returnVar) {
		this.returnVar = returnVar;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
