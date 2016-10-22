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

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.vo.SysProgVO;

public interface ISystemMenuLogicService {
	
	/**
	 * 找出選單設定功能要的
	 * 已在選單的程式 與 同SYS的程式
	 * 
	 * map 中的  key 
	 * enable	- 已在選單的程式
	 * all	- 同SYS的程式
	 * 
	 * @param folderProgramOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public Map<String, List<SysProgVO>> findForMenuSettingsEnableAndAll(String folderProgramOid) throws ServiceException, Exception;
	
	/**
	 * 更新或是新增 TB_SYS_MENU 資料
	 * 
	 * @param folderProgramOid
	 * @param childProgramOidList
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<Boolean> createOrUpdate(String folderProgramOid, List<String> childProgramOidList) throws ServiceException, Exception;
	
}
