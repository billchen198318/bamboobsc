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
package com.netsteadfast.greenstep.base.action;

import java.util.HashMap;
import java.util.Map;

import com.netsteadfast.greenstep.base.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -2023423973050352242L;
	public static final String RESULT_BLANK=Constants._S2_RESULT_BLANK;
	public static final String RESULT_BLANK_VALUE=Constants._S2_RESULT_BLANK_VALUE;
	public static final String RESULT_NO_AUTHORITH=Constants._S2_RESULT_NO_AUTHORITH;
	public static final String RESULT_SEARCH_NO_DATA=Constants._S2_RESULT_SEARCH_NO_DATA;
	public static final String RESULT_LOGIN_AGAIN=Constants._S2_RESULT_LOGIN_AGAIN;
	public static final String RESULT_LOGOUT_AGAIN=Constants._S2_RESULT_LOGOUT_AGAIN;
	private Map<String, Object> request;
	private Map<String, Object> session;
	private Map<String, Object> application;	
	private Map<String, String> fields=new HashMap<String, String>();
	
	@SuppressWarnings("unchecked")
	public BaseAction() {
		super();
		this.request=(Map<String, Object>)ActionContext.getContext().get("request");
		this.session=(Map<String, Object>)ActionContext.getContext().getSession();
		this.application=(Map<String, Object>)ActionContext.getContext().getApplication();	
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	
}
