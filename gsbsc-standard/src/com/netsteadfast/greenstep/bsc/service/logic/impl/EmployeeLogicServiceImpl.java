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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeHierService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeOrgaService;
import com.netsteadfast.greenstep.bsc.service.IKpiEmplService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IPdcaItemOwnerService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IPdcaOwnerService;
import com.netsteadfast.greenstep.bsc.service.IReportRoleViewService;
import com.netsteadfast.greenstep.bsc.service.ITsaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.logic.IEmployeeLogicService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbEmployeeHier;
import com.netsteadfast.greenstep.po.hbm.BbEmployeeOrga;
import com.netsteadfast.greenstep.po.hbm.BbKpiEmpl;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbPdcaItemOwner;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbPdcaOwner;
import com.netsteadfast.greenstep.po.hbm.BbReportRoleView;
import com.netsteadfast.greenstep.po.hbm.BbTsaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.service.logic.IRoleLogicService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.EmployeeHierVO;
import com.netsteadfast.greenstep.vo.EmployeeOrgaVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiEmplVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaItemOwnerVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaOwnerVO;
import com.netsteadfast.greenstep.vo.ReportRoleViewVO;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.UserRoleVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.EmployeeLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class EmployeeLogicServiceImpl extends BscBaseLogicService implements IEmployeeLogicService {
	protected Logger logger=Logger.getLogger(EmployeeLogicServiceImpl.class);
	private final static String TREE_ICON_ID = "PERSON";
	private IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService;
	private ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService;
	private ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService;
	private IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService;
	private IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	private IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> pdcaOwnerService;
	private IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> pdcaItemOwnerService;
	private IRoleLogicService roleLogicService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	private IEmployeeHierService<EmployeeHierVO, BbEmployeeHier, String> employeeHierService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	
	public EmployeeLogicServiceImpl() {
		super();
	}
	
	public IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> getEmployeeOrgaService() {
		return employeeOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeOrgaService")
	@Required	
	public void setEmployeeOrgaService(IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService) {
		this.employeeOrgaService = employeeOrgaService;
	}	
	
	public ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> getSysMsgNoticeService() {
		return sysMsgNoticeService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeService")
	@Required			
	public void setSysMsgNoticeService(
			ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService) {
		this.sysMsgNoticeService = sysMsgNoticeService;
	}

	public ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> getSysCalendarNoteService() {
		return sysCalendarNoteService;
	}

	@Autowired
	@Resource(name="core.service.SysCalendarNoteService")
	@Required	
	public void setSysCalendarNoteService(
			ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> sysCalendarNoteService) {
		this.sysCalendarNoteService = sysCalendarNoteService;
	}
	
	public IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> getReportRoleViewService() {
		return reportRoleViewService;
	}

	@Autowired
	@Resource(name="bsc.service.ReportRoleViewService")
	@Required		
	public void setReportRoleViewService(
			IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService) {
		this.reportRoleViewService = reportRoleViewService;
	}

	public IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> getKpiEmplService() {
		return kpiEmplService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiEmplService")
	@Required		
	public void setKpiEmplService(
			IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService) {
		this.kpiEmplService = kpiEmplService;
	}

	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackAssignService")
	@Required		
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}

	public IMeasureDataService<MeasureDataVO, BbMeasureData, String> getMeasureDataService() {
		return measureDataService;
	}

	@Autowired
	@Resource(name="bsc.service.MeasureDataService")
	@Required		
	public void setMeasureDataService(
			IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService) {
		this.measureDataService = measureDataService;
	}
	
	public IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> getPdcaOwnerService() {
		return pdcaOwnerService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaOwnerService")
	@Required		
	public void setPdcaOwnerService(IPdcaOwnerService<PdcaOwnerVO, BbPdcaOwner, String> pdcaOwnerService) {
		this.pdcaOwnerService = pdcaOwnerService;
	}

	public IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> getPdcaItemOwnerService() {
		return pdcaItemOwnerService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaItemOwnerService")
	@Required		
	public void setPdcaItemOwnerService(
			IPdcaItemOwnerService<PdcaItemOwnerVO, BbPdcaItemOwner, String> pdcaItemOwnerService) {
		this.pdcaItemOwnerService = pdcaItemOwnerService;
	}
	
	public IRoleLogicService getRoleLogicService() {
		return roleLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.RoleLogicService")
	@Required		
	public void setRoleLogicService(IRoleLogicService roleLogicService) {
		this.roleLogicService = roleLogicService;
	}	
	
	public IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> getMonitorItemScoreService() {
		return monitorItemScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.MonitorItemScoreService")
	@Required	
	public void setMonitorItemScoreService(
			IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService) {
		this.monitorItemScoreService = monitorItemScoreService;
	}	
	
	public IEmployeeHierService<EmployeeHierVO, BbEmployeeHier, String> getEmployeeHierService() {
		return employeeHierService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeHierService")
	@Required		
	public void setEmployeeHierService(IEmployeeHierService<EmployeeHierVO, BbEmployeeHier, String> employeeHierService) {
		this.employeeHierService = employeeHierService;
	}
	
	public IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> getPdcaMeasureFreqService() {
		return pdcaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaMeasureFreqService")
	@Required			
	public void setPdcaMeasureFreqService(
			IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService) {
		this.pdcaMeasureFreqService = pdcaMeasureFreqService;
	}

	public ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> getTsaMeasureFreqService() {
		return tsaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMeasureFreqService")
	@Required		
	public void setTsaMeasureFreqService(
			ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService) {
		this.tsaMeasureFreqService = tsaMeasureFreqService;
	}

	private boolean isAdministrator(String account) {
		if (account.equals("admin") || account.equals(Constants.SYSTEM_BACKGROUND_USER)) {
			return true;
		}
		return false;
	}	
	
	private AccountVO tranAccount(EmployeeVO employee) throws Exception {
		AccountVO account = new AccountVO();
		account.setAccount(employee.getAccount());
		account.setOnJob(YesNo.YES);
		account.setPassword( this.getAccountService().tranPassword(employee.getPassword()) );		
		return account;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<EmployeeVO> create(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getEmpId())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", employee.getEmpId());
		if (this.getEmployeeService().countByParams(params) > 0) {
			throw new ServiceException("Please change another Id!");
		}
		AccountVO account = this.tranAccount(employee);
		if (this.isAdministrator(account.getAccount())) {
			throw new ServiceException("Please change another Account!");
		}		
		
		DefaultResult<AccountVO> mResult = this.getAccountService().saveObject(account);
		if (mResult.getValue()==null) {
			throw new ServiceException( mResult.getSystemMessage().getValue() );
		}		
		DefaultResult<EmployeeVO> result = this.getEmployeeService().saveObject(employee);
		employee = result.getValue();
		this.createEmployeeOrganization(employee, organizationOid);
		
		// create default role
		UserRoleVO userRole = new UserRoleVO();
		userRole.setAccount(employee.getAccount());
		userRole.setRole( this.roleLogicService.getDefaultUserRole() );
		userRole.setDescription(employee.getAccount() + " `s role!");
		this.getUserRoleService().saveObject(userRole);
		
		this.createHierarchy(employee, BscConstants.EMPLOYEE_HIER_ZERO_OID);
		
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<EmployeeVO> update(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		EmployeeVO dbEmployee = this.findEmployeeData( employee.getOid() );
		this.deleteEmployeeOrganization( dbEmployee );
		employee.setAccount(dbEmployee.getAccount() );
		employee.setEmpId( dbEmployee.getEmpId() );	
		DefaultResult<EmployeeVO> result = this.getEmployeeService().updateObject(employee);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.createEmployeeOrganization(result.getValue(), organizationOid);		
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<AccountVO> updatePassword(EmployeeVO employee, String newPassword) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) 
				|| super.isBlank(employee.getPassword()) || super.isBlank(newPassword) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		EmployeeVO dbEmployee = this.findEmployeeData(employee.getOid());
		AccountVO account = this.findAccountData( dbEmployee.getAccount() );
		if (!account.getPassword().equals( this.getAccountService().tranPassword(employee.getPassword()) ) ) {
			throw new ServiceException("The current password(old password) is incorrect!");
		}		
		account.setPassword( this.getAccountService().tranPassword(newPassword) );		
		return getAccountService().updateObject(account);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(EmployeeVO employee) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		employee = this.findEmployeeData(employee.getOid());
		AccountVO account = this.findAccountData(employee.getAccount());
		if (this.isAdministrator(account.getAccount())) {
			throw new ServiceException("Administrator cannot delete!");
		}
		
		// check account data for other table use.
		this.checkInformationRelated(account, employee);
		
		this.deleteEmployeeOrganization(employee);
		
		// delete user role
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account.getAccount());
		List<TbUserRole> userRoles = this.getUserRoleService().findListByParams(params);
		for (int i=0; userRoles!=null && i<userRoles.size(); i++) {
			TbUserRole uRole = userRoles.get(i);
			this.getUserRoleService().delete(uRole);
		}
		
		// delete BB_REPORT_ROLE_VIEW
		params.clear();
		params.put("idName", account.getAccount());
		List<BbReportRoleView> reportRoleViews = this.reportRoleViewService.findListByParams(params);
		for (int i=0; reportRoleViews!=null && i<reportRoleViews.size(); i++) {
			BbReportRoleView reportRoleView = reportRoleViews.get( i );
			this.reportRoleViewService.delete(reportRoleView);
		}
		
		// delete from BB_MEASURE_DATA where EMP_ID = :empId
		this.measureDataService.deleteForEmpId( employee.getEmpId() );
		
		this.monitorItemScoreService.deleteForEmpId( employee.getEmpId() );
		
		this.deleteHierarchy(employee);
		
		this.getAccountService().deleteByPKng(account.getOid());
		return getEmployeeService().deleteObject(employee);
	}
	
	private void checkInformationRelated(AccountVO account, EmployeeVO employee) throws ServiceException, Exception {
		if (account==null || super.isBlank(account.getAccount()) 
				|| employee==null || super.isBlank(employee.getEmpId()) ) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		
		// tb_sys_msg_notice
		params.put("toAccount", account.getAccount());
		if (this.sysMsgNoticeService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// tb_sys_calendar_note
		params.clear();
		params.put("account", account.getAccount());
		if (this.sysCalendarNoteService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_kpi_empl
		params.clear();
		params.put("empId", employee.getEmpId());
		if (this.kpiEmplService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_pdca_owner
		if (this.pdcaOwnerService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_pdca_item_owner
		if (this.pdcaItemOwnerService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_pdca_measure_freq
		params.clear();
		params.put("empId", employee.getEmpId());
		if (this.pdcaMeasureFreqService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_tsa_measure_freq
		if (this.tsaMeasureFreqService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// bb_degree_feedback_assign
		params.clear();
		params.put("ownerId", employee.getEmpId());
		if (this.degreeFeedbackAssignService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		params.clear();
		params.put("raterId", employee.getEmpId());
		if (this.degreeFeedbackAssignService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		if (this.foundChild(employee)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
	}
	
	private void createEmployeeOrganization(EmployeeVO employee, List<String> organizationOid) throws ServiceException, Exception {
		if (employee==null || StringUtils.isBlank(employee.getEmpId()) 
				|| organizationOid==null || organizationOid.size() < 1 ) {
			return;
		}
		for (String oid : organizationOid) {
			OrganizationVO organization = this.findOrganizationData(oid);
			EmployeeOrgaVO empOrg = new EmployeeOrgaVO();
			empOrg.setEmpId(employee.getEmpId());
			empOrg.setOrgId(organization.getOrgId());
			this.employeeOrgaService.saveObject(empOrg);
		}		
	}
	
	private void deleteEmployeeOrganization(EmployeeVO employee) throws ServiceException, Exception {
		if (employee==null || StringUtils.isBlank(employee.getEmpId()) ) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", employee.getEmpId());
		List<BbEmployeeOrga> searchList = this.employeeOrgaService.findListByParams(params);
		if (searchList==null || searchList.size()<1 ) {
			return;
		}
		for (BbEmployeeOrga empOrg : searchList) {
			this.employeeOrgaService.delete(empOrg);
		}		
	}
	
	private void createHierarchy(EmployeeVO employee, String supOid) throws ServiceException, Exception {
		if (null == employee || super.isBlank(employee.getOid()) || super.isBlank(supOid)) {
			return;
		}
		EmployeeHierVO hier = new EmployeeHierVO();
		hier.setEmpOid(employee.getOid());
		hier.setSupOid(supOid);
		this.employeeHierService.saveObject(hier);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public List<Map<String, Object>> getTreeData(String basePath) throws ServiceException, Exception {
		List<Map<String, Object>> items = new LinkedList<Map<String, Object>>();
		List<EmployeeVO> empList = this.getEmployeeService().findForJoinHier(); 
		if (empList==null || empList.size()<1 ) {
			return items;
		}
		for (EmployeeVO emp : empList) {
			// 先放沒有父親的員工資料
			if (!(super.isBlank(emp.getSupOid()) || BscConstants.EMPLOYEE_HIER_ZERO_OID.equals(emp.getSupOid()) ) ) {
				continue;
			}
			Map<String, Object> parentDataMap = new LinkedHashMap<String, Object>();
			parentDataMap.put("type", "parent");
			parentDataMap.put("id", emp.getOid());
			parentDataMap.put("name", IconUtils.getMenuIcon(basePath, TREE_ICON_ID) + StringEscapeUtils.escapeHtml4(this.getTreeShowName(emp)) );
			parentDataMap.put("oid", emp.getOid());
			items.add(parentDataMap);
		}
		// 再開始放孩子
		for (int ix=0; ix<items.size(); ix++) {
			Map<String, Object> parentDataMap=items.get(ix);
			String oid = (String)parentDataMap.get("oid");
			this.getTreeData(basePath, parentDataMap, empList, oid);
		}
		return items;
	}
	
	private void getTreeData(String basePath, Map<String, Object> putObject, List<EmployeeVO> searchList, String supOid) throws Exception {
		List<String> childList = new LinkedList<String>();
		this.getChildEmpLevelOne(searchList, supOid, childList);
		if (childList.size()<1) {
			return;
		}
		for (String childEmpOid : childList) {
			EmployeeVO emp = this.getEmployeeFromSearchList(searchList, childEmpOid, false);
			EmployeeVO childEmp = this.getEmployeeFromSearchList(searchList, childEmpOid, true);
			if (emp==null) {
				continue;
			}
			Map<String, Object> thePutObject=null;
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> childrenList = (List<Map<String, Object>>)putObject.get("children");
			if (childrenList==null) {
				childrenList=new LinkedList<Map<String, Object>>();
			}
			Map<String, Object> nodeMap=new LinkedHashMap<String, Object>();
			nodeMap.put("id", emp.getOid());
			nodeMap.put("name", IconUtils.getMenuIcon(basePath, TREE_ICON_ID) + StringEscapeUtils.escapeHtml4(this.getTreeShowName(emp)) );	
			nodeMap.put("oid", emp.getOid() );
			childrenList.add(nodeMap);
			putObject.put("children", childrenList);
			if (childEmp!=null) {					
				thePutObject=nodeMap;
			} else {
				nodeMap.put("type", "Leaf");
				thePutObject=putObject;
			}
			if (childEmp!=null) {
				this.getTreeData(basePath, thePutObject, searchList, childEmpOid);
			}			
		}	
	}	
	
	private List<String> getChildEmpLevelOne(List<EmployeeVO> searchList, String supOid, List<String> childList) throws Exception {
		if (childList==null) {
			childList=new LinkedList<String>();
		}		 
		for (EmployeeVO emp : searchList) {
			if (supOid.equals(emp.getSupOid())) {
				childList.add(emp.getOid());
			} 
		}
		return childList;
	}
	
	private EmployeeVO getEmployeeFromSearchList(List<EmployeeVO> searchList, String empOid, boolean isChild) throws Exception {
		for (EmployeeVO emp : searchList) {
			if (!isChild) {
				if (emp.getOid().equals(empOid)) {
					return emp;
				}				
			} else {
				if (emp.getSupOid().equals(empOid)) {
					return emp;
				}					
			}
		}
		return null;
	}		
	
	private String getTreeShowName(EmployeeVO employee) throws Exception {
		if ( !super.isBlank(employee.getJobTitle()) ) {
			return employee.getEmpId() + " - " + StringEscapeUtils.escapeHtml4(employee.getFullName()) + " ( " + employee.getJobTitle().trim() + " )";
		}
		return employee.getEmpId() + " - " + StringEscapeUtils.escapeHtml4(employee.getFullName());
	}
	
	private boolean foundChild(EmployeeVO employee) throws ServiceException, Exception {
		if (null == employee || super.isBlank(employee.getOid())) {
			return false;
		}		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supOid", employee.getOid());	
		if (this.employeeHierService.countByParams(params) > 0 ) {
			return true;
		}
		return false;
	}
	
	private boolean foundChild(String supOid, String checkEmpOid) throws ServiceException, Exception {
		List<EmployeeVO> treeList = this.getEmployeeService().findForJoinHier();
		if (treeList==null || treeList.size() <1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		boolean f = false;
		List<EmployeeVO> childList=new LinkedList<EmployeeVO>();		
		this.getChild(checkEmpOid, treeList, childList);
		for (int ix=0; childList!=null && ix<childList.size(); ix++) {		
			if (childList.get(ix).getOid().equals(checkEmpOid)) {
				f = true;
			}
		}
		return f;
	}
	
	private void getChild(String supOid, List<EmployeeVO> tree, List<EmployeeVO> put) throws Exception {
		if (put==null || tree==null) {
			return;
		}		
		if (StringUtils.isBlank(supOid) || BscConstants.EMPLOYEE_HIER_ZERO_OID.equals(supOid) ) {
			return;
		}		
		for (EmployeeVO emp : tree) {
			if (emp.getSupOid().equals(supOid)) {
				put.add(emp);
				this.getChild(emp.getOid(), tree, put);
			}
		}
	}		
	
	private void deleteHierarchy(EmployeeVO employee) throws ServiceException, Exception {
		if (null == employee || super.isBlank(employee.getOid())) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empOid", employee.getOid());
		List<BbEmployeeHier> hierList = this.employeeHierService.findListByParams(params);
		if (null == hierList) {
			return;
		}
		for (BbEmployeeHier hier : hierList) {
			employeeHierService.delete(hier);
		}
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> updateSupervisor(EmployeeVO employee, String supervisorOid) throws ServiceException, Exception {
		if (employee==null || super.isBlank(employee.getOid()) || super.isBlank(supervisorOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.FALSE);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)) );
		employee = this.findEmployeeData(employee.getOid());
		this.deleteHierarchy(employee);
		if ("root".equals(supervisorOid) || "r".equals(supervisorOid)) {
			this.createHierarchy(employee, BscConstants.EMPLOYEE_HIER_ZERO_OID);
		} else {
			EmployeeVO newHierEmployee = this.findEmployeeData(supervisorOid);
			// 找當前員工的的資料, 不因該存在要update的新關聯主管
			if ( this.foundChild(employee.getOid(), newHierEmployee.getOid()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			this.createHierarchy(employee, newHierEmployee.getOid());			
		}
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );
		return result;
	}

	/**
	 * for upgrade 0.7.1 need data
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public void initHierarchyForFirst() throws ServiceException, Exception {
		List<EmployeeVO> employeeList = this.getEmployeeService().findListVOByParams(null);
		if (null == employeeList || employeeList.size() < 1) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (EmployeeVO employee : employeeList) {
			paramMap.clear();
			paramMap.put("empOid", employee.getOid());
			if (this.employeeHierService.countByParams(paramMap) > 0) {
				continue;
			}
			this.createHierarchy(employee, BscConstants.EMPLOYEE_HIER_ZERO_OID);
		}
	}
	
	/**
	 * 這個 Method 的 ServiceMethodAuthority 權限給查詢狀態
	 * 這裡的 basePath 只是要取 getTreeData 時參數要用, 再這是沒有用處的
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<String> createOrgChartData(String basePath, EmployeeVO currentEmployee) throws ServiceException, Exception {
		if (null != currentEmployee && !super.isBlank(currentEmployee.getOid())) {
			currentEmployee = this.findEmployeeData( currentEmployee.getOid() );
		}
		List<Map<String, Object>> treeMap = this.getTreeData(basePath);
		if (null == treeMap || treeMap.size() < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		this.resetTreeMapContentForOrgChartData(treeMap, currentEmployee);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("name", "Employee hierarchy");
		rootMap.put("title", "hierarchy structure");
		rootMap.put("children", treeMap);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(rootMap);		
		String uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");			
		
		DefaultResult<String> result = new DefaultResult<String>();
		result.setValue(uploadOid);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS)) );
		return result;
	}
	
	/**
	 * 這個 Method 的 ServiceMethodAuthority 權限給查詢狀態
	 * 這裡的 basePath 只是要取 getTreeData 時參數要用, 再這是沒有用處的
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})	
	@Override
	public DefaultResult<Map<String, Object>> getOrgChartData(String basePath, EmployeeVO currentEmployee) throws ServiceException, Exception {
		if (null != currentEmployee && !super.isBlank(currentEmployee.getOid())) {
			currentEmployee = this.findEmployeeData( currentEmployee.getOid() );
		}
		List<Map<String, Object>> treeMap = this.getTreeData(basePath);
		if (null == treeMap || treeMap.size() < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		this.resetTreeMapContentForOrgChartData(treeMap, currentEmployee);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("name", "Employee hierarchy");
		rootMap.put("title", "hierarchy structure");
		rootMap.put("children", treeMap);
		
		DefaultResult<Map<String, Object>> result = new DefaultResult<Map<String, Object>>();
		result.setValue( rootMap );
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS)) );
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	private void resetTreeMapContentForOrgChartData(List<Map<String, Object>> childMapList, EmployeeVO currentEmployee) throws Exception {
		for (Map<String, Object> nodeMap : childMapList) {
			String nodeEmployeeOid = String.valueOf( nodeMap.get("id") ); // 與 node.get("oid") 一樣
			
			// 去除 OrgChart 不需要的資料
			nodeMap.remove("type");
			nodeMap.remove("id");
			nodeMap.remove("name");
			nodeMap.remove("oid");
			
			EmployeeVO nodeEmployee = this.findEmployeeData(nodeEmployeeOid);
			
			// OrgChart 需要的資料, nodeMap 需要填入 name 與 title
			if (currentEmployee != null && !super.isBlank(currentEmployee.getOid()) 
					&& currentEmployee.getOid().equals(nodeEmployeeOid)) { // 有帶入當前員工來區別顏色
				nodeMap.put("name", "<font color='#8A0808'>" + nodeEmployee.getEmpId() + " - " + nodeEmployee.getFullName() + "</font>" );
				nodeMap.put("title", "<font color='#8A0808'>" + ( super.isBlank(nodeEmployee.getJobTitle()) ? "no job description" : nodeEmployee.getJobTitle().trim() ) + "</font>" );				
			} else {
				nodeMap.put("name", nodeEmployee.getEmpId() + " - " + nodeEmployee.getFullName());
				nodeMap.put("title", ( super.isBlank(nodeEmployee.getJobTitle()) ? "no job description" : nodeEmployee.getJobTitle().trim() ) );				
			}
			
			if (nodeMap.get("children") != null && (nodeMap.get("children") instanceof List<?>)) { // 還有孩子項目資料
				this.resetTreeMapContentForOrgChartData( (List<Map<String, Object>>) nodeMap.get("children"), currentEmployee );
			}
		}
	}
	
}
