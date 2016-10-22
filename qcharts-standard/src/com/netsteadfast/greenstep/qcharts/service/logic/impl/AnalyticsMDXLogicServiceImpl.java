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
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.po.hbm.QcOlapCatalog;
import com.netsteadfast.greenstep.po.hbm.QcOlapConf;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.service.IOlapCatalogService;
import com.netsteadfast.greenstep.qcharts.service.IOlapConfService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsMDXLogicService;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;
import com.netsteadfast.greenstep.vo.OlapConfVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.AnalyticsMDXLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AnalyticsMDXLogicServiceImpl extends CoreBaseLogicService implements IAnalyticsMDXLogicService {
	protected Logger logger=Logger.getLogger(AnalyticsMDXLogicServiceImpl.class);
	private IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService;
	private IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	
	public AnalyticsMDXLogicServiceImpl() {
		super();
	}
	
	public IOlapConfService<OlapConfVO, QcOlapConf, String> getOlapConfService() {
		return olapConfService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapConfService")
	@Required		
	public void setOlapConfService(
			IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService) {
		this.olapConfService = olapConfService;
	}

	public IOlapMdxService<OlapMdxVO, QcOlapMdx, String> getOlapMdxService() {
		return olapMdxService;
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

	@Autowired
	@Resource(name="qcharts.service.OlapMdxService")
	@Required		
	public void setOlapMdxService(
			IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService) {
		this.olapMdxService = olapMdxService;
	}	
	
	private OlapConfVO loadOlapConfig(String oid) throws ServiceException, Exception {
		OlapConfVO conf = new OlapConfVO();
		conf.setOid(oid);
		DefaultResult<OlapConfVO> result = this.olapConfService.findObjectByOid(conf);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		conf = result.getValue();
		return conf;
	}
	
	private OlapCatalogVO loadOlapCatalog(String oid) throws ServiceException, Exception {
		OlapCatalogVO catalog = new OlapCatalogVO();
		catalog.setOid(oid);
		DefaultResult<OlapCatalogVO> result = this.olapCatalogService.findObjectByOid(catalog);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		catalog = result.getValue();		
		return catalog;
	}	

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<OlapMdxVO> create(OlapMdxVO olapMdx, String configOid, String catalogOid) throws ServiceException, Exception {
		if (null==olapMdx || super.isNoSelectId(configOid) || super.isNoSelectId(catalogOid) 
				|| super.isBlank(olapMdx.getName())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		OlapConfVO config = this.loadOlapConfig(configOid); // 查資料是否存在
		OlapCatalogVO catalog = this.loadOlapCatalog(catalogOid); // 查資料是否存在
		olapMdx.setConfOid( config.getOid() );
		olapMdx.setCatalogOid( catalog.getOid() );		
		return this.olapMdxService.saveObject(olapMdx);		
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<OlapMdxVO> update(OlapMdxVO olapMdx, String configOid, String catalogOid) throws ServiceException, Exception {
		if (null==olapMdx || super.isNoSelectId(configOid) || super.isNoSelectId(catalogOid) 
				|| super.isBlank(olapMdx.getName()) || super.isBlank(olapMdx.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<OlapMdxVO> oldResult = this.olapMdxService.findObjectByOid(olapMdx);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}		
		OlapConfVO config = this.loadOlapConfig(configOid); // 查資料是否存在
		OlapCatalogVO catalog = this.loadOlapCatalog(catalogOid); // 查資料是否存在
		olapMdx.setConfOid( config.getOid() );
		olapMdx.setCatalogOid( catalog.getOid() );	
		if ( olapMdx.getName().equals(oldResult.getValue().getName()) 
				&& !olapMdx.getOid().equals(oldResult.getValue().getOid()) ) { // 存在別筆資料但UK相同 , 所以不能 UPDATE
			throw new ServiceException("Please change another name!");
		}	
		
		oldResult.getValue().setExpression( null );
		this.olapMdxService.updateObject( oldResult.getValue() ); // 先把BLOB 資料清掉
		
		return this.olapMdxService.updateObject(olapMdx);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(OlapMdxVO olapMdx) throws ServiceException, Exception {
		if (null==olapMdx || super.isBlank(olapMdx.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.olapMdxService.deleteObject(olapMdx);
	}

}
