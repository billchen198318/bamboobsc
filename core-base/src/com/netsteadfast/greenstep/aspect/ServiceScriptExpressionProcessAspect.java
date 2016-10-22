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
package com.netsteadfast.greenstep.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.sys.GreenStepHessianUtils;
import com.netsteadfast.greenstep.util.ServiceScriptExpressionUtils;

@Order(10)
@Aspect
@Component
public class ServiceScriptExpressionProcessAspect implements IBaseAspectService {
	protected Logger logger=Logger.getLogger(ServiceScriptExpressionProcessAspect.class);
	
	/**
	 * no enable for scan DAO package
	 */
	//@Around( AspectConstants.DATA_ACCESS_OBJECT_PACKAGE )
	@Override
	public Object dataAccessObjectProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		/**
		 * do something...
		 */
		return pjp.proceed();
	}
	
	/**
	 * no enable for scan Base service package
	 */
	//@Around( AspectConstants.BASE_SERVICE_PACKAGE )
	@Override
	public Object baseServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		/**
		 * do something...
		 */
		return pjp.proceed();
	}
	
	@Around( AspectConstants.LOGIC_SERVICE_PACKAGE )
	public Object logicServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		Annotation[] annotations=pjp.getTarget().getClass().getAnnotations();
		MethodSignature signature=(MethodSignature)pjp.getSignature();
		if (annotations==null || annotations.length<1) { 
			return pjp.proceed();
		}		
		String beanId = AspectConstants.getServiceId(annotations);
		
		/**
		 * 如果是Hession proxy 代理, 讓 remote-server 端處理, 這裡如果是client端就不需要, 要不然會倍加工兩次, remote-server一次, client端一次
		 */
		if (GreenStepHessianUtils.isEnableCallRemote() && GreenStepHessianUtils.isProxyServiceId(beanId)) {
			return pjp.proceed();
		}		
		
		if (StringUtils.isBlank(beanId)) {
			return pjp.proceed();
		}
		if (!ServiceScriptExpressionUtils.needProcess(beanId, signature.getMethod().getName(), Constants.getSystem()) ) {
			return pjp.proceed();
		}
		Method method = signature.getMethod();
		ServiceScriptExpressionUtils.processBefore(beanId, signature.getMethod(), Constants.getSystem(), pjp);
		Object obj = pjp.proceed();
		ServiceScriptExpressionUtils.processAfter(beanId, method, Constants.getSystem(), obj, pjp);
		return obj;
	}
	
}
