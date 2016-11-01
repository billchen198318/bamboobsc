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
package com.netsteadfast.greenstep.bsc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.compoments.BaseWorkspaceCompoment;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceCompomentService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceConfigService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceLabelService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceTemplateService;
import com.netsteadfast.greenstep.po.hbm.BbWorkspace;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceCompoment;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceConfig;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceLabel;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceTemplate;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.WorkspaceCompomentVO;
import com.netsteadfast.greenstep.vo.WorkspaceConfigVO;
import com.netsteadfast.greenstep.vo.WorkspaceLabelVO;
import com.netsteadfast.greenstep.vo.WorkspaceTemplateVO;
import com.netsteadfast.greenstep.vo.WorkspaceVO;

@SuppressWarnings("unchecked")
public class WorkspaceUtils {
	private static final String RESOURCE_DIR = "META-INF/resource/";
	private static final String TEMPLATE_VARIABLE_NAME = "POSITION_";
	private static IWorkspaceService<WorkspaceVO, BbWorkspace, String> workspaceService;
	private static IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> workspaceTemplateService;
	private static IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService;
	private static IWorkspaceConfigService<WorkspaceConfigVO, BbWorkspaceConfig, String> workspaceConfigService;
	private static IWorkspaceLabelService<WorkspaceLabelVO, BbWorkspaceLabel, String> workspaceLabelService;
	
	static {
		workspaceService = (IWorkspaceService<WorkspaceVO, BbWorkspace, String>)AppContext.getBean("bsc.service.WorkspaceService");
		workspaceTemplateService = (IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String>)
				AppContext.getBean("bsc.service.WorkspaceTemplateService");
		workspaceCompomentService = (IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String>)
				AppContext.getBean("bsc.service.WorkspaceCompomentService");
		workspaceConfigService = (IWorkspaceConfigService<WorkspaceConfigVO, BbWorkspaceConfig, String>)
				AppContext.getBean("bsc.service.WorkspaceConfigService");
		workspaceLabelService = (IWorkspaceLabelService<WorkspaceLabelVO, BbWorkspaceLabel, String>)
				AppContext.getBean("bsc.service.WorkspaceLabelService");
	}
	
	public static String getTemplateConfResource(String templateOid) throws ServiceException, Exception {
		if ( StringUtils.isBlank(templateOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		WorkspaceTemplateVO template = new WorkspaceTemplateVO();
		template.setOid(templateOid);
		DefaultResult<WorkspaceTemplateVO> result = workspaceTemplateService.findObjectByOid(template);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		template = result.getValue();
		String content = TemplateUtils.getResourceSrc(
				WorkspaceUtils.class.getClassLoader(), 
				RESOURCE_DIR + template.getResourceConf());		
		if (StringUtils.isBlank(content)) {
			content = "";
		}
		return content;
	}
	
	public static String getView(String basePath, String workspaceOid, String visionOid, String year, String uploadOid) throws ServiceException, Exception {
		
		Map<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters.put("basePath", basePath);
		templateParameters.put("uploadOid", uploadOid);
		templateParameters.put("visionOid", visionOid);
		templateParameters.put("startYear", year); // 只有 LineChartCompoment.ftl 需要
		templateParameters.put("endYear", year); // 只有 LineChartCompoment.ftl 需要
		return getContent(workspaceOid, true, templateParameters);
	}
	
	public static String getLayoutForView(String workspaceOid) throws ServiceException, Exception {
		
		return getContent(workspaceOid, false, null);
	}
	
	private static String getContent(String workspaceOid, boolean defaultMode, 
			Map<String, Object> templateParameters) throws ServiceException, Exception {
		if ( StringUtils.isBlank(workspaceOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		WorkspaceVO workspace = new WorkspaceVO();
		workspace.setOid(workspaceOid);
		DefaultResult<WorkspaceVO> result = workspaceService.findObjectByOid(workspace);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		workspace = result.getValue();
		
		if (defaultMode) { // for default mode need title
			templateParameters.put("title", workspace.getName());			
		}			
		
		WorkspaceTemplateVO template = new WorkspaceTemplateVO();
		template.setTemplateId(workspace.getTemplateId());
		DefaultResult<WorkspaceTemplateVO> wtResult = workspaceTemplateService.findByUK(template);
		if (wtResult.getValue()==null) {
			throw new ServiceException( wtResult.getSystemMessage().getValue() );
		}
		template = wtResult.getValue();		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spaceId", workspace.getSpaceId());
		List<BbWorkspaceConfig> configs = workspaceConfigService.findListByParams(params);
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				WorkspaceUtils.class.getClassLoader(), 
				RESOURCE_DIR + template.getResource(), 
				fillTemplateParameters(template, configs, defaultMode, templateParameters) );
		if (StringUtils.isBlank(content)) {
			content = "";
		}
		return content;		
	}	
	
	private static Map<String, Object> fillTemplateParameters(WorkspaceTemplateVO template, 
			List<BbWorkspaceConfig> configs, boolean defaultMode, 
			Map<String, Object> templateParameters) throws ServiceException, Exception {
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		for (int position=0; position<template.getPositionSize(); position++) {
			String content = "";
			for (int c=0; configs!=null && c<configs.size(); c++) {
				BbWorkspaceConfig config = configs.get(c);
				if ( config.getPosition() != position ) {
					continue;
				}
				WorkspaceCompomentVO compoment = new WorkspaceCompomentVO();
				compoment.setCompId(config.getCompId());
				DefaultResult<WorkspaceCompomentVO> cResult = workspaceCompomentService.findByUK(compoment);
				if (cResult.getValue()==null) {
					throw new ServiceException( cResult.getSystemMessage().getValue() );
				}
				compoment = cResult.getValue();
				if (defaultMode) { // default mode
					try {
						setTitle(config, templateParameters);						
						content = getCompomentRenderBody(compoment, templateParameters);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				} else { // view layout mode
					content = getCompomentImage(compoment);
				}
			}			
			parameter.put(TEMPLATE_VARIABLE_NAME+position, content);			
		}		
		return parameter;
	}
	
	private static void setTitle(BbWorkspaceConfig config, Map<String, Object> templateParameters) throws ServiceException, Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spaceId", config.getSpaceId());
		params.put("position", config.getPosition());
		List<BbWorkspaceLabel> labels = workspaceLabelService.findListByParams(params);
		if (labels==null || labels.size()<1 ) {
			return;
		}
		for (BbWorkspaceLabel label : labels) {
			templateParameters.put("title", label.getLabel());
		}
	}
	
	private static String getCompomentRenderBody(WorkspaceCompomentVO compoment, Map<String, Object> templateParameters) throws Exception {
		Class<BaseWorkspaceCompoment> clazz = (Class<BaseWorkspaceCompoment>) Class.forName(compoment.getClassName());
		BaseWorkspaceCompoment compomentObj = clazz.newInstance();
		compomentObj.loadFromId(compoment.getCompId());
		compomentObj.getParameters().putAll(templateParameters);		
		compomentObj.doRender();
		return StringUtils.defaultString( compomentObj.getBody() );
	}
	
	private static String getCompomentImage(WorkspaceCompomentVO compoment) {
		return "<img src=\"./compoment-images/" + compoment.getImage() + "\" border=\"0\" alt=\"" + compoment.getName() + "\" />";
	}

}
