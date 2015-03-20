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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiReportContentQueryAction")
@Scope
public class KpiReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -4172108603200151171L;
	protected Logger logger=Logger.getLogger(KpiReportContentQueryAction.class);
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private List<Map<String, Object>> perspectivesPieChartValue = new LinkedList<Map<String, Object>>();
	private List<String> perspectivesPieChartBgColor = new LinkedList<String>();
	private List<Map<String, Object>> perspectivesBarChartValue = new LinkedList<Map<String, Object>>();
	private List<String> perspectivesBarChartBgColor = new LinkedList<String>();
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private String uploadOid = "";	
	private List<Map<String, String>> lineChartNames = new LinkedList<Map<String, String>>();
	private List<List<List<Object>>> lineChartValues = new LinkedList<List<List<Object>>>();
	
	public KpiReportContentQueryAction() {
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
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException, Exception {
		try {
			super.checkFields(
					new String[]{
							"visionOid"
					}, 
					new String[]{
							"Please select vision!<BR/>"
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}	
		String frequency = this.getFields().get("frequency");
		String startDate = this.getFields().get("startDate");
		String endDate = this.getFields().get("endDate");
		String startYearDate = this.getFields().get("startYearDate");
		String endYearDate = this.getFields().get("endYearDate");
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			if ( StringUtils.isBlank( startDate ) || StringUtils.isBlank( endDate ) ) {
				this.getFieldsId().add("startDate");
				this.getFieldsId().add("endDate");
				throw new ControllerException("Start-date and end-date is required!");				
			}
		}
		if ( BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			if ( StringUtils.isBlank( startYearDate ) || StringUtils.isBlank( endYearDate ) ) {
				this.getFieldsId().add("startYearDate");
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("Start-year and end-year is required!");				
			}			
		}		
		if ( !StringUtils.isBlank( startDate ) || !StringUtils.isBlank( endDate ) ) {
			if ( !SimpleUtils.isDate( startDate ) ) {
				this.getFieldsId().add("startDate");
				throw new ControllerException("Start-date format is incorrect!");
			}
			if ( !SimpleUtils.isDate( endDate ) ) {
				this.getFieldsId().add("endDate");
				throw new ControllerException("End-date format is incorrect!");			
			}
			if ( Integer.parseInt( endDate.replaceAll("/", "").replaceAll("-", "") )
					< Integer.parseInt( startDate.replaceAll("/", "").replaceAll("-", "") ) ) {
				this.getFieldsId().add("startDate");
				this.getFieldsId().add("endDate");
				throw new ControllerException("Start-date / end-date incorrect!");			
			}			
		}
		if ( !StringUtils.isBlank( startYearDate ) || !StringUtils.isBlank( endYearDate ) ) {
			if ( !SimpleUtils.isDate( startYearDate+"/01/01" ) ) {
				this.getFieldsId().add("startYearDate");
				throw new ControllerException("Start-year format is incorrect!");				
			}
			if ( !SimpleUtils.isDate( endYearDate+"/01/01" ) ) {
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("End-year format is incorrect!");						
			}
			if ( Integer.parseInt( endYearDate.replaceAll("/", "").replaceAll("-", "") )
					< Integer.parseInt( startYearDate.replaceAll("/", "").replaceAll("-", "") ) ) {
				this.getFieldsId().add("startYearDate");
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("Start-year / end-year incorrect!");			
			}					
		}
		String dataFor = this.getFields().get("dataFor");
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureDataOrganizationOid")) ) {
			this.getFieldsId().add("measureDataOrganizationOid");
			throw new ControllerException("Please select measure-data organization!");
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureDataEmployeeOid")) ) {
			this.getFieldsId().add("measureDataEmployeeOid");
			throw new ControllerException("Please select measure-data employee!");
		}
	}		
	
	private void setDateValue() throws Exception {
		/**
		 * 周與月頻率的要調整區間日期
		 */
		String frequency = this.getFields().get("frequency");
		if (!BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				&& !BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			return;
		}
		String startDate = this.getFields().get("startDate");
		String endDate = this.getFields().get("endDate");
		if (BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency)) {
			int firstDay = Integer.parseInt( startDate.substring(startDate.length()-2, startDate.length()) );
			int endDay = Integer.parseInt( endDate.substring(endDate.length()-2, endDate.length()) );
			if (firstDay>=1 && firstDay<8) {
				firstDay = 1;
			}
			if (firstDay>=8 && firstDay<15) {
				firstDay = 8;
			}
			if (firstDay>=15 && firstDay<22) {
				firstDay = 15;
			}
			if (firstDay>=22) { 
				firstDay = 22;
			}
			if (endDay>=1 && endDay<8) {
				endDay = 7;
			}
			if (endDay>=8 && endDay<15) {
				endDay = 14;
			}
			if (endDay>=15 && endDay<22) {
				endDay = 21;
			}
			if (endDay>=22) { 
				endDay = SimpleUtils.getMaxDayOfMonth( 
						Integer.parseInt(endDate.substring(0, 4)), 
						Integer.parseInt(endDate.substring(5, 7)) );
			}
			String newStartDate = startDate.substring(0, startDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(firstDay), 2, "0");
			String newEndDate = endDate.substring(0, endDate.length()-2)
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");
			this.getFields().put("startDate", newStartDate);
			this.getFields().put("endDate", newEndDate);
		}
		if (BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency)) {
			int endDay = SimpleUtils.getMaxDayOfMonth( Integer.parseInt(endDate.substring(0, 4)), 
					Integer.parseInt(endDate.substring(5, 7)) );
			String newStartDate = startDate.substring(0, startDate.length()-2) + "01";
			String newEndDate = endDate.substring(0, endDate.length()-2) 
					+ StringUtils.leftPad(String.valueOf(endDay), 2, "0");			
			this.getFields().put("startDate", newStartDate);
			this.getFields().put("endDate", newEndDate);			
		}
	}
	
	private void checkDateRange() throws ControllerException, Exception {
		String frequency = this.getFields().get("frequency");
		String startDate = this.defaultString( this.getFields().get("startDate") ).replaceAll("/", "-");
		String endDate = this.defaultString( this.getFields().get("endDate") ).replaceAll("/", "-");
		String startYearDate = this.defaultString( this.getFields().get("startYearDate") ).replaceAll("/", "-");
		String endYearDate = this.defaultString( this.getFields().get("endYearDate") ).replaceAll("/", "-");
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			int betweenMonths = Months.monthsBetween(dt1, dt2).getMonths();
			if ( betweenMonths >= 12 ) {
				this.getFieldsId().add("startDate");
				this.getFieldsId().add("endDate");
				throw new ControllerException("Date range can not be more than 12 months!");	
			}
			return;
		}
		DateTime dt1 = new DateTime( startYearDate + "-01-01" ); 
		DateTime dt2 = new DateTime( endYearDate + "-01-01" );		
		int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			if ( betweenYears >= 3 ) {
				this.getFieldsId().add("startYearDate");
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("Date range can not be more than 3 years!");				
			}
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			if ( betweenYears >= 4 ) {
				this.getFieldsId().add("startYearDate");
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("Date range can not be more than 4 years!");				
			}			
		}
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency)) {
			if ( betweenYears >= 6 ) {
				this.getFieldsId().add("startYearDate");
				this.getFieldsId().add("endYearDate");
				throw new ControllerException("Date range can not be more than 6 years!");				
			}			
		}
	}
	
	@SuppressWarnings("unchecked")
	private Context getChainContext() throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", this.getFields().get("visionOid"));
		context.put("startDate", this.getFields().get("startDate"));
		context.put("endDate", this.getFields().get("endDate"));		
		context.put("startYearDate", this.getFields().get("startYearDate"));
		context.put("endYearDate", this.getFields().get("endYearDate"));		
		context.put("frequency", this.getFields().get("frequency"));
		context.put("dataFor", this.getFields().get("dataFor"));
		context.put("orgId", BscConstants.MEASURE_DATA_ORGANIZATION_FULL);
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		context.put("account", "");
		context.put("pieCanvasToData", SimpleUtils.deHex( this.defaultString( this.getFields().get("pieCanvasToData") ) ) );
		context.put("barCanvasToData", SimpleUtils.deHex( this.defaultString( this.getFields().get("barCanvasToData") ) ) );
		context.put("uploadSignatureOid", this.getFields().get("uploadSignatureOid") );
		if (!this.isNoSelectId(this.getFields().get("measureDataOrganizationOid"))) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOid( this.getFields().get("measureDataOrganizationOid") );
			DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(organization);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			organization = result.getValue();
			context.put("orgId", organization.getOrgId() );
		}
		if (!this.isNoSelectId(this.getFields().get("measureDataEmployeeOid"))) {
			EmployeeVO employee = new EmployeeVO();
			employee.setOid( this.getFields().get("measureDataEmployeeOid") );
			DefaultResult<EmployeeVO> result = this.employeeService.findObjectByOid(employee);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			employee = result.getValue();
			context.put("empId", employee.getEmpId() );
			context.put("account", employee.getAccount() );
		}		
		return context;
	}
	
	private void fillPerspectivesPieChartData(BscStructTreeObj treeObj) throws Exception {
		List<VisionVO> visions = treeObj.getVisions();
		for (VisionVO vision : visions) {
			if ( !vision.getOid().equals( this.getFields().get("visionOid") ) ) {
				continue;
			}
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("label", perspective.getName());
				dataMap.put("value", perspective.getScore());
				this.perspectivesPieChartValue.add(dataMap);
				this.perspectivesPieChartBgColor.add(perspective.getBgColor());
			}
		}
	}
	
	private void fillPerspectivesBarChartData(BscStructTreeObj treeObj) throws Exception {
		Map<String, Object> valueMap = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		valueMap.put("key", "Perspectives");
		List<VisionVO> visions = treeObj.getVisions();		
		for (VisionVO vision : visions) {
			if ( !vision.getOid().equals( this.getFields().get("visionOid") ) ) {
				continue;
			}
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("label", perspective.getName());
				dataMap.put("value", perspective.getScore());				
				values.add(dataMap);
				this.perspectivesBarChartBgColor.add(perspective.getBgColor());
			}
		}
		valueMap.put("values", values);
		this.perspectivesBarChartValue.add(valueMap);
	}
	
	private void fillLineChartData(BscStructTreeObj treeObj) throws Exception {
		
		List<VisionVO> visions = treeObj.getVisions();
		for (VisionVO vision : visions) {
			if ( !vision.getOid().equals( this.getFields().get("visionOid") ) ) {
				continue;
			}
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						
						Map<String, String> labelMap = new HashMap<String, String>();
						List<List<Object>> dataList = new LinkedList<List<Object>>();
						labelMap.put("label", kpi.getName());
						this.lineChartNames.add(labelMap);
						for ( DateRangeScoreVO entry : kpi.getDateRangeScores() ) {
							List<Object> dateScoreList=new LinkedList<Object>();
							dateScoreList.add(entry.getDate().replaceAll("/", "-"));
							dateScoreList.add(entry.getScore());
							dataList.add(dateScoreList);							
						}						
						this.lineChartValues.add(dataList);
						
					}					
				}				
			}			
		}
	}
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("kpiReportHtmlContentChain", context);
		this.body = String.valueOf( resultObj.getValue() );
		this.message = resultObj.getMessage();
		if ( !StringUtils.isBlank(this.body) && this.body.startsWith("<!-- BSC_PROG003D0001Q -->") ) {
			this.success = IS_YES;
		}		
		if (context.get("treeObj")==null) {
			return;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)context.get("treeObj");
		this.fillPerspectivesPieChartData(treeObj);
		this.fillPerspectivesBarChartData(treeObj);
		this.fillLineChartData(treeObj);
	}
	
	private void getPdf() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("kpiReportPdfContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}		
	}
	
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("kpiReportExcelContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}		
	}	

	/**
	 * bsc.kpiReportContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0001Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.kpiReportPdfQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0001Q")
	public String doPdf() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getPdf();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.kpiReportExcelQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0001Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
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
	public String getBody() {
		return body;
	}
	
	@JSON
	public String getUploadOid() {
		return uploadOid;
	}

	@JSON
	public String getStartDate() {
		return this.defaultString( this.getFields().get("startDate") );
	}

	@JSON
	public String getEndDate() {
		return this.defaultString( this.getFields().get("endDate") );
	}

	@JSON
	public List<Map<String, Object>> getPerspectivesPieChartValue() {
		return perspectivesPieChartValue;
	}

	@JSON
	public List<String> getPerspectivesPieChartBgColor() {
		return perspectivesPieChartBgColor;
	}

	@JSON
	public List<Map<String, Object>> getPerspectivesBarChartValue() {
		return perspectivesBarChartValue;
	}

	@JSON
	public List<String> getPerspectivesBarChartBgColor() {
		return perspectivesBarChartBgColor;
	}

	@JSON
	public List<Map<String, String>> getLineChartNames() {
		return lineChartNames;
	}

	@JSON
	public List<List<List<Object>>> getLineChartValues() {
		return lineChartValues;
	}
	
}
