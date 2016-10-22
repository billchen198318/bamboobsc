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
import org.gsweb.components.ui.Select;

public class SelectTag implements GSTag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String id="";
	private String name="";
	private String width = "200";
	private String dataSource="";
	private String value = "";
	private String onChange = "";
	private String readonly = "N";
	
	private Select handler() {
		this.width = this.width.toLowerCase();
		if (this.width.endsWith("px")) {
			this.width = StringUtils.replaceOnce(this.width, "px", "");
		}		
		Select select = new Select();
		select.setId(this.id);
		select.setName(this.name);
		select.setWidth( Integer.parseInt(this.width) );
		select.setDataSource(this.dataSource);
		select.setValue(this.value);
		select.setOnChange(this.onChange);
		select.setReadonly(this.readonly);
		return select;
	}
	
	@Override
	public int doEndTag() throws JspException {
		Select select = this.handler();
		try {
			this.pageContext.getOut().write(select.getHtml());
			this.pageContext.getOut().write(select.getScript());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		select = null;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public PageContext getPageContext() {
		return pageContext;
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
