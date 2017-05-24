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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.BaseValueObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.util.GenericsUtils;

/**
 * 
 * @author CXN
 *
 * @param <T>	T is VO
 * @param <E>	E is PO
 * @param <E>	PK 
 */
public abstract class BaseService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends SimpleService<E, PK> implements IBaseService<T, E, PK> {
	
	protected Mapper mapper;	
	
	public BaseService() {
		super();		
	}
	
	public Mapper getMapper() {
		return mapper;
	}
	
	@Autowired
	@Resource(name="mapper")	
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}		
	
	public void doMapper(Object sourceObject, Object targetObject, String mapperId) throws org.dozer.MappingException, ServiceException {
		if (null==mapperId || StringUtils.isBlank(mapperId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DOZER_MAPPER_ID_BLANK));
		}
		this.mapper.map(sourceObject, targetObject, mapperId);
	}
	
	public abstract String getMapperIdPo2Vo();
	public abstract String getMapperIdVo2Po();
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<T> saveIgnoreUK(T object) throws ServiceException, Exception {
		
		return this.saveOrMergeObject("save", false, object);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public DefaultResult<T> mergeIgnoreUK(T object) throws ServiceException, Exception {
		
		return this.saveOrMergeObject("merge", false, object);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DefaultResult<T> saveOrMergeObject(String type, boolean checkUK, T object) throws ServiceException, Exception {
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result=new DefaultResult<T>();
		Class<T> valueObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 0);
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();			
		this.doMapper(object, entityObject, getMapperIdVo2Po());
		if (checkUK) {
			int countByUK=1;
			try {
				countByUK=this.getBaseDataAccessObject().countByUK(entityObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (countByUK>0) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_IS_EXIST));
			}			
		}
		((BaseEntity)entityObject).setOid(this.generateOid());
		((BaseEntity)entityObject).setCuserid(this.getAccountId());
		((BaseEntity)entityObject).setCdate(this.generateDate());
		E insertEntity=null;
		try {
			if ("save".equals(type)) {
				insertEntity=this.getBaseDataAccessObject().save(entityObject);
			} else {
				insertEntity=this.getBaseDataAccessObject().merge(entityObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insertEntity!=null && ((BaseEntity)insertEntity).getOid()!=null ) {
			T insertValueObj=valueObjectClass.newInstance();
			this.doMapper(insertEntity, insertValueObj, getMapperIdPo2Vo());
			result.setValue(insertValueObj);
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS)));
		} else {
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_FAIL)));
		}
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public DefaultResult<T> findObjectByOid(T object) throws ServiceException, Exception {		
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Class<T> valueObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 0);
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();	
		T objectByOid=null;
		try {			
			this.doMapper(object, entityObject, this.getMapperIdVo2Po());
			E entityByOid=this.findByOid(entityObject);	
			if (entityByOid!=null) {
				objectByOid=valueObjectClass.newInstance();
				this.doMapper(entityByOid, objectByOid, this.getMapperIdPo2Vo());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DefaultResult<T> result=new DefaultResult<T>();
		if (objectByOid!=null && !StringUtils.isBlank( ((BaseValueObj)objectByOid).getOid() ) ) {
			result.setValue(objectByOid);			
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}				
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<T> saveObject(T object) throws ServiceException, Exception {
		
		return this.saveOrMergeObject("save", true, object);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public DefaultResult<T> updateObject(T object) throws ServiceException, Exception {
		
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result=new DefaultResult<T>();
		Class<T> valueObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 0);
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();	
		((BaseEntity)entityObject).setOid(((BaseValueObj)object).getOid());
		E findEntity=this.findByOid(entityObject);
		if (findEntity == null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		this.doMapper(object, findEntity, this.getMapperIdVo2Po());
		E updateEntity=null;
		try {
			((BaseEntity)findEntity).setUuserid(this.getAccountId());
			((BaseEntity)findEntity).setUdate(this.generateDate());
			updateEntity=this.getBaseDataAccessObject().update(findEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (updateEntity!=null && ((BaseEntity)updateEntity).getOid()!=null ) {
			T updateValueObj=valueObjectClass.newInstance();
			this.doMapper(updateEntity, updateValueObj, this.getMapperIdPo2Vo());
			result.setValue(updateValueObj);
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)));
		} else {
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)));
		}
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public DefaultResult<T> mergeObject(T object) throws ServiceException, Exception {
		
		return this.saveOrMergeObject("merge", true, object);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<Boolean> deleteObject(T object) throws ServiceException, Exception {
		
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<Boolean> result=new DefaultResult<Boolean>();
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();	
		((BaseEntity)entityObject).setOid(((BaseValueObj)object).getOid());
		boolean status=false;
		if (this.countByOid(entityObject)>0) {
			this.delete(entityObject);
			status=true;
		} 
		result.setValue(status);
		if (status) {
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.DELETE_SUCCESS)));
		} else {
			result.setSystemMessage(
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.DELETE_FAIL)));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public List<T> findListVOByParams(Map<String, Object> params) throws ServiceException, Exception {
		
		List<T> returnList = null;
		List<E> searchList = findListByParams(params, null);
		if (searchList==null || searchList.size()<1) {
			return returnList;
		}
		returnList=new ArrayList<T>();
		for (E entity : searchList) {
			Class<T> objectClass=GenericsUtils.getSuperClassGenricType(getClass(), 0);
			T obj=objectClass.newInstance();	
			this.doMapper(entity, obj, this.getMapperIdPo2Vo());
			returnList.add(obj);
		}
		return returnList;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	@SuppressWarnings("unchecked")
	public DefaultResult<T> findByUK(T object) throws ServiceException, Exception {
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Class<T> valueObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 0);
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();	
		T objectByUK=null;
		try {			
			this.doMapper(object, entityObject, this.getMapperIdVo2Po());
			E entityByUK=this.getBaseDataAccessObject().findByUK(entityObject);
			if (entityByUK!=null) {
				objectByUK=valueObjectClass.newInstance();
				this.doMapper(entityByUK, objectByUK, this.getMapperIdPo2Vo());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		DefaultResult<T> result=new DefaultResult<T>();
		if (objectByUK!=null && !StringUtils.isBlank( ((BaseValueObj)objectByUK).getOid() ) ) {
			result.setValue(objectByUK);			
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}				
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	@SuppressWarnings("unchecked")
	public int countByUK(T object) throws ServiceException, Exception {
		if (object==null || !(object instanceof BaseValueObj) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		int count=0;
		Class<E> entityObjectClass=GenericsUtils.getSuperClassGenricType(getClass(), 1);
		E entityObject=entityObjectClass.newInstance();	
		try {
			this.doMapper(object, entityObject, this.getMapperIdVo2Po());
			count=this.getBaseDataAccessObject().countByUK(entityObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}	
	
}
