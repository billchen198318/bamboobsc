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

public class SysBpmnResourceRoleVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 7260862625340761454L;
	private String oid;	
	private String id;
	private String role;
	private String taskName;
	
	// -------------------------------------------------
	// for Grid query tb_sys_bpmn_resource.name
	private String name;
	// -------------------------------------------------
	
	public SysBpmnResourceRoleVO() {
		super();
	}

	public SysBpmnResourceRoleVO(String oid, String id, String role,
			String taskName) {
		super();
		this.oid = oid;
		this.id = id;
		this.role = role;
		this.taskName = taskName;
	}

	public SysBpmnResourceRoleVO(String oid, String id, String role, String taskName, String name) {
		super();
		this.oid = oid;
		this.id = id;
		this.role = role;
		this.taskName = taskName;
		this.name = name;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
