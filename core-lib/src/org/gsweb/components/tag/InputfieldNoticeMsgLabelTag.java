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

import org.gsweb.components.base.GSTag;
import org.gsweb.components.ui.InputfieldNoticeMsgLabel;

import com.netsteadfast.greenstep.base.Constants;

public class InputfieldNoticeMsgLabelTag implements GSTag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String id="";
	private String name="";
	private String message="";
	
	private InputfieldNoticeMsgLabel handler() {
		InputfieldNoticeMsgLabel msgLabel = new InputfieldNoticeMsgLabel();
		msgLabel.setId(this.id + Constants.INPUTFIELD_NOTICE_MESSAGE_LABEL);
		msgLabel.setName(this.name);
		msgLabel.setMessage(this.message);
		return msgLabel;
	}
	
	@Override
	public int doEndTag() throws JspException {
		InputfieldNoticeMsgLabel msgLabel = this.handler();
		try {
			this.pageContext.getOut().write(msgLabel.getHtml());
			this.pageContext.getOut().write(msgLabel.getScript());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		msgLabel = null;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
