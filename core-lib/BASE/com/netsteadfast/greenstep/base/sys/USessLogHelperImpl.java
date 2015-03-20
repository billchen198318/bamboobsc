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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class USessLogHelperImpl implements IUSessLogHelper {
	protected Logger log=Logger.getLogger(USessLogHelperImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	public USessLogHelperImpl() {
		/*
		 * 不再 constructor 取  jdbcTemplate, 因為listener使用 USessLogHelperImpl 時,  spring 還沒載入完成
		 */
		// this.jdbcTemplate=(JdbcTemplate)AppContext.getBean("jdbcTemplate");
	}
	
	private JdbcTemplate getJdbcTemplate() {	
		if (this.jdbcTemplate==null) {
			this.jdbcTemplate=(JdbcTemplate)AppContext.getBean("jdbcTemplate");
		}
		return this.jdbcTemplate;
	}
	
	/**
	 * 清除 TB_SYS_USESS 資料
	 * 
	 * @throws Exception
	 */
	@Override
	public void beginClean() throws Exception {
		log.info("delete tb_sys_usess data!... ");
		this.getJdbcTemplate().update("delete from tb_sys_usess");
	}

	/**
	 * 新增紀錄
	 * 
	 * @param sessionId
	 * @param account
	 * @throws Exception
	 */	
	@Override
	public void insert(String sessionId, String account) throws Exception {
		log.info("insert... :" + sessionId + " account:"+account);
		this.getJdbcTemplate().update(
				"insert into tb_sys_usess(OID, SESSION_ID, ACCOUNT, CURRENT_ID, CUSERID, CDATE ) values (?, ?, ?, ?, ?, ?) ", 
				new Object[]{
					SimpleUtils.getUUIDStr(),
					sessionId,
					account,
					SimpleUtils.getUUIDStr(),
					"SYS",
					new Date()
				}
		);
	}

	/**
	 * count紀錄
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */	
	@Override
	public long count(String sessionId) throws Exception {
		
		/*
		return this.getJdbcTemplate().queryForLong(
				"select count(*) from tb_sys_usess where SESSION_ID=?", new Object[]{sessionId});
		*/
		return this.getJdbcTemplate().queryForObject(
				"select count(*) from tb_sys_usess where SESSION_ID=?", new Object[]{sessionId}, Long.class);
	}

	/**
	 * 清除紀錄
	 * 
	 * @param sessionId
	 * @throws Exception
	 */	
	@Override
	public void delete(String sessionId) throws Exception {
		log.info("delete... :" + sessionId);
		this.getJdbcTemplate().update("delete from tb_sys_usess where SESSION_ID=?", new Object[]{sessionId});
	}

	/**
	 * count紀錄 (以account)
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */	
	@Override
	public long countByAccount(String account) throws Exception {
		
		/*
		return this.getJdbcTemplate().queryForLong(
				"select count(*) from tb_sys_usess where ACCOUNT=?", new Object[]{account});
		*/
		return this.getJdbcTemplate().queryForObject(
				"select count(*) from tb_sys_usess where ACCOUNT=?", new Object[]{account}, Long.class);
	}

	/**
	 * 找出紀錄登入帳戶的sessionId 
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> findSessionIdByAccount(String account) throws Exception {
		
		return this.getJdbcTemplate().queryForList(
				"select SESSION_ID from tb_sys_usess where ACCOUNT=?", 
				new Object[]{account}, 
				String.class);
	}
	
	/**
	 * 查看是否目前狀態存在DB中
	 * 
	 * @param account
	 * @param currentId
	 * @return
	 * @throws Exception
	 */	
	@Override
	public long countByCurrent(String account, String currentId) throws Exception {
		return this.getJdbcTemplate().queryForObject(
				"select count(*) from tb_sys_usess where ACCOUNT=? and CURRENT_ID=?", 
				new Object[]{account, currentId},
				Long.class);
	}	
	
	/**
	 * 查看是否目前狀態存在DB中
	 * 
	 * @param account
	 * @param currentId
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public long countByCurrent(String account, String currentId, String sessionId) throws Exception {
		return this.getJdbcTemplate().queryForObject(
				"select count(*) from tb_sys_usess where ACCOUNT=? and CURRENT_ID=? and SESSION_ID=?", 
				new Object[]{account, currentId, sessionId},
				Long.class);
	}

	/**
	 * 找出紀錄登入帳戶的current-id 
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */	
	@Override
	public List<String> findCurrenrIdByAccount(String account) throws Exception {
		return this.getJdbcTemplate().queryForList(
				"select CURRENT_ID from tb_sys_usess where ACCOUNT=?", 
				new Object[]{account}, 
				String.class);
	}
	
	/**
	 * 找出紀錄登入帳戶的current-id 
	 * 
	 * @param account
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */	
	@Override
	public List<String> findCurrenrIdByAccount(String account, String sessionId) throws Exception {
		return this.getJdbcTemplate().queryForList(
				"select CURRENT_ID from tb_sys_usess where ACCOUNT=? and SESSION_ID=?", 
				new Object[]{account, sessionId}, 
				String.class);
	}	

}
