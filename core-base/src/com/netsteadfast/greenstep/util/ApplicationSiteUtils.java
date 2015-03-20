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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.vo.SysVO;

public class ApplicationSiteUtils {
	private static Map<String, String> contextPathMap = new HashMap<String, String>();
	
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
		if (FSUtils.readStr(logConfFileFullPath).trim().equals("1")) {
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
			String port = "";
			String tmp[] = sys.getHost().split(":");
			if ( tmp!=null && tmp.length==2 ) {
				port = tmp[1];
			}
			String hostAddress = HostUtils.getHostAddress();
			sys.setHost(hostAddress);
			if (!StringUtils.isBlank(port)) {				
				sys.setHost( hostAddress + ":" + port );
			}
			sysService.updateObject(sys);
			FSUtils.writeStr2(logConfFileFullPath, "1");			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
