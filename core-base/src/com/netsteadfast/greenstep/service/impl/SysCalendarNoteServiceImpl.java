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
import com.netsteadfast.greenstep.dao.ISysCalendarNoteDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysCalendarNote;
import com.netsteadfast.greenstep.service.ISysCalendarNoteService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

@Service("core.service.SysCalendarNoteService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysCalendarNoteServiceImpl extends BaseService<SysCalendarNoteVO, TbSysCalendarNote, String> implements ISysCalendarNoteService<SysCalendarNoteVO, TbSysCalendarNote, String> {
	protected Logger logger=Logger.getLogger(SysCalendarNoteServiceImpl.class);
	private ISysCalendarNoteDAO<TbSysCalendarNote, String> sysCalendarNoteDAO;
	
	public SysCalendarNoteServiceImpl() {
		super();
	}

	public ISysCalendarNoteDAO<TbSysCalendarNote, String> getSysCalendarNoteDAO() {
		return sysCalendarNoteDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysCalendarNoteDAO")
	@Required		
	public void setSysCalendarNoteDAO(
			ISysCalendarNoteDAO<TbSysCalendarNote, String> sysCalendarNoteDAO) {
		this.sysCalendarNoteDAO = sysCalendarNoteDAO;
	}

	@Override
	protected IBaseDAO<TbSysCalendarNote, String> getBaseDataAccessObject() {
		return sysCalendarNoteDAO;
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
		String accountOid = searchValue.getParameter().get("accountOid");
		String date = searchValue.getParameter().get("date");
		if (!StringUtils.isBlank(accountOid) && !Constants.HTML_SELECT_NO_SELECT_ID.equals(accountOid) ) {
			params.put("accountOid", accountOid);
		}		
		if (!StringUtils.isBlank(date) && SimpleUtils.isDate(date) ) {
			params.put("date", date.replaceAll("-", "").replaceAll("/", ""));
		}			
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysCalendarNoteVO(m.oid, m.account, m.calendarId, m.title, m.date) ");
		}
		hqlSb.append("FROM TbSysCalendarNote m WHERE 1=1 ");
		if (params.get("accountOid")!=null) {
			hqlSb.append("  AND m.account IN ( SELECT a.account FROM TbAccount a WHERE a.oid = :accountOid ) ");
		}
		if (params.get("date")!=null) {
			hqlSb.append("  AND m.date = :date ");
		}		
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.account ASC, m.calendarId DESC ");
		}		
		return hqlSb.toString();
	}		
	
	@Override
	public QueryResult<List<SysCalendarNoteVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysCalendarNoteVO>> result=this.sysCalendarNoteDAO.findResult2(
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_SELECT, params), 
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_COUNT, params), 
				params, 
				offset, 
				limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}
	
	/**
	 * 查最大的 CALENDAR_ID
	 * 規則: 年月日+001 , 最大至999 , 如 20141915001
	 * 
	 * @param account
	 * @param date
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public String findForMaxCalendarId(String account, String date) throws ServiceException, Exception {
		if (StringUtils.isBlank(account) || StringUtils.isBlank(date)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		String calendarId = this.sysCalendarNoteDAO.findForMaxCalendarId(account, date);
		if (!StringUtils.isBlank(calendarId)) {
			int num = Integer.parseInt( calendarId.substring(8, 11) )+1;
			if (num>999) { // 一個帳戶當日最多只能有999筆note
				throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
			}
			return date + StringUtils.leftPad(String.valueOf(num), 3, "0");			
		}
		// 第一筆 calendar_id		
		return date + "001";
	}

}
