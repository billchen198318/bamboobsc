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
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.IBaseService;
import com.netsteadfast.greenstep.vo.SysProgVO;

public interface ISysProgService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseService<T, E, PK> {
	
	public static String MAPPER_ID_PO2VO="sysProg.po2vo";
	public static String MAPPER_ID_VO2PO="sysProg.vo2po";
	
	public String findNameForProgId(String progId) throws ServiceException, Exception;
	
	/**
	 * select OID, PROG_ID, NAME, URL, EDIT_MODE, PROG_SYSTEM, ITEM_TYPE, ICON from tb_sys_prog
	 * where 1=1
	 * and PROG_ID = 'CORE_PROG001D0001A' and NAME like '%App%'
	 * order by PROG_ID, NAME ASC
	 * 
	 * @param searchValue
	 * @param pageOf
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public QueryResult<List<SysProgVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception;
	
	/**
	 * 找 TB_SYS_PROG.ITEM_TYPE = 'FOLDER' 或 'ITEM' 的資料
	 * 
	 * @param basePath
	 * @param progSystem
	 * @param itemType
	 * @param pleaseSelect
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public Map<String, String> findSysProgFolderMap(String basePath, String progSystem, String itemType, boolean pleaseSelect) throws ServiceException, Exception;
	
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
	 * @throws ServiceException
	 * @throws Exception
	 */
	public List<SysProgVO> findForInTheFolderMenuItems(String progSystem, String menuParentOid, String itemType) throws ServiceException, Exception;
	
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
	 * @throws ServiceException
	 * @throws Exception
	 */
	public List<SysProgVO> findForSystemItems(String progSystem) throws ServiceException, Exception;
	
}
