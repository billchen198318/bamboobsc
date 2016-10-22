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
package com.netsteadfast.greenstep.bsc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IOrganizationDAO;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@Service("bsc.service.OrganizationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class OrganizationServiceImpl extends BaseService<OrganizationVO, BbOrganization, String> implements IOrganizationService<OrganizationVO, BbOrganization, String> {
	protected Logger logger=Logger.getLogger(OrganizationServiceImpl.class);
	private IOrganizationDAO<BbOrganization, String> organizationDAO;
	
	public OrganizationServiceImpl() {
		super();
	}

	public IOrganizationDAO<BbOrganization, String> getOrganizationDAO() {
		return organizationDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.OrganizationDAO")
	@Required		
	public void setOrganizationDAO(
			IOrganizationDAO<BbOrganization, String> organizationDAO) {
		this.organizationDAO = organizationDAO;
	}

	@Override
	protected IBaseDAO<BbOrganization, String> getBaseDataAccessObject() {
		return organizationDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public List<OrganizationVO> findForJoinParent() throws ServiceException, Exception {
		return this.organizationDAO.findForJoinParent();
	}

	@Override
	public List<String> findForAppendNames(List<String> oids) throws ServiceException, Exception {
		if (oids==null || oids.size()<1) {
			return new ArrayList<String>();
		}
		return this.organizationDAO.findForAppendNames(oids);
	}

	@Override
	public List<String> findForAppendOrganizationOids(String empId) throws ServiceException, Exception {
		if (StringUtils.isBlank(empId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.organizationDAO.findForAppendOrganizationOids(empId);
	}

	@Override
	public List<String> findForAppendOrganizationOidsByKpiOrga(String kpiId) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.organizationDAO.findForAppendOrganizationOidsByKpiOrga(kpiId);
	}

	@Override
	public DefaultResult<List<BbOrganization>> findForInKpiOrga(String kpiId) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<List<BbOrganization>> result = new DefaultResult<List<BbOrganization>>();
		List<BbOrganization> searchList = this.organizationDAO.findForInKpiOrga(kpiId);
		if (searchList!=null && searchList.size() > 0 ) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("orgId", "asc");
		List<BbOrganization> organizationList = this.findListByParams(null, null, orderParams);
		for (BbOrganization organization : organizationList) {
			dataMap.put(organization.getOid(), organization.getName());
		}
		return dataMap;
	}

	@Override
	public List<String> findForAppendOrganizationOidsByReportRoleViewOrga(String roleId) throws ServiceException, Exception {
		if (StringUtils.isBlank(roleId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.organizationDAO.findForAppendOrganizationOidsByReportRoleViewOrga(roleId);		
	}

	@Override
	public List<String> findForAppendOrganizationOidsByPdcaOrga(String pdcaOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.organizationDAO.findForAppendOrganizationOidsByPdcaOrga(pdcaOid);
	}

}
