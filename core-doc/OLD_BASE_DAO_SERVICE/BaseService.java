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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
//import com.netsteadfast.greenstep.base.UserLocalUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.BaseEntityUtil;
import com.netsteadfast.greenstep.base.model.BaseValueObj;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.DynamicHqlQueryParamHandler;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.dynamichql.DynamicHql;
import com.netsteadfast.greenstep.util.GenericsUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

/**
 * 
 * @author CXN
 *
 * @param <T>	T is VO
 * @param <E>	E is PO
 * @param <E>	PK 
 */
public abstract class BaseService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> 
	implements IBaseService<T, E, PK> {
	
	protected PlatformTransactionManager platformTransactionManager;
	protected TransactionTemplate transactionTemplate;
	protected Mapper mapper;	
	
	public BaseService() {
		super();		
	}
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		if (this.platformTransactionManager==null) {
			this.platformTransactionManager=(PlatformTransactionManager)AppContext.getBean("transactionManager");
		}
		return platformTransactionManager;
	}
	
	@Autowired
	@Resource(name="transactionManager")
	@Required
	public void setPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
	public TransactionTemplate getTransactionTemplate() {
		if (this.transactionTemplate==null) {			
			this.transactionTemplate=(TransactionTemplate)AppContext.getBean("transactionTemplate");
		}
		return transactionTemplate;
	}	
	
	@Autowired
	@Resource(name="transactionTemplate")
	@Required
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	public Mapper getMapper() {
		return mapper;
	}
	
	@Autowired
	@Resource(name="mapper")	
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}		
	
	public boolean isSuperRole() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
			return true;
		}
		return false;
	}
	
	public String getAccountId() {		
		Subject subject = SecurityUtils.getSubject();		
		return this.defaultString((String)subject.getPrincipal());		
	}	
	
	public String generateOid() {
		return SimpleUtils.getUUIDStr();
	}
	
	public String defaultString(String source) {
		return SimpleUtils.getStr(source, "");
	}
	
	public Date generateDate() {		
		return new Date();
	}
	
	public void copyProperties(Object source, Object target) throws org.springframework.beans.BeansException {
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}
	
	public void doMapper(Object sourceObject, Object targetObject, String mapperId) throws org.dozer.MappingException, ServiceException {
		if (null==mapperId || StringUtils.isBlank(mapperId) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DOZER_MAPPER_ID_BLANK));
		}
		this.mapper.map(sourceObject, targetObject, mapperId);
	}
	
	public void populate(Object bean, Map<String, Object> properties) throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
	}

	@Override
	public void fillToValueObject(Object destObject, Object origObject)
			throws ServiceException, IllegalAccessException,
			InvocationTargetException {
		
		if (destObject==null || origObject==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}		
		org.apache.commons.beanutils.BeanUtils.copyProperties(destObject, origObject);
	}

	@Override
	public void fillToPersisentObject(Object destObject, Object origObject)
			throws ServiceException, IllegalAccessException,
			InvocationTargetException {
		
		if (destObject==null || origObject==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		org.apache.commons.beanutils.BeanUtils.copyProperties(destObject, origObject);
	}	
	
	// ------------------------------------------------------------------------------------
	
	protected abstract IBaseDAO<E, String> getBaseDataAccessObject();
	
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
	
	public void hibernateSessionClear() throws Exception {
		this.getBaseDataAccessObject().clear();
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	public E save(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().save(entityObject);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	public E update(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().update(entityObject);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	public E merge(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().merge(entityObject);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	public E delete(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().delete(entityObject);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	public E saveRequiresNew(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().save(entityObject);		
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public E mergeRequiresNew(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().merge(entityObject);		
	}
	
	@SuppressWarnings("unchecked")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public E findByOid(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		if (StringUtils.isBlank( ((BaseEntity<String>)entityObject).getOid() ) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST));
		}
		return this.getBaseDataAccessObject().findByOid(entityObject);
	}
	
	@SuppressWarnings("unchecked")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public int countByOid(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		if (StringUtils.isBlank( ((BaseEntity<String>)entityObject).getOid() ) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST));
		}
		return this.getBaseDataAccessObject().countByOid(entityObject);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public boolean deleteByPK(String pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().deleteByPK(pk);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public E findByPK(String pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().findByPK(pk);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public int countByPK(String pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().countByPK(pk);
	}	
	
	@SuppressWarnings("rawtypes")
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public boolean deleteByPK(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Map<String, Object> pkMap=BaseEntityUtil.getPKParameter((BaseEntity)entityObject);
		if (pkMap==null || pkMap.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		return this.getBaseDataAccessObject().deleteByPK(pkMap);
	}
	
	@SuppressWarnings("rawtypes")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public E findByPK(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Map<String, Object> pkMap=BaseEntityUtil.getPKParameter((BaseEntity)entityObject);
		if (pkMap==null || pkMap.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}		
		return this.getBaseDataAccessObject().findByPK(pkMap);
	}
	
	@SuppressWarnings("rawtypes")
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public int countByPK(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Map<String, Object> pkMap=BaseEntityUtil.getPKParameter((BaseEntity)entityObject);
		if (pkMap==null || pkMap.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}				
		return this.getBaseDataAccessObject().countByPK(pkMap);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public boolean deleteByPKng(PK pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().deleteByPK((String)pk);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public E findByPKng(PK pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().findByPK((String)pk);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public int countByPKng(PK pk) throws ServiceException, Exception {
		if (pk==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().countByPK((String)pk);
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
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public long countByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams) throws ServiceException, Exception {
		
		return this.getBaseDataAccessObject().countByParams(params, likeParams);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public long countByParams(Map<String, Object> params) throws ServiceException, Exception {
		
		return countByParams(params, null);
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
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public List<E> findListByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams) throws ServiceException, Exception {
		// No Session found for current thread 所以加了 REQUIRES_NEW
		return (List<E>)this.getBaseDataAccessObject().findListByParams(params, likeParams, null);
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
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public List<E> findListByParams(
			Map<String, Object> params, 
			Map<String, String> likeParams,
			Map<String, String> orderParams) throws ServiceException, Exception {
		
		return (List<E>)this.getBaseDataAccessObject().findListByParams(params, likeParams, orderParams);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public List<E> findListByParams(Map<String, Object> params) throws ServiceException, Exception {
		
		return findListByParams(params, null);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public List<E> findListByParams2(
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception {
		
		return this.getBaseDataAccessObject().findListByParams2(customOperParams);
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)	
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception {
		
		return this.getBaseDataAccessObject().findListByParams2(params, customOperParams);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)		
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams) throws ServiceException, Exception {
		
		return this.getBaseDataAccessObject().findListByParams2(params, likeParams, customOperParams);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)		
	public List<E> findListByParams2(
			Map<String, Object> params,
			Map<String, String> likeParams,
			Map<String, CustomeOperational> customOperParams,
			Map<String, String> orderParams) throws ServiceException, Exception {
		
		return this.getBaseDataAccessObject().findListByParams2(params, likeParams, customOperParams, orderParams);
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
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public E findByEntityUK(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Map<String, Object> ukMap=BaseEntityUtil.getUKParameter((BaseEntity<?>)entityObject);
		if (ukMap==null || ukMap.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}		
		return this.getBaseDataAccessObject().findByEntityUK(ukMap);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public int countByEntityUK(E entityObject) throws ServiceException, Exception {
		if (entityObject==null || !(entityObject instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		Map<String, Object> ukMap=BaseEntityUtil.getUKParameter((BaseEntity<?>)entityObject);
		if (ukMap==null || ukMap.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}		
		return this.getBaseDataAccessObject().countByEntityUK(ukMap);
	}
	
	// ------------------------------------------------------------------------------------
	
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public DefaultResult<List<E>> ibatisSelectListByParams(Map<String, Object> params) throws ServiceException, Exception {
		if (params == null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<List<E>> result=new DefaultResult<List<E>>();
		List<E> searchList = (List<E>)this.getBaseDataAccessObject().ibatisSelectListByParams(params);
		if (searchList!=null && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}
		return result;
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED, timeout=25, readOnly=true)
	public DefaultResult<E> ibatisSelectOneByValue(E valueObj) throws ServiceException, Exception {
		if (null==valueObj) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<E> result = new DefaultResult<E>();
		E searchResult = this.getBaseDataAccessObject().ibatisSelectOneByValue(valueObj);
		if (searchResult!=null) {
			result.setValue(searchResult);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}		
		return result;
	}
	
	/*
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public boolean ibatisInsert(E valueObj) throws ServiceException, Exception {
		if (null==valueObj) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().ibatisInsert(valueObj);
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public boolean ibatisUpdate(E valueObj) throws ServiceException, Exception {
		if (null==valueObj) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().ibatisUpdate(valueObj);
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public boolean ibatisDelete(E valueObj) throws ServiceException, Exception {
		if (null==valueObj) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		return this.getBaseDataAccessObject().ibatisDelete(valueObj);
	}
	*/
	
	// ------------------------------------------------------------------------------------
	
	protected Map<String, String> providedSelectZeroDataMap(boolean pleaseSelectItem) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelectItem) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		return dataMap;
	}
	
	protected boolean isNoSelectId(String value) {
		if (StringUtils.isBlank(value) || Constants.HTML_SELECT_NO_SELECT_ID.equals(value)) {
			return true;
		}
		return false;
	}
	
	protected DynamicHqlQueryParamHandler getQueryParamHandler(SearchValue searchValue) {
		DynamicHqlQueryParamHandler queryParamHandler = new DynamicHqlQueryParamHandler();
		queryParamHandler.setSourceSearchParameterAndRoot(searchValue.getParameter());
		return queryParamHandler;
	}
	
	// ------------------------------------------------------------------------------------
	
	public DynamicHql getDynamicHqlResource(String resource) throws Exception {
		return this.getBaseDataAccessObject().getDynamicHqlResource(resource);
	}	
	
	public String getDynamicHql(String queryName, Map<String, Object> paramMap) throws Exception {
		return this.getBaseDataAccessObject().getDynamicHql(queryName, paramMap);
	}
	
	public String getDynamicHql(String resource, String queryName, Map<String, Object> paramMap) throws Exception {
		return this.getBaseDataAccessObject().getDynamicHql(resource, queryName, paramMap);
	}	
	
}
