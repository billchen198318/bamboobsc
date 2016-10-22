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
import com.netsteadfast.greenstep.vo.SysProgMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgVO;

public interface ISystemProgramLogicService {
	
	/**
	 * 產生 TB_SYS_PROG 資料
	 * 
	 * @param sys
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<SysProgVO> create(SysProgVO sysProg, String sysOid, String iconOid) throws ServiceException, Exception;
	
	/**
	 * 更新 TB_SYS_PROG 資料
	 * 
	 * @param sysProg
	 * @param sysOid
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<SysProgVO> update(SysProgVO sysProg, String sysOid, String iconOid) throws ServiceException, Exception;
	
	/**
	 * 刪除 TB_SYS_PROG 資料
	 * 
	 * @param sysProg
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<Boolean> delete(SysProgVO sysProg) throws ServiceException, Exception;
	
	/**
	 * 產生 tb_sys_prog_multi_name 資料
	 * 
	 * @param multiName
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public DefaultResult<SysProgMultiNameVO> createMultiName(SysProgMultiNameVO multiName) throws ServiceException, Exception;
	
}
