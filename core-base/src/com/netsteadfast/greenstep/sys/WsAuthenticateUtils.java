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
package com.netsteadfast.greenstep.sys;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.sys.IUSessLogHelper;
import com.netsteadfast.greenstep.base.sys.USessLogHelperImpl;
import com.netsteadfast.greenstep.util.EncryptorUtils;

public class WsAuthenticateUtils {
	private static IUSessLogHelper usessLogHelper;
	
	static {
		usessLogHelper = new USessLogHelperImpl();
	}
	
    public static String getAuthKey(String currentId, String account) throws Exception {
    	if ( StringUtils.isBlank(currentId) || StringUtils.isBlank(account) ) {
    		throw new Exception("null key.");
    	}
    	return EncryptorUtils.encrypt(Constants.ENCRYPTOR_KEY1, Constants.ENCRYPTOR_KEY2, 
    			currentId + ";" + account );
    }
    
    public static boolean valid(String authenticate) throws Exception {
    	if ( StringUtils.isBlank(authenticate) ) {
    		throw new Exception("null key.");
    	}
    	String idStr = EncryptorUtils.decrypt(Constants.ENCRYPTOR_KEY1, Constants.ENCRYPTOR_KEY2, 
    			authenticate);
    	if ( StringUtils.isBlank(idStr) ) {
    		throw new Exception("null key.");
    	}
    	String id[] = idStr.split(";");
    	if ( id.length!=2 ) {
    		return false;
    	}
    	if ( StringUtils.isBlank(id[0]) || StringUtils.isBlank(id[1]) ) {
    		return false;
    	}
    	return ( usessLogHelper.countByCurrent(id[1], id[0]) > 0 ? true : false );
    }
    
}
