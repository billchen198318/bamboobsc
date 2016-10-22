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

import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.opensymphony.xwork2.ActionContext;

public class UserAccountHttpSessionSupport {
	protected static Logger logger=Logger.getLogger(UserAccountHttpSessionSupport.class);
	
	public static void create(HttpServletRequest request, AccountObj account, String language) {
		request.getSession().setAttribute(Constants.SESS_ACCOUNT, account);
		request.getSession().setAttribute(Constants.SESS_LANG, language);
	}
	
	public static void create(ActionContext actionContext, AccountObj account, String language) {
		actionContext.getSession().put(Constants.SESS_ACCOUNT, account);
		actionContext.getSession().put(Constants.SESS_LANG, language);
	}
	
	public static void createSysCurrentId(HttpServletRequest request, String sysCurrentId) {
		request.getSession().setAttribute(Constants.SESS_SYSCURRENT_ID, sysCurrentId);
	}
	
	public static void createSysCurrentId(ActionContext actionContext, String sysCurrentId) {
		actionContext.getSession().put(Constants.SESS_SYSCURRENT_ID, sysCurrentId);
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
		try {
			request.getSession().removeAttribute(Constants.SESS_ACCOUNT);
			request.getSession().removeAttribute(Constants.SESS_LANG);
			request.getSession().removeAttribute(Constants.SESS_SYSCURRENT_ID);
		} catch (Exception e) {
			logger.warn( e.getMessage().toString() );
		}
	}
	
	public static void remove(ActionContext actionContext) {
		try {
			actionContext.getSession().remove(Constants.SESS_ACCOUNT);
			actionContext.getSession().remove(Constants.SESS_LANG);
			actionContext.getSession().remove(Constants.SESS_SYSCURRENT_ID);
		} catch (Exception e) {
			logger.warn( e.getMessage().toString() );
		}
	}	
	
	public static void remove(Map<String, Object> session) {
		try {
			session.remove(Constants.SESS_ACCOUNT);
			session.remove(Constants.SESS_LANG);
			session.remove(Constants.SESS_SYSCURRENT_ID);
		} catch (Exception e) {
			logger.warn( e.getMessage().toString() );
		}
	}
	
	public static String getLang(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(Constants.SESS_LANG);
	}
	
	public static String getLang(ActionContext actionContext) {
		return (String)actionContext.getSession().get(Constants.SESS_LANG);
	}

	public static String getLang(Map<String, Object> session) {
		return (String)session.get(Constants.SESS_LANG);
	}
	
	public static String getSysCurrentId(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(Constants.SESS_SYSCURRENT_ID);
	}
	
	public static String getSysCurrentId(ActionContext actionContext) {
		return (String)actionContext.getSession().get(Constants.SESS_SYSCURRENT_ID);
	}

	public static String getSysCurrentId(Map<String, Object> session) {
		return (String)session.get(Constants.SESS_SYSCURRENT_ID);
	}	
	
}
