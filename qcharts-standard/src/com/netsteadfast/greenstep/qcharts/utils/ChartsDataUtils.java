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
package com.netsteadfast.greenstep.qcharts.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapper;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapperSet;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperSetService;
import com.netsteadfast.greenstep.vo.DataQueryMapperSetVO;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;

@SuppressWarnings("unchecked")
public class ChartsDataUtils {
	
	private static IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService;
	private static IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService;
	
	static {		
		dataQueryMapperService = (IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String>)
				AppContext.getBean("qcharts.service.DataQueryMapperService");
		dataQueryMapperSetService = (IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String>)
				AppContext.getBean("qcharts.service.DataQueryMapperSetService");
	}
	
	private static boolean isChartsValue(Object value) {		
		return NumberUtils.isCreatable( String.valueOf(value) );
	}
	
	public static List<Map<String, Object>> getHighchartsPieData(String dataQueryMapperSetOid, 
			List<Map<String, Object>> searchDatas) throws ServiceException, Exception {
		if (StringUtils.isBlank(dataQueryMapperSetOid)) {
			throw new Exception("Mapper config is required!");
		}
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		if (null==searchDatas || searchDatas.size()<1) {
			return data;
		}
		DataQueryMapperSetVO mapperSet = new DataQueryMapperSetVO();
		mapperSet.setOid(dataQueryMapperSetOid);
		DefaultResult<DataQueryMapperSetVO> result = dataQueryMapperSetService.findObjectByOid(mapperSet);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		mapperSet = result.getValue();
		for (Map<String, Object> searchDataMap : searchDatas) {
			String label = (String)searchDataMap.get( mapperSet.getLabelField() );
			Object value = searchDataMap.get( mapperSet.getValueField() );
			if (StringUtils.isBlank(label)) {
				throw new Exception("Mapper label field not found!");
			}
			if (value==null) {
				value = new Integer(0);
			}			
			if (!isChartsValue(value)) {
				throw new Exception("Mapper value field must be numeric!");
			}
			Map<String, Object> itemData = new HashMap<String, Object>();
			itemData.put("name", label);
			itemData.put("y", value);
			data.add(itemData);
		}
		return data;
	}
	
	public static List<String> getHighchartsSeriesCategories(String dataQueryMapperOid, 
			List<Map<String, Object>> searchDatas) throws ServiceException, Exception {
		if (StringUtils.isBlank(dataQueryMapperOid)) {
			throw new Exception("Mapper config is required!");
		}
		List<String> data = new LinkedList<String>();
		if (null==searchDatas || searchDatas.size()<1) {
			return data;
		}		
		DataQueryMapperVO mapper = new DataQueryMapperVO();
		mapper.setOid(dataQueryMapperOid);
		DefaultResult<DataQueryMapperVO> result = dataQueryMapperService.findObjectByOid(mapper);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		mapper = result.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", mapper.getOid());
		List<QcDataQueryMapperSet> mapperSets = dataQueryMapperSetService.findListByParams(paramMap);
		if ( null == mapperSets || mapperSets.size()<1 ) {
			throw new Exception("Mapper config is required!");
		}
		for (QcDataQueryMapperSet mapperSet : mapperSets) {
			for (Map<String, Object> searchDataMap : searchDatas) {
				String label = (String)searchDataMap.get( mapperSet.getLabelField() );
				if (StringUtils.isBlank(label)) {
					throw new Exception("Mapper label field not found!");
				}
				if (!data.contains(label)) {
					data.add(label);
				}
			}			
		}		
		return data;
	}
	
