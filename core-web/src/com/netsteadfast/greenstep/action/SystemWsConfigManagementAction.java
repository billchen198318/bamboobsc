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
import com.netsteadfast.greenstep.po.hbm.TbSysWsConfig;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysWsConfigService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysVO;
import com.netsteadfast.greenstep.vo.SysWsConfigVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemWsConfigManagementAction")
@Scope
public class SystemWsConfigManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 67003764675055920L;
	protected Logger logger=Logger.getLogger(SystemWsConfigManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService;
	private Map<String, String> sysMap = this.providedSelectZeroDataMap(true);
	private SysWsConfigVO sysWsConfig = new SysWsConfigVO(); // edit模式用
	private String selectValue = ""; // edit模式用 , 放 TB_SYS.OID
	
	public SystemWsConfigManagementAction() {
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
	
	public ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> getSysWsConfigService() {
		return sysWsConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysWsConfigService")
	@Required			
	public void setSysWsConfigService(
			ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService) {
		this.sysWsConfigService = sysWsConfigService;
	}

	private void initData() throws ServiceException, Exception {
		this.sysMap = this.sysService.findSysMap(super.getBasePath(), true);
	}
	
	private void loadSysWsConfigData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysWsConfig, new String[]{"oid"} );
		DefaultResult<SysWsConfigVO> result = this.sysWsConfigService.findObjectByOid(sysWsConfig);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysWsConfig = result.getValue();
		SysVO sys = new SysVO();
		sys.setSysId(this.sysWsConfig.getSystem());
		DefaultResult<SysVO> sResult = this.sysService.findByUK(sys);
		if (sResult.getValue()!=null) {
			this.selectValue = sResult.getValue().getOid();
		}
	}
	
	/**
	 * core.systemWsConfigManagementAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001Q")
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
	 * core.systemWsConfigCreateAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001A")	
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
	 * core.systemWsConfigEditAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0001E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysWsConfigData();
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getSysMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysMap);
		return sysMap;
	}

	public SysWsConfigVO getSysWsConfig() {
		return sysWsConfig;
	}

	public void setSysWsConfig(SysWsConfigVO sysWsConfig) {
		this.sysWsConfig = sysWsConfig;
	}

	public String getSelectValue() {
		return selectValue;
	}

}
