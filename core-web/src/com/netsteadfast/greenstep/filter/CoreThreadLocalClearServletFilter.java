package com.netsteadfast.greenstep.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.netsteadfast.greenstep.support.CoreThreadLocalClear;

public class CoreThreadLocalClearServletFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestUrl = httpServletRequest.getRequestURL().toString();		
		try {
			chain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		if (requestUrl.contains(".action") || requestUrl.contains("/services/") || requestUrl.contains("/camel/")) {
			CoreThreadLocalClear.clear();
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}
