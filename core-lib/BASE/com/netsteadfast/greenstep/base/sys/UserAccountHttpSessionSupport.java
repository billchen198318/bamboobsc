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
package com.netsteadfast.greenstep.base.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.opensymphony.xwork2.ActionContext;

public class UserAccountHttpSessionSupport {
	
	public static void create(HttpServletRequest request, AccountObj account) {
		request.getSession().setAttribute(Constants.SESS_ACCOUNT, account);
	}
	
	public static void create(ActionContext actionContext, AccountObj account) {
		actionContext.getSession().put(Constants.SESS_ACCOUNT, account);
	}
	
	public static AccountObj get(HttpServletRequest request) {
		return (AccountObj)request.getSession().getAttribute(Constants.SESS_ACCOUNT);
	}
	
	public static AccountObj get(ActionContext actionContext) {
		return (AccountObj)actionContext.getSession().get(Constants.SESS_ACCOUNT);
	}

	public static AccountObj get(Map<String, Object> session) {
		return (AccountObj)session.get(Constants.SESS_ACCOUNT);
	}
	
	public static void remove(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.SESS_ACCOUNT);
	}
	
	public static void remove(ActionContext actionContext) {
		actionContext.getSession().remove(Constants.SESS_ACCOUNT);
	}	
	
	public static void remove(Map<String, Object> session) {
		session.remove(Constants.SESS_ACCOUNT);
	}
	
}
