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

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.logic.IObjectiveLogicService;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.ObjectiveLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ObjectiveLogicServiceImpl extends CoreBaseLogicService implements IObjectiveLogicService {
	protected Logger logger=Logger.getLogger(ObjectiveLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService; 
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	
	public ObjectiveLogicServiceImpl() {
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

	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")
	@Required			
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
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
	public String findForMaxObjId(String date) throws ServiceException, Exception {
		if (super.isBlank(date) || !NumberUtils.isNumber(date) || date.length()!=8 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		String maxVisionId = this.objectiveService.findForMaxObjId(BscConstants.HEAD_FOR_OBJ_ID+date); 
		if (StringUtils.isBlank(maxVisionId)) {
			return BscConstants.HEAD_FOR_OBJ_ID + date + "001";
		}
		int maxSeq = Integer.parseInt( maxVisionId.substring(11, 14) ) + 1;
		if ( maxSeq > 999 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) + " over max seq 999!");
		}		
		return BscConstants.HEAD_FOR_OBJ_ID + date + StringUtils.leftPad(String.valueOf(maxSeq), 3, "0");	
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<ObjectiveVO> create(ObjectiveVO objective, String perspectiveOid) throws ServiceException, Exception {
		if (null == objective || super.isBlank(perspectiveOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		PerspectiveVO perspective = new PerspectiveVO();
		perspective.setOid(perspectiveOid);
		DefaultResult<PerspectiveVO> pResult = this.perspectiveService.findObjectByOid(perspective);
		if (pResult.getValue()==null) {
			throw new ServiceException(pResult.getSystemMessage().getValue());
		}
		perspective = pResult.getValue();
		objective.setPerId(perspective.getPerId());
		if ( !SimpleUtils.checkBeTrueOf_azAZ09(4, 14, objective.getObjId()) ) { // for import-mode from csv file OBJ_ID is old(before id).
			objective.setObjId( this.findForMaxObjId(SimpleUtils.getStrYMD("")) );
		}		
		this.setStringValueMaxLength(objective, "description", MAX_DESCRIPTION_LENGTH);
		objective.setTarget( ItemTargetOrMaximumAndMinimalValue.get(objective.getTarget()) );
		objective.setMin( ItemTargetOrMaximumAndMinimalValue.get(objective.getMin()) );
		return this.objectiveService.saveObject(objective);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<ObjectiveVO> update(ObjectiveVO objective, String perspectiveOid) throws ServiceException, Exception {
		if (null == objective || super.isBlank(objective.getOid()) || super.isBlank(perspectiveOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<ObjectiveVO> oldResult = this.objectiveService.findObjectByOid(objective);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		PerspectiveVO perspective = new PerspectiveVO();
		perspective.setOid(perspectiveOid);
		DefaultResult<PerspectiveVO> pResult = this.perspectiveService.findObjectByOid(perspective);
		if (pResult.getValue()==null) {
			throw new ServiceException(pResult.getSystemMessage().getValue());
		}
		perspective = pResult.getValue();
		objective.setObjId( oldResult.getValue().getObjId() );
		objective.setPerId(perspective.getPerId());
		this.setStringValueMaxLength(objective, "description", MAX_DESCRIPTION_LENGTH);
		objective.setTarget( ItemTargetOrMaximumAndMinimalValue.get(objective.getTarget()) );
		objective.setMin( ItemTargetOrMaximumAndMinimalValue.get(objective.getMin()) );		
		return this.objectiveService.updateObject(objective);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(ObjectiveVO objective) throws ServiceException, Exception {
		if (null == objective || super.isBlank(objective.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<ObjectiveVO> oldResult = this.objectiveService.findObjectByOid(objective);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objId", oldResult.getValue().getObjId());
		if (this.kpiService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		this.monitorItemScoreService.deleteForTypeClass(MonitorItemType.STRATEGY_OF_OBJECTIVES, oldResult.getValue().getObjId());
		return this.objectiveService.deleteObject(objective);
	}

}
