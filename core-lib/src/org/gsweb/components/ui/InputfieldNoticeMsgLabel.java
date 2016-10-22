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

import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

public class InputfieldNoticeMsgLabel implements UIComponent {
	private PageContext pageContext = null;
	private String id="";
	private String name="";
	private String message="";
	private StringBuilder htmlOut=new StringBuilder();
	
	private Map<String, Object> getParameters() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		params.put("message", StringUtils.defaultString(this.message));
		return params;
	}
	
	private void generateHtml() {
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					InputfieldNoticeMsgLabel.class, IS_HTML, "META-INF/resource/inputfield-notice-msg-label/ui.inputfield-notice-msg-label.htm.ftl", this.getParameters()) );
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getScript() throws Exception {
		return "";
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

}
