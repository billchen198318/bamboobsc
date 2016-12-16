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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.vo.SysVO;

public class ApplicationSiteUtils {
	protected static Logger logger = Logger.getLogger(ApplicationSiteUtils.class);
	public static final String UPDATE_HOST_ALWAYS = "2";
	public static final String UPDATE_HOST_ONLY_FIRST_ONE = "1";
	private static final long TEST_JSON_HTTP_TIMEOUT = 3000; // 3秒
	private static Map<String, String> contextPathMap = new HashMap<String, String>();	
	
	@SuppressWarnings("unchecked")
	public static List<SysVO> getSystems() throws ServiceException, Exception {
		ISysService<SysVO, TbSys, String> sysService = 
				(ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");		
		return sysService.findListVOByParams( null );
	}
	
	@SuppressWarnings("unchecked")
	public static String getHost(String sysId) {
		String host = "";
		ISysService<SysVO, TbSys, String> sysService = 
				(ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");
		SysVO sys = new SysVO();
		sys.setSysId(sysId);
		try {
			DefaultResult<SysVO> result = sysService.findByUK(sys);
			if (result.getValue()==null) {
				return "";
			}
			sys = result.getValue();
			host = sys.getHost();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return host;
	}
	
	@SuppressWarnings("unchecked")
	public static String getBasePath(String sysId, HttpServletRequest request) {
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		if (StringUtils.isBlank(sysId)) {
			return basePath;
		}
		ISysService<SysVO, TbSys, String> sysService = 
				(ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");
		SysVO sys = new SysVO();
		sys.setSysId(sysId);
		try {
			DefaultResult<SysVO> result = sysService.findByUK(sys);
			if (result.getValue()==null) {
				return basePath;
			}
			sys = result.getValue();
			basePath = request.getScheme() + "://" + sys.getHost() + "/" + sys.getContextPath() + "/";
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return basePath;
	}
	
	@SuppressWarnings("unchecked")
	public static String getContextPath(String sysId) {
		String contextPath = "";
		ISysService<SysVO, TbSys, String> sysService = (ISysService<SysVO, TbSys, String>)
				AppContext.getBean("core.service.SysService");
		SysVO sys = new SysVO();
		sys.setSysId(sysId);
		try {
			DefaultResult<SysVO> result = sysService.findByUK(sys);
			if (result.getValue()==null) {
				return contextPath;
			}
			sys = result.getValue();
			contextPath = sys.getContextPath();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contextPath;		
	}
	
	public static String getContextPathFromMap(String sysId) {
		if (contextPathMap.get(sysId)!=null) {
			return contextPathMap.get(sysId);
		}
		String contextPath = getContextPath(sysId);
		if (!StringUtils.isBlank(contextPath)) {
			contextPathMap.put(sysId, contextPath);
		}
		return contextPath;
	}
	
	@SuppressWarnings("unchecked")
	public static void configureHost(String sysId, String logConfFileFullPath) {
		String logValue = FSUtils.readStr(logConfFileFullPath).trim();
		if (!StringUtils.isBlank(logValue) && UPDATE_HOST_ONLY_FIRST_ONE.equals(Constants.getApplicationSiteHostUpdateMode())) {
			// has before start log file, and UPDATE_HOST_ONLY_FIRST_ONE mode
			FSUtils.writeStr2(logConfFileFullPath, UPDATE_HOST_ONLY_FIRST_ONE);
			return;
		}
		ISysService<SysVO, TbSys, String> sysService = 
				(ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");		
		SysVO sys = new SysVO();
		sys.setSysId(sysId);
		try {
			DefaultResult<SysVO> result = sysService.findByUK(sys);
			if (result.getValue()==null) {
				System.out.println(result.getSystemMessage().getValue());				
				return;
			}
			sys = result.getValue();
			// 2016-06-29 rem
			/*
			String port = "";
			String tmp[] = sys.getHost().split(":");
			if ( tmp!=null && tmp.length==2 ) {
				port = tmp[1];
			}
			*/
			String port = String.valueOf( HostUtils.getHttpPort() ); // 2016-06-29 add
			String hostAddress = HostUtils.getHostAddress();
			sys.setHost(hostAddress);
			if (!StringUtils.isBlank(port)) {				
				sys.setHost( hostAddress + ":" + port );
			}
			sysService.updateObject(sys);
			
			
			if (UPDATE_HOST_ALWAYS.equals(Constants.getApplicationSiteHostUpdateMode())) {
				FSUtils.writeStr2(logConfFileFullPath, UPDATE_HOST_ALWAYS);
			} else {
				FSUtils.writeStr2(logConfFileFullPath, UPDATE_HOST_ONLY_FIRST_ONE);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean checkCrossSite(String host, HttpServletRequest request) {
		boolean corssSite = false;
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String basePath80 = request.getScheme()+"://"+request.getServerName();
		basePath = basePath.toLowerCase();
		basePath80 = basePath80.toLowerCase();	
		if (request.getServerPort() == 80 || request.getServerPort() == 443) {
			if (basePath.indexOf( host ) == -1 && basePath80.indexOf( host ) == -1) {
				corssSite = true;
			}
		} else {
			if (basePath.indexOf( host ) == -1) {
				corssSite = true;
			}
		}		
		return corssSite;
	}
	
	private static boolean checkTestConnection(String host, String contextPath, HttpServletRequest request) {
		boolean test = false;
		String basePath = request.getScheme()+"://" + host + "/" + contextPath;
		String urlStr = basePath + "/pages/system/testJsonResult.action";
		try {
			logger.info("checkTestConnection , url=" + urlStr);
			HttpClient client = new HttpClient();
			HttpMethod method = new GetMethod(urlStr);
			HttpClientParams params = new HttpClientParams();
			params.setConnectionManagerTimeout(TEST_JSON_HTTP_TIMEOUT);
			client.setParams(params);
			client.executeMethod(method);
			byte[] responseBody = method.getResponseBody();
			if (null == responseBody) {
				test = false;
				return test;
			}			
			String content = new String(responseBody, Constants.BASE_ENCODING);
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> dataMap = (Map<String, Object>) mapper.readValue(content, HashMap.class);
			if (YesNo.YES.equals(dataMap.get("success"))) {
				test = true;
			}
		} catch (JsonParseException e) {
			logger.error( e.getMessage().toString() );
		} catch (JsonMappingException e) {
			logger.error( e.getMessage().toString() );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!test) {
				logger.warn("checkTestConnection : " + String.valueOf(test));
			} else {
				logger.info("checkTestConnection : " + String.valueOf(test));
			}
		}
		return test;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean checkLoginUrlWithAllSysHostConfig(HttpServletRequest request) {
		boolean pathSuccess = true;
		ISysService<SysVO, TbSys, String> sysService = (ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");				
		try {
			List<TbSys> sysList = sysService.findListByParams( null );
			for (int i=0; sysList != null && i < sysList.size() && pathSuccess; i++) {
				TbSys sys = sysList.get(i);
				pathSuccess = !checkCrossSite(sys.getHost().toLowerCase(), request);
				if (pathSuccess) {
					pathSuccess = checkTestConnection(sys.getHost(), sys.getContextPath(), request);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathSuccess;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SysVO> getSystemsCheckCrossSiteWithTestConnection(HttpServletRequest request) {
		ISysService<SysVO, TbSys, String> sysService = (ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");
		List<SysVO> sysList = null;	
		try {
			sysList = sysService.findListVOByParams( null );
			for (SysVO sys : sysList) {
				if ( checkCrossSite(sys.getHost().toLowerCase(), request) ) {
					sys.setCrossSiteFlag( YesNo.YES );
				} else {
					sys.setCrossSiteFlag( YesNo.NO );
				}
				if ( checkTestConnection(sys.getHost(), sys.getContextPath(), request) ) {
					sys.setTestFlag( YesNo.YES );
				} else {
					sys.setTestFlag( YesNo.NO );
				}
			}			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysList;
	}
	
}
