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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectScoreSaveOrUpdateAction")
@Scope
public class DegreeFeedbackProjectScoreSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -1991878548958363799L;
	protected Logger logger=Logger.getLogger(DegreeFeedbackProjectScoreSaveOrUpdateAction.class);
	private IDegreeFeedbackLogicService degreeFeedbackLogicService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private String message = "";
	private String success = IS_NO;	
	private List<DegreeFeedbackScoreVO> projectScores = new ArrayList<DegreeFeedbackScoreVO>();
	private List<DegreeFeedbackLevelVO> projectLevels = new ArrayList<DegreeFeedbackLevelVO>();
	private String minLevelOid = "#";
	
	public DegreeFeedbackProjectScoreSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IDegreeFeedbackLogicService getDegreeFeedbackLogicService() {
		return degreeFeedbackLogicService;
	}
	
	@Autowired
	@Resource(name="bsc.service.logic.DegreeFeedbackLogicService")		
	public void setDegreeFeedbackLogicService(IDegreeFeedbackLogicService degreeFeedbackLogicService) {
		this.degreeFeedbackLogicService = degreeFeedbackLogicService;
	}	
	
	@JSON(serialize=false)
	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}
	
	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackAssignService")
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}
	
	@JSON(serialize=false)
	public IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> getDegreeFeedbackScoreService() {
		return degreeFeedbackScoreService;
	}
	
	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackScoreService")
	public void setDegreeFeedbackScoreService(
			IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService) {
		this.degreeFeedbackScoreService = degreeFeedbackScoreService;
	}	
	
	@JSON(serialize=false)
	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackLevelService")
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
	}	
	
	@JSON(serialize=false)
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}	
	
	private void checkFields(String type) throws ControllerException {
		this.getCheckFieldHandler()
		.add("projectOid", NotBlankFieldCheckUtils.class, "Data error no project, please close the page!")
		.add("ownerOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG005D0003Q_ownerOid") )
		.process().throwMessage();
		
		if (!"update".equals(type)) {
			return;
		}
		Map<String, List<Map<String, Object>>> scores = null;
		try {
			scores = this.fillJsonData( this.getFields().get("scoreData") );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (scores == null || scores.get("data") == null || scores.get("data").size() < 1 ) {
			super.throwMessage("Please choice item's score!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Map<String, Object>>> fillJsonData(String dataStr) throws Exception {		
		return (Map<String, List<Map<String, Object>>>)new ObjectMapper().readValue( dataStr, LinkedHashMap.class );
	}	
	
	private List<DegreeFeedbackScoreVO> fillScores() throws Exception {
		List<DegreeFeedbackScoreVO> scores = new ArrayList<DegreeFeedbackScoreVO>();
		Map<String, List<Map<String, Object>>> dataMap = this.fillJsonData( this.getFields().get("scoreData") );
		List<Map<String, Object>> nodes = dataMap.get( "data" );
		for (Map<String, Object> node : nodes) {
			String name = String.valueOf(node.get("name"));
			if (StringUtils.isBlank(name)) {
				logger.warn( "BSC_PROG005D0003Q_RADIO name is blank." );				
				continue;
			}			
			// BSC_PROG005D0003Q_RADIO:<s:property value="project.oid"/>:<s:property value="items[#st1.index].oid"/>
			String tmp[] = name.split(":");
			if (tmp.length!=3) {
				logger.warn( "BSC_PROG005D0003Q_RADIO name is error: " + name );				
				continue;				
			}			
			DegreeFeedbackScoreVO scoreObj = new DegreeFeedbackScoreVO();
			scoreObj.setProjectOid( this.getFields().get("projectOid") );
			scoreObj.setItemOid( tmp[2] );
			scoreObj.setScore( NumberUtils.toInt(String.valueOf(node.get("value")), 0) );
			scoreObj.setMemo( String.valueOf(node.get("memo")) );
			scores.add( scoreObj );
		}
		return scores;
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("update");
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackLogicService.updateScore(
				this.getFields().get("projectOid"), 
				this.getFields().get("ownerOid"),
				this.getAccountOid(),
				this.fillScores());
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void fetchScores() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("fetch");		
		BbEmployee rater = this.employeeService.findByAccountOid( this.getAccountOid() );
		if (null == rater || StringUtils.isBlank(rater.getOid())) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) );
		}
		BbEmployee owner = this.employeeService.findByPKng( this.getFields().get("ownerOid") );
		if (null == owner || StringUtils.isBlank(owner.getOid())) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}				
		String projectOid = this.getFields().get("projectOid");
		DegreeFeedbackAssignVO assing = new DegreeFeedbackAssignVO();
		assing.setProjectOid(projectOid);
		assing.setOwnerId(owner.getEmpId());
		assing.setRaterId(rater.getEmpId());
		DefaultResult<DegreeFeedbackAssignVO> result = this.degreeFeedbackAssignService.findByUK(assing);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		assing = result.getValue();		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignOid", assing.getOid());
		paramMap.put("projectOid", projectOid);
		this.projectScores = this.degreeFeedbackScoreService.findListVOByParams(paramMap);				
		paramMap.clear();		
		paramMap.put("projectOid", projectOid);
		this.projectLevels = this.degreeFeedbackLevelService.findListVOByParams(paramMap);		
		paramMap.clear();		
		BbDegreeFeedbackLevel minLevel = this.degreeFeedbackLevelService.findForMinByProject(projectOid);
		if (minLevel!=null) {
			this.minLevelOid = minLevel.getOid();
		}		
		this.success = IS_YES;
	}
	
	/**
	 * bsc.degreeFeedbackProjectScoreUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0003Q")
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
	 * bsc.degreeFeedbackProjectScoreFetchAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0003Q")
	public String doFetchScores() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.fetchScores();
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
	public List<DegreeFeedbackScoreVO> getProjectScores() {
		return projectScores;
	}

	public void setProjectScores(List<DegreeFeedbackScoreVO> projectScores) {
		this.projectScores = projectScores;
	}

	@JSON
	public List<DegreeFeedbackLevelVO> getProjectLevels() {
		return projectLevels;
	}

	public void setProjectLevels(List<DegreeFeedbackLevelVO> projectLevels) {
		this.projectLevels = projectLevels;
	}

	@JSON
	public String getMinLevelOid() {
		return minLevelOid;
	}

	public void setMinLevelOid(String minLevelOid) {
		this.minLevelOid = minLevelOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
