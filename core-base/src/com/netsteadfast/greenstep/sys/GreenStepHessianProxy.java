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

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;

public class GreenStepHessianProxy extends HessianProxy {
	private static final long serialVersionUID = -6136220982415418297L;
	private Map<String, String> context = null;

	public GreenStepHessianProxy(URL url, HessianProxyFactory factory, Class<?> type) {
		super(url, factory, type);
	}
	
	public GreenStepHessianProxy(URL url, HessianProxyFactory factory) {
		super(url, factory);
	}
	
	public void putHeader(Map<String, String> context) {
		this.context = context;
	}
	
	@Override
	protected void addRequestHeaders(HessianConnection conn) {
		super.addRequestHeaders(conn);
		if (this.context == null || this.context.size() < 1) {
			return;
		}
		for (Entry<String, String> e : this.context.entrySet()) {
			conn.addHeader(e.getKey(), e.getValue());
		}
	}
	
}
