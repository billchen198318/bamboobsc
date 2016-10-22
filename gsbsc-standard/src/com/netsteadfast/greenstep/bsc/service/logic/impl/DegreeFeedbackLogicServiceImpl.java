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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BscBaseBusinessProcessManagementLogicService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.vo.BusinessProcessManagementTaskVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.DegreeFeedbackLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackLogicServiceImpl extends BscBaseBusinessProcessManagementLogicService implements IDegreeFeedbackLogicService {
	protected Logger logger=Logger.getLogger(DegreeFeedbackLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_OR_MEMO_LENGTH = 500;
	private static final int MAX_REASON_LENGTH = 50;
	private IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService;
	
	public DegreeFeedbackLogicServiceImpl() {
		super();
	}
	
	@Override
	public String getBusinessProcessManagementResourceId() {
		return "DFProjectPublishProcess";
	}	

	public IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> getDegreeFeedbackProjectService() {
		return degreeFeedbackProjectService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackProjectService")
	@Required			
	public void setDegreeFeedbackProjectService(
			IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService) {
		this.degreeFeedbackProjectService = degreeFeedbackProjectService;
	}

	public IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> getDegreeFeedbackItemService() {
		return degreeFeedbackItemService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackItemService")
	@Required				
	public void setDegreeFeedbackItemService(
			IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService) {
		this.degreeFeedbackItemService = degreeFeedbackItemService;
	}

	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackLevelService")
	@Required		
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
	}
		
	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackAssignService")
	@Required		
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}
	
	public IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> getDegreeFeedbackScoreService() {
		return degreeFeedbackScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackScoreService")
	@Required			
	public void setDegreeFeedbackScoreService(
			IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService) {
		this.degreeFeedbackScoreService = degreeFeedbackScoreService;
	}
	
	private Map<String, Object> getProcessFlowParam(String projectOid, String confirm, String reason) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", projectOid);
		paramMap.put("confirm", confirm);
		paramMap.put("reason", ( super.defaultString( reason ).length()>MAX_REASON_LENGTH ? reason.substring(0, MAX_REASON_LENGTH) : reason ) );
		paramMap.put("cuserid", super.getAccountId());
		return paramMap;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> createProject(
			DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items,
			List<DegreeFeedbackLevelVO> levels, 
			List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception {
		if (project == null || levels == null || levels.size()<1 || items == null || items.size()<1 
				|| ownerEmplOids == null || ownerEmplOids.size()<1 || raterEmplOids == null || raterEmplOids.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (levels.size()>BscConstants.MAX_DEGREE_FEEDBACK_LEVEL_SIZE || items.size()>BscConstants.MAX_DEGREE_FEEDBACK_ITEM_SIZE) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		super.setStringValueMaxLength(project, "description", MAX_DESCRIPTION_OR_MEMO_LENGTH);
		project.setPublishFlag( YesNo.NO );
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.saveObject(project);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		project = result.getValue();
		this.createLevels(project, levels);
		this.createItems(project, items);
		this.createAssign(project, ownerEmplOids, raterEmplOids);
		//改成自己點選Start apply按鈕再啟用申請審核流程
		//this.startProcess(this.getProcessFlowParam(project.getOid(), YesNo.YES, "start apply."));		
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteProject(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		if (null == project || super.isBlank(project.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<DegreeFeedbackProjectVO> oldResult = this.degreeFeedbackProjectService.findObjectByOid(project);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		project = oldResult.getValue();
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariableProjectOid( project.getOid() );
		if (tasks!=null && tasks.size()>0) {
			throw new ServiceException( "Audit processing running, project cannot delete!" );
		}
		this.deleteLevels(project);
		this.deleteItems(project);
		this.deleteAssign(project);
		this.deleteScore(project);		
		return this.degreeFeedbackProjectService.deleteObject(project);
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> updateProject(
			DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items, 
			List<DegreeFeedbackLevelVO> levels, List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception {
		if (project == null || levels == null || levels.size()<1 || items == null || items.size()<1 
				|| ownerEmplOids == null || ownerEmplOids.size()<1 || raterEmplOids == null || raterEmplOids.size()<1
				|| super.isBlank(project.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (levels.size()>BscConstants.MAX_DEGREE_FEEDBACK_LEVEL_SIZE || items.size()>BscConstants.MAX_DEGREE_FEEDBACK_ITEM_SIZE) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		DefaultResult<DegreeFeedbackProjectVO> oldResult = this.degreeFeedbackProjectService.findObjectByOid(project);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}		
		if (YesNo.YES.equals(oldResult.getValue().getPublishFlag())) {
			throw new ServiceException( "Cannot update/modify published project!" );
		}
		DefaultResult<DegreeFeedbackProjectVO> ukResult = this.degreeFeedbackProjectService.findByUK(project);
		if (ukResult.getValue()!=null) {
			if ( !ukResult.getValue().getOid().equals(project.getOid()) ) { // 有相同的名稱UK資料存在了
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_IS_EXIST));
			}
		}		
		super.setStringValueMaxLength(project, "description", MAX_DESCRIPTION_OR_MEMO_LENGTH);
		project.setPublishFlag( oldResult.getValue().getPublishFlag() );
		this.deleteLevels(project);
		this.deleteItems(project);
		this.deleteAssign(project);
		this.deleteScore(project); // 原則上還能修改的project不會有分數資料, 因為還沒發佈
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.updateObject(project);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		project = result.getValue();
		this.createLevels(project, levels);
		this.createItems(project, items);
		this.createAssign(project, ownerEmplOids, raterEmplOids);
		return result;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> updateScore(
			String projectOid, String ownerEmployeeOid, String raterEmployeeOid, 
			List<DegreeFeedbackScoreVO> scores) throws ServiceException, Exception {
		if (super.isBlank(projectOid) || super.isNoSelectId(ownerEmployeeOid) || super.isBlank(raterEmployeeOid) 
				|| null == scores || scores.size()<1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		project.setOid( projectOid );
		DefaultResult<DegreeFeedbackProjectVO> projectResult = this.degreeFeedbackProjectService.findObjectByOid(project);
		if (projectResult.getValue() == null) {
			throw new ServiceException(projectResult.getSystemMessage().getValue());
		}
		project = projectResult.getValue();
		DefaultResult<DegreeFeedbackProjectVO> result = new DefaultResult<DegreeFeedbackProjectVO>();
		BbEmployee rater = this.getEmployeeService().findByAccountOid( raterEmployeeOid );
		if (null == rater || super.isBlank(rater.getOid())) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS) );
		}
		BbEmployee owner = this.getEmployeeService().findByPKng( ownerEmployeeOid );
		if (null == owner || super.isBlank(owner.getOid())) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		
		DegreeFeedbackAssignVO assign = this.findAssign(projectOid, rater.getEmpId(), owner.getEmpId());
		this.deleteScoreWithAssign(project, assign);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (DegreeFeedbackScoreVO score : scores) {
			paramMap.put("oid", score.getItemOid());
			if (this.degreeFeedbackItemService.countByParams(paramMap)!=1) {
				throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
			}
			score.setAssignOid( assign.getOid() );
			super.setStringValueMaxLength(score, "memo", MAX_DESCRIPTION_OR_MEMO_LENGTH);
			DefaultResult<DegreeFeedbackScoreVO> insertResult = this.degreeFeedbackScoreService.saveObject(score);
			if (insertResult.getValue()==null) {
				throw new ServiceException(insertResult.getSystemMessage().getValue());
			}
			result.setSystemMessage( insertResult.getSystemMessage() );			
		}
		paramMap.clear();
		paramMap = null;
		result.setValue(project);
		result.setSystemMessage( new SystemMessage( SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS) ) );		
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public void confirmTask(String projectOid, String taskId, String reason, String confirm) throws ServiceException, Exception {
		if (super.isBlank(projectOid) || super.isBlank(taskId) || super.isBlank(confirm)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DegreeFeedbackProjectVO project = new DegreeFeedbackProjectVO();
		project.setOid(projectOid);
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.findObjectByOid(project);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().toString());
		}
		project = result.getValue();		
		this.completeTask(taskId, this.getProcessFlowParam(projectOid, confirm, reason));
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariableProjectOid(projectOid);
		if (null != tasks && tasks.size()>0) { 
			return;
		}
		
		// 流程跑完了, 更新專案flag , 讓專案發佈
		if (YesNo.YES.equals(confirm)) {
			project.setPublishFlag( YesNo.YES );
			this.degreeFeedbackProjectService.updateObject(project);			
		}
		
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT, ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public String getTaskDiagram(String taskId) throws ServiceException, Exception {
		if (super.isBlank(taskId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return BusinessProcessManagementUtils.getTaskDiagramById2Upload(this.getBusinessProcessManagementResourceId(), taskId);
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> startProcess(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		if (null == project || super.isBlank(project.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService.findObjectByOid(project);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		project = result.getValue();
		if (YesNo.YES.equals(project.getPublishFlag())) {
			throw new ServiceException( "The project is published, cannot start audit processing!" );
		}
		List<BusinessProcessManagementTaskVO> tasks = this.queryTaskByVariableProjectOid(project.getOid());
		if (null!=tasks && tasks.size()>0) {
			throw new ServiceException( "Audit processing has been started!" );
		}
		this.startProcess( this.getProcessFlowParam(project.getOid(), YesNo.YES, "start apply." + project.getName()) );
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );		
		return result;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public List<BusinessProcessManagementTaskVO> queryTaskByVariableProjectOid(String projectOid) throws ServiceException, Exception {
		if (super.isBlank(projectOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return super.queryTaskPlus("projectOid", projectOid);
	}	
	
	private void createLevels(DegreeFeedbackProjectVO project, List<DegreeFeedbackLevelVO> levels) throws ServiceException, Exception {
		for (DegreeFeedbackLevelVO level : levels) {
			if ( super.isBlank(level.getName()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
			}
			if (level.getValue()<1) { // 等級評分分數設定不能小於1
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
			}
			level.setProjectOid(project.getOid());
			this.degreeFeedbackLevelService.saveObject(level);
		}
	}
	
	private void createItems(DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items) throws ServiceException, Exception {
		for (DegreeFeedbackItemVO item : items) {
			if ( super.isBlank(item.getName()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
			}
			item.setProjectOid(project.getOid());
			super.setStringValueMaxLength(item, "description", MAX_DESCRIPTION_OR_MEMO_LENGTH);
			this.degreeFeedbackItemService.saveObject(item);
		}
	}
	
	private void createAssign(DegreeFeedbackProjectVO project, 
			List<String> ownerEmplOids, List<String> raterEmplOids) throws ServiceException, Exception {
		Map<String, EmployeeVO> tmpBag = new HashMap<String, EmployeeVO>();
		for (String ownerOid : ownerEmplOids) {
			EmployeeVO owner = this.fetchEmployee(ownerOid, tmpBag);
			for (String raterOid : raterEmplOids) {
				EmployeeVO rater = this.fetchEmployee(raterOid, tmpBag);
				DegreeFeedbackAssignVO assign = new DegreeFeedbackAssignVO();
				assign.setProjectOid(project.getOid());
				assign.setOwnerId(owner.getEmpId());
				assign.setRaterId(rater.getEmpId());
				this.degreeFeedbackAssignService.saveObject(assign);
			}			
		}
		tmpBag.clear();
		tmpBag = null;
	}
	
	private EmployeeVO fetchEmployee(String oid, Map<String, EmployeeVO> tmpBag) throws ServiceException, Exception {
		if (tmpBag.get(oid)!=null) {
			return tmpBag.get(oid);
		}
		EmployeeVO employee = this.findEmployeeData(oid);
		tmpBag.put(oid, employee);
		return employee;
	}
	
	private void deleteLevels(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		List<BbDegreeFeedbackLevel> levels = this.degreeFeedbackLevelService.findListByParams(paramMap);
		for (BbDegreeFeedbackLevel level : levels) {
			this.degreeFeedbackLevelService.delete(level);
		}
	}
	
	private void deleteItems(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		List<BbDegreeFeedbackItem> items = this.degreeFeedbackItemService.findListByParams(paramMap);
		for (BbDegreeFeedbackItem item : items) {
			this.degreeFeedbackItemService.delete(item);
		}
	}
	
	private void deleteAssign(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		List<BbDegreeFeedbackAssign> assigns = this.degreeFeedbackAssignService.findListByParams(paramMap);
		for (BbDegreeFeedbackAssign assign : assigns) {
			this.degreeFeedbackAssignService.delete(assign);
		}
	}
	
	private void deleteScore(DegreeFeedbackProjectVO project) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		List<BbDegreeFeedbackScore> scores = this.degreeFeedbackScoreService.findListByParams(paramMap);
		for (BbDegreeFeedbackScore score : scores) {
			this.degreeFeedbackScoreService.delete(score);
		}		
	}
	
	private void deleteScoreWithAssign(DegreeFeedbackProjectVO project, 
			DegreeFeedbackAssignVO assign) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", project.getOid());
		paramMap.put("assignOid", assign.getOid());
		List<BbDegreeFeedbackScore> scores = this.degreeFeedbackScoreService.findListByParams(paramMap);
		for (BbDegreeFeedbackScore score : scores) {
			this.degreeFeedbackScoreService.delete(score);
		}		
	}	
	
	private DegreeFeedbackAssignVO findAssign(String projectOid, 
			String raterId, String ownerId) throws ServiceException, Exception {
		DegreeFeedbackAssignVO assing = new DegreeFeedbackAssignVO();
		assing.setProjectOid(projectOid);
		assing.setOwnerId(ownerId);
		assing.setRaterId(raterId);
		DefaultResult<DegreeFeedbackAssignVO> result = this.degreeFeedbackAssignService.findByUK(assing);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		return result.getValue();
	}
	
}
