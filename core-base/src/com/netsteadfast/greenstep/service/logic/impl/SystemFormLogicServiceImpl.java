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

import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.po.hbm.TbSysForm;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.service.logic.ISystemFormLogicService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysFormVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemFormLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemFormLogicServiceImpl extends BaseLogicService implements ISystemFormLogicService {
	protected Logger logger=Logger.getLogger(SystemFormLogicServiceImpl.class);
	private final static int MAX_DESCRIPTION_LENGTH = 500;
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
	private ISysFormService<SysFormVO, TbSysForm, String> sysFormService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	
	public SystemFormLogicServiceImpl() {
		super();
	}
	
	public ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> getSysFormTemplateService() {
		return sysFormTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysFormTemplateService")		
	@Required
	public void setSysFormTemplateService(
			ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService) {
		this.sysFormTemplateService = sysFormTemplateService;
	}
	
	public ISysFormService<SysFormVO, TbSysForm, String> getSysFormService() {
		return sysFormService;
	}

	@Autowired
	@Resource(name="core.service.SysFormService")	
	@Required
	public void setSysFormService(
			ISysFormService<SysFormVO, TbSysForm, String> sysFormService) {
		this.sysFormService = sysFormService;
	}	
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")		
	@Required
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysFormTemplateVO> createTmplate(SysFormTemplateVO template, String uploadOid) throws ServiceException, Exception {		
		if ( null == template || super.isBlank(template.getTplId()) || super.isBlank(uploadOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		byte[] content = UploadSupportUtils.getDataBytes( uploadOid );
		template.setFileName( template.getTplId() + ".jsp" );
		template.setContent( content );
		super.setStringValueMaxLength(template, "description", MAX_DESCRIPTION_LENGTH);
		return this.sysFormTemplateService.saveObject(template);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysFormTemplateVO> updateTemplate(SysFormTemplateVO template, String uploadOid) throws ServiceException, Exception {
		if ( null == template || super.isBlank(template.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormTemplateVO> oldResult = this.sysFormTemplateService.findObjectByOid(template);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		byte[] content = oldResult.getValue().getContent();
		if ( !StringUtils.isBlank( uploadOid ) ) {
			content = UploadSupportUtils.getDataBytes( uploadOid );
		}
		
		// 先把 content 清除
		oldResult.getValue().setContent( null );
		this.sysFormTemplateService.updateObject( oldResult.getValue() );
		
		template.setTplId( oldResult.getValue().getTplId() );
		template.setFileName( template.getTplId() + ".jsp" );
		template.setContent( content ); 
		super.setStringValueMaxLength(template, "description", MAX_DESCRIPTION_LENGTH);
		return this.sysFormTemplateService.updateObject(template);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteTemplate(SysFormTemplateVO template) throws ServiceException, Exception {
		if ( null == template || super.isBlank(template.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormTemplateVO> oldResult = this.sysFormTemplateService.findObjectByOid(template);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateId", oldResult.getValue().getTplId());
		if ( this.sysFormService.countByParams(paramMap) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		return this.sysFormTemplateService.deleteObject(template);
	}

}
