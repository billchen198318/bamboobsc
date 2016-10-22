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
package org.gsweb.components.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ComponentResourceUtils {
	private static Map<String, Object> resource=new HashMap<String, Object>();
	
	public synchronized static Object get(String key) {
		return resource.get(key);
	}
	
	public synchronized static void put(String key, Object value) {
		if (resource.get(key)==null) {
			resource.put(key, value);
		}
	}
	
	public static String getResourceSrc(Class<?> c, String metaInfJSFile) {
		String key=c.getName();
		String out=(String)get(key);
		if (out==null) {
			try {
				out=org.apache.commons.io.IOUtils.toString(
						c.getClassLoader().getResource(metaInfJSFile).openStream(), Constants.BASE_ENCODING);
				put(key, out);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return out;
	}		
	
	public static String getResourceSrc(Class<?> c, String type, String metaInfFile) {
		String key=c.getName()+"."+type;
		String out=(String)get(key);
		if (out==null) {
			try {
				out=org.apache.commons.io.IOUtils.toString(
						c.getClassLoader().getResource(metaInfFile).openStream(), Constants.BASE_ENCODING);
				put(key, out);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return out;
	}	
	
	public static String generatorResource(Class<?> c, String type, String metaInfFile, Map<String, Object> params) throws Exception {
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("resourceTemplate", getResourceSrc(c, type, metaInfFile) );
		Configuration cfg = new Configuration( Configuration.VERSION_2_3_21 );
		cfg.setTemplateLoader(templateLoader);
		Template template = cfg.getTemplate("resourceTemplate", Constants.BASE_ENCODING);
		Writer out = new StringWriter();
		template.process(params, out);
		return out.toString();
	}
	
	public static String getScriptFunctionNameForXhrMain(String functionName) {
		if (StringUtils.isBlank(functionName)) {
			return "";
		}
		functionName = functionName.trim();
		if (functionName.indexOf(";")>-1) {
			functionName = StringUtils.replaceOnce(functionName, ";", "");
		}
		if (functionName.indexOf(")")==-1) {
			functionName = functionName + "()";
		} 		
		return functionName;
	}	
	
	public static String getScriptFunctionNameForGridParams(String functionName) {
		if (StringUtils.isBlank(functionName)) {
			return "";
		}
		functionName = functionName.trim();
		if (functionName.indexOf(";") > -1) {
			functionName = StringUtils.replaceOnce(functionName, ";", "");
		}
		return functionName;
	}	
	
	public static String getScriptFunctionNameForInCall(String functionName) {
		if (StringUtils.isBlank(functionName)) {
			return "";
		}
		functionName = functionName.trim();
		if (functionName.indexOf(")") == -1) {
			functionName += "()";
		}
		if (functionName.indexOf(";") == -1) {
			functionName += ";";
		}
		return functionName;		
	}	
	
	public static void main(String args[]) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();		
		params.put("parameterType", "postData");
		params.put("id", "test");
		params.put("onClick", "myFunction_AA");
		params.put("xhrParameter", " ");
		params.put("xhrUrl", "mydata.do");
		params.put("handleAs", "json");
		params.put("timeout", "600000");
		params.put("sync", "true");
		params.put("preventCache", "true");
		params.put("loadFn", "aa();");
		params.put("errorFn", "bb();");
		System.out.println( generatorResource(ComponentResourceUtils.class, "script", "META-INF/resource/button/ui.button.js", params) );
	}
	
}
