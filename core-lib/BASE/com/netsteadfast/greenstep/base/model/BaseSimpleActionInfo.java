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
package com.netsteadfast.greenstep.base.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class BaseSimpleActionInfo implements ActionInfoProvide {
	private String pageInfoActionName=""; // action
	private String pageInfoNamespace=""; // namespace
	private String pageInfoRemoteAddr=""; // remote addr
	private String pageInfoReferer=""; // referer
	private String actionMethodName = ""; // method name
	private Annotation[] actionAnnotations;
	private Annotation[] actionMethodAnnotations;
	
	public BaseSimpleActionInfo() {
		this.execute();
	}
	
	public void execute() {
		ActionInvocation actionInvocation=ActionContext.getContext().getActionInvocation();		
		HttpServletRequest request=ServletActionContext.getRequest(); 
		
		String action=SimpleUtils.getStr(actionInvocation.getProxy().getActionName(), "");
		String namespace=SimpleUtils.getStr(actionInvocation.getProxy().getNamespace(), "");		
		String remoteAddr=SimpleUtils.getStr(request.getRemoteAddr(), "");
		String referer=SimpleUtils.getStr(request.getHeader("referer"), "");
		this.actionMethodName = actionInvocation.getProxy().getMethod();
		
		ActionContext.getContext().getSession().put(Constants.SESS_PAGE_INFO_ACTION_ByAction, action);
		ActionContext.getContext().getSession().put(Constants.SESS_PAGE_INFO_NAMESPACE_ByAction, namespace);
		ActionContext.getContext().getSession().put(Constants.SESS_PAGE_INFO_RemoteAddr_ByAction, remoteAddr);
		ActionContext.getContext().getSession().put(Constants.SESS_PAGE_INFO_Referer_ByAction, referer);
		this.pageInfoActionName=action;
		this.pageInfoNamespace=namespace;
		this.pageInfoRemoteAddr=remoteAddr;
		this.pageInfoReferer=referer;
		
	}
	
	public void handlerActionAnnotations() {
		if (this.actionAnnotations!=null) {
			return;
		}
		ActionInvocation actionInvocation=ActionContext.getContext().getActionInvocation();
		this.actionAnnotations = actionInvocation.getAction().getClass().getAnnotations();
		Method[] methods = actionInvocation.getAction().getClass().getMethods();
		for (Method method : methods) {
			if (this.actionMethodName.equals(method.getName())) {
				this.actionMethodAnnotations = method.getAnnotations();
			}
		}		
	}
	
	@Override
	public String getPageInfoActionName() {
		return pageInfoActionName;
	}
	
	@Override
	public void setPageInfoActionName(String pageInfoActionName) {
		this.pageInfoActionName = pageInfoActionName;
	}
	
	@Override
	public String getPageInfoNamespace() {
		return pageInfoNamespace;
	}
	
	@Override
	public void setPageInfoNamespace(String pageInfoNamespace) {
		this.pageInfoNamespace = pageInfoNamespace;
	}
	
	@Override
	public String getPageInfoRemoteAddr() {
		return pageInfoRemoteAddr;
	}
	
	@Override
	public void setPageInfoRemoteAddr(String pageInfoRemoteAddr) {
		this.pageInfoRemoteAddr = pageInfoRemoteAddr;
	}
	
	@Override
	public String getPageInfoReferer() {
		return pageInfoReferer;
	}
	
	@Override
	public void setPageInfoReferer(String pageInfoReferer) {
		this.pageInfoReferer = pageInfoReferer;
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	public Annotation[] getActionAnnotations() {
		return actionAnnotations;
	}

	public void setActionAnnotations(Annotation[] actionAnnotations) {
		this.actionAnnotations = actionAnnotations;
	}

	public Annotation[] getActionMethodAnnotations() {
		return actionMethodAnnotations;
	}

	public void setActionMethodAnnotations(Annotation[] actionMethodAnnotations) {
		this.actionMethodAnnotations = actionMethodAnnotations;
	}
	
}
