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

import java.sql.Connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.util.DataUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 主要提供要關閉呼叫 jasperreport 使用的 jdbc connection 的 Interceptor
 *
 */
@Component("greenstep.web.interceptor.JasperReportCloseConnHelperInterceptor")
@Scope("prototype")
public class JasperReportCloseConnHelperInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 4459470662020568944L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String result=actionInvocation.invoke();
		/**
		 * findValue 依照 jasper action xml 設定名稱決定
		 * 
		 * 如:
		 * <param name="connection">conn123</param>
		 * 
		 * 所以 findValue("conn123");
		 * 
		 */
		Connection connection=(Connection)actionInvocation.getStack().findValue("connection");
		if (connection!=null) {
			DataUtils.doReleaseConnection(connection);
			System.out.println(JasperReportCloseConnHelperInterceptor.class.getName()+" doReleaseConnection ...");
		} else {
			System.out.println(JasperReportCloseConnHelperInterceptor.class.getName()+" null connection ...");
		}
		return result;
	}

}
