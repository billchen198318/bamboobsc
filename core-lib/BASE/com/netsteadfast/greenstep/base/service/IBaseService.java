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

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;

/**
 * 
 * @author CXN
 *
 * @param <T>	T is VO
 * @param <E>	E is PO
 * @param <E>	PK 
 */
public interface IBaseService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends ISimpleService<E, PK> {
	
	public void doMapper(Object sourceObject, Object targetObject, String mapperId) throws org.dozer.MappingException, ServiceException;
	
	public DefaultResult<T> saveIgnoreUK(T object) throws ServiceException, Exception;
	public DefaultResult<T> mergeIgnoreUK(T object) throws ServiceException, Exception;		
	public DefaultResult<T> findObjectByOid(T object) throws ServiceException, Exception;	
	
	public DefaultResult<T> saveObject(T object) throws ServiceException, Exception;
	public DefaultResult<T> updateObject(T object) throws ServiceException, Exception;
	public DefaultResult<T> mergeObject(T object) throws ServiceException, Exception;
	public DefaultResult<Boolean> deleteObject(T object) throws ServiceException, Exception;
	
	public List<T> findListVOByParams(Map<String, Object> params) throws ServiceException, Exception;
	
	public DefaultResult<T> findByUK(T object) throws ServiceException, Exception;
	public int countByUK(T object) throws ServiceException, Exception;
	
}
