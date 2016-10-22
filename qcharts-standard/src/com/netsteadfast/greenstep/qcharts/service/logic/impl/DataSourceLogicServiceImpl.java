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
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceDriver;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceDriverService;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataSourceLogicService;
import com.netsteadfast.greenstep.vo.DataQueryVO;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;
import com.netsteadfast.greenstep.vo.DataSourceDriverVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.DataSourceLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DataSourceLogicServiceImpl extends CoreBaseLogicService implements IDataSourceLogicService {
	protected Logger logger=Logger.getLogger(DataSourceLogicServiceImpl.class);
	private static final int MAX_JDBC_URL_LENGTH = 500;
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> dataSourceDriverService;
	private IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	private IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService;
	
	public DataSourceLogicServiceImpl() {
		super();
	}
	
	public IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> getDataSourceDriverService() {
		return dataSourceDriverService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataSourceDriverService")
	@Required			
	public void setDataSourceDriverService(
			IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> dataSourceDriverService) {
		this.dataSourceDriverService = dataSourceDriverService;
	}

	public IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> getDataSourceConfService() {
		return dataSourceConfService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataSourceConfService")
	@Required				
	public void setDataSourceConfService(
			IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService) {
		this.dataSourceConfService = dataSourceConfService;
	}

	public IDataQueryService<DataQueryVO, QcDataQuery, String> getDataQueryService() {
		return dataQueryService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryService")
	@Required		
	public void setDataQueryService(
			IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService) {
		this.dataQueryService = dataQueryService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<DataSourceConfVO> createConf(String driverOid, DataSourceConfVO dataSourceConf) throws ServiceException, Exception {
		if (super.isBlank(driverOid) || null == dataSourceConf) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DataSourceDriverVO driver = new DataSourceDriverVO();
		driver.setOid(driverOid);
		DefaultResult<DataSourceDriverVO> dResult = this.dataSourceDriverService.findObjectByOid(driver);
		if (dResult.getValue()==null) {
			throw new ServiceException( dResult.getSystemMessage().getValue() );
		}
		driver = dResult.getValue();
		dataSourceConf.setDriverId( driver.getId() );
		if (super.defaultString(dataSourceConf.getJdbcUrl()).length() > MAX_JDBC_URL_LENGTH) {
			throw new ServiceException("jdbc-url Only allows " + String.valueOf(MAX_JDBC_URL_LENGTH) + " characters!");
		}
		if (null==dataSourceConf.getDescription()) {
			dataSourceConf.setDescription("");
		}
		this.setStringValueMaxLength(dataSourceConf, "description", MAX_DESCRIPTION_LENGTH);		
		return this.dataSourceConfService.saveObject(dataSourceConf);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DataSourceConfVO> updateConf(String driverOid, DataSourceConfVO dataSourceConf) throws ServiceException, Exception {
		if (super.isBlank(driverOid) || null == dataSourceConf) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<DataSourceConfVO> oldResult = this.dataSourceConfService.findObjectByOid(dataSourceConf);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		dataSourceConf.setId( oldResult.getValue().getId() ); // ID 是 UK, 不允許變動
		
		DataSourceDriverVO driver = new DataSourceDriverVO();
		driver.setOid(driverOid);
		DefaultResult<DataSourceDriverVO> dResult = this.dataSourceDriverService.findObjectByOid(driver);
		if (dResult.getValue()==null) {
			throw new ServiceException( dResult.getSystemMessage().getValue() );
		}
		driver = dResult.getValue();
		dataSourceConf.setDriverId( driver.getId() );
		if (super.defaultString(dataSourceConf.getJdbcUrl()).length() > MAX_JDBC_URL_LENGTH) {
			throw new ServiceException("jdbc-url Only allows " + String.valueOf(MAX_JDBC_URL_LENGTH) + " characters!");
		}
		if (null==dataSourceConf.getDescription()) {
			dataSourceConf.setDescription("");
		}
		this.setStringValueMaxLength(dataSourceConf, "description", MAX_DESCRIPTION_LENGTH);		
		return dataSourceConfService.updateObject(dataSourceConf);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteConf(DataSourceConfVO dataSourceConf) throws ServiceException, Exception {
		if (null == dataSourceConf || super.isBlank(dataSourceConf.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<DataSourceConfVO> oldResult = this.dataSourceConfService.findObjectByOid(dataSourceConf);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		dataSourceConf = oldResult.getValue();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conf", dataSourceConf.getId());
		if ( this.dataQueryService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		return dataSourceConfService.deleteObject(dataSourceConf);
	}

}
