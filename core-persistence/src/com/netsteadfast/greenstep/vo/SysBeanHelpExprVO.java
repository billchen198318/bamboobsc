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

public class SysBeanHelpExprVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 7730143312476877752L;
	private String oid;
	private String helpOid;
	private String exprId;
	private String exprSeq;
	private String runType;
	
	public SysBeanHelpExprVO() {
		
	}
	
	public SysBeanHelpExprVO(String oid, String helpOid, 
			String exprId, String exprSeq, String runType) {
		this.oid = oid;
		this.helpOid = helpOid;
		this.exprId = exprId;
		this.exprSeq = exprSeq;
		this.runType = runType;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getHelpOid() {
		return helpOid;
	}

	public void setHelpOid(String helpOid) {
		this.helpOid = helpOid;
	}

	public String getExprId() {
		return exprId;
	}

	public void setExprId(String exprId) {
		this.exprId = exprId;
	}

	public String getExprSeq() {
		return exprSeq;
	}

	public void setExprSeq(String exprSeq) {
		this.exprSeq = exprSeq;
	}

	public String getRunType() {
		return runType;
	}

	public void setRunType(String runType) {
		this.runType = runType;
	}
	
}
