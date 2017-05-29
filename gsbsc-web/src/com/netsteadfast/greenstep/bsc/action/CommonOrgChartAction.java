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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.service.logic.IEmployeeLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IOrganizationLogicService;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonOrgChartAction")
@Scope
public class CommonOrgChartAction extends BaseJsonAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 8795159524495891163L;
	protected Logger logger = Logger.getLogger(CommonOrgChartAction.class);
	private IEmployeeLogicService employeeLogicService;
	private IOrganizationLogicService organizationLogicService;
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = ""; // 存入 OrgChart 要用的資料, 產生的 tb_sys_upload.OID
	
	private Map<String, Object> rootData = new HashMap<String, Object>(); // 輸出 OrgChart 要用的資料
	
	public CommonOrgChartAction() {
		super();
	}

	@JSON(serialize=false)
	public IEmployeeLogicService getEmployeeLogicService() {
		return employeeLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.EmployeeLogicService")		
	public void setEmployeeLogicService(IEmployeeLogicService employeeLogicService) {
		this.employeeLogicService = employeeLogicService;
	}

	@JSON(serialize=false)
	public IOrganizationLogicService getOrganizationLogicService() {
		return organizationLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.OrganizationLogicService")		
	public void setOrganizationLogicService(IOrganizationLogicService organizationLogicService) {
		this.organizationLogicService = organizationLogicService;
	}
	
	@SuppressWarnings("unused")
	private void createOrgChartDataForEmployee() throws ControllerException, AuthorityException, ServiceException, Exception {
		EmployeeVO employee = new EmployeeVO();
		employee = this.transformFields2ValueObject(employee, "oid");
		DefaultResult<String> result = this.employeeLogicService.createOrgChartData(
				ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), this.getHttpServletRequest()), 
				employee);
		this.uploadOid = super.defaultString(result.getValue());
		this.message = result.getSystemMessage().getValue();
		if (!StringUtils.isBlank(this.uploadOid)) {
			this.loadOrgChartData(this.uploadOid);
			this.success = IS_YES;
		}
	}
	
	private void createOrgChartDataForEmployee2() throws ControllerException, AuthorityException, ServiceException, Exception {
		EmployeeVO employee = new EmployeeVO();
		employee = this.transformFields2ValueObject(employee, "oid");
		DefaultResult<Map<String, Object>> result = this.employeeLogicService.getOrgChartData(
				ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), this.getHttpServletRequest()), 
				employee);
		this.rootData = result.getValue();
		this.message = result.getSystemMessage().getValue();
		if (this.rootData != null && this.rootData.size() > 0) {
			this.success = IS_YES;
		}
	}
	
	@SuppressWarnings("unused")
	private void createOrgChartDataForOrganization() throws ControllerException, AuthorityException, ServiceException, Exception {
		OrganizationVO organization = new OrganizationVO();
		organization = this.transformFields2ValueObject(organization, "oid");
		DefaultResult<String> result = this.organizationLogicService.createOrgChartData(
				ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), this.getHttpServletRequest()), 
				organization);
		this.uploadOid = super.defaultString(result.getValue());
		this.message = result.getSystemMessage().getValue();
		if (!StringUtils.isBlank(this.uploadOid)) {
			this.loadOrgChartData(this.uploadOid);
			this.success = IS_YES;
		}		
	}
	
	private void createOrgChartDataForOrganization2() throws ControllerException, AuthorityException, ServiceException, Exception {
		OrganizationVO organization = new OrganizationVO();
		organization = this.transformFields2ValueObject(organization, "oid");
		DefaultResult<Map<String, Object>> result = this.organizationLogicService.getOrgChartData(
				ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), this.getHttpServletRequest()), 
				organization);		
		this.rootData = result.getValue();
		this.message = result.getSystemMessage().getValue();
		if (this.rootData != null && this.rootData.size() > 0) {
			this.success = IS_YES;
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void loadOrgChartData(String selfOid) throws ControllerException, AuthorityException, ServiceException, Exception {
		String oid = super.defaultString( this.getFields().get("oid") ).trim();
		if (!StringUtils.isBlank(selfOid)) {
			oid = selfOid;
		}
		if (StringUtils.isBlank(oid)) {
			throw new ControllerException( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		String jsonData = new String(UploadSupportUtils.getDataBytes(oid));
		ObjectMapper mapper = new ObjectMapper();
		this.rootData = mapper.readValue(jsonData, HashMap.class);
	}
	
	/**
	 * bsc.commonCreateOrgChartForOrganizationAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String doCreateOrgChartForOrganization() throws Exception {
		try {
			//this.createOrgChartDataForOrganization();
			this.createOrgChartDataForOrganization2();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	/**
	 * bsc.commonCreateOrgChartForEmployeeAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String doCreateOrgChartForEmployee() throws Exception {
		try {
			//this.createOrgChartDataForEmployee();
			this.createOrgChartDataForEmployee2();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}		
	
	/**
	 * bsc.commonLoadOrgChartDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String doLoadOrgChartData() throws Exception {
		try {
			this.loadOrgChartData("");
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;	
	}
	
	/**
	 * bsc.commonOrgChartAction.action
	 * 
	 * 範例(員工):
	 * bsc.commonOrgChartAction.action?oid=employee;0123
	 * bsc.commonOrgChartAction.action?oid=employee
	 * 
	 * 範例(部門/組織):
	 * bsc.commonOrgChartAction.action?oid=organization;0123
	 * bsc.commonOrgChartAction.action?oid=organization
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROGCOMM0001Q")
	public String execute() throws Exception {
		String forward = INPUT;
		String oid = super.defaultString( this.getFields().get("oid") ).trim();
		String tmp[] = oid.split( Constants.ID_DELIMITER );
		this.getFields().put("paramType", "");
		this.getFields().put("paramOid", "");		
		if (tmp == null || tmp.length < 1) {
			logger.error( "parameter error: " + oid );
			forward = RESULT_SEARCH_NO_DATA;
		} else {
			forward = SUCCESS;
			this.getFields().put("paramType", tmp[0].trim());
			if (tmp.length > 1) {
				this.getFields().put("paramOid", tmp[1].trim());
			}
		}
		return forward;
	}
	
	@JSON(serialize=false)
	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@JSON(serialize=false)
	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}	
	
	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}

	@JSON
	public Map<String, Object> getRootData() {
		return rootData;
	}

}
