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

public class SysTemplateParamVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -2072788068982844394L;
	private String oid;
	private String templateId;	
	private String isTitle;
	private String templateVar;
	private String objectVar;	
	
	public SysTemplateParamVO() {
		
	}
	
	public SysTemplateParamVO(String oid, String templateId, String isTitle, 
			String templateVar, String objectVar) {
		this.oid = oid;
		this.templateId = templateId;
		this.isTitle = isTitle;
		this.templateVar = templateVar;
		this.objectVar = objectVar;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(String isTitle) {
		this.isTitle = isTitle;
	}

	public String getTemplateVar() {
		return templateVar;
	}

	public void setTemplateVar(String templateVar) {
		this.templateVar = templateVar;
	}

	public String getObjectVar() {
		return objectVar;
	}

	public void setObjectVar(String objectVar) {
		this.objectVar = objectVar;
	}

}
