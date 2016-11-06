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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.model.WebMessagePublishBaseObj;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.publish.BaseMessagePublishService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;

public class WebMessagePublishListener implements ServletContextListener {
	private final static long SLEEP_TIME = 1000 * 5;
	private ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService;
	private List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>();
	private Thread webMessagePublishThread = null;
	private WebMessagePublishRunnable webMessagePublishRunnable = null;
	
	public WebMessagePublishListener() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if ( null == sysMsgNoticeConfigService ) {
			this.sysMsgNoticeConfigService = (ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String>)
					AppContext.getBean("core.service.SysMsgNoticeConfigService");						
		}		
		event.getServletContext().setAttribute("asyncContexts", asyncContexts);
		this.webMessagePublishRunnable = new WebMessagePublishRunnable(this.asyncContexts);
		this.webMessagePublishThread = new Thread(this.webMessagePublishRunnable);
		this.webMessagePublishThread.start();		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		this.webMessagePublishRunnable.stopRunning();
		this.webMessagePublishThread.interrupt();
		try {
			this.webMessagePublishThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.webMessagePublishThread = null;
		this.webMessagePublishRunnable = null;
	}	
	
	// --------------------------------------------------------------------------------------------------	
	private class WebMessagePublishRunnable implements Runnable {
		private volatile boolean running = true;
		private Map<String, BaseMessagePublishService> publicServices = new HashMap<String, BaseMessagePublishService>();
		private Map<String, SysMsgNoticeConfigVO> msgNoticeConfigMap = new HashMap<String, SysMsgNoticeConfigVO>();
		private List<AsyncContext> asyncContexts = null;
		
		public WebMessagePublishRunnable(List<AsyncContext> asyncContexts) {
			this.asyncContexts = asyncContexts;
		}
		
		@Override
		public void run() {
			
			while (this.running) {
				
				synchronized (asyncContexts) {
					for (AsyncContext context : asyncContexts) {
						try {
							WebMessagePublishBaseObj publishObj = null;
							context.getResponse().setContentType("application/json; charset=" + Constants.BASE_ENCODING );
							String id = StringUtils.defaultString(context.getRequest().getParameter("id")).trim();
							String key = id + ":" + Constants.getSystem();
							if (!StringUtils.isBlank(id)) {
								SysMsgNoticeConfigVO config = msgNoticeConfigMap.get(key);
								if (config==null) {
									config = new SysMsgNoticeConfigVO();
									config.setMsgId(id);
									//config.setSystem(Constants.getSystem());
									try {					
										DefaultResult<SysMsgNoticeConfigVO> result = sysMsgNoticeConfigService.findByUK(config);
										if (result.getValue()!=null) {
											config = result.getValue();
											msgNoticeConfigMap.put(key, config);
										}
									} catch (ServiceException e) {
										e.printStackTrace();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								if (config!=null && !StringUtils.isBlank(config.getOid()) ) {
									BaseMessagePublishService messagePublishService = publicServices.get(key);
									if (messagePublishService==null) {
										try {
											messagePublishService = (BaseMessagePublishService)Class.forName(config.getClassName()).newInstance();
											publicServices.put(key, messagePublishService);								
										} catch (InstantiationException e) {
											e.printStackTrace();
										} catch (IllegalAccessException e) {
											e.printStackTrace();
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
									if (messagePublishService!=null) { // 正確建立完成 BaseMessagePublishService  
										try {
											publishObj = messagePublishService.execute(config, context.getRequest());
										} catch (ServiceException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}											
									}
								}								
							}
							if (publishObj==null) {
								publishObj = new WebMessagePublishBaseObj();
							}
							context.getResponse().getWriter().println( JSONObject.fromObject(publishObj).toString() ); 
							context.complete();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					asyncContexts.clear();
				}
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}		
				
			}
			
		}
		
	    public void stopRunning() {
	        this.running = false;
	    }
		
	}
	// --------------------------------------------------------------------------------------------------
	
}
