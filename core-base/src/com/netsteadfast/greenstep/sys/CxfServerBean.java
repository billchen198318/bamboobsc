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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.BinaryDataProvider;
import org.apache.cxf.jaxrs.provider.DataBindingProvider;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.MultipartProvider;
import org.apache.cxf.jaxrs.provider.XPathProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.model.WSConfig;
import com.netsteadfast.greenstep.po.hbm.TbSysWsConfig;
import com.netsteadfast.greenstep.service.ISysWsConfigService;
import com.netsteadfast.greenstep.vo.SysWsConfigVO;

public class CxfServerBean {
	protected static Logger logger = Logger.getLogger(CxfServerBean.class);
	
	private static JAXRSServerFactoryBean serverFactoryBean = null;
	
	private static Bus bus = null;
	
	private static JAXRSBindingFactory bindingFactory = null;
	
	private static Server server = null;
	
	private static PublishingCXFServlet servlet = null;
	
	private static ServletConfig servletConfig = null;
	
	public static void shutdown() {
		logger.warn("shutdown");
		server.stop();
		server.destroy();
		serverFactoryBean.getBus().shutdown(true);		
	}
	
	public static void reStart() {
		start(servlet, servletConfig, bus, true);
	}
	
	public static void start(PublishingCXFServlet servlet, ServletConfig servletConfig, Bus bus, boolean loadBusManual) {
		logger.info("start");
		CxfServerBean.servlet = servlet;
		CxfServerBean.servletConfig = servletConfig;
		CxfServerBean.bus = bus;
		try {
			if (loadBusManual) {
				logger.info("load bus manual mode");
				servlet.loadBusManual(servletConfig);
			}
			BusFactory.setDefaultBus( bus );
			serverFactoryBean = new JAXRSServerFactoryBean();
			List<TbSysWsConfig> configs = getSystemWsConfigs();
			publishDefault(bus, configs );
			int r = publishRest(serverFactoryBean, configs);			
	        BindingFactoryManager manager = serverFactoryBean.getBus().getExtension(BindingFactoryManager.class);  
	        bindingFactory = new JAXRSBindingFactory();  
	        bindingFactory.setBus(serverFactoryBean.getBus());  
	        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, bindingFactory);
	        if ( r > 0 ) {
	        	server = serverFactoryBean.create();
	        }			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		logger.info("end");
	}	
	
	public static List<Object> getProvider() {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JSONProvider<Object>());
		providers.add(new JAXBElementProvider<Object>());
		providers.add(new MultipartProvider());
		providers.add(new XPathProvider<Object>());
		providers.add(new DataBindingProvider<Object>());
		providers.add(new BinaryDataProvider<Object>());
		providers.add(new JacksonJaxbJsonProvider());
		return providers;
	}
	
	@SuppressWarnings("unchecked")
	public static List<TbSysWsConfig> getSystemWsConfigs() throws ServiceException, Exception {
		List<TbSysWsConfig> configs = null;
		ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService = 
				(ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String>)AppContext.getBean("core.service.SysWsConfigService");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("system", Constants.getSystem());
		configs = sysWsConfigService.findListByParams(params);
		if (configs==null) {
			configs = new ArrayList<TbSysWsConfig>();
		}
		return configs;		
	}	
	
	private static int publishDefault(Bus bus, List<TbSysWsConfig> configs) {
		int c = 0;
		for (TbSysWsConfig config : configs) {
			if (!WSConfig.TYPE_SOAP.equals(config.getType()) || StringUtils.isBlank(config.getPublishAddress()) ) {
				continue;
			}
			try {
				Endpoint.publish(config.getPublishAddress(), AppContext.getBean(config.getBeanId()));
				c++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return c;
	}
	
	private static int publishRest(JAXRSServerFactoryBean sf, List<TbSysWsConfig> configs) {
		int c = 0;
		for (TbSysWsConfig config : configs) {
			if (!WSConfig.TYPE_REST.equals(config.getType())) {
				continue;
			}
			try {
				sf.setServiceBean(AppContext.getBean(config.getBeanId()));
				c++;				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		sf.setProviders(getProvider());
		sf.setAddress(WSConfig.getJAXRSServerFactoryBeanAddress());
		return c;
	}		
	
}
