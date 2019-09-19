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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;

public class CharsetFilter implements Filter {
	private FilterConfig filterConfig;
	private String contentType = "text/html; charset=" + Constants.BASE_ENCODING;
	private String encoding = Constants.BASE_ENCODING;
	
	@Override
	public void destroy() {
		
	}
	
	/**
	 * BUG FIX, run on newer than Tomcat 8.5.35 or Tomcat-9 version, will error :
	 * Resource interpreted as Stylesheet but transferred with MIME type text/html
	 */
	private void resetCssAndJavascriptContentType(ServletRequest request, ServletResponse response, String requestUrl) {
		if (requestUrl.contains(".css")) {
			response.setContentType("text/css; charset=" + Constants.BASE_ENCODING);
		}
		if (requestUrl.contains(".js")) {
			response.setContentType("application/javascript; charset=" + Constants.BASE_ENCODING);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestUrl = httpServletRequest.getRequestURL().toString();
		if (requestUrl.contains(".html") || requestUrl.contains(".htm")) {
			response.setContentType(this.contentType);
			response.setCharacterEncoding(this.encoding);			
		}
		this.resetCssAndJavascriptContentType(request, response, requestUrl);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (!StringUtils.isBlank(this.filterConfig.getInitParameter("encoding")) ) {
			this.encoding = this.filterConfig.getInitParameter("encoding");
		}
		if (!StringUtils.isBlank(this.filterConfig.getInitParameter("contentType")) ) {
			this.contentType = this.filterConfig.getInitParameter("contentType");
		}
	}
	
}
