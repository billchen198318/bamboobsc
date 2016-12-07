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
import com.netsteadfast.greenstep.po.hbm.QcOlapCatalog;
import com.netsteadfast.greenstep.po.hbm.QcOlapConf;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.service.IOlapCatalogService;
import com.netsteadfast.greenstep.qcharts.service.IOlapConfService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;
import com.netsteadfast.greenstep.vo.OlapConfVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsManagementAction")
@Scope
public class AnalyticsManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2669946516145826980L;
	private IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService;
	private IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	private Map<String, String> mdxMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> configMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> catalogMap = this.providedSelectZeroDataMap(true);
	
	public AnalyticsManagementAction() {
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

	public IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> getOlapCatalogService() {
		return olapCatalogService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapCatalogService")		
	@Required	
	public void setOlapCatalogService(
			IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService) {
		this.olapCatalogService = olapCatalogService;
	}

	public IOlapMdxService<OlapMdxVO, QcOlapMdx, String> getOlapMdxService() {
		return olapMdxService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapMdxService")		
	@Required		
	public void setOlapMdxService(
			IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService) {
		this.olapMdxService = olapMdxService;
	}

	private void initData() throws ServiceException, Exception {
		this.configMap = this.olapConfService.findForMap(true);
		this.catalogMap = this.olapCatalogService.findForMap(true);
		this.mdxMap = this.olapMdxService.findForMap(true);
	}	
	
	/**
	 * qcharts.analyticsManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002Q")
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getMdxMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.mdxMap);
		return mdxMap;
	}

	public Map<String, String> getConfigMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.configMap);
		return configMap;
	}

	public Map<String, String> getCatalogMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.catalogMap);
		return catalogMap;
	}

}
