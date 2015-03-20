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
package com.netsteadfast.greenstep.base.action;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.model.AccountObj;
import com.netsteadfast.greenstep.base.model.ActionInfoProvide;
import com.netsteadfast.greenstep.base.model.BaseSimpleActionInfo;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.IActionFieldsCheckUtils;
import com.netsteadfast.greenstep.base.model.InterceptorSimpleActionInfo;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.util.ControllerAuthorityCheckUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class BaseSupportAction extends BaseAction implements ServletRequestAware, ServletResponseAware, ServletContextAware {
	private static final long serialVersionUID = -6660355550728049961L;
	public static final int DefaultPageRowSize=PageOf.Rows[0];
	
	private ServletContext servletContext=null;
	private HttpServletResponse httpServletResponse=null;
	private HttpServletRequest httpServletRequest=null;
	
	private Map<String, Object> settingsMap=Constants.getSettingsMap();
	private ActionInfoProvide interceptorActionInfoProvide=new InterceptorSimpleActionInfo();
	private ActionInfoProvide baseActionInfoProvide=new BaseSimpleActionInfo();
	private PageOf pageOf;
	private SearchValue searchValue;
	private String errorMessage="";	
	private String errorContent=SimpleUtils.getStr((String)settingsMap.get("baseSupperAction.errorContent"), "chen.xin.nien@gmail.com");
	private int dojoAjaxTimeout=SimpleUtils.getInt((String)settingsMap.get("baseSupperAction.dojoAjaxTimeout"), 24000);
	private String dojoAjaxSync=SimpleUtils.getStr((String)settingsMap.get("baseSupperAction.dojoAjaxSync"), "true").toLowerCase();
	private String dojoLocal=SimpleUtils.getStr((String)settingsMap.get("baseSupperAction.dojoLocal"), "");		
	private String verMsg=SimpleUtils.getStr((String)settingsMap.get("baseSupperAction.verMsg"), "");
	private String jsVerBuild=SimpleUtils.getStr((String)settingsMap.get("baseSupperAction.jsVerBuild"), "");
	private String googleMapEnable = SimpleUtils.getStr((String)settingsMap.get("googleMap.enable"), YesNo.NO).toUpperCase();
	private String googleMapKey = SimpleUtils.getStr((String)settingsMap.get("googleMap.key"), "AIzaSyAoenmn_-u4AKu-ETSDD00TNeX0ZW4Zcyo");
	private String googleMapDefaultLat = SimpleUtils.getStr((String)settingsMap.get("googleMap.defaultLat"), "25.047795");
	private String googleMapDefaultLng = SimpleUtils.getStr((String)settingsMap.get("googleMap.defaultLng"), "121.516900");
	private String googleMapLanguage = SimpleUtils.getStr((String)settingsMap.get("googleMap.language"), "en");
	private String googleMapClientLocationEnable = SimpleUtils.getStr((String)settingsMap.get("googleMap.clientLocationEnable"), YesNo.NO).toUpperCase();
	private String twitterEnable = SimpleUtils.getStr((String)settingsMap.get("twitter.enable"), YesNo.NO).toUpperCase();
	
	public BaseSupportAction() {
		super();
		this.init();
	}
	
	private void init() {
		if (this.pageOf==null ) {
			this.pageOf=new PageOf();
		}
		if (this.searchValue==null ) {
			this.searchValue=new SearchValue();
			this.searchValue.setText("");
			this.searchValue.setType("");
		}				
	}
	
	public AccountObj getAccountObj() {
		if (StringUtils.isBlank(this.getAccountId())) {
			return null;
		}
		return UserAccountHttpSessionSupport.get(this.getSession());
	}
	
	public String getAccountId() {
		Subject subject = SecurityUtils.getSubject();
		return this.defaultString((String)subject.getPrincipal());
	}
	
	public String getAccountOid() {
		if (StringUtils.isBlank(this.getAccountId())) {
			return null;
		}
		if (this.getAccountObj()==null) {
			return null;
		}
		return this.getAccountObj().getOid();		
	}
	
	public String getIsSuperRole() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
			return YesNo.YES;
		}
		return YesNo.NO;
	}
	
	/**
	 * UserLoginInterceptor 會去掉沒有使用者登入的請求, 只是配合 json 通一變數 "login" 要用到
	 * 
	 * @return
	 */
	protected String isAccountLogin() {
		if (StringUtils.isBlank(this.getAccountId())) {
			return YesNo.NO;
		}
		return YesNo.YES;		
	}
	
	/**
	 * ControllerAuthorityCheckInterceptor 會去掉沒有權限的action, 只是配合 json 通一變數 "isAuthorize" 要用到
	 * 
	 * @return
	 */
	protected String isActionAuthorize() {
		((BaseSimpleActionInfo)this.baseActionInfoProvide).handlerActionAnnotations();
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ALL) || subject.hasRole(Constants.SUPER_ROLE_ADMIN)) {
			return YesNo.YES;
		}
		if (this.isControllerAuthority( 
				((BaseSimpleActionInfo)this.baseActionInfoProvide).getActionAnnotations(), 
				((BaseSimpleActionInfo)this.baseActionInfoProvide).getActionMethodAnnotations() , 
				subject) ) {
			return YesNo.YES;
		}
		if (subject.isPermitted(this.baseActionInfoProvide.getPageInfoActionName()+Constants._S2_ACTION_EXTENSION)) {
			return YesNo.YES;
		}
		return YesNo.NO;
	}
	
	private boolean isControllerAuthority(Annotation[] annotations, Annotation[] actionMethodAnnotations, Subject subject) {
		return ControllerAuthorityCheckUtils.isControllerAuthority(annotations, actionMethodAnnotations, subject);
	}	
	
	// ----------------------------------------------------------------------------
	
	public String getActionMethodProgramId() {
		((BaseSimpleActionInfo)this.baseActionInfoProvide).handlerActionAnnotations();
		Annotation[] annotations = ((BaseSimpleActionInfo)this.baseActionInfoProvide).getActionMethodAnnotations();
		if (annotations==null || annotations.length<1) {
			return "";
		}
		String progId = "";
		for (Annotation annotation : annotations) {
			if (annotation instanceof ControllerMethodAuthority) {
				progId = this.defaultString( ((ControllerMethodAuthority)annotation).programId() );
			}
		}
		return progId;
	}
	
	// ----------------------------------------------------------------------------
	
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext=arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.httpServletResponse=arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.httpServletRequest=arg0;
	}
			
	public ServletContext getServletContext() {
		return servletContext;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	
	// ----------------------------------------------------------------------------

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	// ----------------------------------------------------------------------------

	public PageOf getPageOf() {
		return pageOf;
	}

	public void setPageOf(PageOf pageOf) {
		this.pageOf = pageOf;
	}

	public SearchValue getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(SearchValue searchValue) {
		this.searchValue = searchValue;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorContent() {
		return errorContent;
	}

	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}

	public ActionInfoProvide getInterceptorActionInfoProvide() {
		return interceptorActionInfoProvide;
	}

	public ActionInfoProvide getBaseActionInfoProvide() {
		return baseActionInfoProvide;
	}	

	public int getDojoAjaxTimeout() {
		return dojoAjaxTimeout;
	}

	public String getDojoAjaxSync() {
		return dojoAjaxSync;
	}

	public String getDojoLocal() {
		return dojoLocal;
	}

	public String getVerMsg() {
		return verMsg;
	}

	public String getJsVerBuild() {
		return jsVerBuild;
	}
	
	public String getGoogleMapEnable() {
		return googleMapEnable;
	}

	public String getGoogleMapKey() {
		return googleMapKey;
	}

	public String getGoogleMapDefaultLat() {
		return googleMapDefaultLat;
	}

	public String getGoogleMapDefaultLng() {
		return googleMapDefaultLng;
	}

	public String getGoogleMapLanguage() {
		return googleMapLanguage;
	}

	public String getGoogleMapClientLocationEnable() {
		return googleMapClientLocationEnable;
	}

	public String getTwitterEnable() {
		return twitterEnable;
	}

	public String getPageMessage() {
		return (String)this.getRequest().get(Constants.PAGE_MESSAGE);
	}
	
	public String getDefaultPageRowSize() {
		return String.valueOf(DefaultPageRowSize);
	}
	
	public String getNowDate() throws Exception {
		return SimpleUtils.getStrYMD("/");
	}
	
	public String getNowDate2() throws Exception {
		return SimpleUtils.getStrYMD("-");
	}
	
	public String getUuid() {
		return SimpleUtils.getUUIDStr();
	}
	
	protected void setPageMessage(String pageMessage) {
		if (null!=pageMessage && pageMessage.length()>=500) {
			pageMessage=pageMessage.substring(0, 500);
		}
		this.getRequest().put(Constants.PAGE_MESSAGE, pageMessage);
	}
	
	protected String defaultString(String sourceString) {
		return SimpleUtils.getStr(sourceString, "");
	}
	
	protected void setAttribute(String id, Object object) {
		this.getHttpServletRequest().setAttribute(id, object);
	}
	
	public String getBasePath() {
		if ( this.getHttpServletRequest() == null ) {
			return null;
		}
		return this.getHttpServletRequest().getScheme()+"://"+this.getHttpServletRequest().getServerName()+":"+this.getHttpServletRequest().getServerPort()+this.getHttpServletRequest().getContextPath()+"/";
	}
	
	public String getDojoMainTabContainer() {
		return Constants.MAIN_TabContainer_ID;
	}
	
	protected void checkFields(String[] fieldsName, String[] msg, 
			Class<IActionFieldsCheckUtils>[] checkUtilsClass, String[] methodsName, List<String> fieldsId) throws ControllerException, InstantiationException, IllegalAccessException {
		if (fieldsName==null || msg==null || checkUtilsClass==null 
				|| (fieldsName.length!=msg.length) 
				|| (fieldsName.length!=checkUtilsClass.length)) {
			throw new java.lang.IllegalArgumentException("check filed args error!");
		}
		StringBuilder exceptionMsg = new StringBuilder();
		for (int i=0; i<fieldsName.length; i++) {
			String value = this.getFields().get(fieldsName[i]);
			IActionFieldsCheckUtils checkUtils = checkUtilsClass[i].newInstance();
			if (methodsName!=null && methodsName.length == checkUtilsClass.length) {
				Method methods[] = checkUtils.getClass().getMethods();
				for (Method method : methods) {
					if (method.getName().equals(methodsName[i])) {
						try {
							if ( !(Boolean)method.invoke(null, value) ) {
								if (fieldsId!=null) {
									fieldsId.add(fieldsName[i]);
								}				
								exceptionMsg.append(msg[i]);								
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (!checkUtils.check(value)) {
					if (fieldsId!=null) {
						fieldsId.add(fieldsName[i]);
					}				
					exceptionMsg.append(msg[i]);
				}				
			}
		}
		if (exceptionMsg.length()>0) {
			throw new ControllerException(exceptionMsg.toString());
		}		
	}	
	
	protected void checkFields(String[] fieldsName, String[] msg, 
			Class<IActionFieldsCheckUtils>[] checkUtilsClass, 
			List<String> fieldsId) throws ControllerException, InstantiationException, IllegalAccessException {
		this.checkFields(fieldsName, msg, checkUtilsClass, null, fieldsId);		
	}
	
	protected void checkFields(String[] fieldsName, String[] msg, 
			Class<IActionFieldsCheckUtils>[] checkUtilsClass) throws ControllerException, InstantiationException, IllegalAccessException {
		this.checkFields(fieldsName, msg, checkUtilsClass, null);
	}
	
	/**
	 * 將 fields 中的資料轉 value-Object
	 * 
	 * @param valueObj
	 * @param fieldsName
	 * @return
	 * @throws Exception
	 */
	protected <T> T transformFields2ValueObject(T valueObj, String... fieldsName) throws Exception {		
		return this.transformFields2ValueObject(valueObj, fieldsName, fieldsName);
	}
	
	/**
	 * 將 fields 中的資料轉 value-Object
	 * 
	 * @param valueObj
	 * @param objFieldsName
	 * @param fieldsName
	 * @return
	 * @throws Exception
	 */
	protected <T> T transformFields2ValueObject(T valueObj, String[] objFieldsName , String[] fieldsName) throws Exception {
		if (fieldsName==null || fieldsName.length<1 
				|| objFieldsName==null || fieldsName.length!=objFieldsName.length || null==valueObj) {
			return valueObj;
		}
		for (int i=0; i<fieldsName.length; i++) {
			try {
				Ognl.setValue(objFieldsName[i], valueObj, this.getFields().get(fieldsName[i]));
			} catch (OgnlException e) {
				e.printStackTrace();
			}			
		}
		return valueObj;
	}
	
	/**
	 * 方便 select 出來的 grid VO list 轉成 Map<String, String> List
	 * 
	 * @param searchList
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	protected <T> List<Map<String, String>> transformSearchGridList2JsonDataMapList(List<T> searchList, String... fields) throws Exception {
		
		/*
		List<Map<String, String>> items = new LinkedList<Map<String, String>>();
		if (fields==null || fields.length<1) {
			throw new java.lang.IllegalArgumentException("fields is blank.");
		}
		if (searchList==null || searchList.size()<1) {
			return items;
		}		
		for (T entity : searchList) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (String field : fields) {
				Object value = Ognl.getValue(field, entity);
				dataMap.put(field, (String)value);				
			}
			items.add(dataMap);
		}
		return items;
		*/
		return this.transformList2JsonDataMapList(searchList, fields, fields);
	}
	
	/**
	 * 方便 select 出來的 list 轉成 Map<String, String> List
	 * 下拉Select要用的json資料常會要用到
	 * 
	 * @param searchList
	 * @param objFields
	 * @param mapKeys
	 * @return
	 * @throws Exception
	 */
	protected <T> List<Map<String, String>> transformList2JsonDataMapList(List<T> searchList, String[] objFields, String[] mapKeys) throws Exception {
		
		List<Map<String, String>> items = new LinkedList<Map<String, String>>();
		if (objFields==null || objFields.length<1 || mapKeys==null || mapKeys.length<1) {
			throw new java.lang.IllegalArgumentException("fields is blank.");
		}
		if (objFields.length != mapKeys.length) {
			throw new java.lang.IllegalArgumentException("object fields and map fields, size not equals.");
		}
		if (searchList==null || searchList.size()<1) {
			return items;
		}		
		for (T entity : searchList) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (int i=0; i<objFields.length; i++) {
				Object value = Ognl.getValue(objFields[i], entity);
				dataMap.put(mapKeys[i], String.valueOf(value));
			}
			items.add(dataMap);
		}
		return items;
	}
	
	protected Map<String, String> providedSelectZeroDataMap(boolean pleaseSelectItem) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelectItem) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		return dataMap;
	}	
	
	protected boolean isNoSelectId(String value) {
		if (StringUtils.isBlank(value) || Constants.HTML_SELECT_NO_SELECT_ID.equals(value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 解開: HEX->BASE64->文字資料
	 * 
	 * @param appendIds
	 * @return
	 * @throws Exception
	 */
	protected List<String> transformAppendIds2ListByEncode(String appendIds) throws Exception {
		if (StringUtils.isBlank(appendIds)) {
			return new ArrayList<String>();
		}
		String str = URLDecoder.decode( SimpleUtils.deB64( SimpleUtils.deHex(appendIds) ), "utf8" );
		if (StringUtils.isBlank(str)) {
			return new ArrayList<String>();
		}		
		return this.transformAppendIds2List(str);		
	}
	
	/**
	 * 將頁面上的 OID 或 key 組成的字串轉成 List 
	 * 
	 * @param appendIds
	 * @return
	 * @throws Exception
	 */
	protected List<String> transformAppendIds2List(String appendIds) throws Exception {
		List<String> idList = new ArrayList<String>();
		String tmp[] = appendIds.split(Constants.ID_DELIMITER);
		if (tmp == null || tmp.length<1 ) {
			return idList;
		}
		for (String value : tmp) {
			value = value.trim();
			if (StringUtils.isBlank(value)) {
				continue;
			}
			idList.add(value);
		}
		return SimpleUtils.getListHashSet(idList);
	}
	
}