	/*
	public static List<String> getHighchartsBarCategories(String dataQueryMapperSetOid, 
			List<Map<String, Object>> searchDatas) throws ServiceException, Exception {
		if (StringUtils.isBlank(dataQueryMapperSetOid)) {
			throw new Exception("Mapper config is required!");
		}
		List<String> data = new LinkedList<String>();
		if (null==searchDatas || searchDatas.size()<1) {
			return data;
		}
		DataQueryMapperSetVO mapperSet = new DataQueryMapperSetVO();
		mapperSet.setOid(dataQueryMapperSetOid);
		DefaultResult<DataQueryMapperSetVO> result = dataQueryMapperSetService.findObjectByOid(mapperSet);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		mapperSet = result.getValue();
		for (Map<String, Object> searchDataMap : searchDatas) {
			String label = (String)searchDataMap.get( mapperSet.getLabelField() );
			if (StringUtils.isBlank(label)) {
				throw new Exception("Mapper label field not found!");
			}
			data.add(label);
		}		
		return data;
	}
	*/

	public static List<Map<String, Object>> getHighchartsSeriesData(String dataQueryMapperOid, List<String> categories, 
			List<Map<String, Object>> searchDatas) throws ServiceException, Exception {
		if (StringUtils.isBlank(dataQueryMapperOid)) {
			throw new Exception("Mapper config is required!");
		}
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		if (null==searchDatas || searchDatas.size()<1 || null==categories || categories.size()<1 ) {
			return data;
		}
		DataQueryMapperVO mapper = new DataQueryMapperVO();
		mapper.setOid(dataQueryMapperOid);
		DefaultResult<DataQueryMapperVO> result = dataQueryMapperService.findObjectByOid(mapper);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		mapper = result.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", mapper.getOid());
		List<QcDataQueryMapperSet> mapperSets = dataQueryMapperSetService.findListByParams(paramMap);
		if ( null == mapperSets || mapperSets.size()<1 ) {
			throw new Exception("Mapper config is required!");
		}
		for (QcDataQueryMapperSet mapperSet : mapperSets) {
			List<Object> values = new LinkedList<Object>();
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("name", mapperSet.getValueField());
			item.put("data", values);
			for (Map<String, Object> searchDataMap : searchDatas) {
				
				for (String categorie : categories) {
					String label = (String)searchDataMap.get( mapperSet.getLabelField() );
					if (!categorie.equals(label)) {
						continue;
					}
					Object value = searchDataMap.get( mapperSet.getValueField() );
					if (value==null) {
						value = new Integer(0);
					}			
					if (!isChartsValue(value)) {
						throw new Exception("Mapper value field must be numeric!");
					}
					values.add(value);										
				}
				
			}
			data.add(item);
		}		
		return data;
	}
	
	/*
	public static List<Map<String, Object>> getHighchartsBarData(String dataQueryMapperSetOid, List<String> categories, 
			List<Map<String, Object>> searchDatas) throws ServiceException, Exception {
		if (StringUtils.isBlank(dataQueryMapperSetOid)) {
			throw new Exception("Mapper config is required!");
		}
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		if (null==searchDatas || searchDatas.size()<1 || null==categories || categories.size()<1 ) {
			return data;
		}
		DataQueryMapperSetVO mapperSet = new DataQueryMapperSetVO();
		mapperSet.setOid(dataQueryMapperSetOid);
		DefaultResult<DataQueryMapperSetVO> result = dataQueryMapperSetService.findObjectByOid(mapperSet);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		mapperSet = result.getValue();
		
		List<Object> values = new LinkedList<Object>();
		for (Map<String, Object> searchDataMap : searchDatas) {
			
			for (String categorie : categories) {
				String label = (String)searchDataMap.get( mapperSet.getLabelField() );
				if (categorie.equals(label)) {
					Object value = searchDataMap.get( mapperSet.getValueField() );
					if (value==null) {
						value = new Integer(0);
					}			
					if (!isChartsValue(value)) {
						throw new Exception("Mapper value field must be numeric!");
					}
					values.add(value);
				}
				
			}
			
		}	
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", mapperSet.getValueField());
		item.put("data", values);
		data.add(item);		
		return data;
	}
	*/
	
}
