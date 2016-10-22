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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 
 * 
 * 
 * public String getNo() {
 * 		return no;
 * }	
 * @M2BMapper(key="txt_no")
 * public void setNo(String no) {
 * 		this.no = no;
 * }
 * public String getName() {
 * 		return name;
 * }
 * @M2BMapper(key="txt_name")
 * public void setName(String name) {
 * 		this.name = name;
 * }
 * public String getAssress() {
 * 		return assress;
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * Map<String, Object> textMap=new HashMap<String, Object>();
 * textMap.put("txt_no", "03125012");
 * textMap.put("txt_name", "for-test!");
 * textMap.put("my_def_date1", new Date());
 * textMap.put("my_def_num", 500);
 * 
 * TXT_A ta=new TXT_A();		
 * M2BMapperUtil.fill(textMap, ta);		
 * 
 *
 */
public class M2BMapperUtil {
	
	public static void fill(
			Map<String, Object> sourceMap, Object targetBean) throws Exception {
		
		if (sourceMap==null || targetBean==null 
				|| sourceMap.size()<1 ) {
			throw new java.lang.IllegalArgumentException("sourceMap or targetBean error.");
		}
		Method[] methods=targetBean.getClass().getMethods();	
		if (methods.length<1) {
			return;
		}
		for (Method method : methods) {
			if (method.getName().indexOf("set")!=0) {
				continue;
			}			
			Annotation[] annotations=method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof M2BMapper) {
					String key=((M2BMapper) annotation).name();
					if (key==null || "".equals(key)) {
						continue;
					}					
					try {
						method.invoke(targetBean, sourceMap.get(((M2BMapper) annotation).name()));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}					
				}
			}
		}				
		
	}
	
}
