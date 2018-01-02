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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.netsteadfast.greenstep.base.Constants;

public class BackgroundProgramUserUtils {
	private static Factory<org.apache.shiro.mgt.SecurityManager> factory;
	private static org.apache.shiro.mgt.SecurityManager securityManager;	
	private static ThreadLocal<Subject> subjectThreadLocal = new ThreadLocal<Subject>();
	
	static {
		factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		securityManager = factory.getInstance();		
	}
	
	public static boolean isLogin() {
		if (subjectThreadLocal.get() != null) {
			return subjectThreadLocal.get().isAuthenticated();
		}
		return false;
	}
	
	public static Subject getSubject() {
		return subjectThreadLocal.get();
	}
	
	public static void logout() throws Exception {
		getSubject().logout();
		subjectThreadLocal.remove();
	}
	
	public static void login() throws Exception {
		if (factory==null || securityManager==null) {
			throw new Exception("Security manager is null!");
		}
		SecurityUtils.setSecurityManager(securityManager);		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				Constants.SYSTEM_BACKGROUND_USER, Constants.SYSTEM_BACKGROUND_PASSWORD);
		subject.login(token);
		subjectThreadLocal.set(subject);
	}	

}
