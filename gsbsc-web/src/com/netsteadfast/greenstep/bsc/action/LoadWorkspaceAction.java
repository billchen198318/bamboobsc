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

import java.util.LinkedList;
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
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.IWorkspaceService;
import com.netsteadfast.greenstep.bsc.util.WorkspaceUtils;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.po.hbm.BbWorkspace;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.VisionVO;
import com.netsteadfast.greenstep.vo.WorkspaceVO;

@ControllerAuthority(check=false)
@Controller("bsc.web.controller.LoadWorkspaceAction")
@Scope
public class LoadWorkspaceAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -5135696585142820327L;
	private IWorkspaceService<WorkspaceVO, BbWorkspace, String> workspaceService;
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private Map<String, String> workspaceMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private int discreteValues = 3;
	private int maximum = 0;
	private int minimum = 0;
	private List<String> yearRangeList = new LinkedList<String>();	
	private String content = "";
	
	public LoadWorkspaceAction() {
		super();
	}
	
	public IWorkspaceService<WorkspaceVO, BbWorkspace, String> getWorkspaceService() {
		return workspaceService;
	}

	@Autowired
	@Resource(name="bsc.service.WorkspaceService")
	@Required		
	public void setWorkspaceService(
			IWorkspaceService<WorkspaceVO, BbWorkspace, String> workspaceService) {
		this.workspaceService = workspaceService;
	}

	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required		
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	private void fillDefaultSelect() throws Exception {
		String workspaceOid = "";
		String visionOid = "";
		for (Map.Entry<String, String> entry : this.workspaceMap.entrySet()) {
			if (!this.isNoSelectId(entry.getKey())) {
				workspaceOid = entry.getKey();
			}
		}
		for (Map.Entry<String, String> entry : this.visionMap.entrySet()) {			
			if (!this.isNoSelectId(entry.getKey())) {
				visionOid = entry.getKey();
			}
		}
		this.getFields().put("workspaceOid", workspaceOid);
		this.getFields().put("visionOid", visionOid);
	}
	
	private void initData() throws ServiceException, Exception {
		this.maximum=Integer.parseInt(SimpleUtils.getStrYMD(SimpleUtils.IS_YEAR));
		this.minimum=this.maximum-(this.discreteValues-1);		
		for (int year=this.minimum; year<=this.maximum; year++) {
			this.yearRangeList.add(String.valueOf(year));
		}		
		this.workspaceMap = this.workspaceService.findForMap(true);
		this.visionMap = this.visionService.findForMap(true);
		this.fillDefaultSelect();
	}
	
	private void renderContent() throws ServiceException, Exception {
		String workspaceOid = this.getFields().get("workspaceOid");
		String visionOid = this.getFields().get("visionOid");
		String year = this.getFields().get("year");
		String uploadOid = this.getFields().get("uploadOid");
		this.content = WorkspaceUtils.getView(super.getBasePath(), workspaceOid, visionOid, year, uploadOid);
	}
	
	/**
	 *  bsc.loadWorkspaceAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0005Q")
	public String execute() throws Exception {
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
	 *  bsc.loadContentBody.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0005Q")	
	public String loadContentBody() throws Exception {
		try {
			this.renderContent();
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

	public String getContent() {
		return content;
	}

	public Map<String, String> getWorkspaceMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.workspaceMap);
		return workspaceMap;
	}

	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	public int getDiscreteValues() {
		return discreteValues;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public List<String> getYearRangeList() {
		return yearRangeList;
	}

}
