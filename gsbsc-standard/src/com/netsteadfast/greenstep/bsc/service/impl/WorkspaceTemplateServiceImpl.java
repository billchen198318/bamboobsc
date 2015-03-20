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
package com.netsteadfast.greenstep.bsc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IWorkspaceTemplateDAO;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceTemplate;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceTemplateService;
import com.netsteadfast.greenstep.vo.WorkspaceTemplateVO;

@Service("bsc.service.WorkspaceTemplateService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class WorkspaceTemplateServiceImpl extends BaseService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> implements IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> {
	protected Logger logger=Logger.getLogger(WorkspaceTemplateServiceImpl.class);
	private IWorkspaceTemplateDAO<BbWorkspaceTemplate, String> workspaceTemplateDAO;
	
	public WorkspaceTemplateServiceImpl() {
		super();
	}

	public IWorkspaceTemplateDAO<BbWorkspaceTemplate, String> getWorkspaceTemplateDAO() {
		return workspaceTemplateDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.WorkspaceTemplateDAO")
	@Required		
	public void setWorkspaceTemplateDAO(
			IWorkspaceTemplateDAO<BbWorkspaceTemplate, String> workspaceTemplateDAO) {
		this.workspaceTemplateDAO = workspaceTemplateDAO;
	}

	@Override
	protected IBaseDAO<BbWorkspaceTemplate, String> getBaseDataAccessObject() {
		return workspaceTemplateDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("templateId", "asc");
		List<BbWorkspaceTemplate> templates = this.findListByParams(null, null, orderParams);
		for (int i=0; templates!=null && i<templates.size(); i++) {
			BbWorkspaceTemplate template = templates.get(i);
			dataMap.put(template.getOid(), template.getName());
		}
		return dataMap;
	}

}
