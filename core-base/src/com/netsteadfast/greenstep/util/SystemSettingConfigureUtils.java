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

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class SystemSettingConfigureUtils {
	private static final String CODE_TYPE = "CNF";
	private static final String _MAIL_DEFAULT_FROM_MAIL_CODE = "CNF_CONF001";
	private static final String _MAIL_ENABLE_CODE = "CNF_CONF002";
	private static final String _FIRST_LOAD_JAVASCRIPT_CODE = "CNF_CONF003";
	private static final String _SYS_FORM_TEMPLATE_FILE_REWRITE_CODE = "CNF_CONF004";
	private static final String _LEFT_AccordionContainer_ENABLE_CODE = "CNF_CONF005";
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;	
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)AppContext.getBean("core.service.SysCodeService");
	}
	
	public static SysCodeVO getCode(String code) {
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType(CODE_TYPE);
		sysCode.setCode(code);
		try {
			DefaultResult<SysCodeVO> result = sysCodeService.findByUK(sysCode);
			if (result.getValue()!=null) {
				sysCode = result.getValue();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysCode;
	}	
	
	public static void updateParam1(String code, String value) throws ServiceException, Exception {
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType(CODE_TYPE);
		sysCode.setCode(code);
		DefaultResult<SysCodeVO> result = sysCodeService.findByUK(sysCode);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysCode = result.getValue();
		sysCode.setParam1( value );
		result = sysCodeService.updateObject(sysCode);	
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
	}
	
	public static SysCodeVO getMailDefaultFrom() {
		return getCode(_MAIL_DEFAULT_FROM_MAIL_CODE);
	}
	
	public static String getMailDefaultFromValue() {
		SysCodeVO sysCode = getMailDefaultFrom();
		return StringUtils.defaultString(sysCode.getParam1());
	}
	
	public static void updateMailDefaultFromValue(String value) throws ServiceException, Exception {
		updateParam1(_MAIL_DEFAULT_FROM_MAIL_CODE, value);
	}
	
	public static SysCodeVO getMailEnable() {
		return getCode(_MAIL_ENABLE_CODE);
	}
	
	public static String getMailEnableValue() {
		SysCodeVO sysCode = getMailEnable();
		return StringUtils.defaultString(sysCode.getParam1()).trim();
	}
	
	public static void updateMailEnableValue(String value) throws ServiceException, Exception {
		updateParam1(_MAIL_ENABLE_CODE, value);
	}
	
	public static SysCodeVO getFirstLoadJavascript() {
		return getCode(_FIRST_LOAD_JAVASCRIPT_CODE);
	}
	
	public static String getFirstLoadJavascriptValue() {
		SysCodeVO sysCode = getFirstLoadJavascript();
		return StringUtils.defaultString( sysCode.getParam1() );
	}
	
	public static SysCodeVO getSysFormTemplateFileRewrite() {
		return getCode(_SYS_FORM_TEMPLATE_FILE_REWRITE_CODE);
	}
	
	public static String getSysFormTemplateFileRewriteValue() {
		SysCodeVO sysCode = getSysFormTemplateFileRewrite();
		return StringUtils.defaultString(sysCode.getParam1());
	}
	
	public static void updateSysFormTemplateFileRewriteValue(String value) throws ServiceException, Exception {
		updateParam1(_SYS_FORM_TEMPLATE_FILE_REWRITE_CODE, value);
	}
	
	public static SysCodeVO getLeftAccordionContainerEnable() {
		return getCode(_LEFT_AccordionContainer_ENABLE_CODE);
	}
	
	public static String getLeftAccordionContainerEnableValue() {
		SysCodeVO sysCode = getLeftAccordionContainerEnable();
		return StringUtils.defaultString(sysCode.getParam1());		
	}
	
	public static void updateLeftAccordionContainerEnableValue(String value) throws ServiceException, Exception {
		updateParam1(_LEFT_AccordionContainer_ENABLE_CODE, value);
	}
	
}
