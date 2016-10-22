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
public class PdcaType {
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private static Map<String, String> pdcaMap = new LinkedHashMap<String, String>();
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>) AppContext.getBean("core.service.SysCodeService");		
	}
	
	private static void loadMapData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", CODE_TYPE);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("code", "ASC");
		try {
			List<TbSysCode> codes = sysCodeService.findListByParams(params, null, orderParams);
			for (TbSysCode code : codes) {
				if (code.getCode().equals(PLAN_CODE)) {
					pdcaMap.put(PLAN, code.getName());
				}
				if (code.getCode().equals(DO_CODE)) {
					pdcaMap.put(DO, code.getName());
				}
				if (code.getCode().equals(CHECK_CODE)) {
					pdcaMap.put(CHECK, code.getName());
				}
				if (code.getCode().equals(ACTION_CODE)) {
					pdcaMap.put(ACTION, code.getName());
				}	
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
	
	public static Map<String, String> getDataMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		if (pdcaMap.size() < 1) {
			loadMapData();
		}
		dataMap.putAll(pdcaMap);
		return dataMap;
	}
	
	public final static String CODE_TYPE = "PDCA";
	
	public static final String PLAN = "P";
	public static final String PLAN_CODE = "PDCA_CODE001";
	
	public static final String DO = "D";
	public static final String DO_CODE = "PDCA_CODE002";	
	
	public static final String CHECK = "C";
	public static final String CHECK_CODE = "PDCA_CODE003";		

	public static final String ACTION = "A";
	public static final String ACTION_CODE = "PDCA_CODE004";	
	
}
