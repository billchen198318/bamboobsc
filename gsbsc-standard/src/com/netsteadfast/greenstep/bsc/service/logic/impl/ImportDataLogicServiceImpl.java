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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IImportDataLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IObjectiveLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IPerspectiveLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IVisionLogicService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.util.BscFormulaUtils;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.ImportDataLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ImportDataLogicServiceImpl extends BaseLogicService implements IImportDataLogicService {
	protected Logger logger=Logger.getLogger(ImportDataLogicServiceImpl.class);
	private IVisionLogicService visionLogicService;
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private IPerspectiveLogicService perspectiveLogicService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IObjectiveLogicService objectiveLogicService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiLogicService kpiLogicService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IFormulaService<FormulaVO, BbFormula, String> formulaService; 
	private IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService; 
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	
	public ImportDataLogicServiceImpl() {
		super();
	}
	
	public IVisionLogicService getVisionLogicService() {
		return visionLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.VisionLogicService")
	@Required		
	public void setVisionLogicService(IVisionLogicService visionLogicService) {
		this.visionLogicService = visionLogicService;
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

	public IPerspectiveLogicService getPerspectiveLogicService() {
		return perspectiveLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.PerspectiveLogicService")
	@Required		
	public void setPerspectiveLogicService(
			IPerspectiveLogicService perspectiveLogicService) {
		this.perspectiveLogicService = perspectiveLogicService;
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

	public IObjectiveLogicService getObjectiveLogicService() {
		return objectiveLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.ObjectiveLogicService")
	@Required			
	public void setObjectiveLogicService(
			IObjectiveLogicService objectiveLogicService) {
		this.objectiveLogicService = objectiveLogicService;
	}

	public IObjectiveService<ObjectiveVO, BbObjective, String> getObjectiveService() {
		return objectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.ObjectiveService")
	@Required		
	public void setObjectiveService(
			IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService) {
		this.objectiveService = objectiveService;
	}

	public IKpiLogicService getKpiLogicService() {
		return kpiLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.KpiLogicService")
	@Required			
	public void setKpiLogicService(IKpiLogicService kpiLogicService) {
		this.kpiLogicService = kpiLogicService;
	}

	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")
	@Required			
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}

	public IFormulaService<FormulaVO, BbFormula, String> getFormulaService() {
		return formulaService;
	}

	@Autowired
	@Resource(name="bsc.service.FormulaService")
	@Required			
	public void setFormulaService(
			IFormulaService<FormulaVO, BbFormula, String> formulaService) {
		this.formulaService = formulaService;
	}

	public IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> getAggregationMethodService() {
		return aggregationMethodService;
	}

	@Autowired
	@Resource(name="bsc.service.AggregationMethodService")
	@Required		
	public void setAggregationMethodService(
			IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService) {
		this.aggregationMethodService = aggregationMethodService;
	}
	
	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeService")
	@Required	
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationService")
	@Required		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}	
	
	public IMeasureDataService<MeasureDataVO, BbMeasureData, String> getMeasureDataService() {
		return measureDataService;
	}

	@Autowired
	@Resource(name="bsc.service.MeasureDataService")
	@Required			
	public void setMeasureDataService(
			IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService) {
		this.measureDataService = measureDataService;
	}		

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> importVisionCsv(String uploadOid) throws ServiceException, Exception {
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN001");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String visId = data.get("VIS_ID");
			String title = data.get("TITLE");
			String content = data.get("CONTENT");
			if ( super.isBlank(visId) ) {
				msg.append("row: " + row + " id is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(title) ) {
				msg.append("row: " + row + " title is blank." + Constants.HTML_BR);
				continue;
			}			
			if ( super.isBlank(content) ) {
				msg.append("row: " + row + " content is blank." + Constants.HTML_BR);
				continue;
			}
			VisionVO vision = new VisionVO();
			vision.setVisId(visId);
			vision.setTitle(title);			
			vision.setContent( content.getBytes(Constants.BASE_ENCODING) );			
			paramMap.put("visId", visId);
			if ( this.visionService.countByParams(paramMap) > 0 ) { // update
				DefaultResult<VisionVO> oldResult = this.visionService.findByUK(vision);
				vision.setOid( oldResult.getValue().getOid() );
				this.visionLogicService.update(vision);
			} else { // insert
				this.visionLogicService.create(vision);
			}
			success = true; 
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<Boolean> importPerspectivesCsv(String uploadOid) throws ServiceException, Exception {		
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN002");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String perId = data.get("PER_ID");
			String visId = data.get("VIS_ID");
			String name = data.get("NAME");
			String weight = data.get("WEIGHT");
			String target = data.get("TARGET");
			String min = data.get("MIN");
			String description = data.get("DESCRIPTION");	
			if ( super.isBlank(perId) ) {
				msg.append("row: " + row + " perspective-id is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(visId) ) {
				msg.append("row: " + row + " vision-id is blank." + Constants.HTML_BR);
				continue;
			}		
			if ( super.isBlank(name) ) {
				msg.append("row: " + row + " name is blank." + Constants.HTML_BR);
				continue;
			}			
			if ( super.isBlank(weight) ) {
				msg.append("row: " + row + " weight is blank." + Constants.HTML_BR);
				continue;				
			}
			if ( super.isBlank(target) ) {
				msg.append("row: " + row + " target is blank." + Constants.HTML_BR);
				continue;				
			}
			if ( super.isBlank(min) ) {
				msg.append("row: " + row + " min is blank." + Constants.HTML_BR);
				continue;				
			}			
			if ( !NumberUtils.isCreatable(weight) ) {
				msg.append("row: " + row + " weight is not number." + Constants.HTML_BR);
				continue;					
			}
			if ( !NumberUtils.isCreatable(target) ) {
				msg.append("row: " + row + " target is not number." + Constants.HTML_BR);
				continue;					
			}
			if ( !NumberUtils.isCreatable(min) ) {
				msg.append("row: " + row + " min is not number." + Constants.HTML_BR);
				continue;					
			}		
			paramMap.clear();
			paramMap.put("visId", visId);
			if ( this.visionService.countByParams(paramMap) < 1 ) {
				throw new ServiceException( "row: " + row + " vision is not found " + visId );
			}
			DefaultResult<VisionVO> visionResult = this.visionService.findForSimpleByVisId(visId);
			if ( visionResult.getValue()==null) {
				throw new ServiceException( visionResult.getSystemMessage().getValue() );
			}
			PerspectiveVO perspective = new PerspectiveVO();
			perspective.setPerId(perId);
			perspective.setVisId(visId);
			perspective.setName(name);
			perspective.setWeight( new BigDecimal(weight) );
			perspective.setTarget( Float.valueOf(target) );
			perspective.setMin( Float.valueOf(min) );
			perspective.setDescription(description);		
			paramMap.clear();
			paramMap.put("perId", perId);			
			if ( this.perspectiveService.countByParams(paramMap) > 0 ) { // update
				DefaultResult<PerspectiveVO> oldResult = this.perspectiveService.findByUK(perspective);
				perspective.setOid( oldResult.getValue().getOid() );				
				this.perspectiveLogicService.update(perspective, visionResult.getValue().getOid() );
			} else { // insert
				this.perspectiveLogicService.create(perspective, visionResult.getValue().getOid() );
			}
			success = true; 
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> importObjectivesCsv(String uploadOid) throws ServiceException, Exception {
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN003");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}		
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String objId = data.get("OBJ_ID");
			String perId = data.get("PER_ID");			
			String name = data.get("NAME");
			String weight = data.get("WEIGHT");
			String target = data.get("TARGET");
			String min = data.get("MIN");
			String description = data.get("DESCRIPTION");
			if ( super.isBlank(objId) ) {
				msg.append("row: " + row + " objective-id is blank." + Constants.HTML_BR);
				continue;
			}					
			if ( super.isBlank(perId) ) {
				msg.append("row: " + row + " perspective-id is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(name) ) {
				msg.append("row: " + row + " name is blank." + Constants.HTML_BR);
				continue;
			}			
			if ( super.isBlank(weight) ) {
				msg.append("row: " + row + " weight is blank." + Constants.HTML_BR);
				continue;				
			}
			if ( super.isBlank(target) ) {
				msg.append("row: " + row + " target is blank." + Constants.HTML_BR);
				continue;				
			}
			if ( super.isBlank(min) ) {
				msg.append("row: " + row + " min is blank." + Constants.HTML_BR);
				continue;				
			}
			if ( !NumberUtils.isCreatable(weight) ) {
				msg.append("row: " + row + " weight is not number." + Constants.HTML_BR);
				continue;					
			}
			if ( !NumberUtils.isCreatable(target) ) {
				msg.append("row: " + row + " target is not number." + Constants.HTML_BR);
				continue;					
			}
			if ( !NumberUtils.isCreatable(min) ) {
				msg.append("row: " + row + " min is not number." + Constants.HTML_BR);
				continue;					
			}		
			PerspectiveVO perspective = new PerspectiveVO();
			perspective.setPerId(perId);
			DefaultResult<PerspectiveVO> perResult = this.perspectiveService.findByUK(perspective);
			if ( perResult.getValue() == null ) {
				throw new ServiceException( "row: " + row + " perspective is not found " + perId );
			}
			perspective = perResult.getValue();
			
			ObjectiveVO objective = new ObjectiveVO();
			objective.setObjId( objId );
			DefaultResult<ObjectiveVO> oldResult = this.objectiveService.findByUK(objective);
			objective.setObjId(objId);			
			objective.setPerId(perId);
			objective.setName(name);
			objective.setWeight( new BigDecimal(weight) );
			objective.setTarget( Float.valueOf(target) );
			objective.setMin( Float.valueOf(min) );
			objective.setDescription(description);			
			if ( oldResult.getValue()!=null ) { // update
				objective.setOid( oldResult.getValue().getOid() );
				this.objectiveLogicService.update(objective, perspective.getOid());
			} else { // insert
				this.objectiveLogicService.create(objective, perspective.getOid());
			}			
			success = true;
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}
	
	private String checkKPIsDataError(
			int row,
			String id, String objId, String name, String weight, String target,
			String min, String unit, String forId, String management, String compareType, String cal,
			String dataType, String orgaMeasureSeparate, String userMeasureSeparate, String quasiRange,
			String description, String max) throws Exception {
		if ( super.isBlank(id) ) {
			return "row: " + row + " id is blank." + Constants.HTML_BR;
		}						
		if ( super.isBlank(objId) ) {
			return "row: " + row + " objective-id is blank." + Constants.HTML_BR;
		}					
		if ( super.isBlank(name) ) {
			return "row: " + row + " name is blank." + Constants.HTML_BR;
		}			
		if ( super.isBlank(weight) ) {
			return "row: " + row + " weight is blank." + Constants.HTML_BR;				
		}
		if ( super.isBlank(max) ) {
			return "row: " + row + " max is blank." + Constants.HTML_BR;			
		}		
		if ( super.isBlank(target) ) {
			return "row: " + row + " target is blank." + Constants.HTML_BR;			
		}
		if ( super.isBlank(min) ) {
			return "row: " + row + " min is blank." + Constants.HTML_BR;			
		}
		if ( super.isBlank(unit) ) {
			return "row: " + row + " unit is blank." + Constants.HTML_BR;			
		}			
		if ( super.isBlank(forId) ) {
			return "row: " + row + " formula-id is blank." + Constants.HTML_BR;			
		}			
		if ( super.isBlank(management) ) {
			return "row: " + row + " management method is blank." + Constants.HTML_BR;			
		}
		if ( super.isBlank(compareType) ) {
			return "row: " + row + " compare type is blank." + Constants.HTML_BR;			
		}
		if ( super.isBlank(cal) ) {
			return "row: " + row + " Calculation( aggregation method ) is blank." + Constants.HTML_BR;			
		}
		if ( super.isBlank(dataType) ) {
			return "row: " + row + " belong type is blank." + Constants.HTML_BR;			
		}			
		if ( super.isBlank(orgaMeasureSeparate) ) {
			return "row: " + row + " Organization measure-data separate flag is blank." + Constants.HTML_BR;		
		}	
		if ( super.isBlank(userMeasureSeparate) ) {
			return "row: " + row + " Personal measure-data separate flag is blank." + Constants.HTML_BR;			
		}			
		if ( super.isBlank(quasiRange) ) {
			return "row: " + row + " quasi range is blank." + Constants.HTML_BR;
		}				
		if ( !NumberUtils.isCreatable(weight) ) {
			return "row: " + row + " weight is not number." + Constants.HTML_BR;		
		}
		if ( !NumberUtils.isCreatable(max) ) {
			return "row: " + row + " max is not number." + Constants.HTML_BR;				
		}		
		if ( !NumberUtils.isCreatable(target) ) {
			return "row: " + row + " target is not number." + Constants.HTML_BR;				
		}
		if ( !NumberUtils.isCreatable(min) ) {
			return "row: " + row + " min is not number." + Constants.HTML_BR;			
		}		
		if ( !NumberUtils.isCreatable(quasiRange) ) {
			return "row: " + row + " quasi range is not number." + Constants.HTML_BR;			
		}		
		if ( BscKpiCode.getCompareTypeMap(false).get(compareType) == null ) {
			return "row: " + row + " compare type is not accept." + Constants.HTML_BR;
		}
		if ( BscKpiCode.getDataTypeMap(false).get(dataType) == null ) {
			return "row: " + row + " belong type is not accept." + Constants.HTML_BR;
		}
		if ( BscKpiCode.getManagementMap(false).get(management) == null ) {
			return "row: " + row + " management method is not accept." + Constants.HTML_BR;				
		}			
		if ( BscKpiCode.getQuasiRangeMap().get(quasiRange) == null ) {
			return "row: " + row + " quasi range is not accept." + Constants.HTML_BR;		
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("aggrId", cal);
		if (this.aggregationMethodService.countByParams(paramMap) < 1) {
			return "row: " + row + " Calculation( aggregation method ) is not accept." + Constants.HTML_BR;
		}
		return "";
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> importKPIsCsv(String uploadOid) throws ServiceException, Exception {
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN004");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}		
		List<String> organizationOids = new ArrayList<String>();
		List<String> employeeOids = new ArrayList<String>();
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		// import mode cannot update KPI's attachment/documents, because KPI export no attachment/documents data.
		List<String> kpiAttacDocs = new ArrayList<String>(); 
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String id = data.get("ID");
			String objId = data.get("OBJ_ID");
			String name = data.get("NAME");
			String weight = data.get("WEIGHT");
			String target = data.get("TARGET");
			String min = data.get("MIN");
			String unit = data.get("UNIT");
			String forId = data.get("FOR_ID");
			String management = data.get("MANAGEMENT");
			String compareType = data.get("COMPARE_TYPE");
			String cal = data.get("CAL");
			String dataType = data.get("DATA_TYPE");
			String orgaMeasureSeparate = data.get("ORGA_MEASURE_SEPARATE");
			String userMeasureSeparate = data.get("USER_MEASURE_SEPARATE");
			String quasiRange = data.get("QUASI_RANGE");
			String description = data.get("DESCRIPTION");
			String max = data.get("MAX");
			String trendsForId = data.get("TRENDS_FOR_ID");
			String activate = data.get("ACTIVATE");
			String errMsg = this.checkKPIsDataError(
					row,
					id, objId, name, weight, target, 
					min, unit, forId, management, compareType, cal, 
					dataType, orgaMeasureSeparate, userMeasureSeparate, quasiRange, 
					description, max);
			if ( !"".equals(errMsg) ) {
				msg.append(errMsg);
				continue;
			}
			ObjectiveVO objective = new ObjectiveVO();
			objective.setObjId(objId);
			DefaultResult<ObjectiveVO> objResult = this.objectiveService.findByUK(objective);
			if ( objResult.getValue() == null ) {
				throw new ServiceException( "row: " + row + " strategy-objectives is not found " + objId );
			}
			objective = objResult.getValue();
			
			/*
			FormulaVO formula = new FormulaVO();
			formula.setForId(forId);
			DefaultResult<FormulaVO> forResult = this.formulaService.findByUK(formula);
			if ( forResult.getValue() == null ) {
				throw new ServiceException( "row: " + row + " formula is not found " + objId );
			}
			formula = forResult.getValue();
			*/
			
			KpiVO kpi = new KpiVO();
			kpi.setId(id);
			kpi.setName(name);
			kpi.setWeight( new BigDecimal(weight) );
			kpi.setMax( Float.valueOf(max) );
			kpi.setTarget( Float.valueOf(target) );
			kpi.setMin( Float.valueOf(min) );
			kpi.setCompareType(compareType);
			kpi.setUnit(unit);
			kpi.setManagement(management);
			kpi.setQuasiRange( Integer.parseInt(quasiRange) );
			kpi.setDataType(dataType);
			kpi.setDescription(description);
			kpi.setOrgaMeasureSeparate(YesNo.NO);
			kpi.setUserMeasureSeparate(YesNo.NO);
			kpi.setActivate(YesNo.NO);
			if ( YesNo.YES.equals(orgaMeasureSeparate) || YesNo.YES.equals(userMeasureSeparate) ) {
				msg.append("row: " + row + " import mode no support organization/personal measure separate data. please manual settings." + Constants.HTML_BR);
			}
			if (YesNo.YES.equals(activate)) {
				kpi.setActivate(YesNo.YES);
			}
			DefaultResult<KpiVO> kResult = this.kpiService.findByUK(kpi);
			if (kResult.getValue()!=null) { // update
				kpi.setOid( kResult.getValue().getOid() );
				this.kpiLogicService.update(
						kpi, 
						objective.getOid(), 
						BscFormulaUtils.getFormulaById(forId).getOid(), 
						AggregationMethodUtils.findSimpleById(cal).getOid(), 
						organizationOids, 
						employeeOids,
						BscFormulaUtils.getFormulaById(trendsForId).getOid(),
						kpiAttacDocs);
			} else { // insert
				this.kpiLogicService.create(
						kpi, 
						objective.getOid(), 
						BscFormulaUtils.getFormulaById(forId).getOid(), 
						AggregationMethodUtils.findSimpleById(cal).getOid(), 
						organizationOids, 
						employeeOids,
						BscFormulaUtils.getFormulaById(trendsForId).getOid(),
						kpiAttacDocs);
			}			
			success = true;
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> importMeasureData(String uploadOid) throws ServiceException, Exception {
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN005");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}		
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String kpiId = data.get("KPI_ID");
			String date = data.get("DATE");
			String target = data.get("TARGET");
			String actual = data.get("ACTUAL");
			String frequency = data.get("FREQUENCY");
			String orgId = data.get("ORG_ID");
			String empId = data.get("EMP_ID");
			if ( super.isBlank(kpiId) ) {
				msg.append("row: " + row + " kpi id is blank." + Constants.HTML_BR);
				continue;
			}				
			if ( super.isBlank(date) ) {
				msg.append("row: " + row + " date is blank." + Constants.HTML_BR);
				continue;
			}							
			if ( super.isBlank(target) ) {
				msg.append("row: " + row + " target is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(actual) ) {
				msg.append("row: " + row + " actual is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(frequency) ) {
				msg.append("row: " + row + " frequency is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(orgId) ) {
				msg.append("row: " + row + " organization-id is blank." + Constants.HTML_BR);
				continue;
			}
			if ( super.isBlank(empId) ) {
				msg.append("row: " + row + " employee-no is blank." + Constants.HTML_BR);
				continue;
			}
			if ( !SimpleUtils.isDate(date) ) {
				msg.append("row: " + row + " is not date " + date + Constants.HTML_BR);
				continue;					
			}			
			if ( !NumberUtils.isCreatable(target) ) {
				msg.append("row: " + row + " target is not number." + Constants.HTML_BR);
				continue;					
			}
			if ( !NumberUtils.isCreatable(actual) ) {
				msg.append("row: " + row + " actual is not number." + Constants.HTML_BR);
				continue;					
			}		
			if ( BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) == null ) {
				msg.append("row: " + row + " frequency is not found." + Constants.HTML_BR);
				continue;			
			}
			paramMap.clear();
			paramMap.put("id", kpiId);
			if ( this.kpiService.countByParams(paramMap) < 1 ) {
				msg.append("row: " + row + " KPI is not found " + kpiId + Constants.HTML_BR);
				continue;					
			}
			if ( !BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(orgId) ) {
				paramMap.clear();
				paramMap.put("orgId", orgId);
				if ( this.organizationService.countByParams(paramMap) < 1 ) {
					msg.append("row: " + row + " organization-id is not found " + orgId + Constants.HTML_BR);
					continue;					
				}				
			}
			if ( !BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(empId) ) {
				paramMap.clear();
				paramMap.put("empId", empId);
				if ( this.employeeService.countByParams(paramMap) < 1 ) {
					msg.append("row: " + row + " employee-no is not found " + empId + Constants.HTML_BR);
					continue;					
				}				
			}
			MeasureDataVO measureData = new MeasureDataVO();
			measureData.setKpiId(kpiId);
			measureData.setDate(date);
			measureData.setTarget( Float.valueOf(target) );
			measureData.setActual( Float.valueOf(actual) );
			measureData.setFrequency(frequency);
			measureData.setOrgId(orgId);
			measureData.setEmpId(empId);
			DefaultResult<MeasureDataVO> oldResult = this.measureDataService.findByUK(measureData);
			if (oldResult.getValue()!=null) { // update
				measureData.setOid( oldResult.getValue().getOid() );
				this.measureDataService.updateObject(measureData);
			} else { // insert
				this.measureDataService.saveObject(measureData);
			}
			success = true;
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

}
