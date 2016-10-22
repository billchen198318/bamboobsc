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
package com.netsteadfast.greenstep.sys;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.springframework.remoting.caucho.HessianServiceExporter;

public class GreenStepHessianServiceExporter extends HessianServiceExporter {
	protected Logger logger = Logger.getLogger(GreenStepHessianServiceExporter.class);
	
	public GreenStepHessianServiceExporter() {
		super();
	}
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * 不用檢查checkValue模式
		 */
		if (!GreenStepHessianUtils.getConfigHessianHeaderCheckValueModeEnable()) {
			super.handleRequest(request, response);
			return;
		}
		
		/**
		 * 一般要檢查checkValue模式
		 */
		String checkValue = GreenStepHessianUtils.getHttpRequestHeaderCheckValue(request);
		Map<String, String> dataMap = null;
		try {			
			dataMap = GreenStepHessianUtils.getDecAuthValue(checkValue);
			if (null == dataMap || !GreenStepHessianUtils.isCheckValue(dataMap)) {
				logger.warn( "fail check value hessian webService" );
				return;
			}
			String userId = GreenStepHessianUtils.getUserId(dataMap);
			if (StringUtils.isBlank(userId)) {
				logger.warn( "no userId cannot access hessian webService" );
				return;
			}
			if (GreenStepHessianUtils.isProxyBlockedAccountId(userId)) {
				logger.warn( "blocked userId: " + userId + " cannot access hessian webService" );
				return;
			}
		} catch (Exception e) {
			logger.error( e.getMessage().toString() );
			e.printStackTrace();
			return;
		}
		Subject subject = null;
		try {
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			subject = loginSupport.forceCreateLoginSubject(request, response, GreenStepHessianUtils.getUserId(dataMap), "0123");
			super.handleRequest(request, response);				
		} catch (Exception e) {
			logger.error( e.getMessage().toString() );
			e.printStackTrace();
		} finally {
			if (null != subject) {
				subject.logout();
			}
		}
	}
	
}
