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

public class SwotVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 5255493386709276632L;
	private String oid;	
	private String visId;
	private String perId;
	private String orgId;
	private String type;
	private String issues;
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIssues() {
		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

}
