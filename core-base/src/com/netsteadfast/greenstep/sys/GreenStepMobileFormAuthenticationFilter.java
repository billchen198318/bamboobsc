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

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.AccountVO;

/**
 * for mobile version web
 *
 */
public class GreenStepMobileFormAuthenticationFilter extends FormAuthenticationFilter {
	protected static Logger logger = Logger.getLogger(GreenStepBaseFormAuthenticationFilter.class);
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private IAccountService<AccountVO, TbAccount, String> accountService;
	
	public GreenStepMobileFormAuthenticationFilter() {
		super();
	}
	
	public IAccountService<AccountVO, TbAccount, String> getAccountService() {
		return accountService;
	}

	@Autowired
	@Resource(name="core.service.AccountService")
	@Required		
	public void setAccountService(
			IAccountService<AccountVO, TbAccount, String> accountService) {
		this.accountService = accountService;
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
		return new GreenStepBaseUsernamePasswordToken(
				username, 
				SimpleUtils.toMD5Hex(SimpleUtils.toB64(password)).toCharArray(), 
				rememberMe, 
				host, 
				captcha);
	}
	
	protected void doCaptchaValidate(HttpServletRequest request, GreenStepBaseUsernamePasswordToken token) {
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
			AccountVO account = this.queryUser(token.getUsername());
			this.userValidate(account);
			Subject subject = this.getSubject(request, response); 
			subject.login(token);
			// set session
			this.setUserSession((HttpServletRequest)request, (HttpServletResponse)response, account);
			return this.onLoginSuccess(token, subject, request, response);			
		} catch (AuthenticationException e) {
			// clear session	
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
		String sysCurrentId = request.getParameter( Constants.SYS_CURRENT_ID );
		if (!StringUtils.isBlank(sysCurrentId)) {
			UserAccountHttpSessionSupport.createSysCurrentId(request, sysCurrentId);
		}
	}
	
	private AccountVO queryUser(String account) throws Exception {
		
		if (StringUtils.isBlank(account)) {
			return null;
		}
		AccountVO accountObj = new AccountVO();
		accountObj.setAccount(account);		
		DefaultResult<AccountVO> result = accountService.findByUK(accountObj);
		if (result.getValue()==null) {
			return null;
		}
		accountObj = result.getValue();		
		return accountObj;
	}
	
	private void userValidate(AccountVO account) throws Exception {
		if(account == null) {
			return;
		}
		if (!YesNo.YES.equals(account.getOnJob())) {
			throw new InvalidAccountException("Invalid account!");
		}
	}
	
    protected boolean isAjaxRequest(HttpServletRequest request) {
    	if (this.isDojoxContentPane(request)) {
    		return false;
    	}
    	return "XMLHttpRequest".equalsIgnoreCase( request.getHeader("X-Requested-With") );
    }
    
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {  	
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
    
    private String getLanguage(HttpServletRequest request) {
		String lang = request.getParameter("lang"); // core-system 取出登入頁面帶入的 lang 參數
		if ( StringUtils.isBlank(lang) ) {
			lang = "en";
		}  	
		return lang;
    }
	
}
