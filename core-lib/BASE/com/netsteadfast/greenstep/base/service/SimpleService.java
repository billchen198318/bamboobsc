/* 
 * Copyright 2012-2017 qifu of copyright Chen Xin Nien
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.BaseEntityUtil;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.DynamicHqlQueryParamHandler;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.dynamichql.DynamicHql;
import com.netsteadfast.greenstep.util.SimpleUtils;

/**
 * 
 * @author CXN
 *
 * @param <E>	E is PO
 * @param <E>	PK 
 */
public abstract class SimpleService<E extends java.io.Serializable, PK extends java.io.Serializable> implements ISimpleService<E, PK> {
	
	protected PlatformTransactionManager platformTransactionManager;
	protected TransactionTemplate transactionTemplate;
	
	public SimpleService() {
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
	
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<E> saveEntityIgnoreUK(E object) throws ServiceException, Exception {
		
		return this.saveOrMergeEntity("save", false, object);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public DefaultResult<E> mergeEntityIgnoreUK(E object) throws ServiceException, Exception {
		
		return this.saveOrMergeEntity("merge", false, object);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DefaultResult<E> saveOrMergeEntity(String type, boolean checkUK, E object) throws ServiceException, Exception {
		if (object==null || !(object instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<E> result=new DefaultResult<E>();
		if (checkUK) {
			int countByUK=1;
			try {
				countByUK=this.getBaseDataAccessObject().countByUK(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (countByUK>0) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_IS_EXIST));
			}			
		}
		((BaseEntity)object).setOid(this.generateOid());
		((BaseEntity)object).setCuserid(this.getAccountId());
		((BaseEntity)object).setCdate(this.generateDate());
		E insertEntity=null;
		try {
			if ("save".equals(type)) {
				insertEntity=this.getBaseDataAccessObject().save(object);
			} else {
				insertEntity=this.getBaseDataAccessObject().merge(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insertEntity!=null && ((BaseEntity)insertEntity).getOid()!=null ) {
			result.setValue(insertEntity);
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
	public DefaultResult<E> findEntityByOid(E object) throws ServiceException, Exception {		
		if (object==null || !(object instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<E> result=new DefaultResult<E>();
		try {			
			E entityObject=this.findByOid(object);	
			if (entityObject!=null && !StringUtils.isBlank( ((BaseEntity<String>)entityObject).getOid() ) ) {
				result.setValue(entityObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.getValue() == null) {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<E> saveEntity(E object) throws ServiceException, Exception {
		
		return this.saveOrMergeEntity("save", true, object);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	public DefaultResult<E> updateEntity(E object) throws ServiceException, Exception {
		
		if (object==null || !(object instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<E> result=new DefaultResult<E>();	
		try {
			((BaseEntity)object).setUuserid(this.getAccountId());
			((BaseEntity)object).setUdate(this.generateDate());
			object=this.getBaseDataAccessObject().update(object);
			if (object!=null && ((BaseEntity)object).getOid()!=null ) {
				result.setValue(object);
				result.setSystemMessage(
						new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.getValue() == null) {
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
	public DefaultResult<E> mergeEntity(E object) throws ServiceException, Exception {
		
		return this.saveOrMergeEntity("merge", true, object);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<Boolean> deleteEntity(E object) throws ServiceException, Exception {
		
		if (object==null || !(object instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<Boolean> result=new DefaultResult<Boolean>();
		boolean status=false;
		if (this.countByOid(object)>0) {
			this.delete(object);
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
	public DefaultResult<E> findEntityByUK(E object) throws ServiceException, Exception {
		if (object==null || !(object instanceof BaseEntity) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.OBJ_NULL));
		}
		DefaultResult<E> result=new DefaultResult<E>();
		try {			
			E entityByUK=this.getBaseDataAccessObject().findByUK(object);
			if (entityByUK != null && !StringUtils.isBlank( ((BaseEntity<String>)entityByUK).getOid() ) ) {
				result.setValue(entityByUK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if ( result.getValue() == null ) {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)));
		}
		return result;
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
