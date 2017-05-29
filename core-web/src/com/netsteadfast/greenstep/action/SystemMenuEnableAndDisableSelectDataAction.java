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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.service.logic.ISystemMenuLogicService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.vo.SysProgVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMenuEnableAndDisableSelectDataAction")
@Scope
public class SystemMenuEnableAndDisableSelectDataAction extends BaseJsonAction {
	private static final long serialVersionUID = 3193471536631207224L;
	protected Logger logger=Logger.getLogger(SystemMenuEnableAndDisableSelectDataAction.class);
	private ISystemMenuLogicService systemMenuLogicService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> disableItems=new LinkedList<Map<String, String>>();
	private List<Map<String, String>> enableItems=new LinkedList<Map<String, String>>();
	
	public SystemMenuEnableAndDisableSelectDataAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemMenuLogicService getSystemMenuLogicService() {
		return systemMenuLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemMenuLogicService")	
	public void setSystemMenuLogicService(
			ISystemMenuLogicService systemMenuLogicService) {
		this.systemMenuLogicService = systemMenuLogicService;
	}
	
	private void handlerIcon(List<SysProgVO> sysProgList) throws ServiceException, Exception {
		for (SysProgVO sysProg : sysProgList) {
			sysProg.setName( IconUtils.getMenuIcon(super.getBasePath(), sysProg.getIcon()) + sysProg.getName() );
		}
	}
	
	private void queryItems() throws ControllerException, ServiceException, Exception {
		String progOid = this.getFields().get("progOid");
		Map<String, List<SysProgVO>> searchDataMap = this.systemMenuLogicService.findForMenuSettingsEnableAndAll(progOid);
		List<SysProgVO> allList = searchDataMap.get("all");
		List<SysProgVO> enableList = searchDataMap.get("enable");
		for (int i=0; i<enableList.size(); i++) {
			for (int j=0; j<allList.size(); j++) {
				if (enableList.get(i).getProgId().equals( allList.get(j).getProgId() )) {
					allList.remove(j);
				}
			}
		}
		this.handlerIcon(allList);
		this.handlerIcon(enableList);
		String fields[] = new String[]{"oid", "name"};
		String mapKeys[] = new String[]{"key", "value"};
		this.disableItems = this.transformList2JsonDataMapList(allList, fields, mapKeys);
		this.enableItems = this.transformList2JsonDataMapList(enableList, fields, mapKeys);
		this.success = IS_YES;
	}

	/**
	 * core.systemMenuEnableAndDisableSelectDataAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0003Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.queryItems();
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
	public List<Map<String, String>> getDisableItems() {
		return disableItems;
	}

	@JSON
	public List<Map<String, String>> getEnableItems() {
		return enableItems;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
