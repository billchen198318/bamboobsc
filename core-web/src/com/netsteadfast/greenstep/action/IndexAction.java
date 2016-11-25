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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.model.MenuResultObj;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.LocaleLanguageUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SystemSettingConfigureUtils;

@ControllerAuthority(check=false)
@Controller("core.web.controller.IndexAction")
@Scope
public class IndexAction extends BaseSupportAction {
	private static final long serialVersionUID = -1938608913545322784L;
	protected Logger logger=Logger.getLogger(IndexAction.class);
	private String comboButtonMenuData = "";
	private String dialogData = "";
	private String treeJsonData = "[]";
	private String showConfigHost = YesNo.NO;
	private String sysDefaultCoreHost = "";
	
	public IndexAction() {
		super();
	}
	
	@ControllerMethodAuthority(programId="CORE_INDEX")
	public String execute() throws Exception {
		try {
			MenuResultObj menuData = MenuSupportUtils.getMenuData(
					super.getBasePath(), 
					super.getHttpServletRequest().getSession().getId(),
					UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext()));
			comboButtonMenuData = menuData.getHtmlData();
			dialogData = menuData.getDialogHtmlData();
			treeJsonData = MenuSupportUtils.getMenuTreeJsonDataStr(
					super.getBasePath(),
					UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!ApplicationSiteUtils.checkLoginUrlWithAllSysHostConfig(getHttpServletRequest())) {
				showConfigHost = YesNo.YES;
			}
		}
		return SUCCESS;
	}
	
	@ControllerMethodAuthority(programId="CORE_INDEX")
	public String showConfigHost() throws Exception {
		sysDefaultCoreHost = ApplicationSiteUtils.getBasePath(Constants.getSystem(), getHttpServletRequest());
		return SUCCESS;
	}
	
	public String getComboButtonMenuData() {
		return comboButtonMenuData;
	}

	public void setComboButtonMenuData(String comboButtonMenuData) {
		this.comboButtonMenuData = comboButtonMenuData;
	}

	public String getTreeJsonData() {
		return treeJsonData;
	}

	public void setTreeJsonData(String treeJsonData) {
		this.treeJsonData = treeJsonData;
	}

	public String getDialogData() {
		return dialogData;
	}

	public void setDialogData(String dialogData) {
		this.dialogData = dialogData;
	}
	
	public String getLeftAccordionContainerEnable() {
		return SystemSettingConfigureUtils.getLeftAccordionContainerEnableValue();
	}
	
	public String getLocaleCode() {
		String locale = UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext() );
		if (StringUtils.isBlank(locale)) {
			locale = LocaleLanguageUtils.getDefault();
		}
		return locale;
	}

	public String getShowConfigHost() {
		return showConfigHost;
	}

	public String getSysDefaultCoreHost() {
		return sysDefaultCoreHost;
	}
	
}
