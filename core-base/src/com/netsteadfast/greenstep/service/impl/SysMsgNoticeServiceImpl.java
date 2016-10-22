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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysMsgNoticeDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;

@Service("core.service.SysMsgNoticeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysMsgNoticeServiceImpl extends BaseService<SysMsgNoticeVO, TbSysMsgNotice, String> implements ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> {
	protected Logger logger=Logger.getLogger(SysMsgNoticeServiceImpl.class);
	private ISysMsgNoticeDAO<TbSysMsgNotice, String> sysMsgNoticeDAO;
	
	public SysMsgNoticeServiceImpl() {
		super();
	}

	public ISysMsgNoticeDAO<TbSysMsgNotice, String> getSysMsgNoticeDAO() {
		return sysMsgNoticeDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysMsgNoticeDAO")
	@Required		
	public void setSysMsgNoticeDAO(
			ISysMsgNoticeDAO<TbSysMsgNotice, String> sysMsgNoticeDAO) {
		this.sysMsgNoticeDAO = sysMsgNoticeDAO;
	}

	@Override
	protected IBaseDAO<TbSysMsgNotice, String> getBaseDataAccessObject() {
		return sysMsgNoticeDAO;
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
				.fullEquals4Select("msgOid")
				.containingLike("title")
				.fullEquals4TextField("noticeId")
				.getValue();
	}	
	
	@Override
	public QueryResult<List<SysMsgNoticeVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<SysMsgNoticeVO>> result=this.sysMsgNoticeDAO.findPageQueryResultByQueryName(
				"findSysMsgNoticePageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

}
