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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceCompomentService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceConfigService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceLabelService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceTemplateService;
import com.netsteadfast.greenstep.bsc.service.logic.IWorkspaceLogicService;
import com.netsteadfast.greenstep.po.hbm.BbWorkspace;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceCompoment;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceConfig;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceLabel;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceTemplate;
import com.netsteadfast.greenstep.vo.WorkspaceCompomentVO;
import com.netsteadfast.greenstep.vo.WorkspaceConfigVO;
import com.netsteadfast.greenstep.vo.WorkspaceLabelVO;
import com.netsteadfast.greenstep.vo.WorkspaceTemplateVO;
import com.netsteadfast.greenstep.vo.WorkspaceVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.WorkspaceLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class WorkspaceLogicServiceImpl extends CoreBaseLogicService implements IWorkspaceLogicService {
	protected Logger logger=Logger.getLogger(WorkspaceLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IWorkspaceService<WorkspaceVO, BbWorkspace, String> workspaceService;
	private IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> workspaceTemplateService;
	private IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService;
	private IWorkspaceConfigService<WorkspaceConfigVO, BbWorkspaceConfig, String> workspaceConfigService;
	private IWorkspaceLabelService<WorkspaceLabelVO, BbWorkspaceLabel, String> workspaceLabelService;
	
	public WorkspaceLogicServiceImpl() {
		super();
	}
	
	public IWorkspaceService<WorkspaceVO, BbWorkspace, String> getWorkspaceService() {
		return workspaceService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceService")
	@Required		
	public void setWorkspaceService(
			IWorkspaceService<WorkspaceVO, BbWorkspace, String> workspaceService) {
		this.workspaceService = workspaceService;
	}

	public IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> getWorkspaceTemplateService() {
		return workspaceTemplateService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceTemplateService")
	@Required		
	public void setWorkspaceTemplateService(
			IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> workspaceTemplateService) {
		this.workspaceTemplateService = workspaceTemplateService;
	}

	public IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> getWorkspaceCompomentService() {
		return workspaceCompomentService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceCompomentService")
	@Required	
	public void setWorkspaceCompomentService(
			IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService) {
		this.workspaceCompomentService = workspaceCompomentService;
	}

	public IWorkspaceConfigService<WorkspaceConfigVO, BbWorkspaceConfig, String> getWorkspaceConfigService() {
		return workspaceConfigService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceConfigService")
	@Required		
	public void setWorkspaceConfigService(
			IWorkspaceConfigService<WorkspaceConfigVO, BbWorkspaceConfig, String> workspaceConfigService) {
		this.workspaceConfigService = workspaceConfigService;
	}

	public IWorkspaceLabelService<WorkspaceLabelVO, BbWorkspaceLabel, String> getWorkspaceLabelService() {
		return workspaceLabelService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceLabelService")
	@Required		
	public void setWorkspaceLabelService(
			IWorkspaceLabelService<WorkspaceLabelVO, BbWorkspaceLabel, String> workspaceLabelService) {
		this.workspaceLabelService = workspaceLabelService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@SuppressWarnings("unchecked")
	@Override
	public DefaultResult<WorkspaceVO> create(String spaceId, String name, String description,
			String workspaceTemplateOid, Map<String, Object> jsonData) throws ServiceException, Exception {
		
		if ( super.isBlank(spaceId) || super.isBlank(name) || super.isNoSelectId(workspaceTemplateOid)
				|| jsonData == null || jsonData.size() < 1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if ( jsonData.get("nodes") == null || !(jsonData.get("nodes") instanceof List) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if ( jsonData.get("labels") == null || !(jsonData.get("labels") instanceof List) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		WorkspaceTemplateVO template = new WorkspaceTemplateVO();
		template.setOid(workspaceTemplateOid);
		DefaultResult<WorkspaceTemplateVO> wtResult = this.workspaceTemplateService.findObjectByOid(template);
		if ( wtResult.getValue()==null ) {
			throw new ServiceException( wtResult.getSystemMessage().getValue() );
		}
		template = wtResult.getValue();
		
		WorkspaceVO workspace = new WorkspaceVO();
		workspace.setSpaceId(spaceId);
		workspace.setTemplateId( template.getTemplateId() );
		workspace.setName(name);
		workspace.setDescription( super.defaultString(description) );
		this.setStringValueMaxLength(workspace, "description", MAX_DESCRIPTION_LENGTH);
		DefaultResult<WorkspaceVO> result = this.workspaceService.saveObject(workspace);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		workspace = result.getValue();
		
		List<Map<String, Object>> nodes = (List<Map<String, Object>>)jsonData.get("nodes");
		List<Map<String, Object>> labels = (List<Map<String, Object>>)jsonData.get("labels"); 		
		
		for (int i=0; nodes!=null && i<nodes.size(); i++) {
			Map<String, Object> data = nodes.get(i);
			String id = (String)data.get("id");
			int position = Integer.parseInt( String.valueOf(data.get("position")) );			
			BbWorkspaceCompoment compoment = new BbWorkspaceCompoment();
			compoment.setCompId(id);
			if ( this.workspaceCompomentService.countByEntityUK(compoment) < 1 ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}			
			WorkspaceConfigVO configObj = new WorkspaceConfigVO();
			configObj.setSpaceId(workspace.getSpaceId());
			configObj.setCompId( id );
			configObj.setPosition(position);
			this.workspaceConfigService.saveObject(configObj);			
		}
		
		for (int i=0; labels!=null && i<labels.size(); i++) {
			Map<String, Object> data = labels.get(i);
			String label = (String)data.get("label");
			int position = Integer.parseInt( String.valueOf(data.get("position")) );			
			if (StringUtils.isBlank(label)) {
				label = "   ";
			}
			WorkspaceLabelVO labelObj = new WorkspaceLabelVO();
			labelObj.setSpaceId(workspace.getSpaceId());
			labelObj.setLabel(label);
			labelObj.setPosition(position);
			this.workspaceLabelService.saveIgnoreUK(labelObj);
		}
		
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<Boolean> delete(String workspaceOid) throws ServiceException, Exception {
		
		if (super.isBlank(workspaceOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		WorkspaceVO workspace = new WorkspaceVO();
		workspace.setOid(workspaceOid);
		DefaultResult<WorkspaceVO> oldResult = this.workspaceService.findObjectByOid(workspace);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		workspace = oldResult.getValue();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spaceId", workspace.getSpaceId());
		
		List<BbWorkspaceConfig> configs = this.workspaceConfigService.findListByParams(params);
		List<BbWorkspaceLabel> labels = this.workspaceLabelService.findListByParams(params);
		
		for (int i=0; configs!=null && i<configs.size(); i++) {
			BbWorkspaceConfig config = configs.get(i);
			this.workspaceConfigService.delete(config);
		}
		
		for (int i=0; labels!=null && i<labels.size(); i++) {
			BbWorkspaceLabel label = labels.get(i);
			this.workspaceLabelService.delete(label);			
		}
		
		return workspaceService.deleteObject( workspace );
	}

}
