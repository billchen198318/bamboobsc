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
import com.netsteadfast.greenstep.dao.ISysProgDAO;
import com.netsteadfast.greenstep.model.MenuItemType;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.vo.SysProgVO;

@Repository("core.dao.SysProgDAO")
public class SysProgDAOImpl extends BaseDAO<TbSysProg, String> implements ISysProgDAO<TbSysProg, String> {
	
	public SysProgDAOImpl() {
		super();
	}

	@Override
	public SysProgVO findNameForProgId(String progId) throws Exception {
		return (SysProgVO) this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.SysProgVO(tsp.name) FROM TbSysProg tsp WHERE tsp.progId = :progId ")
				.setString("progId", progId)
				.uniqueResult();
	}

	/**
	 * 找在選單中(FOLDER) 之下已存在的項目
	 * 
	 * select OID,PROG_ID,NAME,PROG_SYSTEM, ICON
	 * from tb_sys_prog 
	 * where PROG_ID in ( 
	 * 		select PROG_ID 
	 * 		from tb_sys_menu where PARENT_OID=:folderProgramOid 
	 * ) 
	 * and PROG_SYSTEM=:progSystem
	 * and EDIT_MODE='N' 
	 * and ITEM_TYPE=:itemType;
	 * 
	 * @param progSystem
	 * @param menuParentOid
	 * @param itemType
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysProgVO> findForInTheFolderMenuItems(String progSystem, String menuParentOid, String itemType) throws Exception {
		String hqlStr = "SELECT new com.netsteadfast.greenstep.vo.SysProgVO(tsp.oid, tsp.progId, tsp.name, tsp.progSystem, tsp.icon) "; 
		hqlStr += "FROM TbSysProg tsp WHERE tsp.progId IN ( " ;
		if (!StringUtils.isBlank(menuParentOid)) {
			hqlStr += "	SELECT tsm.progId from TbSysMenu tsm WHERE tsm.parentOid = :parentOid ";
		} else {
			hqlStr += "	SELECT tsm.progId from TbSysMenu tsm ";
		}		
		hqlStr += ") ";
		hqlStr += "AND tsp.progSystem = :progSystem AND tsp.editMode='N' ";
		if (!StringUtils.isBlank(itemType)) {
			hqlStr += "AND tsp.itemType = :itemType ";
		}
		hqlStr += "ORDER BY tsp.progId ASC ";				
		Query query = this.getCurrentSession().createQuery(hqlStr);
		if (!StringUtils.isBlank(menuParentOid)) {
			query.setString("parentOid", menuParentOid);
		}		
		if (!StringUtils.isBlank(itemType)) {
			query.setString("itemType", itemType);
		}
		query.setString("progSystem", progSystem);
		return query.list();
	}

	/**
	 * 找同 PROG_SYSTEM 的資料
	 * 
	 * select OID, PROG_ID, NAME, PROG_SYSTEM, ICON 
	 * from tb_sys_prog 
	 * where PROG_SYSTEM=:progSystem
	 * and ITEM_TYPE='ITEM' 
	 * and EDIT_MODE='N';
	 * 
	 * @param progSystem
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysProgVO> findForSystemItems(String progSystem) throws Exception {
		
		return this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.SysProgVO(tsp.oid, tsp.progId, tsp.name, tsp.progSystem, tsp.icon) " +
				"FROM TbSysProg tsp WHERE tsp.progSystem = :progSystem AND tsp.editMode='N' AND tsp.itemType='" + MenuItemType.ITEM + "' ORDER BY tsp.progId ASC ")
		.setString("progSystem", progSystem)
		.list();
	}
	
}
