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
package org.gsweb.components.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.base.GSTag;
import org.gsweb.components.ui.Label;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class LabelTag implements GSTag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String id="";
	private String name="";
	private String text="";
	private String requiredFlag="";
	
	private Label handler() {
		Label label = new Label();
		if (StringUtils.isBlank(this.id)) {
			this.id = SimpleUtils.getUUIDStr();
		}
		label.setId(this.id + Constants.INPUTFIELD_LABEL);
		label.setName(this.name);
		label.setText(this.text);
		label.setRequiredFlag(this.requiredFlag);
		return label;
	}
	
	@Override
	public int doEndTag() throws JspException {
		Label label = this.handler();
		try {
			this.pageContext.getOut().write(label.getHtml());
			this.pageContext.getOut().write(label.getScript());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		label = null;
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		return 0;
	}

	@Override
	public Tag getParent() {
		return this.parent;
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRequiredFlag() {
		return requiredFlag;
	}

	public void setRequiredFlag(String requiredFlag) {
		this.requiredFlag = requiredFlag;
	}
	
}
