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
package com.netsteadfast.greenstep.action.utils;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.validator.routines.EmailValidator;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.model.IActionFieldsCheckUtils;

public class EmailFieldCheckUtils implements IActionFieldsCheckUtils {

	@Override
	public boolean check(String value) throws ControllerException {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		String tmp[] = value.split(Constants.ID_DELIMITER);
		if (tmp==null || tmp.length<1) {
			return true;
		}
		//EmailValidator emailValidator = EmailValidator.getInstance();
		boolean f = true;
		for (int i=0; f && i<tmp.length; i++) {
			//f = emailValidator.isValid(tmp[i]);
			f = ( !tmp[i].endsWith("@") && ( tmp[i].indexOf("@") > 0 ) );
		}		
		return f;
	}
	
}
