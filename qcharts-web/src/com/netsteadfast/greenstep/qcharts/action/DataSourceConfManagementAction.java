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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceDriver;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceDriverService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;
import com.netsteadfast.greenstep.vo.DataSourceDriverVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.DataSourceConfManagementAction")
@Scope
public class DataSourceConfManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -3100558861006206642L;
	private IDataSourceDriverService<DataSourceDriverVO, QcDataSourceDriver, String> dataSourceDriverService;
	private IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	private Map<String, String> driverMap = this.providedSelectZeroDataMap(true);
	private DataSourceConfVO dataSourceConf = new DataSourceConfVO();
	
	public DataSourceConfManagementAction() {
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

	private void initData(String type) throws ServiceException, Exception {
		if ("create".equals(type) || "edit".equals(type) ) {
			this.driverMap = this.dataSourceDriverService.findForMap(true);
		}
	}
	
	private void loadDataSourceConfData() throws ServiceException, Exception {		
		this.transformFields2ValueObject(this.dataSourceConf, new String[]{"oid"});
		DefaultResult<DataSourceConfVO> result = this.dataSourceConfService.findObjectByOid(dataSourceConf);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.dataSourceConf = result.getValue();		
		
		DataSourceDriverVO driver = new DataSourceDriverVO();
		driver.setId(dataSourceConf.getDriverId());
		DefaultResult<DataSourceDriverVO> dResult = this.dataSourceDriverService.findByUK(driver);
		if (dResult.getValue()!=null) {
			this.getFields().put("driverOid", dResult.getValue().getOid());
		}
		
	}
	
	/**
	 * qcharts.dataSourceConfManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
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
	
	/**
	 * qcharts.dataSourceConfCreateAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001A")
	public String create() throws Exception {
		try {
			this.initData("create");
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
	
	/**
	 * qcharts.dataSourceConfEditAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.loadDataSourceConfData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;				
	}				

	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getDriverMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.driverMap);
		return driverMap;
	}

	public DataSourceConfVO getDataSourceConf() {
		return dataSourceConf;
	}

}
