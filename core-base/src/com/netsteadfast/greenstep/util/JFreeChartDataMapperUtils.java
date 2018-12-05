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
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.UploadTypes;

public class JFreeChartDataMapperUtils {
	protected static Logger logger = Logger.getLogger(JFreeChartDataMapperUtils.class);
	
	private static String createUploadData(Map<String, Object> dataMap) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(dataMap);		
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");		
	}
	
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
		return createUploadData(barDataMap);
	}
	
	public static String createPieData(String title,
			List<String> names, List<Float> values, List<String> colors, int width, int height) throws Exception {
		Map<String, Object> barDataMap = fillPieDataMap(
				title, names, values, colors, width, height);
		return createUploadData(barDataMap);
	}	
	
	public static Map<String, Object> fillPieDataMap(String title, List<String> names, List<Float> values, 
			List<String> colors, int width, int height) throws Exception {
		if ( names == null || values == null || colors == null 
				|| names.size() != values.size() || names.size() != colors.size() 
				|| names.size() < 1 ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT) );
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", title);
		dataMap.put("names", names);
		dataMap.put("values", values);
		dataMap.put("colors", colors);
		dataMap.put("width", width);
		dataMap.put("height", height);
		return dataMap;
	}	
	
	public static String createMeterData(String title, float value, int lowerBound, int upperBound, 
			int width, int height) throws Exception {
		Map<String, Object> meterDataMap = fillMeterDataMap(title, value, lowerBound, upperBound, 
				width, height);
		return createUploadData(meterDataMap);
	}
	
	public static Map<String, Object> fillMeterDataMap(String title, float value, int lowerBound, int upperBound, 
			int width, int height) throws Exception {
		if (null == title) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT) );
		}
		if (lowerBound >= upperBound) {
			logger.warn("fillMeterDataMap variable lowerBound(args): " + lowerBound);
			logger.warn("fillMeterDataMap variable upperBound(args): " + upperBound);
			lowerBound = 0;
			upperBound = 100;
			logger.warn("fillMeterDataMap variable lowerBound(replace-to): " + lowerBound);
			logger.warn("fillMeterDataMap variable upperBound(replace-to): " + upperBound);			
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", title);		
		dataMap.put("value", String.valueOf(value));
		dataMap.put("lowerBound", lowerBound);
		dataMap.put("upperBound", upperBound);		
		dataMap.put("width", width);
		dataMap.put("height", height);		
		return dataMap;
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
