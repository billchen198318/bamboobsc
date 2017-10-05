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
package com.netsteadfast.greenstep.bsc.action.utils;

import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.model.IActionFieldsCheckUtils;

public class BscNumberFieldCheckUtils implements IActionFieldsCheckUtils {

	@Override
	public boolean check(String value) throws ControllerException {		
		boolean p1 = NumberUtils.isCreatable(value);
		boolean p2 = true;
		if (p1 && value.indexOf(".") > -1 ) {
			String bit[] = value.trim().split("[.]");
			if ( !(bit.length == 2 && bit[1].length() <= 2) ) {
				p2 = false;
			}
		}
		return p1 && p2;
	}
	
}
