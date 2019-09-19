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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.renjin.sexp.AbstractAtomicVector;

import com.netsteadfast.greenstep.base.model.ScriptTypeCode;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import bsh.Interpreter;

public class ScriptExpressionUtils {
	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static CompilerConfiguration groovyCompilerConfig = new CompilerConfiguration();
	private static ThreadLocal<Interpreter> bshInterpreterTL = new ThreadLocal<Interpreter>();
	private static ThreadLocal<GroovyShell> groovyShellTL = new ThreadLocal<GroovyShell>();
	private static ThreadLocal<ScriptEngine> renjinScriptEngineTL = new ThreadLocal<ScriptEngine>();
	
	static {
		groovyCompilerConfig.getOptimizationOptions().put("indy", true);
		groovyCompilerConfig.getOptimizationOptions().put("int", false);		
	}
	
	public static void clearThreadLocal() {
		bshInterpreterTL.remove();
		groovyShellTL.remove();
		renjinScriptEngineTL.remove();
	}
	
	public static Interpreter buildBshInterpreter(boolean clean) {
		Interpreter bshInterpreter = null;
		if ((bshInterpreter=bshInterpreterTL.get()) == null) {
			bshInterpreter = new Interpreter();
			bshInterpreterTL.set(bshInterpreter);
		}
		if (clean && bshInterpreter.getNameSpace()!=null) {
			bshInterpreter.getNameSpace().clear();
		}
		return bshInterpreter;
	}
	
	public static GroovyShell buildGroovyShell(boolean fromThreadLocal) {
		if (fromThreadLocal) {
			GroovyShell groovyShell = null;
			if ((groovyShell=groovyShellTL.get()) == null) {
				groovyShell = new GroovyShell(groovyCompilerConfig);
				groovyShellTL.set(groovyShell);
			}	
			return groovyShell;			
		}		
		return new GroovyShell(groovyCompilerConfig);
	}
	
	public static PythonInterpreter buildPythonInterpreter(PyObject dist, boolean cleanup) {
		PythonInterpreter pyInterpreter = PythonInterpreter.threadLocalStateInterpreter(dist);
		if (cleanup) {
			pyInterpreter.cleanup();
		}
		return pyInterpreter;
	}
	
	public static ScriptEngine buildRenjinScriptEngine() {
		if (renjinScriptEngineTL.get()!=null) {
			return renjinScriptEngineTL.get();
		}
		ScriptEngine engine = manager.getEngineByName("Renjin");
		renjinScriptEngineTL.set(engine);
		return engine;
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
		if (ScriptTypeCode.IS_R.equals(type)) {
			executeRenjin(scriptExpression, results, parameters);
		}
		return results;
	}
	
	public static String replaceFormulaExpression(String type, String expression) throws Exception {
		if (StringUtils.isBlank(expression)) {
			return expression;
		}
		String bscExpression = expression;
		bscExpression = StringUtils.replace(bscExpression, "÷", "/");
		bscExpression = StringUtils.replace(bscExpression, "×", "*");
		bscExpression = StringUtils.replace(bscExpression, "−", "-");
		bscExpression = StringUtils.replace(bscExpression, "+", "+");
		if (!ScriptTypeCode.IS_PYTHON.equals(type)) {
			bscExpression = StringUtils.replace(bscExpression, "abs(", "Math.abs(");
			bscExpression = StringUtils.replace(bscExpression, "sqrt(", "Math.sqrt(");					
		}		
		return bscExpression;		
	}
	
	private static void executeBsh(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		//Interpreter bshInterpreter = new Interpreter();
		Interpreter bshInterpreter = buildBshInterpreter(true);
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
		//GroovyShell groovyShell = new GroovyShell(groovyCompilerConfig);		
		GroovyShell groovyShell = buildGroovyShell(false);
		Binding binding = groovyShell.getContext();		
		if (parameters!=null) {			
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				//groovyShell.setProperty(entry.getKey(), entry.getValue());
				binding.setVariable(entry.getKey(), entry.getValue());				
			}
		}		
		groovyShell.evaluate(scriptExpression);
		if (results!=null) {
			for (Map.Entry<String, Object> entry : results.entrySet()) {
				//entry.setValue( groovyShell.getVariable(entry.getKey()) );
				entry.setValue( binding.getVariable(entry.getKey()) );
			}
		}
	}
	
	private static void executeJython(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		//PythonInterpreter pyInterpreter = new PythonInterpreter();
		PythonInterpreter pyInterpreter = buildPythonInterpreter(null, true);
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
	
	private static void executeRenjin(String scriptExpression, Map<String, Object> results, Map<String, Object> parameters) throws Exception {
		ScriptEngine engine = buildRenjinScriptEngine();
		if (parameters!=null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				engine.put(entry.getKey(), entry.getValue());
			}
		}
		engine.eval(scriptExpression);
		if (results!=null) {
			for (Map.Entry<String, Object> entry : results.entrySet()) {
				Object res = engine.get(entry.getKey());
				if (res instanceof AbstractAtomicVector) {
					if ( ((AbstractAtomicVector) res).length()>0 ) {
						entry.setValue( ((AbstractAtomicVector) res).getElementAsObject(0) );
					}
				} else {
					entry.setValue(res);
				}
			}
		}
	}	
	
}
