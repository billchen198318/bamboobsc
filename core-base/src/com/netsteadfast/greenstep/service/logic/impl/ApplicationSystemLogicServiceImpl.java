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
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelp;
import com.netsteadfast.greenstep.po.hbm.TbSysCtxBean;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJob;
import com.netsteadfast.greenstep.po.hbm.TbSysIcon;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.po.hbm.TbSysMultiName;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.po.hbm.TbSysTwitter;
import com.netsteadfast.greenstep.po.hbm.TbSysWsConfig;
import com.netsteadfast.greenstep.service.ISysBeanHelpService;
import com.netsteadfast.greenstep.service.ISysCtxBeanService;
import com.netsteadfast.greenstep.service.ISysExprJobService;
import com.netsteadfast.greenstep.service.ISysIconService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.service.ISysMultiNameService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysTwitterService;
import com.netsteadfast.greenstep.service.ISysWsConfigService;
import com.netsteadfast.greenstep.service.logic.IApplicationSystemLogicService;
import com.netsteadfast.greenstep.util.LocaleLanguageUtils;
import com.netsteadfast.greenstep.vo.SysBeanHelpVO;
import com.netsteadfast.greenstep.vo.SysCtxBeanVO;
import com.netsteadfast.greenstep.vo.SysExprJobVO;
import com.netsteadfast.greenstep.vo.SysIconVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;
import com.netsteadfast.greenstep.vo.SysMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.SysTwitterVO;
import com.netsteadfast.greenstep.vo.SysVO;
import com.netsteadfast.greenstep.vo.SysWsConfigVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.ApplicationSystemLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ApplicationSystemLogicServiceImpl extends BaseLogicService implements IApplicationSystemLogicService {
	protected Logger logger=Logger.getLogger(ApplicationSystemLogicServiceImpl.class);
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService;
	private ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService;
	private ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService; 
	private ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService;
	private ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService;
	private ISysMultiNameService<SysMultiNameVO, TbSysMultiName, String> sysMultiNameService;
	private ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService;
	
	public ApplicationSystemLogicServiceImpl() {
		super();
	}

	public ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	@Autowired
	@Resource(name="core.service.SysIconService")
	@Required			
	public void setSysIconService(
			ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		this.sysIconService = sysIconService;
	}

	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required	
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
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

	public ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> getSysMsgNoticeConfigService() {
		return sysMsgNoticeConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysMsgNoticeConfigService")
	@Required		
	public void setSysMsgNoticeConfigService(
			ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> sysMsgNoticeConfigService) {
		this.sysMsgNoticeConfigService = sysMsgNoticeConfigService;
	}

	public ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> getSysWsConfigService() {
		return sysWsConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysWsConfigService")
	@Required			
	public void setSysWsConfigService(
			ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService) {
		this.sysWsConfigService = sysWsConfigService;
	}

	public ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> getSysBeanHelpService() {
		return sysBeanHelpService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpService")
	@Required		
	public void setSysBeanHelpService(
			ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService) {
		this.sysBeanHelpService = sysBeanHelpService;
	}

	public ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> getSysCtxBeanService() {
		return sysCtxBeanService;
	}

	@Autowired
	@Resource(name="core.service.SysCtxBeanService")
	@Required		
	public void setSysCtxBeanService(
			ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService) {
		this.sysCtxBeanService = sysCtxBeanService;
	}

	public ISysTwitterService<SysTwitterVO, TbSysTwitter, String> getSysTwitterService() {
		return sysTwitterService;
	}

	@Autowired
	@Resource(name="core.service.SysTwitterService")
	@Required			
	public void setSysTwitterService(
			ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService) {
		this.sysTwitterService = sysTwitterService;
	}
	
	public ISysMultiNameService<SysMultiNameVO, TbSysMultiName, String> getSysMultiNameService() {
		return sysMultiNameService;
	}

	@Autowired
	@Resource(name="core.service.SysMultiNameService")
	@Required		
	public void setSysMultiNameService(ISysMultiNameService<SysMultiNameVO, TbSysMultiName, String> sysMultiNameService) {
		this.sysMultiNameService = sysMultiNameService;
	}
	
	public ISysExprJobService<SysExprJobVO, TbSysExprJob, String> getSysExprJobService() {
		return sysExprJobService;
	}

	@Autowired
	@Resource(name="core.service.SysExprJobService")
	@Required			
	public void setSysExprJobService(ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService) {
		this.sysExprJobService = sysExprJobService;
	}	
	
	/**
	 * 建立 TB_SYS 資料
	 * 
	 * @param sys
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysVO> create(SysVO sys, String iconOid) throws ServiceException, Exception {
		
		SysIconVO sysIcon = new SysIconVO();
		sysIcon.setOid(iconOid);
		DefaultResult<SysIconVO> iconResult = this.sysIconService.findObjectByOid(sysIcon);
		if (iconResult.getValue()==null) {
			throw new ServiceException(iconResult.getSystemMessage().getValue());
		}		
		sys.setIcon(iconResult.getValue().getIconId());
		return this.sysService.saveObject(sys);
	}

	/**
	 * 刪除 TB_SYS 資料
	 * 
	 * @param sys
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysVO sys) throws ServiceException, Exception {
		
		if (sys==null || StringUtils.isBlank(sys.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
		if (sysResult.getValue()==null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("progSystem", sys.getSysId());
		if (this.sysProgService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		params.clear();
		params.put("system", sys.getSysId());
		if (this.sysMsgNoticeConfigService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.sysWsConfigService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}		
		if (this.sysBeanHelpService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.sysCtxBeanService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.sysTwitterService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		if (this.sysExprJobService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_CANNOT_DELETE));
		}
		
		// 刪除名稱語言資料 tb_sys_multi_name
		params.clear();
		params.put("sysId", sys.getSysId());
		List<TbSysMultiName> sysMultiNames = this.sysMultiNameService.findListByParams(params);
		for (int i=0; sysMultiNames != null && i < sysMultiNames.size(); i++) {
			this.sysMultiNameService.delete( sysMultiNames.get(i) );
		}
		
		return this.sysService.deleteObject(sys);		
	}

	/**
	 * 更新 TB_SYS 資料
	 * 
	 * @param sys
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysVO> update(SysVO sys, String iconOid) throws ServiceException, Exception {
		
		if (null == sys || StringUtils.isBlank(sys.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysIconVO sysIcon = new SysIconVO();
		sysIcon.setOid(iconOid);
		DefaultResult<SysIconVO> iconResult = this.sysIconService.findObjectByOid(sysIcon);
		if (iconResult.getValue()==null) {
			throw new ServiceException(iconResult.getSystemMessage().getValue());
		}		
		sys.setIcon(iconResult.getValue().getIconId());
		return this.sysService.updateObject(sys);
	}

	/**
	 * 產生 tb_sys_multi_name 資料
	 * 
	 * @param multiName
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )
	@Override
	public DefaultResult<SysMultiNameVO> createMultiName(SysMultiNameVO multiName) throws ServiceException, Exception {
		if (null == multiName || super.isBlank(multiName.getSysId()) || super.isBlank(multiName.getName())
				|| super.isBlank(multiName.getLocaleCode())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (LocaleLanguageUtils.getMap().get(multiName.getLocaleCode()) == null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		SysVO sys = new SysVO();
		sys.setSysId(multiName.getSysId());
		DefaultResult<SysVO> sysResult = this.sysService.findByUK(sys);
		if (sysResult.getValue() == null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		super.setStringValueMaxLength(multiName, "name", 100);
		return this.sysMultiNameService.saveObject(multiName);
	}

}
