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
package com.netsteadfast.greenstep.base.sys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.util.EncryptorUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class UserCurrentCookie {
	
	public static boolean foundCurrent(HttpServletRequest request) {
		boolean f = false;
		try {
			Cookie[] cookies = request.getCookies();
			for (int i=0; !f && cookies!=null && i<cookies.length; i++) {
				Cookie cookie=cookies[i];
				if (cookie.getName().equals(Constants.APP_SITE_CURRENTID_COOKIE_NAME)) {
					f = true;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	public static void setCurrentId(HttpServletResponse response, String currentId, String sessionId, 
			String account, String language) {
		try {
			String value = currentId + Constants.ID_DELIMITER + sessionId 
					+ Constants.ID_DELIMITER + account
					+ Constants.ID_DELIMITER + language;
			String encValue = EncryptorUtils.encrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), value);
			encValue = SimpleUtils.toHex(encValue);
			Cookie cookie = new Cookie(Constants.APP_SITE_CURRENTID_COOKIE_NAME, encValue);		
			cookie.setPath("/");
			cookie.setValue(encValue);
			cookie.setMaxAge( 60*60*24 ); // 1-day
			cookie.setHttpOnly(true);
			response.addCookie(cookie);				
		} catch (Exception e) {
			e.printStackTrace();
		}					
	}
	
	public static Map<String, String> getCurrentData(HttpServletRequest request) {	
		Map<String, String> dataMap = new HashMap<String, String>();	
		try {
			Cookie[] cookies = request.getCookies();
			for (int i=0; cookies!=null && dataMap.size()==0 && i<cookies.length; i++) {
				Cookie cookie=cookies[i];
				if (cookie.getName().equals(Constants.APP_SITE_CURRENTID_COOKIE_NAME)) {
					if (StringUtils.isBlank( cookie.getValue())) {
						return dataMap;
					}
					String decVal = SimpleUtils.deHex( cookie.getValue() );
					decVal = EncryptorUtils.decrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), decVal);
					String tmp[] = decVal.split(Constants.ID_DELIMITER);
					if (tmp!=null && tmp.length==4) {
						dataMap.put("currentId", tmp[0]);
						dataMap.put("sessionId", tmp[1]);
						dataMap.put("account", tmp[2]);
						dataMap.put("lang", tmp[3]);
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	
}
