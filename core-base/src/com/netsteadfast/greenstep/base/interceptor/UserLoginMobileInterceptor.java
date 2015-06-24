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
package com.netsteadfast.greenstep.base.interceptor;

import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * for mobile-ver web
 *
 */
@Component("greenstep.web.interceptor.UserLoginMobileInterceptor")
@Scope("prototype")
public class UserLoginMobileInterceptor extends AbstractInterceptor {
	protected static Logger logger = Logger.getLogger(UserLoginMobileInterceptor.class);
	private static final long serialVersionUID = -7712139684710943707L;
	
	public UserLoginMobileInterceptor() {
		super();
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		AccountObj accountObj = UserAccountHttpSessionSupport.get(ServletActionContext.getRequest());
		String accountId = accountObj.getAccount();
		if (SecurityUtils.getSubject().isAuthenticated() && !StringUtils.isBlank(accountId)) {
			return actionInvocation.invoke();
		}		
		String header = ServletActionContext.getRequest().getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
			printWriter.print(Constants.NO_LOGIN_JSON_DATA);
            printWriter.flush();
            printWriter.close();
			return null;			
		}
		return "logout";
	}

}
