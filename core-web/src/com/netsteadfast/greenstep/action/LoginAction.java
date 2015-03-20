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
package com.netsteadfast.greenstep.action;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;

@ControllerAuthority(check=false)
@Component("core.web.controller.LoginAction")
@Scope
public class LoginAction extends BaseSupportAction {
	private static final long serialVersionUID = -5036757280302854465L;
	protected Logger logger=Logger.getLogger(LoginAction.class);
	
	public LoginAction() {
		super();
	}
	
	private void fillErrorMessage() throws Exception {
		
		Object errObj = super.getHttpServletRequest().getAttribute(
				org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if ("com.netsteadfast.greenstep.sys.IncorrectCaptchaException".equals(errObj)) {
			this.setPageMessage("Captcha code incorrect.");
		}		
		if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(errObj)
				|| "org.apache.shiro.authc.UnknownAccountException".equals(errObj)
				|| "org.apache.shiro.authc.AuthenticationException".equals(errObj)) {
			this.setPageMessage("Login fail.");
		}
	}
	
	public String doLogout() throws Exception {
		SecurityUtils.getSubject().logout();		
		UserAccountHttpSessionSupport.remove(super.getHttpServletRequest());
		return SUCCESS;
	}
	
	public String execute() throws Exception {		
		String forward = LOGIN;
		try {
			if (SecurityUtils.getSubject().isAuthenticated()) {
				forward = SUCCESS;
			}						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!SUCCESS.equals(forward)) {
				this.fillErrorMessage();
			}			
		}
		return forward;
	}
	
}
