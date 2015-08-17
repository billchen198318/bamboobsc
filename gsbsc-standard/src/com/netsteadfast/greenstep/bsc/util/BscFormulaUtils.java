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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.model.ScriptTypeCode;
import com.netsteadfast.greenstep.bsc.model.BscMeasureData;
import com.netsteadfast.greenstep.bsc.model.BscMeasureVariable;
import com.netsteadfast.greenstep.bsc.model.FormulaMode;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.vo.FormulaVO;

public class BscFormulaUtils {
	private static final String DEFAULT_RETURN_MODE_VAR = "ans_" + System.currentTimeMillis();
	
	public static Map<String, Object> getParameter(BscMeasureData data) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (data.getTarget()!=null) {
			parameter.put(BscMeasureVariable.TARGET, data.getTarget());
		}
		if (data.getActual()!=null) {
			parameter.put(BscMeasureVariable.ACTUAL, data.getActual());
		}		
		return parameter;
	}
	
	public static Map<String, Object> parse(String type, String returnMode, String returnVar, 
			String expression, Map<String, Object> parameter) throws Exception {
		Map<String, Object> results = new HashMap<String, Object>();
		if (FormulaMode.MODE_CUSTOM.equals(returnMode)) {
			if (StringUtils.isBlank(returnVar)) {
				throw new java.lang.IllegalArgumentException("returnVar cannot blank!");
			}
			results.put(returnVar, null);
		} else {
			results.put(DEFAULT_RETURN_MODE_VAR, null);
		}
		ScriptExpressionUtils.execute(
				type, handlerExpression(type, returnMode, expression), results, parameter);
		//System.out.println(results);
		return results;
	}
	
	public static Object parse(FormulaVO formula, BscMeasureData data) throws Exception {
		if (formula == null || StringUtils.isBlank(formula.getType()) || StringUtils.isBlank(formula.getExpression()) ) {
			throw new java.lang.IllegalArgumentException("formula data cannot blank!");
		}
		Object resultObj = null;
		Map<String, Object> results = parse(formula.getType(), formula.getReturnMode(), formula.getReturnVar(), 
				formula.getExpression(), getParameter(data) );
		if (FormulaMode.MODE_CUSTOM.equals(formula.getReturnMode())) {
			resultObj = results.get(formula.getReturnVar());
		} else {
			resultObj = results.get(DEFAULT_RETURN_MODE_VAR);
		}
		return resultObj;
	}
	
	private static String handlerExpression(String type, String returnMode, String expression) throws Exception {
		if (StringUtils.isBlank(expression)) {
			return expression;
		}
		String bscExpression = expression;
		if (FormulaMode.MODE_DEFAULT.equals(returnMode)) {
			if (ScriptTypeCode.IS_BSH.equals(type)) {
				bscExpression = DEFAULT_RETURN_MODE_VAR + "=" + bscExpression;
			}
			if (ScriptTypeCode.IS_GROOVY.equals(type)) {
				bscExpression = DEFAULT_RETURN_MODE_VAR + "=" + bscExpression;
			}
			if (ScriptTypeCode.IS_PYTHON.equals(type)) {
				bscExpression = DEFAULT_RETURN_MODE_VAR + "=" + bscExpression;
			}			
		}
		return ScriptExpressionUtils.replaceFormulaExpression(type, bscExpression);
	}

}
