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

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 將原本 struts2-plugin 輸出的 json資料 { ..資料內容.. } 多補 頭 [   尾 ] 符號
 * 資料輸出變成
 * [{ ..資料內容.. }] 
 * 
 * @author root
 *
 */
@Deprecated
@Component("greenstep.web.interceptor.JsonOutermostBracketsInterceptor")
@Scope("prototype")
public class JsonOutermostBracketsInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -7828751163502473121L;
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext context=actionInvocation.getInvocationContext();
		HttpServletResponse response=(HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		PrintWriter writer=response.getWriter();
		writer.print("[");
		writer.flush();
		String forward=actionInvocation.invoke();
		writer.print("]");
		writer.flush();
		return forward;
	}

}
