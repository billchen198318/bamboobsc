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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.po.hbm.TbSysJreportParam;
import com.netsteadfast.greenstep.service.ISysJreportParamService;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.service.logic.ISystemJreportLogicService;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemJreportLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemJreportLogicServiceImpl extends BaseLogicService implements ISystemJreportLogicService {
	protected Logger logger=Logger.getLogger(SystemJreportLogicServiceImpl.class);
	private final static int MAX_DESCRIPTION_LENGTH = 500;
	private ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService;
	private ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String > sysJreportParamService;
	
	public SystemJreportLogicServiceImpl() {
		super();
	}
	
	public ISysJreportService<SysJreportVO, TbSysJreport, String> getSysJreportService() {
		return sysJreportService;
	}
	
	@Autowired
	@Resource(name="core.service.SysJreportService")
	@Required			
	public void setSysJreportService(
			ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService) {
		this.sysJreportService = sysJreportService;
	}
	
	public ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> getSysJreportParamService() {
		return sysJreportParamService;
	}
	
	@Autowired
	@Resource(name="core.service.SysJreportParamService")
	@Required		
	public void setSysJreportParamService(
			ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService) {
		this.sysJreportParamService = sysJreportParamService;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysJreportVO> create(SysJreportVO report) throws ServiceException, Exception {
		if (report==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		if (super.defaultString(report.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			report.setDescription( report.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );			
		}		
		if (YesNo.YES.equals(report.getIsCompile())) {
			report.setFile( report.getReportId() + ".jrxml");
		} else {
			report.setFile( report.getReportId() + ".jasper");
		}
		return this.sysJreportService.saveObject(report);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysJreportVO> update(SysJreportVO report) throws ServiceException, Exception {
		if (report==null || super.isBlank(report.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysJreportVO> oldResult = this.sysJreportService.findObjectByOid(report);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}		
		report.setReportId( oldResult.getValue().getReportId() );		
		byte[] content = oldResult.getValue().getContent();
		
		/**
		 * 先將之前的 CONTENT null 在 update , 要不然 CONTENT 會越來越大
		 */
		oldResult.getValue().setContent(null);
		this.sysJreportService.updateObject(oldResult.getValue());
		
		if (super.defaultString(report.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			report.setDescription( report.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );			
		}		
		if (report.getContent()==null) { // 沒有上傳新的jasper,jrxml檔案
			report.setContent( content );			
		}		
		if (YesNo.YES.equals(report.getIsCompile())) {
			report.setFile( report.getReportId() + ".jrxml");
		} else {
			report.setFile( report.getReportId() + ".jasper");
		}		
		return this.sysJreportService.updateObject(report);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysJreportVO report) throws ServiceException, Exception {
		if (report==null || super.isBlank(report.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		DefaultResult<SysJreportVO> mResult = this.sysJreportService.findForSimple(report.getOid());
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		report = mResult.getValue();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", report.getReportId());
		List<TbSysJreportParam> searchList = this.sysJreportParamService.findListByParams(params);
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			sysJreportParamService.delete(searchList.get(i));
		}
		return sysJreportService.deleteObject(report);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<SysJreportParamVO> createParam(SysJreportParamVO reportParam, String reportOid)
			throws ServiceException, Exception {
		if (reportParam==null || super.isBlank(reportOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysJreportVO> mResult = this.sysJreportService.findForSimple(reportOid);
		if (mResult.getValue()==null) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		SysJreportVO report = mResult.getValue();
		reportParam.setReportId(report.getReportId());
		return this.sysJreportParamService.saveObject(reportParam);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> deleteParam(SysJreportParamVO reportParam) throws ServiceException, Exception {
		if (reportParam==null || super.isBlank(reportParam.getOid())) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		return this.sysJreportParamService.deleteObject(reportParam);
	}

}
