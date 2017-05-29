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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.HistoryItemScoreReportContentQueryUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.HistoryItemScoreReportContentQueryAction")
@Scope
public class HistoryItemScoreReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = 3711570676385444183L;
	protected Logger logger=Logger.getLogger(HistoryItemScoreReportContentQueryAction.class);
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, Object>> chartData;
	private List<String> chartCategories;
	private String subtitle = "";
	private String newDateVal = "";
	private String startDate = "";
	private String endDate = "";
	
	private List<String> xAxisCategories;
	private List<String> yAxisCategories;
	private List<List<Object>> heatMapChartData;
	
	public HistoryItemScoreReportContentQueryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	@JSON(serialize=false)
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")			
	public void setEmployeeService(IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("itemType", SelectItemFieldCheckUtils.class, "Please select item type!" )
		.add("frequency", SelectItemFieldCheckUtils.class, "Please select frequency!" )
		.process().throwMessage();
		
		this.getCheckFieldHandler()
		.single("itemType", ( !SimpleUtils.isDate( this.getFields().get("dateVal") ) ), "Error, please refresh this page!")
		.throwMessage();
		
		String dataFor = this.getFields().get("dataFor");
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureDataOrganizationOid")) ) {
			super.throwMessage("measureDataOrganizationOid", "Please select organization!");
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureDataEmployeeOid")) ) {
			super.throwMessage("measureDataEmployeeOid", "Please select employee!");
		}
	}

	@SuppressWarnings("unchecked")
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.newDateVal = this.getChangeDateVal();
		String frequency = this.getFields().get("frequency");
		String measureDataTypeName = BscConstants.MEASURE_DATA_FOR_ALL;
		String empId = BscConstants.MEASURE_DATA_EMPLOYEE_FULL;
		String orgId = BscConstants.MEASURE_DATA_ORGANIZATION_FULL;
		String measureDataOrganizationOid = this.getFields().get("measureDataOrganizationOid");
		String measureDataEmployeeOid = this.getFields().get("measureDataEmployeeOid");
		String dataFor = this.getFields().get("dataFor");
		if ("organization".equals(dataFor) && !super.isNoSelectId(measureDataOrganizationOid)) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid(measureDataOrganizationOid);
			DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(organization);
			if ( result.getValue() == null ) {
				throw new ServiceException( result.getSystemMessage().getValue() );
			}
			organization = result.getValue();
			orgId = organization.getOrgId();
			measureDataTypeName = organization.getOrgId() + " - " + organization.getName();
		}
		if ("employee".equals(dataFor) && !super.isNoSelectId(measureDataEmployeeOid)) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid(measureDataEmployeeOid);
			DefaultResult<EmployeeVO> result = this.employeeService.findObjectByOid(employee);
			if ( result.getValue() == null ) {
				throw new ServiceException( result.getSystemMessage().getValue() );
			}
			employee = result.getValue();
			empId = employee.getEmpId();
			measureDataTypeName = employee.getEmpId() + " - " + employee.getFullName();
		}		
		if ("line".equals(this.getFields().get("chartType"))) { // Line chart
			this.chartData = HistoryItemScoreReportContentQueryUtils.getLineChartData(
					this.getFields().get("itemType"), frequency, this.newDateVal, orgId, empId);
			if (this.chartData == null || this.chartData.size() < 1) {
				super.throwMessage("itemType", SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
			}
			this.chartCategories = HistoryItemScoreReportContentQueryUtils
					.getLineChartCategoriesFromData(this.newDateVal, this.chartData);		
			this.subtitle = "Frequency: " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) + ", "
					+ "Date range: " + this.chartCategories.get(0) + " ~ " + this.getChartCategories().get( this.getChartCategories().size()-1 ) + ", "
					+ "Measure-data for: " + measureDataTypeName;
			this.startDate = this.chartCategories.get(0);
			this.endDate = this.getChartCategories().get( this.getChartCategories().size()-1 );
			this.success = IS_YES;			
		} else { // Heat map chart
			Map<String, Object> chartDataMap = HistoryItemScoreReportContentQueryUtils.getHartMapChartData(
					this.getFields().get("itemType"), frequency, this.newDateVal, orgId, empId);
			if (chartDataMap == null || chartDataMap.size() != 3) {
				super.throwMessage("itemType", SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
			}
			this.heatMapChartData = (List<List<Object>>) chartDataMap.get("seriesData");
			this.xAxisCategories = (List<String>) chartDataMap.get("xAxisCategories");
			this.yAxisCategories = (List<String>) chartDataMap.get("yAxisCategories");
			this.subtitle = "Frequency: " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) + ", "
					+ "Date range: " + this.yAxisCategories.get(0) + " ~ " + this.yAxisCategories.get( this.yAxisCategories.size()-1 ) + ", "
					+ "Measure-data for: " + measureDataTypeName;
			this.startDate = this.yAxisCategories.get(0);
			this.endDate = this.yAxisCategories.get( this.yAxisCategories.size()-1 );	
			this.success = IS_YES;
		}
	}
	
	private String getChangeDateVal() throws ParseException {
		String dateVal = this.getFields().get("dateVal");
		String dateChangeStatus = this.getFields().get("dateChangeStatus");
		String sysNowDate = super.getNowDate().replaceAll("/", "");
		Date dateValObj = DateUtils.parseDate(dateVal, new String[]{"yyyyMMdd"});
		if ("next".equals(dateChangeStatus)) {			
			if (Integer.parseInt(dateVal) >= Integer.parseInt(sysNowDate)) {
				dateVal = sysNowDate;
			} else {
				Date nexDate = DateUtils.addDays(dateValObj, 1);
				dateVal	= DateFormatUtils.format(nexDate, "yyyyMMdd");
			}
		}
		if ("prev".equals(dateChangeStatus)) {
			Date nexDate = DateUtils.addDays(dateValObj, -1);
			dateVal	= DateFormatUtils.format(nexDate, "yyyyMMdd");			
		}
		return dateVal;
	}
	
	/**
	 * bsc.historyItemScoreReportContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0008Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
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
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}

	@JSON
	public List<Map<String, Object>> getChartData() {
		return chartData;
	}

	@JSON
	public List<String> getChartCategories() {
		return chartCategories;
	}

	@JSON
	public String getSubtitle() {
		return subtitle;
	}

	@JSON
	public String getNewDateVal() {
		return newDateVal;
	}

	@JSON
	public String getStartDate() {
		return startDate;
	}

	@JSON
	public String getEndDate() {
		return endDate;
	}

	@JSON
	public List<String> getxAxisCategories() {
		return xAxisCategories;
	}

	@JSON
	public List<String> getyAxisCategories() {
		return yAxisCategories;
	}

	@JSON
	public List<List<Object>> getHeatMapChartData() {
		return heatMapChartData;
	}

}
