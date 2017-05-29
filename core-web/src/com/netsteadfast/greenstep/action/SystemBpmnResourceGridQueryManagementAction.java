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

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseQueryGridJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResourceRole;
import com.netsteadfast.greenstep.service.ISysBpmnResourceRoleService;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.vo.SysBpmnResourceRoleVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBpmnResourceGridQueryManagementAction")
@Scope
public class SystemBpmnResourceGridQueryManagementAction extends BaseQueryGridJsonAction {
	private static final long serialVersionUID = -1235676145744946045L;
	protected Logger logger=Logger.getLogger(SystemBpmnResourceGridQueryManagementAction.class);
	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new ArrayList<Map<String, String>>();
	
	public SystemBpmnResourceGridQueryManagementAction() {
		super();
	}

	@JSON(serialize=false)
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")		
	public void setSysBpmnResourceService(
			ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
	}
	
	@JSON(serialize=false)
	public ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> getSysBpmnResourceRoleService() {
		return sysBpmnResourceRoleService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceRoleService")
	public void setSysBpmnResourceRoleService(
			ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService) {
		this.sysBpmnResourceRoleService = sysBpmnResourceRoleService;
	}

	private void query() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysBpmnResourceVO>> queryResult = this.sysBpmnResourceService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES;
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData(queryResult.getValue());			
	}
	
	private void setGridData(List<SysBpmnResourceVO> searchList) throws Exception {
		if (searchList==null || searchList.size()<1) {
			return;
		}		
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "id", "deploymentId", "name", "description"});		
	}
	
	private void query2() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysBpmnResourceRoleVO>> queryResult = this.sysBpmnResourceRoleService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES;
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData2(queryResult.getValue());		
	}
	
	private void setGridData2(List<SysBpmnResourceRoleVO> searchList) throws Exception {
		if (searchList==null || searchList.size()<1) {
			return;
		}		
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "id", "role", "taskName", "name"});		
	}
	
	/**
	 * core.systemBpmnResourceManagementGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.query();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;
	}
	
	/**
	 * core.systemBpmnResourceRoleAssigneeManagementGridQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0005Q")
	public String queryRoleAssignee() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.query2();
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
	public String getPageOfShowRow() {
		return super.getPageOf().getShowRow();
	}
	
	@JSON
	@Override
	public String getPageOfSelect() {
		return super.getPageOf().getSelect();
	}
	
	@JSON
	@Override
	public String getPageOfCountSize() {
		return super.getPageOf().getCountSize();
	}
	
	@JSON
	@Override
	public String getPageOfSize() {
		return super.getPageOf().getSize();
	}		
	
	@JSON
	@Override
	public List<Map<String, String>> getItems() {
		return items;
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
	
}
