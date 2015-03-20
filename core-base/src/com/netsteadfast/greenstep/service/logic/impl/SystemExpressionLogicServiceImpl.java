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
import java.util.HashMap;
import java.util.Map;

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
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExpr;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.service.logic.ISystemExpressionLogicService;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemExpressionLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemExpressionLogicServiceImpl extends BaseLogicService implements ISystemExpressionLogicService {
	protected Logger logger=Logger.getLogger(SystemExpressionLogicServiceImpl.class);
	private static final int MAX_CONTENT_LENGTH = 8000;
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService;
	
	public SystemExpressionLogicServiceImpl() {
		super();
	}

	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required		
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}

	public ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> getSysBeanHelpExprService() {
		return sysBeanHelpExprService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprService")
	@Required			
	public void setSysBeanHelpExprService(
			ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService) {
		this.sysBeanHelpExprService = sysBeanHelpExprService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysExpressionVO> create(SysExpressionVO expression) throws ServiceException, Exception {
		if (expression==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (super.defaultString(expression.getContent()).length() > MAX_CONTENT_LENGTH) {
			throw new ServiceException("Expression only 8,000 words!");
		}
		if (super.defaultString(expression.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			expression.setDescription( expression.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}		
		return this.sysExpressionService.saveObject(expression);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysExpressionVO> update(SysExpressionVO expression) throws ServiceException, Exception {
		if (expression==null || super.isBlank(expression.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (super.defaultString(expression.getContent()).length() > MAX_CONTENT_LENGTH) {
			throw new ServiceException("Expression only 8,000 words!");
		}
		if (super.defaultString(expression.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			expression.setDescription( expression.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		DefaultResult<SysExpressionVO> oldResult = this.sysExpressionService.findObjectByOid(expression);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		expression.setExprId( oldResult.getValue().getExprId() );
		return this.sysExpressionService.updateObject(expression);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysExpressionVO expression) throws ServiceException, Exception {
		if (expression==null || super.isBlank(expression.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<SysExpressionVO> oldResult = this.sysExpressionService.findObjectByOid(expression);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("exprId", oldResult.getValue().getExprId() );
		if ( this.sysBeanHelpExprService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		return this.sysExpressionService.deleteObject(expression);
	}
	
}
