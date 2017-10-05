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
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.AggregationMethodVariable;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;
import com.netsteadfast.greenstep.vo.KpiVO;

@SuppressWarnings("unchecked")
public class AggregationMethodUtils {
	private static IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService;
	private static ThreadLocal<Map<String, AggregationMethodVO>> aggrMethodPkThreadLocal = 
			new ThreadLocal<Map<String, AggregationMethodVO>>();
	private static ThreadLocal<Map<String, AggregationMethodVO>> aggrMethodUkThreadLocal = 
			new ThreadLocal<Map<String, AggregationMethodVO>>();
	
	static {
		aggregationMethodService = (IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String>)
				AppContext.getBean("bsc.service.AggregationMethodService");
		aggrMethodPkThreadLocal.set( new HashMap<String, AggregationMethodVO>() );
		aggrMethodUkThreadLocal.set( new HashMap<String, AggregationMethodVO>() );			
	}
	
	private static AggregationMethodVO findAggregationMethod(String oid) throws ServiceException, Exception {
		Map<String, AggregationMethodVO> datas = aggrMethodPkThreadLocal.get();
		if (datas!=null) {
			if ( datas.get(oid) != null ) {
				return datas.get(oid);
			}
		}
		AggregationMethodVO aggr = new AggregationMethodVO();
		aggr.setOid(oid);
		DefaultResult<AggregationMethodVO> result = aggregationMethodService.findObjectByOid(aggr);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		aggr = result.getValue();
		aggrMethodPkThreadLocal.get().put(oid, aggr);
		return aggr;
	}
	
	private static AggregationMethodVO findAggregationMethodByUK(String id) throws ServiceException, Exception {
		Map<String, AggregationMethodVO> datas = aggrMethodUkThreadLocal.get();
		if (datas!=null) {
			if ( datas.get(id) != null ) {
				return datas.get(id);
			}
		}
		AggregationMethodVO aggr = new AggregationMethodVO();
		aggr.setAggrId( id );
		DefaultResult<AggregationMethodVO> result = aggregationMethodService.findByUK(aggr);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		aggr = result.getValue();
		aggrMethodUkThreadLocal.get().put(id, aggr);
		return aggr;
	}	
	
	private static Map<String, Object> getParameter(KpiVO kpi, String frequency) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(AggregationMethodVariable.KPI, kpi);
		parameter.put(AggregationMethodVariable.SCORE, 0.0f);
		if (!StringUtils.isBlank(frequency)) {
			parameter.put(AggregationMethodVariable.FREQUENCY, frequency);
		}
		return parameter;
	}
	
	private static Map<String, Object> getScore(String type, String expression, KpiVO kpi, String frequency) throws Exception {
		if ( kpi == null || kpi.getMeasureDatas() == null ) {
			throw new Exception("no measure data!");
		}
		String bscExpression = ScriptExpressionUtils.replaceFormulaExpression(type, expression);
		Map<String, Object> results = new HashMap<String, Object>();
		results.put(AggregationMethodVariable.SCORE, null);
		results = ScriptExpressionUtils.execute(
				type, bscExpression, results, getParameter(kpi, frequency) );
		// System.out.println("results=" +  results );
		return results;		
	}
	
	public static float processDefaultMode(KpiVO kpi) throws Exception {
		Map<String, Object> results = getScore(kpi.getAggregationMethod().getType(), kpi.getAggregationMethod().getExpression1(), kpi, null);
		Object value = results.get( AggregationMethodVariable.SCORE );
		if ( null == value ) {
			return 0.0f;
		}
		if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {			
			return 0.0f;
		}
		return NumberUtils.toFloat( String.valueOf(value), 0.0f);			
	}
	
	public static float processDateRangeMode(KpiVO kpi, String frequency) throws Exception {
		Map<String, Object> results = getScore(kpi.getAggregationMethod().getType(), kpi.getAggregationMethod().getExpression2(), kpi, frequency);
		Object value = results.get( AggregationMethodVariable.SCORE );
		if ( null == value ) {
			return 0.0f;
		}
		if ( !NumberUtils.isCreatable( String.valueOf(value) ) ) {			
			return 0.0f;
		}
		return NumberUtils.toFloat( String.valueOf(value), 0.0f);	
	}		
	
	public static Object processDefaultModeByOid(String aggregationMethodOid, KpiVO kpi) throws ServiceException, Exception {
		AggregationMethodVO aggr = findAggregationMethod(aggregationMethodOid);
		return getScore(aggr.getType(), aggr.getExpression1(), kpi, null);
	}
	
	public static Object processDateRangeModeByOid(String aggregationMethodOid, KpiVO kpi, String frequency) throws ServiceException, Exception {
		AggregationMethodVO aggr = findAggregationMethod(aggregationMethodOid);
		return getScore(aggr.getType(), aggr.getExpression2(), kpi, frequency);
	}		
	
	public static Object processDefaultModeByUK(String id, KpiVO kpi) throws ServiceException, Exception {
		AggregationMethodVO aggr = findAggregationMethodByUK(id);
		return getScore(aggr.getType(), aggr.getExpression1(), kpi, null);
	}
	
	public static Object processDateRangeModeByUK(String id, KpiVO kpi, String frequency) throws ServiceException, Exception {
		AggregationMethodVO aggr = findAggregationMethodByUK(id);
		return getScore(aggr.getType(), aggr.getExpression2(), kpi, frequency);
	}		
	
	public static AggregationMethodVO findSimpleByOid(String oid) throws ServiceException, Exception {
		DefaultResult<AggregationMethodVO> result = aggregationMethodService.findSimpleByOid(oid);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}		
		return result.getValue();
	}
	
	public static AggregationMethodVO findSimpleById(String id) throws ServiceException, Exception {
		DefaultResult<AggregationMethodVO> result = aggregationMethodService.findSimpleById(id);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}	
		return result.getValue();
	}	
	
	public static String getNameByOid(String oid) throws ServiceException, Exception {
		return findSimpleByOid(oid).getName();
	}
	
	public static String getNameByAggrId(String id) throws ServiceException, Exception {
		return findSimpleById(id).getName();
	}	
	
	public Map<String, String> findMap(boolean pleaseSelect) throws ServiceException, Exception {
		return aggregationMethodService.findForMap(pleaseSelect);
	}
	
}
