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
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.sys.SysEventLogSupport;

@Order(0)
@Aspect
@Component
public class ServiceAuthorityCheckAspect implements IBaseAspectService {
	protected Logger logger=Logger.getLogger(ServiceAuthorityCheckAspect.class);

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
		MethodSignature signature=(MethodSignature)pjp.getSignature();
		Annotation[] annotations=pjp.getTarget().getClass().getAnnotations();
		String serviceId = AspectConstants.getServiceId(annotations);
		Subject subject = SecurityUtils.getSubject();
		Method method = signature.getMethod();
		if (subject.hasRole(Constants.SUPER_ROLE_ALL) || subject.hasRole(Constants.SUPER_ROLE_ADMIN)) {
			SysEventLogSupport.log( 
					(String)subject.getPrincipal(), Constants.getSystem(), this.getEventId(serviceId, method.getName()), true );
			return pjp.proceed();
		}
		if (StringUtils.isBlank(serviceId)) { // 沒有 service id 無法判斷檢查 
			SysEventLogSupport.log( 
					(String)subject.getPrincipal(), Constants.getSystem(), this.getEventId(serviceId, method.getName()), true );
			return pjp.proceed();
		}
		if (!this.isServiceAuthorityCheck(annotations)) { // 沒有 ServiceAuthority 或 check=false 就不用檢查了 
			SysEventLogSupport.log( 
					(String)subject.getPrincipal(), Constants.getSystem(), this.getEventId(serviceId, method.getName()), true );
			return pjp.proceed();
		}		
		Annotation[] methodAnnotations = method.getAnnotations();
		if (this.isServiceMethodAuthority(serviceId, methodAnnotations, subject)) {
			SysEventLogSupport.log( 
					(String)subject.getPrincipal(), Constants.getSystem(), this.getEventId(serviceId, method.getName()), true );
			return pjp.proceed();
		}
		logger.warn(
				"[decline] user[" + subject.getPrincipal() + "] " 
						+ pjp.getTarget().getClass().getName() 
						+ " - " 
						+ signature.getMethod().getName());		
		SysEventLogSupport.log( 
				(String)subject.getPrincipal(), Constants.getSystem(), this.getEventId(serviceId, method.getName()), false );
		throw new AuthorityException(SysMessageUtil.get(GreenStepSysMsgConstants.NO_PERMISSION));
	}
	
	private boolean isServiceAuthorityCheck(Annotation[] annotations) { // 沒有 ServiceAuthority 或 check=false 就不用檢查了 
		if (null==annotations || annotations.length<1) {
			return false;
		}
		boolean check = false;
		for (Annotation anno : annotations) {
			if (anno instanceof ServiceAuthority) {
				check = ((ServiceAuthority)anno).check();
			}
		}
		return check;
	}
	
	private boolean isServiceMethodAuthority(String serviceId, Annotation[] annotations, Subject subject) {
		if (annotations==null || annotations.length<1) { // 沒有 @ServiceMethodAuthority 不要檢查權限
			return true;
		}
		boolean status = false;
		boolean foundServiceMethodAuthority = false;
		for (Annotation anno : annotations) {
			if (anno instanceof ServiceMethodAuthority) {
				foundServiceMethodAuthority = true;
				ServiceMethodType[] types = ((ServiceMethodAuthority)anno).type();
				for (int i=0; types!=null && !status && i<types.length; i++) {
					ServiceMethodType type = types[i];
					// 如 core.service.logic.ApplicationSystemLogicService#INSERT
					String methodPerm = serviceId + Constants.SERVICE_ID_TYPE_DISTINGUISH_SYMBOL + type.toString();
					if (subject.isPermitted(methodPerm)) {
						status = true;
					}
				}
			}
		}
		if (!foundServiceMethodAuthority) { // 沒有 @ServiceMethodAuthority 不要檢查權限 
			return true;
		}
		return status;
	}
	
	private String getEventId(String serviceId, String methodName) {
		return serviceId + "@" + methodName;
	}
	
}
