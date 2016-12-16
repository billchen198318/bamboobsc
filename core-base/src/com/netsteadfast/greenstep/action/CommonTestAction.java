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

import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;

/**
 * 活了33年的人生, 至今一無是處, 接下來的路該怎麼走呢? 2016-12-16 , Bill chen ( Chen Xin Nien )
 *
 */
@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonTestAction")
@Scope
public class CommonTestAction extends BaseJsonAction {
	private static final long serialVersionUID = -9088646859588169077L;
	private String message = "";
	private String success = IS_NO;
	
	public CommonTestAction() {
		super();
	}
	
	/**
	 *	/pages/system/testJsonResult.action
	 */
	@ControllerMethodAuthority(programId="CORE_INDEX")
	@JSON(serialize=false)
	public String execute() throws Exception {
		try {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.DATA_IS_EXIST); // this message is no any meant, only for test check work status on Index pages IndexAction#execute() ApplicationSiteUtils.checkLoginUrlWithAllSysHostConfig.
			if (!GreenStepSysMsgConstants.DATA_IS_EXIST.equals(this.message)) {
				this.success = IS_YES;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON(serialize=false)
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

	@JSON(serialize=false)
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}
	
	@JSON(serialize=false)
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}

}
