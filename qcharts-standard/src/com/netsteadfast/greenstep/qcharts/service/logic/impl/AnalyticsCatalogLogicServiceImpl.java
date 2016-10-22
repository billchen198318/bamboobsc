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
package com.netsteadfast.greenstep.qcharts.service.logic.impl;

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

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.po.hbm.QcOlapCatalog;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.service.IOlapCatalogService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsCatalogLogicService;
import com.netsteadfast.greenstep.util.OlapUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.AnalyticsCatalogLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AnalyticsCatalogLogicServiceImpl extends CoreBaseLogicService implements IAnalyticsCatalogLogicService {
	protected Logger logger=Logger.getLogger(AnalyticsCatalogLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	
	public AnalyticsCatalogLogicServiceImpl() {
		super();
	}

	public IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> getOlapCatalogService() {
		return olapCatalogService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapCatalogService")
	@Required				
	public void setOlapCatalogService(
			IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService) {
		this.olapCatalogService = olapCatalogService;
	}
	
	public IOlapMdxService<OlapMdxVO, QcOlapMdx, String> getOlapMdxService() {
		return olapMdxService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapMdxService")
	@Required		
	public void setOlapMdxService(
			IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService) {
		this.olapMdxService = olapMdxService;
	}	
	
	/**
	 * 把 TB_SYS_UPLOAD.CONTENT 的資料放至 QC_OLAP_CATALOG.CONTENT
	 * 
	 * @param olapCatalog
	 * @param uploadOid
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void copyContentByUpload(OlapCatalogVO olapCatalog, String uploadOid) throws ServiceException, Exception {
		if ( super.isBlank(uploadOid) ) {
			return;
		}
		olapCatalog.setContent( UploadSupportUtils.getDataBytes(uploadOid) );	
		String schemaContent = new String( olapCatalog.getContent(), Constants.BASE_ENCODING );
		OlapUtils.testMondrianCatalog(schemaContent);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<OlapCatalogVO> create(OlapCatalogVO olapCatalog, String uploadOid) throws ServiceException, Exception {
		if ( null == olapCatalog || super.isBlank(uploadOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(olapCatalog, "description", MAX_DESCRIPTION_LENGTH);	
		this.copyContentByUpload(olapCatalog, uploadOid);
		return this.olapCatalogService.saveObject(olapCatalog);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<OlapCatalogVO> update(OlapCatalogVO olapCatalog, String uploadOid) throws ServiceException, Exception {
		if ( null == olapCatalog || super.isBlank(olapCatalog.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<OlapCatalogVO> oldResult = this.olapCatalogService.findObjectByOid(olapCatalog);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		olapCatalog.setId( oldResult.getValue().getId() ); // ID 不能修改
		this.setStringValueMaxLength(olapCatalog, "description", MAX_DESCRIPTION_LENGTH);
		olapCatalog.setContent( null ); // 先 null 掉
		this.olapCatalogService.updateObject(olapCatalog); // 先 null 掉
		this.copyContentByUpload(olapCatalog, uploadOid);
		if ( super.isBlank(uploadOid) ) { // 沒有更新上傳
			olapCatalog.setContent( oldResult.getValue().getContent() );
		}
		return this.olapCatalogService.updateObject(olapCatalog);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(OlapCatalogVO olapCatalog) throws ServiceException, Exception {
		if ( null == olapCatalog || super.isBlank(olapCatalog.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("catalogOid", olapCatalog.getOid());
		if ( this.olapMdxService.countByParams(paramMap) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}				
		return this.olapCatalogService.deleteObject(olapCatalog);
	}

}
