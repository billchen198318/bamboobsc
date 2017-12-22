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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class WebSystemHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	protected Logger log=Logger.getLogger(WebSystemHttpSessionListener.class);
	private static final Map<String, HttpSession> sessionsMap=new HashMap<String, HttpSession>();
	private static Properties props = new Properties();
	private static String invalidateSameAccountSession="Y";
	private IUSessLogHelper uSessLogHelper;
	
	static {
		try {
			props.load(WebSystemHttpSessionListener.class.getClassLoader().getResource("META-INF/webSystemHttpSessionListener.properties").openStream());
			invalidateSameAccountSession=SimpleUtils.getStr(props.getProperty("INVALIDATE_SAME_ACCOUNT_SESSION"), "Y");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public WebSystemHttpSessionListener() {
		super();
		uSessLogHelper=new USessLogHelperImpl();
	}
	
	// -----------------------------------------------------------------------------------

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// 2017-12-22 rem
		/*
		try {
			sessionsMap.put(event.getSession().getId(), event.getSession());
			if (this.uSessLogHelper.count(event.getSession().getId())>0) {
				this.uSessLogHelper.delete(event.getSession().getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		*/
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			sessionsMap.remove(event.getSession().getId());
			if (this.uSessLogHelper.count(event.getSession().getId())>0) {
				this.uSessLogHelper.delete(event.getSession().getId());
				SecurityUtils.getSubject().logout();
				log.info(Constants.getSystem() + " sessionDestroyed: " + event.getSession().getId() + " and do SecurityUtils.getSubject().logout().... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------------------
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		try {
			// --------------------------------------------------
			// 2017-12-22 add
			// --------------------------------------------------
			String name=event.getName();
			if (Constants.SESS_ACCOUNT.equals(name)) {
				sessionsMap.put(event.getSession().getId(), event.getSession());
			}
			// --------------------------------------------------
			
			this.cleanBeforeLoginUser(event);
			this.writeLoginUser(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		try {
			this.clearLoginUser(event);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		/*
		try {
			this.updateLoginUser(event);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		*/
	}
	
	/**
	 * 清除之前已有相同帳戶登入的 sessionId 紀錄
	 * 
	 * @param arg0
	 * @throws Exception
	 */
	private void cleanBeforeLoginUser(HttpSessionBindingEvent event) throws Exception {
		if (!"Y".equals(invalidateSameAccountSession)) {
			return;
		}
		String name=event.getName();
		Object value=event.getValue();
		if (Constants.SESS_ACCOUNT.equals(name)) {
			String account=((AccountObj)value).getAccount();
			List<String> sessionIdList=uSessLogHelper.findSessionIdByAccount(account);
			for (int ix=0; sessionIdList!=null && ix<sessionIdList.size(); ix++) {
				String sessId=sessionIdList.get(ix);
				HttpSession beforeUserHttpSession=sessionsMap.get(sessId);
				beforeUserHttpSession.invalidate();
				sessionsMap.remove(sessId);
				//uSessLogHelper.delete(sessId); invalidate 時 sessionDestroyed 有做刪除紀錄的功能
				log.warn("invalidate before session:"+sessId + " account:"+account);
			}
		}
	}
	
	private void writeLoginUser(HttpSessionBindingEvent event) throws Exception {
		String sessionId=event.getSession().getId();
		String name=event.getName();
		Object value=event.getValue();
		if (Constants.SESS_ACCOUNT.equals(name)) {
			if (uSessLogHelper.count(sessionId)>0) {
				uSessLogHelper.delete(sessionId);
			}
			uSessLogHelper.insert(sessionId, ((AccountObj)value).getAccount());
		}		
	}
	
	private void clearLoginUser(HttpSessionBindingEvent event) throws Exception {
		String sessionId=event.getSession().getId();
		String name=event.getName();
		if (Constants.SESS_ACCOUNT.equals(name)) {
			if (uSessLogHelper.count(sessionId)>0) {
				uSessLogHelper.delete(sessionId);
			}
		}			
	}
	
	// -----------------------------------------------------------------------------------
	
}
