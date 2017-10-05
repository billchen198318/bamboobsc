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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.model.ItemTargetOrMaximumAndMinimalValue;
import com.netsteadfast.greenstep.bsc.model.MonitorItemType;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.ISwotService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IPerspectiveLogicService;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SwotVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.PerspectiveLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PerspectiveLogicServiceImpl extends CoreBaseLogicService implements IPerspectiveLogicService {
	protected Logger logger=Logger.getLogger(PerspectiveLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private ISwotService<SwotVO, BbSwot, String> swotService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	
	public PerspectiveLogicServiceImpl() {
		super();
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

	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.ObjectiveService")
	@Required				
	public void setObjectiveService(
			IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
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
	
	public IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> getMonitorItemScoreService() {
		return monitorItemScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.MonitorItemScoreService")
	@Required	
	public void setMonitorItemScoreService(
			IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService) {
		this.monitorItemScoreService = monitorItemScoreService;
	}	

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)		
	@Override
	public String findForMaxPerId(String date) throws ServiceException, Exception {
		if (super.isBlank(date) || !NumberUtils.isCreatable(date) || date.length()!=8 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		String maxVisionId = this.perspectiveService.findForMaxPerId(BscConstants.HEAD_FOR_PER_ID+date); 
		if (StringUtils.isBlank(maxVisionId)) {
			return BscConstants.HEAD_FOR_PER_ID + date + "001";
		}
		int maxSeq = Integer.parseInt( maxVisionId.substring(11, 14) ) + 1;
		if ( maxSeq > 999 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) + " over max seq 999!");
		}		
		return BscConstants.HEAD_FOR_PER_ID + date + StringUtils.leftPad(String.valueOf(maxSeq), 3, "0");			 
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<PerspectiveVO> create(PerspectiveVO perspective, String visionOid) throws ServiceException, Exception {
		if (null == perspective || super.isBlank(visionOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<VisionVO> vResult = this.visionService.findForSimple(visionOid); 
		if (vResult.getValue() == null) {
			throw new ServiceException(vResult.getSystemMessage().getValue());
		}
		VisionVO vision = vResult.getValue();
		perspective.setVisId( vision.getVisId() );
		if ( !SimpleUtils.checkBeTrueOf_azAZ09(4, 14, perspective.getPerId()) ) { // for import-mode from csv file PER_ID is old(before id).
			perspective.setPerId( this.findForMaxPerId(SimpleUtils.getStrYMD("")) );
		}		
		this.setStringValueMaxLength(perspective, "description", MAX_DESCRIPTION_LENGTH);
		perspective.setTarget( ItemTargetOrMaximumAndMinimalValue.get(perspective.getTarget()) );
		perspective.setMin( ItemTargetOrMaximumAndMinimalValue.get(perspective.getMin()) );
		return this.perspectiveService.saveObject(perspective);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<PerspectiveVO> update(PerspectiveVO perspective, String visionOid) throws ServiceException, Exception {
		if (null == perspective || super.isBlank(perspective.getOid()) || super.isBlank(visionOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<PerspectiveVO> oldResult = this.perspectiveService.findObjectByOid(perspective);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		DefaultResult<VisionVO> vResult = this.visionService.findForSimple(visionOid); 
		if (vResult.getValue() == null) {
			throw new ServiceException(vResult.getSystemMessage().getValue());
		}
		VisionVO vision = vResult.getValue();
		perspective.setVisId( vision.getVisId() );
		perspective.setPerId(oldResult.getValue().getPerId());
		this.setStringValueMaxLength(perspective, "description", MAX_DESCRIPTION_LENGTH);
		perspective.setTarget( ItemTargetOrMaximumAndMinimalValue.get(perspective.getTarget()) );
		perspective.setMin( ItemTargetOrMaximumAndMinimalValue.get(perspective.getMin()) );		
		return this.perspectiveService.updateObject(perspective);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(PerspectiveVO perspective) throws ServiceException, Exception {
		if (null == perspective || super.isBlank(perspective.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}				
		DefaultResult<PerspectiveVO> oldResult = this.perspectiveService.findObjectByOid(perspective);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("perId", oldResult.getValue().getPerId());
		if ( this.objectiveService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		this.swotService.deleteForPerId(oldResult.getValue().getPerId());
		this.monitorItemScoreService.deleteForTypeClass(MonitorItemType.PERSPECTIVES, oldResult.getValue().getPerId());
		return this.perspectiveService.deleteObject(perspective);
	}

}
