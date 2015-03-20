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
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExpr;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExprMap;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprMapService;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprService;
import com.netsteadfast.greenstep.service.ISysBeanHelpService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.logic.ISystemBeanHelpLogicService;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprMapVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemBeanHelpLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemBeanHelpLogicServiceImpl extends BaseLogicService implements ISystemBeanHelpLogicService {
	protected Logger logger=Logger.getLogger(SystemBeanHelpLogicServiceImpl.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService;	
	private ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService;
	private ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> sysBeanHelpExprMapService;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	
	public SystemBeanHelpLogicServiceImpl() {
		super();
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

	public ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> getSysBeanHelpExprService() {
		return sysBeanHelpExprService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprService")
	@Required			
	public void setSysBeanHelpExprService(
			ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService) {
		this.sysBeanHelpExprService = sysBeanHelpExprService;
	}

	public ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> getSysBeanHelpExprMapService() {
		return sysBeanHelpExprMapService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprMapService")
	@Required		
	public void setSysBeanHelpExprMapService(
			ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> sysBeanHelpExprMapService) {
		this.sysBeanHelpExprMapService = sysBeanHelpExprMapService;
	}

	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required		
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysBeanHelpVO> create(SysBeanHelpVO beanHelp, String systemOid) throws ServiceException, Exception {
		if (beanHelp==null || super.isBlank(systemOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		sys = sResult.getValue();
		beanHelp.setSystem(sys.getSysId());		
		return sysBeanHelpService.saveObject(beanHelp);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysBeanHelpVO> update(SysBeanHelpVO beanHelp, String systemOid) throws ServiceException, Exception {
		if (beanHelp==null || super.isBlank(beanHelp.getOid()) || super.isBlank(systemOid) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		sys = sResult.getValue();
		beanHelp.setSystem(sys.getSysId());		
		return sysBeanHelpService.updateObject(beanHelp);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysBeanHelpVO beanHelp) throws ServiceException, Exception {
		if (beanHelp==null || super.isBlank(beanHelp.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		// delete TB_SYS_BEAN_HELP_EXPR
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("helpOid", beanHelp.getOid());
		List<TbSysBeanHelpExpr> exprList = this.sysBeanHelpExprService.findListByParams(params);
		for (int i=0; exprList!=null && i<exprList.size(); i++) {
			// delete TB_SYS_BEAN_HELP_EXPR_MAP
			TbSysBeanHelpExpr helpExpr = exprList.get(i);
			params.clear();
			params.put("helpExprOid", helpExpr.getOid());
			List<TbSysBeanHelpExprMap> exprMapList = this.sysBeanHelpExprMapService.findListByParams(params);
			for (int j=0; exprMapList!=null && j<exprMapList.size(); j++) {
				TbSysBeanHelpExprMap helpExprMap = exprMapList.get(j);
				this.sysBeanHelpExprMapService.delete(helpExprMap);
			}
			this.sysBeanHelpExprService.delete(helpExpr);
		}				
		return sysBeanHelpService.deleteObject(beanHelp);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysBeanHelpExprVO> createExpr(
			SysBeanHelpExprVO beanHelpExpr, String helpOid, String expressionOid) throws ServiceException, Exception {
		if (beanHelpExpr==null || super.isBlank(helpOid) || super.isBlank(expressionOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysBeanHelpVO sysBeanHelp = new SysBeanHelpVO();
		sysBeanHelp.setOid(helpOid);
		DefaultResult<SysBeanHelpVO> mResult = this.sysBeanHelpService.findObjectByOid(sysBeanHelp);
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		sysBeanHelp = mResult.getValue(); // 查看有沒有資料
		
		SysExpressionVO sysExpression = new SysExpressionVO();
		sysExpression.setOid(expressionOid);
		DefaultResult<SysExpressionVO> exprResult = this.sysExpressionService.findObjectByOid(sysExpression);
		if (exprResult.getValue()==null) {
			throw new ServiceException(exprResult.getSystemMessage().getValue());
		}
		sysExpression = exprResult.getValue();
		
		beanHelpExpr.setHelpOid( sysBeanHelp.getOid() );
		beanHelpExpr.setExprId( sysExpression.getExprId() );		
		return this.sysBeanHelpExprService.saveObject(beanHelpExpr);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteExpr(SysBeanHelpExprVO beanHelpExpr) throws ServiceException, Exception {
		if (null==beanHelpExpr || super.isBlank(beanHelpExpr.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysBeanHelpExprVO> oldResult = this.sysBeanHelpExprService.findObjectByOid(beanHelpExpr);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("helpExprOid", oldResult.getValue().getOid() );
		List<TbSysBeanHelpExprMap> mapList = this.sysBeanHelpExprMapService.findListByParams(params);
		for (int i=0; mapList!=null && i<mapList.size(); i++) {
			this.sysBeanHelpExprMapService.delete( mapList.get(i) );
		}		
		return this.sysBeanHelpExprService.deleteObject(beanHelpExpr);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<SysBeanHelpExprMapVO> createExprMap(
			SysBeanHelpExprMapVO beanHelpExprMap, String helpExprOid) throws ServiceException, Exception {
		if (beanHelpExprMap==null || super.isBlank(helpExprOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		SysBeanHelpExprVO sysBeanHelpExpr = new SysBeanHelpExprVO();
		sysBeanHelpExpr.setOid(helpExprOid);
		DefaultResult<SysBeanHelpExprVO> mResult = this.sysBeanHelpExprService.findObjectByOid(sysBeanHelpExpr);
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		sysBeanHelpExpr = mResult.getValue(); // 查看有沒有資料
		beanHelpExprMap.setHelpExprOid( sysBeanHelpExpr.getOid() );		
		return this.sysBeanHelpExprMapService.saveObject(beanHelpExprMap);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> deleteExprMap(SysBeanHelpExprMapVO beanHelpExprMap) throws ServiceException, Exception {
		if (beanHelpExprMap==null || super.isBlank(beanHelpExprMap.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.sysBeanHelpExprMapService.deleteObject(beanHelpExprMap);
	}

}
