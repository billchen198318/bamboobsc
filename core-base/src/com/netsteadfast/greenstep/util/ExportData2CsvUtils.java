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
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.xmlbeans.exportdata.ExportDataConfig;

public class ExportData2CsvUtils {
	protected static Logger logger=Logger.getLogger(ExportData2CsvUtils.class);
	private static final String META_CONF_DIR = "META-INF/resource/export_data_config";
	
	private static ExportDataConfig getConfig(String configXmlFile) throws Exception {
		InputStream is = ExportData2CsvUtils.class.getClassLoader()
			.getResource( META_CONF_DIR + "/" + configXmlFile ).openStream();
		byte[] xmlContent = IOUtils.toString(is, Constants.BASE_ENCODING).getBytes();
		JAXBContext jaxbContext = JAXBContext.newInstance( ExportDataConfig.class );
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream( xmlContent );
		ExportDataConfig config = (ExportDataConfig) jaxbUnmarshaller.unmarshal( bais );
		return config;
	}
	
	public static String create(String configXmlFile, NamedParameterJdbcTemplate jdbcTemplate, 
			Map<String, Object> sqlParamMap) throws ServiceException, Exception {
		ExportDataConfig config = getConfig(configXmlFile);
		String csvText = processCsvText(config, jdbcTemplate, sqlParamMap);
		return createUpload(config, csvText);
	}
	
	public static String create(String configXmlFile, Map<String, Object> sqlParamMap) 
			throws ServiceException, Exception {
		ExportDataConfig config = getConfig(configXmlFile);
		String csvText = processCsvText(config, DataUtils.getJdbcTemplate(), sqlParamMap);
		return createUpload(config, csvText);
	}	
	
	private static String createUpload(ExportDataConfig config, String csvText) 
			throws ServiceException, Exception {
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				csvText.getBytes(), 
				config.getId() + ".csv");
	}
	
	private static String processCsvText(ExportDataConfig config, NamedParameterJdbcTemplate jdbcTemplate, 
			Map<String, Object> sqlParamMap) throws Exception {
		logger.info("export-Id: " + config.getId() + " name: " + config.getName());
		StringBuilder out = new StringBuilder();
		out.append(config.getTitle()).append("\r\n");
		List<Map<String, Object>> results = jdbcTemplate.queryForList(config.getSql(), sqlParamMap);
		for (int i=0; results!=null && i<results.size(); i++) {
			Map<String, Object> dataMap = results.get(i);
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				if ( entry.getValue() != null ) {
					String str = "";
					if (entry.getValue() instanceof byte[]) { // blob text
						str = new String( (byte[])entry.getValue() , Constants.BASE_ENCODING );
					} else {
						str = String.valueOf( entry.getValue() );
					}					
					if (config.isEscapeCsv()) {
						//str = StringEscapeUtils.escapeCsv(str);
						str = SimpleUtils.escapeCsv(str);
					}
					if (StringUtils.isBlank(str)) {
						str = " ";
					}
					out.append("\"").append(str).append("\"");					
				} else {
					out.append(" ");
				}
				out.append( config.getSeparateSymbol() );
			}
			out.append("\r\n");
		}
		return out.toString();
	}

}
