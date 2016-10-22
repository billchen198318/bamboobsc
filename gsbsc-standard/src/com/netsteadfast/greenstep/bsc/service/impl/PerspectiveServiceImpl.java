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
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IPerspectiveDAO;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@Service("bsc.service.PerspectiveService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PerspectiveServiceImpl extends BaseService<PerspectiveVO, BbPerspective, String> implements IPerspectiveService<PerspectiveVO, BbPerspective, String> {
	protected Logger logger=Logger.getLogger(PerspectiveServiceImpl.class);
	private IPerspectiveDAO<BbPerspective, String> perspectiveDAO;
	
	public PerspectiveServiceImpl() {
		super();
	}

	public IPerspectiveDAO<BbPerspective, String> getPerspectiveDAO() {
		return perspectiveDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.PerspectiveDAO")
	@Required		
	public void setPerspectiveDAO(
			IPerspectiveDAO<BbPerspective, String> perspectiveDAO) {
		this.perspectiveDAO = perspectiveDAO;
	}

	@Override
	protected IBaseDAO<BbPerspective, String> getBaseDataAccessObject() {
		return perspectiveDAO;
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
	public String findForMaxPerId(String perId) throws ServiceException, Exception {
		if (StringUtils.isBlank(perId) || !perId.startsWith(BscConstants.HEAD_FOR_PER_ID) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.perspectiveDAO.findForMaxPerId(perId);
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.fullEquals4Select("visionOid")
				.fullEquals4TextField("perId")
				.containingLike("name")
				.getValue();
	}	

	@Override
	public QueryResult<List<PerspectiveVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<PerspectiveVO>> result=this.perspectiveDAO.findPageQueryResultByQueryName(
				"findPerspectivePageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public List<PerspectiveVO> findForListByVisionOid(String visionOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(visionOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.perspectiveDAO.findForListByVisionOid(visionOid);
	}

	@Override
	public Map<String, String> findForMapByVisionOid(String visionOid, boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<PerspectiveVO> searchList = this.findForListByVisionOid(visionOid);
		if (searchList==null || searchList.size() < 1 ) {
			return dataMap;
		}
		for (PerspectiveVO p : searchList) {
			dataMap.put(p.getOid(), p.getName());
		}
		return dataMap;
	}

}
