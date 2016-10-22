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
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class FormulaMode {
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private static Map<String, String> returnModeMap = new LinkedHashMap<String, String>();
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)
				AppContext.getBean("core.service.SysCodeService");
	}
	
	private static void loadReturnModeMapData() {
		returnModeMap.clear();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", CODE_TYPE);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("code", "ASC");
		try {
			List<TbSysCode> codes = sysCodeService.findListByParams(params, null, orderParams);
			for (TbSysCode code : codes) {
				if (MODE_DEFAULT_CODE.equals(code.getCode()) ) {
					returnModeMap.put(MODE_DEFAULT, code.getName());
				}
				if (MODE_CUSTOM_CODE.equals(code.getCode()) ) {
					returnModeMap.put(MODE_CUSTOM, code.getName());
				}				
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (returnModeMap.size() != 2) {
			returnModeMap.clear();
			returnModeMap.put(MODE_DEFAULT, MODE_DEFAULT);
			returnModeMap.put(MODE_CUSTOM, MODE_CUSTOM);
		}
	}
	
	public static Map<String, String> getReturnModeMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (returnModeMap.size() < 1 ) {
			loadReturnModeMapData();
		}
		dataMap.putAll(returnModeMap);
		return dataMap;
	}
	
	
	/**
	 * TB_SYS_CODE.TYPE
	 */
	public final static String CODE_TYPE = "BFM";
	
	/**
	 * 預設的模式
	 */
	public final static String MODE_DEFAULT = "D";
	
	/**
	 * 預設的模式的TB_SYS_CODE.CODE
	 */
	public final static String MODE_DEFAULT_CODE = "BSC_FORMD01";
	
	/**
	 * 自訂回傳變數的模式
	 */
	public final static String MODE_CUSTOM = "C";
	
	/**
	 * 自訂回傳變數的模式的TB_SYS_CODE.CODE
	 */
	public final static String MODE_CUSTOM_CODE = "BSC_FORMD02";
	
}
