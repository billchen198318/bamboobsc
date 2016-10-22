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
package com.netsteadfast.greenstep.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysProgDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.util.IconUtils;
import com.netsteadfast.greenstep.vo.SysProgVO;

@Service("core.service.SysProgService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysProgServiceImpl extends BaseService<SysProgVO, TbSysProg, String> implements ISysProgService<SysProgVO, TbSysProg, String> {
	protected Logger logger=Logger.getLogger(SysProgServiceImpl.class);
	private ISysProgDAO<TbSysProg, String> sysProgDAO;
	
	public SysProgServiceImpl() {
		super();
	}

	public ISysProgDAO<TbSysProg, String> getSysProgDAO() {
		return sysProgDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysProgDAO")
	@Required		
	public void setSysProgDAO(
			ISysProgDAO<TbSysProg, String> sysProgDAO) {
		this.sysProgDAO = sysProgDAO;
	}

	@Override
	protected IBaseDAO<TbSysProg, String> getBaseDataAccessObject() {
		return sysProgDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public String findNameForProgId(String progId) throws ServiceException, Exception {
		if (StringUtils.isBlank(progId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysProgVO sysProg = this.sysProgDAO.findNameForProgId(progId);
		if (null == sysProg) {
			return "";
		}
		return super.defaultString( sysProg.getName() );
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.containingLike("name")
				.fullEquals4TextField("progId")
				.getValue();
	}			

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
	@Override
	public QueryResult<List<SysProgVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<SysProgVO>> result=this.sysProgDAO.findPageQueryResultByQueryName(
				"findSysProgPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}
	
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
	@Override
	public Map<String, String> findSysProgFolderMap(String basePath, String progSystem, String itemType, boolean pleaseSelect) throws ServiceException, Exception {
		
		if (StringUtils.isBlank(progSystem)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Map<String, String> dataMap = super.providedSelectZeroDataMap(pleaseSelect);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("progSystem", progSystem);
		params.put("itemType", itemType);
		List<TbSysProg> searchList = this.findListByParams(params);
		if (null == searchList || searchList.size()<1) {
			return dataMap;
		}
		for (TbSysProg sysProg : searchList) {
			dataMap.put(sysProg.getOid(), IconUtils.getMenuIcon(basePath, sysProg.getIcon()) + sysProg.getName());
		}
		return dataMap;
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
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public List<SysProgVO> findForInTheFolderMenuItems(String progSystem, String menuParentOid, String itemType) throws ServiceException, Exception {
		if (StringUtils.isBlank(progSystem)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.getSysProgDAO().findForInTheFolderMenuItems(progSystem, menuParentOid, itemType);
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
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public List<SysProgVO> findForSystemItems(String progSystem) throws ServiceException, Exception {
		if (StringUtils.isBlank(progSystem)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.getSysProgDAO().findForSystemItems(progSystem);
	}

}
