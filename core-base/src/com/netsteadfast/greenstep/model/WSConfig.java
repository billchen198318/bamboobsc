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
package com.netsteadfast.greenstep.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.netsteadfast.greenstep.base.Constants;

public class WSConfig {
	public static final String TYPE_SOAP = "SOAP";
	public static final String TYPE_REST = "REST";
	
	public static boolean isType(String type) {
		return (TYPE_SOAP.equals(type) || TYPE_REST.equals(type));
	}
	
	public static Map<String, String> getTypes(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		dataMap.put(TYPE_SOAP, TYPE_SOAP);
		dataMap.put(TYPE_REST, TYPE_REST);
		return dataMap;
	}
	
	public static String getJAXRSServerFactoryBeanAddress() {
		return Constants.getJAXRSServerFactoryBeanAddress();
	}
	
}
