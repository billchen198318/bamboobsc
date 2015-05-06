/* 
 * Copyright 2012-2013 bambooBSC of copyright Chen Xin Nien
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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.sys.SysEventLogSupport;
import com.netsteadfast.greenstep.util.ControllerAuthorityCheckUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.ControllerAuthorityCheckInterceptor")
@Scope("prototype")
public class ControllerAuthorityCheckInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -8012366104374649134L;
	protected Logger logger=Logger.getLogger(ControllerAuthorityCheckInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String actionName = actionInvocation.getProxy().getActionName();
		String url = actionName + Constants._S2_ACTION_EXTENSION;		
		Subject subject = SecurityUtils.getSubject();
		/*
		if ( !Constants.getSystem().equals(Constants.getMainSystem()) ) {
			SecurityUtils.setSecurityManager( (DefaultSecurityManager)AppContext.getBean("securityManager") );
			subject = SecurityUtils.getSubject();			
		}
		*/
		if (subject.hasRole(Constants.SUPER_ROLE_ALL) || subject.hasRole(Constants.SUPER_ROLE_ADMIN)) {
			SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, true );
			return actionInvocation.invoke();
		}		
		Annotation[] annotations = actionInvocation.getAction().getClass().getAnnotations();
		Annotation[] actionMethodAnnotations = null;
		Method[] methods = actionInvocation.getAction().getClass().getMethods();
		for (Method method : methods) {
			if (actionInvocation.getProxy().getMethod().equals(method.getName())) {
				actionMethodAnnotations = method.getAnnotations();
			}
		}		
		if (this.isControllerAuthority(annotations, actionMethodAnnotations, subject)) {
			SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, true );
			return actionInvocation.invoke();
		}		
		if (subject.isPermitted(url) || subject.isPermitted("/"+url)) {
			SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, true );
			return actionInvocation.invoke();
		}
		logger.warn("[decline] user=" + subject.getPrincipal() + " url=" + url);
		String isDojoxContentPane = ServletActionContext.getRequest().getParameter(Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD);
		if (YesNo.YES.equals(isDojoxContentPane)) { // dojox.layout.ContentPane 它的 X-Requested-With 是 XMLHttpRequest
			SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, false );
			return Constants._S2_RESULT_NO_AUTHORITH;
		}
		String header = ServletActionContext.getRequest().getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
			printWriter.print(Constants.NO_AUTHZ_JSON_DATA);
            printWriter.flush();
            printWriter.close();
            SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, false );
			return null;
		}
		SysEventLogSupport.log( (String)subject.getPrincipal(), Constants.getSystem(), url, false );
		return Constants._S2_RESULT_NO_AUTHORITH;
	}
	
	private boolean isControllerAuthority(Annotation[] actionAnnotations, Annotation[] actionMethodAnnotations, Subject subject) {
		return ControllerAuthorityCheckUtils.isControllerAuthority(actionAnnotations, actionMethodAnnotations, subject);
	}	

}
