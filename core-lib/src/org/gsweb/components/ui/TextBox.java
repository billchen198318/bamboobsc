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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

import com.opensymphony.xwork2.ActionContext;

public class TextBox implements UIComponent {
	private PageContext pageContext = null;
	private String id="";
	private String name="";
	private int maxlength = 20;
	private String placeHolder = "";
	private int width = 200;
	private String value  = "";
	private String readonly = "N";
	private StringBuilder htmlOut=new StringBuilder();	
	
	private Map<String, Object> getParameters(String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		params.put("name", this.name);
		params.put("maxlength", String.valueOf(this.maxlength));
		params.put("placeHolder", StringEscapeUtils.escapeEcmaScript(this.placeHolder) );
		params.put("width", String.valueOf(this.width));
		params.put("value", this.value);
		Object valueStackObj = null;
		if (!StringUtils.isBlank(this.value)) {
			valueStackObj = ActionContext.getContext().getValueStack().findValue(this.value);
		}				
		if (valueStackObj != null) { // 存在 action 中的變數的話就使用這個變術的值		
			this.fillValueByValueStackObj(params, valueStackObj);
		}
		params.put("readonly", this.readonly);
		return params;
	}
	
	private void fillValueByValueStackObj(Map<String, Object> params, Object valueStackObj) {
		if (valueStackObj instanceof java.lang.String) {
			params.put("value", StringEscapeUtils.escapeHtml4( (String)valueStackObj ) );
			return;
		}		
		if (valueStackObj instanceof java.lang.Integer) {
			params.put("value", String.valueOf( (Integer)valueStackObj ) );
			return;			
		}
		if (valueStackObj instanceof java.lang.Long) {
			params.put("value", String.valueOf( (Long)valueStackObj ) );
			return;						
		}
		if (valueStackObj instanceof java.math.BigDecimal) {
			params.put("value", ((java.math.BigDecimal)valueStackObj).toString() );
			return;					
		}
		if (valueStackObj instanceof java.math.BigInteger) {
			params.put("value", ((java.math.BigInteger)valueStackObj).toString() );
			return;							
		}
		if (valueStackObj instanceof java.lang.Float) {
			params.put("value", String.valueOf( (Float)valueStackObj ) );
			return;						
		}
		if (valueStackObj instanceof java.lang.Double) {
			params.put("value", String.valueOf( (Double)valueStackObj ) );
			return;						
		}			
	}
	
	private void generateHtml() {
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					TextBox.class, IS_HTML, "META-INF/resource/textbox/ui.textbox.htm.ftl", this.getParameters(IS_HTML)) );
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
		return " ";
	}

	@Override
	public String getHtml() throws Exception {
		this.generateHtml();
		return this.htmlOut.toString();
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
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

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

}
