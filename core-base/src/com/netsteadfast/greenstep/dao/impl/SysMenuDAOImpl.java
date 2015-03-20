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
package com.netsteadfast.greenstep.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.dao.ISysMenuDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysMenu;
import com.netsteadfast.greenstep.vo.SysMenuVO;

@Repository("core.dao.SysMenuDAO")
public class SysMenuDAOImpl extends BaseDAO<TbSysMenu, String> implements ISysMenuDAO<TbSysMenu, String> {
	
	public SysMenuDAOImpl() {
		super();
	}

	/**
	 * 找此帳戶有的選單 , 如果是 super 就把 account 帶入空白
	 * select 
	 * 		sm.OID, sm.PROG_ID, sm.PARENT_OID, sm.ENABLE_FLAG,
	 * 		tsp.NAME, tsp.URL, tsp.PROG_SYSTEM, tsp.ITEM_TYPE, tsp.ICON
	 * from tb_sys_menu sm, tb_sys_prog tsp
	 * where tsp.PROG_ID = sm.PROG_ID and tsp.PROG_SYSTEM='SYS'
	 * and sm.PROG_ID in ( -- 如果是 super 就不用這一段
	 * 		select PROG_ID from tb_sys_menu_role where ROLE in (
	 * 			select ROLE from tb_user_role where ACCOUNT='admin'
	 * 		)
	 * )
	 * order by tsp.PROG_ID, tsp.NAME asc
	 * 
	 * @param progSystem
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuVO> findForMenuGenerator(String progSystem, String account) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT	new com.netsteadfast.greenstep.vo.SysMenuVO(	");
		sb.append("		sm.oid, sm.progId, sm.parentOid, sm.enableFlag,	");
		sb.append("		tsp.name, tsp.url, tsp.progSystem, tsp.itemType, tsp.icon	");
		sb.append(")	");
		sb.append("FROM	");
		sb.append("TbSysMenu sm, TbSysProg tsp	");
		sb.append("WHERE sm.progId=tsp.progId	AND	tsp.progSystem = :progSystem	");
		if (!StringUtils.isBlank(account)) {
			sb.append("AND sm.progId IN (	");
			sb.append("	SELECT progId FROM TbSysMenuRole where role IN (	");
			sb.append("		SELECT role FROM TbUserRole WHERE account = :account ");
			sb.append("	)	");
			sb.append(")	");
		}
		sb.append("ORDER BY tsp.progId, tsp.name ASC	");
		Query query = this.getCurrentSession().createQuery(sb.toString());
		query.setString("progSystem", progSystem);
		if (!StringUtils.isBlank(account)) {
			query.setString("account", account);
		}				
		return query.list();
	}
	
}
