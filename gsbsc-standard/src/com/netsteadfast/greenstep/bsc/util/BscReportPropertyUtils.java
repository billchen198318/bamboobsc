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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.bsc.model.BscReportCode;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class BscReportPropertyUtils {
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private static ThreadLocal<List<TbSysCode>> sysCodeThreadLocal = new ThreadLocal<List<TbSysCode>>();
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)
				AppContext.getBean("core.service.SysCodeService");
	}
	
	public static void clearThreadLocal() {
		sysCodeThreadLocal.remove();
	}
	
	public static void loadData() throws ServiceException, Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", BscReportCode.CODE_TYPE);
		List<TbSysCode> sysCodeList = sysCodeService.findListByParams(params);
		sysCodeThreadLocal.set(sysCodeList);
	}
	
	private static String getParameterValue(String code) {
		List<TbSysCode> sysCodeList = sysCodeThreadLocal.get();
		if (sysCodeList==null) {
			return code;
		}		
		String value = "";
		for (TbSysCode sysCode : sysCodeList) {
			if (sysCode.getCode().equals(code)) {
				value = StringUtils.defaultString( sysCode.getParam1() );
			}
		}
		if (StringUtils.isBlank(value)) {
			return code;
		}
		return value;		
	}
	
	public static String getBackgroundColor() {
		return getParameterValue(BscReportCode.BACKGROUND_COLOR);
	}
	
	public static String getFontColor() {
		return getParameterValue(BscReportCode.FONT_COLOR);
	}
	
	public static String getPerspectiveTitle() {
		return getParameterValue(BscReportCode.PERSPECTIVE_TITLE);
	}
	
	public static String getObjectiveTitle() {
		return getParameterValue(BscReportCode.OBJECTIVE_TITLE);
	}
	
	public static String getKpiTitle() {
		return getParameterValue(BscReportCode.KPI_TITLE);
	}
	
	public static String getPersonalReportClassLevel() {
		return getParameterValue(BscReportCode.PERSONAL_REPORT_CLASS_LEVEL);
	}
	
	public static String getScoreLabel() {
		return getParameterValue(BscReportCode.SCORE_LABEL);
	}
	
	public static String getWeightLabel() {
		return getParameterValue(BscReportCode.WEIGHT_LABEL);
	}
	
	public static String getMaxLabel() {
		return getParameterValue(BscReportCode.MAX_LABEL);
	}
	
	public static String getTargetLabel() {
		return getParameterValue(BscReportCode.TARGET_LABEL);
	}
	
	public static String getMinLabel() {
		return getParameterValue(BscReportCode.MIN_LABEL);
	}
	
	public static String getManagementLabel() {
		return getParameterValue(BscReportCode.MANAGEMENT_LABEL);
	}
	
	public static String getCalculationLabel() {
		return getParameterValue(BscReportCode.CALCULATION_LABEL);
	}
	
	public static String getUnitLabel() {
		return getParameterValue(BscReportCode.UNIT_LABEL);
	}
	
	public static String getFormulaLabel() {
		return getParameterValue(BscReportCode.FORMULA_LABEL);
	}
	
	public static String getOrganizationLabel() {
		return getParameterValue(BscReportCode.ORGANIZATION_LABEL);
	}
	
	public static String getEmployeeLabel() {
		return getParameterValue(BscReportCode.EMPLOYEE_LABEL);
	}
	
}
