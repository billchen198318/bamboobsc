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

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;

public class GreenStepHessianProxyFactory extends HessianProxyFactory {
	
	private Map<String, String> context = new HashMap<String, String>();

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}
	
	public void addHeader(String key, String value) {
		this.context.put(key, value);
	}	
	
	public void setHeaderCheckValue(String value) {
		this.context.put(GreenStepHessianUtils.HEADER_CHECK_VALUE_PARAM_NAME, value);
	}
	
	public Object createForHeaderMode(Class<?> api, String url) throws MalformedURLException {
		if (api == null) {
			throw new NullPointerException("api must not be null for HessianProxyFactory.create()");
		}
		GreenStepHessianProxy handler = new GreenStepHessianProxy(new URL(url), this, api);
		handler.putHeader(this.context);
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { api, HessianRemoteObject.class }, handler);
	}
	
}
