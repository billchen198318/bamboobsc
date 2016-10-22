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
package com.netsteadfast.greenstep.base.sys;

import java.util.List;

public interface IUSessLogHelper {
	
	/**
	 * 清除 TB_SYS_USESS 資料
	 * 
	 * @throws Exception
	 */
	public void beginClean() throws Exception;
	
	/**
	 * 新增紀錄
	 * 
	 * @param sessionId
	 * @param account
	 * @throws Exception
	 */
	public void insert(String sessionId, String account) throws Exception;
	
	/**
	 * count紀錄
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public long count(String sessionId) throws Exception;
	
	/**
	 * 清除紀錄
	 * 
	 * @param sessionId
	 * @throws Exception
	 */
	public void delete(String sessionId) throws Exception;
	
	/**
	 * count紀錄 (以account)
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public long countByAccount(String account) throws Exception;
	
	/**
	 * 找出紀錄登入帳戶的sessionId 
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public List<String> findSessionIdByAccount(String account) throws Exception;

	/**
	 * 查看是否目前狀態存在DB中
	 * 
	 * @param account
	 * @param currentId
	 * @return
	 * @throws Exception
	 */
	public long countByCurrent(String account, String currentId) throws Exception;	
	
	/**
	 * 查看是否目前狀態存在DB中
	 * 
	 * @param account
	 * @param currentId
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public long countByCurrent(String account, String currentId, String sessionId) throws Exception;
	
	/**
	 * 找出紀錄登入帳戶的current-id 
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public List<String> findCurrenrIdByAccount(String account) throws Exception;	
	
	/**
	 * 找出紀錄登入帳戶的current-id 
	 * 
	 * @param account
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */		
	public List<String> findCurrenrIdByAccount(String account, String sessionId) throws Exception;
	
}
