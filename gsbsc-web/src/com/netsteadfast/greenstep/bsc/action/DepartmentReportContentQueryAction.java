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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DepartmentReportContentQueryAction")
@Scope
public class DepartmentReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -2525164716959616800L;
	protected Logger logger=Logger.getLogger(DepartmentReportContentQueryAction.class);
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private String uploadOid = "";
	
	public DepartmentReportContentQueryAction() {
		super();
	}

	@JSON(serialize=false)
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("visionOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0003Q_visionOid") )
		.add("frequency", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0003Q_frequency") )
		.add("organizationOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0003Q_organizationOid") )
		.add("dateType", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG003D0003Q_dateType") )
		.process().throwMessage();
		
		String frequency = this.getFields().get("frequency");
		String dateType = this.getFields().get("dateType");
		
		// year
		this.getCheckFieldHandler().single("frequency|dateType", ( "3".equals(dateType) && !"6".equals(frequency) ), this.getText("MESSAGE.BSC_PROG003D0003Q_msg1") ).throwMessage();
		
		// half-year
		this.getCheckFieldHandler().single("frequency|dateType", ( !"3".equals(dateType) && !"5".equals(frequency) ), this.getText("MESSAGE.BSC_PROG003D0003Q_msg1") ).throwMessage();
		
		if ( !SimpleUtils.isDate( this.getFields().get("year")+"/01/01" ) ) {
			super.throwMessage( this.getText("MESSAGE.BSC_PROG003D0003Q_msg2") );
		}
	}	
	
	@SuppressWarnings("unchecked")
	private Context getChainContext() throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", this.getFields().get("visionOid"));
		context.put("frequency", this.getFields().get("frequency"));
		context.put("startYearDate", this.getFields().get("year"));
		context.put("endYearDate", this.getFields().get("year"));
		context.put("dateType", this.getFields().get("dateType"));
		context.put("dataFor", BscConstants.MEASURE_DATA_FOR_ORGANIZATION);
		OrganizationVO organization = new OrganizationVO();
		organization.setOid( this.getFields().get("organizationOid") );
		DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(organization);
		if ( result.getValue() == null ) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		organization = result.getValue();
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL );
		context.put("orgId", organization.getOrgId() );
		context.put("uploadSignatureOid", this.getFields().get("uploadSignatureOid") );
		return context;
	}	
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("organizationReportHtmlContentChain", context);
		this.body = String.valueOf( resultObj.getValue() );
		this.message = resultObj.getMessage();
		if ( !StringUtils.isBlank(this.body) && this.body.startsWith("<!-- BSC_PROG003D0003Q -->") ) {
			this.success = IS_YES;
		}				
	}	
	
	private void getPdf() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("organizationReportPdfContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}		
	}	
	
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("organizationReportExcelContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}				
	}	
	
	/**
	 * bsc.departmentReportContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0003Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.departmentReportPdfQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0003Q")
	public String doPdf() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getPdf();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.departmentReportExcelQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0003Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
	public String getBody() {
		return body;
	}
	
	@JSON
	public String getUploadOid() {
		return uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
