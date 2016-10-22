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

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class BscKpiCode implements java.io.Serializable {
	private static final long serialVersionUID = -6237662357675810151L;
	private static final String[][] QUASI_RANGE_TABLE = new String[][] {
		{ "0", 		"0%" },
		{ "1", 		"1%" },
		{ "3", 		"3%" },
		{ "5", 		"5%" },
		{ "7", 		"7%" },
		{ "9", 		"9%" },
		{ "10", 	"10%" },
		{ "15", 	"15%" },
		{ "20", 	"20%" },
		{ "25", 	"25%" },
		{ "30", 	"30%" },
		{ "35", 	"35%" },
		{ "40", 	"40%" },
		{ "45", 	"45%" },
		{ "50", 	"50%" },
	};
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private static Map<String, String> managementMap = new LinkedHashMap<String, String>();
	private static Map<String, String> compareTypeMap = new LinkedHashMap<String, String>();
	@Deprecated
	private static Map<String, String> calMap = new LinkedHashMap<String, String>();
	private static Map<String, String> dataTypeMap = new LinkedHashMap<String, String>();
	private static Map<String, String> quasiRangeMap = new LinkedHashMap<String, String>();
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)
				AppContext.getBean("core.service.SysCodeService");
		for (int i=0; i<QUASI_RANGE_TABLE.length; i++) {
			quasiRangeMap.put(QUASI_RANGE_TABLE[i][0], QUASI_RANGE_TABLE[i][1]);
		}
	}	
	
	private static void fillDataMap(TbSysCode code) {
		if (MANAGEMENT_BIGGER_IS_BETTER_CODE.equals(code.getCode()) ) {
			managementMap.put(MANAGEMENT_BIGGER_IS_BETTER, code.getName());
		}
		if (MANAGEMENT_SMALLER_IS_BETTER_CODE.equals(code.getCode()) ) {
			managementMap.put(MANAGEMENT_SMALLER_IS_BETTER, code.getName());
		}	
		if (MANAGEMENT_QUASI_IS_BETTER_CODE.equals(code.getCode()) ) {
			managementMap.put(MANAGEMENT_QUASI_IS_BETTER, code.getName());
		}				
		
		if (COMPARE_TYPE_TARGET_CODE.equals(code.getCode()) ) {
			compareTypeMap.put(COMPARE_TYPE_TARGET, code.getName());
		}
		if (COMPARE_TYPE_MIN_CODE.equals(code.getCode()) ) {
			compareTypeMap.put(COMPARE_TYPE_MIN, code.getName());
		}		
		
		if (CAL_AVERAGE_CODE.equals(code.getCode()) ) {
			calMap.put(CAL_AVERAGE, code.getName());
		}
		if (CAL_TOTAL_CODE.equals(code.getCode()) ) {
			calMap.put(CAL_TOTAL, code.getName());
		}		
		
		if (DATA_TYPE_DEPARTMENT_CODE.equals(code.getCode()) ) {
			dataTypeMap.put(DATA_TYPE_DEPARTMENT, code.getName());
		}
		if (DATA_TYPE_PERSONAL_CODE.equals(code.getCode()) ) {
			dataTypeMap.put(DATA_TYPE_PERSONAL, code.getName());
		}
		if (DATA_TYPE_BOTH_CODE.equals(code.getCode()) ) {
			dataTypeMap.put(DATA_TYPE_BOTH, code.getName());
		}		
		
	}
	
	private static void checkDataMap() {
		if (managementMap.size() != 3) {
			managementMap.clear();
			managementMap.put(MANAGEMENT_BIGGER_IS_BETTER, MANAGEMENT_BIGGER_IS_BETTER);
			managementMap.put(MANAGEMENT_SMALLER_IS_BETTER, MANAGEMENT_SMALLER_IS_BETTER);
			managementMap.put(MANAGEMENT_QUASI_IS_BETTER, MANAGEMENT_QUASI_IS_BETTER);
		}		
		if (compareTypeMap.size() != 2) {
			compareTypeMap.clear();
			compareTypeMap.put(COMPARE_TYPE_TARGET, COMPARE_TYPE_TARGET);
			compareTypeMap.put(COMPARE_TYPE_MIN, COMPARE_TYPE_MIN);
		}
		if (calMap.size() != 2) {
			calMap.clear();
			calMap.put(CAL_AVERAGE, CAL_AVERAGE);
			calMap.put(CAL_TOTAL, CAL_TOTAL);
		}
		if (dataTypeMap.size() != 3) {
			dataTypeMap.clear();
			dataTypeMap.put(DATA_TYPE_DEPARTMENT, DATA_TYPE_DEPARTMENT);
			dataTypeMap.put(DATA_TYPE_PERSONAL, DATA_TYPE_PERSONAL);
			dataTypeMap.put(DATA_TYPE_BOTH, DATA_TYPE_BOTH);
		}		
	}
	
	private static void loadMapData() {
		managementMap.clear();
		compareTypeMap.clear();
		calMap.clear();
		dataTypeMap.clear();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", CODE_TYPE);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("code", "ASC");
		try {
			List<TbSysCode> codes = sysCodeService.findListByParams(params, null, orderParams);
			for (TbSysCode code : codes) {
				fillDataMap(code);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkDataMap();
	}			
	
	public static Map<String, String> getManagementMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (managementMap.size() < 1 ) {
			loadMapData();
		}
		dataMap.putAll(managementMap);
		return dataMap;
	}	
	
	@Deprecated
	public static Map<String, String> getCalculationMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (calMap.size() < 1 ) {
			loadMapData();
		}
		dataMap.putAll(calMap);
		return dataMap;
	}		
	
	public static Map<String, String> getCompareTypeMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (compareTypeMap.size() < 1 ) {
			loadMapData();
		}
		dataMap.putAll(compareTypeMap);
		return dataMap;
	}		
	
	public static Map<String, String> getDataTypeMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (dataTypeMap.size() < 1 ) {
			loadMapData();
		}
		dataMap.putAll(dataTypeMap);
		return dataMap;
	}		
	
	public static Map<String, String> getQuasiRangeMap() {
		return quasiRangeMap;
	}
	
	public static Map<String, String> getActivateOptionMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		dataMap.put(YesNo.YES, YesNo.YES);
		dataMap.put(YesNo.NO, YesNo.NO);
		return dataMap;
	}

	/**
	 * TB_SYS_CODE.TYPE
	 */
	public final static String CODE_TYPE = "KPI";	
	
	// -------------------------------------------------------------------------------------------
	
	/**
	 * 越大越好
	 */
	public static final String MANAGEMENT_BIGGER_IS_BETTER = "1";
	public static final String MANAGEMENT_BIGGER_IS_BETTER_CODE = "BSC_KPIMA01";
	
	/**
	 * 越小越好
	 */
	public static final String MANAGEMENT_SMALLER_IS_BETTER = "2";	
	public static final String MANAGEMENT_SMALLER_IS_BETTER_CODE = "BSC_KPIMA02";
	
	/**
	 * 越準越好
	 */
	public static final String MANAGEMENT_QUASI_IS_BETTER = "3";		
	public static final String MANAGEMENT_QUASI_IS_BETTER_CODE = "BSC_KPIMA03";
	
	// -------------------------------------------------------------------------------------------	
	
	/**
	 * 1.與TARGET比較
	 */
	public static final String COMPARE_TYPE_TARGET = "1";
	public static final String COMPARE_TYPE_TARGET_CODE = "BSC_KPICT01";
	
	/**
	 * 2.與MIN比較
	 */
	public static final String COMPARE_TYPE_MIN = "2";
	public static final String COMPARE_TYPE_MIN_CODE = "BSC_KPICT02";
	
	// -------------------------------------------------------------------------------------------
	
	/**
	 * 平均
	 */
	@Deprecated
	public static final String CAL_AVERAGE = "1";
	@Deprecated
	public static final String CAL_AVERAGE_CODE = "BSC_KPICA01";
	
	/**
	 * 加總
	 */
	@Deprecated
	public static final String CAL_TOTAL = "2";
	@Deprecated
	public static final String CAL_TOTAL_CODE = "BSC_KPICA02";
	
	// -------------------------------------------------------------------------------------------
	
	/**
	 * 部門 KPI
	 */
	public static final String DATA_TYPE_DEPARTMENT = "1";
	public static final String DATA_TYPE_DEPARTMENT_CODE = "BSC_KPIDT01";
	
	/**
	 * 個人 KPI
	 */
	public static final String DATA_TYPE_PERSONAL = "2";
	public static final String DATA_TYPE_PERSONAL_CODE = "BSC_KPIDT02";
	
	/**
	 * 部門/個人 KPI
	 */
	public static final String DATA_TYPE_BOTH = "3";
	public static final String DATA_TYPE_BOTH_CODE = "BSC_KPIDT03";
	
}
