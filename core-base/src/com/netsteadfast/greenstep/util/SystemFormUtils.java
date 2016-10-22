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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.FormResultType;
import com.netsteadfast.greenstep.po.hbm.TbSysForm;
import com.netsteadfast.greenstep.po.hbm.TbSysFormMethod;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormMethodService;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysFormVO;

@SuppressWarnings("unchecked")
public class SystemFormUtils {
	protected static Logger logger=Logger.getLogger(SystemFormUtils.class);
	private static final String FORM_PAGE_PATH = "pages/sys-form-pages/";
	private static ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService;
	private static ISysFormService<SysFormVO, TbSysForm, String> sysFormService;
	private static ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	
	public SystemFormUtils() {
		super();
	}
	
	static {
		sysFormMethodService = (ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String>)
				AppContext.getBean("core.service.SysFormMethodService");
		sysFormService = (ISysFormService<SysFormVO, TbSysForm, String>)
				AppContext.getBean("core.service.SysFormService");
		sysFormTemplateService = (ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String>)
				AppContext.getBean("core.service.SysFormTemplateService");
	}
	
	public static SysFormMethodVO findFormMethod(String formId, String methodName) throws ServiceException, Exception {
		SysFormMethodVO formMethod = new SysFormMethodVO();
		formMethod.setFormId( formId );
		formMethod.setName( methodName );
		DefaultResult<SysFormMethodVO> result = sysFormMethodService.findByUK(formMethod);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		formMethod = result.getValue();
		return formMethod;
	}
	
	public static SysFormVO findForm(String formId) throws ServiceException, Exception {
		SysFormVO form = new SysFormVO();
		form.setFormId( formId );
		DefaultResult<SysFormVO> result = sysFormService.findByUK(form);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		form = result.getValue();
		return form;
	}
	
