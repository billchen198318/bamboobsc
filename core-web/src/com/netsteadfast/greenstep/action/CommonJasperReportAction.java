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

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.po.hbm.TbSysJreportParam;
import com.netsteadfast.greenstep.service.ISysJreportParamService;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.util.DataUtils;
import com.netsteadfast.greenstep.util.JReportUtils;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonJasperReportAction")
@Scope
public class CommonJasperReportAction extends BaseSupportAction {
	private static final long serialVersionUID = 1055057734884462175L;
	protected Logger logger=Logger.getLogger(CommonJasperReportAction.class);
	private ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService;
	private ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService;
	private Connection connection = null;
	private String location = "";
	private Map<String, Object> reportParams = new HashMap<String, Object>();
	private String format="";
	private List<String> dataSource=new ArrayList<String>();
	private String jreportId="";
	
	public CommonJasperReportAction() {
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
	
	private void handlerParameter() throws ControllerException, ServiceException, Exception {
		if (StringUtils.isBlank(this.jreportId)) {
			throw new ControllerException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		DefaultResult<SysJreportVO> mResult = this.sysJreportService.findForSimpleByReportId(this.jreportId);
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		SysJreportVO sysJreport = mResult.getValue();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", sysJreport.getReportId());
		//List<TbSysJreportParam> paramList = this.sysJreportParamService.findListByParams(params);
		this.dataSource.add("1");
		this.connection = DataUtils.getConnection();
		String reportFolder = Constants.getDeployJasperReportDir() + File.separator + sysJreport.getReportId() + File.separator;
		/*
		this.reportParams.put("REPORT_FOLDER", reportFolder);
		this.reportParams.put("SUBREPORT_DIR", reportFolder);
		for (int i=0; paramList!=null && i<paramList.size(); i++) {
			TbSysJreportParam sysJreportParam = paramList.get(i);
			Enumeration<String> urlParams = super.getHttpServletRequest().getParameterNames();
			while (urlParams.hasMoreElements()) {
				String p = urlParams.nextElement();
				if (p.equals(sysJreportParam.getUrlParam())) {
					String value = super.getHttpServletRequest().getParameter(p);
					this.reportParams.put(sysJreportParam.getRptParam(), value);
				}
			}
		}
		*/
		this.reportParams = JReportUtils.getParameter(sysJreport.getReportId(), super.getHttpServletRequest());
		this.location = reportFolder + sysJreport.getFile();
	}
	
	/**
	 * core.commonJasperReportAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0002Q")	
	public String execute() throws Exception {
		String forward = RESULT_BLANK;
		try {
			this.handlerParameter();
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

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, Object> getReportParams() {
		return reportParams;
	}

	public void setReportParams(Map<String, Object> reportParams) {
		this.reportParams = reportParams;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<String> dataSource) {
		this.dataSource = dataSource;
	}

	public String getJreportId() {
		return jreportId;
	}

	public void setJreportId(String jreportId) {
		this.jreportId = jreportId;
	}
	
}
