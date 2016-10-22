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
package com.netsteadfast.greenstep.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.sys.GreenStepHessianProxyFactory;
import com.netsteadfast.greenstep.sys.GreenStepHessianUtils;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;

@Order(1)
@Aspect
@Component
public class HessianServiceProxyAspect implements IBaseAspectService {
	protected Logger logger = Logger.getLogger(HessianServiceProxyAspect.class);
	
	@Around( AspectConstants.LOGIC_SERVICE_PACKAGE )
	@Override
	public Object logicServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		if (!GreenStepHessianUtils.isEnableCallRemote()) {
			return pjp.proceed();
		}
		return this.proxyProcess(pjp);
	}

	@Around( AspectConstants.BASE_SERVICE_PACKAGE )
	@Override
	public Object baseServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		if (!GreenStepHessianUtils.isEnableCallRemote()) {
			return pjp.proceed();
		}		
		return this.proxyProcess(pjp);
	}
	
	/**
	 * no for DataAccessObject
	 */
	@Override
	public Object dataAccessObjectProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		return pjp.proceed();
	}
	
	/**
	 * 給 GridPage 查詢返回後處理
	 * 如果 hessian 遠端呼叫有啟用時, 要重新調用 PageOf.setCountSize 與 PageOf.toCalculateSize
	 * 因為帶入遠端 Service 的 PageOf 並沒有回傳, 遠端只有回傳 QueryResult 
	 * 
	 * @param queryResult
	 * @param pageOf
	 */
	private void setReCalculateSizePageOfForPageFindGridResult(Object resultObj, Object[] params) {
		if (null == resultObj || !(resultObj instanceof QueryResult)) {
			return;
		}
		if (params == null || params.length < 1) {
			return;
		}
		PageOf pageOf = null;
		for (int i=0; i<params.length && pageOf == null; i++) {
			if (params[i] instanceof PageOf) {
				pageOf = (PageOf)params[i];
			}
		}
		if (null == pageOf) {
			return;
		}
		QueryResult<?> result = (QueryResult<?>) resultObj;
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
	}
	
	private Object proxyProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		MethodSignature signature = (MethodSignature)pjp.getSignature();
		Annotation[] annotations = pjp.getTarget().getClass().getAnnotations();
		String serviceId = AspectConstants.getServiceId(annotations);
		
		/**
		 * 不需要被遠端代理的 service-bean
		 */
		if (!GreenStepHessianUtils.isProxyServiceId(serviceId)) {
			//logger.info( "reject proxy service: " + serviceId );
			return pjp.proceed();
		}
		
		String userId = StringUtils.defaultString((String)SecurityUtils.getSubject().getPrincipal());
		
		if (GreenStepHessianUtils.getConfigHessianHeaderCheckValueModeEnable()) {
			/**
			 * 沒使用者資訊不能處理遠端代理的 service-bean
			 */
			if (StringUtils.isBlank(userId)) {
				logger.warn( "no userId" );
				pjp.proceed();
			}
			
			/**
			 * 此使用者不能存取遠端代理的 service-bean
			 */
			if (GreenStepHessianUtils.isProxyBlockedAccountId(userId)) {
				logger.warn( "reject proxy service: " + serviceId + " , blocked userId: " + userId );
				return pjp.proceed();
			}			
		}
		
		String serviceInterfacesName = "";
		Class<?> serviceInterfaces[] = pjp.getTarget().getClass().getInterfaces();
		for (Class<?> clazz : serviceInterfaces) {
			if (clazz.getName().indexOf(".service.")>-1) {
				serviceInterfacesName = clazz.getName();
			}
		}
		if (StringUtils.isBlank(serviceInterfacesName)) {
			logger.error( "error no service interface: " + serviceId );
			throw new Exception( "error no service interface: " + serviceId );			
		}
		
		String url = GreenStepHessianUtils.getServiceUrl(serviceId);
		String theSystemPath = ApplicationSiteUtils.getHost( Constants.getSystem() ) + "/" + ApplicationSiteUtils.getContextPath( Constants.getSystem() );
		
		/**
		 * 不要自己呼叫遠端代理的自己, 會造成 HessianServiceProxyAspect 一直重複觸發 (客戶端與server-remote同一台的情況下)
		 * 如客戶端是 http://127.0.0.1:8080/
		 * 但是客戶端有開啟 hessian.enable=Y 呼叫遠端代理, 然後遠端url設定為 hessian.serverUrl=http://127.0.0.1:8080/
		 * 
		 */
		if ( url.indexOf(theSystemPath) > -1 ) {
			logger.error("cannot open same-server. now system contextPath = " + theSystemPath + " , but proxy url = " + url);
			throw new Exception("cannot open same-server. now system contextPath = " + theSystemPath + " , but proxy url = " + url);
		}
		
		logger.info( "proxy url = " + url );
		HessianProxyFactory factory = null;
		Object proxyServiceObject = null;
		if (GreenStepHessianUtils.getConfigHessianHeaderCheckValueModeEnable()) { // 一般要checkValue模式
			factory = new GreenStepHessianProxyFactory();
			((GreenStepHessianProxyFactory)factory).setHeaderCheckValue(GreenStepHessianUtils.getEncAuthValue( userId ));
			proxyServiceObject = ((GreenStepHessianProxyFactory)factory).createForHeaderMode(Class.forName(serviceInterfacesName), url);			
		} else { // 不使用checkValue模式
			factory = new HessianProxyFactory();
			proxyServiceObject = factory.create(Class.forName(serviceInterfacesName), url);
		}
		Method[] proxyObjectMethods = proxyServiceObject.getClass().getMethods();
		Method proxyObjectMethod = null;
		if (null == proxyObjectMethods) {
			logger.error( "error no find proxy method: " + serviceId );
			throw new Exception( "error no find proxy method: " + serviceId );
		}
		for (Method m : proxyObjectMethods) {
			if ( m.getName().equals(signature.getMethod().getName()) 
					&& Arrays.equals(m.getParameterTypes(), signature.getMethod().getParameterTypes()) ) {
				proxyObjectMethod = m;
			}
		}
		
		if (null == proxyObjectMethod) {
			logger.error( "error no execute proxy method: " + serviceId );
			throw new Exception( "error no execute proxy method: " + serviceId );
		}
		
		Object resultObj = null;
		try {
			resultObj = proxyObjectMethod.invoke(proxyServiceObject, pjp.getArgs());
			this.setReCalculateSizePageOfForPageFindGridResult(resultObj, pjp.getArgs());
		} catch (InvocationTargetException e) {
			if (e.getMessage() != null) {
				throw new ServiceException(e.getMessage().toString());
			}
			if (e.getTargetException().getMessage() != null) {
				throw new ServiceException(e.getTargetException().getMessage().toString());
			}
			throw e;
		} catch (Exception e) {
			logger.error( e.getMessage().toString() );
			throw e;
		}
		logger.info( "proxy success: " + serviceId + " method: " + proxyObjectMethod.getName() );
		return resultObj;		
	}
	
}
