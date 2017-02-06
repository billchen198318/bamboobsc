/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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
package com.netsteadfast.greenstep.bsc.action;

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
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.TsaManagementAction")
@Scope
public class TsaManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 5766362874404689604L;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private Map<String, String> measureDataOrganizationMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> measureDataEmployeeMap = this.providedSelectZeroDataMap(true);	
	
	public TsaManagementAction() {
		super();
	}
	
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}	
	
	@Autowired
	@Required
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}	
	
	private void initData(String type) throws ServiceException, Exception {
		if ("create".equals(type) || "edit".equals(type)) {
			// 這邊是設定配合需要被PDCA處理的項目, 所以不要限定
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);			
		}		
	}
	
	/**
	 * bsc.tsaManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001Q")
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
	 * bsc.tsaCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001A")	
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
	 * bsc.tsaEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001E")	
	public String edit() throws Exception {
		try {
			this.initData("edit");
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
	 * bsc.tsaQueryAction.action 
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0002Q")
	public String queryForecast() throws Exception {
		try {
			this.initData("queryForecast");
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}
	
	public Map<String, String> getFrequencyMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.frequencyMap);
		return frequencyMap;
	}

	public Map<String, String> getMeasureDataOrganizationMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataOrganizationMap);
		return measureDataOrganizationMap;
	}

	public Map<String, String> getMeasureDataEmployeeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataEmployeeMap);
		return measureDataEmployeeMap;
	}	
	
}
