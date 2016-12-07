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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.service.logic.IOrganizationLogicService;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.OrganizationManagementAction")
@Scope
public class OrganizationManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 3207254352160726157L;
	private IOrganizationLogicService organizationLogicService;
	private String checkBoxIdDelimiter = BscConstants.ORGA_SELECT_CHECKBOX_ID_DELIMITER;
	
	// -------------------------------------------------------------------------------
	// 產生組織json tree 資料用的
	private String identifier="id";
	private String label="name";
	@SuppressWarnings("rawtypes")
	private List items;
	// -------------------------------------------------------------------------------		
	
	public OrganizationManagementAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOrganizationLogicService getOrganizationLogicService() {
		return organizationLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.OrganizationLogicService")	
	public void setOrganizationLogicService(
			IOrganizationLogicService organizationLogicService) {
		this.organizationLogicService = organizationLogicService;
	}
	
	private void initData() throws ServiceException, Exception {
		
	}

	private void initTreeData() throws ServiceException, Exception {
		this.items = this.organizationLogicService.getTreeData( ApplicationSiteUtils.getBasePath(
				Constants.getMainSystem(), this.getHttpServletRequest()), 
				( YesNo.YES.equals(this.getFields().get("checkBoxMode")) ? true : false ), 
				super.defaultString( this.getFields().get("appendId") ) );
	}
	
	private void handlerSelectOrganization() throws Exception {
		String tmp[] = this.getFields().get("oid").split(Constants.ID_DELIMITER);
		this.getFields().put("appendId", tmp[0].trim());		
		this.getFields().put("functionName", tmp[1].trim());
	}
	
	/**
	 * bsc.organizationManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q")
	public String execute() throws Exception {
		if ( YesNo.NO.equals(super.getGoogleMapEnable()) ) {
			//ActionContext.getContext().put("value", this.getProgramId() + ": no enable google map! can not use this feature.");			
			//return RESULT_BLANK_VALUE;			
			super.setErrorMessage(this.getProgramId() + ": no enable google map! can not use this feature.");
			return ERROR;
		}
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
	 * 產生組織架構tree json資料
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q_S00")
	@JSON(serialize=false)
	public String getOrganizationTreeJson() throws Exception {
		try {
			this.initTreeData();
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
	 * bsc.organizationSelectAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0002Q_S00")
	public String select() throws Exception {
		try {
			if ( StringUtils.isBlank(this.getFields().get("oid")) ) { // 這裡的 oid 是放選取部門的 oid 組成字串要放入的 hidden 欄位id , 與要觸發的button event
				return RESULT_SEARCH_NO_DATA;
			}
			String tmp[] = this.getFields().get("oid").split(Constants.ID_DELIMITER);
			if (tmp.length!=2) {
				return RESULT_SEARCH_NO_DATA;
			}
			this.handlerSelectOrganization();
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

	public String getIdentifier() {
		return identifier;
	}

	public String getLabel() {
		return label;
	}

	@SuppressWarnings("rawtypes")
	public List getItems() {
		return items;
	}

	public String getCheckBoxIdDelimiter() {
		return checkBoxIdDelimiter;
	}

}
