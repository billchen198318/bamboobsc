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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.ITsaMaCoefficientsService;
import com.netsteadfast.greenstep.bsc.service.ITsaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.ITsaService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbTsa;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.po.hbm.BbTsaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.TsaMaCoefficientsVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.TsaVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.TsaManagementAction")
@Scope
public class TsaManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 5766362874404689604L;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	private ITsaService<TsaVO, BbTsa, String> tsaService;
	private ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	private ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> measureDataOrganizationMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> measureDataEmployeeMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> paramMap = this.providedSelectZeroDataMap(true);
	private TsaVO tsa = null;
	private TsaMeasureFreqVO measureFreq = null;
	private TsaMaCoefficientsVO coefficient1 = null;
	private TsaMaCoefficientsVO coefficient2 = null;
	private TsaMaCoefficientsVO coefficient3 = null;
	
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
	
	public ITsaService<TsaVO, BbTsa, String> getTsaService() {
		return tsaService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaService")
	@Required	
	public void setTsaService(ITsaService<TsaVO, BbTsa, String> tsaService) {
		this.tsaService = tsaService;
	}

	public ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> getTsaMeasureFreqService() {
		return tsaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMeasureFreqService")
	@Required		
	public void setTsaMeasureFreqService(ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService) {
		this.tsaMeasureFreqService = tsaMeasureFreqService;
	}
	
	public ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> getTsaMaCoefficientsService() {
		return tsaMaCoefficientsService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMaCoefficientsService")
	@Required			
	public void setTsaMaCoefficientsService(ITsaMaCoefficientsService<TsaMaCoefficientsVO, BbTsaMaCoefficients, String> tsaMaCoefficientsService) {
		this.tsaMaCoefficientsService = tsaMaCoefficientsService;
	}	
	
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.VisionService")	
	public void setVisionService(IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	private void initData(String type) throws ServiceException, Exception {
		if ("create".equals(type) || "edit".equals(type) || "queryForecast".equals(type)) {
			// 這邊是設定不要限定 部門&員工下拉選項
			this.measureDataOrganizationMap = this.organizationService.findForMap(true);
			this.measureDataEmployeeMap = this.employeeService.findForMap(true);			
		}		
		if ("queryForecast".equals(type)) {
			this.visionMap = this.visionService.findForMap(true);
			this.paramMap = this.tsaService.findForMap(true);
		}
	}
	
	private void fetchData() throws ServiceException, Exception {
		// ==========================================================================================
		// main
		this.tsa = new TsaVO();
		this.transformFields2ValueObject(this.tsa, "oid");
		DefaultResult<TsaVO> result = this.tsaService.findObjectByOid(this.tsa);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.tsa = result.getValue();
		// ==========================================================================================
		
		// ==========================================================================================
		// measure - freq
		this.measureFreq = new TsaMeasureFreqVO();
		this.measureFreq.setTsaOid( this.tsa.getOid() );
		DefaultResult<TsaMeasureFreqVO> mfResult = this.tsaMeasureFreqService.findByUK(this.measureFreq);
		if (mfResult.getValue() == null) {
			throw new ServiceException( mfResult.getSystemMessage().getValue() );
		}
		this.measureFreq = mfResult.getValue();
		this.measureFreq.setEmployeeOid("");
		if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(this.measureFreq.getEmpId()) && !StringUtils.isBlank(this.measureFreq.getEmpId())) {
			
			EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(this.employeeService, this.measureFreq.getEmpId());
			this.measureFreq.setEmployeeOid(employee.getOid());
			
		}
		this.measureFreq.setOrganizationOid("");
		if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(this.measureFreq.getOrgId()) && !StringUtils.isBlank(this.measureFreq.getOrgId())) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOrgId(this.measureFreq.getOrgId());
			DefaultResult<OrganizationVO> orgResult = this.organizationService.findByUK(organization);
			if (orgResult.getValue()!=null) {
				this.measureFreq.setOrganizationOid(orgResult.getValue().getOid());
			}
		}
		this.getFields().put("measureFreqStartDate", "");
		this.getFields().put("measureFreqEndDate", "");
		this.getFields().put("measureFreqStartYearDate", this.measureFreq.getStartDateTextBoxValue());
		this.getFields().put("measureFreqEndYearDate", this.measureFreq.getEndDateTextBoxValue());
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(measureFreq.getFreq()) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(measureFreq.getFreq()) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(measureFreq.getFreq()) ) {
			this.getFields().put("measureFreqStartDate", this.measureFreq.getStartDateTextBoxValue());
			this.getFields().put("measureFreqEndDate", this.measureFreq.getEndDateTextBoxValue());
			this.getFields().put("measureFreqStartYearDate", "");
			this.getFields().put("measureFreqEndYearDate", "");			
		}
		//1-DEPT,2-EMP,3-Both
		this.getFields().put("measureFreqDataFor", "all");
		if ("1".equals(this.measureFreq.getDataType())) {
			this.getFields().put("measureFreqDataFor", "organization");
		}
		if ("2".equals(this.measureFreq.getDataType())) {
			this.getFields().put("measureFreqDataFor", "employee");
		}		
		// ==========================================================================================
		
		// ==========================================================================================
		// coefficients
		this.coefficient1 = new TsaMaCoefficientsVO();
		this.coefficient2 = new TsaMaCoefficientsVO();
		this.coefficient3 = new TsaMaCoefficientsVO();
		this.coefficient1.setTsaOid( this.tsa.getOid() );
		this.coefficient1.setSeq(1);
		this.coefficient2.setTsaOid( this.tsa.getOid() );
		this.coefficient2.setSeq(2);
		this.coefficient3.setTsaOid( this.tsa.getOid() );
		this.coefficient3.setSeq(3);
		DefaultResult<TsaMaCoefficientsVO> cResult1 = this.tsaMaCoefficientsService.findByUK(this.coefficient1);
		DefaultResult<TsaMaCoefficientsVO> cResult2 = this.tsaMaCoefficientsService.findByUK(this.coefficient2);
		DefaultResult<TsaMaCoefficientsVO> cResult3 = this.tsaMaCoefficientsService.findByUK(this.coefficient3);
		if (cResult1.getValue() == null) {
			throw new ServiceException( cResult1.getSystemMessage().getValue() );
		}
		if (cResult2.getValue() == null) {
			throw new ServiceException( cResult2.getSystemMessage().getValue() );
		}
		if (cResult3.getValue() == null) {
			throw new ServiceException( cResult3.getSystemMessage().getValue() );
		}
		this.coefficient1 = cResult1.getValue();
		this.coefficient2 = cResult2.getValue();
		this.coefficient3 = cResult3.getValue();
		// ==========================================================================================
	}
	
	/**
	 * bsc.tsaManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return SUCCESS;	
	}	
	
	/**
	 * bsc.tsaEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.fetchData();
			forward = SUCCESS;
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return forward;	
	}		
	
	/**
	 * bsc.tsaQueryAction.action 
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0002Q")
	public String queryForecast() throws Exception {
		try {
			this.initData("queryForecast");
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
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
	
	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}	

	public Map<String, String> getMeasureDataOrganizationMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataOrganizationMap);
		return measureDataOrganizationMap;
	}

	public Map<String, String> getMeasureDataEmployeeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.measureDataEmployeeMap);
		return measureDataEmployeeMap;
	}

	public Map<String, String> getParamMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.paramMap);
		return paramMap;
	}

	public TsaVO getTsa() {
		return tsa;
	}

	public TsaMeasureFreqVO getMeasureFreq() {
		return measureFreq;
	}

	public TsaMaCoefficientsVO getCoefficient1() {
		return coefficient1;
	}

	public TsaMaCoefficientsVO getCoefficient2() {
		return coefficient2;
	}

	public TsaMaCoefficientsVO getCoefficient3() {
		return coefficient3;
	}	
	
}
