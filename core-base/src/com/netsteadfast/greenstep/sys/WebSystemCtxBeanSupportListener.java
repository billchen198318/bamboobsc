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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ContextInitializedAndDestroyedBean;
import com.netsteadfast.greenstep.model.CtxBeanTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysCtxBean;
import com.netsteadfast.greenstep.service.ISysCtxBeanService;
import com.netsteadfast.greenstep.vo.SysCtxBeanVO;

public class WebSystemCtxBeanSupportListener implements ServletContextListener {
	protected Logger logger=Logger.getLogger(WebSystemCtxBeanSupportListener.class);
	private ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService;
	
	@SuppressWarnings("unchecked")
	private List<TbSysCtxBean> findSysCtxBeanList(String type) throws ServiceException, Exception {
		sysCtxBeanService = (ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String>)AppContext.getBean("core.service.SysCtxBeanService");
		List<TbSysCtxBean> ctxBeans = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("system", Constants.getSystem());
		ctxBeans = sysCtxBeanService.findListByParams(params);
		if (ctxBeans==null) {
			ctxBeans = new ArrayList<TbSysCtxBean>();
		}
		return ctxBeans;
	}
	
	private void processBean(ServletContextEvent event, String type) {
		try {
			List<TbSysCtxBean> ctxBeans = this.findSysCtxBeanList(type);
			for (int i=0; ctxBeans!=null && i<ctxBeans.size(); i++) {
				TbSysCtxBean bean = ctxBeans.get(i);
				try {
					Object executerObj = Class.forName(bean.getClassName()).newInstance();
					if (!(executerObj instanceof ContextInitializedAndDestroyedBean)) {
						logger.warn("class not instanceof ContextInitializedAndDestroyedBean : " + bean.getClassName());
						continue;
					}
					Method[] methods = executerObj.getClass().getMethods();
					for (Method method : methods) {
						if (method.getName().equals("execute")) {
							try {
								method.invoke(executerObj, event);
							} catch (ServiceException se) {
								se.printStackTrace();
								logger.warn(se.getMessage().toString());
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage().toString());
							}							
						}
					}
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
					logger.error(cnfe.getMessage().toString());
				}				
			}			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		this.processBean(event, CtxBeanTypes.DESTROY);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.processBean(event, CtxBeanTypes.INIT);		
	}
	
}
