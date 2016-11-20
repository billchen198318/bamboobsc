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
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.service.logic.ISystemJreportLogicService;
import com.netsteadfast.greenstep.util.JReportUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

import net.lingala.zip4j.exception.ZipException;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemJreportSaveOrUpdateAction")
@Scope
public class SystemJreportSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6250514802058878481L;
	protected Logger logger=Logger.getLogger(SystemJreportSaveOrUpdateAction.class);
	private ISystemJreportLogicService systemJreportLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemJreportSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemJreportLogicService getSystemJreportLogicService() {
		return systemJreportLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemJreportLogicService")		
	public void setSystemJreportLogicService(
			ISystemJreportLogicService systemJreportLogicService) {
		this.systemJreportLogicService = systemJreportLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("reportId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0008A_reportId") )
		.process().throwMessage();
	}	
	
	private void checkParamFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("rptParam", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0008E_S00_rptParam") )
		.add("urlParam", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0008E_S00_urlParam") )
		.process().throwMessage();
	}
	
	private void selfTestUploadReportData() throws ControllerException, AuthorityException, ServiceException, Exception {
		String uploadOid = super.getFields().get("uploadOid");
		String reportId = super.getFields().get("reportId");
		String testDir = JReportUtils.selfTestDecompress4Upload(uploadOid);
		String jrxmlFileFullPath = testDir + "/" + reportId + "/" + reportId + ".jrxml"; 
		File jrxmlFile = new File(jrxmlFileFullPath);
		if (!jrxmlFile.exists()) {
			jrxmlFile = null;
			super.throwMessage( "uploadOid", reportId + ".jrxml file not exists!" );
		}		
		if (!"true".equals(this.getFields().get("isCompile"))) {
			String jasperFileFullPath = testDir + "/" + reportId + "/" + reportId + ".jasper";
			File jasperFile = new File(jasperFileFullPath);
			if (!jasperFile.exists()) {
				jasperFile = null;
				super.throwMessage( "uploadOid", reportId + ".jasper file not exists!" );
			}					
		}
		jrxmlFile = null;
	}
	
	private void fillContent(SysJreportVO report) throws IOException, Exception {
		String uploadOid = super.getFields().get("uploadOid");
		File file = UploadSupportUtils.getRealFile(uploadOid);		
		report.setContent( FileUtils.readFileToByteArray(file) );
		file = null;
	}
	
	private void deployReport(SysJreportVO report) {
		try {
			JReportUtils.deployReport(report);
		} catch (Exception e) {
			logger.error( e.getMessage().toString() );
			e.printStackTrace();			
		}
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			super.throwMessage( "Please upload Report(zip) file!" );
		}
		this.selfTestUploadReportData();
		SysJreportVO report = new SysJreportVO();		
		this.transformFields2ValueObject(report, new String[]{"reportId", "description"});
		report.setIsCompile(YesNo.NO);
		if ("true".equals(this.getFields().get("isCompile"))) {
			report.setIsCompile(YesNo.YES);
		}
		this.fillContent(report);
		DefaultResult<SysJreportVO> result = this.systemJreportLogicService.create(report);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
			this.deployReport( result.getValue() );			
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if (!StringUtils.isBlank(this.getFields().get("uploadOid"))) {
			this.selfTestUploadReportData();
		}
		SysJreportVO report = new SysJreportVO();		
		this.transformFields2ValueObject(report, new String[]{"oid", "reportId", "description"});
		report.setIsCompile(YesNo.NO);
		if ("true".equals(this.getFields().get("isCompile"))) {
			report.setIsCompile(YesNo.YES);
		}
		if (!StringUtils.isBlank(this.getFields().get("uploadOid"))) {
			this.fillContent(report);
		}		
		DefaultResult<SysJreportVO> result = this.systemJreportLogicService.update(report);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
			this.deployReport( result.getValue() );			
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysJreportVO report = new SysJreportVO();
		this.transformFields2ValueObject(report, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemJreportLogicService.delete(report);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	private void saveParam() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkParamFields();
		SysJreportParamVO reportParam = new SysJreportParamVO();
		this.transformFields2ValueObject(reportParam, new String[]{"rptParam", "urlParam"});
		DefaultResult<SysJreportParamVO> result = this.systemJreportLogicService.createParam(
				reportParam, this.getFields().get("sysJreportOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void deleteParam() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysJreportParamVO reportParam = new SysJreportParamVO();
		this.transformFields2ValueObject(reportParam, new String[]{"oid"});
		DefaultResult<Boolean> result = this.systemJreportLogicService.deleteParam(reportParam);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * core.systemJreportSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.save();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (ZipException e) {
			this.message = e.getMessage().toString();
			super.addFieldsNoticeMessage("uploadOid", e.getMessage().toString());
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemJreportUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008E")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (ZipException e) {
			this.message = e.getMessage().toString();
			super.addFieldsNoticeMessage("uploadOid", e.getMessage().toString());
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemJreportDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008Q")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();;
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemJreportParameterSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008E_S00")
	public String doParamSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveParam();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.systemJreportParameterDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0008E_S00")
	public String doParamDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.deleteParam();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		

	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
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

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
