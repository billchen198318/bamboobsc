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
package com.netsteadfast.greenstep.service.logic;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.SysCalendarNoteVO;

public interface ISystemCalendarNoteLogicService {
	
	/**
	 * 產生 TB_SYS_CALENDAR_NOTE 資料
	 * 
	 * @param sysCalendarNote
	 * @param accountOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<SysCalendarNoteVO> create(SysCalendarNoteVO sysCalendarNote, String accountOid) throws ServiceException, Exception;
	
	/**
	 * 更新 TB_SYS_CALENDAR_NOTE 資料
	 * 
	 * @param sysCalendarNote
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<SysCalendarNoteVO> update(SysCalendarNoteVO sysCalendarNote) throws ServiceException, Exception;
	
}
