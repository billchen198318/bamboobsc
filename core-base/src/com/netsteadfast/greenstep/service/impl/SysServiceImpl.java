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
import com.netsteadfast.greenstep.dao.ISysDAO;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.vo.SysVO;

@Service("core.service.SysService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysServiceImpl extends BaseService<SysVO, TbSys, String> implements ISysService<SysVO, TbSys, String> {
	protected Logger logger=Logger.getLogger(SysServiceImpl.class);
	private ISysDAO<TbSys, String> sysDAO;
	
	public SysServiceImpl() {
		super();
	}

	public ISysDAO<TbSys, String> getSysDAO() {
		return sysDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysDAO")
	@Required		
	public void setSysDAO(
			ISysDAO<TbSys, String> sysDAO) {
		this.sysDAO = sysDAO;
	}

	@Override
	protected IBaseDAO<TbSys, String> getBaseDataAccessObject() {
		return sysDAO;
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
		String name = searchValue.getParameter().get("name");
		String sysId = searchValue.getParameter().get("sysId");
		if (!StringUtils.isBlank(name)) {
			params.put("name", "%"+name+"%");
		}
		if (!StringUtils.isBlank(sysId)) {
			params.put("sysId", sysId);
		}		
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysVO(ts.oid, ts.sysId, ts.name, ts.host, ts.contextPath, ts.isLocal, ts.icon) ");
		}
		hqlSb.append("FROM TbSys ts WHERE 1=1 ");
		if (params.get("name")!=null) {
			hqlSb.append("  and ts.name LIKE :name ");
		}		
		if (params.get("sysId")!=null) {
			hqlSb.append("  and ts.sysId = :sysId ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY ts.name ASC ");
		}		
		return hqlSb.toString();
	}
	
	@Override
	public QueryResult<List<SysVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysVO>> result=this.sysDAO.findResult2(
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
	public Map<String, String> findSysMap(String basePath, boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> sysMap = super.providedSelectZeroDataMap(pleaseSelect);
		List<TbSys> searchList = this.findListByParams(null);
		if (searchList==null || searchList.size()<1) {
			return sysMap;
		}
		for (TbSys sys : searchList) {
			sysMap.put(sys.getOid(), IconUtils.getMenuIcon(basePath, sys.getIcon()) + sys.getName());
		}
		return sysMap;
	}

}
