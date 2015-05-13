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

import javax.annotation.Resource;

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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SystemFormUtils;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SettingsManagementAction")
@Scope
public class SettingsManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -7925145860675328170L;
	private ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	
	public SettingsManagementAction() {
		super();
	}
	
	public ISysCodeService<SysCodeVO, TbSysCode, String> getSysCodeService() {
		return sysCodeService;
	}

	@Autowired
	@Resource(name="core.service.SysCodeService")	
	@Required
	public void setSysCodeService(
			ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService) {
		this.sysCodeService = sysCodeService;
	}

	private void initData() throws ServiceException, Exception {		
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType("CNF");
		sysCode.setCode("CNF_CONF001");
		DefaultResult<SysCodeVO> result = sysCodeService.findByUK(sysCode);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysCode = result.getValue();
		this.getFields().put("mailFrom", this.defaultString(sysCode.getParam1()).trim() );
		
		sysCode = new SysCodeVO();
		sysCode.setType("CNF");
		sysCode.setCode("CNF_CONF002");
		result = sysCodeService.findByUK(sysCode);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysCode = result.getValue();
		this.getFields().put("mailEnable", this.defaultString(sysCode.getParam1()).trim() );
		this.getFields().put("sysTemplateReWrite", (SystemFormUtils.getEnableTemplateFileReWriteAlways() ? YesNo.YES : YesNo.NO) );
	}
	
	/**
	 * core.settingsManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0011Q")
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

	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId());
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

}
