/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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

public class TsaMaCoefficientsVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 3351302549654721846L;
	private String oid;
	private String tsaOid;
	private int seq;
	private float seqValue;
	
	public TsaMaCoefficientsVO() {
		
	}
	
	public TsaMaCoefficientsVO(String oid, String tsaOid, int seq, float seqValue) {
		super();
		this.oid = oid;
		this.tsaOid = tsaOid;
		this.seq = seq;
		this.seqValue = seqValue;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getTsaOid() {
		return tsaOid;
	}

	public void setTsaOid(String tsaOid) {
		this.tsaOid = tsaOid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public float getSeqValue() {
		return seqValue;
	}

	public void setSeqValue(float seqValue) {
		this.seqValue = seqValue;
	}
	
	// 編輯Edit頁面 dijit/form/NumberSpinner 要用的數值 
	public String getSeqValueAsString() {
		return ( this.seqValue >= 0.0f ? "+" : "" ) + String.valueOf(this.seqValue);
	}
	
}
