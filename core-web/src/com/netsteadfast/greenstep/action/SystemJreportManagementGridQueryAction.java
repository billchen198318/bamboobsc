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
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.po.hbm.TbSysJreportParam;
import com.netsteadfast.greenstep.service.ISysJreportParamService;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemJreportManagementGridQueryAction")
@Scope
public class SystemJreportManagementGridQueryAction extends BaseQueryGridJsonAction {	
	private static final long serialVersionUID = 5640126284827542935L;
	protected Logger logger=Logger.getLogger(SystemJreportManagementGridQueryAction.class);
	private ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService;
	private ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new ArrayList<Map<String, String>>();
	
	public SystemJreportManagementGridQueryAction() {
		super();
	}

	@JSON(serialize=false)
	public ISysJreportService<SysJreportVO, TbSysJreport, String> getSysJreportService() {
		return sysJreportService;
	}

	@Autowired
	@Resource(name="core.service.SysJreportService")		
	public void setSysJreportService(
			ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService) {
		this.sysJreportService = sysJreportService;
	}

	@JSON(serialize=false)
	public ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> getSysJreportParamService() {
		return sysJreportParamService;
	}

	@Autowired
	@Resource(name="core.service.SysJreportParamService")	
	public void setSysJreportParamService(
			ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService) {
		this.sysJreportParamService = sysJreportParamService;
	}

	private void query() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysJreportVO>> queryResult = this.sysJreportService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES; // 只要有查詢 SQL 沒有產生 Exception , 就是成功, 查尋Grid沒資料也算是一種OK狀態
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridData(queryResult.getValue());		
	}
	
	private void setGridData(List<SysJreportVO> searchList) throws Exception {		
		if (searchList==null || searchList.size()<1) {
			return;
		}		
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, 
				new String[]{"oid", "reportId", "file", "isCompile", "description"});		
	}	
	
	private void queryParam() throws ControllerException, AuthorityException, ServiceException, Exception {
		QueryResult<List<SysJreportParamVO>> queryResult = this.sysJreportParamService.findGridResult(
				super.getSearchValue(), 
				super.getPageOf());
		this.success = IS_YES; // 只要有查詢 SQL 沒有產生 Exception , 就是成功, 查尋Grid沒資料也算是一種OK狀態
		if (queryResult.getValue()==null) {
			this.message=super.defaultString(queryResult.getSystemMessage().getValue());
			return;
		}
		this.setGridDataParam(queryResult.getValue());		
	}
	
	private void setGridDataParam(List<SysJreportParamVO> searchList) throws Exception {		
		if (searchList==null || searchList.size()<1) {
			return;
		}		
		this.items = super.transformSearchGridList2JsonDataMapList(
				searchList, new String[]{"oid", "reportId", "urlParam", "rptParam"});		
	}		
	
	/**
	 * core.systemJreportManagementGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q")
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
	 * core.systemJreportParameterGridQueryAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q_S00")
	public String doQueryParam() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.queryParam();
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
