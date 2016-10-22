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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.IUSessLogHelper;
import com.netsteadfast.greenstep.base.sys.USessLogHelperImpl;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.base.sys.UserCurrentCookie;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.UserLoginInterceptor")
@Scope("prototype")
public class UserLoginInterceptor extends AbstractInterceptor {
	protected static Logger logger = Logger.getLogger(UserLoginInterceptor.class);
	private static final long serialVersionUID = -115850491560281565L;
	private IUSessLogHelper uSessLogHelper;
	private AccountObj accountObj = null;
	
	public UserLoginInterceptor() {
		super();
		uSessLogHelper=new USessLogHelperImpl();
	}
		
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext actionContext=actionInvocation.getInvocationContext();  
		Map<String, Object> session=actionContext.getSession();  
		this.accountObj = (AccountObj)session.get(Constants.SESS_ACCOUNT);
		Map<String, String> dataMap = UserCurrentCookie.getCurrentData( (HttpServletRequest)actionContext.get(StrutsStatics.HTTP_REQUEST) );
		String currentId = StringUtils.defaultString( dataMap.get("currentId") );
		String accountId = StringUtils.defaultString( dataMap.get("account") );
		if (accountObj!=null && !StringUtils.isBlank(accountObj.getAccount()) ) {
			if ( StringUtils.isBlank(currentId) ) {
				currentId = "NULL";
			}
			String sessSysCurrentId = (String)session.get(Constants.SESS_SYSCURRENT_ID);
			if ( !currentId.equals(sessSysCurrentId) ) {
				logger.warn( "currentId: " + currentId + " not equals session variable currentId: " + sessSysCurrentId );
				return this.redirectLogin(actionInvocation, session, currentId, accountId);
			}
			if (uSessLogHelper.countByCurrent(accountObj.getAccount(), currentId)<1) {
				return this.redirectLogin(actionInvocation, session, currentId, accountId);
			}						
			return actionInvocation.invoke();
		} 
		return this.redirectLogin(actionInvocation, session, currentId, accountId);
	}
	
	private String redirectLogin(ActionInvocation actionInvocation, Map<String, Object> session,
			String currentId, String accountId) throws Exception {
		if (session!=null) {
			UserAccountHttpSessionSupport.remove(session);
			if ( !Constants.getSystem().equals(Constants.getMainSystem()) ) { // for gsbsc-web, qcharts-web
				if ( SecurityUtils.getSubject().isAuthenticated() ) {
					SecurityUtils.getSubject().logout();
				}				
			}			
		}		
		String header = ServletActionContext.getRequest().getHeader("X-Requested-With");
		String isDojoContentPaneXhrLoad = ServletActionContext.getRequest().getParameter(Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD);	
		String isIframeMode = ServletActionContext.getRequest().getParameter(Constants.IS_IFRAME_MODE);
		if ("XMLHttpRequest".equalsIgnoreCase(header) && !YesNo.YES.equals(isDojoContentPaneXhrLoad) ) {
			PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
			printWriter.print(Constants.NO_LOGIN_JSON_DATA);
            printWriter.flush();
            printWriter.close();
			return null;
		}		
		if ( !Constants.getSystem().equals(Constants.getMainSystem()) ) { // for gsbsc-web, qcharts-web
			if ( !StringUtils.isBlank(accountId) && !StringUtils.isBlank(currentId) 
					&& this.uSessLogHelper.countByCurrent(accountId, currentId) > 0 ) { 
				/**
				 * 
				 * 有在 CORE-WEB 登入, 但是"這次的登入" 與 "上一次登入" 不同
				 * 必須讓 shiroFilter 重新刷新過 , 所以樣頁面處理 refreshDojoContentPane
				 * 
				 * 如:
				 * 1. 先用 admin 登入  
				 * 2. 登出 此時 core-web 的 session 失效了, 但 gsbsc-web 還存在
				 * 3. 用 tester 登入 , 此時使用 gsbsc-web 或 qcharts-web 時讓 shiroFilter 重新刷新一次
				 * 
				 */
				Annotation[] actionMethodAnnotations = null;
				Method[] methods = actionInvocation.getAction().getClass().getMethods();
				for (Method method : methods) {
					if (actionInvocation.getProxy().getMethod().equals(method.getName())) {
						actionMethodAnnotations = method.getAnnotations();
					}
				}								
				String progId = this.getProgramId( actionMethodAnnotations );
				if ( !StringUtils.isBlank(progId) ) {
					Map<String, Object> valueStackMap = new HashMap<String, Object>();
					valueStackMap.put("progId", progId);
					ActionContext.getContext().getValueStack().push(valueStackMap);			
					logger.warn("do page call refreshDojoContentPane event = " + progId);					
					return "refreshDojoContentPane"; // 重新調用 url , 讓 shiroFilter 重導
				} else {
					HttpServletRequest request = ServletActionContext.getRequest();
					String url = SimpleUtils.getHttpRequestUrl( request );
					logger.warn("redirect URL = " + url );					
					ServletActionContext.getResponse().sendRedirect( url );							
					return null;
				}
				
			}
		}		
		if (YesNo.YES.equals(isIframeMode)) { // iframe 不要導向登入頁面, 這樣頁面怪怪的
			ActionContext.getContext().getValueStack().setValue("errorMessage", "Login session expired, please login again!");
			return Constants._S2_RESULT_ERROR;
		}
		if (YesNo.YES.equals(isDojoContentPaneXhrLoad)) {						
			return Constants._S2_RESULT_LOGIN_AGAIN;
		}
		//return "login";		
		return "logout"; // 導向logout , 讓 logout action 執行 SecurityUtils.getSubject().logout()
	}
	
	private String getProgramId(Annotation[] annotations) {
		String progId = "";
		for (Annotation annotation : annotations) {
			if (annotation instanceof ControllerMethodAuthority) {
				progId = StringUtils.defaultString( ((ControllerMethodAuthority)annotation).programId() );
			}
		}
		if ( StringUtils.isBlank(progId) ) { // 沒有ControllerMethodAuthority , 就找 url 的 prog_id 參數 , 主要是 COMMON FORM 會用到
			progId = StringUtils.defaultString( ActionContext.getContext().getValueStack().findString("prog_id") );			
		}
		return progId;		
	}

}
