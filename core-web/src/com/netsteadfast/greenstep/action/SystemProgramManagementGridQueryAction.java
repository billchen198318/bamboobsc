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
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.po.hbm.TbSysProgMultiName;
import com.netsteadfast.greenstep.service.ISysProgMultiNameService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.vo.SysProgMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemProgramManagementGridQueryAction")
@Scope
public class SystemProgramManagementGridQueryAction extends BaseQueryGridJsonAction {
	private static final long serialVersionUID = -986466520750467708L;
	protected Logger logger=Logger.getLogger(SystemProgramManagementGridQueryAction.class);
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> sysProgMultiNameService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new ArrayList<Map<String, String>>();
	
	public SystemProgramManagementGridQueryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	public void setSysProgService(
			ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}
	
	@JSON(serialize=false)
	public ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> getSysProgMultiNameService() {
		return sysProgMultiNameService;
	}

	@Autowired
	@Resource(name="core.service.SysProgMultiNameService")
	public void setSysProgMultiNameService(
			ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> sysProgMultiNameService) {
		this.sysProgMultiNameService = sysProgMultiNameService;
	}	
	
	private void query() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysProgVO>> queryResult = this.sysProgService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES; // 只要有查詢 SQL 沒有產生 Exception , 就是成功, 查尋Grid沒資料也算是一種OK狀態
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData(queryResult.getValue());		
	}
	
	private void setGridData(List<SysProgVO> searchList) throws Exception {
		
		if (searchList==null || searchList.size()<1) {
			return;
		}
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "progId", "name", "url", "progSystem", "itemType"});		
	}	
	
	private void queryMultiName() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysProgMultiNameVO>> queryResult = this.sysProgMultiNameService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES; 
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData2(queryResult.getValue());		
	}	
	
	private void setGridData2(List<SysProgMultiNameVO> searchList) throws Exception {
		if (searchList==null || searchList.size()<1) {
			return;
		}
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "progId", "localeCode", "name", "enableFlag", "defaultName"});
	}
	
	/**
	 * core.systemProgramManagementGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002Q")
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
	 * core.systemProgramMultiNameGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E_S00")
	public String doQueryMultiName() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.queryMultiName();
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