	public static SysFormTemplateVO findTemplate(String templateId) throws ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		template.setTplId( templateId );
		DefaultResult<SysFormTemplateVO> result = sysFormTemplateService.findByUK(template);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		template = result.getValue();
		//this.writePage(template);		
		return template;
	}	
	
	public static boolean getEnableTemplateFileReWriteAlways() {
		if (YesNo.YES.equals(SystemSettingConfigureUtils.getSysFormTemplateFileRewriteValue())) {
			return true;
		}
		return false;
	}
	
	private static Map<String, Object> getParameters(SysFormMethodVO formMethod, Object actionObj,
			Map<String, Object> actionDatas, PageOf pageOf, SearchValue searchValue, List<Map<String, String>> items, 
			Map<String, String> fields, List<String> fieldsId, Map<String, String> fieldsMessage) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formMethodObj", formMethod);
		paramMap.put("form_id", formMethod.getFormId());
		paramMap.put("form_method", formMethod.getName());
		paramMap.put("actionObj", actionObj);
		paramMap.put("datas", actionDatas);
		paramMap.put("pageOf", pageOf);
		paramMap.put("searchValue", searchValue);
		paramMap.put("items", items);
		paramMap.put("fields", fields);
		paramMap.put("fieldsId", fieldsId);
		paramMap.put("fieldsMessage", fieldsMessage);
		return paramMap;
	}	
	
	public static void writePage(SysFormTemplateVO template, HttpServletRequest request) throws Exception {
		String pageFileFullPath = request.getSession().getServletContext().getRealPath("/");
		pageFileFullPath += FORM_PAGE_PATH + template.getFileName();
		if ( !FSUtils.writeStr2(pageFileFullPath, new String(template.getContent(), Constants.BASE_ENCODING) ) ) {
			throw new Exception("create page file error.");
		}		
		logger.info("write template file: " + pageFileFullPath);
	}	
	
	public static void writePage2(TbSysFormTemplate template, HttpServletRequest request) throws Exception {
		String pageFileFullPath = request.getSession().getServletContext().getRealPath("/");
		pageFileFullPath += FORM_PAGE_PATH + template.getFileName();
		if ( !FSUtils.writeStr2(pageFileFullPath, new String(template.getContent(), Constants.BASE_ENCODING) ) ) {
			throw new Exception("create page file error.");
		}
		logger.info("write template file: " + pageFileFullPath);
	}	
	
	public static Map<String, String> processExpression(SysFormMethodVO formMethod, Object actionObj,
			Map<String, Object> actionDatas, PageOf pageOf, SearchValue searchValue, List<Map<String, String>> items, 
			Map<String, String> fields, List<String> fieldsId, Map<String, String> fieldsMessage,
			HttpServletRequest request) throws ControllerException, ServiceException, Exception {		
		
		Map<String, String> resultMap = new HashMap<String, String>();
		SysFormVO form = findForm( formMethod.getFormId() );
		String expression = new String(formMethod.getExpression(), Constants.BASE_ENCODING);
		Map<String, Object> paramMap = getParameters(
				formMethod, actionObj, actionDatas, pageOf, searchValue, items, fields, fieldsId, fieldsMessage);
		ScriptExpressionUtils.execute(
				formMethod.getType(), 
				expression, 
				null, 
				paramMap);		
		if (FormResultType.DEFAULT.equals(formMethod.getResultType())) {
			SysFormTemplateVO template = findTemplate( form.getTemplateId() );
			setViewPage(resultMap, FORM_PAGE_PATH + template.getFileName());
			String pageFileFullPath = request.getSession().getServletContext().getRealPath("/");
			pageFileFullPath += FORM_PAGE_PATH + template.getFileName();
			File file = new File(pageFileFullPath);
			if ( !file.exists() || getEnableTemplateFileReWriteAlways() ) {
				if ( !file.exists() ) {
					logger.warn("no template file: " + pageFileFullPath);
				}
				file = null;								
				writePage(template, request);				
			}
			file = null;
		}		
		if (FormResultType.JSON.equals(formMethod.getResultType())) {
			setJsonValue(
					resultMap, 
					(String)( (Map<String, Object>)paramMap.get("datas") ).get("jsonMessage"), 
					(String)( (Map<String, Object>)paramMap.get("datas") ).get("jsonSuccess"));
		}
		if (FormResultType.REDIRECT.equals(formMethod.getResultType())) {
			setRedirectUrl(resultMap, (String)( (Map<String, Object>)paramMap.get("datas") ).get("redirectUrl"));
		}
		return resultMap;
	}	
	
	public static void deploy( HttpServletRequest request ) throws Exception {
		logger.info("begin deploy...");
		List<TbSysFormTemplate> templates = sysFormTemplateService.findListByParams(null);
		if ( templates == null ) {
			return;
		}
		for (TbSysFormTemplate template : templates) {
			writePage2(template, request);
		}
		logger.info("end...");
	}
	
	public static void setViewPage(Map<String, String> resultMap, String viewPage) {
		resultMap.put("viewPage", viewPage);
	}
	
	public static String getViewPage(Map<String, String> resultMap) {
		return StringUtils.defaultString( resultMap.get("viewPage") );
	}
	
	public static void setJsonValue(Map<String, String> resultMap, String message, String success) {
		resultMap.put("message", message);
		resultMap.put("success", success);
	}
	
	public static String getJsonMessage(Map<String, String> resultMap) {
		return StringUtils.defaultString( resultMap.get("message") );
	}
	
	public static String getJsonSuccess(Map<String, String> resultMap) {
		return StringUtils.defaultString( resultMap.get("success") );
	}
	
	public static void setRedirectUrl(Map<String, String> resultMap, String url) {
		resultMap.put("url", url);
	}
	
	public static String getRedirectUrl(Map<String, String> resultMap) {
		String url = resultMap.get("url");
		return ( !StringUtils.isBlank(url) ? url : "/pages/system/blank.jsp" );
	}

}
