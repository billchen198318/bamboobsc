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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtils {
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) { 
        return getSuperClassGenricType(clazz, 0);  
    }    
    
    @SuppressWarnings({ "rawtypes" })
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException { 
        Type genType = clazz.getGenericSuperclass(); 
        if (!(genType instanceof ParameterizedType)) {
            return Object.class; 
        }  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); 
        if (index >= params.length || index < 0) { 
            return Object.class; 
        } 
        if (!(params[index] instanceof Class)) { 
            return Object.class; 
        } 
        return (Class) params[index]; 
    }
    
}
