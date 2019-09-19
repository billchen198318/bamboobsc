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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@SuppressWarnings("unchecked")
public class BscReportSupportUtils {	
	private static final String REPORT_UP_DOWN_HTML_ICON_STATUS_EXPR_ID = "BSC_RPT_EXPR0003";
	private static final String REPORT_UP_DOWN_BYTE_ICON_STATUS_EXPR_ID = "BSC_RPT_EXPR0004";
	private static final String REPORT_UP_DOWN_HTML_ICON_STATUS_FOR_BASE_EXPR_ID = "BSC_RPT_EXPR0005";
	private static final String REPORT_UP_DOWN_BYTE_ICON_STATUS_FOR_BASE_EXPR_ID = "BSC_RPT_EXPR0006";	
	private static ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService; 
	private static IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private static IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private static ThreadLocal<SysExpressionVO> exprThreadLocal01 = new ThreadLocal<SysExpressionVO>(); // for BSC_RPT_EXPR0003
	private static ThreadLocal<SysExpressionVO> exprThreadLocal02 = new ThreadLocal<SysExpressionVO>(); // for BSC_RPT_EXPR0004
	private static ThreadLocal<SysExpressionVO> exprThreadLocal03 = new ThreadLocal<SysExpressionVO>(); // BSC_RPT_EXPR0005
	private static ThreadLocal<SysExpressionVO> exprThreadLocal04 = new ThreadLocal<SysExpressionVO>(); // BSC_RPT_EXPR0006
	private static NumberFormat numberFormat = null;
	
