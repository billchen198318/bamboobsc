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

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.BaseEntityUtil;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.dynamichql.DynamicHql;
import com.netsteadfast.greenstep.util.DataUtils;
import com.netsteadfast.greenstep.util.DynamicHqlUtils;
import com.netsteadfast.greenstep.util.GenericsUtils;

public abstract class BaseDAO<T extends java.io.Serializable, PK extends java.io.Serializable> implements IBaseDAO<T, PK> {
	protected Logger logger=Logger.getLogger(BaseDAO.class);
	protected SessionFactory sessionFactory;
	protected Transaction hbmTransaction;		
	
	private static String MAPPER_DEFINE_ID_SELECT_BY_PARAMS=".selectByParams";
	private static String MAPPER_DEFINE_ID_SELECT_BY_VALUE=".selectByValue";
	
	private SqlSession sqlSession;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected Class<T> entityClass;
	
	
	@SuppressWarnings("unchecked")
	public BaseDAO() {
		super();
		this.entityClass=GenericsUtils.getSuperClassGenricType(getClass());
	}
	
	// -------------------------------------------------------------------------------------------
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	@Required
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	
	@Autowired
	@Required
	@Resource(name="sqlSession")
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	@Required
	@Resource(name="jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	@Autowired
	@Required
	@Resource(name="namedParameterJdbcTemplate")	
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public Connection getConnection() throws Exception {
		return DataUtils.getConnection();
	}
	
	public Session getCurrentSession() throws Exception {
		return sessionFactory.getCurrentSession();
	}
	
	// -------------------------------------------------------------------------------------------
			
	public void updateByNativeSQL(String sql) throws DataAccessException, Exception {
		this.getJdbcTemplate().update(sql);
	}
	
	public void executeByNativeSQL(String sql) throws DataAccessException, Exception {
		this.getJdbcTemplate().execute(sql);
	}
		
	@SuppressWarnings({ "unchecked", "hiding"})
	public <T> Object queryByNativeSQL(String sql, T rowMapper, Object... args) throws DataAccessException, Exception {		
		return (T)this.getJdbcTemplate().queryForObject(sql, (RowMapper<T>)rowMapper, args);		
	}
	
	public int queryByNativeSQL(String sql) throws DataAccessException, Exception {
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql) throws DataAccessException, Exception {
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List queryForListByNativeSQL(String sql, RowMapper rowMapper) throws DataAccessException, Exception {
		return this.getJdbcTemplate().query(sql, rowMapper);
	}
	
	@SuppressWarnings("rawtypes")
	public List queryForListByNativeSQL(String sql, Object[] args) throws DataAccessException, Exception {
		return this.getJdbcTemplate().queryForList(sql, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List queryForListByNativeSQL(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException, Exception {
		return this.getJdbcTemplate().query(sql, args, rowMapper);
	}

	// -------------------------------------------------------------------------------------------
	
	@Override
	public int count(String hql) throws Exception {
		return DataAccessUtils.intResult(this.getCurrentSession().createQuery(hql).list());
	}
	
	@Override
	public int count(String hql, Object... args) throws Exception {
		Query query=this.getCurrentSession().createQuery(hql);
		for (int position=0; args!=null && position<args.length; position++) {
			this.setQueryParams(query, Integer.toString(position), args[position]);
		}
		return DataAccessUtils.intResult(query.list());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findList(final String hql, final int offset, final int length) throws Exception {
		List list=null;
		Session hbmSession=this.getCurrentSession();
		if (hbmSession==null) {
			return list;
		}
		try {
			Query query=hbmSession.createQuery(hql);
			query.setFirstResult(offset);
			query.setMaxResults(length);
			list=query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	/**
	 * 頁面查詢grid資料用 
	 * 
	 * map 放入 key為 persisent obj 欄位名稱
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <RO extends QueryResult<List<VO>>, VO extends java.io.Serializable> QueryResult<List<VO>> findPageQueryResult(
			String findHQL, String countHQL, 
			Map<String, Object> params, 
			int offset, int limit) throws Exception {
		
		QueryResult<List<VO>> result=new QueryResult<List<VO>>();
		List<VO> list=null;
		long count=0L;
		Session hbmSession = this.getCurrentSession();
		try {
			Query query=hbmSession.createQuery(countHQL);
			if (params!=null) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (entry.getKey().equals(Constants._RESERVED_PARAM_NAME_QUERY_ORDER_BY) 
							|| entry.getKey().equals(Constants._RESERVED_PARAM_NAME_QUERY_SORT_TYPE)) {
						continue;
					}
					this.setQueryParams(query, entry.getKey(), entry.getValue());				
				}				
			}
			count=((Long)query.uniqueResult()).longValue();	
			int newOffset=offset;
			if (count>0) {
				if (offset>=count) { // 改掉原本頁面上一次查詢的欄位值 , 2014-10-08 offset>count
					newOffset=(int)(count-Long.valueOf(limit));
					if (newOffset<0) {
						newOffset=0;
					}
				}				
				query=hbmSession.createQuery(findHQL);
				if (params!=null) {
					for (Map.Entry<String, Object> entry : params.entrySet()) {
						if (entry.getKey().equals(Constants._RESERVED_PARAM_NAME_QUERY_ORDER_BY) 
								|| entry.getKey().equals(Constants._RESERVED_PARAM_NAME_QUERY_SORT_TYPE)) {
							continue;
						}
						this.setQueryParams(query, entry.getKey(), entry.getValue());
					}								
				}
				query.setFirstResult(newOffset); //offset
				query.setMaxResults(limit);
				list=(List<VO>)query.list();					
			}
			result.setRowCount(count);
			result.setOffset(newOffset); // 2017-06-30 bug fix, result.setOffset(offset);
			result.setLimit(limit);
			result.setFindHQL(findHQL);
			result.setCountHQL(countHQL);			
			if (list!=null && list.size()>0) {
				result.setValue(list);
			} else {
				result.setSystemMessage(
						new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSystemMessage(new SystemMessage(e.getMessage()));
		}				
		return result;
	}
	
	/**
	 * 頁面查詢grid資料用 
	 * 
	 * map 放入 key為 persisent obj 欄位名稱
	 * 
	 */
	public <RO extends QueryResult<List<VO>>, VO extends java.io.Serializable> QueryResult<List<VO>> findPageQueryResultByQueryName(
			String pageQueryName, Map<String, Object> params, int offset, int limit) throws Exception {
		
		String selectQueryName = pageQueryName + "-select";
		String countQueryName = pageQueryName + "-count";
		return this.findPageQueryResult(
				this.getDynamicHql(selectQueryName, params), 
				this.getDynamicHql(countQueryName, params), 
				params, 
				offset, 
				limit);		
	}
	
	/**
	 * for public QueryResult getList... doInHibernate
	 * @param query		 JPA-Style : from TB_ACCOUNT where account = ?0 
	 * @param position   JPA-Style : "0", "1" .....
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	private void setQueryParams(Query query, String position, Object params) {
		if (params instanceof java.lang.String ) {
			query.setString(position, (java.lang.String)params);
			return;
		}
		if (params instanceof java.lang.Character) {
			query.setCharacter(position, (java.lang.Character)params);
			return;
		}
		if (params instanceof java.lang.Double ) {
			query.setDouble(position, (java.lang.Double)params);	
			return;
		}							
		if (params instanceof java.lang.Byte ) {
			query.setByte(position, (java.lang.Byte)params);
			return;
		}														
		if (params instanceof java.lang.Integer ) {
			query.setInteger(position, (java.lang.Integer)params);
			return;
		}
		if (params instanceof java.lang.Long ) {
			query.setLong(position, (java.lang.Long)params);
			return;
		}							
		if (params instanceof java.lang.Boolean ) {
			query.setBoolean(position, (java.lang.Boolean)params);
			return;
		}
		if (params instanceof java.math.BigDecimal ) {
			query.setBigDecimal(position, (java.math.BigDecimal)params);
			return;
		}							
		if (params instanceof java.util.Date ) {
			query.setDate(position, (java.util.Date)params);
			return;
		}		
		if (params instanceof java.util.List ) {
			List listParams=(List)params;
			this.setQueryParamsOfList(query, position, listParams);
			return;
		}
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setQueryParamsOfList(Query query, String position, List listParams) {
		
		if (listParams==null || listParams.size()<1) {
			return;
		}
		if (listParams.get(0) instanceof String ) {
			query.setParameterList(position, (List<String>)listParams);
			return;
		}
		if (listParams.get(0) instanceof Character) {
			query.setParameterList(position, (List<Character>)listParams);
			return;
		}		
		if (listParams.get(0) instanceof BigDecimal ) {
			query.setParameterList(position, (List<BigDecimal>)listParams);
			return;
		}		
		if (listParams.get(0) instanceof Integer ) {
			query.setParameterList(position, (List<Integer>)listParams);
			return;
		}
		if (listParams.get(0) instanceof Long ) {
			query.setParameterList(position, (List<Long>)listParams);
			return;
		}
		
	}

	@Override
	public T save(T entityObject) throws Exception {
		this.getCurrentSession().save(entityObject);
		return entityObject;
	}
	
	public T persist(T entityObject) throws Exception {
		this.getCurrentSession().persist(entityObject);
		return entityObject;
	}

	@Override
	public T update(T entityObject) throws Exception {
		this.getCurrentSession().update(entityObject);
		return entityObject;
	}

	@Override
	public T merge(T entityObject) throws Exception {
		this.getCurrentSession().merge(entityObject);
		return entityObject;
	}

	@Override
	public T delete(T entityObject) throws Exception {
		this.getCurrentSession().delete(entityObject);
		return entityObject;
	}
	
	public void clear() throws Exception {
		this.getCurrentSession().clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findByOid(T entityObj) throws Exception {
		return this.findByPK((PK)BaseEntityUtil.getPKOneValue((BaseEntity<PK>)entityObj));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int countByOid(T entityObj) throws Exception {
		return this.countByPK((PK)BaseEntityUtil.getPKOneValue((BaseEntity<PK>)entityObj));
	}
	
	/**
	 * 只提供給 deleteByPK, findByPK, countByPK 帶入pkMap參數的這3個method使用
	 * findByBaseEntityUK, countByBaseEntityUK 帶入ukMap參數
	 * 
	 * @param hqlHeadCmd
	 * @param pkMap
	 * @return
	 * @throws Exception
	 */
	private Query getQueryByKeyMap(String hqlHeadCmd, Map<String, Object> pkMap) throws Exception {
		StringBuilder hql=new StringBuilder();
		hql.append(hqlHeadCmd).append(" from ").append(this.getPersisentName()).append(" where 1=1 ");
		for (Map.Entry<String, Object> entry : pkMap.entrySet()) {
			hql.append(" and ").append(entry.getKey()).append("=:").append(entry.getKey());
		}
		Query query=this.getCurrentSession().createQuery(hql.toString());
		for (Map.Entry<String, Object> entry : pkMap.entrySet()) {
			this.setQueryParams(query, entry.getKey(), entry.getValue());
		}		
		return query;
	}
	
	public boolean deleteByPK(PK pk) throws Exception {
		boolean status=false;
		T entity=this.findByPK(pk);
		this.delete(entity);
		status=true;
		return status;
	}
	
	public boolean deleteByPK(Map<String, Object> pkMap) throws Exception {
		boolean status=false;
		if (pkMap==null || pkMap.size()<1) {
			return status;
		}
		Query query=this.getQueryByKeyMap("delete", pkMap);
		if (query.executeUpdate()>0) {
			status=true;
		}
		return status;
	}
	
	public T findByPK(PK pk) throws Exception {
		return (T)this.getCurrentSession().get(this.entityClass, pk);
	}
	
	@SuppressWarnings("unchecked")
	public T findByPK(Map<String, Object> pkMap) throws Exception {
		if (pkMap==null || pkMap.size()<1) {
			return null;
		}		
		Query query=this.getQueryByKeyMap("", pkMap);
		return (T)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public int countByPK(PK pk) throws Exception {
		return DataAccessUtils.intResult(
				this.getCurrentSession().createQuery(
						" select count(*) from "+this.getPersisentName() + 
						" where " + BaseEntityUtil.getPKOneName((BaseEntity<PK>)entityClass.newInstance()) + "=?0 ")
						.setString("0", (String)pk).list());		
	}
	
	public int countByPK(Map<String, Object> pkMap) throws Exception {
		if (pkMap==null || pkMap.size()<1) {
			return 0;
		}		
		Query query=this.getQueryByKeyMap("select count(*)", pkMap);
		return DataAccessUtils.intResult(query.list());		
	}
	
	/**
	 * 只提供給 countByParams, findListByParams 用
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private Query getQueryByParams(
			String type, 
			Map<String, Object> params, 
			Map<String, String> likeParams,
			Map<String, String> orderByParams,
			Map<String, CustomeOperational> customOperParams) throws Exception {
		
		StringBuilder sb=new StringBuilder();
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			sb.append("select count(*) from ").append(this.getPersisentName()).append(" ");
		} else {
			sb.append("from ").append(this.getPersisentName()).append(" ");
		}
		int field=0;
		if (params!=null && params.size()>0) { // set hql
			sb.append(" where 1=1 ");
	        for (Map.Entry<String, Object> entry : params.entrySet()) {
	        	sb.append(" and ").append(entry.getKey()).append("=?").append(field).append(" ");
	        	field++;
	        }
		}
		if (customOperParams!=null && customOperParams.size()>0) { // set hql with >= , <= , >, < , <>
			if (params==null || params.size()<1) {
				sb.append(" where 1=1 ");
			}			
			for (Map.Entry<String, CustomeOperational> entry : customOperParams.entrySet()) {
				sb.append(" and ").append(entry.getValue().getField()).append(entry.getValue().getOp()).append("?").append(field).append(" ");
				field++;
			}
		}
		if (likeParams!=null && likeParams.size()>0) { // set hql like
			if ( (params==null || params.size()<1) && (customOperParams==null || customOperParams.size()<1) ) {
				sb.append(" where 1=1 ");
			}
	        for (Map.Entry<String, String> entry : likeParams.entrySet()) {
	        	sb.append(" and ").append(entry.getKey()).append(" like ?").append(field).append(" ");
	        	field++;
	        }			
		}
		if (orderByParams!=null && orderByParams.size()>0) { // order by
			sb.append(" order by ");
			int order=0;
			for (Map.Entry<String, String> entry : orderByParams.entrySet()) {
				sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
				if (++order<orderByParams.size()) {
					sb.append(" , ");
				}				
			}
		}
		field=0;
		Query query=this.getCurrentSession().createQuery(sb.toString());
		if (params!=null && params.size()>0) { // set query params value
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				this.setQueryParams(query, String.valueOf(field), entry.getValue());
				field++;
			}
		}
		if (customOperParams!=null && customOperParams.size()>0) { // set hql with >= , <= , >, < , <>
			for (Map.Entry<String, CustomeOperational> entry : customOperParams.entrySet()) {
				this.setQueryParams(query, String.valueOf(field), entry.getValue().getValue());
				field++;
			}
		}
		if (likeParams!=null && likeParams.size()>0) { // set query params like value
			for (Map.Entry<String, String> entry : likeParams.entrySet()) {
				this.setQueryParams(query, String.valueOf(field), entry.getValue());
				field++;
			}			
		}
		return query;
	}
	
	/**
	 * 用map 提供的參數select資料
	 * 
	 * map 放入 key為 persisent obj 欄位名稱
	 * map 放入 value為 persisent obj 欄位的值
	 * 
	 * likeParams 的 value 如 '%test%' 'test%'
	 * 
	 * @param params
	 * @param likeParams
	 * @return
	 * @throws Exception
	 */	
	public long countByParams(
			Map<String, Object> params, Map<String, String> likeParams) throws Exception {
		return DataAccessUtils.longResult(this.getQueryByParams(Constants.QUERY_TYPE_OF_COUNT, params, likeParams, null, null).list());
	}
	
	/**
	 * 用map 提供的參數select資料
	 * 
	 * map 放入 key為 persisent obj 欄位名稱
	 * map 放入 value為 persisent obj 欄位的值
	 * 
	 * likeParams 的 value 如 '%test%' 'test%'
	 * 
	 * orderParams 的 value 如 {"account欄位名稱", "asc正排序"} , {"name欄位名稱", "desc倒排序"}  
	 * 
	 * @param params
	 * @param likeParams
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public List<T> findListByParams(
			Map<String, Object> params, Map<String, String> likeParams, Map<String, String> orderParams) throws Exception {
		return (List<T>)this.getQueryByParams(Constants.QUERY_TYPE_OF_SELECT, params, likeParams, orderParams, null).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListByParams2(
			Map<String, CustomeOperational> customOperParams) throws Exception {
		return (List<T>)this.getQueryByParams(Constants.QUERY_TYPE_OF_SELECT, null, null, null, customOperParams).list();
	}	
	
	@SuppressWarnings("unchecked")
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, CustomeOperational> customOperParams) throws Exception {
		return (List<T>)this.getQueryByParams(Constants.QUERY_TYPE_OF_SELECT, params, null, null, customOperParams).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams) throws Exception {
		return (List<T>)this.getQueryByParams(Constants.QUERY_TYPE_OF_SELECT, params, likeParams, null, customOperParams).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams,
			Map<String, String> orderParams) throws Exception {
		return (List<T>)this.getQueryByParams(Constants.QUERY_TYPE_OF_SELECT, params, likeParams, orderParams, customOperParams).list();
	}
	
	@SuppressWarnings("unchecked")
	public T findByUK(T entityObject) throws Exception {
		return this.findByEntityUK(BaseEntityUtil.getUKParameter((BaseEntity<PK>)entityObject));
	}
	
	@SuppressWarnings("unchecked")
	public int countByUK(T entityObject) throws Exception {
		return this.countByEntityUK(BaseEntityUtil.getUKParameter((BaseEntity<PK>)entityObject));
	}
	
	@SuppressWarnings("unchecked")
	public T findByEntityUK(Map<String, Object> ukMap) throws Exception {
		if (ukMap==null || ukMap.size()<1) {
			return null;
		}		
		Query query=this.getQueryByKeyMap("", ukMap);
		return (T)query.uniqueResult();
	}
	
	public int countByEntityUK(Map<String, Object> ukMap) throws Exception {
		if (ukMap==null || ukMap.size()<1) {
			return 0;
		}		
		Query query=this.getQueryByKeyMap("select count(*)", ukMap);
		return DataAccessUtils.intResult(query.list());
	}
	
	/**
	 * return a zero persisent object
	 */
	@SuppressWarnings("unchecked")
	public T getZeroPO() throws Exception {
		T entity=this.entityClass.newInstance();
		if (entity instanceof BaseEntity) {
			((BaseEntity<PK>)entity).setOid(null);
		}
		return entity;
	}
	
	public String getPersisentName() {
		return this.entityClass.getSimpleName();
	}
	
	// -------------------------------------------------------------------------------------------
	
	public String getIbatisMapperNameSpace() {
		return this.entityClass.getSimpleName();
	}
	
	@Override
	public List<T> ibatisSelectListByParams(Map<String, Object> params) throws Exception {
		return this.getSqlSession().selectList(this.getIbatisMapperNameSpace()+MAPPER_DEFINE_ID_SELECT_BY_PARAMS, params);
	}

	@Override
	public T ibatisSelectOneByValue(T valueObj) throws Exception {
		return this.getSqlSession().selectOne(this.getIbatisMapperNameSpace()+MAPPER_DEFINE_ID_SELECT_BY_VALUE, valueObj);
	}
	
	public DynamicHql getDynamicHqlResource(String resource) throws Exception {
		return DynamicHqlUtils.loadResource(resource);
	}
	
	public String getDynamicHql(String queryName, Map<String, Object> paramMap) throws Exception {
		return this.getDynamicHql(this.getPersisentName()+"-dynamic-hql.xml", queryName, paramMap);
	}
	
	public String getDynamicHql(String resource, String queryName, Map<String, Object> paramMap) throws Exception {
		return DynamicHqlUtils.process(resource, queryName, paramMap);
	}
	
}
