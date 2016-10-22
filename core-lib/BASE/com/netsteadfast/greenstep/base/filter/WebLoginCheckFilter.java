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
package com.netsteadfast.greenstep.base.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.netsteadfast.greenstep.base.sys.IUSessLogHelper;
import com.netsteadfast.greenstep.base.sys.USessLogHelperImpl;
import com.netsteadfast.greenstep.base.sys.UserCurrentCookie;

public class WebLoginCheckFilter implements Filter {
	private FilterConfig filterConfig = null;
	private IUSessLogHelper uSessLogHelper = null;
	
	private boolean isLogin(HttpServletRequest httpRequest, AccountObj accountObj) {
		Map<String, String> dataMap = UserCurrentCookie.getCurrentData(httpRequest);	
		String account = StringUtils.defaultString( dataMap.get("account") );
		String currentId = StringUtils.defaultString( dataMap.get("currentId") );
		if (StringUtils.isBlank(account)) {
			return false;
		}
		if (!accountObj.getAccount().equals(account)) {
			return false;
		}
		try {
			if (this.uSessLogHelper.countByCurrent(account, currentId) > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = filterConfig.getInitParameter("redirectUrl");
		if ( StringUtils.isBlank(redirectUrl) ) {
			redirectUrl = "/pages/system/login_again.jsp";
		}
		Object accountObj = httpRequest.getSession().getAttribute( Constants.SESS_ACCOUNT );
		if ( accountObj == null || !( accountObj instanceof AccountObj ) ) {
			httpResponse.sendRedirect( request.getServletContext().getContextPath() + redirectUrl );
			return;
		}
		if ( !this.isLogin( httpRequest, (AccountObj)accountObj ) ) {
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.uSessLogHelper = new USessLogHelperImpl();
	}

}
