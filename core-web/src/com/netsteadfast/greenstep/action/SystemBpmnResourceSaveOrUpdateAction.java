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

import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResourceRole;
import com.netsteadfast.greenstep.service.IRoleService;
import com.netsteadfast.greenstep.service.ISysBpmnResourceRoleService;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.RoleVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceRoleVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBpmnResourceSaveOrUpdateAction")
@Scope
public class SystemBpmnResourceSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 3200563358806590053L;
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	protected Logger logger=Logger.getLogger(SystemBpmnResourceSaveOrUpdateAction.class);
	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = ""; // 下載用
	
	public SystemBpmnResourceSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")		
	public void setSysBpmnResourceService(
			ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
	}
	
	@JSON(serialize=false)
	public ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> getSysBpmnResourceRoleService() {
		return sysBpmnResourceRoleService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceRoleService")
	public void setSysBpmnResourceRoleService(
			ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService) {
		this.sysBpmnResourceRoleService = sysBpmnResourceRoleService;
	}
	
	@JSON(serialize=false)
	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}

	@Autowired
	@Resource(name="core.service.RoleService")
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}	
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("id", NotBlankFieldCheckUtils.class, "Id is required!")
		.add("name", NotBlankFieldCheckUtils.class, "Name is required!")
		.process().throwMessage();
	}	
	
	private void checkFields2() throws ControllerException {
		this.getCheckFieldHandler()
		.add("resourceOid", SelectItemFieldCheckUtils.class, "Please select resource!")
		.add("roleOid", SelectItemFieldCheckUtils.class, "Please select role!")
		.add("taskName", NotBlankFieldCheckUtils.class, "Task-name is required!")
		.process().throwMessage();
	}	
	
	private void selfTestUploadResourceData() throws ControllerException, AuthorityException, ServiceException, Exception {
		String id = (String)this.getFields().get("id");		
		String resourceFileProcessId = BusinessProcessManagementUtils.getResourceProcessId4Upload(
				(String)this.getFields().get("uploadOid"));
		if (!id.equals(resourceFileProcessId)) {
			super.throwMessage("id", "Resource file process-Id not equals Id field!");
		}
	}
	
	private void fillContent(SysBpmnResourceVO resource) throws ServiceException, Exception {
		byte[] content = UploadSupportUtils.getDataBytes((String)this.getFields().get("uploadOid"));
		resource.setContent(content);		
	}
	
	private void fillResource(SysBpmnResourceVO resource) throws Exception {
		this.transformFields2ValueObject(resource, new String[]{"id", "name", "description"});
		if (StringUtils.defaultString(resource.getDescription()).length()>MAX_DESCRIPTION_LENGTH) {
			resource.setDescription( resource.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );			
		}		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			super.throwMessage( "uploadOid", "Please upload BPMN(zip) file!" );
		}
		this.selfTestUploadResourceData();
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		this.fillResource(resource);		
		this.fillContent(resource);
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.saveObject(resource);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String uploadOid = this.getFields().get("uploadOid");
		if (!StringUtils.isBlank(uploadOid) ) { // 有更新上傳檔案
			this.selfTestUploadResourceData();
		}
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		resource.setOid( this.getFields().get("oid") );
		DefaultResult<SysBpmnResourceVO> oldResult = this.sysBpmnResourceService.findObjectByOid(resource);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		resource = oldResult.getValue();
		String beforeId = resource.getId();
		byte[] beforeContent = resource.getContent();
		resource.setContent( null );
		sysBpmnResourceService.updateObject(resource); // 先清除原本的byte資料, 要不然每次update 資料越來越大
		if (!StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			this.fillContent(resource);
		} else {
			resource.setContent(beforeContent);
		}
		this.fillResource(resource);
		resource.setId(beforeId);
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.updateObject(resource);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			String oldDeploymentIdId = result.getValue().getDeploymentId();
			if (!StringUtils.isBlank(oldDeploymentIdId) && !StringUtils.isBlank(uploadOid)) { // 更新上傳部屬
				BusinessProcessManagementUtils.deleteDeployment(result.getValue(), false);				
				this.deployment(true);
			}			
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		resource.setOid( this.getFields().get("oid") );
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.findObjectByOid(resource);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		resource = result.getValue();
		sysBpmnResourceService.hibernateSessionClear();
		if (!StringUtils.isBlank(resource.getDeploymentId())) {
			BusinessProcessManagementUtils.deleteDeployment(resource, false);
			result = this.sysBpmnResourceService.findObjectByOid(resource);
			if (result.getValue()==null) {
				throw new ServiceException( result.getSystemMessage().getValue() );
			}
			resource = result.getValue();
			sysBpmnResourceService.hibernateSessionClear();
			if (!StringUtils.isBlank(resource.getDeploymentId())) {
				this.message = "Cannot delete deployment!";
				return;
			}			
		}				
		DefaultResult<Boolean> delResult = sysBpmnResourceService.deleteObject(resource);
		this.message = delResult.getSystemMessage().getValue();
		if (delResult.getValue()!=null && delResult.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	private void deployment(boolean overDeployment) throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		resource.setOid( this.getFields().get("oid") );
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.findObjectByOid(resource);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		resource = result.getValue();
		sysBpmnResourceService.hibernateSessionClear();
		if (!overDeployment && !StringUtils.isBlank(resource.getDeploymentId())) {
			this.message = "Already deployment!";
			return;
		}
		String id = BusinessProcessManagementUtils.deployment(resource);		
		if (!StringUtils.isBlank(id)) {
			this.message = "Deployment success!";
			this.success = IS_YES;
		} else {
			this.message = "Deployment fail!";
		}
	}
	
	private void export() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		resource.setOid( this.getFields().get("oid") );
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.findObjectByOid(resource);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		resource = result.getValue();
		sysBpmnResourceService.hibernateSessionClear();
		this.uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				resource.getContent(), 
				resource.getId()+".zip");
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
		this.success = IS_YES;
	}
	
	private void exportDiagram() throws ControllerException, AuthorityException, ServiceException, Exception {
		String type = this.getFields().get("type");
		String objectId = this.getFields().get("objectId");
		String resourceId = this.getFields().get("resourceId");
		if ("processDefinition".equals(type)) {
			this.uploadOid = BusinessProcessManagementUtils
					.getProcessDefinitionDiagramById2Upload(objectId);
		} else if ("processInstance".equals(type)) {
			this.uploadOid = BusinessProcessManagementUtils
					.getProcessInstanceDiagramById2Upload(objectId);
		} else { // task
			this.uploadOid = BusinessProcessManagementUtils
					.getTaskDiagramById2Upload(resourceId, objectId);
		}
		if (!StringUtils.isBlank(this.uploadOid)) {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS);
			this.success = IS_YES;
		}
	}
	
	private void deleteRoleAssignee() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBpmnResourceRoleVO bpmnResourceRole = new SysBpmnResourceRoleVO();
		this.transformFields2ValueObject(bpmnResourceRole, "oid");
		DefaultResult<Boolean> result = this.sysBpmnResourceRoleService.deleteObject(bpmnResourceRole);
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
		this.message = result.getSystemMessage().getValue();
	}
	
	private void saveRoleAssignee() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields2();
		SysBpmnResourceRoleVO bpmnResourceRole = new SysBpmnResourceRoleVO();
		String resourceOid = this.getFields().get("resourceOid");
		String roleOid = this.getFields().get("roleOid");
		TbSysBpmnResource resourceObj = this.sysBpmnResourceService.findByPKng(resourceOid);
		if (null == resourceObj) {
			super.throwMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) );
		}
		TbRole roleObj = this.roleService.findByPKng(roleOid);
		if (null == roleObj) {
			super.throwMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) );
		}
		this.transformFields2ValueObject(bpmnResourceRole, "taskName");
		bpmnResourceRole.setId(resourceObj.getId());
		bpmnResourceRole.setRole(roleObj.getRole());
		DefaultResult<SysBpmnResourceRoleVO> result = this.sysBpmnResourceRoleService.saveObject(bpmnResourceRole);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.message = result.getSystemMessage().getValue();
		this.success = IS_YES;
	}
	
	/**
	 * core.systemBpmnResourceSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004A")
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
	 * core.systemBpmnResourceUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004E")
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
	 * core.systemBpmnResourceDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q")
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
	 * core.systemBpmnResourceDeploymentAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q")
	public String doDeployment() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.deployment(false);
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;				
	}
	
	/**
	 * core.systemBpmnResourceExportAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q")
	public String doExport() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.export();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;				
	}	
	
	/**
	 * core.systemBpmnResourceExportDiagramAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROG003D0004Q_S00")	
	public String doExportDiagram() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.exportDiagram();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.systemBpmnResourceRoleAssigneeDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0005Q")
	public String doRoleAssigneeDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.deleteRoleAssignee();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.systemBpmnResourceRoleAssigneeSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0005A")
	public String doRoleAssigneeSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveRoleAssignee();
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
	public String getUploadOid() {
		return uploadOid;
	}

	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
