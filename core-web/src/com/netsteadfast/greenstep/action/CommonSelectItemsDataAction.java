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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseQueryGridJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.MenuItemType;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.SysVO;

/**
 * 主要提供下拉資料用的
 *
 */
@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonSelectItemsDataAction")
@Scope
public class CommonSelectItemsDataAction extends BaseQueryGridJsonAction {
	private static final long serialVersionUID = -6480690770314166344L;
	protected Logger logger=Logger.getLogger(CommonSelectItemsDataAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new LinkedList<Map<String, String>>();
	
	public CommonSelectItemsDataAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")	
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
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
	
	private SysVO loadSysValueObj(String sysOid) throws ServiceException, Exception {
		SysVO sys = new SysVO();
		sys.setOid(sysOid);
		DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
		if (sysResult.getValue()==null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		return sys;
	}

	private void queryItems() throws ServiceException, Exception {
		boolean pleaseSelect = YesNo.YES.equals(this.getFields().get("pleaseSelect"));
		if ("SYS".equals(this.getFields().get("type"))) { // 取 sys
			Map<String, String> sysMap = this.sysService.findSysMap(super.getBasePath(), pleaseSelect);
			this.resetPleaseSelectDataMapFromLocaleLang(sysMap);
			for (Map.Entry<String, String> entry : sysMap.entrySet()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("key", entry.getKey());
				dataMap.put("value", entry.getValue());
				this.items.add(dataMap);
			}
			this.success = IS_YES;
		}
		if ( "SYS_PROG".equals(this.getFields().get("type")) ) { // 取 sys_prog ITEM_TYPE='FOLDER'
			String sysOid = this.getFields().get("sysOid");
			SysVO sys = this.loadSysValueObj(sysOid);		
			Map<String, String> sysProgMap = this.sysProgService.findSysProgFolderMap(
					super.getBasePath(), sys.getSysId(), MenuItemType.FOLDER, pleaseSelect);
			this.resetPleaseSelectDataMapFromLocaleLang(sysProgMap);
			for (Map.Entry<String, String> entry : sysProgMap.entrySet()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("key", entry.getKey());
				dataMap.put("value", entry.getValue());
				this.items.add(dataMap);
			}
			this.success = IS_YES;
		}		
		if ("SYS_PROG_IN_MENU".equals(this.getFields().get("type")) ) { // 取 sys_prog.prog_id 存在 tb_sys_menu 的資料 
			String sysOid = this.getFields().get("sysOid");
			SysVO sys = this.loadSysValueObj(sysOid);	
			List<SysProgVO> menuProgList = this.sysProgService.findForInTheFolderMenuItems(sys.getSysId(), null, null);
			//this.items.add(super.providedSelectZeroDataMap(true));
			Map<String, String> dataMapFirst = new HashMap<String, String>();
			dataMapFirst.put("key", Constants.HTML_SELECT_NO_SELECT_ID);
			dataMapFirst.put("value", Constants.HTML_SELECT_NO_SELECT_NAME);
			this.resetPleaseSelectDataMapFromLocaleLang(dataMapFirst);
			this.items.add(dataMapFirst);
			if (menuProgList!=null) {
				for (SysProgVO sysProg : menuProgList) {
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("key", sysProg.getOid());
					dataMap.put("value", IconUtils.getMenuIcon(super.getBasePath(), sysProg.getIcon()) + StringEscapeUtils.escapeHtml4(sysProg.getName()) );
					this.items.add(dataMap);				
				}				
			}
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.commonSelectItemsDataAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_COMMON")
	public String execute() throws Exception {
		try {
			this.queryItems();			
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
	public List<Map<String, String>> getItems() {
		return items;
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
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
