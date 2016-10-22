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
package com.netsteadfast.greenstep.service;

import java.util.List;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.IBaseService;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

public interface ISysCalendarNoteService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseService<T, E, PK> {
	
	public static String MAPPER_ID_PO2VO="sysCalendarNote.po2vo";
	public static String MAPPER_ID_VO2PO="sysCalendarNote.vo2po";
	
	public QueryResult<List<SysCalendarNoteVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception;
	
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
	public String findForMaxCalendarId(String account, String date) throws ServiceException, Exception;
	
}
