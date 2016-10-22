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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class KpiReportCoffeeChartJsonDataCommand extends BaseChainCommandSupport implements Command {
	
	public KpiReportCoffeeChartJsonDataCommand() {
		super();
	}

	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}	
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		this.setJsonData(context, treeObj);
		return false;
	}
	
	private void setJsonData(Context context, BscStructTreeObj treeObj) throws Exception {
		if (treeObj == null || treeObj.getVisions() == null || treeObj.getVisions().size() != 1) {
			return;
		}
		List<Map<String, Object>> rootList = new ArrayList<Map<String, Object>>();
		Map<String, Object> rootDataMap = new HashMap<String, Object>(); 
		List<Map<String, Object>> perspectivesDatas = new ArrayList<Map<String, Object>>();
		List<VisionVO> visions = treeObj.getVisions();
		VisionVO vision = visions.get(0);
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			Map<String, Object> perspectiveDataMap = new HashMap<String, Object>(); 
			List<Map<String, Object>> perspectiveChildren = new ArrayList<Map<String, Object>>();
			perspectiveDataMap.put("name", this.getName(perspective.getName(), perspective.getScore()));
			perspectiveDataMap.put("children", perspectiveChildren);
			perspectiveDataMap.put("colour", perspective.getBgColor());
			perspectiveDataMap.put("fontColor", perspective.getFontColor());
			perspectiveDataMap.put("score", perspective.getScore());
			perspectivesDatas.add(perspectiveDataMap);
			
			for (ObjectiveVO objective : perspective.getObjectives()) {
				Map<String, Object> objectiveDataMap = new HashMap<String, Object>();
				List<Map<String, Object>> objectiveChildren = new ArrayList<Map<String, Object>>();
				objectiveDataMap.put("name", this.getName(objective.getName(), objective.getScore()));
				objectiveDataMap.put("children", objectiveChildren);
				objectiveDataMap.put("colour", objective.getBgColor());
				objectiveDataMap.put("fontColor", objective.getFontColor());
				objectiveDataMap.put("score", objective.getScore());
				perspectiveChildren.add(objectiveDataMap);
				
				for (KpiVO kpi : objective.getKpis()) {
					Map<String, Object> indicatorsDataMap = new HashMap<String, Object>();
					indicatorsDataMap.put("name", this.getName(kpi.getName(), kpi.getScore()));
					indicatorsDataMap.put("colour", kpi.getBgColor());
					indicatorsDataMap.put("fontColor", kpi.getFontColor());
					indicatorsDataMap.put("score", kpi.getScore());
					objectiveChildren.add(indicatorsDataMap);
				}
				
			}
			
		}
		rootDataMap.put("name", this.getName(vision.getTitle(), vision.getScore()));
		rootDataMap.put("children", perspectivesDatas);
		rootDataMap.put("colour", vision.getBgColor());
		rootDataMap.put("fontColor", vision.getFontColor());
		rootDataMap.put("score", vision.getScore());		
		rootList.add(rootDataMap);
		String jsonDataStr = ((JSONArray)JSONSerializer.toJSON(rootList)).toString();
		this.setResult(context, jsonDataStr);
	}
	
	private String getName(String name, float score) {
		return name + "(" + BscReportSupportUtils.parse(score) + ")";
	}

}
