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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysIcon;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.service.ISysIconService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.util.LocaleLanguageUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysIconVO;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemProgramManagementAction")
@Scope
public class SystemProgramManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 869460482483361207L;
	protected Logger logger=Logger.getLogger(SystemProgramManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private Map<String, String> iconDataMap = new LinkedHashMap<String, String>();
	private Map<String, String> progSystemDataMap = new LinkedHashMap<String, String>();
	private String firstProgSystemValue = "";
	private String editProgSystemValue = "";
	private String firstIconValue = "";	
	private String editIconValue = "";
	private SysProgVO sysProg = new SysProgVO();
	private Map<String, Object> langDataMap = LocaleLanguageUtils.getMap();
	private String pageLocaleCode = UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext() );
	
	public SystemProgramManagementAction() {
		super();
	}
	
	public ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	@Autowired
	@Resource(name="core.service.SysIconService")
	@Required		
	public void setSysIconService(
			ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		this.sysIconService = sysIconService;
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
	
	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required			
	public void setSysProgService(
			ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}

	private void initData() throws ServiceException, Exception {
		this.initIconData();
		this.initSystemData();
	}

	private void initIconData() throws ServiceException, Exception {
		this.iconDataMap = IconUtils.getIconsSelectData();
		for (Map.Entry<String, String> entry : iconDataMap.entrySet()) {
			if (!StringUtils.isBlank(this.firstIconValue)) {
				continue;
			}
			this.firstIconValue = entry.getKey();
		}		
	}	
	
	private void initSystemData() throws ServiceException, Exception {
		this.progSystemDataMap = this.sysService.findSysMap(super.getBasePath(), true);
		for (Map.Entry<String, String> entry : progSystemDataMap.entrySet()) {
			if (!StringUtils.isBlank(this.firstProgSystemValue)) {
				continue;
			}
			this.firstProgSystemValue = entry.getKey();
		}			
	}
	
	/**
	 * 修改模式載入 TB_SYS_PROG 資料
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void loadSysProgData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysProg, new String[]{"oid"});
		DefaultResult<SysProgVO> result = this.sysProgService.findObjectByOid(this.sysProg);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		} 
		this.sysProg = result.getValue();
		
		TbSysIcon sysIcon = new TbSysIcon();
		sysIcon.setIconId(sysProg.getIcon());
		sysIcon = this.sysIconService.findByEntityUK(sysIcon);
		if (sysIcon!=null) {
			this.editIconValue = sysIcon.getOid();
		}
		
		TbSys sys = new TbSys();
		sys.setSysId(this.sysProg.getProgSystem());
		sys = this.sysService.findByEntityUK(sys);
		if (sys!=null) {
			this.editProgSystemValue = sys.getOid();
		}
		
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
	
	/**
	 * core.systemProgramManagementAction.action
	 * 
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002Q")
	public String execute() throws Exception {
		try {
			// nothing
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
	 * core.systemProgramCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002A")
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
	 * core.systemProgramEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysProgData();
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
	 * core.systemProgramMultiNameCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E_S00")
	public String createMultiName() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.loadSysProgData();
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

	public Map<String, String> getIconDataMap() {
		return iconDataMap;
	}

	public Map<String, String> getProgSystemDataMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.progSystemDataMap);
		return progSystemDataMap;
	}

	public String getFirstIconValue() {
		return firstIconValue;
	}

	public void setFirstIconValue(String firstIconValue) {
		this.firstIconValue = firstIconValue;
	}

	public String getEditIconValue() {
		return editIconValue;
	}

	public void setEditIconValue(String editIconValue) {
		this.editIconValue = editIconValue;
	}

	public String getFirstProgSystemValue() {
		return firstProgSystemValue;
	}

	public void setFirstProgSystemValue(String firstProgSystemValue) {
		this.firstProgSystemValue = firstProgSystemValue;
	}

	public String getEditProgSystemValue() {
		return editProgSystemValue;
	}

	public void setEditProgSystemValue(String editProgSystemValue) {
		this.editProgSystemValue = editProgSystemValue;
	}

	public SysProgVO getSysProg() {
		return sysProg;
	}

	public void setSysProg(SysProgVO sysProg) {
		this.sysProg = sysProg;
	}

	public Map<String, Object> getLangDataMap() {
		return langDataMap;
	}

	public String getPageLocaleCode() {
		return pageLocaleCode;
	}

}
