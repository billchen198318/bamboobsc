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
import com.netsteadfast.greenstep.po.hbm.TbSysTwitter;
import com.netsteadfast.greenstep.service.ISysTwitterService;
import com.netsteadfast.greenstep.vo.SysTwitterVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemTwitterManagementGridQueryAction")
@Scope
public class SystemTwitterManagementGridQueryAction extends BaseQueryGridJsonAction {
	private static final long serialVersionUID = 6490305810603418947L;
	protected Logger logger=Logger.getLogger(SystemTwitterManagementGridQueryAction.class);
	private ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new ArrayList<Map<String, String>>();
	
	public SystemTwitterManagementGridQueryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysTwitterService<SysTwitterVO, TbSysTwitter, String> getSysTwitterService() {
		return sysTwitterService;
	}

	@Autowired
	@Resource(name="core.service.SysTwitterService")		
	public void setSysTwitterService(
			ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService) {
		this.sysTwitterService = sysTwitterService;
	}

	private void query() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysTwitterVO>> queryResult = this.sysTwitterService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES; // 只要有查詢 SQL 沒有產生 Exception , 就是成功, 查尋Grid沒資料也算是一種OK狀態
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData(queryResult.getValue());			
	}
	
	private void setGridData(List<SysTwitterVO> searchList) throws Exception {
		if (searchList==null || searchList.size()<1) {
			return;
		}		
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "system", "title", "enableFlag"});			
	}
	
	/**
	 * core.systemTwitterManagementGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.query();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
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

}
