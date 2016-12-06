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
package com.netsteadfast.greenstep.qcharts.action;

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
import com.netsteadfast.greenstep.po.hbm.QcOlapConf;
import com.netsteadfast.greenstep.qcharts.service.IOlapConfService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.OlapConfVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsConfigManagementAction")
@Scope
public class AnalyticsConfigManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2495755726145288617L;
	private IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService;
	private OlapConfVO olapConf = new OlapConfVO();
	
	public AnalyticsConfigManagementAction() {
		super();
	}
	
	public IOlapConfService<OlapConfVO, QcOlapConf, String> getOlapConfService() {
		return olapConfService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapConfService")	
	@Required
	public void setOlapConfService(
			IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService) {
		this.olapConfService = olapConfService;
	}

	private void initData() throws ServiceException, Exception {
		
	}
	
	private void loadOlapConfData() throws ServiceException, Exception {
		this.transformFields2ValueObject(olapConf, "oid");
		DefaultResult<OlapConfVO> result = olapConfService.findObjectByOid(olapConf);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.olapConf = result.getValue();
	}
	
	/**
	 * qcharts.analyticsConfigManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003Q")
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
	 * qcharts.analyticsConfigCreateAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003A")
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
	 * qcharts.analyticsConfigEditAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0003E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadOlapConfData();
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

	public OlapConfVO getOlapConf() {
		return olapConf;
	}

	public void setOlapConf(OlapConfVO olapConf) {
		this.olapConf = olapConf;
	}

}
