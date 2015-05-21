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

public class SysFtpTranVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 8787445212402269610L;
	private String oid;		
	private String ftpId;
	private String tranId;
	private String tranType;
	private String cwd;
	private String useSegm;
	private String segmMode;
	private String segmSymbol;
	private String encoding;	
	private String exprType;
	private String nameExpression;
	private String helpExpression;
	private String description;	
	
	public SysFtpTranVO() {
		
	}	

	public SysFtpTranVO(String oid, String ftpId, String tranId,
			String tranType, String cwd, String useSegm, String segmMode,
			String segmSymbol, String encoding, String exprType,
			String nameExpression, String helpExpression, String description) {
		super();
		this.oid = oid;
		this.ftpId = ftpId;
		this.tranId = tranId;
		this.tranType = tranType;
		this.cwd = cwd;
		this.useSegm = useSegm;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
		this.nameExpression = nameExpression;
		this.helpExpression = helpExpression;
		this.description = description;
	}

	public SysFtpTranVO(String oid, String ftpId, String tranId,
			String tranType, String cwd, String useSegm, String segmMode,
			String segmSymbol, String encoding, String exprType,
			String description) {
		super();
		this.oid = oid;
		this.ftpId = ftpId;
		this.tranId = tranId;
		this.tranType = tranType;
		this.cwd = cwd;
		this.useSegm = useSegm;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
		this.description = description;
	}

	public SysFtpTranVO(String oid, String ftpId, String tranId,
			String tranType, String cwd, String useSegm, String segmMode,
			String segmSymbol, String encoding, String exprType) {
		super();
		this.oid = oid;
		this.ftpId = ftpId;
		this.tranId = tranId;
		this.tranType = tranType;
		this.cwd = cwd;
		this.useSegm = useSegm;
		this.segmMode = segmMode;
		this.segmSymbol = segmSymbol;
		this.encoding = encoding;
		this.exprType = exprType;
	}

	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getFtpId() {
		return ftpId;
	}

	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getCwd() {
		return cwd;
	}

	public void setCwd(String cwd) {
		this.cwd = cwd;
	}

	public String getUseSegm() {
		return useSegm;
	}

	public void setUseSegm(String useSegm) {
		this.useSegm = useSegm;
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

	public String getNameExpression() {
		return nameExpression;
	}

	public void setNameExpression(String nameExpression) {
		this.nameExpression = nameExpression;
	}

	public String getHelpExpression() {
		return helpExpression;
	}

	public void setHelpExpression(String helpExpression) {
		this.helpExpression = helpExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
