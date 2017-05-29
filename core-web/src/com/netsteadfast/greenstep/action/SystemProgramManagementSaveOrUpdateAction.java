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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NormalFieldCheckUtils;
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
import com.netsteadfast.greenstep.po.hbm.TbSysProgMultiName;
import com.netsteadfast.greenstep.service.ISysProgMultiNameService;
import com.netsteadfast.greenstep.service.logic.ISystemProgramLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysProgMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemProgramManagementSaveOrUpdateAction")
@Scope
public class SystemProgramManagementSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 6286854555166585626L;
	protected Logger logger=Logger.getLogger(SystemProgramManagementSaveOrUpdateAction.class);
	private ISystemProgramLogicService systemProgramLogicService; 
	private ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> sysProgMultiNameService;
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
	
	@JSON(serialize=false)
	public ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> getSysProgMultiNameService() {
		return sysProgMultiNameService;
	}

	@Autowired
	@Resource(name="core.service.SysProgMultiNameService")
	public void setSysProgMultiNameService(
			ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> sysProgMultiNameService) {
		this.sysProgMultiNameService = sysProgMultiNameService;
	}	
	
	private void checkFields(String workType) throws ControllerException {
		this.getCheckFieldHandler()
		.add("CORE_PROG001D0002"+workType+"_progSystem", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0002A_progSystem") )
		.add("CORE_PROG001D0002"+workType+"_progId", IdFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0002A_progId") )
		.add("CORE_PROG001D0002"+workType+"_name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0002A_name") )
		.add("CORE_PROG001D0002"+workType+"_url", SysProgUrlFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0002A_url") )
		.process().throwMessage();
		
		String url = this.getFields().get("CORE_PROG001D0002"+workType+"_url");
		String type = this.getFields().get("CORE_PROG001D0002"+workType+"_itemType");
		
		this.getCheckFieldHandler().single("CORE_PROG001D0002"+workType+"_url", (MenuItemType.ITEM.equals(type) && StringUtils.isBlank(url) ), "ITEM type item need input URL!").throwMessage();;
		
		this.getCheckFieldHandler().single("CORE_PROG001D0002"+workType+"_itemType", (MenuItemType.FOLDER.equals(type) && "true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_editMode")) ), "ITEM type folder not allowed edit mode!").throwMessage();
		
		// id cannot equals all
		this.getCheckFieldHandler().single("CORE_PROG001D0002"+workType+"_progId", (Constants.HTML_SELECT_NO_SELECT_ID.equals(this.getFields().get("CORE_PROG001D0002"+workType+"_progId")) ), "ID is incorrect, please change another!").throwMessage();
		
		// dialog-mode check
		this.getCheckFieldHandler().single("CORE_PROG001D0002"+workType+"_itemType", (MenuItemType.FOLDER.equals(type) && "true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_isDialog")) ), "ITEM type folder not allowed dialog mode!").throwMessage();
		if ("true".equals(this.getFields().get("CORE_PROG001D0002"+workType+"_isDialog"))) {
			int w = SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002"+workType+"_dialogW"), -1);
			int h = SimpleUtils.getInt(this.getFields().get("CORE_PROG001D0002"+workType+"_dialogH"), -1);
			this.getCheckFieldHandler().single("CORE_PROG001D0002"+workType+"_dialogW|"+"CORE_PROG001D0002"+workType+"_dialogH", (w<50 || h<25), "Dialog width must be greater than 50. height must be greater than 25 !").throwMessage();
		}
		
	}
	
	private void checkFieldsForMultiName() throws ControllerException {
		super.getCheckFieldHandler()
		.add("name", 		NotBlankFieldCheckUtils.class, "Name is required!")
		.add("localeCode", 	NormalFieldCheckUtils.class, "Locale is required!")
		.process().throwMessage();
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
	 * 產生多語名稱資料 tb_sys_prog_multi_name
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void multiNameSave() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForMultiName();
		SysProgMultiNameVO multiName = new SysProgMultiNameVO();
		this.transformFields2ValueObject(multiName, new String[]{"progId", "name", "localeCode"});
		if ("true".equals(super.getFields().get("enableFlag"))) {
			multiName.setEnableFlag(YesNo.YES);
		} else {
			multiName.setEnableFlag(YesNo.NO);
		}
		DefaultResult<SysProgMultiNameVO> result = this.systemProgramLogicService.createMultiName(multiName);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.success = IS_YES;
	}
	
	/**
	 * 刪除多語名稱資料 tb_sys_prog_multi_name
	 * 
	 * @throws ControllerException
	 * @throws AuthorityException
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void multiNameDelete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysProgMultiNameVO multiName = new SysProgMultiNameVO();
		this.transformFields2ValueObject(multiName, new String[]{"oid"});
		DefaultResult<Boolean> result = this.sysProgMultiNameService.deleteObject(multiName);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * core.systemProgramMultiNameSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E_S00")
	public String doMultiNameSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.multiNameSave();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;
	}	
	
	/**
	 * core.systemProgramMultiNameDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0002E_S00")
	public String doMultiNameDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.multiNameDelete();
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
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
