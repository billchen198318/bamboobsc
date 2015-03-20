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
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysTemplateDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysTemplate;
import com.netsteadfast.greenstep.service.ISysTemplateService;
import com.netsteadfast.greenstep.vo.SysTemplateVO;

@Service("core.service.SysTemplateService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysTemplateServiceImpl extends BaseService<SysTemplateVO, TbSysTemplate, String> implements ISysTemplateService<SysTemplateVO, TbSysTemplate, String> {
	protected Logger logger=Logger.getLogger(SysTemplateServiceImpl.class);
	private ISysTemplateDAO<TbSysTemplate, String> sysTemplateDAO;
	
	public SysTemplateServiceImpl() {
		super();
	}

	public ISysTemplateDAO<TbSysTemplate, String> getSysTemplateDAO() {
		return sysTemplateDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysTemplateDAO")
	@Required		
	public void setSysTemplateDAO(
			ISysTemplateDAO<TbSysTemplate, String> sysTemplateDAO) {
		this.sysTemplateDAO = sysTemplateDAO;
	}

	@Override
	protected IBaseDAO<TbSysTemplate, String> getBaseDataAccessObject() {
		return sysTemplateDAO;
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
		String templateId = searchValue.getParameter().get("templateId");
		String title = searchValue.getParameter().get("title");		
		if (!StringUtils.isBlank(templateId)) {
			params.put("templateId", templateId);
		}
		if (!StringUtils.isBlank(title)) {
			params.put("title", "%"+title+"%");
		}			
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysTemplateVO(m.oid, m.templateId, m.title, m.description) ");
		}
		hqlSb.append("FROM TbSysTemplate m WHERE 1=1 ");
		if (params.get("templateId")!=null) {
			hqlSb.append("  and m.templateId = :templateId ");
		}		
		if (params.get("title")!=null) {
			hqlSb.append("  and m.title LIKE :title ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.templateId ASC");
		}		
		return hqlSb.toString();
	}		
	
	@Override
	public QueryResult<List<SysTemplateVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysTemplateVO>> result=this.sysTemplateDAO.findResult2(
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
