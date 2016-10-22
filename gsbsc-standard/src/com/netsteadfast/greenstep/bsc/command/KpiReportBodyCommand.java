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

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiAttacService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpiAttac;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiAttacVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class KpiReportBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/kpi-report-body.ftl";
	private static final String templateResource_NG = "META-INF/resource/kpi-report-body-ng.ftl"; // 有 javascript click 事件的版本
	private static final String templateResource_NG_PER = "META-INF/resource/kpi-report-body-ng-PER.ftl"; // 點選perspective打開OPW後的報表
	private static final String templateResource_NG_OBJ = "META-INF/resource/kpi-report-body-ng-OBJ.ftl"; // 點選objective打開OPW後的報表
	private static final String templateResource_NG_KPI = "META-INF/resource/kpi-report-body-ng-KPI.ftl"; // 點選KPI打開OPW後的報表
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IKpiAttacService<KpiAttacVO, BbKpiAttac, String> kpiAttacService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	
	@SuppressWarnings("unchecked")
	public KpiReportBodyCommand() {
		super();
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");
		kpiAttacService = (IKpiAttacService<KpiAttacVO, BbKpiAttac, String>)
				AppContext.getBean("bsc.service.KpiAttacService");
		sysUploadService = (ISysUploadService<SysUploadVO, TbSysUpload, String>)
				AppContext.getBean("core.service.SysUploadService");
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
		String nextType = (String)context.get("nextType");
		String nextId = (String)context.get("nextId");
		if ( BscConstants.HEAD_FOR_PER_ID.equals( nextType ) && !StringUtils.isBlank(nextId) ) {
			templateResourceSrc = templateResource_NG_PER;
		}
		if ( BscConstants.HEAD_FOR_OBJ_ID.equals( nextType ) && !StringUtils.isBlank(nextId) ) {
			templateResourceSrc = templateResource_NG_OBJ;
		}
		if ( BscConstants.HEAD_FOR_KPI_ID.equals( nextType ) && !StringUtils.isBlank(nextId) ) {
			templateResourceSrc = templateResource_NG_KPI;
		}		
		this.setImgIconBaseAndKpiInfo(treeObj);
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				KpiReportBodyCommand.class.getClassLoader(), 
				templateResourceSrc, 
				parameter);
		this.setResult(context, content);		
		return false;
	}
	
	private void setImgIconBaseAndKpiInfo(BscStructTreeObj treeObj) throws ServiceException, Exception {
		BscReportSupportUtils.loadExpression();
		List<VisionVO> visions = treeObj.getVisions();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (VisionVO vision : visions) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				perspective.setImgIcon(
						BscReportSupportUtils.getHtmlIconBase(
								"PERSPECTIVES", 
								perspective.getTarget(), 
								perspective.getMin(), 
								perspective.getScore(), 
								"", 
								"", 
								0) );				
				for (ObjectiveVO objective : perspective.getObjectives()) {
					objective.setImgIcon(
							BscReportSupportUtils.getHtmlIconBase(
									"OBJECTIVES", 
									objective.getTarget(), 
									objective.getMin(), 
									objective.getScore(), 
									"", 
									"", 
									0) );					
					for (KpiVO kpi : objective.getKpis()) {
						kpi.setImgIcon(
								BscReportSupportUtils.getHtmlIconBase(
										"KPI", 
										kpi.getTarget(), 
										kpi.getMin(), 
										kpi.getScore(), 
										kpi.getCompareType(), 
										kpi.getManagement(), 
										kpi.getQuasiRange()) );
						BscReportSupportUtils.fillKpiEmployees(kpi);
						BscReportSupportUtils.fillKpiOrganizations(kpi);	
						
						// KPI attachment documents
						paramMap.clear();
						paramMap.put("kpiId", kpi.getId());
						List<KpiAttacVO> attacs = kpiAttacService.findListVOByParams(paramMap);
						for (int i=0; attacs!=null && i<attacs.size(); i++) {
							KpiAttacVO attac = attacs.get(i);
							DefaultResult<SysUploadVO> uploadResult = sysUploadService.findForNoByteContent(attac.getUploadOid());
							if (uploadResult.getValue()!=null) {
								attac.setShowName( uploadResult.getValue().getShowName() );
							} else {
								attac.setShowName( "unknown-" + attac.getUploadOid() );
							}
							kpi.getAttachments().add(attac);
						}
						
					}
				}
			}
		}
	}
	
	private void fillReportProperty(Map<String, Object> parameter) throws ServiceException, Exception {
		BscReportPropertyUtils.loadData();
		parameter.put("backgroundColor", BscReportPropertyUtils.getBackgroundColor() );
		parameter.put("fontColor", BscReportPropertyUtils.getFontColor() );
		parameter.put("perspectiveTitle", BscReportPropertyUtils.getPerspectiveTitle() );
		parameter.put("objectiveTitle", BscReportPropertyUtils.getObjectiveTitle() );
		parameter.put("kpiTitle", BscReportPropertyUtils.getKpiTitle() );
		parameter.put("scoreLabel", BscReportPropertyUtils.getScoreLabel() );
		parameter.put("weightLabel", BscReportPropertyUtils.getWeightLabel() );
		parameter.put("maxLabel", BscReportPropertyUtils.getMaxLabel() );
		parameter.put("targetLabel", BscReportPropertyUtils.getTargetLabel() );
		parameter.put("minLabel", BscReportPropertyUtils.getMinLabel() );
		parameter.put("managementLabel", BscReportPropertyUtils.getManagementLabel() );
		parameter.put("calculationLabel", BscReportPropertyUtils.getCalculationLabel() );
		parameter.put("unitLabel", BscReportPropertyUtils.getUnitLabel() );
		parameter.put("formulaLabel", BscReportPropertyUtils.getFormulaLabel() );
		parameter.put("organizationLabel", BscReportPropertyUtils.getOrganizationLabel() );
		parameter.put("employeeLabel", BscReportPropertyUtils.getEmployeeLabel() );
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
				headContent += Constants.HTML_BR + "Measure data for:&nbsp;" 
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
				headContent += Constants.HTML_BR + "Measure data for:&nbsp;" 
						+ employee.getEmpId() + "&nbsp;-&nbsp;" + employee.getFullName();
				if (!StringUtils.isBlank(employee.getJobTitle())) {
					headContent += "&nbsp;(&nbsp;" + employee.getJobTitle() + "&nbsp;)&nbsp;";
				}
			}
		}		
		parameter.put("headContent", headContent);
	}
	
}
