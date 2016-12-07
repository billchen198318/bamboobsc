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

import java.util.LinkedList;
import java.util.List;
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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PersonalReportAction")
@Scope
public class PersonalReportAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2912248245905487693L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> employeeMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private int discreteValues=3;
	private int maximum=0;
	private int minimum=0;
	private List<String> yearRangeList = new LinkedList<String>();
	
	public PersonalReportAction() {
		super();
	}
	
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.VisionService")	
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}
	
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(
			IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
	}	

	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
		if ( YesNo.YES.equals(super.getIsSuperRole()) ) {
			this.employeeMap = this.employeeService.findForMap(true);
			return;
		}
		this.employeeMap = this.reportRoleViewLogicService.findForEmployeeMap(
				true, this.getAccountId());		
		/**
		 * 沒有資料表示,沒有限定使用者的角色,只能選取某些部門或某些員工
		 * 因為沒有限定就全部取出
		 */
		if ( this.employeeMap.size() <= 1 ) { // 第1筆是 - Please select -
			this.employeeMap = this.employeeService.findForMap(true);			
		}					
	}
	
	private void initYearRange() {
		this.maximum=Integer.parseInt( SimpleUtils.getStrYMD(SimpleUtils.IS_YEAR) );
		this.minimum=this.maximum-(this.discreteValues-1);		
		for (int year=this.minimum; year<=this.maximum; year++) {
			this.yearRangeList.add(year+"");
		}
	}	
	
	/**
	 * bsc.personalReportAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0002Q")
	public String execute() throws Exception {
		try {
			this.initData();
			this.initYearRange();
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

	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	public Map<String, String> getEmployeeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.employeeMap);
		return employeeMap;
	}

	public Map<String, String> getFrequencyMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.frequencyMap);
		return frequencyMap;
	}

	public List<String> getYearRangeList() {
		return yearRangeList;
	}

	public int getDiscreteValues() {
		return discreteValues;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}
	
}
