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

import java.util.HashMap;
import java.util.List;
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
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.bsc.service.IKpiAttacService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.util.BscFormulaUtils;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbKpiAttac;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiAttacVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiManagementAction")
@Scope
public class KpiManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 2032871743482471209L;
	private int maxAttachmentDocument = 5;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IFormulaService<FormulaVO, BbFormula, String> formulaService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	private IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService;
	private IKpiAttacService<KpiAttacVO, BbKpiAttac, String> kpiAttacService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> perspectiveMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> objectiveMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> managementMap = BscKpiCode.getManagementMap(true);
	//private Map<String, String> calculationMap = BscKpiCode.getCalculationMap(true);
	private Map<String, String> calculationMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> compareTypeMap = BscKpiCode.getCompareTypeMap(true);
	private Map<String, String> dataTypeMap = BscKpiCode.getDataTypeMap(true);
	private Map<String, String> formulaMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> trendsFormulaMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> quasiRangeMap = BscKpiCode.getQuasiRangeMap();
	private KpiVO kpi = new KpiVO();
	private List<KpiAttacVO> kpiAttac = null;
	
	public KpiManagementAction() {
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

	public IFormulaService<FormulaVO, BbFormula, String> getFormulaService() {
		return formulaService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.FormulaService")		
	public void setFormulaService(
			IFormulaService<FormulaVO, BbFormula, String> formulaService) {
		this.formulaService = formulaService;
	}
	
	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.KpiService")		
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.OrganizationService")		
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
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

	public IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> getAggregationMethodService() {
		return aggregationMethodService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.AggregationMethodService")		
	public void setAggregationMethodService(
			IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService) {
		this.aggregationMethodService = aggregationMethodService;
	}

	public IKpiAttacService<KpiAttacVO, BbKpiAttac, String> getKpiAttacService() {
		return kpiAttacService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.KpiAttacService")	
	public void setKpiAttacService(
			IKpiAttacService<KpiAttacVO, BbKpiAttac, String> kpiAttacService) {
		this.kpiAttacService = kpiAttacService;
	}

	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Required
	@Resource(name="core.service.SysUploadService")		
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}

	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
		this.formulaMap = this.formulaService.findForMap(true, false);
		this.trendsFormulaMap = this.formulaService.findForMap(true, true);
		this.calculationMap = this.aggregationMethodService.findForMap(true);
	}
	
	private void loadKpiData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.kpi, new String[]{"oid"});
		DefaultResult<KpiVO> result = this.kpiService.findObjectByOid(this.kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.kpi = result.getValue();
		this.handlerSelectValueForEdit();
		this.handlerKpiOrgaAndEmplForEdit();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiId", this.kpi.getId());
		this.kpiAttac = this.kpiAttacService.findListVOByParams(paramMap);
		for (int i=0; this.kpiAttac!=null && i<this.kpiAttac.size(); i++) {
			KpiAttacVO attac = this.kpiAttac.get(i);
			attac.setShowName( "unknown-"+attac.getUploadOid() );
			DefaultResult<SysUploadVO> upResult = this.sysUploadService.findForNoByteContent(attac.getUploadOid());
			if (upResult.getValue()!=null) {
				attac.setShowName(upResult.getValue().getShowName());
			}
		}
	}
	
	private void handlerKpiOrgaAndEmplForEdit() throws ServiceException, Exception {
		List<String> appendOrgaOids = this.organizationService.findForAppendOrganizationOidsByKpiOrga(this.kpi.getId());
		List<String> appendOrgaNames = this.organizationService.findForAppendNames(appendOrgaOids);
		StringBuilder sb = new StringBuilder();
		for (int i=0; appendOrgaOids!=null && i<appendOrgaOids.size(); i++) {
			sb.append(appendOrgaOids.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendOrgaOids", sb.toString());
		sb.setLength(0);
		for (int i=0; appendOrgaNames!=null && i<appendOrgaNames.size(); i++) {
			sb.append(appendOrgaNames.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendOrgaNames", sb.toString());
		
		sb.setLength(0);
		List<String> appendEmplOids = this.employeeService.findForAppendEmployeeOidsByKpiEmpl(this.kpi.getId());
		List<String> appendEmplNames = this.employeeService.findForAppendNames(appendEmplOids);		
		for (int i=0; appendEmplOids!=null && i<appendEmplOids.size(); i++) {
			sb.append(appendEmplOids.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendEmplOids", sb.toString());
		sb.setLength(0);
		for (int i=0; appendEmplNames!=null && i<appendEmplNames.size(); i++) {
			sb.append(appendEmplNames.get(i)).append(Constants.ID_DELIMITER);
		}
		this.getFields().put("appendEmplNames", sb.toString());		
	}
	
	private void handlerSelectValueForEdit() throws ServiceException, Exception {
		ObjectiveVO objective = new ObjectiveVO();
		objective.setObjId(this.kpi.getObjId());
		DefaultResult<ObjectiveVO> objResult = this.objectiveService.findByUK(objective);
		if (objResult.getValue()==null) {
			throw new ServiceException( objResult.getSystemMessage().getValue() );
		}
		objective = objResult.getValue();
		
		PerspectiveVO perspective = new PerspectiveVO();
		perspective.setPerId( objective.getPerId() );
		DefaultResult<PerspectiveVO> perResult = this.perspectiveService.findByUK(perspective);
		if (perResult.getValue()==null) {
			throw new ServiceException( perResult.getSystemMessage().getValue() );
		}
		perspective = perResult.getValue();
		
		VisionVO vision = new VisionVO();
		vision.setVisId( perspective.getVisId() );
		DefaultResult<VisionVO> visResult = this.visionService.findForSimpleByVisId( vision.getVisId() );
		if (visResult.getValue()==null) {
			throw new ServiceException( visResult.getSystemMessage().getValue() );
		}
		vision = visResult.getValue();
		
//		FormulaVO formula = new FormulaVO();
//		formula.setForId( this.kpi.getForId() );
//		DefaultResult<FormulaVO> forResult = this.formulaService.findByUK(formula);
//		if (forResult.getValue()==null) {
//			throw new ServiceException( forResult.getSystemMessage().getValue() );
//		}
//		formula = forResult.getValue();
//		
//		FormulaVO trendsFormula = new FormulaVO();
//		trendsFormula.setForId( this.kpi.getTrendsForId() );
//		DefaultResult<FormulaVO> trendsForResult = this.formulaService.findByUK(trendsFormula);
//		if (trendsForResult.getValue()==null) {
//			throw new ServiceException( trendsForResult.getSystemMessage().getValue() );
//		}
//		trendsFormula = trendsForResult.getValue();		
		
		this.getFields().put("visionOid", vision.getOid());
		this.getFields().put("perspectiveOid", perspective.getOid());
		this.getFields().put("objectiveOid", objective.getOid());
		this.getFields().put("formulaOid", BscFormulaUtils.getFormulaById(this.kpi.getForId()).getOid() );
		this.getFields().put("trendsFormulaOid", BscFormulaUtils.getFormulaById(this.kpi.getTrendsForId()).getOid() );
		this.getFields().put("aggrMethodOid", AggregationMethodUtils.findSimpleById(this.kpi.getCal()).getOid() );
		
		this.perspectiveMap = this.perspectiveService.findForMapByVisionOid(vision.getOid(), true);
		this.objectiveMap = this.objectiveService.findForMapByPerspectiveOid(perspective.getOid(), true);
	}

	/**
	 * bsc.kpiManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0004Q")
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
	 * bsc.kpiCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0004A")
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
	 * bsc.kpiEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0004E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadKpiData();
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
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	public Map<String, String> getPerspectiveMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.perspectiveMap);
		return perspectiveMap;
	}

	public Map<String, String> getObjectiveMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.objectiveMap);
		return objectiveMap;
	}

	public Map<String, String> getManagementMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.managementMap);
		return managementMap;
	}

	public Map<String, String> getCalculationMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.calculationMap);
		return calculationMap;
	}

	public Map<String, String> getCompareTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.compareTypeMap);
		return compareTypeMap;
	}

	public Map<String, String> getDataTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.dataTypeMap);
		return dataTypeMap;
	}

	public Map<String, String> getFormulaMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.formulaMap);
		return formulaMap;
	}

	public Map<String, String> getTrendsFormulaMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.trendsFormulaMap);
		return trendsFormulaMap;
	}

	public void setTrendsFormulaMap(Map<String, String> trendsFormulaMap) {
		this.trendsFormulaMap = trendsFormulaMap;
	}

	public Map<String, String> getQuasiRangeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.quasiRangeMap);
		return quasiRangeMap;
	}

	public KpiVO getKpi() {
		return kpi;
	}
	
	public List<KpiAttacVO> getKpiAttac() {
		return kpiAttac;
	}
	
	public int getMaxAttachmentDocument() {
		return maxAttachmentDocument;
	}

}
