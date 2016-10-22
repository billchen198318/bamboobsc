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
package com.netsteadfast.greenstep.base.service.logic;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.util.SimpleUtils;

import ognl.Ognl;
import ognl.OgnlException;

public abstract class BaseLogicService {
	
	public String getAccountId() {
		Subject subject = SecurityUtils.getSubject();		
		return this.defaultString((String)subject.getPrincipal());				
	}
	
	public String generateOid() {
		return SimpleUtils.getUUIDStr();
	}	
	
	public String defaultString(String source) {
		return SimpleUtils.getStr(source, "");
	}	
	
	public boolean isBlank(String source) {
		return StringUtils.isBlank(source);
	}

	protected boolean isNoSelectId(String value) {
		if (StringUtils.isBlank(value) || Constants.HTML_SELECT_NO_SELECT_ID.equals(value)) {
			return true;
		}
		return false;
	}
	
	public <T> T setStringValueMaxLength(T obj, String fieldName, int maxLength) {
		if (obj == null) {
			return obj;
		}
		try {
			Object value = Ognl.getValue(fieldName, obj);
			if ( !(value instanceof String) ) {
				return obj;
			}
			if ( this.isBlank((String)value) || ((String)value).length() <= maxLength ) {
				return obj;
			}
			value = ((String)value).substring(0, maxLength);
			Ognl.setValue(fieldName, obj, value);
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public <T> T replaceSplit2Blank(T obj, String fieldName, String split) {
		if (obj == null) {
			return obj;
		}
		try {
			Object value = Ognl.getValue(fieldName, obj);
			if ( !(value instanceof String) ) {
				return obj;
			}
			if ( this.isBlank((String)value) ) {
				return obj;
			}
			value = ((String)value).replaceAll(split, "");
			Ognl.setValue(fieldName, obj, value);
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return obj;
	}	
	
}
