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

import com.netsteadfast.greenstep.BscConstants;
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
import com.netsteadfast.greenstep.bsc.dao.IVisionDAO;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.vo.VisionVO;

@Service("bsc.service.VisionService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VisionServiceImpl extends BaseService<VisionVO, BbVision, String> implements IVisionService<VisionVO, BbVision, String> {
	protected Logger logger=Logger.getLogger(VisionServiceImpl.class);
	private IVisionDAO<BbVision, String> visionDAO;
	
	public VisionServiceImpl() {
		super();
	}

	public IVisionDAO<BbVision, String> getVisionDAO() {
		return visionDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.VisionDAO")
	@Required		
	public void setVisionDAO(
			IVisionDAO<BbVision, String> visionDAO) {
		this.visionDAO = visionDAO;
	}

	@Override
	protected IBaseDAO<BbVision, String> getBaseDataAccessObject() {
		return visionDAO;
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
	public String findForMaxVisId(String visId) throws ServiceException, Exception {
		if (StringUtils.isBlank(visId) || !visId.startsWith(BscConstants.HEAD_FOR_VIS_ID) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.visionDAO.findForMaxVisId(visId);
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		Map<String, Object> params=new LinkedHashMap<String, Object>();
		String visId = searchValue.getParameter().get("visId");
		String title = searchValue.getParameter().get("title");
		if (!StringUtils.isBlank(visId)) {
			params.put("visId", visId);
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
			hqlSb.append("	new com.netsteadfast.greenstep.vo.VisionVO(m.oid, m.visId, m.title) ");
		}
		hqlSb.append("FROM BbVision m WHERE 1=1 ");		
		if (params.get("visId")!=null) {
			hqlSb.append(" AND m.visId = :visId ");			
		}
		if (params.get("title")!=null) {
			hqlSb.append(" AND m.title LIKE :title ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.visId ASC ");
		}		
		return hqlSb.toString();
	}			

	@Override
	public QueryResult<List<VisionVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<VisionVO>> result=this.visionDAO.findResult2(
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
	public DefaultResult<VisionVO> findForSimple(String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<VisionVO> result = new DefaultResult<VisionVO>();
		VisionVO vision = this.visionDAO.findForSimple(oid);
		if (vision != null) {
			result.setValue(vision);
		} else {
			result.setSystemMessage( 
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}		
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<VisionVO> searchList = this.visionDAO.findForSimple();
		if (searchList == null || searchList.size() < 1 ) {
			return dataMap;
		}
		for (VisionVO vision : searchList) {
			dataMap.put(vision.getOid(), vision.getTitle());
		}
		return dataMap;
	}

	@Override
	public DefaultResult<VisionVO> findForSimpleByVisId(String visId) throws ServiceException, Exception {
		if (StringUtils.isBlank(visId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<VisionVO> result = new DefaultResult<VisionVO>();
		VisionVO vision = this.visionDAO.findForSimpleByVisId(visId);
		if (vision != null) {
			result.setValue(vision);
		} else {
			result.setSystemMessage( 
					new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}		
		return result;
	}

	@Override
	public List<String> findForOidByKpiOrga(String orgId) throws ServiceException, Exception {
		if (StringUtils.isBlank(orgId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.visionDAO.findForOidByKpiOrga(orgId);
	}
	
}
