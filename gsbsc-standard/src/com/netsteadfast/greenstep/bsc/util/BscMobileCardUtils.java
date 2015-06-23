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
package com.netsteadfast.greenstep.bsc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.codehaus.jackson.map.ObjectMapper;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@SuppressWarnings("unchecked")
public class BscMobileCardUtils {
	private static IVisionService<VisionVO, BbVision, String> visionService;
	private static final String _RESOURCE_VISION_CARD = "META-INF/resource/mobile-card/vision-card.ftl";
	
	static {
		visionService = (IVisionService<VisionVO, BbVision, String>)AppContext.getBean("bsc.service.VisionService");
	}
	
	public static String getVisionCardUpload(String frequency, String startDate, 
			String endDate) throws ServiceException, Exception {
		List<VisionVO> visionScores = getVisionCard(frequency, startDate, endDate);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(visionScores);
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");		
	}
	
	public static List<VisionVO> getVisionCard(String frequency, String startDate, 
			String endDate) throws ServiceException, Exception {
		List<VisionVO> visionScores = new ArrayList<VisionVO>();
		Map<String, String> visions = visionService.findForMap(false);
		if (null == visions || visions.size() < 1) {
			return visionScores;
		}
		Context context = new ContextBase();
		context.put("startDate", startDate);
		context.put("endDate", endDate);		
		context.put("startYearDate", startDate);
		context.put("endYearDate", endDate);		
		context.put("frequency", frequency);
		context.put("dataFor", "all");
		context.put("orgId", BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);		
		for (Map.Entry<String, String> entry : visions.entrySet()) {
			String visionOid = entry.getKey();
			context.put("visionOid", visionOid);
			SimpleChain chain = new SimpleChain();
			ChainResultObj resultObj = chain.getResultFromResource("performanceScoreChain", context);
			BscStructTreeObj treeObj = (BscStructTreeObj)resultObj.getValue();
			for (int i=0; treeObj.getVisions()!=null && i<treeObj.getVisions().size(); i++) {
				visionScores.add( treeObj.getVisions().get(i) );
			}
		}
		return visionScores;
	}
	
	public static String getVisionCardContent(VisionVO vision) throws ServiceException, Exception {
		String content = "";
		int perspectiveSize = vision.getPerspectives().size();
		int objectiveSize = 0;
		int kpiSize = 0;
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			objectiveSize += perspective.getObjectives().size();
			List<ObjectiveVO> objectives = perspective.getObjectives();
			for (ObjectiveVO objective : objectives) {
				kpiSize += objective.getKpis().size();
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vision", vision);
		paramMap.put("perspectiveSize", perspectiveSize);
		paramMap.put("objectiveSize", objectiveSize);
		paramMap.put("kpiSize", kpiSize);
		content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				BscMobileCardUtils.class.getClassLoader(), 
				_RESOURCE_VISION_CARD, 
				paramMap);		
		return content;
	}
	
}
