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
package com.netsteadfast.greenstep.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.IDynamicHqlProvide;
import com.netsteadfast.greenstep.base.model.QueryResult;

public interface IBaseDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IDynamicHqlProvide {
	public int count(String hql) throws Exception;
	public int count(String hql, Object... args) throws Exception;
	@SuppressWarnings("rawtypes")
	public List findList(final String hql, final int offset, final int length) throws Exception;	
	public <RO extends QueryResult<List<VO>>, VO extends java.io.Serializable> QueryResult<List<VO>> findPageQueryResult(
			String findHQL, String countHQL, 
			Map<String, Object> params, 
			int offset, int limit) throws Exception;
	public <RO extends QueryResult<List<VO>>, VO extends java.io.Serializable> QueryResult<List<VO>> findPageQueryResultByQueryName(
			String pageQueryName, Map<String, Object> params, int offset, int limit) throws Exception;	
	public T save(T entityObject) throws Exception;
	public T update(T entityObject) throws Exception;
	public T persist(T entityObject) throws Exception;
	public T merge(T entityObject) throws Exception;
	public T delete(T entityObj) throws Exception;
	
	public void clear() throws Exception;
	
	public T findByOid(T entityObj) throws Exception;
	public int countByOid(T entityObj) throws Exception;
	
	public boolean deleteByPK(Map<String, Object> pkMap) throws Exception;
	public T findByPK(Map<String, Object> pkMap) throws Exception; 
	public int countByPK(Map<String, Object> pkMap) throws Exception; 
	public boolean deleteByPK(PK pk) throws Exception;
	public T findByPK(PK pk) throws Exception; 
	public int countByPK(PK pk) throws Exception; 	
	
	public long countByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams) throws Exception;
	public List<T> findListByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams,
			Map<String, String> orderParams) throws Exception;
	public List<T> findListByParams2(
			Map<String, CustomeOperational> customOperParams) throws Exception;	
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, CustomeOperational> customOperParams) throws Exception;
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams) throws Exception;	
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams,
			Map<String, String> orderParams) throws Exception;	
	
	public T findByUK(T entityObject) throws Exception;
	public int countByUK(T entityObject) throws Exception;
	public T findByEntityUK(Map<String, Object> ukMap) throws Exception;
	public int countByEntityUK(Map<String, Object> ukMap) throws Exception;
	
	public T getZeroPO() throws Exception;
	
	public void updateByNativeSQL(String sql) throws DataAccessException, Exception;
	public void executeByNativeSQL(String sql) throws DataAccessException, Exception;
	@SuppressWarnings("hiding")
	public <T> Object queryByNativeSQL(String sql, T rowMapper, Object... args) throws DataAccessException, Exception;
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql) throws DataAccessException, Exception;
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql, RowMapper rowMapper) throws DataAccessException, Exception;
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql, Object[] args) throws DataAccessException, Exception;
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException, Exception;	
	
	public String getIbatisMapperNameSpace();		
	public List<T> ibatisSelectListByParams(Map<String, Object> params) throws Exception;
	public T ibatisSelectOneByValue(T valueObj) throws Exception;
	/*
	public boolean ibatisInsert(T valueObj) throws Exception;
	public boolean ibatisUpdate(T valueObj) throws Exception;
	public boolean ibatisDelete(T valueObj) throws Exception;
	*/
	
	public String getDynamicHql(String queryName, Map<String, Object> paramMap) throws Exception;
	
}
