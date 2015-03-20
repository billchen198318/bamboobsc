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
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysTemplateParamDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysTemplateParam;
import com.netsteadfast.greenstep.service.ISysTemplateParamService;
import com.netsteadfast.greenstep.vo.SysTemplateParamVO;

@Service("core.service.SysTemplateParamService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysTemplateParamServiceImpl extends BaseService<SysTemplateParamVO, TbSysTemplateParam, String> implements ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> {
	protected Logger logger=Logger.getLogger(SysTemplateParamServiceImpl.class);
	private ISysTemplateParamDAO<TbSysTemplateParam, String> sysTemplateParamDAO;
	
	public SysTemplateParamServiceImpl() {
		super();
	}

	public ISysTemplateParamDAO<TbSysTemplateParam, String> getSysTemplateParamDAO() {
		return sysTemplateParamDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysTemplateParamDAO")
	@Required		
	public void setSysTemplateParamDAO(
			ISysTemplateParamDAO<TbSysTemplateParam, String> sysTemplateParamDAO) {
		this.sysTemplateParamDAO = sysTemplateParamDAO;
	}

	@Override
	protected IBaseDAO<TbSysTemplateParam, String> getBaseDataAccessObject() {
		return sysTemplateParamDAO;
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
		String templateOid = searchValue.getParameter().get("templateOid");	
		params.put("templateOid", templateOid);
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysTemplateParamVO(m.oid, m.templateId, m.isTitle, m.templateVar, m.objectVar) ");
		}
		hqlSb.append("FROM TbSysTemplateParam m WHERE 1=1 ");
		hqlSb.append("and m.templateId IN ( SELECT t.templateId FROM TbSysTemplate t WHERE t.oid = :templateOid ) ");		
		return hqlSb.toString();
	}			
	
	@Override
	public QueryResult<List<SysTemplateParamVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysTemplateParamVO>> result=this.sysTemplateParamDAO.findResult2(
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_SELECT, params), 
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_COUNT, params), 
				params, 
				offset, 
				limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

}
