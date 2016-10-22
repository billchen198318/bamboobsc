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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IDegreeFeedbackProjectDAO;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;

@Service("bsc.service.DegreeFeedbackProjectService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackProjectServiceImpl extends BaseService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> implements IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> {
	protected Logger logger=Logger.getLogger(DegreeFeedbackProjectServiceImpl.class);
	private IDegreeFeedbackProjectDAO<BbDegreeFeedbackProject, String> degreeFeedbackProjectDAO;
	
	public DegreeFeedbackProjectServiceImpl() {
		super();
	}

	public IDegreeFeedbackProjectDAO<BbDegreeFeedbackProject, String> getDegreeFeedbackProjectDAO() {
		return degreeFeedbackProjectDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.DegreeFeedbackProjectDAO")
	@Required		
	public void setDegreeFeedbackProjectDAO(
			IDegreeFeedbackProjectDAO<BbDegreeFeedbackProject, String> degreeFeedbackProjectDAO) {
		this.degreeFeedbackProjectDAO = degreeFeedbackProjectDAO;
	}

	@Override
	protected IBaseDAO<BbDegreeFeedbackProject, String> getBaseDataAccessObject() {
		return degreeFeedbackProjectDAO;
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
				.fullEquals4TextField("year")
				.containingLike("name")
				.getValue();
	}	

	@Override
	public QueryResult<List<DegreeFeedbackProjectVO>> findGridResult(
			SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<DegreeFeedbackProjectVO>> result=this.degreeFeedbackProjectDAO.findPageQueryResultByQueryName(
				"findDegreeFeedbackProjectPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public DefaultResult<List<BbDegreeFeedbackProject>> findByPublishFlag(String publishFlag, String raterId) throws Exception {
		if (StringUtils.isBlank(raterId) || StringUtils.isBlank(publishFlag)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (!YesNo.YES.equals(publishFlag) && !YesNo.NO.equals(publishFlag)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_INCORRECT));
		}
		DefaultResult<List<BbDegreeFeedbackProject>> result = new DefaultResult<List<BbDegreeFeedbackProject>>();
		List<BbDegreeFeedbackProject> list = this.degreeFeedbackProjectDAO.findByPublishFlag(publishFlag, raterId);
		if (null != list && list.size()>0) {
			result.setValue(list);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public DefaultResult<List<DegreeFeedbackProjectVO>> findByPublishFlag2ValueObject(String publishFlag, String raterId) throws Exception {
		DefaultResult<List<BbDegreeFeedbackProject>> queryResult = this.findByPublishFlag(publishFlag, raterId);
		DefaultResult<List<DegreeFeedbackProjectVO>> result = new DefaultResult<List<DegreeFeedbackProjectVO>>();
		if (queryResult.getValue()==null) {
			result.setSystemMessage( queryResult.getSystemMessage() );
			return result;
		}
		List<DegreeFeedbackProjectVO> datas = new ArrayList<DegreeFeedbackProjectVO>(); 
		List<BbDegreeFeedbackProject> queryList = queryResult.getValue();
		for (BbDegreeFeedbackProject entity : queryList) {
			DegreeFeedbackProjectVO obj = new DegreeFeedbackProjectVO();
			this.doMapper(entity, obj, this.getMapperIdPo2Vo());
			datas.add( obj );
		}
		result.setValue(datas);
		return result;
	}

}
