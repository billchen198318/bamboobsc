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

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.LocaleLangInterceptor")
@Scope("prototype")
public class LocaleLangInterceptor extends AbstractInterceptor {
	protected static Logger logger = Logger.getLogger(LocaleLangInterceptor.class);
	private static final long serialVersionUID = 3463512378191819586L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String lang = UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext() );
		if ( !StringUtils.isBlank(lang) ) {
			Locale locale = new Locale(lang);
			ActionContext.getContext().setLocale(locale);
			//logger.info( "SET-Locale: " + lang );
		}
		return actionInvocation.invoke();
	}

}
