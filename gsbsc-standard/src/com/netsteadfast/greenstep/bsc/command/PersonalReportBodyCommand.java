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
package com.netsteadfast.greenstep.bsc.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

public class PersonalReportBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/personal-report-body.ftl";
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	@SuppressWarnings("unchecked")
	public PersonalReportBodyCommand() {
		super();
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");		
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		String dateType = (String)context.get("dateType");
		String empId = (String)context.get("empId");
		String account = (String)context.get("account");		
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("treeObj", treeObj);
		parameter.put("dateType", dateType);
		parameter.put("year", (String)context.get("startYearDate"));
		parameter.put("fullName", " ");
		parameter.put("jobTitle", " ");
		parameter.put("departmentName", " ");
		parameter.put("employeeOid", " ");
		parameter.put("total", 0.0f);
		if ( context.get("total") != null && context.get("total") instanceof Float ) {
			parameter.put("total", context.get("total") );
		}
		EmployeeVO employee = new EmployeeVO();
		employee.setEmpId(empId);
		employee.setAccount(account);
		DefaultResult<EmployeeVO> result = this.employeeService.findByUK(employee);
		if (result.getValue()!=null) {
			parameter.put("fullName", result.getValue().getEmpId() + " - " + result.getValue().getFullName());
			parameter.put("jobTitle", result.getValue().getJobTitle());		
			parameter.put("employeeOid", result.getValue().getOid());
			List<String> appendIds = this.organizationService.findForAppendOrganizationOids(
					result.getValue().getEmpId());
			List<String> appendNames = this.organizationService.findForAppendNames(appendIds);
			StringBuilder sb = new StringBuilder();
			for (int i=0; appendNames!=null && i<appendNames.size(); i++) {
				sb.append(appendNames.get(i)).append(Constants.ID_DELIMITER);
			}
			parameter.put("departmentName", sb.toString() );
		}
		this.fillReportProperty(parameter);
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				PersonalReportBodyCommand.class.getClassLoader(), 
				templateResource, 
				parameter);
		this.setResult(context, content);			
		return false;
	}

	private void fillReportProperty(Map<String, Object> parameter) throws ServiceException, Exception {
		BscReportPropertyUtils.loadData();
		parameter.put("objectiveTitle", BscReportPropertyUtils.getObjectiveTitle() );
		parameter.put("kpiTitle", BscReportPropertyUtils.getKpiTitle() );
		String classLevel = BscReportPropertyUtils.getPersonalReportClassLevel();
		classLevel = StringUtils.defaultString(classLevel);
		classLevel = classLevel.replaceAll("\n", Constants.HTML_BR);	
		parameter.put("classLevel", classLevel );
	}	
	
}
