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
package com.netsteadfast.greenstep.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBpmnResourceManagementAction")
@Scope
public class SystemBpmnResourceManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -8874018162071109964L;
	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private SysBpmnResourceVO bpmnResource = new SysBpmnResourceVO();
	private List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
	private List<ProcessInstance> processInstances = new ArrayList<ProcessInstance>();
	private List<Task> tasks = new ArrayList<Task>();
	
	// ------------------------------------------------------------------------------------
	// CORE_PROG003D0005Q
	// ------------------------------------------------------------------------------------
	private Map<String, String> resourceMap = this.providedSelectZeroDataMap( true );
	
	// CORE_PROG003D0005A
	private Map<String, String> roleMap = this.providedSelectZeroDataMap( true );
	
	public SystemBpmnResourceManagementAction() {
		super();
	}
	
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")		
	@Required
	public void setSysBpmnResourceService(
			ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
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

	private void initData(String type) throws ServiceException, Exception {
		if ("roleAssignee".equals(type) || "createRoleAssignee".equals(type)) {
			this.resourceMap = this.sysBpmnResourceService.findForMap(true);
		}
		if ("createRoleAssignee".equals(type)) {
			this.roleMap = this.roleService.findForMap(true, false);
		}
	}
	
	private void loadResourceData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.bpmnResource, new String[]{"oid"});
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.findObjectByOid(bpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.bpmnResource = result.getValue();
	}
	
	private void queryProcess() throws Exception {
		this.processDefinitions = BusinessProcessManagementUtils.queryProcessDefinition(this.bpmnResource);
		this.processInstances = BusinessProcessManagementUtils.queryProcessInstance(this.bpmnResource);
		this.tasks = BusinessProcessManagementUtils.queryTask(this.bpmnResource);
	}
	
	/**
	 * core.systemBpmnResourceManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
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
	 * core.systemBpmnResourceCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004A")	
	public String create() throws Exception {
		try {
			this.initData("create");
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
	 * core.systemBpmnResourceEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.loadResourceData();
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
	 * core.systemBpmnResourceProcessListAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q_S00")
	public String processList() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("processList");
			this.loadResourceData();
			this.queryProcess();
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
	 * core.systemBpmnResourceRoleAssigneeManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0005Q")	
	public String roleAssignee() throws Exception {
		try {
			this.initData("roleAssignee");
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
	 * core.systemBpmnResourceRoleAssigneeCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0005A")	
	public String createRoleAssignee() throws Exception {
		try {
			this.initData("createRoleAssignee");
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

	public SysBpmnResourceVO getBpmnResource() {
		return bpmnResource;
	}

	public void setBpmnResource(SysBpmnResourceVO bpmnResource) {
		this.bpmnResource = bpmnResource;
	}

	public List<ProcessDefinition> getProcessDefinitions() {
		return processDefinitions;
	}

	public void setProcessDefinitions(List<ProcessDefinition> processDefinitions) {
		this.processDefinitions = processDefinitions;
	}

	public List<ProcessInstance> getProcessInstances() {
		return processInstances;
	}

	public void setProcessInstances(List<ProcessInstance> processInstances) {
		this.processInstances = processInstances;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Map<String, String> getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(Map<String, String> resourceMap) {
		this.resourceMap = resourceMap;
	}

	public Map<String, String> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map<String, String> roleMap) {
		this.roleMap = roleMap;
	}

}
