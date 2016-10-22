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

public class SysUploadTranVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 7656621092374090312L;
	private String oid;
	private String tranId;
	private String segmMode;
	private String segmSymbol;
	private String encoding;
	private String exprType;	
	private String helpExpression;
	private int beginLen;
	private String description;	
	
	public SysUploadTranVO() {
		
	}	

	public SysUploadTranVO(String oid, String tranId, String segmMode,
			String segmSymbol, String encoding, String exprType,
			String helpExpression, int beginLen, String description) {
		super();
		this.oid = oid;
		this.tranId = tranId;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
		this.helpExpression = helpExpression;
		this.beginLen = beginLen;
		this.description = description;
	}

	public SysUploadTranVO(String oid, String tranId, String segmMode,
			String segmSymbol, String encoding, String exprType, int beginLen,
			String description) {
		super();
		this.oid = oid;
		this.tranId = tranId;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
		this.beginLen = beginLen;
		this.description = description;
	}

	public SysUploadTranVO(String oid, String tranId, String segmMode,
			String segmSymbol, String encoding, String exprType, int beginLen) {
		super();
		this.oid = oid;
		this.tranId = tranId;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
		this.beginLen = beginLen;
	}

	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getSegmMode() {
		return segmMode;
	}

	public void setSegmMode(String segmMode) {
		this.segmMode = segmMode;
	}

	public String getSegmSymbol() {
		return segmSymbol;
	}

	public void setSegmSymbol(String segmSymbol) {
		this.segmSymbol = segmSymbol;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getExprType() {
		return exprType;
	}

	public void setExprType(String exprType) {
		this.exprType = exprType;
	}

	public String getHelpExpression() {
		return helpExpression;
	}

	public void setHelpExpression(String helpExpression) {
		this.helpExpression = helpExpression;
	}

	public int getBeginLen() {
		return beginLen;
	}

	public void setBeginLen(int beginLen) {
		this.beginLen = beginLen;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
