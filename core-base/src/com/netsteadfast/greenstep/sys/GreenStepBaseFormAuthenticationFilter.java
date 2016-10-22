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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.ScriptTypeCode;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.IUSessLogHelper;
import com.netsteadfast.greenstep.base.sys.USessLogHelperImpl;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.base.sys.UserCurrentCookie;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.AccountVO;

public class GreenStepBaseFormAuthenticationFilter extends FormAuthenticationFilter {
	protected static Logger logger = Logger.getLogger(GreenStepBaseFormAuthenticationFilter.class);
	public static final String CREATE_USER_DATA_LDAP_MODE_SCRIPT = "META-INF/create-user-data-ldap-mode.groovy";	
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private static String createUserDataLdapModeScript = "";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private IUSessLogHelper uSessLogHelper;
	
	public GreenStepBaseFormAuthenticationFilter() {
		super();
		uSessLogHelper = new USessLogHelperImpl();
	}
	
	public static String getCreateUserDataLdapModeScript() throws Exception {
		if ( !StringUtils.isBlank(createUserDataLdapModeScript) ) {
			return createUserDataLdapModeScript;
		}
		InputStream is = null;
		try {
			is = GreenStepBaseFormAuthenticationFilter.class.getClassLoader().getResource( CREATE_USER_DATA_LDAP_MODE_SCRIPT ).openStream();
			createUserDataLdapModeScript = IOUtils.toString(is, Constants.BASE_ENCODING);			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is!=null) {
				is.close();
			}			
			is = null;			
		}
		return createUserDataLdapModeScript;
	}	
	
	protected String getCaptcha(ServletRequest request) {		
        return WebUtils.getCleanParam(request, this.getCaptchaParam());
    }
	
	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = StringUtils.defaultString(this.getUsername(request));
		String password = StringUtils.defaultString(this.getPassword(request));
		String captcha = StringUtils.defaultString(this.getCaptcha(request));
		//boolean rememberMe = StringUtils.defaultString(isRememberMe(request));
		boolean rememberMe = false;
		String host = StringUtils.defaultString(getHost(request));
		char pwd[] = null;
		try {
			if (this.isLoginLdapMode()) { // login by LDAP.
				pwd = password.toCharArray();
			} else { // default by DB
				ShiroLoginSupport loginSupport = new ShiroLoginSupport();
				pwd = loginSupport.getAccountService().tranPassword(password).toCharArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GreenStepBaseUsernamePasswordToken(
				username, 
				pwd, 
				rememberMe, 
				host, 
				captcha);
	}
	
	protected void doCaptchaValidate(HttpServletRequest request, GreenStepBaseUsernamePasswordToken token) {
		if (!YesNo.YES.equals(Constants.getLoginCaptchaCodeEnable())) { // 2015-12-18 add https://github.com/billchen198318/bamboobsc/issues/5
			return;
		}
		Object sessCaptcha = SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String inputCaptcha = token.getCaptcha();
		if (!(sessCaptcha instanceof String) || StringUtils.isBlank(inputCaptcha) ) {
			throw new IncorrectCaptchaException("captcha error!");
		}
		if (!inputCaptcha.equals(sessCaptcha)) {
			throw new IncorrectCaptchaException("captcha error!");
		}				
	}
	
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		GreenStepBaseUsernamePasswordToken token = 
				(GreenStepBaseUsernamePasswordToken) this.createToken(request, response);
		try {
			this.doCaptchaValidate((HttpServletRequest)request, token);
			
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			AccountVO account = loginSupport.queryUserValidate(token.getUsername());
			
			Subject subject = this.getSubject(request, response);
			subject.login(token);
			
			if (this.isLoginLdapMode() && account==null) { // is no account data in DataBase, create it.
				account = this.createUserDataLdapLoginMode(token.getUsername(), new String(token.getPassword()));
			}			
			// set session
			this.setUserSession((HttpServletRequest)request, (HttpServletResponse)response, account);
			return this.onLoginSuccess(token, subject, request, response);			
		} catch (AuthenticationException e) {
			// clear session	
			logger.warn( e.getMessage().toString() );			
			UserAccountHttpSessionSupport.remove( (HttpServletRequest)request );
			this.getSubject(request, response).logout();
			return this.onLoginFailure(token, e, request, response);
		}		
	} 
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, 
			ServletRequest request, ServletResponse response) throws Exception {
		
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (!this.isAjaxRequest(httpServletRequest)) {
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
        } else {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_AUTHZ_JSON_DATA);
        }
		return false;
	}
	
	private void setUserSession(HttpServletRequest request, HttpServletResponse response, AccountVO account) throws Exception {		
		UserAccountHttpSessionSupport.create(request, account, this.getLanguage(request));
		String httpSessionId = request.getSession().getId();
		if (StringUtils.isBlank(httpSessionId)) {
			httpSessionId = "NULL";
		}
		if ( Constants.getSystem().equals( Constants.getMainSystem() ) ) { // core-web
			List<String> currs = uSessLogHelper.findCurrenrIdByAccount(account.getAccount(), httpSessionId);
			if (currs!=null && currs.size()>0) {
				UserCurrentCookie.setCurrentId(response, currs.get(0), request.getSession().getId(), 
						account.getAccount(), this.getLanguage(request));			
				UserAccountHttpSessionSupport.createSysCurrentId(request, currs.get(0));
			}			
			SysLoginLogSupport.log( account.getAccount() );	 // only core-system need log tb_sys_login_log
		} else { // gsbsc-web, qcharts-web
			
			/*
			 * 2015-09-07 rem
			 * 
			String sysCurrentId = request.getParameter( Constants.SYS_CURRENT_ID );
			if (!StringUtils.isBlank(sysCurrentId)) {
				UserAccountHttpSessionSupport.createSysCurrentId(request, sysCurrentId);
			}
			*/
			// 2015-09-07 add
			Map<String, String> dataMap = UserCurrentCookie.getCurrentData(request);
			String sysCurrentId = dataMap.get("currentId");
			if (!StringUtils.isBlank(sysCurrentId)) {
				UserAccountHttpSessionSupport.createSysCurrentId(request, sysCurrentId);
			}
			
		}
	}
	
    protected boolean isAjaxRequest(HttpServletRequest request) {
    	if (this.isDojoxContentPane(request)) {
    		return false;
    	}
    	return "XMLHttpRequest".equalsIgnoreCase( request.getHeader("X-Requested-With") );
    }
    
    private boolean isLoginLdapMode() {
    	try {
        	if (AppContext.getBean("ldapContextFactory")!=null && (AppContext.getBean("ldapContextFactory") instanceof JndiLdapContextFactory)) {
        		return true;
        	}    		
    	} catch (NoSuchBeanDefinitionException e) {
    		// nothing...    		
    	}
    	return false;
    }
    
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	if ( !Constants.getSystem().equals( Constants.getMainSystem() ) && !isAjaxRequest((HttpServletRequest)request) ) { // 非 core-web
    		try {
				if ( this.loginUseCurrentCookieForGeneralPackage(request, response) ) { // no need to login-page
					String url = SimpleUtils.getHttpRequestUrl( (HttpServletRequest)request );
					logger.warn("URL = " + url );					
					WebUtils.issueRedirect(request, response, url);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}    	
    	if (isAjaxRequest((HttpServletRequest)request)) {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_LOGIN_JSON_DATA);
    		return;
    	}
    	if (this.isIframeMode((HttpServletRequest)request)) { // iframe 不要導向 login.action 因為畫面會怪怪的    		
    		WebUtils.issueRedirect(request, response, "/pages/system/error_static.jsp");
    		return;
    	}    	
    	if (this.isDojoxContentPane((HttpServletRequest)request)) { // 在 dojox.layout.ContentPane 不要出現 login.action 頁面    		
    		WebUtils.issueRedirect(request, response, Constants.DOJOX_CONTENT_PANE_XHR_RE_LOGIN_PAGE);
    		return;
    	}
    	WebUtils.issueRedirect(request, response, getLoginUrl());
    }	
    
    private boolean isDojoxContentPane(HttpServletRequest request) {
    	String isDojoxContentPane = request.getParameter(Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD);
    	if (YesNo.YES.equals(isDojoxContentPane)) { // dojox.layout.ContentPane 它的 X-Requested-With 是 XMLHttpRequest
    		return true;
    	}
    	return false;
    }
    
    private boolean isIframeMode(HttpServletRequest request) {
    	String isIframeMode = request.getParameter(Constants.IS_IFRAME_MODE);
    	if (YesNo.YES.equals(isIframeMode)) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * 非 core-web 登入方式 , 給 gsbsc-web, qcharts-web 用的
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private boolean loginUseCurrentCookieForGeneralPackage(
    		ServletRequest request, ServletResponse response) throws Exception {
    	
    	Map<String, String> dataMap = UserCurrentCookie.getCurrentData( (HttpServletRequest)request );
    	if ( dataMap == null ) {
    		return false;
    	}
    	String currentId = dataMap.get("currentId");
    	String accountId = dataMap.get("account");
    	if ( StringUtils.isBlank(currentId) || StringUtils.isBlank(accountId) ) {
    		return false;
    	}
		// 先去 tb_sys_usess 用 current_id與帳戶 去查有沒有存在 db	
    	if ( this.uSessLogHelper.countByCurrent(accountId, currentId) < 1 ) { // 沒有在 core-web 登入, 所以沒有 TB_SYS_USESS 的資料
    		return false;
    	}    	
    	String captchaStr = "0123"; // 這理的 captcha 不須要比對了 , 因為不是 core-web 的登入畫面    	
    	request.setAttribute(this.captchaParam, captchaStr);
    	( (HttpServletRequest)request ).getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, captchaStr);
    	
    	ShiroLoginSupport loginSupport = new ShiroLoginSupport();
    	AccountVO account = loginSupport.queryUserValidate(accountId);
    	loginSupport.forceCreateLoginSubject((HttpServletRequest)request, (HttpServletResponse)response, accountId, captchaStr);
		
		// set session
		this.setUserSession((HttpServletRequest)request, (HttpServletResponse)response, account);    	    	
    	return true;
    }
    
    private String getLanguage(HttpServletRequest request) {
		String lang = request.getParameter("lang"); // core-system 取出登入頁面帶入的 lang 參數
    	if ( !Constants.getSystem().equals( Constants.getMainSystem() ) ) { // 非 core-system 所以 lang 參數在 cookie 中取出
        	Map<String, String> dataMap = UserCurrentCookie.getCurrentData( request );
        	if ( dataMap != null ) {
        		//System.out.println(dataMap);
        		lang = (String)dataMap.get("lang");
        	}
    	}		
		if ( StringUtils.isBlank(lang) ) {
			lang = "en";
		}  	
		return lang;
    }
    
    /**
     * Create need user data when login by LDAP!
     * 
     * @param account
     * @param password
     * @throws Exception
     */
    private AccountVO createUserDataLdapLoginMode(String account, String password) throws Exception {    	
    	if (account.length()>24) {
    		throw new Exception("Create user data fail! account ID length more then 24.");
    	}
    	if (password.length()>35) {
    		throw new Exception("Create user data fail! password length more then 35.");
    	}
    	ShiroLoginSupport loginSupport = new ShiroLoginSupport();
    	logger.info("create user data, login by LDAP mode, account: " + account);    	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("account", account);
    	paramMap.put("transPassword", loginSupport.getAccountService().tranPassword(password));
    	ScriptExpressionUtils.execute(ScriptTypeCode.IS_GROOVY, getCreateUserDataLdapModeScript(), null, paramMap);
    	return loginSupport.queryUser(account);
    }
	
}
