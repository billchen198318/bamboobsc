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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.dynamichql.DynamicHql;
import com.netsteadfast.greenstep.base.model.dynamichql.Query;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class DynamicHqlUtils {
	protected static Logger logger = Logger.getLogger(DynamicHqlUtils.class);
	private final static String TEMPLATE_ID = "dynamic-hql-resource";
	private static Map<String, DynamicHql> resourceDataMap = new HashMap<String, DynamicHql>();
	
	public static DynamicHql loadResource(String resource) throws Exception {
		DynamicHql dynamicHql = resourceDataMap.get(resource);
		if (dynamicHql == null) {
			InputStream in = null;
			try {
				in = DynamicHqlUtils.class.getResourceAsStream( "/dynamichql/" + resource );				
				byte[] xmlBytes = IOUtils.toByteArray(in);
				JAXBContext jaxbContext = JAXBContext.newInstance( DynamicHql.class );
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				dynamicHql = (DynamicHql) jaxbUnmarshaller.unmarshal( new ByteArrayInputStream(xmlBytes) );
				resourceDataMap.put(resource, dynamicHql);				
			} catch (IOException e) {
				logger.error( e.getMessage().toString() );				
				throw e;
			} finally {
				if (in != null) {
					IOUtils.closeQuietly(in);
				}
				in = null;
			}
		}		
		return dynamicHql;
	}
	
	public static String process(String resource, String queryName, Map<String, Object> paramMap) throws Exception {
		DynamicHql dynamicHql = loadResource(resource);	
		if (null == dynamicHql) {
			logger.error( "no dynamic hql resource." );			
			throw new Exception( "no dynamic hql resource." );
		}
		String hql = "";
		for (int i=0; i<dynamicHql.getQuery().size() && hql.length()<1; i++) {
			Query queryObj = dynamicHql.getQuery().get(i);
			if (!queryObj.getName().equals(queryName)) {
				continue;
			}			
			StringTemplateLoader templateLoader = new StringTemplateLoader();
			templateLoader.putTemplate(TEMPLATE_ID, queryObj.getContent());
			Configuration cfg = new Configuration( Configuration.VERSION_2_3_21 );
			cfg.setTemplateLoader(templateLoader);
			Template template = cfg.getTemplate(TEMPLATE_ID, Constants.BASE_ENCODING);
			Writer out = new StringWriter();
			template.process(paramMap, out);			
			hql = out.toString();
		}
		if (StringUtils.isBlank(hql)) {
			logger.warn( "hql is blank." );			
		}
		return hql;		
	}
	
}
