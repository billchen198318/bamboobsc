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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SysProgUrlFieldCheckUtils;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.MenuItemType;
import com.netsteadfast.greenstep.service.logic.ISystemProgramLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysProgVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemProgramManagementSaveOrUpdateAction")
@Scope
public class SystemProgramManagementSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 6286854555166585626L;
	protected Logger logger=Logger.getLogger(SystemProgramManagementSaveOrUpdateAction.class);
	private ISystemProgramLogicService systemProgramLogicService; 
	private String message = "";
	private String success = IS_NO;	
	
	public SystemProgramManagementSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISystemProgramLogicService getSystemProgramLogicService() {
		return systemProgramLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemProgramLogicService")	
	public void setSystemProgramLogicService(
			ISystemProgramLogicService systemProgramLogicService) {
		this.systemProgramLogicService = systemProgramLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields(String workType) throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"CORE_PROG001D0002"+workType+"_progSystem",
							"CORE_PROG001D0002"+workType+"_progId", 
							"CORE_PROG001D0002"+workType+"_name", 
							"CORE_PROG001D0002"+workType+"_url"
					}, 
					new String[]{
							this.getText("MESSAGE.CORE_PROG001D0002A_progSystem") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0002A_progId") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0002A_name") + "<BR/>",
							this.getText("MESSAGE.CORE_PROG001D0002A_url") + "<BR/>",
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							SysProgUrlFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
		String url = this.getFields().get("CORE_PROG001D0002"+workType+"_url");
		String type = this.getFields().get("CORE_PROG001D0002"+workType+"_itemType");
		if (MenuItemType.ITEM.equals(type) && StringUtils.isBlank(url) ) {
			this.getFieldsId().add("CORE_PROG001D0002"+workType+"_url");
			throw new ControllerException("ITEM type item need input URL!");			
		}
		if (MenuItemType.FOLDER.equals(type) && "true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_editMode")) ) {
			this.getFieldsId().add("CORE_PROG001D0002"+workType+"_itemType");
			throw new ControllerException("ITEM type folder not allowed edit mode!");	
		}
		if (Constants.HTML_SELECT_NO_SELECT_ID.equals(this.getFields().get("CORE_PROG001D0002"+workType+"_progId")) ) { // id cannot equals all
			this.getFieldsId().add("CORE_PROG001D0002"+workType+"_progId");
			throw new ControllerException("ID is incorrect, please change another!");	
		}		
		
		// dialog-mode check
		if (MenuItemType.FOLDER.equals(type) && "true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_isDialog")) ) {
			this.getFieldsId().add("CORE_PROG001D0002"+workType+"_itemType");
			throw new ControllerException("ITEM type folder not allowed dialog mode!");			
		}
		if ("true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_isDialog"))) {
			int w = SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002"+workType+"_dialogW"), -1);
			int h = SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002"+workType+"_dialogH"), -1);
			if (w<50 || h<25) {
				this.getFieldsId().add("CORE_PROG001D0002"+workType+"_dialogW");
				this.getFieldsId().add("CORE_PROG001D0002"+workType+"_dialogH");
				throw new ControllerException("Dialog width must be greater than 50. height must be greater than 25 !");					
			}
		}
		
	}

	/**
	 * 產生 TB_SYS_PROG 資料
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("A");
		SysProgVO sysProg = new SysProgVO();
		this.transformFields2ValueObject(
				sysProg, 
				new String[]{"progId", "name", "url", "itemType", "isWindow"}, 
				new String[]{"CORE_PROG001D0002A_progId", "CORE_PROG001D0002A_name", "CORE_PROG001D0002A_url", "CORE_PROG001D0002A_itemType", "CORE_PROG001D0002A_isWindow"}
		);
		sysProg.setEditMode(YesNo.NO);
		sysProg.setIsDialog(YesNo.NO);
		if ("true".equals(this.getFields().get("CORE_PROG001D0002A_editMode"))) {
			sysProg.setEditMode(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("CORE_PROG001D0002A_isDialog"))) {
			sysProg.setIsDialog(YesNo.YES);
			sysProg.setDialogW( SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002A_dialogW"), 50) );
			sysProg.setDialogH( SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002A_dialogH"), 25) );
		}
		DefaultResult<SysProgVO> result = this.systemProgramLogicService.create(
				sysProg, 
				this.getFields().get("CORE_PROG001D0002A_progSystem"), 
				this.getFields().get("CORE_PROG001D0002A_icon"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * 更新 TB_SYS_PROG 資料
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("E");
		SysProgVO sysProg = new SysProgVO();
		this.transformFields2ValueObject(
				sysProg, 
				new String[]{"oid", "progId", "name", "url", "itemType", "isWindow"}, 
				new String[]{"oid", "CORE_PROG001D0002E_progId", "CORE_PROG001D0002E_name", "CORE_PROG001D0002E_url", "CORE_PROG001D0002E_itemType", "CORE_PROG001D0002E_isWindow"}
		);
		sysProg.setEditMode(YesNo.NO);
		sysProg.setIsDialog(YesNo.NO);
		if ("true".equals(this.getFields().get("CORE_PROG001D0002E_editMode"))) {
			sysProg.setEditMode(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("CORE_PROG001D0002E_isDialog"))) {
			sysProg.setIsDialog(YesNo.YES);
			sysProg.setDialogW( SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002E_dialogW"), 50) );
			sysProg.setDialogH( SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002E_dialogH"), 25) );
		}		
		DefaultResult<SysProgVO> result = this.systemProgramLogicService.update(
				sysProg, 
				this.getFields().get("CORE_PROG001D0002E_progSystem"), 
				this.getFields().get("CORE_PROG001D0002E_icon"));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * 刪除 TB_SYS_PROG 資料
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysProgVO sysProg = new SysProgVO();
		DefaultResult<Boolean> result = this.systemProgramLogicService.delete(
				this.transformFields2ValueObject(sysProg, new String[]{"oid"}));
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null || !result.getValue()) {
			return;
		}
		this.success = IS_YES;
	}	
	
	/**
	 * core.systemProgramSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002A")
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
	 * core.systemProgramUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E")
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
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * core.systemProgramDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002A")
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
