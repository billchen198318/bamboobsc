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
import com.netsteadfast.greenstep.service.ISysIconService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.util.LocaleLanguageUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysIconVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.ApplicationSystemManagementAction")
@Scope
public class ApplicationSystemManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -7151451162903619193L;
	protected Logger logger=Logger.getLogger(ApplicationSystemManagementAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private Map<String, String> iconDataMap = new LinkedHashMap<String, String>();
	private String firstIconValue = "";
	private String editIconValue = "";
	private SysVO sys = new SysVO();
	private Map<String, Object> langDataMap = LocaleLanguageUtils.getMap();
	private String pageLocaleCode = UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext() );
	
	public ApplicationSystemManagementAction() {
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

	private void initIconData() throws ServiceException, Exception {
		this.iconDataMap = IconUtils.getIconsSelectData();
		for (Map.Entry<String, String> entry : iconDataMap.entrySet()) {
			if (!StringUtils.isBlank(this.firstIconValue)) {
				continue;
			}
			this.firstIconValue = entry.getKey();
		}		
	}
	
	private void loadSysData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sys, new String[]{"oid"});
		DefaultResult<SysVO> result = this.sysService.findObjectByOid(this.sys);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		} 
		this.sys = result.getValue();
		TbSysIcon sysIcon = new TbSysIcon();
		sysIcon.setIconId(sys.getIcon());
		sysIcon = this.sysIconService.findByEntityUK(sysIcon);
		if (sysIcon!=null) {
			this.editIconValue = super.defaultString(sysIcon.getOid());
		}
	}
	
	/**
	 * core.applicationSystemManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001Q")
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
	 * core.applicationSystemCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001A")
	public String create() throws Exception {
		try {
			this.initIconData();
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
	 * core.applicationSystemEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initIconData();
			this.loadSysData();
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
	 * 編輯多語言名稱設定檔的頁面, 幹突然不是很想寫這些程式, 幹人生好魯蛇(Loser), 魯蛇(Loser)努力也沒用, 哭 Orz...
	 * core.applicationSystemCreateMultiNameAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0001E_S00")
	public String createMultiName() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.loadSysData();
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

	public Map<String, String> getIconDataMap() {
		return iconDataMap;
	}

	public void setIconDataMap(Map<String, String> iconDataMap) {
		this.iconDataMap = iconDataMap;
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

	public SysVO getSys() {
		return sys;
	}

	public void setSys(SysVO sys) {
		this.sys = sys;
	}

	public Map<String, Object> getLangDataMap() {
		return langDataMap;
	}

	public String getPageLocaleCode() {
		if (StringUtils.isBlank(this.pageLocaleCode)) {
			return LocaleLanguageUtils.getDefault();
		}
		return pageLocaleCode;
	}

}
