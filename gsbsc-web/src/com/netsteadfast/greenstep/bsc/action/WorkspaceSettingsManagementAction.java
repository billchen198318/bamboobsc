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

import java.util.ArrayList;
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
import com.netsteadfast.greenstep.bsc.service.IWorkspaceCompomentService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceTemplateService;
import com.netsteadfast.greenstep.bsc.util.WorkspaceUtils;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceCompoment;
import com.netsteadfast.greenstep.po.hbm.BbWorkspaceTemplate;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.WorkspaceCompomentVO;
import com.netsteadfast.greenstep.vo.WorkspaceTemplateVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.WorkspaceSettingsManagementAction")
@Scope
public class WorkspaceSettingsManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 7034975769671003150L;
	private IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> workspaceTemplateService;
	private IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService;
	private Map<String, String> workspaceTemplateMap = this.providedSelectZeroDataMap(true);
	private List<BbWorkspaceCompoment> workspaceCompoments = new ArrayList<BbWorkspaceCompoment>();
	private String confContent = ""; // 新增頁面(Content) 拖拉目標的body 內容
	
	public WorkspaceSettingsManagementAction() {
		super();
	}
	
	public IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> getWorkspaceTemplateService() {
		return workspaceTemplateService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceTemplateService")
	@Required	
	public void setWorkspaceTemplateService(
			IWorkspaceTemplateService<WorkspaceTemplateVO, BbWorkspaceTemplate, String> workspaceTemplateService) {
		this.workspaceTemplateService = workspaceTemplateService;
	}

	public IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> getWorkspaceCompomentService() {
		return workspaceCompomentService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceCompomentService")
	@Required		
	public void setWorkspaceCompomentService(
			IWorkspaceCompomentService<WorkspaceCompomentVO, BbWorkspaceCompoment, String> workspaceCompomentService) {
		this.workspaceCompomentService = workspaceCompomentService;
	}

	private void initData(String type) throws ServiceException, Exception {
		if ("createBase".equals(type) || "editBase".equals(type)) {
			
		}
		if ("createContent".equals(type) || "editContent".equals(type)) {
			this.workspaceTemplateMap = this.workspaceTemplateService.findForMap(true);
			this.workspaceCompoments = this.workspaceCompomentService.findListByParams(null);
		}
	}
	
	private void handlerConfContentBody() throws ServiceException, Exception {
		String workspaceTemplateOid = this.getFields().get("workspaceTemplateOid");
		if (this.isNoSelectId(workspaceTemplateOid)) {
			return;
		}
		confContent = WorkspaceUtils.getTemplateConfResource(workspaceTemplateOid);
		if (confContent==null) {
			confContent = "";
		}
	}
	
	private void handlerViewContentBody() throws ServiceException, Exception {
		confContent = WorkspaceUtils.getLayoutForView( this.getFields().get("oid") );
		if (confContent==null) {
			confContent = "";
		}
	}	
	
	/**
	 * bsc.workspaceSettingsManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0002Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
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
	 * bsc.workspaceSettingsCreateAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG004D0002A")	
	public String create() throws Exception {
		try {
			this.initData("create");
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
	 * bsc.workspaceSettingsCreateBaseAction.action
	 */		
	@ControllerMethodAuthority(programId="BSC_PROG004D0002A_C00")
	public String createBase() throws Exception {
		try {
			this.initData("createBase");			
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
	 * bsc.workspaceSettingsCreateContentAction.action
	 */		
	@ControllerMethodAuthority(programId="BSC_PROG004D0002A_C01")	
	public String createContent() throws Exception {
		try {
			this.initData("createContent");
			this.handlerConfContentBody();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.setPageMessage(e.toString());
			} else {
				this.setPageMessage(e.getMessage().toString());
			}						
		}
		return SUCCESS;			
	}
	
	/**
	 * bsc.workspaceSettingsViewAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0002Q_S00")	
	public String view() throws Exception {
		try {
			this.initData("view");
			this.handlerViewContentBody();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.setPageMessage(e.toString());
			} else {
				this.setPageMessage(e.getMessage().toString());
			}						
		}
		return SUCCESS;				
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

	public Map<String, String> getWorkspaceTemplateMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.workspaceTemplateMap);
		return workspaceTemplateMap;
	}

	public List<BbWorkspaceCompoment> getWorkspaceCompoments() {
		return workspaceCompoments;
	}

	public String getConfContent() {
		return confContent;
	}
	
}
