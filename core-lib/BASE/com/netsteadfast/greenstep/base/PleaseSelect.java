package com.netsteadfast.greenstep.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PleaseSelect {
	
	private static final String _CONFIG = "META-INF/please-select-label-name.json";
	private static String _pleaseSelectDatas = " { } ";
	private static Map<String, Object> _pleaseSelectMap;
	
	static {
		try {
			InputStream is = Constants.class.getClassLoader().getResource( _CONFIG ).openStream();
			_pleaseSelectDatas = IOUtils.toString(is, Constants.BASE_ENCODING);
			is.close();
			is = null;
			_pleaseSelectMap = loadDatas();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null==_pleaseSelectMap) {
				_pleaseSelectMap = new HashMap<String, Object>();
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadDatas() {
		Map<String, Object> datas = null;
		try {
			datas = (Map<String, Object>)new ObjectMapper().readValue( _pleaseSelectDatas, LinkedHashMap.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	public static Map<String, Object> getDataMap() {
		return _pleaseSelectMap;
	}		

	public static String getLabel(String localeLang) {
		String label = (String) _pleaseSelectMap.get(localeLang);
		return (StringUtils.isBlank(label)) ? Constants.HTML_SELECT_NO_SELECT_NAME : label;
	}
	
}
