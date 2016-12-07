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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PerspectiveManagementAction")
@Scope
public class PerspectiveManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -1542429371782603688L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;  
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private PerspectiveVO perspective = new PerspectiveVO(); // edit模式要用
	
	public PerspectiveManagementAction() {
		super();
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

	public IPerspectiveService<PerspectiveVO, BbPerspective, String> getPerspectiveService() {
		return perspectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.PerspectiveService")
	@Required		
	public void setPerspectiveService(
			IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}

	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
	}		
	
	private void loadPerspectiveData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.perspective, new String[]{"oid"});
		DefaultResult<PerspectiveVO> result = this.perspectiveService.findObjectByOid(this.perspective);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.perspective = result.getValue();
		this.getFields().put("visionSelectValue", Constants.HTML_SELECT_NO_SELECT_ID);
		VisionVO vision = new VisionVO();
		vision.setVisId( this.perspective.getVisId() );
		DefaultResult<VisionVO> vResult = this.visionService.findForSimpleByVisId(vision.getVisId());
		if (vResult.getValue()!=null) {
			this.getFields().put("visionSelectValue", vResult.getValue().getOid() );
		}
	}
	
	/**
	 *  bsc.perspectiveManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002Q")
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
	 *  bsc.perspectiveCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002A")
	public String create() throws Exception {
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
	 *  bsc.perspectiveEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0002E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadPerspectiveData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;		
	}	
	
	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	public PerspectiveVO getPerspective() {
		return perspective;
	}

}
