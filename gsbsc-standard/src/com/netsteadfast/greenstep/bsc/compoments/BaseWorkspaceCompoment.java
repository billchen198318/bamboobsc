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
package com.netsteadfast.greenstep.bsc.compoments;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceCompomentService;
import com.netsteadfast.greenstep.compoments.BaseCompoment;
import com.netsteadfast.greenstep.compoments.CompomentsModel;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceCompoment;
import com.netsteadfast.greenstep.vo.WorkspaceCompomentVO;

public abstract class BaseWorkspaceCompoment extends BaseCompoment<CompomentsModel> {
	private static final long serialVersionUID = -5581734340485912925L;
	private IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService;
	
	@SuppressWarnings("unchecked")
	public BaseWorkspaceCompoment() {
		super();
		workspaceCompomentService = (IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String>)
				AppContext.getBean("bsc.service.WorkspaceCompomentService");
	}
	
	public abstract String getResource();
	
	public abstract Map<String, Object> getParameters();
	
	public abstract String getBody() throws Exception;

	@Override
	public CompomentsModel loadFromId(String compomentId) throws ServiceException, Exception {
		
		if ( StringUtils.isBlank(compomentId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		WorkspaceCompomentVO compoment = new WorkspaceCompomentVO();
		compoment.setCompId(compomentId);
		DefaultResult<WorkspaceCompomentVO> result = workspaceCompomentService.findByUK(compoment);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		compoment = result.getValue();
		CompomentsModel model = new CompomentsModel();
		this.setModel(model);
		model.setId( compoment.getCompId() );
		model.setName( compoment.getName() );
		model.setResource( this.getResource() );
		model.setParameters( this.getParameters() );
		model.setContent("");
		return model;
	}

}
