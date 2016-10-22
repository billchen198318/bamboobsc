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
import com.netsteadfast.greenstep.po.hbm.TbSysFormMethod;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysFormMethodService;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.service.logic.ISystemFormLogicService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;
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
	private ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService;
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
	
	public ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> getSysFormMethodService() {
		return sysFormMethodService;
	}

	@Autowired
	@Resource(name="core.service.SysFormMethodService")	
	@Required	
	public void setSysFormMethodService(
			ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService) {
		this.sysFormMethodService = sysFormMethodService;
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
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysFormTemplateVO> updateTemplateContentOnly(SysFormTemplateVO template, 
			String content) throws ServiceException, Exception {
		DefaultResult<SysFormTemplateVO> oldResult = this.sysFormTemplateService.findObjectByOid(template);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		oldResult.getValue().setContent( null ); // clear blob-content
		this.sysFormTemplateService.updateObject( oldResult.getValue() ); // clear blob-content
		template = oldResult.getValue();
		template.setContent( super.defaultString( content ).getBytes() );
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

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysFormVO> create(SysFormVO form, String templateOid) throws ServiceException, Exception {
		if ( null == form || super.isBlank(form.getFormId()) || super.isNoSelectId(templateOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		super.setStringValueMaxLength(form, "description", MAX_DESCRIPTION_LENGTH);
		SysFormTemplateVO template = this.findTemplate(templateOid);
		form.setTemplateId( template.getTplId() );
		return this.sysFormService.saveObject(form);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysFormVO> update(SysFormVO form, String templateOid) throws ServiceException, Exception {
		if ( null == form || super.isBlank(templateOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormVO> oldResult = this.sysFormService.findObjectByOid(form);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		form.setFormId( oldResult.getValue().getFormId() );
		super.setStringValueMaxLength(form, "description", MAX_DESCRIPTION_LENGTH);
		SysFormTemplateVO template = this.findTemplate(templateOid);
		form.setTemplateId( template.getTplId() );		
		return this.sysFormService.updateObject(form);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(SysFormVO form) throws ServiceException, Exception {
		if (null==form || super.isBlank(form.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormVO> oldResult = this.sysFormService.findObjectByOid(form);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		this.deleteMethods( oldResult.getValue().getFormId() );
		return this.sysFormService.deleteObject(form);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysFormMethodVO> createMethod(SysFormMethodVO formMethod, String formOid) throws ServiceException, Exception {
		if ( null == formMethod || super.isBlank(formOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysFormVO form = this.findForm(formOid);
		super.setStringValueMaxLength(formMethod, "description", MAX_DESCRIPTION_LENGTH);
		formMethod.setFormId( form.getFormId() );
		return this.sysFormMethodService.saveObject(formMethod);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysFormMethodVO> updateMethod(SysFormMethodVO formMethod, String formOid) throws ServiceException, Exception {
		if ( null == formMethod || super.isBlank(formMethod.getOid()) || super.isBlank(formOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormMethodVO> oldResult = this.sysFormMethodService.findObjectByOid(formMethod);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		
		SysFormVO form = this.findForm(formOid);
		formMethod.setFormId( form.getFormId() );
		
		// check UK(same "name" and "formId" ) is found. because can update formMethod's UK , so need to check it.
		DefaultResult<SysFormMethodVO> ukResult = this.sysFormMethodService.findByUK(formMethod);
		if (ukResult.getValue()!=null) {
			if ( !ukResult.getValue().getOid().equals(formMethod.getOid()) ) { // same UK found, but is another data.
				throw new ServiceException( "Please change another name!" );
			}
		}
		
		oldResult.getValue().setExpression( null ); // clear blob expression
		this.sysFormMethodService.updateObject( oldResult.getValue() );
		super.setStringValueMaxLength(formMethod, "description", MAX_DESCRIPTION_LENGTH);
		return this.sysFormMethodService.updateObject(formMethod);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysFormMethodVO> updateMethodExpressionOnly(SysFormMethodVO formMethod, 
			String expression) throws ServiceException, Exception {
		if ( super.isBlank(expression) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysFormMethodVO> oldResult = this.sysFormMethodService.findObjectByOid(formMethod);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		oldResult.getValue().setExpression( null ); // clear blob-expression
		this.sysFormMethodService.updateObject(formMethod); // clear blob-expression
		formMethod = oldResult.getValue();
		formMethod.setExpression( expression.getBytes() );
		return this.sysFormMethodService.updateObject(formMethod);
	}	

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> deleteMethod(SysFormMethodVO formMethod) throws ServiceException, Exception {
		if ( null == formMethod || super.isBlank(formMethod.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.sysFormMethodService.deleteObject(formMethod);
	}	
	
	private SysFormTemplateVO findTemplate(String templateOid) throws ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		template.setOid(templateOid);
		DefaultResult<SysFormTemplateVO> result = this.sysFormTemplateService.findObjectByOid(template);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		return result.getValue();
	}
	
	private SysFormVO findForm(String formOid) throws ServiceException, Exception {
		SysFormVO form = new SysFormVO();
		form.setOid(formOid);
		DefaultResult<SysFormVO> formResult = this.sysFormService.findObjectByOid(form);
		if ( formResult.getValue() == null ) {
			throw new ServiceException( formResult.getSystemMessage().getValue() );
		}
		return formResult.getValue();		
	}
	
	private void deleteMethods(String formId) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", formId);	
		List<TbSysFormMethod> methods = this.sysFormMethodService.findListByParams(paramMap);
		for (TbSysFormMethod method : methods) {
			this.sysFormMethodService.delete(method);
		}
	}

}