	static {
		numberFormat = new DecimalFormat("0.00");
		numberFormat.setCurrency( Currency.getInstance(Locale.US) );
		sysExpressionService = (ISysExpressionService<SysExpressionVO, TbSysExpression, String>)
				AppContext.getBean("core.service.SysExpressionService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
	}
	
	public static void clearThreadLocal() {
		exprThreadLocal01.remove();
		exprThreadLocal02.remove();
		exprThreadLocal03.remove();
		exprThreadLocal04.remove();
	}
	
	public static void loadExpression() throws ServiceException, Exception {
		loadExpression(exprThreadLocal01, REPORT_UP_DOWN_HTML_ICON_STATUS_EXPR_ID);
		loadExpression(exprThreadLocal02, REPORT_UP_DOWN_BYTE_ICON_STATUS_EXPR_ID);
		loadExpression(exprThreadLocal03, REPORT_UP_DOWN_HTML_ICON_STATUS_FOR_BASE_EXPR_ID);
		loadExpression(exprThreadLocal04, REPORT_UP_DOWN_BYTE_ICON_STATUS_FOR_BASE_EXPR_ID);
	}
	
	public static void loadExpression(
			ThreadLocal<SysExpressionVO> exprThreadLocal, String exprId) throws ServiceException, Exception {
		if ( exprThreadLocal.get() == null ) { 
			SysExpressionVO sysExpression = new SysExpressionVO();
			sysExpression.setExprId( exprId );
			DefaultResult<SysExpressionVO> result = sysExpressionService.findByUkCacheable(sysExpression); 
			if (result.getValue()!=null) {
				sysExpression = result.getValue();
				exprThreadLocal.set(sysExpression);
			}			
		}		
	}
	
	public static String getUrlIcon(KpiVO kpi, float score) throws Exception {
		String icon = "";
		SysExpressionVO sysExpression = exprThreadLocal01.get();
		if (null == sysExpression) {
			return icon;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		parameters.put("kpi", kpi);
		parameters.put("score", score);
		results.put("icon", " ");
		ScriptExpressionUtils.execute(
				sysExpression.getType(), 
				sysExpression.getContent(), 
				results, 
				parameters);
		icon = (String)results.get("icon");
		return StringUtils.defaultString( icon );
	}
	
	public static String getUrlIconBase(String mode, float target, float min, float score,
			String kpiCompareType, String kpiManagement, float kpiQuasiRange) throws Exception {
		String icon = "";
		SysExpressionVO sysExpression = exprThreadLocal03.get();
		if (null == sysExpression) {
			return icon;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		parameters.put("mode", mode);
		parameters.put("target", target);
		parameters.put("min", min);
		parameters.put("score", score);
		parameters.put("compareType", kpiCompareType);
		parameters.put("management", kpiManagement);
		parameters.put("quasiRange", kpiQuasiRange);
		results.put("icon", " ");
		ScriptExpressionUtils.execute(
				sysExpression.getType(), 
				sysExpression.getContent(), 
				results, 
				parameters);
		icon = (String)results.get("icon");
		return StringUtils.defaultString( icon );		
	}
	
	public static String getHtmlIcon(KpiVO kpi, float score) throws Exception {
		String icon = getUrlIcon(kpi, score);
		if ( StringUtils.isBlank(icon) ) {
			return "";
		}
		return "<img src='./images/" + icon + "' border='0' >";
	}
	
	public static byte[] getByteIcon(KpiVO kpi, float score) throws Exception {
		byte[] datas = null;
		SysExpressionVO sysExpression = exprThreadLocal02.get();
		if (null == sysExpression) {
			return datas;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		parameters.put("kpi", kpi);
		parameters.put("score", score);
		results.put("icon", " ");
		ScriptExpressionUtils.execute(
				sysExpression.getType(), 
				sysExpression.getContent(), 
				results, 
				parameters);
		String iconResource = (String)results.get("icon");		
		ClassLoader classLoader = BscReportSupportUtils.class.getClassLoader();
		datas = IOUtils.toByteArray( classLoader.getResource(iconResource).openStream() );
		return datas;
	}
	
	public static String getHtmlIconBase(String mode, float target, float min, float score,
			String kpiCompareType, String kpiManagement, float kpiQuasiRange) throws Exception {
		String icon = getUrlIconBase(mode, target, min, score, 
				kpiCompareType, kpiManagement, kpiQuasiRange);
		if ( StringUtils.isBlank(icon) ) {
			return "";
		}
		return "<img src='./images/" + icon + "' border='0' >";
	}	
	
	public static byte[] getByteIconBase(String mode, float target, float min, float score,
			String kpiCompareType, String kpiManagement, float kpiQuasiRange) throws Exception {
		byte[] datas = null;
		SysExpressionVO sysExpression = exprThreadLocal04.get();
		if (null == sysExpression) {
			return datas;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		parameters.put("mode", mode);
		parameters.put("target", target);
		parameters.put("min", min);
		parameters.put("score", score);
		parameters.put("compareType", kpiCompareType);
		parameters.put("management", kpiManagement);
		parameters.put("quasiRange", kpiQuasiRange);
		results.put("icon", " ");
		ScriptExpressionUtils.execute(
				sysExpression.getType(), 
				sysExpression.getContent(), 
				results, 
				parameters);
		String iconResource = (String)results.get("icon");		
		ClassLoader classLoader = BscReportSupportUtils.class.getClassLoader();
		datas = IOUtils.toByteArray( classLoader.getResource(iconResource).openStream() );
		return datas;
	}	
	
	public static String parse(float score) {			
		return numberFormat.format(score);
	}
	
	public static String parse2(float score) {			
		String str = numberFormat.format(score);
		if (str.endsWith(".00")) {
			str = str.substring(0, str.length()-3);
		}
		return str;
	}	
	
	/**
	 * 為KPI報表顯示要用的 , 取出KPI所屬的部門/組織
	 * 
	 * @param kpi
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static void fillKpiOrganizations(KpiVO kpi) throws ServiceException, Exception {
		List<String> appendOrgaOids = organizationService.findForAppendOrganizationOidsByKpiOrga(kpi.getId());
		for (int i=0; appendOrgaOids!=null && i<appendOrgaOids.size(); i++) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid( appendOrgaOids.get(i) );
			DefaultResult<OrganizationVO> result = organizationService.findObjectByOid(organization);
			if ( result.getValue() != null ) {
				organization = result.getValue();
				kpi.getOrganizations().add(organization);
			}
		}
	}
	
	/**
	 * 為KPI報表顯示要用的 , 取出KPI所屬的員工
	 * 
	 * @param kpi
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static void fillKpiEmployees(KpiVO kpi) throws ServiceException, Exception {
		List<String> appendEmplOids = employeeService.findForAppendEmployeeOidsByKpiEmpl(kpi.getId());
		for (int i=0; appendEmplOids!=null && i<appendEmplOids.size(); i++) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid( appendEmplOids.get(i) );
			DefaultResult<EmployeeVO> result = employeeService.findObjectByOid(employee);
			if ( result.getValue() != null ) {
				employee = result.getValue();
				kpi.getEmployees().add(employee);
			}
		}
	}
	
}
