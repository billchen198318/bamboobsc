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

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.vo.StrategyMapItemsVO;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.StrategyMapManagementAction")
@Scope
public class StrategyMapManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -818474109895868062L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private String visionOid = "";
	private List<String> divItems = new ArrayList<String>();
	private List<String> cssItems = new ArrayList<String>();
	private List<String> conItems = new ArrayList<String>();
	private String printMode = YesNo.NO;
	private ObjectiveVO objective; // 給 dbclick 地圖上的 策略目標方塊 , 開啟顯示策略目標內容Dialog用的
	
	public StrategyMapManagementAction() {
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
	public void setPerspectiveService(IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}	
	
	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.ObjectiveService")
	@Required	
	public void setObjectiveService(IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
	}

	@SuppressWarnings("unchecked")
	private Context getChainContext() throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", this.visionOid);
		return context;
	}

	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
	}	
	
	private void loadNew() throws ControllerException, ServiceException, Exception {
		if ( StringUtils.isBlank(this.visionOid) || super.isNoSelectId(this.visionOid) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_vision") );
		}
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("strategyMapItemsForNewChain", context);
		this.setPageMessage( resultObj.getMessage() );
		if ( resultObj.getValue() instanceof StrategyMapItemsVO ) {
			this.divItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getDiv();
			this.cssItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCss();
		}
		
	}
	
	private void loadRecord() throws ControllerException, ServiceException, Exception {
		if ( StringUtils.isBlank(this.visionOid) || super.isNoSelectId(this.visionOid) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_vision") );
		}
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("strategyMapItemsForRecChain", context);
		this.setPageMessage( resultObj.getMessage() );
		if ( resultObj.getValue() instanceof StrategyMapItemsVO ) {
			this.divItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getDiv();
			this.cssItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCss();
			this.conItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCon();
		}		
	}
	
	private void loadObjectiveItem() throws ControllerException, ServiceException, Exception {
		String objId = super.defaultString( super.getFields().get("oid") ).trim(); // 這裡的 fields.oid 放的是 BB_OBJECTIVE.OBJ_ID
		if (StringUtils.isBlank(objId)) {
			throw new ControllerException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.objective = new  ObjectiveVO();
		this.objective.setObjId(objId);
		DefaultResult<ObjectiveVO> result = this.objectiveService.findByUK(this.objective);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.objective = result.getValue();
		
		PerspectiveVO perspective = new PerspectiveVO();
		perspective.setPerId( this.objective.getPerId() );
		DefaultResult<PerspectiveVO> pResult = this.perspectiveService.findByUK(perspective);
		if (pResult.getValue() == null) {
			throw new ServiceException( pResult.getSystemMessage().getValue() );
		}
		perspective = pResult.getValue();
		
		DefaultResult<VisionVO> vResult = this.visionService.findForSimpleByVisId(perspective.getVisId());
		if (vResult.getValue() == null) {
			throw new ServiceException( vResult.getSystemMessage().getValue() );
		}
		VisionVO vision = vResult.getValue();
		
		this.visionOid = vision.getOid();
	}
	
	/**
	 *  bsc.strategyMapManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
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
	 *  bsc.strategyMapLoadNewAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doLoadNew() throws Exception {
		try {
			this.initData();
			this.loadNew();
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
	 *  bsc.strategyMapLoadRecordAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doLoadRecord() throws Exception {
		try {
			this.initData();
			this.loadRecord();
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
	 *  bsc.strategyMapOpenWinDlgAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doOpenWinDlg() throws Exception {
		String forward = doLoadRecord();
		this.printMode = YesNo.YES;
		return forward;
	}
	
	/**
	 * bsc.strategyMapOpenStrategyObjectiveItemWinDlgAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q_S00")
	public String doOpenStrategyObjectiveItemWinDlg() throws Exception {
		String forward = INPUT;
		try {
			this.loadObjectiveItem();
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

	public Map<String, String> getVisionMap() {
		return visionMap;
	}

	public String getVisionOid() {
		return visionOid;
	}

	public void setVisionOid(String visionOid) {
		this.visionOid = visionOid;
	}

	public List<String> getDivItems() {
		return divItems;
	}

	public List<String> getCssItems() {
		return cssItems;
	}

	public List<String> getConItems() {
		return conItems;
	}

	public String getPrintMode() {
		return printMode;
	}

	public ObjectiveVO getObjective() {
		return objective;
	}
	
	public String getYearDate() {
		return super.getNowDate().substring(0, 4);
	}
	
}
