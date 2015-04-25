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
import org.springframework.context.annotation.Scope;
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
import com.netsteadfast.greenstep.dao.ISysFormTemplateDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;

@Service("core.service.SysFormTemplateService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysFormTemplateServiceImpl extends BaseService<SysFormTemplateVO, TbSysFormTemplate, String> implements ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> {
	protected Logger logger=Logger.getLogger(SysFormTemplateServiceImpl.class);
	private ISysFormTemplateDAO<TbSysFormTemplate, String> sysFormTemplateDAO;
	
	public SysFormTemplateServiceImpl() {
		super();
	}

	public ISysFormTemplateDAO<TbSysFormTemplate, String> getSysFormTemplateDAO() {
		return sysFormTemplateDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysFormTemplateDAO")
	@Required		
	public void setSysFormTemplateDAO(
			ISysFormTemplateDAO<TbSysFormTemplate, String> sysFormTemplateDAO) {
		this.sysFormTemplateDAO = sysFormTemplateDAO;
	}

	@Override
	protected IBaseDAO<TbSysFormTemplate, String> getBaseDataAccessObject() {
		return sysFormTemplateDAO;
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
		String templateId = searchValue.getParameter().get("tplId");
		String name = searchValue.getParameter().get("name");		
		if (!StringUtils.isBlank(templateId)) {
			params.put("tplId", templateId);
		}
		if (!StringUtils.isBlank(name)) {
			params.put("name", "%"+name+"%");
		}			
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysFormTemplateVO(m.oid, m.tplId, m.name, m.fileName, m.description) ");
		}
		hqlSb.append("FROM TbSysFormTemplate m WHERE 1=1 ");
		if (params.get("tplId")!=null) {
			hqlSb.append("  and m.tplId = :tplId ");
		}		
		if (params.get("name")!=null) {
			hqlSb.append("  and m.name LIKE :name ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.tplId ASC");
		}		
		return hqlSb.toString();
	}		

	@Override
	public QueryResult<List<SysFormTemplateVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysFormTemplateVO>> result=this.sysFormTemplateDAO.findResult2(
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
	public Map<String, String> findForAllMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<SysFormTemplateVO> searchList = this.sysFormTemplateDAO.findAllForSimpleList();
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			dataMap.put(searchList.get(i).getOid(), searchList.get(i).getName());
		}
		return dataMap;
	}

}
