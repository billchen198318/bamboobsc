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
package com.netsteadfast.greenstep.qcharts.utils;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceDriver;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceDriverService;
import com.netsteadfast.greenstep.util.DataUtils;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;
import com.netsteadfast.greenstep.vo.DataSourceDriverVO;

@SuppressWarnings("unchecked")
public class ManualJdbcTemplateFactory {
	private static ThreadLocal<DataProperty> jdbcTemplateTreadLocal = new ThreadLocal<DataProperty>();
	private static IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> dataSourceDriverService;
	private static IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	
	static {
		dataSourceDriverService = (IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String>)
				AppContext.getBean("qcharts.service.DataSourceDriverService");
		dataSourceConfService = (IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String>)
				AppContext.getBean("qcharts.service.DataSourceConfService");
		
	}
	
	public static NamedParameterJdbcTemplate getManualJdbcTemplate(String confOid) throws ServiceException, Exception {
		if ( jdbcTemplateTreadLocal.get()!=null && jdbcTemplateTreadLocal.get().getDataSourceConf().getOid().equals(confOid) ) {
			return jdbcTemplateTreadLocal.get().getJdbcTemplate();
		}
		DataSourceConfVO conf = new DataSourceConfVO();
		conf.setOid(confOid);
		DefaultResult<DataSourceConfVO> confResult = dataSourceConfService.findObjectByOid(conf);
		if (confResult.getValue()==null) {
			throw new ServiceException(confResult.getSystemMessage().getValue());
		}
		conf = confResult.getValue();
		DataSourceDriverVO driver = new DataSourceDriverVO();
		driver.setId( conf.getDriverId() );
		DefaultResult<DataSourceDriverVO> driverResult = dataSourceDriverService.findByUK(driver);
		if (driverResult.getValue()==null) {
			throw new ServiceException(driverResult.getSystemMessage().getValue());
		}
		driver = driverResult.getValue();
		NamedParameterJdbcTemplate jdbcTemplate = DataUtils.getManualJdbcTemplate(
				Class.forName(driver.getClassName()), 
				conf.getJdbcUrl(), 
				conf.getDbAccount(), 
				conf.getDbPassword());
		DataProperty dataProperty = new DataProperty();
		dataProperty.setDataSourceConf(conf);
		dataProperty.setJdbcTemplate(jdbcTemplate);
		jdbcTemplateTreadLocal.set(dataProperty);
		return jdbcTemplate;
	}
	
	public static void clearTreadLocal() {
		jdbcTemplateTreadLocal.set(null);
		jdbcTemplateTreadLocal.remove();
	}
	
	private static class DataProperty {
		private NamedParameterJdbcTemplate jdbcTemplate = null;
		private DataSourceConfVO dataSourceConf = null;
		
		public NamedParameterJdbcTemplate getJdbcTemplate() {
			return jdbcTemplate;
		}
		
		public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
		
		public DataSourceConfVO getDataSourceConf() {
			return dataSourceConf;
		}
		
		public void setDataSourceConf(DataSourceConfVO dataSourceConf) {
			this.dataSourceConf = dataSourceConf;
		}
		
	}

}
