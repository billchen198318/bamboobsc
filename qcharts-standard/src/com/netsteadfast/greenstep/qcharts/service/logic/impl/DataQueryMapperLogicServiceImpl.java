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
import com.netsteadfast.greenstep.base.service.logic.CoreBaseLogicService;
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapper;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapperSet;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperSetService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataQueryMapperLogicService;
import com.netsteadfast.greenstep.vo.DataQueryMapperSetVO;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;
import com.netsteadfast.greenstep.vo.DataQueryVO;

@ServiceAuthority(check=true)
@Service("qcharts.service.logic.DataQueryMapperLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DataQueryMapperLogicServiceImpl extends CoreBaseLogicService implements IDataQueryMapperLogicService {
	protected Logger logger=Logger.getLogger(DataQueryMapperLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService;
	private IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService;
	private IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService;
	
	public DataQueryMapperLogicServiceImpl() {
		super();
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

	public IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> getDataQueryMapperSetService() {
		return dataQueryMapperSetService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryMapperSetService")
	@Required			
	public void setDataQueryMapperSetService(
			IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService) {
		this.dataQueryMapperSetService = dataQueryMapperSetService;
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
	public DefaultResult<DataQueryMapperVO> createMapper(DataQueryMapperVO queryMapper, 
			List<Map<String, String>> fieldsData) throws ServiceException, Exception {
		if (null == queryMapper || null == fieldsData || fieldsData.size() < 1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(queryMapper, "description", MAX_DESCRIPTION_LENGTH);
		DefaultResult<DataQueryMapperVO> result = this.dataQueryMapperService.saveObject(queryMapper);
		this.createMapperSet(result.getValue(), fieldsData);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<DataQueryMapperVO> updateMapper(DataQueryMapperVO queryMapper, 
			List<Map<String, String>> fieldsData) throws ServiceException, Exception {
		if (null == queryMapper || super.isBlank(queryMapper.getOid()) || null == fieldsData || fieldsData.size() < 1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		// name 是 UK , 修改之前要檢查是否重復
		DataQueryMapperVO checkQueryMapper = new DataQueryMapperVO();
		checkQueryMapper.setName( queryMapper.getName() );
		DefaultResult<DataQueryMapperVO> checkResult = this.dataQueryMapperService.findByUK(checkQueryMapper);
		if (checkResult.getValue()!=null) {
			checkQueryMapper = checkResult.getValue();
			if ( !checkQueryMapper.getOid().equals(queryMapper.getOid()) ) { // 有別筆資料的 name (UK) 相同
				throw new ServiceException("Please change name!");
			}
		}
		this.deleteMapperSet(queryMapper);
		this.createMapperSet(queryMapper, fieldsData);
		return this.dataQueryMapperService.updateObject(queryMapper);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteMapper(DataQueryMapperVO queryMapper) throws ServiceException, Exception {
		if (null == queryMapper || super.isBlank(queryMapper.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", queryMapper.getOid());
		if (this.dataQueryService.countByParams(paramMap)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		this.deleteMapperSet(queryMapper);
		return this.dataQueryMapperService.deleteObject(queryMapper);
	}	
	
	private void createMapperSet(DataQueryMapperVO queryMapper, 
			List<Map<String, String>> fieldsData) throws ServiceException, Exception {
		for (Map<String, String> data : fieldsData) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				DataQueryMapperSetVO mapperSet = new DataQueryMapperSetVO();
				mapperSet.setMapperOid( queryMapper.getOid() );
				mapperSet.setLabelField( entry.getKey() );
				mapperSet.setValueField( entry.getValue() );
				dataQueryMapperSetService.saveObject(mapperSet);
			}
		}		
	}
	
	private void deleteMapperSet(DataQueryMapperVO queryMapper) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", queryMapper.getOid());
		List<QcDataQueryMapperSet> searchDatas = this.dataQueryMapperSetService.findListByParams(paramMap);
		for (QcDataQueryMapperSet mapperSet : searchDatas) {
			this.dataQueryMapperSetService.delete(mapperSet);
		}
	}

}
