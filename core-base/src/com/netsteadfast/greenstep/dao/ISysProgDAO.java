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
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.vo.SysProgVO;

public interface ISysProgDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<TbSysProg, String> {
	
	public SysProgVO findNameForProgId(String progId) throws Exception;
	
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
	public List<SysProgVO> findForInTheFolderMenuItems(String progSystem, String menuParentOid, String itemType) throws Exception;
	
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
	public List<SysProgVO> findForSystemItems(String progSystem) throws Exception;
	
}
