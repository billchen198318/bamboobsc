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
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

public class KpiReportBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/kpi-report-body.ftl";
	private static final String templateResource_NG = "META-INF/resource/kpi-report-body-ng.ftl"; // 有 javascript click 事件的版本
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	@SuppressWarnings("unchecked")
	public KpiReportBodyCommand() {
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
		String frequency = (String)context.get("frequency");
		String startYearDate = StringUtils.defaultString( (String)context.get("startYearDate") ).trim();
		String endYearDate = StringUtils.defaultString( (String)context.get("endYearDate") ).trim();
		String startDate = StringUtils.defaultString( (String)context.get("startDate") ).trim();
		String endDate = StringUtils.defaultString( (String)context.get("endDate") ).trim();
		String date1 = startDate;
		String date2 = endDate;		
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			date1 = startYearDate + "/01/01";
			date2 = endYearDate + "/12/" + SimpleUtils.getMaxDayOfMonth(Integer.parseInt(endYearDate), 12);			
		}				
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("treeObj", treeObj);
		parameter.put("date1", date1);
		parameter.put("date2", date2);
		parameter.put("frequency", BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) );
		parameter.put("headContent", "");
		this.fillHeadContent(context, parameter);
		this.fillReportProperty(parameter);
		String templateResourceSrc = templateResource;
		if ( YesNo.YES.equals((String)context.get("ngVer")) ) { // 有 javascript click 事件的版本
			templateResourceSrc = templateResource_NG;
		}
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				KpiReportBodyCommand.class.getClassLoader(), 
				templateResourceSrc, 
				parameter);
		this.setResult(context, content);		
		return false;
	}
	
	private void fillReportProperty(Map<String, Object> parameter) throws ServiceException, Exception {
		BscReportPropertyUtils.loadData();
		parameter.put("backgroundColor", BscReportPropertyUtils.getBackgroundColor() );
		parameter.put("fontColor", BscReportPropertyUtils.getFontColor() );
		parameter.put("perspectiveTitle", BscReportPropertyUtils.getPerspectiveTitle() );
		parameter.put("objectiveTitle", BscReportPropertyUtils.getObjectiveTitle() );
		parameter.put("kpiTitle", BscReportPropertyUtils.getKpiTitle() );
	}

	private void fillHeadContent(Context context, Map<String, Object> parameter) throws ServiceException, Exception {
		String headContent = "";
		String orgId = (String)context.get("orgId");
		String empId = (String)context.get("empId");
		String account = (String)context.get("account");
		if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(orgId) && !StringUtils.isBlank(orgId) ) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOrgId(orgId);
			DefaultResult<OrganizationVO> result = this.organizationService.findByUK(organization);
			if (result.getValue()!=null) {
				organization = result.getValue();
				headContent += "<BR/>Measure data for:&nbsp;" 
						+ organization.getOrgId() + "&nbsp;-&nbsp;" + organization.getName();
			}
		}
		if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(empId) 
				&& !StringUtils.isBlank(empId) && !StringUtils.isBlank(account) ) {
			EmployeeVO employee = new EmployeeVO();
			employee.setEmpId(empId);
			employee.setAccount(account);
			DefaultResult<EmployeeVO> result = this.employeeService.findByUK(employee);
			if (result.getValue()!=null) {
				employee = result.getValue();
				headContent += "<BR/>Measure data for:&nbsp;" 
						+ employee.getEmpId() + "&nbsp;-&nbsp;" + employee.getFullName();
				if (!StringUtils.isBlank(employee.getJobTitle())) {
					headContent += "&nbsp;(&nbsp;" + employee.getJobTitle() + "&nbsp;)&nbsp;";
				}
			}
		}		
		parameter.put("headContent", headContent);
	}
	
}
