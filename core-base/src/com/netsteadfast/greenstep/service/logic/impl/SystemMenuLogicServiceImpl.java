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
package com.netsteadfast.greenstep.service.logic.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.model.ZeroKeyProvide;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.model.MenuItemType;
import com.netsteadfast.greenstep.po.hbm.TbSysMenu;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.service.ISysMenuService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.logic.ISystemMenuLogicService;
import com.netsteadfast.greenstep.vo.SysMenuVO;
import com.netsteadfast.greenstep.vo.SysProgVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemMenuLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemMenuLogicServiceImpl extends BaseLogicService implements ISystemMenuLogicService {
	protected Logger logger=Logger.getLogger(SystemMenuLogicServiceImpl.class);
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private ISysMenuService<SysMenuVO, TbSysMenu, String> sysMenuService;
	
	public SystemMenuLogicServiceImpl() {
		super();
	}
	
	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required		
	public void setSysProgService(
			ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}			
	
	public ISysMenuService<SysMenuVO, TbSysMenu, String> getSysMenuService() {
		return sysMenuService;
	}

	@Autowired
	@Resource(name="core.service.SysMenuService")
	@Required		
	public void setSysMenuService(
			ISysMenuService<SysMenuVO, TbSysMenu, String> sysMenuService) {
		this.sysMenuService = sysMenuService;
	}

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
	@ServiceMethodAuthority(type={ServiceMethodType.SELECT})
	@Override
	public Map<String, List<SysProgVO>> findForMenuSettingsEnableAndAll(String folderProgramOid) throws ServiceException, Exception {
		
		if (StringUtils.isBlank(folderProgramOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysProgVO sysProg = new SysProgVO();
		sysProg.setOid(folderProgramOid);
		DefaultResult<SysProgVO> spResult = this.sysProgService.findObjectByOid(sysProg);
		if (spResult.getValue()==null) {
			throw new ServiceException(spResult.getSystemMessage().getValue());
		}
		sysProg = spResult.getValue();
		
		Map<String, List<SysProgVO>> dataMap = new HashMap<String, List<SysProgVO>>();
		SysMenuVO sysMenu = new SysMenuVO();
		List<SysProgVO> enableList = null;
		List<SysProgVO> allList = null;
		sysMenu.setProgId(sysProg.getProgId());
		sysMenu.setParentOid(ZeroKeyProvide.OID_KEY);
		DefaultResult<SysMenuVO> smResult = this.sysMenuService.findByUK(sysMenu);
		if (smResult.getValue()!=null) {
			sysMenu = smResult.getValue();						
			enableList = this.sysProgService.findForInTheFolderMenuItems(
					sysProg.getProgSystem(), sysMenu.getOid(), MenuItemType.ITEM);			
		}
		allList = this.sysProgService.findForSystemItems(sysProg.getProgSystem());
		if (enableList==null) {
			enableList = new ArrayList<SysProgVO>();
		}
		if (allList==null) {
			allList = new ArrayList<SysProgVO>();
		}
		dataMap.put("enable", enableList);
		dataMap.put("all", allList);
		return dataMap;
	}

	/**
	 * 更新或是新增 TB_SYS_MENU 資料
	 * 
	 * @param folderProgramOid
	 * @param childProgramOidList
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> createOrUpdate(String folderProgramOid, List<String> childProgramOidList) throws ServiceException, Exception {
		
		if (StringUtils.isBlank(folderProgramOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue(false);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL)));
		
		// 找 TB_SYS_PROG 資料
		SysProgVO sysProg = new SysProgVO();
		sysProg.setOid(folderProgramOid);
		DefaultResult<SysProgVO> spResult = this.sysProgService.findObjectByOid(sysProg);
		if (spResult.getValue()==null) {
			throw new ServiceException(spResult.getSystemMessage().getValue());
		}
		sysProg = spResult.getValue();
		
		// 找出 TB_SYS_MENU 原資料 , 沒有資料就是新增
		SysMenuVO sysMenu = new SysMenuVO();
		sysMenu.setProgId(sysProg.getProgId());
		sysMenu.setParentOid(ZeroKeyProvide.OID_KEY);
		if (this.sysMenuService.countByUK(sysMenu)>0) { // update 更新
			DefaultResult<SysMenuVO> smResult = this.sysMenuService.findByUK(sysMenu);
			if (smResult.getValue()==null) {
				throw new ServiceException(smResult.getSystemMessage().getValue());
			}
			sysMenu = smResult.getValue();
		} else { // create new 新產
			sysMenu.setProgId(sysProg.getProgId());
			sysMenu.setParentOid(ZeroKeyProvide.OID_KEY);
			sysMenu.setEnableFlag(YesNo.YES);
			DefaultResult<SysMenuVO> smResult = this.sysMenuService.saveObject(sysMenu);
			if (smResult.getValue()==null) {
				throw new ServiceException(smResult.getSystemMessage().getValue());
			}
			sysMenu = smResult.getValue();
		}
		
		this.removeMenuChildData(sysMenu);
		this.createOrUpdate(sysMenu, childProgramOidList);		
		result.setValue(true);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)));
		return result;
	}	
	
	private void removeMenuChildData(SysMenuVO parentSysMenu) throws ServiceException, Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentOid", parentSysMenu.getOid());
		List<TbSysMenu> sysMenuList = this.sysMenuService.findListByParams(params);
		if (sysMenuList==null || sysMenuList.size()<1) {
			return;
		}
		for (TbSysMenu childSysMenu : sysMenuList) {
			this.sysMenuService.delete(childSysMenu);			
		}
	}
	
	private void createOrUpdate(SysMenuVO parentSysMenu, List<String> childProgramOidList) throws ServiceException, Exception {
		for (String progOid : childProgramOidList) {
			SysProgVO sysProg = new SysProgVO();
			sysProg.setOid(progOid);
			DefaultResult<SysProgVO> spResult = this.sysProgService.findObjectByOid(sysProg);
			if (spResult.getValue()==null) {
				throw new ServiceException(spResult.getSystemMessage().getValue());
			}
			sysProg = spResult.getValue();			
			
			SysMenuVO childSysMenu = new SysMenuVO();
			childSysMenu.setProgId(sysProg.getProgId());
			childSysMenu.setParentOid(parentSysMenu.getOid());
			childSysMenu.setEnableFlag(YesNo.YES);
			DefaultResult<SysMenuVO> result = this.sysMenuService.saveObject(childSysMenu);
			if (result.getValue()==null) {
				throw new ServiceException(result.getSystemMessage().getValue());
			}
		}
	}
	
}
