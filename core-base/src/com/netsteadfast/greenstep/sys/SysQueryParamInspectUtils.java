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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.po.hbm.TbSysQfieldLog;
import com.netsteadfast.greenstep.service.ISysQfieldLogService;
import com.netsteadfast.greenstep.vo.SysQfieldLogVO;

@SuppressWarnings("unchecked")
public class SysQueryParamInspectUtils {
	protected static Logger logger = Logger.getLogger(SysQueryParamInspectUtils.class);
	private static final String CONFIG_BEAN_ID = "app.config.queryParamInspectSettings";
	private static final String KEY_SP_STR = "#";
	private static final String VALUE_SP_STR = ",";
	private static final int MAX_VALUE_LENGTH = 500;
	private static ISysQfieldLogService<SysQfieldLogVO, TbSysQfieldLog, String> sysQfieldLogService; 
	
	static {
		sysQfieldLogService = (ISysQfieldLogService<SysQfieldLogVO, TbSysQfieldLog, String>)
				AppContext.getBean("core.service.SysQfieldLogService");
	}
	
	public static Map<String, String> getConfigParamMap() throws Exception {
		return (Map<String, String>)AppContext.getBean(CONFIG_BEAN_ID);
	}
	
	public static List<String> getFields(String systemId, String progId, String actionMethodName) throws Exception {
		if (StringUtils.isBlank(systemId) || StringUtils.isBlank(progId) || StringUtils.isBlank(actionMethodName)) {
			throw new IllegalArgumentException("Query parameter inspact config id error.");
		}
		List<String> fields = null;
		String key = systemId + KEY_SP_STR + progId + KEY_SP_STR + actionMethodName;
		String configValue = null;
		if ( StringUtils.isBlank(( configValue = getConfigParamMap().get(key) )) ) {
			return fields;
		}
		String tmp[] = configValue.split(VALUE_SP_STR);
		if (tmp==null || tmp.length<1) {
			return fields;
		}
		fields = new ArrayList<String>();
		for (String fieldName : tmp) {
			if ( fieldName.trim().length() < 1 ) {
				continue;
			}
			fields.add(fieldName.trim());
		}
		return fields;
	}
	
	private static String getFieldValue(String fieldName, HttpServletRequest request) throws Exception {
		String value = null;
		value = request.getParameter(fieldName);
		if (StringUtils.defaultString(value).length()>MAX_VALUE_LENGTH) {
			value = value.substring(0, MAX_VALUE_LENGTH);
		}
		return value;
	}
	
	public static void log(String systemId, String progId, String actionMethodName, 
			HttpServletRequest request) throws ServiceException, Exception {
		
		List<String> fields = getFields(systemId, progId, actionMethodName);
		if (fields==null || fields.size()<1) {
			return;
		}
		if (SecurityUtils.getSubject()==null || StringUtils.isBlank((String)SecurityUtils.getSubject().getPrincipal())) {
			logger.warn( "No login userId." );
			return;
		}
		logger.info( "log query parameter systemId: " + systemId + "  programId: " + progId );
		for (String fieldName : fields) {
			String value = getFieldValue(fieldName, request);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			SysQfieldLogVO fieldLog = new SysQfieldLogVO();
			fieldLog.setSystem(systemId);
			fieldLog.setProgId(progId);
			fieldLog.setMethodName(actionMethodName);
			fieldLog.setFieldName(fieldName);
			fieldLog.setFieldValue( value );
			fieldLog.setQueryUserId( (String)SecurityUtils.getSubject().getPrincipal() );
			sysQfieldLogService.saveObject(fieldLog);
		}
		
	}
	
}
