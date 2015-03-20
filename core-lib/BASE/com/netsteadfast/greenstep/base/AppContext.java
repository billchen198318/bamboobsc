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
package com.netsteadfast.greenstep.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class AppContext {
	private static ApplicationContext applicationContext;
	private static AppContext singleton=new AppContext();
	
	static {		
		applicationContext=ContextLoader.getCurrentWebApplicationContext();
		if (applicationContext==null ) {
			applicationContext=new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext.xml"});
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		AppContext.applicationContext = applicationContext;
	}
	
	public static Object getBean(final String beanId) {
		return applicationContext.getBean(beanId);
	}
	
	public static AppContext getInstance() {
		return singleton;
	}	
}
