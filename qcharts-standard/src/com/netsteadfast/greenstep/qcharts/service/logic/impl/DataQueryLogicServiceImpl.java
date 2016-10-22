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
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapper;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceDriver;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceDriverService;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataQueryLogicService;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;
import com.netsteadfast.greenstep.vo.DataQueryVO;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;
import com.netsteadfast.greenstep.vo.DataSourceDriverVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.DataQueryLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DataQueryLogicServiceImpl extends CoreBaseLogicService implements IDataQueryLogicService {
	protected Logger logger=Logger.getLogger(DataQueryLogicServiceImpl.class);
	private IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> dataSourceDriverService;
	private IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	private IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService;
	private IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService;
	
	public DataQueryLogicServiceImpl() {
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
	
	public IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> getDataQueryMapperService() {
		return dataQueryMapperService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryMapperService")
	@Required			
	public void setDataQueryMapperService(
			IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService) {
		this.dataQueryMapperService = dataQueryMapperService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DataQueryVO> create(String dataSourceConfOid, String dataQueryMapperOid, 
			DataQueryVO dataQuery) throws ServiceException, Exception {
		if (super.isNoSelectId(dataSourceConfOid) || super.isNoSelectId(dataQueryMapperOid) || null == dataQuery) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DataSourceConfVO conf = new DataSourceConfVO();
		conf.setOid(dataSourceConfOid);
		DefaultResult<DataSourceConfVO> confResult = dataSourceConfService.findObjectByOid(conf);
		if (confResult.getValue()==null) {
			throw new ServiceException(confResult.getSystemMessage().getValue());
		}
		conf = confResult.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oid", dataQueryMapperOid);
		if (this.dataQueryMapperService.countByParams(paramMap) < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		dataQuery.setConf(conf.getId());
		dataQuery.setMapperOid(dataQueryMapperOid);		
		return this.dataQueryService.saveObject(dataQuery);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DataQueryVO> update(String dataSourceConfOid, String dataQueryMapperOid, 
			DataQueryVO dataQuery) throws ServiceException, Exception {
		if (super.isBlank(dataSourceConfOid) || super.isBlank(dataQueryMapperOid) 
				|| null == dataQuery || super.isBlank(dataQuery.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DataSourceConfVO conf = new DataSourceConfVO();
		conf.setOid(dataSourceConfOid);
		DefaultResult<DataSourceConfVO> confResult = dataSourceConfService.findObjectByOid(conf);
		if (confResult.getValue()==null) {
			throw new ServiceException(confResult.getSystemMessage().getValue());
		}
		conf = confResult.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oid", dataQueryMapperOid);
		if (this.dataQueryMapperService.countByParams(paramMap) < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		DefaultResult<DataQueryVO> oldResult = this.dataQueryService.findObjectByOid(dataQuery);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		if ( oldResult.getValue().getName().equals( dataQuery.getName() ) 
				&& !oldResult.getValue().getOid().equals( dataQuery.getOid() ) ) { // 存在別筆資料但UK相同 , 所以不能 UPDATE
			throw new ServiceException("Please change another name!");
		}
		
		oldResult.getValue().setQueryExpression(null);
		this.dataQueryService.updateObject( oldResult.getValue() ); // 先把BLOB 資料清掉
		
		dataQuery.setConf(conf.getId());
		dataQuery.setMapperOid(dataQueryMapperOid);					
		return this.dataQueryService.updateObject(dataQuery);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(DataQueryVO dataQuery) throws ServiceException, Exception {
		if (null == dataQuery || super.isBlank(dataQuery.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		return this.dataQueryService.deleteObject(dataQuery);
	}

}
