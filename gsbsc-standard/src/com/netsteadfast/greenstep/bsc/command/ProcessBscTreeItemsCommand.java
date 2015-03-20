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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class ProcessBscTreeItemsCommand extends BaseChainCommandSupport implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		String host = StringUtils.defaultString( (String)context.get("http") ) + ApplicationSiteUtils.getHost(Constants.getMainSystem()) 
				+ "/" + ApplicationSiteUtils.getContextPathFromMap(Constants.getMainSystem()) + "/";
		String iconImg = IconUtils.getMenuIcon(host, "STAR");		
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		List<Map<String, Object>> items = new LinkedList<Map<String, Object>>();
		for (VisionVO vision : treeObj.getVisions()) {
			Map<String, Object> visionDataMap = new HashMap<String, Object>();
			visionDataMap.put("type", "parent");
			visionDataMap.put("name", vision.getTitle() );
			visionDataMap.put("id", BscConstants.KPI_TREE_NOT_ITEM + vision.getOid() ); 
			items.add(visionDataMap);
			List<Map<String, Object>> perspectiveItems = new LinkedList<Map<String, Object>>();
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				Map<String, Object> perspectiveDataMap = new HashMap<String, Object>();
				perspectiveDataMap.put("type", "parent");
				perspectiveDataMap.put("name", perspective.getName() );
				perspectiveDataMap.put("id", BscConstants.KPI_TREE_NOT_ITEM + perspective.getOid() );
				perspectiveItems.add(perspectiveDataMap);
				visionDataMap.put("children", perspectiveItems);
				List<Map<String, Object>> objectivesStrategyList = new LinkedList<Map<String, Object>>();
				for (ObjectiveVO objective : perspective.getObjectives()) {
					Map<String, Object> objectiveMap = new HashMap<String, Object>();
					objectiveMap.put("type", "parent");
					objectiveMap.put("name", objective.getName() );
					objectiveMap.put("id", BscConstants.KPI_TREE_NOT_ITEM + objective.getOid() );
					objectivesStrategyList.add(objectiveMap);
					perspectiveDataMap.put("children", objectivesStrategyList);
					List<Map<String, Object>> kpiList = new LinkedList<Map<String, Object>>();
					for (KpiVO kpi : objective.getKpis()) {
						Map<String, Object> kpiMap = new HashMap<String, Object>();
						kpiMap.put("type", "Leaf");
						kpiMap.put("name", iconImg + kpi.getName() );
						kpiMap.put("id", kpi.getOid());	
						kpiList.add(kpiMap);
						objectiveMap.put("children", kpiList);
					}
				}
			}
		}
		this.setResult(context, items);
		if (null==items || items.size() < 1 ) {
			this.setMessage(context, SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		return false;
	}

}
