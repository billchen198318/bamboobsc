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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.model.ItemTargetOrMaximumAndMinimalValue;
import com.netsteadfast.greenstep.bsc.model.MonitorItemType;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.bsc.service.IKpiAttacService;
import com.netsteadfast.greenstep.bsc.service.IKpiEmplService;
import com.netsteadfast.greenstep.bsc.service.IKpiOrgaService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPdcaKpisService;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbKpiAttac;
import com.netsteadfast.greenstep.po.hbm.BbKpiEmpl;
import com.netsteadfast.greenstep.po.hbm.BbKpiOrga;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPdcaKpis;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiAttacVO;
import com.netsteadfast.greenstep.vo.KpiEmplVO;
import com.netsteadfast.greenstep.vo.KpiOrgaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaKpisVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;
import com.thoughtworks.xstream.XStream;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.KpiLogicService")
@WebService
@Path("/")
@Produces("application/json")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class KpiLogicServiceImpl extends BscBaseLogicService implements IKpiLogicService {
	protected Logger logger=Logger.getLogger(KpiLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private IFormulaService<FormulaVO, BbFormula, String> formulaService;
	private IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService;
	private IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	private IKpiAttacService<KpiAttacVO, BbKpiAttac, String> kpiAttacService;
	private IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	
	public KpiLogicServiceImpl() {
		super();
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

	public IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> getKpiOrgaService() {
		return kpiOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiOrgaService")
	@Required				
	public void setKpiOrgaService(
			IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService) {
		this.kpiOrgaService = kpiOrgaService;
	}

	public IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> getKpiEmplService() {
		return kpiEmplService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiEmplService")
	@Required			
	public void setKpiEmplService(
			IKpiEmplService<KpiEmplVO, BbKpiEmpl, String> kpiEmplService) {
		this.kpiEmplService = kpiEmplService;
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
	
	public IKpiAttacService<KpiAttacVO, BbKpiAttac, String> getKpiAttacService() {
		return kpiAttacService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiAttacService")
	@Required				
	public void setKpiAttacService(
			IKpiAttacService<KpiAttacVO, BbKpiAttac, String> kpiAttacService) {
		this.kpiAttacService = kpiAttacService;
	}

	public IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> getPdcaKpisService() {
		return pdcaKpisService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaKpisService")
	@Required		
	public void setPdcaKpisService(IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService) {
		this.pdcaKpisService = pdcaKpisService;
	}
	
	public IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> getMonitorItemScoreService() {
		return monitorItemScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.MonitorItemScoreService")
	@Required	
	public void setMonitorItemScoreService(
			IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService) {
		this.monitorItemScoreService = monitorItemScoreService;
	}	

	private FormulaVO fetchFormulaByOid(String formulaOid) throws ServiceException, Exception {
		FormulaVO formula = new FormulaVO();
		formula.setOid(formulaOid);
		DefaultResult<FormulaVO> forResult = this.formulaService.findObjectByOid(formula);
		if (forResult.getValue()==null) {
			throw new ServiceException( forResult.getSystemMessage().getValue() );
		}
		return forResult.getValue();		
	}
	
	private void handlerDataForCreateOrUpdate(KpiVO kpi, 
			String objectiveOid, String formulaOid, String aggrOid, String trendsFormulaOid) throws ServiceException, Exception {
		ObjectiveVO objective = new ObjectiveVO();
		objective.setOid(objectiveOid);
		DefaultResult<ObjectiveVO> objResult = this.objectiveService.findObjectByOid(objective);
		if (objResult.getValue()==null) {
			throw new ServiceException( objResult.getSystemMessage().getValue() );
		}
		objective = objResult.getValue();			
		kpi.setObjId( objective.getObjId() );
		kpi.setForId( this.fetchFormulaByOid(formulaOid).getForId() );
		kpi.setTrendsForId( this.fetchFormulaByOid(trendsFormulaOid).getForId() );
		kpi.setCal( AggregationMethodUtils.findSimpleByOid(aggrOid).getAggrId() );
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<KpiVO> create(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids, String trendsFormulaOid, List<String> attachment) throws ServiceException, Exception {
		if (null == kpi || super.isNoSelectId(objectiveOid) || super.isNoSelectId(formulaOid) 
				|| super.isNoSelectId(aggrOid) || super.isNoSelectId(trendsFormulaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.setStringValueMaxLength(kpi, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerDataForCreateOrUpdate(kpi, objectiveOid, formulaOid, aggrOid, trendsFormulaOid);
		kpi.setTarget( ItemTargetOrMaximumAndMinimalValue.get(kpi.getTarget()) );
		kpi.setMin( ItemTargetOrMaximumAndMinimalValue.get(kpi.getMin()) );
		kpi.setMax( ItemTargetOrMaximumAndMinimalValue.get(kpi.getMax()) );
		DefaultResult<KpiVO> result = this.kpiService.saveObject(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}				
		this.createKpiOrganization(result.getValue(), organizationOids); // create KPI's organization				
		this.createKpiEmployee(result.getValue(), employeeOids); // create KPI's owner
		this.createKpiAttachment(kpi, attachment); // create attachment
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<KpiVO> update(KpiVO kpi, String objectiveOid, String formulaOid, String aggrOid,
			List<String> organizationOids, List<String> employeeOids, String trendsFormulaOid, List<String> attachment) throws ServiceException, Exception {
		if (null == kpi || super.isBlank(kpi.getOid()) || super.isNoSelectId(objectiveOid) || super.isNoSelectId(formulaOid) 
				|| super.isNoSelectId(aggrOid) || super.isNoSelectId(trendsFormulaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<KpiVO> oldResult = this.kpiService.findObjectByOid(kpi);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiId", oldResult.getValue().getId());
		if (this.pdcaKpisService.countByParams(paramMap) > 0 && !YesNo.YES.equals(kpi.getActivate())) {
			throw new ServiceException( "Cannot disable activate options, because the KPI is used in!" );
		}		
		
		this.setStringValueMaxLength(kpi, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerDataForCreateOrUpdate(kpi, objectiveOid, formulaOid, aggrOid, trendsFormulaOid);
		kpi.setId( oldResult.getValue().getId() );		
		kpi.setTarget( ItemTargetOrMaximumAndMinimalValue.get(kpi.getTarget()) );
		kpi.setMin( ItemTargetOrMaximumAndMinimalValue.get(kpi.getMin()) );
		kpi.setMax( ItemTargetOrMaximumAndMinimalValue.get(kpi.getMax()) );		
		DefaultResult<KpiVO> result = this.kpiService.updateObject(kpi);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}				
		kpi = result.getValue();		
		this.deleteKpiOrganization(kpi); // delete KPI's organization
		this.deleteKpiEmployee(kpi); // delete KPI's owner
		this.deleteKpiAttachment(kpi); // delete attachment
		this.createKpiOrganization(result.getValue(), organizationOids); // create KPI's organization				
		this.createKpiEmployee(result.getValue(), employeeOids); // create KPI's owner
		this.createKpiAttachment(kpi, attachment); // create attachment
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(KpiVO kpi) throws ServiceException, Exception {
		if (null == kpi || super.isBlank(kpi.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<KpiVO> oldResult = this.kpiService.findObjectByOid(kpi);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiId", oldResult.getValue().getId());
		if (this.pdcaKpisService.countByParams(paramMap) > 0) {
			String msg = "PDCA Project used the KPI, " + SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE);
			throw new ServiceException( msg );
		}
		
		this.deleteKpiOrganization( oldResult.getValue() ); // delete KPI's organization
		this.deleteKpiEmployee( oldResult.getValue() ); // delete KPI's owner						
		this.deleteKpiAttachment( oldResult.getValue() ); // delete attachment
		this.measureDataService.deleteForKpiId( oldResult.getValue().getId() ); // delete measuer data
		this.monitorItemScoreService.deleteForTypeClass(MonitorItemType.KPI, oldResult.getValue().getId());
		return this.kpiService.deleteObject(kpi);		
	}	
	
	private void deleteKpiOrganization(KpiVO kpi) throws ServiceException, Exception {
		if (kpi==null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kpiId", kpi.getId());
		List<BbKpiOrga> searchList = this.kpiOrgaService.findListByParams(params);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbKpiOrga kpiOrga : searchList) {
			this.kpiOrgaService.delete(kpiOrga);
		}
	}
	
	private void deleteKpiEmployee(KpiVO kpi) throws ServiceException, Exception {
		if (kpi==null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kpiId", kpi.getId());
		List<BbKpiEmpl> searchList = this.kpiEmplService.findListByParams(params);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbKpiEmpl kpiEmpl : searchList) {
			this.kpiEmplService.delete(kpiEmpl);
		}		
	}
	
	private void deleteKpiAttachment(KpiVO kpi) throws ServiceException, Exception {
		if (kpi==null) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiId", kpi.getId());
		List<BbKpiAttac> searchList = this.kpiAttacService.findListByParams(paramMap);
		if (searchList==null || searchList.size() < 1) {
			return;
		}
		for (BbKpiAttac attac : searchList) {			
			//this.sysUploadService.updateTypeOnly(attac.getUploadOid(), UploadTypes.IS_TEMP); // 把UPLOAD狀態改為TMP
			UploadSupportUtils.updateType(attac.getUploadOid(), UploadTypes.IS_TEMP);
			this.kpiAttacService.delete(attac);
		}
	}
	
	private void createKpiOrganization(KpiVO kpi, List<String> organizationOids) throws ServiceException, Exception {
		if (kpi==null || organizationOids==null || organizationOids.size() < 1 ) {
			return;
		}
		for (String oid : organizationOids) {
			OrganizationVO organization = this.findOrganizationData(oid);
			KpiOrgaVO kpiOrga = new KpiOrgaVO();
			kpiOrga.setKpiId(kpi.getId());
			kpiOrga.setOrgId(organization.getOrgId());
			this.kpiOrgaService.saveObject(kpiOrga);
		}
	}
	
	private void createKpiEmployee(KpiVO kpi, List<String> employeeOids) throws ServiceException, Exception {
		if (kpi==null || employeeOids==null || employeeOids.size() < 1 ) {
			return;
		}
		for (String oid : employeeOids) {
			EmployeeVO employee = this.findEmployeeData(oid);
			KpiEmplVO kpiEmpl = new KpiEmplVO();
			kpiEmpl.setKpiId(kpi.getId());
			kpiEmpl.setEmpId(employee.getEmpId());
			this.kpiEmplService.saveObject(kpiEmpl);
		}		
	}
	
	private void createKpiAttachment(KpiVO kpi, List<String> attachment) throws ServiceException, Exception {
		if (kpi==null || attachment==null || attachment.size() < 1 ) {
			return;
		}
		for (String uploadOid : attachment) {
			SysUploadVO upload = this.findUploadDataForNoByteContent(uploadOid);
			if (!(upload.getSystem().equals(Constants.getSystem()) && upload.getType().equals(UploadTypes.IS_TEMP))) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			KpiAttacVO attac = new KpiAttacVO();
			attac.setKpiId(kpi.getId());
			attac.setUploadOid(uploadOid);
			attac.setViewMode( UploadSupportUtils.getViewMode(upload.getShowName()) );
			DefaultResult<KpiAttacVO> result = this.kpiAttacService.saveObject(attac);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
			//this.sysUploadService.updateTypeOnly(uploadOid, UploadTypes.IS_KPI_DOCUMENT);
			UploadSupportUtils.updateType(uploadOid, UploadTypes.IS_KPI_DOCUMENT);
		}
	}
	
	/**
	 * for TEST
	 * 這是測試 WS REST 用的  metod , 暴露 KPIs 主檔資料
	 * 
	 * rest address: http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/
	 * 
	 * json:
	 * http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/json
	 * 
	 * xml:
	 * http://127.0.0.1:8080/gsbsc-web/services/jaxrs/kpis/xml
	 * 
	 * @param format			example:	xml / json
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@WebMethod
	@GET
	@Path("/kpis/{format}")
	@Override
	public String findKpis(@WebParam(name="format") @PathParam("format") String format) throws ServiceException, Exception {				
		List<KpiVO> kpis = null;
		try {
			kpis = this.kpiService.findListVOByParams( null );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null == kpis) {
				kpis = new ArrayList<KpiVO>();
			}
		}
		if ("json".equals(format)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("KPIS", kpis);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString( paramMap );
		}		
		XStream xstream = new XStream();
		//xstream.registerConverter( new DateConverter() );
		xstream.setMode(XStream.NO_REFERENCES);		
		xstream.alias("KPIS", List.class);
		xstream.alias("KPI", KpiVO.class);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(kpis);
	}

}
