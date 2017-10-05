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
package org.gsweb.components.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;

public class FilteringSelect implements UIComponent {
	private PageContext pageContext = null;
	private String id="";
	private String name="";
	private String dataSource="";
	private int width = 200;
	private String value = "";
	private String onChange = "";
	private String readonly = "N";
	private StringBuilder script=new StringBuilder();
	private StringBuilder htmlOut=new StringBuilder();
	private Map<String, String> dataMap = null;
	
	@SuppressWarnings("unchecked")
	private void handlerDataSource() throws JsonParseException, JsonMappingException, IOException {
		if (this.dataMap!=null) {
			return;
		}
		this.dataMap = new LinkedHashMap<String, String>();
		if (StringUtils.isBlank(this.dataSource)) {
			return;
		}
		Object dataSourceObj = ActionContext.getContext().getValueStack().findValue(this.dataSource);
		if (dataSourceObj == null) { // tag 傳過來的資料
			this.dataMap = (Map<String, String>)new ObjectMapper().readValue(this.dataSource, LinkedHashMap.class);
			return;
		}
		if (dataSourceObj instanceof java.lang.String) { // action 傳過來的資料
			this.dataMap = (Map<String, String>)new ObjectMapper().readValue((String)dataSourceObj, LinkedHashMap.class);
		}
		if (dataSourceObj instanceof Map) { // action 傳過來的資料
			this.dataMap = ((Map<String, String>)dataSourceObj);
		}
	}
	
	private Map<String, Object> getParameters(String type) {
		try {
			this.handlerDataSource();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		params.put("name", this.name);
		params.put("width", String.valueOf(this.width));
		params.put("dataSource", this.dataMap);
		params.put("value", this.value);
		Object valueStackObj = null;
		if (!StringUtils.isBlank(this.value)) {
			valueStackObj = ActionContext.getContext().getValueStack().findValue(this.value);
		}
		if (valueStackObj != null && valueStackObj instanceof java.lang.String ) { // 存在 action 中的變數的話就使用這個變術的值		
			params.put("value", StringEscapeUtils.escapeHtml4( (String)valueStackObj ) );
		}
		if (valueStackObj != null 
				&& ( valueStackObj instanceof Long || valueStackObj instanceof Integer ) ) { // 存在 action 中的變數的話就使用這個變術的值		
			params.put("value", String.valueOf(valueStackObj) );
		}		
		if (!StringUtils.isBlank(this.onChange)) {
			params.put("onChange", this.onChange);
		}
		params.put("readonly", this.readonly);
		return params;
	}
	
	private void generateHtml() {
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					FilteringSelect.class, IS_HTML, "META-INF/resource/select/ui.filtering-select.htm.ftl", this.getParameters(IS_HTML)) );
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getScript() throws Exception {
		return script.toString();
	}

	@Override
	public String getHtml() throws Exception {
		this.generateHtml();
		return htmlOut.toString();
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

}
