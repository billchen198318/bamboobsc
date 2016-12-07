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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
import com.netsteadfast.greenstep.po.hbm.QcOlapCatalog;
import com.netsteadfast.greenstep.qcharts.service.IOlapCatalogService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsCatalogManagementAction")
@Scope
public class AnalyticsCatalogManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 1812898859421649904L;
	private IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService;
	private OlapCatalogVO olapCatalog = new OlapCatalogVO();
	private InputStream inputStream = null;
	private String filename = "";
	private String contentType = "";
	
	public AnalyticsCatalogManagementAction() {
		super();
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

	private void initData() throws ServiceException, Exception {
		
	}	
	
	private void loadOlapCatalogData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.olapCatalog, "oid");
		DefaultResult<OlapCatalogVO> result = this.olapCatalogService.findObjectByOid(this.olapCatalog);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.olapCatalog = result.getValue();
	}
	
	private void exportOlapCatalogData() throws ServiceException, Exception {
		this.inputStream = new ByteArrayInputStream( this.olapCatalog.getContent() );		
		this.filename = this.olapCatalog.getId() + ".xml";
		this.contentType = "application/octet-stream";
	}

	/**
	 * qcharts.analyticsCatalogManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004Q")
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
	 * qcharts.analyticsCatalogCreateAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004A")
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
	 * qcharts.analyticsCatalogEditAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadOlapCatalogData();
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
	 * qcharts.analyticsCatalogExportAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0004Q")
	public String export() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadOlapCatalogData();
			this.exportOlapCatalogData();
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

	public OlapCatalogVO getOlapCatalog() {
		return olapCatalog;
	}

	public void setOlapCatalog(OlapCatalogVO olapCatalog) {
		this.olapCatalog = olapCatalog;
	}

	public InputStream getInputStream() {
		if ( this.inputStream == null ) {
			this.inputStream = new ByteArrayInputStream( "<Schema></Schema>".getBytes() );
		}
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
