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
import com.netsteadfast.greenstep.bsc.model.MonitorItemType;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapConnsService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapNodesService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapService;
import com.netsteadfast.greenstep.bsc.service.ISwotService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IVisionLogicService;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMap;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapConns;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapNodes;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.StrategyMapConnsVO;
import com.netsteadfast.greenstep.vo.StrategyMapNodesVO;
import com.netsteadfast.greenstep.vo.StrategyMapVO;
import com.netsteadfast.greenstep.vo.SwotVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.VisionLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VisionLogicServiceImpl extends CoreBaseLogicService implements IVisionLogicService {
	protected Logger logger=Logger.getLogger(VisionLogicServiceImpl.class);	
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IStrategyMapService<StrategyMapVO, BbStrategyMap, String> strategyMapService;
	private IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String> strategyMapNodesService;
	private IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String> strategyMapConnsService;	
	private ISwotService<SwotVO, BbSwot, String> swotService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	
	public VisionLogicServiceImpl() {
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
	
	public IStrategyMapService<StrategyMapVO, BbStrategyMap, String> getStrategyMapService() {
		return strategyMapService;
	}

	@Autowired
	@Resource(name="bsc.service.StrategyMapService")
	@Required			
	public void setStrategyMapService(
			IStrategyMapService<StrategyMapVO, BbStrategyMap, String> strategyMapService) {
		this.strategyMapService = strategyMapService;
	}

	public IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String> getStrategyMapNodesService() {
		return strategyMapNodesService;
	}

	@Autowired
	@Resource(name="bsc.service.StrategyMapNodesService")
	@Required		
	public void setStrategyMapNodesService(
			IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String> strategyMapNodesService) {
		this.strategyMapNodesService = strategyMapNodesService;
	}

	public IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String> getStrategyMapConnsService() {
		return strategyMapConnsService;
	}

	@Autowired
	@Resource(name="bsc.service.StrategyMapConnsService")
	@Required	
	public void setStrategyMapConnsService(
			IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String> strategyMapConnsService) {
		this.strategyMapConnsService = strategyMapConnsService;
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

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<VisionVO> create(VisionVO vision) throws ServiceException, Exception {
		if (null == vision) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if ( !SimpleUtils.checkBeTrueOf_azAZ09(4, 14, vision.getVisId()) ) { // for import-mode from csv file VIS_ID is old(before id).			
			vision.setVisId( this.findForMaxVisId(SimpleUtils.getStrYMD("")) );
		}				
		return this.visionService.saveObject(vision);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<VisionVO> update(VisionVO vision) throws ServiceException, Exception {
		if (null == vision || super.isBlank(vision.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<VisionVO> oldResult = this.visionService.findObjectByOid(vision);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		oldResult.getValue().setContent(null);
		visionService.updateObject( oldResult.getValue() );
		vision.setVisId( oldResult.getValue().getVisId() );		
		return visionService.updateObject(vision);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(VisionVO vision) throws ServiceException, Exception {
		if (null == vision || super.isBlank(vision.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<VisionVO> oldResult = this.visionService.findObjectByOid(vision);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		vision = oldResult.getValue();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visId", vision.getVisId());
		if (this.perspectiveService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		this.deleteStrategyMap(vision);
		this.swotService.deleteForVisId(vision.getVisId());
		this.monitorItemScoreService.deleteForTypeClass(MonitorItemType.VISION, vision.getVisId());
		return visionService.deleteObject(vision);
	}	
	
	private void deleteStrategyMap(VisionVO vision) throws ServiceException, Exception {
		
		StrategyMapVO strategyMap = new StrategyMapVO();
		strategyMap.setVisId( vision.getVisId() );
		DefaultResult<StrategyMapVO> smResult = this.strategyMapService.findByUK(strategyMap);
		if ( smResult.getValue() == null ) { // 沒有 BB_STRATEGY_MAP 資料
			return;
		}
		strategyMap = smResult.getValue();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterOid", strategyMap.getOid());
		
		List<BbStrategyMapNodes> nodes = this.strategyMapNodesService.findListByParams(params);
		List<BbStrategyMapConns> conns = this.strategyMapConnsService.findListByParams(params);
		
		for (int i=0; nodes!=null && i<nodes.size(); i++) {
			strategyMapNodesService.delete( nodes.get(i) );
		}
		for (int i=0; conns!=null && i<conns.size(); i++) {
			strategyMapConnsService.delete( conns.get(i) );
		}
		
		this.strategyMapService.deleteObject(strategyMap);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)	
	@Override
	public String findForMaxVisId(String date) throws ServiceException, Exception {
		if (super.isBlank(date) || !NumberUtils.isCreatable(date) || date.length()!=8 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		String maxVisionId = this.visionService.findForMaxVisId(BscConstants.HEAD_FOR_VIS_ID+date); 
		if (StringUtils.isBlank(maxVisionId)) {
			return BscConstants.HEAD_FOR_VIS_ID + date + "001";
		}
		int maxSeq = Integer.parseInt( maxVisionId.substring(11, 14) ) + 1;
		if ( maxSeq > 999 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) + " over max seq 999!");
		}		
		return BscConstants.HEAD_FOR_VIS_ID + date + StringUtils.leftPad(String.valueOf(maxSeq), 3, "0");		
	}

}
