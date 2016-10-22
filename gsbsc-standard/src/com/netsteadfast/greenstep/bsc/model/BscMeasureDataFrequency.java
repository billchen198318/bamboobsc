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
package com.netsteadfast.greenstep.bsc.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class BscMeasureDataFrequency {
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private static Map<String, String> frequencyMap = new LinkedHashMap<String, String>();
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)
				AppContext.getBean("core.service.SysCodeService");		
	}	
	
	private static void loadMapData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", CODE_TYPE);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("code", "ASC");
		try {
			List<TbSysCode> codes = sysCodeService.findListByParams(params, null, orderParams);
			for (TbSysCode code : codes) {
				if (code.getCode().equals(FREQUENCY_DAY_CODE)) {
					frequencyMap.put(FREQUENCY_DAY, code.getName());
				}
				if (code.getCode().equals(FREQUENCY_WEEK_CODE)) {
					frequencyMap.put(FREQUENCY_WEEK, code.getName());
				}
				if (code.getCode().equals(FREQUENCY_MONTH_CODE)) {
					frequencyMap.put(FREQUENCY_MONTH, code.getName());
				}				
				if (code.getCode().equals(FREQUENCY_QUARTER_CODE)) {
					frequencyMap.put(FREQUENCY_QUARTER, code.getName());
				}		
				if (code.getCode().equals(FREQUENCY_HALF_OF_YEAR_CODE)) {
					frequencyMap.put(FREQUENCY_HALF_OF_YEAR, code.getName());
				}
				if (code.getCode().equals(FREQUENCY_YEAR_CODE)) {
					frequencyMap.put(FREQUENCY_YEAR, code.getName());
				}				
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static Map<String, String> getFrequencyMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (frequencyMap.size() < 1) {
			loadMapData();
		}
		dataMap.putAll(frequencyMap);
		return dataMap;
	}
	
	public static String getQueryDate(String date, String frequency) {
		String queryDate = date.replaceAll("-", "").replaceAll("/", "");		
		if (FREQUENCY_DAY.equals(frequency) || FREQUENCY_WEEK.equals(frequency) ) {
			queryDate = queryDate.substring(0, 6);
		}
		if (FREQUENCY_MONTH.equals(frequency) || FREQUENCY_QUARTER.equals(frequency) 
				|| FREQUENCY_HALF_OF_YEAR.equals(frequency) || FREQUENCY_YEAR.equals(frequency) ) {
			queryDate = queryDate.substring(0, 4);
		}		
		return queryDate;
	}
	
	/**
	 * TB_SYS_CODE.TYPE
	 */
	public final static String CODE_TYPE = "KMD";			
	
	/**
	 * 日
	 */
	public static final String FREQUENCY_DAY = "1";
	public static final String FREQUENCY_DAY_CODE = "KMD_MEASURE01";
	
	
	/**
	 * 周
	 */
	public static final String FREQUENCY_WEEK = "2";
	public static final String FREQUENCY_WEEK_CODE = "KMD_MEASURE02";
	
	/**
	 * 月
	 */
	public static final String FREQUENCY_MONTH = "3";
	public static final String FREQUENCY_MONTH_CODE = "KMD_MEASURE03";
	
	/**
	 * 季
	 */
	public static final String FREQUENCY_QUARTER = "4";
	public static final String FREQUENCY_QUARTER_CODE = "KMD_MEASURE04";
	
	/**
	 * 半年
	 */
	public static final String FREQUENCY_HALF_OF_YEAR = "5";
	public static final String FREQUENCY_HALF_OF_YEAR_CODE = "KMD_MEASURE05";
	
	/**
	 * 年
	 */
	public static final String FREQUENCY_YEAR = "6";
	public static final String FREQUENCY_YEAR_CODE = "KMD_MEASURE06";
	
	/**
	 * 給報表查詢時, 在 bb_measure_data 正確的 "月", "周" 的日期起迄
	 * yyyyMMdd 
	 * 2013/01/01
	 * 20130101
	 * 
	 * @param yyyyMMdd
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getWeekOrMonthStartEnd(String frequency, String startDate, String endDate) throws Exception {
		if (!BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) && !BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency)) {
			throw new java.lang.IllegalArgumentException("frequency error.");
		}
		Map<String, String> dateMap=new HashMap<String, String>();
		if (!SimpleUtils.isDate(startDate) || !SimpleUtils.isDate(endDate)) {
			throw new java.lang.IllegalArgumentException("startDate/endDate error.");
		}
		if (BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency)) {
			int firstDay = Integer.parseInt( startDate.substring(startDate.length()-2, startDate.length()) );
			int endDay = Integer.parseInt( endDate.substring(endDate.length()-2, endDate.length()) );
			if (firstDay>=1 && firstDay<8) {
				firstDay = 1;
			}
			if (firstDay>=8 && firstDay<15) {
				firstDay = 8;
			}
			if (firstDay>=15 && firstDay<22) {
				firstDay = 15;
			}
			if (firstDay>=22) { 
				firstDay = 22;
			}
			if (endDay>=1 && endDay<8) {
				endDay = 7;
			}
			if (endDay>=8 && endDay<15) {
				endDay = 14;
			}
			if (endDay>=15 && endDay<22) {
				endDay = 21;
			}
			if (endDay>=22) { 
				endDay = SimpleUtils.getMaxDayOfMonth( 
						Integer.parseInt(endDate.substring(0, 4)), 
						Integer.parseInt(endDate.substring(5, 7)) );
			}
			String newStartDate = startDate.substring(0, startDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(firstDay), 2, "0");
			String newEndDate = endDate.substring(0, endDate.length()-2)
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");
			dateMap.put("startDate", newStartDate);
			dateMap.put("endDate", newEndDate);
		}
		if (BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency)) {
			int endDay = SimpleUtils.getMaxDayOfMonth( Integer.parseInt(endDate.substring(0, 4)), 
					Integer.parseInt(endDate.substring(5, 7)) );
			String newStartDate = startDate.substring(0, startDate.length()-2) + "01";
			String newEndDate = endDate.substring(0, endDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");			
			dateMap.put("startDate", newStartDate);
			dateMap.put("endDate", newEndDate);	
		}
		return dateMap;		
	}	
	
}
