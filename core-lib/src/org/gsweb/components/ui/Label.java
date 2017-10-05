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

import com.netsteadfast.greenstep.base.model.YesNo;

public class Label implements UIComponent {
	private PageContext pageContext = null;
	private String id="";
	private String text="";
	private String requiredFlag=YesNo.NO;
	private StringBuilder htmlOut=new StringBuilder();
	
	private Map<String, Object> getParameters() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		params.put("text", StringEscapeUtils.escapeHtml4(StringUtils.defaultString(this.text)));
		params.put("requiredFlag", (YesNo.YES.equals(requiredFlag) ? YesNo.YES : YesNo.NO));
		return params;
	}
	
	private void generateHtml() {
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					Label.class, IS_HTML, "META-INF/resource/label/ui.label.htm.ftl", this.getParameters()) );
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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	@Override
	public void setName(String name) {
		
	}

	@Override
	public String getName() {
		return "";
	}

	public String getRequiredFlag() {
		return requiredFlag;
	}

	public void setRequiredFlag(String requiredFlag) {
		this.requiredFlag = requiredFlag;
	}
	
}
