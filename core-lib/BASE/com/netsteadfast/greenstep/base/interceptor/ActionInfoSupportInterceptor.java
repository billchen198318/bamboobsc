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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.ActionInfoSupportInterceptor")
@Scope("prototype")
public class ActionInfoSupportInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5275135133994028561L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		/*
		ActionInvocation ai=(ActionInvocation)ActionContext.getContext().get(ActionContext.ACTION_INVOCATION); 
		String action=ai.getProxy().getActionName(); 
		String namespace=ai.getProxy().getNamespace();
		*/
		HttpServletRequest request=ServletActionContext.getRequest(); 
		ActionContext context=actionInvocation.getInvocationContext();	
		String action=actionInvocation.getProxy().getActionName();
		String namespace=actionInvocation.getProxy().getNamespace();
		String remoteAddr=request.getRemoteAddr();
		String referer=request.getHeader("referer");		
		context.getSession().put(Constants.SESS_PAGE_INFO_ACTION_ByInterceptor, action);
		context.getSession().put(Constants.SESS_PAGE_INFO_NAMESPACE_ByInterceptor, namespace);
		context.getSession().put(Constants.SESS_PAGE_INFO_RemoteAddr_ByInterceptor, remoteAddr);
		context.getSession().put(Constants.SESS_PAGE_INFO_Referer_ByInterceptor, referer);	
		return actionInvocation.invoke();
	}
	
}
