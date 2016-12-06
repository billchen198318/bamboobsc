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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ReportRoleViewManagementAction")
@Scope
public class ReportRoleViewManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2883835026713254011L;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService; 
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;	
	private Map<String, String> roleMap = this.providedSelectZeroDataMap(true);
	private String defaultUrl = ""; // 沒有代入 oid 參術的 url
	
	public ReportRoleViewManagementAction() {
		super();
	}

	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required		
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}

	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required		
	public void setSysProgService(
			ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}	
	
	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}

	@Autowired
	@Resource(name="core.service.RoleService")
	@Required	
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required	
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")
	@Required		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}	
	
	private void initData() throws ServiceException, Exception {
		String progId = super.getActionMethodProgramId();
		TbSysProg prog = new TbSysProg();
		prog.setProgId(progId);
		prog = this.sysProgService.findByEntityUK(prog);
		if ( prog == null ) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) );
		}		
		TbSys sys = new TbSys();
		sys.setSysId( prog.getProgSystem() );
		sys = this.sysService.findByEntityUK(sys);
		if ( sys == null ) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) );
		}		
		this.defaultUrl = MenuSupportUtils.getUrl(
				super.getBasePath(), 
				sys, 
				prog, 
				super.getHttpServletRequest().getSession().getId());
		this.roleMap = this.roleService.findForMap(true, true);
	}
	
	private void loadAppendDatas() throws ServiceException, Exception {
		if ( super.isNoSelectId(this.getFields().get("oid")) ) {
			return;
		}
		RoleVO role = new RoleVO();
		role.setOid( this.getFields().get("oid") );
		DefaultResult<RoleVO> result = this.roleService.findObjectByOid(role);
		if ( result.getValue() == null ) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		role = result.getValue();
		List<String> emplOids = this.employeeService.findForAppendEmployeeOidsByReportRoleViewEmpl( role.getRole() );
		List<String> orgaOids = this.organizationService.findForAppendOrganizationOidsByReportRoleViewOrga( role.getRole() );
		List<String> emplNames = this.employeeService.findForAppendNames( emplOids );
		List<String> orgaNames = this.organizationService.findForAppendNames( orgaOids );
		StringBuilder sb = new StringBuilder();
		for (int i=0; emplOids!=null && i<emplOids.size(); i++) {
			sb.append(emplOids.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendEmplOids", sb.toString());
		sb.setLength(0);
		for (int i=0; emplNames!=null && i<emplNames.size(); i++) {
			sb.append(emplNames.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendEmplNames", sb.toString());
		
		sb.setLength(0);
		for (int i=0; orgaOids!=null && i<orgaOids.size(); i++) {
			sb.append(orgaOids.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendOrgaOids", sb.toString());
		sb.setLength(0);
		for (int i=0; orgaNames!=null && i<orgaNames.size(); i++) {
			sb.append(orgaNames.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendOrgaNames", sb.toString());	
	}
	
	/**
	 *  bsc.reportRoleViewManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0003Q")
	public String execute() throws Exception {
		try {
			this.initData();
			this.loadAppendDatas();
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

	public Map<String, String> getRoleMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.roleMap);
		return roleMap;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

}
