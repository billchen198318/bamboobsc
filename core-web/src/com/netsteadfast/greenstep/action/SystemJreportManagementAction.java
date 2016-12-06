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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.po.hbm.TbSysJreportParam;
import com.netsteadfast.greenstep.service.ISysJreportParamService;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemJreportManagementAction")
@Scope
public class SystemJreportManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2810740119507303251L;
	private ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService;
	private ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService;
	private SysJreportVO sysJreport = new SysJreportVO(); // edit模式要用
	private InputStream inputStream = null;
	private String filename = "";
	private String contentType = "";
	private List<TbSysJreportParam> jreportParams = new ArrayList<TbSysJreportParam>();
	
	public SystemJreportManagementAction() {
		super();
	}
	
	public ISysJreportService<SysJreportVO, TbSysJreport, String> getSysJreportService() {
		return sysJreportService;
	}

	@Autowired
	@Resource(name="core.service.SysJreportService")
	@Required		
	public void setSysJreportService(
			ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService) {
		this.sysJreportService = sysJreportService;
	}

	public ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> getSysJreportParamService() {
		return sysJreportParamService;
	}

	@Autowired
	@Resource(name="core.service.SysJreportParamService")
	@Required			
	public void setSysJreportParamService(
			ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService) {
		this.sysJreportParamService = sysJreportParamService;
	}

	private void initData() throws ServiceException, Exception {
		
	}
	
	private void loadSysJreportData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysJreport, new String[]{"oid"});
		DefaultResult<SysJreportVO> result = this.sysJreportService.findObjectByOid(sysJreport);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysJreport = result.getValue();
	}
	
	private void exportReportData() throws ServiceException, Exception {
		this.inputStream = new ByteArrayInputStream( this.sysJreport.getContent() ); 
		this.filename = this.sysJreport.getReportId() + ".zip";
		this.contentType = "application/octet-stream";
	}	
	
	/**
	 * core.systemJreportManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q")
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
	 * core.systemJreportCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008A")	
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
	 * core.systemJreportEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysJreportData();
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
	 * core.systemJreportEditParamAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008E_S00")		
	public String editParam() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysJreportData();
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
	 * core.systemJreportExportAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q")
	public String export() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysJreportData();
			this.exportReportData();
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
	 * core.systemJreportPreviewParamAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q_S00")
	public String previewParam() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysJreportData();		
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("reportId", this.sysJreport.getReportId());
			this.jreportParams = this.sysJreportParamService.findListByParams(paramMap);
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

	public SysJreportVO getSysJreport() {
		return sysJreport;
	}

	public void setSysJreport(SysJreportVO sysJreport) {
		this.sysJreport = sysJreport;
	}
	
	public InputStream getInputStream() {
		if ( this.inputStream == null ) {
			this.inputStream = new ByteArrayInputStream( "".getBytes() );
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

	public List<TbSysJreportParam> getJreportParams() {
		return jreportParams;
	}
	
}
