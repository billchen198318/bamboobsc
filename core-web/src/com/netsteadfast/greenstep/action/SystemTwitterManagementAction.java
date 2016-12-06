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

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysTwitter;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysTwitterService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysTwitterVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemTwitterManagementAction")
@Scope
public class SystemTwitterManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 7587401520199154429L;
	protected Logger logger=Logger.getLogger(SystemTwitterManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService;
	private Map<String, String> sysMap = this.providedSelectZeroDataMap(true);
	private SysTwitterVO sysTwitter = new SysTwitterVO(); // 給edit用
	
	public SystemTwitterManagementAction() {
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
	
	public ISysTwitterService<SysTwitterVO, TbSysTwitter, String> getSysTwitterService() {
		return sysTwitterService;
	}

	@Autowired
	@Resource(name="core.service.SysTwitterService")
	@Required			
	public void setSysTwitterService(
			ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService) {
		this.sysTwitterService = sysTwitterService;
	}

	private void initData() throws ServiceException, Exception {
		this.sysMap = this.sysService.findSysMap(super.getBasePath(), true);
	}	
	
	private void loadSysTwitterData() throws ServiceException, Exception {
		sysTwitter.setOid( this.getFields().get("oid") );
		DefaultResult<SysTwitterVO> result = this.sysTwitterService.findObjectByOid(sysTwitter);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.sysTwitter = result.getValue();
		this.getFields().put("content", new String(this.sysTwitter.getContent(), "utf8") );
		SysVO sys = new SysVO();
		sys.setSysId( this.sysTwitter.getSystem() );
		DefaultResult<SysVO> sysResult = this.sysService.findByUK(sys);
		if ( sysResult.getValue()==null ) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		this.getFields().put("systemOid", sys.getOid());
	}
	
	/**
	 * core.systemTwitterManagementAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010Q")
	public String execute() throws Exception {
		try {
			this.initData();
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
	 * core.systemTwitterCreateAction.action
	 * 
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG001D0010A")	
	public String create() throws Exception {
		try {
			this.initData();
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
	 * core.systemTwitterEditAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysTwitterData();
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

	public Map<String, String> getSysMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysMap);
		return sysMap;
	}

	public SysTwitterVO getSysTwitter() {
		return sysTwitter;
	}
	
}
