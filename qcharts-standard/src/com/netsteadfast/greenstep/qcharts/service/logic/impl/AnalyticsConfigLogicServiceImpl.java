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

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.po.hbm.QcOlapConf;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.service.IOlapConfService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsConfigLogicService;
import com.netsteadfast.greenstep.vo.OlapConfVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.AnalyticsConfigLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AnalyticsConfigLogicServiceImpl extends CoreBaseLogicService implements IAnalyticsConfigLogicService {
	protected Logger logger=Logger.getLogger(AnalyticsConfigLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	
	public AnalyticsConfigLogicServiceImpl() {
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

	@Autowired
	@Resource(name="qcharts.service.OlapMdxService")
	@Required		
	public void setOlapMdxService(
			IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService) {
		this.olapMdxService = olapMdxService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<OlapConfVO> create(OlapConfVO olapConf) throws ServiceException, Exception {
		if ( null == olapConf ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(olapConf, "description", MAX_DESCRIPTION_LENGTH);
		return this.olapConfService.saveObject(olapConf);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<OlapConfVO> update(OlapConfVO olapConf) throws ServiceException, Exception {
		if ( null == olapConf || super.isBlank(olapConf.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<OlapConfVO> oldResult = this.olapConfService.findObjectByOid(olapConf);
		if ( oldResult.getValue() == null ) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}				
		olapConf.setId( oldResult.getValue().getId() ); // ID 不能修改
		this.setStringValueMaxLength(olapConf, "description", MAX_DESCRIPTION_LENGTH);
		return this.olapConfService.updateObject(olapConf);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(OlapConfVO olapConf) throws ServiceException, Exception {
		if ( null == olapConf || super.isBlank(olapConf.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("confOid", olapConf.getOid());
		if ( this.olapMdxService.countByParams(paramMap) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}				
		return this.olapConfService.deleteObject(olapConf);
	}

}
