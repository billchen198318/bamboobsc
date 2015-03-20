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

public class SysJreportParamVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -2839906464273160644L;
	private String oid;	
	private String reportId;
	private String urlParam;
	private String rptParam;
	
	public SysJreportParamVO() {
		
	}
	
	public SysJreportParamVO(String oid, String reportId, String urlParam, String rptParam) {
		this.oid = oid;
		this.reportId = reportId;
		this.urlParam = urlParam;
		this.rptParam = rptParam;
	}
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public String getRptParam() {
		return rptParam;
	}

	public void setRptParam(String rptParam) {
		this.rptParam = rptParam;
	}

}
