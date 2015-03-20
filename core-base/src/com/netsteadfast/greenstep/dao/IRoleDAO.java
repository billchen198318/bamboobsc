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
package com.netsteadfast.greenstep.dao;

import java.util.List;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.po.hbm.TbRole;
import com.netsteadfast.greenstep.vo.RoleVO;

public interface IRoleDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<TbRole, String> {

	/**
	 * 查帳戶下有的 role
	 * 
	 * select OID, ROLE, DESCRIPTION from tb_role 
	 * WHERE ROLE in (	
	 * 		select ROLE from tb_user_role WHERE ACCOUNT='admin'
	 * );
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public List<RoleVO> findForAccount(String account) throws Exception;
	
	/**
	 * select OID, ROLE, DESCRIPTION from tb_role;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RoleVO> findForAll() throws Exception;
	
	/**
	 * 查某隻程式屬於的role
	 * 
	 * select OID, ROLE, DESCRIPTION from tb_role where ROLE in (
	 * 		select ROLE from tb_sys_menu_role WHERE PROG_ID = :progId 
	 * )
	 * 
	 * @param progId
	 * @return
	 * @throws Exception
	 */
	public List<RoleVO> findForProgram(String progId) throws Exception;
	
}
