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
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PerspectivesDashboardAction")
@Scope
public class PerspectivesDashboardAction extends BaseJsonAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2448544036438639172L;
	protected Logger logger=Logger.getLogger(PerspectivesDashboardAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private Map<String, String> measureDataOrganizationMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> measureDataEmployeeMap = this.providedSelectZeroDataMap(true);	
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = "";
	private VisionVO vision = null;
	private List<String> categories = new LinkedList<String>();
	private List<Map<String, Object>> series = new LinkedList<Map<String, Object>>();	
	
	public PerspectivesDashboardAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.VisionService")	
	public void setVisionService(IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	@JSON(serialize=false)
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}

	@JSON(serialize=false)
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	@JSON(serialize=false)
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
	}
	
	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
		if ( YesNo.YES.equals(super.getIsSuperRole()) ) {
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);
			return;
		} 
		this.measureDataOrganizationMap = this.reportRoleViewLogicService.findForOrganizationMap(
				true, this.getAccountId());
		this.measureDataEmployeeMap = this.reportRoleViewLogicService.findForEmployeeMap(
				true, this.getAccountId());
		/**
		 * 沒有資料表示,沒有限定使用者的角色,只能選取某些部門或某些員工
		 * 因為沒有限定就全部取出
		 */
		if ( this.measureDataOrganizationMap.size() <= 1 && this.measureDataEmployeeMap.size() <= 1 ) { // 第1筆是 - Please select -
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);	
		}		
	}
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("visionOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0004Q_visionOid") )
		.add("frequency", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0004Q_frequency") )
		.process().throwMessage();
		
		String frequency = this.getFields().get("frequency");
		String startDate = this.getFields().get("startDate");
		String endDate = this.getFields().get("endDate");
		String startYearDate = this.getFields().get("startYearDate");
		String endYearDate = this.getFields().get("endYearDate");
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			if ( StringUtils.isBlank( startDate ) || StringUtils.isBlank( endDate ) ) {
				super.throwMessage("startDate|endDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg1"));			
			}
			if ( !StringUtils.isBlank( startDate ) || !StringUtils.isBlank( endDate ) ) {
				if ( !SimpleUtils.isDate( startDate ) ) {
					super.throwMessage("startDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg3"));
				}
				if ( !SimpleUtils.isDate( endDate ) ) {
					super.throwMessage("endDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg4"));		
				}
				if ( Integer.parseInt( endDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startDate|endDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg5"));	
				}			
			}			
		}
		if ( BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			if ( StringUtils.isBlank( startYearDate ) || StringUtils.isBlank( endYearDate ) ) {
				super.throwMessage("startYearDate|endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg2"));			
			}
			if ( !StringUtils.isBlank( startYearDate ) || !StringUtils.isBlank( endYearDate ) ) {
				if ( !SimpleUtils.isDate( startYearDate+"/01/01" ) ) {
					super.throwMessage("startYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg6"));		
				}
				if ( !SimpleUtils.isDate( endYearDate+"/01/01" ) ) {
					super.throwMessage("endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg7"));					
				}
				if ( Integer.parseInt( endYearDate.replaceAll("/", "").replaceAll("-", "") )
						< Integer.parseInt( startYearDate.replaceAll("/", "").replaceAll("-", "") ) ) {
					super.throwMessage("startYearDate|endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg8"));	
				}					
			}			
		}		
		String dataFor = this.getFields().get("dataFor");
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureDataOrganizationOid")) ) {
			super.throwMessage("measureDataOrganizationOid", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg9"));
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureDataEmployeeOid")) ) {
			super.throwMessage("measureDataEmployeeOid", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg10"));
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
		Map<String, String> startEndDateMap = BscMeasureDataFrequency.getWeekOrMonthStartEnd(frequency, startDate, endDate);
		this.getFields().put("startDate", startEndDateMap.get("startDate"));
		this.getFields().put("endDate", startEndDateMap.get("endDate"));			
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
				super.throwMessage("startDate|endDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg11"));
			}
			return;
		}
		DateTime dt1 = new DateTime( startYearDate + "-01-01" ); 
		DateTime dt2 = new DateTime( endYearDate + "-01-01" );		
		int betweenYears = Years.yearsBetween(dt1, dt2).getYears();
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) {
			if ( betweenYears >= 3 ) {
				super.throwMessage("startYearDate|endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg12"));			
			}
		}
		if (BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) {
			if ( betweenYears >= 4 ) {
				super.throwMessage("startYearDate|endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg13"));		
			}			
		}
		if (BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency)) {
			if ( betweenYears >= 6 ) {
				super.throwMessage("startYearDate|endYearDate", this.getText("MESSAGE.BSC_PROG003D0004Q_contentQuery_msg14"));			
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
	
	private Context getContext() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.setDateValue();
		this.checkDateRange();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("performanceScoreChain", context);
		this.message = resultObj.getMessage();	
		if (context.get("treeObj")==null) {
			return context;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)context.get("treeObj");
		if (null != treeObj) {
			this.success = IS_YES;
		}
		this.vision = treeObj.getVisions().get(0);
		return context;
	}	
	
	@SuppressWarnings("unchecked")
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		Context context = this.getChainContext();
		List< Map<String, Object> > chartDatas = (List<Map<String, Object>>) context.get("chartDatas");
		if ( chartDatas == null || chartDatas.size() < 1 ) {
			super.throwMessage( this.getText("MESSAGE.BSC_PROG003D0004Q_msg1") );
		}
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("perspectivesDashboardExcelContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}			
	}
	
	private void fillCategoriesAndSeries() throws Exception {
		for (DateRangeScoreVO dateRangeScore : this.vision.getPerspectives().get(0).getDateRangeScores()) { // 用第1筆的資料來組  categories 就可已了
			this.categories.add( dateRangeScore.getDate() );
		}
		for (PerspectiveVO perspective : this.vision.getPerspectives()) {
			Map<String, Object> mapData = new HashMap<String, Object>();
			List<Float> rangeScore = new LinkedList<Float>();			
			for (DateRangeScoreVO dateRangeScore : perspective.getDateRangeScores()) {
				rangeScore.add( dateRangeScore.getScore() );
			}
			mapData.put("name", perspective.getName());
			mapData.put("data", rangeScore);
			this.series.add( mapData );
		}
	}
	
	/**
	 *  bsc.perspectivesDashboardAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0004Q")
	public String execute() throws Exception {
		try {
			this.initData();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.perspectivesDashboardContentAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0004Q")
	public String doContentScore() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContext();
			if (IS_YES.equals(this.success)) {
				this.fillCategoriesAndSeries();
			}
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.perspectivesDashboardExcelAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0004Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	

	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
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
	
	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	@JSON(serialize=false)
	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	@JSON(serialize=false)
	public Map<String, String> getFrequencyMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.frequencyMap);
		return frequencyMap;
	}

	public Map<String, String> getMeasureDataOrganizationMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataOrganizationMap);
		return measureDataOrganizationMap;
	}

	public void setMeasureDataOrganizationMap(Map<String, String> measureDataOrganizationMap) {
		this.measureDataOrganizationMap = measureDataOrganizationMap;
	}

	public Map<String, String> getMeasureDataEmployeeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataEmployeeMap);
		return measureDataEmployeeMap;
	}

	public void setMeasureDataEmployeeMap(Map<String, String> measureDataEmployeeMap) {
		this.measureDataEmployeeMap = measureDataEmployeeMap;
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
	public VisionVO getVision() {
		return vision;
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
	public List<String> getCategories() {
		return categories;
	}

	@JSON
	public List<Map<String, Object>> getSeries() {
		return series;
	}
	
}
