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

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CodeGeneratorUtil {
	
	public static void main(String args[]) throws Exception {
		execute("com.netsteadfast.greenstep", "SysProg");
	}
	
	public static void execute(String packageName, String headName) throws Exception {
		if (packageName.length()<2) {
			throw new Exception("package name is require!");
		}
		if (headName.length()<2) {
			throw new Exception("head name is require!");
		}
		generatorProcess(packageName, headName, "IDAO.ftl");
		generatorProcess(packageName, headName, "IService.ftl");	
		generatorProcess(packageName, headName, "DAOImpl.ftl");
		generatorProcess(packageName, headName, "ServiceImpl.ftl");
	}
	
	private static void generatorProcess(String packageName, String headName, String templateFilePath) throws Exception {
		Configuration cfg = new Configuration( Configuration.VERSION_2_3_21 );
		cfg.setClassForTemplateLoading(CodeGeneratorUtil.class, "");
		Template template = cfg.getTemplate(templateFilePath, "utf-8");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("packageName", packageName);
		params.put("headName", headName);
		params.put("headNameSmall", headName.substring(0, 1).toLowerCase() + headName.substring(1, headName.length()));
		String outDir = System.getProperty("user.dir") + "/out";
		String outFilePath = outDir + "/" + getOutFileFootName(headName, templateFilePath);
		Writer file = new FileWriter (new File(outFilePath));
		template.process(params, file);
		file.flush();
		file.close();
	}
	
	private static String getOutFileFootName(String headName, String templateFilePath) {
		if (templateFilePath.endsWith("IDAO.ftl")) {
			return "I" + headName + "DAO.java";
		}
		if (templateFilePath.endsWith("IService.ftl")) {
			return "I" + headName + "Service.java";
		}
		if (templateFilePath.endsWith("DAOImpl.ftl")) {
			return headName + "DAOImpl.java";
		}
		if (templateFilePath.endsWith("ServiceImpl.ftl")) {
			return headName + "ServiceImpl.java";
		}		
		return "unknown.java";
	}
	
}
