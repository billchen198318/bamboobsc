/* 
 * Copyright 2012-2017 bambooBSC of copyright Chen Xin Nien
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
package com.netsteadfast.greenstep.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;

/**
 * Copy From:
 * 
 * Simple Servlet Filter that sets the Log4J MDC to the currently logged-in
 * user. This filter is intended to be used with JBoss&acute;s http-invoker web
 * application to identify/separate parallel fat client RMI requests.
 * 
 * $id: $
 * 
 * @author Matthias G&auml;rtner
 * 
 */
public class MDCUserServletFilter implements Filter {
	private static final String _USERID_KEY_NAME = "userId";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		boolean setUserId = false;
		if ( session != null && session.getAttribute(Constants.SESS_ACCOUNT) != null ) {
			String accountId = ((AccountObj)session.getAttribute(Constants.SESS_ACCOUNT)).getAccount();
			MDC.put(_USERID_KEY_NAME, accountId);
			setUserId = true;
		}
		if (!setUserId) {
			String url = httpRequest.getRequestURL().toString();
			if (url.indexOf("/services/") > -1) {
				MDC.put(_USERID_KEY_NAME, "CXF-WEBSERVICE");
				setUserId = true;
			}
			if (url.indexOf("/camel/") > -1) {
				MDC.put(_USERID_KEY_NAME, "CAMEL-ESB");
				setUserId = true;
			}
		}
		try {
			chain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		} finally {
			if (setUserId) {
				MDC.remove(_USERID_KEY_NAME);
			}
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
}
