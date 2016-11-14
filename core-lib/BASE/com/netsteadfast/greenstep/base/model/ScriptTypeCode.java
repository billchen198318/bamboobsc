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
package com.netsteadfast.greenstep.base.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.netsteadfast.greenstep.base.Constants;

public class ScriptTypeCode {
	public static final String IS_BSH = "BSH"; // beanShell2
	public static final String IS_GROOVY = "GROOVY"; // groovy
	public static final String IS_PYTHON = "PYTHON"; // jpython
	public static final String IS_R = "R"; // R , Renjin
	
	public static boolean isTypeCode(String type) {
		if (IS_BSH.equals(type) || IS_GROOVY.equals(type) || IS_PYTHON.equals(type) || IS_R.equals(type)) {
			return true;
		}
		return false;
	}
	
	public static Map<String, String> getTypeMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		dataMap.put(IS_BSH, "java ( BeanShell2 )");
		dataMap.put(IS_GROOVY, "groovy");
		dataMap.put(IS_PYTHON, "python");
		dataMap.put(IS_R, "R (Renjin)");
		return dataMap;
	}
	
}
