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
import java.util.List;
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
import com.netsteadfast.greenstep.model.TemplateCode;
import com.netsteadfast.greenstep.po.hbm.TbSysTemplate;
import com.netsteadfast.greenstep.po.hbm.TbSysTemplateParam;
import com.netsteadfast.greenstep.service.ISysTemplateParamService;
import com.netsteadfast.greenstep.service.ISysTemplateService;
import com.netsteadfast.greenstep.service.logic.ISystemTemplateLogicService;
import com.netsteadfast.greenstep.vo.SysTemplateParamVO;
import com.netsteadfast.greenstep.vo.SysTemplateVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemTemplateLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemTemplateLogicServiceImpl extends BaseLogicService implements ISystemTemplateLogicService {
	protected Logger logger=Logger.getLogger(SystemTemplateLogicServiceImpl.class);
	private static final int MAX_MESSAGE_LENGTH = 4000;
	private ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService;
	private ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> sysTemplateParamService;
	
	public SystemTemplateLogicServiceImpl() {
		super();
	}
	
	public ISysTemplateService<SysTemplateVO, TbSysTemplate, String> getSysTemplateService() {
		return sysTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysTemplateService")	
	@Required
	public void setSysTemplateService(
			ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService) {
		this.sysTemplateService = sysTemplateService;
	}

	public ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> getSysTemplateParamService() {
		return sysTemplateParamService;
	}

	@Autowired
	@Resource(name="core.service.SysTemplateParamService")	
	@Required
	public void setSysTemplateParamService(
			ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> sysTemplateParamService) {
		this.sysTemplateParamService = sysTemplateParamService;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysTemplateVO> create(SysTemplateVO sysTemplate) throws ServiceException, Exception {
		if (sysTemplate==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		if (super.defaultString(sysTemplate.getMessage()).length() > MAX_MESSAGE_LENGTH ) {
			throw new ServiceException("Content max only 4,000 words!");
		}
		return sysTemplateService.saveObject(sysTemplate);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<SysTemplateVO> update(SysTemplateVO sysTemplate) throws ServiceException, Exception {
		if (sysTemplate==null || super.isBlank(sysTemplate.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		if (super.defaultString(sysTemplate.getMessage()).length() > MAX_MESSAGE_LENGTH ) {
			throw new ServiceException("Content max only 4,000 words!");
		}		
		DefaultResult<SysTemplateVO> oldResult = this.sysTemplateService.findObjectByOid(sysTemplate);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		sysTemplate.setTemplateId( oldResult.getValue().getTemplateId() );		
		return sysTemplateService.updateObject(sysTemplate);
	}	

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysTemplateVO sysTemplate) throws ServiceException, Exception {
		if (sysTemplate==null || super.isBlank(sysTemplate.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysTemplateVO> oldResult = this.sysTemplateService.findObjectByOid(sysTemplate);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		sysTemplate = oldResult.getValue();
		if (TemplateCode.isUsed(sysTemplate.getTemplateId())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", sysTemplate.getTemplateId());
		List<TbSysTemplateParam> templateParamList = this.sysTemplateParamService.findListByParams(params);
		for (int i=0; templateParamList!=null && i<templateParamList.size(); i++) {
			this.sysTemplateParamService.delete(templateParamList.get(i));
		}
		return this.sysTemplateService.deleteObject(sysTemplate);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysTemplateParamVO> createParam(SysTemplateParamVO sysTemplateParam, String templateOid) throws ServiceException, Exception {
		if (sysTemplateParam==null || super.isBlank(templateOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysTemplateVO sysTemplate = new SysTemplateVO();
		sysTemplate.setOid(templateOid);
		DefaultResult<SysTemplateVO> mResult = this.sysTemplateService.findObjectByOid(sysTemplate);
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		sysTemplate = mResult.getValue();
		sysTemplateParam.setTemplateId(sysTemplate.getTemplateId());
		return this.sysTemplateParamService.saveObject(sysTemplateParam);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> deleteParam(SysTemplateParamVO sysTemplateParam) throws ServiceException, Exception {
		if (sysTemplateParam==null || super.isBlank(sysTemplateParam.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.sysTemplateParamService.deleteObject(sysTemplateParam);
	}

}
