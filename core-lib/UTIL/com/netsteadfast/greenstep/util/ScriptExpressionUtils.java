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

import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.python.util.PythonInterpreter;

import com.netsteadfast.greenstep.base.model.ScriptTypeCode;

import groovy.lang.GroovyShell;
import bsh.Interpreter;

public class ScriptExpressionUtils {
	private static CompilerConfiguration groovyCompilerConfig = new CompilerConfiguration();
	
	static {
		groovyCompilerConfig.getOptimizationOptions().put("indy", true);
		groovyCompilerConfig.getOptimizationOptions().put("int", false);		
	}
	
	/**
	 * 執行 script 
	 * 
	 * @param type					請參考 ScriptTypeCode , 目前有 BSH, GROOVY, PYTHON
	 * @param scriptExpression		script內容
	 * @param results				輸出的值
	 * @param parameters			代入的值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> execute(String type, String scriptExpression, 
			Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		if (!ScriptTypeCode.isTypeCode(type)) {
			throw new java.lang.IllegalArgumentException("no support script language of : " + type);
		}
		if (ScriptTypeCode.IS_BSH.equals(type)) {
			executeBsh(scriptExpression, results, parameters);
		}
		if (ScriptTypeCode.IS_GROOVY.equals(type)) {
			executeGroovy(scriptExpression, results, parameters);
		}
		if (ScriptTypeCode.IS_PYTHON.equals(type)) {
			executeJython(scriptExpression, results, parameters);
		}
		return results;
	}
	
	private static void executeBsh(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		Interpreter bshInterpreter = new Interpreter();
		if (parameters!=null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				bshInterpreter.set(entry.getKey(), entry.getValue());
			}
		}
		bshInterpreter.eval(scriptExpression);
		if (results!=null) {
			for (Map.Entry<String, Object> entry : results.entrySet()) {
				entry.setValue( bshInterpreter.get(entry.getKey()) );
			}
		}
	}
	
	private static void executeGroovy(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {	
		GroovyShell groovyShell = new GroovyShell(groovyCompilerConfig);		
		if (parameters!=null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				groovyShell.setProperty(entry.getKey(), entry.getValue());
			}
		}		
		groovyShell.evaluate(scriptExpression);
		if (results!=null) {
			for (Map.Entry<String, Object> entry : results.entrySet()) {
				entry.setValue( groovyShell.getVariable(entry.getKey()) );
			}
		}
	}
	
	private static void executeJython(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		PythonInterpreter pyInterpreter = new PythonInterpreter();
		if (parameters!=null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				pyInterpreter.set(entry.getKey(), entry.getValue());
			}
		}
		pyInterpreter.exec(scriptExpression);
		if (results!=null) {
			for (Map.Entry<String, Object> entry : results.entrySet()) {
				entry.setValue( pyInterpreter.get(entry.getKey()) );
			}
		}
	}
	
}
