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
import java.util.List;

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
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.logic.IWeightLogicService;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.WeightLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class WeightLogicServiceImpl extends CoreBaseLogicService implements IWeightLogicService {
	protected Logger logger=Logger.getLogger(WeightLogicServiceImpl.class);	
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	public WeightLogicServiceImpl() {
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
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> update(List<PerspectiveVO> perspectives,
			List<ObjectiveVO> objectives, List<KpiVO> kpis) throws ServiceException, Exception {
		if (perspectives==null || perspectives.size()<1 || objectives==null || objectives.size()<1 
				|| kpis==null || kpis.size()<1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		for (PerspectiveVO perspective : perspectives) {
			BbPerspective entity = this.perspectiveService.findByPKng(perspective.getOid());
			entity.setWeight( perspective.getWeight() );
			this.perspectiveService.update(entity);
		}
		for (ObjectiveVO objective : objectives) {
			BbObjective entity = this.objectiveService.findByPKng(objective.getOid());
			entity.setWeight( objective.getWeight() );
			this.objectiveService.update(entity);
		}
		for (KpiVO kpi : kpis) {
			BbKpi entity = this.kpiService.findByPKng(kpi.getOid());
			entity.setWeight( kpi.getWeight() );
			this.kpiService.update(entity);
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );
		result.setValue(Boolean.TRUE);
		return result;
	}

}
