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

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ObjectiveManagementAction")
@Scope
public class ObjectiveManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 6408907069683473791L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> perspectiveMap = this.providedSelectZeroDataMap(true);
	private ObjectiveVO objective = new ObjectiveVO();
	
	public ObjectiveManagementAction() {
		super();
	}

	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}
	
	@Autowired
	@Required
	@Resource(name="bsc.service.VisionService")
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	

	public IPerspectiveService<PerspectiveVO, BbPerspective, String> getPerspectiveService() {
		return perspectiveService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.PerspectiveService")	
	public void setPerspectiveService(
			IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}

	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.ObjectiveService")	
	public void setObjectiveService(
			IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
	}	
	
	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
	}
	
	private void loadObjectiveData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.objective, new String[]{"oid"});
		DefaultResult<ObjectiveVO> result = this.objectiveService.findObjectByOid(objective);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.objective = result.getValue();
		PerspectiveVO perspective = new PerspectiveVO();
		perspective.setPerId( this.objective.getPerId() );
		DefaultResult<PerspectiveVO> pResult = this.perspectiveService.findByUK(perspective);
		if (pResult.getValue()==null) {
			throw new ServiceException( pResult.getSystemMessage().getValue() );
		}
		perspective = pResult.getValue();
		this.getFields().put("perspectiveOid", perspective.getOid());
		VisionVO vision = new VisionVO();
		vision.setVisId( perspective.getVisId() );
		DefaultResult<VisionVO> vResult = this.visionService.findForSimpleByVisId(vision.getVisId());
		if (vResult.getValue()==null) {
			throw new ServiceException( vResult.getSystemMessage().getValue() );
		}
		vision = vResult.getValue();
		this.getFields().put("visionOid", vision.getOid());
		this.perspectiveMap = this.perspectiveService.findForMapByVisionOid(vision.getOid(), true);
	}

	/**
	 * bsc.objectiveManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0003Q")
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
	 * bsc.objectiveCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0003A")
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
	 * bsc.objectiveEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0003E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadObjectiveData();
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

	public Map<String, String> getPerspectiveMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.perspectiveMap);
		return perspectiveMap;
	}

	public ObjectiveVO getObjective() {
		return objective;
	}

	public void setObjective(ObjectiveVO objective) {
		this.objective = objective;
	}

}
