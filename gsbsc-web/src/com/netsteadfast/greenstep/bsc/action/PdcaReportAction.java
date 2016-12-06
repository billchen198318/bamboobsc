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
package com.netsteadfast.greenstep.bsc.action;

import java.util.Map;

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
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.PdcaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PdcaReportAction")
@Scope
public class PdcaReportAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -9113952422196266449L;
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;
	private Map<String, String> pdcaMap = this.providedSelectZeroDataMap( true );
	
	public PdcaReportAction() {
		super();
	}
	
	private void initData() throws ServiceException, Exception {
		this.pdcaMap = this.pdcaService.findForMap( true );
	}
	
	public IPdcaService<PdcaVO, BbPdca, String> getPdcaService() {
		return pdcaService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaService")
	@Required
	public void setPdcaService(IPdcaService<PdcaVO, BbPdca, String> pdcaService) {
		this.pdcaService = pdcaService;
	}

	/**
	 * bsc.pdcaReportAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG006D0002Q")
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

	public Map<String, String> getPdcaMap() {
		return pdcaMap;
	}

	public void setPdcaMap(Map<String, String> pdcaMap) {
		this.pdcaMap = pdcaMap;
	}

}
