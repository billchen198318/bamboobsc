package com.netsteadfast.greenstep.bsc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.netsteadfast.greenstep.bsc.support.BSCThreadLocalClear;

public class BSCThreadLocalClearServletFilter implements Filter {
	
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
			BSCThreadLocalClear.clear();
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}
