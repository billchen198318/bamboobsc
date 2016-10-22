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
package com.netsteadfast.greenstep.service.logic.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysCtxBean;
import com.netsteadfast.greenstep.service.ISysCtxBeanService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.logic.ISystemContextBeanLogicService;
import com.netsteadfast.greenstep.vo.SysCtxBeanVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemContextBeanLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemContextBeanLogicServiceImpl extends BaseLogicService implements ISystemContextBeanLogicService {
	protected Logger logger=Logger.getLogger(SystemContextBeanLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCxtBeanService;
	private ISysService<SysVO, TbSys, String> sysService;
	
	public SystemContextBeanLogicServiceImpl() {
		super();
	}

	public ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> getSysCxtBeanService() {
		return sysCxtBeanService;
	}

	@Autowired
	@Resource(name="core.service.SysCtxBeanService")
	@Required			
	public void setSysCxtBeanService(
			ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCxtBeanService) {
		this.sysCxtBeanService = sysCxtBeanService;
	}

	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required			
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysCtxBeanVO> create(SysCtxBeanVO ctxBean, String systemOid) throws ServiceException, Exception {
		if (ctxBean==null || super.isBlank(systemOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = this.sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(sResult.getSystemMessage().getValue());
		}
		sys = sResult.getValue();
		ctxBean.setSystem(sys.getSysId());
		if (super.defaultString(ctxBean.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			ctxBean.setDescription( ctxBean.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		return this.sysCxtBeanService.saveObject(ctxBean);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysCtxBeanVO> update(SysCtxBeanVO ctxBean, String systemOid) throws ServiceException, Exception {
		if (ctxBean==null || super.isBlank(ctxBean.getOid()) || super.isBlank(systemOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = this.sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(sResult.getSystemMessage().getValue());
		}
		sys = sResult.getValue();
		ctxBean.setSystem(sys.getSysId());
		DefaultResult<SysCtxBeanVO> anotherResult = this.sysCxtBeanService.findByUK(ctxBean);
		if (anotherResult.getValue()!=null) { // 有另一筆相同UK的資料, 但OID不同, 所以這次update 是不允許的
			if (!anotherResult.getValue().getOid().equals( ctxBean.getOid() )) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_IS_EXIST));
			}
		}
		if (super.defaultString(ctxBean.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			ctxBean.setDescription( ctxBean.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}		
		return this.sysCxtBeanService.updateObject(ctxBean);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysCtxBeanVO ctxBean) throws ServiceException, Exception {
		if (ctxBean==null || super.isBlank(ctxBean.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.sysCxtBeanService.deleteObject(ctxBean);
	}

}
