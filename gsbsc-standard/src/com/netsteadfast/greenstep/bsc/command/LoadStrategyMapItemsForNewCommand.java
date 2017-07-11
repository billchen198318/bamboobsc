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

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.StrategyMapUtils;
import com.netsteadfast.greenstep.bsc.vo.StrategyMapItemsVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class LoadStrategyMapItemsForNewCommand extends BaseChainCommandSupport implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
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
		StrategyMapItemsVO mapItems = this.fillStrategyMapItems(vision);
		context.put("backgroundOid", StrategyMapUtils.createUpload(vision, 800, 600));
		this.setResult(context, mapItems);
		return false;
	}
	
	private StrategyMapItemsVO fillStrategyMapItems(VisionVO vision) throws Exception {
		StrategyMapItemsVO mapItems = new StrategyMapItemsVO();
		int w = 190;
		int top = 10;
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			int left = 10;
			for (ObjectiveVO objective : perspective.getObjectives()) {
				String idName = objective.getObjId();
				/**
				 * 1em = 16px
				 * 
				 * <div class="w" id="phone1">PHONE INTERVIEW 1<div class="ep"></div></div>
				 * #phone1 { left:35em; top:12em; width:7em; }
				 * 
				 */
				String div = "<div class=\"w\" id=\"" + idName + "\">" + objective.getName() + "<div class=\"ep\"></div></div>";
				String css = "#" + idName + " { left:" + left + "px; top:" + top + "px; width:" + w + "px; }";
				mapItems.getDiv().add(div);
				mapItems.getCss().add(css);
				left += (w+80);
			}
			top += 120;
		}
		return mapItems;
	}

}
