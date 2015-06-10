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

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.service.logic.IRoleLogicService;
import com.netsteadfast.greenstep.vo.RolePermissionVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.RolePermittedSaveOrUpdateAction")
@Scope
public class RolePermittedSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 736104379589270859L;
	protected Logger logger=Logger.getLogger(RolePermittedSaveOrUpdateAction.class);
	private IRoleLogicService roleLogicService;	
	private String message = "";
	private String success = IS_NO;
	
	public RolePermittedSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IRoleLogicService getRoleLogicService() {
		return roleLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.RoleLogicService")		
	public void setRoleLogicService(IRoleLogicService roleLogicService) {
		this.roleLogicService = roleLogicService;
	}	
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"permission",
							"permType"
					}, 
					new String[]{
							this.getText("MESSAGE.CORE_PROG002D0001E_S00_permission") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG002D0001E_S00_permType") + "<BR/>"
					},					
					new Class[]{
							NotBlankFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
	}
	
	/**
	 * 建立 TB_ROLE_PERMISSION 資料
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String roleOid = this.getFields().get("roleOid");
		RolePermissionVO permission = new RolePermissionVO();
		this.transformFields2ValueObject(permission, new String[]{"permType", "permission", "description"});
		DefaultResult<RolePermissionVO> result = this.roleLogicService.createPermission(permission, roleOid);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * 刪除 TB_ROLE_PERMISSION 資料
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		RolePermissionVO permission = new RolePermissionVO();
		this.transformFields2ValueObject(permission, new String[]{"oid"});
		DefaultResult<Boolean> result = this.roleLogicService.deletePermission(permission);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null || !result.getValue()) {
			return;
		}
		this.success = IS_YES;			
	}	
	
	/**
	 * core.rolePermittedSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001E_S00")
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
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.rolePermittedDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG002D0001E_S00")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
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

}
