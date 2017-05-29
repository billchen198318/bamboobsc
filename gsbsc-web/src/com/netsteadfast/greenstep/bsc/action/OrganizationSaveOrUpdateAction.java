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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.logic.IOrganizationLogicService;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.OrganizationSaveOrUpdateAction")
@Scope
public class OrganizationSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 8558872900212542322L;
	protected Logger logger=Logger.getLogger(OrganizationSaveOrUpdateAction.class);
	private IOrganizationLogicService organizationLogicService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService; 
	private String message = "";
	private String success = IS_NO;
	private OrganizationVO organization = new OrganizationVO(); // 取部門資料json action要用的
	
	public OrganizationSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOrganizationLogicService getOrganizationLogicService() {
		return organizationLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.OrganizationLogicService")		
	public void setOrganizationLogicService(
			IOrganizationLogicService organizationLogicService) {
		this.organizationLogicService = organizationLogicService;
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
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("orgId", IdFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0002Q_orgId") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0002Q_name") )
		.process().throwMessage();
		
		this.getCheckFieldHandler().single(
				"address", 
				( super.defaultString( super.getFields().get("address") ).length() > 500 ), 
				this.getText("MESSAGE.BSC_PROG001D0002Q_address") ).throwMessage();
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		OrganizationVO organization = new OrganizationVO();
		this.transformFields2ValueObject(organization, new String[]{"orgId", "name", "address", "lat", "lng", "description"});
		DefaultResult<OrganizationVO> result = this.organizationLogicService.create(organization);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		OrganizationVO organization = new OrganizationVO();
		this.transformFields2ValueObject(organization, new String[]{"oid", "orgId", "name", "address", "lat", "lng", "description"});
		DefaultResult<OrganizationVO> result = this.organizationLogicService.update(organization);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		OrganizationVO organization = new OrganizationVO();
		this.transformFields2ValueObject(organization, new String[]{"oid"});
		DefaultResult<Boolean> result = this.organizationLogicService.delete(organization);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void loadOrganizationData() throws ControllerException, AuthorityException, ServiceException, Exception {		
		this.transformFields2ValueObject(this.organization, new String[]{"oid"});
		DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(this.organization);
		if (result.getValue()!=null) {
			this.organization = result.getValue();
			this.success = IS_YES;
		} else {
			this.message = result.getSystemMessage().getValue();
		}
	}
	
	private void updateParent() throws ControllerException, AuthorityException, ServiceException, Exception {
		OrganizationVO organization = new OrganizationVO();
		this.transformFields2ValueObject(organization, new String[]{"oid"});
		DefaultResult<Boolean> result = this.organizationLogicService.updateParent(
				organization, this.getFields().get("parentOid"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}	

	/**
	 * bsc.organizationSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			if ( StringUtils.isBlank(this.getFields().get("oid")) ) {
				this.save();
			} else {
				this.update();
			}			
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.organizationDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.getOrganizationDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q")
	public String doGetOrganizationData() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.loadOrganizationData();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.organizationUpdateParentAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q")
	public String doUpdateParent() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.updateParent();
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

	public OrganizationVO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationVO organization) {
		this.organization = organization;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
		
}
