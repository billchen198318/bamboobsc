/* 
 * Copyright 2012-2013 bambooBSC of copyright Chen Xin Nien
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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.EncryptorUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

@SuppressWarnings("unchecked")
public class GreenStepHessianUtils {
	
	public static final String HEADER_CHECK_VALUE_PARAM_NAME = "greenstep_hessian_auth_check_value";
	public static final String CHECK_VALUE_PARAM_NAME = "checkValue";
	public static final String USER_ID_PARAM_NAME = "accountId";
	private static String configHessianUrlPattern = "/hessian/"; // 預設的 hession 服務子位址, 必須與 web.xml 設定一致
	private static String configHessianExtensionName = ".hessian"; // 預設的 hession 服務擴展名, 必須與 Hessian-servlet.xml 設定一致	
	private static Map<String, Object> configMap = null;
	private static String checkValue = "W$oTj09!Rq@N30l$";
	private static List<String> proxyServiceId = new ArrayList<String>();
	private static List<String> proxyBlockedAccountId = new ArrayList<String>();
	
	static {
		configMap = (Map<String, Object>)AppContext.getBean("hessian.config");
		checkValue = SimpleUtils.getStr(String.valueOf(configMap.get("checkValue")), checkValue);
		if (checkValue.trim().length()<1) {
			checkValue = "W$oTj09!Rq@N30l$";
		}
		String proxyServiceIdStr = StringUtils.defaultString( String.valueOf( configMap.get("proxyServiceId") ) )
				.replaceAll("\t", "").replaceAll("\n", "").replaceAll(" ", "").trim();
		String serviceIdTmp[] = proxyServiceIdStr.split(",");
		for (int i=0; serviceIdTmp!=null && i<serviceIdTmp.length; i++) {
			String serviceId = serviceIdTmp[i].replaceAll(" ", "").trim();
			if (StringUtils.isBlank(serviceId)) {
				continue;
			}
			proxyServiceId.add(serviceId);
		}
		String proxyBlockedAccountIdStr = StringUtils.defaultString( String.valueOf( configMap.get("proxyBlockedAccountId") ) )
				.replaceAll("\t", "").replaceAll("\n", "").replaceAll(" ", "").trim();
		String blockedAccountIdTmp[] = proxyBlockedAccountIdStr.split(",");
		for (int i=0; blockedAccountIdTmp!=null && i<blockedAccountIdTmp.length; i++) {
			String accountId = blockedAccountIdTmp[i].replaceAll(" ", "").trim();
			if (StringUtils.isBlank(accountId)) {
				continue;
			}
			proxyBlockedAccountId.add(accountId);
		}
		if (!StringUtils.isBlank((String)configMap.get("configHessianUrlPattern"))) {
			configHessianUrlPattern = String.valueOf(configMap.get("configHessianUrlPattern")).trim();
		}
		if (!StringUtils.isBlank((String)configMap.get("configHessianExtensionName"))) {
			configHessianExtensionName = String.valueOf(configMap.get("configHessianExtensionName")).trim();
		}		
	}
	
	public static Map<String, Object> getConfigMap() {
		return configMap;
	}
	
	public static String getConfigHessianUrlPattern() {
		return configHessianUrlPattern;
	}
	
	public static String getConfigHessianExtensionName() {
		return configHessianExtensionName;
	}
	
	public static boolean getConfigHessianHeaderCheckValueModeEnable() {
		if (YesNo.YES.equals(configMap.get("configHessianHeaderCheckValueModeEnable"))) {
			return true;
		}
		return false;
	}
	
	public static boolean isEnableCallRemote() {
		if (YesNo.YES.equals(configMap.get("enable"))) {
			return true;
		}
		return false;
	}
	
	public static String getServerUrl() {
		return String.valueOf( configMap.get("serverUrl") ).trim();
	}
	
	public static String getUserId(Map<String, String> dataMap) {
		return dataMap.get(USER_ID_PARAM_NAME);
	}
	
	public static String getCheckValue(Map<String, String> dataMap) {
		return dataMap.get(CHECK_VALUE_PARAM_NAME);
	}
	
	public static String getHttpRequestHeaderCheckValue(HttpServletRequest request) {
		return request.getHeader(HEADER_CHECK_VALUE_PARAM_NAME);
	}
	
	public static boolean isCheckValue(Map<String, String> dataMap) throws Exception {
		return isCheckValue( dataMap.get(CHECK_VALUE_PARAM_NAME) );
	}
	
	public static boolean isCheckValue(String decCheckValue) throws Exception {		
		if (!checkValue.equals( decCheckValue )) {
			return false;
		}
		return true;
	}
	
	public static boolean isProxyServiceId(String serviceId) {
		return proxyServiceId.contains(serviceId);
	}	
	
	public static boolean isProxyBlockedAccountId(String accountId) {
		return proxyBlockedAccountId.contains(accountId);
	}
	
	public static String getEncAuthValue(String accountId) throws Exception {
		if (StringUtils.isBlank(accountId)) {
			throw new Exception( "accountId is required!" );
		}
		String value = checkValue + Constants.ID_DELIMITER + SimpleUtils.createRandomString(8) 
			+ Constants.ID_DELIMITER + System.currentTimeMillis() + Constants.ID_DELIMITER + accountId;
		return SimpleUtils.toHex( EncryptorUtils.encrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), value) );
	}
	
	public static Map<String, String> getDecAuthValue(String encValue) throws Exception {
		String value = EncryptorUtils.decrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), SimpleUtils.deHex(encValue));
		String val[] = StringUtils.defaultString(value).trim().split(Constants.ID_DELIMITER);
		if (val.length!=4) {
			return null;
		}
		if (val[0].trim().length()<1 || val[1].trim().length()!=8 || !NumberUtils.isCreatable(val[2]) 
				|| !SimpleUtils.checkBeTrueOf_azAZ09(val[3])) {
			return null;
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put(CHECK_VALUE_PARAM_NAME, val[0].trim());
		data.put(USER_ID_PARAM_NAME, val[3].trim());
		return data;
	}
	
	public static String getServiceUrl(String serviceBeanId) throws Exception {
		String contextPath = ApplicationSiteUtils.getContextPath( Constants.getSystem() ) + getConfigHessianUrlPattern();
		String url = GreenStepHessianUtils.getServerUrl() + contextPath + serviceBeanId + getConfigHessianExtensionName();
		return url;
	}
	
}
