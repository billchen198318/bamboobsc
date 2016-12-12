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

public class TemplateCode {
	private static final String _USE_CODE[] = new String[] { "TPLMSG0001", "TPLMSG0002", "TPLMSG0003" };
	
	public static boolean isUsed(String code) {
		boolean f = false;
		for (int i=0; !f && i<_USE_CODE.length; i++) {
			if (_USE_CODE[i].equals(code)) {
				f = true;
			}
		}
		return f;
	}
	
	/**
	 * 提供 calendar note 用的
	 */
	public static final String TPLMSG0001 = "TPLMSG0001";
	
	/**
	 * 提供 message notice 用的
	 */
	public static final String TPLMSG0002 = "TPLMSG0002";
	
	/**
	 * 提供 HistoryItemScoreNoticeHandler 發送 mail 內容要使用的樣板
	 */
	public static final String TPLMSG0003 = "TPLMSG0003";
	
}
