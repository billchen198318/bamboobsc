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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.sys.SysQueryParamInspectUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.QueryParamInspectInterceptor")
@Scope("prototype")
public class QueryParamInspectInterceptor extends AbstractInterceptor {
	protected static Logger logger = Logger.getLogger(QueryParamInspectInterceptor.class);
	private static final long serialVersionUID = -9147160140755133572L;
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Annotation[] actionMethodAnnotations = null;
		Method[] methods = actionInvocation.getAction().getClass().getMethods();
		for (Method method : methods) {
			if (actionInvocation.getProxy().getMethod().equals(method.getName())) {
				actionMethodAnnotations = method.getAnnotations();
			}
		}		
		if (actionMethodAnnotations!=null && actionMethodAnnotations.length>0) {
			try {
				this.log(actionInvocation, actionMethodAnnotations);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}		
		return actionInvocation.invoke();
	}
	
	private void log(ActionInvocation actionInvocation, Annotation[] annotations) throws ServiceException, Exception {
		String progId = "";
		for (Annotation anno : annotations) {
			if (anno instanceof ControllerMethodAuthority) {
				progId = ((ControllerMethodAuthority)anno).programId();
			}
		}
		if (StringUtils.isBlank(progId)) {
			return;
		}
		SysQueryParamInspectUtils.log(
				Constants.getSystem(), 
				progId, 
				actionInvocation.getProxy().getMethod(), 
				ServletActionContext.getRequest());		
	}

}
