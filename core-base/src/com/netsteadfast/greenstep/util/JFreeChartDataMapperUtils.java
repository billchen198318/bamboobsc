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
package com.netsteadfast.greenstep.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.UploadTypes;

public class JFreeChartDataMapperUtils {
	
	public static Map<String, Object> fillBarDataMap(String categoryLabel, String valueLabel, String title,
			List<String> names, List<Float> values, List<String> colors, int width, int height,
			boolean horizontal) throws Exception {
		if ( names == null || values == null || colors == null 
				|| names.size() != values.size() || names.size() != colors.size() 
				|| names.size() < 1 ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT) );
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("categoryLabel", categoryLabel);
		dataMap.put("valueLabel", valueLabel);
		dataMap.put("title", title);
		dataMap.put("names", names);
		dataMap.put("values", values);
		dataMap.put("colors", colors);
		dataMap.put("width", width);
		dataMap.put("height", height);
		dataMap.put("horizontal", ( horizontal ? YesNo.YES : YesNo.NO ) );
		return dataMap;
	}
	
	public static String createBarData(String categoryLabel, String valueLabel, String title,
			List<String> names, List<Float> values, List<String> colors, int width, int height, 
			boolean horizontal) throws Exception {
		Map<String, Object> barDataMap = fillBarDataMap(
				categoryLabel, valueLabel, title, names, values, colors, width, height,
				horizontal);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(barDataMap);		
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getChartData2Map(String uploadOid) throws Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		String jsonData = new String(UploadSupportUtils.getDataBytes(uploadOid));		
		ObjectMapper mapper = new ObjectMapper();
		return (Map<String, Object>)mapper.readValue(jsonData, HashMap.class);
	}
	
}
