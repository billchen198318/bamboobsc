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
package com.netsteadfast.greenstep.service.aspect;

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
import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;

@Order(0)
@Aspect
@Component
public class ServiceAuthorityCheckAspect {
	protected Logger logger=Logger.getLogger(ServiceAuthorityCheckAspect.class);

	@Around( ServiceAspectConstants.AROUND_VALUE )
	public Object aroundMethod(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		
		MethodSignature signature=(MethodSignature)pjp.getSignature();
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ALL) || subject.hasRole(Constants.SUPER_ROLE_ADMIN)) {
			return pjp.proceed();
		}
		Annotation[] annotations=pjp.getTarget().getClass().getAnnotations();
		String serviceId = this.getServiceId(annotations);
		if (StringUtils.isBlank(serviceId)) { // 沒有 service id 無法判斷檢查 
			return pjp.proceed();
		}
		if (!this.isServiceAuthorityCheck(annotations)) { // 沒有 ServiceAuthority 或 check=false 就不用檢查了 
			return pjp.proceed();
		}
		Method method = signature.getMethod();
		Annotation[] methodAnnotations = method.getAnnotations();
		if (this.isServiceMethodAuthority(serviceId, methodAnnotations, subject)) {
			return pjp.proceed();
		}
		logger.warn(
				"[decline] user[" + subject.getPrincipal() + "] " 
						+ pjp.getTarget().getClass().getName() 
						+ " - " 
						+ signature.getMethod().getName());		
		throw new AuthorityException(SysMessageUtil.get(GreenStepSysMsgConstants.NO_PERMISSION));
	}
	
	private String getServiceId(Annotation[] annotations) {
		String serviceId = "";
		for (Annotation anno : annotations) {
			if (anno instanceof Service) {
				serviceId = ((Service)anno).value();
			}
		}
		return serviceId;
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
		if (annotations==null || annotations.length<1) {
			return false;
		}
		boolean status = false;
		for (Annotation anno : annotations) {
			if (anno instanceof ServiceMethodAuthority) {
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
		return status;
	}
	
}
