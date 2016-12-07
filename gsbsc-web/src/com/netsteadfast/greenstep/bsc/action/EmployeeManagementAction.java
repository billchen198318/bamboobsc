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
package com.netsteadfast.greenstep.bsc.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.EmployeeManagementAction")
@Scope
public class EmployeeManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 3624959005836569272L;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private EmployeeVO employee = new EmployeeVO(); // 編輯edit模式要用
	private String appendId; // 編輯edit模式要用
	private String appendName; // 編輯edit模式要用
	private String checkBoxIdDelimiter = BscConstants.EMPL_SELECT_CHECKBOX_ID_DELIMITER;
	
	public EmployeeManagementAction() {
		super();
	}
	
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")
	@Required	
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	private void initData() throws ServiceException, Exception {
		
	}
	
	private void handlerSelectEmployee() throws Exception {
		String tmp[] = this.getFields().get("oid").split(Constants.ID_DELIMITER);
		this.getFields().put("appendId", tmp[0].trim());		
		this.getFields().put("functionName", tmp[1].trim());
	}	
	
	private void loadEmployeeData(boolean needEmployeeOrganization) throws ServiceException, Exception {
		this.transformFields2ValueObject(this.employee, new String[]{"oid"});
		DefaultResult<EmployeeVO> result = this.employeeService.findObjectByOid(employee);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.employee = result.getValue();
		if (!needEmployeeOrganization) {
			return;
		}
		List<String> appendIds = this.organizationService.findForAppendOrganizationOids(this.employee.getEmpId());
		List<String> appendNames = this.organizationService.findForAppendNames(appendIds);
		StringBuilder sb = new StringBuilder();
		for (int i=0; appendIds!=null && i<appendIds.size(); i++) {
			sb.append(appendIds.get(i)).append(Constants.ID_DELIMITER);
		}
		this.appendId = sb.toString();
		sb.setLength(0);
		for (int i=0; appendNames!=null && i<appendNames.size(); i++) {
			sb.append(appendNames.get(i)).append(Constants.ID_DELIMITER);
		}
		this.appendName = sb.toString();
	}
	
	/**
	 * bsc.employeeManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001Q")
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
	
	/**
	 * bsc.employeeCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001A")
	public String create() throws Exception {
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
	
	/**
	 * bsc.employeeEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadEmployeeData(true);
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
	
	/**
	 * bsc.employeePasswordEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001E_S00")
	public String editPassword() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadEmployeeData(false);
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
	
	/**
	 * bsc.employeeSelectAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0001Q_S00")
	public String select() throws Exception {
		try {
			if ( StringUtils.isBlank(this.getFields().get("oid")) ) { // 這裡的 oid 是放選取員工的 oid 組成字串要放入的 hidden 欄位id , 與要觸發的button event
				return RESULT_SEARCH_NO_DATA;
			}
			String tmp[] = this.getFields().get("oid").split(Constants.ID_DELIMITER);
			if (tmp.length!=2) {
				return RESULT_SEARCH_NO_DATA;
			}
			this.handlerSelectEmployee();			
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public EmployeeVO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeVO employee) {
		this.employee = employee;
	}

	public String getAppendId() {
		return appendId;
	}

	public void setAppendId(String appendId) {
		this.appendId = appendId;
	}

	public String getAppendName() {
		return appendName;
	}

	public void setAppendName(String appendName) {
		this.appendName = appendName;
	}

	public String getCheckBoxIdDelimiter() {
		return checkBoxIdDelimiter;
	}

}
