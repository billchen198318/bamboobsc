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

public class SwotReportDtlVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 5756484083714818659L;
	private String oid;
	private String reportId;
	private int seq;
	private String label;
	private String issues1;
	private String issues2;
	private String issues3;
	private String issues4;
	
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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIssues1() {
		return issues1;
	}

	public void setIssues1(String issues1) {
		this.issues1 = issues1;
	}

	public String getIssues2() {
		return issues2;
	}

	public void setIssues2(String issues2) {
		this.issues2 = issues2;
	}

	public String getIssues3() {
		return issues3;
	}

	public void setIssues3(String issues3) {
		this.issues3 = issues3;
	}

	public String getIssues4() {
		return issues4;
	}

	public void setIssues4(String issues4) {
		this.issues4 = issues4;
	}

}
