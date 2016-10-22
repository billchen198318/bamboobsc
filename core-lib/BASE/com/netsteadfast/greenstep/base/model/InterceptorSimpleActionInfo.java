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

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class InterceptorSimpleActionInfo implements ActionInfoProvide {
	private String pageInfoActionName=""; // action
	private String pageInfoNamespace=""; // namespace
	private String pageInfoRemoteAddr=""; // remote addr
	private String pageInfoReferer=""; // referer
	
	public InterceptorSimpleActionInfo() {
		this.execute();
	}
	
	public void execute() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		this.pageInfoActionName=SimpleUtils.getStr((String)session.getAttribute(Constants.SESS_PAGE_INFO_ACTION_ByInterceptor), "");
		this.pageInfoNamespace=SimpleUtils.getStr((String)session.getAttribute(Constants.SESS_PAGE_INFO_NAMESPACE_ByInterceptor), "");
		this.pageInfoReferer=SimpleUtils.getStr((String)session.getAttribute(Constants.SESS_PAGE_INFO_Referer_ByInterceptor), "");
		this.pageInfoRemoteAddr=SimpleUtils.getStr((String)session.getAttribute(Constants.SESS_PAGE_INFO_RemoteAddr_ByInterceptor), "");		
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
	
}
