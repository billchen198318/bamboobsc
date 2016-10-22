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
package com.netsteadfast.greenstep.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.model.YesNo;

public class Constants {	
	
	/**
	 * 不要更改這個設定
	 */
	public static final String BASE_ENCODING = "utf-8";
	
	public static final String HTML_BR = "<BR/>";
	
	/**
	 * EncryptorUtils 要用的 key1
	 */
	public static final String ENCRYPTOR_KEY1 = "@s&T$1O3aSg59#7!";
	
	/**
	 * EncryptorUtils 要用的 key2
	 */
	public static final String ENCRYPTOR_KEY2 = "3215498760654321";
	
	public static final String MAIN_TabContainer_ID = "gscoreTabContainer"; // index.jsp 主要的 Tab id
	public static final String GS_GET_APPLICATION_NAME_SCRIPT_FN = "_getApplicationProgramNameById(progId)"; // 表層非實作 for MenuSupportUtils 與 core.js 用的 script function
	public static final String GS_GET_APPLICATION_NAME_SCRIPT_OBJ = "_applicationProgramNameData"; //  放程式名稱script變數名 for MenuSupportUtils 與 core.js 用的 
	
	public static final String INPUTFIELD_NOTICE_MESSAGE_LABEL = "_noticeMsgLabel"; // 輸入欄位標籤旁邊放置 顯示訊息用的 label , 如 CORE_PROG001D0001A_name_noticeMsgLabel
	public static final String INPUTFIELD_LABEL = "_Label"; // 輸入欄位標籤 label , 如 CORE_PROG001D0001A_name_Label
	
	/**
	 * 選單 js function 打開新的 ContentPane 時 程式 url 要代入的參數 , 用來分別 ajax json xhr 與 ContentPane xhr 判定用 
	 * dojox.layout.ContentPane 它的 X-Requested-With 是 XMLHttpRequest
	 */
	public static final String IS_DOJOX_CONTENT_PANE_XHR_LOAD = "isDojoxContentPane"; 
	public static final String DOJOX_CONTENT_PANE_XHR_RE_LOGIN_PAGE = "/pages/system/login_again.jsp"; // 當session timeout 時在 dojox.layout.ContentPane 出現重登訊息頁面
	public static final String IS_IFRAME_MODE = "isIframeMode";
	public static final String SYS_CURRENT_ID = "gscoreSysCurrentId"; // 主要給 GSBSC-WEB, QCHARTS-WEB 點選 menu 時, url會代入 CORE-WEB 的 current-id 
	
	public static final String QUERY_TYPE_OF_SELECT="select"; // BaseService 查詢 grid 要用
	public static final String QUERY_TYPE_OF_COUNT="count"; // BaseService 查詢 grid 要用
	
	public static final String SESS_ACCOUNT="SESSION_GSCORE_ACCOUNT"; // 登入 account id 放到 session 變數名
	public static final String SESS_LANG = "SESSION_GSCORE_LANG";
	public static final String SESS_SYSCURRENT_ID = "SESSION_GSCORE_SYSCURRENT_ID";
	
	public static final String SESS_PAGE_INFO_NAMESPACE_ByInterceptor="SESSION_pageInfoNamespaceByInterceptor";
	public static final String SESS_PAGE_INFO_ACTION_ByInterceptor="SESSION_pageInfoActionNameByInterceptor";
	public static final String SESS_PAGE_INFO_RemoteAddr_ByInterceptor="SESSION_pageInfoRemoteAddrByInterceptor";
	public static final String SESS_PAGE_INFO_Referer_ByInterceptor="SESSION_pageInfoRefererByInterceptor";
	public static final String SESS_PAGE_INFO_NAMESPACE_ByAction="SESSION_pageInfoNamespaceByAction";
	public static final String SESS_PAGE_INFO_ACTION_ByAction="SESSION_pageInfoActionNameByAction";
	public static final String SESS_PAGE_INFO_RemoteAddr_ByAction="SESSION_pageInfoRemoteAddrByAction";
	public static final String SESS_PAGE_INFO_Referer_ByAction="SESSION_pageInfoRefererByAction";
	
	public static final String _S2_RESULT_BLANK="blank";
	public static final String _S2_RESULT_BLANK_VALUE="blankValue";
	public static final String _S2_RESULT_NO_AUTHORITH="noAuthorith";	
	public static final String _S2_RESULT_SEARCH_NO_DATA="searchNoData";
	public static final String _S2_RESULT_LOGIN_AGAIN="loginAgain";
	public static final String _S2_RESULT_LOGOUT_AGAIN="logoutAgain";
	public static final String _S2_RESULT_ERROR="error";
	public static final String _S2_RESULT_EXCEPTION="Exception";
	
	public static final String PAGE_MESSAGE="greenstep_pageMessage";
	
	public static final String SUPER_ROLE_ALL = "*";
	public static final String SUPER_ROLE_ADMIN = "admin";
	public static final String SUPER_PERMISSION = "*";
	public static final String SYSTEM_BACKGROUND_USER = "system"; // 背景程式要用 , 配 SubjectBuilderForBackground.java 與 shiro.ini
	public static final String SYSTEM_BACKGROUND_PASSWORD = "password99"; // 背景程式要用 , 配 SubjectBuilderForBackground.java 與 shiro.ini
	
	public static final String SERVICE_ID_TYPE_DISTINGUISH_SYMBOL = "#"; // logic service 用來組 service id 與 ServiceMethodType 成字串, 查有沒有權限
	
