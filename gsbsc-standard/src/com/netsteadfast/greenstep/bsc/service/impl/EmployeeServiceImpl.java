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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IEmployeeDAO;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@Service("bsc.service.EmployeeService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class EmployeeServiceImpl extends BaseService<EmployeeVO, BbEmployee, String> implements IEmployeeService<EmployeeVO, BbEmployee, String> {
	protected Logger logger=Logger.getLogger(EmployeeServiceImpl.class);
	private IEmployeeDAO<BbEmployee, String> employeeDAO;
	
	public EmployeeServiceImpl() {
		super();
	}

	public IEmployeeDAO<BbEmployee, String> getEmployeeDAO() {
		return employeeDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.EmployeeDAO")
	@Required		
	public void setEmployeeDAO(
			IEmployeeDAO<BbEmployee, String> employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	protected IBaseDAO<BbEmployee, String> getBaseDataAccessObject() {
		return employeeDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.fullEquals4TextField("empId")
				.containingLike("fullName")
				.getValue();
	}	

	@Override
	public QueryResult<List<EmployeeVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<EmployeeVO>> result=this.employeeDAO.findPageQueryResultByQueryName(
				"findEmployeePageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public List<String> findForAppendNames(List<String> oids) throws ServiceException, Exception {
		if (oids==null || oids.size()<1) {
			return new ArrayList<String>();
		}
		return this.employeeDAO.findForAppendNames(oids);
	}

	@Override
	public List<String> findForAppendEmployeeOidsByKpiEmpl(String kpiId) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.employeeDAO.findForAppendEmployeeOidsByKpiEmpl(kpiId);
	}

	@Override
	public DefaultResult<List<BbEmployee>> findForInKpiEmpl(String kpiId) throws ServiceException, Exception {
		if (StringUtils.isBlank(kpiId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<List<BbEmployee>> result = new DefaultResult<List<BbEmployee>>();
		List<BbEmployee> searchList = this.employeeDAO.findForInKpiEmpl(kpiId);
		if (searchList!=null && searchList.size() >0 ) {
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
		orderParams.put("empId", "asc");
		List<BbEmployee> searchList = this.findListByParams(null, null, orderParams);
		for (BbEmployee employee : searchList) {
			dataMap.put(employee.getOid(), employee.getFullName());
		}
		return dataMap;
	}

	@Override
	public List<String> findForAppendEmployeeOidsByReportRoleViewEmpl(String roleId) throws ServiceException, Exception {
		if (StringUtils.isBlank(roleId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}				
		return this.employeeDAO.findForAppendEmployeeOidsByReportRoleViewEmpl(roleId);
	}

	@Override
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(String projectOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(projectOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.employeeDAO.findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(projectOid);
	}

	@Override
	public List<String> findForAppendEmployeeOidsByDegreeFeedbackProjectRater(String projectOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(projectOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		return this.employeeDAO.findForAppendEmployeeOidsByDegreeFeedbackProjectRater(projectOid);
	}

	@Override
	public BbEmployee findByAccountId(String accountId) throws ServiceException, Exception {
		if (StringUtils.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("account", accountId);
		List<BbEmployee> searchList = this.findListByParams(paramMap);
		if (searchList==null || searchList.size()<1) {
			return null;
		}
		if (searchList.size()!=1) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}		
		return searchList.get(0);
	}

	@Override
	public BbEmployee findByAccountOid(String accountOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(accountOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.employeeDAO.findByAccountOid(accountOid);
	}

	@Override
	public Map<String, String> findForMapByDegreeFeedbackProjectOwner(boolean pleaseSelect, String projectOid) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<String> oids = this.findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(projectOid);		
		for (String oid : oids ) {
			BbEmployee employee = this.findByPKng(oid);
			if (null == employee || StringUtils.isBlank(employee.getOid()) ) {
				logger.warn( "Lost bb_employee data OID: " + oid );
				continue;
			}
			dataMap.put( employee.getOid(), employee.getEmpId() + " - " + employee.getFullName() );
		}		
		return dataMap;
	}

	@Override
	public List<String> findForAppendEmployeeOidsByPdcaOwner(String pdcaOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		return this.employeeDAO.findForAppendEmployeeOidsByPdcaOwner(pdcaOid);
	}

	@Override
	public List<String> findForAppendEmployeeOidsByPdcaItemOwner(String pdcaOid, String itemOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(pdcaOid) || StringUtils.isBlank(itemOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}			
		return this.employeeDAO.findForAppendEmployeeOidsByPdcaItemOwner(pdcaOid, itemOid);
	}

	@Override
	public List<EmployeeVO> findForJoinHier() throws ServiceException, Exception {
		return this.employeeDAO.findForJoinHier();
	}
	
}
