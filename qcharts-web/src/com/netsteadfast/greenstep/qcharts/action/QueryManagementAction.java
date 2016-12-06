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
package com.netsteadfast.greenstep.qcharts.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapper;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.utils.ManualJdbcTemplateFactory;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;
import com.netsteadfast.greenstep.vo.DataQueryVO;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.QueryManagementAction")
@Scope
public class QueryManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -3134229077036959289L;
	private IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	private IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService;
	private IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService;
	private Map<String, String> confMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> mapperMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> mapperSetMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> queryHistoryMap = this.providedSelectZeroDataMap(true);
	
	public QueryManagementAction() {
		super();
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

	private void initData() throws ServiceException, Exception {
		ManualJdbcTemplateFactory.clear();
		this.confMap = this.dataSourceConfService.findForMap(true);
		this.mapperMap = this.dataQueryMapperService.findForMap(true);
		this.queryHistoryMap = this.dataQueryService.findForMap(true);
	}
	
	/**
	 * qcharts.queryManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0001Q")
	public String execute() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}	

	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getConfMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.confMap);
		return confMap;
	}

	public Map<String, String> getMapperMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.mapperMap);
		return mapperMap;
	}

	public Map<String, String> getMapperSetMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.mapperSetMap);
		return mapperSetMap;
	}

	public Map<String, String> getQueryHistoryMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.queryHistoryMap);
		return queryHistoryMap;
	}

}
