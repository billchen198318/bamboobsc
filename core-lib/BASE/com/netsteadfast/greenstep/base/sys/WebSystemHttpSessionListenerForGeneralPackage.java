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

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;

public class WebSystemHttpSessionListenerForGeneralPackage implements HttpSessionListener, HttpSessionAttributeListener {
	protected Logger log=Logger.getLogger(WebSystemHttpSessionListenerForGeneralPackage.class);
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.info(Constants.getSystem() + " sessionCreated: " + event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		if (event.getSession().getAttribute(Constants.SESS_ACCOUNT)!=null
				&& event.getSession().getAttribute(Constants.SESS_ACCOUNT) instanceof AccountObj) {
			if (!Constants.getSystem().equals(Constants.getMainSystem())) {
				// SecurityUtils.setSecurityManager( (DefaultSecurityManager)AppContext.getBean("securityManager") );
				SecurityUtils.getSubject().logout();
				log.info(Constants.getSystem() + " sessionDestroyed: " + event.getSession().getId() + " and do SecurityUtils.getSubject().logout().... ");					
			}		
		}
	}

}
