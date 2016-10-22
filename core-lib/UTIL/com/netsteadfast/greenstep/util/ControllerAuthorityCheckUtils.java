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
package com.netsteadfast.greenstep.util;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.SystemForm;

public class ControllerAuthorityCheckUtils {
	
	public static boolean isControllerAuthority(Annotation[] actionAnnotations, Annotation[] actionMethodAnnotations, Subject subject) {
		if (actionAnnotations!=null && actionAnnotations.length>0) { 
			for (Annotation anno : actionAnnotations) {
				if (anno instanceof ControllerAuthority) {
					if (!((ControllerAuthority)anno).check()) { // check=false , 表示不要檢查權限
						return true;
					}					
				}
				if (anno instanceof SystemForm) {
					String progId = (String) ServletActionContext.getRequest().getParameter("prog_id");
					if ( StringUtils.isBlank(progId) ) { // COMMON FORM 沒有代 prog_id 直接不允許
						return false;
					}
					if (subject.isPermitted(progId)) {
						return true;
					}					
				}
			}
		}
		if (actionMethodAnnotations!=null && actionMethodAnnotations.length>0) {
			for (Annotation anno : actionMethodAnnotations) {
				if (anno instanceof ControllerMethodAuthority) {
					String progId = ((ControllerMethodAuthority)anno).programId();
					if (StringUtils.isBlank(progId)) {
						return false;	
					}
					if (subject.isPermitted(progId)) {
						return true;
					}							
				}
			}
		}
		return false;
	}
	
}
