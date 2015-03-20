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
package com.netsteadfast.greenstep.service.impl;

import java.util.LinkedHashMap;
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

import com.netsteadfast.greenstep.base.Constants;
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
import com.netsteadfast.greenstep.dao.ISysJreportDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@Service("core.service.SysJreportService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysJreportServiceImpl extends BaseService<SysJreportVO, TbSysJreport, String> implements ISysJreportService<SysJreportVO, TbSysJreport, String> {
	protected Logger logger=Logger.getLogger(SysJreportServiceImpl.class);
	private ISysJreportDAO<TbSysJreport, String> sysJreportDAO;
	
	public SysJreportServiceImpl() {
		super();
	}

	public ISysJreportDAO<TbSysJreport, String> getSysJreportDAO() {
		return sysJreportDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysJreportDAO")
	@Required		
	public void setSysJreportDAO(
			ISysJreportDAO<TbSysJreport, String> sysJreportDAO) {
		this.sysJreportDAO = sysJreportDAO;
	}

	@Override
	protected IBaseDAO<TbSysJreport, String> getBaseDataAccessObject() {
		return sysJreportDAO;
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
		Map<String, Object> params=new LinkedHashMap<String, Object>();
		String reportId = searchValue.getParameter().get("reportId");
		if (!StringUtils.isBlank(reportId)) {
			params.put("reportId", reportId);
		}	
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysJreportVO(m.oid, m.reportId, m.file, m.isCompile, m.description) ");
		}
		hqlSb.append("FROM TbSysJreport m WHERE 1=1 ");
		if (params.get("reportId")!=null) {
			hqlSb.append("  and m.reportId = :reportId ");
		}		
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.reportId ASC");
		}		
		return hqlSb.toString();
	}		

	@Override
	public QueryResult<List<SysJreportVO>> findGridResult(SearchValue searchValue, 
			PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysJreportVO>> result=this.sysJreportDAO.findResult2(
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_SELECT, params), 
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_COUNT, params), 
				params, 
				offset, 
				limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public DefaultResult<SysJreportVO> findForSimple(String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysJreportVO searchObj = this.sysJreportDAO.findForSimple(oid);
		DefaultResult<SysJreportVO> result = new DefaultResult<SysJreportVO>();
		if (null!=searchObj && !StringUtils.isBlank(searchObj.getOid())) {
			result.setValue(searchObj);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST)) );
		}
		return result;
	}

	@Override
	public DefaultResult<SysJreportVO> findForSimpleByReportId(String reportId) throws ServiceException, Exception {
		if (StringUtils.isBlank(reportId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysJreportVO searchObj = this.sysJreportDAO.findForSimpleByReportId(reportId);
		DefaultResult<SysJreportVO> result = new DefaultResult<SysJreportVO>();
		if (null!=searchObj && !StringUtils.isBlank(searchObj.getOid())) {
			result.setValue(searchObj);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST)) );
		}
		return result;
	}

}
