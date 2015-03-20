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

public class SysBeanHelpExprMapVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -8097682343774335311L;
	private String oid;
	private String helpExprOid;
	private String methodResultFlag;
	private String methodParamClass;
	private int methodParamIndex;
	private String varName;
	
	public SysBeanHelpExprMapVO() {
		
	}
	
	public SysBeanHelpExprMapVO(String oid, String helpExprOid, String methodResultFlag,
			String methodParamClass, int methodParamIndex, String varName) {
		this.oid = oid;
		this.helpExprOid = helpExprOid;
		this.methodResultFlag = methodResultFlag;
		this.methodParamClass = methodParamClass;
		this.methodParamIndex = methodParamIndex;
		this.varName = varName;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getHelpExprOid() {
		return helpExprOid;
	}

	public void setHelpExprOid(String helpExprOid) {
		this.helpExprOid = helpExprOid;
	}

	public String getMethodResultFlag() {
		return methodResultFlag;
	}

	public void setMethodResultFlag(String methodResultFlag) {
		this.methodResultFlag = methodResultFlag;
	}

	public String getMethodParamClass() {
		return methodParamClass;
	}

	public void setMethodParamClass(String methodParamClass) {
		this.methodParamClass = methodParamClass;
	}

	public int getMethodParamIndex() {
		return methodParamIndex;
	}

	public void setMethodParamIndex(int methodParamIndex) {
		this.methodParamIndex = methodParamIndex;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

}
