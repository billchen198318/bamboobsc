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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.util.SimpleUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("greenstep.web.interceptor.UserAgentRejectInterceptor")
@Scope("prototype")
public class UserAgentRejectInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -6402499430173243263L;
	protected Logger logger=Logger.getLogger(UserAgentRejectInterceptor.class);
	private static Properties props = new Properties();
	private static String rejectAgent[] = null;
	
	static {
		try {
			props.load(UserAgentRejectInterceptor.class.getClassLoader().getResource("META-INF/userAgentReject.properties").openStream());
			rejectAgent = SimpleUtils.getStr(props.getProperty("REJECT")).trim().split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {		
		HttpServletRequest request=ServletActionContext.getRequest(); 
		String userAgent = SimpleUtils.getStr(request.getHeader("User-Agent")).toUpperCase();
		if (rejectAgent==null) {
			return actionInvocation.invoke();
		}
		for (int i=0; rejectAgent!=null && i<rejectAgent.length; i++) {
			if (rejectAgent[i].trim().equals("")) {
				continue;
			}
			if (userAgent.indexOf(rejectAgent[i])>-1) {
				Map<String, Object> valueStackMap = new HashMap<String, Object>();
				valueStackMap.put("errorMessage", "not supported " + userAgent);
				valueStackMap.put("pageMessage", "not supported " + userAgent);
				ActionContext.getContext().getValueStack().push(valueStackMap);
				logger.warn("reject User-Agent="+userAgent);
				return "rejectUserAgent";
			}
		}
		return actionInvocation.invoke();
	}

}
