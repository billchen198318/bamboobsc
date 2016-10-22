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
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.ISwotService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.ISwotLogicService;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SwotVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.SwotLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SwotLogicServiceImpl extends BscBaseLogicService implements ISwotLogicService {
	protected Logger logger=Logger.getLogger(SwotLogicServiceImpl.class);	
	private static final int MAX_ISSUES_LENGTH = 500;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private ISwotService<SwotVO, BbSwot, String> swotService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService; 
	
	public SwotLogicServiceImpl() {
		super();
	}
	
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required		
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}

	public ISwotService<SwotVO, BbSwot, String> getSwotService() {
		return swotService;
	}

	@Autowired
	@Resource(name="bsc.service.SwotService")
	@Required		
	public void setSwotService(ISwotService<SwotVO, BbSwot, String> swotService) {
		this.swotService = swotService;
	}

	public IPerspectiveService<PerspectiveVO, BbPerspective, String> getPerspectiveService() {
		return perspectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.PerspectiveService")
	@Required			
	public void setPerspectiveService(
			IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> create(String visionOid, 
			String organizationOid, List<SwotVO> datas) throws ServiceException, Exception {
		
		if (super.isBlank(visionOid) || super.isBlank(organizationOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		VisionVO vision = new VisionVO();
		vision.setOid(visionOid);
		DefaultResult<VisionVO> vResult = this.visionService.findObjectByOid(vision);
		if (vResult.getValue()==null) {
			throw new ServiceException(vResult.getSystemMessage().getValue());
		}
		vision = vResult.getValue();
		OrganizationVO organization = this.findOrganizationData( organizationOid );
		
		//因為 dojo 的 dijit.InlineEditBox 元件特性是, 沒有修改過, 就不會產生editor , 所以修改模式下用全部刪除後->再全部新增會有問題
		//this.delete(vision.getVisId());
		
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );
		
		Map<String, Object> params = new HashMap<String, Object>();
		for (SwotVO swot : datas) {
			if (!vision.getVisId().equals(swot.getVisId()) || !organization.getOrgId().equals(swot.getOrgId())) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			this.setStringValueMaxLength(swot, "issues", MAX_ISSUES_LENGTH);
			params.clear();
			params.put("perId", swot.getPerId());
			if ( this.perspectiveService.countByParams(params) < 1 ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			if ( this.swotService.countByUK(swot) > 0 ) { // 修改 ISSUES
				this.updateIssues(swot);
			} else { // 新增
				this.swotService.saveObject(swot);
			}
		}
		
		return result;
	}
	
	private void updateIssues(SwotVO swot) throws ServiceException, Exception {
		
		DefaultResult<SwotVO> oldResult = this.swotService.findByUK(swot);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		oldResult.getValue().setIssues( swot.getIssues() );
		this.swotService.updateObject( oldResult.getValue() );
	}
	
	/*
	private void delete(String visId) throws ServiceException, Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visId", visId);
		List<BbSwot> searchList = this.swotService.findListByParams(params);
		for (BbSwot swot : searchList) {
			this.swotService.delete(swot);
		}
		
	}
	*/

}
