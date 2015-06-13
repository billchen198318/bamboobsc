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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class WeightBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/weight-settings-body.ftl";
	private static final BigDecimal MAX_WEIGHT_VALUE = new BigDecimal("100.00");
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		if ( YesNo.YES.equals(context.get("autoAllocation")) ) {
			this.autoAllocation(treeObj);
		}		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("treeObj", treeObj);
		parameter.put("weightName", String.valueOf(context.get("weightName")));
		/*
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("resourceTemplate", 
				TemplateUtils.getResourceSrc(WeightBodyCommand.class.getClassLoader(), templateResource) );
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(templateLoader);
		Template template = cfg.getTemplate("resourceTemplate", "utf-8");
		Writer out = new StringWriter();
		template.process(parameter, out);				
		this.setResult(context, out.toString() );
		*/
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				WeightBodyCommand.class.getClassLoader(), 
				templateResource, 
				parameter);
		this.setResult(context, content );
		return false;
	}
	
	private void autoAllocation(BscStructTreeObj treeObj) throws Exception {
		int scale = 2;
		for (VisionVO vision : treeObj.getVisions()) {
			for (int px=0; px<vision.getPerspectives().size(); px++) {
				PerspectiveVO perspective = vision.getPerspectives().get(px);
				int round = BigDecimal.ROUND_DOWN;
				if ( (px+1) == vision.getPerspectives().size() ) {
					round = BigDecimal.ROUND_UP;
				}
				perspective.setWeight( 
						MAX_WEIGHT_VALUE.divide(new BigDecimal(vision.getPerspectives().size()), scale, round) );
				for (int ox=0; ox<perspective.getObjectives().size(); ox++) {
					ObjectiveVO objective = perspective.getObjectives().get(ox);
					round = BigDecimal.ROUND_DOWN;
					if ( (ox+1) == perspective.getObjectives().size() ) {
						round = BigDecimal.ROUND_UP;
					}
					objective.setWeight( 
							MAX_WEIGHT_VALUE.divide(new BigDecimal(perspective.getObjectives().size()), scale, round) );					
					for (int kx=0; kx<objective.getKpis().size(); kx++) {
						KpiVO kpi = objective.getKpis().get(kx);
						round = BigDecimal.ROUND_DOWN;
						if ( (kx+1) == objective.getKpis().size() ) {
							round = BigDecimal.ROUND_UP;
						}						
						kpi.setWeight( 
								MAX_WEIGHT_VALUE.divide(new BigDecimal(objective.getKpis().size()), scale, round) );						
					}
					
				}
			}
		}
	}

}
