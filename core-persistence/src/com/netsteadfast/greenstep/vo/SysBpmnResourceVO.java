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

public class SysBpmnResourceVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 4077554004791666977L;
	private String oid;	
	private String id;
	private String deploymentId;
	private String name;
	private byte[] content;
	private String description;
	
	public SysBpmnResourceVO() {
		
	}
	
	public SysBpmnResourceVO(String oid, String id, String deploymentId,
			String name, String description) {
		super();
		this.oid = oid;
		this.id = id;
		this.deploymentId = deploymentId;
		this.name = name;
		this.description = description;
	}
	
	public SysBpmnResourceVO(String oid, String id, String deploymentId,
			String name, byte[] content, String description) {
		super();
		this.oid = oid;
		this.id = id;
		this.deploymentId = deploymentId;
		this.name = name;
		this.content = content;
		this.description = description;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
