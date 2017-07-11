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
package com.netsteadfast.greenstep.bsc.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapConnsService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapNodesService;
import com.netsteadfast.greenstep.bsc.service.IStrategyMapService;
import com.netsteadfast.greenstep.bsc.util.StrategyMapUtils;
import com.netsteadfast.greenstep.bsc.vo.StrategyMapItemsVO;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMap;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapConns;
import com.netsteadfast.greenstep.po.hbm.BbStrategyMapNodes;
import com.netsteadfast.greenstep.vo.StrategyMapConnsVO;
import com.netsteadfast.greenstep.vo.StrategyMapNodesVO;
import com.netsteadfast.greenstep.vo.StrategyMapVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class LoadStrategyMapItemsForRecCommand extends BaseChainCommandSupport implements Command {	
	private IStrategyMapService<StrategyMapVO, BbStrategyMap, String> strategyMapService;
	private IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String> strategyMapNodesService;
	private IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String> strategyMapConnsService;
	
	@SuppressWarnings("unchecked")
	public LoadStrategyMapItemsForRecCommand() {
		super();
		strategyMapService = (IStrategyMapService<StrategyMapVO, BbStrategyMap, String>)
				AppContext.getBean("bsc.service.StrategyMapService");
		strategyMapNodesService = (IStrategyMapNodesService<StrategyMapNodesVO, BbStrategyMapNodes, String>)
				AppContext.getBean("bsc.service.StrategyMapNodesService");
		strategyMapConnsService = (IStrategyMapConnsService<StrategyMapConnsVO, BbStrategyMapConns, String>)
				AppContext.getBean("bsc.service.StrategyMapConnsService");		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws ServiceException, Exception {
		if ( this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);	
		String visionOid = (String)context.get("visionOid");
		VisionVO vision = null;
		for (VisionVO obj : treeObj.getVisions()) {
			if ( obj.getOid().equals(visionOid) ) {
				vision = obj;
			}
		}
		StrategyMapItemsVO mapItems = this.fillStrategyMapItems(vision, context);
		context.put("backgroundOid", StrategyMapUtils.createUpload(vision, 800, 600));
		this.setResult(context, mapItems);		
		return false;
	}
	
	private StrategyMapItemsVO fillStrategyMapItems(VisionVO vision, Context context) throws ServiceException, Exception {
		StrategyMapItemsVO mapItems = new StrategyMapItemsVO();
		int w = 190;
		StrategyMapVO map = new StrategyMapVO();
		map.setVisId( vision.getVisId() );
		DefaultResult<StrategyMapVO> smResult = this.strategyMapService.findByUK(map);
		if ( smResult.getValue() == null ) {
			this.setMessage(context, smResult.getSystemMessage().getValue() );
			return mapItems;
		}
		map = smResult.getValue();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterOid", map.getOid());
		
		List<BbStrategyMapNodes> nodes = this.strategyMapNodesService.findListByParams(params);
		List<BbStrategyMapConns> conns = this.strategyMapConnsService.findListByParams(params);
		for (int i=0; nodes!=null && i<nodes.size(); i++) {
			BbStrategyMapNodes node = nodes.get(i);
			String div = "<div class=\"w\" id=\"" + node.getId() + "\">" + node.getText() + "<div class=\"ep\"></div></div>";
			String css = "#" + node.getId() + " { left:" + node.getPositionX() + "px; top:" + node.getPositionY() + "px; width:" + w + "px; }";
			mapItems.getDiv().add(div);
			mapItems.getCss().add(css);			
		}
		for (int i=0; conns!=null && i<conns.size(); i++) {
			BbStrategyMapConns conn = conns.get(i);
			mapItems.getCon().add("{ source:\"" + conn.getSourceId() + "\", target:\"" + conn.getTargetId() + "\" }");
		}		
		return mapItems;
	}

}