	/**
	 * GreenStepBaseFormAuthenticationFilter 要用的
	 */
	public static final String NO_LOGIN_JSON_DATA = "{ \"success\":\"" + YesNo.NO + "\",\"message\":\"Please login!\",\"login\":\"" + YesNo.NO + "\",\"isAuthorize\":\"" + YesNo.NO + "\" }";
	/**
	 * GreenStepBaseFormAuthenticationFilter 要用的
	 */	
	public static final String NO_AUTHZ_JSON_DATA = "{ \"success\":\"" + YesNo.NO + "\",\"message\":\"no authorize!\",\"login\":\"" + YesNo.YES + "\",\"isAuthorize\":\"" + YesNo.NO + "\" }";
	
	public static final String APP_SITE_CURRENTID_COOKIE_NAME = "GREENSTEPSYSTEMCURRENTID"; // 跨站 cookie 要用的名稱
	
	public static final String _S2_ACTION_EXTENSION = ".action";
	
	public static final String HTML_SELECT_NO_SELECT_ID="all";
	public static final String HTML_SELECT_NO_SELECT_NAME=" - please select - ";	
	
	public static final String ID_DELIMITER = ";"; // 有時要將多筆 OID 或 key 組成一組字串 , 這是就用這個符號來區分
	
	public static final String DATETIME_DELIMITER = "-"; // calendar note 與 notice message 記錄的日期時間(起迄)用此符號來區分
	
	public static final String TMP_SUB_DIR_NAME = "greenstep";
	
	/**
	 * true 時用系統定的 jasper 路徑 systemId = finalLocation;
	 * false 時用JasperReportsResult.java 原本的 String systemId = servletContext.getRealPath(finalLocation); 
	 * 本系統不可將此調成 false
	 */
	public static final boolean JASPER_REPORTS_RESULT_LOCATION_REPLACE_MODE = true;
	
	/**
	 * common load form 的預定action
	 * 有分 core.commomLoadForm.action , bsc.commomLoadForm.action , qcharts.commomLoadForm.action
	 */
	public static final String _COMMON_LOAD_FORM_ACTION = "commomLoadForm" + _S2_ACTION_EXTENSION;
	
	/**
	 * 保留查詢參數名稱 for PageOf , BaseDAO
	 */
	public static final String _RESERVED_PARAM_NAME_QUERY_SORT_TYPE = "sortType";
	
	/**
	 * 保留查詢參數名稱 for PageOf , BaseDAO
	 */
	public static final String _RESERVED_PARAM_NAME_QUERY_ORDER_BY = "orderBy";
	
	public static String getTmpDir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String getWorkTmpDir() {
		String dirPath = getTmpDir() + "/" + TMP_SUB_DIR_NAME + "/";
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			try {
				FileUtils.forceMkdir(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = null;
		return dirPath;
	}
	
	/**
	 * map data from applicationContext-appSettings.xml app.config.appSettings
	 */
	private static Map<String, Object> appSettingsMap = null;
	
	/**
	 * get applicationContext-appSettings.xml app.config.appSettings
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSettingsMap() {
		if (appSettingsMap!=null) {
			return appSettingsMap;
		}
		appSettingsMap = (Map<String, Object>)AppContext.getBean("app.config.appSettings");
		if (appSettingsMap==null) {
			appSettingsMap=new HashMap<String, Object>();
		}
		return appSettingsMap;
	}
	
	/**
	 * 系統上傳用目錄夾
	 * 
	 * @return
	 */
	public static String getUploadDir() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.uploadDir");
	}
	
	/**
	 * 本系統的ID , 需要與 TB_SYS.SYS_ID 配合
	 * 
	 * @return
	 */
	public static String getSystem() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.system");
	}
	
	/**
	 * 主系統的ID , 需要與 TB_SYS.SYS_ID 配合 , 目前是CORE
	 * 
	 * @return
	 */
	public static String getMainSystem() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.mainSystem");		
	}
	
	/**
	 * 必須與 web.xml 中的設定一樣
	 * 
	 * @return
	 */
	public static String getCxfWebServiceMainPathName() {
		return "services";
	}
	
	/**
	 * 給 CXF JAXRSServerFactoryBean addrss 設定的值 , 如 /jaxrs/
	 * @return
	 */
	public static String getJAXRSServerFactoryBeanAddress() {
		getSettingsMap();
		return (String)appSettingsMap.get("cxf.JAXRSServerFactoryBean.address");
	}
	
	/**
	 * 取到系統要部屬jasper-report 的目錄位址
	 * @return
	 */
	public static String getDeployJasperReportDir() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.deployJasperReportDir");
	}
	
	/**
	 * 系統加密用的key1 , EncryptorUtils 要用的 key1
	 * @return
	 */
	public static String getEncryptorKey1() {
		getSettingsMap();
		String key = (String)appSettingsMap.get("base.encryptorKey1");
		if (StringUtils.isBlank(key)) {
			key = ENCRYPTOR_KEY1;
		}
		return key;
	}
	
	/**
	 * 系統加密用的key2 , EncryptorUtils 要用的 key2
	 * @return
	 */
	public static String getEncryptorKey2() {
		getSettingsMap();
		String key = (String)appSettingsMap.get("base.encryptorKey2");
		if (StringUtils.isBlank(key)) {
			key = ENCRYPTOR_KEY2;
		}
		return key;
	}	
	
	/**
	 * 登入頁面是否需要輸入 CaptchaCode
	 * @return
	 */
	public static String getLoginCaptchaCodeEnable() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.loginCaptchaCodeEnable");
	}
	
	/**
	 * 是否更新 tb_sys.host 的設定
	 * config update tb_sys.host when start CORE-WEB,GSBSC-WEB,QCHARTS-WEB
	 * 1 - only first one 只有第一次啟動時, check 有 log 檔案後就不更新了
	 * 2 - always update 每次都會更新
	 * @return
	 */
	public static String getApplicationSiteHostUpdateMode() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.applicationSiteHostUpdateMode");
	}
	
}
