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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.model.MenuResultObj;
import com.netsteadfast.greenstep.util.MenuSupportUtils;

/**
 * 選單產生的Action
 *
 */
@ControllerAuthority(check=false)
@Controller("core.web.controller.MenuGenerateAction")
@Scope
public class MenuGenerateAction extends BaseSupportAction {
	private static final long serialVersionUID = -2212991103378640997L;
	protected Logger logger=Logger.getLogger(MenuGenerateAction.class);
	private InputStream inputStream;
	
	public MenuGenerateAction() {
		super();
	}
	
	/**
	 * 下拉選單
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateMenu() throws Exception {
		try {
			MenuResultObj menuData = MenuSupportUtils.getMenuData(
					super.getBasePath(),
					super.getHttpServletRequest().getSession().getId(),
					UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext()));
			this.inputStream=new ByteArrayInputStream( menuData.getHtmlData().getBytes() );			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;		
	}
	
	/**
	 * 下拉選單要用的 javascript
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateMenuJs() throws Exception {
		try {
			MenuResultObj menuData = MenuSupportUtils.getMenuData(
					super.getBasePath(),
					super.getHttpServletRequest().getSession().getId(),
					UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext()));
			this.inputStream=new ByteArrayInputStream( menuData.getJavascriptData().getBytes() );			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;				
	}
	
	public InputStream getInputStream() {
		if (this.inputStream==null) {
			this.inputStream=new ByteArrayInputStream("<!-- error com.netsteadfast.greenstep.base.action.MenuGenerateAction -->".getBytes());
		}		
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}	
	
}
