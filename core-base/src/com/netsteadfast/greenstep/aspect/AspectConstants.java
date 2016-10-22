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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class AspectConstants {
	
	/**
	 * 注意 base.dao.*.* 不是 base.dao..*.*
	 */
	public static final String DATA_ACCESS_OBJECT_PACKAGE = " execution(* com.netsteadfast.greenstep.base.dao.*.*(..) ) || execution(* com.netsteadfast.greenstep.dao..*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.dao..*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.dao..*.*(..) ) ";
	
	/**
	 * 注意 service.*.*   不是 service..*.* , 如果兩個點..*.* 就會包含 service.logic.
	 * 注意 base.service.*.* 不是 base.service..*.*
	 */
	public static final String BASE_SERVICE_PACKAGE = " execution(* com.netsteadfast.greenstep.base.service.*.*(..) ) || execution(* com.netsteadfast.greenstep.service.*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.service.*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.service.*.*(..) ) ";
	
	/**
	 * 注意 base.service.logic.*.* 不是 base.service.logic..*.*
	 */
	public static final String LOGIC_SERVICE_PACKAGE = " execution(* com.netsteadfast.greenstep.base.service.logic.*.*(..) ) || execution(* com.netsteadfast.greenstep.service.logic..*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.service.logic..*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.service.logic..*.*(..) ) ";
	
	public static boolean isLogicService(String serviceId) {
		if (StringUtils.defaultString(serviceId).indexOf(".service.logic.") > -1) {
			return true;
		}
		return false;
	}
	
	public static String getServiceId(Annotation[] annotations) {
		String serviceId = "";
		if (annotations == null) {
			return serviceId;
		}
		for (Annotation anno : annotations) {
			if (anno instanceof Service) {
				serviceId = ((Service)anno).value();
			}
		}
		return serviceId;
	}
	
	public static String getRepositoryId(Annotation[] annotations) {
		String repoId = "";
		if (annotations == null) {
			return repoId;
		}
		for (Annotation anno : annotations) {
			if (anno instanceof Repository) {
				repoId = ((Repository)anno).value();
			}
		}
		return repoId;
	}	
	
}
