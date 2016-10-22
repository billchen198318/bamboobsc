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
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.vo.SwotDataVO;
import com.netsteadfast.greenstep.util.TemplateUtils;

public class SwotBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/swot-body.ftl";
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof SwotDataVO) ) {
			return false;
		}		
		SwotDataVO swotData = (SwotDataVO)this.getResult(context);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("issues", swotData.getIssues());
		parameter.put("vision", swotData.getVision());
		parameter.put("organization", swotData.getOrganization());
		parameter.put("strengthsName", String.valueOf(context.get("strengthsName")));
		parameter.put("weaknessesName", String.valueOf(context.get("weaknessesName")));
		parameter.put("opportunitiesName", String.valueOf(context.get("opportunitiesName")));
		parameter.put("threatsName", String.valueOf(context.get("threatsName")));
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				SwotBodyCommand.class.getClassLoader(), 
				templateResource, 
				parameter);
		this.setResult(context, content );		
		return false;
	}

}
