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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.ScriptExpressionRunType;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelp;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExpr;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExprMap;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprMapService;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprService;
import com.netsteadfast.greenstep.service.ISysBeanHelpService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprMapVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@SuppressWarnings("unchecked")
public class ServiceScriptExpressionUtils {
	private static ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService;
	private static ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService;
	private static ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> sysBeanHelpExprMapService;
	private static ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	
	static {
		sysBeanHelpService = (ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String>)
				AppContext.getBean("core.service.SysBeanHelpService");
		sysBeanHelpExprService = (ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String>)
				AppContext.getBean("core.service.SysBeanHelpExprService");
		sysBeanHelpExprMapService = (ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String>)
				AppContext.getBean("core.service.SysBeanHelpExprMapService");
		sysExpressionService = (ISysExpressionService<SysExpressionVO, TbSysExpression, String>)
				AppContext.getBean("core.service.SysExpressionService");
	}
	
	public static boolean needProcess(String beanId, String methodName, String system) throws ServiceException, Exception {
		boolean f = false;
		SysBeanHelpVO beanHelp = new SysBeanHelpVO();
		beanHelp.setBeanId(beanId);
		beanHelp.setMethod(methodName);
		beanHelp.setSystem(system);		
		if (sysBeanHelpService.countByUK(beanHelp)>0) {
			f = true;
		}
		beanHelp = null;
		return f;
	}
	
	public static void processBefore(String beanId, Method method, String system, ProceedingJoinPoint pjp) throws ServiceException, Exception {
		process(ScriptExpressionRunType.IS_BEFORE, beanId, method, system, null, pjp);
	}
	
	public static void processAfter(String beanId, Method method, String system, 
			Object resultObj, ProceedingJoinPoint pjp) throws ServiceException, Exception {
		process(ScriptExpressionRunType.IS_AFTER, beanId, method, system, resultObj, pjp);
	}
	
	private static SysBeanHelpVO loadSysBeanHelperData(String beanId, String methodName, String system) throws ServiceException, Exception {
		SysBeanHelpVO beanHelp = new SysBeanHelpVO();
		beanHelp.setBeanId(beanId);
		beanHelp.setMethod(methodName);
		beanHelp.setSystem(system);
		DefaultResult<SysBeanHelpVO> result = sysBeanHelpService.findByUK(beanHelp);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		beanHelp = result.getValue();
		return beanHelp;
	}
	
	private static List<TbSysBeanHelpExpr> loadSysBeanHelpExprsData(SysBeanHelpVO beanHelp) throws ServiceException, Exception {
		List<TbSysBeanHelpExpr> exprs = null;
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> orderParams = new HashMap<String, String>();
		params.put("helpOid", beanHelp.getOid());
		orderParams.put("exprSeq", "ASC");
		exprs = sysBeanHelpExprService.findListByParams(params, null, orderParams);
		if (exprs == null) {
			exprs = new ArrayList<TbSysBeanHelpExpr>();
		}
		params.clear();
		orderParams.clear();
		params = null;
		orderParams = null;
		return exprs;		
	}
	
	private static List<TbSysBeanHelpExprMap> loadSysBeanHelpExprMapsData(TbSysBeanHelpExpr beanHelpExpr) throws ServiceException, Exception {
		List<TbSysBeanHelpExprMap> exprMaps = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("helpExprOid", beanHelpExpr.getOid());
		exprMaps = sysBeanHelpExprMapService.findListByParams(params);
		if (exprMaps == null) {
			exprMaps = new ArrayList<TbSysBeanHelpExprMap>();
		}
		params.clear();
		params = null;
		return exprMaps;		
	}
	
	private static Map<String, Object> getParameters(TbSysBeanHelpExpr beanHelpExpr, List<TbSysBeanHelpExprMap> beanHelpExprMaps,
			Object resultObj, ProceedingJoinPoint pjp) {
		Object[] args = pjp.getArgs();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (TbSysBeanHelpExprMap map : beanHelpExprMaps) {
			Object value = null;
			if (YesNo.YES.equals(map.getMethodResultFlag())) {
				value = resultObj;
			} else {
				for (int i=0; args!=null && i<args.length; i++) {
					if (args[i] !=null && args[i].getClass() != null 
							&& args[i].getClass().getName().equals(map.getMethodParamClass()) 
							&& map.getMethodParamIndex() == i ) {
						value = args[i];
					}
				}
			}
			dataMap.put(map.getVarName(), value);
		}
		return dataMap;
	}	
	
	private static void process(String runType, String beanId, Method method, String system, 
			Object resultObj, ProceedingJoinPoint pjp) throws ServiceException, Exception {
		SysBeanHelpVO beanHelp = loadSysBeanHelperData(beanId, method.getName(), system);
		if (!YesNo.YES.equals(beanHelp.getEnableFlag()) ) {
			return;
		}
		List<TbSysBeanHelpExpr> beanHelpExprs = loadSysBeanHelpExprsData(beanHelp);
		if (beanHelpExprs==null || beanHelpExprs.size()<1) {
			return;
		}
		for (TbSysBeanHelpExpr helpExpr : beanHelpExprs) {
			SysExpressionVO expression = new SysExpressionVO();
			expression.setExprId(helpExpr.getExprId());
			DefaultResult<SysExpressionVO> eResult = sysExpressionService.findByUK(expression);
			if (eResult.getValue()==null) {
				continue;
			}
			expression = eResult.getValue();
			List<TbSysBeanHelpExprMap> exprMaps = loadSysBeanHelpExprMapsData(helpExpr);
			/* 2015-04-14 rem
			try {
				ScriptExpressionUtils.execute(
						expression.getType(), 
						expression.getContent(), 
						null, 
						getParameters(helpExpr, exprMaps, resultObj, pjp));						
			} catch (Exception e) {
				e.printStackTrace();
			}	
			*/
			// 不要 try catch 包起來, 讓外面也能接到 Exception 
			ScriptExpressionUtils.execute(
					expression.getType(), 
					expression.getContent(), 
					null, 
					getParameters(helpExpr, exprMaps, resultObj, pjp));					
		}
	}

}
