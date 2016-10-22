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

import java.util.Map;

import org.activiti.engine.task.Task;

import com.netsteadfast.greenstep.base.model.YesNo;

public class BusinessProcessManagementTaskVO implements java.io.Serializable {	
	private static final long serialVersionUID = -649164140417201907L;
	private Task task;
	private String allowApproval = YesNo.NO;
	private Map<String, Object> variables = null;
	
	public BusinessProcessManagementTaskVO() {
		
	}
	
	public BusinessProcessManagementTaskVO(Task task, String allowApproval, Map<String, Object> variables) {
		super();
		this.task = task;
		this.allowApproval = allowApproval;
		this.variables = variables;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getAllowApproval() {
		return allowApproval;
	}

	public void setAllowApproval(String allowApproval) {
		this.allowApproval = allowApproval;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	
	public String getVariableValueAsString(String key) throws Exception {
		if ( null == this.variables ) {
			return "";
		}
		Object value = this.variables.get(key);
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}
	
}
