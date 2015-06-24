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
import com.netsteadfast.greenstep.base.model.DefaultResult;
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
	
	public static String getVisionCardUpload(VisionVO vision) throws ServiceException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(vision);
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");			
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
		context.put("startYearDate", startDate.substring(0, 4));
		context.put("endYearDate", endDate.substring(0, 4));		
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
				VisionVO vision = treeObj.getVisions().get(i);
				vision.setContent( " ".getBytes() );
				DefaultResult<VisionVO> vResult = visionService.findObjectByOid(vision);
				if (vResult.getValue()!=null) { // 計算分數chain 取出的vision資料沒有放 content 欄位, 但這邊要用到, 所以取出content欄位
					vision.setContent( new String(vResult.getValue().getContent(), Constants.BASE_ENCODING).getBytes() );
				}
				visionScores.add( vision );
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
		BscReportPropertyUtils.loadData();		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("perspectiveTitle", BscReportPropertyUtils.getPerspectiveTitle());
		paramMap.put("objectiveTitle", BscReportPropertyUtils.getObjectiveTitle());
		paramMap.put("kpiTitle", BscReportPropertyUtils.getKpiTitle());
		paramMap.put("backgroundColor", BscReportPropertyUtils.getBackgroundColor());
		paramMap.put("fontColor", BscReportPropertyUtils.getFontColor());
		paramMap.put("vision", vision);
		paramMap.put("visionContent", new String(vision.getContent(), Constants.BASE_ENCODING) );
		paramMap.put("perspectiveSize", perspectiveSize);
		paramMap.put("objectiveSize", objectiveSize);
		paramMap.put("kpiSize", kpiSize);
		paramMap.put("percentage", getPercentage(vision.getScore(), 100f));
		content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				BscMobileCardUtils.class.getClassLoader(), 
				_RESOURCE_VISION_CARD, 
				paramMap);		
		return content;
	}
	
	private static String getPercentage(float score, float compareValue) {
		int percentage=0;
		if (score<=compareValue && score!=0.0f && compareValue!=0.0f) {
			float p=score/compareValue * 100;
			percentage = Math.round(p);
		}
		return String.valueOf(percentage);
	}
	
}
