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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeOrgaService;
import com.netsteadfast.greenstep.bsc.service.IKpiOrgaService;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationParService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IPdcaOrgaService;
import com.netsteadfast.greenstep.bsc.service.IReportRoleViewService;
import com.netsteadfast.greenstep.bsc.service.ISwotService;
import com.netsteadfast.greenstep.bsc.service.ITsaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.logic.IOrganizationLogicService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbEmployeeOrga;
import com.netsteadfast.greenstep.po.hbm.BbKpiOrga;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbOrganizationPar;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbPdcaOrga;
import com.netsteadfast.greenstep.po.hbm.BbReportRoleView;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.po.hbm.BbTsaMeasureFreq;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.EmployeeOrgaVO;
import com.netsteadfast.greenstep.vo.KpiOrgaVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.OrganizationParVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaOrgaVO;
import com.netsteadfast.greenstep.vo.ReportRoleViewVO;
import com.netsteadfast.greenstep.vo.SwotVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.OrganizationLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class OrganizationLogicServiceImpl extends BscBaseLogicService implements IOrganizationLogicService {
	protected Logger logger=Logger.getLogger(OrganizationLogicServiceImpl.class);
	private final static int MAX_DESCRIPTION_LENGTH = 500;
	private final static String TREE_ICON_ID = "STOCK_HOME";
	private IOrganizationParService<OrganizationParVO, BbOrganizationPar, String> organizationParService;
	private IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService; 
	private IKpiOrgaService<KpiOrgaVO, BbKpiOrga, String> kpiOrgaService;
	private ISwotService<SwotVO, BbSwot, String> swotService;
	private IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService;
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;
	private IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> pdcaOrgaService;
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService;
	
	public OrganizationLogicServiceImpl() {
		super();
	}

	public IOrganizationParService<OrganizationParVO, BbOrganizationPar, String> getOrganizationParService() {
		return organizationParService;
	}

	@Autowired
	@Resource(name="bsc.service.OrganizationParService")
	@Required		
	public void setOrganizationParService(
			IOrganizationParService<OrganizationParVO, BbOrganizationPar, String> organizationParService) {
		this.organizationParService = organizationParService;
	}
	
	public IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> getEmployeeOrgaService() {
		return employeeOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.EmployeeOrgaService")
	@Required		
	public void setEmployeeOrgaService(
			IEmployeeOrgaService<EmployeeOrgaVO, BbEmployeeOrga, String> employeeOrgaService) {
		this.employeeOrgaService = employeeOrgaService;
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
	
	public ISwotService<SwotVO, BbSwot, String> getSwotService() {
		return swotService;
	}

	@Autowired
	@Resource(name="bsc.service.SwotService")
	@Required		
	public void setSwotService(ISwotService<SwotVO, BbSwot, String> swotService) {
		this.swotService = swotService;
	}	
	
	public IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> getReportRoleViewService() {
		return reportRoleViewService;
	}

	@Autowired
	@Resource(name="bsc.service.ReportRoleViewService")
	@Required		
	public void setReportRoleViewService(
			IReportRoleViewService<ReportRoleViewVO, BbReportRoleView, String> reportRoleViewService) {
		this.reportRoleViewService = reportRoleViewService;
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
	
	public IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> getPdcaOrgaService() {
		return pdcaOrgaService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaOrgaService")
	@Required	
	public void setPdcaOrgaService(IPdcaOrgaService<PdcaOrgaVO, BbPdcaOrga, String> pdcaOrgaService) {
		this.pdcaOrgaService = pdcaOrgaService;
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
	
	public IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> getPdcaMeasureFreqService() {
		return pdcaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaMeasureFreqService")
	@Required			
	public void setPdcaMeasureFreqService(
			IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService) {
		this.pdcaMeasureFreqService = pdcaMeasureFreqService;
	}	
	
	public ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> getTsaMeasureFreqService() {
		return tsaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.TsaMeasureFreqService")
	@Required		
	public void setTsaMeasureFreqService(
			ITsaMeasureFreqService<TsaMeasureFreqVO, BbTsaMeasureFreq, String> tsaMeasureFreqService) {
		this.tsaMeasureFreqService = tsaMeasureFreqService;
	}	
	
	private void handlerLongitudeAndLatitude(OrganizationVO organization) {
		if ( !NumberUtils.isCreatable(organization.getLat()) ) {
			organization.setLat( (String)Constants.getSettingsMap().get("googleMap.defaultLat") );
		}
		if ( !NumberUtils.isCreatable(organization.getLng()) ) {
			organization.setLng( (String)Constants.getSettingsMap().get("googleMap.defaultLng") );
		}				
	}
	
	private void checkOrganizationIdIsZero(OrganizationVO organization) throws ServiceException, Exception {
		if (super.isBlank(organization.getOrgId()) || BscConstants.ORGANIZATION_ZERO_ID.equals(organization.getOrgId()) ) {
			throw new ServiceException("Please change another Id! does not accept id is `" 
					+ super.defaultString(organization.getOrgId()) + "`");
		}
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<OrganizationVO> create(OrganizationVO organization) throws ServiceException, Exception {
		if (organization==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.checkOrganizationIdIsZero(organization);
		this.setStringValueMaxLength(organization, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerLongitudeAndLatitude(organization);
		this.createParent(organization, BscConstants.ORGANIZATION_ZERO_ID);
		return this.getOrganizationService().saveObject(organization);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<OrganizationVO> update(OrganizationVO organization) throws ServiceException, Exception {
		if (organization==null || super.isBlank(organization.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		this.checkOrganizationIdIsZero(organization);
		OrganizationVO dbOrganization = this.findOrganizationData(organization.getOid());
		organization.setOrgId( dbOrganization.getOrgId() );
		this.setStringValueMaxLength(organization, "description", MAX_DESCRIPTION_LENGTH);
		this.handlerLongitudeAndLatitude(organization);
		return this.getOrganizationService().updateObject(organization);
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> delete(OrganizationVO organization) throws ServiceException, Exception {
		if (organization==null || super.isBlank(organization.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		organization = this.findOrganizationData(organization.getOid());
		if (this.foundChild(organization)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", organization.getOrgId());
		if (this.employeeOrgaService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.kpiOrgaService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.pdcaOrgaService.countByParams(params) > 0 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.pdcaMeasureFreqService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.tsaMeasureFreqService.countByParams(params) > 0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		this.deleteParent(organization);
		this.swotService.deleteForOrgId(organization.getOrgId());
		
		// delete BB_REPORT_ROLE_VIEW
		params.clear();
		params.put("idName", organization.getOrgId());
		List<BbReportRoleView> reportRoleViews = this.reportRoleViewService.findListByParams(params);
		for (int i=0; reportRoleViews!=null && i<reportRoleViews.size(); i++) {
			BbReportRoleView reportRoleView = reportRoleViews.get( i );
			this.reportRoleViewService.delete(reportRoleView);
		}		
		
		// delete from BB_MEASURE_DATA where ORG_ID = :orgId
		this.measureDataService.deleteForOrgId( organization.getOrgId() );
		
		this.monitorItemScoreService.deleteForOrgId( organization.getOrgId() );
		
		return this.getOrganizationService().deleteObject(organization);
	}	
	
	/**
	 * 為了組織架構 tree 的呈現用, json 資料
	 * 
	 * 不包含
	 * {
	 * 		"identifier":"id",
	 * 		"label":"name",
	 * 
	 * ==================================================
	 * 只包含 items 的資料內容
	 * ==================================================		
	 * 
	 * 		"items":[
	 * 			...............
	 * 		]
	 * ==================================================
	 * 
	 * }	
	 * 
	 * @param basePath
	 * @param checkBox		是否打開checkBox
	 * @param appendId		已被打勾的部門OID組成的字串, 如 1b2ac208-345c-4f93-92c5-4b26aead31d2;3ba52439-6756-45e8-8269-ae7b4fb6a3dc
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public List<Map<String, Object>> getTreeData(String basePath, boolean checkBox, String appendId) throws ServiceException, Exception {
		List<Map<String, Object>> items=new LinkedList<Map<String, Object>>();
		List<OrganizationVO> orgList = this.getOrganizationService().findForJoinParent(); 
		if (orgList==null || orgList.size()<1 ) {
			return items;
		}
		for (OrganizationVO org : orgList) {
			// 先放沒有父親的ORG-ID
			if (!(super.isBlank(org.getParId()) || BscConstants.ORGANIZATION_ZERO_ID.equals(org.getParId()) ) ) {
				continue;
			}
			Map<String, Object> parentDataMap=new LinkedHashMap<String, Object>();
			parentDataMap.put("type", "parent");
			parentDataMap.put("name", ( checkBox ? getCheckBoxHtmlContent(org, appendId) : "" ) + IconUtils.getMenuIcon(basePath, TREE_ICON_ID) + StringEscapeUtils.escapeHtml4(org.getName()) );
			parentDataMap.put("id", org.getOid());
			parentDataMap.put("orgId", org.getOrgId());
			items.add(parentDataMap);			
		}
		// 再開始放孩子
		for (int ix=0; ix<items.size(); ix++) {
			Map<String, Object> parentDataMap=items.get(ix);
			String orgId=(String)parentDataMap.get("orgId");
			this.getTreeData(basePath, checkBox, appendId, parentDataMap, orgList, orgId);
		}
		return items;
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> updateParent(OrganizationVO organization, String parentOid) throws ServiceException, Exception {
		if (organization==null || super.isBlank(organization.getOid()) || super.isBlank(parentOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(Boolean.FALSE);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)) );
		organization = this.findOrganizationData(organization.getOid());
		this.deleteParent(organization);
		if ("root".equals(parentOid) || "r".equals(parentOid)) {
			this.createParent(organization, BscConstants.ORGANIZATION_ZERO_ID);
		} else {
			OrganizationVO newParOrganization = this.findOrganizationData(parentOid);
			// 找當前部門的子部門中的資料, 不因該存在要update的新父部門
			if ( this.foundChild(organization.getOrgId(), newParOrganization.getOrgId()) ) {
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			this.createParent(organization, newParOrganization.getOrgId());			
		}
		result.setValue(Boolean.TRUE);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) );
		return result;
	}	
	
	/**
	 * 為了組織架構 tree 的呈現用, json 資料
	 * 產生 MAP 與 LIST
	 * 
	 * @param putObject
	 * @param searchList
	 * @param parentOrgId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getTreeData(
			String basePath, boolean checkBox, String appendId,
			Map<String, Object> putObject, List<OrganizationVO> searchList, String parentOrgId) throws Exception {
		List<String> childList = new LinkedList<String>();
		this.getChildOrgIdLevelOne(searchList, parentOrgId, childList);
		if (childList.size()<1) {
			return;
		}
		for (String childOrgId : childList) {
			OrganizationVO organization = this.getOrganizationFromSearchList(searchList, childOrgId, false);
			OrganizationVO childOrganization = this.getOrganizationFromSearchList(searchList, childOrgId, true);
			if (organization==null) {
				continue;
			}
			Map<String, Object> thePutObject=null;
			List<Map<String, Object>> childrenList = (List<Map<String, Object>>)putObject.get("children");
			if (childrenList==null) {
				childrenList=new LinkedList<Map<String, Object>>();
			}
			Map<String, Object> nodeMap=new LinkedHashMap<String, Object>();
			nodeMap.put("id", organization.getOid());
			nodeMap.put("name", ( checkBox ? getCheckBoxHtmlContent(organization, appendId) : "" ) + IconUtils.getMenuIcon(basePath, TREE_ICON_ID) + StringEscapeUtils.escapeHtml4(organization.getName()) );	
			nodeMap.put("orgId", organization.getOrgId() );
			childrenList.add(nodeMap);
			putObject.put("children", childrenList);
			if (childOrganization!=null) {					
				thePutObject=nodeMap;
			} else {
				nodeMap.put("type", "Leaf");
				thePutObject=putObject;
			}
			if (childOrganization!=null) {
				this.getTreeData(basePath, checkBox, appendId, thePutObject, searchList, childOrgId);
			}			
		}	
	}
	
	/**
	 * 為了組織架構 tree 的呈現用, json 資料
	 * 自己下1層的子部門 , 不包含下2層或之後的部門
	 * 
	 * @param searchList
	 * @param parentOrgId
	 * @param childList
	 * @return
	 * @throws Exception
	 */
	private List<String> getChildOrgIdLevelOne(List<OrganizationVO> searchList, String parentOrgId, List<String> childList) throws Exception {
		if (childList==null) {
			childList=new LinkedList<String>();
		}		 
		for (OrganizationVO org : searchList) {
			if (parentOrgId.equals(org.getParId())) {
				childList.add(org.getOrgId());
			} 
		}
		return childList;
	}
	
	/**
	 * 為了組織架構 tree 的呈現用, json 資料
	 * 傳回部門treeVO
	 * 
	 * @param searchList
	 * @param orgId
	 * @param isChild
	 * @return
	 * @throws Exception
	 */
	private OrganizationVO getOrganizationFromSearchList(List<OrganizationVO> searchList, String orgId, boolean isChild) throws Exception {
		for (OrganizationVO org : searchList) {
			if (!isChild) {
				if (org.getOrgId().equals(orgId)) {
					return org;
				}				
			} else {
				if (org.getParId().equals(orgId)) {
					return org;
				}					
			}
		}
		return null;
	}	
	
	private void createParent(OrganizationVO organization, String parId) throws ServiceException, Exception {
		if (null == organization || super.isBlank(organization.getOrgId()) || super.isBlank(parId)) {
			return;
		}		
		OrganizationParVO par = new OrganizationParVO();
		par.setOrgId(organization.getOrgId());
		par.setParId(parId);
		this.organizationParService.saveObject(par);
	}
	
	private void deleteParent(OrganizationVO organization) throws ServiceException, Exception {
		if (null == organization || super.isBlank(organization.getOrgId())) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", organization.getOrgId());
		List<BbOrganizationPar> parList = this.organizationParService.findListByParams(params);
		if (null == parList) {
			return;
		}
		for (BbOrganizationPar par : parList) {
			organizationParService.delete(par);
		}
	}
	
	private boolean foundChild(OrganizationVO organization) throws ServiceException, Exception {
		if (null == organization || super.isBlank(organization.getOrgId())) {
			return false;
		}		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parId", organization.getOrgId());	
		if (this.organizationParService.countByParams(params) > 0 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 找 parentId 之下的子部門資料的 ORG_ID 是否有與 checkOrgId 一樣的 
	 * 
	 * @param parentId
	 * @param checkOrgId
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	private boolean foundChild(String parentId, String checkOrgId) throws ServiceException, Exception {
		List<OrganizationVO> treeList = this.getOrganizationService().findForJoinParent();
		if (treeList==null || treeList.size() <1 ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		boolean f = false;
		List<OrganizationVO> childList=new LinkedList<OrganizationVO>();		
		this.getChild(parentId, treeList, childList);
		for (int ix=0; childList!=null && ix<childList.size(); ix++) {		
			if (childList.get(ix).getOrgId().equals(checkOrgId)) {
				f = true;
			}
		}
		return f;
	}
	
	/**
	 * 去找查出的 BB_ORGANIZATION inner join BB_ORGANIZATION_PAR 資料tree 中的父部門為 parentId 的資料都放入 put 中
	 * 等於找出 parentId 的所有子部門資料
	 * 
	 * @param parentId 		要找的 ORG_ID
	 * @param tree			當前的 BB_ORGANIZATION inner join BB_ORGANIZATION_PAR 資料
	 * @param put			要放入 parentId 的相關子部門
	 * @throws Exception
	 */
	private void getChild(String parentId, List<OrganizationVO> tree, List<OrganizationVO> put) throws Exception {
		if (put==null || tree==null) {
			return;
		}		
		if (StringUtils.isBlank(parentId) || BscConstants.ORGANIZATION_ZERO_ID.equals(parentId) ) {
			return;
		}		
		for (OrganizationVO org : tree) {
			if (org.getParId().equals(parentId)) {
				put.add(org);
				this.getChild(org.getOrgId(), tree, put);
			}
		}
	}	
	
	private String getCheckBoxHtmlContent(OrganizationVO organization, String appendId) throws Exception {
		String content = "";
		if (organization==null || super.isBlank(organization.getOid())) {
			return content;
		}
		String idName = BscConstants.ORGA_SELECT_CHECKBOX_ID_DELIMITER + organization.getOid();
		//String click = StringUtils.replaceOnce(BscConstants.ORGA_SELECT_CHECKBOX_FN, "${checkBoxId}", idName);
		//click = StringUtils.replaceOnce(click, "${oid}", organization.getOid());		
		content += "<input type='checkbox' name='" + idName + "' id='" + idName + "' ";
		//content += " onclick='" + click + "' ";
		if (appendId.indexOf(organization.getOid()+Constants.ID_DELIMITER) > -1 ) {
			content += " checked ";
		}		
		content += " onclick='return false;' disabled />";
		return content;
	}
	
	/**
	 * 這個 Method 的 ServiceMethodAuthority 權限給查詢狀態
	 * 這裡的 basePath 只是要取 getTreeData 時參數要用, 再這是沒有用處的
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<String> createOrgChartData(String basePath, OrganizationVO currentOrganization) throws ServiceException, Exception {
		if (null != currentOrganization && !super.isBlank(currentOrganization.getOid())) {
			currentOrganization = this.findOrganizationData( currentOrganization.getOid() );
		}
		List<Map<String, Object>> treeMap = this.getTreeData(basePath, false, "");
		if (null == treeMap || treeMap.size() < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		this.resetTreeMapContentForOrgChartData(treeMap, currentOrganization);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("name", "Organization / Department hierarchy");
		rootMap.put("title", "hierarchy structure");
		rootMap.put("children", treeMap);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(rootMap);		
		String uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");			
		
		DefaultResult<String> result = new DefaultResult<String>();
		result.setValue(uploadOid);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS)) );
		return result;
	}
	
	/**
	 * 這個 Method 的 ServiceMethodAuthority 權限給查詢狀態
	 * 這裡的 basePath 只是要取 getTreeData 時參數要用, 再這是沒有用處的
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})	
	@Override
	public DefaultResult<Map<String, Object>> getOrgChartData(String basePath, OrganizationVO currentOrganization) throws ServiceException, Exception {
		if (null != currentOrganization && !super.isBlank(currentOrganization.getOid())) {
			currentOrganization = this.findOrganizationData( currentOrganization.getOid() );
		}
		List<Map<String, Object>> treeMap = this.getTreeData(basePath, false, "");
		if (null == treeMap || treeMap.size() < 1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		this.resetTreeMapContentForOrgChartData(treeMap, currentOrganization);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("name", "Organization / Department hierarchy");
		rootMap.put("title", "hierarchy structure");
		rootMap.put("children", treeMap);
		
		DefaultResult<Map<String, Object>> result = new DefaultResult<Map<String, Object>>();
		result.setValue( rootMap );
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.INSERT_SUCCESS)) );
		return result;
	}		
	
	@SuppressWarnings("unchecked")
	private void resetTreeMapContentForOrgChartData(List<Map<String, Object>> childMapList, OrganizationVO currentOrganization) throws Exception {
		for (Map<String, Object> nodeMap : childMapList) {
			String nodeOrganizationOid = String.valueOf( nodeMap.get("id") );
			
			// 去除 OrgChart 不需要的資料
			nodeMap.remove("type");
			nodeMap.remove("id");
			nodeMap.remove("name");
			nodeMap.remove("orgId");
			
			OrganizationVO nodeOrganization = this.findOrganizationData(nodeOrganizationOid);
			
			// OrgChart 需要的資料, nodeMap 需要填入 name 與 title
			if (currentOrganization != null && !super.isBlank(currentOrganization.getOid()) 
					&& currentOrganization.getOid().equals(nodeOrganizationOid)) { // 有帶入當前部門(組織)來區別顏色
				nodeMap.put("name", "<font color='#8A0808'>" + nodeOrganization.getOrgId() + "</font>" );
				nodeMap.put("title", "<font color='#8A0808'>" + nodeOrganization.getName() + "</font>" );				
			} else {
				nodeMap.put("name", nodeOrganization.getOrgId() );
				nodeMap.put("title", nodeOrganization.getName() );				
			}
			
			if (nodeMap.get("children") != null && (nodeMap.get("children") instanceof List<?>)) { // 還有孩子項目資料
				this.resetTreeMapContentForOrgChartData( (List<Map<String, Object>>) nodeMap.get("children"), currentOrganization );
			}
		}
	}
	
}
