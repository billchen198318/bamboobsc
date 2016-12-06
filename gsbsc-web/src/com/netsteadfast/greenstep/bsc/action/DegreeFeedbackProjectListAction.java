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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectListAction")
@Scope
public class DegreeFeedbackProjectListAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -7624043175140468696L;
	private IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;	
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private List<DegreeFeedbackProjectVO> projects = new ArrayList<DegreeFeedbackProjectVO>();
	private DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
	private Map<String, String> ownerMap = this.providedSelectZeroDataMap(true);
	private List<BbDegreeFeedbackItem> items = new ArrayList<BbDegreeFeedbackItem>();
	private List<BbDegreeFeedbackLevel> levels = new ArrayList<BbDegreeFeedbackLevel>();
	
	public DegreeFeedbackProjectListAction() {
		super();
	}
	
	public IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> getDegreeFeedbackProjectService() {
		return degreeFeedbackProjectService;
	}
	
	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackProjectService")	
	public void setDegreeFeedbackProjectService(
			IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService) {
		this.degreeFeedbackProjectService = degreeFeedbackProjectService;
	}
	
	public IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> getDegreeFeedbackItemService() {
		return degreeFeedbackItemService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackItemService")		
	public void setDegreeFeedbackItemService(
			IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService) {
		this.degreeFeedbackItemService = degreeFeedbackItemService;
	}

	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackLevelService")			
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
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
	
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
	}		
	
	private void initData(String type) throws ServiceException, Exception {
		if ( "list".equals(type) ) {
			this.loadDataForList();					
		}
		if ( "enterScore".equals(type) ) {
			this.loadDataForEnterScore(false);
		}
		if ( "viewScore".equals(type) ) {
			this.loadDataForEnterScore(true);
		}
	}
	
	private void loadDataForList() throws ServiceException, Exception {
		BbEmployee employee = this.employeeService.findByAccountId( this.getAccountId() );
		if (null == employee) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) );
		}
		DefaultResult<List<DegreeFeedbackProjectVO>> result = this.degreeFeedbackProjectService
				.findByPublishFlag2ValueObject(YesNo.YES, employee.getEmpId());
		if ( result.getValue() != null ) {
			this.projects = result.getValue();
		}		
	}
	
	private void loadDataForEnterScore(boolean roleViewEmployeeLimit) throws ServiceException, Exception {
		this.transformFields2ValueObject(this.project, new String[]{"oid"});
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.findObjectByOid(this.project);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.project = result.getValue();
		if (!roleViewEmployeeLimit) {
			this.ownerMap = this.employeeService.findForMapByDegreeFeedbackProjectOwner(
					true, this.project.getOid());
		} else {
			this.loadDropListEmployeeMapForScoreView();
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", this.project.getOid());
		this.items = this.degreeFeedbackItemService.findListByParams(paramMap);
		Map<String, String> orderParam = new HashMap<String, String>();
		orderParam.put("value", "asc");
		this.levels = this.degreeFeedbackLevelService.findListByParams(paramMap, null, orderParam);
	}
	
	private void loadDropListEmployeeMapForScoreView() throws ServiceException, Exception {
		this.ownerMap = this.employeeService.findForMapByDegreeFeedbackProjectOwner(true, this.project.getOid());
		if ( YesNo.YES.equals(super.getIsSuperRole()) ) {
			return;
		}
		// 非系統管理員權限, 且如果有設定 bb_report_role_view資料後, 只能看設定的員工.
		Map<String, String> roleLimitViewEmployees = this.reportRoleViewLogicService.findForEmployeeMap(
				false, super.getAccountId());
		if (null == roleLimitViewEmployees || roleLimitViewEmployees.size()<1) {
			return;
		}
		Map<String, String> roleViewAllowMixProjectOwnerEmployeeMap = this.providedSelectZeroDataMap( true );
		for (Map.Entry<String, String> item : roleLimitViewEmployees.entrySet()) {
			if ( !StringUtils.isBlank( this.ownerMap.get(item.getKey()) ) ) {
				roleViewAllowMixProjectOwnerEmployeeMap.put(item.getKey(), item.getValue());
			}
		}
		this.ownerMap.clear();
		this.ownerMap.putAll( roleViewAllowMixProjectOwnerEmployeeMap );
	}	
	
	/**
	 * bsc.degreeFeedbackProjectListAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0002Q")
	public String execute() throws Exception {
		try {
			this.initData("list");
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
	 * bsc.degreeFeedbackProjectEnterScoreAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG005D0003Q")
	public String enterScore() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("enterScore");
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
	 * bsc.degreeFeedbackProjectEnterReportAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG005D0004Q")	
	public String enterReport() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("viewScore");
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
	
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public List<DegreeFeedbackProjectVO> getProjects() {
		return projects;
	}

	public void setProjects(List<DegreeFeedbackProjectVO> projects) {
		this.projects = projects;
	}

	public DegreeFeedbackProjectVO getProject() {
		return project;
	}

	public void setProject(DegreeFeedbackProjectVO project) {
		this.project = project;
	}

	public Map<String, String> getOwnerMap() {
		return ownerMap;
	}

	public void setOwnerMap(Map<String, String> ownerMap) {
		this.ownerMap = ownerMap;
	}

	public List<BbDegreeFeedbackItem> getItems() {
		return items;
	}

	public void setItems(List<BbDegreeFeedbackItem> items) {
		this.items = items;
	}

	public List<BbDegreeFeedbackLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<BbDegreeFeedbackLevel> levels) {
		this.levels = levels;
	}

}
