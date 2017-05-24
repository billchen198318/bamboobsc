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
package com.netsteadfast.greenstep.base.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.IDynamicHqlProvide;

/**
 * 
 * @author CXN
 *
 * @param <T>	T is VO
 * @param <E>	E is PO
 * @param <E>	PK 
 */
public interface IBaseService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IDynamicHqlProvide {
	/*
	 * // FIXME 改用 Apache Shiro 取代
	public void setUserLocalAccount(Object account);
	public Object getUserLocalAccount();
	*/
	public String getAccountId();
	public String generateOid();
	public String defaultString(String source);
	public Date generateDate();
	public void copyProperties(Object source, Object target) throws org.springframework.beans.BeansException;
	public void doMapper(Object sourceObject, Object targetObject, String mapperId) throws org.dozer.MappingException, ServiceException;
	public void fillToValueObject(
			Object destObject, Object origObject) throws ServiceException, IllegalAccessException, InvocationTargetException; 
	public void fillToPersisentObject(
			Object destObject, Object origObject) throws ServiceException, IllegalAccessException, InvocationTargetException;
	
	// ------------------------------------------------------------------------------------
	
	public E save(E entityObject) throws ServiceException, Exception;		
	public E update(E entityObject) throws ServiceException, Exception;
	public E merge(E entityObject) throws ServiceException, Exception;
	public E delete(E entityObject) throws ServiceException, Exception;
	public E findByOid(E entityObject) throws ServiceException, Exception;
	public int countByOid(E entityObject) throws ServiceException, Exception;
	
	/**
	 * 使用 REQUIRES_NEW
	 * 
	 * @param entityObject
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public E saveRequiresNew(E entityObject) throws ServiceException, Exception;
	
	/**
	 * 使用 REQUIRES_NEW
	 * 
	 * @param entityObject
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public E mergeRequiresNew(E entityObject) throws ServiceException, Exception;
	
	public boolean deleteByPK(String pk) throws ServiceException, Exception;
	public E findByPK(String pk) throws ServiceException, Exception;
	public int countByPK(String pk) throws ServiceException, Exception;	
	public boolean deleteByPK(E entityObject) throws ServiceException, Exception;
	public E findByPK(E entityObject) throws ServiceException, Exception;
	public int countByPK(E entityObject) throws ServiceException, Exception;
	
	public boolean deleteByPKng(PK pk) throws ServiceException, Exception;
	public E findByPKng(PK pk) throws ServiceException, Exception;
	public int countByPKng(PK pk) throws ServiceException, Exception;		
	
	public long countByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams) throws ServiceException, Exception;
	public List<E> findListByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams) throws ServiceException, Exception;
	public List<E> findListByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams,
			Map<String, String> orderParams) throws ServiceException, Exception;	
	public List<E> findListByParams2(
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception;	
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception;
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception;
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams,
			Map<String, String> orderParams) throws ServiceException, Exception;	
	public long countByParams(Map<String, Object> params) throws ServiceException, Exception;
	public List<E> findListByParams(Map<String, Object> params) throws ServiceException, Exception;
	public List<T> findListVOByParams(Map<String, Object> params) throws ServiceException, Exception;
	
	public DefaultResult<T> saveIgnoreUK(T object) throws ServiceException, Exception;
	public DefaultResult<T> mergeIgnoreUK(T object) throws ServiceException, Exception;		
	public DefaultResult<T> findObjectByOid(T object) throws ServiceException, Exception;	
	
	public DefaultResult<T> saveObject(T object) throws ServiceException, Exception;
	public DefaultResult<T> updateObject(T object) throws ServiceException, Exception;
	public DefaultResult<T> mergeObject(T object) throws ServiceException, Exception;
	public DefaultResult<Boolean> deleteObject(T object) throws ServiceException, Exception;
	
	public void hibernateSessionClear() throws Exception;
	
	public DefaultResult<T> findByUK(T object) throws ServiceException, Exception;
	public int countByUK(T object) throws ServiceException, Exception;
	
	public E findByEntityUK(E entityObject) throws ServiceException, Exception;
	public int countByEntityUK(E entityObject) throws ServiceException, Exception;
	
	// ------------------------------------------------------------------------------------
	
	public DefaultResult<List<E>> ibatisSelectListByParams(Map<String, Object> params) throws ServiceException, Exception;
	public DefaultResult<E> ibatisSelectOneByValue(E valueObj) throws ServiceException, Exception;
	/*
	public boolean ibatisInsert(E valueObj) throws ServiceException, Exception;
	public boolean ibatisUpdate(E valueObj) throws ServiceException, Exception;
	public boolean ibatisDelete(E valueObj) throws ServiceException, Exception;
	*/
	
	// ------------------------------------------------------------------------------------
	
	public String getDynamicHql(String queryName, Map<String, Object> paramMap) throws Exception;
	
}
