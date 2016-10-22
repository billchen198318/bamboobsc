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
package com.netsteadfast.greenstep.base.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;

import ognl.Ognl;
import ognl.OgnlException;

public class DynamicHqlQueryParamHandler implements java.io.Serializable {
	private static final long serialVersionUID = 8844134742123809153L;
	private static final String FULL_EQUALS = "FULL_EQUALS";
	private static final String BEGINNING_LIKE = "BEGINNING_LIKE";
	private static final String ENDING_LIKE = "ENDING_LIKE";
	private static final String CONTAINING_LIKE = "CONTAINING_LIKE";
	private static final String EQUALS_LIKE = "EQUALS_LIKE";
	private Map<String, Object> value = new LinkedHashMap<String, Object>(); // 依條件組出 , 給 DynamicHql 查詢要用的變數
	private Map<String, Object> root = new HashMap<String, Object>(); // Ognl expression 要用的變數root
	private Map<String, String> sourceSearchParameter; // 查詢頁面的 searchValue.parameter
	
	public DynamicHqlQueryParamHandler() {
		
	}
	
	public DynamicHqlQueryParamHandler(Map<String, String> sourceSearchParameter) {
		this.sourceSearchParameter = sourceSearchParameter;
	}
	
	public Map<String, String> getSourceSearchParameter() {
		return sourceSearchParameter;
	}

	public void setSourceSearchParameter(Map<String, String> sourceSearchParameter) {
		this.sourceSearchParameter = sourceSearchParameter;
	}

	public void setSourceSearchParameterAndRoot(Map<String, String> sourceSearchParameter) {
		this.sourceSearchParameter = sourceSearchParameter;
		if (this.sourceSearchParameter != null) {
			this.root.putAll( this.sourceSearchParameter );
		}
	}	

	public Map<String, Object> getRoot() {
		return root;
	}

	public Map<String, Object> getValue() {
		return value;
	}
	
	public DynamicHqlQueryParamHandler addExprVariable(String key, Object value) {
		root.put(key, value);
		return this;
	}
	
	public DynamicHqlQueryParamHandler fullEquals4TextField(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckNoBlank(value)) {
			this.putValue(FULL_EQUALS, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler fullEquals4Select(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckWithSelectId(value)) {
			this.putValue(FULL_EQUALS, field, value);
		}
		return this;
	}	
	
	public DynamicHqlQueryParamHandler fullEquals(String field, String expression) {
		String value = this.sourceSearchParameter.get(field);
		if (this.root.get(field) == null) {
			this.root.put(field, value);
		}
		if (this.parse(expression)) {
			this.putValue(FULL_EQUALS, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler beginningLike(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckNoBlank(value)) {
			this.putValue(BEGINNING_LIKE, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler beginningLike(String field, String expression) {
		String value = this.sourceSearchParameter.get(field);
		if (this.root.get(field) == null) {
			this.root.put(field, value);
		}
		if (this.parse(expression)) {
			this.putValue(BEGINNING_LIKE, field, value);
		}		
		return this;
	}
	
	public DynamicHqlQueryParamHandler endingLike(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckNoBlank(value)) {
			this.putValue(ENDING_LIKE, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler endingLike(String field, String expression) {
		String value = this.sourceSearchParameter.get(field);
		if (this.root.get(field) == null) {
			this.root.put(field, value);
		}
		if (this.parse(expression)) {
			this.putValue(ENDING_LIKE, field, value);
		}		
		return this;
	}
	
	public DynamicHqlQueryParamHandler containingLike(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckNoBlank(value)) {
			this.putValue(CONTAINING_LIKE, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler containingLike(String field, String expression) {
		String value = this.sourceSearchParameter.get(field);
		if (this.root.get(field) == null) {
			this.root.put(field, value);
		}
		if (this.parse(expression)) {
			this.putValue(CONTAINING_LIKE, field, value);
		}		
		return this;
	}
	
	public DynamicHqlQueryParamHandler equalsLike(String field) {
		String value = this.sourceSearchParameter.get(field);
		if (this.defaultCheckNoBlank(value)) {
			this.putValue(EQUALS_LIKE, field, value);
		}
		return this;
	}
	
	public DynamicHqlQueryParamHandler equalsLike(String field, String expression) {
		String value = this.sourceSearchParameter.get(field);
		if (this.root.get(field) == null) {
			this.root.put(field, value);
		}
		if (this.parse(expression)) {
			this.putValue(EQUALS_LIKE, field, value);
		}		
		return this;
	}
	
	private boolean defaultCheckWithSelectId(String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		}
		if (Constants.HTML_SELECT_NO_SELECT_ID.equals(value)) {
			return false;
		}
		return true;
	}
	
	private boolean defaultCheckNoBlank(String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		}
		return true;
	}	
	
	private boolean parse(String expression) {
		boolean c = false;
		try {
			c = "true".equals( String.valueOf(Ognl.getValue(expression, this.root)).toLowerCase() );
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private void putValue(String type, String field, String value) {
		if (FULL_EQUALS.equals(type) || EQUALS_LIKE.equals(type)) {
			this.value.put(field, value);
		} else if (BEGINNING_LIKE.equals(type)) {
			this.value.put(field, value+"%");
		} else if (ENDING_LIKE.equals(type)) {
			this.value.put(field, "%"+value);
		} else if (CONTAINING_LIKE.equals(type)) {
			this.value.put(field, "%"+value+"%");
		}
	}
	
}
