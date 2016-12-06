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

import java.util.LinkedHashMap;
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
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemMessageNoticeConfigManagementAction")
@Scope
public class SystemMessageNoticeConfigManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -4113135426895025803L;
	protected Logger logger=Logger.getLogger(SystemMessageNoticeConfigManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService;
	private Map<String, String> systemDataMap = new LinkedHashMap<String, String>();
	private SysMsgNoticeConfigVO sysMsgNoticeConfig = new SysMsgNoticeConfigVO();
	private String selectValue = ""; // edit模式用 , TB_SYS.OID
	
	public SystemMessageNoticeConfigManagementAction() {
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

	public ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> getSysMsgNoticeConfigService() {
		return sysMsgNoticeConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeConfigService")
	@Required		
	public void setSysMsgNoticeConfigService(
			ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService) {
		this.sysMsgNoticeConfigService = sysMsgNoticeConfigService;
	}

	private void initData() throws ServiceException, Exception {
		this.systemDataMap = this.sysService.findSysMap(super.getBasePath(), true);
	}
	
	private void loadSysMsgNoticeConfigData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysMsgNoticeConfig, new String[]{"oid"});
		DefaultResult<SysMsgNoticeConfigVO> result = this.sysMsgNoticeConfigService.findObjectByOid(sysMsgNoticeConfig);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysMsgNoticeConfig = result.getValue();
		SysVO sys = new SysVO();
		sys.setSysId(sysMsgNoticeConfig.getSystem());
		DefaultResult<SysVO> sysResult = this.sysService.findByUK(sys);
		if (sysResult.getValue()!=null) {
			this.selectValue = sysResult.getValue().getOid();
		}
	}

	/**
	 * core.systemMessageNoticeConfigManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005Q")
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
	 * core.systemMessageNoticeConfigCreateAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005A")
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
	 * core.systemMessageNoticeConfigEditAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0005E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysMsgNoticeConfigData();
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

	public Map<String, String> getSystemDataMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.systemDataMap);
		return systemDataMap;
	}

	public SysMsgNoticeConfigVO getSysMsgNoticeConfig() {
		return sysMsgNoticeConfig;
	}

	public void setSysMsgNoticeConfig(SysMsgNoticeConfigVO sysMsgNoticeConfig) {
		this.sysMsgNoticeConfig = sysMsgNoticeConfig;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}
	
}
