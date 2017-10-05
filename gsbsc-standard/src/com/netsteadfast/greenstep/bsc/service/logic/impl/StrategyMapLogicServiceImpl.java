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

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapConnsService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapNodesService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IStrategyMapLogicService;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMap;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapConns;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapNodes;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.StrategyMapConnsVO;
import com.netsteadfast.greenstep.vo.StrategyMapNodesVO;
import com.netsteadfast.greenstep.vo.StrategyMapVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.StrategyMapLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class StrategyMapLogicServiceImpl extends CoreBaseLogicService implements IStrategyMapLogicService {
	protected Logger logger=Logger.getLogger(StrategyMapLogicServiceImpl.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IStrategyMapService<StrategyMapVO, BbStrategyMap, String> strategyMapService;
	private IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String> strategyMapNodesService;
	private IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String> strategyMapConnsService;
	
	public StrategyMapLogicServiceImpl() {
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

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> create(String visionOid, Map<String, Object> jsonData) throws ServiceException, Exception {
		this.delete(visionOid);
		
		VisionVO vision = new VisionVO();
		vision.setOid(visionOid);
		DefaultResult<VisionVO> vResult = this.visionService.findObjectByOid(vision);
		if (vResult.getValue()==null) { // 沒 TB_VISION 資料, 不用清 STRATEGY MAP 			
			throw new ServiceException( vResult.getSystemMessage().getValue() );
		}		
		vision = vResult.getValue();
		
		StrategyMapVO strategyMap = new StrategyMapVO();
		strategyMap.setVisId( vision.getVisId() );
		DefaultResult<StrategyMapVO> smResult = strategyMapService.saveObject(strategyMap);
		if ( smResult.getValue() == null ) {
			throw new ServiceException( smResult.getSystemMessage().getValue() );
		}
		strategyMap = smResult.getValue();		
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS) ) );
		this.saveNodesAndConnections(strategyMap, jsonData);		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void saveNodesAndConnections(StrategyMapVO strategyMap, 
			Map<String, Object> jsonData) throws ServiceException, Exception {
		
		List<Map<String, Object>> connections = (List<Map<String, Object>>)jsonData.get("connections");
		List<Map<String, Object>> nodes = (List<Map<String, Object>>)jsonData.get("nodes"); 
		
		for (int i=0; connections!=null && i<connections.size(); i++) {
			Map<String, Object> data = connections.get(i);
			String connectionId = (String)data.get("connectionId");
			String sourceId = (String)data.get("sourceId");
			String targetId = (String)data.get("targetId");
			if ( StringUtils.isBlank(connectionId) || StringUtils.isBlank(sourceId) 
					|| StringUtils.isBlank(targetId) ) {
				continue;
			}
			StrategyMapConnsVO mapConns = new StrategyMapConnsVO();
			mapConns.setMasterOid(strategyMap.getOid());
			mapConns.setConnectionId(connectionId);
			mapConns.setSourceId(sourceId);
			mapConns.setTargetId(targetId);
			this.strategyMapConnsService.saveIgnoreUK(mapConns);
		}
		
		for (int i=0; nodes!=null && i<nodes.size(); i++) {
			Map<String, Object> data = nodes.get(i);
			String id = (String)data.get("id");
			String text = (String)data.get("text");
			String positionX = String.valueOf( data.get("positionX") );
			String positionY = String.valueOf( data.get("positionY") );
			if ( StringUtils.isBlank(id) || StringUtils.isBlank(text) 
					|| !NumberUtils.isCreatable(positionX) || !NumberUtils.isCreatable(positionY) ) {
				continue;
			}
			StrategyMapNodesVO mapNodes = new StrategyMapNodesVO();
			mapNodes.setMasterOid(strategyMap.getOid());
			mapNodes.setId(id);
			mapNodes.setText(text);
			mapNodes.setPositionX( NumberUtils.toInt(positionX) );
			mapNodes.setPositionY( NumberUtils.toInt(positionY) );
			this.strategyMapNodesService.saveIgnoreUK(mapNodes);
		}
		
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(String visionOid) throws ServiceException, Exception {
		if (super.isBlank(visionOid) || super.isNoSelectId(visionOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.DELETE_SUCCESS) ) );
		VisionVO vision = new VisionVO();
		vision.setOid(visionOid);
		DefaultResult<VisionVO> vResult = this.visionService.findObjectByOid(vision);
		if (vResult.getValue()==null) { // 沒 TB_VISION 資料, 不用清 STRATEGY MAP 			
			return result;
		}
		vision = vResult.getValue();
		StrategyMapVO strategyMap = new StrategyMapVO();
		strategyMap.setVisId(vision.getVisId());
		DefaultResult<StrategyMapVO> smResult = this.strategyMapService.findByUK(strategyMap);
		if ( smResult.getValue() == null ) { // 沒有 BB_STRATEGY_MAP 資料
			return result;
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
		
		return strategyMapService.deleteObject(strategyMap);
	}

}
