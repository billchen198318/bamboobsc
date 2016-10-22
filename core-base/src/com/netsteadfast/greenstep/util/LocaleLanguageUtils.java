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
package com.netsteadfast.greenstep.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;

public class LocaleLanguageUtils {
	private static final String _CONFIG = "META-INF/lang.json";
	private static String _datas = " { } ";
	private static Map<String, Object> _configDataMap;
	
	static {
		try {
			InputStream is = LocaleLanguageUtils.class.getClassLoader().getResource( _CONFIG ).openStream();
			_datas = IOUtils.toString(is, Constants.BASE_ENCODING);
			is.close();
			is = null;
			_configDataMap = loadDatas();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null==_configDataMap) {
				_configDataMap = new HashMap<String, Object>();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadDatas() {
		Map<String, Object> datas = null;
		try {
			datas = (Map<String, Object>)new ObjectMapper().readValue( _datas, LinkedHashMap.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap() {
		return (Map<String, Object>) _configDataMap.get("langs");
	}
	
	public static String getDefault() {
		return (String) _configDataMap.get("default");
	}
	
	public static void main(String args[]) throws Exception {
		Map<String, Object> langs = getMap();
		System.out.println(langs);
		System.out.println(getDefault());
	}
	
}
